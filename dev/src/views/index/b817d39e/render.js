var render = function() {
                    var t = this,
                        e = t._self._c;
                    return e("div", {
                        staticClass: "index-wrapper",
                        class: {
                            night: t.isNight,
                            day: !t.isNight
                        }
                    }, [t.$store.getters.isNormalPage ? e("div", {
                        staticClass: "navigation-wrapper",
                        class: [t.navigationClass, t.isWebApp && !t.isNight ? "status-bar-light-bg" : ""],
                        style: t.navigationStyle,
                        on: {
                            touchstart: t.handleTouchStart,
                            touchmove: t.handleTouchMove,
                            touchend: t.handleTouchEnd
                        }
                    }, [e("div", {
                        staticClass: "navigation-inner-wrapper"
                    }, [e("div", {
                        staticClass: "navigation-title"
                    }, [t._v(" 阅读 "), e("span", {
                        staticClass: "version-text",
                        on: {
                            click: t.updateForce
                        }
                    }, [t._v(t._s(t.$store.state.version))])]), e("div", {
                        staticClass: "navigation-sub-title"
                    }, [t._v(" 清风不识字，何故乱翻书 ")]), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "search-wrapper"
                    }, [e("el-input", {
                        staticClass: "search-input",
                        attrs: {
                            size: "mini",
                            placeholder: "搜索书籍"
                        },
                        nativeOn: {
                            keyup: function(e) {
                                return !e.type.indexOf("key") && t._k(e.keyCode, "enter", 13, e.key, "Enter") ? null : t.searchBook(1)
                            }
                        },
                        model: {
                            value: t.search,
                            callback: function(e) {
                                t.search = e
                            },
                            expression: "search"
                        }
                    }, [e("i", {
                        staticClass: "el-input__icon el-icon-search",
                        attrs: {
                            slot: "prefix"
                        },
                        slot: "prefix"
                    })])], 1), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-wrapper search-setting"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 搜索设置 ")]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择搜索方式"
                        },
                        model: {
                            value: t.searchConfig.searchType,
                            callback: function(e) {
                                t.$set(t.searchConfig, "searchType", e)
                            },
                            expression: "searchConfig.searchType"
                        }
                    }, t._l(t.searchTypeList, (function(t, o) {
                        return e("el-option", {
                            key: "search-type-" + o,
                            attrs: {
                                label: t.name,
                                value: t.value
                            }
                        })
                    })), 1)], 1), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "single" === t.searchConfig.searchType,
                            expression: "searchConfig.searchType === 'single'"
                        }],
                        staticClass: "setting-item"
                    }, ["yes" === t.shelfConfig.virtualOptimize ? e("VirtualSelect", {
                        attrs: {
                            "data-list": t.bookSourceList
                        },
                        model: {
                            value: t.searchConfig.bookSourceUrl,
                            callback: function(e) {
                                t.$set(t.searchConfig, "bookSourceUrl", e)
                            },
                            expression: "searchConfig.bookSourceUrl"
                        }
                    }) : e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择搜索书源"
                        },
                        model: {
                            value: t.searchConfig.bookSourceUrl,
                            callback: function(e) {
                                t.$set(t.searchConfig, "bookSourceUrl", e)
                            },
                            expression: "searchConfig.bookSourceUrl"
                        }
                    }, t._l(t.bookSourceList, (function(t, o) {
                        return e("el-option", {
                            key: "source-" + o,
                            attrs: {
                                label: t.bookSourceName,
                                value: t.bookSourceUrl
                            }
                        })
                    })), 1)], 1), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "single" === t.searchConfig.searchType,
                            expression: "searchConfig.searchType === 'single'"
                        }],
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.searchBookManual
                        }
                    }, [t._v(" 精确搜书 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.saveBookManual
                        }
                    }, [t._v(" 手动加书 ")])], 1), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "single" !== t.searchConfig.searchType,
                            expression: "searchConfig.searchType !== 'single'"
                        }],
                        staticClass: "setting-item"
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择搜索书源分组"
                        },
                        model: {
                            value: t.searchConfig.bookSourceGroup,
                            callback: function(e) {
                                t.$set(t.searchConfig, "bookSourceGroup", e)
                            },
                            expression: "searchConfig.bookSourceGroup"
                        }
                    }, t._l(t.bookSourceGroupList, (function(t, o) {
                        return e("el-option", {
                            key: "source-group-" + o,
                            attrs: {
                                label: t.name + " (" + t.count + ")",
                                value: t.value
                            }
                        })
                    })), 1)], 1), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "single" !== t.searchConfig.searchType,
                            expression: "searchConfig.searchType !== 'single'"
                        }],
                        staticClass: "setting-item"
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择并发线程"
                        },
                        model: {
                            value: t.searchConfig.concurrentCount,
                            callback: function(e) {
                                t.$set(t.searchConfig, "concurrentCount", e)
                            },
                            expression: "searchConfig.concurrentCount"
                        }
                    }, t._l(t.concurrentList, (function(t, o) {
                        return e("el-option", {
                            key: "source-" + o,
                            attrs: {
                                label: t + "并发线程",
                                value: t
                            }
                        })
                    })), 1)], 1)]), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "recent-wrapper"
                    }, [e("div", {
                        staticClass: "recent-title"
                    }, [t._v(" 最近阅读 "), e("span", {
                        staticClass: "right-text",
                        on: {
                            click: function(e) {
                                return t.$store.commit("clearReadingBook")
                            }
                        }
                    }, [t._v("清除")])]), e("div", {
                        staticClass: "reading-recent"
                    }, [e("el-tag", {
                        staticClass: "recent-book",
                        class: {
                            "no-point": "" == t.readingRecent.bookUrl
                        },
                        attrs: {
                            type: "warning",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.toDetail(t.readingRecent)
                            }
                        }
                    }, [t._v(" " + t._s(t.readingRecent.name) + " ")])], 1)]), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 后端设定 "), t.isTauri ? e("span", {
                        staticClass: "right-text",
                        on: {
                            click: function(e) {
                                return t.$router.push({
                                    path: "/setting"
                                })
                            }
                        }
                    }, [t._v("设置")]) : t._e()]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-connect",
                        class: {
                            "no-point": t.connecting
                        },
                        attrs: {
                            type: t.connectType,
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.setIP
                        }
                    }, [t._v(" " + t._s(t.connectStatus) + " ")])], 1)]), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 书源设置 ")]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                t.showBookSourceManageDialog = !0
                            }
                        }
                    }, [t._v(" 书源管理 ")]), e("el-popover", {
                        attrs: {
                            placement: "right",
                            width: t.popupWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component explore-popover"
                        },
                        model: {
                            value: t.popExploreVisible,
                            callback: function(e) {
                                t.popExploreVisible = e
                            },
                            expression: "popExploreVisible"
                        }
                    }, [e("Explore", {
                        ref: "popExplore",
                        staticClass: "popup",
                        attrs: {
                            visible: t.popExploreVisible,
                            bookSourceList: t.bookSourceList
                        },
                        on: {
                            showSearchList: t.showSearchList,
                            close: function(e) {
                                t.popExploreVisible = !1
                            }
                        }
                    }), e("el-tag", {
                        ref: "exploreBtn",
                        staticClass: "setting-btn",
                        attrs: {
                            slot: "reference",
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                t.showNavigation = !1
                            }
                        },
                        slot: "reference"
                    }, [t._v(" 探索书源 ")])], 1), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.uploadBookSource
                        }
                    }, [t._v(" 导入书源 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showRemoteBookSourceSubDialog
                        }
                    }, [t._v(" 书源订阅 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showFailureBookSource()
                            }
                        }
                    }, [t._v(" 失效书源 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.debugBookSource()
                            }
                        }
                    }, [t._v(" 调试书源 ")]), e("input", {
                        ref: "fileRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file"
                        },
                        on: {
                            change: t.onSourceFileChange
                        }
                    })], 1)]), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 书架设置 ")]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                t.showShelfSettingsDialog = !t.showShelfSettingsDialog
                            }
                        }
                    }, [t._v(" 书架设置 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showBookManage
                        }
                    }, [t._v(" 书籍管理 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showManageBookGroup
                        }
                    }, [t._v(" 分组管理 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.importLocalBook
                        }
                    }, [t._v(" 导入书籍 ")]), e("input", {
                        ref: "bookRef",
                        staticStyle: {
                            display: "none"
                        },
                        attrs: {
                            type: "file",
                            multiple: "multiple"
                        },
                        on: {
                            change: t.onBookFileChange
                        }
                    }), !t.$store.state.isSecureMode || t.$store.state.userInfo.enableLocalStore ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showFileManagerDialog("__LOCAL_STORE__", "书仓文件管理")
                            }
                        }
                    }, [t._v(" 浏览书仓 ")]) : t._e(), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showBookmarkDialog
                        }
                    }, [t._v(" 书签管理 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showReplaceRuleDialog
                        }
                    }, [t._v(" 替换规则 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.init(!0)
                            }
                        }
                    }, [t._v(" 刷新缓存 ")])], 1)]), e("div", {
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 用户空间 "), t.$store.getters.hasLogin ? e("span", {
                        staticClass: "right-text",
                        on: {
                            click: function(e) {
                                return t.logout()
                            }
                        }
                    }, [t._v("注销")]) : t.$store.state.isSecureMode ? e("span", {
                        staticClass: "right-text",
                        on: {
                            click: function(e) {
                                return t.$store.commit("setShowLogin", !0)
                            }
                        }
                    }, [t._v("登录")]) : t._e()]), t.$store.state.isManagerMode ? e("div", {
                        staticClass: "setting-item"
                    }, [e("el-select", {
                        staticClass: "setting-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择用户空间"
                        },
                        model: {
                            value: t.userNS,
                            callback: function(e) {
                                t.userNS = e
                            },
                            expression: "userNS"
                        }
                    }, t._l(t.userList, (function(t, o) {
                        return e("el-option", {
                            key: "source-" + o,
                            attrs: {
                                label: t.username,
                                value: t.userNS
                            }
                        })
                    })), 1)], 1) : t._e(), e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-item"
                    }, [t.isShowActiveLicenseBtn ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showActiveLicenseDialog
                        }
                    }, [t._v(" 授权管理 ")]) : t._e(), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.showLicense
                        }
                    }, [t._v(" 查看授权信息 ")]), t.localStorageAvaliable ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.saveUserConfig
                        }
                    }, [t._v(" 备份我的配置 ")]) : t._e(), t.localStorageAvaliable ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.restoreUserConfig
                        }
                    }, [t._v(" 同步我的配置 ")]) : t._e(), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showFileManagerDialog("__HOME__", "用户数据管理")
                            }
                        }
                    }, [t._v(" 我的数据管理 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.downloadBackupFile
                        }
                    }, [t._v(" 下载数据备份 ")]), t.$store.state.showManagerMode && !t.$store.state.isManagerMode ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.loadUserList
                        }
                    }, [t._v(" 进入管理模式 ")]) : t._e(), t.$store.state.isManagerMode ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showUserManageDialog()
                            }
                        }
                    }, [t._v(" 用户权限管理 ")]) : t._e(), t.$store.state.isManagerMode ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showFileManagerDialog("__STORAGE__", "数据目录管理")
                            }
                        }
                    }, [t._v(" 数据目录管理 ")]) : t._e(), t.$store.state.isManagerMode ? e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.exitSecureMode
                        }
                    }, [t._v(" 退出管理模式 ")]) : t._e()], 1)]), !t.$store.state.isSecureMode || t.$store.state.userInfo.enableWebdav ? e("div", {
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: t.$store.getters.hasLogin,
                            expression: "$store.getters.hasLogin"
                        }],
                        staticClass: "setting-title"
                    }, [t._v(" WebDAV ")]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.showFileManagerDialog("__WEBDAV__", "WebDAV文件管理")
                            }
                        }
                    }, [t._v(" 文件管理 ")]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.isNight ? "dark" : "light"
                        },
                        on: {
                            click: t.backupToWebdav
                        }
                    }, [t._v(" 保存备份 ")])], 1)]) : t._e(), e("div", {
                        staticClass: "setting-wrapper"
                    }, [e("div", {
                        staticClass: "setting-title"
                    }, [t._v(" 本地缓存 "), e("span", {
                        staticClass: "right-text"
                    }, [t._v(t._s(t.localCacheStats.total))])]), e("div", {
                        staticClass: "setting-item"
                    }, [e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.clearCache("bookSourceList")
                            }
                        }
                    }, [t._v(" 清空书源缓存 "), e("span", [t._v(t._s(t.localCacheStats.bookSourceList))])]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.clearCache("rssSources")
                            }
                        }
                    }, [t._v(" 清空RSS源缓存 "), e("span", [t._v(t._s(t.localCacheStats.rssSources))])]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.clearCache("chapterList")
                            }
                        }
                    }, [t._v(" 清空章节列表缓存 "), e("span", [t._v(t._s(t.localCacheStats.chapterList))])]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.clearCache("chapterContent")
                            }
                        }
                    }, [t._v(" 清空章节内容缓存 "), e("span", [t._v(t._s(t.localCacheStats.chapterContent))])]), e("el-tag", {
                        staticClass: "setting-btn",
                        attrs: {
                            type: "info",
                            effect: t.$store.getters.isNight ? "dark" : "light"
                        },
                        on: {
                            click: function(e) {
                                return t.clearCache("ttsData")
                            }
                        }
                    }, [t._v(" 清空TTS缓存 "), e("span", [t._v(t._s(t.localCacheStats.ttsData))])])], 1)])]), e("div", {
                        staticClass: "bottom-icons"
                    }, [e("a", {
                        attrs: {
                            href: "https://github.com/hectorqin/reader",
                            target: "_blank"
                        }
                    }, [e("div", {
                        staticClass: "bottom-icon"
                    }, [t.isNight ? e("img", {
                        attrs: {
                            src: o("fa39"),
                            alt: ""
                        }
                    }) : e("img", {
                        attrs: {
                            src: o("0d98"),
                            alt: ""
                        }
                    })])]), e("span", {
                        ref: "themes",
                        staticClass: "theme-item",
                        style: t.themeColor,
                        on: {
                            click: t.toogleNight
                        }
                    }, [t.isNight ? e("i", {
                        staticClass: "el-icon-sunny"
                    }) : e("i", {
                        staticClass: "el-icon-moon"
                    })])])]) : t._e(), e("div", {
                        ref: "shelfWrapper",
                        staticClass: "shelf-wrapper",
                        class: t.isWebApp && !t.isNight ? "status-bar-light-bg" : "",
                        on: {
                            click: t.hideMenu
                        }
                    }, [e("div", {
                        staticClass: "shelf-title"
                    }, [t.$store.getters.isNormalPage && t.collapseMenu ? e("i", {
                        staticClass: "el-icon-menu",
                        on: {
                            click: function(e) {
                                return e.stopPropagation(), t.toggleMenu.apply(null, arguments)
                            }
                        }
                    }) : t._e(), t._v(" " + t._s(t.isSearchResult ? t.isExploreResult ? "探索" : t.isShowSearchBookSourceListDesc ? "书源" : "搜索" : "书架") + " (" + t._s(t.bookList.length) + ") "), t.$store.getters.isNormalPage && t.isSearchResult ? e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.backToShelf
                        }
                    }, [t._v(" 书架 ")]) : t._e(), t.$store.getters.isNormalPage && t.isSearchResult && !t.isShowSearchBookSourceListDesc ? e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.loadMore
                        }
                    }, [t.loadingMore ? e("i", {
                        staticClass: "el-icon-loading"
                    }) : t._e(), t._v(" " + t._s(t.loadingMore ? "加载中..." : "加载更多") + " ")]) : t._e(), t.$store.getters.isNormalPage && t.isSearchResult && t.isShowSearchBookSourceListDesc ? e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.displaySearchResult
                        }
                    }, [t._v(" " + t._s("返回搜索") + " ")]) : t._e(), t.$store.getters.isNormalPage && !t.isSearchResult ? e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: function(e) {
                                t.showBookEditButton = !t.showBookEditButton
                            }
                        }
                    }, [t._v(" " + t._s(t.showBookEditButton ? "取消" : "编辑") + " ")]) : t._e(), t.isSearchResult ? t._e() : e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.refreshShelf
                        }
                    }, [t.refreshLoading ? e("i", {
                        staticClass: "el-icon-loading"
                    }) : t._e(), t._v(" " + t._s(t.refreshLoading ? "刷新中..." : "刷新") + " ")]), t.$store.getters.isNormalPage && !t.isSearchResult ? e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.showRssDialog
                        }
                    }, [t._v(" RSS ")]) : t._e(), !t.$store.getters.isNormalPage || t.isSearchResult && !t.isExploreResult ? t._e() : e("div", {
                        staticClass: "title-btn",
                        on: {
                            click: t.showExplorePop
                        }
                    }, [t._v(" 书海 ")]), t.showBookEditButton ? e("el-input", {
                        attrs: {
                            size: "mini",
                            placeholder: "搜索书架"
                        },
                        model: {
                            value: t.keyword,
                            callback: function(e) {
                                t.keyword = e
                            },
                            expression: "keyword"
                        }
                    }) : t._e()], 1), t.isSearchResult ? t._e() : e("div", {
                        staticClass: "book-group-wrapper"
                    }, [e("el-tabs", {
                        staticClass: "book-group-tabs",
                        attrs: {
                            stretch: ""
                        },
                        model: {
                            value: t.showBookGroupString,
                            callback: function(e) {
                                t.showBookGroupString = e
                            },
                            expression: "showBookGroupString"
                        }
                    }, t._l(t.bookGroupDisplayList, (function(t) {
                        return e("el-tab-pane", {
                            key: "bookGroup-" + t.groupId,
                            attrs: {
                                label: t.groupName,
                                name: "" + t.groupId
                            }
                        })
                    })), 1)], 1), e("div", {
                        ref: "bookList",
                        staticClass: "books-wrapper",
                        on: {
                            touchstart: t.handleTouchStart,
                            touchmove: t.handleTouchMove,
                            touchend: t.handleTouchEnd,
                            scroll: t.scrollHandler
                        }
                    }, [!t.isSearchResult && "list" !== t.shelfConfig.viewCate || "yes" !== t.shelfConfig.virtualOptimize ? t.isSearchResult || "list" === t.shelfConfig.viewCate ? e("BookList", {
                        ref: "bookListComp",
                        attrs: {
                            bookList: t.bookList,
                            showNavigation: t.showNavigation,
                            isSearchResult: t.isSearchResult,
                            showBookEditButton: t.showBookEditButton,
                            showSourceCount: !t.isShowSearchBookSourceListDesc
                        },
                        on: {
                            showBookInfoDialog: t.showBookInfoDialog,
                            toDetail: t.toDetail,
                            deleteBook: t.deleteBook,
                            editBook: t.editBook,
                            addBookToShelf: t.addBookToShelf,
                            showSearchBookSourceList: t.showSearchBookSourceList
                        }
                    }) : e("BookColumn", {
                        ref: "bookListComp",
                        attrs: {
                            bookList: t.bookList,
                            showNavigation: t.showNavigation,
                            isSearchResult: t.isSearchResult,
                            showBookEditButton: t.showBookEditButton
                        },
                        on: {
                            showBookInfoDialog: t.showBookInfoDialog,
                            toDetail: t.toDetail,
                            deleteBook: t.deleteBook,
                            editBook: t.editBook,
                            addBookToShelf: t.addBookToShelf
                        }
                    }) : e("BookVirtualList", {
                        ref: "bookListComp",
                        attrs: {
                            bookList: t.bookList,
                            showNavigation: t.showNavigation,
                            isSearchResult: t.isSearchResult,
                            showBookEditButton: t.showBookEditButton,
                            showSourceCount: !t.isShowSearchBookSourceListDesc
                        },
                        on: {
                            showBookInfoDialog: t.showBookInfoDialog,
                            toDetail: t.toDetail,
                            deleteBook: t.deleteBook,
                            editBook: t.editBook,
                            addBookToShelf: t.addBookToShelf,
                            showSearchBookSourceList: t.showSearchBookSourceList
                        }
                    })], 1)]), t.$store.getters.isNormalPage ? e("el-dialog", {
                        class: t.isWebApp && !t.isNight ? "status-bar-light-bg" : "",
                        attrs: {
                            title: t.isImportRssSource ? "导入RSS源" : "导入书源",
                            visible: t.showImportSourceDialog,
                            width: t.dialogWidth,
                            top: this.collapseMenu ? "0" : "15vh",
                            fullscreen: t.collapseMenu
                        },
                        on: {
                            "update:visible": function(e) {
                                t.showImportSourceDialog = e
                            }
                        }
                    }, [e("div", {
                        staticClass: "source-container source-list-container"
                    }, [e("el-checkbox-group", {
                        on: {
                            change: t.handleCheckedSourcesChange
                        },
                        model: {
                            value: t.checkedSourceIndex,
                            callback: function(e) {
                                t.checkedSourceIndex = e
                            },
                            expression: "checkedSourceIndex"
                        }
                    }, t._l(t.importSourceList, (function(o, s) {
                        return e("el-checkbox", {
                            key: s,
                            staticClass: "source-checkbox",
                            attrs: {
                                label: s
                            }
                        }, [t._v(t._s(t.isImportRssSource ? o.sourceName : o.bookSourceName) + " " + t._s(t.isImportRssSource ? o.sourceUrl : o.bookSourceUrl) + " " + t._s(t.getSourceTag(o)))])
                    })), 1)], 1), e("div", {
                        staticClass: "dialog-footer",
                        attrs: {
                            slot: "footer"
                        },
                        slot: "footer"
                    }, [e("el-checkbox", {
                        staticClass: "float-left checkbox-btn",
                        attrs: {
                            indeterminate: t.isIndeterminate,
                            border: "",
                            size: "medium"
                        },
                        on: {
                            change: t.handleCheckAllChange
                        },
                        model: {
                            value: t.checkAll,
                            callback: function(e) {
                                t.checkAll = e
                            },
                            expression: "checkAll"
                        }
                    }, [t._v("全选")]), e("el-checkbox", {
                        staticClass: "float-left checkbox-btn",
                        attrs: {
                            border: "",
                            size: "medium"
                        },
                        on: {
                            change: t.handleCheckWebviewChange
                        }
                    }, [t._v("Webview 源")]), e("el-checkbox", {
                        staticClass: "float-left checkbox-btn",
                        attrs: {
                            border: "",
                            size: "medium"
                        },
                        on: {
                            change: t.handleCheckJSChange
                        }
                    }, [t._v("JavaScript 源")]), e("span", {
                        staticClass: "check-tip"
                    }, [t._v("已选择 " + t._s(t.checkedSourceIndex.length) + " 个")]), e("el-button", {
                        attrs: {
                            size: "medium"
                        },
                        on: {
                            click: function(e) {
                                t.showImportSourceDialog = !1, t.checkedSourceIndex = []
                            }
                        }
                    }, [t._v("取消")]), e("el-button", {
                        attrs: {
                            size: "medium",
                            type: "primary"
                        },
                        on: {
                            click: t.saveSourceList
                        }
                    }, [t._v("确定")])], 1)]) : t._e(), t.$store.getters.isNormalPage ? e("el-dialog", {
                        class: t.isWebApp && !t.isNight ? "status-bar-light-bg-dialog" : "",
                        attrs: {
                            visible: t.showBookSourceManageDialog,
                            width: t.dialogWidth,
                            top: t.dialogTop,
                            fullscreen: t.collapseMenu
                        },
                        on: {
                            "update:visible": function(e) {
                                t.showBookSourceManageDialog = e
                            },
                            closed: function(e) {
                                t.isShowFailureBookSource = !1, t.showSourceGroup = "全部"
                            }
                        }
                    }, [e("div", {
                        staticClass: "custom-dialog-title",
                        attrs: {
                            slot: "title"
                        },
                        slot: "title"
                    }, [e("span", {
                        staticClass: "el-dialog__title"
                    }, [t._v(t._s(t.isShowFailureBookSource ? "失效书源管理" : "书源管理") + " "), t.isShowFailureBookSource ? t._e() : e("span", {
                        staticClass: "float-right span-btn",
                        on: {
                            click: function(e) {
                                return t.deleteAllBookSource()
                            }
                        }
                    }, [t._v("清空")]), t.isShowFailureBookSource ? t._e() : e("span", {
                        staticClass: "float-right span-btn",
                        on: {
                            click: function(e) {
                                return t.deleteBookSourceFile()
                            }
                        }
                    }, [t._v("恢复默认")]), t.isShowFailureBookSource ? t._e() : e("span", {
                        staticClass: "float-right span-btn",
                        on: {
                            click: function(e) {
                                return t.exportBookSource()
                            }
                        }
                    }, [t._v("导出")]), t.isShowFailureBookSource ? t._e() : e("span", {
                        staticClass: "float-right span-btn",
                        on: {
                            click: function(e) {
                                return t.editBookSource(!1)
                            }
                        }
                    }, [t._v("新增")])])]), e("div", {
                        staticClass: "source-container table-container"
                    }, [t.isShowFailureBookSource ? e("div", {
                        staticClass: "check-form"
                    }, [e("span", {
                        staticClass: "check-form-label"
                    }, [t._v("搜索词：")]), e("el-input", {
                        attrs: {
                            size: "small"
                        },
                        model: {
                            value: t.checkBookSourceConfig.keyword,
                            callback: function(e) {
                                t.$set(t.checkBookSourceConfig, "keyword", e)
                            },
                            expression: "checkBookSourceConfig.keyword"
                        }
                    }), e("span", {
                        staticClass: "check-form-label",
                        staticStyle: {
                            "min-width": "68px"
                        }
                    }, [t._v(" 超时(ms)： ")]), e("el-input-number", {
                        attrs: {
                            min: 1e3,
                            max: 15e3,
                            step: 500,
                            size: "small"
                        },
                        model: {
                            value: t.checkBookSourceConfig.timeout,
                            callback: function(e) {
                                t.$set(t.checkBookSourceConfig, "timeout", e)
                            },
                            expression: "checkBookSourceConfig.timeout"
                        }
                    }), e("span", {
                        staticClass: "check-form-label"
                    }, [t._v("并发数：")]), e("el-input-number", {
                        attrs: {
                            min: 3,
                            max: 15,
                            step: 1,
                            size: "small"
                        },
                        model: {
                            value: t.checkBookSourceConfig.concurrent,
                            callback: function(e) {
                                t.$set(t.checkBookSourceConfig, "concurrent", e)
                            },
                            expression: "checkBookSourceConfig.concurrent"
                        }
                    })], 1) : t._e(), e("div", {
                        staticClass: "source-group-wrapper"
                    }, [e("el-tabs", {
                        staticClass: "booksource-group-tabs",
                        attrs: {
                            stretch: ""
                        },
                        model: {
                            value: t.showSourceGroup,
                            callback: function(e) {
                                t.showSourceGroup = e
                            },
                            expression: "showSourceGroup"
                        }
                    }, t._l(t.bookSourceShowGroup, (function(t) {
                        return e("el-tab-pane", {
                            key: "sourceGroupTab-" + t,
                            attrs: {
                                label: t,
                                name: t
                            }
                        })
                    })), 1)], 1), e("el-table", {
                        key: t.isShowFailureBookSource,
                        attrs: {
                            data: t.bookSourceShowResultPageList,
                            height: t.dialogContentHeight - 42 - 42 - (t.isShowFailureBookSource ? 32 : 0)
                        },
                        on: {
                            "selection-change": function(e) {
                                t.manageSourceSelection = e
                            }
                        }
                    }, [e("el-table-column", {
                        attrs: {
                            type: "selection",
                            width: "25",
                            fixed: t.$store.state.miniInterface,
                            selectable: t.isBookSourceSelectable
                        }
                    }), e("el-table-column", {
                        attrs: {
                            property: "bookSourceName",
                            label: "书源名称",
                            "min-width": "60",
                            sortable: "",
                            fixed: t.$store.state.miniInterface
                        }
                    }), e("el-table-column", {
                        attrs: {
                            property: "bookSourceUrl",
                            label: "书源链接",
                            "min-width": "120"
                        },
                        scopedSlots: t._u([{
                            key: "default",
                            fn: function(o) {
                                return [e("el-link", {
                                    attrs: {
                                        type: "primary",
                                        href: o.row.bookSourceUrl,
                                        target: "_blank"
                                    }
                                }, [t._v(t._s(o.row.bookSourceUrl))])]
                            }
                        }], null, !1, 2464495659)
                    }), t.isShowFailureBookSource ? e("el-table-column", {
                        attrs: {
                            property: "errorMsg",
                            label: "错误信息",
                            sortable: "",
                            "min-width": "120"
                        }
                    }) : t._e(), e("el-table-column", {
                        attrs: {
                            label: "书架书籍",
                            "min-width": "120"
                        },
                        scopedSlots: t._u([{
                            key: "default",
                            fn: function(o) {
                                return [e("pre", [t._v(t._s(t.showSourceBook(o.row)))])]
                            }
                        }], null, !1, 1425242229)
                    }), t.isShowFailureBookSource ? t._e() : e("el-table-column", {
                        attrs: {
                            width: "120px"
                        },
                        scopedSlots: t._u([{
                            key: "default",
                            fn: function(o) {
                                return [e("el-button", {
                                    attrs: {
                                        type: "text"
                                    },
                                    on: {
                                        click: function(e) {
                                            return t.editBookSource(o.row)
                                        }
                                    }
                                }, [t._v("编辑")]), e("el-button", {
                                    attrs: {
                                        type: "text"
                                    },
                                    on: {
                                        click: function(e) {
                                            return t.editBookSourceHeader(o.row)
                                        }
                                    }
                                }, [t._v("请求头")])]
                            }
                        }], null, !1, 3106287277)
                    }, [e("template", {
                        slot: "header"
                    }, [e("el-input", {
                        attrs: {
                            size: "mini",
                            placeholder: "搜索书源"
                        },
                        model: {
                            value: t.bookSourceKeyword,
                            callback: function(e) {
                                t.bookSourceKeyword = e
                            },
                            expression: "bookSourceKeyword"
                        }
                    })], 1)], 2)], 1)], 1), e("div", {
                        staticClass: "dialog-footer",
                        attrs: {
                            slot: "footer"
                        },
                        slot: "footer"
                    }, [e("div", [e("el-button", {
                        staticClass: "float-left",
                        attrs: {
                            type: "primary",
                            size: "medium"
                        },
                        on: {
                            click: t.deleteBookSourceList
                        }
                    }, [t._v("批量删除"), t.manageSourceSelection.length ? e("span", [t._v(" (" + t._s(t.manageSourceSelection.length) + ")")]) : t._e()]), t.isShowFailureBookSource ? e("el-button", {
                        staticStyle: {
                            "margin-bottom": "5px"
                        },
                        attrs: {
                            type: "primary",
                            size: "medium"
                        },
                        on: {
                            click: t.checkBookSource
                        }
                    }, [t._v(t._s(t.isCheckingBookSource ? "正在" : "") + "检测书源 " + t._s(t.checkBookSourceTip))]) : t._e()], 1), e("div", {
                        staticClass: "source-pagination"
                    }, [e("el-pagination", {
                        attrs: {
                            "current-page": t.bookSourcePagination.page,
                            "page-sizes": [25, 50, 100, 200, 300, 400],
                            "page-size": t.bookSourcePagination.size,
                            layout: "total, sizes, prev, pager, next",
                            total: t.bookSourceShowLength,
                            "pager-count": t.collapseMenu ? 5 : 7
                        },
                        on: {
                            "update:currentPage": function(e) {
                                return t.$set(t.bookSourcePagination, "page", e)
                            },
                            "update:current-page": function(e) {
                                return t.$set(t.bookSourcePagination, "page", e)
                            },
                            "update:pageSize": function(e) {
                                return t.$set(t.bookSourcePagination, "size", e)
                            },
                            "update:page-size": function(e) {
                                return t.$set(t.bookSourcePagination, "size", e)
                            }
                        }
                    })], 1)])]) : t._e(), t.$store.getters.isNormalPage ? e("el-dialog", {
                        class: t.isWebApp && !t.isNight ? "status-bar-light-bg-dialog" : "",
                        attrs: {
                            title: "导入本地书籍" + t.importMultiBookTip,
                            visible: t.showImportBookDialog,
                            width: t.dialogSmallWidth,
                            top: t.dialogTop,
                            fullscreen: t.collapseMenu
                        },
                        on: {
                            "update:visible": function(e) {
                                t.showImportBookDialog = e
                            },
                            closed: t.importBookDialogClosed
                        }
                    }, [e("div", {
                        staticClass: "source-container table-container"
                    }, [e("div", {
                        staticClass: "check-form"
                    }, [e("div", {
                        staticClass: "book-cover"
                    }, [e("el-image", {
                        key: t.getBookCoverUrl(t.importBookInfo),
                        staticClass: "cover",
                        attrs: {
                            src: t.getCover(t.getBookCoverUrl(t.importBookInfo), !0),
                            fit: "cover",
                            lazy: ""
                        }
                    })], 1), e("div", {
                        staticClass: "book-info"
                    }, [e("div", [e("span", [t._v("书名：")]), e("el-input", {
                        attrs: {
                            size: "small"
                        },
                        model: {
                            value: t.importBookInfo.name,
                            callback: function(e) {
                                t.$set(t.importBookInfo, "name", e)
                            },
                            expression: "importBookInfo.name"
                        }
                    })], 1), e("div", [e("span", [t._v("作者：")]), e("el-input", {
                        attrs: {
                            size: "small"
                        },
                        model: {
                            value: t.importBookInfo.author,
                            callback: function(e) {
                                t.$set(t.importBookInfo, "author", e)
                            },
                            expression: "importBookInfo.author"
                        }
                    })], 1), e("div", [e("span", [t._v("分组：")]), e("el-select", {
                        attrs: {
                            size: "mini",
                            filterable: "",
                            multiple: "",
                            placeholder: "未分组"
                        },
                        model: {
                            value: t.importBookGroup,
                            callback: function(e) {
                                t.importBookGroup = e
                            },
                            expression: "importBookGroup"
                        }
                    }, t._l(t.bookGroupSetList, (function(t, o) {
                        return e("el-option", {
                            key: "bookGroup-" + o,
                            attrs: {
                                label: t.groupName,
                                value: t.groupId
                            }
                        })
                    })), 1)], 1), t.isShowTocRule ? e("div", [e("span", [t._v("规则：")]), e("el-select", {
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "内置规则"
                        },
                        model: {
                            value: t.importUsedTxtRule,
                            callback: function(e) {
                                t.importUsedTxtRule = e
                            },
                            expression: "importUsedTxtRule"
                        }
                    }, t._l(t.tocRuleList, (function(t, o) {
                        return e("el-option", {
                            key: "txtTocRule-" + o,
                            attrs: {
                                label: t.name,
                                value: t.rule
                            }
                        })
                    })), 1), e("el-button", {
                        staticClass: "toc-refresh-btn",
                        attrs: {
                            type: "text"
                        },
                        on: {
                            click: function(e) {
                                return t.getChapterListByRule()
                            }
                        }
                    }, [t._v("刷新目录")])], 1) : t._e(), t.isShowTocRule ? e("div", [e("el-input", {
                        attrs: {
                            type: "textarea",
                            rows: 2,
                            size: "small"
                        },
                        model: {
                            value: t.importBookInfo.tocUrl,
                            callback: function(e) {
                                t.$set(t.importBookInfo, "tocUrl", e)
                            },
                            expression: "importBookInfo.tocUrl"
                        }
                    })], 1) : t._e()])]), e("div", {
                        staticClass: "chapter-title"
                    }, [t._v(" 章节列表(" + t._s(t.importBookChapters.length) + ") ")]), e("div", {
                        staticClass: "chapter-list",
                        style: {
                            maxHeight: t.dialogContentHeight - 40 - 35 + "px"
                        }
                    }, t._l(t.importBookChapters, (function(o, s) {
                        return e("p", {
                            key: s
                        }, [t._v(" " + t._s(s + 1) + ". " + t._s(o.title) + " ")])
                    })), 0)]), e("div", {
                        staticClass: "dialog-footer",
                        attrs: {
                            slot: "footer"
                        },
                        slot: "footer"
                    }, [e("el-button", {
                        attrs: {
                            type: "primary",
                            size: "medium"
                        },
                        on: {
                            click: function(e) {
                                return t.saveBook(t.importBookInfo, !0)
                            }
                        }
                    }, [t._v("确定导入")]), e("el-button", {
                        attrs: {
                            size: "medium"
                        },
                        on: {
                            click: function(e) {
                                t.showImportBookDialog = !1
                            }
                        }
                    }, [t._v("取消")])], 1)]) : t._e(), e("ShelfSettings", {
                        model: {
                            value: t.showShelfSettingsDialog,
                            callback: function(e) {
                                t.showShelfSettingsDialog = e
                            },
                            expression: "showShelfSettingsDialog"
                        }
                    })], 1)
                };
