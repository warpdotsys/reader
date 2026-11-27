/**
 * 恢复自 reader-pro-3.2.14 前端产物 js/app.54619a3e.js 的入口模块（module id: 56d7）
 * 证据与限制见 docs/FRONTEND-RECONSTRUCTION.md §12。
 * 说明：本文件是入口模块函数的**逐字切片**（js-beautify 后可读），其中
 *   require("xxxx") 即产物里的 o("xxxx")（webpack 模块 id），映射见 src/modules-map.json；
 *   变量名为压缩后的短名（Vue 实为 n["default"]），未做重命名以免破坏结构。
 */
module.exports = (function(e, t, o) {
        "use strict";
        o.r(t);
        require("e6cf"), require("a4d3"), require("c975"), require("d81d"), require("45fc"), require("0d03"), require("b0c0"), require("e9f5"), require("ab43"), require("d3b7"), require("e25e"), require("2ca0");
        var n = require("2b0e"),
            r = function() {
                var e = this,
                    t = e._self._c;
                return t("div", {
                    attrs: {
                        id: "app"
                    }
                }, [t("keep-alive", [t("router-view")], 1), t("el-dialog", {
                    attrs: {
                        visible: e.showLogin,
                        width: e.dialogWidth,
                        top: e.dialogTop
                    },
                    on: {
                        "update:visible": function(t) {
                            e.showLogin = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v(e._s(e.isLogin ? "登录" : "注册") + " "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            e.isLogin = !e.isLogin
                        }
                    }
                }, [e._v(e._s(e.isLogin ? "注册" : "登录"))])])]), t("el-form", {
                    attrs: {
                        model: e.loginForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "用户名"
                    }
                }, [t("el-input", {
                    attrs: {
                        autocomplete: "on"
                    },
                    model: {
                        value: e.loginForm.username,
                        callback: function(t) {
                            e.$set(e.loginForm, "username", t)
                        },
                        expression: "loginForm.username"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "密码"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "password",
                        autocomplete: "on",
                        "show-password": ""
                    },
                    nativeOn: {
                        keyup: function(t) {
                            return !t.type.indexOf("key") && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.login.apply(null, arguments)
                        }
                    },
                    model: {
                        value: e.loginForm.password,
                        callback: function(t) {
                            e.$set(e.loginForm, "password", t)
                        },
                        expression: "loginForm.password"
                    }
                })], 1), e.isLogin ? e._e() : t("el-form-item", {
                    attrs: {
                        label: "邀请码(没有则不填)"
                    }
                }, [t("el-input", {
                    attrs: {
                        autocomplete: "off"
                    },
                    nativeOn: {
                        keyup: function(t) {
                            return !t.type.indexOf("key") && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.login.apply(null, arguments)
                        }
                    },
                    model: {
                        value: e.loginForm.code,
                        callback: function(t) {
                            e.$set(e.loginForm, "code", t)
                        },
                        expression: "loginForm.code"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.remember,
                        callback: function(t) {
                            e.remember = t
                        },
                        expression: "remember"
                    }
                }, [e._v("记住登录信息")])], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.login
                    }
                }, [e._v("确 定")])], 1)], 1), t("el-dialog", {
                    attrs: {
                        title: e.editorTitle,
                        visible: e.showEditor,
                        width: e.dialogWidth,
                        top: e.$store.state.miniInterface ? "0" : "10vh",
                        fullscreen: e.$store.state.miniInterface
                    },
                    on: {
                        "update:visible": function(t) {
                            e.showEditor = t
                        }
                    }
                }, [t("div", {
                    ref: "editorRef",
                    staticClass: "code-editor language-json"
                }), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.closeEditor
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.saveEditor
                    }
                }, [e._v("保 存")])], 1)]), e.$store.state.showImageViewer ? t("ImageViewer", {
                    attrs: {
                        "z-index": 3200,
                        "initial-index": e.$store.state.previewImageIndex,
                        "on-close": e.closeViewer,
                        "url-list": e.$store.state.previewImgList
                    }
                }) : e._e(), t("ReplaceRuleForm", {
                    attrs: {
                        rule: e.replaceRule,
                        isAdd: e.isAddReplaceRule
                    },
                    model: {
                        value: e.showReplaceRuleForm,
                        callback: function(t) {
                            e.showReplaceRuleForm = t
                        },
                        expression: "showReplaceRuleForm"
                    }
                }), t("ReplaceRule", {
                    model: {
                        value: e.showReplaceRuleDialog,
                        callback: function(t) {
                            e.showReplaceRuleDialog = t
                        },
                        expression: "showReplaceRuleDialog"
                    }
                }), t("MPCode", {
                    model: {
                        value: e.showMPCodeDialog,
                        callback: function(t) {
                            e.showMPCodeDialog = t
                        },
                        expression: "showMPCodeDialog"
                    }
                }), t("BookManage", {
                    model: {
                        value: e.showBookManageDialog,
                        callback: function(t) {
                            e.showBookManageDialog = t
                        },
                        expression: "showBookManageDialog"
                    }
                }), t("BookInfo", {
                    model: {
                        value: e.showBookInfoDialog,
                        callback: function(t) {
                            e.showBookInfoDialog = t
                        },
                        expression: "showBookInfoDialog"
                    }
                }), t("UserManage", {
                    model: {
                        value: e.showUserManageDialog,
                        callback: function(t) {
                            e.showUserManageDialog = t
                        },
                        expression: "showUserManageDialog"
                    }
                }), t("UserForm", {
                    attrs: {
                        user: e.userForm
                    },
                    model: {
                        value: e.showUserFormDialog,
                        callback: function(t) {
                            e.showUserFormDialog = t
                        },
                        expression: "showUserFormDialog"
                    }
                }), t("BookGroup", {
                    attrs: {
                        isSet: e.isSetBookGroup
                    },
                    model: {
                        value: e.showBookGroupDialog,
                        callback: function(t) {
                            e.showBookGroupDialog = t
                        },
                        expression: "showBookGroupDialog"
                    }
                }), t("RssSourceList", {
                    model: {
                        value: e.showRssSourceListDialog,
                        callback: function(t) {
                            e.showRssSourceListDialog = t
                        },
                        expression: "showRssSourceListDialog"
                    }
                }), t("RssArticleList", {
                    attrs: {
                        rssSource: e.rssSource
                    },
                    model: {
                        value: e.showRssArticleListDialog,
                        callback: function(t) {
                            e.showRssArticleListDialog = t
                        },
                        expression: "showRssArticleListDialog"
                    }
                }), t("RssArticle", {
                    attrs: {
                        rssArticleInfo: e.rssArticleInfo
                    },
                    model: {
                        value: e.showRssArticleDialog,
                        callback: function(t) {
                            e.showRssArticleDialog = t
                        },
                        expression: "showRssArticleDialog"
                    }
                }), t("SearchBookContent", {
                    attrs: {
                        book: e.searchBook
                    },
                    model: {
                        value: e.showSearchBookContentDialog,
                        callback: function(t) {
                            e.showSearchBookContentDialog = t
                        },
                        expression: "showSearchBookContentDialog"
                    }
                }), t("BookmarkForm", {
                    attrs: {
                        bookmark: e.bookmark,
                        isAdd: e.isAddBookmark
                    },
                    model: {
                        value: e.showBookmarkForm,
                        callback: function(t) {
                            e.showBookmarkForm = t
                        },
                        expression: "showBookmarkForm"
                    }
                }), t("Bookmark", {
                    attrs: {
                        book: e.bookmarkInBook
                    },
                    model: {
                        value: e.showBookmarkDialog,
                        callback: function(t) {
                            e.showBookmarkDialog = t
                        },
                        expression: "showBookmarkDialog"
                    }
                }), t("FileManager", {
                    attrs: {
                        title: e.fileManagerTitle,
                        home: e.fileManagerHome
                    },
                    model: {
                        value: e.showFileManager,
                        callback: function(t) {
                            e.showFileManager = t
                        },
                        expression: "showFileManager"
                    }
                }), t("BookCover", {
                    attrs: {
                        book: e.bookCoverBook
                    },
                    model: {
                        value: e.showBookCoverDialog,
                        callback: function(t) {
                            e.showBookCoverDialog = t
                        },
                        expression: "showBookCoverDialog"
                    }
                }), t("License", {
                    model: {
                        value: e.showLicenseDialog,
                        callback: function(t) {
                            e.showLicenseDialog = t
                        },
                        expression: "showLicenseDialog"
                    }
                }), t("BookConfig", {
                    model: {
                        value: e.showBookConfigDialog,
                        callback: function(t) {
                            e.showBookConfigDialog = t
                        },
                        expression: "showBookConfigDialog"
                    }
                }), t("RemoteBookSourceSub", {
                    model: {
                        value: e.showRemoteBookSourceSubDialog,
                        callback: function(t) {
                            e.showRemoteBookSourceSubDialog = t
                        },
                        expression: "showRemoteBookSourceSubDialog"
                    }
                }), t("ActiveLicense", {
                    model: {
                        value: e.showActiveLicenseDialog,
                        callback: function(t) {
                            e.showActiveLicenseDialog = t
                        },
                        expression: "showActiveLicenseDialog"
                    }
                }), t("HttpTTS", {
                    model: {
                        value: e.showHttpTTSDialog,
                        callback: function(t) {
                            e.showHttpTTSDialog = t
                        },
                        expression: "showHttpTTSDialog"
                    }
                })], 1)
            },
            a = [],
            i = require("5530"),
            s = require("c7eb"),
            c = require("1da1"),
            l = (require("d401"), require("7db0"), require("4160"), require("e260"), require("14d9"), require("f665"), require("4d63"), require("c607"), require("ac1f"), require("2c3e"), require("00b4"), require("25f0"), require("3ca3"), require("5319"), require("e323"), require("ddb0"), require("4795"), require("./utils/app/be3b.js")),
            u = require("./utils/app/8552.js"),
            f = require("08a9"),
            d = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "替换规则管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("替换规则管理")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.$store.state.filterRules,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: "规则名称",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "scope",
                        label: "替换范围",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "isEnabled",
                        label: "是否启用",
                        "min-width": "80"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleRuleEnabled(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.isEnabled,
                                    callback: function(t) {
                                        e.$set(o.row, "isEnabled", t)
                                    },
                                    expression: "scope.row.isEnabled"
                                }
                            })]
                        }
                    }], null, !1, 2011687545)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editReplaceRule(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 1473522600)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteReplaceRules
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.uploadFile
                    }
                }, [e._v("导入")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.addNew
                    }
                }, [e._v("添加")]), t("input", {
                    ref: "fileRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onFileChange(t)
                        }
                    }
                })], 1)])]) : e._e()
            },
            p = [],
            h = (require("99af"), require("277d"), require("7d54"), require("b64b"), require("159b"), require("2f62")),
            m = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "ReplaceRule",
                data: function() {
                    return {
                        localSelection: []
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show"],
                watch: {
                    show: function(e) {}
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            default:
                                return o
                        }
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    deleteReplaceRules: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.localSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的替换规则"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的替换规则吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/deleteReplaceRules", e.localSelection).then((function(t) {
                                            t.data.isSuccess && (e.localSelection = [], e.$message.success("删除替换规则成功"), e.$root.$children[0].loadReplaceRules(!0))
                                        }), (function(t) {
                                            e.$message.error("删除替换规则失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    toggleRuleEnabled: function(e, t) {
                        var o = this;
                        l["a"].post("/saveReplaceRule", Object(i["a"])(Object(i["a"])({}, e), {}, {
                            isEnabled: t
                        })).then((function(e) {
                            e.data.isSuccess && (o.$message.success("修改成功"), o.$root.$children[0].loadReplaceRules(!0))
                        }), (function(e) {
                            o.$message.error("修改失败 " + (e && e.toString()))
                        }))
                    },
                    editReplaceRule: function(e) {
                        u["a"].$emit("showReplaceRuleForm", Object(i["a"])({}, e), !1)
                    },
                    addNew: function() {
                        u["a"].$emit("showReplaceRuleForm", {
                            name: "文本替换",
                            pattern: "",
                            replacement: "",
                            isRegex: !1,
                            isEnabled: !0,
                            scope: this.$store.getters.readingBook ? this.$store.getters.readingBook.name + ";" + this.$store.getters.readingBook.bookUrl : "*"
                        }, !1)
                    },
                    uploadFile: function() {
                        this.$refs.fileRef.dispatchEvent(new MouseEvent("click"))
                    },
                    onFileChange: function(e) {
                        var t = this,
                            o = e.target.files && e.target.files[0],
                            n = new FileReader;
                        n.onload = function(e) {
                            var o = e.target.result;
                            try {
                                var n = JSON.parse(o);
                                Array.isArray(n) && n.length && t.comfirmImport(n)
                            } catch (r) {
                                t.$message.error("替换规则文件错误")
                            }
                        }, n.onerror = function() {
                            var e = new FormData;
                            e.append("file", o), l["a"].post(t.api + "/readSourceFile", e, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                if (e.data.isSuccess) {
                                    var o = [];
                                    e.data.data.forEach((function(e) {
                                        try {
                                            var t = JSON.parse(e);
                                            Array.isArray(t) && (o = o.concat(t))
                                        } catch (n) {}
                                    })), o.length ? t.comfirmImport(o) : t.$message.error("替换规则文件错误")
                                }
                            }), (function(e) {
                                t.$message.error("读取替换规则文件内容失败 " + (e && e.toString()))
                            }))
                        }, n.readAsText(o), this.$refs.fileRef.value = null
                    },
                    comfirmImport: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条替换规则吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/saveReplaceRules", e).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("导入替换规则成功"), t.$root.$children[0].loadReplaceRules(!0))
                                        }), (function(e) {
                                            t.$message.error("导入替换规则失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    }
                }
            },
            g = m,
            b = (require("./utils/app/cd2b.js"), require("2877")),
            k = Object(b["a"])(g, d, p, !1, null, "44eeac3f", null),
            w = k.exports,
            v = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "替换规则",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-form", {
                    attrs: {
                        model: e.ruleForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "名称"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.name,
                        callback: function(t) {
                            e.$set(e.ruleForm, "name", t)
                        },
                        expression: "ruleForm.name"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "规则"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.pattern,
                        callback: function(t) {
                            e.$set(e.ruleForm, "pattern", t)
                        },
                        expression: "ruleForm.pattern"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换为"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.replacement,
                        callback: function(t) {
                            e.$set(e.ruleForm, "replacement", t)
                        },
                        expression: "ruleForm.replacement"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "替换范围"
                    }
                }, [t("el-input", {
                    model: {
                        value: e.ruleForm.scope,
                        callback: function(t) {
                            e.$set(e.ruleForm, "scope", t)
                        },
                        expression: "ruleForm.scope"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isRegex,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isRegex", t)
                        },
                        expression: "ruleForm.isRegex"
                    }
                }, [e._v("使用正则表达式")]), t("el-checkbox", {
                    model: {
                        value: e.ruleForm.isEnabled,
                        callback: function(t) {
                            e.$set(e.ruleForm, "isEnabled", t)
                        },
                        expression: "ruleForm.isEnabled"
                    }
                }, [e._v("是否启用")])], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("确 定")])], 1)], 1) : e._e()
            },
            S = [],
            A = require("./utils/app/bfff.js"),
            x = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "ReplaceRuleForm",
                data: function() {
                    return {
                        ruleForm: Object(i["a"])({}, A["d"])
                    }
                },
                props: ["show", "rule", "isAdd"],
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                watch: {
                    show: function(e) {
                        e && (this.ruleForm = this.rule || Object(i["a"])({}, A["d"]))
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    save: function() {
                        var e = this;
                        if (this.ruleForm.name)
                            if (this.ruleForm.pattern)
                                if (this.ruleForm.scope) {
                                    if (this.isAdd) {
                                        var t = this.$store.state.filterRules.find((function(t) {
                                            return t.name === e.ruleForm.name
                                        }));
                                        if (t) return void this.$message.error("规则名不能重复")
                                    }
                                    var o = Object(i["a"])({}, this.ruleForm);
                                    l["a"].post("/saveReplaceRule", o).then((function(t) {
                                        t.data.isSuccess && (e.$message.success((e.isAdd ? "新增" : "编辑") + "替换规则成功"), e.$root.$children[0].loadReplaceRules(!0), e.cancel())
                                    }), (function(t) {
                                        e.$message.error((e.isAdd ? "新增" : "编辑") + "替换规则失败 " + (t && t.toString()))
                                    }))
                                } else this.$message.error("替换范围不能为空");
                        else this.$message.error("规则不能为空");
                        else this.$message.error("规则名不能为空")
                    }
                }
            },
            B = x,
            C = (require("./utils/app/9308.js"), Object(b["a"])(B, v, S, !1, null, "f3156612", null)),
            y = C.exports,
            O = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "关注公众号【假装大佬】",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-image", {
                    staticClass: "qrcode-img",
                    attrs: {
                        src: require("./utils/app/3383.js"),
                        fit: "cover",
                        lazy: ""
                    }
                })], 1)
            },
            I = [],
            T = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "ReplaceRuleForm",
                data: function() {
                    return {}
                },
                props: ["show"],
                computed: Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])),
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    }
                }
            },
            j = T,
            L = (require("./utils/app/c1ed.js"), Object(b["a"])(j, O, I, !1, null, "52ffc46f", null)),
            $ = L.exports,
            R = (require("841c"), function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书架管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title flex-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书架管理 ")]), t("span", {
                    staticClass: "title-center"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "输入关键字搜索"
                    },
                    model: {
                        value: e.search,
                        callback: function(t) {
                            e.search = t
                        },
                        expression: "search"
                    }
                })], 1), t("span", {
                    directives: [{
                        name: "show",
                        rawName: "v-show",
                        value: !e.$store.state.miniInterface,
                        expression: "!$store.state.miniInterface"
                    }],
                    staticClass: "action-zone"
                }, [t("span", {
                    staticClass: "float-right small-tip"
                }, [e._v("❗️只能缓存文本内容")])])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.manageBookSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        selectable: e.isBookSelectable,
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        label: "书名名",
                        "min-width": "100",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showBookInfo(o.row)
                                    }
                                }
                            }, [e._v(e._s(o.row.name))])]
                        }
                    }], null, !1, 4264428119)
                }), t("el-table-column", {
                    attrs: {
                        property: "author",
                        label: "作者",
                        sortable: "custom",
                        "min-width": "100"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "group",
                        label: "分组",
                        "min-width": "120",
                        filters: e.bookGroupFilters,
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(t) {
                            return [e._v(" " + e._s(e.renderBookGroup(t.row)) + " ")]
                        }
                    }], null, !1, 1549071982)
                }), t("el-table-column", {
                    attrs: {
                        label: "章节",
                        "min-width": "120"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("span", [e._v("共 " + e._s(o.row.totalChapterNum) + " 章")]), t("br"), "loc_book" !== o.row.origin ? t("span", [e._v(" 服务器缓存： " + e._s(o.row.cachedChapterCount || 0) + " 章 "), t("br")]) : e._e(), t("span", [e._v("浏览器缓存： " + e._s(o.row.localCacheCount) + " 章")])]
                        }
                    }], null, !1, 4230467245)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editBook(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), t("el-button", {
                                staticClass: "text-button",
                                staticStyle: {
                                    "margin-left": "0"
                                },
                                attrs: {
                                    size: "medium",
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.setBookGroup(o.row)
                                    }
                                }
                            }, [e._v("分组")]), t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.cacheBook(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e.isCaching(o.row) ? t("span", [t("i", {
                                staticClass: "el-icon-loading"
                            }), e._v(" 缓存中 ")]) : t("span", [e._v(" 缓存"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })])]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, ["loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookOnServer"
                                }
                            }, [e._v("服务器缓存")]) : e._e(), "loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookSSE"
                                }
                            }, [e._v("缓存到服务器")]) : e._e(), t("el-dropdown-item", {
                                attrs: {
                                    command: "cacheBookLocal"
                                }
                            }, [e._v("缓存到浏览器")]), "loc_book" !== o.row.origin ? t("el-dropdown-item", {
                                attrs: {
                                    command: "deleteBookCache"
                                }
                            }, [e._v("删除服务器缓存")]) : e._e(), t("el-dropdown-item", {
                                attrs: {
                                    command: "deleteBookLocalCache"
                                }
                            }, [e._v("删除浏览器缓存")])], 1)], 1), t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.exportBook(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e._v(" 导出"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, [t("el-dropdown-item", {
                                attrs: {
                                    command: "txt"
                                }
                            }, [e._v("导出为TXT")]), t("el-dropdown-item", {
                                attrs: {
                                    command: "epub"
                                }
                            }, [e._v("导出为Epub")])], 1)], 1)]
                        }
                    }], null, !1, 675343596)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.multiOperate
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 操作 "), e.manageBookSelection.length ? t("span", [e._v(" (" + e._s(e.manageBookSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookList"
                    }
                }, [e._v("批量删除")]), t("el-dropdown-item", {
                    attrs: {
                        command: "addBookGroupMulti"
                    }
                }, [e._v("添加分组")]), t("el-dropdown-item", {
                    attrs: {
                        command: "removeBookGroupMulti"
                    }
                }, [e._v("删除分组")])], 1)], 1), t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.cacheBookMulti
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 缓存 "), e.manageBookSelection.length ? t("span", [e._v(" (" + e._s(e.manageBookSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookOnServer"
                    }
                }, [e._v("服务器缓存")]), t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookSSE"
                    }
                }, [e._v("缓存到服务器")]), t("el-dropdown-item", {
                    attrs: {
                        command: "cacheBookLocal"
                    }
                }, [e._v("缓存到浏览器")]), t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookCache"
                    }
                }, [e._v("删除服务器缓存")]), t("el-dropdown-item", {
                    attrs: {
                        command: "deleteBookLocalCache"
                    }
                }, [e._v("删除浏览器缓存")])], 1)], 1), e.multiOperating ? t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancelMulti
                    }
                }, [e._v("取消批量操作")]) : e._e()], 1), t("div", {
                    staticClass: "source-pagination"
                }, [t("el-pagination", {
                    attrs: {
                        "current-page": e.pagination.page,
                        "page-sizes": [25, 50, 100, 200, 300, 400, e.filterList.length],
                        "page-size": e.pagination.size,
                        layout: "total, sizes, prev, pager, next",
                        total: e.filterList.length,
                        "pager-count": e.$store.state.miniInterface ? 5 : 7
                    },
                    on: {
                        "update:currentPage": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:current-page": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:pageSize": function(t) {
                            return e.$set(e.pagination, "size", t)
                        },
                        "update:page-size": function(t) {
                            return e.$set(e.pagination, "size", t)
                        }
                    }
                })], 1)])]) : e._e()
            }),
            F = [],
            U = (require("4de4"), require("c740"), require("caad"), require("a15b"), require("fb6a"), require("4e82"), require("a434"), require("910d"), require("2532"), require("./utils/app/7513.js")),
            D = require("f537"),
            E = o.n(D),
            M = require("30b5"),
            N = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookManage",
                data: function() {
                    return {
                        bookList: [],
                        manageBookSelection: [],
                        search: "",
                        pagination: {
                            page: 1,
                            size: 25
                        },
                        sortable: {
                            prop: "",
                            order: null
                        },
                        multiOperating: !1
                    }
                },
                props: ["show"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])), {}, {
                    bookGroupList: function() {
                        return this.$store.state.bookGroupList.filter((function(e) {
                            return e.groupId > 0
                        }))
                    },
                    bookGroupFilters: function() {
                        return [{
                            text: "未分组",
                            value: 0
                        }].concat(this.bookGroupList.map((function(e) {
                            return {
                                text: e.groupName,
                                value: e.groupId
                            }
                        })))
                    },
                    cachingBookList: {
                        get: function() {
                            return this.$store.state.cachingBookList
                        },
                        set: function(e) {
                            this.$store.commit("setCachingBookList", e)
                        }
                    },
                    cachingBookMap: function() {
                        var e = {};
                        return this.cachingBookList.map((function(t) {
                            e[t.bookUrl] = !0
                        })), e
                    },
                    filterList: function() {
                        var e = this;
                        return this.bookList.filter((function(t) {
                            return !e.search || t.name.toLowerCase().includes(e.search.toLowerCase())
                        }))
                    },
                    sortList: function() {
                        var e = this;
                        if (!this.sortable.prop || !this.sortable.order) return this.filterList;
                        var t = [].concat(this.filterList);
                        return t.sort((function(t, o) {
                            if ("ascending" !== e.sortable.order) {
                                var n = t;
                                t = o, o = n
                            }
                            return t[e.sortable.prop] > o[e.sortable.prop] ? 1 : t[e.sortable.prop] < o[e.sortable.prop] ? -1 : 0
                        })), t
                    },
                    showList: function() {
                        var e = (this.pagination.page - 1) * this.pagination.size;
                        return e > this.sortList.length ? [] : this.sortList.slice(e, Math.min(e + this.pagination.size, this.sortList.length))
                    }
                }),
                created: function() {
                    window.cacheEventSource = window.cacheEventSource || {}, window.cacheRequestHandle = window.cacheRequestHandle || {}, window.bookManageComp = this
                },
                watch: {
                    show: function(e) {
                        e ? this.loadBookCacheInfo() : this.manageBookSelection = []
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            default:
                                return o
                        }
                    },
                    isBookSelectable: function() {
                        return !0
                    },
                    sortChange: function(e) {
                        var t = e.prop,
                            o = e.order;
                        this.sortable = {
                            prop: t,
                            order: o
                        }
                    },
                    loadBookCacheInfo: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.abrupt("return", l["a"].get(e.api + "/getShelfBookWithCacheInfo").then((function(t) {
                                            t.data.isSuccess && e.computeCachedCata(t.data.data).then((function(t) {
                                                e.bookList = t
                                            }))
                                        }), (function(t) {
                                            e.$message.error("获取书架信息失败 " + (t && t.toString()))
                                        })));
                                    case 1:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    deleteBookList: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.manageBookSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的书籍"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的书籍吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/deleteBooks", e.manageBookSelection).then((function(t) {
                                            t.data.isSuccess && (e.manageBookSelection = [], e.$message.success("删除书籍成功"), e.loadBookCacheInfo(), e.$root.$children[0].loadBookShelf())
                                        }), (function(t) {
                                            e.$message.error("删除书籍失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    addBookGroupMulti: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.abrupt("return", e.operateBookGroupMulti(!0));
                                    case 1:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    removeBookGroupMulti: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.abrupt("return", e.operateBookGroupMulti());
                                    case 1:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    operateBookGroupMulti: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n, r, a, i, c, u;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (n = e ? "添加" : "移除", t.manageBookSelection.length) {
                                            o.next = 4;
                                            break
                                        }
                                        return t.$message.error("请选择需要" + n + "分组的书籍"), o.abrupt("return");
                                    case 4:
                                        return r = {
                                            groupId: ""
                                        }, a = [{
                                            name: "groupId",
                                            label: n + "分组",
                                            type: "select",
                                            placeholder: "请选择分组",
                                            multiple: !1,
                                            options: t.bookGroupList.map((function(e) {
                                                return {
                                                    label: e.groupName,
                                                    value: e.groupId
                                                }
                                            }))
                                        }], o.next = 8, t.$msgbox({
                                            title: e ? "批量添加分组" : "批量删除分组",
                                            message: t.renderForm(e ? "addBookGroupMulti" : "removeBookGroupMulti", r, a, (function(e) {
                                                return r.groupId = 0 | e.groupId
                                            })),
                                            showCancelButton: !0,
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消"
                                        })["catch"]((function(e) {
                                            return "close" === e && "close"
                                        }));
                                    case 8:
                                        if (i = o.sent, "confirm" === i) {
                                            o.next = 11;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 11:
                                        if (c = t.bookGroupList.find((function(e) {
                                                return e.groupId === r.groupId
                                            })), c) {
                                            o.next = 15;
                                            break
                                        }
                                        return t.$message.error("请选择分组"), o.abrupt("return");
                                    case 15:
                                        return o.next = 17, t.$confirm(e ? "确认要将所选择的书籍添加到".concat(c.groupName, "分组吗?") : "确认要将所选择的书籍从".concat(c.groupName, "分组中移除吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 17:
                                        if (u = o.sent, u) {
                                            o.next = 20;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 20:
                                        l["a"].post(t.api + (e ? "/addBookGroupMulti" : "/removeBookGroupMulti"), {
                                            groupId: c.groupId,
                                            bookList: t.manageBookSelection
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("操作成功"), t.loadBookCacheInfo(), t.$root.$children[0].loadBookShelf())
                                        }), (function(e) {
                                            t.$message.error("操作失败 " + (e && e.toString()))
                                        }));
                                    case 21:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    renderBookGroup: function(e) {
                        var t = [];
                        return this.$store.state.bookGroupList.forEach((function(o) {
                            o.groupId > 0 && E.a.fromNumber(o.groupId).and(e.group).greaterThan(0) && t.push(o.groupName)
                        })), t.length || t.push("未分组"), t.join(" ")
                    },
                    filterHandler: function(e, t, o) {
                        var n = o["property"];
                        return t[n] === e
                    },
                    showBookInfo: function(e) {
                        u["a"].$emit("showBookInfoDialog", e)
                    },
                    editBook: function(e) {
                        var t = this;
                        u["a"].$emit("editBook", e, !1, (function() {
                            t.loadBookCacheInfo()
                        }))
                    },
                    setBookGroup: function(e) {
                        this.$store.commit("setShowBookInfo", e), u["a"].$emit("showBookGroupDialog", !0)
                    },
                    isCaching: function(e) {
                        return !!this.cachingBookMap[e.bookUrl] || (!(!window.cacheEventSource || !window.cacheEventSource[e.bookUrl]) || !(!window.cacheRequestHandle || !window.cacheRequestHandle[e.bookUrl]))
                    },
                    cacheBook: function(e, t) {
                        this[t](e)
                    },
                    cancelMulti: function() {
                        if (this.multiOperating) {
                            if (this.multiCachingHandler.cancel(), "cacheBookSSE" === this.multiOperating || "cacheBookLocal" === this.multiOperating)
                                for (var e = 0; e < this.lastSelection.length; e++) this.isCaching(this.lastSelection[e]) && this[this.multiOperating](this.lastSelection[e]);
                            this.multiOperating = !1, this.lastSelection = [], this.$message.info("批量操作已取消")
                        }
                    },
                    multiOperate: function(e) {
                        this.manageBookSelection.length ? this[e]() : this.$message.error("请选择需要操作的书籍")
                    },
                    cacheBookMulti: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n, r, a, i;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (!t.multiOperating) {
                                            o.next = 3;
                                            break
                                        }
                                        return t.$message.error("正在批量操作，请取消后再试"), o.abrupt("return");
                                    case 3:
                                        if (t.manageBookSelection.length) {
                                            o.next = 6;
                                            break
                                        }
                                        return t.$message.error("请选择需要操作的书籍"), o.abrupt("return");
                                    case 6:
                                        if ("deleteBookCache" !== e && "deleteBookLocalCache" !== e) {
                                            o.next = 12;
                                            break
                                        }
                                        return o.next = 9, t.$confirm("确认要批量删除".concat("deleteBookCache" === e ? "服务器上" : "浏览器中", "所选书籍的缓存章节吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 9:
                                        if (n = o.sent, n) {
                                            o.next = 12;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 12:
                                        if ("cacheBookOnServer" !== e) {
                                            o.next = 20;
                                            break
                                        }
                                        return o.next = 15, t.$confirm("确认要在服务器上批量缓存所选书籍的章节内容吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 15:
                                        if (r = o.sent, r) {
                                            o.next = 18;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 18:
                                        return t.cacheBookOnServer(t.manageBookSelection), o.abrupt("return");
                                    case 20:
                                        t.multiOperating = e, t.lastSelection = [].concat(t.manageBookSelection), t.multiCachingHandler = Object(U["a"])(2, (function(e) {
                                            e.isEnd() && (t.multiOperating = !1, t.lastSelection = [], t.$message.success("批量操作完成"))
                                        })), a = Object(s["a"])().mark((function o(n) {
                                            return Object(s["a"])().wrap((function(o) {
                                                while (1) switch (o.prev = o.next) {
                                                    case 0:
                                                        t.multiCachingHandler((function() {
                                                            return t[e](t.manageBookSelection[n])
                                                        }));
                                                    case 1:
                                                    case "end":
                                                        return o.stop()
                                                }
                                            }), o)
                                        })), i = 0;
                                    case 25:
                                        if (!(i < t.manageBookSelection.length)) {
                                            o.next = 30;
                                            break
                                        }
                                        return o.delegateYield(a(i), "t0", 27);
                                    case 27:
                                        i++, o.next = 25;
                                        break;
                                    case 30:
                                        t.$message.info("批量操作未完成/取消之前请勿关闭当前页面");
                                    case 31:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    cacheBookSSE: function(e) {
                        var t = this;
                        return new Promise((function(o, n) {
                            var r = function() {
                                try {
                                    window.cacheEventSource[e.bookUrl] && window.cacheEventSource[e.bookUrl].readyState !== window.cacheEventSource[e.bookUrl].CLOSED && window.cacheEventSource[e.bookUrl].close(), window.cacheEventSource[e.bookUrl] = null, delete window.cacheEventSource[e.bookUrl];
                                    var o = t.cachingBookList.findIndex((function(t) {
                                        return t.bookUrl === e.bookUrl
                                    }));
                                    t.cachingBookList.splice(o, 1), t.cachingBookList = [].concat(t.cachingBookList)
                                } catch (n) {}
                            };
                            if (t.isCaching(e)) return t.$message.info("已取消缓存"), n("已取消缓存"), void(window.cacheEventSource[e.bookUrl] && r());
                            var a = {
                                    url: e.bookUrl,
                                    refresh: 0
                                },
                                s = M(t.api + "/cacheBookSSE", a);
                            r(), t.cachingBookList = t.cachingBookList.concat([e]), window.cacheEventSource[e.bookUrl] = new EventSource(s, {
                                withCredentials: !0
                            }), window.cacheEventSource[e.bookUrl].addEventListener("error", (function(e) {
                                r();
                                try {
                                    if (e.data) {
                                        var o = JSON.parse(e.data);
                                        o && o.errorMsg && (t.$message.error(o.errorMsg), n(o.errorMsg))
                                    }
                                } catch (a) {}
                            })), window.cacheEventSource[e.bookUrl].addEventListener("end", (function(n) {
                                t.$message.info(e.name + "缓存到服务器完成"), o(e.name + "缓存到服务器完成"), r();
                                try {
                                    n.data
                                } catch (a) {}
                            })), window.cacheEventSource[e.bookUrl].addEventListener("message", (function(o) {
                                try {
                                    if (o.data) {
                                        var n = JSON.parse(o.data);
                                        if (n && n.cachedCount) {
                                            var r = t.bookList.findIndex((function(t) {
                                                return t.bookUrl === e.bookUrl
                                            }));
                                            t.$set(t.bookList, r, Object(i["a"])(Object(i["a"])({}, e), {}, {
                                                cachedChapterCount: n.cachedCount
                                            }))
                                        }
                                    }
                                } catch (a) {}
                            }))
                        }))
                    },
                    cacheBookLocal: function(e) {
                        var t = this;
                        return new Promise((function(o, n) {
                            if (t.isCaching(e)) return t.$message.info("已取消缓存"), n("已取消缓存"), void(window.cacheRequestHandle[e.bookUrl] && (window.cacheRequestHandle[e.bookUrl].cancel(), window.cacheRequestHandle[e.bookUrl] = null, delete window.cacheRequestHandle[e.bookUrl]));
                            var r = !1,
                                a = function() {
                                    t.computeCachedCata([e]).then((function(o) {
                                        r = !1;
                                        var n = t.bookList.findIndex((function(t) {
                                            return t.bookUrl === e.bookUrl
                                        }));
                                        t.$set(t.bookList, n, Object(i["a"])(Object(i["a"])({}, e), {}, {
                                            localCacheCount: o[0].localCacheCount
                                        }))
                                    }))
                                };
                            window.cacheRequestHandle[e.bookUrl] = Object(U["a"])(2, (function(e) {
                                r || (r = !0, a()), e.isEnd() && (t.$message.success("缓存到浏览器完成"), o("缓存到浏览器完成"), a())
                            }));
                            for (var s = function(o) {
                                    window.cacheRequestHandle[e.bookUrl]((function() {
                                        return t.$root.$children[0].getBookContent(o, {
                                            timeout: 1e3 * t.$store.getters.config.chapterRequestTimeout,
                                            silent: !0
                                        }, !1, !0, e)
                                    }))
                                }, c = 0; c < e.totalChapterNum; c++) s(c)
                        }))
                    },
                    cacheBookOnServer: function(e) {
                        var t = this;
                        e = Array.isArray(e) ? e : [e], l["a"].post(this.api + "/cacheBookOnServer", {
                            bookUrlList: e.map((function(e) {
                                return e.bookUrl
                            }))
                        }).then((function(e) {
                            e.data.isSuccess && t.$message.success("提交缓存任务成功")
                        }), (function(e) {
                            t.$message.error("提交缓存任务失败 " + (e && e.toString()))
                        }))
                    },
                    exportBook: function(e, t) {
                        var o = M(this.api + "/exportBook", {
                            url: e.bookUrl,
                            isEpub: "epub" === t ? 1 : 0,
                            accessToken: this.$store.state.token
                        });
                        window.open(o, "__blank")
                    },
                    computeCachedCata: function(e, t) {
                        var o = {};
                        return e.forEach((function(e) {
                            o[e.bookUrl] = {
                                key: "localCache@" + e.name + "_" + e.author + "@" + e.bookUrl + "@chapterContent-",
                                map: {}
                            }
                        })), window.$cacheStorage.iterate((function(e, t) {
                            for (var n in o)
                                if (t.startsWith(o[n].key)) {
                                    try {
                                        var r = parseInt(t.replace(o[n].key, ""));
                                        o[n].map[r] = !0
                                    } catch (a) {}
                                    break
                                }
                        })).then((function() {
                            return t ? o : e.map((function(e) {
                                var t = o[e.bookUrl].map;
                                return e.localCacheCount = Object.keys(t).length, e
                            }))
                        }))["catch"]((function() {
                            return t ? o : e.map((function(e) {
                                return e.localCacheCount = 0, e
                            }))
                        }))
                    },
                    deleteBookCache: function(e, t) {
                        var o = this;
                        return new Promise(function() {
                            var n = Object(c["a"])(Object(s["a"])().mark((function n(r, a) {
                                var i;
                                return Object(s["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t) {
                                                n.next = 7;
                                                break
                                            }
                                            return n.next = 3, o.$confirm("确认要删除服务器上《".concat(e.name, "》的缓存章节吗?"), "提示", {
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消",
                                                type: "warning"
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 3:
                                            if (i = n.sent, i) {
                                                n.next = 7;
                                                break
                                            }
                                            return a("canceled"), n.abrupt("return");
                                        case 7:
                                            l["a"].post(o.api + "/deleteBookCache", {
                                                bookUrl: e.bookUrl
                                            }).then((function(e) {
                                                e.data.isSuccess && (o.$message.success("删除服务器缓存成功"), o.loadBookCacheInfo(), r(""))
                                            }), (function(e) {
                                                o.$message.error("删除服务器缓存失败 " + (e && e.toString())), a(e)
                                            }));
                                        case 8:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })));
                            return function(e, t) {
                                return n.apply(this, arguments)
                            }
                        }())
                    },
                    deleteBookLocalCache: function(e, t) {
                        var o = this;
                        return new Promise(function() {
                            var n = Object(c["a"])(Object(s["a"])().mark((function n(r, a) {
                                var i;
                                return Object(s["a"])().wrap((function(n) {
                                    while (1) switch (n.prev = n.next) {
                                        case 0:
                                            if (!t) {
                                                n.next = 7;
                                                break
                                            }
                                            return n.next = 3, o.$confirm("确认要删除浏览器中《".concat(e.name, "》的缓存章节吗?"), "提示", {
                                                confirmButtonText: "确定",
                                                cancelButtonText: "取消",
                                                type: "warning"
                                            })["catch"]((function() {
                                                return !1
                                            }));
                                        case 3:
                                            if (i = n.sent, i) {
                                                n.next = 7;
                                                break
                                            }
                                            return a("canceled"), n.abrupt("return");
                                        case 7:
                                            o.computeCachedCata([e], !0).then((function(t) {
                                                var o = [];
                                                for (var n in t[e.bookUrl].map) {
                                                    var r = t[e.bookUrl].key + n;
                                                    o.push(window.$cacheStorage.removeItem(r))
                                                }
                                                return Promise.all(o)
                                            })).then((function() {
                                                o.$message.success("删除浏览器缓存成功"), r("删除浏览器缓存成功"), o.computeCachedCata([].concat(o.bookList)).then((function(e) {
                                                    o.bookList = e
                                                }))
                                            }));
                                        case 8:
                                        case "end":
                                            return n.stop()
                                    }
                                }), n)
                            })));
                            return function(e, t) {
                                return n.apply(this, arguments)
                            }
                        }())
                    }
                }
            },
            _ = N,
            P = (require("./utils/app/6a04.js"), Object(b["a"])(_, R, F, !1, null, "99ff4e1a", null)),
            G = P.exports,
            W = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍信息",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书籍信息 "), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.editBook
                    }
                }, [e._v("编辑")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.showBookConfig
                    }
                }, [e._v("设置")]) : e._e(), e.isInShelf && "index" === e.$route.name ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.deleteBook
                    }
                }, [e._v("移出书架")]) : e._e(), e.isInShelf ? t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }, [e._v("修改封面")]) : e.isInShelf ? e._e() : t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.saveBook(e.showBookInfo, !0)
                        }
                    }
                }, [e._v("加入书架")])])]), e.show ? t("div", {
                    staticClass: "book-info-container"
                }, [t("div", {
                    staticClass: "book-cover"
                }, [t("div", {
                    staticClass: "book-cover-bg",
                    style: e.bookCoverBgStyle
                }), t("div", {
                    staticClass: "book-cover-bg-image"
                }, [t("img", {
                    directives: [{
                        name: "lazy",
                        rawName: "v-lazy",
                        value: e.getCover(e.getBookCoverUrl(e.showBookInfo)),
                        expression: "getCover(getBookCoverUrl(showBookInfo))"
                    }],
                    key: e.showBookInfo.name,
                    attrs: {
                        alt: ""
                    },
                    on: {
                        click: e.triggerBookCoverRefClick
                    }
                }), t("input", {
                    ref: "bookCoverRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        accept: "image/jpg, image/png, image/jpeg"
                    },
                    on: {
                        change: e.onCoverFileChange
                    }
                })])]), t("div", {
                    staticClass: "book-name"
                }, [e._v(e._s(e.showBookInfo.name))]), t("BookKind", {
                    staticClass: "text-center",
                    attrs: {
                        book: e.showBookInfo
                    }
                }), t("div", {
                    staticClass: "book-props"
                }, [t("div", {
                    staticClass: "book-prop book-author"
                }, [e._v(" 作者： " + e._s(e.showBookInfo.author || "未知") + " ")]), t("div", {
                    staticClass: "book-prop book-origin"
                }, [e._v(" 来源： " + e._s(e.displayOriginName(e.showBookInfo.origin)) + " "), "loc_book" === e.showBookInfo.origin ? t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.refreshLocalBook(e.showBookInfo)
                        }
                    }
                }, [e._v("更新")]) : e._e()], 1), t("div", {
                    staticClass: "book-prop book-latest"
                }, [t("span", {
                    staticClass: "latest-title"
                }, [e._v("最新： " + e._s(e.showBookInfo.latestChapterTitle) + " ")]), e.isInShelf ? t("span", {
                    staticClass: "book-prop-btn"
                }, [e._v(" 追更 "), t("el-switch", {
                    attrs: {
                        "active-color": "#13ce66",
                        "inactive-color": "#ff4949",
                        "active-value": !0,
                        "inactive-value": !1
                    },
                    on: {
                        change: function(t) {
                            return e.toggleBookCanUpdate(e.showBookInfo)
                        }
                    },
                    model: {
                        value: e.showBookInfo.canUpdate,
                        callback: function(t) {
                            e.$set(e.showBookInfo, "canUpdate", t)
                        },
                        expression: "showBookInfo.canUpdate"
                    }
                })], 1) : e._e()]), e.showBookInfo.lastCheckError ? t("div", {
                    staticClass: "book-prop last-check-error"
                }, [e._v(" 错误： " + e._s(e.showBookInfo.lastCheckError) + " ")]) : e._e(), e.isInShelf ? t("div", {
                    staticClass: "book-prop book-group"
                }, [e._v(" 分组： " + e._s(e.displayGroupName(e.showBookInfo.group)) + " "), t("el-button", {
                    staticClass: "book-prop-btn",
                    attrs: {
                        type: "text"
                    },
                    on: {
                        click: function(t) {
                            return e.showSetBookGroup()
                        }
                    }
                }, [e._v("设置分组")])], 1) : e._e()]), t("div", {
                    staticClass: "book-intro",
                    domProps: {
                        innerHTML: e._s(e.renderBookIntro(e.showBookInfo))
                    }
                })], 1) : e._e()])
            },
            z = [],
            H = require("./utils/app/0fe7.js"),
            V = {
                components: {
                    BookKind: H["a"]
                },
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookInfo",
                data: function() {
                    return {}
                },
                props: ["show"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])), {}, {
                    bookCoverBgStyle: function() {
                        return {
                            backgroundImage: "url(".concat(this.getCover(this.getBookCoverUrl(this.showBookInfo), !0), ")")
                        }
                    },
                    bookSourceList: function() {
                        return this.$store.state.bookSourceList
                    },
                    showBookInfo: {
                        get: function() {
                            return this.$store.state.showBookInfo
                        },
                        set: function(e) {
                            this.$store.commit("setShowBookInfo", e)
                        }
                    },
                    isInShelf: function() {
                        var e = this;
                        return this.$store.getters.shelfBooks.find((function(t) {
                            return t.bookUrl === e.showBookInfo.bookUrl
                        }))
                    }
                }),
                watch: {
                    show: function(e) {}
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    displayGroupName: function(e) {
                        var t = [],
                            o = "";
                        return this.$store.state.bookGroupList.forEach((function(n) {
                            n.groupId > 0 && E.a.fromNumber(n.groupId).and(e).greaterThan(0) ? t.push(n.groupName) : -4 === n.groupId && (o = n.groupName)
                        })), t.join(",") || o || "未分组"
                    },
                    displayOriginName: function(e) {
                        return "loc_book" === e ? "本地" : (this.bookSourceList.find((function(t) {
                            return t.bookSourceUrl === e
                        })) || {}).bookSourceName || "未知书源"
                    },
                    renderBookIntro: function(e) {
                        var t = (e.intro || "暂无简介").split("\n");
                        return t.map((function(e) {
                            return "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;".concat(e.replace(/^\s+/g, ""), "</p>")
                        })).join("")
                    },
                    getBookCoverUrl: function(e) {
                        return e.customCoverUrl || e.coverUrl
                    },
                    triggerBookCoverRefClick: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (!e.isInShelf) {
                                            t.next = 7;
                                            break
                                        }
                                        return t.next = 3, e.$confirm("请选择操作?", "提示", {
                                            confirmButtonText: "上传封面",
                                            cancelButtonText: "选择其它书源",
                                            type: "warning",
                                            closeOnClickModal: !1,
                                            closeOnPressEscape: !1,
                                            distinguishCancelAndClose: !0
                                        })["catch"]((function(e) {
                                            return "close" === e && "close"
                                        }));
                                    case 3:
                                        if (o = t.sent, "close" !== o) {
                                            t.next = 6;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 6:
                                        o ? e.$refs.bookCoverRef.dispatchEvent(new MouseEvent("click")) : u["a"].$emit("showBookCoverDialog", e.showBookInfo);
                                    case 7:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    onCoverFileChange: function(e) {
                        var t = this;
                        if (e.target && e.target.files && e.target.files.length) {
                            var o = e.target.files && e.target.files[0],
                                n = new FormData;
                            n.append("file", o), n.append("type", "covers"), l["a"].post(this.api + "/uploadFile", n, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                if (e.data.isSuccess) {
                                    if (!e.data.data.length) return void t.$message.error("上传文件失败");
                                    t.showBookInfo.customCoverUrl = e.data.data[0], t.saveBook(t.showBookInfo)
                                }
                            }), (function(e) {
                                t.$message.error("上传文件失败 " + (e && e.toString()))
                            }))
                        }
                    },
                    refreshLocalBook: function(e) {
                        var t = this;
                        l["a"].post(this.api + "/refreshLocalBook", {
                            bookUrl: e.bookUrl
                        }).then((function(e) {
                            e.data.isSuccess && (t.$message.success("更新成功"), t.showBookInfo = e.data.data, t.$store.commit("updateShelfBook", e.data.data))
                        }), (function(e) {
                            t.$message.error("更新失败" + (e && e.toString()))
                        }))
                    },
                    toggleBookCanUpdate: function(e) {
                        return this.saveBook(e)
                    },
                    saveBook: function(e, t) {
                        var o = this;
                        return l["a"].post(this.api + "/saveBook", e).then((function(e) {
                            e.data.isSuccess && (o.$message.success(t ? "加入书架成功" : "操作成功"), o.showBookInfo = e.data.data, t ? o.$root.$children[0].loadBookShelf(!0) : o.$store.commit("updateShelfBook", e.data.data))
                        }), (function(e) {
                            o.$message.error((t ? "加入书架失败" : "操作失败") + (e && e.toString()))
                        }))
                    },
                    deleteBook: function() {
                        this.$root.$children[0].deleteBook(this.showBookInfo)
                    },
                    editBook: function() {
                        var e = this;
                        u["a"].$emit("editBook", this.showBookInfo, !1, (function(t) {
                            e.showBookInfo = t
                        }))
                    },
                    showBookConfig: function() {
                        u["a"].$emit("showBookConfigDialog")
                    },
                    showSetBookGroup: function() {
                        u["a"].$emit("showBookGroupDialog", !0)
                    }
                }
            },
            Q = V,
            K = (require("./utils/app/6fec.js"), require("./utils/app/79a1.js"), Object(b["a"])(Q, W, z, !1, null, "1a462b56", null)),
            J = K.exports,
            q = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "用户管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title flex-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("用户管理 ")]), t("span", {
                    staticClass: "title-center"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "输入关键字搜索"
                    },
                    model: {
                        value: e.search,
                        callback: function(t) {
                            e.search = t
                        },
                        expression: "search"
                    }
                })], 1)]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.manageUserSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        selectable: e.isUserSelectable,
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "username",
                        label: "用户名",
                        "min-width": "100",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastLoginAt",
                        label: "上次登录",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "createdAt",
                        label: "注册时间",
                        sortable: "custom",
                        formatter: e.formatTableField,
                        "min-width": "120"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "enableWebdav",
                        label: "WebDAV",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserWebdav(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableWebdav,
                                    callback: function(t) {
                                        e.$set(o.row, "enableWebdav", t)
                                    },
                                    expression: "scope.row.enableWebdav"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 2358517604)
                }), t("el-table-column", {
                    attrs: {
                        property: "enableLocalStore",
                        label: "书仓",
                        "min-width": "80",
                        filters: [{
                            text: "开启",
                            value: !0
                        }, {
                            text: "关闭",
                            value: !1
                        }],
                        "filter-method": e.filterHandler
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" !== o.row.userNS ? t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleUserLocalStore(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.enableLocalStore,
                                    callback: function(t) {
                                        e.$set(o.row, "enableLocalStore", t)
                                    },
                                    expression: "scope.row.enableLocalStore"
                                }
                            }) : e._e()]
                        }
                    }], null, !1, 4075233220)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return ["default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editUser(o.row)
                                    }
                                }
                            }, [e._v("修改")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.resetPassword(o.row)
                                    }
                                }
                            }, [e._v("重置密码")]) : e._e(), "default" != o.row.userNS ? t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.setAsDefaultBookSources(o.row)
                                    }
                                }
                            }, [e._v("设为默认书源")]) : e._e()]
                        }
                    }], null, !1, 1517798773)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteUserList
                    }
                }, [e._v("批量删除"), e.manageUserSelection.length ? t("span", [e._v(" (" + e._s(e.manageUserSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteUserBookSource
                    }
                }, [e._v("使用默认书源")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.clearInactiveUser()
                        }
                    }
                }, [e._v("清理")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.showAddUserDialog()
                        }
                    }
                }, [e._v("新增")])], 1), t("div", {
                    staticClass: "source-pagination"
                }, [t("el-pagination", {
                    attrs: {
                        "current-page": e.pagination.page,
                        "page-sizes": [25, 50, 100, 200, 300, 400, e.filterList.length],
                        "page-size": e.pagination.size,
                        layout: "total, sizes, prev, pager, next",
                        total: e.filterList.length,
                        "pager-count": e.$store.state.miniInterface ? 5 : 7
                    },
                    on: {
                        "update:currentPage": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:current-page": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:pageSize": function(t) {
                            return e.$set(e.pagination, "size", t)
                        },
                        "update:page-size": function(t) {
                            return e.$set(e.pagination, "size", t)
                        }
                    }
                })], 1)])]) : e._e()
            },
            Y = [],
            Z = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "UserManage",
                data: function() {
                    return {
                        manageUserSelection: [],
                        search: "",
                        pagination: {
                            page: 1,
                            size: 25
                        },
                        sortable: {
                            prop: "",
                            order: null
                        }
                    }
                },
                props: ["show"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])), {}, {
                    userList: {
                        get: function() {
                            return this.$store.state.userList
                        },
                        set: function(e) {
                            this.$store.commit("setUserList", e)
                        }
                    },
                    filterList: function() {
                        var e = this;
                        return this.userList.filter((function(t) {
                            return "default" !== t.userNS && (!e.search || t.username.toLowerCase().includes(e.search.toLowerCase()))
                        }))
                    },
                    sortList: function() {
                        var e = this;
                        if (!this.sortable.prop || !this.sortable.order) return this.filterList;
                        var t = [].concat(this.filterList);
                        return t.sort((function(t, o) {
                            if ("ascending" !== e.sortable.order) {
                                var n = t;
                                t = o, o = n
                            }
                            return t[e.sortable.prop] > o[e.sortable.prop] ? 1 : t[e.sortable.prop] < o[e.sortable.prop] ? -1 : 0
                        })), t
                    },
                    showList: function() {
                        var e = (this.pagination.page - 1) * this.pagination.size;
                        return e > this.sortList.length ? [] : this.sortList.slice(e, Math.min(e + this.pagination.size, this.sortList.length))
                    }
                }),
                watch: {
                    show: function(e) {
                        e && (this.manageUserSelection = [])
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    sortChange: function(e) {
                        var t = e.prop,
                            o = e.order;
                        this.sortable = {
                            prop: t,
                            order: o
                        }
                    },
                    showAddUserDialog: function() {
                        u["a"].$emit("showUserFormDialog")
                    },
                    clearInactiveUser: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, e.$prompt("请输入需要清理的未登录天数", "清理不活跃用户", {
                                            inputValue: 31,
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return e ? !isNaN(parseInt(e)) || "天数必须是数字" : "天数不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (o = t.sent, o && o.value) {
                                            t.next = 5;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 5:
                                        l["a"].post(e.api + "/clearInactiveUsers", {
                                            inactiveDay: parseInt(o.value)
                                        }, {
                                            timeout: 0
                                        }).then((function(t) {
                                            t.data.isSuccess && (e.$message.success("清理不活跃用户成功"), e.userList = t.data.data.map((function(e) {
                                                return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                                    userNS: e.username
                                                })
                                            })))
                                        }), (function(t) {
                                            e.$message.error("清理不活跃用户失败 " + (t && t.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            case "createdAt":
                            case "lastLoginAt":
                            case "lastModified":
                                return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
                            case "size":
                                return e.isDirectory ? "" : Object(U["d"])(o);
                            default:
                                return o
                        }
                    },
                    isUserSelectable: function(e) {
                        return "default" !== e.userNS
                    },
                    deleteUserList: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.manageUserSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的用户"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的用户吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/deleteUsers", e.manageUserSelection.map((function(e) {
                                            return e.username
                                        })), {
                                            timeout: 0
                                        }).then((function(t) {
                                            t.data.isSuccess && (e.manageUserSelection = [], e.$message.success("删除用户成功"), e.userList = t.data.data.map((function(e) {
                                                return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                                    userNS: e.username
                                                })
                                            })))
                                        }), (function(t) {
                                            e.$message.error("删除用户失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    deleteUserBookSource: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.manageUserSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除书源的用户"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的用户书源吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/deleteUserBookSource", e.manageUserSelection.map((function(e) {
                                            return e.username
                                        }))).then((function(t) {
                                            t.data.isSuccess && (e.manageUserSelection = [], e.$message.success("操作成功"))
                                        }), (function(t) {
                                            e.$message.error("操作失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    toggleUserWebdav: function(e, t) {
                        var o = this;
                        l["a"].post(this.api + "/updateUser", {
                            username: e.username,
                            enableWebdav: t
                        }).then((function(e) {
                            e.data.isSuccess && (o.$message.success("修改成功"), o.userList = e.data.data.map((function(e) {
                                return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                    userNS: e.username
                                })
                            })))
                        }), (function(e) {
                            o.$message.error("修改失败 " + (e && e.toString()))
                        }))
                    },
                    toggleUserLocalStore: function(e, t) {
                        var o = this;
                        l["a"].post(this.api + "/updateUser", {
                            username: e.username,
                            enableLocalStore: t
                        }).then((function(e) {
                            e.data.isSuccess && (o.$message.success("修改成功"), o.userList = e.data.data.map((function(e) {
                                return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                    userNS: e.username
                                })
                            })))
                        }), (function(e) {
                            o.$message.error("修改失败 " + (e && e.toString()))
                        }))
                    },
                    editUser: function(e) {
                        u["a"].$emit("showUserFormDialog", e)
                    },
                    setAsDefaultBookSources: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要将用户".concat(e.username, "的书源设为默认书源（新用户有效）吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        return o.abrupt("return", l["a"].post(t.api + "/setAsDefaultBookSources", {
                                            username: e.username
                                        }).then((function(e) {
                                            e.data.isSuccess && t.$message.success("设置成功")
                                        }), (function(e) {
                                            t.$message.error("设置失败 " + (e && e.toString()))
                                        })));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    resetPassword: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$prompt("", "重置密码", {
                                            inputValue: "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "密码不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/resetPassword", {
                                            username: e.username,
                                            password: n.value
                                        }).then((function(e) {
                                            e.data.isSuccess && t.$message.success("重置密码成功")
                                        }), (function(e) {
                                            t.$message.error("重置密码失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    filterHandler: function(e, t, o) {
                        var n = o["property"];
                        return t[n] === e
                    }
                }
            },
            X = Z,
            ee = (require("./utils/app/6867.js"), Object(b["a"])(X, q, Y, !1, null, "33306aac", null)),
            te = ee.exports,
            oe = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isAdd ? "新增用户" : "修改用户",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-form", {
                    attrs: {
                        model: e.userForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "用户名"
                    }
                }, [t("el-input", {
                    attrs: {
                        autocomplete: "off",
                        readonly: !e.isAdd
                    },
                    model: {
                        value: e.userForm.username,
                        callback: function(t) {
                            e.$set(e.userForm, "username", t)
                        },
                        expression: "userForm.username"
                    }
                })], 1), e.isAdd ? t("el-form-item", {
                    attrs: {
                        label: "密码"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "password",
                        autocomplete: "off",
                        "show-password": ""
                    },
                    model: {
                        value: e.userForm.password,
                        callback: function(t) {
                            e.$set(e.userForm, "password", t)
                        },
                        expression: "userForm.password"
                    }
                })], 1) : e._e(), t("el-form-item", {
                    attrs: {
                        label: "书籍上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookLimit", t)
                        },
                        expression: "userForm.bookLimit"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "书源上限"
                    }
                }, [t("el-input-number", {
                    attrs: {
                        min: 1
                    },
                    model: {
                        value: e.userForm.bookSourceLimit,
                        callback: function(t) {
                            e.$set(e.userForm, "bookSourceLimit", t)
                        },
                        expression: "userForm.bookSourceLimit"
                    }
                })], 1), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableWebdav,
                        callback: function(t) {
                            e.$set(e.userForm, "enableWebdav", t)
                        },
                        expression: "userForm.enableWebdav"
                    }
                }, [e._v("启用webdav")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableLocalStore,
                        callback: function(t) {
                            e.$set(e.userForm, "enableLocalStore", t)
                        },
                        expression: "userForm.enableLocalStore"
                    }
                }, [e._v("启用书仓")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableBookSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableBookSource", t)
                        },
                        expression: "userForm.enableBookSource"
                    }
                }, [e._v("编辑书源")]), t("el-checkbox", {
                    model: {
                        value: e.userForm.enableRssSource,
                        callback: function(t) {
                            e.$set(e.userForm, "enableRssSource", t)
                        },
                        expression: "userForm.enableRssSource"
                    }
                }, [e._v("编辑RSS源")])], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("确 定")])], 1)], 1) : e._e()
            },
            ne = [],
            re = {
                username: "",
                password: "",
                bookLimit: 200,
                bookSourceLimit: 100,
                enableWebdav: !1,
                enableLocalStore: !1,
                enableBookSource: !0,
                enableRssSource: !0
            },
            ae = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "UserForm",
                data: function() {
                    return {
                        isAdd: !0,
                        userForm: Object(i["a"])({}, re)
                    }
                },
                props: ["show", "user"],
                computed: Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])),
                watch: {
                    show: function(e) {
                        e && (this.user && this.user.username ? (this.userForm = Object(i["a"])(Object(i["a"])({}, re), this.user), this.isAdd = !1) : (this.userForm = Object(i["a"])({}, re), this.isAdd = !0))
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    save: function() {
                        var e = this;
                        this.userForm.username ? !this.isAdd || this.userForm.password ? l["a"].post(this.api + (this.isAdd ? "/addUser" : "/updateUser"), this.userForm).then((function(t) {
                            if (t.data.isSuccess) {
                                e.$message.success("操作成功"), e.cancel();
                                var o = t.data.data.map((function(e) {
                                    return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                        userNS: e.username
                                    })
                                }));
                                e.$store.commit("setUserList", o)
                            }
                        }), (function(t) {
                            e.$message.error("操作失败 " + (t && t.toString()))
                        })) : this.$message.success("密码不能为空") : this.$message.success("用户名不能为空")
                    }
                }
            },
            ie = ae,
            se = Object(b["a"])(ie, oe, ne, !1, null, "7e126aa8", null),
            ce = se.exports,
            le = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.isShowBookGroupSettingDialog ? "设置分组" : "分组管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        },
                        opened: e.opened
                    }
                }, [t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    key: e.isShowBookGroupSettingDialog,
                    ref: "bookGroupTableRef",
                    attrs: {
                        data: e.showBookGroupList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.bookGroupSelection = t
                        }
                    }
                }, [e.isShowBookGroupSettingDialog ? t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25"
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "groupName",
                        label: "分组名",
                        "min-width": "100"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("div", {
                                staticClass: "drag-icon"
                            }, [t("i", {
                                staticClass: "el-icon-rank"
                            })]), t("span", [e._v(" " + e._s(e.displayBookGroupName(o.row)))])]
                        }
                    }], null, !1, 4111896031)
                }), e.isShowBookGroupSettingDialog ? e._e() : t("el-table-column", {
                    attrs: {
                        property: "show",
                        label: "显示",
                        "min-width": "80"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-switch", {
                                attrs: {
                                    "active-color": "#13ce66",
                                    "inactive-color": "#ff4949",
                                    "active-value": !0,
                                    "inactive-value": !1
                                },
                                on: {
                                    change: function(t) {
                                        return e.toggleBookGroupShow(o.row, t)
                                    }
                                },
                                model: {
                                    value: o.row.show,
                                    callback: function(t) {
                                        e.$set(o.row, "show", t)
                                    },
                                    expression: "scope.row.show"
                                }
                            })]
                        }
                    }], null, !1, 3235955515)
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.saveBookGroup(o.row)
                                    }
                                }
                            }, [e._v("编辑")]), !e.isShowBookGroupSettingDialog && o.row.groupId > 0 && !e.getShowShelfBooks(o.row.groupId).length ? t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteBookGroup(o.row)
                                    }
                                }
                            }, [e._v("删除")]) : e._e()]
                        }
                    }], null, !1, 1414828911)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveBookGroup()
                        }
                    }
                }, [e._v("添加分组")]), e.isShowSaveOrderButton ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.saveOrder()
                        }
                    }
                }, [e._v("保存排序")]) : e._e(), e.isShowBookGroupSettingDialog ? t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.setBookGroup
                    }
                }, [e._v("确认")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            },
            ue = [],
            fe = require("2909"),
            de = (require("13d5"), require("9485"), require("aa47")),
            pe = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookGroup",
                data: function() {
                    return {
                        isShowBookGroupSettingDialog: !1,
                        showBookGroupList: [],
                        sortedBookGroupList: [],
                        refreshCounter: 0,
                        bookGroupSelection: []
                    }
                },
                props: ["show", "isSet"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])), {}, {
                    bookGroupList: function() {
                        return this.$store.state.bookGroupList
                    },
                    shelfBooks: function() {
                        return this.$store.getters.shelfBooks
                    },
                    showBookInfo: {
                        get: function() {
                            return this.$store.state.showBookInfo
                        },
                        set: function(e) {
                            this.$store.commit("setShowBookInfo", e)
                        }
                    },
                    isShowSaveOrderButton: function() {
                        return this.showBookGroupList.reduce((function(e, t) {
                            return e + "" + t.groupId
                        }), "") !== this.sortedBookGroupList.reduce((function(e, t) {
                            return e + "" + t.groupId
                        }), "")
                    }
                }),
                watch: {
                    show: function(e) {
                        e && (this.isShowBookGroupSettingDialog = this.isSet, this.bookGroupSelection = [], this.computeBookGroupList())
                    },
                    isSet: function(e) {
                        this.dragableElement && this.dragableElement.option("disabled", e)
                    },
                    bookGroupList: function() {
                        this.computeBookGroupList()
                    }
                },
                methods: {
                    cancel: function() {
                        this.isShowBookGroupSettingDialog = !1, this.$emit("setShow", !1)
                    },
                    computeBookGroupList: function() {
                        var e = this;
                        this.showBookGroupList = [], this.$nextTick((function() {
                            e.showBookGroupList = e.isSet ? e.$store.state.bookGroupList.filter((function(e) {
                                return e.groupId > 0
                            })) : e.$store.state.bookGroupList, e.sortedBookGroupList = e.showBookGroupList
                        }))
                    },
                    opened: function() {
                        var e = this;
                        if (this.$refs.bookGroupTableRef.doLayout(), this.setDragable(), this.isSet) {
                            var t = this.getBookGroupListForBook(this.showBookInfo.group);
                            this.$nextTick((function() {
                                e.$refs.bookGroupTableRef.clearSelection(), t.forEach((function(t) {
                                    e.$refs.bookGroupTableRef.toggleRowSelection(t, !0)
                                }))
                            }))
                        }
                    },
                    loadBookGroup: function(e) {
                        return this.$root.$children[0].loadBookGroup(e)
                    },
                    getShowShelfBooks: function(e) {
                        if (-1 === e) return this.shelfBooks;
                        if (-2 === e) return this.shelfBooks.filter((function(e) {
                            return "loc_book" === e.origin
                        }));
                        if (-3 === e) return this.shelfBooks.filter((function(e) {
                            return 1 === e.type
                        }));
                        if (-4 === e) return this.shelfBooks.filter((function(e) {
                            return 0 === e.group
                        }));
                        try {
                            return this.shelfBooks.filter((function(t) {
                                return 0 === e || E.a.fromNumber(t.group).and(e).greaterThan(0)
                            }))
                        } catch (t) {}
                    },
                    toggleBookGroupShow: function(e, t) {
                        var o = this;
                        l["a"].post(this.api + "/saveBookGroup", Object(i["a"])(Object(i["a"])({}, e), {}, {
                            show: t
                        })).then((function(e) {
                            e.data.isSuccess && (o.$message.success("修改成功"), o.loadBookGroup(!0))
                        }), (function(e) {
                            o.$message.error("修改失败 " + (e && e.toString()))
                        }))
                    },
                    deleteBookGroup: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要删除该分组吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/deleteBookGroup", {
                                            groupId: e.groupId
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("删除分组成功"), t.loadBookGroup(!0))
                                        }), (function(e) {
                                            t.$message.error("删除分组失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    saveBookGroup: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$prompt("", "".concat(e ? "编辑分组" : "添加分组"), {
                                            inputValue: e ? e.groupName : "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "分组名不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/saveBookGroup", Object(i["a"])(Object(i["a"])({}, e), {}, {
                                            groupName: n.value
                                        })).then((function(o) {
                                            o.data.isSuccess && (t.$message.success(e ? "修改成功" : "添加成功"), t.loadBookGroup(!0))
                                        }), (function(o) {
                                            t.$message.error((e ? "修改失败" : "添加失败") + (o && o.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    displayBookGroupName: function(e) {
                        return e.groupName + (e.groupId < 0 ? "(" + this.$store.getters.builtInBookGroupMap[e.groupId] + ")" : "")
                    },
                    setBookGroup: function() {
                        var e = this;
                        this.bookGroupSelection.length ? l["a"].post(this.api + "/saveBookGroupId", {
                            bookUrl: this.showBookInfo.bookUrl,
                            groupId: this.bookGroupSelection.reduce((function(e, t) {
                                return E.a.fromNumber(e).or(t.groupId).toNumber()
                            }), 0)
                        }).then((function(t) {
                            t.data.isSuccess && (e.$message.success("设置成功"), e.cancel(), e.showBookInfo = t.data.data, e.$store.commit("updateShelfBook", t.data.data))
                        }), (function(t) {
                            e.$message.error("设置失败" + (t && t.toString()))
                        })) : this.$message.error("请选择书籍分组")
                    },
                    getBookGroupListForBook: function(e) {
                        var t = [];
                        return this.$store.state.bookGroupList.forEach((function(o) {
                            o.groupId > 0 && E.a.fromNumber(e).and(o.groupId).greaterThan(0) && t.push(o)
                        })), t
                    },
                    setDragable: function() {
                        var e = this,
                            t = this.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
                        this.dragableElement = de["a"].create(t, {
                            handle: ".drag-icon",
                            setData: function(e) {
                                e.setData("Text", "")
                            },
                            onEnd: function(t) {
                                if (t.oldIndex !== t.newIndex) {
                                    var o = Object(fe["a"])(e.showBookGroupList),
                                        n = o.splice(t.oldIndex, 1)[0];
                                    o.splice(t.newIndex, 0, n), e.sortedBookGroupList = o
                                }
                            }
                        }), window.bookGroupComp = this, this.dragableElement.option("disabled", this.isSet)
                    },
                    saveOrder: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        l["a"].post(e.api + "/saveBookGroupOrder", {
                                            order: e.sortedBookGroupList.map((function(e, t) {
                                                return {
                                                    groupId: e.groupId,
                                                    order: t
                                                }
                                            }))
                                        }).then((function(t) {
                                            t.data.isSuccess && (e.$message.success("保存成功"), e.loadBookGroup(!0))
                                        }), (function(t) {
                                            e.$message.error("保存失败" + (t && t.toString()))
                                        }));
                                    case 1:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    }
                }
            },
            he = pe,
            me = (require("./utils/app/525d.js"), Object(b["a"])(he, le, ue, !1, null, "289916ad", null)),
            ge = me.exports,
            be = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("RSS订阅(" + e._s(e.rssSourceList.length) + ") "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            e.showRssSourceEditButton = !e.showRssSourceEditButton
                        }
                    }
                }, [e._v(e._s(e.showRssSourceEditButton ? "取消" : "编辑"))]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.uploadRssSource
                    }
                }, [e._v("导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: function(t) {
                            return e.editRssSource(!1)
                        }
                    }
                }, [e._v("新增")])]), t("input", {
                    ref: "rssInputRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onSourceFileChange(t, !0)
                        }
                    }
                })]), t("div", {
                    staticClass: "rss-source-list-container"
                }, e._l(e.rssSourceList, (function(o, n) {
                    return t("div", {
                        key: "rss-" + n,
                        staticClass: "rss-source",
                        on: {
                            click: function(t) {
                                return e.showRssArticleListDialog(o)
                            }
                        }
                    }, [e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-close",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.deleteRssSource(o)
                            }
                        }
                    }) : e._e(), e.showRssSourceEditButton ? t("i", {
                        staticClass: "el-icon-edit",
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.editRssSource(o)
                            }
                        }
                    }) : e._e(), t("el-image", {
                        staticClass: "rss-icon",
                        attrs: {
                            src: e.getImage(o.sourceIcon, !0),
                            fit: "cover",
                            lazy: ""
                        }
                    }), t("div", {
                        staticClass: "rss-title"
                    }, [e._v(e._s(o.sourceName))])], 1)
                })), 0)])
            },
            ke = [],
            we = (require("f4b3"), require("e9c4"), {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "RssSourceList",
                data: function() {
                    return {
                        showRssSourceEditButton: !1
                    }
                },
                props: ["show", "rssSource"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])), {}, {
                    rssSourceList: function() {
                        return [].concat(this.$store.state.rssSourceList).sort((function(e, t) {
                            return e.customOrder - t.customOrder
                        }))
                    }
                }),
                watch: {
                    show: function(e) {}
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    deleteRssSource: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要删除该RSS订阅源吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/deleteRssSource", e).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("删除成功"), t.loadRssSources(!0))
                                        }), (function(e) {
                                            t.$message.error("删除失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    uploadRssSource: function() {
                        this.$refs.rssInputRef.dispatchEvent(new MouseEvent("click"))
                    },
                    onSourceFileChange: function(e, t) {
                        u["a"].$emit("onSourceFileChange", e, t)
                    },
                    showRssArticleListDialog: function(e) {
                        u["a"].$emit("showRssArticleListDialog", e)
                    },
                    editRssSource: function(e) {
                        var t = this;
                        e = e || {
                            sourceName: "新增RSS源",
                            sourceUrl: "",
                            sourceIcon: "",
                            sourceGroup: "",
                            enabled: !0,
                            singleUrl: !0,
                            articleStyle: 0,
                            ruleArticles: "",
                            ruleTitle: "",
                            rulePubDate: "",
                            ruleImage: "",
                            ruleLink: "",
                            ruleContent: "",
                            enableJs: !0
                        }, u["a"].$emit("showEditor", "编辑RSS源", JSON.stringify(e, null, 4), (function(e, o) {
                            try {
                                var n = JSON.parse(e);
                                if (!n.sourceName) return void t.$message.error("RSS源名称不能为空");
                                if (!n.sourceUrl) return void t.$message.error("RSS源链接不能为空");
                                l["a"].post(t.api + "/saveRssSource", n).then((function(e) {
                                    e.data.isSuccess && (o(), t.$message.success("保存RSS源成功"), t.loadRssSources(!0))
                                }), (function(e) {
                                    t.$message.error("保存RSS源失败 " + (e && e.toString()))
                                }))
                            } catch (r) {
                                t.$message.error("RSS源必须是JSON格式")
                            }
                        }))
                    },
                    loadRssSources: function(e) {
                        return this.$root.$children[0].loadRssSources(e)
                    }
                }
            }),
            ve = we,
            Se = (require("./utils/app/a428.js"), require("./utils/app/421e.js"), Object(b["a"])(ve, be, ke, !1, null, "ef9d6efc", null)),
            Ae = Se.exports,
            xe = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.rssSource.sourceName,
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [e.sortUrls.length > 1 ? t("el-tabs", {
                    on: {
                        "tab-click": e.onTabClick
                    },
                    model: {
                        value: e.sortName,
                        callback: function(t) {
                            e.sortName = t
                        },
                        expression: "sortName"
                    }
                }, e._l(e.sortUrls, (function(e, o) {
                    return t("el-tab-pane", {
                        key: "sort-" + o + e.name,
                        attrs: {
                            label: e.name,
                            name: e.name
                        }
                    })
                })), 1) : e._e(), t("div", {
                    ref: "rssArticleListRef",
                    staticClass: "rss-article-list-container"
                }, [e._l(e.rssArticleList, (function(o, n) {
                    return t("div", {
                        key: "rss-article-" + n,
                        staticClass: "rss-article",
                        on: {
                            click: function(t) {
                                return e.getRssArticleContent(o)
                            }
                        }
                    }, [t("div", {
                        staticClass: "rss-article-info"
                    }, [t("div", {
                        staticClass: "rss-article-title"
                    }, [e._v(e._s(o.title))]), t("div", {
                        staticClass: "rss-article-date"
                    }, [e._v(e._s(o.pubDate))])]), o.image ? t("div", {
                        staticClass: "rss-article-image"
                    }, [t("div", {
                        staticClass: "image-wrapper"
                    }, [t("el-image", {
                        staticClass: "rss-article-img",
                        attrs: {
                            src: e.getCover(o.image, !0, !0),
                            "preview-src-list": e.rssArticleImageList,
                            fit: "cover",
                            lazy: ""
                        },
                        on: {
                            click: function(t) {
                                return t.stopPropagation(), e.noop.apply(null, arguments)
                            }
                        }
                    })], 1)]) : e._e()])
                })), t("div", {
                    staticClass: "load-more-rss",
                    on: {
                        click: function(t) {
                            e.hasMoreRssArticles && e.getRssArticles(e.page + 1)
                        }
                    }
                }, [e._v(" " + e._s(e.hasMoreRssArticles ? "加载更多" : "没有更多啦") + " ")])], 2)], 1)
            },
            Be = [],
            Ce = (require("9911"), {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "RssArticleList",
                data: function() {
                    return {
                        rssArticleList: [],
                        sourceUrl: "",
                        sortUrls: [],
                        sortName: "",
                        sortUrl: "",
                        hasMoreRssArticles: !0,
                        page: 1
                    }
                },
                props: ["show", "rssSource"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])), {}, {
                    rssArticleImageList: function() {
                        var e = this;
                        return this.rssArticleList.filter((function(e) {
                            return e.image
                        })).map((function(t) {
                            return e.getImage(t.image, !0, !0)
                        }))
                    }
                }),
                watch: {
                    show: function(e) {
                        if (e) try {
                            this.parseSourceUrl(), this.getRssArticles()
                        } catch (t) {
                            this.$message.error("解析失败: " + t)
                        } else this.sortUrls = [], this.rssArticleList = [], this.page = 1, this.sortName = "", this.sortUrl = "", this.hasMoreRssArticles = !0
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    parseSourceUrl: function() {
                        if (this.sourceUrl = this.rssSource.sourceUrl, !this.rssSource.singleUrl && this.rssSource.sortUrl) {
                            var e = [];
                            this.rssSource.sortUrl.replace(/\r\n/g, "\n").split("\n").forEach((function(t) {
                                t && (t = t.split("::"), e.push({
                                    name: t[0],
                                    url: t[1]
                                }))
                            })), this.sortUrls = e, this.sortName = e[0].name, this.sortUrl = e[0].url
                        }
                    },
                    onTabClick: function(e) {
                        this.sortUrl = this.sortUrls.find((function(t) {
                            return t.name === e.name
                        })).url, this.page = 1, this.hasMoreRssArticles = !0, this.getRssArticles()
                    },
                    getRssArticles: function(e) {
                        var t = this;
                        this.page = e || 1, l["a"].post(this.api + "/getRssArticles", {
                            sourceUrl: this.sourceUrl,
                            sortName: this.sortName,
                            sortUrl: this.sortUrl,
                            page: this.page
                        }).then((function(e) {
                            if (e.data.isSuccess) {
                                var o = e.data.data.first;
                                if (!o.length) return t.$message.error("没有数据"), void(t.hasMoreRssArticles = !1);
                                t.page > 1 ? t.rssArticleList = [].concat(t.rssArticleList).concat(o) : t.rssArticleList = o
                            }
                        }), (function(e) {
                            t.$message.error("加载RSS文章列表失败 " + (e && e.toString()))
                        }))
                    },
                    noop: function() {},
                    showRssArticle: function() {},
                    getRssArticleContent: function(e) {
                        var t = this;
                        l["a"].post(this.api + "/getRssContent", {
                            sourceUrl: this.rssSource.sourceUrl,
                            link: e.link,
                            origin: e.origin
                        }).then((function(t) {
                            t.data.isSuccess && u["a"].$emit("showRssArticleDialog", Object(i["a"])(Object(i["a"])({}, e), {}, {
                                content: t.data.data
                            }))
                        }), (function(e) {
                            t.$message.error("加载RSS文章内容失败 " + (e && e.toString()))
                        }))
                    }
                }
            }),
            ye = Ce,
            Oe = (require("./utils/app/ff48.js"), require("./utils/app/a7a7.js"), Object(b["a"])(ye, xe, Be, !1, null, "3672581d", null)),
            Ie = Oe.exports,
            Te = (require("e01a"), function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.rssArticleInfo.title,
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [e.show ? t("div", {
                    staticClass: "rss-article-info-container"
                }, [t("div", {
                    ref: "rssArticleContentRef",
                    staticClass: "rss-article-content",
                    domProps: {
                        innerHTML: e._s(e.rssArticleInfo.content || e.rssArticleInfo.description)
                    },
                    on: {
                        click: e.rssArticleClickHandler
                    }
                })]) : e._e()])
            }),
            je = [],
            Le = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "RssArticle",
                data: function() {
                    return {
                        rssArticleList: [],
                        sourceUrl: "",
                        sortUrls: [],
                        sortName: "",
                        hasMoreRssArticles: !0,
                        page: 1
                    }
                },
                props: ["show", "rssArticleInfo"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])), {}, {
                    rssArticleImageList: function() {
                        var e = this;
                        return this.rssArticleList.filter((function(e) {
                            return e.image
                        })).map((function(t) {
                            return e.getImage(t.image, !0, !0)
                        }))
                    }
                }),
                watch: {
                    show: function(e) {
                        e && this.$nextTick(this.runScript)
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    rssArticleClickHandler: function(e) {
                        if (e.target && e.target.nodeName && "img" === e.target.nodeName.toLowerCase()) {
                            var t = this.$refs.rssArticleContentRef.querySelectorAll("img");
                            if (t.length) {
                                for (var o = [], n = 0, r = 0; r < t.length; r++) o.push(t[r].src), t[r] === e.target && (n = r);
                                this.$store.commit("setPreviewImageIndex", n), this.$store.commit("setPreviewImgList", o)
                            }
                        }
                    },
                    runScript: function() {
                        try {
                            var e = this.$refs.rssArticleContentRef.querySelectorAll("script");
                            if (e.length)
                                for (var t = 0; t < e.length; t++) {
                                    var o = e[t].innerText;
                                    new Function(o)()
                                }
                        } catch (n) {
                            this.$message.error("执行脚本出错: " + n)
                        }
                    }
                }
            },
            $e = Le,
            Re = (require("./utils/app/60fa.js"), require("./utils/app/b799.js"), Object(b["a"])($e, Te, je, !1, null, "12878b5b", null)),
            Fe = Re.exports,
            Ue = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        },
                        opened: e.opend
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [t("span", {
                    staticClass: "title-input"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "搜索书籍内容"
                    },
                    nativeOn: {
                        keyup: function(t) {
                            return !t.type.indexOf("key") && e._k(t.keyCode, "enter", 13, t.key, "Enter") ? null : e.searchBookContent(-1)
                        }
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                }, [t("i", {
                    staticClass: "el-input__icon el-icon-search",
                    attrs: {
                        slot: "prefix"
                    },
                    slot: "prefix"
                })])], 1)])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "resultTable",
                    attrs: {
                        data: e.searchResultList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "row-click": e.clickRow
                    }
                }, [t("el-table-column", {
                    attrs: {
                        property: "chapterTitle",
                        "min-width": "100px",
                        label: "章节"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "resultText",
                        "min-width": "250px",
                        label: "搜索结果"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium",
                        disabled: e.loading
                    },
                    on: {
                        click: function(t) {
                            return e.searchBookContent(e.lastIndex)
                        }
                    }
                }, [e._v(e._s(e.loading ? "加载中" : "加载更多"))]), e.lastScrollTop > 0 ? t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.restoreScrollTop
                    }
                }, [e._v("跳转上次位置")]) : e._e(), t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取消")])], 1)]) : e._e()
            },
            De = [],
            Ee = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "SearchBookContent",
                data: function() {
                    return {
                        lastScrollTop: 0,
                        keyword: "",
                        lastIndex: 0,
                        searchResultList: [],
                        loading: !1
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show", "book"],
                watch: {
                    show: function(e) {},
                    book: {
                        deep: !0,
                        handler: function(e, t) {
                            e.bookUrl !== t.bookUrl && (this.keyword = "", this.lastIndex = -1, this.searchResultList = [])
                        }
                    }
                },
                created: function() {
                    window.searchBookComp = this
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            default:
                                return o
                        }
                    },
                    opend: function() {
                        var e = this;
                        this.$nextTick((function() {
                            e.restoreScrollTop()
                        }))
                    },
                    restoreScrollTop: function() {
                        var e = this;
                        if (this.$refs.resultTable && this.$refs.resultTable.$ready) try {
                            this.$refs.resultTable.bodyWrapper.scrollTop = this.lastScrollTop
                        } catch (t) {
                            setTimeout((function() {
                                e.restoreScrollTop()
                            }), 10)
                        } else this.$nextTick((function() {
                            e.restoreScrollTop()
                        }))
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    searchBookContent: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (!t.loading) {
                                            o.next = 2;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 2:
                                        t.loading = !0, l["a"].post(t.api + "/searchBookContent", {
                                            url: t.book.bookUrl,
                                            keyword: t.keyword,
                                            lastIndex: e
                                        }).then((function(o) {
                                            t.loading = !1, o.data.isSuccess && (t.lastIndex = o.data.data.lastIndex, t.searchResultList = -1 === e ? o.data.data.list : [].concat(t.searchResultList).concat(o.data.data.list))
                                        }), (function(e) {
                                            t.loading = !1, t.$message.error("加载失败 " + (e && e.toString()))
                                        }));
                                    case 4:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    clickRow: function(e) {
                        this.lastScrollTop = this.$refs.resultTable.bodyWrapper.scrollTop, u["a"].$emit("showSearchContent", e), this.cancel()
                    }
                }
            },
            Me = Ee,
            Ne = (require("./utils/app/13d2e.js"), Object(b["a"])(Me, Ue, De, !1, null, "6e24e744", null)),
            _e = Ne.exports,
            Pe = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title flex-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v(e._s(e.book ? e.book.name : "") + " 书签管理 ")]), t("span", {
                    staticClass: "title-center"
                }, [t("el-input", {
                    staticClass: "search-input",
                    attrs: {
                        size: "mini",
                        placeholder: "输入关键字搜索"
                    },
                    model: {
                        value: e.search,
                        callback: function(t) {
                            e.search = t
                        },
                        expression: "search"
                    }
                })], 1)]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.showList,
                        height: e.dialogContentHeight - 42
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        },
                        "sort-change": e.sortChange
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        "min-width": "150px",
                        label: "书籍",
                        sortable: "custom",
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(t) {
                            return [e._v(" " + e._s(t.row.bookName) + " - " + e._s(t.row.bookAuthor) + " ")]
                        }
                    }], null, !1, 3213026799)
                }), t("el-table-column", {
                    attrs: {
                        property: "chapterName",
                        label: "章节",
                        "min-width": "150px",
                        sortable: "custom"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "bookText",
                        label: "内容",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "content",
                        label: "备注",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [e.book ? t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showBookmark(o.row)
                                    }
                                }
                            }, [e._v("跳转")]) : e._e(), t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editBookmark(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 2296415621)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteBookmarks
                    }
                }, [e._v("批量删除 "), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.uploadFile
                    }
                }, [e._v("导入")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.addBookmark
                    }
                }, [e._v("添加")]), t("input", {
                    ref: "fileRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onFileChange(t)
                        }
                    }
                })], 1), t("div", {
                    staticClass: "source-pagination"
                }, [t("el-pagination", {
                    attrs: {
                        "current-page": e.pagination.page,
                        "page-sizes": [25, 50, 100, 200, 300, 400, e.filterList.length],
                        "page-size": e.pagination.size,
                        layout: "total, sizes, prev, pager, next",
                        total: e.filterList.length,
                        "pager-count": e.$store.state.miniInterface ? 5 : 7
                    },
                    on: {
                        "update:currentPage": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:current-page": function(t) {
                            return e.$set(e.pagination, "page", t)
                        },
                        "update:pageSize": function(t) {
                            return e.$set(e.pagination, "size", t)
                        },
                        "update:page-size": function(t) {
                            return e.$set(e.pagination, "size", t)
                        }
                    }
                })], 1)])]) : e._e()
            },
            Ge = [],
            We = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "Bookmark",
                data: function() {
                    return {
                        localSelection: [],
                        search: "",
                        pagination: {
                            page: 1,
                            size: 25
                        },
                        sortable: {
                            prop: "",
                            order: null
                        }
                    }
                },
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])), {}, {
                    bookmarkList: function() {
                        var e = this;
                        return this.book && this.book.name ? this.$store.state.bookmarks.filter((function(t) {
                            return t.bookName === e.book.name && t.bookAuthor === e.book.author
                        })) : this.$store.state.bookmarks
                    },
                    filterList: function() {
                        var e = this;
                        return this.bookmarkList.filter((function(t) {
                            return !e.search || t.bookName.toLowerCase().includes(e.search.toLowerCase()) || t.bookAuthor.toLowerCase().includes(e.search.toLowerCase()) || t.chapterName.toLowerCase().includes(e.search.toLowerCase()) || t.bookText.toLowerCase().includes(e.search.toLowerCase()) || t.content.toLowerCase().includes(e.search.toLowerCase())
                        }))
                    },
                    sortList: function() {
                        var e = this;
                        if (!this.sortable.prop || !this.sortable.order) return this.filterList;
                        var t = [].concat(this.filterList);
                        return t.sort((function(t, o) {
                            if ("ascending" !== e.sortable.order) {
                                var n = t;
                                t = o, o = n
                            }
                            return t[e.sortable.prop] > o[e.sortable.prop] ? 1 : t[e.sortable.prop] < o[e.sortable.prop] ? -1 : 0
                        })), t
                    },
                    showList: function() {
                        var e = (this.pagination.page - 1) * this.pagination.size;
                        return e > this.filterList.length ? [] : this.filterList.slice(e, Math.min(e + this.pagination.size, this.filterList.length))
                    }
                }),
                props: ["show", "book"],
                watch: {
                    show: function(e) {}
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            default:
                                return o
                        }
                    },
                    sortChange: function(e) {
                        var t = e.prop,
                            o = e.order;
                        this.sortable = {
                            prop: t,
                            order: o
                        }
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    addBookmark: function() {
                        u["a"].$emit("addBookmark")
                    },
                    deleteBookmarks: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.localSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的书签"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的书签吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/deleteBookmarks", e.localSelection).then((function(t) {
                                            t.data.isSuccess && (e.localSelection = [], e.$message.success("删除书签成功"), e.$root.$children[0].loadBookmarks(!0))
                                        }), (function(t) {
                                            e.$message.error("删除书签失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    editBookmark: function(e) {
                        u["a"].$emit("showBookmarkForm", Object(i["a"])({}, e), !1)
                    },
                    uploadFile: function() {
                        this.$refs.fileRef.dispatchEvent(new MouseEvent("click"))
                    },
                    onFileChange: function(e) {
                        var t = this,
                            o = e.target.files && e.target.files[0],
                            n = new FileReader;
                        n.onload = function(e) {
                            var o = e.target.result;
                            try {
                                var n = JSON.parse(o);
                                Array.isArray(n) && n.length && t.comfirmImport(n)
                            } catch (r) {
                                t.$message.error("书签文件错误")
                            }
                        }, n.onerror = function() {
                            var e = new FormData;
                            e.append("file", o), l["a"].post(t.api + "/readSourceFile", e, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                if (e.data.isSuccess) {
                                    var o = [];
                                    e.data.data.forEach((function(e) {
                                        try {
                                            var t = JSON.parse(e);
                                            Array.isArray(t) && (o = o.concat(t))
                                        } catch (n) {}
                                    })), o.length ? t.comfirmImport(o) : t.$message.error("书签文件错误")
                                }
                            }), (function(e) {
                                t.$message.error("读取书签文件内容失败 " + (e && e.toString()))
                            }))
                        }, n.readAsText(o), this.$refs.fileRef.value = null
                    },
                    comfirmImport: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条书签吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/saveBookmarks", e).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("导入书签成功"), t.$root.$children[0].loadBookmarks(!0))
                                        }), (function(e) {
                                            t.$message.error("导入书签失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    showBookmark: function(e) {
                        u["a"].$emit("showBookmark", e), this.cancel()
                    }
                }
            },
            ze = We,
            He = (require("./utils/app/3455.js"), Object(b["a"])(ze, Pe, Ge, !1, null, "6574bc14", null)),
            Ve = He.exports,
            Qe = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书签",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-form", {
                    attrs: {
                        model: e.bookmarkForm
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "书名"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookName", t)
                        },
                        expression: "bookmarkForm.bookName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "作者"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookAuthor,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookAuthor", t)
                        },
                        expression: "bookmarkForm.bookAuthor"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "章节"
                    }
                }, [t("el-input", {
                    attrs: {
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.chapterName,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "chapterName", t)
                        },
                        expression: "bookmarkForm.chapterName"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "内容"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 5,
                        readonly: ""
                    },
                    model: {
                        value: e.bookmarkForm.bookText,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "bookText", t)
                        },
                        expression: "bookmarkForm.bookText"
                    }
                })], 1), t("el-form-item", {
                    attrs: {
                        label: "备注"
                    }
                }, [t("el-input", {
                    attrs: {
                        type: "textarea",
                        rows: 3
                    },
                    model: {
                        value: e.bookmarkForm.content,
                        callback: function(t) {
                            e.$set(e.bookmarkForm, "content", t)
                        },
                        expression: "bookmarkForm.content"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("取 消")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("确 定")])], 1)], 1) : e._e()
            },
            Ke = [],
            Je = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookmarkForm",
                data: function() {
                    return {
                        bookmarkForm: Object(i["a"])({}, A["c"])
                    }
                },
                props: ["show", "bookmark", "isAdd"],
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                watch: {
                    show: function(e) {
                        e && (this.bookmarkForm = this.bookmark || Object(i["a"])({}, A["c"]))
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    save: function() {
                        var e = this;
                        if (this.bookmarkForm.bookName || this.bookmarkForm.bookAuthor)
                            if (this.bookmarkForm.bookText) {
                                var t = Object(i["a"])({}, this.bookmarkForm);
                                l["a"].post("/saveBookmark", t).then((function(t) {
                                    t.data.isSuccess && (e.$message.success((e.isAdd ? "新增" : "编辑") + "书签成功"), e.$root.$children[0].loadBookmarks(!0), e.cancel())
                                }), (function(t) {
                                    e.$message.error((e.isAdd ? "新增" : "编辑") + "书签失败 " + (t && t.toString()))
                                }))
                            } else this.$message.error("书籍内容不能为空");
                        else this.$message.error("书籍信息错误")
                    }
                }
            },
            qe = Je,
            Ye = (require("./utils/app/c1cd.js"), Object(b["a"])(qe, Qe, Ke, !1, null, "b4d6c7ce", null)),
            Ze = Ye.exports,
            Xe = (require("8a79"), function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: e.title,
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v(e._s(this.title) + " "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveAndImportBooks
                    }
                }, [e._v("一键导入")]), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.resolveBooks
                    }
                }, [e._v("解析书籍")])])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    ref: "fileTable",
                    attrs: {
                        data: e.filteredFileList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.fileSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface,
                        selectable: function(e) {
                            return !e.toParent
                        }
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: e.resolveMode ? "书名" : "文件名",
                        sortable: "",
                        "sort-method": e.sortMethod,
                        filters: [{
                            text: "已导入",
                            value: "imported"
                        }, {
                            text: "未导入",
                            value: "unImport"
                        }, {
                            text: "可导入",
                            value: "canImport"
                        }, {
                            text: "目录",
                            value: "directory"
                        }],
                        "filter-method": e.filterHandler,
                        fixed: e.$store.state.miniInterface
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isImported ? t("span", {
                                staticClass: "imported-book-name"
                            }, [e._v(e._s(o.row.name))]) : o.row.isDirectory ? e._e() : t("span", [e._v(e._s(o.row.name))]), o.row.isDirectory ? t("el-link", {
                                attrs: {
                                    type: "primary"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showFileList(o.row.path)
                                    }
                                }
                            }, [e._v(e._s(o.row.name))]) : e._e()]
                        }
                    }], null, !1, 3985659619)
                }), e.resolveMode ? t("el-table-column", {
                    attrs: {
                        property: "book.author",
                        label: "作者名",
                        "min-width": "100px",
                        sortable: "",
                        filters: e.authorList,
                        "filter-method": e.filterAuthor
                    }
                }) : e._e(), t("el-table-column", {
                    attrs: {
                        property: "size",
                        label: "大小",
                        sortable: "",
                        formatter: e.formatTableField,
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastModified",
                        label: "修改时间",
                        sortable: "",
                        formatter: e.formatTableField,
                        width: "120px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        align: "right",
                        width: "140px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [o.row.isDirectory ? e._e() : t("el-dropdown", {
                                attrs: {
                                    trigger: e.$store.state.touchable ? "click" : "hover"
                                },
                                on: {
                                    command: function(t) {
                                        return e.operateFile(o.row, t)
                                    }
                                }
                            }, [t("el-button", {
                                staticClass: "text-button",
                                attrs: {
                                    type: "text",
                                    size: "medium"
                                }
                            }, [e._v(" 操作"), t("i", {
                                staticClass: "el-icon-arrow-down el-icon--right"
                            })]), t("el-dropdown-menu", {
                                attrs: {
                                    slot: "dropdown"
                                },
                                slot: "dropdown"
                            }, [o.row.isDirectory ? e._e() : t("el-dropdown-item", {
                                attrs: {
                                    command: "downloadFile"
                                }
                            }, [e._v("下载")]), !o.row.isDirectory && o.row.name.endsWith(".zip") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "restoreFromFile"
                                }
                            }, [e._v("还原")]) : e._e(), !o.row.isDirectory && o.row.name.endsWith(".json") ? t("el-dropdown-item", {
                                attrs: {
                                    command: "editFile"
                                }
                            }, [e._v("编辑")]) : e._e(), o.row.canImport ? t("el-dropdown-item", {
                                attrs: {
                                    command: "importFromFile"
                                }
                            }, [e._v("加入书架")]) : e._e()], 1)], 1), o.row.toParent ? e._e() : t("el-button", {
                                staticStyle: {
                                    color: "#f56c6c"
                                },
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.deleteFile(o.row)
                                    }
                                }
                            }, [e._v("删除")])]
                        }
                    }], null, !1, 1541015572)
                }, [t("template", {
                    slot: "header"
                }, [t("el-input", {
                    attrs: {
                        size: "mini",
                        placeholder: "搜索本页"
                    },
                    model: {
                        value: e.keyword,
                        callback: function(t) {
                            e.keyword = t
                        },
                        expression: "keyword"
                    }
                })], 1)], 2)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-dropdown", {
                    staticClass: "float-left",
                    attrs: {
                        trigger: e.$store.state.touchable ? "click" : "hover"
                    },
                    on: {
                        command: e.operate
                    }
                }, [t("el-button", {
                    attrs: {
                        type: "primary",
                        size: "medium"
                    }
                }, [e._v(" 操作"), e.fileSelection.length ? t("span", [e._v(" (" + e._s(e.fileSelection.length) + ")")]) : e._e(), t("i", {
                    staticClass: "el-icon-arrow-down el-icon--right"
                })]), t("el-dropdown-menu", {
                    attrs: {
                        slot: "dropdown"
                    },
                    slot: "dropdown"
                }, [t("el-dropdown-item", {
                    attrs: {
                        command: "deleteFileList"
                    }
                }, [e._v("批量删除")]), t("el-dropdown-item", {
                    attrs: {
                        command: "importFromFile"
                    }
                }, [e._v("批量加入书架")]), t("el-dropdown-item", {
                    attrs: {
                        command: "mkdir"
                    }
                }, [e._v("新建目录")]), t("el-dropdown-item", {
                    attrs: {
                        command: "upload"
                    }
                }, [e._v("上传文件")])], 1)], 1), t("input", {
                    ref: "bookRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file",
                        multiple: "multiple"
                    },
                    on: {
                        change: e.onFileChange
                    }
                })], 1)]) : e._e()
            }),
            et = [],
            tt = (require("3c65"), require("30b5")),
            ot = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "FileManager",
                data: function() {
                    return {
                        currentPath: "/",
                        fileList: [],
                        keyword: "",
                        searchWord: "",
                        resolveMode: !1,
                        fileSelection: []
                    }
                },
                props: ["show", "home", "title"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])), {}, {
                    localBook: function() {
                        return this.$store.getters.shelfBooks.filter((function(e) {
                            return "loc_book" === e.origin
                        }))
                    },
                    filteredFileList: function() {
                        var e = this;
                        return this.fileList.filter((function(t) {
                            return !e.keyword || t.name.toLowerCase().includes(e.keyword.toLowerCase())
                        }))
                    },
                    authorList: function() {
                        if (!this.resolveMode) return [];
                        var e = {};
                        return this.filteredFileList.map((function(e) {
                            return e.book.author
                        })).filter((function(t) {
                            return !(!t || e[t]) && (e[t] = 1, !0)
                        })).map((function(e) {
                            return {
                                text: e,
                                value: e
                            }
                        }))
                    }
                }),
                watch: {
                    show: function(e) {
                        var t = this;
                        e && (this.resolveMode = !1, this.showFileList("/"), this.$nextTick((function() {
                            t.$refs.fileTable.clearFilter(), t.$refs.fileTable.clearSelection(), t.$refs.fileTable.clearSort()
                        })))
                    }
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            case "createdAt":
                            case "lastLoginAt":
                            case "lastModified":
                                return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
                            case "size":
                                return e.isDirectory ? "" : Object(U["d"])(o);
                            default:
                                return o
                        }
                    },
                    canImport: function(e) {
                        var t = e.path.toLowerCase();
                        return t.endsWith(".txt") || t.endsWith(".epub") || t.endsWith(".umd") || t.endsWith(".cbz") || t.endsWith(".pdf")
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    resolveBooks: function() {
                        var e = this;
                        this.$confirm("解析当前目录及子目录的所有书籍耗费资源较多，确认要继续?", "提示", {
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            type: "warning",
                            beforeClose: function(t, o, n) {
                                "confirm" === t ? (o.confirmButtonLoading = !0, o.confirmButtonText = "解析中...", l["a"].post(e.api + "/file/parse", {
                                    path: e.currentPath,
                                    home: e.home
                                }).then((function(t) {
                                    t.data.isSuccess && (e.fileList = t.data.data.map((function(t) {
                                        return Object(i["a"])(Object(i["a"])({}, t), {}, {
                                            isImported: !!e.localBook.find((function(e) {
                                                return e.originName.indexOf(t.path) >= 0
                                            })),
                                            canImport: e.canImport(t)
                                        })
                                    })), e.resolveMode = !0, e.$nextTick((function() {
                                        e.$refs.fileTable && e.$refs.fileTable.doLayout()
                                    })))
                                }), (function(t) {
                                    e.$message.error("解析书籍失败 " + (t && t.toString()))
                                })).then((function() {
                                    n(), o.confirmButtonLoading = !1
                                }))) : n()
                            }
                        })["catch"]((function() {
                            return !1
                        }))
                    },
                    resolveAndImportBooks: function() {
                        var e = this;
                        this.$confirm("一键导入当前目录及子目录的所有书籍耗费资源较多，确认要继续?", "提示", {
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            type: "warning",
                            beforeClose: function(t, o, n) {
                                "confirm" === t ? (o.confirmButtonLoading = !0, o.confirmButtonText = "导入中...", l["a"].post(e.api + "/file/parse", {
                                    path: e.currentPath,
                                    home: e.home,
                                    import: 1
                                }, {
                                    timeout: 3e5
                                }).then((function(t) {
                                    t.data.isSuccess && (e.$root.$children[0].loadBookShelf(), e.$alert("成功导入" + t.data.data.length + "本书", "导入结果"))
                                }), (function(t) {
                                    e.$message.error("一键导入书籍失败 " + (t && t.toString()))
                                })).then((function() {
                                    n(), o.confirmButtonLoading = !1
                                }))) : n()
                            }
                        })["catch"]((function() {
                            return !1
                        }))
                    },
                    showFileList: function(e) {
                        var t = this;
                        this.currentPath = e || "/", l["a"].get(this.api + "/file/list", {
                            params: {
                                path: this.currentPath,
                                home: this.home
                            }
                        }).then((function(e) {
                            if (e.data.isSuccess) {
                                if (e.data.data = e.data.data || [], "/" !== t.currentPath) {
                                    var o = t.currentPath.split("/").filter((function(e) {
                                        return e
                                    }));
                                    o.pop(), e.data.data.unshift({
                                        name: "..",
                                        isDirectory: !0,
                                        toParent: !0,
                                        path: "/" + o.join("/")
                                    })
                                }
                                e.data.data.sort(t.sortMethod), t.fileList = e.data.data.map((function(e) {
                                    return Object(i["a"])(Object(i["a"])({}, e), {}, {
                                        isImported: !e.isDirectory && !!t.localBook.find((function(t) {
                                            return t.originName.indexOf(e.path) >= 0
                                        })),
                                        canImport: t.canImport(e)
                                    })
                                }))
                            }
                        }), (function(e) {
                            t.$message.error("加载文件列表失败 " + (e && e.toString()))
                        }))
                    },
                    sortMethod: function(e, t) {
                        return ".." === e.name ? -1 : ".." === t.name ? 1 : e.isDirectory && !t.isDirectory ? -1 : !e.isDirectory && t.isDirectory || e.name > t.name ? 1 : -1
                    },
                    filterHandler: function(e, t) {
                        switch (e) {
                            case "imported":
                                return t.isImported;
                            case "unImport":
                                return !t.isDirectory && !t.isImported;
                            case "canImport":
                                return !t.isDirectory && t.canImport;
                            case "directory":
                                return t.isDirectory;
                            default:
                                return !0
                        }
                    },
                    filterAuthor: function(e, t) {
                        return t.book.author === e
                    },
                    operate: function(e) {
                        if ("importFromFile" === e) return this.importFromFile(!0);
                        this[e].call(this)
                    },
                    operateFile: function(e, t) {
                        this[t].call(this, e)
                    },
                    deleteFileList: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.fileSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的文件"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的文件吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/file/deleteMulti", {
                                            path: e.fileSelection.map((function(e) {
                                                return e.path
                                            })),
                                            home: e.home
                                        }).then((function(t) {
                                            t.data.isSuccess && (e.fileSelection = [], e.$message.success("删除文件成功"), e.showFileList(e.currentPath))
                                        }), (function(t) {
                                            e.$message.error("删除文件失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    deleteFile: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要删除该".concat(e.isDirectory ? "目录" : "文件", "吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/file/delete", {
                                            path: e.path,
                                            home: t.home
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("删除成功"), t.showFileList(t.currentPath))
                                        }), (function(e) {
                                            t.$message.error("删除失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    editFile: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        l["a"].get(t.api + "/file/get", {
                                            params: {
                                                path: e.path,
                                                home: t.home
                                            }
                                        }).then((function(o) {
                                            if (o.data.isSuccess) {
                                                var n = o.data.data;
                                                try {
                                                    n = JSON.stringify(JSON.parse(n), null, 4)
                                                } catch (r) {
                                                    n = o.data.data
                                                }
                                                u["a"].$emit("showEditor", "编辑 " + e.name, n, function() {
                                                    var o = Object(c["a"])(Object(s["a"])().mark((function o(n, r) {
                                                        var a;
                                                        return Object(s["a"])().wrap((function(o) {
                                                            while (1) switch (o.prev = o.next) {
                                                                case 0:
                                                                    return o.prev = 0, o.next = 3, t.$confirm("随意修改内容有可能会造成数据错乱，确认要保存编辑后的内容吗?", "提示", {
                                                                        confirmButtonText: "确定",
                                                                        cancelButtonText: "取消",
                                                                        type: "warning"
                                                                    })["catch"]((function() {
                                                                        return !1
                                                                    }));
                                                                case 3:
                                                                    if (a = o.sent, a) {
                                                                        o.next = 6;
                                                                        break
                                                                    }
                                                                    return o.abrupt("return");
                                                                case 6:
                                                                    l["a"].post(t.api + "/file/save", {
                                                                        path: e.path,
                                                                        home: t.home,
                                                                        content: n
                                                                    }).then((function(e) {
                                                                        e.data.isSuccess && r()
                                                                    }), (function(e) {
                                                                        t.$message.error("保存文件内容失败 " + (e && e.toString()))
                                                                    })), o.next = 12;
                                                                    break;
                                                                case 9:
                                                                    o.prev = 9, o.t0 = o["catch"](0), t.$message.error("内容必须是JSON格式");
                                                                case 12:
                                                                case "end":
                                                                    return o.stop()
                                                            }
                                                        }), o, null, [
                                                            [0, 9]
                                                        ])
                                                    })));
                                                    return function(e, t) {
                                                        return o.apply(this, arguments)
                                                    }
                                                }())
                                            }
                                        }), (function(e) {
                                            t.$message.error("获取文件内容失败 " + (e && e.toString()))
                                        }));
                                    case 1:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    mkdir: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, e.$prompt("", "新建目录", {
                                            inputValue: "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "目录名不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (o = t.sent, o) {
                                            t.next = 5;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 5:
                                        l["a"].post(e.api + "/file/mkdir", {
                                            path: e.currentPath,
                                            name: o.value,
                                            home: e.home
                                        }).then((function(t) {
                                            t.data.isSuccess && (e.$message.success("操作成功"), e.showFileList(e.currentPath))
                                        }), (function(t) {
                                            e.$message.error("操作失败" + (t && t.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    importFromFile: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (!0 !== e) {
                                            o.next = 4;
                                            break
                                        }
                                        if (t.fileSelection.length) {
                                            o.next = 4;
                                            break
                                        }
                                        return t.$message.error("请选择需要加入书架的书籍"), o.abrupt("return");
                                    case 4:
                                        l["a"].post(t.api + "/file/importPreview", {
                                            path: !0 === e ? t.fileSelection.map((function(e) {
                                                return e.path
                                            })) : [e.path],
                                            home: t.home
                                        }).then((function(e) {
                                            if (e.data.isSuccess) {
                                                if (!e.data.data || !e.data.data.length) return void t.$message.error("没有选择可导入的书籍");
                                                setTimeout((function() {
                                                    u["a"].$emit("importPreview", e.data.data)
                                                }), 0)
                                            }
                                        }), (function(e) {
                                            t.$message.error("请求失败 " + (e && e.toString()))
                                        }));
                                    case 5:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    upload: function() {
                        this.$refs.bookRef.dispatchEvent(new MouseEvent("click"))
                    },
                    downloadFile: function(e) {
                        var t = tt(this.api + "/file/download", {
                            home: this.home,
                            path: e.path,
                            accessToken: this.$store.state.token
                        });
                        window.open(t, "__blank")
                    },
                    onFileChange: function(e) {
                        var t = this;
                        if (e.target && e.target.files && e.target.files.length) {
                            for (var o = new FormData, n = 0; n < e.target.files.length; n++) {
                                var r = e.target.files[n];
                                o.append("file" + n, r)
                            }
                            o.append("home", this.home), o.append("path", this.currentPath), l["a"].post(this.api + "/file/upload", o, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                e.data.isSuccess && (t.$message.success("上传文件成功"), t.showFileList(t.currentPath))
                            }), (function(e) {
                                t.$message.error("上传文件 " + (e && e.toString()))
                            })), this.$refs.bookRef.value = null
                        }
                    },
                    restoreFromFile: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要从该压缩文件恢复书源、书架、分组、RSS订阅数据、替换规则、书签、用户配置和Webdav书籍吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/file/restore", {
                                            path: e.path,
                                            home: t.home
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("恢复成功"), t.$root.$children[0].init(!0))
                                        }), (function(e) {
                                            t.$message.error("恢复失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    }
                }
            },
            nt = ot,
            rt = (require("./utils/app/9be0.js"), require("./utils/app/c4fe.js"), Object(b["a"])(nt, Xe, et, !1, null, "eecda6a0", null)),
            at = rt.exports,
            it = function() {
                var e = this,
                    t = e._self._c;
                return t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍封面",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书籍封面 "), t("span", {
                    staticClass: "float-right span-btn",
                    class: {
                        loading: e.loadingMore
                    },
                    on: {
                        click: e.searchBookSourceByEventStream
                    }
                }, [e.loadingMore ? t("i", {
                    staticClass: "el-icon-loading"
                }) : e._e(), e._v(" " + e._s(e.loadingMore ? "加载中..." : "加载更多") + " ")])])]), e.show ? t("div", {
                    staticClass: "book-cover-container"
                }, [e.coverList.length ? t("div", {
                    staticClass: "cover-list"
                }, e._l(e.coverList, (function(o, n) {
                    return t("div", {
                        key: "cover-" + n,
                        staticClass: "book-cover-wrapper",
                        on: {
                            click: function(t) {
                                return e.setCover(o)
                            }
                        }
                    }, [t("el-image", {
                        staticClass: "book-cover",
                        attrs: {
                            src: o,
                            fit: "cover",
                            lazy: ""
                        }
                    }, [t("img", {
                        staticClass: "el-image__inner",
                        staticStyle: {
                            "object-fit": "cover"
                        },
                        attrs: {
                            slot: "error",
                            src: e.noCover,
                            alt: ""
                        },
                        slot: "error"
                    })])], 1)
                })), 0) : t("div", {
                    staticClass: "empty-notice"
                }, [e._v(" 暂无其它来源 ")])]) : e._e()])
            },
            st = [],
            ct = require("./utils/app/7e43.js"),
            lt = o.n(ct),
            ut = require("30b5"),
            ft = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookCover",
                data: function() {
                    return {
                        coverList: [],
                        lastIndex: -1,
                        loadingMore: !1,
                        noCover: lt.a
                    }
                },
                props: ["show", "book"],
                computed: Object(i["a"])(Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])), {}, {
                    showBookInfo: {
                        get: function() {
                            return this.$store.state.showBookInfo
                        },
                        set: function(e) {
                            this.$store.commit("setShowBookInfo", e)
                        }
                    },
                    coverListMap: function() {
                        return this.coverList.reduce((function(e, t) {
                            return e[t] = t, e
                        }), {})
                    }
                }),
                watch: {
                    show: function(e) {
                        e && (this.coverList = [], this.lastIndex = -1, this.loadingMore = !1, this.getBookSource())
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    getBookCoverUrl: function(e) {
                        return e.customCoverUrl || e.coverUrl
                    },
                    getBookSource: function(e) {
                        var t = this;
                        l["a"].post(this.api + "/getAvailableBookSource", {
                            url: this.book.bookUrl,
                            refresh: e ? 1 : 0
                        }).then((function(e) {
                            t.loading = !1, e.data.isSuccess && (t.coverList = (e.data.data || []).map((function(e) {
                                return e.coverUrl
                            })))
                        }), (function(e) {
                            throw t.loading = !1, t.$message.error("获取书籍来源信息失败 " + (e && e.toString())), e
                        }))
                    },
                    searchBookSourceByEventStream: function() {
                        var e = this,
                            t = function() {
                                try {
                                    e.searchEventSource && e.searchEventSource.readyState != e.searchEventSource.CLOSED && e.searchEventSource.close(), e.searchEventSource = null
                                } catch (t) {}
                            };
                        if (this.loadingMore) return t(), void(this.loadingMore = !1);
                        var o = {
                            accessToken: this.$store.state.token,
                            concurrentCount: this.$store.state.searchConfig.concurrentCount,
                            url: this.book.bookUrl,
                            lastIndex: this.lastIndex
                        };
                        this.loadingMore = !0;
                        var n = ut(this.api + "/searchBookSourceSSE", o);
                        t(), this.searchEventSource = new EventSource(n, {
                            withCredentials: !0
                        }), this.searchEventSource.addEventListener("error", (function(o) {
                            e.loadingMore = !1, t();
                            try {
                                if (o.data) {
                                    var n = JSON.parse(o.data);
                                    n && n.errorMsg && e.$message.error(n.errorMsg)
                                }
                            } catch (r) {}
                        }));
                        var r = this.coverList.length;
                        this.searchEventSource.addEventListener("end", (function(o) {
                            e.loadingMore = !1, t();
                            try {
                                if (o.data) {
                                    var n = JSON.parse(o.data);
                                    n && n.lastIndex && (e.lastIndex = n.lastIndex)
                                }
                                e.coverList.length === r && e.$message.error("没有更多啦")
                            } catch (a) {}
                        })), this.searchEventSource.addEventListener("message", (function(t) {
                            try {
                                if (t.data) {
                                    var o = JSON.parse(t.data);
                                    o && o.lastIndex && (e.lastIndex = o.lastIndex), o.data && (e.coverList = [].concat(e.coverList, o.data.filter((function(t) {
                                        return !e.coverListMap[t.coverUrl]
                                    })).map((function(e) {
                                        return e.coverUrl
                                    }))))
                                }
                            } catch (n) {}
                        }))
                    },
                    setCover: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n, r;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要将封面设置为所选择的图片吗？", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return", !1);
                                    case 5:
                                        r = Object(i["a"])(Object(i["a"])({}, t.book), {}, {
                                            coverUrl: e
                                        }), t.saveBook(r);
                                    case 7:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    saveBook: function(e) {
                        var t = this;
                        return l["a"].post(this.api + "/saveBook", e).then((function(o) {
                            o.data.isSuccess && (t.$message.success("操作成功"), t.showBookInfo.bookUrl === e.bookUrl && (t.showBookInfo = o.data.data), t.$store.commit("updateShelfBook", o.data.data))
                        }), (function(e) {
                            t.$message.error("操作失败" + (e && e.toString()))
                        }))
                    }
                }
            },
            dt = ft,
            pt = (require("./utils/app/d7fd.js"), require("./utils/app/d8db.js"), Object(b["a"])(dt, it, st, !1, null, "2fdb5f62", null)),
            ht = pt.exports,
            mt = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "授权管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("授权管理 "), t("span", {
                    staticClass: "float-right span-btn",
                    on: {
                        click: e.importLicense
                    }
                }, [e._v("更新密钥")])])]), t("div", [t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("授权类型")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.type))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("授权域名")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.host))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("过期时间")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(0 === e.license.expiredAt ? "永久有效" : new Date(e.license.expiredAt).format("yy-MM-dd hh:mm")))])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("用户上限")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(e.license.userMaxLimit) + " 人")])]), t("div", {
                    staticClass: "license-item"
                }, [t("span", {
                    staticClass: "license-name"
                }, [e._v("kindle端")]), t("span", {
                    staticClass: "license-value"
                }, [e._v(e._s(0 === e.license.simpleWebExpiredAt ? "永久有效" : new Date(e.license.simpleWebExpiredAt).format("yy-MM-dd hh:mm")) + " 过期")]), t("span", {
                    staticClass: "span-btn",
                    on: {
                        click: e.supplyLicense
                    }
                }, [e._v("申请7天试用")])])])]) : e._e()
            },
            gt = [],
            bt = (require("466d"), "https://r.htmake.com"),
            kt = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "License",
                data: function() {
                    return {
                        license: {
                            host: "",
                            userMaxLimit: 50,
                            expiredAt: 0,
                            openApi: !1,
                            simpleWebExpiredAt: (new Date).getTime()
                        }
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show"],
                watch: {
                    show: function(e) {
                        e && this.getLicense()
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    getLicense: function() {
                        var e = this;
                        l["a"].get(this.api + "/getLicense").then((function(t) {
                            t.data.isSuccess && (e.license = t.data.data.license)
                        }), (function(t) {
                            e.$message.error("获取授权失败 " + (t && t.toString()))
                        }))
                    },
                    supplyLicense: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, e.$prompt("请输入邮箱进行验证，每个邮箱仅限试用一次，有效期7天", "验证邮箱", {
                                            inputValue: "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "邮箱不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (o = t.sent, o) {
                                            t.next = 5;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 5:
                                        if (n = o.value, n.match(/@(163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud).com$/)) {
                                            t.next = 9;
                                            break
                                        }
                                        return e.$message.error("仅支持163|126|qq|yahoo|sina|sohu|yeah|139|189|21cn|outlook|gmail|icloud等邮箱"), t.abrupt("return");
                                    case 9:
                                        l["a"].post(bt + "/reader3/sendCodeToEmail", {
                                            email: n
                                        }).then((function(t) {
                                            t.data.isSuccess && e.showVerifyCodePrompt(n)
                                        }), (function(t) {
                                            e.$message.error("发送验证码失败 " + (t && t.toString()))
                                        }));
                                    case 10:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    showVerifyCodePrompt: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$prompt("请输入验证码", "验证邮箱", {
                                            inputValue: "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "验证码不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(bt + "/reader3/supplyLicense", {
                                            email: e,
                                            code: n.value
                                        }, {
                                            alert: !1
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("申请试用密钥成功，有效期7天，请谨慎更新"), t.comfirmImport(e.data.data.key))
                                        }), (function(e) {
                                            t.$message.error("申请试用失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    importLicense: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, e.$prompt("请输入密钥", "更新密钥", {
                                            inputValue: "",
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            inputValidator: function(e) {
                                                return !!e || "密钥不能为空"
                                            }
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (o = t.sent, o) {
                                            t.next = 5;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 5:
                                        return t.abrupt("return", e.comfirmImport(o.value));
                                    case 6:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    comfirmImport: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要更新密钥吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/importLicense", {
                                            content: e
                                        }).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("更新密钥成功"), t.license = e.data.data.license)
                                        }), (function(e) {
                                            t.$message.error("更新密钥失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    }
                }
            },
            wt = kt,
            vt = (require("./utils/app/62a7.js"), Object(b["a"])(wt, mt, gt, !1, null, "3ee772ca", null)),
            St = vt.exports,
            At = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书籍设置",
                        visible: e.show,
                        width: e.dialogSmallWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("el-form", {
                    attrs: {
                        model: e.bookConfig
                    }
                }, [t("el-form-item", {
                    attrs: {
                        label: "PDF图片宽度"
                    }
                }, [t("el-select", {
                    staticClass: "setting-select",
                    attrs: {
                        size: "mini",
                        filterable: "",
                        placeholder: "请选择PDF图片宽度"
                    },
                    model: {
                        value: e.bookConfig.pdfImageWidth,
                        callback: function(t) {
                            e.$set(e.bookConfig, "pdfImageWidth", t)
                        },
                        expression: "bookConfig.pdfImageWidth"
                    }
                }, e._l(e.pdfImageWidthList, (function(e, o) {
                    return t("el-option", {
                        key: "pdf-image-width-" + o,
                        attrs: {
                            label: e.name,
                            value: e.value
                        }
                    })
                })), 1)], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.save
                    }
                }, [e._v("保 存")]), t("el-button", {
                    attrs: {
                        size: "medium",
                        type: "primary"
                    },
                    on: {
                        click: e.cancel
                    }
                }, [e._v("关 闭")])], 1)], 1) : e._e()
            },
            xt = [],
            Bt = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "BookConfig",
                data: function() {
                    return {
                        isAdd: !0,
                        bookConfig: {
                            pdfImageWidth: (this.$store.state.showBookInfo.readConfig || {}).pdfImageWidth || 800
                        },
                        pdfImageWidthList: [{
                            name: "750px",
                            value: 750
                        }, {
                            name: "800px",
                            value: 800
                        }, {
                            name: "850px",
                            value: 850
                        }, {
                            name: "900px",
                            value: 900
                        }, {
                            name: "950px",
                            value: 950
                        }, {
                            name: "1000px",
                            value: 1e3
                        }, {
                            name: "1100px",
                            value: 1100
                        }, {
                            name: "1200px",
                            value: 1200
                        }, {
                            name: "1300px",
                            value: 1300
                        }, {
                            name: "1400px",
                            value: 1400
                        }, {
                            name: "1500px",
                            value: 1500
                        }, {
                            name: "1600px",
                            value: 1600
                        }]
                    }
                },
                props: ["show"],
                computed: Object(i["a"])({}, Object(h["b"])(["dialogSmallWidth", "dialogTop"])),
                watch: {
                    show: function(e) {
                        e && (this.bookConfig = {
                            pdfImageWidth: (this.$store.state.showBookInfo.readConfig || {}).pdfImageWidth || 800
                        })
                    }
                },
                methods: {
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    save: function() {
                        var e = this;
                        l["a"].post("/book/saveBookConfig", Object(i["a"])({
                            bookUrl: this.$store.state.showBookInfo.bookUrl
                        }, this.bookConfig)).then((function(t) {
                            t.data.isSuccess && (e.$message.success("更新成功"), e.showBookInfo = t.data.data, e.$store.commit("updateShelfBook", t.data.data))
                        }), (function(t) {
                            e.$message.error("更新失败" + (t && t.toString()))
                        }))
                    }
                }
            },
            Ct = Bt,
            yt = Object(b["a"])(Ct, At, xt, !1, null, "08713662", null),
            Ot = yt.exports,
            It = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "书源订阅管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("书源订阅管理 ")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.remoteBookSourceList,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: "名称",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "link",
                        label: "链接",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastSyncTime",
                        formatter: e.formatTableField,
                        label: "上次同步",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "120px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.showForm(o.row, o.$index)
                                    }
                                }
                            }, [e._v("修改")]), t("el-button", {
                                attrs: {
                                    type: "text",
                                    disabled: e.loadingIndex >= 0
                                },
                                on: {
                                    click: function(t) {
                                        return e.sync(o.row, o.$index)
                                    }
                                }
                            }, [e.loadingIndex === o.$index ? t("i", {
                                staticClass: "el-icon-loading"
                            }) : e._e(), e._v(e._s(e.loadingIndex === o.$index ? "同步中" : "同步"))])]
                        }
                    }], null, !1, 3286982204)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteRemoteBookSourceSub
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: function(t) {
                            return e.showForm(!1)
                        }
                    }
                }, [e._v("新增")])], 1)])]) : e._e()
            },
            Tt = [],
            jt = function(e, t) {
                return l["a"].get("/file/get", {
                    params: {
                        path: e,
                        home: t || "__HOME__"
                    },
                    silent: !0
                }).then((function(e) {
                    return e.data.isSuccess ? e.data.data : null
                }))["catch"]((function() {
                    return null
                }))
            },
            Lt = function(e, t, o) {
                return l["a"].post("/file/save", {
                    path: e,
                    content: t,
                    home: o || "__HOME__"
                }, {
                    silent: !0
                }).then((function(e) {
                    return !!e.data.isSuccess
                }))["catch"]((function() {
                    return null
                }))
            },
            $t = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "RemoteBookSourceSub",
                data: function() {
                    return {
                        localSelection: [],
                        filePath: "remoteBookSourceSub.json",
                        remoteBookSourceList: [],
                        loadingIndex: -1
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show"],
                watch: {
                    show: function(e) {
                        e && this.loadRemoteBookSourceList()
                    }
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            case "lastSyncTime":
                                return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
                            default:
                                return o
                        }
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    loadRemoteBookSourceList: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, jt(e.filePath);
                                    case 2:
                                        if (o = t.sent, o) try {
                                            n = JSON.parse(o), Array.isArray(n) && (e.remoteBookSourceList = n)
                                        } catch (r) {}
                                        case 4: case "end": return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    deleteRemoteBookSourceSub: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.localSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的书源订阅"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的书源订阅吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        n = e.remoteBookSourceList.filter((function(t) {
                                            return !e.localSelection.includes(t)
                                        })), e.saveData(n);
                                    case 10:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    sync: function(e, t) {
                        var o = this;
                        this.loadingIndex = t, l["a"].post("/saveFromRemoteSource", {
                            url: e.link
                        }, {
                            timeout: 6e5
                        }).then((function(e) {
                            o.loadingIndex = -1, o.$root.$children[0].loadBookSource(!0), e.data.isSuccess && (o.remoteBookSourceList[t].lastSyncTime = (new Date).getTime(), o.saveData(o.remoteBookSourceList, !0))
                        }), (function(e) {
                            o.$message.error("同步失败 " + (e && e.toString()))
                        }))
                    },
                    showForm: function(e, t) {
                        var o = this;
                        return Object(c["a"])(Object(s["a"])().mark((function n() {
                            var r, a, c, l;
                            return Object(s["a"])().wrap((function(n) {
                                while (1) switch (n.prev = n.next) {
                                    case 0:
                                        return r = !e, e = Object(i["a"])({}, e || {
                                            name: "",
                                            link: "",
                                            lastSyncTime: null
                                        }), a = [{
                                            name: "name",
                                            label: "名称",
                                            type: "input"
                                        }, {
                                            name: "link",
                                            label: "链接",
                                            type: "input"
                                        }], n.next = 5, o.$msgbox({
                                            title: r ? "新增订阅" : "编辑订阅",
                                            message: o.renderForm(o.filePath, e, a, (function(t) {
                                                return e = t
                                            })),
                                            showCancelButton: !0,
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消"
                                        })["catch"]((function(e) {
                                            return "close" === e && "close"
                                        }));
                                    case 5:
                                        if (c = n.sent, "confirm" !== c) {
                                            n.next = 12;
                                            break
                                        }
                                        l = [].concat(o.remoteBookSourceList), r ? l = l.concat([e]) : l[t] = e, o.saveData(l), n.next = 13;
                                        break;
                                    case 12:
                                        return n.abrupt("return", !1);
                                    case 13:
                                    case "end":
                                        return n.stop()
                                }
                            }), n)
                        })))()
                    },
                    saveData: function(e, t) {
                        var o = this;
                        return Object(c["a"])(Object(s["a"])().mark((function n() {
                            var r;
                            return Object(s["a"])().wrap((function(n) {
                                while (1) switch (n.prev = n.next) {
                                    case 0:
                                        return n.next = 2, Lt(o.filePath, JSON.stringify(e));
                                    case 2:
                                        r = n.sent, r && (o.localSelection = [], t || o.$message.success("操作成功"), o.loadRemoteBookSourceList());
                                    case 4:
                                    case "end":
                                        return n.stop()
                                }
                            }), n)
                        })))()
                    }
                }
            },
            Rt = $t,
            Ft = (require("./utils/app/62b7.js"), Object(b["a"])(Rt, It, Tt, !1, null, "e4af2df0", null)),
            Ut = Ft.exports,
            Dt = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "密钥管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("密钥管理 ")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.list,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "host",
                        "min-width": "150px",
                        label: "Host",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "code",
                        label: "Code",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "type",
                        label: "类型",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "userMaxLimit",
                        label: "用户上限",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "expiredAt",
                        label: "过期时间",
                        formatter: e.formatTableField,
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "simpleWebExpiredAt",
                        formatter: e.formatTableField,
                        label: "Kindle过期",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "activeTime",
                        formatter: e.formatTableField,
                        label: "激活时间",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "activeIp",
                        label: "激活IP",
                        "min-width": "100px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastOnlineTime",
                        formatter: e.formatTableField,
                        label: "上次在线时间",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "lastOnlineIp",
                        label: "上次在线IP",
                        "min-width": "100px"
                    }
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteItems
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.showForm
                    }
                }, [e._v("生成密钥")])], 1)])]) : e._e()
            },
            Et = [],
            Mt = require("./utils/app/63f0.js"),
            Nt = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "ActiveLicense",
                data: function() {
                    return {
                        localSelection: [],
                        filePath: "data/activeLicense.json",
                        list: [],
                        loadingIndex: -1
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show"],
                watch: {
                    show: function(e) {
                        e && this.loadList()
                    }
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            case "expiredAt":
                            case "simpleWebExpiredAt":
                            case "activeTime":
                            case "lastOnlineTime":
                                return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
                            default:
                                return o
                        }
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    loadList: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, jt(e.filePath, "__STORAGE__");
                                    case 2:
                                        if (o = t.sent, o) try {
                                            n = JSON.parse(o), Array.isArray(n) && (e.list = n)
                                        } catch (r) {}
                                        case 4: case "end": return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    deleteItems: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.localSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的记录"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的记录吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        n = e.list.filter((function(t) {
                                            return !e.localSelection.includes(t)
                                        })), e.saveData(n);
                                    case 10:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    showForm: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n, r;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return o = Object(Mt["b"])("lastGenerateLicenseConfig", {
                                            host: "*",
                                            userMaxLimit: 15,
                                            expiredAt: 0,
                                            openApi: !1,
                                            simpleWebExpiredAt: 1735660799e3,
                                            key: "Pvkp7tMQJpi4kWBE",
                                            type: "basic",
                                            code: ""
                                        }), n = [{
                                            name: "host",
                                            label: "Host",
                                            type: "input"
                                        }, {
                                            name: "userMaxLimit",
                                            label: "用户上限",
                                            type: "input"
                                        }, {
                                            name: "expiredAt",
                                            label: "过期时间",
                                            type: "input"
                                        }, {
                                            name: "simpleWebExpiredAt",
                                            label: "Kindle有效期",
                                            type: "input"
                                        }, {
                                            name: "type",
                                            label: "类型",
                                            type: "input"
                                        }, {
                                            name: "code",
                                            label: "Code",
                                            type: "input"
                                        }], t.next = 4, e.$msgbox({
                                            title: "生成密钥",
                                            message: e.renderForm(e.filePath, o, n, (function(e) {
                                                return o = e
                                            })),
                                            showCancelButton: !0,
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消"
                                        })["catch"]((function(e) {
                                            return "close" === e && "close"
                                        }));
                                    case 4:
                                        if (r = t.sent, "confirm" !== r) {
                                            t.next = 16;
                                            break
                                        }
                                        return o.userMaxLimit = parseInt(o.userMaxLimit), o.expiredAt = "" + o.expiredAt, o.expiredAt && o.expiredAt.indexOf("-") > 0 ? o.expiredAt = new Date(o.expiredAt.replace(/-/g, "/") + " 23:59:59").getTime() : o.expiredAt = parseInt(o.expiredAt), o.simpleWebExpiredAt = "" + o.simpleWebExpiredAt, o.simpleWebExpiredAt && o.simpleWebExpiredAt.indexOf("-") > 0 ? o.simpleWebExpiredAt = new Date(o.simpleWebExpiredAt.replace(/-/g, "/") + " 23:59:59").getTime() : o.simpleWebExpiredAt = parseInt(o.simpleWebExpiredAt), Object(Mt["c"])("lastGenerateLicenseConfig", o), t.next = 14, l["a"].post("/generateLicense", o).then((function(e) {
                                            e.data.isSuccess && u["a"].$emit("showEditor", "生成授权", JSON.stringify(e.data.data, null, 4))
                                        }), (function(t) {
                                            throw e.$message.error("生成密钥失败 " + (t && t.toString())), t
                                        }));
                                    case 14:
                                        t.next = 17;
                                        break;
                                    case 16:
                                        return t.abrupt("return", !1);
                                    case 17:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    saveData: function(e, t) {
                        var o = this;
                        return Object(c["a"])(Object(s["a"])().mark((function n() {
                            var r;
                            return Object(s["a"])().wrap((function(n) {
                                while (1) switch (n.prev = n.next) {
                                    case 0:
                                        return n.next = 2, Lt(o.filePath, JSON.stringify(e), "__STORAGE__");
                                    case 2:
                                        r = n.sent, r && (o.localSelection = [], t || o.$message.success("操作成功"), o.loadList());
                                    case 4:
                                    case "end":
                                        return n.stop()
                                }
                            }), n)
                        })))()
                    }
                }
            },
            _t = Nt,
            Pt = (require("./utils/app/c9b1.js"), Object(b["a"])(_t, Dt, Et, !1, null, "53b78df8", null)),
            Gt = Pt.exports,
            Wt = function() {
                var e = this,
                    t = e._self._c;
                return e.$store.getters.isNormalPage ? t("el-dialog", {
                    class: e.isWebApp && !e.$store.getters.isNight ? "status-bar-light-bg-dialog" : "",
                    attrs: {
                        title: "HttpTTS管理",
                        visible: e.show,
                        width: e.dialogWidth,
                        top: e.dialogTop,
                        fullscreen: e.$store.state.miniInterface,
                        "before-close": e.cancel
                    },
                    on: {
                        "update:visible": function(t) {
                            e.show = t
                        }
                    }
                }, [t("div", {
                    staticClass: "custom-dialog-title",
                    attrs: {
                        slot: "title"
                    },
                    slot: "title"
                }, [t("span", {
                    staticClass: "el-dialog__title"
                }, [e._v("HttpTTS管理")])]), t("div", {
                    staticClass: "source-container table-container"
                }, [t("el-table", {
                    attrs: {
                        data: e.$store.state.httpTTS,
                        height: e.dialogContentHeight
                    },
                    on: {
                        "selection-change": function(t) {
                            e.localSelection = t
                        }
                    }
                }, [t("el-table-column", {
                    attrs: {
                        type: "selection",
                        width: "25",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "name",
                        "min-width": "150px",
                        label: "名称",
                        fixed: e.$store.state.miniInterface
                    }
                }), t("el-table-column", {
                    attrs: {
                        property: "url",
                        label: "链接",
                        "min-width": "150px"
                    }
                }), t("el-table-column", {
                    attrs: {
                        label: "操作",
                        width: "100px"
                    },
                    scopedSlots: e._u([{
                        key: "default",
                        fn: function(o) {
                            return [t("el-button", {
                                attrs: {
                                    type: "text"
                                },
                                on: {
                                    click: function(t) {
                                        return e.editItem(o.row)
                                    }
                                }
                            }, [e._v("编辑")])]
                        }
                    }], null, !1, 966691103)
                })], 1)], 1), t("div", {
                    staticClass: "dialog-footer",
                    attrs: {
                        slot: "footer"
                    },
                    slot: "footer"
                }, [t("div", [t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.deleteReplaceRules
                    }
                }, [e._v("批量删除"), e.localSelection.length ? t("span", [e._v(" (" + e._s(e.localSelection.length) + ")")]) : e._e()]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.uploadFile
                    }
                }, [e._v("导入")]), t("el-button", {
                    staticClass: "float-left",
                    attrs: {
                        type: "primary",
                        size: "medium"
                    },
                    on: {
                        click: e.addNew
                    }
                }, [e._v("添加")]), t("input", {
                    ref: "fileRef",
                    staticStyle: {
                        display: "none"
                    },
                    attrs: {
                        type: "file"
                    },
                    on: {
                        change: function(t) {
                            return e.onFileChange(t)
                        }
                    }
                })], 1)])]) : e._e()
            },
            zt = [],
            Ht = {
                model: {
                    prop: "show",
                    event: "setShow"
                },
                name: "HttpTTS",
                data: function() {
                    return {
                        localSelection: []
                    }
                },
                computed: Object(i["a"])({}, Object(h["b"])(["dialogWidth", "dialogTop", "dialogContentHeight"])),
                props: ["show"],
                watch: {
                    show: function(e) {}
                },
                methods: {
                    formatTableField: function(e, t, o) {
                        switch (t.property) {
                            default:
                                return o
                        }
                    },
                    cancel: function() {
                        this.$emit("setShow", !1)
                    },
                    deleteReplaceRules: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (e.localSelection.length) {
                                            t.next = 3;
                                            break
                                        }
                                        return e.$message.error("请选择需要删除的HttpTTS"), t.abrupt("return");
                                    case 3:
                                        return t.next = 5, e.$confirm("确认要删除所选择的HttpTTS吗?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (o = t.sent, o) {
                                            t.next = 8;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 8:
                                        l["a"].post(e.api + "/httpTTS/deleteMulti", e.localSelection).then((function(t) {
                                            t.data.isSuccess && (e.localSelection = [], e.$message.success("删除HttpTTS成功"), e.$root.$children[0].loadHttpTTS(!0))
                                        }), (function(t) {
                                            e.$message.error("删除HttpTTS失败 " + (t && t.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    editItem: function(e) {
                        this.showEditor(Object(i["a"])({}, e), "编辑HttpTTS")
                    },
                    addNew: function() {
                        this.showEditor({
                            name: "新增HttpTTS",
                            url: "",
                            contentType: "",
                            header: ""
                        }, "新增HttpTTS")
                    },
                    showEditor: function(e, t) {
                        var o = this;
                        u["a"].$emit("showEditor", t, JSON.stringify(e, null, 4), (function(e, t) {
                            try {
                                var n = JSON.parse(e);
                                if (!n.name) return void o.$message.error("名称不能为空");
                                if (!n.url) return void o.$message.error("链接不能为空");
                                l["a"].post(o.api + "/httpTTS/save", n).then((function(e) {
                                    e.data.isSuccess && (t(), o.$message.success("保存成功"), o.$root.$children[0].loadHttpTTS(!0))
                                }), (function(e) {
                                    o.$message.error("保存失败 " + (e && e.toString()))
                                }))
                            } catch (r) {
                                o.$message.error("必须是JSON格式")
                            }
                        }))
                    },
                    uploadFile: function() {
                        this.$refs.fileRef.dispatchEvent(new MouseEvent("click"))
                    },
                    onFileChange: function(e) {
                        var t = this,
                            o = e.target.files && e.target.files[0],
                            n = new FileReader;
                        n.onload = function(e) {
                            var o = e.target.result;
                            try {
                                var n = JSON.parse(o);
                                Array.isArray(n) && n.length && t.comfirmImport(n)
                            } catch (r) {
                                t.$message.error("HttpTTS文件错误")
                            }
                        }, n.onerror = function() {
                            var e = new FormData;
                            e.append("file", o), l["a"].post(t.api + "/readSourceFile", e, {
                                headers: {
                                    "Content-Type": "multipart/form-data"
                                }
                            }).then((function(e) {
                                if (e.data.isSuccess) {
                                    var o = [];
                                    e.data.data.forEach((function(e) {
                                        try {
                                            var t = JSON.parse(e);
                                            Array.isArray(t) && (o = o.concat(t))
                                        } catch (n) {}
                                    })), o.length ? t.comfirmImport(o) : t.$message.error("HttpTTS文件错误")
                                }
                            }), (function(e) {
                                t.$message.error("读取HttpTTS文件内容失败 " + (e && e.toString()))
                            }))
                        }, n.readAsText(o), this.$refs.fileRef.value = null
                    },
                    comfirmImport: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        return o.next = 2, t.$confirm("确认要导入文件中的".concat(e.length, "条HttpTTS吗?"), "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 2:
                                        if (n = o.sent, n) {
                                            o.next = 5;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 5:
                                        l["a"].post(t.api + "/httpTTS/saveMulti", e).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("导入HttpTTS成功"), t.$root.$children[0].loadHttpTTS(!0))
                                        }), (function(e) {
                                            t.$message.error("导入HttpTTS失败 " + (e && e.toString()))
                                        }));
                                    case 6:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    }
                }
            },
            Vt = Ht,
            Qt = (require("./utils/app/96b6.js"), Object(b["a"])(Vt, Wt, zt, !1, null, "75500020", null)),
            Kt = Qt.exports,
            Jt = require("0377"),
            qt = require("c197"),
            Yt = o.n(qt);
        require("f393"), require("a878"), require("./utils/app/82da.js");
        Date.prototype.format = function(e) {
            var t = {
                "M+": this.getMonth() + 1,
                "d+": this.getDate(),
                "h+": this.getHours(),
                "m+": this.getMinutes(),
                "s+": this.getSeconds(),
                "q+": Math.floor((this.getMonth() + 3) / 3),
                S: this.getMilliseconds()
            };
            for (var o in /(y+)/.test(e) && (e = e.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length))), t) new RegExp("(" + o + ")").test(e) && (e = e.replace(RegExp.$1, 1 == RegExp.$1.length ? t[o] : ("00" + t[o]).substr(("" + t[o]).length)));
            return e
        }, String.prototype.getBytesLength = function() {
            for (var e, t = 0, o = 0; o < this.length; o++) e = this.charCodeAt(o), e < 127 ? t++ : t += 128 <= e && e <= 2047 ? 2 : 2048 <= e && e <= 65535 ? 3 : 4;
            return t
        };
        var Zt = {
                name: "app",
                components: {
                    ImageViewer: f["a"],
                    ReplaceRule: w,
                    ReplaceRuleForm: y,
                    MPCode: $,
                    BookManage: G,
                    BookInfo: J,
                    UserManage: te,
                    UserForm: ce,
                    BookGroup: ge,
                    RssSourceList: Ae,
                    RssArticleList: Ie,
                    RssArticle: Fe,
                    SearchBookContent: _e,
                    Bookmark: Ve,
                    BookmarkForm: Ze,
                    FileManager: at,
                    BookCover: ht,
                    License: St,
                    BookConfig: Ot,
                    RemoteBookSourceSub: Ut,
                    ActiveLicense: Gt,
                    HttpTTS: Kt
                },
                data: function() {
                    return {
                        remember: !0,
                        loginForm: {
                            username: "",
                            password: "",
                            code: ""
                        },
                        showEditor: !1,
                        editorTitle: "编辑器",
                        editorContent: "",
                        showReplaceRuleDialog: !1,
                        showReplaceRuleForm: !1,
                        replaceRule: {},
                        isAddReplaceRule: !0,
                        showMPCodeDialog: !1,
                        showBookManageDialog: !1,
                        showBookInfoDialog: !1,
                        showUserManageDialog: !1,
                        showUserFormDialog: !1,
                        userForm: {},
                        showBookGroupDialog: !1,
                        isSetBookGroup: !1,
                        showRssSourceListDialog: !1,
                        showRssArticleListDialog: !1,
                        rssSource: {},
                        showRssArticleDialog: !1,
                        rssArticleInfo: {},
                        showSearchBookContentDialog: !1,
                        searchBook: {},
                        isLogin: !0,
                        showBookmarkDialog: !1,
                        showBookmarkForm: !1,
                        bookmark: {},
                        isAddBookmark: !0,
                        bookmarkInBook: {},
                        showFileManager: !1,
                        fileManagerHome: "",
                        fileManagerTitle: "文件管理",
                        showBookCoverDialog: !1,
                        bookCoverBook: {},
                        showLicenseDialog: !1,
                        showBookConfigDialog: !1,
                        showRemoteBookSourceSubDialog: !1,
                        showActiveLicenseDialog: !1,
                        showHttpTTSDialog: !1
                    }
                },
                beforeCreate: function() {
                    var e = this;
                    this.$store.dispatch("init"), this.$store.dispatch("syncFromLocalStorage"), this.$store.commit("setMiniInterface", Object(U["e"])()), document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), window.onresize = function() {
                        document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), e.$store.commit("setMiniInterface", Object(U["e"])()), e.$store.commit("setWindowSize", {
                            width: window.innerWidth,
                            height: window.innerHeight
                        }), e.$store.commit("setTouchable", "ontouchstart" in document)
                    };
                    var t = window.getQueryString("api");
                    t && this.$store.commit("setApi", t), (window.navigator.userAgent.indexOf("iPhone") >= 0 || window.navigator.userAgent.indexOf("iPad") >= 0) && (document.documentElement.style.setProperty("height", "100vh"), document.body.style.setProperty("height", "100vh"));
                    try {
                        var o = getComputedStyle(document.documentElement);
                        this.$store.commit("setSafeArea", {
                            top: 0 | o.getPropertyValue("--sat"),
                            bottom: 0 | o.getPropertyValue("--sab"),
                            left: 0 | o.getPropertyValue("--sal"),
                            right: 0 | o.getPropertyValue("--sar")
                        })
                    } catch (n) {}
                },
                created: function() {
                    var e = this;
                    return Object(c["a"])(Object(s["a"])().mark((function t() {
                        return Object(s["a"])().wrap((function(t) {
                            while (1) switch (t.prev = t.next) {
                                case 0:
                                    return e.$route && e.$route.query && e.$route.query.api && e.$store.commit("setApi", e.$route.query.api), window.matchMedia("(prefers-color-scheme: dark)").addEventListener("change", (function() {
                                        e.autoSetTheme(e.autoTheme)
                                    })), t.next = 4, e.tauriInit()["catch"]((function() {}));
                                case 4:
                                    e.getUserInfo().then((function() {
                                        e.$store.state.notAutoSync || e.restoreUserConfig(!0), e.$store.dispatch("syncFromLocalStorage"), e.autoSetTheme(e.autoTheme), e.init()
                                    })), e.loadTxtTocRules();
                                case 6:
                                case "end":
                                    return t.stop()
                            }
                        }), t)
                    })))()
                },
                beforeMount: function() {
                    var e = this;
                    this.setTheme(this.isNight), this.setMiniInterfaceClass(), this.setPageTypeClass(), this.setDocumentProperty(), this.eventBus = u["a"], u["a"].$on("showEditor", this.showEditorListener), u["a"].$on("showReplaceRuleForm", this.showReplaceRuleFormListener), u["a"].$on("showReplaceRuleDialog", (function() {
                        e.showReplaceRuleDialog = !0
                    })), u["a"].$on("showMPCodeDialog", (function() {
                        e.showMPCodeDialog = !0
                    })), u["a"].$on("showBookManageDialog", (function() {
                        e.showBookManageDialog = !0
                    })), u["a"].$on("showBookInfoDialog", (function(t) {
                        e.showBookInfo = t, e.showBookInfoDialog = !0
                    })), u["a"].$on("showUserManageDialog", (function() {
                        e.showUserManageDialog = !0
                    })), u["a"].$on("showUserFormDialog", (function(t) {
                        e.showUserFormDialog = !0, e.userForm = Object(i["a"])({}, t)
                    })), u["a"].$on("showBookGroupDialog", (function(t) {
                        e.showBookGroupDialog = !0, e.isSetBookGroup = !!t
                    })), u["a"].$on("showRssArticleListDialog", (function(t) {
                        e.showRssArticleListDialog = !0, e.rssSource = t
                    })), u["a"].$on("showRssSourceListDialog", (function() {
                        e.showRssSourceListDialog = !0
                    })), u["a"].$on("showRssArticleDialog", (function(t) {
                        e.showRssArticleDialog = !0, e.rssArticleInfo = t
                    })), u["a"].$on("showSearchBookContentDialog", (function(t) {
                        e.showSearchBookContentDialog = !0, e.searchBook = t
                    })), u["a"].$on("showBookmarkForm", (function(t, o, n) {
                        e.bookmark = t, e.isAddBookmark = o, e.bookmarkCallback = n, e.showBookmarkForm = !0
                    })), u["a"].$on("showBookmarkDialog", (function(t) {
                        e.showBookmarkDialog = !0, e.bookmarkInBook = t
                    })), u["a"].$on("showFileManagerDialog", (function(t, o) {
                        e.fileManagerHome = t, e.fileManagerTitle = o, e.showFileManager = !0
                    })), u["a"].$on("showBookCoverDialog", (function(t) {
                        e.showBookCoverDialog = !0, e.bookCoverBook = t
                    })), u["a"].$on("showBookConfigDialog", (function() {
                        e.showBookConfigDialog = !0
                    })), u["a"].$on("showLicenseDialog", (function() {
                        e.showLicenseDialog = !0
                    })), u["a"].$on("showRemoteBookSourceSubDialog", (function() {
                        e.showRemoteBookSourceSubDialog = !0
                    })), u["a"].$on("showActiveLicenseDialog", (function() {
                        e.showActiveLicenseDialog = !0
                    })), u["a"].$on("showHttpTTSDialog", (function() {
                        e.showHttpTTSDialog = !0
                    }))
                },
                mounted: function() {
                    document.documentElement.style.setProperty("--vh", "".concat(.01 * window.innerHeight, "px")), window.reader = this
                },
                computed: {
                    isNight: function() {
                        return this.$store.getters.isNight
                    },
                    autoTheme: function() {
                        return this.$store.getters.config.autoTheme
                    },
                    dialogWidth: function() {
                        return this.$store.getters.dialogSmallWidth
                    },
                    dialogTop: function() {
                        return this.$store.getters.dialogTop
                    },
                    showLogin: {
                        get: function() {
                            return this.$store.state.showLogin
                        },
                        set: function(e) {
                            this.$store.commit("setShowLogin", e)
                        }
                    },
                    connected: function() {
                        return this.$store.state.connected
                    },
                    showBookInfo: {
                        get: function() {
                            return this.$store.state.showBookInfo
                        },
                        set: function(e) {
                            this.$store.commit("setShowBookInfo", e)
                        }
                    }
                },
                watch: {
                    isNight: function(e) {
                        this.setTheme(e)
                    },
                    autoTheme: function(e) {
                        this.autoSetTheme(e)
                    },
                    miniInterface: function() {
                        this.setMiniInterfaceClass()
                    },
                    connected: function(e) {
                        e && (window.customCSSLoad || window.loadLink(this.$store.getters.customCSSUrl, (function() {
                            window.customCSSLoad = !0
                        })))
                    },
                    "$store.state.config.pageType": function() {
                        this.setPageTypeClass()
                    },
                    "$store.state.config.horizontalPadding": function() {
                        this.setDocumentProperty()
                    },
                    "$store.state.config.topPadding": function() {
                        this.setDocumentProperty()
                    },
                    "$store.state.config.bottomPadding": function() {
                        this.setDocumentProperty()
                    },
                    showReplaceRuleForm: function(e) {
                        e || this.replaceRuleCallback && (this.replaceRuleCallback(), this.replaceRuleCallback = null)
                    },
                    showBookmarkForm: function(e) {
                        e || this.bookmarkCallback && (this.bookmarkCallback(), this.bookmarkCallback = null)
                    },
                    showLogin: function(e) {
                        e || (this.isLogin = !0)
                    }
                },
                methods: {
                    tauriInit: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o, n, r;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (!window.__TAURI__ || !window.__TAURI__.invoke) {
                                            t.next = 14;
                                            break
                                        }
                                        return t.next = 3, window.__TAURI__.invoke("is_server_running")["catch"]((function() {
                                            return !1
                                        }));
                                    case 3:
                                        if (o = t.sent, n = function() {
                                                var t = Object(c["a"])(Object(s["a"])().mark((function t() {
                                                    var o;
                                                    return Object(s["a"])().wrap((function(t) {
                                                        while (1) switch (t.prev = t.next) {
                                                            case 0:
                                                                return t.next = 2, window.__TAURI__.invoke("get_server_port")["catch"]((function() {
                                                                    return !1
                                                                }));
                                                            case 2:
                                                                o = t.sent, o && e.$store.commit("setApi", "http://localhost:".concat(o, "/reader3/"));
                                                            case 4:
                                                            case "end":
                                                                return t.stop()
                                                        }
                                                    }), t)
                                                })));
                                                return function() {
                                                    return t.apply(this, arguments)
                                                }
                                            }(), o) {
                                            t.next = 12;
                                            break
                                        }
                                        return r = e.$loading({
                                            lock: !0,
                                            text: "正在启动服务...",
                                            spinner: "el-icon-loading",
                                            background: "rgba(0, 0, 0, 0.7)"
                                        }), t.next = 9, window.__TAURI__.invoke("start_server")["catch"]((function(t) {
                                            e.$message.error(t), e.$nextTick((function() {
                                                e.$router.push("/setting")
                                            }))
                                        })).then(n);
                                    case 9:
                                        r.close(), t.next = 14;
                                        break;
                                    case 12:
                                        return t.next = 14, n();
                                    case 14:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    autoSetTheme: function(e) {
                        e && (window.matchMedia("(prefers-color-scheme: dark)").matches ? this.$store.commit("setNightTheme", !0) : this.$store.commit("setNightTheme", !1))
                    },
                    setTheme: function(e) {
                        document.body.className = e ? (document.body.className || "").replace("night-theme", "") + " night-theme" : (document.body.className || "").replace("night-theme", "")
                    },
                    setMiniInterfaceClass: function() {
                        this.$store.state.miniInterface ? document.body.className = (document.body.className || "").replace("mini-interface", "") + " mini-interface" : document.body.className = (document.body.className || "").replace("mini-interface", "")
                    },
                    setPageTypeClass: function() {
                        this.$store.getters.isKindlePage ? document.body.className = (document.body.className || "").replace("kindle-page", "") + " kindle-page" : document.body.className = (document.body.className || "").replace("kindle-page", "")
                    },
                    setDocumentProperty: function() {
                        if (document.documentElement.style.setProperty("--horizontal-padding", "".concat(0 | this.$store.state.config.horizontalPadding, "px")), document.documentElement.style.setProperty("--top-padding", "".concat(0 | this.$store.state.config.topPadding, "px")), document.documentElement.style.setProperty("--bottom-padding", "".concat(0 | this.$store.state.config.bottomPadding, "px")), window.contentCom) try {
                            window.contentCom.syncCSSProperty()
                        } catch (e) {}
                    },
                    cancel: function() {
                        this.showLogin = !1, this.loginForm = {
                            username: "",
                            password: "",
                            code: ""
                        }
                    },
                    login: function() {
                        var e = this;
                        return Object(c["a"])(Object(s["a"])().mark((function t() {
                            var o;
                            return Object(s["a"])().wrap((function(t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        if (!e.isLogining) {
                                            t.next = 2;
                                            break
                                        }
                                        return t.abrupt("return");
                                    case 2:
                                        return e.isLogining = !0, t.next = 5, l["a"].post("/login", Object(i["a"])(Object(i["a"])({}, e.loginForm), {}, {
                                            isLogin: e.isLogin
                                        }));
                                    case 5:
                                        o = t.sent, o.data.isSuccess && (e.$store.commit("setShowLogin", !1), e.$nextTick((function() {
                                            e.$store.commit("setLoginAuth", !0)
                                        })), e.remember && o.data.data && o.data.data.accessToken && e.$store.commit("setToken", o.data.data.accessToken), e.getUserInfo().then((function() {
                                            e.$store.dispatch("syncFromLocalStorage"), e.init(!0)
                                        }))), e.isLogining = !1;
                                    case 8:
                                    case "end":
                                        return t.stop()
                                }
                            }), t)
                        })))()
                    },
                    init: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (!t.initing) {
                                            o.next = 3;
                                            break
                                        }
                                        return e && setTimeout((function() {
                                            t.init(e)
                                        }), 100), o.abrupt("return");
                                    case 3:
                                        if (t.initing = !0, !e && t.$store.state.shelfBooks.length) {
                                            o.next = 7;
                                            break
                                        }
                                        return o.next = 7, t.loadBookShelf()["catch"]((function() {
                                            t.initing = !1
                                        }));
                                    case 7:
                                        return o.next = 9, Promise.all([t.loadBookSource(e), t.loadBookGroup(e), t.loadRssSources(e), t.loadReplaceRules(e), t.loadBookmarks(e), t.loadHttpTTS(e)]);
                                    case 9:
                                        t.initing = !1;
                                    case 10:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    getUserInfo: function() {
                        var e = this;
                        return Object(U["g"])((function() {
                            return l["a"].get(e.api + "/getUserInfo")
                        }), "userInfo").then((function(t) {
                            t.data.isCache ? e.$store.commit("setConnected", !1) : e.$store.commit("setConnected", !0), t.data.isSuccess && (e.$store.commit("setIsSecureMode", t.data.data.secure), t.data.data.secure && t.data.data.secureKey && e.$store.commit("setShowManagerMode", !0), t.data.data.userInfo && e.$store.commit("setUserInfo", t.data.data.userInfo))
                        }), (function(t) {
                            e.$message.error("加载用户信息失败 " + (t && t.toString()))
                        }))
                    },
                    loadTxtTocRules: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setTxtTocRules", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get("/getTxtTocRules")
                        }), "txtTocRules", e, o).then(o, (function(e) {
                            t.$message.error("加载txt章节规则失败 " + (e && e.toString()))
                        }))
                    },
                    loadHttpTTS: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setHttpTTS", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get("/httpTTS/list")
                        }), "httpTTS", e, o).then(o, (function(e) {
                            t.$message.error("加载httpTTS失败 " + (e && e.toString()))
                        }))
                    },
                    showEditorListener: function(e, t, o) {
                        var n = this;
                        this.editorTitle = e, this.editorContent = t, this.showEditor = !0, this.callback = o, this.$nextTick((function() {
                            n.initEditor()
                        }))
                    },
                    showReplaceRuleFormListener: function(e, t, o) {
                        this.replaceRule = e, this.isAddReplaceRule = t, this.replaceRuleCallback = o, this.showReplaceRuleForm = !0
                    },
                    loadBookShelf: function(e, t) {
                        var o = this;
                        return t = t || this.api, Object(U["g"])((function() {
                            return l["a"].get(t + "/getBookshelf?refresh=" + (e ? 1 : 0), {
                                timeout: 3e5
                            })
                        }), "getBookshelf@" + this.currentUserName).then((function(e) {
                            e.data.isCache ? o.$store.commit("setConnected", !1) : o.$store.commit("setConnected", !0), e.data.isSuccess && (o.$store.commit("setShelfBooks", e.data.data), u["a"].$emit("shelfBooksLoaded", e.data.data))
                        }))["catch"]((function(e) {
                            throw o.$store.commit("setConnected", !1), o.$message.error("后端连接失败 " + (e && e.toString())), e
                        }))
                    },
                    loadBookGroup: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setBookGroupList", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get(t.api + "/getBookGroups")
                        }), "bookGroup@" + this.currentUserName, e, o).then(o, (function(e) {
                            t.$message.error("加载分组列表失败 " + (e && e.toString()))
                        }))
                    },
                    loadRssSources: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setRssSourceList", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get(t.api + "/getRssSources", {
                                params: {
                                    simple: 1
                                }
                            })
                        }), "rssSources@" + this.currentUserName, e).then(o, (function(e) {
                            t.$message.error("加载RSS订阅列表失败 " + (e && e.toString()))
                        }))
                    },
                    loadBookSource: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setBookSourceList", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get(t.api + "/getBookSources", {
                                params: {
                                    simple: 1
                                }
                            })
                        }), "bookSourceList@" + this.currentUserName, e, o).then(o, (function(e) {
                            t.$message.error("加载书源列表失败 " + (e && e.toString()))
                        }))
                    },
                    loadReplaceRules: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setFilterRules", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get(t.api + "/getReplaceRules")
                        }), "replaceRule@" + this.currentUserName, e, o).then(o, (function(e) {
                            t.$message.error("加载替换规则失败 " + (e && e.toString()))
                        }))
                    },
                    loadBookmarks: function(e) {
                        var t = this,
                            o = function(e) {
                                e.data.isSuccess && t.$store.commit("setBookmarks", e.data.data || [])
                            };
                        return Object(U["i"])((function() {
                            return l["a"].get(t.api + "/getBookmarks")
                        }), "bookmark@" + this.currentUserName, e, o).then(o, (function(e) {
                            t.$message.error("加载书签失败 " + (e && e.toString()))
                        }))
                    },
                    isInShelf: function(e, t) {
                        var o = this;
                        return Object(c["a"])(Object(s["a"])().mark((function n() {
                            var r, a;
                            return Object(s["a"])().wrap((function(n) {
                                while (1) switch (n.prev = n.next) {
                                    case 0:
                                        if (e && e.bookUrl && e.origin) {
                                            n.next = 3;
                                            break
                                        }
                                        return o.$message.error("书籍信息错误"), n.abrupt("return", !1);
                                    case 3:
                                        if (r = o.$store.getters.shelfBooks.find((function(t) {
                                                return t.bookUrl === e.bookUrl
                                            })), r) {
                                            n.next = 12;
                                            break
                                        }
                                        if (!t) {
                                            n.next = 12;
                                            break
                                        }
                                        return n.next = 8, o.$confirm(t, "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 8:
                                        if (a = n.sent, a) {
                                            n.next = 11;
                                            break
                                        }
                                        return n.abrupt("return", !1);
                                    case 11:
                                        return n.abrupt("return", l["a"].post(o.api + "/saveBook", e).then((function(e) {
                                            if (e.data.isSuccess) return !0
                                        }), (function() {
                                            return o.$message.error("导入书籍失败"), !1
                                        })));
                                    case 12:
                                        return n.abrupt("return", !!r);
                                    case 13:
                                    case "end":
                                        return n.stop()
                                }
                            }), n)
                        })))()
                    },
                    getBookContent: function(e, t, o, n, r) {
                        var a = this;
                        r = r || {
                            name: this.$store.getters.readingBook.name,
                            author: this.$store.getters.readingBook.author,
                            bookUrl: this.$store.getters.readingBook.bookUrl
                        };
                        var s = {
                            url: r.bookUrl,
                            index: e
                        };
                        return o && (s.refresh = 1), n && (s.cache = 1), Object(U["b"])((function() {
                            return l["a"].post(a.api + "/getBookContent", s, Object(i["a"])({
                                timeout: 1e3 * a.$store.getters.config.chapterRequestTimeout
                            }, t))
                        }), r.name + "_" + r.author + "@" + r.bookUrl + "@chapterContent-" + e, o)
                    },
                    initEditor: function() {
                        var e = this,
                            t = this.$refs.editorRef;
                        t || setTimeout((function() {
                            e.initEditor()
                        }), 10);
                        try {
                            this.jar = Object(Jt["a"])(t, Yt.a.highlightElement, {
                                tab: "\t"
                            }), this.jar.updateCode(this.editorContent), this.jar.onUpdate((function(t) {
                                e.editorContent = t
                            }))
                        } catch (o) {}
                    },
                    closeEditor: function() {
                        this.jar && this.jar.destroy && this.jar.destroy(), this.showEditor = !1, this.editorTitle = "", this.editorContent = "", this.callback = null
                    },
                    saveEditor: function() {
                        var e = this;
                        this.callback && this.callback(this.editorContent, (function() {
                            e.closeEditor()
                        }))
                    },
                    closeViewer: function() {
                        this.$store.commit("setPreviewImgList", !1)
                    },
                    saveUserConfig: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n, r;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (window.localStorage) {
                                            o.next = 3;
                                            break
                                        }
                                        return t.$message.error("当前终端不支持localStorage"), o.abrupt("return");
                                    case 3:
                                        if (e) {
                                            o.next = 9;
                                            break
                                        }
                                        return o.next = 6, t.$confirm("确认要备份当前终端的阅读配置、书架设置、搜索设置、自定义配置方案、朗读配置吗?", "提示")["catch"]((function() {
                                            return !1
                                        }));
                                    case 6:
                                        if (n = o.sent, n) {
                                            o.next = 9;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 9:
                                        r = {}, ["config", "shelfConfig", "searchConfig", "customConfigList", "speechVoiceConfig"].forEach((function(e) {
                                            var t = Object(Mt["b"])(e);
                                            t && (r[e] = t)
                                        })), l["a"].post(t.api + "/saveUserConfig", r).then((function(e) {
                                            e.data.isSuccess && t.$message.success("备份配置成功")
                                        }), (function(e) {
                                            t.$message.error("备份配置失败 " + (e && e.toString()))
                                        }));
                                    case 12:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    restoreUserConfig: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (window.localStorage) {
                                            o.next = 3;
                                            break
                                        }
                                        return t.$message.error("当前终端不支持localStorage"), o.abrupt("return");
                                    case 3:
                                        if (e) {
                                            o.next = 9;
                                            break
                                        }
                                        return o.next = 6, t.$confirm("确认要从备份文件中恢复当前终端的阅读配置、书架设置、搜索设置、自定义配置方案、朗读方案吗?", "提示")["catch"]((function() {
                                            return !1
                                        }));
                                    case 6:
                                        if (n = o.sent, n) {
                                            o.next = 9;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 9:
                                        l["a"].get(t.api + "/getUserConfig").then((function(e) {
                                            if (e.data.isSuccess) {
                                                for (var o in e.data.data) Object.hasOwnProperty.call(e.data.data, o) && Object(Mt["c"])(o, e.data.data[o]);
                                                t.$store.dispatch("syncFromLocalStorage"), t.$message.success("同步配置成功")
                                            }
                                        }), (function(e) {
                                            t.$message.error("同步配置失败 " + (e && e.toString()))
                                        }));
                                    case 10:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    },
                    deleteBook: function(e) {
                        var t = this;
                        return Object(c["a"])(Object(s["a"])().mark((function o() {
                            var n;
                            return Object(s["a"])().wrap((function(o) {
                                while (1) switch (o.prev = o.next) {
                                    case 0:
                                        if (e && (e.name || e.bookUrl)) {
                                            o.next = 3;
                                            break
                                        }
                                        return t.$message.error("书籍信息错误"), o.abrupt("return");
                                    case 3:
                                        return o.next = 5, t.$confirm("此操作将删除书籍信息以及阅读进度, 是否继续?", "提示", {
                                            confirmButtonText: "确定",
                                            cancelButtonText: "取消",
                                            type: "warning"
                                        })["catch"]((function() {
                                            return !1
                                        }));
                                    case 5:
                                        if (n = o.sent, n) {
                                            o.next = 8;
                                            break
                                        }
                                        return o.abrupt("return");
                                    case 8:
                                        l["a"].post(t.api + "/deleteBook", e).then((function(e) {
                                            e.data.isSuccess && (t.$message.success("删除成功"), t.loadBookShelf())
                                        }), (function(e) {
                                            t.$message.error("删除失败 " + (e && e.toString()))
                                        }));
                                    case 9:
                                    case "end":
                                        return o.stop()
                                }
                            }), o)
                        })))()
                    }
                }
            },
            Xt = Zt,
            eo = (require("./utils/app/6b7cd.js"), require("./utils/app/fb25.js"), Object(b["a"])(Xt, r, a, !1, null, null, null)),
            to = eo.exports,
            oo = require("8c4f");
        n["default"].use(oo["a"]);
        var no = oo["a"].prototype.push;
        oo["a"].prototype.push = function(e) {
            return no.call(this, e)["catch"]((function(e) {
                return e
            }))
        };
        var ro = [{
                path: "/",
                name: "index",
                component: function() {
                    return Promise.all([o.e("index~reader"), o.e("index")]).then(o.bind(null, "d504"))
                }
            }, {
                path: "/setting",
                name: "Setting",
                component: function() {
                    return o.e("setting").then(o.bind(null, "4ef5"))
                }
            }, {
                path: "/reader",
                name: "Reader",
                component: function() {
                    return Promise.all([o.e("index~reader"), o.e("reader")]).then(o.bind(null, "968f"))
                }
            }],
            ao = new oo["a"]({
                base: "",
                routes: ro
            }),
            io = ao,
            so = (require("0fb7"), require("450d"), require("f529")),
            co = o.n(so),
            lo = (require("9e1f"), require("6ed5")),
            uo = o.n(lo),
            fo = (require("960d"), require("defb")),
            po = o.n(fo),
            ho = (require("bd49"), require("18ff")),
            mo = o.n(ho),
            go = (require("cb70"), require("b370")),
            bo = o.n(go),
            ko = (require("e612"), require("dd87")),
            wo = o.n(ko),
            vo = (require("075a"), require("72aa")),
            So = o.n(vo),
            Ao = (require("e2f3"), require("06f9")),
            xo = o.n(Ao),
            Bo = (require("acb6"), require("c673")),
            Co = o.n(Bo),
            yo = (require("9d4c"), require("e450")),
            Oo = o.n(yo),
            Io = (require("672e"), require("101e")),
            To = o.n(Io),
            jo = (require("3c52"), require("0d7b")),
            Lo = o.n(jo),
            $o = (require("fe07"), require("6ac5")),
            Ro = o.n($o),
            Fo = (require("fd71"), require("a447")),
            Uo = o.n(Fo),
            Do = (require("e960"), require("b35b")),
            Eo = o.n(Do),
            Mo = (require("eca7"), require("3787")),
            No = o.n(Mo),
            _o = (require("425f"), require("4105")),
            Po = o.n(_o),
            Go = (require("b5c2"), require("20cf")),
            Wo = o.n(Go),
            zo = (require("a586"), require("7464")),
            Ho = o.n(zo),
            Vo = (require("d4df"), require("7fc1")),
            Qo = o.n(Vo),
            Ko = (require("560b"), require("dcdc")),
            Jo = o.n(Ko),
            qo = (require("a7cc"), require("df33")),
            Yo = o.n(qo),
            Zo = (require("b0ee"), require("d180")),
            Xo = o.n(Zo),
            en = (require("a335"), require("c0bb")),
            tn = o.n(en),
            on = (require("be4f"), require("896a")),
            nn = o.n(on),
            rn = (require("cbb5"), require("8bbc")),
            an = o.n(rn),
            sn = (require("6611"), require("e772")),
            cn = o.n(sn),
            ln = (require("1f1a"), require("4e4b")),
            un = o.n(ln),
            fn = (require("10cb"), require("f3ad")),
            dn = o.n(fn),
            pn = (require("06f1"), require("6ac9")),
            hn = o.n(pn),
            mn = (require("5466"), require("ecdf")),
            gn = o.n(mn),
            bn = (require("38a0"), require("ad41")),
            kn = o.n(bn),
            wn = (require("b84d"), require("c216")),
            vn = o.n(wn),
            Sn = (require("8f24"), require("76b9")),
            An = o.n(Sn),
            xn = (require("e3ea"), require("7bc3")),
            Bn = o.n(xn),
            Cn = (require("1951"), require("eedf")),
            yn = o.n(Cn);
        require("cca6");
        n["default"].use(yn.a), n["default"].use(Bn.a), n["default"].use(An.a), n["default"].use(vn.a), n["default"].use(kn.a), n["default"].use(gn.a), n["default"].use(hn.a), n["default"].use(dn.a), n["default"].use(un.a), n["default"].use(cn.a), n["default"].use(an.a), n["default"].use(nn.a.directive), n["default"].use(tn.a), n["default"].use(Xo.a), n["default"].use(Yo.a), n["default"].use(Jo.a), n["default"].use(Qo.a), n["default"].use(Ho.a), n["default"].use(Wo.a), n["default"].use(Po.a), n["default"].use(No.a), n["default"].use(Eo.a), n["default"].use(Uo.a), n["default"].use(Ro.a), n["default"].use(Lo.a), n["default"].use(To.a), n["default"].use(Oo.a), n["default"].use(Co.a), n["default"].use(xo.a), n["default"].use(So.a), n["default"].use(wo.a), n["default"].use(bo.a), n["default"].use(mo.a), n["default"].use(po.a), n["default"].prototype.$msgbox = uo.a, n["default"].prototype.$message = Object.assign({}, co.a, {
            info: function(e, t) {
                var o = "string" === typeof e ? {
                    message: e
                } : e;
                o.duration = t || 1e3, co.a.info(o)
            },
            error: function(e, t) {
                var o = "string" === typeof e ? {
                    message: e
                } : e;
                o.duration = t || 2e3, co.a.error(o)
            },
            success: function(e, t) {
                var o = "string" === typeof e ? {
                    message: e
                } : e;
                o.duration = t || 1e3, co.a.success(o)
            }
        }), n["default"].prototype.$ELEMENT = {
            zIndex: 2100
        }, n["default"].prototype.$alert = uo.a.alert, n["default"].prototype.$confirm = uo.a.confirm, n["default"].prototype.$prompt = uo.a.prompt, n["default"].prototype.$loading = nn.a.service;
        var On = require("./utils/app/1a58.js"),
            In = (require("./utils/app/be72.js"), require("9483"));

        function Tn() {
            try {
                window.getQueryString("nopwa") || Object(In["a"])("".concat("", "service-worker.js"), {
                    ready: function() {
                        window.serviceWorkerReady = !0
                    },
                    registered: function(e) {
                        if (window.localStorage) {
                            var t = window.localStorage.getItem("READER_APP_BUILD_VERSION"),
                                o = "v3.2.14-05250935";
                            t !== o && (e.active.postMessage({
                                type: "SKIP_WAITING"
                            }), window.localStorage.setItem("READER_APP_BUILD_VERSION", o))
                        }
                    }
                })
            } catch (e) {}
        }
        var jn = require("./utils/app/3e1c.js"),
            Ln = o.n(jn),
            $n = require("caf9"),
            Rn = require("53ca"),
            Fn = Object.prototype.hasOwnProperty;

        function Un(e) {
            return "[Throws: " + (e ? e.message : "?") + "]"
        }

        function Dn(e, t) {
            if (Fn.call(e, t)) try {
                return e[t]
            } catch (o) {
                return Un(o)
            }
            return e[t]
        }

        function En(e) {
            var t = [];

            function o(e) {
                if (null === e || "object" !== Object(Rn["a"])(e)) return e;
                if (-1 !== t.indexOf(e)) return "[Circular]";
                if (t.push(e), "function" === typeof e.toJSON) try {
                    var n = o(e.toJSON());
                    return t.pop(), n
                } catch (i) {
                    return Un(i)
                }
                if (Array.isArray(e)) {
                    var r = e.map(o);
                    return t.pop(), r
                }
                var a = Object.keys(e).reduce((function(t, n) {
                    return t[n] = o(Dn(e, n)), t
                }), {});
                return t.pop(), a
            }
            return o(e)
        }
        var Mn = function(e, t, o) {
                return JSON.stringify(En(e), t, o)
            },
            Nn = require("a002"),
            _n = o.n(Nn);
        try {
            require("eb2f")
        } catch (Pn) {
            console.error(Pn)
        }
        try {
            window.location.href.indexOf("errorAlert") > 0 && (window.errorAlert = !0), window.onerror = function(e, t, o, n, r) {
                window.errorAlert && window.alert(Mn({
                    event: e,
                    source: t,
                    lineno: o,
                    colno: n,
                    error: r
                }))
            }, window.addEventListener("unhandledrejection", (function(e) {
                window.errorAlert && window.alert(Mn(e))
            })), n["default"].config.errorHandler = function(e) {
                window.errorAlert && window.alert(Mn(e)), console.error(e)
            }, window.$cacheStorage = _n.a.createInstance({
                name: "cacheStorage"
            }), Tn(), n["default"].config.productionTip = !1, n["default"].use($n["a"], {
                observer: !0,
                preLoad: 1.8
            }), n["default"].mixin({
                computed: {
                    api: function() {
                        return this.$store.getters.api
                    },
                    isWebApp: function() {
                        return window.navigator.standalone
                    },
                    isPWA: function() {
                        return ["fullscreen", "standalone", "minimal-ui"].some((function(e) {
                            return window.matchMedia("(display-mode: " + e + ")").matches
                        }))
                    },
                    isNightTheme: function() {
                        return this.$store.getters.isNight
                    },
                    currentUserName: function() {
                        return this.$store.getters.currentUserName
                    }
                },
                methods: {
                    getImagePath: function(e, t) {
                        return e && (e.startsWith("http://") || e.startsWith("https://") || e.startsWith("//")) ? t && window.serviceWorkerReady || "noProxy" === this.$store.state.shelfConfig.imageProxy ? e : this.api + "/cover?path=" + e : !!e && this.$store.getters.apiRoot + e
                    },
                    getCover: function(e, t, o) {
                        return e = this.getImagePath(e, o), e ? t ? e : {
                            src: e,
                            error: lt.a
                        } : lt.a
                    },
                    getImage: function(e, t, o) {
                        return e = this.getImagePath(e, o), e ? t ? e : {
                            src: e,
                            error: lt.a
                        } : Ln.a
                    },
                    currentDateTime: function() {
                        var e = new Date,
                            t = function(e) {
                                return e < 10 ? "0" + e : e
                            };
                        return e.getFullYear() + t(e.getMonth() + 1) + t(e.getDate()) + "_" + t(e.getHours()) + t(e.getMinutes()) + t(e.getSeconds())
                    },
                    dateFormat: function(e) {
                        var t = (new Date).getTime(),
                            o = parseInt((t - e) / 1e3),
                            n = "";
                        return n = o <= 30 ? "刚刚" : o < 60 ? o + "秒前" : o < 3600 ? parseInt(o / 60) + "分钟前" : o < 86400 ? parseInt(o / 3600) + "小时前" : o < 2592e3 ? parseInt(o / 86400) + "天前" : o < 31536e3 ? parseInt(o / 2592e3) + "月前" : parseInt(o / 31536e3) + "年前", n
                    },
                    renderForm: function(e, t, o, r) {
                        this.$createElement;
                        n["default"].component(e, {
                            render: function() {
                                var e = this,
                                    t = arguments[0];
                                return t("div", {
                                    style: {
                                        textAlign: "center"
                                    }
                                }, [o.map((function(o) {
                                    switch (o.type) {
                                        case "input":
                                            return t("div", {
                                                class: "form-item"
                                            }, [t("span", {
                                                style: {
                                                    display: "inline-block",
                                                    width: "85px"
                                                }
                                            }, [o.label, "："]), t("el-input", {
                                                attrs: {
                                                    size: "mini"
                                                },
                                                style: {
                                                    width: "172px"
                                                },
                                                model: {
                                                    value: e.info[o.name],
                                                    callback: function(t) {
                                                        e.$set(e.info, o.name, t)
                                                    }
                                                }
                                            })]);
                                        case "select":
                                            return t("div", {
                                                class: "form-item"
                                            }, [t("span", {
                                                style: {
                                                    display: "inline-block",
                                                    width: "85px"
                                                }
                                            }, [o.label, "："]), t("el-select", {
                                                attrs: {
                                                    size: "mini",
                                                    filterable: !0,
                                                    multiple: "undefined" === typeof o.multiple || o.multiple,
                                                    placeholder: o.placeholder || ""
                                                },
                                                model: {
                                                    value: e.info[o.name],
                                                    callback: function(t) {
                                                        e.$set(e.info, o.name, t)
                                                    }
                                                }
                                            }, [o.options.map((function(e, n) {
                                                return t("el-option", {
                                                    key: o.name + "-op-" + n,
                                                    attrs: {
                                                        label: e.label,
                                                        value: e.value
                                                    }
                                                })
                                            }))])]);
                                        default:
                                            return null
                                    }
                                }))])
                            },
                            data: function() {
                                return {
                                    info: t,
                                    items: o
                                }
                            },
                            watch: {
                                info: {
                                    handler: function(e) {
                                        r(e)
                                    },
                                    deep: !0
                                }
                            }
                        });
                        var a = n["default"].component(e);
                        return this.$createElement(a)
                    },
                    nearBottom: function(e) {
                        return document.documentElement.clientHeight + (document.documentElement.scrollTop || document.body.scrollTop) >= document.documentElement.scrollHeight - e
                    }
                }
            }), new n["default"]({
                router: io,
                store: On["a"],
                render: function(e) {
                    return e(to)
                }
            }).$mount("#app")
        } catch (Pn) {
            alert(Pn.stack)
        }
    });
