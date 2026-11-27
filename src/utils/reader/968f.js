// 恢复自 reader-pro-3.2.14 前端产物 beauty/reader.js 的 webpack 模块 968f（逐字切片，形参 e, t, n）
const e = module;
const t = module.exports;
const n = require;

            "use strict";
            n.r(t);
            n("99af"), n("b0c0");
            var a = function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        ref: "chapterWrapperRef",
                        staticClass: "chapter-wrapper",
                        class: {
                            night: e.isNight,
                            day: !e.isNight,
                            "mini-interface": e.$store.state.miniInterface
                        },
                        style: e.bodyTheme
                    }, [t("div", {
                        staticClass: "tool-bar",
                        style: e.leftBarTheme
                    }, [t("div", {
                        staticClass: "tools"
                    }, [t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: e.toShelf
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("首页")])]), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popBookShelfVisible,
                            callback: function(t) {
                                e.popBookShelfVisible = t
                            },
                            expression: "popBookShelfVisible"
                        }
                    }, [t("BookShelf", {
                        ref: "popBookShelf",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popBookShelfVisible
                        },
                        on: {
                            changeBook: e.changeBook,
                            toShelf: e.toShelf
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("书架")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popBookSourceVisible,
                            callback: function(t) {
                                e.popBookSourceVisible = t
                            },
                            expression: "popBookSourceVisible"
                        }
                    }, [t("BookSource", {
                        ref: "popBookSource",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popBookSourceVisible
                        },
                        on: {
                            changeBookSource: function(t) {
                                return e.changeBookSource()
                            },
                            close: function(t) {
                                e.popBookSourceVisible = !1
                            }
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "tool-el-icon"
                    }, [t("i", {
                        staticClass: "el-icon-menu"
                    })]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("书源")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.popCataVisible,
                            callback: function(t) {
                                e.popCataVisible = t
                            },
                            expression: "popCataVisible"
                        }
                    }, [t("PopCata", {
                        ref: "popCata",
                        staticClass: "popup",
                        attrs: {
                            visible: e.popCataVisible
                        },
                        on: {
                            gotoChapter: e.gotoChapter,
                            refresh: e.refreshCatalog,
                            close: function(t) {
                                e.popCataVisible = !1
                            }
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("目录")])])], 1), t("el-popover", {
                        attrs: {
                            placement: "right",
                            width: e.popperWidth,
                            trigger: "click",
                            "visible-arrow": !1,
                            "popper-class": "popper-component"
                        },
                        model: {
                            value: e.readSettingsVisible,
                            callback: function(t) {
                                e.readSettingsVisible = t
                            },
                            expression: "readSettingsVisible"
                        }
                    }, [t("ReadSettings", {
                        staticClass: "popup",
                        attrs: {
                            visible: e.readSettingsVisible
                        },
                        on: {
                            close: function(t) {
                                e.readSettingsVisible = !1
                            },
                            showClickZone: function(t) {
                                e.showClickZone = !0
                            },
                            readMethodChange: e.beforeReadMethodChange
                        }
                    }), t("div", {
                        staticClass: "tool-icon",
                        attrs: {
                            slot: "reference"
                        },
                        slot: "reference"
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("设置")])])], 1), e.$store.state.miniInterface ? e._e() : t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toTop(0)
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("顶部")])]), e.$store.state.miniInterface ? e._e() : t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toBottom(0)
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), t("div", {
                        staticClass: "icon-text"
                    }, [e._v("底部")])])], 1)]), t("div", {
                        staticClass: "read-bar",
                        style: e.rightBarTheme
                    }, [t("div", {
                        staticClass: "float-btn-zone"
                    }, [t("div", {
                        staticClass: "float-left-btn-zone"
                    }, [e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showBookmarkDialog
                        }
                    }, [t("i", {
                        staticClass: "el-icon-collection-tag"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showSearchBookContentDialog
                        }
                    }, [t("i", {
                        staticClass: "el-icon-search"
                    })]), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showReadingBookInfo
                        }
                    }, [t("i", {
                        staticClass: "el-icon-info"
                    })]), !e.$store.state.miniInterface || e.isSlideRead || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toTop(0)
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-top"
                    })]), !e.$store.state.miniInterface || e.isSlideRead || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toBottom(0)
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-bottom"
                    })])]), t("div", {
                        staticClass: "float-right-btn-zone"
                    }, [e.isAudio || e.isVideo || e.isEpub || e.isCbz || e.isPdf ? e._e() : t("div", {
                        staticClass: "float-btn",
                        class: e.isEditContent ? "editing" : "",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toggleEditContent()
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-edit"
                    })]), e.isAudio || e.isVideo || e.isEpub ? e._e() : t("div", {
                        staticClass: "float-btn",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.showCacheContent
                        }
                    }, [t("i", {
                        staticClass: "el-icon-download"
                    })]), e.isPdf ? t("div", {
                        staticClass: "float-btn",
                        staticStyle: {
                            "line-height": "36px"
                        },
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.readOriginal
                        }
                    }, [t("i", {
                        staticClass: "el-icon-reading"
                    })]) : e._e(), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.refreshContent
                        }
                    }, [t("i", {
                        staticClass: "el-icon-refresh-right"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        class: e.autoReading ? "auto-reading" : "",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                return e.toggleAutoReading()
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-view"
                    })]), e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: function(t) {
                                e.showReadBar = !e.showReadBar
                            }
                        }
                    }, [t("i", {
                        staticClass: "el-icon-headset"
                    })]), t("div", {
                        staticClass: "float-btn",
                        style: e.popupAbsoluteBtnStyle,
                        on: {
                            click: e.toogleNight
                        }
                    }, [e.isNight ? t("i", {
                        staticClass: "el-icon-sunny"
                    }) : t("i", {
                        staticClass: "el-icon-moon"
                    })])])]), !e.$store.state.miniInterface || e.isAudio || e.isVideo ? e._e() : t("div", {
                        staticClass: "progress"
                    }, [t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 1,
                            max: e.totalPages,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.showPage,
                            input: function(t) {
                                e.progressValue = t
                            }
                        },
                        model: {
                            value: e.currentPage,
                            callback: function(t) {
                                e.currentPage = t
                            },
                            expression: "currentPage"
                        }
                    })], 1), t("span", {
                        staticClass: "progress-tip"
                    }, [e._v(e._s(e.formatProgressTip()))])]), e.showCacheContentZone ? t("div", {
                        staticClass: "cache-content-zone"
                    }, [t("div", [e._v(" 缓存章节 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(50)
                            }
                        }
                    }, [e._v(" 后面50章 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(100)
                            }
                        }
                    }, [e._v(" 后面100章 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isCachingContent,
                            expression: "!isCachingContent"
                        }],
                        staticClass: "cache-content-btn",
                        on: {
                            click: function(t) {
                                return e.cacheChapterContent(!0)
                            }
                        }
                    }, [e._v(" 后面全部 ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isCachingContent,
                            expression: "isCachingContent"
                        }],
                        staticClass: "caching-tip"
                    }, [e._v(" " + e._s(e.cachingContentTip) + " ")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isCachingContent,
                            expression: "isCachingContent"
                        }],
                        staticClass: "caching-cancel-btn",
                        on: {
                            click: e.cancelCaching
                        }
                    }, [t("i", {
                        staticClass: "el-icon-close"
                    })])]) : e._e(), t("div", {
                        staticClass: "tools"
                    }, [t("div", {
                        staticClass: "tool-icon progress-text",
                        on: {
                            click: e.showCacheContent
                        }
                    }, [e.$store.state.miniInterface ? t("span", [e._v("阅读进度: ")]) : e._e(), e._v(" " + e._s(e.readingProgress) + " ")]), t("div", {
                        staticClass: "tool-icon",
                        style: e.$store.state.miniInterface ? {
                            order: -1
                        } : {},
                        on: {
                            click: function(t) {
                                return e.toLastChapter()
                            }
                        }
                    }, [t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")]), e.$store.state.miniInterface ? t("span", [e._v("上一章")]) : e._e()]), t("div", {
                        staticClass: "tool-icon",
                        on: {
                            click: function(t) {
                                return e.toNextChapter()
                            }
                        }
                    }, [e.$store.state.miniInterface ? t("span", [e._v("下一章")]) : e._e(), t("div", {
                        staticClass: "iconfont"
                    }, [e._v("  ")])])])]), t("div", {
                        staticClass: "read-bar",
                        style: e.readBarTheme
                    }, [t("div", {
                        staticClass: "reader-bar-inner"
                    }, [t("div", {
                        staticClass: "operate-bar"
                    }, [t("div", {
                        staticClass: "close-btn",
                        on: {
                            click: e.exitRead
                        }
                    }, [t("i", {
                        staticClass: "el-icon-close"
                    })]), t("div", {
                        staticClass: "center"
                    }, [t("span", {
                        staticClass: "ctrl-btn",
                        on: {
                            click: e.speechPrev
                        }
                    }, [e._v("上一段")]), t("span", {
                        staticClass: "play-pause-btn",
                        on: {
                            click: e.toggleSpeech
                        }
                    }, [e.speechSpeaking ? t("i", {
                        staticClass: "el-icon-video-pause",
                        style: e.popupAbsoluteBtnStyle
                    }) : t("i", {
                        staticClass: "el-icon-video-play",
                        style: e.popupAbsoluteBtnStyle
                    })]), t("span", {
                        staticClass: "ctrl-btn",
                        on: {
                            click: e.speechNext
                        }
                    }, [e._v("下一段")])]), t("div", {
                        staticClass: "collapse-btn",
                        on: {
                            click: function(t) {
                                e.showSpeechConfig = !e.showSpeechConfig
                            }
                        }
                    }, [e.showSpeechConfig ? t("i", {
                        staticClass: "el-icon-bottom"
                    }) : t("i", {
                        staticClass: "el-icon-top"
                    })])]), e.showSpeechConfig ? t("div", {
                        staticClass: "setting-item"
                    }, [t("div", {
                        staticClass: "setting-title"
                    }, [e._v(" 朗读方式 "), t("span", {
                        staticClass: "float-right",
                        on: {
                            click: e.showHttpTTSDialog
                        }
                    }, [e._v("HttpTTS 管理")])]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "voice-list"
                    }, [t("el-select", {
                        staticClass: "voice-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择朗读方式"
                        },
                        model: {
                            value: e.ttsType,
                            callback: function(t) {
                                e.ttsType = t
                            },
                            expression: "ttsType"
                        }
                    }, e._l(e.ttsTypeList, (function(e, n) {
                        return t("el-option", {
                            key: "tts-type-" + n,
                            attrs: {
                                label: e.name,
                                value: e.value
                            }
                        })
                    })), 1)], 1)]), "local" !== e.ttsType ? t("div", {
                        staticClass: "setting-oneline"
                    }, [t("span", {
                        staticClass: "setting-title"
                    }, [e._v("连读优化：")]), t("el-switch", {
                        attrs: {
                            "active-color": "#13ce66",
                            "inactive-color": "#ff4949",
                            "active-value": !0,
                            "inactive-value": !1
                        },
                        model: {
                            value: e.cacheTTSAudio,
                            callback: function(t) {
                                e.cacheTTSAudio = t
                            },
                            expression: "cacheTTSAudio"
                        }
                    })], 1) : e._e(), t("div", {
                        staticClass: "setting-title"
                    }, [e._v(" 语音库 ")]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "voice-list"
                    }, [t("el-select", {
                        staticClass: "voice-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "请选择语音库"
                        },
                        model: {
                            value: e.voiceName,
                            callback: function(t) {
                                e.voiceName = t
                            },
                            expression: "voiceName"
                        }
                    }, e._l(e.voiceList, (function(e, n) {
                        return t("el-option", {
                            key: "search-type-" + n,
                            attrs: {
                                label: e.LocalName || e.name,
                                value: e.name
                            }
                        })
                    })), 1)], 1)])]) : e._e(), e.showSpeechConfig ? t("div", {
                        staticClass: "setting-item"
                    }, [t("div", {
                        staticClass: "setting-title"
                    }, [e._v("语音设置")]), t("div", {
                        staticClass: "setting-value"
                    }, [t("div", {
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("语速")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: .5,
                            max: 2,
                            step: .1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechRate
                        },
                        model: {
                            value: e.speechRate,
                            callback: function(t) {
                                e.speechRate = t
                            },
                            expression: "speechRate"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn",
                        on: {
                            click: function(t) {
                                return e.changeSpeechRate(1)
                            }
                        }
                    }, [e._v("重置")])]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: "httpTTS" !== e.ttsType,
                            expression: "ttsType !== 'httpTTS'"
                        }],
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("语调")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 0,
                            max: 2,
                            step: .1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechPitch
                        },
                        model: {
                            value: e.speechPitch,
                            callback: function(t) {
                                e.speechPitch = t
                            },
                            expression: "speechPitch"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn",
                        on: {
                            click: function(t) {
                                return e.changeSpeechPitch(1)
                            }
                        }
                    }, [e._v("重置")])]), t("div", {
                        staticClass: "progress"
                    }, [t("span", {
                        staticClass: "progress-tip"
                    }, [e._v("定时")]), t("div", {
                        staticClass: "progress-bar"
                    }, [t("el-slider", {
                        attrs: {
                            min: 0,
                            max: 180,
                            step: 1,
                            "show-tooltip": !1
                        },
                        on: {
                            change: e.changeSpeechMinutes
                        },
                        model: {
                            value: e.speechMinutes,
                            callback: function(t) {
                                e.speechMinutes = t
                            },
                            expression: "speechMinutes"
                        }
                    })], 1), t("span", {
                        staticClass: "setting-btn"
                    }, [e._v(e._s(e.speechMinutes) + "分钟")])])])]) : e._e()])]), t("div", {
                        ref: "content",
                        staticClass: "chapter",
                        class: e.chapterClass,
                        style: e.chapterTheme
                    }, [e.showClickZone ? t("div", {
                        staticClass: "click-zone",
                        style: e.isSlideRead ? {} : {
                            position: "fixed"
                        }
                    }, [t("div", {
                        style: e.showPrevPageStyle
                    }, [t("span", [e._v("点击前一页")])]), t("div", {
                        style: e.showMenuZoneStyle
                    }, [t("span", [e._v("点击显示菜单")])]), t("div", {
                        style: e.showNextPageStyle
                    }, [t("span", [e._v("点击后一页")])]), t("div", {
                        staticClass: "close-btn",
                        on: {
                            click: function(t) {
                                e.showClickZone = !1
                            }
                        }
                    }, [e._v("关闭")]), t("div", {
                        style: e.showPrevPageExtraStyle
                    }), t("div", {
                        style: e.showNextPageExtraStyle
                    })]) : e._e(), t("div", {
                        ref: "top",
                        staticClass: "top-bar"
                    }, [e._v(" " + e._s(e.$store.state.miniInterface ? e.title : "") + " "), e.$store.state.miniInterface && !e.isSlideRead ? t("span", {
                        staticClass: "right-timestr"
                    }, [e._v(e._s(e.timeStr))]) : e._e()]), t("div", {
                        staticClass: "content",
                        on: {
                            touchstart: e.handleTouchStart,
                            touchmove: e.handleTouchMove,
                            touchend: e.handleTouchEnd,
                            click: e.handlerClick
                        }
                    }, [e.show ? t("div", {
                        staticClass: "content-inner",
                        style: e.contentInnerStyle
                    }, [t("Content", {
                        ref: "bookContentRef",
                        staticClass: "book-content",
                        style: e.contentStyle,
                        attrs: {
                            title: e.title,
                            content: e.content,
                            showContent: e.show,
                            error: e.error,
                            showChapterList: e.showChapterList,
                            isScrollRead: e.isScrollRead,
                            isSlideRead: e.isSlideRead,
                            isEditContent: e.isEditContent
                        },
                        on: {
                            prevChapter: e.toLastChapter,
                            nextChapter: e.toNextChapter,
                            updateProgress: e.saveReadingPosition,
                            iframeLoad: function(t) {
                                return e.$emit("iframeLoad")
                            },
                            contentChange: function(t) {
                                return e.onContentChange()
                            },
                            epubClick: e.eventHandler,
                            epubLocationChange: e.epubLocationChangeHandler,
                            epubClickHash: e.epubClickHash,
                            epubKeydown: function(t) {
                                return e.keydownHandler(t, !0)
                            },
                            epubTouch: e.epubTouch
                        }
                    })], 1) : e._e()]), t("div", {
                        ref: "bottom",
                        staticClass: "bottom-bar"
                    }, [t("audio", {
                        ref: "ttsAudio",
                        attrs: {
                            preload: "preload",
                            src: "",
                            autoplay: ""
                        },
                        on: {
                            ended: e.onTTSAudioEnded,
                            error: e.onTTSAudioError,
                            play: e.onTTSAudioPlay,
                            pause: e.onTTSAudioPause
                        }
                    }), e.isSlideRead ? t("span", [e._v(e._s("第".concat(e.currentPage, "/").concat(e.totalPages, "页 ").concat(e.readingProgress)))]) : e._e(), e.isSlideRead ? t("span", [e._v(e._s(e.timeStr))]) : e._e(), !e.show || e.isSlideRead || e.error || e.isScrollRead ? e._e() : t("span", {
                        staticClass: "bottom-btn",
                        on: {
                            click: function(t) {
                                return e.toNextChapter()
                            }
                        }
                    }, [e._v("加载下一章")])])])])
                },
                u = [],
                o = n("c7eb"),
                r = n("1da1"),
                i = n("5530"),
                s = (n("d401"), n("4de4"), n("7db0"), n("4160"), n("c975"), n("e260"), n("d81d"), n("14d9"), n("fb6a"), n("4e82"), n("c19f"), n("ace4"), n("b420"), n("2c66"), n("249d"), n("40e9"), n("0d03"), n("c0b6"), n("e9f5"), n("910d"), n("f665"), n("7d54"), n("ab43"), n("cca6"), n("d3b7"), n("e25e"), n("4d63"), n("c607"), n("ac1f"), n("2c3e"), n("25f0"), n("8a79"), n("3ca3"), n("466d"), n("5319"), n("5b81"), n("841c"), n("1276"), n("2ca0"), n("5cc6"), n("907a"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n("d139"), n("3a7b"), n("986a"), n("1d02"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n("3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n("170b"), n("b39a"), n("6ce5"), n("2834"), n("72f7"), n("4ea1"), n("81b2"), n("159b"), n("ddb0"), n("0eb6"), n("b7ef"), n("8bd4"), n("4795"), n("2b3d"), n("bf19"), n("9861"), n("88a7"), n("271a"), n("5494"), function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "popup-wrapper",
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "title-zone"
                    }, [t("div", {
                        staticClass: "title"
                    }, [e._v(" 目录 "), e.catalog.length ? t("span", [e._v("(" + e._s(e.catalog.length) + ")")]) : e._e()]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isSearching,
                            expression: "!isSearching"
                        }],
                        class: {
                            "title-btn": !0
                        }
                    }, [e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.asc = !e.asc
                            }
                        }
                    }, [e._v(e._s(e.asc ? "倒序" : "顺序"))]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.toTop
                        }
                    }, [e._v("顶部")]) : e._e(), e.catalog.length ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.toBottom
                        }
                    }, [e._v("底部")]) : e._e(), "loc_book" === e.book.origin ? t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: e.changeRule
                        }
                    }, [e._v(" 修改规则 ")]) : e._e(), t("span", {
                        class: {
                            loading: e.refreshLoading,
                            "refresh-btn": !0
                        },
                        on: {
                            click: e.refreshChapter
                        }
                    }, [e.refreshLoading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.refreshLoading ? "刷新中..." : "刷新") + " ")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isSearching,
                            expression: "isSearching"
                        }],
                        class: {
                            "title-btn": !0
                        }
                    }, [t("el-input", {
                        staticClass: "booksource-filter-input",
                        attrs: {
                            size: "mini",
                            placeholder: "搜索目录"
                        },
                        model: {
                            value: e.searchKeyword,
                            callback: function(t) {
                                e.searchKeyword = t
                            },
                            expression: "searchKeyword"
                        }
                    }), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])], 1)]), t("div", {
                        ref: "cataData",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "cata"
                    }, ["yes" === e.$store.getters.shelfConfig.virtualOptimize ? t("PopCatalogVirtualList", {
                        ref: "virtList",
                        attrs: {
                            catalog: e.cataList,
                            cachedCataMap: e.cachedCataMap,
                            asc: e.asc,
                            visible: e.visible
                        },
                        on: {
                            gotoChapter: e.gotoChapter
                        }
                    }) : e._l(e.cataList, (function(n) {
                        return t("div", {
                            key: n.index,
                            ref: "cata",
                            refInFor: !0,
                            staticClass: "log",
                            class: {
                                selected: e.isSelected(n.index)
                            },
                            on: {
                                click: function(t) {
                                    return e.gotoChapter(n)
                                }
                            }
                        }, [t("div", {
                            class: {
                                "log-text": !0,
                                cached: e.cachedCataMap[n.index]
                            }
                        }, [e._v(" " + e._s(n.title) + " ")])])
                    }))], 2)])])
                }),
                l = [],
                c = (n("caad"), n("2532"), n("8efd")),
                p = n("be3b"),
                d = n("2b0e"),
                D = function() {
                    var e = this,
                        t = e._self._c;
                    return e.visible ? t("VirtList", {
                        ref: "virtList",
                        staticClass: "virtual-catalog",
                        attrs: {
                            itemKey: "index",
                            list: e.catalog,
                            minSize: 40
                        },
                        scopedSlots: e._u([{
                            key: "default",
                            fn: function(n) {
                                var a = n.itemData;
                                return [t("div", {
                                    ref: "data",
                                    staticClass: "log",
                                    class: {
                                        selected: e.isSelected(a.index)
                                    },
                                    on: {
                                        click: function(t) {
                                            return e.$emit("gotoChapter", a)
                                        }
                                    }
                                }, [t("div", {
                                    class: {
                                        "log-text": !0,
                                        "cached-text": e.cachedCataMap[a.index]
                                    }
                                }, [e._v(" " + e._s(a.title) + " ")])])]
                            }
                        }], null, !1, 650954991)
                    }) : e._e()
                },
                h = [],
                A = (n("c740"), n("febf")),
                F = {
                    name: "BookShelfVirtualList",
                    components: {
                        VirtList: A["a"]
                    },
                    props: ["catalog", "cachedCataMap", "asc", "visible"],
                    watch: {
                        visible: function() {
                            var e = this;
                            this.$nextTick((function() {
                                var t = e.catalog.findIndex((function(t) {
                                    return t.index === e.$store.getters.readingBook.index
                                }));
                                e.$refs.virtList && e.$refs.virtList.scrollToIndex(t >= 0 ? t : 0)
                            }))
                        }
                    },
                    methods: {
                        isSelected: function(e) {
                            return this.asc ? e === this.$store.getters.readingBook.index : this.catalog.length - 1 - e === this.$store.getters.readingBook.index
                        },
                        scrollToBottom: function() {
                            var e = this;
                            this.$nextTick((function() {
                                e.$refs.virtList && e.$refs.virtList.scrollToBottom()
                            }))
                        },
                        scrollToTop: function() {
                            var e = this;
                            this.$nextTick((function() {
                                e.$refs.virtList && e.$refs.virtList.scrollToTop()
                            }))
                        }
                    }
                },
                f = F,
                m = (n("a2c7"), n("2877")),
                C = Object(m["a"])(f, D, h, !1, null, null, null),
                E = C.exports,
                y = {
                    name: "PopCata",
                    components: {
                        PopCatalogVirtualList: E
                    },
                    data: function() {
                        return {
                            refreshLoading: !1,
                            asc: !0,
                            expand: !1,
                            cachedCataMap: {},
                            tocUrl: "",
                            isSearching: !1,
                            searchKeyword: ""
                        }
                    },
                    props: ["visible"],
                    computed: {
                        book: function() {
                            return this.$store.getters.readingBook
                        },
                        index: function() {
                            return this.$store.getters.readingBook.index
                        },
                        catalog: function() {
                            return this.$store.getters.readingBook.catalog || []
                        },
                        cataList: function() {
                            var e = this;
                            return this.isSearching && this.searchKeyword ? this.asc ? this.catalog.filter((function(t) {
                                return (t.title || "").includes(e.searchKeyword)
                            })) : [].concat(this.catalog).reverse().filter((function(t) {
                                return (t.title || "").includes(e.searchKeyword)
                            })) : this.asc ? this.catalog : [].concat(this.catalog).reverse()
                        },
                        theme: function() {
                            return this.$store.getters.config.theme
                        },
                        popupTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup
                            }
                        },
                        tocRuleList: function() {
                            return this.book && this.book.originName ? this.book.originName.toLowerCase().endsWith(".txt") ? this.$store.state.txtTocRules : [{
                                name: "根据 Spin 获取章节，使用 Toc 补充章节名",
                                rule: "spin+toc"
                            }, {
                                name: "根据 Spin 获取章节，强制使用 Toc 章节名",
                                rule: "spin<toc"
                            }, {
                                name: "根据 Spin 获取章节",
                                rule: "spin"
                            }, {
                                name: "根据 Toc 获取章节，使用 Spin 补充章节名",
                                rule: "toc+spin"
                            }, {
                                name: "根据 Toc 获取章节，强制使用 Spin 章节名",
                                rule: "toc<spin"
                            }, {
                                name: "根据 Toc 获取章节",
                                rule: "toc"
                            }] : []
                        }
                    },
                    mounted: function() {
                        window.popcatalogComp = this
                    },
                    watch: {
                        visible: function(e) {
                            var t = this;
                            e && (this.computeCachedCata(), this.$nextTick((function() {
                                t.jumpToCurrent()
                            })))
                        },
                        catalog: function() {
                            this.refreshLoading = !1, this.computeCachedCata()
                        },
                        index: function() {
                            this.computeCachedCata()
                        },
                        asc: function() {
                            this.jumpToCurrent()
                        }
                    },
                    methods: {
                        isSelected: function(e) {
                            return this.asc ? e === this.$store.getters.readingBook.index : this.catalog.length - 1 - e === this.$store.getters.readingBook.index
                        },
                        gotoChapter: function(e) {
                            var t = this.catalog.indexOf(e);
                            this.$emit("close"), this.$emit("gotoChapter", t)
                        },
                        refreshChapter: function() {
                            this.refreshLoading = !0, this.$emit("refresh")
                        },
                        changeRule: function() {
                            var e = this;
                            return Object(r["a"])(Object(o["a"])().mark((function t() {
                                var n, a;
                                return Object(o["a"])().wrap((function(t) {
                                    while (1) switch (t.prev = t.next) {
                                        case 0:
                                            return t.next = 2, e.$msgbox({
                                                title: "修改目录规则",
                                                message: e.renderComp(),
                                                showCancelButton: !0,
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消"
                                            })["catch"]((function(e) {
                                                return "close" === e && "close"
                                            }));
                                        case 2:
                                            if (n = t.sent, "confirm" !== n) {
                                                t.next = 12;
                                                break
                                            }
                                            if (e.tocUrl !== e.book.tocUrl) {
                                                t.next = 7;
                                                break
                                            }
                                            return e.$message.error("未修改规则"), t.abrupt("return");
                                        case 7:
                                            return a = e.$store.getters.shelfBooks.find((function(t) {
                                                return t.bookUrl === e.book.bookUrl
                                            })), a.tocUrl = e.tocUrl, t.abrupt("return", p["a"].post(e.api + "/saveBook", a).then((function(t) {
                                                t.data.isSuccess && (e.$message.success("操作成功"), e.$store.commit("updateShelfBook", t.data.data), e.refreshLoading = !0, e.$emit("refresh"))
                                            }), (function(t) {
                                                e.$message.error("操作失败" + (t && t.toString()))
                                            })));
                                        case 12:
                                            return t.abrupt("return", !1);
                                        case 13:
                                        case "end":
                                            return t.stop()
                                    }
                                }), t)
                            })))()
                        },
                        renderComp: function() {
                            this.$createElement;
                            var e = this.tocRuleList;
                            this.tocUrl = this.book.tocUrl;
                            var t = this;
                            d["default"].component("custComp2", {
                                render: function() {
                                    var t = this,
                                        n = arguments[0];
                                    return window.custComp2 = this, n("div", {
                                        style: {
                                            textAlign: "center"
                                        }
                                    }, [n("span", ["请选择规则："]), n("el-select", {
                                        attrs: {
                                            size: "mini",
                                            filterable: !0,
                                            placeholder: "未分组"
                                        },
                                        model: {
                                            value: t.selectedRule,
                                            callback: function(e) {
                                                t.selectedRule = e
                                            }
                                        },
                                        on: {
                                            change: this.change
                                        }
                                    }, [e.map((function(e, t) {
                                        return n("el-option", {
                                            key: "rule-" + t,
                                            attrs: {
                                                label: e.name,
                                                value: e.rule
                                            }
                                        })
                                    }))]), n("el-input", {
                                        attrs: {
                                            type: "textarea",
                                            rows: 3,
                                            size: "small"
                                        },
                                        style: {
                                            marginTop: "10px"
                                        },
                                        model: {
                                            value: t.selectedRule,
                                            callback: function(e) {
                                                t.selectedRule = e
                                            }
                                        },
                                        on: {
                                            change: this.change
                                        }
                                    })])
                                },
                                data: function() {
                                    return {
                                        selectedRule: t.tocUrl
                                    }
                                },
                                methods: {
                                    change: function() {
                                        t.tocUrl = this.selectedRule
                                    }
                                }
                            });
                            var n = d["default"].component("custComp2");
                            return this.$createElement(n)
                        },
                        jumpToCurrent: function(e) {
                            var t = this;
                            if ("yes" !== this.$store.getters.shelfConfig.virtualOptimize)
                                if ("undefined" === typeof e && (e = this.asc ? this.$store.getters.readingBook.index : this.catalog.length - 1 - this.$store.getters.readingBook.index), this.$refs.cata && this.$refs.cata[e]) {
                                    var n = this.$refs.cataData;
                                    Object(c["a"])(this.$refs.cata[e], {
                                        container: n,
                                        duration: 0
                                    })
                                } else setTimeout((function() {
                                    t.jumpToCurrent(e)
                                }), 10)
                        },
                        toTop: function() {
                            this.$refs.virtList ? this.$refs.virtList.scrollToTop() : this.jumpToCurrent(0)
                        },
                        toBottom: function() {
                            this.$refs.virtList ? this.$refs.virtList.scrollToBottom() : this.jumpToCurrent(this.catalog.length - 1)
                        },
                        computeCachedCata: function() {
                            var e = this,
                                t = {},
                                n = "localCache@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + this.$store.getters.readingBook.bookUrl + "@chapterContent-";
                            window.$cacheStorage.iterate((function(e, a) {
                                if (a.startsWith(n)) try {
                                    var u = parseInt(a.replace(n, ""));
                                    t[u] = !0
                                } catch (re) {}
                            })).then((function() {
                                e.cachedCataMap = t
                            }))["catch"]((function() {}))
                        }
                    }
                },
                g = y,
                b = (n("1a8c"), Object(m["a"])(g, s, l, !1, null, "4f5b9f48", null)),
                v = b.exports,
                B = function() {
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
                },
                k = [],
                w = n("ade3"),
                x = (n("a15b"), n("a434"), n("b64b"), n("07ac"), n("bfff")),
                S = n("8552"),
                N = n("7513"),
                T = n("63f0"),
                L = {
                    name: "ReadSettings",
                    data: function() {
                        return {
                            themeColors: [{
                                background: "rgba(250, 245, 235, 0.8)"
                            }, {
                                background: "rgba(245, 234, 204, 0.8)"
                            }, {
                                background: "rgba(230, 242, 230, 0.8)"
                            }, {
                                background: "rgba(228, 241, 245, 0.8)"
                            }, {
                                background: "rgba(245, 228, 228, 0.8)"
                            }, {
                                background: "rgba(224, 224, 224, 0.8)"
                            }, {
                                background: "rgba(0, 0, 0, 0.5)"
                            }, {
                                background: "rgba(255, 255, 255, 0.8)"
                            }],
                            builtinBG: [{
                                src: "bg/山水画.jpg"
                            }, {
                                src: "bg/山水墨影.jpg"
                            }, {
                                src: "bg/羊皮纸1.jpg"
                            }, {
                                src: "bg/护眼漫绿.jpg"
                            }, {
                                src: "bg/羊皮纸2.jpg"
                            }, {
                                src: "bg/新羊皮纸.jpg"
                            }, {
                                src: "bg/羊皮纸3.jpg"
                            }, {
                                src: "bg/明媚倾城.jpg"
                            }, {
                                src: "bg/羊皮纸4.jpg"
                            }, {
                                src: "bg/深宫魅影.jpg"
                            }, {
                                src: "bg/午后沙滩.jpg"
                            }, {
                                src: "bg/清新时光.jpg"
                            }, {
                                src: "bg/宁静夜色.jpg"
                            }, {
                                src: "bg/边彩画布.jpg"
                            }],
                            fonts: ["系统", "黑体", "楷体", "宋体", "仿宋"],
                            readMethods: ["上下滑动", "左右滑动", "上下滚动", "上下滚动2"],
                            clickMethods: ["下一页", "自动", "不翻页", "固定模式"],
                            selectionActions: ["操作弹窗", "忽略"],
                            pageModes: ["自适应", "手机模式"],
                            pageTypes: ["正常", "Kindle"],
                            themeTypes: ["day", "night"],
                            configDefaultTypeList: ["白天默认", "黑夜默认"],
                            autoReadingMethods: ["像素滚动", "段落滚动"],
                            chineseFonts: ["简体", "繁体", "原文"],
                            epubModes: ["iframe", "解析"],
                            quickKeyModes: ["默认", "自定义"],
                            quickKeyOps: ["上一页", "下一页", "上半页", "下半页", "上一章", "下一章", "首页", "尾页", "返回", "不操作"],
                            quickKeyList: [{
                                key: "ArrowUp",
                                label: "上键"
                            }, {
                                key: "ArrowDown",
                                label: "下键"
                            }, {
                                key: "ArrowLeft",
                                label: "左键"
                            }, {
                                key: "ArrowRight",
                                label: "右键"
                            }, {
                                key: "Space",
                                label: "空格"
                            }, {
                                key: "PageUp",
                                label: "PgUp"
                            }, {
                                key: "PageDown",
                                label: "PgDn"
                            }, {
                                key: "Home",
                                label: "Home"
                            }, {
                                key: "End",
                                label: "End"
                            }, {
                                key: "Escape",
                                label: "Esc"
                            }],
                            customFontName: "",
                            customFonts: x["a"],
                            config: this.$store.state.config,
                            configRules: {
                                fontSize: {
                                    min: 8,
                                    delta: 1
                                },
                                fontWeight: {
                                    min: 100,
                                    max: 900,
                                    delta: 100
                                },
                                animateMSTime: {
                                    min: 0,
                                    max: 500,
                                    delta: 50
                                },
                                autoReadingPixel: {
                                    min: 1,
                                    delta: 5
                                },
                                autoReadingLineTime: {
                                    min: 10,
                                    delta: 50
                                },
                                lineHeight: {
                                    min: 1,
                                    max: 5,
                                    delta: .2
                                },
                                paragraphSpace: {
                                    min: 0,
                                    max: 5,
                                    delta: .2
                                },
                                readWidth: {
                                    min: 160 * Math.min(Math.floor(window.innerWidth / 160), 4),
                                    max: 160 * Math.floor(window.innerWidth / 160),
                                    delta: 160
                                },
                                horizontalPadding: {
                                    min: 0,
                                    delta: 1
                                },
                                topPadding: {
                                    min: 0,
                                    delta: 1
                                },
                                bottomPadding: {
                                    min: 0,
                                    delta: 1
                                },
                                chapterRequestTimeout: {
                                    min: 0,
                                    max: 300,
                                    delta: 1
                                }
                            }
                        }
                    },
                    props: ["visible"],
                    mounted: function() {
                        this.syncConfig()
                    },
                    computed: {
                        moonIcon: function() {
                            return this.$store.getters.isSystemNight ? "" : ""
                        },
                        popupTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup
                            }
                        },
                        currentCustomConfig: function() {
                            var e = this;
                            return this.$store.state.customConfigList.find((function(t) {
                                return t.name === e.$store.state.config.customConfig
                            }))
                        },
                        notAutoSync: function() {
                            return this.$store.state.notAutoSync
                        }
                    },
                    watch: {
                        config: {
                            deep: !0,
                            handler: function(e) {
                                this.$store.commit("setConfig", Object(i["a"])({}, e)), this.notAutoSync || this.$root.$children[0].saveUserConfig(!0)
                            }
                        },
                        visible: function(e) {
                            e && this.syncConfig()
                        }
                    },
                    methods: {
                        syncConfig: function() {
                            var e = Object(i["a"])(Object(i["a"])(Object(i["a"])({}, x["b"].config), this.config), {}, {
                                selectionAction: "忽略" !== this.$store.state.config.selectionAction ? "操作弹窗" : "忽略"
                            });
                            Object.keys(e).sort().join("") === Object.keys(this.config).sort().join("") && Object.values(e).sort().join("") === Object.values(this.config).sort().join("") || (this.config = e)
                        },
                        setPageType: function(e) {
                            if (e !== this.config.pageType) {
                                var t = {};
                                "Kindle" === e ? (Object(T["c"])("lastNormalConfig", this.config), t = Object(T["b"])("lastKindleConfig"), t = t || {
                                    animateMSTime: 0,
                                    fontSize: Math.min(this.fontSize, 20),
                                    theme: 7,
                                    readMethod: "左右滑动",
                                    selectionAction: "忽略",
                                    pageMode: "手机模式"
                                }) : (Object(T["c"])("lastKindleConfig", this.config), t = Object(T["b"])("lastNormalConfig") || {}), this.config = Object(i["a"])(Object(i["a"])(Object(i["a"])({}, this.config), t || {}), {}, {
                                    pageType: e
                                }), this.$emit("readMethodChange"), this.$emit("pageModeChange"), "手机模式" === this.config.pageMode ? this.$store.commit("setMiniInterface", !0) : this.$store.commit("setMiniInterface", Object(N["e"])())
                            }
                        },
                        setPageMode: function(e) {
                            this.$emit("pageModeChange"), this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                pageMode: e
                            }), "手机模式" === this.config.pageMode ? this.$store.commit("setMiniInterface", !0) : this.$store.commit("setMiniInterface", Object(N["e"])())
                        },
                        setReadMethod: function(e) {
                            this.$emit("readMethodChange"), this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                readMethod: e
                            })
                        },
                        setEpubMode: function(e) {
                            this.$emit("epubModeChange"), this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                epubMode: e
                            })
                        },
                        setQuickKeyMode: function(e) {
                            this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                quickKeyMode: e
                            })
                        },
                        setQuickKeyOpt: function(e, t) {
                            this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                quickKey: Object(i["a"])(Object(i["a"])({}, this.config.quickKey), {}, Object(w["a"])({}, e, t))
                            })
                        },
                        setConfig: function(e, t) {
                            var n = {};
                            n[e] = t, this.config = Object(i["a"])(Object(i["a"])({}, this.config), n)
                        },
                        setAutoTheme: function() {
                            this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                autoTheme: !this.config.autoTheme
                            })
                        },
                        incConfig: function(e) {
                            var t = {},
                                n = this.configRules[e],
                                a = +this.config[e];
                            t[e] = "max" in n ? Math.min(n.max, a + n.delta) : a + n.delta, this.config = Object(i["a"])(Object(i["a"])({}, this.config), t)
                        },
                        decConfig: function(e) {
                            var t = {},
                                n = this.configRules[e],
                                a = +this.config[e];
                            t[e] = "min" in n ? Math.max(n.min, a - n.delta) : a - n.delta, this.config = Object(i["a"])(Object(i["a"])({}, this.config), t)
                        },
                        getCustomBGImgURL: function(e) {
                            return this.api.replace(/\/reader3\/?/, "") + e
                        },
                        setBGImg: function(e) {
                            var t = Object(i["a"])({}, this.config);
                            t.contentBGImg === e ? delete t.contentBGImg : t.contentBGImg = e, this.config = t
                        },
                        uploadBGFile: function() {
                            this.$refs.bgFileRef.dispatchEvent(new MouseEvent("click"))
                        },
                        onBGFileChange: function(e) {
                            var t = this,
                                n = e.target.files && e.target.files[0],
                                a = new FormData;
                            a.append("file", n), a.append("type", "background"), p["a"].post(this.api + "/uploadFile", a, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                if (e.data.isSuccess) {
                                    if (!e.data.data.length) return void t.$message.error("上传文件失败");
                                    var n = Object(i["a"])({}, t.config);
                                    n.customBGImgList = n.customBGImgList || [], n.customBGImgList.includes(e.data.data[0]) || n.customBGImgList.push(e.data.data[0]), n.contentBGImg = e.data.data[0], t.config = n
                                }
                            }), (function(e) {
                                t.$message.error("上传文件失败 " + (e && e.toString()))
                            })), this.$refs.bgFileRef.value = null
                        },
                        uploadFontFile: function(e, t) {
                            var n = this;
                            return Object(r["a"])(Object(o["a"])().mark((function a() {
                                var u;
                                return Object(o["a"])().wrap((function(a) {
                                    while (1) switch (a.prev = a.next) {
                                        case 0:
                                            if (!n.config.customFontsMap || !n.config.customFontsMap[e]) {
                                                a.next = 9;
                                                break
                                            }
                                            return a.next = 3, n.$confirm("已上传自定义的".concat(t, "字体?"), "提示", {
                                                confirmButtonText: "继续上传",
                                                cancelButtonText: "恢复默认",
                                                type: "warning",
                                                closeOnClickModal: !1,
                                                closeOnPressEscape: !1,
                                                distinguishCancelAndClose: !0
                                            })["catch"]((function(e) {
                                                return "close" === e && "close"
                                            }));
                                        case 3:
                                            if (u = a.sent, "close" !== u) {
                                                a.next = 6;
                                                break
                                            }
                                            return a.abrupt("return");
                                        case 6:
                                            if (u) {
                                                a.next = 9;
                                                break
                                            }
                                            return p["a"].post(n.api + "/deleteFile", {
                                                url: n.config.customFontsMap[e]
                                            }).then((function(t) {
                                                if (t.data.isSuccess) {
                                                    var a = Object(i["a"])({}, n.config);
                                                    delete a.customFontsMap[e], n.config = a, Object(N["h"])(e)
                                                }
                                            }), (function(e) {
                                                n.$message.error("删除自定义字体文件失败 " + (e && e.toString()))
                                            })), a.abrupt("return");
                                        case 9:
                                            n.customFontName = e, n.$refs.fontFileRef.dispatchEvent(new MouseEvent("click"));
                                        case 11:
                                        case "end":
                                            return a.stop()
                                    }
                                }), a)
                            })))()
                        },
                        onFontFileChange: function(e) {
                            var t = this,
                                n = e.target.files && e.target.files[0];
                            if (n.name.toLowerCase().endsWith(".ttf")) {
                                var a = new FormData;
                                a.append("file", n), a.append("type", "fonts"), p["a"].post(this.api + "/uploadFile", a, {
                                    headers: {
                                        "Content-Type": "multipart/form-data"
                                    }
                                }).then((function(e) {
                                    if (e.data.isSuccess) {
                                        if (!e.data.data.length) return void t.$message.error("上传文件失败");
                                        var n = Object(i["a"])({}, t.config);
                                        n.customFontsMap = n.customFontsMap || {}, n.customFontsMap[t.customFontName] = e.data.data[0], t.config = n
                                    }
                                }), (function(e) {
                                    t.$message.error("上传文件失败 " + (e && e.toString()))
                                })), this.$refs.fontFileRef.value = null
                            } else this.$message.error("只支持 TTF 字体文件")
                        },
                        deleteCustomBGImg: function(e) {
                            var t = this;
                            p["a"].post(this.api + "/deleteFile", {
                                url: e
                            }).then((function(n) {
                                if (n.data.isSuccess) {
                                    var a = Object(i["a"])({}, t.config);
                                    a.customBGImgList = a.customBGImgList || [];
                                    var u = a.customBGImgList.indexOf(e); - 1 != u && a.customBGImgList.splice(u, 1), a.contentBGImg === e && (a.contentBGImg = t.builtinBG[0].src), t.config = a
                                }
                            }), (function(e) {
                                t.$message.error("删除文件失败 " + (e && e.toString()))
                            }))
                        },
                        toggleNotAutoSync: function() {
                            this.$store.commit("setNotAutoSync", !this.notAutoSync)
                        },
                        backupConfig: function() {
                            this.$root.$children[0].saveUserConfig()
                        },
                        restoreConfig: function() {
                            this.$root.$children[0].restoreUserConfig()
                        },
                        resetConfig: function() {
                            var e = this;
                            return Object(r["a"])(Object(o["a"])().mark((function t() {
                                var n;
                                return Object(o["a"])().wrap((function(t) {
                                    while (1) switch (t.prev = t.next) {
                                        case 0:
                                            return t.next = 2, e.$confirm("确认要重置为默认配置吗?", "提示")["catch"]((function() {
                                                return !1
                                            }));
                                        case 2:
                                            if (n = t.sent, n) {
                                                t.next = 5;
                                                break
                                            }
                                            return t.abrupt("return");
                                        case 5:
                                            e.config = Object(i["a"])({}, x["b"].config);
                                        case 6:
                                        case "end":
                                            return t.stop()
                                    }
                                }), t)
                            })))()
                        },
                        showClickZone: function() {
                            this.$emit("close"), this.$emit("showClickZone")
                        },
                        showRuleEditor: function() {
                            this.$emit("close"), S["a"].$emit("showReplaceRuleDialog")
                        },
                        addNewCustomConfig: function() {
                            var e = this;
                            return Object(r["a"])(Object(o["a"])().mark((function t() {
                                var n, a, u, r;
                                return Object(o["a"])().wrap((function(t) {
                                    while (1) switch (t.prev = t.next) {
                                        case 0:
                                            return t.next = 2, e.$prompt("请输入方案名称", "添加配置方案", {
                                                inputValue: "",
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消",
                                                inputValidator: function(e) {
                                                    return !!e || "方案名不能为空"
                                                }
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 2:
                                            if (n = t.sent, n) {
                                                t.next = 5;
                                                break
                                            }
                                            return t.abrupt("return");
                                        case 5:
                                            if (a = n.value.replace(/^\s+/, "").replace(/\s+$/, ""), a) {
                                                t.next = 8;
                                                break
                                            }
                                            return t.abrupt("return", "方案名不能为空");
                                        case 8:
                                            if (u = e.$store.state.customConfigList.find((function(e) {
                                                    return e.name === a
                                                })), !u) {
                                                t.next = 11;
                                                break
                                            }
                                            return t.abrupt("return", "方案名不能重复");
                                        case 11:
                                            r = Object(i["a"])({}, e.$store.state.customConfigList[0]), r.name = a, e.$store.commit("setCustomConfigList", [].concat(e.$store.state.customConfigList).concat([r]));
                                        case 14:
                                        case "end":
                                            return t.stop()
                                    }
                                }), t)
                            })))()
                        },
                        setCustomConfig: function(e) {
                            this.config = Object(i["a"])(Object(i["a"])({}, this.config), {}, {
                                customConfig: e.name
                            }, e)
                        },
                        deleteCustomConfig: function(e, t) {
                            var n = this;
                            return Object(r["a"])(Object(o["a"])().mark((function a() {
                                var u, r;
                                return Object(o["a"])().wrap((function(a) {
                                    while (1) switch (a.prev = a.next) {
                                        case 0:
                                            if (u = [].concat(n.$store.state.customConfigList), !(e <= 1)) {
                                                a.next = 4;
                                                break
                                            }
                                            return n.$message.error("内置方案不能删除"), a.abrupt("return");
                                        case 4:
                                            if (!(u.length <= e)) {
                                                a.next = 7;
                                                break
                                            }
                                            return n.$message.error("方案不存在"), a.abrupt("return");
                                        case 7:
                                            if (n.$store.state.config.customConfig !== t) {
                                                a.next = 10;
                                                break
                                            }
                                            return n.$message.error("方案正在使用，无法删除"), a.abrupt("return");
                                        case 10:
                                            return a.next = 12, n.$confirm("确认要删除".concat(t, "方案吗？"), "提示", {
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消",
                                                type: "warning"
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 12:
                                            if (r = a.sent, r) {
                                                a.next = 15;
                                                break
                                            }
                                            return a.abrupt("return");
                                        case 15:
                                            u.splice(e, 1), n.$store.commit("setCustomConfigList", [].concat(u));
                                        case 17:
                                        case "end":
                                            return a.stop()
                                    }
                                }), a)
                            })))()
                        },
                        setConfigDefaultType: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a, u;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            return n.next = 2, t.$confirm("确认要设置当前方案为".concat(e, "吗？继续操作将替换现有的").concat(e, "方案"), "提示", {
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消",
                                                type: "warning"
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 2:
                                            if (a = n.sent, a) {
                                                n.next = 5;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 5:
                                            u = [].concat(t.$store.state.customConfigList), u.forEach((function(n) {
                                                n.name === t.$store.state.config.customConfig ? n.configDefaultType = e : n.configDefaultType === e && (n.configDefaultType = "")
                                            })), t.$store.commit("setCustomConfigList", u);
                                        case 8:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        }
                    }
                },
                I = L,
                R = (n("2412"), n("90fc"), Object(m["a"])(I, B, k, !1, null, "062ccc78", null)),
                P = R.exports,
                $ = function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "popup-wrapper",
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "title-zone"
                    }, [t("div", {
                        staticClass: "title"
                    }, [e._v("来源(" + e._s(e.showBookSourceList.length) + ")")]), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: !e.isSearching,
                            expression: "!isSearching"
                        }],
                        class: {
                            "title-btn": !0,
                            loading: e.loading
                        }
                    }, [t("el-select", {
                        staticClass: "booksource-group-select",
                        attrs: {
                            size: "mini",
                            filterable: "",
                            placeholder: "全部分组"
                        },
                        model: {
                            value: e.bookSourceGroup,
                            callback: function(t) {
                                e.bookSourceGroup = t
                            },
                            expression: "bookSourceGroup"
                        }
                    }, e._l(e.$store.getters.bookSourceGroupList, (function(e, n) {
                        return t("el-option", {
                            key: "source-group-" + n,
                            attrs: {
                                label: e.name + " (" + e.count + ")",
                                value: e.value
                            }
                        })
                    })), 1), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]), t("span", {
                        class: {
                            loading: e.loading
                        },
                        on: {
                            click: e.refresh
                        }
                    }, [e.loading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.loading ? "刷新中..." : "刷新") + " ")]), t("span", {
                        class: {
                            loading: e.loadingMore
                        },
                        on: {
                            click: e.searchBookSourceByEventStream
                        }
                    }, [e.loadingMore ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.loadingMore ? "加载中..." : "加载更多") + " ")]), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])], 1), t("div", {
                        directives: [{
                            name: "show",
                            rawName: "v-show",
                            value: e.isSearching,
                            expression: "isSearching"
                        }],
                        class: {
                            "title-btn": !0,
                            loading: e.loadingMore
                        }
                    }, [t("el-input", {
                        staticClass: "booksource-filter-input",
                        attrs: {
                            size: "mini",
                            placeholder: "搜索书源"
                        },
                        model: {
                            value: e.searchKeyword,
                            callback: function(t) {
                                e.searchKeyword = t
                            },
                            expression: "searchKeyword"
                        }
                    }), t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.isSearching = !e.isSearching
                            }
                        }
                    }, [e._v(" " + e._s(e.isSearching ? "取消" : "搜索") + " ")])], 1)]), t("div", {
                        ref: "sourceList",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "source-list"
                    }, e._l(e.showBookSourceList, (function(n, a) {
                        return t("div", {
                            key: a,
                            ref: "source",
                            refInFor: !0,
                            staticClass: "source-item",
                            class: {
                                selected: e.isSelected(n)
                            },
                            on: {
                                click: function(t) {
                                    return e.changeBookSource(n)
                                }
                            }
                        }, [t("div", {
                            staticClass: "source-title"
                        }, [t("div", {
                            staticClass: "source-name"
                        }, [e._v(" " + e._s(n.originName) + " ")]), t("div", {
                            staticClass: "source-time"
                        }, [e._v(" " + e._s(n.time ? "⏱ " + n.time + "ms" : "") + " ")])]), t("div", {
                            staticClass: "source-latest-chapter"
                        }, [e._v(" " + e._s(n.latestChapterTitle || "无最新章节") + " ")])])
                    })), 0)])])
                },
                M = [],
                O = (n("13d5"), n("45fc"), n("9485"), n("a732"), n("30b5")),
                z = {
                    name: "BookSource",
                    data: function() {
                        return {
                            index: this.$store.getters.readingBook.index,
                            bookSource: [],
                            bookSourceGroup: "",
                            bookSourceGroupIndexMap: {},
                            loading: !1,
                            expand: !1,
                            loadingMore: !1,
                            isSearching: !1,
                            searchKeyword: ""
                        }
                    },
                    props: ["visible"],
                    computed: {
                        theme: function() {
                            return this.$store.getters.config.theme
                        },
                        popupTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup
                            }
                        },
                        bookSourceMap: function() {
                            return this.bookSource.reduce((function(e, t) {
                                return e[t.bookUrl] = t, e
                            }), {})
                        },
                        readingBook: function() {
                            return this.$store.getters.readingBook || {}
                        },
                        showBookSourceList: function() {
                            var e = this;
                            return this.isSearching && this.searchKeyword ? this.bookSource.filter((function(t) {
                                return (t.latestChapterTitle || "").includes(e.searchKeyword)
                            })) : this.bookSource
                        }
                    },
                    mounted: function() {},
                    watch: {
                        visible: function(e) {
                            e && this.getBookSource()
                        },
                        readingBook: function(e, t) {
                            e.bookUrl !== t.bookUrl && (this.bookSourceGroupIndexMap = {})
                        }
                    },
                    methods: {
                        isSelected: function(e) {
                            return e.bookUrl == this.$store.getters.readingBook.bookUrl
                        },
                        getBookSource: function(e) {
                            var t = this;
                            p["a"].post(this.api + "/getAvailableBookSource", {
                                url: this.$store.getters.readingBook.bookUrl,
                                refresh: e ? 1 : 0
                            }).then((function(e) {
                                var n;
                                (t.loading = !1, e.data.isSuccess) && (t.bookSource = e.data.data || [], t.bookSource.length && (t.bookSourceGroupIndexMap[""] = Math.max(null !== (n = t.bookSourceGroupIndexMap[""]) && void 0 !== n ? n : 0, t.bookSource.length), t.jumpToActive()))
                            }), (function(e) {
                                throw t.loading = !1, t.$message.error("获取书籍来源信息失败 " + (e && e.toString())), e
                            }))
                        },
                        changeBookSource: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            return n.next = 2, t.$root.$children[0].isInShelf(t.$store.getters.readingBook, "加入书架之后才能切换书源, 是否加入书架?");
                                        case 2:
                                            if (a = n.sent, a) {
                                                n.next = 5;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 5:
                                            p["a"].post(t.api + "/setBookSource", {
                                                bookUrl: t.$store.getters.readingBook.bookUrl,
                                                newUrl: e.bookUrl,
                                                bookSourceUrl: e.origin
                                            }).then((function(n) {
                                                if (n.data.isSuccess) {
                                                    t.$message.info("换源成功");
                                                    var a = Object.assign({}, t.$store.getters.readingBook);
                                                    a.bookUrl = e.bookUrl, a.type = "undefined" !== typeof e.type ? e.type : a.type, a.coverUrl = "undefined" !== typeof e.coverUrl ? e.coverUrl : a.coverUrl, t.$store.commit("setReadingBook", a), t.$emit("changeBookSource"), p["a"].get(t.api + "/getBookshelf", {}).then((function(e) {
                                                        e.data.isSuccess && t.$store.commit("setShelfBooks", e.data.data)
                                                    }), (function() {}))
                                                }
                                            }), (function(e) {
                                                throw t.$message.error("换源失败 " + (e && e.toString())), e
                                            }));
                                        case 6:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        refresh: function() {
                            this.loadingMore || (this.loading = !0, this.getBookSource(!0))
                        },
                        loadMoreSource: function() {
                            var e = this;
                            this.loadingMore || (this.loadingMore = !0, p["a"].post(this.api + "/searchBookSource", {
                                url: this.$store.getters.readingBook.bookUrl,
                                bookSourceGroup: this.bookSourceGroup,
                                lastIndex: this.bookSourceGroupIndexMap[this.bookSourceGroup]
                            }).then((function(t) {
                                if (e.loadingMore = !1, t.data.isSuccess) {
                                    var n = t.data.data.list || [];
                                    e.bookSource = [].concat(e.bookSource, n.filter((function(t) {
                                        return !e.bookSourceMap[t.bookUrl]
                                    }))), t.data.data.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = t.data.data.lastIndex)
                                }
                            }), (function(t) {
                                throw e.loadingMore = !1, e.$message.error("加载更多书籍来源失败 " + (t && t.toString())), t
                            })))
                        },
                        searchBookSourceByEventStream: function() {
                            var e = this,
                                t = function() {
                                    try {
                                        e.searchEventSource && e.searchEventSource.readyState != e.searchEventSource.CLOSED && e.searchEventSource.close(), e.searchEventSource = null
                                    } catch (re) {}
                                };
                            if (this.loadingMore) return t(), void(this.loadingMore = !1);
                            var n = {
                                accessToken: this.$store.state.token,
                                concurrentCount: this.$store.state.searchConfig.concurrentCount,
                                url: this.$store.getters.readingBook.bookUrl,
                                bookSourceGroup: this.bookSourceGroup,
                                lastIndex: this.bookSourceGroupIndexMap[this.bookSourceGroup]
                            };
                            this.loadingMore = !0;
                            var a = O(this.api + "/searchBookSourceSSE", n);
                            t(), this.searchEventSource = new EventSource(a, {
                                withCredentials: !0
                            }), this.searchEventSource.addEventListener("error", (function(n) {
                                e.loadingMore = !1, t();
                                try {
                                    if (n.data) {
                                        var a = JSON.parse(n.data);
                                        a && a.errorMsg && e.$message.error(a.errorMsg)
                                    }
                                } catch (re) {}
                            }));
                            var u = this.bookSource.length;
                            this.searchEventSource.addEventListener("end", (function(n) {
                                e.loadingMore = !1, t();
                                try {
                                    if (n.data) {
                                        var a = JSON.parse(n.data);
                                        a && a.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = a.lastIndex)
                                    }
                                    e.bookSource.length === u && e.$message.error("没有更多啦")
                                } catch (re) {}
                            })), this.searchEventSource.addEventListener("message", (function(t) {
                                try {
                                    if (t.data) {
                                        var n = JSON.parse(t.data);
                                        n && n.lastIndex && (e.bookSourceGroupIndexMap[e.bookSourceGroup] = n.lastIndex), n.data && (e.bookSource = [].concat(e.bookSource, n.data.filter((function(t) {
                                            return !e.bookSourceMap[t.bookUrl]
                                        }))))
                                    }
                                } catch (re) {}
                            }))
                        },
                        jumpToActive: function() {
                            var e = this;
                            this.$nextTick((function() {
                                var t = -1;
                                if (e.bookSource.some((function(n, a) {
                                        if (n.bookUrl == e.$store.getters.readingBook.bookUrl) return t = a, !0
                                    })), !(t < 0)) {
                                    var n = e.$refs.sourceList;
                                    Object(c["a"])(e.$refs.source[t], {
                                        container: n,
                                        duration: 0
                                    })
                                }
                            }))
                        }
                    }
                },
                _ = z,
                j = (n("b9b4"), Object(m["a"])(_, $, M, !1, null, "abde66a6", null)),
                U = j.exports,
                q = function() {
                    var e = this,
                        t = e._self._c;
                    return t("div", {
                        staticClass: "popup-wrapper",
                        style: e.popupTheme
                    }, [t("div", {
                        staticClass: "title-zone"
                    }, [t("div", {
                        staticClass: "title"
                    }, [e._v("书架(" + e._s(e.shelfBooks.length) + ")")]), t("div", {
                        class: {
                            "title-btn": !0,
                            loading: e.refreshLoading
                        }
                    }, [t("span", {
                        staticClass: "span-btn",
                        on: {
                            click: function(t) {
                                e.expand = !e.expand
                            }
                        }
                    }, [e._v(" " + e._s(e.expand ? "收起" : "展开") + " ")]), t("span", {
                        class: {
                            loading: e.refreshLoading
                        },
                        on: {
                            click: e.refreshShelf
                        }
                    }, [e.refreshLoading ? t("i", {
                        staticClass: "el-icon-loading"
                    }) : e._e(), e._v(" " + e._s(e.refreshLoading ? "刷新中..." : "刷新") + " ")])])]), t("div", {
                        ref: "bookList",
                        staticClass: "data-wrapper",
                        class: {
                            night: e.$store.getters.isNight,
                            day: !e.$store.getters.isNight,
                            expand: e.expand
                        }
                    }, [t("div", {
                        staticClass: "shelfbook-list"
                    }, [e.shelfBooks.length && "yes" === e.$store.getters.shelfConfig.virtualOptimize ? t("BookShelfVirtualList", {
                        attrs: {
                            bookList: e.shelfBooks,
                            visible: e.visible
                        },
                        on: {
                            changeBook: e.changeBook
                        }
                    }) : e._l(e.shelfBooks, (function(n) {
                        return t("div", {
                            key: n.bookUrl,
                            ref: "book",
                            refInFor: !0,
                            staticClass: "book-item",
                            class: {
                                selected: e.isSelected(n)
                            },
                            on: {
                                click: function(t) {
                                    return e.changeBook(n)
                                }
                            }
                        }, [t("div", {
                            staticClass: "book-title"
                        }, [t("div", {
                            staticClass: "book-name"
                        }, [e._v(" " + e._s(n.name) + " ")]), n.totalChapterNum - 1 - n.durChapterIndex ? t("div", {
                            staticClass: "book-progress"
                        }, [e._v(" " + e._s(n.totalChapterNum - 1 - n.durChapterIndex) + " ")]) : e._e()]), t("div", {
                            staticClass: "chapter-text"
                        }, [e._v(" " + e._s(n.durChapterTitle) + " ")])])
                    }))], 2)])])
                },
                Y = [],
                W = function() {
                    var e = this,
                        t = e._self._c;
                    return e.visible ? t("VirtList", {
                        ref: "virtList",
                        attrs: {
                            itemKey: "bookUrl",
                            list: e.bookList,
                            minSize: 40
                        },
                        scopedSlots: e._u([{
                            key: "default",
                            fn: function(n) {
                                var a = n.itemData;
                                return [t("div", {
                                    ref: "book",
                                    staticClass: "book-item",
                                    class: {
                                        selected: a.bookUrl === e.$store.getters.readingBook.bookUrl
                                    },
                                    on: {
                                        click: function(t) {
                                            return e.$emit("changeBook", a)
                                        }
                                    }
                                }, [t("div", {
                                    staticClass: "book-title"
                                }, [t("div", {
                                    staticClass: "book-name"
                                }, [e._v(" " + e._s(a.name) + " ")]), a.totalChapterNum - 1 - a.durChapterIndex ? t("div", {
                                    staticClass: "book-progress"
                                }, [e._v(" " + e._s(a.totalChapterNum - 1 - a.durChapterIndex) + " ")]) : e._e()]), t("div", {
                                    staticClass: "chapter-text"
                                }, [e._v(" " + e._s(a.durChapterTitle) + " ")])])]
                            }
                        }], null, !1, 690613472)
                    }) : e._e()
                },
                H = [],
                K = {
                    name: "BookShelfVirtualList",
                    components: {
                        VirtList: A["a"]
                    },
                    props: ["bookList", "visible"],
                    watch: {
                        visible: function() {
                            var e = this;
                            this.$nextTick((function() {
                                e.$refs.virtList.$forceUpdate();
                                var t = e.bookList.findIndex((function(t) {
                                    return t.bookUrl === e.$store.getters.readingBook.bookUrl
                                }));
                                e.$refs.virtList && e.$refs.virtList.scrollToIndex(t >= 0 ? t : 0)
                            }))
                        }
                    }
                },
                V = K,
                Q = (n("e5c1"), Object(m["a"])(V, W, H, !1, null, null, null)),
                G = Q.exports,
                X = {
                    name: "BookShelf",
                    data: function() {
                        return {
                            refreshLoading: !1,
                            expand: !1
                        }
                    },
                    components: {
                        BookShelfVirtualList: G
                    },
                    props: ["visible"],
                    computed: {
                        theme: function() {
                            return this.$store.getters.config.theme
                        },
                        popupTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup
                            }
                        },
                        shelfBooks: function() {
                            return this.$store.getters.shelfBooks
                        }
                    },
                    mounted: function() {},
                    watch: {
                        visible: function(e) {
                            e && this.getBookshelf()
                        }
                    },
                    methods: {
                        isSelected: function(e) {
                            return e.bookUrl == this.$store.getters.readingBook.bookUrl
                        },
                        getBookshelf: function(e) {
                            var t = this;
                            this.shelfBooks.length || Object(N["g"])((function() {
                                return p["a"].get(t.api + "/getBookshelf", {
                                    params: {
                                        refresh: e ? 1 : 0
                                    }
                                })
                            }), "getBookshelf" + ((this.$store.state.userInfo || {}).username || "default")).then((function(e) {
                                t.refreshLoading = !1, e.data.isSuccess && (t.$store.commit("setShelfBooks", e.data.data), t.shelfBooks.length && t.jumpToActive())
                            }), (function(e) {
                                throw t.refreshLoading = !1, t.$message.error("获取书架书籍失败 " + (e && e.toString())), e
                            }))
                        },
                        changeBook: function(e) {
                            var t, n, a = {
                                name: e.name,
                                bookUrl: e.bookUrl,
                                index: null !== (t = null !== (n = e.index) && void 0 !== n ? n : e.durChapterIndex) && void 0 !== t ? t : 0,
                                type: e.type,
                                coverUrl: e.customCoverUrl || e.coverUrl,
                                tocUrl: e.tocUrl,
                                author: e.author,
                                origin: e.origin,
                                originName: e.originName,
                                latestChapterTitle: e.latestChapterTitle,
                                intro: e.intro
                            };
                            this.$emit("changeBook", a)
                        },
                        refreshShelf: function() {
                            this.refreshLoading || (this.refreshLoading = !0, this.getBookshelf(!0))
                        },
                        jumpToActive: function() {
                            var e = this;
                            "yes" !== this.$store.getters.shelfConfig.virtualOptimize && this.$nextTick((function() {
                                var t = -1;
                                if (e.shelfBooks.some((function(n, a) {
                                        if (n.bookUrl == e.$store.getters.readingBook.bookUrl) return t = a, !0
                                    })), !(t < 0))
                                    if (e.$refs.book && e.$refs.book[t]) {
                                        var n = e.$refs.bookList;
                                        Object(c["a"])(e.$refs.book[t], {
                                            container: n,
                                            duration: 0
                                        })
                                    } else setTimeout((function() {
                                        e.jumpToActive()
                                    }), 10)
                            }))
                        },
                        backToHome: function() {
                            this.$emit("toShelf")
                        }
                    }
                },
                Z = X,
                J = (n("7564"), Object(m["a"])(Z, q, Y, !1, null, "510c7aa8", null)),
                ee = J.exports,
                te = (n("f4b3"), n("e9c4"), n("d9e2"), n("00b4"), n("3835")),
                ne = (n("a623"), n("d866"), n("498a"), n("53ca"));
            n("277d"), n("baa5"), n("7a82"), n("e439"), n("7039"), n("10d1");

            function ae(e) {
                return "function" === typeof e
            }
            window.Proxy && window.CustomElementRegistry, "function" === typeof document.all && document.all, new WeakMap, new WeakMap;
            new WeakMap;
            new WeakMap;

            function ue(e) {
                var t = e;
                return t.protocol + "//" + t.host + t.pathname
            }
            var oe = window.requestIdleCallback || function(e) {
                return setTimeout(e, 1)
            };

            function re(e, t) {
                console.error("[wujie error]: ".concat(e), t)
            }

            function ie(e) {
                var t = e.indexOf(">") + 1,
                    n = e.lastIndexOf("<");
                return e.substring(t, n)
            }

            function se(e) {
                if ("object" === Object(ne["a"])(e)) return "/";
                try {
                    var t = new URL(e, location.href),
                        n = t.origin,
                        a = t.pathname,
                        u = a.split("/");
                    return u.pop(), "".concat(n).concat(u.join("/"), "/")
                } catch (o) {
                    return console.warn(o), ""
                }
            }

            function le(e) {
                return function(t) {
                    for (var n = arguments.length, a = new Array(n > 1 ? n - 1 : 0), u = 1; u < n; u++) a[u - 1] = arguments[u];
                    return e.reduce((function(e, t) {
                        return ae(t) ? t.apply(void 0, [e].concat(a)) : e
                    }), t || "")
                }
            }
            var ce = /(<script[\s\S]*?>)[\s\S]*?<\/script>/gi,
                pe = /<(script)\s+((?!type=('|")text\/ng-template\3)[^])*?>[^]*?<\/\1>/i,
                de = /.*\ssrc=('|")?([^>'"\s]+)/,
                De = /.*\stype=('|")?([^>'"\s]+)/,
                he = /.*\sentry\s*.*/,
                Ae = /.*\sasync\s*.*/,
                Fe = /.*\sdefer\s*.*/,
                fe = /.*\snomodule\s*.*/,
                me = /.*\stype=('|")?module('|")?\s*.*/,
                Ce = /<(link)\s+[^]*?>/gi,
                Ee = /\srel=('|")?(preload|prefetch|modulepreload)\1/,
                ye = /.*\shref=('|")?([^>'"\s]+)/,
                ge = /.*\sas=('|")?font\1.*/,
                be = /<style[^>]*>[\s\S]*?<\/style>/gi,
                ve = /\s+rel=('|")?stylesheet\1.*/,
                Be = /.*\shref=('|")?([^>'"\s]+)/,
                ke = /<!--([\s\S]*?)-->/g,
                we = /<link(\s+|\s+[^]+\s+)ignore(\s*|\s+[^]*|=[^]*)>/i,
                xe = /<style(\s+|\s+[^]+\s+)ignore(\s*|\s+[^]*|=[^]*)>/i,
                Se = /<script(\s+|\s+[^]+\s+)ignore(\s*|\s+[^]*|=[^]*)>/i,
                Ne = /.*\scrossorigin=?('|")?(use-credentials|anonymous)?('|")?/i;

            function Te(e) {
                return e.startsWith("//") || e.startsWith("http://") || e.startsWith("https://")
            }

            function Le(e, t) {
                return new URL(e, t).toString()
            }

            function Ie(e) {
                var t = ["text/javascript", "module", "application/javascript", "text/ecmascript", "application/ecmascript"];
                return !e || -1 !== t.indexOf(e)
            }

            function Re() {
                var e = window.document.createElement("script");
                return "noModule" in e
            }
            var Pe = function(e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] && arguments[1];
                    return "\x3c!-- ".concat(t ? "prefetch/preload/modulepreload" : "", " link ").concat(e, " replaced by wujie --\x3e")
                },
                $e = function(e) {
                    return "\x3c!-- inline-style-".concat(e, " replaced by wujie --\x3e")
                },
                Me = function(e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : "";
                    return "\x3c!-- ".concat(t, " script ").concat(e, " replaced by wujie --\x3e")
                },
                Oe = "\x3c!-- inline scripts replaced by wujie --\x3e",
                ze = function(e) {
                    return "\x3c!-- ignore asset ".concat(e || "file", " replaced by wujie --\x3e")
                },
                _e = function(e, t) {
                    return "\x3c!-- ".concat(t ? "nomodule" : "module", " script ").concat(e, " ignored by wujie --\x3e")
                };

            function je(e, t, n) {
                var a = [],
                    u = [],
                    o = null,
                    r = Re(),
                    i = e.replace(ke, "").replace(Ce, (function(e) {
                        var n = !!e.match(ve);
                        if (n) {
                            var a = e.match(Be),
                                o = e.match(we);
                            if (a) {
                                var r = a && a[2],
                                    i = r;
                                return r && !Te(r) && (i = Le(r, t)), o ? ze(i) : (u.push({
                                    src: i
                                }), Pe(i))
                            }
                        }
                        var s = e.match(Ee) && e.match(ye) && !e.match(ge);
                        if (s) {
                            var l = e.match(ye),
                                c = Object(te["a"])(l, 3),
                                p = c[2];
                            return Pe(p, !0)
                        }
                        return e
                    })).replace(be, (function(e) {
                        if (xe.test(e)) return ze("style file");
                        var t = ie(e);
                        return u.push({
                            src: "",
                            content: t
                        }), $e(u.length - 1)
                    })).replace(ce, (function(e, n) {
                        var u = n.match(Se),
                            i = !!n.match(me),
                            s = n.match(Ne),
                            l = (null === s || void 0 === s ? void 0 : s[2]) || "",
                            c = r && !!n.match(fe) || !r && i,
                            p = n.match(De),
                            d = p && p[2];
                        if (!Ie(d)) return e;
                        if (pe.test(e) && n.match(de)) {
                            var D = n.match(he),
                                h = n.match(de),
                                A = h && h[2];
                            if (o && D) throw new SyntaxError("You should not set multiply entry script!");
                            if (A && !Te(A) && (A = Le(A, t)), o = o || D && A, u) return ze(A || "js file");
                            if (c) return _e(A || "js file", r);
                            if (A) {
                                var F = !!n.match(Ae),
                                    f = !!n.match(Fe);
                                return a.push(F || f ? {
                                    async: F,
                                    defer: f,
                                    src: A,
                                    module: i,
                                    crossorigin: !!s,
                                    crossoriginType: l
                                } : {
                                    src: A,
                                    module: i,
                                    crossorigin: !!s,
                                    crossoriginType: l
                                }), Me(A, (F ? "async" : f && "defer") || "")
                            }
                            return e
                        }
                        if (u) return ze("js file");
                        if (c) return _e("js file", r);
                        var m = ie(e),
                            C = m.split(/[\r\n]+/).every((function(e) {
                                return !e.trim() || e.trim().startsWith("//")
                            }));
                        return !C && m && a.push({
                            src: "",
                            content: m,
                            module: i,
                            crossorigin: !!s,
                            crossoriginType: l
                        }), Oe
                    })),
                    s = {
                        template: i,
                        scripts: a,
                        styles: u,
                        entry: o || a[a.length - 1]
                    };
                return "function" === typeof n && (s = n(s)), s
            }
            var Ue = "window上不存在fetch属性，需要自行polyfill",
                qe = "脚本请求出现错误",
                Ye = "样式请求出现错误";

            function We(e, t) {
                return t.map((function(t) {
                    return t[e]
                })).filter((function(e) {
                    return null === e || void 0 === e ? void 0 : e.length
                })).reduce((function(e, t) {
                    return e.concat(t)
                }), [])
            }

            function He(e, t) {
                return t.some((function(t) {
                    return "string" === typeof t ? e === t : t.test(e)
                }))
            }
            var Ke = {},
                Ve = {},
                Qe = {};
            if (!window.fetch) throw re(Ue), new Error;
            var Ge = window.fetch.bind(window);

            function Xe(e) {
                return e
            }

            function Ze(e, t, n) {
                return Je.apply(this, arguments)
            }

            function Je() {
                return Je = Object(r["a"])(Object(o["a"])().mark((function e(t, n, a) {
                    var u, r, i, s;
                    return Object(o["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                return u = ue(t.proxyLocation), r = le(t.plugins.map((function(e) {
                                    return e.cssLoader
                                }))), i = a().map((function(e) {
                                    var t = e.src,
                                        n = e.ignore,
                                        a = e.contentPromise;
                                    return {
                                        src: t,
                                        ignore: n,
                                        contentPromise: a.then((function(e) {
                                            return r(e, t, u)
                                        }))
                                    }
                                })), e.next = 5, et(n, i);
                            case 5:
                                return s = e.sent, e.abrupt("return", t.replace ? t.replace(s) : s);
                            case 7:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                }))), Je.apply(this, arguments)
            }

            function et(e, t) {
                return tt.apply(this, arguments)
            }

            function tt() {
                return tt = Object(r["a"])(Object(o["a"])().mark((function e(t, n) {
                    var a;
                    return Object(o["a"])().wrap((function(e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                return a = t, e.abrupt("return", Promise.all(n.map((function(e, t) {
                                    return e.contentPromise.then((function(n) {
                                        e.src ? a = a.replace(Pe(e.src), e.ignore ? '<link href="'.concat(e.src, '" rel="stylesheet" type="text/css">') : "<style>/* ".concat(e.src, " */").concat(n, "</style>")) : n && (a = a.replace($e(t), "<style>/* inline-style-".concat(t, " */").concat(n, "</style>")))
                                    }))
                                }))).then((function() {
                                    return a
                                })));
                            case 2:
                            case "end":
                                return e.stop()
                        }
                    }), e)
                }))), tt.apply(this, arguments)
            }
            var nt = function(e) {
                    return e.startsWith("<")
                },
                at = function(e, t, n, a, u) {
                    return t[e] || (t[e] = n(e).then((function(n) {
                        if (n.status >= 400) {
                            if (t[e] = null, a) return re(Ye, {
                                src: e,
                                response: n
                            }), u && u(e, new Error(Ye)), "";
                            throw re(qe, {
                                src: e,
                                response: n
                            }), u && u(e, new Error(qe)), new Error(qe)
                        }
                        return n.text()
                    }))["catch"]((function(n) {
                        return t[e] = null, a ? (re(Ye, e), u && u(e, n), "") : (re(qe, e), u && u(e, n), "")
                    })))
                };

            function ut(e, t, n) {
                return e.map((function(e) {
                    var a = e.src,
                        u = e.content,
                        o = e.ignore;
                    return u ? {
                        src: "",
                        contentPromise: Promise.resolve(u)
                    } : nt(a) ? {
                        src: "",
                        contentPromise: Promise.resolve(ie(a))
                    } : {
                        src: a,
                        ignore: o,
                        contentPromise: o ? Promise.resolve("") : at(a, Ke, t, !0, n)
                    }
                }))
            }

            function ot(e, t, n, a) {
                return e.map((function(e) {
                    var u = e.src,
                        o = e.async,
                        r = e.defer,
                        s = e.module,
                        l = e.ignore,
                        c = null;
                    return c = (o || r) && u && !s ? new Promise((function(e, o) {
                        return a ? oe((function() {
                            return at(u, Ve, t, !1, n).then(e, o)
                        })) : at(u, Ve, t, !1, n).then(e, o)
                    })) : s && u || l ? Promise.resolve("") : u ? at(u, Ve, t, !1, n) : Promise.resolve(e.content), Object(i["a"])(Object(i["a"])({}, e), {}, {
                        contentPromise: c
                    })
                }))
            }

            function rt(e, t) {
                var n, a, u = null !== (n = t.fetch) && void 0 !== n ? n : Ge,
                    o = null === (a = t.fiber) || void 0 === a || a,
                    r = t.plugins,
                    s = t.loadError,
                    l = r ? le(r.map((function(e) {
                        return e.htmlLoader
                    }))) : Xe,
                    c = We("jsExcludes", r),
                    p = We("cssExcludes", r),
                    d = We("jsIgnores", r),
                    D = We("cssIgnores", r),
                    h = se,
                    A = function(e, t) {
                        return u(e).then((function(e) {
                            return e.text()
                        }), (function(t) {
                            return s && s(e, t), Promise.reject(t)
                        })).then((function(n) {
                            var a = h(e),
                                r = je(t(n), a),
                                l = r.template,
                                A = r.scripts,
                                F = r.styles;
                            return {
                                template: l,
                                assetPublicPath: a,
                                getExternalScripts: function() {
                                    return ot(A.filter((function(e) {
                                        return !e.src || !He(e.src, c)
                                    })).map((function(e) {
                                        return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                            ignore: e.src && He(e.src, d)
                                        })
                                    })), u, s, o)
                                },
                                getExternalStyleSheets: function() {
                                    return ut(F.filter((function(e) {
                                        return !e.src || !He(e.src, p)
                                    })).map((function(e) {
                                        return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                            ignore: e.src && He(e.src, D)
                                        })
                                    })), u, s)
                                }
                            }
                        }))
                    };
                return t.plugins.some((function(e) {
                    return e.htmlLoader
                })) ? A(e, l) : Qe[e] || (Qe[e] = A(e, l))
            }
            var it, st, lt, ct, pt = n("b532"),
                dt = function(e) {
                    var t = pt.Converter({
                        from: "cn",
                        to: "tw"
                    });
                    if ("string" === typeof e) return t(e);
                    var n = pt.HTMLConverter(t, e, "", "zh-TW");
                    n.convert(), n = pt.HTMLConverter(t, e, "zh-CN", "zh-TW"), n.convert()
                },
                Dt = function(e) {
                    var t = pt.Converter({
                        from: "tw",
                        to: "cn"
                    });
                    if ("string" === typeof e) return t(e);
                    var n = pt.HTMLConverter(t, e, "", "zh-CN");
                    n.convert(), n = pt.HTMLConverter(t, e, "zh-TW", "zh-CN"), n.convert()
                },
                ht = {
                    name: "ShadowIframe",
                    data: function() {
                        return {
                            loading: !0,
                            initing: !1,
                            nowSrc: ""
                        }
                    },
                    computed: {
                        contentWindow: function() {
                            return this.$refs.iframe && this.$refs.iframe.contentWindow
                        },
                        renderIframe: function() {
                            return "iframe" === this.renderType
                        },
                        completeSrc: function() {
                            return this.nowSrc ? this.nowSrc.startsWith("//") ? window.location.protocol + this.nowSrc : this.nowSrc : this.src.startsWith("//") ? window.location.protocol + this.src : this.src
                        },
                        chineseFont: function() {
                            return this.$store.getters.config.chineseFont
                        }
                    },
                    watch: {
                        src: function(e) {
                            this.loadShadowDom(e)
                        },
                        renderIframe: function(e) {
                            e || this.loadShadowDom(this.nowSrc || this.src)
                        },
                        chineseFont: function(e) {
                            this.renderIframe || ("简体" === e ? Dt(this.shadowRoot.documentElement) : "繁体" === e && dt(this.shadowRoot.documentElement))
                        }
                    },
                    props: ["src", "iframeStyle", "renderType"],
                    render: function() {
                        return this.renderIframe ? this.fallback() : this.renderByShadowDom()
                    },
                    created: function() {
                        this.initIframe()
                    },
                    mounted: function() {
                        window.shadowIframe = this, window.simplized = Dt, window.traditionalized = dt, this.loadShadowDom(this.completeSrc)
                    },
                    methods: {
                        checkShadowDom: function() {},
                        loadShadowDom: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a, u, r, s, l;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t.initing && e) {
                                                n.next = 2;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 2:
                                            return t.initing = !0, t.nowSrc = e, n.next = 6, rt(e, {
                                                fetch: function(e) {
                                                    return p["a"].get(e).then((function(e) {
                                                        return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                                            text: function() {
                                                                return e.data
                                                            }
                                                        })
                                                    }))
                                                },
                                                plugins: [{
                                                    htmlLoader: function(n) {
                                                        return n = n.replace(/\sxlink:href=('|")?([^>'"\s]+)/gi, (function(t) {
                                                            var n = t.match(/href=('|")?([^>'"\s]+)/);
                                                            if (n) {
                                                                var a = new URL(n[2], e);
                                                                return ' xlink:href="'.concat(a, '"')
                                                            }
                                                        })).replace(/<a[^>]*>/gi, (function(t) {
                                                            var n = t.match(/href=('|")?([^>'"\s]+)/);
                                                            if (n) {
                                                                var a = new URL(n[2], e);
                                                                return t.replace(n[2], a)
                                                            }
                                                        })).replace(/\ssrc=['"](\S+)['"]/gi, (function(t) {
                                                            var n = t.match(/src=('|")?([^>'"\s]+)/);
                                                            if (n) {
                                                                var a = new URL(n[2], e);
                                                                return ' src="'.concat(a, '"')
                                                            }
                                                        })), "简体" === t.$store.getters.config.chineseFont ? Dt(n) : dt(n)
                                                    }
                                                }],
                                                loadError: console.error
                                            });
                                        case 6:
                                            return a = n.sent, u = a.template, r = a.getExternalStyleSheets, s = {
                                                proxyLocation: window.location,
                                                plugins: []
                                            }, n.next = 12, Ze(s, u, r);
                                        case 12:
                                            l = n.sent, t.renderShadowDom(l, e);
                                        case 14:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        renderShadowDom: function(e, t) {
                            var n = this;
                            if (!this.$refs.shadowDom) return setTimeout((function() {
                                n.renderShadowDom(e)
                            }), 10);
                            if (this.$refs.shadowDom.shadowRoot || (this.shadowRoot = this.$refs.shadowDom.attachShadow({
                                    mode: "open"
                                })), this.shadowRoot) {
                                var a = document.createElement("html");
                                a.innerHTML = e, this.loading = !1, this.reader_style_dom = document.createElement("style");
                                var u = a.getElementsByTagName("head")[0];
                                u.appendChild(this.reader_style_dom), this.shadowRoot.firstChild && this.shadowRoot.firstChild.remove(), this.shadowRoot.documentElement = a, this.shadowRoot.head = u, this.shadowRoot.body = a.querySelector("body"), this.shadowRoot.appendChild(a), this.initShadowDomJavascript(t)
                            }
                        },
                        initShadowDomJavascript: function(e) {
                            var t = this,
                                n = this.shadowRoot.querySelector("html"),
                                a = this.shadowRoot.querySelector("body"),
                                u = this,
                                o = new URL(e);

                            function r(e, t) {
                                u.$emit("iframeEvent", e, t)
                            }

                            function i() {
                                r("setHeight", Math.max(n.scrollHeight, a.scrollHeight)), r("setWidth", Math.max(n.scrollWidth, a.scrollWidth))
                            }

                            function s(e) {
                                return !(!e || !e.nodeName) && ("a" === e.nodeName.toLowerCase() ? e : s(e.parentNode))
                            }

                            function l(e) {
                                return !(!e || !e.nodeName) && ("img" === e.nodeName.toLowerCase() ? e : void 0)
                            }
                            n.addEventListener("load", (function() {
                                i(), r("load", e)
                            })), window.addEventListener("resize", i), n.addEventListener("DOMNodeInserted", i, !1), n.addEventListener("click", (function(e) {
                                var t = s(e.target),
                                    a = l(e.target);
                                if (t)
                                    if (t.pathname === o.pathname) {
                                        var i = n.querySelector(t.hash);
                                        i && (r("clickHash", i.getBoundingClientRect()), e.preventDefault())
                                    } else e.preventDefault(), r("clickA", e.target.href), u.loadShadowDom(e.target.href);
                                else if (a) {
                                    var c = n.querySelectorAll("img");
                                    if (c.length) {
                                        for (var p = [], d = 0, D = 0; D < c.length; D++) p.push(c[D].src), c[D] === a && (d = D);
                                        r("previewImageList", {
                                            imageList: p,
                                            imageIndex: d
                                        })
                                    }
                                } else r("click", {
                                    target: e.target.nodeName,
                                    clientX: e.clientX,
                                    clientY: e.clientY
                                })
                            })), this.$emit("iframeEvent", "inited"), this.initing = !1, this.$nextTick((function() {
                                t.$emit("iframeEvent", "load")
                            }))
                        },
                        renderByShadowDom: function() {
                            var e = this.$createElement;
                            return e("div", {
                                ref: "shadowDom"
                            }, [this.loading ? e("span", ["Loading..."]) : null])
                        },
                        fallback: function() {
                            var e = this.$createElement;
                            return e("iframe", {
                                class: "epub-iframe",
                                ref: "iframe",
                                style: this.iframeStyle,
                                attrs: {
                                    src: this.src
                                }
                            })
                        },
                        initIframe: function() {
                            var e = this;
                            window.addEventListener("message", (function(t) {
                                if (e.$refs.iframe && t.source === e.$refs.iframe.contentWindow) {
                                    var n;
                                    try {
                                        n = JSON.parse(t.data)
                                    } catch (re) {
                                        return
                                    }
                                    e.$emit("iframeEvent", n.event, n.data)
                                }
                            }))
                        },
                        syncIframeHeight: function() {
                            var e = this;
                            this.renderIframe ? this.sendToIframe("execute", {
                                script: "\n          var syncHeight = function(){\n            if(!document.body){\n              setTimeout(syncHeight, 10);\n              return;\n            }\n            reader_notify('setWidth', document.documentElement.scrollHeight || document.body.scrollHeight);\n          };\n          syncHeight();\n          "
                            }) : this.ensure((function() {
                                var t = e.shadowRoot.querySelector("html"),
                                    n = e.shadowRoot.querySelector("body");
                                e.$emit("iframeEvent", "setHeight", Math.max(t.scrollHeight, n.scrollHeight))
                            }), (function() {
                                return e.shadowRoot
                            }))
                        },
                        syncIframeWidth: function() {
                            var e = this;
                            this.renderIframe ? this.sendToIframe("execute", {
                                script: "var syncWidth = function(){\n            if(!document.body){\n              setTimeout(syncWidth, 10);\n              return;\n            }\n            reader_notify('setWidth', document.body.scrollWidth);\n          };\n          syncWidth();\n          "
                            }) : this.ensure((function() {
                                var t = e.shadowRoot.querySelector("html"),
                                    n = e.shadowRoot.querySelector("body");
                                e.$emit("iframeEvent", "setWidth", Math.max(t.scrollWidth, n.scrollWidth))
                            }), (function() {
                                return e.shadowRoot
                            }))
                        },
                        setIframeStyle: function(e) {
                            var t = this;
                            this.renderIframe ? this.sendToIframe("setStyle", {
                                style: e
                            }) : (e += "\n        html {\n          height: 100%;\n          columns: calc(100vw - 16px - var(--horizontal-padding, 0px)) 1;\n          column-gap: calc(16px + var(--horizontal-padding, 0px));\n        }\n        ", this.ensure((function() {
                                t.reader_style_dom.textContent = e
                            }), (function() {
                                return t.reader_style_dom
                            })))
                        },
                        transformX: function(e) {
                            var t = this;
                            this.renderIframe ? this.sendToIframe("execute", {
                                script: 'document.body.style.transform="translateX('.concat(e, 'px)";')
                            }) : this.ensure((function() {
                                var n = t.shadowRoot.querySelector("body");
                                n.style.transform = "translateX(".concat(e, "px)")
                            }), (function() {
                                return t.shadowRoot
                            }))
                        },
                        syncCSSProperty: function() {
                            this.renderIframe && this.sendToIframe("execute", {
                                script: "document.documentElement.style.setProperty('--horizontal-padding', \"".concat(0 | this.$store.state.config.horizontalPadding, 'px");')
                            })
                        },
                        sendToIframe: function(e, t) {
                            var n = this;
                            this.$refs.iframe ? this.$refs.iframe && this.$refs.iframe.contentWindow && this.$refs.iframe.contentWindow.postMessage(JSON.stringify(Object(i["a"])({
                                event: e
                            }, t)), "*") : setTimeout((function() {
                                n.sendToIframe(e, t)
                            }), 10)
                        },
                        ensure: function(e, t) {
                            var n = this;
                            t && t() ? e && e() : setTimeout((function() {
                                n.ensure(e, t)
                            }), 10)
                        }
                    }
                },
                At = ht,
                Ft = Object(m["a"])(At, it, st, !1, null, "401db35b", null),
                ft = Ft.exports,
                mt = n("f7a5"),
                Ct = n.n(mt),
                Et = {
                    name: "Content",
                    data: function() {
                        return {
                            currentTime: 0,
                            audioDuration: 0,
                            playing: !1,
                            currentSpeed: 1,
                            audioVolume: 100,
                            startTime: 0,
                            iframeStyle: {},
                            iframeSize: {
                                scrollHeight: 0,
                                scrollWidth: 0
                            }
                        }
                    },
                    components: {
                        ShadowIframe: ft
                    },
                    props: ["content", "error", "title", "showContent", "isSlideRead", "isScrollRead", "showChapterList", "currentShowChapter", "isEditContent"],
                    render: function() {
                        var e = this,
                            t = arguments[0];
                        if (this.isVideo) return this.renderVideo();
                        if (this.showContent) {
                            if (this.$store.getters.currentChapter && this.$store.getters.currentChapter.isVolume) return t("div", {
                                class: "content-body chapter-content reading-chapter volume-chapter",
                                style: this.containerStyle
                            }, [t("div", {
                                class: "volume-content"
                            }, [t("h3", {
                                attrs: {
                                    "data-pos": 0
                                }
                            }, [this.title]), t("p", {
                                class: "volume-tag"
                            }, [this.content])])]);
                            if (this.isAudio) return this.renderAudio();
                            if (this.isEpub) return this.renderEpub();
                            if (this.isScrollRead) return this.renderScrollChapterList();
                            var n = this.title.length + 2;
                            return t("div", {
                                class: "content-body chapter-content reading-chapter",
                                attrs: {
                                    contenteditable: this.isEditContent
                                },
                                style: this.containerStyle,
                                directives: [{
                                    name: "lazy-container",
                                    value: {
                                        selector: "img"
                                    }
                                }]
                            }, [this.isCbz || this.isPdf ? null : t("h3", {
                                attrs: {
                                    "data-pos": 0
                                }
                            }, [this.title]), this.content.split(/\n+/).map((function(a) {
                                if (a = a.replace(/^\s+/g, ""), !a) return null;
                                var u = n;
                                return n += a.length + 2, a.indexOf("<img") >= 0 ? (a = a.replace(/src=/g, "data-src=").replace(/__API_ROOT__/g, e.$store.getters.apiRoot), t("div", {
                                    style: e.containerStyle,
                                    domProps: {
                                        innerHTML: a
                                    },
                                    attrs: {
                                        "data-pos": u
                                    }
                                })) : t("p", {
                                    style: e.pStyle,
                                    domProps: {
                                        innerHTML: a
                                    },
                                    attrs: {
                                        "data-pos": u
                                    }
                                })
                            }))])
                        }
                        return t("div")
                    },
                    mounted: function() {
                        this.isAudio && this.play(!0), window.contentCom = this, this.loadCustomFontFamil()
                    },
                    unmounted: function() {
                        delete window.contentCom
                    },
                    computed: {
                        readingBook: function() {
                            return this.$store.getters.readingBook
                        },
                        chapter: function() {
                            return this.$store.getters.readingBook.catalog[this.$store.getters.readingBook.index] || {}
                        },
                        show: function() {
                            return this.$store.state.showContent
                        },
                        fontSize: function() {
                            return this.$store.getters.config.fontSize + "px"
                        },
                        autoPlay: {
                            get: function() {
                                return this.$store.state.autoPlay
                            },
                            set: function(e) {
                                this.$store.commit("setAutoPlay", e)
                            }
                        },
                        isCarToon: function() {
                            return !this.error && !this.isEpub && (this.content || "").indexOf("<img") >= 0
                        },
                        isAudio: function() {
                            return !this.error && 1 === this.readingBook.type
                        },
                        isVideo: function() {
                            return !this.error && 4 === this.readingBook.type
                        },
                        isEpub: function() {
                            return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".epub")
                        },
                        isEpubIframe: function() {
                            return "iframe" === this.$store.getters.config.epubMode
                        },
                        isCbz: function() {
                            return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".cbz")
                        },
                        isPdf: function() {
                            return !this.error && this.readingBook.bookUrl.toLowerCase().endsWith(".pdf")
                        },
                        containerStyle: function() {
                            return Object(i["a"])(Object(i["a"])({
                                fontSize: this.$store.getters.config.fontSize + "px",
                                fontWeight: this.$store.getters.config.fontWeight || void 0,
                                color: this.$store.getters.config.fontColor || (this.$store.getters.isNight ? "#666" : "#262626")
                            }, this.$store.getters.currentFontFamily), this.$store.getters.config.contentCSS || {})
                        },
                        pStyle: function() {
                            return {
                                lineHeight: this.$store.getters.config.lineHeight,
                                marginTop: "undefined" !== typeof this.$store.getters.config.paragraphSpace ? this.$store.getters.config.paragraphSpace + "em" : null,
                                marginBottom: "undefined" !== typeof this.$store.getters.config.paragraphSpace ? this.$store.getters.config.paragraphSpace + "em" : null
                            }
                        },
                        windowSize: function() {
                            return this.$store.state.windowSize
                        },
                        currentCustomFontFamily: function() {
                            return this.$store.getters.currentCustomFontFamily
                        }
                    },
                    watch: {
                        containerStyle: function() {
                            this.isEpub && this.isEpubIframe && this.setIframeStyle()
                        },
                        pStyle: function() {
                            this.isEpub && this.isEpubIframe && this.setIframeStyle()
                        },
                        windowSize: function() {
                            this.isEpub
                        },
                        currentCustomFontFamily: function() {
                            this.loadCustomFontFamil()
                        },
                        isSlideRead: function() {
                            this.isEpub && this.isEpubIframe && (this.iframeStyle = {}, this.setIframeStyle(), this.isSlideRead ? this.syncIframeWidth() : this.syncIframeHeight(), this.sendToIframe("execute", {
                                script: 'document.body.style.transform="translateX(0px)";'
                            }))
                        },
                        showContent: {
                            handler: function(e) {
                                e && this.isVideo && this.initDplayer()
                            },
                            immediate: !0
                        }
                    },
                    methods: {
                        renderScrollChapterList: function() {
                            var e = this,
                                t = this.$createElement;
                            return t("div", {
                                class: "content-body",
                                style: this.containerStyle,
                                directives: [{
                                    name: "lazy-container",
                                    value: {
                                        selector: "img"
                                    }
                                }]
                            }, [this.showChapterList.map((function(n) {
                                if (n.isVolume) return t("div", {
                                    class: "content-body chapter-content reading-chapter volume-chapter"
                                }, [t("div", {
                                    class: "volume-content"
                                }, [t("h3", {
                                    attrs: {
                                        "data-pos": 0
                                    }
                                }, [n.title]), t("p", {
                                    class: "volume-tag"
                                }, [n.content])])]);
                                var a = n.title.length + 2;
                                return t("div", {
                                    class: ["chapter-content", e.readingBook.index === n.index ? "reading-chapter" : ""],
                                    attrs: {
                                        "data-index": n.index
                                    }
                                }, [e.isCbz || e.isPdf ? null : t("h3", {
                                    attrs: {
                                        "data-pos": 0
                                    }
                                }, [n.title]), n.content.split(/\n+/).map((function(n) {
                                    if (n = n.replace(/^\s+/g, ""), !n) return null;
                                    var u = a;
                                    return a += n.length + 2, n.indexOf("<img") >= 0 ? (n = n.replace(/src=/g, "data-src=").replace("__API_ROOT__", e.$store.getters.apiRoot), t("div", {
                                        style: e.containerStyle,
                                        domProps: {
                                            innerHTML: n
                                        },
                                        attrs: {
                                            "data-pos": u
                                        }
                                    })) : t("p", {
                                        style: e.pStyle,
                                        domProps: {
                                            innerHTML: n
                                        },
                                        attrs: {
                                            "data-pos": u
                                        }
                                    })
                                }))])
                            }))])
                        },
                        renderAudio: function() {
                            var e = this,
                                t = this.$createElement;
                            return t("div", {
                                class: "content-audio"
                            }, [t("audio", {
                                ref: "audio",
                                attrs: {
                                    preload: "preload",
                                    src: this.content
                                },
                                on: {
                                    loadMetaData: this.audioEvent,
                                    progress: this.onProgress,
                                    playing: this.onProgress,
                                    timeupdate: this.onTimeupdate,
                                    play: this.onPlay,
                                    pause: this.onPause,
                                    ended: this.onEnd,
                                    error: this.onError,
                                    seeked: this.onSeeked,
                                    seeking: this.onSeeking,
                                    stalled: this.audioEvent,
                                    suspend: this.onsuspend,
                                    loadeddata: this.audioEvent,
                                    loadedmetadata: this.computeDuration,
                                    durationchange: this.computeDuration,
                                    canplay: this.onCanPlay,
                                    canplaythrough: this.audioEvent,
                                    waiting: this.onWaiting
                                }
                            }), t("div", {
                                class: "book-cover"
                            }, [t("img", {
                                directives: [{
                                    name: "lazy",
                                    value: this.getCover(this.readingBook.coverUrl)
                                }]
                            })]), t("div", {
                                class: "book-progress"
                            }, [t("div", {
                                class: "progress-tip"
                            }, [this.formatTime(this.currentTime)]), t("div", {
                                class: "progress-container"
                            }, [t("el-slider", {
                                attrs: {
                                    min: 0,
                                    max: this.audioDuration,
                                    "show-tooltip": !1
                                },
                                model: {
                                    value: e.currentTime,
                                    callback: function(t) {
                                        e.currentTime = t
                                    }
                                },
                                on: {
                                    change: function(t) {
                                        e.seekTime(t)
                                    }
                                }
                            })]), t("div", {
                                class: "progress-tip total-time"
                            }, [this.formatTime(this.audioDuration)])]), t("div", {
                                class: "book-operation"
                            }, [t("i", {
                                class: "reader-iconfont reader-icon-jian15s",
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(),
                                            function() {
                                                e.seekTime(e.$refs.audio.currentTime - 15)
                                            }()
                                    }
                                }
                            }), t("i", {
                                class: "reader-iconfont reader-icon-player-backward-step",
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(), e.prevChapter(t)
                                    }
                                }
                            }), t("i", {
                                class: ["reader-iconfont", this.playing ? "reader-icon-player-play" : "reader-icon-player-pause"],
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(), e.toggle(t)
                                    }
                                }
                            }), t("i", {
                                class: "reader-iconfont reader-icon-player-forward-step",
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(), e.nextChapter(t)
                                    }
                                }
                            }), t("i", {
                                class: "reader-iconfont reader-icon-15s",
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(),
                                            function() {
                                                e.seekTime(e.$refs.audio.currentTime + 15)
                                            }()
                                    }
                                }
                            })]), t("div", {
                                class: "book-operation"
                            }, [t("span", {
                                style: {
                                    display: "flex",
                                    flexDirection: "row",
                                    alignItems: "center"
                                }
                            }, [t("i", {
                                class: ["reader-iconfont", this.audioVolume > 0 ? "reader-icon-volume" : "reader-icon-volume-off"],
                                style: {
                                    marginRight: this.audioVolume > 0 ? "15px" : "25px"
                                },
                                on: {
                                    click: function(t) {
                                        return t.stopPropagation(), t.preventDefault(),
                                            function() {
                                                e.setAudioVolume(e.audioVolume > 0 ? 0 : 100)
                                            }()
                                    }
                                }
                            }), t("el-slider", {
                                attrs: {
                                    min: 0,
                                    max: 100,
                                    "show-tooltip": !1
                                },
                                style: {
                                    width: "180px"
                                },
                                model: {
                                    value: e.audioVolume,
                                    callback: function(t) {
                                        e.audioVolume = t
                                    }
                                },
                                on: {
                                    change: function(t) {
                                        e.setAudioVolume(t)
                                    }
                                }
                            })])]), t("div", {
                                class: "book-info",
                                style: {
                                    background: this.getCover(this.readingBook.coverUrl, !0)
                                }
                            }, [t("div", {
                                class: "book-cover"
                            }, [t("img", {
                                directives: [{
                                    name: "lazy",
                                    value: this.getCover(this.readingBook.coverUrl)
                                }]
                            })]), t("div", {
                                class: "book-intro"
                            }, [t("div", {
                                class: "title"
                            }, [this.title]), t("div", {
                                class: "subtitle"
                            }, [this.readingBook.name, this.readingBook.author ? "•" : "", this.readingBook.author])])])])
                        },
                        renderVideo: function() {
                            var e = this.$createElement;
                            return e("div", {
                                ref: "dplayer"
                            })
                        },
                        initDplayer: function() {
                            var e = this;
                            if (this.$refs.dplayer) {
                                this.dplayer && this.dplayer.destroy();
                                var t = {
                                    video: {
                                        url: this.content
                                    }
                                };
                                try {
                                    t = JSON.parse(this.content), "string" === typeof t && (t = {
                                        video: {
                                            url: this.content
                                        }
                                    })
                                } catch (n) {}
                                this.dplayer = new Ct.a(Object(i["a"])(Object(i["a"])({
                                    container: this.$refs.dplayer
                                }, t), {}, {
                                    preventClickToggle: !0,
                                    autoplay: !0
                                })), this.dplayer.on("durationchange", (function() {
                                    e.startTime && (e.dplayer.seek(e.startTime), e.startTime = null)
                                })), this.dplayer.on("timeupdate", (function() {
                                    e.currentTime = 0 | e.dplayer.video.currentTime, e.$emit("updateProgress")
                                }))
                            } else setTimeout((function() {
                                e.initDplayer()
                            }), 50)
                        },
                        renderEpub: function() {
                            var e = this,
                                t = this.$createElement;
                            if (!this.content.startsWith("/book-assets")) return null;
                            var n = this.content;
                            return n.startsWith("/") || (n = "/" + n), t(ft, {
                                class: "epub-iframe",
                                ref: "iframe",
                                style: this.iframeStyle,
                                attrs: {
                                    src: this.$store.getters.apiRoot + n,
                                    renderType: this.isEpubIframe ? "iframe" : "shadowDom"
                                },
                                on: {
                                    iframeEvent: function(t, n) {
                                        e.iframeEvent(t, n)
                                    }
                                }
                            })
                        },
                        iframeEvent: function(e, t) {
                            var n = this;
                            switch (e) {
                                case "inited":
                                    this.iframeStyle = {}, this.setIframeStyle(), this.isSlideRead ? this.syncIframeWidth() : this.syncIframeHeight();
                                    break;
                                case "load":
                                    setTimeout((function() {
                                        n.$emit("iframeLoad"), n.$emit("epubLocationChange", t)
                                    }), 100);
                                    break;
                                case "setHeight":
                                    this.iframeSize.scrollHeight = t, this.isSlideRead || (this.iframeStyle = Object(i["a"])(Object(i["a"])({}, this.iframeStyle), {}, {
                                        height: Math.max(t, .8 * this.windowSize.height) + "px"
                                    }), this.$emit("contentChange"));
                                    break;
                                case "setWidth":
                                    this.iframeSize.scrollWidth = t, this.isSlideRead && this.$emit("contentChange");
                                    break;
                                case "click":
                                    this.$emit("epubClick", t);
                                    break;
                                case "clickHash":
                                    this.$emit("epubClickHash", t);
                                    break;
                                case "keydown":
                                    this.$emit("epubKeydown", t);
                                    break;
                                case "previewImageList":
                                    this.$store.commit("setPreviewImageIndex", t.imageIndex), this.$store.commit("setPreviewImgList", t.imageList);
                                    break;
                                case "clickA":
                                    this.$emit("epubLocationChange", t);
                                    break;
                                case "touchstart":
                                case "touchmove":
                                case "touchend":
                                    this.$emit("epubTouch", {
                                        event: e,
                                        data: t
                                    });
                                    break;
                                default:
                                    break
                            }
                        },
                        syncIframeHeight: function() {
                            var e = this;
                            this.ensure((function() {
                                return e.$refs.iframe.syncIframeHeight()
                            }), (function() {
                                return e.$refs.iframe
                            }))
                        },
                        syncIframeWidth: function() {
                            var e = this;
                            this.ensure((function() {
                                return e.$refs.iframe.syncIframeWidth()
                            }), (function() {
                                return e.$refs.iframe
                            }))
                        },
                        setIframeStyle: function() {
                            var e = this;
                            this.$root.$children[0].setDocumentProperty();
                            var t = "";
                            for (var n in this.containerStyle) Object.hasOwnProperty.call(this.containerStyle, n) && (t += n.replace(/([A-Z])/g, (function(e) {
                                return "-" + e.toLowerCase()
                            })) + ":" + this.containerStyle[n] + " !important;");
                            var a = "";
                            this.isSlideRead && (t += "\n          height: 100%;\n          ".concat(this.isEpubIframe ? "overflow: hidden;" : "", "\n          columns: calc(100vw - 16px - var(--horizontal-padding, 0px)) 1;\n          column-gap: calc(16px + var(--horizontal-padding, 0px));\n        "), a += "\n          break-inside: avoid;\n        ");
                            var u = "";
                            for (var o in this.pStyle) Object.hasOwnProperty.call(this.pStyle, o) && (u += o.replace(/([A-Z])/g, (function(e) {
                                return "-" + e.toLowerCase()
                            })) + ":" + this.pStyle[o] + " !important;");
                            u += "font-family: " + this.containerStyle.fontFamily + " !important;", u += "font-size: " + this.containerStyle.fontSize + " !important;", u += "font-weight: " + this.containerStyle.fontWeight + " !important;", u += "color: " + this.containerStyle.color + " !important;";
                            var r = "";
                            r += "font-family: " + this.containerStyle.fontFamily + " !important;", r += "font-weight: " + this.containerStyle.fontWeight + " !important;", r += "color: " + this.containerStyle.color + " !important;";
                            var i = "";
                            this.currentCustomFontFamily && (i = '\n        @font-face {\n          font-family: "'.concat(this.currentCustomFontFamily.name, '";\n          src: url("').concat(this.currentCustomFontFamily.url, '");\n        }'));
                            var s = "\n        ".concat(i, "\n        *::-webkit-scrollbar {\n          display: none;\n          width: 0 !important;\n          height: 0 !important;\n        }\n        *:focus {\n          outline: none !important;\n        }\n        html {\n          ").concat(this.isSlideRead ? "height: 100%;" : "", "\n          min-height: 100%;\n        }\n        body {\n          margin: 0 !important;\n          ").concat(t, "\n        }\n        .reading {\n          color: red !important;\n        }\n        body p {\n          ").concat(u, "\n        }\n        body h1, body h2, body h3, body h4 {\n          ").concat(r, "\n        }\n        img, body img {\n          display: block;\n          max-width: ").concat(this.isEpubIframe ? "100vw" : "100%", " !important;\n          height: auto !important;\n          ").concat(a, "\n        }");
                            this.ensure((function() {
                                return e.$refs.iframe.setIframeStyle(s)
                            }), (function() {
                                return e.$refs.iframe
                            }))
                        },
                        ensure: function(e, t) {
                            var n = this;
                            t && t() ? e && e() : setTimeout((function() {
                                n.ensure(e, t)
                            }), 10)
                        },
                        transformX: function(e) {
                            var t = this;
                            this.ensure((function() {
                                return t.$refs.iframe.transformX(e)
                            }), (function() {
                                return t.$refs.iframe
                            }))
                        },
                        syncCSSProperty: function() {
                            var e = this;
                            this.ensure((function() {
                                return e.$refs.iframe.syncCSSProperty()
                            }), (function() {
                                return e.$refs.iframe
                            }))
                        },
                        formatTime: function(e) {
                            if (!e) return "00:00";
                            var t = function(e) {
                                return e >= 10 ? "" + e : "0" + e
                            };
                            if (e < 60) return "00:" + t(e);
                            if (e < 3600) {
                                var n = Math.round(e / 60),
                                    a = e % 60;
                                return t(n) + ":" + t(a)
                            }
                            var u = Math.round(e / 3600),
                                o = Math.round(e / 3600 / 60),
                                r = e % 60;
                            return t(u) + ":" + t(o) + ":" + t(r)
                        },
                        seekTime: function(e) {
                            isNaN(e) || e === 1 / 0 || this.$refs.audio && (this.$refs.audio.currentTime = e)
                        },
                        setAudioVolume: function(e) {
                            isNaN(e) || e === 1 / 0 || (this.audioVolume = e, window.audioVolume = e, this.$refs.audio && (this.$refs.audio.volume = e / 100))
                        },
                        ensureSeekTime: function(e) {
                            this.startTime = e
                        },
                        toggle: function() {
                            this.playing ? this.$refs.audio && this.$refs.audio.pause() : this.play()
                        },
                        play: function(e) {
                            var t = this;
                            this.$refs.audio ? (e && (this.$refs.audio.load(), this.computeDuration()), e && !this.autoPlay || this.$refs.audio.play()) : setTimeout((function() {
                                t.play(e)
                            }), 10)
                        },
                        prevChapter: function() {
                            this.autoPlay = !0, this.$emit("prevChapter")
                        },
                        nextChapter: function() {
                            this.autoPlay = !0, this.$emit("nextChapter")
                        },
                        computeDuration: function() {
                            var e = this;
                            if (this.$refs.audio) {
                                var t = this.$refs.audio.duration;
                                this.$refs.audio.readyState >= 1 && !isNaN(t) && t !== 1 / 0 && t ? (this.audioDuration = parseInt(t), this.$refs.audio.playbackRate = this.currentSpeed, this.$refs.audio.currentTime = this.startTime, this.autoPlay && this.$refs.audio.play(), window.audioVolume && this.setAudioVolume(window.audioVolume)) : setTimeout((function() {
                                    e.computeDuration()
                                }), 50)
                            } else setTimeout((function() {
                                e.computeDuration()
                            }), 100)
                        },
                        onProgress: function() {},
                        onTimeupdate: function() {
                            this.$refs.audio && (this.currentTime = 0 | this.$refs.audio.currentTime), this.$emit("updateProgress")
                        },
                        onPlay: function() {
                            this.playing = !0
                        },
                        onPause: function() {
                            this.playing = !1
                        },
                        onEnd: function() {
                            this.playing = !1, this.currentTime = 0, this.audioDuration = 0, this.autoPlay = !0, this.$emit("nextChapter")
                        },
                        onError: function(e) {
                            this.$message.error(e.toString()), this.playing = !1
                        },
                        onSeeked: function() {},
                        onSeeking: function() {},
                        audioEvent: function() {},
                        onsuspend: function() {},
                        onCanPlay: function() {},
                        onWaiting: function() {},
                        loadCustomFontFamil: function() {
                            this.currentCustomFontFamily && (Object(N["f"])(this.currentCustomFontFamily.name, this.currentCustomFontFamily.url), this.isEpub && this.isEpubIframe && this.setIframeStyle())
                        }
                    }
                },
                yt = Et,
                gt = (n("25fe"), n("09f0"), Object(m["a"])(yt, lt, ct, !1, null, "59408c4f", null)),
                bt = gt.exports;
            n("6eba");

            function vt(e) {
                var t = Date.now();
                window.requestAnimationFrame((function n() {
                    var a = (Date.now() - t) / e.duration;
                    a > 1 && (a = 1);
                    var u = e.timing(a);
                    e.draw(u), a < 1 ? window.requestAnimationFrame(n) : e.onEnd && e.onEnd()
                }))
            }
            window.requestAnimationFrame || (window.requestAnimationFrame = function(e) {
                return setTimeout(e, 1e3 / 60)
            }), vt.Timings = {
                linear: function(e) {
                    return e
                },
                circle: function(e) {
                    return 1 - Math.sin(Math.acos(e))
                },
                circle2: function(e) {
                    return 1 - Math.pow(1 - Math.pow(e, 2), .5)
                },
                bounce: function(e) {
                    for (var t = 0, n = 1; t += n, n /= 2;)
                        if (e >= (7 - 4 * t) / 11) return -Math.pow((11 - 6 * t - 11 * e) / 4, 2) + Math.pow(n, 2)
                },
                power: function(e, t) {
                    return Math.pow(t, e)
                },
                back: function(e, t) {
                    return Math.pow(t, 2) * ((e + 1) * t - e)
                },
                elastic: function(e, t) {
                    return Math.pow(2, 10 * (t - 1)) * Math.cos(20 * Math.PI * e / 3 * t)
                }
            }, vt.Utils = {
                makeEaseOut: function(e) {
                    return function(t) {
                        return 1 - e(1 - t)
                    }
                },
                makeEaseInOut: function(e) {
                    return function(t) {
                        return t < .5 ? e(2 * t) / 2 : 1 - e(2 * (1 - t)) / 2
                    }
                }
            };
            var Bt = vt,
                kt = {
                    "中文（上海话，简体）": {
                        ShortName: ["wuu-CN-XiaotongNeural", "wuu-CN-YunzheNeural"],
                        LocalName: ["晓彤", "云哲"]
                    },
                    "中文（广东话，简体）": {
                        ShortName: ["yue-CN-XiaoMinNeural", "yue-CN-YunSongNeural"],
                        LocalName: ["晓敏", "云松"]
                    },
                    "中文（普通话，简体）": {
                        ShortName: ["zh-CN-XiaoxiaoNeural", "zh-CN-YunyangNeural", "zh-CN-YunxiNeural", "zh-CN-XiaoyiNeural", "zh-CN-YunjianNeural", "zh-CN-YunxiaNeural"],
                        LocalName: ["晓晓（女 - 年轻人）", "云扬（男 - 年轻人）", "云希（男 - 年轻人 - 抖音热门）", "晓伊（女 - 儿童）", "云健（男 - 中年人）", "云夏（男 - 儿童）"]
                    },
                    "中文（河南话，简体）": {
                        ShortName: ["zh-CN-henan-YundengNeural"],
                        LocalName: ["云登"]
                    },
                    "中文（东北话，简体）": {
                        ShortName: ["zh-CN-liaoning-XiaobeiNeural"],
                        LocalName: ["晓北"]
                    },
                    "中文（陕西话，简体）": {
                        ShortName: ["zh-CN-shaanxi-XiaoniNeural"],
                        LocalName: ["晓妮"]
                    },
                    "中文（山东话，简体）": {
                        ShortName: ["zh-CN-shandong-YunxiangNeural"],
                        LocalName: ["云翔"]
                    },
                    "中文（四川话，简体）": {
                        ShortName: ["zh-CN-sichuan-YunxiNeural"],
                        LocalName: ["云希"]
                    },
                    "中文（香港话，繁体）": {
                        ShortName: ["zh-HK-HiuMaanNeural", "zh-HK-HiuGaaiNeural", "zh-HK-WanLungNeural"],
                        LocalName: ["曉曼", "曉佳", "雲龍"]
                    },
                    "中文（台湾话，繁体）": {
                        ShortName: ["zh-TW-HsiaoChenNeural", "zh-TW-HsiaoYuNeural", "zh-TW-YunJheNeural"],
                        LocalName: ["曉臻", "曉雨", "雲哲"]
                    },
                    "English (Australia)": {
                        ShortName: ["en-AU-NatashaNeural", "en-AU-WilliamNeural", "en-AU-AnnetteNeural", "en-AU-CarlyNeural", "en-AU-DarrenNeural", "en-AU-DuncanNeural", "en-AU-ElsieNeural", "en-AU-FreyaNeural", "en-AU-JoanneNeural", "en-AU-KenNeural", "en-AU-KimNeural", "en-AU-NeilNeural", "en-AU-TimNeural", "en-AU-TinaNeural"],
                        LocalName: ["Natasha", "William", "Annette", "Carly", "Darren", "Duncan", "Elsie", "Freya", "Joanne", "Ken", "Kim", "Neil", "Tim", "Tina"]
                    },
                    "English (Canada)": {
                        ShortName: ["en-CA-ClaraNeural", "en-CA-LiamNeural"],
                        LocalName: ["Clara", "Liam"]
                    },
                    "English (United Kingdom)": {
                        ShortName: ["en-GB-LibbyNeural", "en-GB-AbbiNeural", "en-GB-AlfieNeural", "en-GB-BellaNeural", "en-GB-ElliotNeural", "en-GB-EthanNeural", "en-GB-HollieNeural", "en-GB-MaisieNeural", "en-GB-NoahNeural", "en-GB-OliverNeural", "en-GB-OliviaNeural", "en-GB-ThomasNeural", "en-GB-RyanNeural", "en-GB-SoniaNeural", "en-GB-MiaNeural"],
                        LocalName: ["Libby", "Abbi", "Alfie", "Bella", "Elliot", "Ethan", "Hollie", "Maisie", "Noah", "Oliver", "Olivia", "Thomas", "Ryan", "Sonia", "Mia"]
                    },
                    "English (Hongkong)": {
                        ShortName: ["en-HK-SamNeural", "en-HK-YanNeural"],
                        LocalName: ["Sam", "Yan"]
                    },
                    "English (Ireland)": {
                        ShortName: ["en-IE-ConnorNeural", "en-IE-EmilyNeural"],
                        LocalName: ["Connor", "Emily"]
                    },
                    "English (India)": {
                        ShortName: ["en-IN-NeerjaNeural", "en-IN-PrabhatNeural"],
                        LocalName: ["Neerja", "Prabhat"]
                    },
                    "English (Kenya)": {
                        ShortName: ["en-KE-AsiliaNeural", "en-KE-ChilembaNeural"],
                        LocalName: ["Asilia", "Chilemba"]
                    },
                    "English (Nigeria)": {
                        ShortName: ["en-NG-AbeoNeural", "en-NG-EzinneNeural"],
                        LocalName: ["Abeo", "Ezinne"]
                    },
                    "English (New Zealand)": {
                        ShortName: ["en-NZ-MitchellNeural", "en-NZ-MollyNeural"],
                        LocalName: ["Mitchell", "Molly"]
                    },
                    "English (Philippines)": {
                        ShortName: ["en-PH-JamesNeural", "en-PH-RosaNeural"],
                        LocalName: ["James", "Rosa"]
                    },
                    "English (Singapore)": {
                        ShortName: ["en-SG-LunaNeural", "en-SG-WayneNeural"],
                        LocalName: ["Luna", "Wayne"]
                    },
                    "English (Tanzania)": {
                        ShortName: ["en-TZ-ElimuNeural", "en-TZ-ImaniNeural"],
                        LocalName: ["Elimu", "Imani"]
                    },
                    "English (United States)": {
                        ShortName: ["en-US-JennyNeural", "en-US-JennyMultilingualNeural", "en-US-GuyNeural", "en-US-AmberNeural", "en-US-AnaNeural", "en-US-AriaNeural", "en-US-AshleyNeural", "en-US-BrandonNeural", "en-US-ChristopherNeural", "en-US-CoraNeural", "en-US-DavisNeural", "en-US-ElizabethNeural", "en-US-EricNeural", "en-US-JacobNeural", "en-US-JaneNeural", "en-US-JasonNeural", "en-US-MichelleNeural", "en-US-MonicaNeural", "en-US-NancyNeural", "en-US-SaraNeural", "en-US-TonyNeural", "en-US-AIGenerate1Neural", "en-US-AIGenerate2Neural", "en-US-RogerNeural", "en-US-SteffanNeural"],
                        LocalName: ["Jenny", "Jenny Multilingual", "Guy", "Amber", "Ana", "Aria", "Ashley", "Brandon", "Christopher", "Cora", "Davis", "Elizabeth", "Eric", "Jacob", "Jane", "Jason", "Michelle", "Monica", "Nancy", "Sara", "Tony", "AIGenerate1", "AIGenerate2", "Roger", "Steffan"]
                    },
                    "English (South Africa)": {
                        ShortName: ["en-ZA-LeahNeural", "en-ZA-LukeNeural"],
                        LocalName: ["Leah", "Luke"]
                    },
                    "Afrikaans (South Africa)": {
                        ShortName: ["af-ZA-AdriNeural", "af-ZA-WillemNeural"],
                        LocalName: ["Adri", "Willem"]
                    },
                    "Amharic (Ethiopia)": {
                        ShortName: ["am-ET-AmehaNeural", "am-ET-MekdesNeural"],
                        LocalName: ["አምሀ", "መቅደስ"]
                    },
                    "Arabic (United Arab Emirates)": {
                        ShortName: ["ar-AE-FatimaNeural", "ar-AE-HamdanNeural"],
                        LocalName: ["فاطمة", "حمدان"]
                    },
                    "Arabic (Bahrain)": {
                        ShortName: ["ar-BH-AliNeural", "ar-BH-LailaNeural"],
                        LocalName: ["علي", "ليلى"]
                    },
                    "Arabic (Algeria)": {
                        ShortName: ["ar-DZ-AminaNeural", "ar-DZ-IsmaelNeural"],
                        LocalName: ["أمينة", "إسماعيل"]
                    },
                    "Arabic (Egypt)": {
                        ShortName: ["ar-EG-SalmaNeural", "ar-EG-ShakirNeural"],
                        LocalName: ["سلمى", "شاكر"]
                    },
                    "Arabic (Iraq)": {
                        ShortName: ["ar-IQ-BasselNeural", "ar-IQ-RanaNeural"],
                        LocalName: ["باسل", "رنا"]
                    },
                    "Arabic (Jordan)": {
                        ShortName: ["ar-JO-SanaNeural", "ar-JO-TaimNeural"],
                        LocalName: ["سناء", "تيم"]
                    },
                    "Arabic (Kuwait)": {
                        ShortName: ["ar-KW-FahedNeural", "ar-KW-NouraNeural"],
                        LocalName: ["فهد", "نورا"]
                    },
                    "Arabic (Lebanon)": {
                        ShortName: ["ar-LB-LaylaNeural", "ar-LB-RamiNeural"],
                        LocalName: ["ليلى", "رامي"]
                    },
                    "Arabic (Libya)": {
                        ShortName: ["ar-LY-ImanNeural", "ar-LY-OmarNeural"],
                        LocalName: ["إيمان", "أحمد"]
                    },
                    "Arabic (Morocco)": {
                        ShortName: ["ar-MA-JamalNeural", "ar-MA-MounaNeural"],
                        LocalName: ["جمال", "منى"]
                    },
                    "Arabic (Oman)": {
                        ShortName: ["ar-OM-AbdullahNeural", "ar-OM-AyshaNeural"],
                        LocalName: ["عبدالله", "عائشة"]
                    },
                    "Arabic (Qatar)": {
                        ShortName: ["ar-QA-AmalNeural", "ar-QA-MoazNeural"],
                        LocalName: ["أمل", "معاذ"]
                    },
                    "Arabic (Saudi Arabia)": {
                        ShortName: ["ar-SA-HamedNeural", "ar-SA-ZariyahNeural"],
                        LocalName: ["حامد", "زارية"]
                    },
                    "Arabic (Syria)": {
                        ShortName: ["ar-SY-AmanyNeural", "ar-SY-LaithNeural"],
                        LocalName: ["أماني", "ليث"]
                    },
                    "Arabic (Tunisia)": {
                        ShortName: ["ar-TN-HediNeural", "ar-TN-ReemNeural"],
                        LocalName: ["هادي", "ريم"]
                    },
                    "Arabic (Yemen)": {
                        ShortName: ["ar-YE-MaryamNeural", "ar-YE-SalehNeural"],
                        LocalName: ["مريم", "صالح"]
                    },
                    "Azerbaijani (Azerbaijan)": {
                        ShortName: ["az-AZ-BabekNeural", "az-AZ-BanuNeural"],
                        LocalName: ["Babək", "Banu"]
                    },
                    "Bulgarian (Bulgaria)": {
                        ShortName: ["bg-BG-BorislavNeural", "bg-BG-KalinaNeural"],
                        LocalName: ["Борислав", "Калина"]
                    },
                    "Bangla (Bangladesh)": {
                        ShortName: ["bn-BD-NabanitaNeural", "bn-BD-PradeepNeural"],
                        LocalName: ["নবনীতা", "প্রদ্বীপ"]
                    },
                    "Bengali (India)": {
                        ShortName: ["bn-IN-BashkarNeural", "bn-IN-TanishaaNeural"],
                        LocalName: ["ভাস্কর", "তানিশা"]
                    },
                    "Bosnian (Bosnia)": {
                        ShortName: ["bs-BA-GoranNeural", "bs-BA-VesnaNeural"],
                        LocalName: ["Goran", "Vesna"]
                    },
                    "Catalan (Spain)": {
                        ShortName: ["ca-ES-JoanaNeural", "ca-ES-AlbaNeural", "ca-ES-EnricNeural"],
                        LocalName: ["Joana", "Alba", "Enric"]
                    },
                    "Czech (Czech)": {
                        ShortName: ["cs-CZ-AntoninNeural", "cs-CZ-VlastaNeural"],
                        LocalName: ["Antonín", "Vlasta"]
                    },
                    "Welsh (United Kingdom)": {
                        ShortName: ["cy-GB-AledNeural", "cy-GB-NiaNeural"],
                        LocalName: ["Aled", "Nia"]
                    },
                    "Danish (Denmark)": {
                        ShortName: ["da-DK-ChristelNeural", "da-DK-JeppeNeural"],
                        LocalName: ["Christel", "Jeppe"]
                    },
                    "German (Austria)": {
                        ShortName: ["de-AT-IngridNeural", "de-AT-JonasNeural"],
                        LocalName: ["Ingrid", "Jonas"]
                    },
                    "German (Switzerland)": {
                        ShortName: ["de-CH-JanNeural", "de-CH-LeniNeural"],
                        LocalName: ["Jan", "Leni"]
                    },
                    "German (Germany)": {
                        ShortName: ["de-DE-KatjaNeural", "de-DE-AmalaNeural", "de-DE-BerndNeural", "de-DE-ChristophNeural", "de-DE-ConradNeural", "de-DE-ElkeNeural", "de-DE-GiselaNeural", "de-DE-KasperNeural", "de-DE-KillianNeural", "de-DE-KlarissaNeural", "de-DE-KlausNeural", "de-DE-LouisaNeural", "de-DE-MajaNeural", "de-DE-RalfNeural", "de-DE-TanjaNeural"],
                        LocalName: ["Katja", "Amala", "Bernd", "Christoph", "Conrad", "Elke", "Gisela", "Kasper", "Killian", "Klarissa", "Klaus", "Louisa", "Maja", "Ralf", "Tanja"]
                    },
                    "Greek (Greece)": {
                        ShortName: ["el-GR-AthinaNeural", "el-GR-NestorasNeural"],
                        LocalName: ["Αθηνά", "Νέστορας"]
                    },
                    "Spanish (Argentina)": {
                        ShortName: ["es-AR-ElenaNeural", "es-AR-TomasNeural"],
                        LocalName: ["Elena", "Tomas"]
                    },
                    "Spanish (Bolivia)": {
                        ShortName: ["es-BO-MarceloNeural", "es-BO-SofiaNeural"],
                        LocalName: ["Marcelo", "Sofia"]
                    },
                    "Spanish (Chile)": {
                        ShortName: ["es-CL-CatalinaNeural", "es-CL-LorenzoNeural"],
                        LocalName: ["Catalina", "Lorenzo"]
                    },
                    "Spanish (Colombia)": {
                        ShortName: ["es-CO-GonzaloNeural", "es-CO-SalomeNeural"],
                        LocalName: ["Gonzalo", "Salome"]
                    },
                    "Spanish (Costa Rica)": {
                        ShortName: ["es-CR-JuanNeural", "es-CR-MariaNeural"],
                        LocalName: ["Juan", "María"]
                    },
                    "Spanish (Cuba)": {
                        ShortName: ["es-CU-BelkysNeural", "es-CU-ManuelNeural"],
                        LocalName: ["Belkys", "Manuel"]
                    },
                    "Spanish (Dominican Republic)": {
                        ShortName: ["es-DO-EmilioNeural", "es-DO-RamonaNeural"],
                        LocalName: ["Emilio", "Ramona"]
                    },
                    "Spanish (Ecuador)": {
                        ShortName: ["es-EC-AndreaNeural", "es-EC-LuisNeural"],
                        LocalName: ["Andrea", "Luis"]
                    },
                    "Spanish (Spain)": {
                        ShortName: ["es-ES-ElviraNeural", "es-ES-AbrilNeural", "es-ES-AlvaroNeural", "es-ES-ArnauNeural", "es-ES-DarioNeural", "es-ES-EliasNeural", "es-ES-EstrellaNeural", "es-ES-IreneNeural", "es-ES-LaiaNeural", "es-ES-LiaNeural", "es-ES-NilNeural", "es-ES-SaulNeural", "es-ES-TeoNeural", "es-ES-TrianaNeural", "es-ES-VeraNeural"],
                        LocalName: ["Elvira", "Abril", "Álvaro", "Arnau", "Dario", "Elias", "Estrella", "Irene", "Laia", "Lia", "Nil", "Saul", "Teo", "Triana", "Vera"]
                    },
                    "Spanish (Equatorial Guinea)": {
                        ShortName: ["es-GQ-JavierNeural", "es-GQ-TeresaNeural"],
                        LocalName: ["Javier", "Teresa"]
                    },
                    "Spanish (Guatemala)": {
                        ShortName: ["es-GT-AndresNeural", "es-GT-MartaNeural"],
                        LocalName: ["Andrés", "Marta"]
                    },
                    "Spanish (Honduras)": {
                        ShortName: ["es-HN-CarlosNeural", "es-HN-KarlaNeural"],
                        LocalName: ["Carlos", "Karla"]
                    },
                    "Spanish (Mexico)": {
                        ShortName: ["es-MX-DaliaNeural", "es-MX-BeatrizNeural", "es-MX-CandelaNeural", "es-MX-CarlotaNeural", "es-MX-CecilioNeural", "es-MX-GerardoNeural", "es-MX-JorgeNeural", "es-MX-LarissaNeural", "es-MX-LibertoNeural", "es-MX-LucianoNeural", "es-MX-MarinaNeural", "es-MX-NuriaNeural", "es-MX-PelayoNeural", "es-MX-RenataNeural", "es-MX-YagoNeural"],
                        LocalName: ["Dalia", "Beatriz", "Candela", "Carlota", "Cecilio", "Gerardo", "Jorge", "Larissa", "Liberto", "Luciano", "Marina", "Nuria", "Pelayo", "Renata", "Yago"]
                    },
                    "Spanish (Nicaragua)": {
                        ShortName: ["es-NI-FedericoNeural", "es-NI-YolandaNeural"],
                        LocalName: ["Federico", "Yolanda"]
                    },
                    "Spanish (Panama)": {
                        ShortName: ["es-PA-MargaritaNeural", "es-PA-RobertoNeural"],
                        LocalName: ["Margarita", "Roberto"]
                    },
                    "Spanish (Peru)": {
                        ShortName: ["es-PE-AlexNeural", "es-PE-CamilaNeural"],
                        LocalName: ["Alex", "Camila"]
                    },
                    "Spanish (Puerto Rico)": {
                        ShortName: ["es-PR-KarinaNeural", "es-PR-VictorNeural"],
                        LocalName: ["Karina", "Víctor"]
                    },
                    "Spanish (Paraguay)": {
                        ShortName: ["es-PY-MarioNeural", "es-PY-TaniaNeural"],
                        LocalName: ["Mario", "Tania"]
                    },
                    "Spanish (El Salvador)": {
                        ShortName: ["es-SV-LorenaNeural", "es-SV-RodrigoNeural"],
                        LocalName: ["Lorena", "Rodrigo"]
                    },
                    "Spanish (United States)": {
                        ShortName: ["es-US-AlonsoNeural", "es-US-PalomaNeural"],
                        LocalName: ["Alonso", "Paloma"]
                    },
                    "Spanish (Uruguay)": {
                        ShortName: ["es-UY-MateoNeural", "es-UY-ValentinaNeural"],
                        LocalName: ["Mateo", "Valentina"]
                    },
                    "Spanish (Venezuela)": {
                        ShortName: ["es-VE-PaolaNeural", "es-VE-SebastianNeural"],
                        LocalName: ["Paola", "Sebastián"]
                    },
                    "Estonian (Estonia)": {
                        ShortName: ["et-EE-AnuNeural", "et-EE-KertNeural"],
                        LocalName: ["Anu", "Kert"]
                    },
                    Basque: {
                        ShortName: ["eu-ES-AinhoaNeural", "eu-ES-AnderNeural"],
                        LocalName: ["Ainhoa", "Ander"]
                    },
                    "Persian (Iran)": {
                        ShortName: ["fa-IR-DilaraNeural", "fa-IR-FaridNeural"],
                        LocalName: ["دلارا", "فرید"]
                    },
                    "Finnish (Finland)": {
                        ShortName: ["fi-FI-SelmaNeural", "fi-FI-HarriNeural", "fi-FI-NooraNeural"],
                        LocalName: ["Selma", "Harri", "Noora"]
                    },
                    "Filipino (Philippines)": {
                        ShortName: ["fil-PH-AngeloNeural", "fil-PH-BlessicaNeural"],
                        LocalName: ["Angelo", "Blessica"]
                    },
                    "French (Belgium)": {
                        ShortName: ["fr-BE-CharlineNeural", "fr-BE-GerardNeural"],
                        LocalName: ["Charline", "Gerard"]
                    },
                    "French (Canada)": {
                        ShortName: ["fr-CA-SylvieNeural", "fr-CA-AntoineNeural", "fr-CA-JeanNeural"],
                        LocalName: ["Sylvie", "Antoine", "Jean"]
                    },
                    "French (Switzerland)": {
                        ShortName: ["fr-CH-ArianeNeural", "fr-CH-FabriceNeural"],
                        LocalName: ["Ariane", "Fabrice"]
                    },
                    "French (France)": {
                        ShortName: ["fr-FR-AlainNeural", "fr-FR-BrigitteNeural", "fr-FR-CelesteNeural", "fr-FR-ClaudeNeural", "fr-FR-CoralieNeural", "fr-FR-EloiseNeural", "fr-FR-JacquelineNeural", "fr-FR-JeromeNeural", "fr-FR-JosephineNeural", "fr-FR-MauriceNeural", "fr-FR-YvesNeural", "fr-FR-YvetteNeural", "fr-FR-DeniseNeural", "fr-FR-HenriNeural"],
                        LocalName: ["Alain", "Brigitte", "Celeste", "Claude", "Coralie", "Eloise", "Jacqueline", "Jerome", "Josephine", "Maurice", "Yves", "Yvette", "Denise", "Henri"]
                    },
                    "Irish (Ireland)": {
                        ShortName: ["ga-IE-ColmNeural", "ga-IE-OrlaNeural"],
                        LocalName: ["Colm", "Orla"]
                    },
                    Galicia: {
                        ShortName: ["gl-ES-RoiNeural", "gl-ES-SabelaNeural"],
                        LocalName: ["Roi", "Sabela"]
                    },
                    "Gujarati (India)": {
                        ShortName: ["gu-IN-DhwaniNeural", "gu-IN-NiranjanNeural"],
                        LocalName: ["ધ્વની", "નિરંજન"]
                    },
                    "Hebrew (Israel)": {
                        ShortName: ["he-IL-AvriNeural", "he-IL-HilaNeural"],
                        LocalName: ["אברי", "הילה"]
                    },
                    "Hindi (India)": {
                        ShortName: ["hi-IN-MadhurNeural", "hi-IN-SwaraNeural"],
                        LocalName: ["मधुर", "स्वरा"]
                    },
                    "Croatian (Croatia)": {
                        ShortName: ["hr-HR-GabrijelaNeural", "hr-HR-SreckoNeural"],
                        LocalName: ["Gabrijela", "Srećko"]
                    },
                    "Hungarian (Hungary)": {
                        ShortName: ["hu-HU-NoemiNeural", "hu-HU-TamasNeural"],
                        LocalName: ["Noémi", "Tamás"]
                    },
                    "Armenian (Armenia)": {
                        ShortName: ["hy-AM-AnahitNeural", "hy-AM-HaykNeural"],
                        LocalName: ["Անահիտ", "Հայկ"]
                    },
                    "Indonesian (Indonesia)": {
                        ShortName: ["id-ID-ArdiNeural", "id-ID-GadisNeural"],
                        LocalName: ["Ardi", "Gadis"]
                    },
                    "Icelandic (Iceland)": {
                        ShortName: ["is-IS-GudrunNeural", "is-IS-GunnarNeural"],
                        LocalName: ["Guðrún", "Gunnar"]
                    },
                    "Italian (Italy)": {
                        ShortName: ["it-IT-IsabellaNeural", "it-IT-ElsaNeural", "it-IT-BenignoNeural", "it-IT-CalimeroNeural", "it-IT-CataldoNeural", "it-IT-DiegoNeural", "it-IT-FabiolaNeural", "it-IT-FiammaNeural", "it-IT-GianniNeural", "it-IT-ImeldaNeural", "it-IT-IrmaNeural", "it-IT-LisandroNeural", "it-IT-PalmiraNeural", "it-IT-PierinaNeural", "it-IT-RinaldoNeural"],
                        LocalName: ["Isabella", "Elsa", "Benigno", "Calimero", "Cataldo", "Diego", "Fabiola", "Fiamma", "Gianni", "Imelda", "Irma", "Lisandro", "Palmira", "Pierina", "Rinaldo"]
                    },
                    "Japanese (Japan)": {
                        ShortName: ["ja-JP-NanamiNeural", "ja-JP-KeitaNeural", "ja-JP-AoiNeural", "ja-JP-DaichiNeural", "ja-JP-MayuNeural", "ja-JP-NaokiNeural", "ja-JP-ShioriNeural"],
                        LocalName: ["七海", "圭太", "碧衣", "大智", "真夕", "直紀", "志織"]
                    },
                    "Javanese (Indonesia)": {
                        ShortName: ["jv-ID-DimasNeural", "jv-ID-SitiNeural"],
                        LocalName: ["Dimas", "Siti"]
                    },
                    "Georgian (Georgia)": {
                        ShortName: ["ka-GE-EkaNeural", "ka-GE-GiorgiNeural"],
                        LocalName: ["ეკა", "გიორგი"]
                    },
                    "Kazakh (Kazakhstan)": {
                        ShortName: ["kk-KZ-AigulNeural", "kk-KZ-DauletNeural"],
                        LocalName: ["Айгүл", "Дәулет"]
                    },
                    "Khmer (Cambodia)": {
                        ShortName: ["km-KH-PisethNeural", "km-KH-SreymomNeural"],
                        LocalName: ["ពិសិដ្ឋ", "ស្រីមុំ"]
                    },
                    "Kannada (India)": {
                        ShortName: ["kn-IN-GaganNeural", "kn-IN-SapnaNeural"],
                        LocalName: ["ಗಗನ್", "ಸಪ್ನಾ"]
                    },
                    "Korean (Korea)": {
                        ShortName: ["ko-KR-SunHiNeural", "ko-KR-InJoonNeural", "ko-KR-BongJinNeural", "ko-KR-GookMinNeural", "ko-KR-JiMinNeural", "ko-KR-SeoHyeonNeural", "ko-KR-SoonBokNeural", "ko-KR-YuJinNeural"],
                        LocalName: ["선히", "인준", "봉진", "국민", "지민", "서현", "순복", "유진"]
                    },
                    "Lao (Laos)": {
                        ShortName: ["lo-LA-ChanthavongNeural", "lo-LA-KeomanyNeural"],
                        LocalName: ["ຈັນທະວົງ", "ແກ້ວມະນີ"]
                    },
                    "Lithuanian (Lithuania)": {
                        ShortName: ["lt-LT-LeonasNeural", "lt-LT-OnaNeural"],
                        LocalName: ["Leonas", "Ona"]
                    },
                    "Latvian (Latvia)": {
                        ShortName: ["lv-LV-EveritaNeural", "lv-LV-NilsNeural"],
                        LocalName: ["Everita", "Nils"]
                    },
                    "Macedonian (Republic of North Macedonia)": {
                        ShortName: ["mk-MK-AleksandarNeural", "mk-MK-MarijaNeural"],
                        LocalName: ["Александар", "Марија"]
                    },
                    "Malayalam (India)": {
                        ShortName: ["ml-IN-MidhunNeural", "ml-IN-SobhanaNeural"],
                        LocalName: ["മിഥുൻ", "ശോഭന"]
                    },
                    "Mongolian (Mongolia)": {
                        ShortName: ["mn-MN-BataaNeural", "mn-MN-YesuiNeural"],
                        LocalName: ["Батаа", "Есүй"]
                    },
                    "Marathi (India)": {
                        ShortName: ["mr-IN-AarohiNeural", "mr-IN-ManoharNeural"],
                        LocalName: ["आरोही", "मनोहर"]
                    },
                    "Malay (Malaysia)": {
                        ShortName: ["ms-MY-OsmanNeural", "ms-MY-YasminNeural"],
                        LocalName: ["Osman", "Yasmin"]
                    },
                    "Maltese (Malta)": {
                        ShortName: ["mt-MT-GraceNeural", "mt-MT-JosephNeural"],
                        LocalName: ["Grace", "Joseph"]
                    },
                    "Burmese (Myanmar)": {
                        ShortName: ["my-MM-NilarNeural", "my-MM-ThihaNeural"],
                        LocalName: ["နီလာ", "သီဟ"]
                    },
                    "Norwegian (Bokmål, Norway)": {
                        ShortName: ["nb-NO-PernilleNeural", "nb-NO-FinnNeural", "nb-NO-IselinNeural"],
                        LocalName: ["Pernille", "Finn", "Iselin"]
                    },
                    "Nepali (Nepal)": {
                        ShortName: ["ne-NP-HemkalaNeural", "ne-NP-SagarNeural"],
                        LocalName: ["हेमकला", "सागर"]
                    },
                    "Dutch (Belgium)": {
                        ShortName: ["nl-BE-ArnaudNeural", "nl-BE-DenaNeural"],
                        LocalName: ["Arnaud", "Dena"]
                    },
                    "Dutch (Netherlands)": {
                        ShortName: ["nl-NL-ColetteNeural", "nl-NL-FennaNeural", "nl-NL-MaartenNeural"],
                        LocalName: ["Colette", "Fenna", "Maarten"]
                    },
                    "Polish (Poland)": {
                        ShortName: ["pl-PL-AgnieszkaNeural", "pl-PL-MarekNeural", "pl-PL-ZofiaNeural"],
                        LocalName: ["Agnieszka", "Marek", "Zofia"]
                    },
                    "Pashto (Afghanistan)": {
                        ShortName: ["ps-AF-GulNawazNeural", "ps-AF-LatifaNeural"],
                        LocalName: [" ګل نواز", "لطيفه"]
                    },
                    "Portuguese (Brazil)": {
                        ShortName: ["pt-BR-FranciscaNeural", "pt-BR-AntonioNeural", "pt-BR-BrendaNeural", "pt-BR-DonatoNeural", "pt-BR-ElzaNeural", "pt-BR-FabioNeural", "pt-BR-GiovannaNeural", "pt-BR-HumbertoNeural", "pt-BR-JulioNeural", "pt-BR-LeilaNeural", "pt-BR-LeticiaNeural", "pt-BR-ManuelaNeural", "pt-BR-NicolauNeural", "pt-BR-ValerioNeural", "pt-BR-YaraNeural"],
                        LocalName: ["Francisca", "Antônio", "Brenda", "Donato", "Elza", "Fabio", "Giovanna", "Humberto", "Julio", "Leila", "Leticia", "Manuela", "Nicolau", "Valerio", "Yara"]
                    },
                    "Portuguese (Portugal)": {
                        ShortName: ["pt-PT-DuarteNeural", "pt-PT-FernandaNeural", "pt-PT-RaquelNeural"],
                        LocalName: ["Duarte", "Fernanda", "Raquel"]
                    },
                    "Romanian (Romania)": {
                        ShortName: ["ro-RO-AlinaNeural", "ro-RO-EmilNeural"],
                        LocalName: ["Alina", "Emil"]
                    },
                    "Russian (Russia)": {
                        ShortName: ["ru-RU-SvetlanaNeural", "ru-RU-DariyaNeural", "ru-RU-DmitryNeural"],
                        LocalName: ["Светлана", "Дария", "Дмитрий"]
                    },
                    "Sinhala (Sri Lanka)": {
                        ShortName: ["si-LK-SameeraNeural", "si-LK-ThiliniNeural"],
                        LocalName: ["සමීර", "තිළිණි"]
                    },
                    "Slovak (Slovakia)": {
                        ShortName: ["sk-SK-LukasNeural", "sk-SK-ViktoriaNeural"],
                        LocalName: ["Lukáš", "Viktória"]
                    },
                    "Slovenian (Slovenia)": {
                        ShortName: ["sl-SI-PetraNeural", "sl-SI-RokNeural"],
                        LocalName: ["Petra", "Rok"]
                    },
                    "Somali (Somalia)": {
                        ShortName: ["so-SO-MuuseNeural", "so-SO-UbaxNeural"],
                        LocalName: ["Muuse", "Ubax"]
                    },
                    "Albanian (Albania)": {
                        ShortName: ["sq-AL-AnilaNeural", "sq-AL-IlirNeural"],
                        LocalName: ["Anila", "Ilir"]
                    },
                    "Serbian (Serbia)": {
                        ShortName: ["sr-RS-NicholasNeural", "sr-RS-SophieNeural"],
                        LocalName: ["Никола", "Софија"]
                    },
                    "Sundanese (Indonesia)": {
                        ShortName: ["su-ID-JajangNeural", "su-ID-TutiNeural"],
                        LocalName: ["Jajang", "Tuti"]
                    },
                    "Swedish (Sweden)": {
                        ShortName: ["sv-SE-SofieNeural", "sv-SE-HilleviNeural", "sv-SE-MattiasNeural"],
                        LocalName: ["Sofie", "Hillevi", "Mattias"]
                    },
                    "Swahili (Kenya)": {
                        ShortName: ["sw-KE-RafikiNeural", "sw-KE-ZuriNeural"],
                        LocalName: ["Rafiki", "Zuri"]
                    },
                    "Swahili (Tanzania)": {
                        ShortName: ["sw-TZ-DaudiNeural", "sw-TZ-RehemaNeural"],
                        LocalName: ["Daudi", "Rehema"]
                    },
                    "Tamil (India)": {
                        ShortName: ["ta-IN-PallaviNeural", "ta-IN-ValluvarNeural"],
                        LocalName: ["பல்லவி", "வள்ளுவர்"]
                    },
                    "Tamil (Sri Lanka)": {
                        ShortName: ["ta-LK-KumarNeural", "ta-LK-SaranyaNeural"],
                        LocalName: ["குமார்", "சரண்யா"]
                    },
                    "Tamil (Malaysia)": {
                        ShortName: ["ta-MY-KaniNeural", "ta-MY-SuryaNeural"],
                        LocalName: ["கனி", "சூர்யா"]
                    },
                    "Tamil (Singapore)": {
                        ShortName: ["ta-SG-AnbuNeural", "ta-SG-VenbaNeural"],
                        LocalName: ["அன்பு", "வெண்பா"]
                    },
                    "Telugu (India)": {
                        ShortName: ["te-IN-MohanNeural", "te-IN-ShrutiNeural"],
                        LocalName: ["మోహన్", "శ్రుతి"]
                    },
                    "Thai (Thailand)": {
                        ShortName: ["th-TH-PremwadeeNeural", "th-TH-AcharaNeural", "th-TH-NiwatNeural"],
                        LocalName: ["เปรมวดี", "อัจฉรา", "นิวัฒน์"]
                    },
                    "Turkish (Turkey)": {
                        ShortName: ["tr-TR-AhmetNeural", "tr-TR-EmelNeural"],
                        LocalName: ["Ahmet", "Emel"]
                    },
                    "Ukrainian (Ukraine)": {
                        ShortName: ["uk-UA-OstapNeural", "uk-UA-PolinaNeural"],
                        LocalName: ["Остап", "Поліна"]
                    },
                    "Urdu (India)": {
                        ShortName: ["ur-IN-GulNeural", "ur-IN-SalmanNeural"],
                        LocalName: ["گل", "سلمان"]
                    },
                    "Urdu (Pakistan)": {
                        ShortName: ["ur-PK-AsadNeural", "ur-PK-UzmaNeural"],
                        LocalName: ["اسد", "عظمیٰ"]
                    },
                    "Uzbek (Uzbekistan)": {
                        ShortName: ["uz-UZ-MadinaNeural", "uz-UZ-SardorNeural"],
                        LocalName: ["Madina", "Sardor"]
                    },
                    "Vietnamese (Vietnam)": {
                        ShortName: ["vi-VN-HoaiMyNeural", "vi-VN-NamMinhNeural"],
                        LocalName: ["Hoài My", "Nam Minh"]
                    },
                    "Zulu (South Africa)": {
                        ShortName: ["zu-ZA-ThandoNeural", "zu-ZA-ThandoNeural"],
                        LocalName: ["Thando", "Thando"]
                    }
                },
                wt = kt["中文（普通话，简体）"].LocalName.map((function(e, t) {
                    return {
                        LocalName: e,
                        name: kt["中文（普通话，简体）"].ShortName[t]
                    }
                })),
                xt = /[\u2000-\u206F\u2E00-\u2E7F\\'!"#$%&\(\)*+,-\./:;<=>?@\[\]^_`{\|}~，。？《》；：、«]/g,
                St = n("30b5"),
                Nt = {
                    components: {
                        PopCata: v,
                        BookSource: U,
                        BookShelf: ee,
                        Content: bt,
                        ReadSettings: P
                    },
                    mounted: function() {
                        var e = this;
                        window.readerPage = this, this.speechAvalable = window.speechSynthesis && window.speechSynthesis.getVoices, this.speechAvalable && (this.fetchVoiceList(), void 0 !== window.speechSynthesis.onvoiceschanged && (window.speechSynthesis.onvoiceschanged = this.fetchVoiceList)), window.addEventListener("unload", this.saveReadingPosition), S["a"].$on("showSearchContent", (function(t) {
                            if (!e._inactive)
                                if (e.chapterIndex !== t.chapterIndex) {
                                    if (e.isScrollRead) return e.scrollStartChapterIndex = t.chapterIndex, void e.computeShowChapterList().then((function() {
                                        e.showMatchKeyword(t)
                                    }));
                                    e.onNextContentRendered((function() {
                                        e.$nextTick((function() {
                                            e.showMatchKeyword(t)
                                        }))
                                    })), e.getContent(t.chapterIndex)
                                } else e.showMatchKeyword(t)
                        })), S["a"].$on("showBookmark", (function(t) {
                            if (!e._inactive)
                                if (e.chapterIndex !== t.chapterIndex) {
                                    if (e.isScrollRead) return e.scrollStartChapterIndex = t.chapterIndex, void e.computeShowChapterList().then((function() {
                                        e.showBookmark(t)
                                    }));
                                    e.onNextContentRendered((function() {
                                        e.$nextTick((function() {
                                            e.showBookmark(t)
                                        }))
                                    })), e.getContent(t.chapterIndex)
                                } else e.showBookmark(t)
                        })), S["a"].$on("addBookmark", (function() {
                            e._inactive || e.addBookmark()
                        }))
                    },
                    activated: function() {
                        var e = this;
                        this.init(), window.addEventListener("keydown", this.keydownHandler), this.title ? document.title = this.$store.getters.readingBook.name + " - " + this.title : document.title = this.$store.getters.readingBook.name, this.formatTime(), this.timer = setInterval((function() {
                            e.formatTime()
                        }), 5e3), this.unwatchFn = this.$store.watch((function(e) {
                            return e.config
                        }), (function() {
                            e.$nextTick((function() {
                                e.computePages((function() {
                                    e.currentPage > e.totalPages && e.showPage(e.totalPages, 0)
                                }))
                            }))
                        }), {
                            deep: !0
                        }), window.addEventListener("scroll", this.scrollHandler);
                        try {
                            this.releaseWakeLockFn = this.wakeLock()
                        } catch (t) {}
                        this.$Lazyload.$on("loaded", this.lazyloadHandler)
                    },
                    deactivated: function() {
                        this.saveBookProgress(), this.startSavePosition = !1, this.lastReadingBook = this.$store.getters.readingBook, this.timer && clearInterval(this.timer), window.removeEventListener("keydown", this.keydownHandler), window.removeEventListener("scroll", this.scrollHandler), this.unwatchFn && this.unwatchFn(), this.releaseWakeLockFn && this.releaseWakeLockFn(), this.$Lazyload.$off("loaded", this.lazyloadHandler)
                    },
                    watch: {
                        chapterName: function(e) {
                            this.title = e
                        },
                        content: function() {
                            var e = this;
                            this.contentStyle = {}, this.transformX = 0, this.currentPage = 1, this.$nextTick((function() {
                                e.computePages(), e.saveReadingPosition()
                            })), this.isEpub && this.$once("iframeLoad", (function() {
                                e.computePages()
                            }))
                        },
                        readSettingsVisible: function(e) {},
                        title: function(e) {
                            document.title = e ? this.$store.getters.readingBook.name + " - " + e : this.$store.getters.readingBook.name
                        },
                        isSlideRead: function(e) {
                            var t = this;
                            e || (this.contentStyle = {}, this.transformX = 0, this.isEpub && this.$refs.bookContentRef.transformX(0)), this.$nextTick((function() {
                                t.computePages((function() {
                                    t.currentParagraph ? t.showParagraph(t.currentParagraph, !0) : t.showPage(t.currentPage, 0)
                                }))
                            }))
                        },
                        isScrollRead: function(e) {
                            e && (this.scrollStartChapterIndex = this.chapterIndex, this.computeShowChapterList())
                        },
                        windowSize: function() {
                            var e = this;
                            this.$nextTick((function() {
                                e.computePages((function() {
                                    e.showPage(e.currentPage, 0)
                                }))
                            }))
                        },
                        loginAuth: function(e) {
                            e && this.init(!0)
                        },
                        showReadBar: function(e) {
                            e && (this.showToolBar = !1)
                        },
                        readingBook: function(e, t) {
                            e.bookUrl !== t.bookUrl && (this.startSavePosition = !1, this.autoShowPosition())
                        },
                        currentPage: function(e, t) {
                            var n = this;
                            null != this.$store.getters.readingBook && e !== t && e >= this.totalPages - 2 && this.$store.getters.readingBook.index < this.$store.getters.readingBook.catalog.length - 1 && (this.isScrollRead || this.preCaching || (this.preCaching = !0, this.getBookContent(this.$store.getters.readingBook.index + 1, {
                                timeout: 1e3 * this.$store.getters.config.chapterRequestTimeout,
                                silent: !0
                            }, !1, !0).then((function() {
                                n.preCaching = !1
                            }))))
                        },
                        filterRules: function() {
                            this.isScrollRead ? this.computeShowChapterList() : this.content = this.filterContent(this.originalContent)
                        },
                        chineseFont: function() {
                            this.title = this.title, this.content = this.content, this.computeShowChapterList()
                        }
                    },
                    data: function() {
                        return {
                            title: "",
                            originalContent: "",
                            content: "",
                            error: !1,
                            popCataVisible: !1,
                            readSettingsVisible: !1,
                            popBookSourceVisible: !1,
                            popBookShelfVisible: !1,
                            showToolBar: !0,
                            book: null,
                            show: !1,
                            contentStyle: {},
                            currentPage: 1,
                            totalPages: 1,
                            transformX: 0,
                            transforming: !1,
                            showClickZone: !1,
                            timeStr: "",
                            progressValue: 1,
                            speechAvalable: !1,
                            showReadBar: !1,
                            speechSpeaking: !1,
                            localVoiceList: [],
                            showSpeechConfig: !0,
                            ttsTypeList: [{
                                name: "浏览器",
                                value: "local"
                            }, {
                                name: "大声朗读",
                                value: "edge"
                            }, {
                                name: "HttpTTS",
                                value: "httpTTS"
                            }],
                            currentTTSCacheKey: null,
                            currentParagraph: null,
                            startSavePosition: !1,
                            showCacheContentZone: !1,
                            isCachingContent: !1,
                            cachingContentTip: "",
                            autoReading: !1,
                            showChapterList: [],
                            scrollStartChapterIndex: 0,
                            showNextChapterSize: 1,
                            showPrevChapterSize: 0,
                            speechMinutes: 0,
                            speechEndTime: 0,
                            isEditContent: !1,
                            contentInnerStyle: {}
                        }
                    },
                    computed: {
                        readingBook: function() {
                            return this.$store.getters.readingBook || {}
                        },
                        catalog: function() {
                            return (this.$store.getters.readingBook || {}).catalog || []
                        },
                        chapterIndex: function() {
                            return 0 | ((this.$store.getters.readingBook || {}).index || 0)
                        },
                        windowSize: function() {
                            return this.$store.state.windowSize
                        },
                        config: function() {
                            return this.$store.getters.config
                        },
                        theme: function() {
                            return this.config.theme
                        },
                        animateMSTime: function() {
                            return this.config.animateMSTime
                        },
                        isNight: function() {
                            return this.$store.getters.isNight
                        },
                        bodyTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.body
                            }
                        },
                        isSlideRead: function() {
                            return !(this.autoReading || this.showReadBar || this.isAudio || this.isVideo) && this.$store.getters.isSlideRead
                        },
                        isScrollRead: function() {
                            return !this.isEpub && !this.isAudio && !this.isVideo && !this.isSlideRead && ("上下滚动" === this.config.readMethod || "上下滚动2" === this.config.readMethod)
                        },
                        chapterClass: function() {
                            return (this.isSlideRead ? "slide-reader " : "") + (this.isEpub ? "iframe" === this.config.epubMode ? "epub-iframe" : "epub-dom" : this.isCarToon ? "cartoon" : this.isAudio ? "audio" : this.isVideo ? "video" : "")
                        },
                        chapterTheme: function() {
                            var e = this.showReadBar ? {
                                paddingBottom: (this.showSpeechConfig ? 280 : 80) + "px"
                            } : {};
                            return "string" === typeof this.$store.getters.currentThemeConfig.content ? Object(i["a"])(Object(i["a"])({}, e), {}, {
                                background: this.$store.getters.currentThemeConfig.content,
                                width: this.readWidth
                            }) : Object(i["a"])(Object(i["a"])(Object(i["a"])({}, e), this.$store.getters.currentThemeConfig.content), {}, {
                                width: this.readWidth
                            })
                        },
                        leftBarTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popup,
                                marginLeft: this.$store.state.miniInterface ? 0 : -(this.readWidthConfig / 2 + 68) + "px",
                                display: this.$store.state.miniInterface && !this.showToolBar ? "none" : "block"
                            }
                        },
                        rightBarTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popupPure,
                                marginRight: this.$store.state.miniInterface ? 0 : -(this.readWidthConfig / 2 + 52) + "px",
                                display: this.$store.state.miniInterface && !this.showToolBar ? "none" : "block"
                            }
                        },
                        readBarTheme: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popupPure,
                                marginRight: this.$store.state.miniInterface ? 0 : -this.readWidthConfig / 2 + "px",
                                zIndex: 200,
                                display: this.speechAvalable && this.showReadBar ? "block" : "none",
                                width: this.$store.state.miniInterface ? "100vw" : "500px"
                            }
                        },
                        readWidth: function() {
                            return this.$store.state.miniInterface ? this.windowSize.width + "px" : this.readWidthConfig - 130 + "px"
                        },
                        readWidthConfig: function() {
                            var e = this.$store.getters.config.readWidth;
                            while (e > this.$store.state.windowSize.width - 140) e -= 20;
                            return e
                        },
                        popperWidth: function() {
                            return this.$store.state.miniInterface ? this.windowSize.width - 33 : this.readWidthConfig - 33
                        },
                        readingProgress: function() {
                            return this.catalog && this.catalog.length ? parseInt(100 * (this.chapterIndex + 1) / this.catalog.length) + "%" : ""
                        },
                        showPrevPageStyle: function() {
                            return this.isSlideRead && "固定模式" !== this.$store.getters.config.clickMethod ? {
                                left: 0,
                                top: 0,
                                bottom: 0,
                                right: this.windowSize.width / 2 + "px",
                                background: "#43987324",
                                paddingRight: .2 * this.windowSize.width + "px"
                            } : {
                                left: 0,
                                top: 0,
                                right: 0,
                                bottom: "固定模式" === this.$store.getters.config.clickMethod ? .7 * this.windowSize.height + "px" : this.windowSize.height / 2 + "px",
                                background: "#43987324"
                            }
                        },
                        showMenuZoneStyle: function() {
                            return {
                                top: .3 * this.windowSize.height + "px",
                                bottom: .3 * this.windowSize.height + "px",
                                left: .3 * this.windowSize.width + "px",
                                right: .3 * this.windowSize.width + "px",
                                background: "#636060",
                                zIndex: 10
                            }
                        },
                        showNextPageStyle: function() {
                            return this.isSlideRead && "固定模式" !== this.$store.getters.config.clickMethod ? {
                                right: 0,
                                top: 0,
                                bottom: 0,
                                left: this.windowSize.width / 2 + "px",
                                background: "#6b1a7324",
                                paddingLeft: .2 * this.windowSize.width + "px"
                            } : {
                                left: 0,
                                bottom: 0,
                                right: 0,
                                top: "固定模式" === this.$store.getters.config.clickMethod ? .7 * this.windowSize.height + "px" : this.windowSize.height / 2 + "px",
                                background: "#6b1a7324"
                            }
                        },
                        showNextPageExtraStyle: function() {
                            return "固定模式" === this.$store.getters.config.clickMethod ? {
                                top: .3 * this.windowSize.height + "px",
                                bottom: .3 * this.windowSize.height + "px",
                                right: 0,
                                left: .7 * this.windowSize.width + "px",
                                background: "#6b1a7324",
                                paddingRight: .2 * this.windowSize.width + "px"
                            } : {
                                display: "none"
                            }
                        },
                        showPrevPageExtraStyle: function() {
                            return "固定模式" === this.$store.getters.config.clickMethod ? {
                                top: .3 * this.windowSize.height + "px",
                                bottom: .3 * this.windowSize.height + "px",
                                left: 0,
                                right: .7 * this.windowSize.width + "px",
                                background: "#43987324",
                                paddingRight: .2 * this.windowSize.width + "px"
                            } : {
                                display: "none"
                            }
                        },
                        loginAuth: function() {
                            return this.$store.state.loginAuth
                        },
                        filterRules: function() {
                            return this.$store.state.filterRules
                        },
                        httpTTSList: function() {
                            return this.$store.state.httpTTS
                        },
                        themeBtnStyle: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popupPure
                            }
                        },
                        popupAbsoluteBtnStyle: function() {
                            return {
                                background: this.$store.getters.currentThemeConfig.popupPure
                            }
                        },
                        voiceName: {
                            get: function() {
                                return this.$store.state.speechVoiceConfig.voiceName
                            },
                            set: function(e) {
                                e !== this.$store.state.speechVoiceConfig.voiceName && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", Object(i["a"])(Object(i["a"])({}, this.$store.state.speechVoiceConfig), {}, {
                                    voiceName: e
                                }))
                            }
                        },
                        speechRate: {
                            get: function() {
                                return this.$store.state.speechVoiceConfig.speechRate
                            },
                            set: function(e) {
                                e !== this.$store.state.speechVoiceConfig.speechRate && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", Object(i["a"])(Object(i["a"])({}, this.$store.state.speechVoiceConfig), {}, {
                                    speechRate: e
                                }))
                            }
                        },
                        speechPitch: {
                            get: function() {
                                return this.$store.state.speechVoiceConfig.speechPitch
                            },
                            set: function(e) {
                                e !== this.$store.state.speechVoiceConfig.speechPitch && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", Object(i["a"])(Object(i["a"])({}, this.$store.state.speechVoiceConfig), {}, {
                                    speechPitch: e
                                }))
                            }
                        },
                        ttsType: {
                            get: function() {
                                return this.$store.state.speechVoiceConfig.ttsType
                            },
                            set: function(e) {
                                e !== this.$store.state.speechVoiceConfig.ttsType && this.speechSpeaking && this.restartSpeech(), this.$store.commit("setSpeechVoiceConfig", Object(i["a"])(Object(i["a"])({}, this.$store.state.speechVoiceConfig), {}, {
                                    ttsType: e
                                }))
                            }
                        },
                        cacheTTSAudio: {
                            get: function() {
                                return this.$store.state.speechVoiceConfig.cacheTTSAudio
                            },
                            set: function(e) {
                                this.$store.commit("setSpeechVoiceConfig", Object(i["a"])(Object(i["a"])({}, this.$store.state.speechVoiceConfig), {}, {
                                    cacheTTSAudio: e
                                }))
                            }
                        },
                        isCarToon: function() {
                            return !this.error && !this.isEpub && !this.isCbz && !this.isPdf && (this.content || "").indexOf("<img") >= 0
                        },
                        isAudio: function() {
                            return !this.error && 1 === this.$store.getters.readingBook.type
                        },
                        isVideo: function() {
                            return !this.error && 4 === this.$store.getters.readingBook.type
                        },
                        isEpub: function() {
                            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".epub")
                        },
                        isEpubResolve: function() {
                            return this.isEpub && "iframe" !== this.config.epubMode
                        },
                        isEpubIframe: function() {
                            return this.isEpub && "iframe" === this.config.epubMode
                        },
                        isCbz: function() {
                            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".cbz")
                        },
                        isPdf: function() {
                            return !this.error && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.bookUrl.toLowerCase().endsWith(".pdf")
                        },
                        scrollOffset: function() {
                            return this.$store.getters.config.fontSize * this.$store.getters.config.lineHeight * 2 + this.$store.getters.config.fontSize * this.$store.getters.config.paragraphSpace * 2
                        },
                        formatedTitle: function() {
                            return this.formatChinese(this.title)
                        },
                        chineseFont: function() {
                            return this.config.chineseFont
                        },
                        slideDistance: function() {
                            return 16 + this.config.horizontalPadding
                        },
                        voiceList: function() {
                            return "local" === this.ttsType ? this.localVoiceList : "edge" === this.ttsType ? wt : this.httpTTSList
                        }
                    },
                    methods: {
                        init: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            t.$store.getters.readingBook && t.$store.getters.readingBook.bookUrl ? (console.log("init: readingBook already set, skipping init"), t.initBook(e)) : null != t.$route.query && null != t.$route.query.bookUrl ? (console.log("init: loading book from URL parameters", t.$route.query.bookUrl), t.$store.getters.shelfBooks.length ? t.initBookFromUrl(e) : S["a"].$once("shelfBooksLoaded", (function() {
                                                t.initBookFromUrl(e)
                                            }))) : (t.$message.error("缓存信息丢失,请在书架选择书籍！"), t.title = "", t.content = "缓存信息丢失,请在书架选择书籍！", t.error = !0, t.show = !0);
                                        case 1:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        initBookFromUrl: function(e) {
                            var t, n, a = this,
                                u = this.$store.getters.shelfBooks.find((function(e) {
                                    return e.bookUrl === a.$route.query.bookUrl
                                }));
                            this.$store.commit("setReadingBook", Object(i["a"])(Object(i["a"])({}, u), {}, {
                                name: u.name,
                                bookUrl: u.bookUrl,
                                index: null !== (t = null !== (n = u.index) && void 0 !== n ? n : u.durChapterIndex) && void 0 !== t ? t : 0,
                                type: u.type,
                                coverUrl: u.customCoverUrl || u.coverUrl,
                                tocUrl: u.tocUrl,
                                author: u.author,
                                origin: u.origin,
                                originName: u.originName,
                                latestChapterTitle: u.latestChapterTitle,
                                intro: u.intro
                            })), this.initBook(e)
                        },
                        initBook: function(e) {
                            var t = this;
                            e || !this.lastReadingBook || this.lastReadingBook.bookUrl !== this.$store.getters.readingBook.bookUrl || this.lastReadingBook.index !== this.$store.getters.readingBook.index ? (this.title = "", this.show = !1, this.loading = this.$loading({
                                target: this.$refs.content,
                                lock: !0,
                                text: "正在获取内容",
                                spinner: "el-icon-loading",
                                background: "rgba(0,0,0,0)"
                            }), this.lastReadingBook = this.$store.getters.readingBook, this.autoShowPosition(), this.loadCatalog(!1, !0)) : (this.isScrollRead ? (this.scrollStartChapterIndex = this.chapterIndex, this.showPrevChapterSize = 0, this.computeShowChapterList().then((function() {
                                t.autoShowPosition(!0)
                            }))) : this.isEpubIframe ? this.autoShowPosition() : this.$nextTick((function() {
                                t.autoShowPosition(!0)
                            })), setTimeout((function() {
                                t.$store.commit("setReadingBook", t.lastReadingBook)
                            }), 100))
                        },
                        changeBook: function(e) {
                            this.$message.info("换书成功"), this.popBookShelfVisible = !1, this.show = !1, this.$store.commit("setReadingBook", e), this.$router.push({
                                path: "/reader",
                                query: {
                                    bookUrl: e.bookUrl
                                }
                            }), this.loadCatalog(!0, !0)
                        },
                        changeBookSource: function() {
                            this.popBookSourceVisible = !1, this.show = !1, this.tryRefresh = !1, this.loadCatalog(!0, !0)
                        },
                        loadCatalog: function(e, t) {
                            var n = this;
                            this.api ? this.getCatalog(e).then((function(e) {
                                if (e.data.isSuccess) {
                                    var a = Object.assign({}, n.$store.getters.readingBook);
                                    a.catalog = e.data.data, n.$store.commit("setReadingBook", a), n.$emit("loadCatalog");
                                    var u = a.index || 0;
                                    n.getContent(u)
                                } else t && (n.title = "", n.content = "获取章节目录失败！\n" + e.data.errorMsg, n.error = !0, n.show = !0, n.$emit("showContent")), n.loading.close()
                            }), (function(e) {
                                n.loading.close(), n.$message.error("获取书籍目录列表 " + (e && e.toString()))
                            })) : setTimeout((function() {
                                n.loadCatalog && n.loadCatalog(e)
                            }), 1e3)
                        },
                        getCatalog: function(e) {
                            var t = this,
                                n = {
                                    url: this.$store.getters.readingBook.bookUrl,
                                    refresh: e ? 1 : 0
                                };
                            return this.$route.query.search && (n.bookSourceUrl = this.$store.getters.readingBook.origin), Object(N["g"])((function() {
                                return p["a"].post(t.api + "/getChapterList", n)
                            }), this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + this.$store.getters.readingBook.bookUrl + "@chapterList")
                        },
                        refreshCatalog: function() {
                            return this.loadCatalog(!0)
                        },
                        getBookContent: function(e, t, n, a) {
                            return this.$root.$children[0].getBookContent(e, t, n, a)
                        },
                        refreshContent: function() {
                            this.autoShowPosition(), this.getContent(this.$store.getters.readingBook.index, !0)
                        },
                        gotoChapter: function(e) {
                            if ("undefined" !== typeof this.$store.getters.readingBook.catalog[e]) {
                                var t = Object(i["a"])({}, this.$store.getters.readingBook);
                                if (t.index = e, this.$store.commit("setReadingBook", t), this.isScrollRead) return this.scrollStartChapterIndex = e, void this.computeShowChapterList(!0);
                                this.getContent(e)
                            } else this.$message.error("章节信息错误")
                        },
                        getContent: function(e, t) {
                            var n = this;
                            this.show = !1, this.contentInnerStyle = {}, this.loading && this.loading.visible || (this.loading = this.$loading({
                                target: this.$refs.content,
                                lock: !0,
                                text: t ? "正在刷新内容" : "正在获取内容",
                                spinner: "el-icon-loading",
                                background: "rgba(0,0,0,0)"
                            }));
                            var a = this.$store.getters.readingBook.bookUrl;
                            try {
                                var u = Object(i["a"])({}, this.$store.getters.readingBook);
                                u.index = e, this.$store.commit("setReadingBook", u)
                            } catch (re) {
                                console.error(re)
                            }
                            if (this.toTop(0), this.$store.getters.readingBook.catalog[e]) {
                                var o = this.$store.getters.readingBook.catalog[e].title,
                                    r = this.$store.getters.readingBook.catalog[e].index;
                                this.title = o, this.getBookContent(r, {}, t).then((function(t) {
                                    if (a === n.$store.getters.readingBook.bookUrl && e === n.$store.getters.readingBook.index) {
                                        if (t.data.isCache && n.saveBookProgress(), t.data.isSuccess) {
                                            var u = t.data.data;
                                            n.originalContent = u, n.content = n.filterContent(u), n.addChapterContentToCache({
                                                bookUrl: a,
                                                index: e,
                                                title: o,
                                                content: u,
                                                error: !1
                                            }), n.loading.close(), n.error = !1, n.show = !0, n.$emit("showContent")
                                        } else n.content = "获取章节内容失败！\n" + t.data.errorMsg, n.addChapterContentToCache({
                                            bookUrl: a,
                                            index: e,
                                            title: o,
                                            content: "获取章节内容失败！\n" + t.data.errorMsg,
                                            error: !0
                                        }), n.error = !0, n.show = !0, n.$emit("showContent"), n.loading.close();
                                        n.isScrollRead && n.computeShowChapterList()
                                    }
                                }), (function(t) {
                                    if (a === n.$store.getters.readingBook.bookUrl && e === n.$store.getters.readingBook.index) throw n.content = "获取章节内容失败！\n" + (t && t.toString()), n.addChapterContentToCache({
                                        bookUrl: a,
                                        index: e,
                                        title: o,
                                        content: "获取章节内容失败！\n" + (t && t.toString()),
                                        error: !0
                                    }), n.error = !0, n.show = !0, n.$emit("showContent"), n.loading.close(), n.$message.error("获取章节内容失败 " + (t && t.toString())), n.isScrollRead && n.computeShowChapterList(), t
                                }))
                            } else this.tryRefresh ? (this.tryRefresh = !1, this.content = "获取章节内容失败，请更新目录！", this.error = !0, this.show = !0, this.$emit("showContent"), this.loading.close()) : (this.tryRefresh = !0, this.refreshCatalog())
                        },
                        toggleEditContent: function() {
                            this.isEditContent ? (this.isEditContent = !1, this.saveContent()) : this.isEditContent = !0
                        },
                        saveContent: function() {
                            var e = this;
                            return Object(r["a"])(Object(o["a"])().mark((function t() {
                                var n, a, u;
                                return Object(o["a"])().wrap((function(t) {
                                    while (1) switch (t.prev = t.next) {
                                        case 0:
                                            return t.next = 2, e.$confirm("是否保存章节内容?", "提示", {
                                                confirmButtonText: "保存",
                                                cancelButtonText: "取消",
                                                type: "warning"
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 2:
                                            if (n = t.sent, n) {
                                                t.next = 7;
                                                break
                                            }
                                            return e.autoShowPosition(), e.getContent(e.$store.getters.readingBook.index), t.abrupt("return");
                                        case 7:
                                            return a = e.getTextContent(e.getContentParagraphContainer()), u = "localCache@" + e.$store.getters.readingBook.name + "_" + e.$store.getters.readingBook.author + "@" + e.$store.getters.readingBook.bookUrl + "@chapterContent-" + e.chapterIndex, t.abrupt("return", p["a"].post(e.api + "/saveBookContent", {
                                                url: e.$store.getters.readingBook.bookUrl,
                                                index: e.chapterIndex,
                                                content: a
                                            }).then(function() {
                                                var t = Object(r["a"])(Object(o["a"])().mark((function t(n) {
                                                    return Object(o["a"])().wrap((function(t) {
                                                        while (1) switch (t.prev = t.next) {
                                                            case 0:
                                                                if (!n.data.isSuccess) {
                                                                    t.next = 4;
                                                                    break
                                                                }
                                                                return t.next = 3, window.$cacheStorage.removeItem(u)["catch"]((function() {}));
                                                            case 3:
                                                                e.$message.success("保存成功！注意：刷新章节内容将会失效");
                                                            case 4:
                                                            case "end":
                                                                return t.stop()
                                                        }
                                                    }), t)
                                                })));
                                                return function(e) {
                                                    return t.apply(this, arguments)
                                                }
                                            }())["catch"]((function(t) {
                                                e.$message.error("保存章节内容失败 " + (t && t.toString()))
                                            })));
                                        case 10:
                                        case "end":
                                            return t.stop()
                                    }
                                }), t)
                            })))()
                        },
                        getTextContent: function(e) {
                            if (!e) return "";
                            if ("H3" === e.nodeName) return "";
                            if ("P" === e.nodeName) return e.innerText;
                            if ("IMG" === e.nodeName) {
                                var t = e.cloneNode(!0),
                                    n = t.getAttribute("data-error");
                                return t.removeAttribute("data-error"), t.removeAttribute("data-src"), t.removeAttribute("lazy"), t.setAttribute("src", n), t.outerHTML
                            }
                            if ("#text" === e.nodeName) return e.nodeValue;
                            for (var a = "", u = 0; u < e.childNodes.length; u++) u > 0 && "#text" !== e.childNodes[u].nodeName && (a += "\n"), a += this.getTextContent(e.childNodes[u]);
                            return a.replace(/^\n*/, "").replace(/\n*$/, "")
                        },
                        filterContent: function(e) {
                            var t = this;
                            if (this.isEpub || this.isAudio || this.isVideo) return e;
                            if (!e) return e;
                            try {
                                this.filterRules.forEach((function(n) {
                                    if ("undefined" === typeof n.isEnabled || !1 !== n.isEnabled) {
                                        var a = n.scope.split(";");
                                        "*" !== a[0] && a[0] !== t.$store.getters.readingBook.name || (1 == a.length || a.length > 1 && a[1] === t.$store.getters.readingBook.bookUrl) && (e = n.isRegex ? e.replace(new RegExp(n.pattern, "ig"), n.replacement) : e.replaceAll(n.pattern, n.replacement))
                                    }
                                }))
                            } catch (re) {}
                            return e.replace(/\\n+/g, "\n"), e = this.formatChinese(e), e
                        },
                        loadShowChapter: function(e, t) {
                            var n = this;
                            if (!t && this.chapterContentCache && this.chapterContentCache.chapters[e] && !this.chapterContentCache.chapters[e].error) return e >= this.chapterIndex - this.showPrevChapterSize && e <= this.chapterIndex + this.showNextChapterSize && this.computeShowChapterList(), Promise.resolve();
                            var a = this.$store.getters.readingBook.bookUrl;
                            if (!this.$store.getters.readingBook.catalog) return new Promise((function(a) {
                                n.$once("loadCatalog", (function() {
                                    n.loadShowChapter(e, t).then(a)
                                }))
                            }));
                            if (!this.$store.getters.readingBook.catalog[e]) return Promise.reject("章节不存在");
                            var u = this.$store.getters.readingBook.catalog[e].title,
                                o = this.$store.getters.readingBook.catalog[e].index;
                            return this.getBookContent(o, {}, t, !0).then((function(t) {
                                t.data.isSuccess ? n.addChapterContentToCache({
                                    bookUrl: a,
                                    index: e,
                                    title: u,
                                    content: t.data.data,
                                    error: !1
                                }) : n.addChapterContentToCache({
                                    bookUrl: a,
                                    index: e,
                                    title: u,
                                    content: "获取章节内容失败！\n" + t.data.errorMsg,
                                    error: !0
                                })
                            }), (function(t) {
                                throw n.addChapterContentToCache({
                                    bookUrl: a,
                                    index: e,
                                    title: u,
                                    content: "获取章节内容失败！\n" + (t && t.toString()),
                                    error: !0
                                }), t
                            }))
                        },
                        addChapterContentToCache: function(e) {
                            this.chapterContentCache && this.chapterContentCache.bookUrl === this.readingBook.bookUrl || (this.chapterContentCache = {
                                bookUrl: this.readingBook.bookUrl,
                                chapters: {}
                            }), "undefined" !== typeof this.chapterContentCache.chapters[e.index] && e.error && !this.chapterContentCache.chapters[e.index].error || (e.isVolume = !!(this.readingBook.catalog[e.index] || {}).isVolume, this.chapterContentCache.chapters[e.index] = e)
                        },
                        computeShowChapterList: function(e) {
                            var t = this;
                            if (!this.chapterContentCache) return new Promise((function(n) {
                                setTimeout((function() {
                                    t.computeShowChapterList(e).then(n)
                                }), 10)
                            }));
                            if (!this.isScrollRead) return Promise.resolve();
                            var n = [],
                                a = this.scrollStartChapterIndex || this.chapterIndex;
                            "上下滚动2" === this.config.readMethod && (a = this.chapterIndex - this.showPrevChapterSize);
                            for (var u = [], o = a; o <= this.chapterIndex + this.showNextChapterSize; o++) this.chapterContentCache.chapters[o] ? n.push(Object(i["a"])(Object(i["a"])({}, this.chapterContentCache.chapters[o]), {}, {
                                content: this.filterContent(this.chapterContentCache.chapters[o].content)
                            })) : u.push(this.loadShowChapter(o));
                            if (u.length) return Promise.all(u).then((function() {
                                t.computeShowChapterList(e)
                            }));
                            this.saveReadingPosition(), this.startSavePosition = !1, this.showChapterList = n, this.$nextTick((function() {
                                t.computePages((function() {
                                    e ? (t.toTop(0), t.startSavePosition = !0) : "上下滚动2" === t.config.readMethod ? t.autoShowPosition(!0) : t.startSavePosition = !0
                                }))
                            }))
                        },
                        saveBookProgress: function() {
                            return p["a"].post(this.api + "/saveBookProgress", {
                                url: this.$store.getters.readingBook.bookUrl,
                                index: this.chapterIndex
                            }, {
                                silent: !0
                            })["catch"]((function() {}))
                        },
                        toTop: function(e) {
                            this.$store.state.miniInterface ? this.scrollContent(-(document.documentElement.scrollTop || document.body.scrollTop), e) : Object(c["a"])(this.$refs.top, {
                                duration: e
                            })
                        },
                        toBottom: function(e) {
                            Object(c["a"])(this.$refs.bottom, {
                                duration: e
                            })
                        },
                        toNextChapter: function(e) {
                            if (this.$store.getters.readingBook && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.catalog) {
                                var t = this.$store.getters.readingBook.index;
                                if (t++, "undefined" !== typeof this.$store.getters.readingBook.catalog[t]) {
                                    if (this.isScrollRead) return this.scrollStartChapterIndex = t, void this.computeShowChapterList(!0);
                                    this.getContent(t)
                                } else e && e(), this.$message.error("本章是最后一章")
                            } else e && e()
                        },
                        toLastChapter: function(e) {
                            if (this.$store.getters.readingBook && this.$store.getters.readingBook.bookUrl && this.$store.getters.readingBook.catalog) {
                                var t = this.$store.getters.readingBook.index;
                                if (t--, "undefined" !== typeof this.$store.getters.readingBook.catalog[t]) {
                                    if (this.isScrollRead) return this.scrollStartChapterIndex = t, void this.computeShowChapterList(!0);
                                    this.getContent(t)
                                } else this.$message.error("本章是第一章"), e && e()
                            } else e && e()
                        },
                        toShelf: function() {
                            this.$router.push("/")
                        },
                        onContentChange: function() {
                            this.computePages()
                        },
                        computePages: function(e) {
                            var t = this;
                            if (this.$refs.bookContentRef && this.$refs.bookContentRef.$el) {
                                if (this.isSlideRead) this.isEpub ? this.totalPages = Math.ceil(this.$refs.bookContentRef.iframeSize.scrollWidth / (this.windowSize.width - this.slideDistance)) : this.totalPages = Math.ceil(this.$refs.bookContentRef.$el.scrollWidth / (this.windowSize.width - this.slideDistance));
                                else {
                                    var n = this.windowSize.height - this.scrollOffset;
                                    if (this.totalPages = Math.ceil(this.$refs.bookContentRef.$el.scrollHeight / n), !this.isScrollRead) {
                                        var a = n * this.totalPages - this.$refs.bookContentRef.$el.scrollHeight - 68;
                                        this.contentInnerStyle = {
                                            "padding-bottom": a + "px"
                                        }
                                    }
                                }
                                this.$emit("afterComputePages"), e && e()
                            } else setTimeout((function() {
                                t.computePages(e)
                            }), 30)
                        },
                        nextPage: function(e) {
                            var t = this;
                            if (this.show && !this.transforming)
                                if (this.isSlideRead) this.currentPage < this.totalPages ? ("undefined" === typeof e && (this.transformX = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1)), this.currentPage += 1, this.transforming = !0, this.transform("undefined" === typeof e ? -(this.windowSize.width - this.slideDistance) : e, this.animateMSTime)) : this.toNextChapter((function() {
                                    "undefined" !== typeof e && t.showPage(t.currentPage, 0)
                                }));
                                else if (this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
                            else {
                                this.currentPage += 1;
                                var n = this.windowSize.height - this.scrollOffset;
                                this.transforming = !0, this.scrollContent(n, this.animateMSTime)
                            }
                        },
                        prevPage: function(e) {
                            var t = this;
                            if (this.show && !this.transforming)
                                if (this.isSlideRead) this.currentPage > 1 ? ("undefined" === typeof e && (this.transformX = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1)), this.currentPage -= 1, this.transforming = !0, this.transform("undefined" === typeof e ? this.windowSize.width - this.slideDistance : e, this.animateMSTime)) : (this.onNextContentPagesInit((function() {
                                    t.showPage(t.totalPages, 0)
                                })), this.toLastChapter((function() {
                                    "undefined" !== typeof e && t.showPage(t.currentPage, 0)
                                })));
                                else if ((document.documentElement.scrollTop || document.body.scrollTop) > 0) {
                                this.currentPage -= 1;
                                var n = -this.windowSize.height + this.scrollOffset;
                                this.transforming = !0, this.scrollContent(n, this.animateMSTime)
                            } else this.currentPage = 1, this.onNextContentPagesInit((function() {
                                t.showPage(t.totalPages, 0)
                            })), this.toLastChapter()
                        },
                        showPage: function(e, t) {
                            if (this.show)
                                if (this.currentPage = Math.min(e, this.totalPages), this.isSlideRead) {
                                    var n = -(this.windowSize.width - this.slideDistance) * (this.currentPage - 1) - this.transformX;
                                    this.transform(n, "undefined" === typeof t ? this.animateMSTime : t)
                                } else {
                                    var a = (this.windowSize.height - 10) * (this.currentPage - 1) - (document.documentElement.scrollTop || document.body.scrollTop);
                                    this.scrollContent(a, "undefined" === typeof t ? this.animateMSTime : t)
                                }
                        },
                        transform: function(e, t, n) {
                            var a = this;
                            n && (this.transformX = 0);
                            var u = function() {
                                a.isEpub ? a.$refs.bookContentRef && a.$refs.bookContentRef.transformX(a.transformX + e) : a.contentStyle = {
                                    transform: "translateX(".concat(a.transformX + e, "px)")
                                }, a.transformX += e, a.transforming = !1, setTimeout(a.saveReadingPosition, t)
                            };
                            if (t) {
                                var o = Bt.Utils.makeEaseInOut(Bt.Timings.power.bind(null, 3));
                                new Bt({
                                    duration: t || 500,
                                    timing: o,
                                    draw: function(t) {
                                        a.isEpub ? a.$refs.bookContentRef && a.$refs.bookContentRef.transformX(a.transformX + e * t) : a.contentStyle = {
                                            transform: "translateX(".concat(a.transformX + e * t, "px)")
                                        }
                                    },
                                    onEnd: u
                                })
                            } else u()
                        },
                        scrollContent: function(e, t, n) {
                            var a = this,
                                u = n ? 0 : document.documentElement.scrollTop || document.body.scrollTop,
                                o = function() {
                                    document.documentElement.scrollTop = u + e, document.body.scrollTop = u + e, a.transforming = !1, setTimeout(a.saveReadingPosition, t)
                                };
                            if (t) {
                                var r = Bt.Utils.makeEaseInOut(Bt.Timings.power.bind(null, 3));
                                new Bt({
                                    duration: t || 500,
                                    timing: r,
                                    draw: function(t) {
                                        document.documentElement.scrollTop = u + e * t, document.body.scrollTop = u + e * t
                                    },
                                    onEnd: o
                                })
                            } else o()
                        },
                        handlerClick: function(e) {
                            this.isEpub || (this.lastTouch || this.ignoreNextClick || this.eventHandler(e), this.ignoreNextClick = !1)
                        },
                        handleTouchStart: function(e) {
                            this.lastSelection = this.checkSelection(), this.lastSelection || this.isAudio || this.isVideo || (this.lastTouch = !1, this.lastMoveX = !1, e.touches && e.touches[0] && (this.lastTouch = e.touches[0]))
                        },
                        handleTouchMove: function(e) {
                            if (!this.checkSelection() && e.touches && e.touches[0] && this.lastTouch && (this.lastMoveY = e.touches[0].clientY - this.lastTouch.clientY, this.isSlideRead)) {
                                e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation();
                                var t = e.touches[0].clientX - this.lastTouch.clientX;
                                this.isEpub ? this.$refs.bookContentRef && this.$refs.bookContentRef.transformX(this.transformX + t) : this.contentStyle = {
                                    transform: "translateX(".concat(this.transformX + t, "px)")
                                }, this.lastMoveX = t
                            }
                        },
                        handleTouchEnd: function() {
                            var e = this;
                            this.checkSelection(!0) || (this.lastMoveX ? (this.transformX += this.lastMoveX, this.lastMoveX > 0 ? this.prevPage(this.windowSize.width - this.slideDistance - this.lastMoveX) : this.nextPage(-(this.windowSize.width - this.slideDistance) - this.lastMoveX)) : Math.abs(this.lastMoveY) <= 3 && this.lastTouch && (this.isEpub || this.eventHandler(this.lastTouch)), setTimeout((function() {
                                e.lastTouch = !1, e.lastMoveX = !1, e.lastMoveY = !1
                            }), 300))
                        },
                        epubTouch: function(e) {
                            switch (e.event) {
                                case "touchstart":
                                    this.handleTouchStart(e.data);
                                    break;
                                case "touchmove":
                                    this.handleTouchMove(e.data);
                                    break;
                                default:
                                    this.handleTouchEnd()
                            }
                        },
                        epubClickHash: function(e) {
                            this.isSlideRead ? "undefined" !== typeof e.left && this.showPage(this.currentPage + Math.round(e.left / (this.windowSize.width - this.slideDistance)) + (e.left > 0 ? 1 : 0), 0) : "undefined" !== typeof e.top && this.scrollContent(e.top - (this.$store.state.miniInterface ? this.getFirstParagraphPos().bottom : 0) - (0 | window.webAppDistance) - (0 | this.$store.state.safeArea.top), 0, !0)
                        },
                        epubLocationChangeHandler: function(e) {
                            function t(e) {
                                var t = document.createElement("a");
                                return t.href = e, decodeURIComponent(t.pathname)
                            }
                            e = t(e);
                            var n = this.catalog[this.chapterIndex];
                            if (n) {
                                for (var a = this.content.replace(n.url, ""), u = e.replace(a, ""), o = -1, r = 0; r < this.catalog.length; r++)
                                    if (this.catalog[r].url === u) {
                                        o = r;
                                        break
                                    } if (o >= 0) {
                                    var s = Object(i["a"])({}, this.$store.getters.readingBook);
                                    s.index = o, this.$store.commit("setReadingBook", s), this.title = this.$store.getters.readingBook.catalog[o].title
                                }
                            }
                        },
                        eventHandler: function(e) {
                            if (this.checkSelection(!0)) this.ignoreNextClick = !0;
                            else if (this.popBookSourceVisible || this.popBookShelfVisible || this.popCataVisible || this.readSettingsVisible) this.isEpub && (this.popBookSourceVisible = !1, this.popBookShelfVisible = !1, this.popCataVisible = !1, this.readSettingsVisible = !1);
                            else if (this.isAudio) this.showReadBar || (this.showToolBar = !this.showToolBar);
                            else if (this.autoReading) this.showToolBar = !this.showToolBar;
                            else if (this.isEditContent) this.showToolBar = !this.showToolBar;
                            else {
                                var t = this.windowSize.width / 2,
                                    n = this.windowSize.height / 2,
                                    a = .2 * this.windowSize.width,
                                    u = .2 * this.windowSize.height;
                                if (this.isEpub && "iframe" === this.config.epubMode && (e.clientY = e.clientY + 45 - (document.documentElement.scrollTop || document.body.scrollTop)), Math.abs(e.clientY - n) <= u && Math.abs(e.clientX - t) <= a) this.showReadBar || (this.showToolBar = !this.showToolBar);
                                else {
                                    if (this.isVideo) return;
                                    if ("下一页" === this.$store.getters.config.clickMethod) return this.showToolBar = !1, void this.nextPage();
                                    if ("不翻页" === this.$store.getters.config.clickMethod) return void(this.showToolBar = !this.showToolBar);
                                    "固定模式" === this.$store.getters.config.clickMethod ? e.clientY < n - u || e.clientY > n - u && e.clientX < t - a ? (this.showToolBar = !1, this.prevPage()) : (e.clientY > n + u || e.clientY < n + u && e.clientX > t + a) && (this.showToolBar = !1, this.nextPage()) : this.isSlideRead ? e.clientX > t ? (this.showToolBar = !1, this.nextPage()) : e.clientX < t && (this.showToolBar = !1, this.prevPage()) : e.clientY > n ? (this.showToolBar = !1, this.nextPage()) : e.clientY < n && (this.showToolBar = !1, this.prevPage())
                                }
                            }
                        },
                        keydownHandler: function(e, t) {
                            if (!(this.popBookSourceVisible || this.popBookShelfVisible || this.popCataVisible || this.readSettingsVisible || this.showTextFilterPrompting) && (t || document.activeElement === document.body) && !this.isAudio && !this.isVideo) {
                                var n = {
                                        37: "ArrowLeft",
                                        38: "ArrowUp",
                                        39: "ArrowRight",
                                        40: "ArrowDown",
                                        27: "Escape",
                                        32: "Space",
                                        33: "PageUp",
                                        34: "PageDown",
                                        35: "End",
                                        36: "Home"
                                    },
                                    a = "Space" === e.code ? "Space" : e.key || n[e.keyCode];
                                if ("自定义" !== this.config.quickKeyMode) switch (a) {
                                    case "ArrowLeft":
                                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.isSlideRead ? this.prevPage() : this.toLastChapter();
                                        break;
                                    case "ArrowRight":
                                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.isSlideRead ? this.nextPage() : this.toNextChapter();
                                        break;
                                    case "ArrowUp":
                                    case "PageUp":
                                        (this.isSlideRead || "PageUp" === a) && (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.prevPage());
                                        break;
                                    case "ArrowDown":
                                    case "PageDown":
                                        this.isSlideRead || "PageDown" === a ? (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.nextPage()) : !this.isSlideRead && this.nearBottom(10) && (this.currentPage = 1, this.toNextChapter());
                                        break;
                                    case "Escape":
                                        this.toShelf();
                                        break;
                                    case "Home":
                                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.showPage(0, 0);
                                        break;
                                    case "End":
                                        e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.showPage(this.totalPages, 0);
                                        break;
                                    case "Space":
                                        if (!this.isSlideRead)
                                            if (e.preventDefault && e.preventDefault(), e.stopPropagation && e.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
                                            else {
                                                var u = this.windowSize.height / 2;
                                                this.scrollContent(u, this.animateMSTime)
                                            } break
                                } else this.quickKeyHandle(a, e)
                            }
                        },
                        quickKeyHandle: function(e, t) {
                            if (this.config.quickKey[e]) {
                                var n = this.config.quickKey[e];
                                switch (n) {
                                    case "上一页":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.prevPage();
                                        break;
                                    case "下一页":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nextPage();
                                        break;
                                    case "上半页":
                                        if (!this.isSlideRead)
                                            if (t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.toLastChapter();
                                            else {
                                                var a = -this.windowSize.height / 2;
                                                this.scrollContent(a, this.animateMSTime)
                                            } break;
                                    case "下半页":
                                        if (!this.isSlideRead)
                                            if (t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.nearBottom(10)) this.currentPage = 1, this.toNextChapter();
                                            else {
                                                var u = this.windowSize.height / 2;
                                                this.scrollContent(u, this.animateMSTime)
                                            } break;
                                    case "上一章":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.toLastChapter();
                                        break;
                                    case "下一章":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.currentPage = 1, this.toNextChapter();
                                        break;
                                    case "返回":
                                        this.toShelf();
                                        break;
                                    case "首页":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.showPage(0, 0);
                                        break;
                                    case "尾页":
                                        t.preventDefault && t.preventDefault(), t.stopPropagation && t.stopPropagation(), this.showToolBar = !1, this.showPage(this.totalPages, 0);
                                        break;
                                    default:
                                        break
                                }
                            }
                        },
                        formatProgressTip: function(e) {
                            return "第 ".concat(e || this.progressValue, "/").concat(this.totalPages, " 页")
                        },
                        formatTime: function() {
                            var e = new Date,
                                t = function(e) {
                                    return e >= 10 ? "" + e : "0" + e
                                };
                            this.timeStr = t(e.getHours()) + ":" + t(e.getMinutes())
                        },
                        checkSelection: function(e) {
                            var t = this,
                                n = "";
                            return this.isEpubIframe ? this.$refs.bookContentRef && this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.contentWindow && this.$refs.bookContentRef.$el.contentWindow.getSelection ? n = this.$refs.bookContentRef.$el.contentWindow.getSelection().toString() : this.$refs.bookContentRef && this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.contentWindow && this.$refs.bookContentRef.$el.contentWindow.document && this.$refs.bookContentRef.$el.contentWindow.document.selection && "Control" != this.$refs.bookContentRef.$el.contentWindow.document.selection.type && (n = this.$refs.bookContentRef.$el.contentWindow.document.selection.createRange().text) : window.getSelection ? n = window.getSelection().toString() : document.selection && "Control" != document.selection.type && (n = document.selection.createRange().text), n && e && setTimeout((function() {
                                "过滤弹窗" !== t.$store.getters.config.selectionAction && "操作弹窗" !== t.$store.getters.config.selectionAction || t.showTextOperate(n)
                            }), 200), n
                        },
                        showTextOperate: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t.isEditContent) {
                                                n.next = 2;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 2:
                                            return n.next = 4, t.$confirm("请选择操作?", "提示", {
                                                confirmButtonText: "添加过滤规则",
                                                cancelButtonText: "添加书签",
                                                type: "warning",
                                                closeOnClickModal: !1,
                                                closeOnPressEscape: !1,
                                                distinguishCancelAndClose: !0
                                            })["catch"]((function(e) {
                                                return "close" === e && "close"
                                            }));
                                        case 4:
                                            if (a = n.sent, "close" !== a) {
                                                n.next = 7;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 7:
                                            if (!a) {
                                                n.next = 11;
                                                break
                                            }
                                            return n.abrupt("return", t.showTextFilterPrompt(e));
                                        case 11:
                                            return n.abrupt("return", t.showAddBookmark(e));
                                        case 12:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        showTextFilterPrompt: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t.showTextFilterPrompting) {
                                                n.next = 2;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 2:
                                            if (e.replace(/^\s+/, "").replace(/\s+$/, "")) {
                                                n.next = 4;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 4:
                                            a = Object.assign({}, x["d"], {
                                                name: "文本替换",
                                                pattern: e,
                                                replacement: "",
                                                isRegex: !1,
                                                isEnabled: !0,
                                                scope: t.$store.getters.readingBook.name + ";" + t.$store.getters.readingBook.bookUrl
                                            }), t.showTextFilterPrompting = !0, S["a"].$emit("showReplaceRuleForm", a, !0, (function() {
                                                t.showTextFilterPrompting = !1
                                            }));
                                        case 7:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        showAddBookmark: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a, u, r, i, s, l, c, p, d;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t.showAddBookmarking) {
                                                n.next = 2;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 2:
                                            if (a = e.replace(/^\s+/, "").replace(/\s+$/, ""), u = t.getContentMatchParagraph(a, 1, .7), u) {
                                                n.next = 7;
                                                break
                                            }
                                            return t.$message.error("选择1-2段整段文字才能定位段落"), n.abrupt("return");
                                        case 7:
                                            if (r = 5, i = 150, s = [u], l = u.innerText + "\n", !(s.length < r && l.length < i)) {
                                                n.next = 27;
                                                break
                                            }
                                            c = -1, p = t.getContentParagraphList(), d = 0;
                                        case 15:
                                            if (!(d < p.length)) {
                                                n.next = 27;
                                                break
                                            }
                                            if (!(c > 0 && d > c)) {
                                                n.next = 23;
                                                break
                                            }
                                            if (s.push(p[d]), l += p[d].innerText + "\n", !(s.length >= r || l.length >= i)) {
                                                n.next = 21;
                                                break
                                            }
                                            return n.abrupt("break", 27);
                                        case 21:
                                            n.next = 24;
                                            break;
                                        case 23:
                                            s[s.length - 1] === p[d] && (c = d);
                                        case 24:
                                            d++, n.next = 15;
                                            break;
                                        case 27:
                                            t.showAddBookmarkForm(l);
                                        case 28:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        addBookmark: function() {
                            for (var e = this.getCurrentParagraph(), t = this.getContentParagraphList(), n = "", a = -1, u = 0; u < t.length; u++)
                                if (e === t[u] && (a = u), a >= 0 && u >= a && (n += t[u].innerText + "\n", u >= a + 5)) break;
                            this.showAddBookmarkForm(n)
                        },
                        showAddBookmarkForm: function(e) {
                            var t = this;
                            e = e.replace(/\\n*$/, "");
                            var n = Object.assign({}, x["c"], {
                                bookName: this.$store.getters.readingBook.name,
                                bookAuthor: this.$store.getters.readingBook.author,
                                chapterIndex: this.chapterIndex,
                                chapterPos: this.currentPage,
                                chapterName: this.title,
                                bookText: e,
                                content: ""
                            });
                            this.showAddBookmarking = !0, S["a"].$emit("showBookmarkForm", n, !0, (function() {
                                t.showAddBookmarking = !1
                            }))
                        },
                        toogleNight: function() {
                            this.isNight ? this.$store.commit("setNightTheme", !1) : this.$store.commit("setNightTheme", !0)
                        },
                        fetchVoiceList: function() {
                            this.localVoiceList = window.speechSynthesis.getVoices().sort((function(e, t) {
                                return e.lang.startsWith("zh-") && t.lang.startsWith("zh-") ? e.lang > t.lang ? 1 : e.lang < t.lang ? -1 : 0 : e.lang.startsWith("zh-") ? -1 : t.lang.startsWith("zh-") || e.lang > t.lang ? 1 : e.lang < t.lang ? -1 : 0
                            }))
                        },
                        changeSpeechRate: function(e) {
                            this.speechRate = e
                        },
                        changeSpeechPitch: function(e) {
                            this.speechPitch = e
                        },
                        changeSpeechMinutes: function(e) {
                            this.speechMinutes = e, this.speechEndTime = e ? (new Date).getTime() + 60 * e * 1e3 : 0
                        },
                        showHttpTTSDialog: function() {
                            S["a"].$emit("showHttpTTSDialog")
                        },
                        startSpeech: function() {
                            if (!this.error && this.voiceName) {
                                if (window.speechSynthesis.speaking) try {
                                    window.speechSynthesis.cancel()
                                } catch (e) {}
                                if (this.speechEndTime > 0 && (new Date).getTime() > this.speechEndTime) return this.$message.info("定时关闭朗读"), void(this.speechEndTime = 0);
                                "local" !== this.ttsType ? this.speechCurrentByTTS() : this.speechCurrent()
                            }
                        },
                        speechCurrentByTTS: function() {
                            var e = this;
                            return Object(r["a"])(Object(o["a"])().mark((function t() {
                                var n, a, u, r, i, s, l;
                                return Object(o["a"])().wrap((function(t) {
                                    while (1) switch (t.prev = t.next) {
                                        case 0:
                                            if (n = e.getCurrentParagraph(), n) {
                                                t.next = 4;
                                                break
                                            }
                                            return e.speechNext(), t.abrupt("return");
                                        case 4:
                                            if (a = n.innerText, !a.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/)) {
                                                t.next = 8;
                                                break
                                            }
                                            return setTimeout((function() {
                                                e.speechNext()
                                            }), 300), t.abrupt("return");
                                        case 8:
                                            if (a = a.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), a) {
                                                t.next = 12;
                                                break
                                            }
                                            return e.speechNext(), t.abrupt("return");
                                        case 12:
                                            if (u = e.getTTSConfig(a), r = u.config, i = u.cacheKey, e.currentTTSCacheKey = i, s = St(e.api + "/book/tts", r), !e.cacheTTSAudio) {
                                                t.next = 20;
                                                break
                                            }
                                            return t.next = 18, e.getCachedTTSAudioURL(i);
                                        case 18:
                                            l = t.sent, s = l || s;
                                        case 20:
                                            e.$refs.ttsAudio.src = s, e.$refs.ttsAudio.load(), e.$refs.ttsAudio.play(), e.showParagraph(n, !0), n.className = "reading";
                                        case 25:
                                        case "end":
                                            return t.stop()
                                    }
                                }), t)
                            })))()
                        },
                        getTTSConfig: function(e) {
                            var t = this.speechPitch,
                                n = this.speechRate;
                            "textToSpeechCn" === this.ttsType ? (t = parseInt(50 * (t - 1)), n = parseInt(200 * (n - 1))) : "edge" === this.ttsType && (t = parseInt(50 * (t - 1)));
                            var a = {
                                text: e,
                                type: this.ttsType,
                                voice: this.voiceName,
                                pitch: "".concat(t),
                                rate: "".concat(n),
                                accessToken: this.$store.state.token
                            };
                            return {
                                config: a,
                                cacheKey: "localCache@ttsData@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author + "@" + "".concat(a.text).concat(a.type).concat(a.voice).concat(a.pitch).concat(a.rate).MD5(32)
                            }
                        },
                        onTTSAudioPlay: function() {
                            this.speechSpeaking = !0, this.skipAutoNext = !1, this.cacheTTSAudio && (this.cacheNextParagraphTTSAudio(1), this.cacheNextParagraphTTSAudio(2), this.cacheNextParagraphTTSAudio(3))
                        },
                        onTTSAudioPause: function() {
                            this.speechSpeaking = !1, this.skipAutoNext = !1
                        },
                        onTTSAudioEnded: function() {
                            this.currentTTSCacheKey && window.$cacheStorage.removeItem(this.currentTTSCacheKey)["catch"]((function() {})), this.skipAutoNext ? (this.skipAutoNext = !1, this.speechSpeaking = !1) : this.speechNext()
                        },
                        onTTSAudioError: function(e) {
                            this.speechSpeaking && ((e.error || e.name) && this.$message.error("朗读错误:  ".concat(e.type || "", "  ").concat(e.error || e.name || e.toString())), this.speechSpeaking = !1)
                        },
                        getCachedTTSAudioURL: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            return n.next = 2, window.$cacheStorage.getItem(e)["catch"]((function() {
                                                return !1
                                            }));
                                        case 2:
                                            if (a = n.sent, !a) {
                                                n.next = 7;
                                                break
                                            }
                                            return n.next = 6, t.base64ToBlob(a, "audio/mpeg");
                                        case 6:
                                            return n.abrupt("return", n.sent);
                                        case 7:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        cacheNextParagraphTTSAudio: function(e) {
                            var t = this;
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a, u, r, s, l, c;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (a = t.getNextParagraph(e), a) {
                                                n.next = 3;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 3:
                                            if (u = a.innerText, !u.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/)) {
                                                n.next = 6;
                                                break
                                            }
                                            return n.abrupt("return", t.cacheNextParagraphTTSAudio(e + 1));
                                        case 6:
                                            if (u = u.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), u) {
                                                n.next = 9;
                                                break
                                            }
                                            return n.abrupt("return", t.cacheNextParagraphTTSAudio(e + 1));
                                        case 9:
                                            return r = t.getTTSConfig(u), s = r.config, l = r.cacheKey, n.next = 12, window.$cacheStorage.getItem(l)["catch"]((function() {
                                                return !1
                                            }));
                                        case 12:
                                            if (c = n.sent, !c) {
                                                n.next = 15;
                                                break
                                            }
                                            return n.abrupt("return");
                                        case 15:
                                            p["a"].post("/book/tts", Object(i["a"])(Object(i["a"])({}, s), {}, {
                                                base64: "1"
                                            }), {
                                                silent: !0
                                            }).then((function(e) {
                                                e.data.isSuccess && window.$cacheStorage.setItem(l, e.data.data)["catch"]((function() {}))
                                            }), (function() {}));
                                        case 16:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        base64ToBlob: function(e, t) {
                            return Object(r["a"])(Object(o["a"])().mark((function n() {
                                var a, u, r, i;
                                return Object(o["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            for (a = window.atob(e), u = new Uint8Array(a.length), r = 0; r < a.length; r++) u[r] = a.charCodeAt(r);
                                            if (i = new Blob([u], {
                                                    type: t
                                                }), !(window.navigator.userAgent.indexOf("iPhone") >= 0 || window.navigator.userAgent.indexOf("iPad") >= 0)) {
                                                n.next = 8;
                                                break
                                            }
                                            return n.abrupt("return", new Promise((function(e, t) {
                                                var n = new FileReader;
                                                n.onload = function() {
                                                    e(n.result)
                                                }, n.onerror = t, n.readAsDataURL(i)
                                            })));
                                        case 8:
                                            return n.abrupt("return", window.URL.createObjectURL(i));
                                        case 9:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })))()
                        },
                        speechCurrent: function() {
                            var e = this,
                                t = this.voiceList.find((function(t) {
                                    return t.name === e.voiceName
                                }));
                            if (t) {
                                var n = this.getCurrentParagraph();
                                if (n) {
                                    var a = n.innerText;
                                    a.match(/^([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/) ? setTimeout((function() {
                                        e.speechNext()
                                    }), 300) : (a = a.replace(/([\t-\r \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000\uFEFF]|(?:[\0-\x1F\x7F-\x9F\xAD\u0378\u0379\u0380-\u0383\u038B\u038D\u03A2\u0530\u0557\u0558\u058B\u058C\u0590\u05C8-\u05CF\u05EB-\u05EE\u05F5-\u0605\u061C\u06DD\u070E\u070F\u074B\u074C\u07B2-\u07BF\u07FB\u07FC\u082E\u082F\u083F\u085C\u085D\u085F\u086B-\u086F\u088F-\u0896\u08E2\u0984\u098D\u098E\u0991\u0992\u09A9\u09B1\u09B3-\u09B5\u09BA\u09BB\u09C5\u09C6\u09C9\u09CA\u09CF-\u09D6\u09D8-\u09DB\u09DE\u09E4\u09E5\u09FF\u0A00\u0A04\u0A0B-\u0A0E\u0A11\u0A12\u0A29\u0A31\u0A34\u0A37\u0A3A\u0A3B\u0A3D\u0A43-\u0A46\u0A49\u0A4A\u0A4E-\u0A50\u0A52-\u0A58\u0A5D\u0A5F-\u0A65\u0A77-\u0A80\u0A84\u0A8E\u0A92\u0AA9\u0AB1\u0AB4\u0ABA\u0ABB\u0AC6\u0ACA\u0ACE\u0ACF\u0AD1-\u0ADF\u0AE4\u0AE5\u0AF2-\u0AF8\u0B00\u0B04\u0B0D\u0B0E\u0B11\u0B12\u0B29\u0B31\u0B34\u0B3A\u0B3B\u0B45\u0B46\u0B49\u0B4A\u0B4E-\u0B54\u0B58-\u0B5B\u0B5E\u0B64\u0B65\u0B78-\u0B81\u0B84\u0B8B-\u0B8D\u0B91\u0B96-\u0B98\u0B9B\u0B9D\u0BA0-\u0BA2\u0BA5-\u0BA7\u0BAB-\u0BAD\u0BBA-\u0BBD\u0BC3-\u0BC5\u0BC9\u0BCE\u0BCF\u0BD1-\u0BD6\u0BD8-\u0BE5\u0BFB-\u0BFF\u0C0D\u0C11\u0C29\u0C3A\u0C3B\u0C45\u0C49\u0C4E-\u0C54\u0C57\u0C5B\u0C5C\u0C5E\u0C5F\u0C64\u0C65\u0C70-\u0C76\u0C8D\u0C91\u0CA9\u0CB4\u0CBA\u0CBB\u0CC5\u0CC9\u0CCE-\u0CD4\u0CD7-\u0CDC\u0CDF\u0CE4\u0CE5\u0CF0\u0CF4-\u0CFF\u0D0D\u0D11\u0D45\u0D49\u0D50-\u0D53\u0D64\u0D65\u0D80\u0D84\u0D97-\u0D99\u0DB2\u0DBC\u0DBE\u0DBF\u0DC7-\u0DC9\u0DCB-\u0DCE\u0DD5\u0DD7\u0DE0-\u0DE5\u0DF0\u0DF1\u0DF5-\u0E00\u0E3B-\u0E3E\u0E5C-\u0E80\u0E83\u0E85\u0E8B\u0EA4\u0EA6\u0EBE\u0EBF\u0EC5\u0EC7\u0ECF\u0EDA\u0EDB\u0EE0-\u0EFF\u0F48\u0F6D-\u0F70\u0F98\u0FBD\u0FCD\u0FDB-\u0FFF\u10C6\u10C8-\u10CC\u10CE\u10CF\u1249\u124E\u124F\u1257\u1259\u125E\u125F\u1289\u128E\u128F\u12B1\u12B6\u12B7\u12BF\u12C1\u12C6\u12C7\u12D7\u1311\u1316\u1317\u135B\u135C\u137D-\u137F\u139A-\u139F\u13F6\u13F7\u13FE\u13FF\u169D-\u169F\u16F9-\u16FF\u1716-\u171E\u1737-\u173F\u1754-\u175F\u176D\u1771\u1774-\u177F\u17DE\u17DF\u17EA-\u17EF\u17FA-\u17FF\u180E\u181A-\u181F\u1879-\u187F\u18AB-\u18AF\u18F6-\u18FF\u191F\u192C-\u192F\u193C-\u193F\u1941-\u1943\u196E\u196F\u1975-\u197F\u19AC-\u19AF\u19CA-\u19CF\u19DB-\u19DD\u1A1C\u1A1D\u1A5F\u1A7D\u1A7E\u1A8A-\u1A8F\u1A9A-\u1A9F\u1AAE\u1AAF\u1ACF-\u1AFF\u1B4D\u1BF4-\u1BFB\u1C38-\u1C3A\u1C4A-\u1C4C\u1C8B-\u1C8F\u1CBB\u1CBC\u1CC8-\u1CCF\u1CFB-\u1CFF\u1F16\u1F17\u1F1E\u1F1F\u1F46\u1F47\u1F4E\u1F4F\u1F58\u1F5A\u1F5C\u1F5E\u1F7E\u1F7F\u1FB5\u1FC5\u1FD4\u1FD5\u1FDC\u1FF0\u1FF1\u1FF5\u1FFF\u200B-\u200F\u202A-\u202E\u2060-\u206F\u2072\u2073\u208F\u209D-\u209F\u20C1-\u20CF\u20F1-\u20FF\u218C-\u218F\u242A-\u243F\u244B-\u245F\u2B74\u2B75\u2B96\u2CF4-\u2CF8\u2D26\u2D28-\u2D2C\u2D2E\u2D2F\u2D68-\u2D6E\u2D71-\u2D7E\u2D97-\u2D9F\u2DA7\u2DAF\u2DB7\u2DBF\u2DC7\u2DCF\u2DD7\u2DDF\u2E5E-\u2E7F\u2E9A\u2EF4-\u2EFF\u2FD6-\u2FEF\u3040\u3097\u3098\u3100-\u3104\u3130\u318F\u31E6-\u31EE\u321F\uA48D-\uA48F\uA4C7-\uA4CF\uA62C-\uA63F\uA6F8-\uA6FF\uA7CE\uA7CF\uA7D2\uA7D4\uA7DD-\uA7F1\uA82D-\uA82F\uA83A-\uA83F\uA878-\uA87F\uA8C6-\uA8CD\uA8DA-\uA8DF\uA954-\uA95E\uA97D-\uA97F\uA9CE\uA9DA-\uA9DD\uA9FF\uAA37-\uAA3F\uAA4E\uAA4F\uAA5A\uAA5B\uAAC3-\uAADA\uAAF7-\uAB00\uAB07\uAB08\uAB0F\uAB10\uAB17-\uAB1F\uAB27\uAB2F\uAB6C-\uAB6F\uABEE\uABEF\uABFA-\uABFF\uD7A4-\uD7AF\uD7C7-\uD7CA\uD7FC-\uD7FF\uE000-\uF8FF\uFA6E\uFA6F\uFADA-\uFAFF\uFB07-\uFB12\uFB18-\uFB1C\uFB37\uFB3D\uFB3F\uFB42\uFB45\uFBC3-\uFBD2\uFD90\uFD91\uFDC8-\uFDCE\uFDD0-\uFDEF\uFE1A-\uFE1F\uFE53\uFE67\uFE6C-\uFE6F\uFE75\uFEFD-\uFF00\uFFBF-\uFFC1\uFFC8\uFFC9\uFFD0\uFFD1\uFFD8\uFFD9\uFFDD-\uFFDF\uFFE7\uFFEF-\uFFFB\uFFFE\uFFFF]|\uD800[\uDC0C\uDC27\uDC3B\uDC3E\uDC4E\uDC4F\uDC5E-\uDC7F\uDCFB-\uDCFF\uDD03-\uDD06\uDD34-\uDD36\uDD8F\uDD9D-\uDD9F\uDDA1-\uDDCF\uDDFE-\uDE7F\uDE9D-\uDE9F\uDED1-\uDEDF\uDEFC-\uDEFF\uDF24-\uDF2C\uDF4B-\uDF4F\uDF7B-\uDF7F\uDF9E\uDFC4-\uDFC7\uDFD6-\uDFFF]|\uD801[\uDC9E\uDC9F\uDCAA-\uDCAF\uDCD4-\uDCD7\uDCFC-\uDCFF\uDD28-\uDD2F\uDD64-\uDD6E\uDD7B\uDD8B\uDD93\uDD96\uDDA2\uDDB2\uDDBA\uDDBD-\uDDBF\uDDF4-\uDDFF\uDF37-\uDF3F\uDF56-\uDF5F\uDF68-\uDF7F\uDF86\uDFB1\uDFBB-\uDFFF]|\uD802[\uDC06\uDC07\uDC09\uDC36\uDC39-\uDC3B\uDC3D\uDC3E\uDC56\uDC9F-\uDCA6\uDCB0-\uDCDF\uDCF3\uDCF6-\uDCFA\uDD1C-\uDD1E\uDD3A-\uDD3E\uDD40-\uDD7F\uDDB8-\uDDBB\uDDD0\uDDD1\uDE04\uDE07-\uDE0B\uDE14\uDE18\uDE36\uDE37\uDE3B-\uDE3E\uDE49-\uDE4F\uDE59-\uDE5F\uDEA0-\uDEBF\uDEE7-\uDEEA\uDEF7-\uDEFF\uDF36-\uDF38\uDF56\uDF57\uDF73-\uDF77\uDF92-\uDF98\uDF9D-\uDFA8\uDFB0-\uDFFF]|\uD803[\uDC49-\uDC7F\uDCB3-\uDCBF\uDCF3-\uDCF9\uDD28-\uDD2F\uDD3A-\uDD3F\uDD66-\uDD68\uDD86-\uDD8D\uDD90-\uDE5F\uDE7F\uDEAA\uDEAE\uDEAF\uDEB2-\uDEC1\uDEC5-\uDEFB\uDF28-\uDF2F\uDF5A-\uDF6F\uDF8A-\uDFAF\uDFCC-\uDFDF\uDFF7-\uDFFF]|\uD804[\uDC4E-\uDC51\uDC76-\uDC7E\uDCBD\uDCC3-\uDCCF\uDCE9-\uDCEF\uDCFA-\uDCFF\uDD35\uDD48-\uDD4F\uDD77-\uDD7F\uDDE0\uDDF5-\uDDFF\uDE12\uDE42-\uDE7F\uDE87\uDE89\uDE8E\uDE9E\uDEAA-\uDEAF\uDEEB-\uDEEF\uDEFA-\uDEFF\uDF04\uDF0D\uDF0E\uDF11\uDF12\uDF29\uDF31\uDF34\uDF3A\uDF45\uDF46\uDF49\uDF4A\uDF4E\uDF4F\uDF51-\uDF56\uDF58-\uDF5C\uDF64\uDF65\uDF6D-\uDF6F\uDF75-\uDF7F\uDF8A\uDF8C\uDF8D\uDF8F\uDFB6\uDFC1\uDFC3\uDFC4\uDFC6\uDFCB\uDFD6\uDFD9-\uDFE0\uDFE3-\uDFFF]|\uD805[\uDC5C\uDC62-\uDC7F\uDCC8-\uDCCF\uDCDA-\uDD7F\uDDB6\uDDB7\uDDDE-\uDDFF\uDE45-\uDE4F\uDE5A-\uDE5F\uDE6D-\uDE7F\uDEBA-\uDEBF\uDECA-\uDECF\uDEE4-\uDEFF\uDF1B\uDF1C\uDF2C-\uDF2F\uDF47-\uDFFF]|\uD806[\uDC3C-\uDC9F\uDCF3-\uDCFE\uDD07\uDD08\uDD0A\uDD0B\uDD14\uDD17\uDD36\uDD39\uDD3A\uDD47-\uDD4F\uDD5A-\uDD9F\uDDA8\uDDA9\uDDD8\uDDD9\uDDE5-\uDDFF\uDE48-\uDE4F\uDEA3-\uDEAF\uDEF9-\uDEFF\uDF0A-\uDFBF\uDFE2-\uDFEF\uDFFA-\uDFFF]|\uD807[\uDC09\uDC37\uDC46-\uDC4F\uDC6D-\uDC6F\uDC90\uDC91\uDCA8\uDCB7-\uDCFF\uDD07\uDD0A\uDD37-\uDD39\uDD3B\uDD3E\uDD48-\uDD4F\uDD5A-\uDD5F\uDD66\uDD69\uDD8F\uDD92\uDD99-\uDD9F\uDDAA-\uDEDF\uDEF9-\uDEFF\uDF11\uDF3B-\uDF3D\uDF5B-\uDFAF\uDFB1-\uDFBF\uDFF2-\uDFFE]|\uD808[\uDF9A-\uDFFF]|\uD809[\uDC6F\uDC75-\uDC7F\uDD44-\uDFFF]|[\uD80A\uD812-\uD817\uD819\uD824-\uD82A\uD82D\uD82E\uD830-\uD832\uD83F\uD87C\uD87D\uD87F\uD889-\uDB3F\uDB41-\uDBFF][\uDC00-\uDFFF]|\uD80B[\uDC00-\uDF8F\uDFF3-\uDFFF]|\uD80D[\uDC30-\uDC3F\uDC56-\uDC5F]|\uD810[\uDFFB-\uDFFF]|\uD811[\uDE47-\uDFFF]|\uD818[\uDC00-\uDCFF\uDD3A-\uDFFF]|\uD81A[\uDE39-\uDE3F\uDE5F\uDE6A-\uDE6D\uDEBF\uDECA-\uDECF\uDEEE\uDEEF\uDEF6-\uDEFF\uDF46-\uDF4F\uDF5A\uDF62\uDF78-\uDF7C\uDF90-\uDFFF]|\uD81B[\uDC00-\uDD3F\uDD7A-\uDE3F\uDE9B-\uDEFF\uDF4B-\uDF4E\uDF88-\uDF8E\uDFA0-\uDFDF\uDFE5-\uDFEF\uDFF2-\uDFFF]|\uD821[\uDFF8-\uDFFF]|\uD823[\uDCD6-\uDCFE\uDD09-\uDFFF]|\uD82B[\uDC00-\uDFEF\uDFF4\uDFFC\uDFFF]|\uD82C[\uDD23-\uDD31\uDD33-\uDD4F\uDD53\uDD54\uDD56-\uDD63\uDD68-\uDD6F\uDEFC-\uDFFF]|\uD82F[\uDC6B-\uDC6F\uDC7D-\uDC7F\uDC89-\uDC8F\uDC9A\uDC9B\uDCA0-\uDFFF]|\uD833[\uDCFA-\uDCFF\uDEB4-\uDEFF\uDF2E\uDF2F\uDF47-\uDF4F\uDFC4-\uDFFF]|\uD834[\uDCF6-\uDCFF\uDD27\uDD28\uDD73-\uDD7A\uDDEB-\uDDFF\uDE46-\uDEBF\uDED4-\uDEDF\uDEF4-\uDEFF\uDF57-\uDF5F\uDF79-\uDFFF]|\uD835[\uDC55\uDC9D\uDCA0\uDCA1\uDCA3\uDCA4\uDCA7\uDCA8\uDCAD\uDCBA\uDCBC\uDCC4\uDD06\uDD0B\uDD0C\uDD15\uDD1D\uDD3A\uDD3F\uDD45\uDD47-\uDD49\uDD51\uDEA6\uDEA7\uDFCC\uDFCD]|\uD836[\uDE8C-\uDE9A\uDEA0\uDEB0-\uDFFF]|\uD837[\uDC00-\uDEFF\uDF1F-\uDF24\uDF2B-\uDFFF]|\uD838[\uDC07\uDC19\uDC1A\uDC22\uDC25\uDC2B-\uDC2F\uDC6E-\uDC8E\uDC90-\uDCFF\uDD2D-\uDD2F\uDD3E\uDD3F\uDD4A-\uDD4D\uDD50-\uDE8F\uDEAF-\uDEBF\uDEFA-\uDEFE\uDF00-\uDFFF]|\uD839[\uDC00-\uDCCF\uDCFA-\uDDCF\uDDFB-\uDDFE\uDE00-\uDFDF\uDFE7\uDFEC\uDFEF\uDFFF]|\uD83A[\uDCC5\uDCC6\uDCD7-\uDCFF\uDD4C-\uDD4F\uDD5A-\uDD5D\uDD60-\uDFFF]|\uD83B[\uDC00-\uDC70\uDCB5-\uDD00\uDD3E-\uDDFF\uDE04\uDE20\uDE23\uDE25\uDE26\uDE28\uDE33\uDE38\uDE3A\uDE3C-\uDE41\uDE43-\uDE46\uDE48\uDE4A\uDE4C\uDE50\uDE53\uDE55\uDE56\uDE58\uDE5A\uDE5C\uDE5E\uDE60\uDE63\uDE65\uDE66\uDE6B\uDE73\uDE78\uDE7D\uDE7F\uDE8A\uDE9C-\uDEA0\uDEA4\uDEAA\uDEBC-\uDEEF\uDEF2-\uDFFF]|\uD83C[\uDC2C-\uDC2F\uDC94-\uDC9F\uDCAF\uDCB0\uDCC0\uDCD0\uDCF6-\uDCFF\uDDAE-\uDDE5\uDE03-\uDE0F\uDE3C-\uDE3F\uDE49-\uDE4F\uDE52-\uDE5F\uDE66-\uDEFF]|\uD83D[\uDED8-\uDEDB\uDEED-\uDEEF\uDEFD-\uDEFF\uDF77-\uDF7A\uDFDA-\uDFDF\uDFEC-\uDFEF\uDFF1-\uDFFF]|\uD83E[\uDC0C-\uDC0F\uDC48-\uDC4F\uDC5A-\uDC5F\uDC88-\uDC8F\uDCAE\uDCAF\uDCBC-\uDCBF\uDCC2-\uDCFF\uDE54-\uDE5F\uDE6E\uDE6F\uDE7D-\uDE7F\uDE8A-\uDE8E\uDEC7-\uDECD\uDEDD\uDEDE\uDEEA-\uDEEF\uDEF9-\uDEFF\uDF93\uDFFA-\uDFFF]|\uD869[\uDEE0-\uDEFF]|\uD86D[\uDF3A-\uDF3F]|\uD86E[\uDC1E\uDC1F]|\uD873[\uDEA2-\uDEAF]|\uD87A[\uDFE1-\uDFEF]|\uD87B[\uDE5E-\uDFFF]|\uD87E[\uDE1E-\uDFFF]|\uD884[\uDF4B-\uDF4F]|\uD888[\uDFB0-\uDFFF]|\uDB40[\uDC00-\uDCFF\uDDF0-\uDFFF]|[\uD800-\uDBFF](?![\uDC00-\uDFFF])|(?:[^\uD800-\uDBFF]|^)[\uDC00-\uDFFF])|(?:[!-#%-\*,-\/:;\?@\[-\]_\{\}\xA1\xA7\xAB\xB6\xB7\xBB\xBF\u037E\u0387\u055A-\u055F\u0589\u058A\u05BE\u05C0\u05C3\u05C6\u05F3\u05F4\u0609\u060A\u060C\u060D\u061B\u061D-\u061F\u066A-\u066D\u06D4\u0700-\u070D\u07F7-\u07F9\u0830-\u083E\u085E\u0964\u0965\u0970\u09FD\u0A76\u0AF0\u0C77\u0C84\u0DF4\u0E4F\u0E5A\u0E5B\u0F04-\u0F12\u0F14\u0F3A-\u0F3D\u0F85\u0FD0-\u0FD4\u0FD9\u0FDA\u104A-\u104F\u10FB\u1360-\u1368\u1400\u166E\u169B\u169C\u16EB-\u16ED\u1735\u1736\u17D4-\u17D6\u17D8-\u17DA\u1800-\u180A\u1944\u1945\u1A1E\u1A1F\u1AA0-\u1AA6\u1AA8-\u1AAD\u1B4E\u1B4F\u1B5A-\u1B60\u1B7D-\u1B7F\u1BFC-\u1BFF\u1C3B-\u1C3F\u1C7E\u1C7F\u1CC0-\u1CC7\u1CD3\u2010-\u2027\u2030-\u2043\u2045-\u2051\u2053-\u205E\u207D\u207E\u208D\u208E\u2308-\u230B\u2329\u232A\u2768-\u2775\u27C5\u27C6\u27E6-\u27EF\u2983-\u2998\u29D8-\u29DB\u29FC\u29FD\u2CF9-\u2CFC\u2CFE\u2CFF\u2D70\u2E00-\u2E2E\u2E30-\u2E4F\u2E52-\u2E5D\u3001-\u3003\u3008-\u3011\u3014-\u301F\u3030\u303D\u30A0\u30FB\uA4FE\uA4FF\uA60D-\uA60F\uA673\uA67E\uA6F2-\uA6F7\uA874-\uA877\uA8CE\uA8CF\uA8F8-\uA8FA\uA8FC\uA92E\uA92F\uA95F\uA9C1-\uA9CD\uA9DE\uA9DF\uAA5C-\uAA5F\uAADE\uAADF\uAAF0\uAAF1\uABEB\uFD3E\uFD3F\uFE10-\uFE19\uFE30-\uFE52\uFE54-\uFE61\uFE63\uFE68\uFE6A\uFE6B\uFF01-\uFF03\uFF05-\uFF0A\uFF0C-\uFF0F\uFF1A\uFF1B\uFF1F\uFF20\uFF3B-\uFF3D\uFF3F\uFF5B\uFF5D\uFF5F-\uFF65]|\uD800[\uDD00-\uDD02\uDF9F\uDFD0]|\uD801\uDD6F|\uD802[\uDC57\uDD1F\uDD3F\uDE50-\uDE58\uDE7F\uDEF0-\uDEF6\uDF39-\uDF3F\uDF99-\uDF9C]|\uD803[\uDD6E\uDEAD\uDF55-\uDF59\uDF86-\uDF89]|\uD804[\uDC47-\uDC4D\uDCBB\uDCBC\uDCBE-\uDCC1\uDD40-\uDD43\uDD74\uDD75\uDDC5-\uDDC8\uDDCD\uDDDB\uDDDD-\uDDDF\uDE38-\uDE3D\uDEA9\uDFD4\uDFD5\uDFD7\uDFD8]|\uD805[\uDC4B-\uDC4F\uDC5A\uDC5B\uDC5D\uDCC6\uDDC1-\uDDD7\uDE41-\uDE43\uDE60-\uDE6C\uDEB9\uDF3C-\uDF3E]|\uD806[\uDC3B\uDD44-\uDD46\uDDE2\uDE3F-\uDE46\uDE9A-\uDE9C\uDE9E-\uDEA2\uDF00-\uDF09\uDFE1]|\uD807[\uDC41-\uDC45\uDC70\uDC71\uDEF7\uDEF8\uDF43-\uDF4F\uDFFF]|\uD809[\uDC70-\uDC74]|\uD80B[\uDFF1\uDFF2]|\uD81A[\uDE6E\uDE6F\uDEF5\uDF37-\uDF3B\uDF44]|\uD81B[\uDD6D-\uDD6F\uDE97-\uDE9A\uDFE2]|\uD82F\uDC9F|\uD836[\uDE87-\uDE8B]|\uD839\uDDFF|\uD83A[\uDD5E\uDD5F])|[ \xA0\u1680\u2000-\u200A\u2028\u2029\u202F\u205F\u3000]|(?:[\$\+<->\^`\|~\xA2-\xA6\xA8\xA9\xAC\xAE-\xB1\xB4\xB8\xD7\xF7\u02C2-\u02C5\u02D2-\u02DF\u02E5-\u02EB\u02ED\u02EF-\u02FF\u0375\u0384\u0385\u03F6\u0482\u058D-\u058F\u0606-\u0608\u060B\u060E\u060F\u06DE\u06E9\u06FD\u06FE\u07F6\u07FE\u07FF\u0888\u09F2\u09F3\u09FA\u09FB\u0AF1\u0B70\u0BF3-\u0BFA\u0C7F\u0D4F\u0D79\u0E3F\u0F01-\u0F03\u0F13\u0F15-\u0F17\u0F1A-\u0F1F\u0F34\u0F36\u0F38\u0FBE-\u0FC5\u0FC7-\u0FCC\u0FCE\u0FCF\u0FD5-\u0FD8\u109E\u109F\u1390-\u1399\u166D\u17DB\u1940\u19DE-\u19FF\u1B61-\u1B6A\u1B74-\u1B7C\u1FBD\u1FBF-\u1FC1\u1FCD-\u1FCF\u1FDD-\u1FDF\u1FED-\u1FEF\u1FFD\u1FFE\u2044\u2052\u207A-\u207C\u208A-\u208C\u20A0-\u20C0\u2100\u2101\u2103-\u2106\u2108\u2109\u2114\u2116-\u2118\u211E-\u2123\u2125\u2127\u2129\u212E\u213A\u213B\u2140-\u2144\u214A-\u214D\u214F\u218A\u218B\u2190-\u2307\u230C-\u2328\u232B-\u2429\u2440-\u244A\u249C-\u24E9\u2500-\u2767\u2794-\u27C4\u27C7-\u27E5\u27F0-\u2982\u2999-\u29D7\u29DC-\u29FB\u29FE-\u2B73\u2B76-\u2B95\u2B97-\u2BFF\u2CE5-\u2CEA\u2E50\u2E51\u2E80-\u2E99\u2E9B-\u2EF3\u2F00-\u2FD5\u2FF0-\u2FFF\u3004\u3012\u3013\u3020\u3036\u3037\u303E\u303F\u309B\u309C\u3190\u3191\u3196-\u319F\u31C0-\u31E5\u31EF\u3200-\u321E\u322A-\u3247\u3250\u3260-\u327F\u328A-\u32B0\u32C0-\u33FF\u4DC0-\u4DFF\uA490-\uA4C6\uA700-\uA716\uA720\uA721\uA789\uA78A\uA828-\uA82B\uA836-\uA839\uAA77-\uAA79\uAB5B\uAB6A\uAB6B\uFB29\uFBB2-\uFBC2\uFD40-\uFD4F\uFDCF\uFDFC-\uFDFF\uFE62\uFE64-\uFE66\uFE69\uFF04\uFF0B\uFF1C-\uFF1E\uFF3E\uFF40\uFF5C\uFF5E\uFFE0-\uFFE6\uFFE8-\uFFEE\uFFFC\uFFFD]|\uD800[\uDD37-\uDD3F\uDD79-\uDD89\uDD8C-\uDD8E\uDD90-\uDD9C\uDDA0\uDDD0-\uDDFC]|\uD802[\uDC77\uDC78\uDEC8]|\uD803[\uDD8E\uDD8F]|\uD805\uDF3F|\uD807[\uDFD5-\uDFF1]|\uD81A[\uDF3C-\uDF3F\uDF45]|\uD82F\uDC9C|\uD833[\uDC00-\uDCEF\uDD00-\uDEB3\uDF50-\uDFC3]|\uD834[\uDC00-\uDCF5\uDD00-\uDD26\uDD29-\uDD64\uDD6A-\uDD6C\uDD83\uDD84\uDD8C-\uDDA9\uDDAE-\uDDEA\uDE00-\uDE41\uDE45\uDF00-\uDF56]|\uD835[\uDEC1\uDEDB\uDEFB\uDF15\uDF35\uDF4F\uDF6F\uDF89\uDFA9\uDFC3]|\uD836[\uDC00-\uDDFF\uDE37-\uDE3A\uDE6D-\uDE74\uDE76-\uDE83\uDE85\uDE86]|\uD838[\uDD4F\uDEFF]|\uD83B[\uDCAC\uDCB0\uDD2E\uDEF0\uDEF1]|\uD83C[\uDC00-\uDC2B\uDC30-\uDC93\uDCA0-\uDCAE\uDCB1-\uDCBF\uDCC1-\uDCCF\uDCD1-\uDCF5\uDD0D-\uDDAD\uDDE6-\uDE02\uDE10-\uDE3B\uDE40-\uDE48\uDE50\uDE51\uDE60-\uDE65\uDF00-\uDFFF]|\uD83D[\uDC00-\uDED7\uDEDC-\uDEEC\uDEF0-\uDEFC\uDF00-\uDF76\uDF7B-\uDFD9\uDFE0-\uDFEB\uDFF0]|\uD83E[\uDC00-\uDC0B\uDC10-\uDC47\uDC50-\uDC59\uDC60-\uDC87\uDC90-\uDCAD\uDCB0-\uDCBB\uDCC0\uDCC1\uDD00-\uDE53\uDE60-\uDE6D\uDE70-\uDE7C\uDE80-\uDE89\uDE8F-\uDEC6\uDECE-\uDEDC\uDEDF-\uDEE9\uDEF0-\uDEF8\uDF00-\uDF92\uDF94-\uDFEF]))+$/, ""), a ? (this.utterance = new SpeechSynthesisUtterance(a), this.utterance.onstart = function() {
                                        e.speechSpeaking = !0, e.skipAutoNext = !1
                                    }, this.utterance.onend = function() {
                                        e.skipAutoNext ? (e.skipAutoNext = !1, e.speechSpeaking = !1) : e.speechNext()
                                    }, this.utterance.onerror = function(t) {
                                        "canceled" === t.error || "interrupted" === t.error || (t.error || t.name) && e.$message.error("朗读错误:  ".concat(t.type || "", "  ").concat(t.error || t.name || t.toString())), setTimeout((function() {
                                            e.speechSpeaking = window.speechSynthesis.speaking || !1
                                        }), 200)
                                    }, this.utterance.voice = t, this.utterance.pitch = this.speechPitch, this.utterance.rate = this.speechRate, this.showParagraph(n, !0), n.className = "reading", this.speechSpeaking = !0, window.speechSynthesis.speak(this.utterance)) : this.speechNext())
                                } else this.speechNext()
                            }
                        },
                        stopSpeech: function(e) {
                            try {
                                if (this.skipAutoNext = !0, "local" === this.ttsType ? window.speechSynthesis.cancel() : this.$refs.ttsAudio.pause(), e) {
                                    var t = this.getCurrentParagraph();
                                    t && (t.className = "")
                                }
                            } catch (re) {}
                        },
                        restartSpeech: function() {
                            var e = this;
                            this.stopSpeech(), setTimeout((function() {
                                e.startSpeech()
                            }), 100)
                        },
                        toggleSpeech: function() {
                            this.speechSpeaking ? this.stopSpeech(!0) : this.startSpeech()
                        },
                        speechPrev: function() {
                            var e = this;
                            window.speechSynthesis.speaking && this.stopSpeech();
                            var t = this.getCurrentParagraph(),
                                n = this.getPrevParagraph(1);
                            n ? (this.showParagraph(n, !0), t.className = "", n.className = "reading", this.startSpeech()) : (this.onNextContentRendered((function() {
                                setTimeout((function() {
                                    e.startSpeech()
                                }), 100)
                            })), this.toLastChapter())
                        },
                        speechNext: function() {
                            var e = this;
                            window.speechSynthesis.speaking && this.stopSpeech();
                            var t = this.getCurrentParagraph(),
                                n = this.getNextParagraph(1);
                            n ? (this.showParagraph(n, !0), t.className = "", n.className = "reading", this.startSpeech()) : (this.onNextContentRendered((function() {
                                setTimeout((function() {
                                    e.startSpeech()
                                }), 100)
                            })), this.toNextChapter())
                        },
                        getCurrentParagraph: function() {
                            var e = this.getContentParagraphContainer().querySelectorAll(".reading"),
                                t = null;
                            if (e.length) t = e[0];
                            else
                                for (var n = this.getContentParagraphList(), a = 0; a < n.length; a++) {
                                    var u = n[a].getBoundingClientRect();
                                    if (this.isSlideRead) {
                                        if (u.right > 0) {
                                            t = n[a];
                                            break
                                        }
                                    } else {
                                        var o = u.bottom;
                                        if (this.isEpubIframe && (o -= document.documentElement.scrollTop || document.body.scrollTop), o > 50 + (0 | window.webAppDistance) + (0 | this.$store.state.safeArea.top)) {
                                            t = n[a];
                                            break
                                        }
                                    }
                                }
                            return t
                        },
                        getPrevParagraph: function(e) {
                            for (var t = this.getCurrentParagraph(), n = this.getContentParagraphList(), a = 0; a < n.length; a++)
                                if (a > 0 && t === n[a]) return n[a - e];
                            return null
                        },
                        getNextParagraph: function(e) {
                            for (var t = this.getCurrentParagraph(), n = this.getContentParagraphList(), a = 0; a < n.length; a++)
                                if (t === n[a]) return n[a + (e || 1)];
                            return null
                        },
                        exitRead: function() {
                            var e = this.getCurrentParagraph();
                            this.stopSpeech(!0), this.showReadBar = !1, this.showParagraph(e)
                        },
                        showParagraph: function(e, t) {
                            var n = this;
                            e && (this.isSlideRead ? this.$nextTick((function() {
                                var t = e.getBoundingClientRect();
                                t.left > n.windowSize.width - n.slideDistance && n.showPage(Math.round(t.left / (n.windowSize.width - n.slideDistance)) + 1, 0)
                            })) : t && this.$nextTick((function() {
                                var t = e.getBoundingClientRect(),
                                    a = t.top;
                                n.isEpubIframe && (a -= document.documentElement.scrollTop || document.body.scrollTop), n.scrollContent(a - (n.$store.state.miniInterface ? n.getFirstParagraphPos().bottom : 0) - (0 | window.webAppDistance) - (0 | n.$store.state.safeArea.top), 0)
                            })))
                        },
                        getFirstParagraphPos: function() {
                            return this.$refs.top.getBoundingClientRect()
                        },
                        scrollHandler: function() {
                            var e = this,
                                t = document.documentElement.scrollTop || document.body.scrollTop;
                            if (this.isSlideRead || (this.currentPage = Math.round((t + this.windowSize.height) / (this.windowSize.height - this.scrollOffset))), this.isScrollRead) {
                                var n = this.lastScrollTop || 0;
                                if (n > 0 && 0 === t);
                                else if (t > document.documentElement.scrollHeight - 2 * this.windowSize.height && !this.preCaching && this.startSavePosition) {
                                    this.preCaching = !0;
                                    var a = this.chapterIndex + 1;
                                    this.showChapterList.length && (a = this.showChapterList[this.showChapterList.length - 1].index + 1), this.showNextChapterSize = a - this.chapterIndex, this.loadShowChapter(a).then((function() {
                                        e.computeShowChapterList(), e.preCaching = !1
                                    }))["catch"]((function() {
                                        e.preCaching = !1
                                    }))
                                }
                            }
                            this.lastScrollTop = t, this.scrollTimer && clearTimeout(this.scrollTimer), this.scrollTimer = setTimeout(this.saveReadingPosition, 100)
                        },
                        beforeReadMethodChange: function() {
                            this.currentParagraph = this.getCurrentParagraph()
                        },
                        showPosition: function(e, t) {
                            var n = this;
                            if (this.$refs.bookContentRef)
                                if (this.isAudio || this.isVideo) this.$refs.bookContentRef.ensureSeekTime(e);
                                else if (this.isEpub || this.isCarToon) this.scrollContent(e, 0, !0), t && t();
                            else {
                                for (var a = this.getContentParagraphList(!0), u = 0; u < a.length; u++)
                                    if (a[u].dataset && "undefined" !== typeof a[u].dataset.pos && +a[u].dataset.pos >= e) {
                                        this.showParagraph(a[u], !0);
                                        break
                                    } t && t()
                            } else setTimeout((function() {
                                n.showPosition(e, t)
                            }), 10)
                        },
                        saveReadingPosition: function() {
                            try {
                                if (this.error || !this.startSavePosition) return;
                                Object(T["c"])("bookChapterPage@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author, this.currentPage);
                                var e = null;
                                if (this.isAudio || this.isVideo) e = this.$refs.bookContentRef ? this.$refs.bookContentRef.currentTime : 0;
                                else if (this.isEpub || this.isCarToon) e = document.documentElement.scrollTop || document.body.scrollTop;
                                else {
                                    if (this.preCaching) return;
                                    if (this.currentParagraph = this.getCurrentParagraph(), this.currentParagraph) {
                                        var t = this.currentParagraph;
                                        while (t.className.indexOf("chapter-content") < 0)
                                            if (t = t.parentNode, t === this.$refs.bookContentRef.$el) break;
                                        if (t) {
                                            if (t.dataset && "undefined" !== typeof t.dataset.index) {
                                                var n = +t.dataset.index;
                                                if (n != this.$store.getters.readingBook.index) {
                                                    var a = Object(i["a"])({}, this.$store.getters.readingBook);
                                                    a.index = n, this.$store.commit("setReadingBook", a), this.saveBookProgress(), this.title = this.$store.getters.readingBook.catalog[n].title
                                                }
                                            }
                                            e = t.innerText.indexOf(this.currentParagraph.innerText)
                                        }
                                    }
                                }
                                null !== e && Object(T["c"])("bookChapterProgress@" + this.$store.getters.readingBook.name + "_" + this.$store.getters.readingBook.author, e)
                            } catch (re) {}
                        },
                        autoShowPosition: function(e) {
                            var t = this,
                                n = function() {
                                    if (setTimeout((function() {
                                            t.startSavePosition = !0
                                        }), 2e3), !t.error) {
                                        var e = Object(T["b"])("bookChapterProgress@" + t.$store.getters.readingBook.name + "_" + t.$store.getters.readingBook.author);
                                        if (e && +e) t.$nextTick((function() {
                                            t.showPosition(+e, (function() {
                                                t.startSavePosition = !0
                                            }))
                                        }));
                                        else {
                                            var n = Object(T["b"])("bookChapterPage@" + t.$store.getters.readingBook.name + "_" + t.$store.getters.readingBook.author);
                                            n && +n && t.$nextTick((function() {
                                                t.computePages((function() {
                                                    t.showPage(n, 0)
                                                }))
                                            }))
                                        }
                                    }
                                };
                            e ? n() : this.onNextContentRendered(n)
                        },
                        onNextContentRendered: function(e) {
                            var t = this;
                            this.isEpub ? this.$once("iframeLoad", (function() {
                                t.$nextTick((function() {
                                    e && e()
                                }))
                            })) : this.isCarToon ? this.$once("lazyload", (function() {
                                t.$nextTick((function() {
                                    e && e()
                                }))
                            })) : this.$once("showContent", (function() {
                                t.$nextTick((function() {
                                    e && e()
                                }))
                            }))
                        },
                        onNextContentPagesInit: function(e) {
                            var t = this;
                            if (this.isEpub) this.$once("iframeLoad", (function() {
                                t.$nextTick((function() {
                                    e && e()
                                }))
                            }));
                            else {
                                var n = !1;
                                this.$once("showContent", (function() {
                                    n = !0
                                }));
                                var a = function() {
                                    n && (t.$off("afterComputePages", a), e && e())
                                };
                                this.$on("afterComputePages", a)
                            }
                        },
                        wakeLock: function() {
                            if ("WakeLock" in window && "request" in window.WakeLock) {
                                var e = null,
                                    t = function() {
                                        var e = new AbortController,
                                            t = e.signal;
                                        return window.WakeLock.request("screen", {
                                            signal: t
                                        })["catch"]((function(e) {
                                            e.name
                                        })), e
                                    };
                                e = t();
                                var n = function() {
                                    null !== e && "visible" === document.visibilityState && (e = t())
                                };
                                return document.addEventListener("visibilitychange", n), document.addEventListener("fullscreenchange", n),
                                    function() {
                                        null != e && (e.abort(), e = null), document.removeEventListener("visibilitychange", n), document.removeEventListener("fullscreenchange", n)
                                    }
                            }
                            if ("wakeLock" in navigator && "request" in navigator.wakeLock) {
                                var a = null,
                                    u = function() {
                                        var e = Object(r["a"])(Object(o["a"])().mark((function e() {
                                            return Object(o["a"])().wrap((function(e) {
                                                while (1) switch (e.prev = e.next) {
                                                    case 0:
                                                        return e.prev = 0, e.next = 3, navigator.wakeLock.request("screen");
                                                    case 3:
                                                        a = e.sent, a.addEventListener("release", (function() {})), e.next = 9;
                                                        break;
                                                    case 7:
                                                        e.prev = 7, e.t0 = e["catch"](0);
                                                    case 9:
                                                    case "end":
                                                        return e.stop()
                                                }
                                            }), e, null, [
                                                [0, 7]
                                            ])
                                        })));
                                        return function() {
                                            return e.apply(this, arguments)
                                        }
                                    }();
                                u();
                                var i = function() {
                                    null !== a && "visible" === document.visibilityState && u()
                                };
                                return document.addEventListener("visibilitychange", i), document.addEventListener("fullscreenchange", i),
                                    function() {
                                        null != a && (a.release(), a = null), document.removeEventListener("visibilitychange", i), document.removeEventListener("fullscreenchange", i)
                                    }
                            }
                        },
                        lazyloadHandler: function() {
                            this.isAudio || this.isVideo || (this.computePages(), this.$emit("lazyload"))
                        },
                        showCacheContent: function() {
                            this.showCacheContentZone = !this.showCacheContentZone
                        },
                        cacheChapterContent: function(e) {
                            var t = this,
                                n = [];
                            n = !0 === e ? n.concat(this.catalog.slice(this.chapterIndex + 1, this.catalog.length)) : n.concat(this.catalog.slice(this.chapterIndex + 1, Math.min(this.catalog.length, this.chapterIndex + 1 + e))), n.length ? (this.isCachingContent = !0, this.cachingContentTip = "正在缓存章节  0/" + n.length, this.cachingHandler = Object(N["a"])(2, (function(e) {
                                t.cachingContentTip = "正在缓存章节  " + e.requestCount + "/" + n.length, e.isEnd() && (t.$message.success("缓存完成"), t.isCachingContent = !1, t.cachingContentTip = "")
                            })), n.forEach((function(e) {
                                t.cachingHandler((function() {
                                    return t.getBookContent(e.index, {
                                        timeout: 1e3 * t.$store.getters.config.chapterRequestTimeout,
                                        silent: !0
                                    }, !1, !0)
                                }))
                            }))) : this.$message.error("不需要缓存")
                        },
                        cancelCaching: function() {
                            this.cachingHandler && this.cachingHandler.cancel && (this.cachingHandler.cancel(), this.isCachingContent = !1, this.cachingContentTip = "")
                        },
                        startAutoReading: function() {
                            var e = this;
                            this.showToolBar = !1, this.autoReading = !0, this.$nextTick((function() {
                                e.autoRead()
                            }))
                        },
                        autoRead: function() {
                            var e = this;
                            if (this.autoReading)
                                if (this.showToolBar) this.autoReadingTimer = setTimeout((function() {
                                    e.autoRead()
                                }), 300);
                                else if ("像素滚动" !== this.config.autoReadingMethod) {
                                var t = this.getCurrentParagraph(),
                                    n = this.getNextParagraph();
                                if (n) {
                                    t.className = "reading", n.className = "";
                                    var a = this.config.autoReadingLineTime;
                                    try {
                                        var u = t.getBoundingClientRect();
                                        a *= Math.ceil(u.height / this.config.fontSize / this.config.lineHeight)
                                    } catch (re) {}
                                    this.autoReadingTimer = setTimeout((function() {
                                        t.className = "", n.className = "reading", e.showParagraph(n, !0), setTimeout((function() {
                                            e.autoRead()
                                        }), 32)
                                    }), a)
                                } else this.onNextContentRendered((function() {
                                    setTimeout((function() {
                                        e.autoRead()
                                    }), 100)
                                })), this.toNextChapter((function() {
                                    e.autoReading = !1
                                }))
                            } else this.autoReadByPixel()
                        },
                        autoReadByPixel: function() {
                            var e = this;
                            if (this.autoReading)
                                if (this.showToolBar) this.autoReadingTimer = setTimeout((function() {
                                    e.autoRead()
                                }), 300);
                                else if ("像素滚动" === this.config.autoReadingMethod) {
                                var t = document.documentElement.scrollTop || document.body.scrollTop;
                                t + this.windowSize.height < document.documentElement.scrollHeight ? this.autoReadingTimer = setTimeout((function() {
                                    e.scrollContent(e.config.autoReadingPixel, 0), e.autoReadByPixel()
                                }), this.config.autoReadingLineTime) : (this.onNextContentRendered((function() {
                                    setTimeout((function() {
                                        e.autoReadByPixel()
                                    }), 100)
                                })), this.toNextChapter((function() {
                                    e.autoReading = !1
                                })))
                            } else this.autoRead()
                        },
                        stopAutoReading: function() {
                            this.autoReadingTimer && clearInterval(this.autoReadingTimer), this.autoReading = !1;
                            var e = this.getCurrentParagraph();
                            e.className = ""
                        },
                        toggleAutoReading: function() {
                            this.autoReading ? this.stopAutoReading() : this.startAutoReading()
                        },
                        showReadingBookInfo: function() {
                            var e = Object(i["a"])({}, this.$store.getters.readingBook),
                                t = this.$store.getters.shelfBooks.find((function(t) {
                                    return t.bookUrl === e.bookUrl
                                }));
                            e = Object.assign(e, t || {}), S["a"].$emit("showBookInfoDialog", e)
                        },
                        formatChinese: function(e) {
                            return this.isEpub || this.isAudio || this.isVideo || this.isCbz || this.isCarToon ? e : "简体" === this.config.chineseFont ? Dt(e) : "繁体" === this.config.chineseFont ? dt(e) : e
                        },
                        showSearchBookContentDialog: function() {
                            var e = Object(i["a"])({}, this.$store.getters.readingBook),
                                t = this.$store.getters.shelfBooks.find((function(t) {
                                    return t.bookUrl === e.bookUrl
                                }));
                            e = Object.assign(e, t || {}), S["a"].$emit("showSearchBookContentDialog", e)
                        },
                        showMatchKeyword: function(e) {
                            var t = this;
                            if (!this._inactive)
                                if (this.$refs.bookContentRef)
                                    if (!this.isEpubResolve || this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.shadowRoot) {
                                        if (this.isEpubIframe) try {
                                            if (!this.$refs.bookContentRef.$el || !this.$refs.bookContentRef.$el.contentWindow || !this.$refs.bookContentRef.$el.contentWindow.document || !this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body")) return void setTimeout((function() {
                                                t.showMatchKeyword(e)
                                            }), 10)
                                        } catch (s) {
                                            return
                                        }
                                        try {
                                            for (var n = this.getContentParagraphList(!0), a = 0, u = 0; u < n.length; u++) {
                                                var o = n[u].innerText,
                                                    r = -1,
                                                    i = !1;
                                                while (1) {
                                                    if (r = o.indexOf(e.query, r + 1), !(r >= 0)) break;
                                                    if (a++, a === e.resultCountWithinChapter + 1) {
                                                        i = !0, this.showParagraph(n[u], !0);
                                                        break
                                                    }
                                                }
                                                if (i) break
                                            }
                                        } catch (re) {}
                                    } else setTimeout((function() {
                                        t.showMatchKeyword(e)
                                    }), 10);
                            else setTimeout((function() {
                                t.showMatchKeyword(e)
                            }), 10)
                        },
                        getParagraphListInView: function() {
                            for (var e = this.getContentParagraphList(), t = [], n = 0; n < e.length; n++) {
                                var a = e[n].getBoundingClientRect();
                                this.isSlideRead ? a.right > 0 && a.left > 0 && t.push(e[n]) : a.bottom > 50 + (0 | window.webAppDistance) + (0 | this.$store.state.safeArea.top) && a.bottom < this.windowSize.height && t.push(e[n])
                            }
                            return t
                        },
                        showBookmarkDialog: function() {
                            var e = Object(i["a"])({}, this.$store.getters.readingBook),
                                t = this.$store.getters.shelfBooks.find((function(t) {
                                    return t.bookUrl === e.bookUrl
                                }));
                            e = Object.assign(e, t || {}), S["a"].$emit("showBookmarkDialog", e)
                        },
                        getContentParagraphContainer: function() {
                            if (!this.isEpub) return this.$refs.bookContentRef.$el;
                            if (!this.isEpubIframe) return this.$refs.bookContentRef.$el.shadowRoot;
                            try {
                                return this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body"), this.$refs.bookContentRef.$el.contentWindow.document
                            } catch (e) {
                                return this.$refs.bookContentRef.$el
                            }
                        },
                        getContentParagraphList: function(e) {
                            var t = [];
                            return t = this.isEpub ? (this.isEpubIframe, this.getContentParagraphContainer().querySelectorAll("h1,h2,h3,h4,p")) : this.getContentParagraphContainer().querySelectorAll(e ? ".reading-chapter h3,p" : "h3,p"), t
                        },
                        getContentMatchParagraph: function(e, t, n) {
                            t = t || .7;
                            var a = e.replace(/\\n+/g, "\n").split(/\n+/).map((function(e) {
                                return e.replace(xt, "")
                            })).filter((function(e) {
                                return e
                            }));
                            try {
                                for (var u = this.getContentParagraphList(!0), o = null, r = 0; r < u.length; r++) {
                                    for (var i = !0, s = 0, l = r, c = 0; c < a.length; c++) {
                                        var p = null;
                                        while (r + s < u.length) {
                                            if (p = u[r + s].innerText.replace(xt, ""), p.length) break;
                                            s++, l++
                                        }
                                        if (!p) {
                                            i = !1;
                                            break
                                        }
                                        var d = Object(N["c"])(p, a[c]);
                                        if (d < t) {
                                            i = !1;
                                            break
                                        }
                                        s++
                                    }
                                    if (i) {
                                        o = u[l];
                                        break
                                    }
                                }
                                if (o) return o;
                                if (t - .1 >= n) return this.getContentMatchParagraph(e, t - .1, n)
                            } catch (re) {
                                console.error(re)
                            }
                            return null
                        },
                        showContentMatchParagraph: function(e) {
                            if (!this._inactive) {
                                var t = this.getContentMatchParagraph(e, 1, .6);
                                t ? this.showParagraph(t, !0) : this.$message.error("无法定位内容所在段落")
                            }
                        },
                        showBookmark: function(e) {
                            var t = this;
                            if (!this._inactive)
                                if (this.$refs.bookContentRef)
                                    if (!this.isEpubResolve || this.$refs.bookContentRef.$el && this.$refs.bookContentRef.$el.shadowRoot) {
                                        if (this.isEpubIframe) try {
                                            if (!this.$refs.bookContentRef.$el || !this.$refs.bookContentRef.$el.contentWindow || !this.$refs.bookContentRef.$el.contentWindow.document || !this.$refs.bookContentRef.$el.contentWindow.document.querySelectorAll("body")) return void setTimeout((function() {
                                                t.showBookmark(e)
                                            }), 10)
                                        } catch (n) {
                                            return
                                        }
                                        this.showContentMatchParagraph(e.bookText)
                                    } else setTimeout((function() {
                                        t.showBookmark(e)
                                    }), 10);
                            else setTimeout((function() {
                                t.showBookmark(e)
                            }), 10)
                        },
                        readOriginal: function() {
                            if (this.isPdf && this.$store.getters.readingBook.originName)
                                if (this.$store.getters.readingBook.originName.indexOf("localStore") >= 0 || this.$store.getters.readingBook.originName.indexOf("webdav") >= 0) {
                                    var e = St(this.api + "/file/download", {
                                        home: this.$store.getters.readingBook.originName.indexOf("localStore") >= 0 ? "__LOCAL_STORE__" : "__WEBDAV__",
                                        path: this.$store.getters.readingBook.originName.replace(/^.*(localStore|webdav)/g, ""),
                                        stream: 1,
                                        accessToken: this.$store.state.token
                                    });
                                    window.open(e, "__blank")
                                } else {
                                    var t = this.$store.getters.readingBook.bookUrl.replace("storage/data", "book-assets");
                                    window.open(t, "__blank")
                                }
                        }
                    }
                },
                Tt = Nt,
                Lt = (n("8d39"), n("0af4"), Object(m["a"])(Tt, a, u, !1, null, "9c1aae92", null));
            t["default"] = Lt.exports
        
