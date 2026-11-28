var render = function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "settings-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight
                        },
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "settings-title"
                    }, [e._v(" 设置 "), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.resetConfig
                        }
                    }, [e._v("重置")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.restoreConfig
                        }
                    }, [e._v("同步")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.backupConfig
                        }
                    }, [e._v("保存")]), t("div", {
                        staticClass: "title-btn",
                        on: {
                            click: e.toggleNotAutoSync
                        }
                    }, [e._v(" " + e._s(e.notAutoSync ? "自动同步" : "取消自动同步") + " ")])]), t("div", {
                        staticClass: "setting-list"
                    }, [t("ul", [t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("配置方案")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.$store.state.customConfigList, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.$store.getters.config.customConfig === n.name
                            },
                            on: {
                                click: function(t) {
                                    return e.setCustomConfig(n)
                                }
                            }
                        }, [t("span", [e._v(e._s(n.name))]), a > 1 && e.$store.getters.config.customConfig !== n.name ? t("i", {
                            staticClass: "el-icon-close delete-custom-config-icon",
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.deleteCustomConfig(a, n.name)
                                }
                            }
                        }) : e._e()])
                    })), t("span", {
                        key: "addNewCustomConfig",
                        staticClass: "span-item",
                        on: {
                            click: e.addNewCustomConfig
                        }
                    }, [e._v("新增方案")]), t("span", {
                        key: "autoTheme",
                        ref: "themes",
                        staticClass: "span-item",
                        class: {
                            selected: e.$store.getters.config.autoTheme
                        },
                        on: {
                            click: e.setAutoTheme
                        }
                    }, [e._v("自动切换")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("方案类型")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.configDefaultTypeList, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.currentCustomConfig.configDefaultType === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfigDefaultType(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("阅读主题")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.themeColors, (function(n, a) {
                        return t("span", {
                            key: a,
                            ref: "themes",
                            refInFor: !0,
                            staticClass: "theme-item",
                            class: {
                                selected: e.config.theme === a
                            },
                            style: n,
                            on: {
                                click: function(t) {
                                    return e.setConfig("theme", a)
                                }
                            }
                        }, [6 != a ? t("em", {
                            staticClass: "iconfont"
                        }, [e._v("")]) : t("em", {
                            staticClass: "moon-icon"
                        }, [e._v(e._s(e.moonIcon))])])
                    })), t("span", {
                        key: "custom",
                        ref: "themes",
                        staticClass: "span-item",
                        class: {
                            selected: "custom" === e.config.theme
                        },
                        on: {
                            click: function(t) {
                                return e.setConfig("theme", "custom")
                            }
                        }
                    }, [e._v("自定义")])], 2)]), "custom" === e.config.theme ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("自定义")]), t("div", {
                        staticClass: "custom-theme"
                    }, [t("div", {
                        staticClass: "custom-theme-title"
                    }, [t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("主题模式")]), e._l(e.themeTypes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.themeType == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("themeType", n)
                                }
                            }
                        }, [e._v(e._s("day" === n ? "白天" : "黑夜"))])
                    }))], 2), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("页面背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.bodyColor,
                            callback: function(t) {
                                e.$set(e.config, "bodyColor", t)
                            },
                            expression: "config.bodyColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("浮窗背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.popupColor,
                            callback: function(t) {
                                e.$set(e.config, "popupColor", t)
                            },
                            expression: "config.popupColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("阅读背景颜色 "), t("el-color-picker", {
                        model: {
                            value: e.config.contentColor,
                            callback: function(t) {
                                e.$set(e.config, "contentColor", t)
                            },
                            expression: "config.contentColor"
                        }
                    })], 1), t("span", {
                        staticClass: "custom-theme-title"
                    }, [e._v("阅读背景图片 "), e._l(e.builtinBG, (function(n, a) {
                        return t("img", {
                            key: a,
                            staticClass: "content-bg-preview",
                            class: {
                                selected: e.$store.getters.config.contentBGImg == n.src
                            },
                            attrs: {
                                src: n.src,
                                alt: ""
                            },
                            on: {
                                click: function(t) {
                                    return e.setBGImg(n.src)
                                }
                            }
                        })
                    })), e._l(e.$store.getters.config.customBGImgList || [], (function(n) {
                        return t("div", {
                            key: n,
                            staticClass: "content-bg-preview",
                            class: {
                                selected: e.$store.getters.config.contentBGImg == n
                            }
                        }, [t("img", {
                            attrs: {
                                src: e.getCustomBGImgURL(n),
                                alt: ""
                            },
                            on: {
                                click: function(t) {
                                    return e.setBGImg(n)
                                }
                            }
                        }), t("i", {
                            staticClass: "el-icon-close delete-bg-icon",
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.deleteCustomBGImg(n)
                                }
                            }
                        })])
                    })), t("span", {
                        staticClass: "upload-bg-btn",
                        on: {
                            click: e.uploadBGFile
                        }
                    }, [e._v("上传")]), t("input", {
                        ref: "bgFileRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file"
                        },
                        on: {
                            change: e.onBGFileChange
                        }
                    })], 2)])]) : e._e(), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("正文字体")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.fonts, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.font == a
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("font", a)
                                }
                            }
                        }, [e._v(e._s(n) + " "), t("i", {
                            class: {
                                "el-icon-upload": !0,
                                "upload-font-icon": !0,
                                active: e.config.customFontsMap && e.config.customFontsMap[e.customFonts[a]]
                            },
                            on: {
                                click: function(t) {
                                    return t.stopPropagation(), e.uploadFontFile(e.customFonts[a], n)
                                }
                            }
                        })])
                    })), t("input", {
                        ref: "fontFileRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file"
                        },
                        on: {
                            change: e.onFontFileChange
                        }
                    })], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("简繁转换")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.chineseFonts, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.chineseFont == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("chineseFont", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("字体大小")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("fontSize")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.fontSize,
                            callback: function(t) {
                                e.$set(e.config, "fontSize", t)
                            },
                            expression: "config.fontSize"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "more",
                        on: {
                            click: function(t) {
                                return e.incConfig("fontSize")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("字体粗细")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("fontWeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.fontWeight,
                            callback: function(t) {
                                e.$set(e.config, "fontWeight", t)
                            },
                            expression: "config.fontWeight"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("fontWeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title font-color-title"
                    }, [e._v("字体颜色")]), t("el-color-picker", {
                        model: {
                            value: e.config.fontColor,
                            callback: function(t) {
                                e.$set(e.config, "fontColor", t)
                            },
                            expression: "config.fontColor"
                        }
                    })], 1), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("段落行高")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("lineHeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.lineHeight,
                            callback: function(t) {
                                e.$set(e.config, "lineHeight", t)
                            },
                            expression: "config.lineHeight"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("lineHeight")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("段落间距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("paragraphSpace")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.paragraphSpace,
                            callback: function(t) {
                                e.$set(e.config, "paragraphSpace", t)
                            },
                            expression: "config.paragraphSpace"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("paragraphSpace")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("左右边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("horizontalPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.horizontalPadding,
                            callback: function(t) {
                                e.$set(e.config, "horizontalPadding", t)
                            },
                            expression: "config.horizontalPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("horizontalPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("顶部边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("topPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.topPadding,
                            callback: function(t) {
                                e.$set(e.config, "topPadding", t)
                            },
                            expression: "config.topPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("topPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), e.$store.state.miniInterface ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("底部边距")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("bottomPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.bottomPadding,
                            callback: function(t) {
                                e.$set(e.config, "bottomPadding", t)
                            },
                            expression: "config.bottomPadding"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("bottomPadding")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("页面模式")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.pageModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.pageMode == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setPageMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), e.$store.state.miniInterface ? e._e() : t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("页面宽度")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("readWidth")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])]), t("b"), e._v(" "), t("span", {
                        staticClass: "lang"
                    }, [e._v(e._s(e.config.readWidth))]), t("b"), t("span", {
                        staticClass: "more",
                        on: {
                            click: function(t) {
                                return e.incConfig("readWidth")
                            }
                        }
                    }, [t("em", {
                        staticClass: "iconfont"
                    }, [e._v("")])])])]), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("翻页方式")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.readMethods, (function(n, a) {
                        return t("span", {
                            directives: [{
                                name: "show",
                                rawName: "v-show",
                                value: !e.$store.state.miniInterface && "左右滑动" !== n || e.$store.state.miniInterface,
                                expression: "\n              (!$store.state.miniInterface && method !== '左右滑动') ||\n                $store.state.miniInterface\n            "
                            }],
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.readMethod == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setReadMethod(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), t("span", {
                        staticClass: "small-tip"
                    }, [e._v("❗️上下滚动2会自动隐藏看过的章节，但是可能会抖动")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("动画时长")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("animateMSTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.animateMSTime,
                            callback: function(t) {
                                e.$set(e.config, "animateMSTime", t)
                            },
                            expression: "config.animateMSTime"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("animateMSTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("自动翻页")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.autoReadingMethods, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.autoReadingMethod === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("autoReadingMethod", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), "像素滚动" === e.config.autoReadingMethod ? t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("滚动像素")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("autoReadingPixel")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.autoReadingPixel,
                            callback: function(t) {
                                e.$set(e.config, "autoReadingPixel", t)
                            },
                            expression: "config.autoReadingPixel"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("autoReadingPixel")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]) : e._e(), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("翻页速度")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("autoReadingLineTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.autoReadingLineTime,
                            callback: function(t) {
                                e.$set(e.config, "autoReadingLineTime", t)
                            },
                            expression: "config.autoReadingLineTime"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("autoReadingLineTime")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("全屏点击")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.clickMethods, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.clickMethod == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("clickMethod", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("选择文字")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.selectionActions, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.selectionAction == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setConfig("selectionAction", n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("Epub解析")]), t("div", {
                        staticClass: "selection-zone"
                    }, [e._l(e.epubModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.epubMode == n
                            },
                            on: {
                                click: function(t) {
                                    return e.setEpubMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), t("span", {
                        staticClass: "small-tip"
                    }, [e._v("❗️解析可能导致显示问题，但是翻页性能较好，样式支持更多，也支持简繁切换。")])], 2)]), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("请求超时")]), t("div", {
                        staticClass: "resize"
                    }, [t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.decConfig("chapterRequestTimeout")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-minus"
                    })]), t("b"), t("span", {
                        staticClass: "lang"
                    }, [t("el-input", {
                        staticClass: "setting-input",
                        attrs: {
                            size: "mini"
                        },
                        model: {
                            value: e.config.chapterRequestTimeout,
                            callback: function(t) {
                                e.$set(e.config, "chapterRequestTimeout", t)
                            },
                            expression: "config.chapterRequestTimeout"
                        }
                    })], 1), t("b"), t("span", {
                        staticClass: "less",
                        on: {
                            click: function(t) {
                                return e.incConfig("chapterRequestTimeout")
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-plus"
                    })])])]), t("el-divider"), t("li", [t("span", {
                        staticClass: "setting-item-title"
                    }, [e._v("快捷键")]), t("div", {
                        staticClass: "selection-zone"
                    }, e._l(e.quickKeyModes, (function(n, a) {
                        return t("span", {
                            key: a,
                            staticClass: "span-item",
                            class: {
                                selected: e.config.quickKeyMode === n
                            },
                            on: {
                                click: function(t) {
                                    return e.setQuickKeyMode(n)
                                }
                            }
                        }, [e._v(e._s(n))])
                    })), 0)]), "自定义" === e.config.quickKeyMode ? e._l(e.quickKeyList, (function(n, a) {
                        return t("li", {
                            key: "quickKey-" + a
                        }, [t("span", {
                            staticClass: "setting-item-title"
                        }, [e._v(e._s(n.label))]), t("div", {
                            staticClass: "selection-zone"
                        }, e._l(e.quickKeyOps, (function(a, u) {
                            return t("span", {
                                key: u,
                                staticClass: "span-item",
                                class: {
                                    selected: e.config.quickKey[n.key] === a
                                },
                                on: {
                                    click: function(t) {
                                        return e.setQuickKeyOpt(n.key, a)
                                    }
                                }
                            }, [e._v(e._s(a))])
                        })), 0)])
                    })) : e._e(), t("li", {
                        staticClass: "operation-zone"
                    }, [t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.showClickZone
                        }
                    }, [e._v("显示翻页区域")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.showRuleEditor
                        }
                    }, [e._v("过滤规则管理")])])], 2)])])
                };
