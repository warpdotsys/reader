// chunk-vendors webpack 模块 597f（逐字切片，形参 e, t）
const e = module;
const t = module.exports;

            e.exports = function(e, t, n, r) {
                var i, o = 0;

                function a() {
                    var a = this,
                        s = Number(new Date) - o,
                        u = arguments;

                    function c() {
                        o = Number(new Date), n.apply(a, u)
                    }

                    function l() {
                        i = void 0
                    }
                    r && !i && c(), i && clearTimeout(i), void 0 === r && s > e ? c() : !0 !== t && (i = setTimeout(r ? l : c, void 0 === r ? e - s : e))
                }
                return "boolean" !== typeof t && (r = n, n = t, t = void 0), a
            }
        
