// chunk-vendors webpack 模块 417f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./4160.js"), n("./14d9.js"), n("./a434.js"), n("./d3b7.js"), t.__esModule = !0;
            var r = n("./2b0e.js"),
                i = a(r),
                o = n("./5924.js");

            function a(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var s = [],
                u = "@@clickoutsideContext",
                c = void 0,
                l = 0;

            function f(e, t, n) {
                return function() {
                    var r = arguments.length > 0 && void 0 !== arguments[0] ? arguments[0] : {},
                        i = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {};
                    !(n && n.context && r.target && i.target) || e.contains(r.target) || e.contains(i.target) || e === r.target || n.context.popperElm && (n.context.popperElm.contains(r.target) || n.context.popperElm.contains(i.target)) || (t.expression && e[u].methodName && n.context[e[u].methodName] ? n.context[e[u].methodName]() : e[u].bindingFn && e[u].bindingFn())
                }
            }!i["default"].prototype.$isServer && (0, o.on)(document, "mousedown", (function(e) {
                return c = e
            })), !i["default"].prototype.$isServer && (0, o.on)(document, "mouseup", (function(e) {
                s.forEach((function(t) {
                    return t[u].documentHandler(e, c)
                }))
            })), t["default"] = {
                bind: function(e, t, n) {
                    s.push(e);
                    var r = l++;
                    e[u] = {
                        id: r,
                        documentHandler: f(e, t, n),
                        methodName: t.expression,
                        bindingFn: t.value
                    }
                },
                update: function(e, t, n) {
                    e[u].documentHandler = f(e, t, n), e[u].methodName = t.expression, e[u].bindingFn = t.value
                },
                unbind: function(e) {
                    for (var t = s.length, n = 0; n < t; n++)
                        if (s[n][u].id === e[u].id) {
                            s.splice(n, 1);
                            break
                        } delete e[u]
                }
            }
        
