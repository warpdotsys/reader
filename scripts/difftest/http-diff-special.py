#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
特殊路由差分：文件类 + SSE 类。
http-diff.py 只比文本 JSON，这里处理：
  - 二进制响应（封面、下载、导出）：比状态码 + Content-Type + 长度 + 内容哈希
  - SSE 流式响应：读前 N 个事件，比事件序列

用法：
    python http-diff-special.py --art http://localhost:8830 --loc http://localhost:8831 \
                               --out result.txt
"""
import argparse
import hashlib
import re
import socket
import time
import urllib.error
import urllib.parse
import urllib.request

TS_RE = re.compile(rb'\b1[0-9]{12}\b')
UUID_RE = re.compile(rb'[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}', re.I)


def norm_bytes(b):
    if b is None:
        return None
    b = TS_RE.sub(b'<ts>', b)
    b = UUID_RE.sub(b'<uuid>', b)
    return b


def fetch_raw(base, method, path, body=None, timeout=15):
    url = base.rstrip('/') + path
    data = body.encode('utf-8') if isinstance(body, str) else body
    headers = {}
    if data is not None:
        headers['Content-Type'] = 'application/json'
    req = urllib.request.Request(url, data=data, headers=headers, method=method)
    try:
        with urllib.request.urlopen(req, timeout=timeout) as r:
            return r.status, r.read(), dict(r.headers)
    except urllib.error.HTTPError as e:
        return e.code, e.read(), dict(e.headers)
    except Exception as e:
        return -1, f'{type(e).__name__}: {e}'.encode(), {}


def fetch_sse(base, path, timeout=8, max_events=5):
    """读 SSE 流的前 N 个事件后主动断开"""
    url = base.rstrip('/') + path
    events = []
    try:
        req = urllib.request.Request(url, method='GET')
        with urllib.request.urlopen(req, timeout=timeout) as r:
            buf = b''
            deadline = time.time() + timeout
            while len(events) < max_events and time.time() < deadline:
                chunk = r.read(512)
                if not chunk:
                    break
                buf += chunk
                while b'\n\n' in buf:
                    ev, buf = buf.split(b'\n\n', 1)
                    events.append(norm_bytes(ev).decode('utf-8', 'replace'))
                    if len(events) >= max_events:
                        break
    except Exception as e:
        events.append(f'<{type(e).__name__}>')
    return events


def sig(status, body, headers):
    """响应的可比签名"""
    ct = headers.get('Content-Type', '') or headers.get('content-type', '')
    # 去掉 charset 之外的参数抖动
    ct = ct.split(';')[0].strip()
    nb = norm_bytes(body)
    h = hashlib.sha256(nb).hexdigest()[:16] if nb else 'empty'
    return f'status={status} type={ct} len={len(body) if body else 0} hash={h}'


FILE_ROUTES = [
    ('GET', '/reader3/cover', None),
    ('GET', '/reader3/file/download', None),
    ('GET', '/reader3/file/download?path=', None),
    ('GET', '/reader3/file/list', None),
    ('GET', '/reader3/exportBook', None),
    ('POST', '/reader3/exportBook', '{}'),
    ('POST', '/reader3/exportBook', '{"url":""}'),
    ('GET', '/reader3/generateKeys', None),
    ('POST', '/reader3/generateKeys', '{}'),
    ('GET', '/reader3/generateLicense', None),
    ('POST', '/reader3/generateLicense', '{}'),
    ('GET', '/reader3/user/downloadBackupFile', None),
    ('POST', '/reader3/cacheBookOnServer', '{}'),
    ('POST', '/reader3/cacheBookOnServer', '{"bookUrlList":[]}'),
]

SSE_ROUTES = [
    '/reader3/searchBookMultiSSE',
    '/reader3/searchBookSourceSSE',
    '/reader3/bookSourceDebugSSE',
    '/reader3/cacheBookSSE',
]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--art', required=True)
    ap.add_argument('--loc', required=True)
    ap.add_argument('--out', required=True)
    ap.add_argument('--timeout', type=int, default=15)
    ap.add_argument('--sse-timeout', type=int, default=8)
    args = ap.parse_args()

    passed = failed = 0
    lines = [f'# 特殊路由差分  制品 {args.art}  vs  本地 {args.loc}', '']

    lines.append('## 文件/二进制类')
    for method, path, body in FILE_ROUTES:
        sa, ba, ha = fetch_raw(args.art, method, path, body, args.timeout)
        sb, bb, hb = fetch_raw(args.loc, method, path, body, args.timeout)
        la, lb = sig(sa, ba, ha), sig(sb, bb, hb)
        label = f'{method} {path}' + (f'  body={body}' if body else '')
        if la == lb:
            passed += 1
            lines.append(f'  OK   {label}\n       {la}')
        else:
            failed += 1
            lines.append(f'  DIFF {label}\n       制品 {la}\n       本地 {lb}')
        lines.append('')

    lines.append('## SSE 流式类（读前 5 个事件）')
    for path in SSE_ROUTES:
        ea = fetch_sse(args.art, path, args.sse_timeout)
        eb = fetch_sse(args.loc, path, args.sse_timeout)
        label = f'GET {path}'
        if ea == eb:
            passed += 1
            lines.append(f'  OK   {label}  ({len(ea)} 事件)')
            for e in ea[:2]:
                lines.append(f'         {e[:120]}')
        else:
            failed += 1
            lines.append(f'  DIFF {label}')
            lines.append(f'       制品 {ea[:3]}')
            lines.append(f'       本地 {eb[:3]}')
        lines.append('')

    lines.append(f'=== 通过 {passed} / 差异 {failed} ===')
    with open(args.out, 'w', encoding='utf-8') as f:
        f.write('\n'.join(lines))
    print(f'通过 {passed} / 差异 {failed}')


if __name__ == '__main__':
    main()
