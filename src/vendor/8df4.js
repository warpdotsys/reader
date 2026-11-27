// chunk-vendors webpack 模块 8df4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./7a77.js");

            function i(e) {
                if ("function" !== typeof e) throw new TypeError("executor must be a function.");
                var t;
                this.promise = new Promise((function(e) {
                    t = e
                }));
                var n = this;
                e((function(e) {
                    n.reason || (n.reason = new r(e), t(n.reason))
                }))
            }
            i.prototype.throwIfRequested = function() {
                if (this.reason) throw this.reason
            }, i.source = function() {
                var e, t = new i((function(t) {
                    e = t
                }));
                return {
                    token: t,
                    cancel: e
                }
            }, e.exports = i
        
