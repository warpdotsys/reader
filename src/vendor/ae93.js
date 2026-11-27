// chunk-vendors webpack 模块 ae93（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a = n("./d039.js"),
                s = n("./1626.js"),
                u = n("./861d.js"),
                c = n("./7c73.js"),
                l = n("./e163.js"),
                f = n("./cb2d.js"),
                d = n("./b622.js"),
                h = n("./c430.js"),
                p = d("iterator"),
                v = !1;
            [].keys && (o = [].keys(), "next" in o ? (i = l(l(o)), i !== Object.prototype && (r = i)) : v = !0);
            var m = !u(r) || a((function() {
                var e = {};
                return r[p].call(e) !== e
            }));
            m ? r = {} : h && (r = c(r)), s(r[p]) || f(r, p, (function() {
                return this
            })), e.exports = {
                IteratorPrototype: r,
                BUGGY_SAFARI_ITERATORS: v
            }
        
