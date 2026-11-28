var render = function() {
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
                        src: o("3383"),
                        fit: "cover",
                        lazy: ""
                    }
                })], 1)
            };
