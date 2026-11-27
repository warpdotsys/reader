// chunk-vendors webpack 模块 2cf4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a, s = n("./cfe9.js"),
                u = n("./2ba4.js"),
                c = n("./0366.js"),
                l = n("./1626.js"),
                f = n("./1a2d.js"),
                d = n("./d039.js"),
                h = n("./1be4.js"),
                p = n("./f36a.js"),
                v = n("./cc12.js"),
                m = n("./d6d6.js"),
                g = n("./52c8.js"),
                b = n("./9adc.js"),
                y = s.setImmediate,
                _ = s.clearImmediate,
                w = s.process,
                x = s.Dispatch,
                C = s.Function,
                S = s.MessageChannel,
                O = s.String,
                E = 0,
                k = {},
                T = "onreadystatechange";
            d((function() {
                r = s.location
            }));
            var j = function(e) {
                    if (f(k, e)) {
                        var t = k[e];
                        delete k[e], t()
                    }
                },
                $ = function(e) {
                    return function() {
                        j(e)
                    }
                },
                A = function(e) {
                    j(e.data)
                },
                P = function(e) {
                    s.postMessage(O(e), r.protocol + "//" + r.host)
                };
            y && _ || (y = function(e) {
                m(arguments.length, 1);
                var t = l(e) ? e : C(e),
                    n = p(arguments, 1);
                return k[++E] = function() {
                    u(t, void 0, n)
                }, i(E), E
            }, _ = function(e) {
                delete k[e]
            }, b ? i = function(e) {
                w.nextTick($(e))
            } : x && x.now ? i = function(e) {
                x.now($(e))
            } : S && !g ? (o = new S, a = o.port2, o.port1.onmessage = A, i = c(a.postMessage, a)) : s.addEventListener && l(s.postMessage) && !s.importScripts && r && "file:" !== r.protocol && !d(P) ? (i = P, s.addEventListener("message", A, !1)) : i = T in v("script") ? function(e) {
                h.appendChild(v("script"))[T] = function() {
                    h.removeChild(this), j(e)
                }
            } : function(e) {
                setTimeout($(e), 0)
            }), e.exports = {
                set: y,
                clear: _
            }
        
