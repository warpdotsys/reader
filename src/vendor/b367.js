// chunk-vendors webpack 模块 b367（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./5524.js"),
                i = n("./ef08.js"),
                o = "__core-js_shared__",
                a = i[o] || (i[o] = {});
            (e.exports = function(e, t) {
                return a[e] || (a[e] = void 0 !== t ? t : {})
            })("versions", []).push({
                version: r.version,
                mode: n("./e444.js") ? "pure" : "global",
                copyright: "© 2020 Denis Pushkarev (zloirock.ru)"
            })
        
