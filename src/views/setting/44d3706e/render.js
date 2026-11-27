var render = function() {
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
                };
