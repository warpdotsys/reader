// chunk-vendors webpack 模块 5488（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./d9e2.js"), n("./d401.js"), t.__esModule = !0;
            var r = n("./5924.js");

            function i(e, t) {
                if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
            }
            var o = function() {
                function e() {
                    i(this, e)
                }
                return e.prototype.beforeEnter = function(e) {
                    (0, r.addClass)(e, "collapse-transition"), e.dataset || (e.dataset = {}), e.dataset.oldPaddingTop = e.style.paddingTop, e.dataset.oldPaddingBottom = e.style.paddingBottom, e.style.height = "0", e.style.paddingTop = 0, e.style.paddingBottom = 0
                }, e.prototype.enter = function(e) {
                    e.dataset.oldOverflow = e.style.overflow, 0 !== e.scrollHeight ? (e.style.height = e.scrollHeight + "px", e.style.paddingTop = e.dataset.oldPaddingTop, e.style.paddingBottom = e.dataset.oldPaddingBottom) : (e.style.height = "", e.style.paddingTop = e.dataset.oldPaddingTop, e.style.paddingBottom = e.dataset.oldPaddingBottom), e.style.overflow = "hidden"
                }, e.prototype.afterEnter = function(e) {
                    (0, r.removeClass)(e, "collapse-transition"), e.style.height = "", e.style.overflow = e.dataset.oldOverflow
                }, e.prototype.beforeLeave = function(e) {
                    e.dataset || (e.dataset = {}), e.dataset.oldPaddingTop = e.style.paddingTop, e.dataset.oldPaddingBottom = e.style.paddingBottom, e.dataset.oldOverflow = e.style.overflow, e.style.height = e.scrollHeight + "px", e.style.overflow = "hidden"
                }, e.prototype.leave = function(e) {
                    0 !== e.scrollHeight && ((0, r.addClass)(e, "collapse-transition"), e.style.height = 0, e.style.paddingTop = 0, e.style.paddingBottom = 0)
                }, e.prototype.afterLeave = function(e) {
                    (0, r.removeClass)(e, "collapse-transition"), e.style.height = "", e.style.overflow = e.dataset.oldOverflow, e.style.paddingTop = e.dataset.oldPaddingTop, e.style.paddingBottom = e.dataset.oldPaddingBottom
                }, e
            }();
            t["default"] = {
                name: "ElCollapseTransition",
                functional: !0,
                render: function(e, t) {
                    var n = t.children,
                        r = {
                            on: new o
                        };
                    return e("transition", r, n)
                }
            }
        
