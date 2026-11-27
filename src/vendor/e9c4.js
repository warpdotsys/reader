// chunk-vendors webpack 模块 e9c4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./d066.js"),
                o = n("./2ba4.js"),
                a = n("./c65b.js"),
                s = n("./e330.js"),
                u = n("./d039.js"),
                c = n("./1626.js"),
                l = n("./d9b5.js"),
                f = n("./f36a.js"),
                d = n("./e267.js"),
                h = n("./04f8.js"),
                p = String,
                v = i("JSON", "stringify"),
                m = s(/./.exec),
                g = s("".charAt),
                b = s("".charCodeAt),
                y = s("".replace),
                _ = s(1..toString),
                w = /[\uD800-\uDFFF]/g,
                x = /^[\uD800-\uDBFF]$/,
                C = /^[\uDC00-\uDFFF]$/,
                S = !h || u((function() {
                    var e = i("Symbol")("stringify detection");
                    return "[null]" !== v([e]) || "{}" !== v({
                        a: e
                    }) || "{}" !== v(Object(e))
                })),
                O = u((function() {
                    return '"\\udf06\\ud834"' !== v("\udf06\ud834") || '"\\udead"' !== v("\udead")
                })),
                E = function(e, t) {
                    var n = f(arguments),
                        r = d(t);
                    if (c(r) || void 0 !== e && !l(e)) return n[1] = function(e, t) {
                        if (c(r) && (t = a(r, this, p(e), t)), !l(t)) return t
                    }, o(v, null, n)
                },
                k = function(e, t, n) {
                    var r = g(n, t - 1),
                        i = g(n, t + 1);
                    return m(x, e) && !m(C, i) || m(C, e) && !m(x, r) ? "\\u" + _(b(e, 0), 16) : e
                };
            v && r({
                target: "JSON",
                stat: !0,
                arity: 3,
                forced: S || O
            }, {
                stringify: function(e, t, n) {
                    var r = f(arguments),
                        i = o(S ? E : v, null, r);
                    return O && "string" == typeof i ? y(i, w, k) : i
                }
            })
        
