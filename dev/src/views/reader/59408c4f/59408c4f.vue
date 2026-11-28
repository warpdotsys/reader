<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=reader scopeId=59408c4f；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="settings-wrapper" :class="{night: $store.getters.isNight, day: !$store.getters.isNight}" :style="popupTheme"><div class="settings-title"> 设置 <div class="title-btn" @click="resetConfig">重置</div><div class="title-btn" @click="restoreConfig">同步</div><div class="title-btn" @click="backupConfig">保存</div><div class="title-btn" @click="toggleNotAutoSync">{{ " " + _s(notAutoSync ? "自动同步" : "取消自动同步") + " " }}</div></div><div class="setting-list"><ul><li><span class="setting-item-title">配置方案</span><div class="selection-zone"><template v-for="(n, a) in $store.state.customConfigList"><span :key="a" class="span-item" :class="{selected: $store.getters.config.customConfig === n.name}" @click="(t) => {
                                    return setCustomConfig(n)
                                }"><span>{{ n.name }}</span><template v-if="a > 1 && $store.getters.config.customConfig !== n.name"><i class="el-icon-close delete-custom-config-icon" @click="(t) => {
                                    return t.stopPropagation(), deleteCustomConfig(a, n.name)
                                }" /></template></span></template><span :key='"addNewCustomConfig"' class="span-item" @click="addNewCustomConfig">新增方案</span><span :key='"autoTheme"' ref='"themes"' class="span-item" :class="{selected: $store.getters.config.autoTheme}" @click="setAutoTheme">自动切换</span></div></li><li><span class="setting-item-title">方案类型</span><div class="selection-zone"><template v-for="(n, a) in configDefaultTypeList"><span :key="a" class="span-item" :class="{selected: currentCustomConfig.configDefaultType === n}" @click="(t) => {
                                    return setConfigDefaultType(n)
                                }">{{ n }}</span></template></div></li><li><span class="setting-item-title">阅读主题</span><div class="selection-zone"><template v-for="(n, a) in themeColors"><span :key="a" ref='"themes"' class="theme-item" :class="{selected: config.theme === a}" :style="n" @click='(t) => {
                                    return setConfig("theme", a)
                                }'><template v-if="6 != a"><em class="iconfont"></em></template><template v-else><em class="moon-icon">{{ moonIcon }}</em></template></span></template><span :key='"custom"' ref='"themes"' class="span-item" :class='{selected: "custom" === config.theme}' @click='(t) => {
                                return setConfig("theme", "custom")
                            }'>自定义</span></div></li><template v-if='"custom" === config.theme'><li><span class="setting-item-title">自定义</span><div class="custom-theme"><div class="custom-theme-title"><span class="custom-theme-title">主题模式</span><template v-for="(n, a) in themeTypes"><span :key="a" class="span-item" :class="{selected: themeType == n}" @click='(t) => {
                                    return setConfig("themeType", n)
                                }'>{{ "day" === n ? "白天" : "黑夜" }}</span></template></div><span class="custom-theme-title">页面背景颜色 <el-color-picker v-model="config.bodyColor" /></span><span class="custom-theme-title">浮窗背景颜色 <el-color-picker v-model="config.popupColor" /></span><span class="custom-theme-title">阅读背景颜色 <el-color-picker v-model="config.contentColor" /></span><span class="custom-theme-title">阅读背景图片 <template v-for="(n, a) in builtinBG"><img :key="a" class="content-bg-preview" :class="{selected: $store.getters.config.contentBGImg == n.src}" :src="n.src" alt="" @click="(t) => {
                                    return setBGImg(n.src)
                                }" /></template><template v-for="n in $store.getters.config.customBGImgList || []"><div :key="n" class="content-bg-preview" :class="{selected: $store.getters.config.contentBGImg == n}"><img :src="getCustomBGImgURL(n)" alt="" @click="(t) => {
                                    return setBGImg(n)
                                }" /><i class="el-icon-close delete-bg-icon" @click="(t) => {
                                    return t.stopPropagation(), deleteCustomBGImg(n)
                                }" /></div></template><span class="upload-bg-btn" @click="uploadBGFile">上传</span><input ref='"bgFileRef"' style="display: none" type="file" @change="onBGFileChange" /></span></div></li></template><li><span class="setting-item-title">正文字体</span><div class="selection-zone"><template v-for="(n, a) in fonts"><span :key="a" class="span-item" :class="{selected: config.font == a}" @click='(t) => {
                                    return setConfig("font", a)
                                }'>{{ _s(n) + " " }}<i :class="{'el-icon-upload': !0, 'upload-font-icon': !0, active: config.customFontsMap && config.customFontsMap[customFonts[a]]}" @click="(t) => {
                                    return t.stopPropagation(), uploadFontFile(customFonts[a], n)
                                }" /></span></template><input ref='"fontFileRef"' style="display: none" type="file" @change="onFontFileChange" /></div></li><li><span class="setting-item-title">简繁转换</span><div class="selection-zone"><template v-for="(n, a) in chineseFonts"><span :key="a" class="span-item" :class="{selected: config.chineseFont == n}" @click='(t) => {
                                    return setConfig("chineseFont", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">字体大小</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("fontSize")
                            }'><em class="iconfont"></em></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.fontSize" /></span><b /><span class="more" @click='(t) => {
                                return incConfig("fontSize")
                            }'><em class="iconfont"></em></span></div></li><li><span class="setting-item-title">字体粗细</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("fontWeight")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.fontWeight" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("fontWeight")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title font-color-title">字体颜色</span><el-color-picker v-model="config.fontColor" /></li><el-divider /><li><span class="setting-item-title">段落行高</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("lineHeight")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.lineHeight" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("lineHeight")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">段落间距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("paragraphSpace")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.paragraphSpace" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("paragraphSpace")
                            }'><i class="el-icon-plus" /></span></div></li><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">左右边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("horizontalPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.horizontalPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("horizontalPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">顶部边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("topPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.topPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("topPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><template v-if="$store.state.miniInterface"><li><span class="setting-item-title">底部边距</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("bottomPadding")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.bottomPadding" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("bottomPadding")
                            }'><i class="el-icon-plus" /></span></div></li></template><el-divider /><li><span class="setting-item-title">页面模式</span><div class="selection-zone"><template v-for="(n, a) in pageModes"><span :key="a" class="span-item" :class="{selected: config.pageMode == n}" @click="(t) => {
                                    return setPageMode(n)
                                }">{{ n }}</span></template></div></li><template v-if="!($store.state.miniInterface)"><li><span class="setting-item-title">页面宽度</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("readWidth")
                            }'><em class="iconfont"></em></span><b /> <span class="lang">{{ config.readWidth }}</span><b /><span class="more" @click='(t) => {
                                return incConfig("readWidth")
                            }'><em class="iconfont"></em></span></div></li></template><el-divider /><li><span class="setting-item-title">翻页方式</span><div class="selection-zone"><template v-for="(n, a) in readMethods"><span :key="a" class="span-item" :class="{selected: config.readMethod == n}" v-show='!$store.state.miniInterface && "左右滑动" !== n || $store.state.miniInterface' @click="(t) => {
                                    return setReadMethod(n)
                                }">{{ n }}</span></template><span class="small-tip">❗️上下滚动2会自动隐藏看过的章节，但是可能会抖动</span></div></li><li><span class="setting-item-title">动画时长</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("animateMSTime")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.animateMSTime" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("animateMSTime")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">自动翻页</span><div class="selection-zone"><template v-for="(n, a) in autoReadingMethods"><span :key="a" class="span-item" :class="{selected: config.autoReadingMethod === n}" @click='(t) => {
                                    return setConfig("autoReadingMethod", n)
                                }'>{{ n }}</span></template></div></li><template v-if='"像素滚动" === config.autoReadingMethod'><li><span class="setting-item-title">滚动像素</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("autoReadingPixel")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.autoReadingPixel" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("autoReadingPixel")
                            }'><i class="el-icon-plus" /></span></div></li></template><li><span class="setting-item-title">翻页速度</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("autoReadingLineTime")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.autoReadingLineTime" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("autoReadingLineTime")
                            }'><i class="el-icon-plus" /></span></div></li><li><span class="setting-item-title">全屏点击</span><div class="selection-zone"><template v-for="(n, a) in clickMethods"><span :key="a" class="span-item" :class="{selected: config.clickMethod == n}" @click='(t) => {
                                    return setConfig("clickMethod", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">选择文字</span><div class="selection-zone"><template v-for="(n, a) in selectionActions"><span :key="a" class="span-item" :class="{selected: config.selectionAction == n}" @click='(t) => {
                                    return setConfig("selectionAction", n)
                                }'>{{ n }}</span></template></div></li><li><span class="setting-item-title">Epub解析</span><div class="selection-zone"><template v-for="(n, a) in epubModes"><span :key="a" class="span-item" :class="{selected: config.epubMode == n}" @click="(t) => {
                                    return setEpubMode(n)
                                }">{{ n }}</span></template><span class="small-tip">❗️解析可能导致显示问题，但是翻页性能较好，样式支持更多，也支持简繁切换。</span></div></li><li><span class="setting-item-title">请求超时</span><div class="resize"><span class="less" @click='(t) => {
                                return decConfig("chapterRequestTimeout")
                            }'><i class="el-icon-minus" /></span><b /><span class="lang"><el-input class="setting-input" size="mini" v-model="config.chapterRequestTimeout" /></span><b /><span class="less" @click='(t) => {
                                return incConfig("chapterRequestTimeout")
                            }'><i class="el-icon-plus" /></span></div></li><el-divider /><li><span class="setting-item-title">快捷键</span><div class="selection-zone"><template v-for="(n, a) in quickKeyModes"><span :key="a" class="span-item" :class="{selected: config.quickKeyMode === n}" @click="(t) => {
                                    return setQuickKeyMode(n)
                                }">{{ n }}</span></template></div></li><template v-if='"自定义" === config.quickKeyMode'><template v-for="(n, a) in quickKeyList"><li :key='"quickKey-" + a'><span class="setting-item-title">{{ n.label }}</span><div class="selection-zone"><template v-for="(a, u) in quickKeyOps"><span :key="u" class="span-item" :class="{selected: config.quickKey[n.key] === a}" @click="(t) => {
                                        return setQuickKeyOpt(n.key, a)
                                    }">{{ a }}</span></template></div></li></template></template><li class="operation-zone"><span class="span-btn" @click="showClickZone">显示翻页区域</span><span class="span-btn" @click="showRuleEditor">过滤规则管理</span></li></ul></div></div>
</template>

<script>
import axios from 'axios'
import { clone } from '@/utils/clone'
import { asyncToGenerator } from '@/utils/async'
import { regeneratorRuntime } from '@/utils/regenerator'
import { getStorage, setStorage } from '@/utils/storage'
import { isMiniInterface } from '@/utils/common'
import eventBus from '@/utils/event-bus'
import defaults from '@/config/defaults'

/** 自定义字体的 5 个槽位（产物模块 bfff 的导出 a，变量 x.a） */
const CUSTOM_FONT_IDS = ['custom-system', 'custom-ht', 'custom-kt', 'custom-st', 'custom-fs']

/** 移除已注入的自定义字体样式（产物模块 7513 的导出 h，变量 N.h） */
function removeCustomFont(name) {
  window.customFonts = window.customFonts || {}
  delete window.customFonts[name]
  var els = document.querySelectorAll('#custom-font-' + name)
  for (var i = 0; i < els.length; i++) els[i].remove()
}

export default {
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
          customFonts: CUSTOM_FONT_IDS,
          localConfig: this.$store.state.config,
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
      config: {
          get: function() {
              return this.localConfig
          },
          set: function(e) {
              this.localConfig = e
          }
      },
      themeType: function() {
          return this.config.themeType
      },
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
              this.$store.commit("setConfig", clone({}, e)), this.notAutoSync || this.$root.$children[0].saveUserConfig(!0)
          }
      },
      visible: function(e) {
          e && this.syncConfig()
      }
  },
  methods: {
      syncConfig: function() {
          var e = clone(clone(clone({}, defaults.config), this.config), {}, {
              selectionAction: "忽略" !== this.$store.state.config.selectionAction ? "操作弹窗" : "忽略"
          });
          Object.keys(e).sort().join("") === Object.keys(this.config).sort().join("") && Object.values(e).sort().join("") === Object.values(this.config).sort().join("") || (this.config = e)
      },
      setPageType: function(e) {
          if (e !== this.config.pageType) {
              var t = {};
              "Kindle" === e ? (setStorage("lastNormalConfig", this.config), t = getStorage("lastKindleConfig"), t = t || {
                  animateMSTime: 0,
                  fontSize: Math.min(this.fontSize, 20),
                  theme: 7,
                  readMethod: "左右滑动",
                  selectionAction: "忽略",
                  pageMode: "手机模式"
              }) : (setStorage("lastKindleConfig", this.config), t = getStorage("lastNormalConfig") || {}), this.config = clone(clone(clone({}, this.config), t || {}), {}, {
                  pageType: e
              }), this.$emit("readMethodChange"), this.$emit("pageModeChange"), "手机模式" === this.config.pageMode ? this.$store.commit("setMiniInterface", !0) : this.$store.commit("setMiniInterface", isMiniInterface())
          }
      },
      setPageMode: function(e) {
          this.$emit("pageModeChange"), this.config = clone(clone({}, this.config), {}, {
              pageMode: e
          }), "手机模式" === this.config.pageMode ? this.$store.commit("setMiniInterface", !0) : this.$store.commit("setMiniInterface", isMiniInterface())
      },
      setReadMethod: function(e) {
          this.$emit("readMethodChange"), this.config = clone(clone({}, this.config), {}, {
              readMethod: e
          })
      },
      setEpubMode: function(e) {
          this.$emit("epubModeChange"), this.config = clone(clone({}, this.config), {}, {
              epubMode: e
          })
      },
      setQuickKeyMode: function(e) {
          this.config = clone(clone({}, this.config), {}, {
              quickKeyMode: e
          })
      },
      setQuickKeyOpt: function(e, t) {
          this.config = clone(clone({}, this.config), {}, {
              quickKey: clone(clone({}, this.config.quickKey), {}, { [e]: t })
          })
      },
      setConfig: function(e, t) {
          var n = {};
          n[e] = t, this.config = clone(clone({}, this.config), n)
      },
      setAutoTheme: function() {
          this.config = clone(clone({}, this.config), {}, {
              autoTheme: !this.config.autoTheme
          })
      },
      incConfig: function(e) {
          var t = {},
              n = this.configRules[e],
              a = +this.config[e];
          t[e] = "max" in n ? Math.min(n.max, a + n.delta) : a + n.delta, this.config = clone(clone({}, this.config), t)
      },
      decConfig: function(e) {
          var t = {},
              n = this.configRules[e],
              a = +this.config[e];
          t[e] = "min" in n ? Math.max(n.min, a - n.delta) : a - n.delta, this.config = clone(clone({}, this.config), t)
      },
      getCustomBGImgURL: function(e) {
          return this.api.replace(/\/reader3\/?/, "") + e
      },
      setBGImg: function(e) {
          var t = clone({}, this.config);
          t.contentBGImg === e ? delete t.contentBGImg : t.contentBGImg = e, this.config = t
      },
      uploadBGFile: function() {
          this.$refs.bgFileRef.dispatchEvent(new MouseEvent("click"))
      },
      onBGFileChange: function(e) {
          var t = this,
              n = e.target.files && e.target.files[0],
              a = new FormData;
          a.append("file", n), a.append("type", "background"), axios.post(this.api + "/uploadFile", a, {
              headers: {
                  "Content-Type": "multipart/form-data"
              }
          }).then((function(e) {
              if (e.data.isSuccess) {
                  if (!e.data.data.length) return void t.$message.error("上传文件失败");
                  var n = clone({}, t.config);
                  n.customBGImgList = n.customBGImgList || [], n.customBGImgList.includes(e.data.data[0]) || n.customBGImgList.push(e.data.data[0]), n.contentBGImg = e.data.data[0], t.config = n
              }
          }), (function(e) {
              t.$message.error("上传文件失败 " + (e && e.toString()))
          })), this.$refs.bgFileRef.value = null
      },
      uploadFontFile: function(e, t) {
          var n = this;
          return asyncToGenerator(regeneratorRuntime.mark((function a() {
              var u;
              return regeneratorRuntime.wrap((function(a) {
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
                          return axios.post(n.api + "/deleteFile", {
                              url: n.config.customFontsMap[e]
                          }).then((function(t) {
                              if (t.data.isSuccess) {
                                  var a = clone({}, n.config);
                                  delete a.customFontsMap[e], n.config = a, removeCustomFont(e)
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
              a.append("file", n), a.append("type", "fonts"), axios.post(this.api + "/uploadFile", a, {
                  headers: {
                      "Content-Type": "multipart/form-data"
                  }
              }).then((function(e) {
                  if (e.data.isSuccess) {
                      if (!e.data.data.length) return void t.$message.error("上传文件失败");
                      var n = clone({}, t.config);
                      n.customFontsMap = n.customFontsMap || {}, n.customFontsMap[t.customFontName] = e.data.data[0], t.config = n
                  }
              }), (function(e) {
                  t.$message.error("上传文件失败 " + (e && e.toString()))
              })), this.$refs.fontFileRef.value = null
          } else this.$message.error("只支持 TTF 字体文件")
      },
      deleteCustomBGImg: function(e) {
          var t = this;
          axios.post(this.api + "/deleteFile", {
              url: e
          }).then((function(n) {
              if (n.data.isSuccess) {
                  var a = clone({}, t.config);
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
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var n;
              return regeneratorRuntime.wrap((function(t) {
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
                          e.config = clone({}, defaults.config);
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
          this.$emit("close"), eventBus.$emit("showReplaceRuleDialog")
      },
      addNewCustomConfig: function() {
          var e = this;
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var n, a, u, r;
              return regeneratorRuntime.wrap((function(t) {
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
                          r = clone({}, e.$store.state.customConfigList[0]), r.name = a, e.$store.commit("setCustomConfigList", [].concat(e.$store.state.customConfigList).concat([r]));
                      case 14:
                      case "end":
                          return t.stop()
                  }
              }), t)
          })))()
      },
      setCustomConfig: function(e) {
          this.config = clone(clone({}, this.config), {}, {
              customConfig: e.name
          }, e)
      },
      deleteCustomConfig: function(e, t) {
          var n = this;
          return asyncToGenerator(regeneratorRuntime.mark((function a() {
              var u, r;
              return regeneratorRuntime.wrap((function(a) {
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
          return asyncToGenerator(regeneratorRuntime.mark((function n() {
              var a, u;
              return regeneratorRuntime.wrap((function(n) {
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
  },
}
</script>

<style scoped>
p { display:block;word-wrap:break-word;word-break:break-all;text-indent:2em }
p.reading { color:red!important }
h3 { font-size:28px;line-height:1.2;margin:1em 0;text-align:center }
h3.reading { color:red!important }
.volume-chapter { min-height:100vh;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;-ms-flex-align:center;align-items:center }
.volume-chapter .volume-content { text-align:center }
.volume-chapter .volume-tag { text-align:right }
.content-audio { margin:0 auto;width:100% }
.content-audio .book-cover img { max-width:200px;margin:0 auto;display:block }
.content-audio .book-progress { padding:25px 15px;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-align:center;align-items:center }
.content-audio .book-progress .progress-tip { padding-top:5px;padding-bottom:5px;font-size:14px;width:45px }
.content-audio .book-progress .progress-container { -ms-flex:1;flex:1;margin-left:10px;margin-right:10px }
.content-audio .book-progress .total-time { text-align:right }
.content-audio .book-operation { padding:0 15px 25px;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:distribute;justify-content:space-around }
.content-audio .book-operation i { display:inline-block;cursor:pointer;font-size:24px;line-height:1 }
.content-audio .book-info { padding:10px 15px;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-align:center;align-items:center }
.content-audio .book-info .book-cover { width:50px }
.content-audio .book-info .book-cover img { width:100%;max-height:100% }
.content-audio .book-info .book-intro { -ms-flex:1;flex:1;padding-left:15px }
.content-audio .book-info .book-intro .title { font-size:16px }
.content-audio .book-info .book-intro .subtitle { margin-top:5px;font-size:14px }
.epub-iframe { border:none;width:100%;min-height:calc(var(--vh, 1vh)*50) }
</style>
