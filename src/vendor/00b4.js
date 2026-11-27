// chunk-vendors webpack 模块 00b4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./ac1f.js");
            var r = n("./23e7.js"),
                i = n("./c65b.js"),
                o = n("./1626.js"),
                a = n("./825a.js"),
                s = n("./577e.js"),
                u = function() {
                    var e = !1,
                        t = /[ac]/;
                    return t.exec = function() {
                        return e = !0, /./.exec.apply(this, arguments)
                    }, !0 === t.test("abc") && e
                }(),
                c = /./.test;
            r({
                target: "RegExp",
                proto: !0,
                forced: !u
            }, {
                test: function(e) {
                    var t = a(this),
                        n = s(e),
                        r = t.exec;
                    if (!o(r)) return i(c, t, n);
                    var u = i(r, t, n);
                    return null !== u && (a(u), !0)
                }
            })
        
