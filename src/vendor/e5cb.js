// chunk-vendors webpack 模块 e5cb（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d066.js"),
                i = n("./1a2d.js"),
                o = n("./9112.js"),
                a = n("./3a9b.js"),
                s = n("./d2bb.js"),
                u = n("./e893.js"),
                c = n("./aeb0.js"),
                l = n("./7156.js"),
                f = n("./e391.js"),
                d = n("./ab36.js"),
                h = n("./6f19.js"),
                p = n("./83ab.js"),
                v = n("./c430.js");
            e.exports = function(e, t, n, m) {
                var g = "stackTraceLimit",
                    b = m ? 2 : 1,
                    y = e.split("."),
                    _ = y[y.length - 1],
                    w = r.apply(null, y);
                if (w) {
                    var x = w.prototype;
                    if (!v && i(x, "cause") && delete x.cause, !n) return w;
                    var C = r("Error"),
                        S = t((function(e, t) {
                            var n = f(m ? t : e, void 0),
                                r = m ? new w(e) : new w;
                            return void 0 !== n && o(r, "message", n), h(r, S, r.stack, 2), this && a(x, this) && l(r, this, S), arguments.length > b && d(r, arguments[b]), r
                        }));
                    if (S.prototype = x, "Error" !== _ ? s ? s(S, C) : u(S, C, {
                            name: !0
                        }) : p && g in w && (c(S, w, g), c(S, w, "prepareStackTrace")), u(S, w), !v) try {
                        x.name !== _ && o(x, "name", _), x.constructor = S
                    } catch (O) {}
                    return S
                }
            }
        
