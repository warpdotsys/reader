// chunk-vendors webpack 模块 0377（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            n.d(t, "a", (function() {
                return o
            }));
            var r = n("./3835.js"),
                i = (n("./d401.js"), n("./caad.js"), n("./14d9.js"), n("./a434.js"), n("./0d03.js"), n("./cca6.js"), n("./d3b7.js"), n("./ac1f.js"), n("./00b4.js"), n("./25f0.js"), n("./2532.js"), n("./5319.js"), n("./2ca0.js"), n("./e323.js"), window);

            function o(e, t) {
                var n, o, a = arguments.length > 2 && void 0 !== arguments[2] ? arguments[2] : {},
                    s = Object.assign({
                        tab: "\t",
                        indentOn: /[({\[]$/,
                        moveToNewLine: /^[)}\]]/,
                        spellcheck: !1,
                        catchTab: !0,
                        preserveIdent: !0,
                        addClosing: !0,
                        history: !0,
                        window: i
                    }, a),
                    u = s.window,
                    c = u.document,
                    l = [],
                    f = [],
                    d = -1,
                    h = !1;
                e.setAttribute("contenteditable", "plaintext-only"), e.setAttribute("spellcheck", s.spellcheck ? "true" : "false"), e.style.outline = "none", e.style.overflowWrap = "break-word", e.style.overflowY = "auto", e.style.whiteSpace = "pre-wrap";
                var p = !1;
                t(e), "plaintext-only" !== e.contentEditable && (p = !0), p && e.setAttribute("contenteditable", "true");
                var v = F((function() {
                        var n = _();
                        t(e, n), w(n)
                    }), 30),
                    m = !1,
                    g = function(e) {
                        return !I(e) && !N(e) && "Meta" !== e.key && "Control" !== e.key && "Alt" !== e.key && !e.key.startsWith("Arrow")
                    },
                    b = F((function(e) {
                        g(e) && (j(), m = !1)
                    }), 300),
                    y = function(t, n) {
                        l.push([t, n]), e.addEventListener(t, n)
                    };

                function _() {
                    var t = V(),
                        n = {
                            start: 0,
                            end: 0,
                            dir: void 0
                        },
                        r = t.anchorNode,
                        i = t.anchorOffset,
                        o = t.focusNode,
                        a = t.focusOffset;
                    if (!r || !o) throw "error1";
                    if (r === e && o === e) return n.start = i > 0 && e.textContent ? e.textContent.length : 0, n.end = a > 0 && e.textContent ? e.textContent.length : 0, n.dir = a >= i ? "->" : "<-", n;
                    if (r.nodeType === Node.ELEMENT_NODE) {
                        var s = c.createTextNode("");
                        r.insertBefore(s, r.childNodes[i]), r = s, i = 0
                    }
                    if (o.nodeType === Node.ELEMENT_NODE) {
                        var u = c.createTextNode("");
                        o.insertBefore(u, o.childNodes[a]), o = u, a = 0
                    }
                    return A(e, (function(e) {
                        if (e === r && e === o) return n.start += i, n.end += a, n.dir = i <= a ? "->" : "<-", "stop";
                        if (e === r) {
                            if (n.start += i, n.dir) return "stop";
                            n.dir = "->"
                        } else if (e === o) {
                            if (n.end += a, n.dir) return "stop";
                            n.dir = "<-"
                        }
                        e.nodeType === Node.TEXT_NODE && ("->" != n.dir && (n.start += e.nodeValue.length), "<-" != n.dir && (n.end += e.nodeValue.length))
                    })), e.normalize(), n
                }

                function w(t) {
                    var n, r, i = V(),
                        o = 0,
                        a = 0;
                    if (t.dir || (t.dir = "->"), t.start < 0 && (t.start = 0), t.end < 0 && (t.end = 0), "<-" == t.dir) {
                        var s = t.start,
                            u = t.end;
                        t.start = u, t.end = s
                    }
                    var c = 0;
                    if (A(e, (function(e) {
                            if (e.nodeType === Node.TEXT_NODE) {
                                var i = (e.nodeValue || "").length;
                                if (c + i > t.start && (n || (n = e, o = t.start - c), c + i > t.end)) return r = e, a = t.end - c, "stop";
                                c += i
                            }
                        })), n || (n = e, o = e.childNodes.length), r || (r = e, a = e.childNodes.length), "<-" == t.dir) {
                        var l = [r, a, n, o];
                        n = l[0], o = l[1], r = l[2], a = l[3]
                    }
                    i.setBaseAndExtent(n, o, r, a)
                }

                function x() {
                    var t = V(),
                        n = t.getRangeAt(0),
                        r = c.createRange();
                    return r.selectNodeContents(e), r.setEnd(n.startContainer, n.startOffset), r.toString()
                }

                function C() {
                    var t = V(),
                        n = t.getRangeAt(0),
                        r = c.createRange();
                    return r.selectNodeContents(e), r.setStart(n.endContainer, n.endOffset), r.toString()
                }

                function S(e) {
                    if ("Enter" === e.key) {
                        var t = x(),
                            n = C(),
                            i = D(t),
                            o = Object(r["a"])(i, 1),
                            a = o[0],
                            u = a;
                        if (s.indentOn.test(t) && (u += s.tab), u.length > 0 ? (z(e), e.stopPropagation(), R("\n" + u)) : O(e), u !== a && s.moveToNewLine.test(n)) {
                            var c = _();
                            R("\n" + a), w(c)
                        }
                    }
                }

                function O(e) {
                    if (p && "Enter" === e.key)
                        if (z(e), e.stopPropagation(), "" == C()) {
                            R("\n ");
                            var t = _();
                            t.start = --t.end, w(t)
                        } else R("\n")
                }

                function E(e) {
                    var t = "([{'\"",
                        n = ")]}'\"",
                        r = C(),
                        i = x(),
                        o = "\\" === i.substr(i.length - 1),
                        a = r.substr(0, 1);
                    if (n.includes(e.key) && !o && a === e.key) {
                        var s = _();
                        z(e), s.start = ++s.end, w(s)
                    } else if (t.includes(e.key) && !o && ("\"'".includes(e.key) || ["", " ", "\n"].includes(a))) {
                        z(e);
                        var u = _(),
                            c = u.start == u.end ? "" : V().toString(),
                            l = e.key + c + n[t.indexOf(e.key)];
                        R(l), u.start++, u.end++, w(u)
                    }
                }

                function k(e) {
                    if ("Tab" === e.key)
                        if (z(e), e.shiftKey) {
                            var t = x(),
                                n = D(t),
                                i = Object(r["a"])(n, 2),
                                o = i[0],
                                a = i[1];
                            if (o.length > 0) {
                                var u = _(),
                                    l = Math.min(s.tab.length, o.length);
                                w({
                                    start: a,
                                    end: a + l
                                }), c.execCommand("delete"), u.start -= l, u.end -= l, w(u)
                            }
                        } else R(s.tab)
                }

                function T(t) {
                    if (I(t)) {
                        z(t), d--;
                        var n = f[d];
                        n && (e.innerHTML = n.html, w(n.pos)), d < 0 && (d = 0)
                    }
                    if (N(t)) {
                        z(t), d++;
                        var r = f[d];
                        r && (e.innerHTML = r.html, w(r.pos)), d >= f.length && d--
                    }
                }

                function j() {
                    if (h) {
                        var t = e.innerHTML,
                            n = _(),
                            r = f[d];
                        if (!r || r.html !== t || r.pos.start !== n.start || r.pos.end !== n.end) {
                            d++, f[d] = {
                                html: t,
                                pos: n
                            }, f.splice(d + 1);
                            var i = 300;
                            d > i && (d = i, f.splice(0, 1))
                        }
                    }
                }

                function $(n) {
                    z(n);
                    var r = (n.originalEvent || n).clipboardData.getData("text/plain").replace(/\r/g, ""),
                        i = _();
                    R(r), t(e), w({
                        start: Math.min(i.start, i.end) + r.length,
                        end: Math.min(i.start, i.end) + r.length,
                        dir: "<-"
                    })
                }

                function A(e, t) {
                    var n = [];
                    e.firstChild && n.push(e.firstChild);
                    var r = n.pop();
                    while (r) {
                        if ("stop" === t(r)) break;
                        r.nextSibling && n.push(r.nextSibling), r.firstChild && n.push(r.firstChild), r = n.pop()
                    }
                }

                function P(e) {
                    return e.metaKey || e.ctrlKey
                }

                function I(e) {
                    return P(e) && !e.shiftKey && "Z" === L(e)
                }

                function N(e) {
                    return P(e) && e.shiftKey && "Z" === L(e)
                }

                function M(e) {
                    return P(e) && "C" === L(e)
                }

                function L(e) {
                    var t = e.key || e.keyCode || e.which;
                    if (t) return ("string" === typeof t ? t : String.fromCharCode(t)).toUpperCase()
                }

                function R(e) {
                    e = e.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&#039;"), c.execCommand("insertHTML", !1, e)
                }

                function F(e, t) {
                    var n = 0;
                    return function() {
                        for (var r = arguments.length, i = new Array(r), o = 0; o < r; o++) i[o] = arguments[o];
                        clearTimeout(n), n = u.setTimeout((function() {
                            return e.apply(void 0, i)
                        }), t)
                    }
                }

                function D(e) {
                    var t = e.length - 1;
                    while (t >= 0 && "\n" !== e[t]) t--;
                    t++;
                    var n = t;
                    while (n < e.length && /[ \t]/.test(e[n])) n++;
                    return [e.substring(t, n) || "", t, n]
                }

                function B() {
                    return e.textContent || ""
                }

                function z(e) {
                    e.preventDefault()
                }

                function V() {
                    var t;
                    return (null === (t = e.parentNode) || void 0 === t ? void 0 : t.nodeType) == Node.DOCUMENT_FRAGMENT_NODE ? e.parentNode.getSelection() : u.getSelection()
                }
                return y("keydown", (function(e) {
                    e.defaultPrevented || (o = B(), s.preserveIdent ? S(e) : O(e), s.catchTab && k(e), s.addClosing && E(e), s.history && (T(e), g(e) && !m && (j(), m = !0)), p && !M(e) && w(_()))
                })), y("keyup", (function(e) {
                    e.defaultPrevented || e.isComposing || (o !== B() && v(), b(e), n && n(B()))
                })), y("focus", (function(e) {
                    h = !0
                })), y("blur", (function(e) {
                    h = !1
                })), y("paste", (function(e) {
                    j(), $(e), j(), n && n(B())
                })), {
                    updateOptions: function(e) {
                        Object.assign(s, e)
                    },
                    updateCode: function(n) {
                        e.textContent = n, t(e)
                    },
                    onUpdate: function(e) {
                        n = e
                    },
                    toString: B,
                    save: _,
                    restore: w,
                    recordHistory: j,
                    destroy: function() {
                        for (var t = 0, n = l; t < n.length; t++) {
                            var i = Object(r["a"])(n[t], 2),
                                o = i[0],
                                a = i[1];
                            e.removeEventListener(o, a)
                        }
                    }
                }
            }
        
