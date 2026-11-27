// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 f354（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            var n = r("d039"),
                i = r("b622"),
                a = r("83ab"),
                o = r("c430"),
                s = i("iterator");
            e.exports = !n((function() {
                var e = new URL("b?a=1&b=2&c=3", "https://a"),
                    t = e.searchParams,
                    r = new URLSearchParams("a=1&a=2&b=3"),
                    n = "";
                return e.pathname = "c%20d", t.forEach((function(e, r) {
                    t["delete"]("b"), n += r + e
                })), r["delete"]("a", 2), r["delete"]("b", void 0), o && (!e.toJSON || !r.has("a", 1) || r.has("a", 2) || !r.has("a", void 0) || r.has("b")) || !t.size && (o || !a) || !t.sort || "https://a/c%20d?a=1&c=3" !== e.href || "3" !== t.get("c") || "a=1" !== String(new URLSearchParams("?a=1")) || !t[s] || "a" !== new URL("https://a@b").username || "b" !== new URLSearchParams(new URLSearchParams("a=b")).get("a") || "xn--e1aybc" !== new URL("https://тест").host || "#%D0%B1" !== new URL("https://a#б").hash || "a1c3" !== n || "x" !== new URL("https://x", void 0).host
            }))
        
