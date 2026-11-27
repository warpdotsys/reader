// chunk-vendors webpack 模块 3c65（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./7b0b.js"),
                o = n("./07fa.js"),
                a = n("./3a34.js"),
                s = n("./083a.js"),
                u = n("./3511.js"),
                c = 1 !== [].unshift(0),
                l = function() {
                    try {
                        Object.defineProperty([], "length", {
                            writable: !1
                        }).unshift()
                    } catch (e) {
                        return e instanceof TypeError
                    }
                },
                f = c || !l();
            r({
                target: "Array",
                proto: !0,
                arity: 1,
                forced: f
            }, {
                unshift: function(e) {
                    var t = i(this),
                        n = o(t),
                        r = arguments.length;
                    if (r) {
                        u(n + r);
                        var c = n;
                        while (c--) {
                            var l = c + r;
                            c in t ? t[l] = t[c] : s(t, l)
                        }
                        for (var f = 0; f < r; f++) t[f] = arguments[f]
                    }
                    return a(t, n + r)
                }
            })
        
