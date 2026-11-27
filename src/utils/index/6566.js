// 恢复自 reader-pro-3.2.14 前端产物 beauty/index.js 的 webpack 模块 6566（逐字切片，形参 t, e, o）
const t = module;
const e = module.exports;
const o = require;

            "use strict";
            var s = o("7c73"),
                r = o("edd0"),
                i = o("6964"),
                a = o("0366"),
                n = o("19aa"),
                c = o("7234"),
                l = o("2266"),
                u = o("c6d2"),
                h = o("4754"),
                f = o("2626"),
                p = o("83ab"),
                g = o("f183").fastKey,
                d = o("69f3"),
                k = d.set,
                m = d.getterFor;
            t.exports = {
                getConstructor: function(t, e, o, u) {
                    var h = t((function(t, r) {
                            n(t, f), k(t, {
                                type: e,
                                index: s(null),
                                first: null,
                                last: null,
                                size: 0
                            }), p || (t.size = 0), c(r) || l(r, t[u], {
                                that: t,
                                AS_ENTRIES: o
                            })
                        })),
                        f = h.prototype,
                        d = m(e),
                        v = function(t, e, o) {
                            var s, r, i = d(t),
                                a = S(t, e);
                            return a ? a.value = o : (i.last = a = {
                                index: r = g(e, !0),
                                key: e,
                                value: o,
                                previous: s = i.last,
                                next: null,
                                removed: !1
                            }, i.first || (i.first = a), s && (s.next = a), p ? i.size++ : t.size++, "F" !== r && (i.index[r] = a)), t
                        },
                        S = function(t, e) {
                            var o, s = d(t),
                                r = g(e);
                            if ("F" !== r) return s.index[r];
                            for (o = s.first; o; o = o.next)
                                if (o.key === e) return o
                        };
                    return i(f, {
                        clear: function() {
                            var t = this,
                                e = d(t),
                                o = e.first;
                            while (o) o.removed = !0, o.previous && (o.previous = o.previous.next = null), o = o.next;
                            e.first = e.last = null, e.index = s(null), p ? e.size = 0 : t.size = 0
                        },
                        delete: function(t) {
                            var e = this,
                                o = d(e),
                                s = S(e, t);
                            if (s) {
                                var r = s.next,
                                    i = s.previous;
                                delete o.index[s.index], s.removed = !0, i && (i.next = r), r && (r.previous = i), o.first === s && (o.first = r), o.last === s && (o.last = i), p ? o.size-- : e.size--
                            }
                            return !!s
                        },
                        forEach: function(t) {
                            var e, o = d(this),
                                s = a(t, arguments.length > 1 ? arguments[1] : void 0);
                            while (e = e ? e.next : o.first) {
                                s(e.value, e.key, this);
                                while (e && e.removed) e = e.previous
                            }
                        },
                        has: function(t) {
                            return !!S(this, t)
                        }
                    }), i(f, o ? {
                        get: function(t) {
                            var e = S(this, t);
                            return e && e.value
                        },
                        set: function(t, e) {
                            return v(this, 0 === t ? 0 : t, e)
                        }
                    } : {
                        add: function(t) {
                            return v(this, t = 0 === t ? 0 : t, t)
                        }
                    }), p && r(f, "size", {
                        configurable: !0,
                        get: function() {
                            return d(this).size
                        }
                    }), h
                },
                setStrong: function(t, e, o) {
                    var s = e + " Iterator",
                        r = m(e),
                        i = m(s);
                    u(t, e, (function(t, e) {
                        k(this, {
                            type: s,
                            target: t,
                            state: r(t),
                            kind: e,
                            last: null
                        })
                    }), (function() {
                        var t = i(this),
                            e = t.kind,
                            o = t.last;
                        while (o && o.removed) o = o.previous;
                        return t.target && (t.last = o = o ? o.next : t.state.first) ? h("keys" === e ? o.key : "values" === e ? o.value : [o.key, o.value], !1) : (t.target = null, h(void 0, !0))
                    }), o ? "entries" : "values", !o, !0), f(e)
                }
            }
        
