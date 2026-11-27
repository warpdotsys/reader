// chunk-vendors webpack 模块 d010（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";

            function r(e, t, n) {
                this.$children.forEach((function(i) {
                    var o = i.$options.componentName;
                    o === e ? i.$emit.apply(i, [t].concat(n)) : r.apply(i, [e, t].concat([n]))
                }))
            }
            n("./99af.js"), n("./4160.js"), n("./e9f5.js"), n("./7d54.js"), n("./d3b7.js"), n("./159b.js"), t.__esModule = !0, t["default"] = {
                methods: {
                    dispatch: function(e, t, n) {
                        var r = this.$parent || this.$root,
                            i = r.$options.componentName;
                        while (r && (!i || i !== e)) r = r.$parent, r && (i = r.$options.componentName);
                        r && r.$emit.apply(r, [t].concat(n))
                    },
                    broadcast: function(e, t, n) {
                        r.call(this, e, t, n)
                    }
                }
            }
        
