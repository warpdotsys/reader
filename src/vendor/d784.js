// chunk-vendors webpack 模块 d784（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./ac1f.js");
            var r = n("./c65b.js"),
                i = n("./cb2d.js"),
                o = n("./9263.js"),
                a = n("./d039.js"),
                s = n("./b622.js"),
                u = n("./9112.js"),
                c = s("species"),
                l = RegExp.prototype;
            e.exports = function(e, t, n, f) {
                var d = s(e),
                    h = !a((function() {
                        var t = {};
                        return t[d] = function() {
                            return 7
                        }, 7 !== "" [e](t)
                    })),
                    p = h && !a((function() {
                        var t = !1,
                            n = /a/;
                        return "split" === e && (n = {}, n.constructor = {}, n.constructor[c] = function() {
                            return n
                        }, n.flags = "", n[d] = /./ [d]), n.exec = function() {
                            return t = !0, null
                        }, n[d](""), !t
                    }));
                if (!h || !p || n) {
                    var v = /./ [d],
                        m = t(d, "" [e], (function(e, t, n, i, a) {
                            var s = t.exec;
                            return s === o || s === l.exec ? h && !a ? {
                                done: !0,
                                value: r(v, t, n, i)
                            } : {
                                done: !0,
                                value: r(e, n, t, i)
                            } : {
                                done: !1
                            }
                        }));
                    i(String.prototype, e, m[0]), i(l, d, m[1])
                }
                f && u(l[d], "sham", !0)
            }
        
