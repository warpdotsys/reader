/**
 * 恢复自 reader-pro-3.2.14 前端产物 js/app.54619a3e.js 的请求封装模块（module id: be3b）
 * 证据见 docs/FRONTEND-RECONSTRUCTION.md §12；require("xxxx") 为产物中的 o("xxxx")。
 */
module.exports = (function(e, t, o) {
        "use strict";
        var n = require("c7eb"),
            r = (require("9e1f"), require("450d"), require("6ed5")),
            a = o.n(r),
            i = (require("0fb7"), require("f529")),
            s = o.n(i),
            c = require("5530"),
            l = require("1da1"),
            u = (require("d401"), require("c975"), require("14d9"), require("0d03"), require("d3b7"), require("25f0"), require("4795"), require("bc3a")),
            f = o.n(u),
            d = require("../utils/app/bfff.js"),
            p = require("../utils/app/1a58.js"),
            h = f.a.create({
                baseURL: p["a"].getters.api,
                withCredentials: !0,
                timeout: 6e4
            });
        p["a"].watch((function(e) {
            return e.api
        }), (function() {
            h.defaults.baseURL = p["a"].getters.api
        })), h.interceptors.request.use((function(e) {
            return e
        }), (function(e) {
            return Promise.reject(e)
        })), h.interceptors.response.use((function(e) {
            return e
        }), (function(e) {
            return Promise.reject(e)
        }));
        var m = !1,
            g = function() {
                var e = Object(l["a"])(Object(n["a"])().mark((function e(t) {
                    var o, r, i, l, u, f, b, k, w, v, S, A, x, B, C, y, O, I, T;
                    return Object(n["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                return o = t.url, r = t.method, i = void 0 === r ? "get" : r, l = t.params, u = void 0 === l ? {} : l, f = t.data, b = void 0 === f ? {} : f, k = t.headers, w = void 0 === k ? {} : k, v = t.options, S = void 0 === v ? {} : v, A = t.alert, void 0 === A && (A = "post" === i), p["a"].state.token && (u.accessToken = p["a"].state.token), p["a"].state.isManagerMode && p["a"].state.secureKey && (u.secureKey = p["a"].state.secureKey, u.userNS = p["a"].state.userNS), u.v = (new Date).getTime(), x = Object(c["a"])({
                                    url: o,
                                    method: i,
                                    headers: w,
                                    params: u,
                                    data: b
                                }, S), e.next = 8, h(x)["catch"]((function(e) {
                                    if (b && b.bookSourceUrl && p["a"].state.failureIncludeTimeout) {
                                        var t = e.toString();
                                        window.errorMsgList = window.errorMsgList || [], window.errorMsgList.push(t), t.indexOf("timeout") >= 0 && p["a"].commit("addFailureBookSource", {
                                            bookSourceUrl: b.bookSourceUrl,
                                            errorMsg: t
                                        })
                                    }
                                    throw e
                                }));
                            case 8:
                                if (B = e.sent, B) {
                                    e.next = 11;
                                    break
                                }
                                return e.abrupt("return");
                            case 11:
                                if (C = B.data, "string" !== typeof C) {
                                    e.next = 14;
                                    break
                                }
                                return e.abrupt("return", B);
                            case 14:
                                if (y = C.isSuccess, O = C.errorMsg, y) {
                                    e.next = 48;
                                    break
                                }
                                e.t0 = C.data, e.next = "NEED_LOGIN" === e.t0 ? 19 : "NEED_SECURE_KEY" === e.t0 ? 22 : 32;
                                break;
                            case 19:
                                return p["a"].commit("setShowLogin", !0), m || (m = !0, setTimeout((function() {
                                    O && s.a.error({
                                        message: O,
                                        duration: 2e3
                                    }), setTimeout((function() {
                                        m = !1
                                    }), 2e3)
                                }), 200)), e.abrupt("break", 46);
                            case 22:
                                return e.next = 24, a.a.prompt("请输入管理密码后继续操作", "操作确认");
                            case 24:
                                if (I = e.sent, !I || "confirm" !== I.action || !I.value) {
                                    e.next = 31;
                                    break
                                }
                                return u.secureKey = I.value, p["a"].commit("setSecureKey", I.value), e.next = 30, g({
                                    url: o,
                                    method: i,
                                    params: u,
                                    data: b,
                                    headers: w,
                                    alert: A
                                });
                            case 30:
                                return e.abrupt("return", e.sent);
                            case 31:
                                return e.abrupt("break", 46);
                            case 32:
                                if (!b || !b.bookSourceUrl) {
                                    e.next = 44;
                                    break
                                }
                                if (!O) {
                                    e.next = 44;
                                    break
                                }
                                window.errorMsgList = window.errorMsgList || [], window.errorMsgList.push(O), T = 0;
                            case 37:
                                if (!(T < d["e"].length)) {
                                    e.next = 44;
                                    break
                                }
                                if (!(O.indexOf(d["e"][T]) >= 0)) {
                                    e.next = 41;
                                    break
                                }
                                return p["a"].commit("addFailureBookSource", {
                                    bookSourceUrl: b.bookSourceUrl,
                                    errorMsg: O
                                }), e.abrupt("break", 44);
                            case 41:
                                T++, e.next = 37;
                                break;
                            case 44:
                                return S.silent || O && s.a.error({
                                    message: O,
                                    duration: 2e3
                                }), e.abrupt("break", 46);
                            case 46:
                                e.next = 49;
                                break;
                            case 48:
                                A && O && s.a.success({
                                    message: O,
                                    duration: 1500
                                });
                            case 49:
                                return e.abrupt("return", B);
                            case 50:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                })));
                return function(t) {
                    return e.apply(this, arguments)
                }
            }();
        g.get = function() {
            var e = Object(l["a"])(Object(n["a"])().mark((function e(t, o) {
                return Object(n["a"])().wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return o = o || {}, e.next = 3, g({
                                url: t,
                                method: "get",
                                params: o.params || {},
                                options: o
                            });
                        case 3:
                            return e.abrupt("return", e.sent);
                        case 4:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })));
            return function(t, o) {
                return e.apply(this, arguments)
            }
        }(), g.post = function() {
            var e = Object(l["a"])(Object(n["a"])().mark((function e(t, o, r) {
                return Object(n["a"])().wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, g({
                                url: t,
                                data: o,
                                method: "post",
                                options: r
                            });
                        case 2:
                            return e.abrupt("return", e.sent);
                        case 3:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })));
            return function(t, o, n) {
                return e.apply(this, arguments)
            }
        }(), t["a"] = g
    });
