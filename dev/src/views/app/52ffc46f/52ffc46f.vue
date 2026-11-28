<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=52ffc46f；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="关注公众号【假装大佬】" :visible="show" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><el-image class="qrcode-img" :src='"img/mpcode.560264c9.jpg"' fit="cover" lazy="" /></el-dialog>
</template>

<script>import { merge as __merge } from '@/utils/merge'

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'MPCode',
  data: function() {
      return {}
  },
  props: ["show"],
  computed: {},
  methods: {
      cancel: function() {
          this.$emit("setShow", !1)
      }
  },
  render: function() {
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
                        src: "img/mpcode.560264c9.jpg",
                        fit: "cover",
                        lazy: ""
                    }
                })], 1)
            },
  staticRenderFns: []
}
</script>

<style scoped>
.qrcode-img { display:block;margin:0 auto }
</style>
