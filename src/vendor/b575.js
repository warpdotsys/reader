// chunk-vendors webpack 模块 b575（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r, i, o, a, s, u = n("./cfe9.js"),
                c = n("./157a.js"),
                l = n("./0366.js"),
                f = n("./2cf4.js").set,
                d = n("./01b4.js"),
                h = n("./52c8.js"),
                p = n("./ebc1.js"),
                v = n("./ec87.js"),
                m = n("./9adc.js"),
                g = u.MutationObserver || u.WebKitMutationObserver,
                b = u.document,
                y = u.process,
                _ = u.Promise,
                w = c("queueMicrotask");
            if (!w) {
                var x = new d,
                    C = function() {
                        var e, t;
                        m && (e = y.domain) && e.exit();
                        while (t = x.get()) try {
                            t()
                        } catch (n) {
                            throw x.head && r(), n
                        }
                        e && e.enter()
                    };
                h || m || v || !g || !b ? !p && _ && _.resolve ? (a = _.resolve(void 0), a.constructor = _, s = l(a.then, a), r = function() {
                    s(C)
                }) : m ? r = function() {
                    y.nextTick(C)
                } : (f = l(f, u), r = function() {
                    f(C)
                }) : (i = !0, o = b.createTextNode(""), new g(C).observe(o, {
                    characterData: !0
                }), r = function() {
                    o.data = i = !i
                }), w = function(e) {
                    x.head || r(), x.add(e)
                }
            }
            e.exports = w
        
