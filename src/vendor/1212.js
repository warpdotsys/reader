// chunk-vendors webpack 模块 1212（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o = n("./cfe9.js"),
                a = n("./b5db.js"),
                s = o.process,
                u = o.Deno,
                c = s && s.versions || u && u.version,
                l = c && c.v8;
            l && (r = l.split("."), i = r[0] > 0 && r[0] < 4 ? 1 : +(r[0] + r[1])), !i && a && (r = a.match(/Edge\/(\d+)/), (!r || r[1] >= 74) && (r = a.match(/Chrome\/(\d+)/), r && (i = +r[1]))), e.exports = i
        
