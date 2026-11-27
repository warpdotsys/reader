// chunk-vendors webpack 模块 aa1f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./d039.js"),
                o = n("./825a.js"),
                a = n("./e391.js"),
                s = Error.prototype.toString,
                u = i((function() {
                    if (r) {
                        var e = Object.create(Object.defineProperty({}, "name", {
                            get: function() {
                                return this === e
                            }
                        }));
                        if ("true" !== s.call(e)) return !0
                    }
                    return "2: 1" !== s.call({
                        message: 1,
                        name: 2
                    }) || "Error" !== s.call({})
                }));
            e.exports = u ? function() {
                var e = o(this),
                    t = a(e.name, "Error"),
                    n = a(e.message);
                return t ? n ? t + ": " + n : t : n
            } : s
        
