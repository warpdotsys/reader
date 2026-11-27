// chunk-vendors webpack 模块 d9e2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./23e7.js"),
                i = n("./cfe9.js"),
                o = n("./2ba4.js"),
                a = n("./e5cb.js"),
                s = "WebAssembly",
                u = i[s],
                c = 7 !== new Error("e", {
                    cause: 7
                }).cause,
                l = function(e, t) {
                    var n = {};
                    n[e] = a(e, t, c), r({
                        global: !0,
                        constructor: !0,
                        arity: 1,
                        forced: c
                    }, n)
                },
                f = function(e, t) {
                    if (u && u[e]) {
                        var n = {};
                        n[e] = a(s + "." + e, t, c), r({
                            target: s,
                            stat: !0,
                            constructor: !0,
                            arity: 1,
                            forced: c
                        }, n)
                    }
                };
            l("Error", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("EvalError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("RangeError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("ReferenceError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("SyntaxError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("TypeError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), l("URIError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), f("CompileError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), f("LinkError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            })), f("RuntimeError", (function(e) {
                return function(t) {
                    return o(e, this, arguments)
                }
            }))
        
