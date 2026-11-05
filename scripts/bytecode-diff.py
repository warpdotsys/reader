#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""字节码差异统计器（Python 版，替代 verify-bytecode.ps1 的慢路径）

用法：
    python scripts/bytecode-diff.py --artifact D:/Projects/reader/BOOT-INF/classes \
                                   --local-dir build/classes/kotlin/main \
                                   --local-dir build/classes/java/main \
                                   --out scratch/bc_diff.json

原理：对每个类分别 javap -c -p 制品和本地，抽出指令行（形如 "  12: invokevirtual #34"），
归一化后逐行比较。会比 PowerShell 版快——javap 进程数相同，但少了 PowerShell 的
管道开销和正则回溯。
"""
import argparse
import json
import os
import re
import subprocess
import sys
from concurrent.futures import ThreadPoolExecutor

JAVAP = os.environ.get('JAVAP', r'D:\Tools\java\jdk-11.0.8+10\bin\javap.exe')

# 指令行：  "   12: invokevirtual #34 // Method foo:()V"
INSN_RE = re.compile(r'^\s+(\d+):\s+(\S+)(.*)$')
# 方法签名行："  public final Object foo(java.lang.String);"
METH_RE = re.compile(r'^\s{2}\S.*?\s([\w$<>]+)\(')


def dump(cp, cls):
    """返回 (方法 -> 指令列表) 的字典。"""
    try:
        r = subprocess.run([JAVAP, '-c', '-p', '-classpath', cp, cls],
                           capture_output=True, text=True, errors='replace', timeout=90)
    except subprocess.TimeoutExpired:
        return None
    if r.returncode != 0 or not r.stdout:
        return None

    methods, cur = {}, None
    for line in r.stdout.splitlines():
        m = METH_RE.match(line)
        if m and 'Code:' not in line:
            cur = m.group(1)
            methods.setdefault(cur, [])
            continue
        if cur is None:
            continue
        mi = INSN_RE.match(line)
        if mi:
            # 只保留操作码 + 操作数里排除掉常量池编号带来的抖动，保留可读部分
            op, operand = mi.group(2), mi.group(3)
            # 去掉 "// Method ..." 这类注释尾巴之前的部分已经够用
            methods[cur].append(f'{op}{operand}'.rstrip())
    return methods


def norm(seq):
    """归一化：
    - 折叠空白
    - 把常量池编号 #123 统一成 #（编号分配不同不代表语义不同）
    """
    out = []
    for s in seq:
        s = re.sub(r'\s+', ' ', s).strip()
        # ldc_w / ldc2_w 只是常量池索引宽度不同（编号 > 255 时用宽指令），不是语义差异
        s = s.replace('ldc_w ', 'ldc ').replace('ldc2_w ', 'ldc2 ')
        s = re.sub(r'#\d+', '#', s)
        # 跳转目标偏移：ifle 49 vs ifle 48 只是字节偏移不同，指令序列相同不代表语义不同
        s = re.sub(r'\b(if\w+|goto|jsr|ret)\s+\d+\b', r'\1', s)
        # tableswitch/lookupswitch 的跳转表是纯数字行（"  109" / "  109: 179"），全是偏移，不是指令
        if re.fullmatch(r'\d+(:\s*\d+)?', s):
            continue
        # nop 是编译器为行号/调试插入的占位，两个编译器的插入点不同，不代表语义
        if s == 'nop':
            continue
        out.append(s)
    return out


def scan(root):
    out = {}
    for dp, _, fs in os.walk(root):
        for f in fs:
            if f.endswith('.class'):
                full = os.path.join(dp, f)
                out[os.path.relpath(full, root)[:-6].replace('\\', '.')] = full
    return out


def compare_one(args):
    cls, art_cp, loc_cp = args
    a = dump(art_cp, cls)
    if a is None:
        return cls, None, None, None
    b = dump(loc_cp, cls)
    if b is None:
        return cls, None, None, None

    a_names, b_names = set(a), set(b)
    common = a_names & b_names
    only_a = sorted(a_names - b_names)
    only_b = sorted(b_names - a_names)

    diff_methods = []
    for name in sorted(common):
        sa, sb = norm(a[name]), norm(b[name])
        if sa != sb:
            # delta = 长度差 + 逐行不匹配数
            delta = sum(1 for x, y in zip(sa, sb) if x != y) + abs(len(sa) - len(sb))
            diff_methods.append({'method': name, 'delta': delta,
                                 'artifact_len': len(sa), 'local_len': len(sb)})

    return cls, only_a, only_b, diff_methods


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--artifact', required=True)
    ap.add_argument('--local-dir', action='append', required=True)
    ap.add_argument('--out', default=None)
    ap.add_argument('--jobs', type=int, default=8)
    ap.add_argument('--only', default=None, help='只比对类名匹配此正则的类')
    a = ap.parse_args()

    art = scan(a.artifact)
    loc = {}
    for d in a.local_dir:
        loc.update(scan(d))

    classes = sorted(set(art) & set(loc))
    if a.only:
        pat = re.compile(a.only)
        classes = [c for c in classes if pat.search(c)]

    tasks = [(c, a.artifact, os.pathsep.join(a.local_dir)) for c in classes]
    results = []
    with ThreadPoolExecutor(max_workers=a.jobs) as ex:
        for i, res in enumerate(ex.map(compare_one, tasks), 1):
            results.append(res)
            if i % 50 == 0:
                print(f'  ... {i}/{len(tasks)}', file=sys.stderr)

    exact, different, uncomparable = [], [], []
    for cls, only_a, only_b, diff_methods in results:
        if only_a is None:
            uncomparable.append(cls)
        elif not only_a and not only_b and not diff_methods:
            exact.append(cls)
        else:
            different.append({
                'class': cls,
                'only_in_artifact': only_a,
                'only_in_local': only_b,
                'methods': sorted(diff_methods, key=lambda x: -x['delta'])[:12],
                'total_delta': sum(m['delta'] for m in diff_methods),
            })

    different.sort(key=lambda x: -x['total_delta'])
    summary = {
        'compared': len(classes),
        'exact': len(exact),
        'different': len(different),
        'uncomparable': len(uncomparable),
        'uncomparable_classes': uncomparable,
        'different_classes': different,
    }
    print(json.dumps({k: v for k, v in summary.items() if not isinstance(v, list)} | {
        'uncomparable_classes': uncomparable[:20],
        'top20': [(d['class'], d['total_delta']) for d in different[:20]],
    }, indent=2, ensure_ascii=False))

    if a.out:
        with open(a.out, 'w', encoding='utf-8') as f:
            json.dump(summary, f, ensure_ascii=False, indent=1)
        print(f'\n完整结果已写入 {a.out}', file=sys.stderr)


if __name__ == '__main__':
    main()
