<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=app scopeId=99ff4e1a；无 sourcemap，模板为 render 推断还原 -->
<template>
<el-dialog v-if="$store.getters.isNormalPage" :class='isWebApp && !$store.getters.isNight ? "status-bar-light-bg-dialog" : ""' title="书架管理" :visible="show" :width="dialogWidth" :top="dialogTop" :fullscreen="$store.state.miniInterface" :before-close="cancel" @update:visible="(t) => {
                            show = t
                        }"><div class="custom-dialog-title flex-title" slot="title"><span class="el-dialog__title">书架管理 </span><span class="title-center"><el-input class="search-input" size="mini" placeholder="输入关键字搜索" v-model="search" /></span><span class="action-zone" v-show="!$store.state.miniInterface"><span class="float-right small-tip">❗️只能缓存文本内容</span></span></div><div class="source-container table-container"><el-table :data="showList" :height="dialogContentHeight - 42" @selection-change="(t) => {
                            manageBookSelection = t
                        }" @sort-change="sortChange"><el-table-column type="selection" width="25" :selectable="isBookSelectable" :fixed="$store.state.miniInterface" /><el-table-column property="name" label="书名名" min-width="100" sortable="custom" :fixed="$store.state.miniInterface"><template v-slot:default="o"><el-button class="text-button" size="medium" type="text" @click="(t) => {
                                        return showBookInfo(o.row)
                                    }">{{ o.row.name }}</el-button></template></el-table-column><el-table-column property="author" label="作者" sortable="custom" min-width="100" /><el-table-column property="group" label="分组" min-width="120" :filters="bookGroupFilters" :filter-method="filterHandler"><template v-slot:default="t">{{ " " + _s(renderBookGroup(t.row)) + " " }}</template></el-table-column><el-table-column label="章节" min-width="120"><template v-slot:default="o"><span>{{ "共 " + _s(o.row.totalChapterNum) + " 章" }}</span><br /><template v-if='"loc_book" !== o.row.origin'><span>{{ " 服务器缓存： " + _s(o.row.cachedChapterCount || 0) + " 章 " }}<br /></span></template><span>{{ "浏览器缓存： " + _s(o.row.localCacheCount) + " 章" }}</span></template></el-table-column><el-table-column label="操作" width="100px"><template v-slot:default="o"><el-button class="text-button" size="medium" type="text" @click="(t) => {
                                        return editBook(o.row)
                                    }">编辑</el-button><el-button class="text-button" style="margin-left: 0" size="medium" type="text" @click="(t) => {
                                        return setBookGroup(o.row)
                                    }">分组</el-button><el-dropdown :trigger='$store.state.touchable ? "click" : "hover"' @command="(t) => {
                                        return cacheBook(o.row, t)
                                    }"><el-button class="text-button" type="text" size="medium"><template v-if="isCaching(o.row)"><span><i class="el-icon-loading" /> 缓存中 </span></template><template v-else><span> 缓存<i class="el-icon-arrow-down el-icon--right" /></span></template></el-button><el-dropdown-menu slot="dropdown"><template v-if='"loc_book" !== o.row.origin'><el-dropdown-item command="cacheBookOnServer">服务器缓存</el-dropdown-item></template><template v-if='"loc_book" !== o.row.origin'><el-dropdown-item command="cacheBookSSE">缓存到服务器</el-dropdown-item></template><el-dropdown-item command="cacheBookLocal">缓存到浏览器</el-dropdown-item><template v-if='"loc_book" !== o.row.origin'><el-dropdown-item command="deleteBookCache">删除服务器缓存</el-dropdown-item></template><el-dropdown-item command="deleteBookLocalCache">删除浏览器缓存</el-dropdown-item></el-dropdown-menu></el-dropdown><el-dropdown :trigger='$store.state.touchable ? "click" : "hover"' @command="(t) => {
                                        return exportBook(o.row, t)
                                    }"><el-button class="text-button" type="text" size="medium"> 导出<i class="el-icon-arrow-down el-icon--right" /></el-button><el-dropdown-menu slot="dropdown"><el-dropdown-item command="txt">导出为TXT</el-dropdown-item><el-dropdown-item command="epub">导出为Epub</el-dropdown-item></el-dropdown-menu></el-dropdown></template></el-table-column></el-table></div><div class="dialog-footer" slot="footer"><div><el-dropdown class="float-left" :trigger='$store.state.touchable ? "click" : "hover"' @command="multiOperate"><el-button type="primary" size="medium"> 操作 <template v-if="manageBookSelection.length"><span>{{ " (" + _s(manageBookSelection.length) + ")" }}</span></template><i class="el-icon-arrow-down el-icon--right" /></el-button><el-dropdown-menu slot="dropdown"><el-dropdown-item command="deleteBookList">批量删除</el-dropdown-item><el-dropdown-item command="addBookGroupMulti">添加分组</el-dropdown-item><el-dropdown-item command="removeBookGroupMulti">删除分组</el-dropdown-item></el-dropdown-menu></el-dropdown><el-dropdown class="float-left" :trigger='$store.state.touchable ? "click" : "hover"' @command="cacheBookMulti"><el-button type="primary" size="medium"> 缓存 <template v-if="manageBookSelection.length"><span>{{ " (" + _s(manageBookSelection.length) + ")" }}</span></template><i class="el-icon-arrow-down el-icon--right" /></el-button><el-dropdown-menu slot="dropdown"><el-dropdown-item command="cacheBookOnServer">服务器缓存</el-dropdown-item><el-dropdown-item command="cacheBookSSE">缓存到服务器</el-dropdown-item><el-dropdown-item command="cacheBookLocal">缓存到浏览器</el-dropdown-item><el-dropdown-item command="deleteBookCache">删除服务器缓存</el-dropdown-item><el-dropdown-item command="deleteBookLocalCache">删除浏览器缓存</el-dropdown-item></el-dropdown-menu></el-dropdown><template v-if="multiOperating"><el-button size="medium" @click="cancelMulti">取消批量操作</el-button></template></div><div class="source-pagination"><el-pagination :current-page="pagination.page" :page-sizes="[25, 50, 100, 200, 300, 400, filterList.length]" :page-size="pagination.size" layout="total, sizes, prev, pager, next" :total="filterList.length" :pager-count="$store.state.miniInterface ? 5 : 7" @update:currentPage='(t) => {
                            return $set(pagination, "page", t)
                        }' @update:current-page='(t) => {
                            return $set(pagination, "page", t)
                        }' @update:pageSize='(t) => {
                            return $set(pagination, "size", t)
                        }' @update:page-size='(t) => {
                            return $set(pagination, "size", t)
                        }' /></div></div></el-dialog></el-dialog>
</template>

<script>import axios from 'axios'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import eventBus from '@/utils/event-bus'

/** 并发受限任务队列（产物模块 7513 的导出 a） */
function createConcurrentRunner(limit, onDone) {
  var running = 0
  var queue = []

  function next() {
    return worker()
  }

  async function worker() {
    ++running
    runner.leftCount = queue.length
    var task = queue.shift()
    var res
    var err
    try {
      res = await task()
    } catch (e) {
      err = e
      runner.errorCount++
    } finally {
      --running
      runner.requestCount++
      runner.leftCount = queue.length
      if (onDone) onDone(runner, res, err)
      if (queue.length > 0) next()
    }
  }

  var runner = function (task) {
    if (task && task instanceof Function) {
      queue.push(task)
      runner.leftCount = queue.length
      if (running < limit) next()
    }
  }
  runner.requestCount = 0
  runner.leftCount = 0
  runner.errorCount = 0
  runner.cancel = function () {
    queue.length = 0
    runner.leftCount = 0
  }
  runner.isEnd = function () {
    return !runner.leftCount && !running
  }
  return runner
}

/**
 * 64 位整数位运算替身（产物模块 f537 = goog.math.Long）。
 * 用途是书籍分组位掩码：group 每个 bit 代表一个分组。
 */
const LongBits = (function () {
  function toBig(v) {
    return typeof v === 'bigint' ? v : BigInt(Math.trunc(Number(v) || 0))
  }
  function Bits(v) {
    this.v = v
  }
  Bits.prototype.or = function (o) {
    return new Bits(this.v | toBig(o))
  }
  Bits.prototype.and = function (o) {
    return new Bits(this.v & toBig(o))
  }
  Bits.prototype.greaterThan = function (o) {
    return this.v > toBig(o)
  }
  Bits.prototype.toNumber = function () {
    return Number(this.v)
  }
  return {
    fromNumber: function (n) {
      return new Bits(BigInt.asIntN(64, toBig(n)))
    }
  }
})()

export default {

  model: {
      prop: "show",
      event: "setShow"
  },
  name: 'BookManage',
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
  computed: __merge({}, {
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
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              return regeneratorRuntime.wrap((function(t) {
                  while (1) switch (t.prev = t.next) {
                      case 0:
                          return t.abrupt("return", axios.get(e.api + "/getShelfBookWithCacheInfo").then((function(t) {
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
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              var o;
              return regeneratorRuntime.wrap((function(t) {
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
                          axios.post(e.api + "/deleteBooks", e.manageBookSelection).then((function(t) {
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
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              return regeneratorRuntime.wrap((function(t) {
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
          return asyncToGenerator(regeneratorRuntime.mark((function t() {
              return regeneratorRuntime.wrap((function(t) {
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
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n, r, a, i, c, u;
              return regeneratorRuntime.wrap((function(o) {
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
                          axios.post(t.api + (e ? "/addBookGroupMulti" : "/removeBookGroupMulti"), {
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
              o.groupId > 0 && (LongBits.fromNumber(o.groupId).and(e.group).greaterThan(0)) && t.push(o.groupName)
          })), t.length || t.push("未分组"), t.join(" ")
      },
      filterHandler: function(e, t, o) {
          var n = o["property"];
          return t[n] === e
      },
      showBookInfo: function(e) {
          eventBus.$emit("showBookInfoDialog", e)
      },
      editBook: function(e) {
          var t = this;
          eventBus.$emit("editBook", e, !1, (function() {
              t.loadBookCacheInfo()
          }))
      },
      setBookGroup: function(e) {
          this.$store.commit("setShowBookInfo", e), eventBus.$emit("showBookGroupDialog", !0)
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
          return asyncToGenerator(regeneratorRuntime.mark((function o() {
              var n, r, a, i;
              return regeneratorRuntime.wrap((function(o) {
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
                          t.multiOperating = e, t.lastSelection = [].concat(t.manageBookSelection), t.multiCachingHandler = createConcurrentRunner(2, (function(e) {
                              e.isEnd() && (t.multiOperating = !1, t.lastSelection = [], t.$message.success("批量操作完成"))
                          })), a = regeneratorRuntime.mark((function o(n) {
                              return regeneratorRuntime.wrap((function(o) {
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
                              t.$set(t.bookList, r, __merge(__merge({}, e), {}, {
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
                          t.$set(t.bookList, n, __merge(__merge({}, e), {}, {
                              localCacheCount: o[0].localCacheCount
                          }))
                      }))
                  };
              window.cacheRequestHandle[e.bookUrl] = createConcurrentRunner(2, (function(e) {
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
          e = Array.isArray(e) ? e : [e], axios.post(this.api + "/cacheBookOnServer", {
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
              var n = asyncToGenerator(regeneratorRuntime.mark((function n(r, a) {
                  var i;
                  return regeneratorRuntime.wrap((function(n) {
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
                              axios.post(o.api + "/deleteBookCache", {
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
              var n = asyncToGenerator(regeneratorRuntime.mark((function n(r, a) {
                  var i;
                  return regeneratorRuntime.wrap((function(n) {
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
  },
  render: function() {
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
            },
  staticRenderFns: []
}
</script>

<style scoped>
.float-right { float:right }
.small-tip { font-size:14px;margin-right:10px }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
.source-container .text-button { padding:3px 5px }
.source-container .source-pagination { margin-top:5px;text-align:right }
</style>
