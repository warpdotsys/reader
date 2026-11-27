// chunk-vendors webpack 模块 7b3e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i = n("./a3de.js");
            /**
             * Checks if an event is supported in the current execution environment.
             *
             * NOTE: This will not work correctly for non-generic events such as `change`,
             * `reset`, `load`, `error`, and `select`.
             *
             * Borrows from Modernizr.
             *
             * @param {string} eventNameSuffix Event name, e.g. "click".
             * @param {?boolean} capture Check if the capture phase is supported.
             * @return {boolean} True if the event is supported.
             * @internal
             * @license Modernizr 3.0.0pre (Custom Build) | MIT
             */
            function o(e, t) {
                if (!i.canUseDOM || t && !("addEventListener" in document)) return !1;
                var n = "on" + e,
                    o = n in document;
                if (!o) {
                    var a = document.createElement("div");
                    a.setAttribute(n, "return;"), o = "function" === typeof a[n]
                }
                return !o && r && "wheel" === e && (o = document.implementation.hasFeature("Events.wheel", "3.0")), o
            }
            i.canUseDOM && (r = document.implementation && document.implementation.hasFeature && !0 !== document.implementation.hasFeature("", "")), e.exports = o
        
