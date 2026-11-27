// chunk-vendors webpack 模块 c532（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = n("./1d2b.js"),
                i = Object.prototype.toString;

            function o(e) {
                return "[object Array]" === i.call(e)
            }

            function a(e) {
                return "undefined" === typeof e
            }

            function s(e) {
                return null !== e && !a(e) && null !== e.constructor && !a(e.constructor) && "function" === typeof e.constructor.isBuffer && e.constructor.isBuffer(e)
            }

            function u(e) {
                return "[object ArrayBuffer]" === i.call(e)
            }

            function c(e) {
                return "undefined" !== typeof FormData && e instanceof FormData
            }

            function l(e) {
                var t;
                return t = "undefined" !== typeof ArrayBuffer && ArrayBuffer.isView ? ArrayBuffer.isView(e) : e && e.buffer && e.buffer instanceof ArrayBuffer, t
            }

            function f(e) {
                return "string" === typeof e
            }

            function d(e) {
                return "number" === typeof e
            }

            function h(e) {
                return null !== e && "object" === typeof e
            }

            function p(e) {
                if ("[object Object]" !== i.call(e)) return !1;
                var t = Object.getPrototypeOf(e);
                return null === t || t === Object.prototype
            }

            function v(e) {
                return "[object Date]" === i.call(e)
            }

            function m(e) {
                return "[object File]" === i.call(e)
            }

            function g(e) {
                return "[object Blob]" === i.call(e)
            }

            function b(e) {
                return "[object Function]" === i.call(e)
            }

            function y(e) {
                return h(e) && b(e.pipe)
            }

            function _(e) {
                return "undefined" !== typeof URLSearchParams && e instanceof URLSearchParams
            }

            function w(e) {
                return e.trim ? e.trim() : e.replace(/^\s+|\s+$/g, "")
            }

            function x() {
                return ("undefined" === typeof navigator || "ReactNative" !== navigator.product && "NativeScript" !== navigator.product && "NS" !== navigator.product) && ("undefined" !== typeof window && "undefined" !== typeof document)
            }

            function C(e, t) {
                if (null !== e && "undefined" !== typeof e)
                    if ("object" !== typeof e && (e = [e]), o(e))
                        for (var n = 0, r = e.length; n < r; n++) t.call(null, e[n], n, e);
                    else
                        for (var i in e) Object.prototype.hasOwnProperty.call(e, i) && t.call(null, e[i], i, e)
            }

            function S() {
                var e = {};

                function t(t, n) {
                    p(e[n]) && p(t) ? e[n] = S(e[n], t) : p(t) ? e[n] = S({}, t) : o(t) ? e[n] = t.slice() : e[n] = t
                }
                for (var n = 0, r = arguments.length; n < r; n++) C(arguments[n], t);
                return e
            }

            function O(e, t, n) {
                return C(t, (function(t, i) {
                    e[i] = n && "function" === typeof t ? r(t, n) : t
                })), e
            }

            function E(e) {
                return 65279 === e.charCodeAt(0) && (e = e.slice(1)), e
            }
            e.exports = {
                isArray: o,
                isArrayBuffer: u,
                isBuffer: s,
                isFormData: c,
                isArrayBufferView: l,
                isString: f,
                isNumber: d,
                isObject: h,
                isPlainObject: p,
                isUndefined: a,
                isDate: v,
                isFile: m,
                isBlob: g,
                isFunction: b,
                isStream: y,
                isURLSearchParams: _,
                isStandardBrowserEnv: x,
                forEach: C,
                merge: S,
                extend: O,
                trim: w,
                stripBOM: E
            }
        
