// chunk-vendors webpack 模块 722f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./7037.js")["default"];
            n("./e01a.js"), n("./d28b.js"), n("./d9e2.js"), n("./d401.js"), n("./e260.js"), n("./d3b7.js"), n("./3ca3.js"), n("./ddb0.js"), n("./4795.js"), t.__esModule = !0;
            var i = "function" === typeof Symbol && "symbol" === r(Symbol.iterator) ? function(e) {
                    return r(e)
                } : function(e) {
                    return e && "function" === typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : r(e)
                },
                o = n("./e452.js"),
                a = s(o);

            function s(e) {
                return e && e.__esModule ? e : {
                    default: e
                }
            }
            var u, c = c || {};
            c.Dialog = function(e, t, n) {
                var r = this;
                if (this.dialogNode = e, null === this.dialogNode || "dialog" !== this.dialogNode.getAttribute("role")) throw new Error("Dialog() requires a DOM element with ARIA role of dialog.");
                "string" === typeof t ? this.focusAfterClosed = document.getElementById(t) : "object" === ("undefined" === typeof t ? "undefined" : i(t)) ? this.focusAfterClosed = t : this.focusAfterClosed = null, "string" === typeof n ? this.focusFirst = document.getElementById(n) : "object" === ("undefined" === typeof n ? "undefined" : i(n)) ? this.focusFirst = n : this.focusFirst = null, this.focusFirst ? this.focusFirst.focus() : a["default"].focusFirstDescendant(this.dialogNode), this.lastFocus = document.activeElement, u = function(e) {
                    r.trapFocus(e)
                }, this.addListeners()
            }, c.Dialog.prototype.addListeners = function() {
                document.addEventListener("focus", u, !0)
            }, c.Dialog.prototype.removeListeners = function() {
                document.removeEventListener("focus", u, !0)
            }, c.Dialog.prototype.closeDialog = function() {
                var e = this;
                this.removeListeners(), this.focusAfterClosed && setTimeout((function() {
                    e.focusAfterClosed.focus()
                }))
            }, c.Dialog.prototype.trapFocus = function(e) {
                a["default"].IgnoreUtilFocusChanges || (this.dialogNode.contains(e.target) ? this.lastFocus = e.target : (a["default"].focusFirstDescendant(this.dialogNode), this.lastFocus === document.activeElement && a["default"].focusLastDescendant(this.dialogNode), this.lastFocus = document.activeElement))
            }, t["default"] = c.Dialog
        
