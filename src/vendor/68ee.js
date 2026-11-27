// chunk-vendors webpack 模块 68ee（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./e330.js"),
                i = n("./d039.js"),
                o = n("./1626.js"),
                a = n("./f5df.js"),
                s = n("./d066.js"),
                u = n("./8925.js"),
                c = function() {},
                l = s("Reflect", "construct"),
                f = /^\s*(?:class|function)\b/,
                d = r(f.exec),
                h = !f.test(c),
                p = function(e) {
                    if (!o(e)) return !1;
                    try {
                        return l(c, [], e), !0
                    } catch (t) {
                        return !1
                    }
                },
                v = function(e) {
                    if (!o(e)) return !1;
                    switch (a(e)) {
                        case "AsyncFunction":
                        case "GeneratorFunction":
                        case "AsyncGeneratorFunction":
                            return !1
                    }
                    try {
                        return h || !!d(f, u(e))
                    } catch (t) {
                        return !0
                    }
                };
            v.sham = !0, e.exports = !l || i((function() {
                var e;
                return p(p.call) || !p(Object) || !p((function() {
                    e = !0
                })) || e
            })) ? v : p
        
