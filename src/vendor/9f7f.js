// chunk-vendors webpack 模块 9f7f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./d039.js"),
                i = n("./cfe9.js"),
                o = i.RegExp,
                a = r((function() {
                    var e = o("a", "y");
                    return e.lastIndex = 2, null !== e.exec("abcd")
                })),
                s = a || r((function() {
                    return !o("a", "y").sticky
                })),
                u = a || r((function() {
                    var e = o("^r", "gy");
                    return e.lastIndex = 2, null !== e.exec("str")
                }));
            e.exports = {
                BROKEN_CARET: u,
                MISSED_STICKY: s,
                UNSUPPORTED_Y: a
            }
        
