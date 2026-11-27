// chunk-vendors webpack 模块 e62d（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            t.__esModule = !0, t["default"] = function() {
                if (i["default"].prototype.$isServer) return 0;
                if (void 0 !== a) return a;
                var e = document.createElement("div");
                e.className = "el-scrollbar__wrap", e.style.visibility = "hidden", e.style.width = "100px", e.style.position = "absolute", e.style.top = "-9999px", document.body.appendChild(e);
                var t = e.offsetWidth;
                e.style.overflow = "scroll";
                var n = document.createElement("div");
                n.style.width = "100%", e.appendChild(n);
                var r = n.offsetWidth;
                return e.parentNode.removeChild(e), a = t - r, a
            };
            var r = n("./2b0e.js"),
                i = o(r);

            function o(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var a = void 0
        
