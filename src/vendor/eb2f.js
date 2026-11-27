// chunk-vendors webpack 模块 eb2f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            (function() {
                if (!("onload" in XMLHttpRequest.prototype) || navigator.standalone) return;
                const e = !1,
                    t = ["standalone", "fullscreen", "minimal-ui"],
                    n = "#f8f9fa",
                    r = 24,
                    i = "HelveticaNeue-CondensedBold",
                    o = 128,
                    a = 48,
                    s = 20,
                    u = 120,
                    c = navigator.userAgent || "",
                    l = navigator.vendor && -1 !== navigator.vendor.indexOf("Apple"),
                    f = l && (-1 !== c.indexOf("Mobile/") || "standalone" in navigator) || e,
                    d = Boolean(c.match(/(MSIE |Edge\/|Trident\/)/)),
                    h = "undefined" !== typeof Windows;
                let p, v;
                try {
                    v = sessionStorage
                } catch (I) {}

                function m(e) {
                    try {
                        return document.head.querySelector(e)
                    } catch (I) {
                        return null
                    }
                }

                function g(e, t) {
                    const n = "__pwacompat_" + e;
                    return void 0 !== t && (v[n] = t), v[n]
                }

                function b() {
                    p = m('link[rel="manifest"]');
                    const e = p ? p.href : "";
                    if (!e) throw 'can\'t find <link rel="manifest" href=".." />\'';
                    const t = y([e, location]),
                        n = g("manifest");
                    if (n) {
                        try {
                            const e = JSON.parse(n);
                            C(e, t)
                        } catch (i) {
                            console.warn("PWACompat error", i)
                        }
                        return
                    }
                    const r = new XMLHttpRequest;
                    r.open("GET", e), r.withCredentials = "use-credentials" === p.getAttribute("crossorigin"), r.onload = () => {
                        try {
                            const e = JSON.parse(r.responseText);
                            g("manifest", r.responseText), C(e, t)
                        } catch (i) {
                            console.warn("PWACompat error", i)
                        }
                    }, r.send(null)
                }

                function y(e) {
                    for (let t = 0; t < e.length; ++t) {
                        const n = e[t];
                        try {
                            return new URL("", n), e => new URL(e || "", n).toString()
                        } catch (I) {}
                    }
                    return e => e || ""
                }

                function _(e, t, n) {
                    if (m(e + n)) return;
                    const r = document.createElement(e);
                    for (const i in t) r.setAttribute(i, t[i]);
                    return document.head.appendChild(r), r
                }

                function w(e, t) {
                    t && (!0 === t && (t = "yes"), _("meta", {
                        name: e,
                        content: t
                    }, `[name="${e}"]`))
                }

                function x(e) {
                    const t = e.sizes.split(/\s+/g).map(e => "any" === e ? 1 / 0 : parseInt(e, 10) || 0);
                    return {
                        src: e.src,
                        type: e.type,
                        sizes: e.sizes,
                        largestSize: Math.max.apply(null, t),
                        purpose: e.purpose ? e.purpose.split(/\s+/g) : ["any"]
                    }
                }

                function C(c, l) {
                    const h = (c["icons"] || []).map(x).sort((e, t) => t.largestSize - e.largestSize),
                        v = h.filter(e => e.purpose.indexOf("any") > -1),
                        b = h.filter(e => e.purpose.indexOf("maskable") > -1),
                        y = (b.length > 0 ? b : v).map(e => {
                            const t = {
                                    rel: "icon",
                                    href: l(e["src"]),
                                    sizes: e["sizes"]
                                },
                                n = `[sizes="${e["sizes"]}"]`;
                            if (_("link", t, '[rel="icon"]' + n), f && !(e.largestSize < u)) return t["rel"] = "apple-touch-icon", _("link", t, '[rel="apple-touch-icon"]' + n)
                        }).filter(Boolean),
                        C = m('meta[name="viewport"]'),
                        k = C && C.content || "",
                        T = Boolean(k.match(/\bviewport-fit\s*=\s*cover\b/)),
                        j = c["display"],
                        N = -1 !== t.indexOf(j);
                    if (w("mobile-web-app-capable", N), E(c["theme_color"] || "black", T), d) {
                        w("application-name", c["short_name"]), w("msapplication-tooltip", c["description"]), w("msapplication-starturl", l(c["start_url"] || ".")), w("msapplication-navbutton-color", c["theme_color"]);
                        const e = v[0];
                        e && w("msapplication-TileImage", l(e["src"])), w("msapplication-TileColor", c["background_color"])
                    }
                    if (w("theme-color", c["theme_color"]), !f) {
                        const e = O(c["orientation"]);
                        return w("x5-orientation", e), w("screen-orientation", e), void("fullscreen" === j ? (w("x5-fullscreen", "true"), w("full-screen", "yes")) : N && (w("x5-page-mode", "app"), w("browsermode", "application")))
                    }
                    const M = c["background_color"] || n,
                        L = $(M),
                        R = S(c["related_applications"]);

                    function F(t, n, u, l) {
                        const f = window.devicePixelRatio,
                            d = P({
                                width: t * f,
                                height: n * f
                            });
                        if (d.scale(f, f), d.fillStyle = M, d.fillRect(0, 0, t, n), d.translate(t / 2, (n - s) / 2), l) {
                            let e = l.width / f,
                                t = l.height / f;
                            t > o && (e /= t / o, t = o), e >= a && t >= a && (d.drawImage(l, e / -2, t / -2, e, t), d.translate(0, t / 2 + s))
                        }
                        d.fillStyle = L ? "white" : "black", d.font = `${r}px ${i}`;
                        const h = getComputedStyle(p);
                        d.font = h.getPropertyValue("--pwacompat-splash-font");
                        const v = c["name"] || c["short_name"] || document.title,
                            m = d.measureText(v),
                            g = m.actualBoundingBoxAscent || r;
                        if (d.translate(0, g), m.width < .8 * t) d.fillText(v, m.width / -2, 0);
                        else {
                            const e = v.split(/\s+/g);
                            for (let n = 1; n <= e.length; ++n) {
                                const r = e.slice(0, n).join(" "),
                                    i = d.measureText(r).width;
                                (n === e.length || i > .6 * t) && (d.fillText(r, i / -2, 0), d.translate(0, 1.2 * g), e.splice(0, n), n = 0)
                            }
                        }
                        return () => {
                            const t = d.canvas.toDataURL();
                            if (e) {
                                const e = document.createElement("img");
                                e.src = t, document.body.append(e)
                            }
                            return D(u, t), t
                        }
                    }

                    function D(e, t) {
                        const n = document.createElement("link");
                        n.setAttribute("rel", "apple-touch-startup-image"), n.setAttribute("media", `(orientation: ${e})`), n.setAttribute("href", t), document.head.appendChild(n)
                    }
                    R && w("apple-itunes-app", "app-id=" + R), w("apple-mobile-web-app-capable", N), w("apple-mobile-web-app-title", c["short_name"] || c["name"]);
                    const B = g("iOS");
                    if (!e && B) try {
                        const e = JSON.parse(B);
                        return D("portrait", e["p"]), D("landscape", e["l"]), void y.forEach(t => {
                            const n = e["i"][t.href];
                            n && (t.href = n)
                        })
                    } catch (I) {}
                    const z = {
                        i: {}
                    };

                    function V(e, t) {
                        const n = window.screen,
                            r = F(n.width, n.height, "portrait", e),
                            i = F(n.height, n.width, "landscape", e);
                        setTimeout(() => {
                            z["p"] = r(), setTimeout(() => {
                                z["l"] = i(), t()
                            }, 10)
                        }, 10)
                    }

                    function H(e) {
                        let t = y.length + 1;
                        const n = () => {
                            --t || e()
                        };
                        n(), y.forEach(e => {
                            const t = new Image;
                            t.crossOrigin = "anonymous", t.onerror = n, t.onload = () => {
                                t.onload = null, e.href = A(t, M, !0), z["i"][t.src] = e.href, n()
                            }, t.src = e.href
                        })
                    }

                    function W() {
                        g("iOS", JSON.stringify(z))
                    }

                    function q() {
                        const e = y.shift();
                        if (!e) return void V(null, W);
                        const t = new Image;
                        t.crossOrigin = "anonymous", t.onerror = () => {
                            q()
                        }, t.onload = () => {
                            t.onload = null, V(t, () => {
                                const n = c["background_color"] && A(t, M);
                                n ? (e.href = n, z["i"][t.src] = n, H(W)) : W()
                            })
                        }, t.src = e.href
                    }
                    q()
                }

                function S(e) {
                    let t;
                    return (e || []).filter(e => "itunes" === e["platform"]).forEach(e => {
                        if (e["id"]) t = e["id"];
                        else {
                            const n = e["url"].match(/id(\d+)/);
                            n && (t = n[1])
                        }
                    }), t
                }

                function O(e) {
                    const t = String(e || "").substr(0, 3);
                    return {
                        por: "portrait",
                        lan: "landscape"
                    } [t] || ""
                }

                function E(e, t) {
                    if (!f && !h) return;
                    const n = $(e);
                    if (f) {
                        const e = t ? "black-translucent" : n ? "black" : "default";
                        w("apple-mobile-web-app-status-bar-style", e)
                    } else {
                        const t = k();
                        if (!t) return;
                        const r = n ? 255 : 0;
                        t.foregroundColor = {
                            r: r,
                            g: r,
                            b: r,
                            a: 255
                        }, t.backgroundColor = T(e)
                    }
                }

                function k() {
                    try {
                        return Windows.UI.ViewManagement.ApplicationView.getForCurrentView().titleBar
                    } catch (I) {}
                }

                function T(e) {
                    const t = j(e);
                    return {
                        r: t[0],
                        g: t[1],
                        b: t[2],
                        a: t[3]
                    }
                }

                function j(e) {
                    const t = P();
                    return t.fillStyle = e, t.fillRect(0, 0, 1, 1), t.getImageData(0, 0, 1, 1).data || []
                }

                function $(e) {
                    const t = j(e),
                        n = t.map(e => {
                            const t = e / 255;
                            return t < .03928 ? t / 12.92 : Math.pow((t + .055) / 1.055, 2.4)
                        }),
                        r = .2126 * n[0] + .7152 * n[1] + .0722 * n[2],
                        i = Math.abs(1.05 / (r + .05));
                    return i > 3
                }

                function A(e, t, n = !1) {
                    const r = P(e);
                    if (r.drawImage(e, 0, 0), !n) {
                        const e = r.getImageData(0, 0, 1, 1);
                        if (255 === e.data[3]) return
                    }
                    return r.globalCompositeOperation = "destination-over", r.fillStyle = t, r.fillRect(0, 0, e.width, e.height), r.canvas.toDataURL()
                }

                function P({
                    width: e,
                    height: t
                } = {
                    width: 1,
                    height: 1
                }) {
                    const n = document.createElement("canvas");
                    return n.width = e, n.height = t, n.getContext("2d")
                }
                v = v || {}, "complete" === document.readyState ? b() : window.addEventListener("load", b)
            })()
        
