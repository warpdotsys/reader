#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
HTTP 层差分测试：对制品实例和本地构建实例发同样的请求，对比响应。

用法：
    python http-diff.py --art http://localhost:8830 --loc http://localhost:8831 \
                        --routes routes.txt --out result.txt
"""
import argparse
import json
import re
import sys
import urllib.error
import urllib.parse
import urllib.request

# 需要归一化的动态内容
TS_RE = re.compile(r'\b1[0-9]{12}\b')
UUID_RE = re.compile(r'[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}', re.I)
STACK_RE = re.compile(r'\n\tat [^\n]+')


def norm(text):
    """把响应里的动态内容归一化"""
    if text is None:
        return None
    t = TS_RE.sub('<ts>', text)
    t = UUID_RE.sub('<uuid>', t)
    m = STACK_RE.search(t)
    if m:
        t = t[:m.start()] + '<stacktrace>'
    return t


def fetch(base, method, path, body=None, headers=None, timeout=10):
    url = base.rstrip('/') + path
    data = None
    hdrs = dict(headers or {})
    if body is not None:
        data = body.encode('utf-8') if isinstance(body, str) else body
        hdrs.setdefault('Content-Type', 'application/json')
    req = urllib.request.Request(url, data=data, headers=hdrs, method=method)
    try:
        with urllib.request.urlopen(req, timeout=timeout) as r:
            return r.status, r.read().decode('utf-8', errors='replace'), dict(r.headers)
    except urllib.error.HTTPError as e:
        return e.code, e.read().decode('utf-8', errors='replace'), dict(e.headers)
    except Exception as e:
        return -1, f'{type(e).__name__}: {e}', {}


def compare(art_base, loc_base, method, path, body=None, timeout=10):
    sa, ba, ha = fetch(art_base, method, path, body, timeout=timeout)
    sb, bb, hb = fetch(loc_base, method, path, body, timeout=timeout)

    ta, tb = norm(ba), norm(bb)
    status_ok = (sa == sb)
    body_ok = (ta == tb)

    if status_ok and body_ok:
        return True, None

    detail = []
    if not status_ok:
        detail.append(f'status {sa} vs {sb}')
    if not body_ok:
        # 找首个差异位置
        na, nb = (ta or ''), (tb or '')
        n = min(len(na), len(nb))
        i = 0
        while i < n and na[i] == nb[i]:
            i += 1
        ctx_a = na[max(0, i - 40):i + 80].replace('\n', '\\n')
        ctx_b = nb[max(0, i - 40):i + 80].replace('\n', '\\n')
        detail.append(f'body@{i} 制品={ctx_a!r} 本地={ctx_b!r}')
    return False, '; '.join(detail)


DEFAULT_ROUTES = [
    # (method, path, body)
    ('GET', '/health', None),
    ('GET', '/reader3/getSystemInfo', None),
    ('GET', '/reader3/getBookSources', None),
    ('GET', '/reader3/getBookSource', None),
    ('GET', '/reader3/getBookshelf', None),
    ('GET', '/reader3/getBookGroups', None),
    ('GET', '/reader3/getRssSources', None),
    ('GET', '/reader3/getBookmarks', None),
    ('GET', '/reader3/getReplaceRules', None),
    ('GET', '/reader3/getUsers', None),
    ('GET', '/reader3/getUserInfo', None),
    ('GET', '/reader3/getUserLimit', None),
    ('GET', '/reader3/getUserConfig', None),
    ('GET', '/reader3/getAvailableBookSource', None),
    ('GET', '/reader3/getShelfBookWithCacheInfo', None),
    ('GET', '/reader3/getShelfBookWithCacheInfo?url=', None),
    ('GET', '/reader3/getBookInfo', None),
    ('GET', '/reader3/getChapterList', None),
    ('GET', '/reader3/getBookContent', None),
    ('GET', '/reader3/getBookShelfBooks', None),
    ('GET', '/reader3/clearAllBookSource', None),
    ('GET', '/reader3/deleteAllBookSources', None),
    ('GET', '/nonexistent-endpoint-xyz', None),
    ('POST', '/reader3/getBookSource', '{}'),
    ('POST', '/reader3/getBookSources', '{}'),
    ('POST', '/reader3/getBookShelfBooks', '{}'),
    ('POST', '/reader3/getAvailableBookSource', '{}'),
    ('POST', '/reader3/getAvailableBookSource', '{"url":""}'),
    ('POST', '/reader3/getChapterList', '{}'),
    ('POST', '/reader3/getChapterList', '{"url":""}'),
    ('POST', '/reader3/saveBookSource', '{}'),
    ('POST', '/reader3/saveBookSources', '{}'),
    ('POST', '/reader3/deleteBookSource', '{}'),
    ('POST', '/reader3/deleteBookSources', '{}'),
    ('POST', '/reader3/addUser', '{}'),
    ('POST', '/reader3/resetPassword', '{}'),
    ('POST', '/reader3/getChapterListByRule', '{}'),
    ('POST', '/reader3/getBookContentByRule', '{}'),
]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--art', required=True)
    ap.add_argument('--loc', required=True)
    ap.add_argument('--routes', help='路由文件（每行 method path [body]）')
    ap.add_argument('--out', required=True)
    ap.add_argument('--timeout', type=int, default=10)
    args = ap.parse_args()

    routes = []
    if args.routes:
        with open(args.routes, encoding='utf-8') as f:
            for line in f:
                line = line.strip()
                if not line or line.startswith('#'):
                    continue
                parts = line.split(' ', 2)
                m = parts[0].upper()
                p = parts[1]
                b = parts[2] if len(parts) > 2 else None
                routes.append((m, p, b))
    else:
        routes = DEFAULT_ROUTES

    passed = failed = 0
    failures = []

    with open(args.out, 'w', encoding='utf-8') as out:
        out.write(f'# 制品 {args.art}  vs  本地 {args.loc}\n')
        out.write(f'# 共 {len(routes)} 条路由\n\n')
        for method, path, body in routes:
            ok, detail = compare(args.art, args.loc, method, path, body, args.timeout)
            label = f'{method} {path}' + (f'  body={body}' if body else '')
            if ok:
                passed += 1
                out.write(f'  OK   {label}\n')
            else:
                failed += 1
                failures.append(f'{label}\n       {detail}')
                out.write(f'  DIFF {label}\n       {detail}\n')
            out.flush()

        out.write(f'\n=== 通过 {passed} / 差异 {failed} ===\n')
        if failures:
            out.write('\n=== 差异明细 ===\n')
            for f in failures:
                out.write(f + '\n')

    print(f'通过 {passed} / 差异 {failed}')


if __name__ == '__main__':
    main()
