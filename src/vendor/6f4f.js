// chunk-vendors webpack 模块 6f4f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            var r = n("./77e9.js"),
                i = n("./85e7.js"),
                o = n("./9742.js"),
                a = n("./5a94.js")("IE_PROTO"),
                s = function() {},
                u = "prototype",
                c = function() {
                    var e, t = n("./05f5.js")("iframe"),
                        r = o.length,
                        i = "<",
                        a = ">";
                    t.style.display = "none", n("./9141.js").appendChild(t), t.src = "javascript:", e = t.contentWindow.document, e.open(), e.write(i + "script" + a + "document.F=Object" + i + "/script" + a), e.close(), c = e.F;
                    while (r--) delete c[u][o[r]];
                    return c()
                };
            e.exports = Object.create || function(e, t) {
                var n;
                return null !== e ? (s[u] = r(e), n = new s, s[u] = null, n[a] = e) : n = c(), void 0 === t ? n : i(n, t)
            }
        
