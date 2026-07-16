/** https://github.com/gedoor/legado/tree/master/app/src/main/java/io/legado/app/api */
/** https://github.com/gedoor/legado/tree/master/app/src/main/java/io/legado/app/web */

import type { webReadConfig } from '@/web'
import ajax from './axios'
import type {
  BaseBook,
  Book,
  BookChapter,
  BookProgress,
  SeachBook,
} from '@/book'
import type { Source } from '@/source'

export type LeagdoApiResponse<T> = {
  isSuccess: boolean
  errorMsg: string
  data: T
}

export type ServerCounts = {
  books: number
  bookSources: number
  rssSources: number
  replaceRules: number
  txtTocRules: number
  settings?: number
  appData?: number
  backups?: number
  sourceChecks?: number
}

export type ServerInfo = {
  service: string
  version?: string
  dataDir: string
  counts: ServerCounts
  networkTransport?: string
  customHostCount?: number
  heapDumpOnOom?: boolean
}

export type UpdateCheckResult = {
  channel: string
  currentVersion: string
  latestVersion?: string
  releaseName?: string
  releaseUrl?: string
  publishedAt?: string
  newer: boolean
  latencyMs?: number
  statusCode?: number
  message: string
}

export type AuthState = { required: boolean }
export type AuthSession = { token: string; expiresAt: number }

export type ExploreEntry = { title: string; url: string }
export type ExploreSource = {
  sourceUrl: string
  sourceName: string
  entries: ExploreEntry[]
}

export type TxtTocRule = {
  id?: string | number
  name: string
  rule: string
  replacement?: string
  example?: string
  serialNumber?: number
  enable?: boolean
}

export type ReplaceRule = {
  id?: string | number
  name: string
  group?: string
  pattern: string
  replacement?: string
  scope?: string
  scopeTitle?: boolean
  scopeContent?: boolean
  excludeScope?: string
  isEnabled?: boolean
  isRegex?: boolean
  timeoutMillisecond?: number
  order?: number
}

export type ServerExportData = {
  books: unknown[]
  bookSources: Source[]
  rssSources: Source[]
  replaceRules: ReplaceRule[]
  txtTocRules: TxtTocRule[]
  appSettings?: AppSettings
  appData?: Record<string, AppDataItem[]>
}

export type AppSettings = Record<string, Record<string, unknown>>

export type WebDavTestResult = {
  ok: boolean
  statusCode?: number
  latencyMs: number
  message: string
  target?: string
}

export type ServerBackup = {
  fileName: string
  path: string
  size: number
  modifiedTime: number
  remotePath?: string
}

export type WebDavBackup = {
  fileName: string
  modifiedTime: number
  size: number
  href: string
}

export type BookExportResult = {
  fileName: string
  mime: string
  base64: string
  imageCount?: number
  episodeCount?: number
}

export type BatchBookExportResult = {
  parallel: boolean
  succeeded: number
  failed: number
  results: Array<{
    bookUrl: string
    isSuccess: boolean
    errorMsg?: string
    data?: BookExportResult
  }>
}

export type BackupCheckResult = {
  enabled: boolean
  configured?: boolean
  newer: boolean
  statusCode?: number
  message?: string
  localModifiedTime?: number
  remote?: {
    fileName: string
    modifiedTime: number
    size: number
    href: string
  }
}

export type DirectUploadResult = {
  downloadUrl: string
  statusCode: number
  fileName: string
  summary?: string
}

export type MaintenanceResult = {
  completedAt: string
  cacheEntriesCleared: number
  cacheBytesCleared: number
  jsonFilesCompacted: number
  jsonBytesSaved: number
  scheduled: boolean
  logFile: string
}

export type BatchSourceChangeResult = {
  attempted: number
  succeeded: number
  failed: number
  delayMillis: number
  results: Array<{
    bookUrl: string
    isSuccess: boolean
    errorMsg: string
  }>
}

export type RssRefreshResult = {
  attempted: number
  succeeded: number
  articleCount: number
  results: Array<{
    sourceUrl: string
    sourceName: string
    articleCount: number
    isSuccess: boolean
  }>
}

export type RssArticleContent = {
  content: string
  cached: boolean
}

export type DictionaryResult = {
  name: string
  content: string
  url: string
  degraded?: string
  isSuccess: boolean
  errorMsg?: string
}

export type SourceCheckReport = {
  id: string
  kind: 'bookSource' | 'rssSource' | string
  sourceName: string
  sourceUrl: string
  enabled: boolean
  ok: boolean
  statusCode?: number
  latencyMs: number
  message: string
  checkedAt: number
  timeoutMillis?: number
}

export type SourceCheckSummary = {
  total: number
  ok: number
  failed: number
  skipped: number
  checkedAt: number
}

export type SourceCheckResult = {
  summary: SourceCheckSummary
  reports: SourceCheckReport[]
}

export type AppDataKind = {
  kind: string
  label: string
  description: string
  primaryKey: string
  status: string
  count: number
}

export type AppDataItem = Record<string, unknown>

export let legado_http_entry_point = ''
export let legado_webSocket_entry_point = ''

let wsOnError: typeof WebSocket.prototype.onerror = () => {}
let wsOnMessage: typeof WebSocket.prototype.onmessage = () => {}
export const setWebsocketOnMessage = (callback: typeof wsOnMessage) =>
  (wsOnMessage = callback)
export const setWebsocketOnError = (callback: typeof wsOnError) => {
  //WebSocket.prototype.onerror = callback
  wsOnError = callback
}

export const setApiEntryPoint = (
  http_entry_point: string,
  webSocket_entry_point: string,
) => {
  legado_http_entry_point = new URL(http_entry_point).toString()
  legado_webSocket_entry_point = new URL(webSocket_entry_point).toString()
  ajax.defaults.baseURL = legado_http_entry_point
}

// 书架API
// Http
const getReadConfig = async (http_url = legado_http_entry_point) => {
  const { data } = await ajax.get<LeagdoApiResponse<string>>('getReadConfig', {
    baseURL: http_url.toString(),
    timeout: 3000,
  })
  if (data.isSuccess) {
    try {
      return JSON.parse(data.data) as webReadConfig
    } catch {}
  }
}
const saveReadConfig = (config: webReadConfig) =>
  ajax.post<LeagdoApiResponse<string>>('saveReadConfig', config)

/** @deprecated: 使用`API.saveBookProgressWithBeacon`以确保在页面或者直接关闭的情况下保存进度 */
const saveBookProgress = (bookProgress: BookProgress) =>
  ajax.post('saveBookProgress', bookProgress)

/**主要在直接关闭浏览器情况下可靠发送书籍进度 */
const saveBookProgressWithBeacon = (bookProgress: BookProgress) => {
  if (!bookProgress) return
  // 常规请求可能会被取消 使用Fetch keep-alive 或者 navigator.sendBeacon
  navigator.sendBeacon(
    new URL('saveBookProgress', legado_http_entry_point),
    JSON.stringify(bookProgress),
  )
}

const getBookShelf = () => ajax.get<LeagdoApiResponse<Book[]>>('getBookshelf')

const getChapterList = (/** @type {string} */ bookUrl: string) =>
  ajax.get<LeagdoApiResponse<BookChapter[]>>(
    'getChapterList?url=' + encodeURIComponent(bookUrl),
  )

const refreshToc = (/** @type {string} */ bookUrl: string) =>
  ajax.get<LeagdoApiResponse<BookChapter[]>>(
    'refreshToc?url=' + encodeURIComponent(bookUrl),
  )

const getBookContent = (
  /** @type {string} */ bookUrl: string,
  /** @type {number} */ chapterIndex: number,
) =>
  ajax.get<LeagdoApiResponse<string>>(
    'getBookContent?url=' +
      encodeURIComponent(bookUrl) +
      '&index=' +
      chapterIndex,
  )

// HTTP. The Linux server executes portable source rules directly; this keeps
// search available even when the optional compatibility WebSocket is disabled.
const search = (
  searchKey: string,
  onReceive: (data: SeachBook[]) => void,
  onFinish: () => void,
) => {
  const notifyError = () => {
    ;(wsOnError as unknown as (event: Event) => void)(new Event('error'))
  }
  ajax
    .post<LeagdoApiResponse<SeachBook[]>>('searchBooks', { key: searchKey })
    .then(({ data }) => {
      if (data.isSuccess) onReceive(data.data || [])
      else notifyError()
    })
    .catch(notifyError)
    .finally(onFinish)
}

const getExploreSources = () =>
  ajax.get<LeagdoApiResponse<ExploreSource[]>>('getExploreSources')

const exploreBooks = (sourceUrl: string, url: string, page = 1) =>
  ajax.post<LeagdoApiResponse<SeachBook[]>>('exploreBooks', { sourceUrl, url, page })

const findBookSourceCandidates = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<SeachBook[]>>('findBookSourceCandidates', { bookUrl })

const changeBookSource = (bookUrl: string, candidate: SeachBook) =>
  ajax.post<LeagdoApiResponse<Book>>('changeBookSource', { bookUrl, candidate })

const autoChangeBookSource = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<Book>>('autoChangeBookSource', { bookUrl })

const batchChangeBookSources = (bookUrls?: string[]) =>
  ajax.post<LeagdoApiResponse<BatchSourceChangeResult>>('batchChangeBookSources', { bookUrls })

const refreshRssSources = (sourceUrls?: string[]) =>
  ajax.post<LeagdoApiResponse<RssRefreshResult>>('refreshRssSources', { sourceUrls })

const updateRssArticles = (links: string[], patch: Record<string, unknown>) =>
  ajax.post<LeagdoApiResponse<{ changed: number }>>('updateRssArticles', { links, ...patch })

const getRssArticleContent = (link: string) =>
  ajax.post<LeagdoApiResponse<RssArticleContent>>('getRssArticleContent', { link })

const requestHttpTts = (engineId: string, text: string, speed: number) =>
  ajax.post<Blob>(
    'requestHttpTts',
    { engineId, text, speed },
    { responseType: 'blob' },
  )

const startBookDownload = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<AppDataItem>>('startBookDownload', { bookUrl })

const cancelBookDownload = (id: string) =>
  ajax.post<LeagdoApiResponse<AppDataItem>>('cancelBookDownload', { id })

const getDownloadTaskFile = (taskId: string) =>
  ajax.get<Blob>('downloadTaskFile?id=' + encodeURIComponent(taskId), { responseType: 'blob' })

const lookupDictionary = (text: string, names?: string[]) =>
  ajax.post<LeagdoApiResponse<DictionaryResult[]>>('lookupDictionary', { text, names })

const applyThemeConfig = (themeName: string) =>
  ajax.post<LeagdoApiResponse<AppSettings>>('applyThemeConfig', { themeName })

const saveBook = (book: BaseBook) =>
  ajax.post<LeagdoApiResponse<string>>('saveBook', book)
const exportBook = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<BookExportResult>>('exportBook', { bookUrl })
const exportBookEpisodes = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<BookExportResult>>('exportBookEpisodes', { bookUrl })
const uploadBook = (bookUrl: string) =>
  ajax.post<LeagdoApiResponse<DirectUploadResult>>('uploadBook', { bookUrl })
const exportBooks = (bookUrls: string[]) =>
  ajax.post<LeagdoApiResponse<BatchBookExportResult>>('exportBooks', { bookUrls })
const deleteBook = (book: BaseBook) =>
  ajax.post<LeagdoApiResponse<string>>('deleteBook', book)

const isBookSourceRoute = () => /bookSource/i.test(location.hash || location.href)

// 源编辑API
// Http
const getSources = () =>
  isBookSourceRoute() ? ajax.get('getBookSources') : ajax.get('getRssSources')

const saveSource = (data: Source) =>
  isBookSourceRoute()
    ? ajax.post<LeagdoApiResponse<string>>('saveBookSource', data)
    : ajax.post<LeagdoApiResponse<string>>('saveRssSource', data)

const saveSources = (data: Source[]) =>
  isBookSourceRoute()
    ? ajax.post<LeagdoApiResponse<Source[]>>('saveBookSources', data)
    : ajax.post<LeagdoApiResponse<Source[]>>('saveRssSources', data)

const deleteSource = (data: Source[]) =>
  isBookSourceRoute()
    ? ajax.post<LeagdoApiResponse<string>>('deleteBookSources', data)
    : ajax.post<LeagdoApiResponse<string>>('deleteRssSources', data)

const debug = (
  /** @type {string} */ sourceUrl: string,
  /** @type {string} */ searchKey: string,
  /** @type {(data: string) => void} */ onReceive: (data: string) => void,
  /** @type {() => void} */ onFinish: () => void,
) => {
  ajax
    .post<LeagdoApiResponse<unknown>>('debugSource', {
      kind: isBookSourceRoute() ? 'book' : 'rss',
      sourceUrl,
      key: searchKey,
    })
    .then(({ data }) => {
      onReceive(data.isSuccess ? JSON.stringify(data.data, null, 2) : data.errorMsg)
    })
    .catch(() => {
      ;(wsOnError as unknown as (event: Event) => void)(new Event('error'))
    })
    .finally(onFinish)
}

/**
 * 从阅读获取需要特定处理的书籍封面
 * @param {string} coverUrl
 */
const getProxyCoverUrl = (coverUrl: string) => {
  if (coverUrl.startsWith(legado_http_entry_point)) return coverUrl
  return new URL(
    'cover?path=' + encodeURIComponent(coverUrl),
    legado_http_entry_point,
  ).toString()
}
/**
 * 从阅读获取需要特定处理的图片
 * @param {string} bookUrl
 * @param {string} src
 * @param {number|`${number}`} width
 */
const getProxyImageUrl = (
  bookUrl: string,
  src: string,
  width: number | `${number}`,
) => {
  if (src.startsWith(legado_http_entry_point)) return src
  return new URL(
    'image?path=' +
      encodeURIComponent(src) +
      '&url=' +
      encodeURIComponent(bookUrl) +
      '&width=' +
      width,
    legado_http_entry_point,
  ).toString()
}

const getServerInfo = () =>
  ajax.get<LeagdoApiResponse<ServerInfo>>('getServerInfo')

const checkForUpdates = () =>
  ajax.get<LeagdoApiResponse<UpdateCheckResult>>('checkForUpdates')

const getAuthState = () => ajax.get<LeagdoApiResponse<AuthState>>('getAuthState')
const authenticate = (password: string) => ajax.post<LeagdoApiResponse<AuthSession>>('authenticate', { password })

const exportData = () =>
  ajax.get<LeagdoApiResponse<ServerExportData>>('exportData')

const importData = (data: Partial<ServerExportData>) =>
  ajax.post<LeagdoApiResponse<Record<string, number>>>('importData', data)

const getBackups = () =>
  ajax.get<LeagdoApiResponse<ServerBackup[]>>('getBackups')

const getWebDavBackups = () =>
  ajax.get<LeagdoApiResponse<WebDavBackup[]>>('getWebDavBackups')

const createBackup = () =>
  ajax.post<LeagdoApiResponse<ServerBackup>>('createBackup')

const checkNewBackup = () =>
  ajax.post<LeagdoApiResponse<BackupCheckResult>>('checkNewBackup')

const restoreBackup = (fileName: string) =>
  ajax.post<LeagdoApiResponse<Record<string, number>>>('restoreBackup', {
    fileName,
  })

const restoreWebDavBackup = (fileName: string) =>
  ajax.post<LeagdoApiResponse<Record<string, number>>>('restoreWebDavBackup', { fileName })

const deleteWebDavBackup = (fileName: string) =>
  ajax.post<LeagdoApiResponse<WebDavBackup[]>>('deleteWebDavBackup', { fileName })

const deleteBackup = (fileName: string) =>
  ajax.post<LeagdoApiResponse<ServerBackup[]>>('deleteBackup', { fileName })

const getSourceChecks = () =>
  ajax.get<LeagdoApiResponse<SourceCheckReport[]>>('getSourceChecks')

const checkSources = (options: {
  scope?: 'all' | 'bookSources' | 'rssSources'
  onlyEnabled?: boolean
  timeoutMillis?: number
  limit?: number
}) => ajax.post<LeagdoApiResponse<SourceCheckResult>>('checkSources', options)

const deleteSourceChecks = () =>
  ajax.post<LeagdoApiResponse<SourceCheckReport[]>>('deleteSourceChecks')

const getAppSettings = () =>
  ajax.get<LeagdoApiResponse<AppSettings>>('getAppSettings')

const saveAppSettings = (settings: AppSettings) =>
  ajax.post<LeagdoApiResponse<AppSettings>>('saveAppSettings', settings)

const resetAppSettings = () =>
  ajax.post<LeagdoApiResponse<AppSettings>>('resetAppSettings')

const testWebDav = () =>
  ajax.post<LeagdoApiResponse<WebDavTestResult>>('testWebDav')

const testUploadRule = () =>
  ajax.post<LeagdoApiResponse<DirectUploadResult>>('testUploadRule')

const runMaintenance = () =>
  ajax.post<LeagdoApiResponse<MaintenanceResult>>('runMaintenance')

const getAppDataKinds = () =>
  ajax.get<LeagdoApiResponse<AppDataKind[]>>('getAppDataKinds')

const getAppData = (kind: string) =>
  ajax.get<LeagdoApiResponse<AppDataItem[]>>(
    'getAppData?kind=' + encodeURIComponent(kind),
  )

const saveAppData = (
  kind: string,
  data: AppDataItem | AppDataItem[],
  mode: 'upsert' | 'replace' = 'upsert',
) =>
  ajax.post<LeagdoApiResponse<AppDataItem[]>>(
    'saveAppData?kind=' + encodeURIComponent(kind) + '&mode=' + mode,
    data,
  )

const deleteAppData = (kind: string, data: AppDataItem | AppDataItem[]) =>
  ajax.post<LeagdoApiResponse<AppDataItem[]>>(
    'deleteAppData?kind=' + encodeURIComponent(kind),
    data,
  )

const getTxtTocRules = () =>
  ajax.get<LeagdoApiResponse<TxtTocRule[]>>('getTxtTocRules')

const saveTxtTocRule = (rule: TxtTocRule) =>
  ajax.post<LeagdoApiResponse<string>>('saveTxtTocRule', rule)

const deleteTxtTocRule = (rule: Pick<TxtTocRule, 'id'>) =>
  ajax.post<LeagdoApiResponse<string>>('deleteTxtTocRule', rule)

const getReplaceRules = () =>
  ajax.get<LeagdoApiResponse<string | ReplaceRule[]>>('getReplaceRules')

const saveReplaceRule = (rule: ReplaceRule) =>
  ajax.post<LeagdoApiResponse<string>>('saveReplaceRule', rule)

const deleteReplaceRule = (rule: Pick<ReplaceRule, 'id'>) =>
  ajax.post<LeagdoApiResponse<string>>('deleteReplaceRule', rule)

const testReplaceRule = (rule: ReplaceRule, text: string) =>
  ajax.post<LeagdoApiResponse<string>>('testReplaceRule', { rule, text })

const addLocalBook = (file: File) => {
  const data = new FormData()
  data.append('fileData', file)
  return ajax.post<LeagdoApiResponse<Book>>(
    'addLocalBook?fileName=' + encodeURIComponent(file.name),
    data,
    {
      headers: { 'Content-Type': 'multipart/form-data' },
    },
  )
}

export default {
  getReadConfig,
  saveReadConfig,
  saveBookProgress,
  saveBookProgressWithBeacon,
  getBookShelf,
  getChapterList,
  refreshToc,
  getBookContent,
  search,
  getExploreSources,
  exploreBooks,
  findBookSourceCandidates,
  changeBookSource,
  autoChangeBookSource,
  batchChangeBookSources,
  refreshRssSources,
  updateRssArticles,
  getRssArticleContent,
  requestHttpTts,
  startBookDownload,
  cancelBookDownload,
  getDownloadTaskFile,
  lookupDictionary,
  applyThemeConfig,
  saveBook,
  exportBook,
  exportBookEpisodes,
  uploadBook,
  exportBooks,
  deleteBook,

  getSources,
  saveSources,
  saveSource,
  deleteSource,
  debug,

  getProxyCoverUrl,
  getProxyImageUrl,

  getServerInfo,
  checkForUpdates,
  getAuthState,
  authenticate,
  exportData,
  importData,
  getBackups,
  getWebDavBackups,
  createBackup,
  checkNewBackup,
  restoreBackup,
  restoreWebDavBackup,
  deleteWebDavBackup,
  deleteBackup,
  getSourceChecks,
  checkSources,
  deleteSourceChecks,
  getAppSettings,
  saveAppSettings,
  resetAppSettings,
  testWebDav,
  testUploadRule,
  runMaintenance,
  getAppDataKinds,
  getAppData,
  saveAppData,
  deleteAppData,
  getTxtTocRules,
  saveTxtTocRule,
  deleteTxtTocRule,
  getReplaceRules,
  saveReplaceRule,
  deleteReplaceRule,
  testReplaceRule,
  addLocalBook,
}
