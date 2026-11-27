// chunk-vendors webpack 模块 8558（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./cfe9.js"),
                i = n("./b5db.js"),
                o = n("./c6b6.js"),
                a = function(e) {
                    return i.slice(0, e.length) === e
                };
            e.exports = function() {
                return a("Bun/") ? "BUN" : a("Cloudflare-Workers") ? "CLOUDFLARE" : a("Deno/") ? "DENO" : a("Node.js/") ? "NODE" : r.Bun && "string" == typeof Bun.version ? "BUN" : r.Deno && "object" == typeof Deno.version ? "DENO" : "process" === o(r.process) ? "NODE" : r.window && r.document ? "BROWSER" : "REST"
            }()
        
