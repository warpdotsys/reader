<!-- 恢复自 reader-pro-3.2.14 前端产物 chunk=index scopeId=b817d39e；无 sourcemap，模板为 render 推断还原 -->
<template>
<div class="index-wrapper" :class="{night: isNight, day: !isNight}"><template v-if="$store.getters.isNormalPage"><div class="navigation-wrapper" :class='[navigationClass, isWebApp && !isNight ? "status-bar-light-bg" : ""]' :style="navigationStyle" @touchstart="handleTouchStart" @touchmove="handleTouchMove" @touchend="handleTouchEnd"><div class="navigation-inner-wrapper"><div class="navigation-title"> 阅读 <span class="version-text" @click="updateForce">{{ $store.state.version }}</span></div><div class="navigation-sub-title"> 清风不识字，何故乱翻书 </div><div class="search-wrapper" v-show="$store.getters.hasLogin"><el-input class="search-input" size="mini" placeholder="搜索书籍" v-model="search" @keyup.native='function(e) {
                                return !e.type.indexOf("key") && _k(e.keyCode, "enter", 13, e.key, "Enter") ? null : searchBook(1)
                            }'><i class="el-input__icon el-icon-search" slot="prefix" /></el-input></div><div class="setting-wrapper search-setting" v-show="$store.getters.hasLogin"><div class="setting-title"> 搜索设置 </div><div class="setting-item"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索方式" v-model="searchConfig.searchType"><template v-for="(t, o) in searchTypeList"><el-option :key='"search-type-" + o' :label="t.name" :value="t.value" /></template></el-select></div><div class="setting-item" v-show='"single" === searchConfig.searchType'><template v-if='"yes" === shelfConfig.virtualOptimize'><VirtualSelect :data-list="bookSourceList" v-model="searchConfig.bookSourceUrl" /></template><template v-else><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索书源" v-model="searchConfig.bookSourceUrl"><template v-for="(t, o) in bookSourceList"><el-option :key='"source-" + o' :label="t.bookSourceName" :value="t.bookSourceUrl" /></template></el-select></template></div><div class="setting-item" v-show='"single" === searchConfig.searchType'><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="searchBookManual"> 精确搜书 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="saveBookManual"> 手动加书 </el-tag></div><div class="setting-item" v-show='"single" !== searchConfig.searchType'><el-select class="setting-select" size="mini" filterable="" placeholder="请选择搜索书源分组" v-model="searchConfig.bookSourceGroup"><template v-for="(t, o) in bookSourceGroupList"><el-option :key='"source-group-" + o' :label='t.name + " (" + t.count + ")"' :value="t.value" /></template></el-select></div><div class="setting-item" v-show='"single" !== searchConfig.searchType'><el-select class="setting-select" size="mini" filterable="" placeholder="请选择并发线程" v-model="searchConfig.concurrentCount"><template v-for="(t, o) in concurrentList"><el-option :key='"source-" + o' :label='t + "并发线程"' :value="t" /></template></el-select></div></div><div class="recent-wrapper" v-show="$store.getters.hasLogin"><div class="recent-title"> 最近阅读 <span class="right-text" @click='(e) => {
                                return $store.commit("clearReadingBook")
                            }'>清除</span></div><div class="reading-recent"><el-tag class="recent-book" :class="{'no-point': &quot;&quot; == readingRecent.bookUrl}" type="warning" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                return toDetail(readingRecent)
                            }">{{ " " + _s(readingRecent.name) + " " }}</el-tag></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 后端设定 <template v-if="isTauri"><span class="right-text" @click='(e) => {
                                return $router.push({
                                    path: "/setting"
                                })
                            }'>设置</span></template></div><div class="setting-item"><el-tag class="setting-connect" :class="{'no-point': connecting}" :type="connectType" :effect='isNight ? "dark" : "light"' @click="setIP">{{ " " + _s(connectStatus) + " " }}</el-tag></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 书源设置 </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showBookSourceManageDialog = !0
                            }"> 书源管理 </el-tag><el-popover placement="right" :width="popupWidth" trigger="click" :visible-arrow="false" popper-class="popper-component explore-popover" v-model="popExploreVisible"><Explore ref='"popExplore"' class="popup" :visible="popExploreVisible" :bookSourceList="bookSourceList" @showSearchList="showSearchList" @close="(e) => {
                                popExploreVisible = !1
                            }" /><el-tag ref='"exploreBtn"' class="setting-btn" slot="reference" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showNavigation = !1
                            }"> 探索书源 </el-tag></el-popover><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="uploadBookSource"> 导入书源 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showRemoteBookSourceSubDialog"> 书源订阅 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                return showFailureBookSource()
                            }"> 失效书源 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                return debugBookSource()
                            }"> 调试书源 </el-tag><input ref='"fileRef"' style="display: none" type="file" @change="onSourceFileChange" /></div></div><div class="setting-wrapper" v-show="$store.getters.hasLogin"><div class="setting-title"> 书架设置 </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                showShelfSettingsDialog = !showShelfSettingsDialog
                            }"> 书架设置 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="showBookManage"> 书籍管理 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="showManageBookGroup"> 分组管理 </el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click="importLocalBook"> 导入书籍 </el-tag><input ref='"bookRef"' style="display: none" type="file" multiple="multiple" @change="onBookFileChange" /><template v-if="!$store.state.isSecureMode || $store.state.userInfo.enableLocalStore"><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__LOCAL_STORE__", "书仓文件管理")
                            }'> 浏览书仓 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showBookmarkDialog"> 书签管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showReplaceRuleDialog"> 替换规则 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                return init(!0)
                            }"> 刷新缓存 </el-tag></div></div><div class="setting-wrapper"><div class="setting-title"> 用户空间 <template v-if="$store.getters.hasLogin"><span class="right-text" @click="(e) => {
                                return logout()
                            }">注销</span></template><template v-else><template v-if="$store.state.isSecureMode"><span class="right-text" @click='(e) => {
                                return $store.commit("setShowLogin", !0)
                            }'>登录</span></template></template></div><template v-if="$store.state.isManagerMode"><div class="setting-item"><el-select class="setting-select" size="mini" filterable="" placeholder="请选择用户空间" v-model="userNS"><template v-for="(t, o) in userList"><el-option :key='"source-" + o' :label="t.username" :value="t.userNS" /></template></el-select></div></template><div class="setting-item" v-show="$store.getters.hasLogin"><template v-if="isShowActiveLicenseBtn"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showActiveLicenseDialog"> 授权管理 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="showLicense"> 查看授权信息 </el-tag><template v-if="localStorageAvaliable"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="saveUserConfig"> 备份我的配置 </el-tag></template><template v-if="localStorageAvaliable"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="restoreUserConfig"> 同步我的配置 </el-tag></template><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__HOME__", "用户数据管理")
                            }'> 我的数据管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="downloadBackupFile"> 下载数据备份 </el-tag><template v-if="$store.state.showManagerMode && !$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="loadUserList"> 进入管理模式 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="(e) => {
                                return showUserManageDialog()
                            }"> 用户权限管理 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__STORAGE__", "数据目录管理")
                            }'> 数据目录管理 </el-tag></template><template v-if="$store.state.isManagerMode"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="exitSecureMode"> 退出管理模式 </el-tag></template></div></div><template v-if="!$store.state.isSecureMode || $store.state.userInfo.enableWebdav"><div class="setting-wrapper"><div class="setting-title" v-show="$store.getters.hasLogin"> WebDAV </div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click='(e) => {
                                return showFileManagerDialog("__WEBDAV__", "WebDAV文件管理")
                            }'> 文件管理 </el-tag><el-tag class="setting-btn" type="info" :effect='isNight ? "dark" : "light"' @click="backupToWebdav"> 保存备份 </el-tag></div></div></template><div class="setting-wrapper"><div class="setting-title"> 本地缓存 <span class="right-text">{{ localCacheStats.total }}</span></div><div class="setting-item"><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return clearCache("bookSourceList")
                            }'> 清空书源缓存 <span>{{ localCacheStats.bookSourceList }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return clearCache("rssSources")
                            }'> 清空RSS源缓存 <span>{{ localCacheStats.rssSources }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return clearCache("chapterList")
                            }'> 清空章节列表缓存 <span>{{ localCacheStats.chapterList }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return clearCache("chapterContent")
                            }'> 清空章节内容缓存 <span>{{ localCacheStats.chapterContent }}</span></el-tag><el-tag class="setting-btn" type="info" :effect='$store.getters.isNight ? "dark" : "light"' @click='(e) => {
                                return clearCache("ttsData")
                            }'> 清空TTS缓存 <span>{{ localCacheStats.ttsData }}</span></el-tag></div></div></div><div class="bottom-icons"><a href="https://github.com/hectorqin/reader" target="_blank"><div class="bottom-icon"><template v-if="isNight"><img :src='"data:image/png;base64,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"' alt="" /></template><template v-else><img :src='"data:image/png;base64,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"' alt="" /></template></div></a><span ref='"themes"' class="theme-item" :style="themeColor" @click="toogleNight"><template v-if="isNight"><i class="el-icon-sunny" /></template><template v-else><i class="el-icon-moon" /></template></span></div></div></template><div ref='"shelfWrapper"' class="shelf-wrapper" :class='isWebApp && !isNight ? "status-bar-light-bg" : ""' @click="hideMenu"><div class="shelf-title"><template v-if="$store.getters.isNormalPage && collapseMenu"><i class="el-icon-menu" @click="(e) => {
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
                            }'><div class="custom-dialog-title" slot="title"><span class="el-dialog__title">{{ _s(isShowFailureBookSource ? "失效书源管理" : "书源管理") + " " }}<template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="(e) => {
                                return deleteAllBookSource()
                            }">清空</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="(e) => {
                                return deleteBookSourceFile()
                            }">恢复默认</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="(e) => {
                                return exportBookSource()
                            }">导出</span></template><template v-if="!(isShowFailureBookSource)"><span class="float-right span-btn" @click="(e) => {
                                return editBookSource(!1)
                            }">新增</span></template></span></div><div class="source-container table-container"><template v-if="isShowFailureBookSource"><div class="check-form"><span class="check-form-label">搜索词：</span><el-input size="small" v-model="checkBookSourceConfig.keyword" /><span class="check-form-label" style="min-width: 68px"> 超时(ms)： </span><el-input-number min="1000" max="15000" step="500" size="small" v-model="checkBookSourceConfig.timeout" /><span class="check-form-label">并发数：</span><el-input-number min="3" max="15" step="1" size="small" v-model="checkBookSourceConfig.concurrent" /></div></template><div class="source-group-wrapper"><el-tabs class="booksource-group-tabs" stretch="" v-model="showSourceGroup"><template v-for="t in bookSourceShowGroup"><el-tab-pane :key='"sourceGroupTab-" + t' :label="t" :name="t" /></template></el-tabs></div><el-table :key="isShowFailureBookSource" :data="bookSourceShowResultPageList" :height="dialogContentHeight - 42 - 42 - (isShowFailureBookSource ? 32 : 0)" @selection-change="(e) => {
                                manageSourceSelection = e
                            }"><el-table-column type="selection" width="25" :fixed="$store.state.miniInterface" :selectable="isBookSourceSelectable" /><el-table-column property="bookSourceName" label="书源名称" min-width="60" sortable="" :fixed="$store.state.miniInterface" /><el-table-column property="bookSourceUrl" label="书源链接" min-width="120"><template v-slot:default="o"><el-link type="primary" :href="o.row.bookSourceUrl" target="_blank">{{ o.row.bookSourceUrl }}</el-link></template></el-table-column><template v-if="isShowFailureBookSource"><el-table-column property="errorMsg" label="错误信息" sortable="" min-width="120" /></template><el-table-column label="书架书籍" min-width="120"><template v-slot:default="o"><pre>{{ showSourceBook(o.row) }}</pre></template></el-table-column><template v-if="!(isShowFailureBookSource)"><el-table-column width="120px"><template slot='"header"'><el-input size="mini" placeholder="搜索书源" v-model="bookSourceKeyword" /></template><template v-slot:default="o"><el-button type="text" @click="(e) => {
                                            return editBookSource(o.row)
                                        }">编辑</el-button><el-button type="text" @click="(e) => {
                                            return editBookSourceHeader(o.row)
                                        }">请求头</el-button></template></el-table-column></template></el-table></div><div class="dialog-footer" slot="footer"><div><el-button class="float-left" type="primary" size="medium" @click="deleteBookSourceList">批量删除<template v-if="manageSourceSelection.length"><span>{{ " (" + _s(manageSourceSelection.length) + ")" }}</span></template></el-button><template v-if="isShowFailureBookSource"><el-button style="margin-bottom: 5px" type="primary" size="medium" @click="checkBookSource">{{ _s(isCheckingBookSource ? "正在" : "") + "检测书源 " + _s(checkBookSourceTip) }}</el-button></template></div><div class="source-pagination"><el-pagination :current-page="bookSourcePagination.page" :page-sizes="[25, 50, 100, 200, 300, 400]" :page-size="bookSourcePagination.size" layout="total, sizes, prev, pager, next" :total="bookSourceShowLength" :pager-count="collapseMenu ? 5 : 7" @update:currentPage='(e) => {
                                return $set(bookSourcePagination, "page", e)
                            }' @update:current-page='(e) => {
                                return $set(bookSourcePagination, "page", e)
                            }' @update:pageSize='(e) => {
                                return $set(bookSourcePagination, "size", e)
                            }' @update:page-size='(e) => {
                                return $set(bookSourcePagination, "size", e)
                            }' /></div></div></el-dialog></template><template v-if="$store.getters.isNormalPage"><el-dialog :class='isWebApp && !isNight ? "status-bar-light-bg-dialog" : ""' :title='"导入本地书籍" + importMultiBookTip' :visible="showImportBookDialog" :width="dialogSmallWidth" :top="dialogTop" :fullscreen="collapseMenu" @update:visible="(e) => {
                                showImportBookDialog = e
                            }" @closed="importBookDialogClosed"><div class="source-container table-container"><div class="check-form"><div class="book-cover"><el-image :key="getBookCoverUrl(importBookInfo)" class="cover" :src="getCover(getBookCoverUrl(importBookInfo), !0)" fit="cover" lazy="" /></div><div class="book-info"><div><span>书名：</span><el-input size="small" v-model="importBookInfo.name" /></div><div><span>作者：</span><el-input size="small" v-model="importBookInfo.author" /></div><div><span>分组：</span><el-select size="mini" filterable="" multiple="" placeholder="未分组" v-model="importBookGroup"><template v-for="(t, o) in bookGroupSetList"><el-option :key='"bookGroup-" + o' :label="t.groupName" :value="t.groupId" /></template></el-select></div><template v-if="isShowTocRule"><div><span>规则：</span><el-select size="mini" filterable="" placeholder="内置规则" v-model="importUsedTxtRule"><template v-for="(t, o) in tocRuleList"><el-option :key='"txtTocRule-" + o' :label="t.name" :value="t.rule" /></template></el-select><el-button class="toc-refresh-btn" type="text" @click="(e) => {
                                return getChapterListByRule()
                            }">刷新目录</el-button></div></template><template v-if="isShowTocRule"><div><el-input type="textarea" rows="2" size="small" v-model="importBookInfo.tocUrl" /></div></template></div></div><div class="chapter-title">{{ " 章节列表(" + _s(importBookChapters.length) + ") " }}</div><div class="chapter-list" :style='{maxHeight: dialogContentHeight - 40 - 35 + "px"}'><template v-for="(o, s) in importBookChapters"><p :key="s">{{ " " + _s(s + 1) + ". " + _s(o.title) + " " }}</p></template></div></div><div class="dialog-footer" slot="footer"><el-button type="primary" size="medium" @click="(e) => {
                                return saveBook(importBookInfo, !0)
                            }">确定导入</el-button><el-button size="medium" @click="(e) => {
                                showImportBookDialog = !1
                            }">取消</el-button></div></el-dialog></template><ShelfSettings v-model="showShelfSettingsDialog" /></div>
</template>

<script>
// 依赖的 webpack 模块 id 见同目录 _meta.json 与 docs/FRONTEND-RECONSTRUCTION.md
export default {
  name: "VirtualSelect",
    props: {
    width: {
    type: Number,
    default: 250
    },
    size: {
    type: String,
    default: "small"
    },
    placeholder: {
    type: String,
    default: "请选择"
    },
    dataList: {
    type: Array,
    default: function() {
    return []
    }
    },
    value: {
    type: [String, Number],
    default: ""
    }
    },
    components: {
    VirtList: _["a"]
    },
    watch: {
    visibleVirtualList: function(t) {
    var e = this;
    t ? (this.keyword = "", this.$nextTick((function() {
    var t = e.curIndex ? e.curIndex : 0;
    e.$refs.virtList.scrollToIndex(t - 1)
    }))) : this.keyword = this.curLabel
    },
    value: function(t) {
    var e;
    this.curValue = t, this.curLabel = (null === (e = this.dataList.find((function(e) {
    return e.bookSourceUrl === t
    }))) || void 0 === e ? void 0 : e.bookSourceName) || "", this.curIndex = this.dataList.findIndex((function(e) {
    return e.bookSourceUrl === t
    }))
    },
    dataList: function(t) {
    var e, o = this;
    this.curLabel = (null === (e = t.find((function(t) {
    return t.bookSourceUrl === o.value
    }))) || void 0 === e ? void 0 : e.bookSourceName) || "", this.curIndex = t.findIndex((function(t) {
    return t.bookSourceUrl === o.value
    }))
    }
    },
    data: function() {
    return {
    visibleVirtualList: !1,
    curValue: this.value,
    curLabel: "",
    curIndex: null,
    keyword: ""
    }
    },
    created: function() {
    var t = this;
    this.$on("clickVirtualItem", (function(e) {
    t.curIndex = e.index, t.curLabel = e.bookSourceName, t.visibleVirtualList = !1, t.$emit("input", e.bookSourceUrl)
    }))
    },
    computed: {
    list: function() {
    var t = this;
    return this.dataList.filter((function(e) {
    return !t.keyword || e.bookSourceName.includes(t.keyword)
    }))
    },
    tip: function() {
    return this.curLabel || this.placeholder || "请选择"
    }
    },
    mounted: function() {
    var t, e = this;
    this.curValue = this.value, this.curLabel = (null === (t = this.dataList.find((function(t) {
    return t.bookSourceUrl === e.value
    }))) || void 0 === t ? void 0 : t.bookSourceName) || "", this.curIndex = this.dataList.findIndex((function(t) {
    return t.bookSourceUrl === e.value
    })), this.keyword = this.curLabel
    },
    methods: {
    handleInput: function(t) {
    this.keyword = t, this.$refs.virtList.$forceUpdate()
    }
    },
  render: function() {
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
                            src: "data:image/png;base64,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",
                            alt: ""
                        }
                    }) : e("img", {
                        attrs: {
                            src: "data:image/png;base64,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",
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
                },
  staticRenderFns: []
}
</script>

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
