// chunk-vendors webpack 模块 c6cd（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c430.js"),
                i = n("./cfe9.js"),
                o = n("./6374.js"),
                a = "__core-js_shared__",
                s = e.exports = i[a] || o(a, {});
            (s.versions || (s.versions = [])).push({
                version: "3.42.0",
                mode: r ? "pure" : "global",
                copyright: "© 2014-2025 Denis Pushkarev (zloirock.ru)",
                license: "https://github.com/zloirock/core-js/blob/v3.42.0/LICENSE",
                source: "https://github.com/zloirock/core-js"
            })
        
