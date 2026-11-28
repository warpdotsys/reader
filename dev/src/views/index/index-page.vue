<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index / 模块 d504（scopeId b817d39e）
     主页面 options 来自 d504.js 的变量 rt，模板来自同模块的 render 变量 s（staticRenderFns 为空）。
     产物形如 X["a"] 的 webpack 模块引用已全部改写为具名 import，无残留。 -->
<template>
<div class="index-wrapper" :class="{night: isNight, day: !isNight}"><template v-if="$store.getters.isNormalPage"><div class="navigation-wrapper" :class='[navigationClass, isWebApp && !isNight ? "status-bar-light-bg" : ""]' :style="navigationStyle" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd"><div class="navigation-inner-wrapper"><div class="navigation-title"> 阅读 <span class="version-text" @click="updateForce">{{ $store.state.version }}</span></div><div class="navigation-sub-title"> 清风不识字，何故乱翻书 </div><div class="search-wrapper" v-show="$store.getters.hasLogin"><el-input class="search-input" size="mini" placeholder="搜索书籍" v-model="search" @keyup.native='function(e) {
                                return !e.type.indexOf("key") && _k(e.keyCode, "enter", 13, e.key, "Enter") ? null : searchBook(1)
                            }'><i class="el-input__icon el-icon-search" slot="prefix" /></el-input></div><div class="setting-wrapper search-setting" v-show="$store.getters.hasLogin"><div class="setting-title"> 搜索设置 </div><div class="setting-item"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索方式" v-model="searchConfig.searchType"><template v-for="(t, o) in searchTypeList"><el-option :key='"search-type-" + o' :label="t.name" :value="t.value" /></template></el-select></div><div class="setting-item" v-show='"single" === searchConfig.searchType'><template v-if='"yes" === shelfConfig.virtualOptimize'><VirtualSelect :data-list="bookSourceList" v-model="searchConfig.bookSourceUrl" /></template><template v-else><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索书源" v-model="searchConfig.bookSourceUrl"><template v-for="(t, o) in bookSourceList"><el-option :key='"source-" + o' :label="t.bookSourceName" :value="t.bookSourceUrl" /></template></el-select></template></div><div class="setting-item" v-show='"single" === searchConfig.searchType'><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="searchBookManual"> 精确搜书 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="saveBookManual"> 手动加书 </el-tag></div><div class="setting-item" v-show='"single" !== searchConfig.searchType'><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索书源分组" v-model="searchConfig.bookSourceGroup"><template v-for="(t, o) in bookSourceGroupList"><el-option :key='"source-group-" + o' :label='t.name + " (" + t.count + ")"' :value="t.value" /></template></el-select></div><div class="setting-item" v-show='"single" !== searchConfig.searchType'><el-select class="setting-select" size="mini" filterable="" placeholder="请选择并发线程" v-model="searchConfig.concurrentCount"><template v-for="(t, o) in concurrentList"><el-option :key='"source-" + o' :label='t + "并发线程"' :value="t" /></template></el-select></div></div><div class="recent-wrapper" v-show="$store.getters.hasLogin"><div class="recent-title"> 最近阅读 <span class="right-text" @click='$store.commit("clearReadingBook")'>清除</span></div><div class="reading-recent"><el-tag class="recent-book" :class="{'no-point': &quot;&quot; == readingRecent.bookUrl}" type="warning" :effect='isNight ? "dark" : "light"' @click="toDetail(readingRecent)">{{ " " + _s(readingRecent.name) + " " }}</el-tag></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 后端设定 <template v-if="isTauri"><span class="right-text" @click='(e) => {
                                return $router.push({
                                    path: "/setting"
                                })
                            }'>设置</span></template></div><div class="setting-item"><el-tag class="setting-connect" :class="{'no-point': connecting}" :type="connectType" :effect='isNight ? "dark" : "light"' @click="setIP">{{ " " + _s(connectStatus) + " " }}</el-tag></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 书源设置 </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showBookSourceManageDialog = !0
                            }"> 书源管理 </el-tag><el-popover placement="right" :width="popupWidth" trigger="click" :visible-arrow="false" popper-class="popper-component explore-popover" v-model="popExploreVisible"><Explore ref='"popExplore"' class="popup" :visible="popExploreVisible" :bookSourceList="bookSourceList" @showSearchList="showSearchList" @close="(e) => {
                                popExploreVisible = !1
                            }" /><el-tag ref='"exploreBtn"' class="setting-btn" slot="reference" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showNavigation = !1
                            }"> 探索书源 </el-tag></el-popover><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="uploadBookSource"> 导入书源 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showRemoteBookSourceSubDialog"> 书源订阅 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showFailureBookSource()"> 失效书源 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="debugBookSource()"> 调试书源 </el-tag><input ref='"fileRef"' style="display: none" type="file" @change="onSourceFileChange" /></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 书架设置 </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showShelfSettingsDialog = !showShelfSettingsDialog
                            }"> 书架设置 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="showBookManage"> 书籍管理 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="showManageBookGroup"> 分组管理 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="importLocalBook"> 导入书籍 </el-tag><input ref='"bookRef"' style="display: none" type="file" multiple="multiple" @change="onBookFileChange" /><template v-if="!$store.state.isSecureMode || $store.state.userInfo.enableLocalStore"><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__LOCAL_STORE__", "书仓文件管理")
                            }'> 浏览书仓 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showBookmarkDialog"> 书签管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showReplaceRuleDialog"> 替换规则 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="init(!0)"> 刷新缓存 </el-tag></div></div><div class="setting-wrapper"><div class="setting-title"> 用户空间 <template v-if="$store.getters.hasLogin"><span class="right-text" @click="logout()">注销</span></template><template v-else><template v-if="$store.state.isSecureMode"><span class="right-text" @click='(e) => {
                                return $store.commit("setShowLogin", !0)
                            }'>登录</span></template></template></div><template v-if="$store.state.isManagerMode"><div class="setting-item"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择用户空间" v-model="userNS"><template v-for="(t, o) in userList"><el-option :key='"source-" + o' :label="t.username" :value="t.userNS" /></template></el-select></div></template><div class="setting-item" v-show="$store.getters.hasLogin"><template v-if="isShowActiveLicenseBtn"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showActiveLicenseDialog"> 授权管理 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showLicense"> 查看授权信息 </el-tag><template v-if="localStorageAvaliable"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="saveUserConfig"> 备份我的配置 </el-tag></template><template v-if="localStorageAvaliable"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="restoreUserConfig"> 同步我的配置 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__HOME__", "用户数据管理")
                            }'> 我的数据管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="downloadBackupFile"> 下载数据备份 </el-tag><template v-if="$store.state.showManagerMode && !$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="loadUserList"> 进入管理模式 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showUserManageDialog()"> 用户权限管理 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__STORAGE__", "数据目录管理")
                            }'> 数据目录管理 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="exitSecureMode"> 退出管理模式 </el-tag></template></div></div><template v-if="!$store.state.isSecureMode || $store.state.userInfo.enableWebdav"><div class="setting-wrapper"><div class="setting-title" v-show="$store.getters.hasLogin"> WebDAV </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__WEBDAV__", "WebDAV文件管理")
                            }'> 文件管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="backupToWebdav"> 保存备份 </el-tag></div></div></template><div class="setting-wrapper"><div class="setting-title"> 本地缓存 <span class="right-text">{{ localCacheStats.total }}</span></div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='clearCache("bookSourceList")'> 清空书源缓存 <span>{{ localCacheStats.bookSourceList }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='clearCache("rssSources")'> 清空RSS源缓存 <span>{{ localCacheStats.rssSources }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='clearCache("chapterList")'> 清空章节列表缓存 <span>{{ localCacheStats.chapterList }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='clearCache("chapterContent")'> 清空章节内容缓存 <span>{{ localCacheStats.chapterContent }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='clearCache("ttsData")'> 清空TTS缓存 <span>{{ localCacheStats.ttsData }}</span></el-tag></div></div></div><div class="bottom-icons"><a href="https://github.com/hectorqin/reader" target="_blank"><div class="bottom-icon"><template v-if="isNight"><img :src='"data:image/png;base64,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"' alt="" /></template><template v-else><img :src='"data:image/png;base64,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"' alt="" /></template></div></a><span ref='"themes"' class="theme-item" :style="themeColor" @click="toogleNight"><template v-if="isNight"><i class="el-icon-sunny" /></template><template v-else><i class="el-icon-moon" /></template></span></div></div></template><div ref='"shelfWrapper"' class="shelf-wrapper" :class='isWebApp && !isNight ? "status-bar-light-bg" : ""' @click="hideMenu"><div class="shelf-title"><template v-if="$store.getters.isNormalPage && collapseMenu"><i class="el-icon-menu" @click="(e) => {
                                return e.stopPropagation(), toggleMenu.apply(null, arguments)
                            }" /></template>{{ " " + _s(isSearchResult ? isExploreResult ? "探索" : isShowSearchBookSourceListDesc ? "书源" : "搜索" : "书架") + " (" + _s(bookList.length) + ") " }}<template v-if="$store.getters.isNormalPage && isSearchResult"><div class="title-btn" @click="backToShelf"> 书架 </div></template><template v-if="$store.getters.isNormalPage && isSearchResult && !isShowSearchBookSourceListDesc"><div class="title-btn" @click="loadMore"><template v-if="loadingMore"><i class="el-icon-loading" /></template>{{ " " + _s(loadingMore ? "加载中..." : "加载更多") + " " }}</div></template><template v-if="$store.getters.isNormalPage && isSearchResult && isShowSearchBookSourceListDesc"><div class="title-btn" @click="displaySearchResult">{{ " " + _s("返回搜索") + " " }}</div></template><template v-if="$store.getters.isNormalPage && !isSearchResult"><div class="title-btn" @click="(e) => {
                                showBookEditButton = !showBookEditButton
                            }">{{ " " + _s(showBookEditButton ? "取消" : "编辑") + " " }}</div></template><template v-if="!(isSearchResult)"><div class="title-btn" @click="refreshShelf"><template v-if="refreshLoading"><i class="el-icon-loading" /></template>{{ " " + _s(refreshLoading ? "刷新中..." : "刷新") + " " }}</div></template><template v-if="$store.getters.isNormalPage && !isSearchResult"><div class="title-btn" @click="showRssDialog"> RSS </div></template><template v-if="!(!$store.getters.isNormalPage || isSearchResult && !isExploreResult)"><div class="title-btn" @click="showExplorePop"> 书海 </div></template><template v-if="showBookEditButton"><el-input size="mini" placeholder="搜索书架" v-model="keyword" /></template></div><template v-if="!(isSearchResult)"><div class="book-group-wrapper"><el-tabs class="book-group-tabs" stretch="" v-model="showBookGroupString"><template v-for="t in bookGroupDisplayList"><el-tab-pane :key='"bookGroup-" + t.groupId' :label="t.groupName" :name='"" + t.groupId' /></template></el-tabs></div></template><div ref='"bookList"' class="books-wrapper" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd" @scroll="scrollHandler"><template v-if='!isSearchResult && "list" !== shelfConfig.viewCate || "yes" !== shelfConfig.virtualOptimize'><template v-if='isSearchResult || "list" === shelfConfig.viewCate'><BookList ref='"bookListComp"' :bookList="bookList" :showNavigation="showNavigation" :isSearchResult="isSearchResult" :showBookEditButton="showBookEditButton" :showSourceCount="!isShowSearchBookSourceListDesc" @showBookInfoDialog="showBookInfoDialog" @toDetail="toDetail" @deleteBook="deleteBook" @editBook="editBook" @addBookToShelf="addBookToShelf" @showSearchBookSourceList="showSearchBookSourceList" /></template><template v-else><BookColumn ref='"bookListComp"' :bookList="bookList" :showNavigation="showNavigation" :isSearchResult="isSearchResult" :showBookEditButton="showBookEditButton" @showBookInfoDialog="showBookInfoDialog" @toDetail="toDetail" @deleteBook="deleteBook" @editBook="editBook" @addBookToShelf="addBookToShelf" /></template></template><template v-else><BookVirtualList ref='"bookListComp"' :bookList="bookList" :showNavigation="showNavigation" :isSearchResult="isSearchResult" :showBookEditButton="showBookEditButton" :showSourceCount="!isShowSearchBookSourceListDesc" @showBookInfoDialog="showBookInfoDialog" @toDetail="toDetail" @deleteBook="deleteBook" @editBook="editBook" @addBookToShelf="addBookToShelf" @showSearchBookSourceList="showSearchBookSourceList" /></template></div></div><template v-if="$store.getters.isNormalPage"><el-dialog :class='isWebApp && !isNight ? "status-bar-light-bg" : ""' :title='isImportRssSource ? "导入RSS源" : "导入书源"' :visible="showImportSourceDialog" :width="dialogWidth" :top='this.collapseMenu ? "0" : "15vh"' :fullscreen="collapseMenu" @update:visible="(e) => {
                                showImportSourceDialog = e
                            }"><div class="source-container source-list-container"><el-checkbox-group v-model="checkedSourceIndex" @change="handleCheckedSourcesChange"><template v-for="(o, s) in importSourceList"><el-checkbox :key="s" class="source-checkbox" :label="s">{{ _s(isImportRssSource ? o.sourceName : o.bookSourceName) + " " + _s(isImportRssSource ? o.sourceUrl : o.bookSourceUrl) + " " + _s(getSourceTag(o)) }}</el-checkbox></template></el-checkbox-group></div><div class="dialog-footer" slot="footer"><el-checkbox class="float-left checkbox-btn" :indeterminate="isIndeterminate" border="" size="medium" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox><el-checkbox class="float-left checkbox-btn" border="" size="medium" @change="handleCheckWebviewChange">Webview 源</el-checkbox><el-checkbox class="float-left checkbox-btn" border="" size="medium" @change="handleCheckJSChange">JavaScript 源</el-checkbox><span class="check-tip">{{ "已选择 " + _s(checkedSourceIndex.length) + " 个" }}</span><el-button size="medium" @click="(e) => {
                                showImportSourceDialog = !1, checkedSourceIndex = []
                            }">取消</el-button><el-button size="medium" type="primary" @click="saveSourceList">确定</el-button></div></el-dialog></template><template v-if="$store.getters.isNormalPage"><el-dialog :class='isWebApp && !isNight ? "status-bar-light-bg-dialog" : ""' :visible="showBookSourceManageDialog" :width="dialogWidth" :top="dialogTop" :fullscreen="collapseMenu" @update:visible="(e) => {
                                showBookSourceManageDialog = e
                            }" @closed='(e) => {
                                isShowFailureBookSource = !1, showSourceGroup = "全部"
                            }'><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">{{ _s(isShowFailureBookSource ? "失效书源管理" : "书源管理") + " " }}<template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="deleteAllBookSource()">清空</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="deleteBookSourceFile()">恢复默认</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="exportBookSource()">导出</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="editBookSource(!1)">新增</span></template></span></div><div class="source-container table-container"><template v-if="isShowFailureBookSource"><div class="check-form"><span class="check-form-label">搜索词：</span><el-input size="small" v-model="checkBookSourceConfig.keyword" /><span class="check-form-label" style="min-width: 68px"> 超时(ms)： </span><el-input-number min="1000" max="15000" step="500" size="small" v-model="checkBookSourceConfig.timeout" /><span class="check-form-label">并发数：</span><el-input-number min="3" max="15" step="1" size="small" v-model="checkBookSourceConfig.concurrent" /></div></template><div class="source-group-wrapper"><el-tabs class="booksource-group-tabs" stretch="" v-model="showSourceGroup"><template v-for="t in bookSourceShowGroup"><el-tab-pane :key='"sourceGroupTab-" + t' :label="t" :name="t" /></template></el-tabs></div><el-table :key="isShowFailureBookSource" :data="bookSourceShowResultPageList" :height="dialogContentHeight - 42 - 42 - (isShowFailureBookSource ? 32 : 0)" @selection-change="(e) => {
                                manageSourceSelection = e
                            }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" :selectable="isBookSourceSelectable" /><el-table-column property="bookSourceName" label="书源名称" min-width="60" sortable="" :fixed="$store.state.miniInterface" /><el-table-column property="bookSourceUrl" label="书源链接" min-width="120"><template v-slot:default="o"><el-link type="primary" :href="o.row.bookSourceUrl" target="_blank">{{ o.row.bookSourceUrl }}</el-link></template></el-table-column><template v-if="isShowFailureBookSource"><el-table-column property="errorMsg" label="错误信息" sortable="" min-width="120" /></template><el-table-column label="书架书籍" min-width="120"><template v-slot:default="o"><pre>{{ showSourceBook(o.row) }}</pre></template></el-table-column><template v-if="!(isShowFailureBookSource)"><el-table-column width="120px"><template slot='"header"'><el-input size="mini" placeholder="搜索书源" v-model="bookSourceKeyword" /></template><template v-slot:default="o"><el-button type="text" @click="editBookSource(o.row)">编辑</el-button><el-button type="text" @click="editBookSourceHeader(o.row)">请求头</el-button></template></el-table-column></template></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteBookSourceList">批量删除<template v-if="manageSourceSelection.length"><span>{{ " (" + _s(manageSourceSelection.length) + ")" }}</span></template></el-button><template v-if="isShowFailureBookSource"><el-button style="margin-bottom: 5px" type="primary" size="medium" @click="checkBookSource">{{ _s(isCheckingBookSource ? "正在" : "") + "检测书源 " + _s(checkBookSourceTip) }}</el-button></template></div><div class="source-pagination"><el-pagination :current-page="bookSourcePagination.page" :page-sizes="[25, 50, 100, 200, 300, 400]" :page-size="bookSourcePagination.size" layout="total, sizes, prev, pager, next" :total="bookSourceShowLength" :pager-count="collapseMenu ? 5 : 7" @update:currentPage='(e) => {
                                return $set(bookSourcePagination, "page", e)
                            }' @update:current-page='(e) => {
                                return $set(bookSourcePagination, "page", e)
                            }' @update:pageSize='(e) => {
                                return $set(bookSourcePagination, "size", e)
                            }' @update:page-size='(e) => {
                                return $set(bookSourcePagination, "size", e)
                            }' /></div></div></el-dialog></template><template v-if="$store.getters.isNormalPage"><el-dialog :class='isWebApp && !isNight ? "status-bar-light-bg-dialog" : ""' :title='"导入本地书籍" + importMultiBookTip' :visible="showImportBookDialog" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="collapseMenu" @update:visible="(e) => {
                                showImportBookDialog = e
                            }" @closed="importBookDialogClosed"><div class="source-container table-container"><div class="check-form"><div class="book-cover"><el-image :key="getBookCoverUrl(importBookInfo)" class="cover" :src="getCover(getBookCoverUrl(importBookInfo), !0)" fit="cover" lazy="" /></div><div class="book-info"><div><span>书名：</span><el-input size="small" v-model="importBookInfo.name" /></div><div><span>作者：</span><el-input size="small" v-model="importBookInfo.author" /></div><div><span>分组：</span><el-select size="mini" filterable="" multiple="" placeholder="未分组" v-model="importBookGroup"><template v-for="(t, o) in bookGroupSetList"><el-option :key='"bookGroup-" + o' :label="t.groupName" :value="t.groupId" /></template></el-select></div><template v-if="isShowTocRule"><div><span>规则：</span><el-select size="mini" filterable="" placeholder="内置规则" v-model="importUsedTxtRule"><template v-for="(t, o) in tocRuleList"><el-option :key='"txtTocRule-" + o' :label="t.name" :value="t.rule" /></template></el-select><el-button class="toc-refresh-btn" type="text" @click="getChapterListByRule()">刷新目录</el-button></div></template><template v-if="isShowTocRule"><div><el-input type="textarea" rows="2" size="small" v-model="importBookInfo.tocUrl" /></div></template></div></div><div class="chapter-title">{{ " 章节列表(" + _s(importBookChapters.length) + ") " }}</div><div class="chapter-list" :style='{maxHeight: dialogContentHeight - 40 - 35 + "px"}'><template v-for="(o, s) in importBookChapters"><p :key="s">{{ " " + _s(s + 1) + ". " + _s(o.title) + " " }}</p></template></div></div><div class="dialog-footer" slot="footer"><el-button type="primary" size="medium" @click="(e) => {
                                return saveBook(importBookInfo, !0)
                            }">确定导入</el-button><el-button size="medium" @click="(e) => {
                                showImportBookDialog = !1
                            }">取消</el-button></div></el-dialog></template><ShelfSettings v-model="showShelfSettingsDialog" /></div>
</template>

<script>
import axios from 'axios'
import defaults from '@/config/defaults'
import { mapState } from 'vuex'
import { merge as __merge } from '@/utils/merge'
import { regeneratorRuntime } from '@/utils/regenerator'
import { asyncToGenerator } from '@/utils/async'
import { setStorage } from '@/utils/storage'
import eventBus from '@/utils/event-bus'
import VirtList from '@/components/VirtList'
import Explore from '@/views/index/e5965c98/e5965c98.vue'
import BookList from '@/views/index/2fbafdaa/2fbafdaa.vue'
import BookColumn from '@/views/index/77d83090/77d83090.vue'
import ShelfSettings from '@/views/index/70ce2ba2/70ce2ba2.vue'

// ---------------------------------------------------------------------------
// 产物里被 __vendorRequire / 项目模块提供、工程侧没有同名文件的辅助函数。
// 语义按产物逐条对齐（注释标出出处模块），放在组件外，避免每次实例化重建。
// ---------------------------------------------------------------------------

/** 底部「关于」图标的夜间/日间图（产物图片模块 fa39 / 0d98，导出即 data URI） */
const IMG_BOTTOM_ICON_NIGHT = 'data:image/png;base64,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'
const IMG_BOTTOM_ICON_DAY = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAICElEQVR4Xu1bfYxdRRU/Z3bjbtJK4gcCmpilPEJ779xHVgWRQKKBtibyEb/AFEEQgoLRKNGWYiLWRAXR+EcjoqRSP6ihBrV8GEXRJhqJ0FZ9c+Z1Sdd2Y6QEjWBiNy123xxz8O5m9+29d2bue2/XhM6fO7/z9XtnZs6cuYvwMh/4Mo8fThBwIgOWgIHVq1ePDQ8PjznnxpRSYwDQQMQGM58p5hHxADNPAsCkc25KKTU1MzMzNTExMTVo9wa2BJrN5lnMfCUAXMHMaZ1AENECwE5EfKDVaj1dR4dPpu8ENJvNyzqdzpWIeAUADPscCJyfYeadQ0NDQsRDgTJBsL4QkCTJSkS8OQ/6zUGW64P2ChnMfHe73T5SX83/JHsmIEmSC5VSdwLA23p1JlL+Cefcpna7/dtIuQXwngjQWn8cAL4CAKO9ONGD7DEA2EhEW+vqqE2A1vo7AHBdXcN9lruPiD5cR2ctArIs28fM43UMDkoGEf9ojHlTrP5oArTWHGtkKfFEFBVTFFhrLUfQpUsZUA1bDxPRZaFywQRkWXYjM38rVPFy4hDxI8aYb4f4EERAmqbrEfHnFQq35HPn5cfhSSHGa2IOAsBBZjaI+KkyHcz8TmvtL3w2vARISeucewoAXlmkjJkPdzqd5sTExD9lfs2aNacNDQ3diIiSMa/3ORA4/y9E3N7pdL7bbrf/NCvj2Y/+rZQ6x1dCVxIwNjY2umLFikcQ8aIKR3cSkdT8C8YsEQDw+QLZSWY+gIhyAQJmlsuRXIwaBdgtMzMz24suRlprKYIuqMiCx6enpy+ZmpqSeqFwVBKQpulGRJQqr2p8oqoQSdM0EQIlWOfcAWvtS0GXjTRNG0qpM4UUZn7cWtsuw2ZZdiszf7lKHzNvstZKsRZPgNZ6DwD4avtLieiRwFTuK0xrfQkAPOxRupeI3hJNgNzqnHO7AjzeQkRFaR4g2htEay12b/dpUUpdXnaLLF0CaZrej4gbfMrlF4g5dwP0BUNC6xJm3mGtvapIcSEB+c5PIfd5RDxsjHlDsNd9BGZZ9kzgSTOjlNJFJ0IhAVmWfY6ZZ892n8v/9xkgASDi7caYL3QHU0YABbaxXux0Oqfv37//WR9Lg5jPj9pDADDi0y/tNWOM9hKQZdkqZv6LT2E+L3fxuwKxA4FprT+T9yS8+hHxDGOMVJJzY1EGZFl2MTP/0qsN4DEiWh+AGzhEay0l7zqfIURca4z5lY+A0EvPV4lI2F/2obWWLPy0z5GiS9KiDEjT9A5E3ORTBgDXENH3A3ADh2itrwaA7/kMMfOd1tpbKzNAa70TAN7vUwYAZxNRKwA3cIjWugkAfw4w9CMiknZ9+R4QWP5CbOclwLmeIIGdqkVl8aIloLV+HgBe5fNGKZW1Wi0plpZ9RJxcLxDRq30ZII8NK3xRMfMGa+0PfbilmI84uaaJaKWPADknTw9w/EtE9NkA3MAhoe06Zp6y1i6IrWgJPAEA0tqqHIj4kDHmch9uKeYjTq6niOjcygzIsmwXM4d0VQ8R0aqlCNBnI+Lk+hkRvcu3BO4FgBt8RmUeEd9tjPlpCHZQGLkPDA8P7wm5FTLzdmvtgtesoiXwRQC4LcThvGV1cQh2UJjQpkhu/y4i2ujLgLUA8Fiow8z8IWuttwoL1ReDi/n1c70fIKIHKgmQSa21tLgXnJcVjj1JRG+Ncbxf2Mhf/7hzbmW73f5PCAE/AIDCFlKJ848y8zXWWimilmSkafpJRPx6hLGfENF7uvFlDZGrmFlIiBm/A4BriSi0lxCjew47Pj5+8vHjx6VVH/s0fz0RyZP+glFIQKPROGl0dFQ6LaHLYFapdJK2DQ0N7Wi1Wn+vFWGJULPZfF2n09mAiNfKRSxSt7wsrTLGvBBEQL4PfAMAbu4SmGuBa603y5UYAFYXOPMcIu5wzj2IiH91zj3XvfZ8ASRJ8gql1CnM/Eal1Hul9AaAU3xyJfN3E9HHiuZK2+Ja6zMAQKrCk7sE5x5CkiQ5Vyn1KAC81uOYfOL2LBG9IyQArfVvAOA0ADgrBO/B/EMebMuWZuXTWMXT001EdI8YDnxAOeKcu6jdbj8ZElCz2TzPObc7pNnp04eIm40xd5ThKgloNBojo6OjvweAok9P1hPRS/VClmXXMfOiDWae0W1EFFRdzspkWXYLM3/NF6Bnft+xY8fOn5ycfLEWAfle8EEAKGp9HUHE840xJsdJT66sQ3wDEW2LCSZJknGl1L4YmQLs1URUeZp5vw/IgytskyHiLcaYubNYWlPMfCEivm+eM1uJ6MexgSRJcqpSqpf3hkXtr6hNsBtccuN6HhHXGWP2xgYYgg9scxWpCgpeBIMyYNZCvju/fb5FRPyDMcbbPwgJuID0Ol+k7Q49baIJEIE0TQ8honzyPn88jYi7EHFrq9X6W51gi2RiM6Co4+PzJSoD5mVC1S8jF6nDADANAELUqXU7yLEE1LFTi4B8Y5QPp0q/vJjPfB3HchuhS2APEZ3j+7V72gRLUvSbAPBRn+EBE3APEd3k86FsvnYGzFsO1wOAfKjUXTLP2RwQAVLibo6tL7qJ6JmAfGM8GxGlYJIr6mu6jfSZgGcQ8X7nnHz2EvIcVpkcfSFg1oJco0dGRuTfZeT/CDL5e52deVZf14mzGxHvPXr06INVpW3sUugrAfONa63X5cflwe43+VAntdZr5UsVRPz1oB5iB0ZAaJDLjTtBwHL/Astt/0QGLPcvsNz2/wumfi9urtACBwAAAABJRU5ErkJggg=='

/** 字节数格式化（产物模块 7513 的导出 d，变量 s） */
function formatBytes(bytes, digits) {
  if (bytes == null || bytes === '') return '0 Bytes'
  var units = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB']
  var n = Math.floor(Math.log(bytes) / Math.log(1024))
  var r = bytes / Math.pow(1024, n)
  r = r.toFixed(digits || 2)
  return r + ' ' + units[n]
}

/**
 * 并发受限任务队列（产物模块 7513 的导出 a，变量 c）
 * 返回 runner(task)：task 入队，未达并发上限就立刻起跑；
 * runner 上挂 requestCount / leftCount / errorCount / cancel() / isEnd()。
 */
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

/** 元素是否与容器可视区相交（产物用的 element-ui utils/dom 的 isInContainer，模块 526f 导出 d） */
function isInContainer(el, container) {
  if (!el || !container) return false
  var rect = el.getBoundingClientRect()
  var cRect
  if ([window, document, document.documentElement, null, undefined].includes(container)) {
    cRect = { top: 0, right: window.innerWidth, bottom: window.innerHeight, left: 0 }
  } else {
    cRect = container.getBoundingClientRect()
  }
  return (
    rect.top < cRect.bottom && rect.bottom > cRect.top && rect.right > cRect.left && rect.left < cRect.right
  )
}

/** 拼 query string（产物用的 axios buildURL，模块 30b5） */
function buildURL(url, params, serializer) {
  function encode(val) {
    return encodeURIComponent(val)
      .replace(/%3A/gi, ':')
      .replace(/%24/g, '$')
      .replace(/%2C/gi, ',')
      .replace(/%20/g, '+')
      .replace(/%5B/gi, '[')
      .replace(/%5D/gi, ']')
  }
  if (!params) return url
  var parts
  var isURLSearchParams = typeof URLSearchParams !== 'undefined' && params instanceof URLSearchParams
  var isArray = function (v) {
    return Object.prototype.toString.call(v) === '[object Array]'
  }
  var isDate = function (v) {
    return Object.prototype.toString.call(v) === '[object Date]'
  }
  var isObject = function (v) {
    return v !== null && typeof v === 'object'
  }
  if (serializer) {
    parts = serializer(params)
  } else if (isURLSearchParams) {
    parts = params.toString()
  } else {
    var arr = []
    Object.keys(params).forEach(function (key) {
      var val = params[key]
      if (val === null || typeof val === 'undefined') return
      var k = key
      if (isArray(val)) k += '[]'
      else val = [val]
      val.forEach(function (v) {
        if (isDate(v)) v = v.toISOString()
        else if (isObject(v)) v = JSON.stringify(v)
        arr.push(encode(k) + '=' + encode(v))
      })
    })
    parts = arr.join('&')
  }
  if (parts) {
    var hashmark = url.indexOf('#')
    if (hashmark !== -1) url = url.slice(0, hashmark)
    url += (url.indexOf('?') === -1 ? '?' : '&') + parts
  }
  return url
}

/** 书源检测的失效类型清单（产物模块 bfff 的导出 e，变量 P） */
const FAILURE_TYPE_LIST = [
  'UnknownHostException',
  'ConnectException: Failed to connect',
  'SocketException: Connection reset',
  'SSLHandshakeException',
  'responseCode: 307',
  'responseCode: 400',
  'responseCode: 403',
  'responseCode: 404',
  'responseCode: 500',
  'responseCode: 502',
  'responseCode: 503',
  'responseCode: 504',
  'responseCode: 513'
]

/**
 * 64 位整数的位运算替身（产物模块 f537 = Closure Library goog.math.Long）。
 * 本页面只用到 fromNumber / or / and / greaterThan / toNumber 五个成员，
 * 用途是书籍分组位掩码（group 每个 bit 代表一个分组）。
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

/**
 * BookKind（产物模块 0fe7，无 scopeId，未单独落盘）：
 * 只把 wordCount / kind 渲染成 span.small-tag，render 与产物逐行一致。
 */
const BookKind = {
  name: 'BookKind',
  props: ['book'],
  methods: {
    renderBookKind: function () {
      if (!this.book) return ''
      var e = this.book.wordCount + ',' + this.book.kind
      if (!e) return ''
      var t = e.split(',')
      return t
        .filter(function (e) {
          return e && 'undefined' !== e
        })
        .map(function (e) {
          return '<span class="small-tag">'.concat(e, '</span>')
        })
        .join('')
    }
  },
  render: function () {
    var e = this,
      t = e._self._c
    return t('div', {
      staticClass: 'book-kind',
      domProps: { innerHTML: e._s(e.renderBookKind(e.book)) }
    })
  }
}

// BookVirtualList：产物里同样没有 scopeId（normalizeComponent 第 6 参数为 null），
// 前置恢复没有为它单独落盘，这里把产物的 options + render 原样内联。
const BookVirtualList = {
  name: 'BookList',
  components: {
    BookKind,
    VirtList,
    Explore,
    ShelfSettings,
    BookList,
    BookColumn: VirtList,
    BookVirtualList: VirtList,
    VirtualSelect: VirtList,
  },
  data() {
    return {
                              search: "",
                              searchTypeList: [{
                                  name: "单源搜索",
                                  value: "single"
                              }, {
                                  name: "多源搜索(过滤书名/作者名)",
                                  value: "multi"
                              }],
                              isSearchResult: false,
                              isExploreResult: false,
                              searchResult: [],
                              searchPage: 1,
                              refreshLoading: false,
                              searchLastIndex: -1,
                              showBookEditButton: false,
                              popExploreVisible: false,
                              loadingMore: false,
                              importSourceList: [],
                              showImportSourceDialog: false,
                              isImportRssSource: false,
                              checkAll: false,
                              isIndeterminate: false,
                              checkedSourceIndex: [],
                              showBookSourceManageDialog: false,
                              manageSourceSelection: [],
                              isShowFailureBookSource: false,
                              checkBookSourceTip: "",
                              isCheckingBookSource: false,
                              showNavigation: false,
                              navigationClass: "",
                              navigationStyle: {},
                              popIntroVisible: {},
                              connecting: false,
                              lastScrollTop: 0,
                              localStorageAvaliable: window.localStorage && window.localStorage.getItem && window.localStorage.setItem,
                              showSourceGroup: "全部",
                              bookSourcePagination: {
                                  page: 1,
                                  size: 25
                              },
                              bookSourceKeyword: "",
                              checkBookSourceConfig: {
                                  keyword: "斗罗大陆",
                                  timeout: 5e3,
                                  concurrent: 5
                              },
                              importBookInfo: {},
                              importBookGroup: [],
                              importBookChapters: [],
                              showImportBookDialog: false,
                              importMultiBookTip: "",
                              rssSource: {},
                              concurrentList: [1, 2, 4, 8, 12, 18, 24, 30, 36, 42, 48, 54, 60],
                              localCacheStats: {
                                  total: "0 Bytes",
                                  bookSourceList: "0 Bytes",
                                  rssSources: "0 Bytes",
                                  chapterList: "0 Bytes",
                                  chapterContent: "0 Bytes"
                              },
                              showLocalStoreManageDialog: false,
                              showWebDAVManageDialog: false,
                              importUsedTxtRule: "",
                              showAddUser: false,
                              addUserForm: {
                                  username: "",
                                  password: ""
                              },
                              isTauri: window.__TAURI__,
                              keyword: "",
                              showShelfSettingsDialog: false,
                              searchBookSourceListMap: {},
                              isShowSearchBookSourceListDesc: false
                          }
  },
  props: ['showNavigation', 'bookList', 'isSearchResult', 'showBookEditButton', 'showSourceCount'],
  computed: __merge({}, mapState(['dialogSmallWidth', 'dialogTop'])),
  methods: {
    getBookCoverUrl: function (t) {
      return t.customCoverUrl || t.coverUrl
    }
  },
  render: function () {
    var t = this,
        e = t._self._c;
    return e("div", {
        staticClass: "wrapper"
    }, [e("VirtList", {
        attrs: {
            itemKey: "bookUrl",
            list: t.bookList,
            minSize: 112,
            itemClass: "book",
            itemStyle: t.showNavigation ? {
                minWidth: "360px !important"
            } : {}
        },
        scopedSlots: t._u([{
            key: "default",
            fn: function(o) {
                var s = o.itemData;
                return [e("div", {
                    staticClass: "cover-img",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("showBookInfoDialog", s)
                        }
                    }
                }, [e("el-image", {
                    ref: "bookCoverList",
                    staticClass: "cover",
                    attrs: {
                        src: t.getCover(t.getBookCoverUrl(s), !0),
                        fit: "cover",
                        lazy: ""
                    }
                })], 1), e("div", {
                    staticClass: "info",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("toDetail", s)
                        }
                    }
                }, [e("div", {
                    staticClass: "book-operation"
                }, [!t.isSearchResult && t.showBookEditButton ? e("i", {
                    staticClass: "el-icon-close",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("deleteBook", s)
                        }
                    }
                }) : t._e(), !t.isSearchResult && t.showBookEditButton ? e("i", {
                    staticClass: "el-icon-edit",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("editBook", s)
                        }
                    }
                }) : t._e(), t.isSearchResult ? e("i", {
                    staticClass: "el-icon-edit",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("editBook", s, !0)
                        }
                    }
                }) : t._e(), !t.isSearchResult && !t.showBookEditButton && s.totalChapterNum - 1 - s.durChapterIndex > 0 ? e("el-badge", {
                    staticClass: "unread-num-badge",
                    attrs: {
                        max: 99,
                        value: s.totalChapterNum - 1 - s.durChapterIndex
                    }
                }) : t._e()], 1), e("div", {
                    staticClass: "name",
                    class: t.showBookEditButton ? "edit" : "",
                    attrs: {
                        slot: "reference"
                    },
                    slot: "reference"
                }, [t._v(" " + t._s(s.name) + " ")]), e("div", {
                    staticClass: "sub"
                }, [e("div", {
                    staticClass: "author"
                }, [t._v(" " + t._s(s.author || "") + " ")]), s.totalChapterNum ? e("div", {
                    staticClass: "dot"
                }, [t._v("•")]) : t._e(), s.totalChapterNum ? e("div", {
                    staticClass: "size"
                }, [t._v(" 共" + t._s(s.totalChapterNum) + "章 ")]) : t._e()]), !t.isSearchResult && s.durChapterTitle ? e("div", {
                    staticClass: "dur-chapter"
                }, [t._v(" 已读：" + t._s(s.durChapterTitle) + " ")]) : t._e(), t.isSearchResult ? e("BookKind", {
                    attrs: {
                        book: s
                    }
                }) : t._e(), s.latestChapterTitle ? e("div", {
                    staticClass: "last-chapter"
                }, [t._v(" " + t._s(s.lastCheckTime ? t.dateFormat(s.lastCheckTime) : "最新") + "：" + t._s(s.latestChapterTitle) + " ")]) : t._e(), t.isSearchResult ? e("div", [e("el-tag", {
                    staticClass: "setting-connect",
                    attrs: {
                        type: "success",
                        effect: t.$store.getters.isNight ? "dark" : "light"
                    },
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("addBookToShelf", s)
                        }
                    }
                }, [t._v(" 加入书架 ")]), t.showSourceCount ? e("span", {
                    staticClass: "source-count",
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("showSearchBookSourceList", s)
                        }
                    }
                }, [t._v(t._s(s.sourceCount || 1) + " 个书源")]) : t._e()], 1) : t._e()], 1)]
            }
        }])
    })], 1)
}
}

const VirtualSelect = {
  name: 'VirtualSelect',
  props: {
    width: { type: Number, default: 250 },
    size: { type: String, default: 'small' },
    placeholder: { type: String, default: '请选择' },
    dataList: {
      type: Array,
      default: function () {
        return []
      }
    },
    value: { type: [String, Number], default: '' }
  },
  components: { VirtList },
  watch: {
    visibleVirtualList: function (t) {
      var e = this
      t
        ? ((this.keyword = ''),
          this.$nextTick(function () {
            var t = e.curIndex ? e.curIndex : 0
            e.$refs.virtList.scrollToIndex(t - 1)
          }))
        : (this.keyword = this.curLabel)
    },
    value: function (t) {
      var e
      this.curValue = t
      this.curLabel =
        (null === (e = this.dataList.find(function (e) {
          return e.bookSourceUrl === t
        })) || void 0 === e
          ? void 0
          : e.bookSourceName) || ''
      this.curIndex = this.dataList.findIndex(function (e) {
        return e.bookSourceUrl === t
      })
    },
    dataList: function (t) {
      var e,
        o = this
      this.curLabel =
        (null === (e = t.find(function (t) {
          return t.bookSourceUrl === o.value
        })) || void 0 === e
          ? void 0
          : e.bookSourceName) || ''
      this.curIndex = t.findIndex(function (t) {
        return t.bookSourceUrl === o.value
      })
    }
  },
  data: function () {
    return { visibleVirtualList: !1, curValue: this.value, curLabel: '', curIndex: null, keyword: '' }
  },
  created: function () {
    var t = this
    this.$on('clickVirtualItem', function (e) {
      ;(t.curIndex = e.index),
        (t.curLabel = e.bookSourceName),
        (t.visibleVirtualList = !1),
        t.$emit('input', e.bookSourceUrl)
    })
  },
  computed: {
    list: function () {
      var t = this
      return this.dataList.filter(function (e) {
        return !t.keyword || e.bookSourceName.includes(t.keyword)
      })
    },
    tip: function () {
      return this.curLabel || this.placeholder || '请选择'
    }
  },
  mounted: function () {
    var t,
      e = this
    ;(this.curValue = this.value),
      (this.curLabel =
        (null === (t = this.dataList.find(function (t) {
          return t.bookSourceUrl === e.value
        })) || void 0 === t
          ? void 0
          : t.bookSourceName) || ''),
      (this.curIndex = this.dataList.findIndex(function (t) {
        return t.bookSourceUrl === e.value
      })),
      (this.keyword = this.curLabel)
  },
  methods: {
    handleInput: function (t) {
      ;(this.keyword = t), this.$refs.virtList.$forceUpdate()
    }
  },
  render: function () {
    var t = this,
        e = t._self._c;
    return e("el-popover", {
        attrs: {
            "popper-class": "select-virtual-list-popover",
            trigger: "click",
            placement: "bottom-start",
            width: t.width
        },
        on: {
            show: function(e) {
                return t.$refs.virtList.$forceUpdate()
            }
        },
        model: {
            value: t.visibleVirtualList,
            callback: function(e) {
                t.visibleVirtualList = e
            },
            expression: "visibleVirtualList"
        }
    }, [e("VirtList", {
        ref: "virtList",
        attrs: {
            itemKey: "bookUrl",
            list: t.list,
            minSize: 30
        },
        scopedSlots: t._u([{
            key: "default",
            fn: function(o) {
                var s = o.itemData;
                return [e("div", {
                    class: ["virtual-item", {
                        "is-selected": s.bookSourceUrl === t.curValue
                    }],
                    on: {
                        click: function(e) {
                            return e.stopPropagation(), t.$emit("clickVirtualItem", s)
                        }
                    }
                }, [e("span", [t._v(t._s(s.bookSourceName))])])]
            }
        }])
    }), e("el-input", {
        attrs: {
            slot: "reference",
            size: t.size,
            placeholder: t.tip,
            "suffix-icon": t.visibleVirtualList ? "el-icon-arrow-up" : "el-icon-arrow-down"
        },
        slot: "reference",
        model: {
            value: t.keyword,
            callback: function(e) {
                t.keyword = e
            },
            expression: "keyword"
        }
    })], 1)
}
}

export default {
    components: {
    Explore,
    BookList,
    BookVirtualList,
    BookColumn,
    ShelfSettings,
    VirtualSelect
  },
    data: function() {
        return {
          // 产物里这两张图是 webpack 图片模块（fa39 = 夜间、0d98 = 日间），导出即 base64 data URI
          bottomIconNight: IMG_BOTTOM_ICON_NIGHT,
          bottomIconDay: IMG_BOTTOM_ICON_DAY,
            search: "",
            searchTypeList: [{
                name: "单源搜索",
                value: "single"
            }, {
                name: "多源搜索(过滤书名/作者名)",
                value: "multi"
            }],
            isSearchResult: !1,
            isExploreResult: !1,
            searchResult: [],
            searchPage: 1,
            refreshLoading: !1,
            searchLastIndex: -1,
            showBookEditButton: !1,
            popExploreVisible: !1,
            loadingMore: !1,
            importSourceList: [],
            showImportSourceDialog: !1,
            isImportRssSource: !1,
            checkAll: !1,
            isIndeterminate: !1,
            checkedSourceIndex: [],
            showBookSourceManageDialog: !1,
            manageSourceSelection: [],
            isShowFailureBookSource: !1,
            checkBookSourceTip: "",
            isCheckingBookSource: !1,
            showNavigation: !1,
            navigationClass: "",
            navigationStyle: {},
            popIntroVisible: {},
            connecting: !1,
            lastScrollTop: 0,
            localStorageAvaliable: window.localStorage && window.localStorage.getItem && window.localStorage.setItem,
            showSourceGroup: "全部",
            bookSourcePagination: {
                page: 1,
                size: 25
            },
            bookSourceKeyword: "",
            checkBookSourceConfig: {
                keyword: "斗罗大陆",
                timeout: 5e3,
                concurrent: 5
            },
            importBookInfo: {},
            importBookGroup: [],
            importBookChapters: [],
            showImportBookDialog: !1,
            importMultiBookTip: "",
            rssSource: {},
            concurrentList: [1, 2, 4, 8, 12, 18, 24, 30, 36, 42, 48, 54, 60],
            localCacheStats: {
                total: "0 Bytes",
                bookSourceList: "0 Bytes",
                rssSources: "0 Bytes",
                chapterList: "0 Bytes",
                chapterContent: "0 Bytes"
            },
            showLocalStoreManageDialog: !1,
            showWebDAVManageDialog: !1,
            importUsedTxtRule: "",
            showAddUser: !1,
            addUserForm: {
                username: "",
                password: ""
            },
            isTauri: window.__TAURI__,
            keyword: "",
            showShelfSettingsDialog: !1,
            searchBookSourceListMap: {},
            isShowSearchBookSourceListDesc: !1
        }
    },
    watch: {
        searchConfig: {
            handler: function(t) {
                this.$store.commit("setSearchConfig", t), this.isSearchResult && this.searchBook(1)
            },
            deep: !0
        },
        searchResult: function(t) {
            var e = this;
            this.isSearchResult && t.length && this.$nextTick((function() {
                e.$refs.bookList.scrollTop = e.lastScrollTop
            }))
        },
        collapseMenu: function(t) {
            t ? this.showNavigation || (this.navigationClass = "navigation-hidden") : this.navigationClass = ""
        },
        showNavigation: function(t) {
            var e = this;
            t ? this.navigationClass = "navigation-in" : (this.navigationClass = "navigation-out", setTimeout((function() {
                e.navigationClass = "navigation-hidden"
            }), 300))
        },
        loginAuth: function() {
            this.init(!0)
        },
        userNS: function() {
            this.init(!0)
        },
        importUsedTxtRule: function(t) {
            t && (this.importBookInfo.tocUrl = t)
        },
        importBookGroup: function(t) {
            if (t && this.showImportBookDialog) {
                var e = 0;
                t.forEach((function(t) {
                    e |= t
                })), this.importBookInfo.group = e
            }
        },
        showBookGroup: function() {
            var t = this;
            this.$nextTick((function() {
                setTimeout(t.ensureLoadBookCover)
            }))
        },
        showBookEditButton: function(t) {
            t || (this.keyword = "")
        }
    },
    mounted: function() {
        var t = this;
        document.title = "阅读", this.navigationClass = this.collapseMenu && !this.showNavigation ? "navigation-hidden" : "", window.shelfPage = this, eventBus.$on("onSourceFileChange", (function(e, o) {
            t._inactive || t.onSourceFileChange(e, o)
        })), eventBus.$on("editBook", (function(e, o, s) {
            t._inactive || t.editBook(e, o, s)
        })), eventBus.$on("importPreview", (function(e) {
            t._inactive || t.importMultiBooks(e)
        }))
    },
    activated: function() {
        document.title = "阅读", this.scanCacheStorage(), this.loadBookshelf(), this.lastScrollTop && (this.$refs.bookList.scrollTop = 0 | this.lastScrollTop)
    },
    methods: {
      /** 模板插值里的 str(...)：等价于 Vue 内部 _s（null/undefined 渲染成空串） */
      str: function (val) {
        return val == null
          ? ''
          : typeof val === 'object'
          ? JSON.stringify(val, null, 2)
          : String(val)
      },
        init: function(t) {
            this.$root.$children[0].init(t)
        },
        setIP: function() {
            var t = this;
            this.$prompt("请输入接口地址 ( 如：localhost:8080/reader3 )", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                inputValue: this.api,
                beforeClose: function(e, o, s) {
                    if ("confirm" === e) {
                        t.connecting = !0, o.confirmButtonLoading = !0, o.confirmButtonText = "校验中……";
                        var r = o.inputValue.replace(/\/*$/g, "");
                        t.loadBookshelf(r).then((function() {
                            t.connecting = !1, o.confirmButtonLoading = !1, s(), setStorage("api_prefix", r), t.$store.commit("setApi", r), t.init()
                        }))["catch"]((function() {
                            o.confirmButtonLoading = !1, o.confirmButtonText = "确定"
                        }))
                    } else s()
                }
            }).then((function(e) {
                var o = e.value;
                t.$message({
                    type: "success",
                    message: "与" + o + "连接成功"
                })
            }))["catch"]((function() {}))
        },
        loadBookshelf: function(t, e) {
            var o = this;
            return t = t || this.api, t ? (this.shelfBooks && this.shelfBooks.length || this.loading && this.loading.visible || (this.loading = this.$loading({
                target: this.$refs.bookList,
                lock: !0,
                text: e ? "正在刷新书籍信息" : "正在获取书籍信息",
                spinner: "el-icon-loading",
                background: this.isNight ? "#222" : "#fff"
            })), this.refreshLoading = !0, t.startsWith("http://") || t.startsWith("https://") || t.startsWith("//") || (t = "//" + t), this.$root.$children[0].loadBookShelf(e, t).then((function() {
                o.refreshLoading = !1, o.loading && o.loading.close()
            }))) : (this.$message.error("请先设置后端接口地址"), this.$store.commit("setConnected", !1), Promise.reject(!1))
        },
        refreshShelf: function() {
            return this.loadBookshelf(null, !0)
        },
        loadBookGroup: function(t) {
            return this.$root.$children[0].loadBookGroup(t)
        },
        loadBookSource: function(t) {
            return this.$root.$children[0].loadBookSource(t)
        },
        searchBook: function(t) {
            var e = this;
            this.$store.state.connected ? this.search ? "single" !== this.searchConfig.searchType || this.searchConfig.bookSourceUrl ? (t && (this.searchPage = t), t = this.searchPage, 1 === t && (this.searchLastIndex = -1, this.searchBookSourceListMap = {}), "multi" === this.searchConfig.searchType && window.EventSource ? this.searchBookByEventStream(t) : this.loadingMore || (this.isSearchResult = !0, this.isExploreResult = !1, this.loadingMore = !0, 1 === t && (this.searchResult = []), axios.post(this.api + ("single" === this.searchConfig.searchType ? "/searchBook" : "/searchBookMulti"), {
                key: this.search,
                bookSourceUrl: this.searchConfig.bookSourceUrl,
                bookSourceGroup: this.searchConfig.bookSourceGroup,
                concurrentCount: this.searchConfig.concurrentCount,
                lastIndex: this.searchLastIndex,
                page: t
            }, {
                timeout: "single" === this.searchConfig.searchType ? 3e4 : 18e4
            }).then((function(t) {
                if (e.loadingMore = !1, t.data.isSuccess) {
                    var o = [];
                    "single" === e.searchConfig.searchType ? o = t.data.data : (e.searchLastIndex = t.data.data.lastIndex, o = t.data.data.list);
                    var s = [].concat(e.searchResult),
                        r = s.reduce((function(t, e) {
                            return t[e.name + "_" + e.author] = e, t
                        }), {}),
                        i = s.length;
                    o.forEach((function(t) {
                        e.searchResultMap[t.bookUrl] || (r[t.name + "_" + t.author] ? (r[t.name + "_" + t.author].sourceCount = r[t.name + "_" + t.author].sourceCount || 1, r[t.name + "_" + t.author].sourceCount += 1) : (t.sourceCount = 1, r[t.name + "_" + t.author] = t, s.push(t))), e.searchBookSourceListMap[t.name + "_" + t.author] = e.searchBookSourceListMap[t.name + "_" + t.author] || [], e.searchBookSourceListMap[t.name + "_" + t.author].push(t)
                    })), e.searchResult = s, s.length === i && e.$message.error("没有更多啦")
                }
            }), (function(t) {
                e.$message.error("搜索书籍失败 " + (t && t.toString()))
            })))) : this.$message.error("请选择书源进行搜索") : this.$message.error("请输入关键词进行搜索") : this.$message.error("后端未连接")
        },
        searchBookByEventStream: function(t) {
            var e = this,
                o = function() {
                    try {
                        e.searchEventSource && e.searchEventSource.readyState != e.searchEventSource.CLOSED && e.searchEventSource.close(), e.searchEventSource = null
                    } catch (t) {}
                };
            if (!this.loadingMore || (o(), this.loadingMore = !1, 1 === t)) {
                var s = {
                    accessToken: this.$store.state.token,
                    key: this.search,
                    bookSourceUrl: this.searchConfig.bookSourceUrl,
                    bookSourceGroup: this.searchConfig.bookSourceGroup,
                    concurrentCount: this.searchConfig.concurrentCount,
                    lastIndex: this.searchLastIndex,
                    page: t
                };
                this.isSearchResult = !0, this.isExploreResult = !1, this.loadingMore = !0, 1 === t && (this.searchResult = []);
                var r = buildURL(this.api + "/searchBookMultiSSE", s);
                o(), this.searchEventSource = new EventSource(r, {
                    withCredentials: !0
                }), this.searchEventSource.addEventListener("error", (function(t) {
                    e.loadingMore = !1, o();
                    try {
                        if (t.data) {
                            var s = JSON.parse(t.data);
                            s && s.errorMsg && e.$message.error(s.errorMsg)
                        }
                    } catch (r) {}
                }));
                var i = this.searchResult.length;
                this.searchEventSource.addEventListener("end", (function(t) {
                    e.loadingMore = !1, o();
                    try {
                        var s = !1;
                        if (t.data) {
                            var r = JSON.parse(t.data);
                            r && r.lastIndex && (e.searchLastIndex = r.lastIndex), r && r.isEnd && (s = !0)
                        }
                        e.searchResult.length === i && e.$message.error(s ? "没有更多啦" : "本次未搜索到数据")
                    } catch (a) {}
                })), this.searchEventSource.addEventListener("message", (function(t) {
                    try {
                        if (t.data) {
                            var o = JSON.parse(t.data);
                            if (o && o.lastIndex && (e.searchLastIndex = o.lastIndex), o.data) {
                                var r = [].concat(e.searchResult),
                                    i = r.reduce((function(t, e) {
                                        return t[e.name + "_" + e.author] = e, t
                                    }), {});
                                o.data.forEach((function(t) {
                                    e.searchResultMap[t.bookUrl] || (i[t.name + "_" + t.author] ? (i[t.name + "_" + t.author].sourceCount = i[t.name + "_" + t.author].sourceCount || 1, i[t.name + "_" + t.author].sourceCount += 1) : (t.sourceCount = 1, i[t.name + "_" + t.author] = t, r.push(t))), e.searchBookSourceListMap[t.name + "_" + t.author] = e.searchBookSourceListMap[t.name + "_" + t.author] || [], e.searchBookSourceListMap[t.name + "_" + t.author].push(t)
                                })), e.searchResult = r.sort((function(t, e) {
                                    return t.name === s.key ? -1 : e.name === s.key ? 1 : t.name.indexOf(s.key) >= 0 ? -1 : t.name.indexOf(s.key) >= 0 ? 1 : t.sourceCount > e.sourceCount ? -1 : 1
                                }))
                            }
                        }
                    } catch (a) {}
                }))
            }
        },
        searchBookManual: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            t.$prompt("请输入书籍链接", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                inputValue: t.search,
                                inputPattern: /^(http|https):\/\/.+$/,
                                inputErrorMessage: "url 形式不正确"
                            }).then((function(e) {
                                var o = e.value;
                                t.searchBookDirectly(o)
                            }))["catch"]((function() {}));
                        case 1:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        searchBookDirectly: function(t) {
            var e = this;
            this.isSearchResult = !0, this.isExploreResult = !1, this.loadingMore = !0, axios.post(this.api + "/getBookInfo", {
                url: t,
                bookSourceUrl: this.searchConfig.bookSourceUrl
            }, {
                timeout: 3e4
            }).then((function(t) {
                e.loadingMore = !1, t.data.isSuccess && (t.data.data && t.data.data.name && t.data.data.author ? e.searchResult = [t.data.data] : e.$message.error("没有搜索到书籍"))
            }), (function(t) {
                e.$message.error("搜索书籍失败 " + (t && t.toString()))
            }))
        },
        saveBookManual: function() {
            var t = this,
                e = {
                    bookUrl: "书籍链接",
                    tocUrl: "目录链接",
                    origin: this.searchConfig.bookSourceUrl,
                    originName: "花生小说",
                    name: "书名",
                    author: "作者"
                };
            this.editBook(e, !0, (function() {
                t.loadBookshelf()
            }))
        },
        showSearchBookSourceList: function(t) {
            this.oldSearchResult = [].concat(this.searchResult), this.searchResult = this.searchBookSourceListMap[t.name + "_" + t.author] || [], this.isShowSearchBookSourceListDesc = !0
        },
        displaySearchResult: function() {
            this.searchResult = this.oldSearchResult, this.isShowSearchBookSourceListDesc = !1
        },
        toDetail: function(t) {
            var e, o;
            t.bookUrl && (this.isSearchResult, this.$store.commit("setReadingBook", __merge(__merge({}, t), {}, {
                name: t.name,
                bookUrl: t.bookUrl,
                index: null !== (e = null !== (o = t.index) && void 0 !== o ? o : t.durChapterIndex) && void 0 !== e ? e : 0,
                type: t.type,
                coverUrl: this.getBookCoverUrl(t),
                tocUrl: t.tocUrl,
                author: t.author,
                origin: t.origin,
                originName: t.originName,
                latestChapterTitle: t.latestChapterTitle,
                intro: t.intro
            })), this.$router.push({
                path: "/reader",
                query: this.isSearchResult ? {
                    search: 1,
                    bookUrl: t.bookUrl
                } : {
                    bookUrl: t.bookUrl
                }
            }))
        },
        addBookToShelf: function(t) {
            var e = this;
            return asyncToGenerator(regeneratorRuntime.mark((function o() {
                var s, r;
                return regeneratorRuntime.wrap((function(o) {
                    while (1) switch (o.prev = o.next) {
                        case 0:
                            return s = __merge({}, t), o.next = 3, e.customImportBookInfo({
                                title: "修改书籍",
                                cancelButtonText: "暂不加入"
                            }, s);
                        case 3:
                            if (r = o.sent, !1 !== r) {
                                o.next = 6;
                                break
                            }
                            return o.abrupt("return");
                        case 6:
                            e.saveBook(__merge(__merge({}, s), r));
                        case 7:
                        case "end":
                            return o.stop()
                    }
                }), o)
            })))()
        },
        saveBook: function(t, e, o) {
            var s = this;
            return t && t.bookUrl && t.origin ? axios.post(this.api + "/saveBook", t).then((function(t) {
                if (t.data.isSuccess) return e && (s.showImportBookDialog = !1), s.$message.success(e ? "导入书籍成功" : o ? "修改书籍成功" : "加入书架成功"), o ? s.$store.commit("updateShelfBook", t.data.data) : s.loadBookshelf(), t.data.data
            }), (function(t) {
                s.$message.error((e ? "导入书籍失败" : o ? "修改书籍失败" : "加入书架失败 ") + (t && t.toString()))
            })) : (this.$message.error("书籍信息错误"), Promise.reject(!1))
        },
        deleteBook: function(t) {
            this.$root.$children[0].deleteBook(t)
        },
        editBook: function(t, e, o) {
            var s = this;
            if (t && t.name && t.bookUrl && t.origin) {
                var r = __merge({}, t);
                delete r["variableMap$delegate"], eventBus.$emit("showEditor", e ? "保存书籍" : "编辑书籍", JSON.stringify(r, null, 4), function() {
                    var t = asyncToGenerator(regeneratorRuntime.mark((function t(r, i) {
                        var n, c;
                        return regeneratorRuntime.wrap((function(t) {
                            while (1) switch (t.prev = t.next) {
                                case 0:
                                    if (t.prev = 0, n = JSON.parse(r), n.name) {
                                        t.next = 5;
                                        break
                                    }
                                    return s.$message.error("书籍名称不能为空"), t.abrupt("return");
                                case 5:
                                    if (n.bookUrl) {
                                        t.next = 8;
                                        break
                                    }
                                    return s.$message.error("书籍链接不能为空"), t.abrupt("return");
                                case 8:
                                    if (n.origin) {
                                        t.next = 11;
                                        break
                                    }
                                    return s.$message.error("书籍来源不能为空"), t.abrupt("return");
                                case 11:
                                    if (!e) {
                                        t.next = 17;
                                        break
                                    }
                                    return t.next = 14, s.$confirm("加入书架之后才能编辑书籍信息, 是否加入书架?", "提示", {
                                        confirmButtonText: "确定",
                                        cancelButtonText: "取消",
                                        type: "warning"
                                    })["catch"]((function() {
                                        return !1
                                    }));
                                case 14:
                                    if (c = t.sent, c) {
                                        t.next = 17;
                                        break
                                    }
                                    return t.abrupt("return");
                                case 17:
                                    s.saveBook(n, !1, !0).then((function(t) {
                                        i(), o && o(t)
                                    })), t.next = 23;
                                    break;
                                case 20:
                                    t.prev = 20, t.t0 = t["catch"](0), s.$message.error("书籍信息必须是JSON格式");
                                case 23:
                                case "end":
                                    return t.stop()
                            }
                        }), t, null, [
                            [0, 20]
                        ])
                    })));
                    return function(e, o) {
                        return t.apply(this, arguments)
                    }
                }())
            } else this.$message.error("书籍信息错误")
        },
        backToShelf: function() {
            this.isSearchResult = !1, this.isExploreResult = !1, this.searchResult = [], this.loadingMore = !1
        },
        toogleNight: function() {
            this.isNight ? this.$store.commit("setNightTheme", !1) : this.$store.commit("setNightTheme", !0)
        },
        showSearchList: function(t) {
            this.isSearchResult = !0, this.isExploreResult = !0, this.loadingMore = !1, this.searchResult = t
        },
        loadMore: function() {
            this.lastScrollTop = this.$refs.bookList.scrollTop, this.isExploreResult ? (this.loadingMore = !0, this.$refs.popExplore.loadMore()) : this.searchBook(this.searchPage + 1)
        },
        uploadBookSource: function() {
            this.$refs.fileRef.dispatchEvent(new MouseEvent("click"))
        },
        onSourceFileChange: function(t, e) {
            var o = this,
                s = t.target.files && t.target.files[0],
                r = new FileReader,
                i = e ? "RSS源" : "书源";
            r.onload = function(t) {
                var s = t.target.result;
                try {
                    var r = JSON.parse(s);
                    Array.isArray(r) && r.length ? (o.importSourceList = r.map((function(t) {
                        return t.headerMap && (t.header || (t.header = "string" === typeof t.headerMap ? t.headerMap : JSON.stringify(t.headerMap)), delete t.headerMap), t
                    })), o.showImportSourceDialog = !0, o.isImportRssSource = !!e) : o.$message.error(i + "文件错误")
                } catch (a) {
                    o.$message.error(i + "文件错误")
                }
            }, r.onerror = function() {
                var t = new FormData;
                t.append("file", s), axios.post(o.api + "/readSourceFile", t, {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                }).then((function(t) {
                    if (t.data.isSuccess) {
                        var s = [];
                        t.data.data.forEach((function(t) {
                            try {
                                var e = JSON.parse(t);
                                Array.isArray(e) && (s = s.concat(e))
                            } catch (o) {}
                        })), s.length ? (o.importSourceList = s.map((function(t) {
                            return t.headerMap && (t.header || (t.header = "string" === typeof t.headerMap ? t.headerMap : JSON.stringify(t.headerMap)), delete t.headerMap), t
                        })), o.showImportSourceDialog = !0, o.isImportRssSource = !!e) : o.$message.error(i + "文件错误")
                    }
                }), (function(t) {
                    o.$message.error("读取" + i + "文件内容失败 " + (t && t.toString()))
                }))
            }, r.readAsText(s), this.isRssSource ? this.$refs.rssInputRef.value = null : this.$refs.fileRef.value = null
        },
        showRemoteBookSourceSubDialog: function() {
            return asyncToGenerator(regeneratorRuntime.mark((function t() {
                return regeneratorRuntime.wrap((function(t) {
                    while (1) switch (t.prev = t.next) {
                        case 0:
                            eventBus.$emit("showRemoteBookSourceSubDialog");
                        case 1:
                        case "end":
                            return t.stop()
                    }
                }), t)
            })))()
        },
        handleCheckAllChange: function(t) {
            var e = !1;
            this.checkedSourceIndex = t ? this.importSourceList.map((function(t, o) {
                var s = JSON.stringify(t);
                return -1 !== s.indexOf("@js:") || s.match(/[\\"]*webView[\\"]*:/) ? (e = !0, !1) : o
            })).filter((function(t) {
                return !1 !== t
            })) : [], t && e && this.$message.infwindow.__vendorRequire("部分使用了Javascript和Webview的书源未勾选"), this.isIndeterminate = !1
        },
        handleCheckedSourcesChange: function(t) {
            var e = t.length;
            this.checkAll = e === this.importSourceList.length, this.isIndeterminate = e > 0 && e < this.importSourceList.length
        },
        handleCheckWebviewChange: function(t) {
            var e = this,
                o = this.importSourceList.map((function(o, s) {
                    var r = JSON.stringify(o);
                    return r.match(/[\\"]*webView[\\"]*:/) ? !!t && s : !!e.checkedSourceIndex.includes(s) && s
                })).filter((function(t) {
                    return !1 !== t
                }));
            this.checkedSourceIndex = o
        },
        handleCheckJSChange: function(t) {
            var e = this,
                o = this.importSourceList.map((function(o, s) {
                    var r = JSON.stringify(o);
                    return -1 !== r.indexOf("@js:") ? !!t && s : !!e.checkedSourceIndex.includes(s) && s
                })).filter((function(t) {
                    return !1 !== t
                }));
            this.checkedSourceIndex = o
        },
        getSourceTag: function(t) {
            var e = JSON.stringify(t),
                o = [];
            return -1 !== e.indexOf("@js:") && o.push("@Javascript"), e.match(/[\\"]*webView[\\"]*:/) && o.push("@WebView"), "   " + o.join("  ")
        },
        saveSourceList: function() {
            var t = this;
            if (this.$store.state.connected)
                if (this.checkedSourceIndex.length) {
                    var e = this.checkedSourceIndex.map((function(e) {
                        return t.importSourceList[e]
                    }));
                    axios.post(this.api + (this.isImportRssSource ? "/saveRssSources" : "/saveBookSources"), e, {
                        timeout: 3e5
                    }).then((function(e) {
                        e.data.isSuccess && (t.$message.success(t.isImportRssSource ? "导入RSS源成功" : "导入书源成功"), t.isImportRssSource ? t.loadRssSources(!0) : t.loadBookSource(!0), t.showImportSourceDialog = !1, t.isImportRssSource = !1, t.checkedSourceIndex = [])
                    }), (function(e) {
                        t.$message.error((t.isImportRssSource ? "导入RSS源失败 " : "导入书源失败 ") + (e && e.toString()))
                    }))
                } else this.$message.error("请选择需要导入的源");
            else this.$message.error("后端未连接")
        },
        isBookSourceSelectable: function(t) {
            var e = [];
            return (this.$store.state.shelfBooks || []).forEach((function(o) {
                o.origin === t.bookSourceUrl && e.push(o.name)
            })), !e.length
        },
        showSourceBook: function(t) {
            var e = [];
            return (this.$store.state.shelfBooks || []).forEach((function(o) {
                o.origin === t.bookSourceUrl && e.push(o.name)
            })), e.join("\n")
        },
        getInvalidBookSources: function() {
            var t = this;
            this.$store.state.connected ? axios.post(this.api + "/getInvalidBookSources").then((function(e) {
                e.data.isSuccess && e.data.data.forEach((function(e) {
                    t.$store.commit("addFailureBookSource", {
                        bookSourceUrl: e.sourceUrl,
                        errorMsg: e.error
                    })
                }))
            }), (function() {})) : this.$message.error("后端未连接")
        },
        checkBookSource: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                var o;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            if (!t.isCheckingBookSource) {
                                e.next = 8;
                                break
                            }
                            return e.next = 3, t.$confirm("正在检测失效书源, 是否终止?", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 3:
                            if (o = e.sent, o) {
                                e.next = 6;
                                break
                            }
                            return e.abrupt("return");
                        case 6:
                            return t.bookSourceChecker.cancel(), e.abrupt("return");
                        case 8:
                            if (t.checkBookSourceConfig.keyword) {
                                e.next = 11;
                                break
                            }
                            return t.$message.error("请输入搜索关键词"), e.abrupt("return");
                        case 11:
                            t.isCheckingBookSource = !0, t.$store.commit("setFailureIncludeTimeout", !0), t.bookSourceChecker = createConcurrentRunner(t.checkBookSourceConfig.concurrent, (function(e) {
                                t.checkBookSourceTip = e.requestCount + "/" + t.bookSourceList.length, e.isEnd() && (t.isCheckingBookSource = !1, t.$store.commit("setFailureIncludeTimeout", !1))
                            })), t.bookSourceList.forEach((function(e) {
                                t.bookSourceChecker((function() {
                                    return axios.post(t.api + "/searchBook", {
                                        key: t.checkBookSourceConfig.keyword,
                                        bookSourceUrl: e.bookSourceUrl
                                    }, {
                                        timeout: t.checkBookSourceConfig.timeout,
                                        silent: !0
                                    })
                                }))
                            }));
                        case 15:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        deleteBookSourceList: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                var o;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            if (t.manageSourceSelection.length) {
                                e.next = 3;
                                break
                            }
                            return t.$message.error("请选择需要删除的源"), e.abrupt("return");
                        case 3:
                            return e.next = 5, t.$confirm("确认要删除所选择的书源吗?", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 5:
                            if (o = e.sent, o) {
                                e.next = 8;
                                break
                            }
                            return e.abrupt("return");
                        case 8:
                            axios.post(t.api + "/deleteBookSources", t.manageSourceSelection).then((function(e) {
                                e.data.isSuccess && (t.$store.commit("removeFailureBookSource", t.manageSourceSelection), t.manageSourceSelection = [], t.$message.success("删除书源成功"), t.loadBookSource(!0))
                            }), (function(e) {
                                t.$message.error("删除书源失败 " + (e && e.toString()))
                            }));
                        case 9:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        hideMenu: function() {
            this.$store.getters.isNormalPage && this.collapseMenu && (this.showNavigation = !1)
        },
        toggleMenu: function() {
            this.collapseMenu && (this.showNavigation = !this.showNavigation)
        },
        showExplorePop: function() {
            var t = this;
            setTimeout((function() {
                t.popExploreVisible = !0
            }), 100)
        },
        showBookInfoDialog: function(t) {
            eventBus.$emit("showBookInfoDialog", t)
        },
        saveUserConfig: function() {
            this.$root.$children[0].saveUserConfig()
        },
        restoreUserConfig: function() {
            this.$root.$children[0].restoreUserConfig()
        },
        loadUserList: function() {
            var t = this;
            this.$store.state.connected ? axios.get(this.api + "/getUserList").then((function(e) {
                e.data.isSuccess && (t.userNS = t.$store.state.userInfo.username, t.userList = e.data.data.map((function(t) {
                    return __merge(__merge({}, t), {}, {
                        userNS: t.username
                    })
                })), t.$store.commit("setIsManagerMode", !0))
            }), (function(e) {
                t.$message.error("加载用户空间失败 " + (e && e.toString()))
            })) : this.$message.error("后端未连接")
        },
        formatTableField: function(t, e, o) {
            switch (e.property) {
                case "createdAt":
                case "lastLoginAt":
                case "lastModified":
                    return o ? new Date(o).format("yy-MM-dd hh:mm") : "";
                case "size":
                    return t.isDirectory ? "" : formatBytes(o);
                default:
                    return o
            }
        },
        exitSecureMode: function() {
            this.userNS = "default", this.userList = [], this.$store.commit("setIsManagerMode", !1), this.init(!0)
        },
        backupToWebdav: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                var o;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, t.$confirm("确认要用当前数据覆盖备份文件中的书源、书架、分组、RSS订阅数据、替换规则、书签、用户配置和Webdav书籍吗?", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 2:
                            if (o = e.sent, o) {
                                e.next = 5;
                                break
                            }
                            return e.abrupt("return");
                        case 5:
                            axios.post(t.api + "/backupToWebdav").then((function(e) {
                                e.data.isSuccess && t.$message.success("备份成功")
                            }), (function(e) {
                                t.$message.error("备份失败 " + (e && e.toString()))
                            }));
                        case 6:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        handleTouchStart: function(t) {
            this.lastTouch = !1, this.lastMoveX = !1, this.touchMoveTimes = 0, t.touches && t.touches[0] && t.touches[0].clientX > 20 && t.touches[0].clientX < window.innerWidth - 20 && t.touches[0].clientY > 20 && t.touches[0].clientY < window.innerHeight - 20 && (this.lastTouch = t.touches[0])
        },
        handleTouchMove: function(t) {
            if (t.touches && t.touches[0] && this.lastTouch && this.collapseMenu) {
                var e = t.touches[0].clientX - this.lastTouch.clientX,
                    o = t.touches[0].clientY - this.lastTouch.clientY;
                if (Math.abs(o) > Math.abs(e)) return this.navigationStyle = {}, void(this.lastMoveX = 0);
                t.preventDefault(), t.stopPropagation(), !this.showNavigation && e > 0 && e <= 270 ? (this.touchMoveTimes % 3 === 0 && (this.navigationStyle = {
                    marginLeft: e - 270 + "px"
                }), this.lastMoveX = e) : this.showNavigation && e < 0 && e >= -270 && (this.touchMoveTimes % 3 === 0 && (this.navigationStyle = {
                    marginLeft: e + "px"
                }), this.lastMoveX = e), this.touchMoveTimes++
            }
        },
        handleTouchEnd: function() {
            this.collapseMenu && (this.lastMoveX > 0 ? (this.showNavigation = !0, this.navigationStyle = {}) : this.lastMoveX < 0 && (this.showNavigation = !1, this.navigationStyle = {}))
        },
        showFailureBookSource: function() {
            this.getInvalidBookSources(), this.isShowFailureBookSource = !0, this.showBookSourceManageDialog = !0
        },
        debugBookSource: function() {
            window.open(window.location.origin + window.location.pathname.replace(/index.html$/, "") + "bookSourceDebug/#domain=" + this.api, "_blank")
        },
        setShowSourceGroup: function(t) {
            this.showSourceGroup === t ? this.showSourceGroup = "" : this.showSourceGroup = t
        },
        importLocalBook: function() {
            this.$refs.bookRef.dispatchEvent(new MouseEvent("click"))
        },
        onBookFileChange: function(t) {
            var e = this;
            if (t.target && t.target.files && t.target.files.length) {
                for (var o = new FormData, s = 0; s < t.target.files.length; s++) {
                    var r = t.target.files[s];
                    o.append("file" + s, r)
                }
                axios.post(this.api + "/importBookPreview", o, {
                    headers: {
                        "Content-Type": "multipart/form-data"
                    }
                }).then((function(t) {
                    t.data.isSuccess && t.data.data.length && (t.data.data.length > 1 ? e.importMultiBooks(t.data.data) : (e.importBookInfo = t.data.data[0].book, e.importBookGroup = [], e.importBookChapters = t.data.data[0].chapters, e.showImportBookDialog = !0))
                }), (function(t) {
                    e.$message.error("上传书籍 " + (t && t.toString()))
                })), this.$refs.bookRef.value = null
            }
        },
        importMultiBooks: function(t) {
            var e = this;
            return asyncToGenerator(regeneratorRuntime.mark((function o() {
                var s, r, n, c, l, u;
                return regeneratorRuntime.wrap((function(o) {
                    while (1) switch (o.prev = o.next) {
                        case 0:
                            if (t && t.length) {
                                o.next = 2;
                                break
                            }
                            return o.abrupt("return");
                        case 2:
                            if (1 != t.length) {
                                o.next = 8;
                                break
                            }
                            return e.importBookInfo = t[0].book, e.importBookGroup = [], e.importBookChapters = t[0].chapters, e.showImportBookDialog = !0, o.abrupt("return");
                        case 8:
                            return o.next = 10, e.$confirm("你选择导入多本书籍，请选择导入方式?", "提示", {
                                confirmButtonText: "批量导入",
                                cancelButtonText: "逐一确认导入",
                                type: "warning",
                                closeOnClickModal: !1,
                                closeOnPressEscape: !1,
                                distinguishCancelAndClose: !0
                            })["catch"]((function(t) {
                                return "close" === t && "close"
                            }));
                        case 10:
                            if (s = o.sent, "close" !== s) {
                                o.next = 13;
                                break
                            }
                            return o.abrupt("return");
                        case 13:
                            if (!s) {
                                o.next = 29;
                                break
                            }
                            return o.next = 16, e.customImportBookInfo();
                        case 16:
                            if (r = o.sent, !1 !== r) {
                                o.next = 19;
                                break
                            }
                            return o.abrupt("return");
                        case 19:
                            n = 0;
                        case 20:
                            if (!(n < t.length)) {
                                o.next = 27;
                                break
                            }
                            return c = t[n], o.next = 24, e.saveBook(__merge(__merge({}, c.book), r), !0)["catch"]((function() {}));
                        case 24:
                            n++, o.next = 20;
                            break;
                        case 27:
                            o.next = 39;
                            break;
                        case 29:
                            l = 0;
                        case 30:
                            if (!(l < t.length)) {
                                o.next = 38;
                                break
                            }
                            return u = t[l], e.importMultiBookTip = "（".concat(l + 1, "/").concat(t.length, "）"), o.next = 35, e.waitForImportBook(u);
                        case 35:
                            l++, o.next = 30;
                            break;
                        case 38:
                            e.importMultiBookTip = "";
                        case 39:
                        case "end":
                            return o.stop()
                    }
                }), o)
            })))()
        },
        waitForImportBook: function(t) {
            var e = this;
            return new Promise((function(o) {
                e.importBookInfo = t.book, e.importBookGroup = [], e.importBookChapters = t.chapters, e.showImportBookDialog = !0, e.$once("importEnd", o)
            }))
        },
        importBookDialogClosed: function() {
            var t = this,
                e = this.importBookInfo.bookUrl;
            this.importBookInfo = {}, this.importBookGroup = [], this.importBookChapters = [], this.importUsedTxtRule = "", this.$nextTick((function() {
                t.$emit("importEnd")
            })), -1 !== e.indexOf("assets") && axios.post(this.api + "/deleteFile", {
                url: e
            }, {
                silent: !0
            }).then((function() {}), (function() {}))
        },
        customImportBookInfo: function(t, e) {
            var o = this;
            return asyncToGenerator(regeneratorRuntime.mark((function s() {
                var r, n;
                return regeneratorRuntime.wrap((function(s) {
                    while (1) switch (s.prev = s.next) {
                        case 0:
                            return e = e || {}, e.groupId = [], r = [{
                                name: "groupId",
                                label: "分组",
                                type: "select",
                                placeholder: "请选择分组",
                                options: o.bookGroupSetList.map((function(t) {
                                    return {
                                        label: t.groupName,
                                        value: t.groupId
                                    }
                                }))
                            }], e.name && (r.unshift({
                                name: "name",
                                label: "书名",
                                type: "input"
                            }), r.unshift({
                                name: "author",
                                label: "作者",
                                type: "input"
                            })), s.next = 6, o.$msgbox(__merge({
                                title: "统一设置分组",
                                message: o.renderForm(e.name ? "customImportBookInfo" : "customImportBookGroup", e, r, (function(t) {
                                    e.name && (e.name = t.name, e.author = t.author), e.groupId = t.groupId
                                })),
                                showCancelButton: !0,
                                confirmButtonText: "确定",
                                cancelButtonText: "取消"
                            }, t || {}))["catch"]((function(t) {
                                return "close" === t && "close"
                            }));
                        case 6:
                            if (n = s.sent, "confirm" === n) {
                                s.next = 9;
                                break
                            }
                            return s.abrupt("return", !1);
                        case 9:
                            return s.abrupt("return", {
                                group: e.groupId.reduce((function(t, e) {
                                    return LongBits.fromNumber(t).or(e).toNumber()
                                }), 0)
                            });
                        case 10:
                        case "end":
                            return s.stop()
                    }
                }), s)
            })))()
        },
        showBookManage: function() {
            eventBus.$emit("showBookManageDialog")
        },
        showManageBookGroup: function() {
            this.loadBookGroup(!0), eventBus.$emit("showBookGroupDialog", !1)
        },
        showActiveLicenseDialog: function() {
            eventBus.$emit("showActiveLicenseDialog")
        },
        showFileManagerDialog: function(t, e) {
            eventBus.$emit("showFileManagerDialog", t, e)
        },
        downloadBackupFile: function() {
            var t = buildURL(this.api + "/user/downloadBackupFile", {
                accessToken: this.$store.state.token
            });
            window.open(t, "__blank")
        },
        showBookmarkDialog: function() {
            eventBus.$emit("showBookmarkDialog")
        },
        showReplaceRuleDialog: function() {
            eventBus.$emit("showReplaceRuleDialog")
        },
        showLicense: function() {
            eventBus.$emit("showLicenseDialog")
        },
        getShowShelfBooks: function(t) {
            return -1 === t ? this.shelfBooks : -2 === t ? this.shelfBooks.filter((function(t) {
                return "loc_book" === t.origin
            })) : -3 === t ? this.shelfBooks.filter((function(t) {
                return 1 === t.type
            })) : -4 === t ? this.shelfBooks.filter((function(t) {
                return 0 === t.group
            })) : -5 === t ? this.shelfBooks.filter((function(t) {
                return t.canUpdate && t.lastCheckError
            })) : this.shelfBooks.filter((function(e) {
                return 0 === t || LongBits.fromNumber(e.group).and(t).greaterThan(0)
            }))
        },
        loadRssSources: function(t) {
            return this.$root.$children[0].loadRssSources(t)
        },
        showRssDialog: function() {
            eventBus.$emit("showRssSourceListDialog")
        },
        showRssArticleListDialog: function(t) {
            eventBus.$emit("showRssArticleListDialog", t)
        },
        noop: function() {},
        exportBookSource: function() {
            var t = this;
            axios.get(this.api + "/getBookSources").then((function(e) {
                if (e.data.isSuccess) {
                    var o = document.createElement("a"),
                        s = new Blob([JSON.stringify(e.data.data || [], null, 4)]);
                    o.download = "reader书源-" + t.currentDateTime() + ".json", o.href = URL.createObjectURL(s), o.click()
                }
            }), (function(e) {
                t.$message.error("导出书源失败 " + (e && e.toString()))
            }))
        },
        deleteAllBookSource: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                var o;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, t.$confirm("确认要清空所有书源吗?", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 2:
                            if (o = e.sent, o) {
                                e.next = 5;
                                break
                            }
                            return e.abrupt("return");
                        case 5:
                            axios.post(t.api + "/deleteAllBookSources").then((function(e) {
                                e.data.isSuccess && (t.$message.success("清空书源成功"), t.loadBookSource(!0))
                            }), (function(e) {
                                t.$message.error("清空书源失败 " + (e && e.toString()))
                            }));
                        case 6:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        deleteBookSourceFile: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                var o;
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, t.$confirm("确认要恢复默认书源吗?", "提示", {
                                confirmButtonText: "确定",
                                cancelButtonText: "取消",
                                type: "warning"
                            })["catch"]((function() {
                                return !1
                            }));
                        case 2:
                            if (o = e.sent, o) {
                                e.next = 5;
                                break
                            }
                            return e.abrupt("return");
                        case 5:
                            axios.post(t.api + "/deleteBookSourcesFile").then((function(e) {
                                e.data.isSuccess && (t.$message.success("恢复默认书源成功"), t.loadBookSource(!0))
                            }), (function(e) {
                                t.$message.error("操作失败 " + (e && e.toString()))
                            }));
                        case 6:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        editBookSource: function(t) {
            var e = this,
                o = function(t) {
                    eventBus.$emit("showEditor", "编辑书源", JSON.stringify(t, null, 4), (function(t, o) {
                        try {
                            var s = JSON.parse(t);
                            if (!s.bookSourceName) return void e.$message.error("书源名称不能为空");
                            if (!s.bookSourceUrl) return void e.$message.error("书源链接不能为空");
                            axios.post(e.api + "/saveBookSource", s).then((function(t) {
                                t.data.isSuccess && (o(), e.$message.success("保存书源成功"), e.loadBookSource(!0))
                            }), (function(t) {
                                e.$message.error("保存书源失败 " + (t && t.toString()))
                            }))
                        } catch (r) {
                            e.$message.error("书源必须是JSON格式")
                        }
                    }))
                };
            t ? axios.post(this.api + "/getBookSource", {
                bookSourceUrl: t.bookSourceUrl
            }).then((function(t) {
                t.data.isSuccess && o(t.data.data)
            }), (function(t) {
                e.$message.error("加载书源信息失败 " + (t && t.toString()))
            })) : o({
                bookSourceComment: "",
                bookSourceGroup: "",
                bookSourceName: "新增书源",
                bookSourceType: 0,
                bookSourceUrl: "",
                bookUrlPattern: "",
                enabled: !0,
                header: "",
                enabledExplore: !0,
                exploreUrl: "",
                ruleBookInfo: {},
                ruleContent: {
                    content: ""
                },
                ruleExplore: {},
                ruleSearch: {
                    author: "",
                    bookList: "",
                    bookUrl: "",
                    coverUrl: "",
                    intro: "",
                    kind: "",
                    lastChapter: "",
                    name: ""
                },
                ruleToc: {
                    chapterList: "",
                    chapterName: "",
                    chapterUrl: ""
                },
                searchUrl: ""
            })
        },
        editBookSourceHeader: function(t) {
            var e = this,
                o = function(t) {
                    eventBus.$emit("showEditor", "编辑书源请求头header", t.header || JSON.stringify({
                        Cookie: ""
                    }, null, 4), (function(o, s) {
                        try {
                            o.startsWith("@js:") || o.startsWith("<js>") || JSON.parse(o), t.header = o, axios.post(e.api + "/saveBookSource", t).then((function(t) {
                                t.data.isSuccess && (s(), e.$message.success("保存书源成功"), e.loadBookSource(!0))
                            }), (function(t) {
                                e.$message.error("保存书源失败 " + (t && t.toString()))
                            }))
                        } catch (r) {
                            e.$message.error("书源请求头必须是JSON格式/脚本格式")
                        }
                    }))
                };
            axios.post(this.api + "/getBookSource", {
                bookSourceUrl: t.bookSourceUrl
            }).then((function(t) {
                t.data.isSuccess && o(t.data.data)
            }), (function(t) {
                e.$message.error("加载书源信息失败 " + (t && t.toString()))
            }))
        },
        updateForce: function() {
            "serviceWorker" in navigator && navigator.serviceWorker.getRegistrations().then(function() {
                var t = asyncToGenerator(regeneratorRuntime.mark((function t(e) {
                    var o;
                    return regeneratorRuntime.wrap((function(t) {
                        while (1) switch (t.prev = t.next) {
                            case 0:
                                console.log("registrations", e), o = 0;
                            case 2:
                                if (!(o < e.length)) {
                                    t.next = 8;
                                    break
                                }
                                return t.next = 5, e[o].update();
                            case 5:
                                o++, t.next = 2;
                                break;
                            case 8:
                                console.log("Try to clear home cache"), navigator.serviceWorker.controller && navigator.serviceWorker.controller.postMessage({
                                    type: "CLEAR_HOME_CACHE"
                                }), console.log("Try to skip waiting"), navigator.serviceWorker.controller && navigator.serviceWorker.controller.postMessage({
                                    type: "SKIP_WAITING"
                                }), setTimeout((function() {
                                    console.log("Try to reload force"), window.location.reload(!0)
                                }), 50);
                            case 13:
                            case "end":
                                return t.stop()
                        }
                    }), t)
                })));
                return function(e) {
                    return t.apply(this, arguments)
                }
            }())
        },
        scanCacheStorage: function() {
            var t = this;
            return asyncToGenerator(regeneratorRuntime.mark((function e() {
                return regeneratorRuntime.wrap((function(e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            return e.next = 2, t.analyseLocalStorage();
                        case 2:
                            return e.t0 = e.sent.totalBytes, e.next = 5, t.analyseLocalStorage("bookSourceList");
                        case 5:
                            return e.t1 = e.sent.totalBytes, e.next = 8, t.analyseLocalStorage("rssSources");
                        case 8:
                            return e.t2 = e.sent.totalBytes, e.next = 11, t.analyseLocalStorage("chapterList");
                        case 11:
                            return e.t3 = e.sent.totalBytes, e.next = 14, t.analyseLocalStorage("chapterContent");
                        case 14:
                            return e.t4 = e.sent.totalBytes, e.next = 17, t.analyseLocalStorage("ttsData");
                        case 17:
                            e.t5 = e.sent.totalBytes, t.localCacheStats = {
                                total: e.t0,
                                bookSourceList: e.t1,
                                rssSources: e.t2,
                                chapterList: e.t3,
                                chapterContent: e.t4,
                                ttsData: e.t5
                            };
                        case 19:
                        case "end":
                            return e.stop()
                    }
                }), e)
            })))()
        },
        analyseLocalStorage: function(t) {
            var e = 0,
                o = 0;
            return window.$cacheStorage.iterate((function(s, r) {
                (!t || r.indexOf(t) >= 0) && (e += JSON.stringify(s).getBytesLength(), r.startsWith("localCache@") && (o += JSON.stringify(s).getBytesLength()))
            })).then((function() {
                return {
                    totalBytes: formatBytes(e),
                    cacheBytes: formatBytes(o)
                }
            }))["catch"]((function() {}))
        },
        clearCache: function(t) {
            var e = this,
                o = 0;
            window.$cacheStorage.iterate((function(e, s) {
                (!t || s.indexOf(t) >= 0) && s.startsWith("localCache@") && (o += JSON.stringify(e).getBytesLength(), window.$cacheStorage.removeItem(s))
            })).then((function() {
                return e.scanCacheStorage(), {
                    cacheBytes: formatBytes(o)
                }
            }))["catch"]((function() {}))
        },
        scrollHandler: function() {
            this.lastScrollTop = this.$refs.bookList.scrollTop
        },
        getBookCoverUrl: function(t) {
            return t.customCoverUrl || t.coverUrl
        },
        logout: function() {
            var t = this;
            axios.post(this.api + "/logout").then((function(e) {
                e.data.isSuccess && (t.$store.commit("setToken", ""), window.location.reload(!0))
            }), (function(e) {
                t.$message.error("注销失败 " + (e && e.toString()))
            }))
        },
        getChapterListByRule: function() {
            var t = this;
            return axios.post("/getChapterListByRule", this.importBookInfo).then((function(e) {
                e.data.isSuccess && e.data.data.book && (t.importBookInfo = e.data.data.book, t.importBookChapters = e.data.data.chapters)
            }), (function(e) {
                t.$message.error("注销失败 " + (e && e.toString()))
            }))
        },
        showUserManageDialog: function() {
            eventBus.$emit("showUserManageDialog")
        },
        showMPCode: function() {
            eventBus.$emit("showMPCodeDialog")
        },
        joinTGChannel: function() {
            window.open("https://t.me/facker_channel", "_target")
        },
        ensureLoadBookCover: function() {
            var t = this;
            try {
                this.$refs.bookList.dispatchEvent(new MouseEvent("scroll")), this.$refs.bookListComp.$refs.bookCoverList.forEach((function(e) {
                    !e.show && isInContainer(e.$el, t.$refs.bookList) && (e.show = !0)
                }))
            } catch (e) {
                console.error(e)
            }
        }
    },
    computed: __merge(__merge({}, mapState(["collapseMenu", "dialogWidth", "dialogSmallWidth", "dialogTop", "dialogContentHeight", "popupWidth"])), {}, {
        config: function() {
            return this.$store.getters.config
        },
        isNight: function() {
            return this.$store.getters.isNight
        },
        themeColor: function() {
            return this.$store.getters.isNight ? {
                background: "#f7f7f7"
            } : {
                background: "#222"
            }
        },
        bookList: function() {
            var t = this;
            return this.isSearchResult ? this.searchResult : this.showShelfBooks.filter((function(e) {
                return !t.keyword || (e.name || "").indexOf(t.keyword) >= 0 || (e.author || "").indexOf(t.keyword) >= 0 || (e.kind || "").indexOf(t.keyword) >= 0
            }))
        },
        bookCoverList: function() {
            var t = this;
            return this.bookList.filter((function(e) {
                return t.getBookCoverUrl(e)
            })).map((function(e) {
                return t.getCover(t.getBookCoverUrl(e), !0)
            }))
        },
        shelfBooks: function() {
            var t = this,
                e = [].concat(this.$store.getters.shelfBooks);
            return e.sort((function(e, o) {
                return (e[t.shelfConfig.bookOrder] > o[t.shelfConfig.bookOrder] ? 1 : e[t.shelfConfig.bookOrder] < o[t.shelfConfig.bookOrder] ? -1 : 0) * ("name" === t.shelfConfig.bookOrder ? 1 : -1)
            }))
        },
        showShelfBooks: function() {
            return this.getShowShelfBooks(this.showBookGroup)
        },
        searchResultMap: function() {
            return this.searchResult.reduce((function(t, e) {
                return t[e.bookUrl] = e, t
            }), {})
        },
        connectStatus: function() {
            return this.$store.state.connected ? "后端已连接" : this.connecting ? "正在连接后端服务器……" : "点击设置后端接口前缀"
        },
        connectType: function() {
            return this.$store.state.connected ? "success" : "danger"
        },
        readingRecent: function() {
            return this.$store.getters.readingBook && this.$store.getters.readingBook.name ? this.$store.getters.readingBook : {
                name: "尚无阅读记录",
                bookUrl: "",
                index: 0
            }
        },
        loginAuth: function() {
            return this.$store.state.loginAuth
        },
        bookSourceList: function() {
            return this.$store.state.bookSourceList
        },
        userNS: {
            get: function() {
                return this.$store.state.userNS
            },
            set: function(t) {
                this.$store.commit("setUserNS", t), t && this.$store.commit("setIsManagerMode", !0)
            }
        },
        userList: {
            get: function() {
                return this.$store.state.userList
            },
            set: function(t) {
                this.$store.commit("setUserList", t)
            }
        },
        bookSourceShowList: function() {
            return this.isShowFailureBookSource ? this.$store.state.failureBookSource : this.bookSourceList
        },
        bookSourceGroupList: function() {
            return this.$store.getters.bookSourceGroupList
        },
        bookSourceShowGroup: function() {
            if (this.isShowFailureBookSource) return ["全部"].concat(FAILURE_TYPE_LIST).concat(["timeout"]);
            var t = new Set;
            return t.add("全部"), this.bookSourceShowList.forEach((function(e) {
                e.bookSourceGroup && e.bookSourceGroup.split(",").forEach((function(e) {
                    e && t.add(e)
                }))
            })), t.add("未分组"), Array.from(t)
        },
        bookSourceShowLength: function() {
            return this.bookSourceShowResult.length
        },
        bookSourceShowSearchResult: function() {
            var t = this;
            return this.bookSourceKeyword ? this.bookSourceShowList.filter((function(e) {
                return -1 !== (e.bookSourceName || "").indexOf(t.bookSourceKeyword) || -1 !== (e.bookSourceUrl || "").indexOf(t.bookSourceKeyword)
            })) : this.bookSourceShowList
        },
        bookSourceShowResult: function() {
            var t = this;
            return this.showSourceGroup && "全部" !== this.showSourceGroup ? this.isShowFailureBookSource ? this.bookSourceShowSearchResult.filter((function(e) {
                return !t.showSourceGroup || e.errorMsg.indexOf(t.showSourceGroup) >= 0
            })) : this.bookSourceShowSearchResult.filter((function(e) {
                return "未分组" === t.showSourceGroup ? !e.bookSourceGroup : (e.bookSourceGroup + ",").indexOf(t.showSourceGroup + ",") >= 0
            })) : this.bookSourceShowSearchResult
        },
        bookSourceShowResultPageList: function() {
            var t = (this.bookSourcePagination.page - 1) * this.bookSourcePagination.size;
            return t > this.bookSourceShowResult.length ? [] : this.bookSourceShowResult.slice(t, Math.min(t + this.bookSourcePagination.size, this.bookSourceShowResult.length))
        },
        shelfConfig: function() {
            return this.$store.state.shelfConfig
        },
        showBookGroup: {
            get: function() {
                return this.bookGroupDisplayList.length ? this.$store.state.shelfConfig.showBookGroup : -1
            },
            set: function(t) {
                this.$store.commit("setShelfConfig", __merge(__merge({}, this.$store.state.shelfConfig), {}, {
                    showBookGroup: t
                }))
            }
        },
        showBookGroupString: {
            get: function() {
                return "" + this.showBookGroup
            },
            set: function(t) {
                this.showBookGroup = +t
            }
        },
        bookGroupSetList: function() {
            return this.$store.state.bookGroupList.filter((function(t) {
                return t.groupId > 0
            }))
        },
        bookGroupDisplayList: function() {
            var t = this;
            return this.$store.state.bookGroupList.filter((function(e) {
                return t.getShowShelfBooks(e.groupId).length && e.show
            })).sort((function(t, e) {
                return t.order - e.order
            }))
        },
        searchConfig: {
            get: function() {
                return this.$store.state.searchConfig
            },
            set: function(t) {
                this.$store.commit("setSearchConfig", t)
            }
        },
        isShowTocRule: function() {
            try {
                return this.importBookInfo && this.importBookInfo.originName && (this.importBookInfo.originName.toLowerCase().endsWith(".txt") || this.importBookInfo.originName.toLowerCase().endsWith(".epub") || this.importBookInfo.originName.toLowerCase().endsWith(".pdf"))
            } catch (t) {}
            return !1
        },
        tocRuleList: function() {
            return this.importBookInfo && this.importBookInfo.originName ? this.importBookInfo.originName.toLowerCase().endsWith(".txt") ? this.$store.state.txtTocRules : this.importBookInfo.originName.toLowerCase().endsWith(".epub") ? [{
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
            }] : [{
                name: "使用书签作为章节",
                rule: "outline"
            }, {
                name: "一页一章",
                rule: "page"
            }] : []
        },
        isShowActiveLicenseBtn: function() {
            return this.$store.state.isManagerMode && window.location.host.indexOf("htmake") >= 0
        }
    })
}
</script>

<!-- 页面自身样式：产物 style.css（提取时去掉了 [data-v-*] 属性，故此处 scoped 重新生成） -->
<style scoped>
.index-wrapper { height:100%;width:100%;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row }
.index-wrapper .navigation-wrapper { width:260px;min-width:260px;height:100%;box-sizing:border-box;background-color:#f7f7f7;position:relative;padding-top:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important }
.index-wrapper .navigation-wrapper .navigation-inner-wrapper { padding:48px 36px 66px 36px;height:100%;overflow-y:auto;box-sizing:border-box }
.index-wrapper .navigation-wrapper .navigation-title { font-size:24px;font-weight:600;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif }
.index-wrapper .navigation-wrapper .navigation-title .version-text { float:right;font-size:14px;line-height:33px;font-weight:400;color:#b1b1b1;display:inline-block;cursor:pointer }
.index-wrapper .navigation-wrapper .navigation-sub-title { font-size:16px;font-weight:500;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;margin-top:16px;color:#b1b1b1 }
.index-wrapper .navigation-wrapper .search-wrapper .search-input { border-radius:50%;margin-top:24px }
.index-wrapper .navigation-wrapper .search-wrapper .search-input .el-input__inner { border-radius:50px;border-color:#e3e3e3 }
.index-wrapper .navigation-wrapper .recent-wrapper { margin-top:36px }
.index-wrapper .navigation-wrapper .recent-wrapper .recent-title { font-size:14px;color:#b1b1b1;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif }
.index-wrapper .navigation-wrapper .recent-wrapper .recent-title .right-text { float:right;display:inline-block;height:20px;line-height:20px;cursor:pointer;-webkit-user-select:none;-ms-user-select:none;user-select:none }
.index-wrapper .navigation-wrapper .recent-wrapper .reading-recent { margin:18px 0 }
.index-wrapper .navigation-wrapper .recent-wrapper .reading-recent .recent-book { cursor:pointer;max-width:100%;overflow:hidden;text-overflow:ellipsis }
.index-wrapper .navigation-wrapper .setting-wrapper { margin-top:36px }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-title { font-size:14px;color:#b1b1b1;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-title .right-text { float:right;display:inline-block;height:20px;line-height:20px;cursor:pointer;-webkit-user-select:none;-ms-user-select:none;user-select:none }
.index-wrapper .navigation-wrapper .setting-wrapper .no-point { pointer-events:none }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-connect { cursor:pointer;max-width:100%;overflow:hidden;text-overflow:ellipsis }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-item { padding-top:16px }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-btn { margin-right:15px;margin-bottom:15px;cursor:pointer }
.index-wrapper .navigation-wrapper .setting-wrapper .setting-select { width:100% }
.index-wrapper .navigation-wrapper .search-setting { margin-top:28px }
.index-wrapper .navigation-wrapper .bottom-icons { position:absolute;bottom:30px;width:188px;left:36px;-ms-flex-align:center;align-items:center;display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-pack:justify;justify-content:space-between;pointer-events:none }
.index-wrapper .navigation-wrapper .bottom-icons .bottom-icon { height:36px;pointer-events:all }
.index-wrapper .navigation-wrapper .bottom-icons .bottom-icon img { width:36px;height:36px }
.index-wrapper .navigation-wrapper .bottom-icons .theme-item { line-height:32px;width:36px;height:36px;border-radius:100%;display:inline-block;cursor:pointer;text-align:center;vertical-align:middle;pointer-events:all }
.index-wrapper .navigation-wrapper .bottom-icons .theme-item .el-icon-moon { color:#f7f7f7;line-height:34px }
.index-wrapper .navigation-wrapper .bottom-icons .theme-item .el-icon-sunny { color:#121212;line-height:34px }
.index-wrapper .navigation-wrapper .setting-wrapper:last-child { padding-bottom:20px }
.index-wrapper .shelf-wrapper { padding:48px 48px;height:100%;max-height:100%;width:100%;background-color:#fff;display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;box-sizing:border-box }
.index-wrapper .shelf-wrapper .shelf-title { font-size:20px;font-weight:600;font-family:-apple-system,Noto Sans,Helvetica Neue,Helvetica,Nimbus Sans L,Arial,Liberation Sans,PingFang SC,Hiragino Sans GB,Noto Sans CJK SC,Source Han Sans SC,Source Han Sans CN,Microsoft YaHei,Wenquanyi Micro Hei,WenQuanYi Zen Hei,ST Heiti,SimHei,WenQuanYi Zen Hei Sharp,sans-serif;margin-bottom:5px;min-width:320px;box-sizing:border-box }
.index-wrapper .shelf-wrapper .shelf-title .el-icon-menu { cursor:pointer }
.index-wrapper .shelf-wrapper .shelf-title .title-btn { font-size:14px;line-height:28px;float:right;cursor:pointer;-webkit-user-select:none;-ms-user-select:none;user-select:none;margin-left:10px }
.index-wrapper .shelf-wrapper .shelf-title .title-btn .el-icon-loading { font-size:16px }
.index-wrapper .shelf-wrapper .el-icon-loading { font-size:36px;color:#b5b5b5 }
.index-wrapper .shelf-wrapper .el-loading-text { font-weight:500;color:#b5b5b5 }
.index-wrapper .shelf-wrapper .book-group-wrapper { padding:5px 0;margin-bottom:10px }
.index-wrapper .shelf-wrapper .book-group-wrapper .book-group-tabs { width:100% }
.index-wrapper .shelf-wrapper .book-group-wrapper .book-group-btn { margin-right:10px;cursor:pointer }
.index-wrapper .shelf-wrapper .book-group-wrapper .book-group-btn.selected { color:#fff;background:#409eff;border-color:#409eff }
.index-wrapper .shelf-wrapper .books-wrapper { -ms-flex:1;flex:1;overflow-x:hidden;overflow-y:scroll }
.index-wrapper .shelf-wrapper .books-wrapper::-webkit-scrollbar { width:0!important }
.unread-num-badge .el-badge__content { border:none }
.night .navigation-wrapper { background-color:#121212;border-right:1px solid #555 }
.night .navigation-title, .night .shelf-title { color:#bbb }
.night .shelf-wrapper { background-color:#222 }
.night .el-input__inner { background-color:#444;border:1px solid #444!important;color:#aaa }
.night .book .info .name { color:#bbb!important }
.night .book .info .book-operation, .night .book .info .sub { color:#6b6b6b!important }
.night .book .info .dur-chapter, .night .book .info .intro, .night .book .info .last-chapter { color:#969ba3!important }
.night .check-tip { color:#bbb }
.source-container { padding:0 10px }
.source-container.table-container { padding:0 }
.source-container .check-form { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;overflow-x:auto;-ms-flex-align:center;align-items:center }
.source-container .check-form .check-form-label { min-width:60px }
.source-container .check-form .el-input { width:auto;min-width:100px;margin-right:10px }
.source-container .check-form .el-input-number { min-width:130px;margin-right:10px }
.source-container .check-form .book-cover .cover, .source-container .check-form .book-cover { width:84px;height:112px }
.source-container .check-form .book-info { display:-ms-flexbox;display:flex;-ms-flex-direction:column;flex-direction:column;margin-left:30px;-ms-flex-pack:justify;justify-content:space-between;min-height:100px }
.source-container .check-form .book-info .toc-refresh-btn { margin-left:5px }
.source-container .check-form .book-info span { display:inline-block;min-width:56px;text-align-last:justify }
.source-container .check-form .book-info .el-input { width:auto;min-width:100px;margin-right:10px }
.source-container .check-form .book-info .el-input-number { min-width:130px;margin-right:10px }
.source-container .chapter-title { font-size:15px;padding:5px 0;font-weight:600;margin-top:10px }
.source-container .chapter-list { overflow-y:auto;box-sizing:border-box;padding:0 5px }
.source-container .chapter-list p { margin-top:.4em;margin-bottom:.4em }
.source-container .source-group-wrapper { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;overflow-x:auto;padding:5px 0 }
.source-container .source-group-wrapper .booksource-group-tabs { width:100% }
.source-container .source-group-wrapper .source-group-btn { margin-right:10px;cursor:pointer }
.source-container .source-group-wrapper .source-group-btn.selected { color:#fff;background:#409eff;border-color:#409eff }
.source-container .el-pagination { margin-top:8px;float:right;max-width:100%;overflow-x:auto;box-sizing:border-box }
.source-container .source-checkbox { display:block;padding:8px 0;width:100% }
.source-container pre { margin:0 }
.source-container .source-pagination:after { display:table;content:"";clear:both }
.source-list-container { max-height:calc(var(--vh, 1vh)*70 - 180px);overflow-y:auto;overflow-x:auto }
.night .source-container .source-group-wrapper .source-group-btn.selected { color:#fff;background:#185798;border-color:#185798 }
.night .book-group-wrapper .book-group-btn.selected { color:#fff;background:#185798!important;border-color:#185798!important }
.navigation-inner-wrapper::-webkit-scrollbar, .source-container::-webkit-scrollbar { width:0!important }
.el-dialog__wrapper::-webkit-scrollbar, .el-table__body-wrapper::-webkit-scrollbar { width:0!important }
.dialog-footer { display:-ms-flexbox;display:flex;-ms-flex-direction:row;flex-direction:row;-ms-flex-wrap:wrap;flex-wrap:wrap;-ms-flex-pack:justify;justify-content:space-between }
.dialog-footer .float-left { margin-right:5px;margin-bottom:5px }
.index-wrapper { overflow-x:hidden }
.index-wrapper .navigation-wrapper .navigation-inner-wrapper { padding:20px 36px 66px 36px }
.index-wrapper .shelf-wrapper { padding:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important }
.index-wrapper .shelf-wrapper .shelf-title { padding:20px 24px 0 24px }
.index-wrapper .shelf-wrapper .book-group-wrapper { margin-left:24px;margin-right:24px }
.source-list-container { max-height:calc(var(--vh, 1vh)*100 - 160px) }
.source-container.table-container { margin:-15px -5px }

@media screen and (max-width:750px) {
  .index-wrapper{overflow-x:hidden}
  .index-wrapper .navigation-wrapper .navigation-inner-wrapper{padding:20px 36px 66px 36px}
  .index-wrapper .shelf-wrapper{padding:0;padding-top:constant(safe-area-inset-top)!important;padding-top:env(safe-area-inset-top)!important}
  .index-wrapper .shelf-wrapper .shelf-title{padding:20px 24px 0 24px}
  .index-wrapper .shelf-wrapper .book-group-wrapper{margin-left:24px;margin-right:24px}
  .source-list-container{max-height:calc(var(--vh, 1vh)*100 - 160px)}
}

@media screen and (max-width:480px) {
  .source-container.table-container{margin:-15px -5px}
}
</style>
