// chunk-vendors webpack 模块 13d2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./d039.js"),
                o = n("./1626.js"),
                a = n("./1a2d.js"),
                s = n("./83ab.js"),
                u = n("./5e77.js").CONFIGURABLE,
                c = n("./8925.js"),
                l = n("./69f3.js"),
                f = l.enforce,
                d = l.get,
                h = String,
                p = Object.defineProperty,
                v = r("".slice),
                m = r("".replace),
                g = r([].join),
                b = s && !i((function() {
                    return 8 !== p((function() {}), "length", {
                        value: 8
                    }).length
                })),
                y = String(String).split("String"),
                _ = e.exports = function(e, t, n) {
                    "Symbol(" === v(h(t), 0, 7) && (t = "[" + m(h(t), /^Symbol\(([^)]*)\).*$/, "$1") + "]"), n && n.getter && (t = "get " + t), n && n.setter && (t = "set " + t), (!a(e, "name") || u && e.name !== t) && (s ? p(e, "name", {
                        value: t,
                        configurable: !0
                    }) : e.name = t), b && n && a(n, "arity") && e.length !== n.arity && p(e, "length", {
                        value: n.arity
                    });
                    try {
                        n && a(n, "constructor") && n.constructor ? s && p(e, "prototype", {
                            writable: !1
                        }) : e.prototype && (e.prototype = void 0)
                    } catch (i) {}
                    var r = f(e);
                    return a(r, "source") || (r.source = g(y, "string" == typeof t ? t : "")), e
                };
            Function.prototype.toString = _((function() {
                return o(this) && d(this).source || c(this)
            }), "toString")
        
