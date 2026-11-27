// chunk-vendors webpack 模块 2a5e（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./14d9.js"), n("./13d5.js"), n("./d3b7.js"), t.__esModule = !0, t["default"] = a;
            var r = n("./2b0e.js"),
                i = o(r);

            function o(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }

            function a(e, t) {
                if (!i["default"].prototype.$isServer)
                    if (t) {
                        var n = [],
                            r = t.offsetParent;
                        while (r && e !== r && e.contains(r)) n.push(r), r = r.offsetParent;
                        var o = t.offsetTop + n.reduce((function(e, t) {
                                return e + t.offsetTop
                            }), 0),
                            a = o + t.offsetHeight,
                            s = e.scrollTop,
                            u = s + e.clientHeight;
                        o < s ? e.scrollTop = o : a > u && (e.scrollTop = a - e.clientHeight)
                    } else e.scrollTop = 0
            }
        
