// chunk-vendors webpack 模块 a3de（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = !("undefined" === typeof window || !window.document || !window.document.createElement),
                i = {
                    canUseDOM: r,
                    canUseWorkers: "undefined" !== typeof Worker,
                    canUseEventListeners: r && !(!window.addEventListener && !window.attachEvent),
                    canUseViewport: r && !!window.screen,
                    isInWorker: !r
                };
            e.exports = i
        
