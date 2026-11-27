// chunk-vendors webpack 模块 36f2（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a, s = n("./cfe9.js"),
                u = n("./2a07.js"),
                c = n("./dbe5.js"),
                l = s.structuredClone,
                f = s.ArrayBuffer,
                d = s.MessageChannel,
                h = !1;
            if (c) h = function(e) {
                l(e, {
                    transfer: [e]
                })
            };
            else if (f) try {
                d || (r = u("worker_threads"), r && (d = r.MessageChannel)), d && (i = new d, o = new f(2), a = function(e) {
                    i.port1.postMessage(null, [e])
                }, 2 === o.byteLength && (a(o), 0 === o.byteLength && (h = a)))
            } catch (p) {}
            e.exports = h
        
