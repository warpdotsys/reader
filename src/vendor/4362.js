// chunk-vendors webpack 模块 4362（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = (typeof window !== 'undefined' && window.__vmRequire) ? window.__vmRequire : require;

            t.nextTick = function(e) {
                    var t = Array.prototype.slice.call(arguments);
                    t.shift(), setTimeout((function() {
                        e.apply(null, t)
                    }), 0)
                }, t.platform = t.arch = t.execPath = t.title = "browser", t.pid = 1, t.browser = !0, t.env = {}, t.argv = [], t.binding = function(e) {
                    throw new Error("No such module. (Possibly not yet loaded)")
                },
                function() {
                    var e, r = "/";
                    t.cwd = function() {
                        return r
                    }, t.chdir = function(t) {
                        e || (e = n("./df7c.js")), r = e.resolve(t, r)
                    }
                }(), t.exit = t.kill = t.umask = t.dlopen = t.uptime = t.memoryUsage = t.uvCounters = function() {}, t.features = {}
        
