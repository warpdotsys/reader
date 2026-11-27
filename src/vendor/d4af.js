// chunk-vendors webpack 模块 d4af（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./8eb7.js"),
                i = n("./7b3e.js"),
                o = 10,
                a = 40,
                s = 800;

            function u(e) {
                var t = 0,
                    n = 0,
                    r = 0,
                    i = 0;
                return "detail" in e && (n = e.detail), "wheelDelta" in e && (n = -e.wheelDelta / 120), "wheelDeltaY" in e && (n = -e.wheelDeltaY / 120), "wheelDeltaX" in e && (t = -e.wheelDeltaX / 120), "axis" in e && e.axis === e.HORIZONTAL_AXIS && (t = n, n = 0), r = t * o, i = n * o, "deltaY" in e && (i = e.deltaY), "deltaX" in e && (r = e.deltaX), (r || i) && e.deltaMode && (1 == e.deltaMode ? (r *= a, i *= a) : (r *= s, i *= s)), r && !t && (t = r < 1 ? -1 : 1), i && !n && (n = i < 1 ? -1 : 1), {
                    spinX: t,
                    spinY: n,
                    pixelX: r,
                    pixelY: i
                }
            }
            u.getEventType = function() {
                return r.firefox() ? "DOMMouseScroll" : i("wheel") ? "wheel" : "mousewheel"
            }, e.exports = u
        
