var render = function() {
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
            };
