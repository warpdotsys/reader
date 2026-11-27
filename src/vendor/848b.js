// chunk-vendors webpack 模块 848b（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./4a0c.js"),
                i = {};
            ["object", "boolean", "number", "function", "string", "symbol"].forEach((function(e, t) {
                i[e] = function(n) {
                    return typeof n === e || "a" + (t < 1 ? "n " : " ") + e
                }
            }));
            var o = {},
                a = r.version.split(".");

            function s(e, t) {
                for (var n = t ? t.split(".") : a, r = e.split("."), i = 0; i < 3; i++) {
                    if (n[i] > r[i]) return !0;
                    if (n[i] < r[i]) return !1
                }
                return !1
            }

            function u(e, t, n) {
                if ("object" !== typeof e) throw new TypeError("options must be an object");
                var r = Object.keys(e),
                    i = r.length;
                while (i-- > 0) {
                    var o = r[i],
                        a = t[o];
                    if (a) {
                        var s = e[o],
                            u = void 0 === s || a(s, o, e);
                        if (!0 !== u) throw new TypeError("option " + o + " must be " + u)
                    } else if (!0 !== n) throw Error("Unknown option " + o)
                }
            }
            i.transitional = function(e, t, n) {
                var i = t && s(t);

                function a(e, t) {
                    return "[Axios v" + r.version + "] Transitional option '" + e + "'" + t + (n ? ". " + n : "")
                }
                return function(n, r, s) {
                    if (!1 === e) throw new Error(a(r, " has been removed in " + t));
                    return i && !o[r] && (o[r] = !0, console.warn(a(r, " has been deprecated since v" + t + " and will be removed in the near future"))), !e || e(n, r, s)
                }
            }, e.exports = {
                isOlderVersion: s,
                assertOptions: u,
                validators: i
            }
        
