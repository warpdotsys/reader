// 恢复自 reader-pro-3.2.14 前端产物 beauty/index-reader.js 的 webpack 模块 febf（逐字切片，形参 e, t, r）
const e = module;
const t = module.exports;
const r = require;

            "use strict";
            r.d(t, "a", (function() {
                return y
            }));
            var n = r("2b0e"),
                i = !0;
            n["default"], n["default"].util.warn;

            function a(e, t, r) {
                const {
                    attrs: i,
                    on: a,
                    ...o
                } = t;
                return Object(n["h"])(e, {
                    attrs: i,
                    on: a,
                    ...o
                }, r)
            }

            function o(e, t, r) {
                const {
                    attrs: i,
                    on: a,
                    ...o
                } = t;
                let s = {};
                return a && Object.entries(a).forEach(e => {
                    const [t, r] = e;
                    s["on" + (t[0].toUpperCase() + t.slice(1))] = r
                }), Object(n["h"])(e, {
                    ...i,
                    ...s,
                    ...o
                }, r)
            }
            const s = i ? a : o;

            function l(e, t, r) {
                const {
                    attrs: i,
                    on: a,
                    ...o
                } = t;
                return Object(n["h"])(e, {
                    attrs: i,
                    on: a,
                    ...o
                }, [r])
            }

            function u(e, t, r) {
                const {
                    attrs: i,
                    on: a,
                    ...o
                } = t;
                let s = {};
                return a && Object.entries(a).forEach(e => {
                    const [t, r] = e;
                    s["on" + (t[0].toUpperCase() + t.slice(1))] = r
                }), Object(n["h"])(e, {
                    ...i,
                    ...s,
                    ...o
                }, {
                    default: () => r
                })
            }
            const c = i ? l : u;

            function f(e, t) {
                var r;
                return null == (r = e.$scopedSlots) ? void 0 : r[t]
            }

            function h(e, t) {
                var r;
                return null == (r = e.$slots) ? void 0 : r[t]
            }
            const d = i ? f : h;

            function p(...e) {
                let t = "";
                return e.forEach(e => {
                    "string" === typeof e ? t += e + ";" : Array.isArray(e) ? t += p(...e) : "object" === typeof e && Object.entries(e).forEach(([e, r]) => {
                        r && (t += `${e}:${r};`)
                    })
                }), t
            }
            const v = e => {
                    const t = Object(n["ref"])(null);
                    return Object(n["onMounted"])(() => {
                        e.resizeObserver && t.value && e.resizeObserver.observe(t.value)
                    }), Object(n["onBeforeUnmount"])(() => {
                        e.resizeObserver && t.value && e.resizeObserver.unobserve(t.value)
                    }), {
                        itemRefEl: t
                    }
                },
                g = Object(n["defineComponent"])({
                    name: "ObserverItem",
                    props: {
                        resizeObserver: {
                            type: ResizeObserver,
                            require: !0
                        },
                        id: {
                            type: [String, Number],
                            require: !0
                        }
                    },
                    setup(e) {
                        return v(e)
                    },
                    render() {
                        var e;
                        const {
                            id: t
                        } = this;
                        return s("div", {
                            ref: "itemRefEl",
                            attrs: {
                                "data-id": t
                            }
                        }, [null == (e = d(this, "default")) ? void 0 : e()])
                    }
                }),
                m = {
                    itemGap: 0,
                    fixed: !1,
                    buffer: 0,
                    bufferTop: 0,
                    bufferBottom: 0,
                    scrollDistance: 0,
                    horizontal: !1,
                    fixSelection: !1,
                    start: 0,
                    offset: 0,
                    listStyle: "",
                    listClass: "",
                    itemStyle: "",
                    itemClass: "",
                    headerClass: "",
                    headerStyle: "",
                    footerClass: "",
                    footerStyle: "",
                    stickyHeaderClass: "",
                    stickyHeaderStyle: "",
                    stickyFooterClass: "",
                    stickyFooterStyle: ""
                };

            function b(e, t) {
                const r = new Proxy(e, {
                        get(e, t) {
                            var r;
                            return null != (r = Reflect.get(e, t)) ? r : Reflect.get(m, t)
                        }
                    }),
                    a = Object(n["ref"])(null),
                    o = Object(n["ref"])(null),
                    s = Object(n["ref"])(null),
                    l = Object(n["ref"])(null),
                    u = Object(n["ref"])(null),
                    c = Object(n["ref"])(null),
                    f = new Map,
                    h = Object(n["ref"])(0);
                let d = "backward",
                    p = !1,
                    v = !1,
                    g = !1,
                    b = null;
                const y = Object(n["shallowReactive"])({
                        clientSize: 0,
                        headerSize: 0,
                        footerSize: 0,
                        stickyHeaderSize: 0,
                        stickyFooterSize: 0
                    }),
                    S = Object(n["shallowReactive"])({
                        views: 0,
                        offset: 0,
                        listTotalSize: 0,
                        virtualSize: 0,
                        inViewBegin: 0,
                        inViewEnd: 0,
                        renderBegin: 0,
                        renderEnd: 0,
                        bufferTop: 0,
                        bufferBottom: 0
                    });

                function w() {
                    const e = r.horizontal ? "scrollLeft" : "scrollTop";
                    return a.value ? a.value[e] : 0
                }

                function k() {
                    return y.headerSize + y.footerSize + y.stickyHeaderSize + y.stickyFooterSize
                }

                function z() {
                    return S.listTotalSize + y.headerSize + y.footerSize + y.stickyHeaderSize + y.stickyFooterSize
                }

                function B(e) {
                    var t;
                    return r.fixed ? r.minSize + r.itemGap : null != (t = f.get(String(e))) ? t : r.minSize + r.itemGap
                }

                function R(e, t) {
                    f.set(String(e), t)
                }

                function O(e) {
                    f.delete(String(e))
                }

                function U(e) {
                    var t, n;
                    if (r.fixed) return {
                        top: (r.minSize + r.itemGap) * e,
                        current: r.minSize + r.itemGap,
                        bottom: (r.minSize + r.itemGap) * (e + 1)
                    };
                    const {
                        itemKey: i
                    } = r;
                    let a = y.headerSize;
                    for (let s = 0; s <= e - 1; s += 1) {
                        const e = B(null == (t = r.list[s]) ? void 0 : t[i]);
                        a += e
                    }
                    const o = B(null == (n = r.list[e]) ? void 0 : n[i]);
                    return {
                        top: a,
                        current: o,
                        bottom: a + o
                    }
                }

                function P(e) {
                    g = !0;
                    const t = r.horizontal ? "scrollLeft" : "scrollTop";
                    a.value && (a.value[t] = e)
                }
                async function L(e) {
                    if (e < 0) return;
                    if (e >= r.list.length - 1) return void E();
                    let {
                        top: t
                    } = U(e);
                    P(t);
                    const n = () => {
                        const {
                            top: r
                        } = U(e);
                        if (P(r), t !== r) return t = r, void(b = n);
                        b = null
                    };
                    b = n
                }
                async function j(e) {
                    var t;
                    const {
                        top: n,
                        bottom: i
                    } = U(e), a = w(), o = w() + y.clientSize, s = B(null == (t = r.list[e]) ? void 0 : t[r.itemKey]);
                    n < a && a < i && s < y.clientSize ? P(n) : n + y.stickyHeaderSize < o && o < i + y.stickyHeaderSize && s < y.clientSize ? P(i - y.clientSize + y.stickyHeaderSize) : (n + y.stickyHeaderSize >= o || i <= a) && L(e)
                }
                async function x() {
                    let e = 0;

                    function t() {
                        e += 1, P(0), setTimeout(() => {
                            var n;
                            if (e > 10) return;
                            const i = r.horizontal ? "scrollLeft" : "scrollTop";
                            0 !== (null == (n = null == a ? void 0 : a.value) ? void 0 : n[i]) && t()
                        }, 3)
                    }
                    t()
                }
                async function E() {
                    let e = 0;

                    function t() {
                        e += 1, P(z()), setTimeout(() => {
                            e > 10 || Math.abs(Math.round(S.offset + y.clientSize) - Math.round(z())) > 2 && t()
                        }, 3)
                    }
                    t()
                }

                function C() {
                    const e = window.getSelection();
                    if (e) {
                        const {
                            anchorNode: t,
                            anchorOffset: r,
                            focusNode: n,
                            focusOffset: i
                        } = e;
                        t && null !== r && null !== n && i && requestAnimationFrame(() => {
                            r < i ? e.setBaseAndExtent(t, r, n, i) : e.setBaseAndExtent(n, i, t, r)
                        })
                    }
                }

                function H(e) {
                    var n;
                    i && r.fixSelection && "backward" === d && C(), e < S.inViewBegin && (p = !0), S.inViewBegin = e, S.inViewEnd = Math.min(e + S.views, r.list.length - 1), null == (n = null == t ? void 0 : t.rangeUpdate) || n.call(t, S.inViewBegin, S.inViewEnd)
                }

                function A() {
                    var e, t;
                    const {
                        views: n,
                        offset: i,
                        inViewBegin: a
                    } = S, {
                        itemKey: o
                    } = r, s = i - y.headerSize;
                    let l = a,
                        u = J();
                    if (s < 0) H(0);
                    else {
                        if ("forward" === d) {
                            if (s >= u) return;
                            for (let t = l - 1; t >= 0; t -= 1) {
                                const n = B(null == (e = r.list[t]) ? void 0 : e[o]);
                                if (u -= n, u <= s && s < u + n) {
                                    l = t;
                                    break
                                }
                            }
                        }
                        if ("backward" === d) {
                            if (s <= u) return;
                            for (let e = l; e <= r.list.length - 1; e += 1) {
                                const n = B(null == (t = r.list[e]) ? void 0 : t[o]);
                                if (u <= s && s < u + n) {
                                    l = e;
                                    break
                                }
                                u += n
                            }
                            p = !1
                        }
                        l !== S.inViewBegin && H(l)
                    }
                }

                function T() {
                    var e, n;
                    const i = Math.max(r.scrollDistance, 2);
                    if ("forward" === d) S.offset - i <= 0 && (null == (e = null == t ? void 0 : t.toTop) || e.call(t, r.list[0]));
                    else if ("backward" === d) {
                        const e = Math.round(S.offset + y.clientSize),
                            a = Math.round(z() - e);
                        a <= i && (null == (n = null == t ? void 0 : t.toBottom) || n.call(t, r.list[r.list.length - 1]))
                    }
                }

                function q(e) {
                    var r;
                    null == (r = null == t ? void 0 : t.scroll) || r.call(t, e);
                    const n = w();
                    n !== S.offset && (d = n < S.offset ? "forward" : "backward", S.offset = n, A(), T())
                }

                function F() {
                    const e = Math.ceil(y.clientSize / (r.minSize + r.itemGap)) + 1;
                    S.views = e
                }

                function M() {
                    F(), H(S.inViewBegin)
                }

                function V() {
                    var e;
                    if (r.fixed) return void(S.listTotalSize = (r.minSize + r.itemGap) * r.list.length);
                    const {
                        itemKey: t
                    } = r;
                    let n = 0;
                    for (let i = 0; i <= r.list.length - 1; i += 1) n += B(null == (e = r.list[i]) ? void 0 : e[t]);
                    S.listTotalSize = n
                }

                function N() {
                    S.offset = 0, S.listTotalSize = 0, S.virtualSize = 0, S.inViewBegin = 0, S.inViewEnd = 0, S.renderBegin = 0, S.renderEnd = 0, f.clear(), G()
                }

                function $(e) {
                    V();
                    let t = 0;
                    e.forEach(e => {
                        t += B(e[r.itemKey])
                    }), D(), P(S.offset - t), A()
                }

                function I(e) {
                    V();
                    let t = 0;
                    e.forEach(e => {
                        t += B(e[r.itemKey])
                    }), D(), P(S.offset + t), v = !0, g = !1, A()
                }

                function G() {
                    h.value += 1
                }
                let K = void 0;
                "undefined" !== typeof ResizeObserver && (K = new ResizeObserver(e => {
                    var n;
                    let i = 0;
                    for (const a of e) {
                        const e = a.target.dataset.id;
                        if (e) {
                            const o = B(e);
                            let s = 0;
                            if (a.borderBoxSize) {
                                const e = Array.isArray(a.borderBoxSize) ? a.borderBoxSize[0] : a.borderBoxSize;
                                s = r.horizontal ? e.inlineSize : e.blockSize
                            } else s = r.horizontal ? a.contentRect.width : a.contentRect.height;
                            "client" === e ? (y.clientSize = s, M()) : "header" === e ? y.headerSize = s : "footer" === e ? y.footerSize = s : "stickyHeader" === e ? y.stickyHeaderSize = s : "stickyFooter" === e ? y.stickyFooterSize = s : o !== s && (R(e, s), i += s - o, null == (n = null == t ? void 0 : t.itemResize) || n.call(t, e, s))
                        }
                    }
                    S.listTotalSize += i, b && b(), !p && !v || 0 === i || g || (p = !1, v = !1, P(S.offset + i)), g = !1
                }));
                const D = () => {
                    var e;
                    let t = 0;
                    const n = S.renderBegin;
                    for (let i = 0; i < n; i++) t += B(null == (e = r.list[i]) ? void 0 : e[r.itemKey]);
                    S.virtualSize = t
                };

                function J() {
                    return S.virtualSize + Q(S.renderBegin, S.inViewBegin)
                }

                function Q(e, t) {
                    var n;
                    const i = Math.min(e, t),
                        a = Math.max(e, t);
                    let o = 0;
                    for (let s = i; s < a; s += 1) o += B(null == (n = r.list[s]) ? void 0 : n[r.itemKey]);
                    return o
                }

                function _() {
                    return S
                }
                Object(n["onBeforeMount"])(() => {
                    r.bufferTop ? S.bufferTop = r.bufferTop : S.bufferTop = r.buffer, r.bufferBottom ? S.bufferBottom = r.bufferBottom : S.bufferBottom = r.buffer
                }), Object(n["onMounted"])(() => {
                    a.value && (a.value.addEventListener("scroll", q), null == K || K.observe(a.value)), u.value && (null == K || K.observe(u.value)), c.value && (null == K || K.observe(c.value)), s.value && (null == K || K.observe(s.value)), l.value && (null == K || K.observe(l.value)), r.start ? L(r.start) : r.offset && P(r.offset)
                }), Object(n["onBeforeUnmount"])(() => {
                    a.value && (a.value.removeEventListener("scroll", q), null == K || K.unobserve(a.value), y.clientSize = 0), u.value && (null == K || K.unobserve(u.value), y.stickyHeaderSize = 0), c.value && (null == K || K.unobserve(c.value), y.stickyFooterSize = 0), s.value && (null == K || K.unobserve(s.value), y.headerSize = 0), l.value && (null == K || K.unobserve(l.value), y.footerSize = 0)
                });
                const Y = Object(n["shallowRef"])([]);

                function W(e, t) {
                    const n = S.renderBegin;
                    S.renderBegin = e, S.renderEnd = t, e > n ? S.virtualSize += Q(e, n) : S.virtualSize -= Q(e, n), Y.value = r.list.slice(S.renderBegin, S.renderEnd + 1), D()
                }
                return Object(n["watch"])(() => [S.inViewBegin, S.inViewEnd, h.value], (e, t) => {
                    if (e && t) {
                        const [t] = e, n = S.renderBegin;
                        let i = t,
                            a = S.inViewEnd;
                        if (i = Math.max(0, i - S.bufferTop), a = Math.min(a + S.bufferBottom, r.list.length - 1 > 0 ? r.list.length - 1 : 0), null == r ? void 0 : r.renderControl) {
                            const {
                                begin: e,
                                end: n
                            } = r.renderControl(t, S.inViewEnd);
                            i = e, a = n
                        }
                        S.renderBegin = i, S.renderEnd = a, i > n ? S.virtualSize += Q(i, n) : S.virtualSize -= Q(i, n), Y.value = r.list.slice(S.renderBegin, S.renderEnd + 1)
                    }
                }, {
                    immediate: !0
                }), Object(n["watch"])(() => r.list.length, () => {
                    r.list.length <= 0 ? N() : (V(), H(S.inViewBegin), D(), G())
                }, {
                    immediate: !0
                }), {
                    props: r,
                    renderList: Y,
                    clientRefEl: a,
                    listRefEl: o,
                    headerRefEl: s,
                    footerRefEl: l,
                    stickyHeaderRefEl: u,
                    stickyFooterRefEl: c,
                    reactiveData: S,
                    slotSize: y,
                    sizesMap: f,
                    resizeObserver: K,
                    getReactiveData: _,
                    getOffset: w,
                    getSlotSize: k,
                    reset: N,
                    scrollToIndex: L,
                    manualRender: W,
                    scrollIntoView: j,
                    scrollToTop: x,
                    scrollToBottom: E,
                    scrollToOffset: P,
                    getItemSize: B,
                    deleteItemSize: O,
                    deletedList2Top: $,
                    addedList2Top: I,
                    getItemPosByIndex: U,
                    forceUpdate: G
                }
            }
            const y = Object(n["defineComponent"])({
                name: "VirtList",
                emits: {
                    scroll: e => e,
                    toTop: e => e,
                    toBottom: e => e,
                    itemResize: (e, t) => !0,
                    rangeUpdate: (e, t) => !0
                },
                props: {
                    list: {
                        type: Array,
                        default: () => []
                    },
                    itemKey: {
                        type: [String, Number],
                        required: !0
                    },
                    minSize: {
                        type: Number,
                        default: 20,
                        required: !0
                    },
                    itemGap: {
                        type: Number,
                        default: 0
                    },
                    renderControl: {
                        type: Function,
                        default: void 0
                    },
                    fixed: {
                        type: Boolean,
                        default: !1
                    },
                    buffer: {
                        type: Number,
                        default: 0
                    },
                    bufferTop: {
                        type: Number,
                        default: 0
                    },
                    bufferBottom: {
                        type: Number,
                        default: 0
                    },
                    scrollDistance: {
                        type: Number,
                        default: 0
                    },
                    horizontal: {
                        type: Boolean,
                        default: !1
                    },
                    start: {
                        type: Number,
                        default: 0
                    },
                    offset: {
                        type: Number,
                        default: 0
                    },
                    listStyle: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    listClass: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    itemStyle: {
                        type: [String, Array, Object, Function],
                        default: ""
                    },
                    itemClass: {
                        type: [String, Array, Object, Function],
                        default: ""
                    },
                    headerClass: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    headerStyle: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    footerClass: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    footerStyle: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    stickyHeaderClass: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    stickyHeaderStyle: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    stickyFooterClass: {
                        type: [String, Array, Object],
                        default: ""
                    },
                    stickyFooterStyle: {
                        type: [String, Array, Object],
                        default: ""
                    }
                },
                setup(e, t) {
                    const r = {
                        scroll: e => {
                            t.emit("scroll", e)
                        },
                        toTop: e => {
                            t.emit("toTop", e)
                        },
                        toBottom: e => {
                            t.emit("toBottom", e)
                        },
                        itemResize: (e, r) => {
                            t.emit("itemResize", e, r)
                        },
                        rangeUpdate: (e, r) => {
                            t.emit("rangeUpdate", e, r)
                        }
                    };
                    return b(e, r)
                },
                render() {
                    const {
                        renderList: e,
                        reactiveData: t,
                        resizeObserver: r
                    } = this, {
                        itemGap: n,
                        itemKey: i,
                        horizontal: a,
                        listStyle: o,
                        listClass: l,
                        itemStyle: u,
                        itemClass: f,
                        headerClass: h,
                        headerStyle: v,
                        footerClass: m,
                        footerStyle: b,
                        stickyHeaderClass: y,
                        stickyHeaderStyle: S,
                        stickyFooterClass: w,
                        stickyFooterStyle: k
                    } = this.props, z = () => {
                        var e;
                        return d(this, "stickyHeader") ? s("div", {
                            key: "slot-sticky-header",
                            class: y,
                            style: p("position: sticky; z-index: 10;", a ? "left: 0" : "top: 0;", S),
                            ref: "stickyHeaderRefEl",
                            attrs: {
                                "data-id": "stickyHeader"
                            }
                        }, [null == (e = d(this, "stickyHeader")) ? void 0 : e()]) : null
                    }, B = () => {
                        var e;
                        return d(this, "stickyFooter") ? s("div", {
                            key: "slot-sticky-footer",
                            class: w,
                            style: p("position: sticky; z-index: 10;", a ? "right: 0" : "bottom: 0;", k),
                            ref: "stickyFooterRefEl",
                            attrs: {
                                "data-id": "stickyFooter"
                            }
                        }, [null == (e = d(this, "stickyFooter")) ? void 0 : e()]) : null
                    }, R = () => {
                        var e;
                        return d(this, "header") ? s("div", {
                            key: "slot-header",
                            class: h,
                            style: v,
                            ref: "headerRefEl",
                            attrs: {
                                "data-id": "header"
                            }
                        }, [null == (e = d(this, "header")) ? void 0 : e()]) : null
                    }, O = () => {
                        var e;
                        return d(this, "footer") ? s("div", {
                            key: "slot-footer",
                            class: m,
                            style: b,
                            ref: "footerRefEl",
                            attrs: {
                                "data-id": "footer"
                            }
                        }, [null == (e = d(this, "footer")) ? void 0 : e()]) : null
                    }, {
                        listTotalSize: U,
                        virtualSize: P,
                        renderBegin: L
                    } = t, j = () => {
                        var t, h;
                        const v = [];
                        for (let a = 0; a < e.length; a += 1) {
                            const o = e[a];
                            v.push(c(g, {
                                key: o[i],
                                class: "function" === typeof f ? f(o, a) : f,
                                style: p(n > 0 ? `padding: ${n/2}px 0;` : "", "function" === typeof u ? u(o, a) : u),
                                attrs: {
                                    id: o[i],
                                    resizeObserver: r
                                }
                            }, null == (t = d(this, "default")) ? void 0 : t({
                                itemData: o,
                                index: L + a
                            })))
                        }
                        if (0 === v.length && d(this, "empty")) {
                            const e = this.slotSize.clientSize - this.getSlotSize();
                            v.push(s("div", {
                                key: "slot-empty-" + e,
                                style: `height: ${e}px`
                            }, [null == (h = d(this, "empty")) ? void 0 : h()]))
                        }
                        const m = a ? `will-change: width; min-width: ${U}px; display: flex; ${o}` : `will-change: height; min-height: ${U}px; ${o}`,
                            b = a ? `width: ${P}px; will-change: width;` : `height: ${P}px; will-change: height;`;
                        return s("div", {
                            ref: "listRefEl",
                            class: l,
                            style: m
                        }, [s("div", {
                            style: b
                        }), v])
                    };
                    return s("div", {
                        ref: "clientRefEl",
                        class: "virt-list__client",
                        style: "width: 100%; height: 100%; overflow: auto;",
                        attrs: {
                            "data-id": "client"
                        }
                    }, [z(), R(), j(), O(), B()])
                }
            });
            Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean
        
