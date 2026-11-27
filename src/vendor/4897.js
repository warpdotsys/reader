// chunk-vendors webpack 模块 4897（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./3410.js"), t.__esModule = !0, t.i18n = t.use = t.t = void 0;
            var r = n("./f0d9.js"),
                i = f(r),
                o = n("./2b0e.js"),
                a = f(o),
                s = n("./3c4e.js"),
                u = f(s),
                c = n("./9d7e.js"),
                l = f(c);

            function f(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var d = (0, l["default"])(a["default"]),
                h = i["default"],
                p = !1,
                v = function() {
                    var e = Object.getPrototypeOf(this || a["default"]).$t;
                    if ("function" === typeof e && a["default"].locale) return p || (p = !0, a["default"].locale(a["default"].config.lang, (0, u["default"])(h, a["default"].locale(a["default"].config.lang) || {}, {
                        clone: !0
                    }))), e.apply(this, arguments)
                },
                m = t.t = function(e, t) {
                    var n = v.apply(this, arguments);
                    if (null !== n && void 0 !== n) return n;
                    for (var r = e.split("."), i = h, o = 0, a = r.length; o < a; o++) {
                        var s = r[o];
                        if (n = i[s], o === a - 1) return d(n, t);
                        if (!n) return "";
                        i = n
                    }
                    return ""
                },
                g = t.use = function(e) {
                    h = e || h
                },
                b = t.i18n = function(e) {
                    v = e || v
                };
            t["default"] = {
                use: g,
                t: m,
                i18n: b
            }
        
