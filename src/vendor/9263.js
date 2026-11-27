// chunk-vendors webpack 模块 9263（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./c65b.js"),
                i = n("./e330.js"),
                o = n("./577e.js"),
                a = n("./ad6d.js"),
                s = n("./9f7f.js"),
                u = n("./5692.js"),
                c = n("./7c73.js"),
                l = n("./69f3.js").get,
                f = n("./fce3.js"),
                d = n("./107c.js"),
                h = u("native-string-replace", String.prototype.replace),
                p = RegExp.prototype.exec,
                v = p,
                m = i("".charAt),
                g = i("".indexOf),
                b = i("".replace),
                y = i("".slice),
                _ = function() {
                    var e = /a/,
                        t = /b*/g;
                    return r(p, e, "a"), r(p, t, "a"), 0 !== e.lastIndex || 0 !== t.lastIndex
                }(),
                w = s.BROKEN_CARET,
                x = void 0 !== /()??/.exec("")[1],
                C = _ || x || w || f || d;
            C && (v = function(e) {
                var t, n, i, s, u, f, d, C = this,
                    S = l(C),
                    O = o(e),
                    E = S.raw;
                if (E) return E.lastIndex = C.lastIndex, t = r(v, E, O), C.lastIndex = E.lastIndex, t;
                var k = S.groups,
                    T = w && C.sticky,
                    j = r(a, C),
                    $ = C.source,
                    A = 0,
                    P = O;
                if (T && (j = b(j, "y", ""), -1 === g(j, "g") && (j += "g"), P = y(O, C.lastIndex), C.lastIndex > 0 && (!C.multiline || C.multiline && "\n" !== m(O, C.lastIndex - 1)) && ($ = "(?: " + $ + ")", P = " " + P, A++), n = new RegExp("^(?:" + $ + ")", j)), x && (n = new RegExp("^" + $ + "$(?!\\s)", j)), _ && (i = C.lastIndex), s = r(p, T ? n : C, P), T ? s ? (s.input = y(s.input, A), s[0] = y(s[0], A), s.index = C.lastIndex, C.lastIndex += s[0].length) : C.lastIndex = 0 : _ && s && (C.lastIndex = C.global ? s.index + s[0].length : i), x && s && s.length > 1 && r(h, s[0], n, (function() {
                        for (u = 1; u < arguments.length - 2; u++) void 0 === arguments[u] && (s[u] = void 0)
                    })), s && k)
                    for (s.groups = f = c(null), u = 0; u < k.length; u++) d = k[u], f[d[0]] = s[d[1]];
                return s
            }), e.exports = v
        
