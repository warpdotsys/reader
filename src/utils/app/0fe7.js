// 恢复自 reader-pro-3.2.14 前端产物 beauty/app.js 的 webpack 模块 0fe7（逐字切片，形参 e, t, o）
const e = module;
const t = module.exports;
const o = require;

        "use strict";
        var n = function() {
                var e = this,
                    t = e._self._c;
                return t("div", {
                    staticClass: "book-kind",
                    domProps: {
                        innerHTML: e._s(e.renderBookKind(e.book))
                    }
                })
            },
            r = [],
            a = (o("4de4"), o("a15b"), o("d81d"), o("e9f5"), o("910d"), o("ab43"), o("d3b7"), {
                name: "BookKind",
                props: ["book"],
                methods: {
                    renderBookKind: function() {
                        if (!this.book) return "";
                        var e = this.book.wordCount + "," + this.book.kind;
                        if (!e) return "";
                        var t = e.split(",");
                        return t.filter((function(e) {
                            return e && "undefined" !== e
                        })).map((function(e) {
                            return '<span class="small-tag">'.concat(e, "</span>")
                        })).join("")
                    }
                }
            }),
            i = a,
            s = (o("c2d5"), o("2877")),
            c = Object(s["a"])(i, n, r, !1, null, null, null);
        t["a"] = c.exports
    
