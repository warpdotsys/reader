// chunk-vendors webpack 模块 4738（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./d256.js"),
                o = n("./1626.js"),
                a = n("./94ca.js"),
                s = n("./8925.js"),
                u = n("./b622.js"),
                c = n("./8558.js"),
                l = n("./c430.js"),
                f = n("./1212.js"),
                d = i && i.prototype,
                h = u("species"),
                p = !1,
                v = o(r.PromiseRejectionEvent),
                m = a("Promise", (function() {
                    var e = s(i),
                        t = e !== String(i);
                    if (!t && 66 === f) return !0;
                    if (l && (!d["catch"] || !d["finally"])) return !0;
                    if (!f || f < 51 || !/native code/.test(e)) {
                        var n = new i((function(e) {
                                e(1)
                            })),
                            r = function(e) {
                                e((function() {}), (function() {}))
                            },
                            o = n.constructor = {};
                        if (o[h] = r, p = n.then((function() {})) instanceof r, !p) return !0
                    }
                    return !t && ("BROWSER" === c || "DENO" === c) && !v
                }));
            e.exports = {
                CONSTRUCTOR: m,
                REJECTION_EVENT: v,
                SUBCLASSING: p
            }
        
