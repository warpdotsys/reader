// chunk-vendors webpack 模块 ace4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./4625.js"),
                o = n("./d039.js"),
                a = n("./621a.js"),
                s = n("./825a.js"),
                u = n("./23cb.js"),
                c = n("./50c4.js"),
                l = a.ArrayBuffer,
                f = a.DataView,
                d = f.prototype,
                h = i(l.prototype.slice),
                p = i(d.getUint8),
                v = i(d.setUint8),
                m = o((function() {
                    return !new l(2).slice(1, void 0).byteLength
                }));
            r({
                target: "ArrayBuffer",
                proto: !0,
                unsafe: !0,
                forced: m
            }, {
                slice: function(e, t) {
                    if (h && void 0 === t) return h(s(this), e);
                    var n = s(this).byteLength,
                        r = u(e, n),
                        i = u(void 0 === t ? n : t, n),
                        o = new l(c(i - r)),
                        a = new f(this),
                        d = new f(o),
                        m = 0;
                    while (r < i) v(d, m++, p(a, r++));
                    return o
                }
            })
        
