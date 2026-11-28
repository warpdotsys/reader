<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=setting scopeId=44d3706e；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="setting-wrapper" :class="{night: isNight, day: !isNight}"><div class="navigation-wrapper" :class='[navigationClass, isWebApp && !isNight ? "status-bar-light-bg" : ""]' :style="navigationStyle" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd"><div class="navigation-inner-wrapper"><div class="navigation-title"> 阅读 <span class="version-text">{{ $store.state.version }}</span></div><div class="navigation-sub-title"> 清风不识字，何故乱翻书 </div></div></div><div ref='"shelfWrapper"' class="setting-zone-wrapper" :class='isWebApp && !isNight ? "status-bar-light-bg" : ""' @click="hideMenu"><div class="setting-zone-title"><template v-if="$store.getters.isNormalPage && collapseMenu"><i class="el-icon-menu" @click="(i) => {
                                return i.stopPropagation(), toggleMenu.apply(null, arguments)
                            }" /></template> 设置 <div class="title-btn" @click="backToShelf"> 书架 </div></div><div class="setting-form-wrapper"><el-form :model="windowConfigForm" :label-width='collapseMenu ? "auto" : "95px"'><el-form-item label="Java路径"><el-input v-model="windowConfigForm.javaPath" /><el-link style="line-height: 25px" type="warning" href="https://www.123pan.com/s/Xcl8Vv-Vjkxv.html" target="__blank" :underline="false">注意！如果已安装java8及以上环境，请在此处填入已安装的java程序的路径，如 C:\jdk\bin\java.exe。如果没有安装java8及以上环境，请点此去下载安装</el-link></el-form-item><el-form-item label="服务端口"><el-input v-model="windowConfigForm.serverPort" /></el-form-item><el-form-item label="服务配置"><el-input type="textarea" rows="5" v-model="windowConfigForm.serverConfig" /><el-link :underline="false" type="warning">JSON对象格式，注意不要使用中文符号 “，等</el-link></el-form-item><el-form-item label="jvm配置"><el-input type="textarea" rows="5" v-model="windowConfigForm.jvmConfig" /><el-link :underline="false" type="warning">JSON数组格式，注意不要使用中文符号 “，等</el-link></el-form-item><el-form-item label="窗口大小"><el-checkbox v-model="windowConfigForm.setWindowSize">启动时设置窗口大小</el-checkbox><el-checkbox v-model="windowConfigForm.rememberSize">自动记住窗口大小</el-checkbox></el-form-item><el-form-item label="窗口宽度" v-show="windowConfigForm.setWindowSize"><el-input v-model="windowConfigForm.width" /></el-form-item><el-form-item label="窗口高度" v-show="windowConfigForm.setWindowSize"><el-input v-model="windowConfigForm.height" /></el-form-item><el-form-item label="窗口位置"><el-checkbox v-model="windowConfigForm.setWindowPosition">启动时设置窗口位置</el-checkbox><el-checkbox v-model="windowConfigForm.rememberPosition">自动记住窗口位置</el-checkbox></el-form-item><el-form-item label="横坐标" v-show="windowConfigForm.setWindowPosition"><el-input v-model="windowConfigForm.positionX" /></el-form-item><el-form-item label="纵坐标" v-show="windowConfigForm.setWindowPosition"><el-input v-model="windowConfigForm.positionY" /></el-form-item><el-form-item label="页面链接"><el-input v-model="windowConfigForm.windowUrl" /><el-link :underline="false" type="warning">如无必要，请勿设置</el-link></el-form-item><el-form-item label="调试页面"><el-switch active-color="#13ce66" inactive-color="#ff4949" :active-value="true" :inactive-value="false" v-model="windowConfigForm.debug" /></el-form-item><div style="text-align: center"><el-button type="primary" @click="saveConfig">保存并重启</el-button></div></el-form></div></div></div>
</template>

<script>
import { merge as __merge } from '@/utils/merge'
import { regeneratorMark, regeneratorWrap } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import { mapState } from 'vuex'
// 依赖的 webpack 模块 id 见同目录 _meta.json 与 docs/FRONTEND-RECONSTRUCTION.md
export default {
  data: function() {
    return {
    showNavigation: !1,
    navigationClass: "",
    navigationStyle: {},
    windowConfigForm: {
    javaPath: "",
    serverPort: 8080,
    width: 1280,
    height: 800,
    positionX: 0,
    positionY: 0,
    rememberSize: !0,
    rememberPosition: !1,
    setWindowSize: !0,
    setWindowPosition: !1,
    serverConfig: "{\n\n}",
    jvmConfig: "[\n\n]"
    }
    }
    },
    created: function() {
    var e = this;
    window.__TAURI__ && window.__TAURI__.invoke && window.__TAURI__.invoke("get_config").then((function(i) {
    i.serverConfig = JSON.stringify(i.serverConfig, null, 4), i.jvmConfig = JSON.stringify(i.jvmConfig, null, 4) || "[]", e.windowConfigForm = __merge({}, i)
    }))["catch"]((function() {})), window.settingPage = this, this.navigationClass = this.collapseMenu && !this.showNavigation ? "navigation-hidden" : ""
    },
    computed: __merge({}, mapState(["isNight", "collapseMenu"])),
    methods: {
    handleTouchStart: function(e) {
    this.lastTouch = !1, this.lastMoveX = !1, this.touchMoveTimes = 0, e.touches && e.touches[0] && e.touches[0].clientX > 20 && e.touches[0].clientX < window.innerWidth - 20 && e.touches[0].clientY > 20 && e.touches[0].clientY < window.innerHeight - 20 && (this.lastTouch = e.touches[0])
    },
    handleTouchMove: function(e) {
    if (e.touches && e.touches[0] && this.lastTouch && this.collapseMenu) {
    var i = e.touches[0].clientX - this.lastTouch.clientX,
    n = e.touches[0].clientY - this.lastTouch.clientY;
    if (Math.abs(n) > Math.abs(i)) return this.navigationStyle = {}, void(this.lastMoveX = 0);
    e.preventDefault(), e.stopPropagation(), !this.showNavigation && i > 0 && i <= 270 ? (this.touchMoveTimes % 3 === 0 && (this.navigationStyle = {
    marginLeft: i - 270 + "px"
    }), this.lastMoveX = i) : this.showNavigation && i < 0 && i >= -270 && (this.touchMoveTimes % 3 === 0 && (this.navigationStyle = {
    marginLeft: i + "px"
    }), this.lastMoveX = i), this.touchMoveTimes++
    }
    },
    handleTouchEnd: function() {
    this.collapseMenu && (this.lastMoveX > 0 ? (this.showNavigation = !0, this.navigationStyle = {}) : this.lastMoveX < 0 && (this.showNavigation = !1, this.navigationStyle = {}))
    },
    hideMenu: function() {
    this.$store.getters.isNormalPage && this.collapseMenu && (this.showNavigation = !1)
    },
    toggleMenu: function() {
    this.collapseMenu && (this.showNavigation = !this.showNavigation)
    },
    backToShelf: function() {
    var e = this;
    return asyncToGenerator(regeneratorRuntime.mark((function i() {
    var n;
    return regeneratorRuntime.wrap((function(i) {
    while (1) switch (i.prev = i.next) {
    case 0:
    if (!window.__TAURI__ || !window.__TAURI__.invoke) {
    i.next = 6;
    break
    }
    return i.next = 3, window.__TAURI__.invoke("check_java", {
    javaPath: e.windowConfigForm.javaPath || ""
    })["catch"]((function(i) {
    return e.showJavaConfirm((e.windowConfigForm.javaPath ? "未检测到 Java 运行环境，请确认 Java 路径设置是否正确 " : "未检测到 Java 运行环境，请确认是否已安装Java8及以上运行环境 ") + i), !1
    }));
    case 3:
    if (n = i.sent, n) {
    i.next = 6;
    break
    }
    return i.abrupt("return");
    case 6:
    e.$router.replace("/");
    case 7:
    case "end":
    return i.stop()
    }
    }), i)
    })))()
    },
    showJavaConfirm: function(e) {
    var i = this;
    return asyncToGenerator(regeneratorRuntime.mark((function n() {
    var t;
    return regeneratorRuntime.wrap((function(n) {
    while (1) switch (n.prev = n.next) {
    case 0:
    return n.next = 2, i.$confirm(e, "提示", {
    confirmButtonText: "我已安装java8及以上环境",
    cancelButtonText: "我未安装，去下载安装",
    type: "warning"
    })["catch"]((function() {
    return !1
    }));
    case 2:
    t = n.sent, t || window.open("https://www.123pan.com/s/Xcl8Vv-Vjkxv.html", "__blank");
    case 4:
    case "end":
    return n.stop()
    }
    }), n)
    })))()
    },
    saveConfig: function() {
    var e = this;
    return asyncToGenerator(regeneratorRuntime.mark((function i() {
    var n, t, o, r;
    return regeneratorRuntime.wrap((function(i) {
    while (1) switch (i.prev = i.next) {
    case 0:
    if (n = __merge(__merge({}, e.windowConfigForm), {}, {
    serverPort: parseInt(e.windowConfigForm.serverPort),
    width: parseFloat(e.windowConfigForm.width),
    height: parseFloat(e.windowConfigForm.height),
    positionX: parseFloat(e.windowConfigForm.positionX),
    positionY: parseFloat(e.windowConfigForm.positionY)
    }), !(n.serverPort <= 0 || n.serverPort >= 65536)) {
    i.next = 4;
    break
    }
    return e.$message.error("服务端口只能在 0-65536"), i.abrupt("return");
    case 4:
    if (!e.windowConfigForm.setWindowSize) {
    i.next = 11;
    break
    }
    if (!(isNaN(n.width) || n.width <= 0)) {
    i.next = 8;
    break
    }
    return e.$message.error("窗口宽度必须大于0"), i.abrupt("return");
    case 8:
    if (!(isNaN(n.height) || n.height <= 0)) {
    i.next = 11;
    break
    }
    return e.$message.error("窗口高度必须大于0"), i.abrupt("return");
    case 11:
    if (!e.windowConfigForm.setWindowPosition) {
    i.next = 18;
    break
    }
    if (!(isNaN(n.positionX) || n.positionX <= 0)) {
    i.next = 15;
    break
    }
    return e.$message.error("窗口横坐标必须大于0"), i.abrupt("return");
    case 15:
    if (!(isNaN(n.positionY) || n.positionY <= 0)) {
    i.next = 18;
    break
    }
    return e.$message.error("窗口纵坐标必须大于0"), i.abrupt("return");
    case 18:
    i.prev = 18, n.serverConfig = n.serverConfig.trim(), "{" !== n.serverConfig[0] && e.$message.error("服务配置必须是 JSON 对象格式"), n.serverConfig = JSON.parse(n.serverConfig), i.next = 28;
    break;
    case 24:
    return i.prev = 24, i.t0 = i["catch"](18), e.$message.error("服务配置必须是 JSON 对象格式"), i.abrupt("return");
    case 28:
    i.prev = 28, n.jvmConfig = n.jvmConfig.trim(), "[" !== n.jvmConfig[0] && e.$message.error("jvm配置必须是 JSON 数组格式"), n.jvmConfig = JSON.parse(n.jvmConfig), i.next = 38;
    break;
    case 34:
    return i.prev = 34, i.t1 = i["catch"](28), e.$message.error("jvm配置必须是 JSON 数组格式"), i.abrupt("return");
    case 38:
    if (window.__TAURI__ && window.__TAURI__.invoke) {
    i.next = 41;
    break
    }
    return e.$message.error("必须使用 Tauri 客户端打开此页面"), i.abrupt("return");
    case 41:
    return i.next = 43, window.__TAURI__.invoke("check_java", {
    javaPath: n.javaPath || ""
    })["catch"]((function(i) {
    return e.showJavaConfirm((e.windowConfigForm.javaPath ? "未检测到 Java 运行环境，请确认 Java 路径设置是否正确 " : "未检测到 Java 运行环境，请确认是否已安装Java8及以上运行环境 ") + i), !1
    }));
    case 43:
    if (t = i.sent, t) {
    i.next = 46;
    break
    }
    return i.abrupt("return");
    case 46:
    return i.next = 48, window.__TAURI__.invoke("save_config", {
    config: n
    })["catch"]((function(i) {
    return e.$message.error("配置保存失败" + i), !1
    }));
    case 48:
    if (o = i.sent, o) {
    i.next = 51;
    break
    }
    return i.abrupt("return");
    case 51:
    return i.next = 53, window.__TAURI__.invoke("stop_server", {
    config: n
    })["catch"]((function(i) {
    return e.$message.error("reader停止失败，请手动重启软件" + i), !1
    })).then((function() {
    return !0
    }));
    case 53:
    if (r = i.sent, r) {
    i.next = 56;
    break
    }
    return i.abrupt("return");
    case 56:
    window.__TAURI__.process.relaunch();
    case 57:
    case "end":
    return i.stop()
    }
    }), i, null, [
    [18, 24],
    [28, 34]
    ])
    })))()
    }
    },
    watch: {
    collapseMenu: function(e) {
    e ? this.showNavigation || (this.navigationClass = "navigation-hidden") : this.navigationClass = ""
    },
    showNavigation: function(e) {
    var i = this;
    e ? this.navigationClass = "navigation-in" : (this.navigationClass = "navigation-out", setTimeout((function() {
    i.navigationClass = "navigation-hidden"
    }), 300))
    }
    },
  render: function() {
                    var e = this,
                        i = e._self._c;
                    return i("div", {
                        staticClass: "setting-wrapper",
                        class: {
                            night: e.isNight,
                            day: !e.isNight
                        }
                    }, [i("div", {
                        staticClass: "navigation-wrapper",
                        class: [e.navigationClass, e.isWebApp && !e.isNight ? "status-bar-light-bg" : ""],
                        style: e.navigationStyle,
                        on: {
                            touchstart: e.handleTouchStart,
                            touchmove: e.handleTouchMove,
                            touchend: e.handleTouchEnd
                        }
                    }, [i("div", {
                        staticClass: "navigation-inner-wrapper"
                    }, [i("div", {
                        staticClass: "navigation-title"
                    }, [e._v(" 阅读 "), i("span", {
                        staticClass: "version-text"
                    }, [e._v(e._s(e.$store.state.version))])]), i("div", {
                        staticClass: "navigation-sub-title"
                    }, [e._v(" 清风不识字，何故乱翻书 ")])])]), i("div", {
                        ref: "shelfWrapper",
                        staticClass: "setting-zone-wrapper",
                        class: e.isWebApp && !e.isNight ? "status-bar-light-bg" : "",
                        on: {
                            click: e.hideMenu
                        }
                    }, [i("div", {
                        staticClass: "setting-zone-title"
                    }, [e.$store.getters.isNormalPage && e.collapseMenu ? i("i", {
                        staticClass: "el-icon-menu",
                        on: {
                            click: function(i) {
                                return i.stopPropagation(), e.toggleMenu.apply(null, arguments)
                            }
                        }
                    }) : e._e(), e._v(" 设置 "), i("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.backToShelf
                        }
                    }, [e._v(" 书架 ")])]), i("div", {
                        staticClass: "setting-form-wrapper"
                    }, [i("el-form", {
                        attrs: {
                            model: e.windowConfigForm,
                            "label-width": e.collapseMenu ? "auto" : "95px"
                        }
                    }, [i("el-form-item", {
                        attrs: {
                            label: "Java路径"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.javaPath,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "javaPath", i)
                            },
                            expression: "windowConfigForm.javaPath"
                        }
                    }), i("el-link", {
                        staticStyle: {
                            "line-height": "25px"
                        },
                        attrs: {
                            type: "warning",
                            href: "https://www.123pan.com/s/Xcl8Vv-Vjkxv.html",
                            target: "__blank",
                            underline: !1
                        }
                    }, [e._v("注意！如果已安装java8及以上环境，请在此处填入已安装的java程序的路径，如 C:\\jdk\\bin\\java.exe。如果没有安装java8及以上环境，请点此去下载安装")])], 1), i("el-form-item", {
                        attrs: {
                            label: "服务端口"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.serverPort,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "serverPort", i)
                            },
                            expression: "windowConfigForm.serverPort"
                        }
                    })], 1), i("el-form-item", {
                        attrs: {
                            label: "服务配置"
                        }
                    }, [i("el-input", {
                        attrs: {
                            type: "textarea",
                            rows: 5
                        },
                        model: {
                            value: e.windowConfigForm.serverConfig,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "serverConfig", i)
                            },
                            expression: "windowConfigForm.serverConfig"
                        }
                    }), i("el-link", {
                        attrs: {
                            underline: !1,
                            type: "warning"
                        }
                    }, [e._v("JSON对象格式，注意不要使用中文符号 “，等")])], 1), i("el-form-item", {
                        attrs: {
                            label: "jvm配置"
                        }
                    }, [i("el-input", {
                        attrs: {
                            type: "textarea",
                            rows: 5
                        },
                        model: {
                            value: e.windowConfigForm.jvmConfig,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "jvmConfig", i)
                            },
                            expression: "windowConfigForm.jvmConfig"
                        }
                    }), i("el-link", {
                        attrs: {
                            underline: !1,
                            type: "warning"
                        }
                    }, [e._v("JSON数组格式，注意不要使用中文符号 “，等")])], 1), i("el-form-item", {
                        attrs: {
                            label: "窗口大小"
                        }
                    }, [i("el-checkbox", {
                        model: {
                            value: e.windowConfigForm.setWindowSize,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "setWindowSize", i)
                            },
                            expression: "windowConfigForm.setWindowSize"
                        }
                    }, [e._v("启动时设置窗口大小")]), i("el-checkbox", {
                        model: {
                            value: e.windowConfigForm.rememberSize,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "rememberSize", i)
                            },
                            expression: "windowConfigForm.rememberSize"
                        }
                    }, [e._v("自动记住窗口大小")])], 1), i("el-form-item", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.windowConfigForm.setWindowSize,
                            expression: "windowConfigForm.setWindowSize"
                        }],
                        attrs: {
                            label: "窗口宽度"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.width,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "width", i)
                            },
                            expression: "windowConfigForm.width"
                        }
                    })], 1), i("el-form-item", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.windowConfigForm.setWindowSize,
                            expression: "windowConfigForm.setWindowSize"
                        }],
                        attrs: {
                            label: "窗口高度"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.height,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "height", i)
                            },
                            expression: "windowConfigForm.height"
                        }
                    })], 1), i("el-form-item", {
                        attrs: {
                            label: "窗口位置"
                        }
                    }, [i("el-checkbox", {
                        model: {
                            value: e.windowConfigForm.setWindowPosition,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "setWindowPosition", i)
                            },
                            expression: "windowConfigForm.setWindowPosition"
                        }
                    }, [e._v("启动时设置窗口位置")]), i("el-checkbox", {
                        model: {
                            value: e.windowConfigForm.rememberPosition,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "rememberPosition", i)
                            },
                            expression: "windowConfigForm.rememberPosition"
                        }
                    }, [e._v("自动记住窗口位置")])], 1), i("el-form-item", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.windowConfigForm.setWindowPosition,
                            expression: "windowConfigForm.setWindowPosition"
                        }],
                        attrs: {
                            label: "横坐标"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.positionX,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "positionX", i)
                            },
                            expression: "windowConfigForm.positionX"
                        }
                    })], 1), i("el-form-item", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.windowConfigForm.setWindowPosition,
                            expression: "windowConfigForm.setWindowPosition"
                        }],
                        attrs: {
                            label: "纵坐标"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.positionY,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "positionY", i)
                            },
                            expression: "windowConfigForm.positionY"
                        }
                    })], 1), i("el-form-item", {
                        attrs: {
                            label: "页面链接"
                        }
                    }, [i("el-input", {
                        model: {
                            value: e.windowConfigForm.windowUrl,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "windowUrl", i)
                            },
                            expression: "windowConfigForm.windowUrl"
                        }
                    }), i("el-link", {
                        attrs: {
                            underline: !1,
                            type: "warning"
                        }
                    }, [e._v("如无必要，请勿设置")])], 1), i("el-form-item", {
                        attrs: {
                            label: "调试页面"
                        }
                    }, [i("el-switch", {
                        attrs: {
                            "active-color": "#13ce66",
                            "inactive-color": "#ff4949",
                            "active-value": !0,
                            "inactive-value": !1
                        },
                        model: {
                            value: e.windowConfigForm.debug,
                            callback: function(i) {
                                e.$set(e.windowConfigForm, "debug", i)
                            },
                            expression: "windowConfigForm.debug"
                        }
                    })], 1), i("div", {
                        staticStyle: {
                            "text-align": "center"
                        }
                    }, [i("el-button", {
                        attrs: {
                            type: "primary"
                        },
                        on: {
                            click: e.saveConfig
                        }
                    }, [e._v("保存并重启")])], 1)], 1)], 1)])])
                },
  staticRenderFns: []
}
</script>

<style scoped>
.setting-wrapper { height:100%;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row }
.setting-wrapper .navigation-wrapper { width:260px;min-width:260px;height:100%;box-sizing:border-box;background-color:#f7f7f7;position:relative;padding-top:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important }
.setting-wrapper .navigation-wrapper .navigation-inner-wrapper { padding:48px 36px 66px 36px;height:100%;overflow-y:auto;box-sizing:border-box }
.setting-wrapper .navigation-wrapper .navigation-title { font-size:24px;font-weight:600;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif }
.setting-wrapper .navigation-wrapper .navigation-title .version-text { float:right;font-size:14px;line-height:33px;font-weight:400;color:#b1b1b1;display:inline-block;cursor:pointer }
.setting-wrapper .navigation-wrapper .navigation-sub-title { font-size:16px;font-weight:500;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;margin-top:16px;color:#b1b1b1 }
.setting-wrapper .navigation-wrapper .setting-wrapper { margin-top:36px }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-title { font-size:14px;color:#b1b1b1;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-title .right-text { float:right;display:inline-block;height:20px;line-height:20px;cursor:pointer;-webkit-user-select:none;-ms-user-select:none;user-select:none }
.setting-wrapper .navigation-wrapper .setting-wrapper .no-point { pointer-events:none }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-connect { cursor:pointer;max-width:100%;overflow:hidden;text-overflow:ellipsis }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-item { padding-top:16px }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-btn { margin-right:15px;margin-bottom:15px;cursor:pointer }
.setting-wrapper .navigation-wrapper .setting-wrapper .setting-select { width:100% }
.setting-wrapper .navigation-wrapper .search-setting { margin-top:28px }
.setting-wrapper .navigation-wrapper .bottom-icons { position:absolute;bottom:30px;width:188px;left:36px;-ms-flex-align:center;align-items:center;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:justify;justify-content:space-between;pointer-events:none }
.setting-wrapper .navigation-wrapper .bottom-icons .bottom-icon { height:36px;pointer-events:all }
.setting-wrapper .navigation-wrapper .bottom-icons .bottom-icon img { width:36px;height:36px }
.setting-wrapper .navigation-wrapper .bottom-icons .theme-item { line-height:32px;width:36px;height:36px;border-radius:100%;display:inline-block;cursor:pointer;text-align:center;vertical-align:middle;pointer-events:all }
.setting-wrapper .navigation-wrapper .bottom-icons .theme-item .el-icon-moon { color:#f7f7f7;line-height:34px }
.setting-wrapper .navigation-wrapper .bottom-icons .theme-item .el-icon-sunny { color:#121212;line-height:34px }
.setting-wrapper .navigation-wrapper .setting-wrapper:last-child { padding-bottom:20px }
.setting-wrapper .setting-zone-wrapper { padding:48px 48px;height:100%;max-height:100%;width:100%;background-color:#fff;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;box-sizing:border-box }
.setting-wrapper .setting-zone-wrapper .setting-zone-title { font-size:20px;font-weight:600;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;margin-bottom:5px;min-width:320px;box-sizing:border-box }
.setting-wrapper .setting-zone-wrapper .setting-zone-title .el-icon-menu { cursor:pointer }
.setting-wrapper .setting-zone-wrapper .setting-zone-title .title-btn { font-size:14px;line-height:28px;float:right;cursor:pointer;-webkit-user-select:none;-ms-user-select:none;user-select:none;margin-left:10px }
.setting-wrapper .setting-zone-wrapper .setting-zone-title .title-btn .el-icon-loading { font-size:16px }
.setting-wrapper .setting-zone-wrapper .setting-form-wrapper { -ms-flex:1;flex:1;overflow-x:hidden;overflow-y:scroll;padding:10px 10px 30px }
.night .navigation-wrapper { background-color:#121212;border-right:1px solid #555 }
.night .navigation-title, .night .setting-zone-title { color:#bbb }
.night .setting-zone-wrapper { background-color:#222 }
.night .el-input__inner { background-color:#444;border:1px solid #444!important;color:#aaa }
.navigation-inner-wrapper::-webkit-scrollbar { width:0!important }
.setting-wrapper { overflow-x:hidden }
.setting-wrapper .navigation-wrapper .navigation-inner-wrapper { padding:20px 36px 66px 36px }
.setting-wrapper .setting-zone-wrapper { padding:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important }
.setting-wrapper .setting-zone-wrapper .setting-zone-title { padding:20px 24px 0 24px }

@media screen and (max-width:750px) {
  .setting-wrapper{overflow-x:hidden}
  .setting-wrapper .navigation-wrapper .navigation-inner-wrapper{padding:20px 36px 66px 36px}
  .setting-wrapper .setting-zone-wrapper{padding:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important}
  .setting-wrapper .setting-zone-wrapper .setting-zone-title{padding:20px 24px 0 24px}
}
</style>
