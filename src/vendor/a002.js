// chunk-vendors webpack 模块 a002（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            (function(t) {
                var n;
                /*!
                    localForage -- Offline Storage, Improved
                    Version 1.10.0
                    https://localforage.github.io/localForage
                    (c) 2013-2017 Mozilla, Apache License 2.0
                */
                (function(t) {
                    e.exports = t()
                })((function() {
                    return function e(t, r, i) {
                        function o(s, u) {
                            if (!r[s]) {
                                if (!t[s]) {
                                    var c = "function" == typeof n && n;
                                    if (!u && c) return n(s, !0);
                                    if (a) return a(s, !0);
                                    var l = new Error("Cannot find module '" + s + "'");
                                    throw l.code = "MODULE_NOT_FOUND", l
                                }
                                var f = r[s] = {
                                    exports: {}
                                };
                                t[s][0].call(f.exports, (function(e) {
                                    var n = t[s][1][e];
                                    return o(n || e)
                                }), f, f.exports, e, t, r, i)
                            }
                            return r[s].exports
                        }
                        for (var a = "function" == typeof n && n, s = 0; s < i.length; s++) o(i[s]);
                        return o
                    }({
                        1: [function(e, n, r) {
                            (function(e) {
                                "use strict";
                                var t, r, i = e.MutationObserver || e.WebKitMutationObserver;
                                if (i) {
                                    var o = 0,
                                        a = new i(l),
                                        s = e.document.createTextNode("");
                                    a.observe(s, {
                                        characterData: !0
                                    }), t = function() {
                                        s.data = o = ++o % 2
                                    }
                                } else if (e.setImmediate || "undefined" === typeof e.MessageChannel) t = "document" in e && "onreadystatechange" in e.document.createElement("script") ? function() {
                                    var t = e.document.createElement("script");
                                    t.onreadystatechange = function() {
                                        l(), t.onreadystatechange = null, t.parentNode.removeChild(t), t = null
                                    }, e.document.documentElement.appendChild(t)
                                } : function() {
                                    setTimeout(l, 0)
                                };
                                else {
                                    var u = new e.MessageChannel;
                                    u.port1.onmessage = l, t = function() {
                                        u.port2.postMessage(0)
                                    }
                                }
                                var c = [];

                                function l() {
                                    var e, t;
                                    r = !0;
                                    var n = c.length;
                                    while (n) {
                                        t = c, c = [], e = -1;
                                        while (++e < n) t[e]();
                                        n = c.length
                                    }
                                    r = !1
                                }

                                function f(e) {
                                    1 !== c.push(e) || r || t()
                                }
                                n.exports = f
                            }).call(this, "undefined" !== typeof t ? t : "undefined" !== typeof self ? self : "undefined" !== typeof window ? window : {})
                        }, {}],
                        2: [function(e, t, n) {
                            "use strict";
                            var r = e(1);

                            function i() {}
                            var o = {},
                                a = ["REJECTED"],
                                s = ["FULFILLED"],
                                u = ["PENDING"];

                            function c(e) {
                                if ("function" !== typeof e) throw new TypeError("resolver must be a function");
                                this.state = u, this.queue = [], this.outcome = void 0, e !== i && h(this, e)
                            }

                            function l(e, t, n) {
                                this.promise = e, "function" === typeof t && (this.onFulfilled = t, this.callFulfilled = this.otherCallFulfilled), "function" === typeof n && (this.onRejected = n, this.callRejected = this.otherCallRejected)
                            }

                            function f(e, t, n) {
                                r((function() {
                                    var r;
                                    try {
                                        r = t(n)
                                    } catch (i) {
                                        return o.reject(e, i)
                                    }
                                    r === e ? o.reject(e, new TypeError("Cannot resolve promise with itself")) : o.resolve(e, r)
                                }))
                            }

                            function d(e) {
                                var t = e && e.then;
                                if (e && ("object" === typeof e || "function" === typeof e) && "function" === typeof t) return function() {
                                    t.apply(e, arguments)
                                }
                            }

                            function h(e, t) {
                                var n = !1;

                                function r(t) {
                                    n || (n = !0, o.reject(e, t))
                                }

                                function i(t) {
                                    n || (n = !0, o.resolve(e, t))
                                }

                                function a() {
                                    t(i, r)
                                }
                                var s = p(a);
                                "error" === s.status && r(s.value)
                            }

                            function p(e, t) {
                                var n = {};
                                try {
                                    n.value = e(t), n.status = "success"
                                } catch (r) {
                                    n.status = "error", n.value = r
                                }
                                return n
                            }

                            function v(e) {
                                return e instanceof this ? e : o.resolve(new this(i), e)
                            }

                            function m(e) {
                                var t = new this(i);
                                return o.reject(t, e)
                            }

                            function g(e) {
                                var t = this;
                                if ("[object Array]" !== Object.prototype.toString.call(e)) return this.reject(new TypeError("must be an array"));
                                var n = e.length,
                                    r = !1;
                                if (!n) return this.resolve([]);
                                var a = new Array(n),
                                    s = 0,
                                    u = -1,
                                    c = new this(i);
                                while (++u < n) l(e[u], u);
                                return c;

                                function l(e, i) {
                                    function u(e) {
                                        a[i] = e, ++s !== n || r || (r = !0, o.resolve(c, a))
                                    }
                                    t.resolve(e).then(u, (function(e) {
                                        r || (r = !0, o.reject(c, e))
                                    }))
                                }
                            }

                            function b(e) {
                                var t = this;
                                if ("[object Array]" !== Object.prototype.toString.call(e)) return this.reject(new TypeError("must be an array"));
                                var n = e.length,
                                    r = !1;
                                if (!n) return this.resolve([]);
                                var a = -1,
                                    s = new this(i);
                                while (++a < n) u(e[a]);
                                return s;

                                function u(e) {
                                    t.resolve(e).then((function(e) {
                                        r || (r = !0, o.resolve(s, e))
                                    }), (function(e) {
                                        r || (r = !0, o.reject(s, e))
                                    }))
                                }
                            }
                            t.exports = c, c.prototype["catch"] = function(e) {
                                return this.then(null, e)
                            }, c.prototype.then = function(e, t) {
                                if ("function" !== typeof e && this.state === s || "function" !== typeof t && this.state === a) return this;
                                var n = new this.constructor(i);
                                if (this.state !== u) {
                                    var r = this.state === s ? e : t;
                                    f(n, r, this.outcome)
                                } else this.queue.push(new l(n, e, t));
                                return n
                            }, l.prototype.callFulfilled = function(e) {
                                o.resolve(this.promise, e)
                            }, l.prototype.otherCallFulfilled = function(e) {
                                f(this.promise, this.onFulfilled, e)
                            }, l.prototype.callRejected = function(e) {
                                o.reject(this.promise, e)
                            }, l.prototype.otherCallRejected = function(e) {
                                f(this.promise, this.onRejected, e)
                            }, o.resolve = function(e, t) {
                                var n = p(d, t);
                                if ("error" === n.status) return o.reject(e, n.value);
                                var r = n.value;
                                if (r) h(e, r);
                                else {
                                    e.state = s, e.outcome = t;
                                    var i = -1,
                                        a = e.queue.length;
                                    while (++i < a) e.queue[i].callFulfilled(t)
                                }
                                return e
                            }, o.reject = function(e, t) {
                                e.state = a, e.outcome = t;
                                var n = -1,
                                    r = e.queue.length;
                                while (++n < r) e.queue[n].callRejected(t);
                                return e
                            }, c.resolve = v, c.reject = m, c.all = g, c.race = b
                        }, {
                            1: 1
                        }],
                        3: [function(e, n, r) {
                            (function(t) {
                                "use strict";
                                "function" !== typeof t.Promise && (t.Promise = e(2))
                            }).call(this, "undefined" !== typeof t ? t : "undefined" !== typeof self ? self : "undefined" !== typeof window ? window : {})
                        }, {
                            2: 2
                        }],
                        4: [function(e, t, n) {
                            "use strict";
                            var r = "function" === typeof Symbol && "symbol" === typeof Symbol.iterator ? function(e) {
                                return typeof e
                            } : function(e) {
                                return e && "function" === typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
                            };

                            function i(e, t) {
                                if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
                            }

                            function o() {
                                try {
                                    if ("undefined" !== typeof indexedDB) return indexedDB;
                                    if ("undefined" !== typeof webkitIndexedDB) return webkitIndexedDB;
                                    if ("undefined" !== typeof mozIndexedDB) return mozIndexedDB;
                                    if ("undefined" !== typeof OIndexedDB) return OIndexedDB;
                                    if ("undefined" !== typeof msIndexedDB) return msIndexedDB
                                } catch (e) {
                                    return
                                }
                            }
                            var a = o();

                            function s() {
                                try {
                                    if (!a || !a.open) return !1;
                                    var e = "undefined" !== typeof openDatabase && /(Safari|iPhone|iPad|iPod)/.test(navigator.userAgent) && !/Chrome/.test(navigator.userAgent) && !/BlackBerry/.test(navigator.platform),
                                        t = "function" === typeof fetch && -1 !== fetch.toString().indexOf("[native code");
                                    return (!e || t) && "undefined" !== typeof indexedDB && "undefined" !== typeof IDBKeyRange
                                } catch (n) {
                                    return !1
                                }
                            }

                            function u(e, t) {
                                e = e || [], t = t || {};
                                try {
                                    return new Blob(e, t)
                                } catch (o) {
                                    if ("TypeError" !== o.name) throw o;
                                    for (var n = "undefined" !== typeof BlobBuilder ? BlobBuilder : "undefined" !== typeof MSBlobBuilder ? MSBlobBuilder : "undefined" !== typeof MozBlobBuilder ? MozBlobBuilder : WebKitBlobBuilder, r = new n, i = 0; i < e.length; i += 1) r.append(e[i]);
                                    return r.getBlob(t.type)
                                }
                            }
                            "undefined" === typeof Promise && e(3);
                            var c = Promise;

                            function l(e, t) {
                                t && e.then((function(e) {
                                    t(null, e)
                                }), (function(e) {
                                    t(e)
                                }))
                            }

                            function f(e, t, n) {
                                "function" === typeof t && e.then(t), "function" === typeof n && e["catch"](n)
                            }

                            function d(e) {
                                return "string" !== typeof e && (console.warn(e + " used as a key, but it is not a string."), e = String(e)), e
                            }

                            function h() {
                                if (arguments.length && "function" === typeof arguments[arguments.length - 1]) return arguments[arguments.length - 1]
                            }
                            var p = "local-forage-detect-blob-support",
                                v = void 0,
                                m = {},
                                g = Object.prototype.toString,
                                b = "readonly",
                                y = "readwrite";

                            function _(e) {
                                for (var t = e.length, n = new ArrayBuffer(t), r = new Uint8Array(n), i = 0; i < t; i++) r[i] = e.charCodeAt(i);
                                return n
                            }

                            function w(e) {
                                return new c((function(t) {
                                    var n = e.transaction(p, y),
                                        r = u([""]);
                                    n.objectStore(p).put(r, "key"), n.onabort = function(e) {
                                        e.preventDefault(), e.stopPropagation(), t(!1)
                                    }, n.oncomplete = function() {
                                        var e = navigator.userAgent.match(/Chrome\/(\d+)/),
                                            n = navigator.userAgent.match(/Edge\//);
                                        t(n || !e || parseInt(e[1], 10) >= 43)
                                    }
                                }))["catch"]((function() {
                                    return !1
                                }))
                            }

                            function x(e) {
                                return "boolean" === typeof v ? c.resolve(v) : w(e).then((function(e) {
                                    return v = e, v
                                }))
                            }

                            function C(e) {
                                var t = m[e.name],
                                    n = {};
                                n.promise = new c((function(e, t) {
                                    n.resolve = e, n.reject = t
                                })), t.deferredOperations.push(n), t.dbReady ? t.dbReady = t.dbReady.then((function() {
                                    return n.promise
                                })) : t.dbReady = n.promise
                            }

                            function S(e) {
                                var t = m[e.name],
                                    n = t.deferredOperations.pop();
                                if (n) return n.resolve(), n.promise
                            }

                            function O(e, t) {
                                var n = m[e.name],
                                    r = n.deferredOperations.pop();
                                if (r) return r.reject(t), r.promise
                            }

                            function E(e, t) {
                                return new c((function(n, r) {
                                    if (m[e.name] = m[e.name] || L(), e.db) {
                                        if (!t) return n(e.db);
                                        C(e), e.db.close()
                                    }
                                    var i = [e.name];
                                    t && i.push(e.version);
                                    var o = a.open.apply(a, i);
                                    t && (o.onupgradeneeded = function(t) {
                                        var n = o.result;
                                        try {
                                            n.createObjectStore(e.storeName), t.oldVersion <= 1 && n.createObjectStore(p)
                                        } catch (r) {
                                            if ("ConstraintError" !== r.name) throw r;
                                            console.warn('The database "' + e.name + '" has been upgraded from version ' + t.oldVersion + " to version " + t.newVersion + ', but the storage "' + e.storeName + '" already exists.')
                                        }
                                    }), o.onerror = function(e) {
                                        e.preventDefault(), r(o.error)
                                    }, o.onsuccess = function() {
                                        var t = o.result;
                                        t.onversionchange = function(e) {
                                            e.target.close()
                                        }, n(t), S(e)
                                    }
                                }))
                            }

                            function k(e) {
                                return E(e, !1)
                            }

                            function T(e) {
                                return E(e, !0)
                            }

                            function j(e, t) {
                                if (!e.db) return !0;
                                var n = !e.db.objectStoreNames.contains(e.storeName),
                                    r = e.version < e.db.version,
                                    i = e.version > e.db.version;
                                if (r && (e.version !== t && console.warn('The database "' + e.name + "\" can't be downgraded from version " + e.db.version + " to version " + e.version + "."), e.version = e.db.version), i || n) {
                                    if (n) {
                                        var o = e.db.version + 1;
                                        o > e.version && (e.version = o)
                                    }
                                    return !0
                                }
                                return !1
                            }

                            function $(e) {
                                return new c((function(t, n) {
                                    var r = new FileReader;
                                    r.onerror = n, r.onloadend = function(n) {
                                        var r = btoa(n.target.result || "");
                                        t({
                                            __local_forage_encoded_blob: !0,
                                            data: r,
                                            type: e.type
                                        })
                                    }, r.readAsBinaryString(e)
                                }))
                            }

                            function A(e) {
                                var t = _(atob(e.data));
                                return u([t], {
                                    type: e.type
                                })
                            }

                            function P(e) {
                                return e && e.__local_forage_encoded_blob
                            }

                            function I(e) {
                                var t = this,
                                    n = t._initReady().then((function() {
                                        var e = m[t._dbInfo.name];
                                        if (e && e.dbReady) return e.dbReady
                                    }));
                                return f(n, e, e), n
                            }

                            function N(e) {
                                C(e);
                                for (var t = m[e.name], n = t.forages, r = 0; r < n.length; r++) {
                                    var i = n[r];
                                    i._dbInfo.db && (i._dbInfo.db.close(), i._dbInfo.db = null)
                                }
                                return e.db = null, k(e).then((function(t) {
                                    return e.db = t, j(e) ? T(e) : t
                                })).then((function(r) {
                                    e.db = t.db = r;
                                    for (var i = 0; i < n.length; i++) n[i]._dbInfo.db = r
                                }))["catch"]((function(t) {
                                    throw O(e, t), t
                                }))
                            }

                            function M(e, t, n, r) {
                                void 0 === r && (r = 1);
                                try {
                                    var i = e.db.transaction(e.storeName, t);
                                    n(null, i)
                                } catch (o) {
                                    if (r > 0 && (!e.db || "InvalidStateError" === o.name || "NotFoundError" === o.name)) return c.resolve().then((function() {
                                        if (!e.db || "NotFoundError" === o.name && !e.db.objectStoreNames.contains(e.storeName) && e.version <= e.db.version) return e.db && (e.version = e.db.version + 1), T(e)
                                    })).then((function() {
                                        return N(e).then((function() {
                                            M(e, t, n, r - 1)
                                        }))
                                    }))["catch"](n);
                                    n(o)
                                }
                            }

                            function L() {
                                return {
                                    forages: [],
                                    db: null,
                                    dbReady: null,
                                    deferredOperations: []
                                }
                            }

                            function R(e) {
                                var t = this,
                                    n = {
                                        db: null
                                    };
                                if (e)
                                    for (var r in e) n[r] = e[r];
                                var i = m[n.name];
                                i || (i = L(), m[n.name] = i), i.forages.push(t), t._initReady || (t._initReady = t.ready, t.ready = I);
                                var o = [];

                                function a() {
                                    return c.resolve()
                                }
                                for (var s = 0; s < i.forages.length; s++) {
                                    var u = i.forages[s];
                                    u !== t && o.push(u._initReady()["catch"](a))
                                }
                                var l = i.forages.slice(0);
                                return c.all(o).then((function() {
                                    return n.db = i.db, k(n)
                                })).then((function(e) {
                                    return n.db = e, j(n, t._defaultConfig.version) ? T(n) : e
                                })).then((function(e) {
                                    n.db = i.db = e, t._dbInfo = n;
                                    for (var r = 0; r < l.length; r++) {
                                        var o = l[r];
                                        o !== t && (o._dbInfo.db = n.db, o._dbInfo.version = n.version)
                                    }
                                }))
                            }

                            function F(e, t) {
                                var n = this;
                                e = d(e);
                                var r = new c((function(t, r) {
                                    n.ready().then((function() {
                                        M(n._dbInfo, b, (function(i, o) {
                                            if (i) return r(i);
                                            try {
                                                var a = o.objectStore(n._dbInfo.storeName),
                                                    s = a.get(e);
                                                s.onsuccess = function() {
                                                    var e = s.result;
                                                    void 0 === e && (e = null), P(e) && (e = A(e)), t(e)
                                                }, s.onerror = function() {
                                                    r(s.error)
                                                }
                                            } catch (u) {
                                                r(u)
                                            }
                                        }))
                                    }))["catch"](r)
                                }));
                                return l(r, t), r
                            }

                            function D(e, t) {
                                var n = this,
                                    r = new c((function(t, r) {
                                        n.ready().then((function() {
                                            M(n._dbInfo, b, (function(i, o) {
                                                if (i) return r(i);
                                                try {
                                                    var a = o.objectStore(n._dbInfo.storeName),
                                                        s = a.openCursor(),
                                                        u = 1;
                                                    s.onsuccess = function() {
                                                        var n = s.result;
                                                        if (n) {
                                                            var r = n.value;
                                                            P(r) && (r = A(r));
                                                            var i = e(r, n.key, u++);
                                                            void 0 !== i ? t(i) : n["continue"]()
                                                        } else t()
                                                    }, s.onerror = function() {
                                                        r(s.error)
                                                    }
                                                } catch (c) {
                                                    r(c)
                                                }
                                            }))
                                        }))["catch"](r)
                                    }));
                                return l(r, t), r
                            }

                            function B(e, t, n) {
                                var r = this;
                                e = d(e);
                                var i = new c((function(n, i) {
                                    var o;
                                    r.ready().then((function() {
                                        return o = r._dbInfo, "[object Blob]" === g.call(t) ? x(o.db).then((function(e) {
                                            return e ? t : $(t)
                                        })) : t
                                    })).then((function(t) {
                                        M(r._dbInfo, y, (function(o, a) {
                                            if (o) return i(o);
                                            try {
                                                var s = a.objectStore(r._dbInfo.storeName);
                                                null === t && (t = void 0);
                                                var u = s.put(t, e);
                                                a.oncomplete = function() {
                                                    void 0 === t && (t = null), n(t)
                                                }, a.onabort = a.onerror = function() {
                                                    var e = u.error ? u.error : u.transaction.error;
                                                    i(e)
                                                }
                                            } catch (c) {
                                                i(c)
                                            }
                                        }))
                                    }))["catch"](i)
                                }));
                                return l(i, n), i
                            }

                            function z(e, t) {
                                var n = this;
                                e = d(e);
                                var r = new c((function(t, r) {
                                    n.ready().then((function() {
                                        M(n._dbInfo, y, (function(i, o) {
                                            if (i) return r(i);
                                            try {
                                                var a = o.objectStore(n._dbInfo.storeName),
                                                    s = a["delete"](e);
                                                o.oncomplete = function() {
                                                    t()
                                                }, o.onerror = function() {
                                                    r(s.error)
                                                }, o.onabort = function() {
                                                    var e = s.error ? s.error : s.transaction.error;
                                                    r(e)
                                                }
                                            } catch (u) {
                                                r(u)
                                            }
                                        }))
                                    }))["catch"](r)
                                }));
                                return l(r, t), r
                            }

                            function V(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            M(t._dbInfo, y, (function(r, i) {
                                                if (r) return n(r);
                                                try {
                                                    var o = i.objectStore(t._dbInfo.storeName),
                                                        a = o.clear();
                                                    i.oncomplete = function() {
                                                        e()
                                                    }, i.onabort = i.onerror = function() {
                                                        var e = a.error ? a.error : a.transaction.error;
                                                        n(e)
                                                    }
                                                } catch (s) {
                                                    n(s)
                                                }
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function H(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            M(t._dbInfo, b, (function(r, i) {
                                                if (r) return n(r);
                                                try {
                                                    var o = i.objectStore(t._dbInfo.storeName),
                                                        a = o.count();
                                                    a.onsuccess = function() {
                                                        e(a.result)
                                                    }, a.onerror = function() {
                                                        n(a.error)
                                                    }
                                                } catch (s) {
                                                    n(s)
                                                }
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function W(e, t) {
                                var n = this,
                                    r = new c((function(t, r) {
                                        e < 0 ? t(null) : n.ready().then((function() {
                                            M(n._dbInfo, b, (function(i, o) {
                                                if (i) return r(i);
                                                try {
                                                    var a = o.objectStore(n._dbInfo.storeName),
                                                        s = !1,
                                                        u = a.openKeyCursor();
                                                    u.onsuccess = function() {
                                                        var n = u.result;
                                                        n ? 0 === e || s ? t(n.key) : (s = !0, n.advance(e)) : t(null)
                                                    }, u.onerror = function() {
                                                        r(u.error)
                                                    }
                                                } catch (c) {
                                                    r(c)
                                                }
                                            }))
                                        }))["catch"](r)
                                    }));
                                return l(r, t), r
                            }

                            function q(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            M(t._dbInfo, b, (function(r, i) {
                                                if (r) return n(r);
                                                try {
                                                    var o = i.objectStore(t._dbInfo.storeName),
                                                        a = o.openKeyCursor(),
                                                        s = [];
                                                    a.onsuccess = function() {
                                                        var t = a.result;
                                                        t ? (s.push(t.key), t["continue"]()) : e(s)
                                                    }, a.onerror = function() {
                                                        n(a.error)
                                                    }
                                                } catch (u) {
                                                    n(u)
                                                }
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function U(e, t) {
                                t = h.apply(this, arguments);
                                var n = this.config();
                                e = "function" !== typeof e && e || {}, e.name || (e.name = e.name || n.name, e.storeName = e.storeName || n.storeName);
                                var r, i = this;
                                if (e.name) {
                                    var o = e.name === n.name && i._dbInfo.db,
                                        s = o ? c.resolve(i._dbInfo.db) : k(e).then((function(t) {
                                            var n = m[e.name],
                                                r = n.forages;
                                            n.db = t;
                                            for (var i = 0; i < r.length; i++) r[i]._dbInfo.db = t;
                                            return t
                                        }));
                                    r = e.storeName ? s.then((function(t) {
                                        if (t.objectStoreNames.contains(e.storeName)) {
                                            var n = t.version + 1;
                                            C(e);
                                            var r = m[e.name],
                                                i = r.forages;
                                            t.close();
                                            for (var o = 0; o < i.length; o++) {
                                                var s = i[o];
                                                s._dbInfo.db = null, s._dbInfo.version = n
                                            }
                                            var u = new c((function(t, r) {
                                                var i = a.open(e.name, n);
                                                i.onerror = function(e) {
                                                    var t = i.result;
                                                    t.close(), r(e)
                                                }, i.onupgradeneeded = function() {
                                                    var t = i.result;
                                                    t.deleteObjectStore(e.storeName)
                                                }, i.onsuccess = function() {
                                                    var e = i.result;
                                                    e.close(), t(e)
                                                }
                                            }));
                                            return u.then((function(e) {
                                                r.db = e;
                                                for (var t = 0; t < i.length; t++) {
                                                    var n = i[t];
                                                    n._dbInfo.db = e, S(n._dbInfo)
                                                }
                                            }))["catch"]((function(t) {
                                                throw (O(e, t) || c.resolve())["catch"]((function() {})), t
                                            }))
                                        }
                                    })) : s.then((function(t) {
                                        C(e);
                                        var n = m[e.name],
                                            r = n.forages;
                                        t.close();
                                        for (var i = 0; i < r.length; i++) {
                                            var o = r[i];
                                            o._dbInfo.db = null
                                        }
                                        var s = new c((function(t, n) {
                                            var r = a.deleteDatabase(e.name);
                                            r.onerror = function() {
                                                var e = r.result;
                                                e && e.close(), n(r.error)
                                            }, r.onblocked = function() {
                                                console.warn('dropInstance blocked for database "' + e.name + '" until all open connections are closed')
                                            }, r.onsuccess = function() {
                                                var e = r.result;
                                                e && e.close(), t(e)
                                            }
                                        }));
                                        return s.then((function(e) {
                                            n.db = e;
                                            for (var t = 0; t < r.length; t++) {
                                                var i = r[t];
                                                S(i._dbInfo)
                                            }
                                        }))["catch"]((function(t) {
                                            throw (O(e, t) || c.resolve())["catch"]((function() {})), t
                                        }))
                                    }))
                                } else r = c.reject("Invalid arguments");
                                return l(r, t), r
                            }
                            var X = {
                                _driver: "asyncStorage",
                                _initStorage: R,
                                _support: s(),
                                iterate: D,
                                getItem: F,
                                setItem: B,
                                removeItem: z,
                                clear: V,
                                length: H,
                                key: W,
                                keys: q,
                                dropInstance: U
                            };

                            function G() {
                                return "function" === typeof openDatabase
                            }
                            var Y = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
                                K = "~~local_forage_type~",
                                J = /^~~local_forage_type~([^~]+)~/,
                                Z = "__lfsc__:",
                                Q = Z.length,
                                ee = "arbf",
                                te = "blob",
                                ne = "si08",
                                re = "ui08",
                                ie = "uic8",
                                oe = "si16",
                                ae = "si32",
                                se = "ur16",
                                ue = "ui32",
                                ce = "fl32",
                                le = "fl64",
                                fe = Q + ee.length,
                                de = Object.prototype.toString;

                            function he(e) {
                                var t, n, r, i, o, a = .75 * e.length,
                                    s = e.length,
                                    u = 0;
                                "=" === e[e.length - 1] && (a--, "=" === e[e.length - 2] && a--);
                                var c = new ArrayBuffer(a),
                                    l = new Uint8Array(c);
                                for (t = 0; t < s; t += 4) n = Y.indexOf(e[t]), r = Y.indexOf(e[t + 1]), i = Y.indexOf(e[t + 2]), o = Y.indexOf(e[t + 3]), l[u++] = n << 2 | r >> 4, l[u++] = (15 & r) << 4 | i >> 2, l[u++] = (3 & i) << 6 | 63 & o;
                                return c
                            }

                            function pe(e) {
                                var t, n = new Uint8Array(e),
                                    r = "";
                                for (t = 0; t < n.length; t += 3) r += Y[n[t] >> 2], r += Y[(3 & n[t]) << 4 | n[t + 1] >> 4], r += Y[(15 & n[t + 1]) << 2 | n[t + 2] >> 6], r += Y[63 & n[t + 2]];
                                return n.length % 3 === 2 ? r = r.substring(0, r.length - 1) + "=" : n.length % 3 === 1 && (r = r.substring(0, r.length - 2) + "=="), r
                            }

                            function ve(e, t) {
                                var n = "";
                                if (e && (n = de.call(e)), e && ("[object ArrayBuffer]" === n || e.buffer && "[object ArrayBuffer]" === de.call(e.buffer))) {
                                    var r, i = Z;
                                    e instanceof ArrayBuffer ? (r = e, i += ee) : (r = e.buffer, "[object Int8Array]" === n ? i += ne : "[object Uint8Array]" === n ? i += re : "[object Uint8ClampedArray]" === n ? i += ie : "[object Int16Array]" === n ? i += oe : "[object Uint16Array]" === n ? i += se : "[object Int32Array]" === n ? i += ae : "[object Uint32Array]" === n ? i += ue : "[object Float32Array]" === n ? i += ce : "[object Float64Array]" === n ? i += le : t(new Error("Failed to get type for BinaryArray"))), t(i + pe(r))
                                } else if ("[object Blob]" === n) {
                                    var o = new FileReader;
                                    o.onload = function() {
                                        var n = K + e.type + "~" + pe(this.result);
                                        t(Z + te + n)
                                    }, o.readAsArrayBuffer(e)
                                } else try {
                                    t(JSON.stringify(e))
                                } catch (a) {
                                    console.error("Couldn't convert value into a JSON string: ", e), t(null, a)
                                }
                            }

                            function me(e) {
                                if (e.substring(0, Q) !== Z) return JSON.parse(e);
                                var t, n = e.substring(fe),
                                    r = e.substring(Q, fe);
                                if (r === te && J.test(n)) {
                                    var i = n.match(J);
                                    t = i[1], n = n.substring(i[0].length)
                                }
                                var o = he(n);
                                switch (r) {
                                    case ee:
                                        return o;
                                    case te:
                                        return u([o], {
                                            type: t
                                        });
                                    case ne:
                                        return new Int8Array(o);
                                    case re:
                                        return new Uint8Array(o);
                                    case ie:
                                        return new Uint8ClampedArray(o);
                                    case oe:
                                        return new Int16Array(o);
                                    case se:
                                        return new Uint16Array(o);
                                    case ae:
                                        return new Int32Array(o);
                                    case ue:
                                        return new Uint32Array(o);
                                    case ce:
                                        return new Float32Array(o);
                                    case le:
                                        return new Float64Array(o);
                                    default:
                                        throw new Error("Unkown type: " + r)
                                }
                            }
                            var ge = {
                                serialize: ve,
                                deserialize: me,
                                stringToBuffer: he,
                                bufferToString: pe
                            };

                            function be(e, t, n, r) {
                                e.executeSql("CREATE TABLE IF NOT EXISTS " + t.storeName + " (id INTEGER PRIMARY KEY, key unique, value)", [], n, r)
                            }

                            function ye(e) {
                                var t = this,
                                    n = {
                                        db: null
                                    };
                                if (e)
                                    for (var r in e) n[r] = "string" !== typeof e[r] ? e[r].toString() : e[r];
                                var i = new c((function(e, r) {
                                    try {
                                        n.db = openDatabase(n.name, String(n.version), n.description, n.size)
                                    } catch (i) {
                                        return r(i)
                                    }
                                    n.db.transaction((function(i) {
                                        be(i, n, (function() {
                                            t._dbInfo = n, e()
                                        }), (function(e, t) {
                                            r(t)
                                        }))
                                    }), r)
                                }));
                                return n.serializer = ge, i
                            }

                            function _e(e, t, n, r, i, o) {
                                e.executeSql(n, r, i, (function(e, a) {
                                    a.code === a.SYNTAX_ERR ? e.executeSql("SELECT name FROM sqlite_master WHERE type='table' AND name = ?", [t.storeName], (function(e, s) {
                                        s.rows.length ? o(e, a) : be(e, t, (function() {
                                            e.executeSql(n, r, i, o)
                                        }), o)
                                    }), o) : o(e, a)
                                }), o)
                            }

                            function we(e, t) {
                                var n = this;
                                e = d(e);
                                var r = new c((function(t, r) {
                                    n.ready().then((function() {
                                        var i = n._dbInfo;
                                        i.db.transaction((function(n) {
                                            _e(n, i, "SELECT * FROM " + i.storeName + " WHERE key = ? LIMIT 1", [e], (function(e, n) {
                                                var r = n.rows.length ? n.rows.item(0).value : null;
                                                r && (r = i.serializer.deserialize(r)), t(r)
                                            }), (function(e, t) {
                                                r(t)
                                            }))
                                        }))
                                    }))["catch"](r)
                                }));
                                return l(r, t), r
                            }

                            function xe(e, t) {
                                var n = this,
                                    r = new c((function(t, r) {
                                        n.ready().then((function() {
                                            var i = n._dbInfo;
                                            i.db.transaction((function(n) {
                                                _e(n, i, "SELECT * FROM " + i.storeName, [], (function(n, r) {
                                                    for (var o = r.rows, a = o.length, s = 0; s < a; s++) {
                                                        var u = o.item(s),
                                                            c = u.value;
                                                        if (c && (c = i.serializer.deserialize(c)), c = e(c, u.key, s + 1), void 0 !== c) return void t(c)
                                                    }
                                                    t()
                                                }), (function(e, t) {
                                                    r(t)
                                                }))
                                            }))
                                        }))["catch"](r)
                                    }));
                                return l(r, t), r
                            }

                            function Ce(e, t, n, r) {
                                var i = this;
                                e = d(e);
                                var o = new c((function(o, a) {
                                    i.ready().then((function() {
                                        void 0 === t && (t = null);
                                        var s = t,
                                            u = i._dbInfo;
                                        u.serializer.serialize(t, (function(t, c) {
                                            c ? a(c) : u.db.transaction((function(n) {
                                                _e(n, u, "INSERT OR REPLACE INTO " + u.storeName + " (key, value) VALUES (?, ?)", [e, t], (function() {
                                                    o(s)
                                                }), (function(e, t) {
                                                    a(t)
                                                }))
                                            }), (function(t) {
                                                if (t.code === t.QUOTA_ERR) {
                                                    if (r > 0) return void o(Ce.apply(i, [e, s, n, r - 1]));
                                                    a(t)
                                                }
                                            }))
                                        }))
                                    }))["catch"](a)
                                }));
                                return l(o, n), o
                            }

                            function Se(e, t, n) {
                                return Ce.apply(this, [e, t, n, 1])
                            }

                            function Oe(e, t) {
                                var n = this;
                                e = d(e);
                                var r = new c((function(t, r) {
                                    n.ready().then((function() {
                                        var i = n._dbInfo;
                                        i.db.transaction((function(n) {
                                            _e(n, i, "DELETE FROM " + i.storeName + " WHERE key = ?", [e], (function() {
                                                t()
                                            }), (function(e, t) {
                                                r(t)
                                            }))
                                        }))
                                    }))["catch"](r)
                                }));
                                return l(r, t), r
                            }

                            function Ee(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            var r = t._dbInfo;
                                            r.db.transaction((function(t) {
                                                _e(t, r, "DELETE FROM " + r.storeName, [], (function() {
                                                    e()
                                                }), (function(e, t) {
                                                    n(t)
                                                }))
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function ke(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            var r = t._dbInfo;
                                            r.db.transaction((function(t) {
                                                _e(t, r, "SELECT COUNT(key) as c FROM " + r.storeName, [], (function(t, n) {
                                                    var r = n.rows.item(0).c;
                                                    e(r)
                                                }), (function(e, t) {
                                                    n(t)
                                                }))
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function Te(e, t) {
                                var n = this,
                                    r = new c((function(t, r) {
                                        n.ready().then((function() {
                                            var i = n._dbInfo;
                                            i.db.transaction((function(n) {
                                                _e(n, i, "SELECT key FROM " + i.storeName + " WHERE id = ? LIMIT 1", [e + 1], (function(e, n) {
                                                    var r = n.rows.length ? n.rows.item(0).key : null;
                                                    t(r)
                                                }), (function(e, t) {
                                                    r(t)
                                                }))
                                            }))
                                        }))["catch"](r)
                                    }));
                                return l(r, t), r
                            }

                            function je(e) {
                                var t = this,
                                    n = new c((function(e, n) {
                                        t.ready().then((function() {
                                            var r = t._dbInfo;
                                            r.db.transaction((function(t) {
                                                _e(t, r, "SELECT key FROM " + r.storeName, [], (function(t, n) {
                                                    for (var r = [], i = 0; i < n.rows.length; i++) r.push(n.rows.item(i).key);
                                                    e(r)
                                                }), (function(e, t) {
                                                    n(t)
                                                }))
                                            }))
                                        }))["catch"](n)
                                    }));
                                return l(n, e), n
                            }

                            function $e(e) {
                                return new c((function(t, n) {
                                    e.transaction((function(r) {
                                        r.executeSql("SELECT name FROM sqlite_master WHERE type='table' AND name <> '__WebKitDatabaseInfoTable__'", [], (function(n, r) {
                                            for (var i = [], o = 0; o < r.rows.length; o++) i.push(r.rows.item(o).name);
                                            t({
                                                db: e,
                                                storeNames: i
                                            })
                                        }), (function(e, t) {
                                            n(t)
                                        }))
                                    }), (function(e) {
                                        n(e)
                                    }))
                                }))
                            }

                            function Ae(e, t) {
                                t = h.apply(this, arguments);
                                var n = this.config();
                                e = "function" !== typeof e && e || {}, e.name || (e.name = e.name || n.name, e.storeName = e.storeName || n.storeName);
                                var r, i = this;
                                return r = e.name ? new c((function(t) {
                                    var r;
                                    r = e.name === n.name ? i._dbInfo.db : openDatabase(e.name, "", "", 0), e.storeName ? t({
                                        db: r,
                                        storeNames: [e.storeName]
                                    }) : t($e(r))
                                })).then((function(e) {
                                    return new c((function(t, n) {
                                        e.db.transaction((function(r) {
                                            function i(e) {
                                                return new c((function(t, n) {
                                                    r.executeSql("DROP TABLE IF EXISTS " + e, [], (function() {
                                                        t()
                                                    }), (function(e, t) {
                                                        n(t)
                                                    }))
                                                }))
                                            }
                                            for (var o = [], a = 0, s = e.storeNames.length; a < s; a++) o.push(i(e.storeNames[a]));
                                            c.all(o).then((function() {
                                                t()
                                            }))["catch"]((function(e) {
                                                n(e)
                                            }))
                                        }), (function(e) {
                                            n(e)
                                        }))
                                    }))
                                })) : c.reject("Invalid arguments"), l(r, t), r
                            }
                            var Pe = {
                                _driver: "webSQLStorage",
                                _initStorage: ye,
                                _support: G(),
                                iterate: xe,
                                getItem: we,
                                setItem: Se,
                                removeItem: Oe,
                                clear: Ee,
                                length: ke,
                                key: Te,
                                keys: je,
                                dropInstance: Ae
                            };

                            function Ie() {
                                try {
                                    return "undefined" !== typeof localStorage && "setItem" in localStorage && !!localStorage.setItem
                                } catch (e) {
                                    return !1
                                }
                            }

                            function Ne(e, t) {
                                var n = e.name + "/";
                                return e.storeName !== t.storeName && (n += e.storeName + "/"), n
                            }

                            function Me() {
                                var e = "_localforage_support_test";
                                try {
                                    return localStorage.setItem(e, !0), localStorage.removeItem(e), !1
                                } catch (t) {
                                    return !0
                                }
                            }

                            function Le() {
                                return !Me() || localStorage.length > 0
                            }

                            function Re(e) {
                                var t = this,
                                    n = {};
                                if (e)
                                    for (var r in e) n[r] = e[r];
                                return n.keyPrefix = Ne(e, t._defaultConfig), Le() ? (t._dbInfo = n, n.serializer = ge, c.resolve()) : c.reject()
                            }

                            function Fe(e) {
                                var t = this,
                                    n = t.ready().then((function() {
                                        for (var e = t._dbInfo.keyPrefix, n = localStorage.length - 1; n >= 0; n--) {
                                            var r = localStorage.key(n);
                                            0 === r.indexOf(e) && localStorage.removeItem(r)
                                        }
                                    }));
                                return l(n, e), n
                            }

                            function De(e, t) {
                                var n = this;
                                e = d(e);
                                var r = n.ready().then((function() {
                                    var t = n._dbInfo,
                                        r = localStorage.getItem(t.keyPrefix + e);
                                    return r && (r = t.serializer.deserialize(r)), r
                                }));
                                return l(r, t), r
                            }

                            function Be(e, t) {
                                var n = this,
                                    r = n.ready().then((function() {
                                        for (var t = n._dbInfo, r = t.keyPrefix, i = r.length, o = localStorage.length, a = 1, s = 0; s < o; s++) {
                                            var u = localStorage.key(s);
                                            if (0 === u.indexOf(r)) {
                                                var c = localStorage.getItem(u);
                                                if (c && (c = t.serializer.deserialize(c)), c = e(c, u.substring(i), a++), void 0 !== c) return c
                                            }
                                        }
                                    }));
                                return l(r, t), r
                            }

                            function ze(e, t) {
                                var n = this,
                                    r = n.ready().then((function() {
                                        var t, r = n._dbInfo;
                                        try {
                                            t = localStorage.key(e)
                                        } catch (i) {
                                            t = null
                                        }
                                        return t && (t = t.substring(r.keyPrefix.length)), t
                                    }));
                                return l(r, t), r
                            }

                            function Ve(e) {
                                var t = this,
                                    n = t.ready().then((function() {
                                        for (var e = t._dbInfo, n = localStorage.length, r = [], i = 0; i < n; i++) {
                                            var o = localStorage.key(i);
                                            0 === o.indexOf(e.keyPrefix) && r.push(o.substring(e.keyPrefix.length))
                                        }
                                        return r
                                    }));
                                return l(n, e), n
                            }

                            function He(e) {
                                var t = this,
                                    n = t.keys().then((function(e) {
                                        return e.length
                                    }));
                                return l(n, e), n
                            }

                            function We(e, t) {
                                var n = this;
                                e = d(e);
                                var r = n.ready().then((function() {
                                    var t = n._dbInfo;
                                    localStorage.removeItem(t.keyPrefix + e)
                                }));
                                return l(r, t), r
                            }

                            function qe(e, t, n) {
                                var r = this;
                                e = d(e);
                                var i = r.ready().then((function() {
                                    void 0 === t && (t = null);
                                    var n = t;
                                    return new c((function(i, o) {
                                        var a = r._dbInfo;
                                        a.serializer.serialize(t, (function(t, r) {
                                            if (r) o(r);
                                            else try {
                                                localStorage.setItem(a.keyPrefix + e, t), i(n)
                                            } catch (s) {
                                                "QuotaExceededError" !== s.name && "NS_ERROR_DOM_QUOTA_REACHED" !== s.name || o(s), o(s)
                                            }
                                        }))
                                    }))
                                }));
                                return l(i, n), i
                            }

                            function Ue(e, t) {
                                if (t = h.apply(this, arguments), e = "function" !== typeof e && e || {}, !e.name) {
                                    var n = this.config();
                                    e.name = e.name || n.name, e.storeName = e.storeName || n.storeName
                                }
                                var r, i = this;
                                return r = e.name ? new c((function(t) {
                                    e.storeName ? t(Ne(e, i._defaultConfig)) : t(e.name + "/")
                                })).then((function(e) {
                                    for (var t = localStorage.length - 1; t >= 0; t--) {
                                        var n = localStorage.key(t);
                                        0 === n.indexOf(e) && localStorage.removeItem(n)
                                    }
                                })) : c.reject("Invalid arguments"), l(r, t), r
                            }
                            var Xe = {
                                    _driver: "localStorageWrapper",
                                    _initStorage: Re,
                                    _support: Ie(),
                                    iterate: Be,
                                    getItem: De,
                                    setItem: qe,
                                    removeItem: We,
                                    clear: Fe,
                                    length: He,
                                    key: ze,
                                    keys: Ve,
                                    dropInstance: Ue
                                },
                                Ge = function(e, t) {
                                    return e === t || "number" === typeof e && "number" === typeof t && isNaN(e) && isNaN(t)
                                },
                                Ye = function(e, t) {
                                    var n = e.length,
                                        r = 0;
                                    while (r < n) {
                                        if (Ge(e[r], t)) return !0;
                                        r++
                                    }
                                    return !1
                                },
                                Ke = Array.isArray || function(e) {
                                    return "[object Array]" === Object.prototype.toString.call(e)
                                },
                                Je = {},
                                Ze = {},
                                Qe = {
                                    INDEXEDDB: X,
                                    WEBSQL: Pe,
                                    LOCALSTORAGE: Xe
                                },
                                et = [Qe.INDEXEDDB._driver, Qe.WEBSQL._driver, Qe.LOCALSTORAGE._driver],
                                tt = ["dropInstance"],
                                nt = ["clear", "getItem", "iterate", "key", "keys", "length", "removeItem", "setItem"].concat(tt),
                                rt = {
                                    description: "",
                                    driver: et.slice(),
                                    name: "localforage",
                                    size: 4980736,
                                    storeName: "keyvaluepairs",
                                    version: 1
                                };

                            function it(e, t) {
                                e[t] = function() {
                                    var n = arguments;
                                    return e.ready().then((function() {
                                        return e[t].apply(e, n)
                                    }))
                                }
                            }

                            function ot() {
                                for (var e = 1; e < arguments.length; e++) {
                                    var t = arguments[e];
                                    if (t)
                                        for (var n in t) t.hasOwnProperty(n) && (Ke(t[n]) ? arguments[0][n] = t[n].slice() : arguments[0][n] = t[n])
                                }
                                return arguments[0]
                            }
                            var at = function() {
                                    function e(t) {
                                        for (var n in i(this, e), Qe)
                                            if (Qe.hasOwnProperty(n)) {
                                                var r = Qe[n],
                                                    o = r._driver;
                                                this[n] = o, Je[o] || this.defineDriver(r)
                                            } this._defaultConfig = ot({}, rt), this._config = ot({}, this._defaultConfig, t), this._driverSet = null, this._initDriver = null, this._ready = !1, this._dbInfo = null, this._wrapLibraryMethodsWithReady(), this.setDriver(this._config.driver)["catch"]((function() {}))
                                    }
                                    return e.prototype.config = function(e) {
                                        if ("object" === ("undefined" === typeof e ? "undefined" : r(e))) {
                                            if (this._ready) return new Error("Can't call config() after localforage has been used.");
                                            for (var t in e) {
                                                if ("storeName" === t && (e[t] = e[t].replace(/\W/g, "_")), "version" === t && "number" !== typeof e[t]) return new Error("Database version must be a number.");
                                                this._config[t] = e[t]
                                            }
                                            return !("driver" in e) || !e.driver || this.setDriver(this._config.driver)
                                        }
                                        return "string" === typeof e ? this._config[e] : this._config
                                    }, e.prototype.defineDriver = function(e, t, n) {
                                        var r = new c((function(t, n) {
                                            try {
                                                var r = e._driver,
                                                    i = new Error("Custom driver not compliant; see https://mozilla.github.io/localForage/#definedriver");
                                                if (!e._driver) return void n(i);
                                                for (var o = nt.concat("_initStorage"), a = 0, s = o.length; a < s; a++) {
                                                    var u = o[a],
                                                        f = !Ye(tt, u);
                                                    if ((f || e[u]) && "function" !== typeof e[u]) return void n(i)
                                                }
                                                var d = function() {
                                                    for (var t = function(e) {
                                                            return function() {
                                                                var t = new Error("Method " + e + " is not implemented by the current driver"),
                                                                    n = c.reject(t);
                                                                return l(n, arguments[arguments.length - 1]), n
                                                            }
                                                        }, n = 0, r = tt.length; n < r; n++) {
                                                        var i = tt[n];
                                                        e[i] || (e[i] = t(i))
                                                    }
                                                };
                                                d();
                                                var h = function(n) {
                                                    Je[r] && console.info("Redefining LocalForage driver: " + r), Je[r] = e, Ze[r] = n, t()
                                                };
                                                "_support" in e ? e._support && "function" === typeof e._support ? e._support().then(h, n) : h(!!e._support) : h(!0)
                                            } catch (p) {
                                                n(p)
                                            }
                                        }));
                                        return f(r, t, n), r
                                    }, e.prototype.driver = function() {
                                        return this._driver || null
                                    }, e.prototype.getDriver = function(e, t, n) {
                                        var r = Je[e] ? c.resolve(Je[e]) : c.reject(new Error("Driver not found."));
                                        return f(r, t, n), r
                                    }, e.prototype.getSerializer = function(e) {
                                        var t = c.resolve(ge);
                                        return f(t, e), t
                                    }, e.prototype.ready = function(e) {
                                        var t = this,
                                            n = t._driverSet.then((function() {
                                                return null === t._ready && (t._ready = t._initDriver()), t._ready
                                            }));
                                        return f(n, e, e), n
                                    }, e.prototype.setDriver = function(e, t, n) {
                                        var r = this;
                                        Ke(e) || (e = [e]);
                                        var i = this._getSupportedDrivers(e);

                                        function o() {
                                            r._config.driver = r.driver()
                                        }

                                        function a(e) {
                                            return r._extend(e), o(), r._ready = r._initStorage(r._config), r._ready
                                        }

                                        function s(e) {
                                            return function() {
                                                var t = 0;

                                                function n() {
                                                    while (t < e.length) {
                                                        var i = e[t];
                                                        return t++, r._dbInfo = null, r._ready = null, r.getDriver(i).then(a)["catch"](n)
                                                    }
                                                    o();
                                                    var s = new Error("No available storage method found.");
                                                    return r._driverSet = c.reject(s), r._driverSet
                                                }
                                                return n()
                                            }
                                        }
                                        var u = null !== this._driverSet ? this._driverSet["catch"]((function() {
                                            return c.resolve()
                                        })) : c.resolve();
                                        return this._driverSet = u.then((function() {
                                            var e = i[0];
                                            return r._dbInfo = null, r._ready = null, r.getDriver(e).then((function(e) {
                                                r._driver = e._driver, o(), r._wrapLibraryMethodsWithReady(), r._initDriver = s(i)
                                            }))
                                        }))["catch"]((function() {
                                            o();
                                            var e = new Error("No available storage method found.");
                                            return r._driverSet = c.reject(e), r._driverSet
                                        })), f(this._driverSet, t, n), this._driverSet
                                    }, e.prototype.supports = function(e) {
                                        return !!Ze[e]
                                    }, e.prototype._extend = function(e) {
                                        ot(this, e)
                                    }, e.prototype._getSupportedDrivers = function(e) {
                                        for (var t = [], n = 0, r = e.length; n < r; n++) {
                                            var i = e[n];
                                            this.supports(i) && t.push(i)
                                        }
                                        return t
                                    }, e.prototype._wrapLibraryMethodsWithReady = function() {
                                        for (var e = 0, t = nt.length; e < t; e++) it(this, nt[e])
                                    }, e.prototype.createInstance = function(t) {
                                        return new e(t)
                                    }, e
                                }(),
                                st = new at;
                            t.exports = st
                        }, {
                            3: 3
                        }]
                    }, {}, [4])(4)
                }))
            }).call(this, n("./c8ba.js"))
        
