var render = function() {
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
                };
