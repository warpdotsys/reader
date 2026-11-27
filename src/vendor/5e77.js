// chunk-vendors webpack 模块 5e77（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./83ab.js"),
                i = n("./1a2d.js"),
                o = Function.prototype,
                a = r && Object.getOwnPropertyDescriptor,
                s = i(o, "name"),
                u = s && "something" === function() {}.name,
                c = s && (!r || r && a(o, "name").configurable);
            e.exports = {
                EXISTS: s,
                PROPER: u,
                CONFIGURABLE: c
            }
        
