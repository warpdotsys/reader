// chunk-vendors webpack 模块 1c7e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./b622.js"),
                i = r("iterator"),
                o = !1;
            try {
                var a = 0,
                    s = {
                        next: function() {
                            return {
                                done: !!a++
                            }
                        },
                        return: function() {
                            o = !0
                        }
                    };
                s[i] = function() {
                    return this
                }, Array.from(s, (function() {
                    throw 2
                }))
            } catch (u) {}
            e.exports = function(e, t) {
                try {
                    if (!t && !o) return !1
                } catch (u) {
                    return !1
                }
                var n = !1;
                try {
                    var r = {};
                    r[i] = function() {
                        return {
                            next: function() {
                                return {
                                    done: n = !0
                                }
                            }
                        }
                    }, e(r)
                } catch (u) {}
                return n
            }
        
