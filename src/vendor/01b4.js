// chunk-vendors webpack 模块 01b4（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            "use strict";
            var r = function() {
                this.head = null, this.tail = null
            };
            r.prototype = {
                add: function(e) {
                    var t = {
                            item: e,
                            next: null
                        },
                        n = this.tail;
                    n ? n.next = t : this.head = t, this.tail = t
                },
                get: function() {
                    var e = this.head;
                    if (e) {
                        var t = this.head = e.next;
                        return null === t && (this.tail = null), e.item
                    }
                }
            }, e.exports = r
        
