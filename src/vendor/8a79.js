// chunk-vendors webpack 模块 8a79（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./4625.js"),
                o = n("./06cf.js").f,
                a = n("./50c4.js"),
                s = n("./577e.js"),
                u = n("./5a34.js"),
                c = n("./1d80.js"),
                l = n("./ab13.js"),
                f = n("./c430.js"),
                d = i("".slice),
                h = Math.min,
                p = l("endsWith"),
                v = !f && !p && !! function() {
                    var e = o(String.prototype, "endsWith");
                    return e && !e.writable
                }();
            r({
                target: "String",
                proto: !0,
                forced: !v && !p
            }, {
                endsWith: function(e) {
                    var t = s(c(this));
                    u(e);
                    var n = arguments.length > 1 ? arguments[1] : void 0,
                        r = t.length,
                        i = void 0 === n ? r : h(a(n), r),
                        o = s(e);
                    return d(t, i - o.length, i) === o
                }
            })
        
