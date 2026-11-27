// chunk-vendors webpack 模块 8eb7（逐字切片，形参 e, t）
const e = module;
const t = module.exports;

            var n, r, i, o, a, s, u, c, l, f, d, h, p, v, m, g = !1;

            function b() {
                if (!g) {
                    g = !0;
                    var e = navigator.userAgent,
                        t = /(?:MSIE.(\d+\.\d+))|(?:(?:Firefox|GranParadiso|Iceweasel).(\d+\.\d+))|(?:Opera(?:.+Version.|.)(\d+\.\d+))|(?:AppleWebKit.(\d+(?:\.\d+)?))|(?:Trident\/\d+\.\d+.*rv:(\d+\.\d+))/.exec(e),
                        b = /(Mac OS X)|(Windows)|(Linux)/.exec(e);
                    if (h = /\b(iPhone|iP[ao]d)/.exec(e), p = /\b(iP[ao]d)/.exec(e), f = /Android/i.exec(e), v = /FBAN\/\w+;/i.exec(e), m = /Mobile/i.exec(e), d = !!/Win64/.exec(e), t) {
                        n = t[1] ? parseFloat(t[1]) : t[5] ? parseFloat(t[5]) : NaN, n && document && document.documentMode && (n = document.documentMode);
                        var y = /(?:Trident\/(\d+.\d+))/.exec(e);
                        s = y ? parseFloat(y[1]) + 4 : n, r = t[2] ? parseFloat(t[2]) : NaN, i = t[3] ? parseFloat(t[3]) : NaN, o = t[4] ? parseFloat(t[4]) : NaN, o ? (t = /(?:Chrome\/(\d+\.\d+))/.exec(e), a = t && t[1] ? parseFloat(t[1]) : NaN) : a = NaN
                    } else n = r = i = a = o = NaN;
                    if (b) {
                        if (b[1]) {
                            var _ = /(?:Mac OS X (\d+(?:[._]\d+)?))/.exec(e);
                            u = !_ || parseFloat(_[1].replace("_", "."))
                        } else u = !1;
                        c = !!b[2], l = !!b[3]
                    } else u = c = l = !1
                }
            }
            var y = {
                ie: function() {
                    return b() || n
                },
                ieCompatibilityMode: function() {
                    return b() || s > n
                },
                ie64: function() {
                    return y.ie() && d
                },
                firefox: function() {
                    return b() || r
                },
                opera: function() {
                    return b() || i
                },
                webkit: function() {
                    return b() || o
                },
                safari: function() {
                    return y.webkit()
                },
                chrome: function() {
                    return b() || a
                },
                windows: function() {
                    return b() || c
                },
                osx: function() {
                    return b() || u
                },
                linux: function() {
                    return b() || l
                },
                iphone: function() {
                    return b() || h
                },
                mobile: function() {
                    return b() || h || p || f || m
                },
                nativeApp: function() {
                    return b() || v
                },
                android: function() {
                    return b() || f
                },
                ipad: function() {
                    return b() || p
                }
            };
            e.exports = y
        
