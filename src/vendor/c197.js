// chunk-vendors webpack 模块 c197（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            (function(t) {
                var n = "undefined" !== typeof window ? window : "undefined" !== typeof WorkerGlobalScope && self instanceof WorkerGlobalScope ? self : {},
                    r = function(e) {
                        var t = /(?:^|\s)lang(?:uage)?-([\w-]+)(?=\s|$)/i,
                            n = 0,
                            r = {},
                            i = {
                                manual: e.Prism && e.Prism.manual,
                                disableWorkerMessageHandler: e.Prism && e.Prism.disableWorkerMessageHandler,
                                util: {
                                    encode: function e(t) {
                                        return t instanceof o ? new o(t.type, e(t.content), t.alias) : Array.isArray(t) ? t.map(e) : t.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/\u00a0/g, " ")
                                    },
                                    type: function(e) {
                                        return Object.prototype.toString.call(e).slice(8, -1)
                                    },
                                    objId: function(e) {
                                        return e["__id"] || Object.defineProperty(e, "__id", {
                                            value: ++n
                                        }), e["__id"]
                                    },
                                    clone: function e(t, n) {
                                        var r, o;
                                        switch (n = n || {}, i.util.type(t)) {
                                            case "Object":
                                                if (o = i.util.objId(t), n[o]) return n[o];
                                                for (var a in r = {}, n[o] = r, t) t.hasOwnProperty(a) && (r[a] = e(t[a], n));
                                                return r;
                                            case "Array":
                                                return o = i.util.objId(t), n[o] ? n[o] : (r = [], n[o] = r, t.forEach((function(t, i) {
                                                    r[i] = e(t, n)
                                                })), r);
                                            default:
                                                return t
                                        }
                                    },
                                    getLanguage: function(e) {
                                        while (e) {
                                            var n = t.exec(e.className);
                                            if (n) return n[1].toLowerCase();
                                            e = e.parentElement
                                        }
                                        return "none"
                                    },
                                    setLanguage: function(e, n) {
                                        e.className = e.className.replace(RegExp(t, "gi"), ""), e.classList.add("language-" + n)
                                    },
                                    currentScript: function() {
                                        if ("undefined" === typeof document) return null;
                                        if (document.currentScript && "SCRIPT" === document.currentScript.tagName) return document.currentScript;
                                        try {
                                            throw new Error
                                        } catch (r) {
                                            var e = (/at [^(\r\n]*\((.*):[^:]+:[^:]+\)$/i.exec(r.stack) || [])[1];
                                            if (e) {
                                                var t = document.getElementsByTagName("script");
                                                for (var n in t)
                                                    if (t[n].src == e) return t[n]
                                            }
                                            return null
                                        }
                                    },
                                    isActive: function(e, t, n) {
                                        var r = "no-" + t;
                                        while (e) {
                                            var i = e.classList;
                                            if (i.contains(t)) return !0;
                                            if (i.contains(r)) return !1;
                                            e = e.parentElement
                                        }
                                        return !!n
                                    }
                                },
                                languages: {
                                    plain: r,
                                    plaintext: r,
                                    text: r,
                                    txt: r,
                                    extend: function(e, t) {
                                        var n = i.util.clone(i.languages[e]);
                                        for (var r in t) n[r] = t[r];
                                        return n
                                    },
                                    insertBefore: function(e, t, n, r) {
                                        r = r || i.languages;
                                        var o = r[e],
                                            a = {};
                                        for (var s in o)
                                            if (o.hasOwnProperty(s)) {
                                                if (s == t)
                                                    for (var u in n) n.hasOwnProperty(u) && (a[u] = n[u]);
                                                n.hasOwnProperty(s) || (a[s] = o[s])
                                            } var c = r[e];
                                        return r[e] = a, i.languages.DFS(i.languages, (function(t, n) {
                                            n === c && t != e && (this[t] = a)
                                        })), a
                                    },
                                    DFS: function e(t, n, r, o) {
                                        o = o || {};
                                        var a = i.util.objId;
                                        for (var s in t)
                                            if (t.hasOwnProperty(s)) {
                                                n.call(t, s, t[s], r || s);
                                                var u = t[s],
                                                    c = i.util.type(u);
                                                "Object" !== c || o[a(u)] ? "Array" !== c || o[a(u)] || (o[a(u)] = !0, e(u, n, s, o)) : (o[a(u)] = !0, e(u, n, null, o))
                                            }
                                    }
                                },
                                plugins: {},
                                highlightAll: function(e, t) {
                                    i.highlightAllUnder(document, e, t)
                                },
                                highlightAllUnder: function(e, t, n) {
                                    var r = {
                                        callback: n,
                                        container: e,
                                        selector: 'code[class*="language-"], [class*="language-"] code, code[class*="lang-"], [class*="lang-"] code'
                                    };
                                    i.hooks.run("before-highlightall", r), r.elements = Array.prototype.slice.apply(r.container.querySelectorAll(r.selector)), i.hooks.run("before-all-elements-highlight", r);
                                    for (var o, a = 0; o = r.elements[a++];) i.highlightElement(o, !0 === t, r.callback)
                                },
                                highlightElement: function(t, n, r) {
                                    var o = i.util.getLanguage(t),
                                        a = i.languages[o];
                                    i.util.setLanguage(t, o);
                                    var s = t.parentElement;
                                    s && "pre" === s.nodeName.toLowerCase() && i.util.setLanguage(s, o);
                                    var u = t.textContent,
                                        c = {
                                            element: t,
                                            language: o,
                                            grammar: a,
                                            code: u
                                        };

                                    function l(e) {
                                        c.highlightedCode = e, i.hooks.run("before-insert", c), c.element.innerHTML = c.highlightedCode, i.hooks.run("after-highlight", c), i.hooks.run("complete", c), r && r.call(c.element)
                                    }
                                    if (i.hooks.run("before-sanity-check", c), s = c.element.parentElement, s && "pre" === s.nodeName.toLowerCase() && !s.hasAttribute("tabindex") && s.setAttribute("tabindex", "0"), !c.code) return i.hooks.run("complete", c), void(r && r.call(c.element));
                                    if (i.hooks.run("before-highlight", c), c.grammar)
                                        if (n && e.Worker) {
                                            var f = new Worker(i.filename);
                                            f.onmessage = function(e) {
                                                l(e.data)
                                            }, f.postMessage(JSON.stringify({
                                                language: c.language,
                                                code: c.code,
                                                immediateClose: !0
                                            }))
                                        } else l(i.highlight(c.code, c.grammar, c.language));
                                    else l(i.util.encode(c.code))
                                },
                                highlight: function(e, t, n) {
                                    var r = {
                                        code: e,
                                        grammar: t,
                                        language: n
                                    };
                                    if (i.hooks.run("before-tokenize", r), !r.grammar) throw new Error('The language "' + r.language + '" has no grammar.');
                                    return r.tokens = i.tokenize(r.code, r.grammar), i.hooks.run("after-tokenize", r), o.stringify(i.util.encode(r.tokens), r.language)
                                },
                                tokenize: function(e, t) {
                                    var n = t.rest;
                                    if (n) {
                                        for (var r in n) t[r] = n[r];
                                        delete t.rest
                                    }
                                    var i = new u;
                                    return c(i, i.head, e), s(e, i, t, i.head, 0), f(i)
                                },
                                hooks: {
                                    all: {},
                                    add: function(e, t) {
                                        var n = i.hooks.all;
                                        n[e] = n[e] || [], n[e].push(t)
                                    },
                                    run: function(e, t) {
                                        var n = i.hooks.all[e];
                                        if (n && n.length)
                                            for (var r, o = 0; r = n[o++];) r(t)
                                    }
                                },
                                Token: o
                            };

                        function o(e, t, n, r) {
                            this.type = e, this.content = t, this.alias = n, this.length = 0 | (r || "").length
                        }

                        function a(e, t, n, r) {
                            e.lastIndex = t;
                            var i = e.exec(n);
                            if (i && r && i[1]) {
                                var o = i[1].length;
                                i.index += o, i[0] = i[0].slice(o)
                            }
                            return i
                        }

                        function s(e, t, n, r, u, f) {
                            for (var d in n)
                                if (n.hasOwnProperty(d) && n[d]) {
                                    var h = n[d];
                                    h = Array.isArray(h) ? h : [h];
                                    for (var p = 0; p < h.length; ++p) {
                                        if (f && f.cause == d + "," + p) return;
                                        var v = h[p],
                                            m = v.inside,
                                            g = !!v.lookbehind,
                                            b = !!v.greedy,
                                            y = v.alias;
                                        if (b && !v.pattern.global) {
                                            var _ = v.pattern.toString().match(/[imsuy]*$/)[0];
                                            v.pattern = RegExp(v.pattern.source, _ + "g")
                                        }
                                        for (var w = v.pattern || v, x = r.next, C = u; x !== t.tail; C += x.value.length, x = x.next) {
                                            if (f && C >= f.reach) break;
                                            var S = x.value;
                                            if (t.length > e.length) return;
                                            if (!(S instanceof o)) {
                                                var O, E = 1;
                                                if (b) {
                                                    if (O = a(w, C, e, g), !O || O.index >= e.length) break;
                                                    var k = O.index,
                                                        T = O.index + O[0].length,
                                                        j = C;
                                                    j += x.value.length;
                                                    while (k >= j) x = x.next, j += x.value.length;
                                                    if (j -= x.value.length, C = j, x.value instanceof o) continue;
                                                    for (var $ = x; $ !== t.tail && (j < T || "string" === typeof $.value); $ = $.next) E++, j += $.value.length;
                                                    E--, S = e.slice(C, j), O.index -= C
                                                } else if (O = a(w, 0, S, g), !O) continue;
                                                k = O.index;
                                                var A = O[0],
                                                    P = S.slice(0, k),
                                                    I = S.slice(k + A.length),
                                                    N = C + S.length;
                                                f && N > f.reach && (f.reach = N);
                                                var M = x.prev;
                                                P && (M = c(t, M, P), C += P.length), l(t, M, E);
                                                var L = new o(d, m ? i.tokenize(A, m) : A, y, A);
                                                if (x = c(t, M, L), I && c(t, x, I), E > 1) {
                                                    var R = {
                                                        cause: d + "," + p,
                                                        reach: N
                                                    };
                                                    s(e, t, n, x.prev, C, R), f && R.reach > f.reach && (f.reach = R.reach)
                                                }
                                            }
                                        }
                                    }
                                }
                        }

                        function u() {
                            var e = {
                                    value: null,
                                    prev: null,
                                    next: null
                                },
                                t = {
                                    value: null,
                                    prev: e,
                                    next: null
                                };
                            e.next = t, this.head = e, this.tail = t, this.length = 0
                        }

                        function c(e, t, n) {
                            var r = t.next,
                                i = {
                                    value: n,
                                    prev: t,
                                    next: r
                                };
                            return t.next = i, r.prev = i, e.length++, i
                        }

                        function l(e, t, n) {
                            for (var r = t.next, i = 0; i < n && r !== e.tail; i++) r = r.next;
                            t.next = r, r.prev = t, e.length -= i
                        }

                        function f(e) {
                            var t = [],
                                n = e.head.next;
                            while (n !== e.tail) t.push(n.value), n = n.next;
                            return t
                        }
                        if (e.Prism = i, o.stringify = function e(t, n) {
                                if ("string" == typeof t) return t;
                                if (Array.isArray(t)) {
                                    var r = "";
                                    return t.forEach((function(t) {
                                        r += e(t, n)
                                    })), r
                                }
                                var o = {
                                        type: t.type,
                                        content: e(t.content, n),
                                        tag: "span",
                                        classes: ["token", t.type],
                                        attributes: {},
                                        language: n
                                    },
                                    a = t.alias;
                                a && (Array.isArray(a) ? Array.prototype.push.apply(o.classes, a) : o.classes.push(a)), i.hooks.run("wrap", o);
                                var s = "";
                                for (var u in o.attributes) s += " " + u + '="' + (o.attributes[u] || "").replace(/"/g, "&quot;") + '"';
                                return "<" + o.tag + ' class="' + o.classes.join(" ") + '"' + s + ">" + o.content + "</" + o.tag + ">"
                            }, !e.document) return e.addEventListener ? (i.disableWorkerMessageHandler || e.addEventListener("message", (function(t) {
                            var n = JSON.parse(t.data),
                                r = n.language,
                                o = n.code,
                                a = n.immediateClose;
                            e.postMessage(i.highlight(o, i.languages[r], r)), a && e.close()
                        }), !1), i) : i;
                        var d = i.util.currentScript();

                        function h() {
                            i.manual || i.highlightAll()
                        }
                        if (d && (i.filename = d.src, d.hasAttribute("data-manual") && (i.manual = !0)), !i.manual) {
                            var p = document.readyState;
                            "loading" === p || "interactive" === p && d && d.defer ? document.addEventListener("DOMContentLoaded", h) : window.requestAnimationFrame ? window.requestAnimationFrame(h) : window.setTimeout(h, 16)
                        }
                        return i
                    }(n);
                /**
                 * Prism: Lightweight, robust, elegant syntax highlighting
                 *
                 * @license MIT <https://opensource.org/licenses/MIT>
                 * @author Lea Verou <https://lea.verou.me>
                 * @namespace
                 * @public
                 */
                e.exports && (e.exports = r), "undefined" !== typeof t && (t.Prism = r), r.languages.markup = {
                        comment: {
                            pattern: /<!--(?:(?!<!--)[\s\S])*?-->/,
                            greedy: !0
                        },
                        prolog: {
                            pattern: /<\?[\s\S]+?\?>/,
                            greedy: !0
                        },
                        doctype: {
                            pattern: /<!DOCTYPE(?:[^>"'[\]]|"[^"]*"|'[^']*')+(?:\[(?:[^<"'\]]|"[^"]*"|'[^']*'|<(?!!--)|<!--(?:[^-]|-(?!->))*-->)*\]\s*)?>/i,
                            greedy: !0,
                            inside: {
                                "internal-subset": {
                                    pattern: /(^[^\[]*\[)[\s\S]+(?=\]>$)/,
                                    lookbehind: !0,
                                    greedy: !0,
                                    inside: null
                                },
                                string: {
                                    pattern: /"[^"]*"|'[^']*'/,
                                    greedy: !0
                                },
                                punctuation: /^<!|>$|[[\]]/,
                                "doctype-tag": /^DOCTYPE/i,
                                name: /[^\s<>'"]+/
                            }
                        },
                        cdata: {
                            pattern: /<!\[CDATA\[[\s\S]*?\]\]>/i,
                            greedy: !0
                        },
                        tag: {
                            pattern: /<\/?(?!\d)[^\s>\/=$<%]+(?:\s(?:\s*[^\s>\/=]+(?:\s*=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+(?=[\s>]))|(?=[\s/>])))+)?\s*\/?>/,
                            greedy: !0,
                            inside: {
                                tag: {
                                    pattern: /^<\/?[^\s>\/]+/,
                                    inside: {
                                        punctuation: /^<\/?/,
                                        namespace: /^[^\s>\/:]+:/
                                    }
                                },
                                "special-attr": [],
                                "attr-value": {
                                    pattern: /=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+)/,
                                    inside: {
                                        punctuation: [{
                                            pattern: /^=/,
                                            alias: "attr-equals"
                                        }, {
                                            pattern: /^(\s*)["']|["']$/,
                                            lookbehind: !0
                                        }]
                                    }
                                },
                                punctuation: /\/?>/,
                                "attr-name": {
                                    pattern: /[^\s>\/]+/,
                                    inside: {
                                        namespace: /^[^\s>\/:]+:/
                                    }
                                }
                            }
                        },
                        entity: [{
                            pattern: /&[\da-z]{1,8};/i,
                            alias: "named-entity"
                        }, /&#x?[\da-f]{1,8};/i]
                    }, r.languages.markup["tag"].inside["attr-value"].inside["entity"] = r.languages.markup["entity"], r.languages.markup["doctype"].inside["internal-subset"].inside = r.languages.markup, r.hooks.add("wrap", (function(e) {
                        "entity" === e.type && (e.attributes["title"] = e.content.replace(/&amp;/, "&"))
                    })), Object.defineProperty(r.languages.markup.tag, "addInlined", {
                        value: function(e, t) {
                            var n = {};
                            n["language-" + t] = {
                                pattern: /(^<!\[CDATA\[)[\s\S]+?(?=\]\]>$)/i,
                                lookbehind: !0,
                                inside: r.languages[t]
                            }, n["cdata"] = /^<!\[CDATA\[|\]\]>$/i;
                            var i = {
                                "included-cdata": {
                                    pattern: /<!\[CDATA\[[\s\S]*?\]\]>/i,
                                    inside: n
                                }
                            };
                            i["language-" + t] = {
                                pattern: /[\s\S]+/,
                                inside: r.languages[t]
                            };
                            var o = {};
                            o[e] = {
                                pattern: RegExp(/(<__[^>]*>)(?:<!\[CDATA\[(?:[^\]]|\](?!\]>))*\]\]>|(?!<!\[CDATA\[)[\s\S])*?(?=<\/__>)/.source.replace(/__/g, (function() {
                                    return e
                                })), "i"),
                                lookbehind: !0,
                                greedy: !0,
                                inside: i
                            }, r.languages.insertBefore("markup", "cdata", o)
                        }
                    }), Object.defineProperty(r.languages.markup.tag, "addAttribute", {
                        value: function(e, t) {
                            r.languages.markup.tag.inside["special-attr"].push({
                                pattern: RegExp(/(^|["'\s])/.source + "(?:" + e + ")" + /\s*=\s*(?:"[^"]*"|'[^']*'|[^\s'">=]+(?=[\s>]))/.source, "i"),
                                lookbehind: !0,
                                inside: {
                                    "attr-name": /^[^\s=]+/,
                                    "attr-value": {
                                        pattern: /=[\s\S]+/,
                                        inside: {
                                            value: {
                                                pattern: /(^=\s*(["']|(?!["'])))\S[\s\S]*(?=\2$)/,
                                                lookbehind: !0,
                                                alias: [t, "language-" + t],
                                                inside: r.languages[t]
                                            },
                                            punctuation: [{
                                                pattern: /^=/,
                                                alias: "attr-equals"
                                            }, /"|'/]
                                        }
                                    }
                                }
                            })
                        }
                    }), r.languages.html = r.languages.markup, r.languages.mathml = r.languages.markup, r.languages.svg = r.languages.markup, r.languages.xml = r.languages.extend("markup", {}), r.languages.ssml = r.languages.xml, r.languages.atom = r.languages.xml, r.languages.rss = r.languages.xml,
                    function(e) {
                        var t = /(?:"(?:\\(?:\r\n|[\s\S])|[^"\\\r\n])*"|'(?:\\(?:\r\n|[\s\S])|[^'\\\r\n])*')/;
                        e.languages.css = {
                            comment: /\/\*[\s\S]*?\*\//,
                            atrule: {
                                pattern: RegExp("@[\\w-](?:" + /[^;{\s"']|\s+(?!\s)/.source + "|" + t.source + ")*?" + /(?:;|(?=\s*\{))/.source),
                                inside: {
                                    rule: /^@[\w-]+/,
                                    "selector-function-argument": {
                                        pattern: /(\bselector\s*\(\s*(?![\s)]))(?:[^()\s]|\s+(?![\s)])|\((?:[^()]|\([^()]*\))*\))+(?=\s*\))/,
                                        lookbehind: !0,
                                        alias: "selector"
                                    },
                                    keyword: {
                                        pattern: /(^|[^\w-])(?:and|not|only|or)(?![\w-])/,
                                        lookbehind: !0
                                    }
                                }
                            },
                            url: {
                                pattern: RegExp("\\burl\\((?:" + t.source + "|" + /(?:[^\\\r\n()"']|\\[\s\S])*/.source + ")\\)", "i"),
                                greedy: !0,
                                inside: {
                                    function: /^url/i,
                                    punctuation: /^\(|\)$/,
                                    string: {
                                        pattern: RegExp("^" + t.source + "$"),
                                        alias: "url"
                                    }
                                }
                            },
                            selector: {
                                pattern: RegExp("(^|[{}\\s])[^{}\\s](?:[^{};\"'\\s]|\\s+(?![\\s{])|" + t.source + ")*(?=\\s*\\{)"),
                                lookbehind: !0
                            },
                            string: {
                                pattern: t,
                                greedy: !0
                            },
                            property: {
                                pattern: /(^|[^-\w\xA0-\uFFFF])(?!\s)[-_a-z\xA0-\uFFFF](?:(?!\s)[-\w\xA0-\uFFFF])*(?=\s*:)/i,
                                lookbehind: !0
                            },
                            important: /!important\b/i,
                            function: {
                                pattern: /(^|[^-a-z0-9])[-a-z0-9]+(?=\()/i,
                                lookbehind: !0
                            },
                            punctuation: /[(){};:,]/
                        }, e.languages.css["atrule"].inside.rest = e.languages.css;
                        var n = e.languages.markup;
                        n && (n.tag.addInlined("style", "css"), n.tag.addAttribute("style", "css"))
                    }(r), r.languages.clike = {
                        comment: [{
                            pattern: /(^|[^\\])\/\*[\s\S]*?(?:\*\/|$)/,
                            lookbehind: !0,
                            greedy: !0
                        }, {
                            pattern: /(^|[^\\:])\/\/.*/,
                            lookbehind: !0,
                            greedy: !0
                        }],
                        string: {
                            pattern: /(["'])(?:\\(?:\r\n|[\s\S])|(?!\1)[^\\\r\n])*\1/,
                            greedy: !0
                        },
                        "class-name": {
                            pattern: /(\b(?:class|extends|implements|instanceof|interface|new|trait)\s+|\bcatch\s+\()[\w.\\]+/i,
                            lookbehind: !0,
                            inside: {
                                punctuation: /[.\\]/
                            }
                        },
                        keyword: /\b(?:break|catch|continue|do|else|finally|for|function|if|in|instanceof|new|null|return|throw|try|while)\b/,
                        boolean: /\b(?:false|true)\b/,
                        function: /\b\w+(?=\()/,
                        number: /\b0x[\da-f]+\b|(?:\b\d+(?:\.\d*)?|\B\.\d+)(?:e[+-]?\d+)?/i,
                        operator: /[<>]=?|[!=]=?=?|--?|\+\+?|&&?|\|\|?|[?*/~^%]/,
                        punctuation: /[{}[\];(),.:]/
                    }, r.languages.javascript = r.languages.extend("clike", {
                        "class-name": [r.languages.clike["class-name"], {
                            pattern: /(^|[^$\w\xA0-\uFFFF])(?!\s)[_$A-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\.(?:constructor|prototype))/,
                            lookbehind: !0
                        }],
                        keyword: [{
                            pattern: /((?:^|\})\s*)catch\b/,
                            lookbehind: !0
                        }, {
                            pattern: /(^|[^.]|\.\.\.\s*)\b(?:as|assert(?=\s*\{)|async(?=\s*(?:function\b|\(|[$\w\xA0-\uFFFF]|$))|await|break|case|class|const|continue|debugger|default|delete|do|else|enum|export|extends|finally(?=\s*(?:\{|$))|for|from(?=\s*(?:['"]|$))|function|(?:get|set)(?=\s*(?:[#\[$\w\xA0-\uFFFF]|$))|if|implements|import|in|instanceof|interface|let|new|null|of|package|private|protected|public|return|static|super|switch|this|throw|try|typeof|undefined|var|void|while|with|yield)\b/,
                            lookbehind: !0
                        }],
                        function: /#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*(?:\.\s*(?:apply|bind|call)\s*)?\()/,
                        number: {
                            pattern: RegExp(/(^|[^\w$])/.source + "(?:" + /NaN|Infinity/.source + "|" + /0[bB][01]+(?:_[01]+)*n?/.source + "|" + /0[oO][0-7]+(?:_[0-7]+)*n?/.source + "|" + /0[xX][\dA-Fa-f]+(?:_[\dA-Fa-f]+)*n?/.source + "|" + /\d+(?:_\d+)*n/.source + "|" + /(?:\d+(?:_\d+)*(?:\.(?:\d+(?:_\d+)*)?)?|\.\d+(?:_\d+)*)(?:[Ee][+-]?\d+(?:_\d+)*)?/.source + ")" + /(?![\w$])/.source),
                            lookbehind: !0
                        },
                        operator: /--|\+\+|\*\*=?|=>|&&=?|\|\|=?|[!=]==|<<=?|>>>?=?|[-+*/%&|^!=<>]=?|\.{3}|\?\?=?|\?\.?|[~:]/
                    }), r.languages.javascript["class-name"][0].pattern = /(\b(?:class|extends|implements|instanceof|interface|new)\s+)[\w.\\]+/, r.languages.insertBefore("javascript", "keyword", {
                        regex: {
                            pattern: RegExp(/((?:^|[^$\w\xA0-\uFFFF."'\])\s]|\b(?:return|yield))\s*)/.source + /\//.source + "(?:" + /(?:\[(?:[^\]\\\r\n]|\\.)*\]|\\.|[^/\\\[\r\n])+\/[dgimyus]{0,7}/.source + "|" + /(?:\[(?:[^[\]\\\r\n]|\\.|\[(?:[^[\]\\\r\n]|\\.|\[(?:[^[\]\\\r\n]|\\.)*\])*\])*\]|\\.|[^/\\\[\r\n])+\/[dgimyus]{0,7}v[dgimyus]{0,7}/.source + ")" + /(?=(?:\s|\/\*(?:[^*]|\*(?!\/))*\*\/)*(?:$|[\r\n,.;:})\]]|\/\/))/.source),
                            lookbehind: !0,
                            greedy: !0,
                            inside: {
                                "regex-source": {
                                    pattern: /^(\/)[\s\S]+(?=\/[a-z]*$)/,
                                    lookbehind: !0,
                                    alias: "language-regex",
                                    inside: r.languages.regex
                                },
                                "regex-delimiter": /^\/|\/$/,
                                "regex-flags": /^[a-z]+$/
                            }
                        },
                        "function-variable": {
                            pattern: /#?(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*[=:]\s*(?:async\s*)?(?:\bfunction\b|(?:\((?:[^()]|\([^()]*\))*\)|(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*)\s*=>))/,
                            alias: "function"
                        },
                        parameter: [{
                            pattern: /(function(?:\s+(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*)?\s*\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\))/,
                            lookbehind: !0,
                            inside: r.languages.javascript
                        }, {
                            pattern: /(^|[^$\w\xA0-\uFFFF])(?!\s)[_$a-z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*=>)/i,
                            lookbehind: !0,
                            inside: r.languages.javascript
                        }, {
                            pattern: /(\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\)\s*=>)/,
                            lookbehind: !0,
                            inside: r.languages.javascript
                        }, {
                            pattern: /((?:\b|\s|^)(?!(?:as|async|await|break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|finally|for|from|function|get|if|implements|import|in|instanceof|interface|let|new|null|of|package|private|protected|public|return|set|static|super|switch|this|throw|try|typeof|undefined|var|void|while|with|yield)(?![$\w\xA0-\uFFFF]))(?:(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*\s*)\(\s*|\]\s*\(\s*)(?!\s)(?:[^()\s]|\s+(?![\s)])|\([^()]*\))+(?=\s*\)\s*\{)/,
                            lookbehind: !0,
                            inside: r.languages.javascript
                        }],
                        constant: /\b[A-Z](?:[A-Z_]|\dx?)*\b/
                    }), r.languages.insertBefore("javascript", "string", {
                        hashbang: {
                            pattern: /^#!.*/,
                            greedy: !0,
                            alias: "comment"
                        },
                        "template-string": {
                            pattern: /`(?:\\[\s\S]|\$\{(?:[^{}]|\{(?:[^{}]|\{[^}]*\})*\})+\}|(?!\$\{)[^\\`])*`/,
                            greedy: !0,
                            inside: {
                                "template-punctuation": {
                                    pattern: /^`|`$/,
                                    alias: "string"
                                },
                                interpolation: {
                                    pattern: /((?:^|[^\\])(?:\\{2})*)\$\{(?:[^{}]|\{(?:[^{}]|\{[^}]*\})*\})+\}/,
                                    lookbehind: !0,
                                    inside: {
                                        "interpolation-punctuation": {
                                            pattern: /^\$\{|\}$/,
                                            alias: "punctuation"
                                        },
                                        rest: r.languages.javascript
                                    }
                                },
                                string: /[\s\S]+/
                            }
                        },
                        "string-property": {
                            pattern: /((?:^|[,{])[ \t]*)(["'])(?:\\(?:\r\n|[\s\S])|(?!\2)[^\\\r\n])*\2(?=\s*:)/m,
                            lookbehind: !0,
                            greedy: !0,
                            alias: "property"
                        }
                    }), r.languages.insertBefore("javascript", "operator", {
                        "literal-property": {
                            pattern: /((?:^|[,{])[ \t]*)(?!\s)[_$a-zA-Z\xA0-\uFFFF](?:(?!\s)[$\w\xA0-\uFFFF])*(?=\s*:)/m,
                            lookbehind: !0,
                            alias: "property"
                        }
                    }), r.languages.markup && (r.languages.markup.tag.addInlined("script", "javascript"), r.languages.markup.tag.addAttribute(/on(?:abort|blur|change|click|composition(?:end|start|update)|dblclick|error|focus(?:in|out)?|key(?:down|up)|load|mouse(?:down|enter|leave|move|out|over|up)|reset|resize|scroll|select|slotchange|submit|unload|wheel)/.source, "javascript")), r.languages.js = r.languages.javascript,
                    function() {
                        if ("undefined" !== typeof r && "undefined" !== typeof document) {
                            Element.prototype.matches || (Element.prototype.matches = Element.prototype.msMatchesSelector || Element.prototype.webkitMatchesSelector);
                            var e = "Loading…",
                                t = function(e, t) {
                                    return "✖ Error " + e + " while fetching file: " + t
                                },
                                n = "✖ Error: File does not exist or is empty",
                                i = {
                                    js: "javascript",
                                    py: "python",
                                    rb: "ruby",
                                    ps1: "powershell",
                                    psm1: "powershell",
                                    sh: "bash",
                                    bat: "batch",
                                    h: "c",
                                    tex: "latex"
                                },
                                o = "data-src-status",
                                a = "loading",
                                s = "loaded",
                                u = "failed",
                                c = "pre[data-src]:not([" + o + '="' + s + '"]):not([' + o + '="' + a + '"])';
                            r.hooks.add("before-highlightall", (function(e) {
                                e.selector += ", " + c
                            })), r.hooks.add("before-sanity-check", (function(t) {
                                var n = t.element;
                                if (n.matches(c)) {
                                    t.code = "", n.setAttribute(o, a);
                                    var l = n.appendChild(document.createElement("CODE"));
                                    l.textContent = e;
                                    var h = n.getAttribute("data-src"),
                                        p = t.language;
                                    if ("none" === p) {
                                        var v = (/\.(\w+)$/.exec(h) || [, "none"])[1];
                                        p = i[v] || v
                                    }
                                    r.util.setLanguage(l, p), r.util.setLanguage(n, p);
                                    var m = r.plugins.autoloader;
                                    m && m.loadLanguages(p), f(h, (function(e) {
                                        n.setAttribute(o, s);
                                        var t = d(n.getAttribute("data-range"));
                                        if (t) {
                                            var i = e.split(/\r\n?|\n/g),
                                                a = t[0],
                                                u = null == t[1] ? i.length : t[1];
                                            a < 0 && (a += i.length), a = Math.max(0, Math.min(a - 1, i.length)), u < 0 && (u += i.length), u = Math.max(0, Math.min(u, i.length)), e = i.slice(a, u).join("\n"), n.hasAttribute("data-start") || n.setAttribute("data-start", String(a + 1))
                                        }
                                        l.textContent = e, r.highlightElement(l)
                                    }), (function(e) {
                                        n.setAttribute(o, u), l.textContent = e
                                    }))
                                }
                            })), r.plugins.fileHighlight = {
                                highlight: function(e) {
                                    for (var t, n = (e || document).querySelectorAll(c), i = 0; t = n[i++];) r.highlightElement(t)
                                }
                            };
                            var l = !1;
                            r.fileHighlight = function() {
                                l || (console.warn("Prism.fileHighlight is deprecated. Use `Prism.plugins.fileHighlight.highlight` instead."), l = !0), r.plugins.fileHighlight.highlight.apply(this, arguments)
                            }
                        }

                        function f(e, r, i) {
                            var o = new XMLHttpRequest;
                            o.open("GET", e, !0), o.onreadystatechange = function() {
                                4 == o.readyState && (o.status < 400 && o.responseText ? r(o.responseText) : o.status >= 400 ? i(t(o.status, o.statusText)) : i(n))
                            }, o.send(null)
                        }

                        function d(e) {
                            var t = /^\s*(\d+)\s*(?:(,)\s*(?:(\d+)\s*)?)?$/.exec(e || "");
                            if (t) {
                                var n = Number(t[1]),
                                    r = t[2],
                                    i = t[3];
                                return r ? i ? [n, Number(i)] : [n, void 0] : [n, n]
                            }
                        }
                    }()
            }).call(this, n("./c8ba.js"))
        
