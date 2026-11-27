// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 dad2（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("d066"),
                r = function(t) {
                    return {
                        size: t,
                        has: function() {
                            return !1
                        },
                        keys: function() {
                            return {
                                next: function() {
                                    return {
                                        done: !0
                                    }
                                }
                            }
                        }
                    }
                },
                i = function(t) {
                    return {
                        size: t,
                        has: function() {
                            return !0
                        },
                        keys: function() {
                            throw new Error("e")
                        }
                    }
                };
            t.exports = function(t, e) {
                var o = s("Set");
                try {
                    (new o)[t](r(0));
                    try {
                        return (new o)[t](r(-1)), !1
                    } catch (n) {
                        if (!e) return !0;
                        try {
                            return (new o)[t](i(-1 / 0)), !1
                        } catch (c) {
                            var a = new o;
                            return a.add(1), a.add(2), e(a[t](i(1 / 0)))
                        }
                    }
                } catch (c) {
                    return !1
                }
            }
        
