// chunk-vendors webpack 模块 3c5d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./c65b.js"),
                o = n("./ebb5.js"),
                a = n("./07fa.js"),
                s = n("./182d.js"),
                u = n("./7b0b.js"),
                c = n("./d039.js"),
                l = r.RangeError,
                f = r.Int8Array,
                d = f && f.prototype,
                h = d && d.set,
                p = o.aTypedArray,
                v = o.exportTypedArrayMethod,
                m = !c((function() {
                    var e = new Uint8ClampedArray(2);
                    return i(h, e, {
                        length: 1,
                        0: 3
                    }, 1), 3 !== e[1]
                })),
                g = m && o.NATIVE_ARRAY_BUFFER_VIEWS && c((function() {
                    var e = new f(2);
                    return e.set(1), e.set("2", 1), 0 !== e[0] || 2 !== e[1]
                }));
            v("set", (function(e) {
                p(this);
                var t = s(arguments.length > 1 ? arguments[1] : void 0, 1),
                    n = u(e);
                if (m) return i(h, this, n, t);
                var r = this.length,
                    o = a(n),
                    c = 0;
                if (o + t > r) throw new l("Wrong length");
                while (c < o) this[t + c] = n[c++]
            }), !m || g)
        
