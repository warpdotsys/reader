// chunk-vendors webpack 模块 cee4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c532.js"),
                i = n("./1d2b.js"),
                o = n("./0a06.js"),
                a = n("./4a7b.js"),
                s = n("./2444.js");

            function u(e) {
                var t = new o(e),
                    n = i(o.prototype.request, t);
                return r.extend(n, o.prototype, t), r.extend(n, t), n
            }
            var c = u(s);
            c.Axios = o, c.create = function(e) {
                return u(a(c.defaults, e))
            }, c.Cancel = n("./7a77.js"), c.CancelToken = n("./8df4.js"), c.isCancel = n("./2e67.js"), c.all = function(e) {
                return Promise.all(e)
            }, c.spread = n("./0df6.js"), c.isAxiosError = n("./5f02.js"), e.exports = c, e.exports.default = c
        
