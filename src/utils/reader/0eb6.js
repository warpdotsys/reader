// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 0eb6（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            var a = n("23e7"),
                u = n("d066"),
                o = n("2a07"),
                r = n("d039"),
                i = n("7c73"),
                s = n("5c6c"),
                l = n("9bf2").f,
                c = n("cb2d"),
                p = n("edd0"),
                d = n("1a2d"),
                D = n("19aa"),
                h = n("825a"),
                A = n("aa1f"),
                F = n("e391"),
                f = n("cf98"),
                m = n("0d26"),
                C = n("69f3"),
                E = n("83ab"),
                y = n("c430"),
                g = "DOMException",
                b = "DATA_CLONE_ERR",
                v = u("Error"),
                B = u(g) || function() {
                    try {
                        var e = u("MessageChannel") || o("worker_threads").MessageChannel;
                        (new e).port1.postMessage(new WeakMap)
                    } catch (t) {
                        if (t.name === b && 25 === t.code) return t.constructor
                    }
                }(),
                k = B && B.prototype,
                w = v.prototype,
                x = C.set,
                S = C.getterFor(g),
                N = "stack" in new v(g),
                T = function(e) {
                    return d(f, e) && f[e].m ? f[e].c : 0
                },
                L = function() {
                    D(this, I);
                    var e = arguments.length,
                        t = F(e < 1 ? void 0 : arguments[0]),
                        n = F(e < 2 ? void 0 : arguments[1], "Error"),
                        a = T(n);
                    if (x(this, {
                            type: g,
                            name: n,
                            message: t,
                            code: a
                        }), E || (this.name = n, this.message = t, this.code = a), N) {
                        var u = new v(t);
                        u.name = g, l(this, "stack", s(1, m(u.stack, 1)))
                    }
                },
                I = L.prototype = i(w),
                R = function(e) {
                    return {
                        enumerable: !0,
                        configurable: !0,
                        get: e
                    }
                },
                P = function(e) {
                    return R((function() {
                        return S(this)[e]
                    }))
                };
            E && (p(I, "code", P("code")), p(I, "message", P("message")), p(I, "name", P("name"))), l(I, "constructor", s(1, L));
            var $ = r((function() {
                    return !(new B instanceof v)
                })),
                M = $ || r((function() {
                    return w.toString !== A || "2: 1" !== String(new B(1, 2))
                })),
                O = $ || r((function() {
                    return 25 !== new B(1, "DataCloneError").code
                })),
                z = $ || 25 !== B[b] || 25 !== k[b],
                _ = y ? M || O || z : $;
            a({
                global: !0,
                constructor: !0,
                forced: _
            }, {
                DOMException: _ ? L : B
            });
            var j = u(g),
                U = j.prototype;
            for (var q in M && (y || B === j) && c(U, "toString", A), O && E && B === j && p(U, "code", R((function() {
                    return T(h(this).name)
                }))), f)
                if (d(f, q)) {
                    var Y = f[q],
                        W = Y.s,
                        H = s(6, Y.c);
                    d(j, W) || l(j, W, H), d(U, W) || l(U, W, H)
                }
        
