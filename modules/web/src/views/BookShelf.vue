<template>
  <div :class="{ 'index-wrapper': true, night: isNight, day: !isNight }">
    <div class="navigation-wrapper">
      <div class="navigation-title-wrapper">
        <div class="navigation-title">阅读</div>
        <div class="navigation-sub-title">清风不识字，何故乱翻书</div>
      </div>
      <div class="search-wrapper">
        <el-input
          placeholder="搜索书籍，在线书籍自动加入书架"
          v-model="searchWord"
          class="search-input"
          :prefix-icon="SearchIcon"
          @keyup.enter="searchBook"
        >
        </el-input>
      </div>
      <div class="bottom-wrapper">
        <div class="recent-wrapper">
          <div class="recent-title">最近阅读</div>
          <div class="reading-recent">
            <el-tag
              :type="
                readingRecent.name == '尚无阅读记录' ? 'warning' : 'primary'
              "
              class="recent-book"
              size="large"
              @click="
                toDetail(
                  readingRecent.bookUrl,
                  readingRecent.name,
                  readingRecent.author,
                  readingRecent.chapterIndex,
                  readingRecent.chapterPos,
                  readingRecent.isSeachBook,
                  true,
                )
              "
              :class="{ 'no-point': readingRecent.bookUrl == '' }"
            >
              {{ readingRecent.name }}
            </el-tag>
          </div>
        </div>
        <div class="setting-wrapper">
          <div class="setting-title">基本设定</div>
          <div class="setting-item">
            <el-tag
              :type="connectType"
              size="large"
              class="setting-connect"
              :class="{ 'no-point': newConnect }"
              @click="setLegadoRetmoteUrl"
            >
              {{ connectStatus }}
            </el-tag>
          </div>
        </div>
      </div>
      <div class="bottom-icons">
        <a
          href="https://github.com/gedoor/legado_web_bookshelf"
          target="_blank"
        >
          <div class="bottom-icon">
            <img :src="githubUrl" alt="" />
          </div>
        </a>
      </div>
    </div>
    <div class="shelf-wrapper" ref="shelfWrapper">
      <div class="shelf-refresh-bar">
        <span v-if="lastRefreshSummary" role="status">{{ lastRefreshSummary }}</span>
        <button
          type="button"
          :disabled="refreshingShelf"
          :title="refreshingShelf ? '正在刷新书架' : '刷新书架章节信息'"
          aria-label="刷新书架章节信息"
          @click="refreshBookshelf()"
        >
          <el-icon :class="{ spinning: refreshingShelf }"><Refresh /></el-icon>
        </button>
        <button
          class="batch-source-button"
          type="button"
          :disabled="batchChanging || shelf.length === 0"
          :title="batchChanging ? '正在批量换源' : '批量更换书架书源'"
          @click="batchChangeSources"
        >
          {{ batchChanging ? '换源中' : '批量换源' }}
        </button>
        <button class="batch-source-button" type="button" title="新建书籍分组" @click="createBookGroup">
          新建分组
        </button>
        <button class="batch-source-button" type="button" title="管理书籍分组" @click="router.push({ path: '/features', query: { kind: 'bookGroups' } })">
          管理分组
        </button>
        <button class="batch-source-button" type="button" :disabled="selectedGroupId === 0 || books.length === 0" @click="updateFilteredGroup(true)">
          加入分组
        </button>
        <button class="batch-source-button" type="button" :disabled="selectedGroupId === 0 || groupFilteredBooks.length === 0" @click="updateFilteredGroup(false)">
          移出分组
        </button>
      </div>
      <div
        v-if="bookGroups.length > 0 && groupStyle !== '2'"
        class="group-selector"
        :class="{ 'group-selector-side': groupStyle === '1' }"
        aria-label="书架分组"
      >
        <button
          v-for="group in groupOptions"
          :key="group.id"
          type="button"
          :class="{ active: selectedGroupId === group.id }"
          @click="selectedGroupId = group.id"
        >
          {{ group.name }}
          <span>{{ group.count }}</span>
        </button>
      </div>
      <el-select
        v-else-if="bookGroups.length > 0"
        v-model="selectedGroupId"
        class="group-selector-select"
        aria-label="书架分组"
      >
        <el-option
          v-for="group in groupOptions"
          :key="group.id"
          :label="`${group.name} (${group.count})`"
          :value="group.id"
        />
      </el-select>
      <book-items
        :books="groupFilteredBooks"
        @bookClick="handleBookClick"
        :isSearch="isSearching"
        :layout="bookshelfLayout"
        :margin="bookshelfMargin"
        :show-last-update="showLastUpdate"
        :show-unread="showUnread"
        :default-cover="defaultCover"
        :show-cover-name="showCoverName"
        :show-cover-author="showCoverAuthor"
        :fallback-cover-style="fallbackCoverStyle"
        :load-covers="loadCovers"
      ></book-items>
      <div v-if="showWaitUpCount && waitUpCount > 0" class="wait-up-count" role="status">
        {{ waitUpLabel }} {{ waitUpCount }}
      </div>
      <div v-if="showBookshelfFastScroller" class="shelf-fast-scroller">
        <label class="sr-only" :for="fastScrollerId">{{ fastScrollerLabel }}</label>
        <input
          :id="fastScrollerId"
          v-model.number="scrollProgress"
          class="fast-scroller-input"
          type="range"
          min="0"
          max="100"
          step="1"
          :aria-label="fastScrollerLabel"
          @input="scrollShelfToProgress"
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import '@/assets/bookshelf.css'
import '@/assets/fonts/shelffont.css'
import { useBookStore } from '@/store'
import githubUrl from '@/assets/imgs/github.png'
import { useLoading } from '@/hooks/loading'
import { Refresh, Search as SearchIcon } from '@element-plus/icons-vue'
import { baseURL_localStorage_key } from '@/api/axios'
import API, {
  legado_http_entry_point,
  parseLeagdoHttpUrlWithDefault,
  setApiEntryPoint,
} from '@api'
import { validatorHttpUrl } from '@/utils/utils'
import type { Book, SeachBook } from '@/book'
import type { webReadConfig } from '@/web'
import type { AppSettings } from '@api'

const store = useBookStore()
const isNight = computed(() => store.isNight)

/** shortcuts of `store.setConfig` */
const applyReadConfig = (config?: webReadConfig) => {
  try {
    if (config !== undefined) store.setConfig(config)
  } catch {
    ElMessage.info('阅读界面配置解析错误')
  }
}

const readingRecent = ref<typeof store.readingBook>({
  name: '尚无阅读记录',
  author: '',
  bookUrl: '',
  chapterIndex: 0,
  chapterPos: 0,
  isSeachBook: false,
})

const shelfWrapper = ref<HTMLElement>()
//const shelfWrapper = useTemplateRef<HTMLElement>("shelfWrapper")
const { showLoading, closeLoading, loadingWrapper, isLoading } = useLoading(
  shelfWrapper,
  '正在获取书籍信息',
)

// 书架书籍和在线书籍搜索
const books = shallowRef<Book[] | SeachBook[]>([])
const shelf = computed(() => store.shelf)
const appSettings = ref<AppSettings>({})
const refreshingShelf = ref(false)
const batchChanging = ref(false)
const lastRefreshSummary = ref('')
const autoRefreshShelf = computed(() => appSettings.value.main?.auto_refresh === true)
const onlyUpdateRead = computed(() => appSettings.value.main?.onlyUpdateRead === true)
const bookshelfLayout = computed<'list' | 'grid' | 'wall'>(() => {
  const value = String(appSettings.value.main?.bookshelfLayout ?? '0')
  return value === '1' ? 'grid' : value === '2' ? 'wall' : 'list'
})
const bookshelfSort = computed(() => String(appSettings.value.main?.bookshelfSort ?? '0'))
const groupStyle = computed(() => String(appSettings.value.main?.bookGroupStyle ?? '0'))
type BookGroupOption = { groupId: number; groupName: string; show?: boolean; order?: number }
const bookGroups = ref<BookGroupOption[]>([])
const selectedGroupId = ref(0)
const belongsToGroup = (book: Book | SeachBook, groupId: number) => {
  if (groupId === 0 || !('group' in book)) return groupId === 0
  const value = Number(book.group || 0)
  return value === groupId || (value & groupId) === groupId
}
const groupFilteredBooks = computed(() =>
  selectedGroupId.value === 0 || isSearching.value
    ? books.value
    : books.value.filter(book => belongsToGroup(book, selectedGroupId.value)),
)
const groupOptions = computed(() => [
  { id: 0, name: '全部', count: books.value.length },
  ...bookGroups.value.map(group => ({
    id: group.groupId,
    name: group.groupName,
    count: books.value.filter(book => belongsToGroup(book, group.groupId)).length,
  })),
])
const bookshelfMargin = computed(() =>
  Math.min(48, Math.max(0, Number(appSettings.value.main?.bookshelfMargin ?? 12))),
)
const showLastUpdate = computed(
  () => appSettings.value.main?.showLastUpdateTime === true,
)
const showUnread = computed(
  () => appSettings.value.main?.showUnread !== false,
)
const showWaitUpCount = computed(
  () => appSettings.value.main?.showWaitUpCount === true,
)
const showBookshelfFastScroller = computed(
  () => appSettings.value.main?.showBookshelfFastScroller === true,
)
const waitUpCount = computed(() =>
  shelf.value.filter(book => book.canUpdate === true || Number(book.lastCheckCount || 0) > 0).length,
)
const scrollProgress = ref(0)
const fastScrollerId = 'bookshelf-fast-scroller'
const waitUpLabel = String.fromCharCode(24453, 26356)
const fastScrollerLabel = String.fromCharCode(20070, 26550, 24555, 36895, 28378, 21160)
const defaultCover = computed(() => {
  const theme = appSettings.value.theme || {}
  const configured = isNight.value ? theme.defaultCoverDark : theme.defaultCover
  return theme.useDefaultCover === true && typeof configured === 'string' ? configured : ''
})
const showCoverName = computed(() => {
  if (typeof coverRule.value.showName === 'boolean') return coverRule.value.showName
  const theme = appSettings.value.theme || {}
  return isNight.value ? theme.coverShowNameN !== false : theme.coverShowName !== false
})
const showCoverAuthor = computed(() => {
  if (typeof coverRule.value.showAuthor === 'boolean') return coverRule.value.showAuthor
  const theme = appSettings.value.theme || {}
  return isNight.value ? theme.coverShowAuthorN !== false : theme.coverShowAuthor !== false
})
type CoverRule = {
  background?: string
  color?: string
  borderColor?: string
  radius?: number
  showName?: boolean
  showAuthor?: boolean
}
const coverRule = computed<CoverRule>(() => {
  const raw = String(appSettings.value.theme?.coverRule ?? '').trim()
  if (!raw) return {}
  try {
    return JSON.parse(raw) as CoverRule
  } catch {
    return CSS.supports('background', raw) ? { background: raw } : {}
  }
})
const safeCoverCss = (property: string, value: unknown) => {
  const text = String(value ?? '').trim()
  return text && CSS.supports(property, text) ? text : undefined
}
const fallbackCoverStyle = computed(() => ({
  background: safeCoverCss('background', coverRule.value.background),
  color: safeCoverCss('color', coverRule.value.color),
  borderColor: safeCoverCss('border-color', coverRule.value.borderColor),
  borderRadius: Number.isFinite(Number(coverRule.value.radius))
    ? `${Math.min(24, Math.max(0, Number(coverRule.value.radius)))}px`
    : undefined,
}))
type NetworkInformationLike = EventTarget & { type?: string }
const networkRevision = ref(0)
const networkInformation = () =>
  (navigator as Navigator & { connection?: NetworkInformationLike }).connection
const loadCovers = computed(() => {
  networkRevision.value
  if (appSettings.value.theme?.loadCoverOnlyWifi !== true) return true
  const type = networkInformation()?.type
  return type !== 'cellular'
})
const refreshNetworkPolicy = () => {
  networkRevision.value += 1
}
const searchWord = ref('')
const isSearching = ref(false)
const precisionSearch = computed(
  () => appSettings.value.network?.precisionSearch === true,
)
const matchesShelfSearch = (book: Book, query: string) => {
  const normalizedQuery = query.trim().toLocaleLowerCase()
  const name = book.name.trim().toLocaleLowerCase()
  const author = book.author.trim().toLocaleLowerCase()
  return precisionSearch.value
    ? name === normalizedQuery || author === normalizedQuery
    : name.includes(normalizedQuery) || author.includes(normalizedQuery)
}
watchEffect(() => {
  if (isSearching.value && searchWord.value != '') return
  isSearching.value = false
  books.value = []
  const visibleBooks = searchWord.value
    ? shelf.value.filter(book => matchesShelfSearch(book, searchWord.value))
    : shelf.value
  books.value = sortShelfBooks(visibleBooks)
})
function sortShelfBooks(items: Book[]) {
  const sorted = [...items]
  if (bookshelfSort.value === '1') {
    return sorted.sort((left, right) => Number(right.lastCheckTime || 0) - Number(left.lastCheckTime || 0))
  }
  if (bookshelfSort.value === '2') {
    return sorted.sort((left, right) => left.name.localeCompare(right.name, 'zh-Hans-CN'))
  }
  return sorted.sort((left, right) => Number(right.durChapterTime || 0) - Number(left.durChapterTime || 0))
}

async function loadAppPreferences() {
  try {
    const [response, groupsResponse] = await Promise.all([
      API.getAppSettings(),
      API.getAppData('bookGroups'),
    ])
    if (response.data.isSuccess) appSettings.value = response.data.data
    if (groupsResponse.data.isSuccess) {
      bookGroups.value = groupsResponse.data.data
        .filter(group => group.show !== false)
        .map(group => ({
          groupId: Number(group.groupId),
          groupName: String(group.groupName || group.groupId),
          show: group.show !== false,
          order: Number(group.order || 0),
        }))
        .filter(group => Number.isFinite(group.groupId) && group.groupId > 0)
        .sort((left, right) => Number(left.order || 0) - Number(right.order || 0))
    }
  } catch {
    // Keep the original bookshelf behavior when preferences are unavailable.
  }
}

async function createBookGroup() {
  try {
    const result = await ElMessageBox.prompt('输入分组名称', '新建书籍分组', {
      confirmButtonText: '创建', cancelButtonText: '取消', inputPattern: /\S+/, inputErrorMessage: '分组名称不能为空',
    })
    const groups = await API.getAppData('bookGroups')
    if (!groups.data.isSuccess) throw new Error(groups.data.errorMsg || '无法读取分组')
    const used = new Set(groups.data.data.map(group => Number(group.groupId)))
    let groupId = 1
    while (used.has(groupId) && groupId < 1_073_741_824) groupId *= 2
    if (used.has(groupId)) throw new Error('分组数量已达到上限')
    await API.saveAppData('bookGroups', {
      groupId,
      groupName: result.value.trim(),
      order: groups.data.data.length,
      show: true,
      enableRefresh: true,
      bookSort: -1,
    })
    await loadAppPreferences()
    selectedGroupId.value = groupId
    ElMessage.success('分组已创建')
  } catch (error) {
    if (error !== 'cancel') ElMessage.error(error instanceof Error ? error.message : '创建分组失败')
  }
}

async function updateFilteredGroup(add: boolean) {
  const groupId = selectedGroupId.value
  if (groupId === 0) return
  const targetBooks = add ? books.value : groupFilteredBooks.value
  const candidates = targetBooks.filter((book): book is Book => !('respondTime' in book))
  if (candidates.length === 0) return
  const groupName = groupOptions.value.find(group => group.id === groupId)?.name || String(groupId)
  try {
    await ElMessageBox.confirm(
      `${add ? '将当前筛选书籍加入' : '将当前筛选书籍移出'}分组“${groupName}”？`,
      '更新书籍分组',
      { type: 'warning', confirmButtonText: '确认', cancelButtonText: '取消' },
    )
    for (const book of candidates) {
      const current = Number(book.group || 0)
      const group = add ? (current | groupId) : (current & ~groupId)
      if (group !== current) await API.saveBook({ ...(book as Record<string, unknown>), group } as any)
    }
    await loadShelf()
    ElMessage.success('书籍分组已更新')
  } catch (error) {
    if (error !== 'cancel') ElMessage.error(error instanceof Error ? error.message : '更新书籍分组失败')
  }
}
function updateScrollProgress() {
  const scrollHeight = Math.max(0, document.documentElement.scrollHeight - window.innerHeight)
  scrollProgress.value = scrollHeight === 0
    ? 0
    : Math.round((window.scrollY / scrollHeight) * 100)
}

function scrollShelfToProgress() {
  const scrollHeight = Math.max(0, document.documentElement.scrollHeight - window.innerHeight)
  window.scrollTo({ top: (scrollProgress.value / 100) * scrollHeight, behavior: 'auto' })
}
//搜索在线书籍
const searchBook = () => {
  if (searchWord.value == '') return
  books.value = []
  store.clearSearchBooks()
  showLoading()
  isSearching.value = true
  API.search(
    searchWord.value,
    searcBooks => {
      if (isLoading) {
        closeLoading()
      }
      try {
        store.setSearchBooks(searcBooks)
        books.value = store.searchBooks
        //store.searchBooks.forEach((item) => books.value.push(item));
      } catch (e) {
        ElMessage.error('后端数据错误')
        throw e
      }
    },
    () => {
      closeLoading()
      if (books.value.length == 0) {
        ElMessage.info('搜索结果为空')
      }
    },
  )
}

//连接状态
const connectionStore = useConnectionStore()
const { connectStatus, connectType, newConnect } = storeToRefs(connectionStore)

const setLegadoRetmoteUrl = () => {
  ElMessageBox.prompt(
    '请输入 后端地址 ( 如：http://127.0.0.1:9527 或者通过内网穿透的地址)',
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      inputPlaceholder: legado_http_entry_point,
      inputValidator: value => validatorHttpUrl(value),
      inputErrorMessage: '输入的格式不对',
      beforeClose: (action, instance, done) => {
        if (action === 'confirm') {
          connectionStore.setNewConnect(true)
          instance.confirmButtonLoading = true
          instance.confirmButtonText = '校验中……'
          // instance.inputValue
          const url = new URL(instance.inputValue).toString()
          API.getReadConfig(url)
            .then(function (config) {
              connectionStore.setNewConnect(false)
              applyReadConfig(config)
              instance.confirmButtonLoading = false
              store.clearSearchBooks()
              setApiEntryPoint(...parseLeagdoHttpUrlWithDefault(url))
              if (url === location.origin) {
                localStorage.removeItem(baseURL_localStorage_key)
              } else {
                localStorage.setItem(baseURL_localStorage_key, url)
              }
              store.loadBookShelf()
              done()
            })
            .catch(function (error) {
              connectionStore.setNewConnect(false)
              instance.confirmButtonLoading = false
              instance.confirmButtonText = '确定'
              throw error
            })
        } else {
          done()
        }
      },
    },
  )
}

const router = useRouter()
const handleBookClick = async (book: SeachBook | Book) => {
  // 判断是否为 searchBook
  const isSeachBook = 'respondTime' in book
  if (isSeachBook) {
    if (appSettings.value.maintenance?.showAddToShelfAlert !== false) {
      try {
        await ElMessageBox.confirm(
          `确认将《${book.name}》加入书架并打开吗？`,
          '加入书架',
          {
            confirmButtonText: '加入并打开',
            cancelButtonText: '取消',
            type: 'info',
          },
        )
      } catch {
        return
      }
    }
    await API.saveBook(book)
  }
  const {
    bookUrl,
    name,
    author,
    // @ts-expect-error: descruct with default value
    durChapterIndex = 0,
    // @ts-expect-error: descruct with default value
    durChapterPos = 0,
  } = book

  toDetail(bookUrl, name, author, durChapterIndex, durChapterPos, isSeachBook)
}
const toDetail = (
  bookUrl: string,
  bookName: string,
  bookAuthor: string,
  chapterIndex: number,
  chapterPos: number,
  isSeachBook: boolean | undefined = false,
  fromReadRecentClick = false,
) => {
  if (bookName === '尚无阅读记录') return
  // 最近书籍不再书架上 自动搜索
  if (
    fromReadRecentClick &&
    shelf.value.every(book => book.bookUrl !== bookUrl)
  ) {
    searchWord.value = bookName
    searchBook()
    return
  }
  sessionStorage.setItem('bookUrl', bookUrl)
  sessionStorage.setItem('bookName', bookName)
  sessionStorage.setItem('bookAuthor', bookAuthor)
  sessionStorage.setItem('chapterIndex', String(chapterIndex))
  sessionStorage.setItem('chapterPos', String(chapterPos))
  sessionStorage.setItem('isSeachBook', String(isSeachBook))
  readingRecent.value = {
    name: bookName,
    author: bookAuthor,
    bookUrl,
    chapterIndex,
    chapterPos,
    isSeachBook,
  }
  localStorage.setItem('readingRecent', JSON.stringify(readingRecent.value))
  router.push({
    path: '/chapter',
  })
}

const loadShelf = async () => {
  await store.loadWebConfig()
  await store.saveBookProgress()
  //确保各种网络情况下同步请求先完成
  await store.loadBookShelf()
}

const hasBeenRead = (book: Book) =>
  Number(book.durChapterIndex || 0) > 0 ||
  Number(book.durChapterPos || 0) > 0 ||
  Number(book.durChapterTime || 0) > 0

async function refreshBookshelf(showMessage = true) {
  if (refreshingShelf.value) return
  const candidates = shelf.value.filter(book => !onlyUpdateRead.value || hasBeenRead(book))
  if (candidates.length === 0) {
    lastRefreshSummary.value = onlyUpdateRead.value ? '没有已读书籍需要刷新' : '书架为空'
    if (showMessage) ElMessage.info(lastRefreshSummary.value)
    return
  }
  refreshingShelf.value = true
  let cursor = 0
  let succeeded = 0
  let failed = 0
  const worker = async () => {
    while (cursor < candidates.length) {
      const book = candidates[cursor++]
      try {
        const response = await API.getChapterList(book.bookUrl)
        if (response.data.isSuccess) succeeded += 1
        else failed += 1
      } catch {
        failed += 1
      }
    }
  }
  try {
    await Promise.all(Array.from({ length: Math.min(4, candidates.length) }, worker))
    await store.loadBookShelf()
    lastRefreshSummary.value = `已刷新 ${succeeded} 本${failed ? `，失败 ${failed} 本` : ''}`
    if (showMessage) {
      const notify = failed ? ElMessage.warning : ElMessage.success
      notify(lastRefreshSummary.value)
    }
  } finally {
    refreshingShelf.value = false
  }
}

async function batchChangeSources() {
  if (batchChanging.value || shelf.value.length === 0) return
  try {
    await ElMessageBox.confirm(
      `将依次为书架中的 ${shelf.value.length} 本书搜索并切换备用书源。无匹配书源的书籍会保持不变。`,
      '批量换源',
      { confirmButtonText: '开始换源', cancelButtonText: '取消', type: 'warning' },
    )
  } catch {
    return
  }
  batchChanging.value = true
  try {
    const response = await API.batchChangeBookSources(shelf.value.map(book => book.bookUrl))
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '批量换源失败')
    const result = response.data.data
    store.shelf = []
    await store.loadBookShelf()
    lastRefreshSummary.value = `换源完成 ${result.succeeded}/${result.attempted}${result.failed ? `，失败 ${result.failed} 本` : ''}`
    ;(result.failed ? ElMessage.warning : ElMessage.success)(lastRefreshSummary.value)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '批量换源失败')
  } finally {
    batchChanging.value = false
  }
}

async function initializeShelf() {
  await Promise.all([loadAppPreferences(), loadShelf()])
  if (autoRefreshShelf.value) await refreshBookshelf(false)
}

onMounted(() => {
  window.addEventListener('scroll', updateScrollProgress, { passive: true })
  networkInformation()?.addEventListener('change', refreshNetworkPolicy)
  updateScrollProgress()
  //获取最近阅读书籍
  const readingRecentStr = localStorage.getItem('readingRecent')
  if (readingRecentStr != null) {
    readingRecent.value = JSON.parse(readingRecentStr)
    if (typeof readingRecent.value.chapterIndex == 'undefined') {
      readingRecent.value.chapterIndex = 0
    }
  }
  console.log('bookshelf mounted')
  loadingWrapper(initializeShelf())
})
onUnmounted(() => {
  window.removeEventListener('scroll', updateScrollProgress)
  networkInformation()?.removeEventListener('change', refreshNetworkPolicy)
})
</script>

<style lang="scss" scoped>
@keyframes shelf-refresh-spin {
  to { transform: rotate(360deg); }
}

.index-wrapper {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: row;

  .navigation-wrapper {
    width: 260px;
    min-width: 260px;
    padding: 48px 36px;
    background-color: #f7f7f7;

    .navigation-title {
      font-size: 24px;
      font-weight: 500;
      font-family: FZZCYSK;
    }

    .navigation-sub-title {
      font-size: 16px;
      font-weight: 300;
      font-family: FZZCYSK;
      margin-top: 16px;
      color: #b1b1b1;
    }

    .search-wrapper {
      .search-input {
        border-radius: 50%;
        margin-top: 24px;

        :deep(.el-input__wrapper) {
          border-radius: 50px;
          border-color: #e3e3e3;
        }
      }
    }

    .bottom-wrapper {
      display: flex;
      flex-direction: column;
    }

    .recent-wrapper {
      margin-top: 36px;

      .recent-title {
        font-size: 14px;
        color: #b1b1b1;
        font-family: FZZCYSK;
      }

      .reading-recent {
        margin: 18px 0;

        .recent-book {
          font-size: 10px;
          /*           // font-weight: 400;
          // margin: 12px 0;
          // font-weight: 500;
          // color: #6B7C87; */
          cursor: pointer;
          /*           // padding: 6px 18px; */
        }
      }
    }

    .setting-wrapper {
      margin-top: 36px;

      .setting-title {
        font-size: 14px;
        color: #b1b1b1;
        font-family: FZZCYSK;
      }

      .no-point {
        pointer-events: none;
      }

      .setting-connect {
        font-size: 8px;
        margin-top: 16px;
        /*         // color: #6B7C87; */
        cursor: pointer;
      }
    }

    .bottom-icons {
      position: fixed;
      bottom: 0;
      height: 120px;
      width: 260px;
      align-items: center;
      display: flex;
      flex-direction: row;
    }
  }

  .shelf-wrapper {
    padding: 48px 48px;
    width: 100%;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
    overflow: hidden;
    position: relative;

    .shelf-refresh-bar {
      display: flex;
      align-items: center;
      justify-content: flex-end;
      gap: 10px;
      min-height: 34px;
      margin-bottom: 10px;
      color: #64748b;
      font-size: 12px;

      button {
        display: inline-grid;
        place-items: center;
        width: 32px;
        height: 32px;
        padding: 0;
        border: 1px solid rgba(148, 163, 184, 0.5);
        border-radius: 6px;
        background: rgba(255, 255, 255, 0.86);
        color: #475569;
        cursor: pointer;
      }

      button:disabled {
        cursor: wait;
        opacity: 0.65;
      }

      .batch-source-button {
        display: inline-flex;
        width: auto;
        min-width: 74px;
        padding: 0 10px;
        white-space: nowrap;
      }

      .spinning {
        animation: shelf-refresh-spin 0.9s linear infinite;
      }
    }

    .group-selector {
      display: flex;
      gap: 6px;
      margin-bottom: 16px;
      overflow-x: auto;

      button {
        display: inline-flex;
        align-items: center;
        gap: 6px;
        min-height: 34px;
        padding: 6px 10px;
        border: 1px solid rgba(148, 163, 184, 0.48);
        border-radius: 6px;
        background: rgba(255, 255, 255, 0.82);
        color: #475569;
        white-space: nowrap;
        cursor: pointer;
      }

      button.active {
        border-color: var(--legado-primary);
        background: var(--legado-primary);
        color: #fff;
      }

      span {
        font-size: 11px;
        opacity: 0.75;
      }
    }

    .group-selector-side {
      position: absolute;
      top: 48px;
      left: 48px;
      z-index: 10;
      flex-direction: column;
      width: 132px;
      max-height: calc(100vh - 100px);
      margin: 0;
    }

    .group-selector-side + :deep(.books-wrapper) {
      width: calc(100% - 148px);
      margin-left: 148px;
    }

    .group-selector-select {
      width: min(260px, 100%);
      margin-bottom: 16px;
    }

    .wait-up-count {
      position: fixed;
      right: 24px;
      bottom: 24px;
      z-index: 22;
      min-width: 34px;
      padding: 6px 9px;
      border: 1px solid rgba(180, 83, 9, 0.28);
      border-radius: 7px;
      background: rgba(255, 251, 235, 0.96);
      color: #92400e;
      font-size: 12px;
      font-weight: 700;
      line-height: 1;
      text-align: center;
      box-shadow: 0 8px 20px rgba(15, 23, 42, 0.12);
    }

    .shelf-fast-scroller {
      position: fixed;
      right: 10px;
      top: 50%;
      z-index: 21;
      display: flex;
      height: min(38vh, 320px);
      transform: translateY(-50%);
      align-items: center;
      padding: 8px 5px;
      border: 1px solid rgba(148, 163, 184, 0.55);
      border-radius: 7px;
      background: rgba(255, 255, 255, 0.9);
      box-shadow: 0 10px 24px rgba(15, 23, 42, 0.12);

      .fast-scroller-input {
        width: min(34vh, 300px);
        accent-color: #0f766e;
        transform: rotate(-90deg);
      }
    }
  }

  .sr-only {
    position: absolute;
    width: 1px;
    height: 1px;
    padding: 0;
    margin: -1px;
    overflow: hidden;
    clip: rect(0, 0, 0, 0);
    white-space: nowrap;
    border: 0;
  }
}

@media screen and (max-width: 750px) {
  .index-wrapper {
    overflow-x: hidden;
    flex-direction: column;

    .navigation-wrapper {
      padding: 20px 24px;
      box-sizing: border-box;
      width: 100%;

      .navigation-title-wrapper {
        white-space: nowrap;
        display: flex;
        justify-content: space-between;
        align-items: flex-end;
      }

      .bottom-wrapper {
        flex-direction: row;

        > * {
          flex-grow: 1;
          margin-top: 18px;

          .reading-recent,
          .setting-item {
            margin-bottom: 0px;
          }
        }
      }

      .bottom-icons {
        display: none;
      }
    }

    .shelf-wrapper {
      padding: 0;
      flex-grow: 1;

      .group-selector,
      .group-selector-side {
        position: static;
        flex-direction: row;
        width: auto;
        max-height: none;
        margin: 10px 12px;
      }

      .group-selector-side + :deep(.books-wrapper) {
        width: 100%;
        margin-left: 0;
      }

      .group-selector-select {
        width: calc(100% - 24px);
        margin: 10px 12px;
      }

      :deep(.el-loading-spinner) {
        display: none;
      }
    }
  }
}

.night {
  .navigation-wrapper {
    background-color: #454545;

    .navigation-title {
      color: #aeaeae;
    }

    .search-wrapper {
      .search-input {
        .el-input__wrapper {
          background-color: #454545;
        }

        .el-input__inner {
          color: #b1b1b1;
        }
      }
    }
  }

  :deep(.shelf-wrapper) {
    background-color: #161819;
  }
}
</style>
