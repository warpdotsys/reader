// chunk-vendors webpack 模块 e452（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n("./99af.js"), n("./e260.js"), n("./d3b7.js"), n("./ac1f.js"), n("./00b4.js"), n("./ddb0.js"), t.__esModule = !0;
            var r = r || {};
            r.Utils = r.Utils || {}, r.Utils.focusFirstDescendant = function(e) {
                for (var t = 0; t < e.childNodes.length; t++) {
                    var n = e.childNodes[t];
                    if (r.Utils.attemptFocus(n) || r.Utils.focusFirstDescendant(n)) return !0
                }
                return !1
            }, r.Utils.focusLastDescendant = function(e) {
                for (var t = e.childNodes.length - 1; t >= 0; t--) {
                    var n = e.childNodes[t];
                    if (r.Utils.attemptFocus(n) || r.Utils.focusLastDescendant(n)) return !0
                }
                return !1
            }, r.Utils.attemptFocus = function(e) {
                if (!r.Utils.isFocusable(e)) return !1;
                r.Utils.IgnoreUtilFocusChanges = !0;
                try {
                    e.focus()
                } catch (t) {}
                return r.Utils.IgnoreUtilFocusChanges = !1, document.activeElement === e
            }, r.Utils.isFocusable = function(e) {
                if (e.tabIndex > 0 || 0 === e.tabIndex && null !== e.getAttribute("tabIndex")) return !0;
                if (e.disabled) return !1;
                switch (e.nodeName) {
                    case "A":
                        return !!e.href && "ignore" !== e.rel;
                    case "INPUT":
                        return "hidden" !== e.type && "file" !== e.type;
                    case "BUTTON":
                    case "SELECT":
                    case "TEXTAREA":
                        return !0;
                    default:
                        return !1
                }
            }, r.Utils.triggerEvent = function(e, t) {
                var n = void 0;
                n = /^mouse|click/.test(t) ? "MouseEvents" : /^key/.test(t) ? "KeyboardEvent" : "HTMLEvents";
                for (var r = document.createEvent(n), i = arguments.length, o = Array(i > 2 ? i - 2 : 0), a = 2; a < i; a++) o[a - 2] = arguments[a];
                return r.initEvent.apply(r, [t].concat(o)), e.dispatchEvent ? e.dispatchEvent(r) : e.fireEvent("on" + t, r), e
            }, r.Utils.keys = {
                tab: 9,
                enter: 13,
                space: 32,
                left: 37,
                up: 38,
                right: 39,
                down: 40,
                esc: 27
            }, t["default"] = r.Utils
        
