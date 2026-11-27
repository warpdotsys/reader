// chunk-vendors webpack 模块 20cc（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./2ba4.js"),
                o = n("./1626.js"),
                a = n("./8558.js"),
                s = n("./b5db.js"),
                u = n("./f36a.js"),
                c = n("./d6d6.js"),
                l = r.Function,
                f = /MSIE .\./.test(s) || "BUN" === a && function() {
                    var e = r.Bun.version.split(".");
                    return e.length < 3 || "0" === e[0] && (e[1] < 3 || "3" === e[1] && "0" === e[2])
                }();
            e.exports = function(e, t) {
                var n = t ? 2 : 1;
                return f ? function(r, a) {
                    var s = c(arguments.length, 1) > n,
                        f = o(r) ? r : l(r),
                        d = s ? u(arguments, n) : [],
                        h = s ? function() {
                            i(f, this, d)
                        } : f;
                    return t ? e(h, a) : e(h)
                } : e
            }
        
