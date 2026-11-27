// chunk-vendors webpack 模块 dbb4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./83ab.js"),
                o = n("./56ef.js"),
                a = n("./fc6a.js"),
                s = n("./06cf.js"),
                u = n("./8418.js");
            r({
                target: "Object",
                stat: !0,
                sham: !i
            }, {
                getOwnPropertyDescriptors: function(e) {
                    var t, n, r = a(e),
                        i = s.f,
                        c = o(r),
                        l = {},
                        f = 0;
                    while (c.length > f) n = i(r, t = c[f++]), void 0 !== n && u(l, t, n);
                    return l
                }
            })
        
