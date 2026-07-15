import type { AppDataItem, AppDataKind } from '@api'

export type AppDataFieldType =
  | 'text'
  | 'textarea'
  | 'number'
  | 'switch'
  | 'select'
  | 'color'

export type AppDataColumnType =
  | 'text'
  | 'number'
  | 'boolean'
  | 'time'
  | 'status'
  | 'color'

export type AppDataOption = {
  label: string
  value: string | number | boolean
}

export type AppDataField = {
  key: string
  label: string
  type: AppDataFieldType
  placeholder?: string
  options?: AppDataOption[]
  wide?: boolean
  rows?: number
  min?: number
  max?: number
  step?: number
}

export type AppDataColumn = {
  key: string
  label: string
  type?: AppDataColumnType
  minWidth?: number
}

export type AppDataProfile = {
  columns: AppDataColumn[]
  fields: AppDataField[]
}

const enabledOptions: AppDataOption[] = [
  { label: '启用', value: true },
  { label: '停用', value: false },
]

const sortOptions: AppDataOption[] = [
  { label: '默认', value: -1 },
  { label: '手动排序', value: 0 },
  { label: '更新时间', value: 1 },
  { label: '书名', value: 2 },
  { label: '作者', value: 3 },
]

const contentTypeOptions: AppDataOption[] = [
  { label: 'JSON', value: 'application/json' },
  { label: '表单', value: 'application/x-www-form-urlencoded' },
  { label: '文本', value: 'text/plain' },
]

const downloadStatusOptions: AppDataOption[] = [
  { label: '等待', value: 'pending' },
  { label: '运行中', value: 'running' },
  { label: '已完成', value: 'done' },
  { label: '失败', value: 'failed' },
  { label: '暂停', value: 'paused' },
]

export const appDataProfiles: Record<string, AppDataProfile> = {
  bookGroups: {
    columns: [
      { key: 'groupName', label: '分组名', minWidth: 180 },
      { key: 'order', label: '排序', type: 'number', minWidth: 90 },
      { key: 'show', label: '显示', type: 'boolean', minWidth: 90 },
      { key: 'enableRefresh', label: '刷新', type: 'boolean', minWidth: 90 },
      { key: 'bookSort', label: '书籍排序', minWidth: 120 },
      { key: 'onlyUpdateRead', label: '仅读中', type: 'boolean', minWidth: 100 },
    ],
    fields: [
      { key: 'groupId', label: '分组 ID', type: 'number' },
      { key: 'groupName', label: '分组名', type: 'text' },
      { key: 'order', label: '排序', type: 'number' },
      { key: 'bookSort', label: '书籍排序', type: 'select', options: sortOptions },
      { key: 'show', label: '在书架显示', type: 'switch' },
      { key: 'enableRefresh', label: '允许刷新', type: 'switch' },
      { key: 'onlyUpdateRead', label: '只更新阅读中', type: 'switch' },
      { key: 'cover', label: '分组封面', type: 'text', wide: true },
    ],
  },
  bookmarks: {
    columns: [
      { key: 'bookName', label: '书名', minWidth: 170 },
      { key: 'chapterName', label: '章节', minWidth: 180 },
      { key: 'chapterIndex', label: '章节序号', type: 'number', minWidth: 100 },
      { key: 'chapterPos', label: '位置', type: 'number', minWidth: 90 },
      { key: 'content', label: '摘录', minWidth: 260 },
      { key: 'time', label: '时间', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'time', label: '时间戳', type: 'number' },
      { key: 'bookName', label: '书名', type: 'text' },
      { key: 'bookAuthor', label: '作者', type: 'text' },
      { key: 'bookUrl', label: '书籍 URL', type: 'text', wide: true },
      { key: 'chapterName', label: '章节名', type: 'text' },
      { key: 'chapterIndex', label: '章节序号', type: 'number' },
      { key: 'chapterPos', label: '章节位置', type: 'number' },
      { key: 'content', label: '书签摘录', type: 'textarea', rows: 4, wide: true },
      { key: 'bookText', label: '上下文', type: 'textarea', rows: 4, wide: true },
    ],
  },
  readRecords: {
    columns: [
      { key: 'bookName', label: '书名', minWidth: 180 },
      { key: 'chapterTitle', label: '章节', minWidth: 180 },
      { key: 'chapterIndex', label: '章节序号', type: 'number', minWidth: 100 },
      { key: 'chapterPos', label: '位置', type: 'number', minWidth: 90 },
      { key: 'readDuration', label: '阅读时长', minWidth: 110 },
      { key: 'readTime', label: '最近阅读', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'id', label: '记录 ID', type: 'text' },
      { key: 'bookName', label: '书名', type: 'text' },
      { key: 'bookAuthor', label: '作者', type: 'text' },
      { key: 'bookUrl', label: '书籍 URL', type: 'text', wide: true },
      { key: 'chapterTitle', label: '章节标题', type: 'text' },
      { key: 'chapterIndex', label: '章节序号', type: 'number' },
      { key: 'chapterPos', label: '章节位置', type: 'number' },
      { key: 'readDuration', label: '阅读时长', type: 'text' },
      { key: 'readTime', label: '最近阅读时间', type: 'number' },
    ],
  },
  httpTTS: {
    columns: [
      { key: 'name', label: '名称', minWidth: 170 },
      { key: 'url', label: '请求地址', minWidth: 260 },
      { key: 'contentType', label: '类型', minWidth: 150 },
      { key: 'enabledCookieJar', label: 'Cookie', type: 'boolean', minWidth: 100 },
      { key: 'concurrentRate', label: '并发', minWidth: 90 },
      { key: 'lastUpdateTime', label: '更新', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'id', label: 'TTS ID', type: 'text' },
      { key: 'name', label: '名称', type: 'text' },
      { key: 'url', label: '请求地址', type: 'textarea', rows: 3, wide: true },
      {
        key: 'contentType',
        label: 'Content-Type',
        type: 'select',
        options: contentTypeOptions,
      },
      { key: 'header', label: '请求头', type: 'textarea', rows: 4, wide: true },
      { key: 'loginUrl', label: '登录地址', type: 'text', wide: true },
      { key: 'loginUi', label: '登录 UI 脚本', type: 'textarea', rows: 4, wide: true },
      { key: 'jsLib', label: 'JS 库', type: 'textarea', rows: 4, wide: true },
      { key: 'enabledCookieJar', label: '启用 CookieJar', type: 'switch' },
      { key: 'concurrentRate', label: '并发速率', type: 'text' },
      { key: 'lastUpdateTime', label: '更新时间', type: 'number' },
    ],
  },
  cookies: {
    columns: [
      { key: 'url', label: '域名 / URL', minWidth: 220 },
      { key: 'sourceName', label: '来源', minWidth: 150 },
      { key: 'cookie', label: 'Cookie', minWidth: 320 },
      { key: 'lastUseTime', label: '最近使用', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'url', label: '域名 / URL', type: 'text', wide: true },
      { key: 'sourceName', label: '来源名称', type: 'text' },
      { key: 'cookie', label: 'Cookie', type: 'textarea', rows: 8, wide: true },
      { key: 'lastUseTime', label: '最近使用时间', type: 'number' },
    ],
  },
  dictRules: {
    columns: [
      { key: 'name', label: '名称', minWidth: 160 },
      { key: 'enabled', label: '启用', type: 'boolean', minWidth: 90 },
      { key: 'sortNumber', label: '排序', type: 'number', minWidth: 90 },
      { key: 'urlRule', label: '查询规则', minWidth: 260 },
      { key: 'showRule', label: '显示规则', minWidth: 220 },
    ],
    fields: [
      { key: 'name', label: '名称', type: 'text' },
      { key: 'enabled', label: '启用', type: 'switch' },
      { key: 'sortNumber', label: '排序', type: 'number' },
      { key: 'urlRule', label: '查询规则', type: 'textarea', rows: 4, wide: true },
      { key: 'showRule', label: '显示规则', type: 'textarea', rows: 4, wide: true },
      { key: 'header', label: '请求头', type: 'textarea', rows: 3, wide: true },
      { key: 'jsLib', label: 'JS 库', type: 'textarea', rows: 4, wide: true },
    ],
  },
  rssArticles: {
    columns: [
      { key: 'title', label: '标题', minWidth: 220 },
      { key: 'sourceName', label: '订阅源', minWidth: 150 },
      { key: 'group', label: '分组', minWidth: 120 },
      { key: 'read', label: '已读', type: 'boolean', minWidth: 90 },
      { key: 'starred', label: '收藏', type: 'boolean', minWidth: 90 },
      { key: 'pubDate', label: '发布时间', minWidth: 170 },
    ],
    fields: [
      { key: 'link', label: '文章链接', type: 'text', wide: true },
      { key: 'title', label: '标题', type: 'text', wide: true },
      { key: 'sourceName', label: '订阅源', type: 'text' },
      { key: 'group', label: '分组', type: 'text' },
      { key: 'author', label: '作者', type: 'text' },
      { key: 'pubDate', label: '发布时间', type: 'text' },
      { key: 'read', label: '已读', type: 'switch' },
      { key: 'starred', label: '收藏', type: 'switch' },
      { key: 'content', label: '正文缓存', type: 'textarea', rows: 6, wide: true },
    ],
  },
  rssReadRecords: {
    columns: [
      { key: 'title', label: '标题', minWidth: 220 },
      { key: 'sourceName', label: '订阅源', minWidth: 150 },
      { key: 'progress', label: '进度', minWidth: 100 },
      { key: 'readTime', label: '阅读时间', type: 'time', minWidth: 170 },
      { key: 'link', label: '链接', minWidth: 240 },
    ],
    fields: [
      { key: 'record', label: '记录 ID', type: 'text' },
      { key: 'title', label: '标题', type: 'text', wide: true },
      { key: 'link', label: '链接', type: 'text', wide: true },
      { key: 'sourceName', label: '订阅源', type: 'text' },
      { key: 'progress', label: '进度', type: 'text' },
      { key: 'readTime', label: '阅读时间', type: 'number' },
    ],
  },
  rssStars: {
    columns: [
      { key: 'title', label: '标题', minWidth: 220 },
      { key: 'sourceName', label: '订阅源', minWidth: 150 },
      { key: 'starTime', label: '收藏时间', type: 'time', minWidth: 170 },
      { key: 'summary', label: '摘要', minWidth: 260 },
      { key: 'link', label: '链接', minWidth: 240 },
    ],
    fields: [
      { key: 'link', label: '文章链接', type: 'text', wide: true },
      { key: 'title', label: '标题', type: 'text', wide: true },
      { key: 'sourceName', label: '订阅源', type: 'text' },
      { key: 'starTime', label: '收藏时间', type: 'number' },
      { key: 'summary', label: '摘要', type: 'textarea', rows: 4, wide: true },
    ],
  },
  cacheRecords: {
    columns: [
      { key: 'key', label: '键', minWidth: 220 },
      { key: 'type', label: '类型', minWidth: 120 },
      { key: 'sourceUrl', label: '来源 URL', minWidth: 220 },
      { key: 'updatedAt', label: '更新', type: 'time', minWidth: 170 },
      { key: 'value', label: '值', minWidth: 300 },
    ],
    fields: [
      { key: 'key', label: '键', type: 'text', wide: true },
      { key: 'type', label: '类型', type: 'text' },
      { key: 'sourceUrl', label: '来源 URL', type: 'text', wide: true },
      { key: 'expires', label: '过期时间', type: 'number' },
      { key: 'updatedAt', label: '更新时间', type: 'number' },
      { key: 'value', label: '值', type: 'textarea', rows: 8, wide: true },
    ],
  },
  downloadTasks: {
    columns: [
      { key: 'name', label: '名称', minWidth: 180 },
      { key: 'kind', label: '类型', minWidth: 110 },
      { key: 'status', label: '状态', type: 'status', minWidth: 110 },
      { key: 'progress', label: '进度', type: 'number', minWidth: 90 },
      { key: 'path', label: '路径', minWidth: 240 },
      { key: 'updatedAt', label: '更新', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'id', label: '任务 ID', type: 'text' },
      { key: 'name', label: '名称', type: 'text' },
      { key: 'kind', label: '类型', type: 'text' },
      { key: 'status', label: '状态', type: 'select', options: downloadStatusOptions },
      { key: 'progress', label: '进度', type: 'number', min: 0, max: 100 },
      { key: 'url', label: '下载 URL', type: 'text', wide: true },
      { key: 'path', label: '保存路径', type: 'text', wide: true },
      { key: 'error', label: '错误信息', type: 'textarea', rows: 3, wide: true },
      { key: 'updatedAt', label: '更新时间', type: 'number' },
    ],
  },
  themeConfigs: {
    columns: [
      { key: 'themeName', label: '主题名', minWidth: 170 },
      { key: 'isNight', label: '夜间', type: 'boolean', minWidth: 90 },
      { key: 'colorPrimary', label: '主色', type: 'color', minWidth: 130 },
      { key: 'colorAccent', label: '强调色', type: 'color', minWidth: 130 },
      { key: 'backgroundColor', label: '背景', type: 'color', minWidth: 130 },
      { key: 'updatedAt', label: '更新', type: 'time', minWidth: 170 },
    ],
    fields: [
      { key: 'themeName', label: '主题名', type: 'text' },
      { key: 'isNight', label: '夜间主题', type: 'switch' },
      { key: 'colorPrimary', label: '主色', type: 'color' },
      { key: 'colorAccent', label: '强调色', type: 'color' },
      { key: 'backgroundColor', label: '背景色', type: 'color' },
      { key: 'textColor', label: '正文色', type: 'color' },
      { key: 'cover', label: '封面资源', type: 'text', wide: true },
      { key: 'config', label: '扩展配置', type: 'textarea', rows: 6, wide: true },
      { key: 'updatedAt', label: '更新时间', type: 'number' },
    ],
  },
  readStyles: {
    columns: [
      { key: 'name', label: '样式名', minWidth: 170 },
      { key: 'bgStr', label: '背景', type: 'color', minWidth: 130 },
      { key: 'textColor', label: '文字', type: 'color', minWidth: 130 },
      { key: 'textSize', label: '字号', type: 'number', minWidth: 90 },
      { key: 'lineSpacingExtra', label: '行距', type: 'number', minWidth: 90 },
      { key: 'paragraphSpacing', label: '段距', type: 'number', minWidth: 90 },
    ],
    fields: [
      { key: 'name', label: '样式名', type: 'text' },
      { key: 'bgStr', label: '背景', type: 'color' },
      { key: 'textColor', label: '文字颜色', type: 'color' },
      { key: 'textSize', label: '字号', type: 'number', min: 10, max: 60 },
      { key: 'lineSpacingExtra', label: '行距', type: 'number', min: 0, max: 80 },
      { key: 'paragraphSpacing', label: '段距', type: 'number', min: 0, max: 80 },
      { key: 'paddingLeft', label: '左边距', type: 'number', min: 0, max: 120 },
      { key: 'paddingRight', label: '右边距', type: 'number', min: 0, max: 120 },
      { key: 'fontPath', label: '字体路径', type: 'text', wide: true },
      { key: 'bold', label: '粗体', type: 'switch' },
    ],
  },
}

export function createAppDataTemplate(kind: AppDataKind): AppDataItem {
  const now = Date.now()
  const base: AppDataItem = { [kind.primaryKey]: `${now}` }

  switch (kind.kind) {
    case 'bookGroups':
      return {
        groupId: now,
        groupName: '新分组',
        cover: '',
        order: 0,
        enableRefresh: true,
        show: true,
        bookSort: -1,
        onlyUpdateRead: false,
      }
    case 'bookmarks':
      return {
        time: now,
        bookName: '',
        bookAuthor: '',
        chapterIndex: 0,
        chapterPos: 0,
        chapterName: '',
        bookText: '',
        content: '',
      }
    case 'readRecords':
      return {
        id: `${now}`,
        bookName: '',
        bookAuthor: '',
        bookUrl: '',
        chapterTitle: '',
        chapterIndex: 0,
        chapterPos: 0,
        readDuration: '',
        readTime: now,
      }
    case 'httpTTS':
      return {
        id: `${now}`,
        name: 'HTTP TTS',
        url: '',
        contentType: 'application/json',
        concurrentRate: '0',
        header: '',
        loginUrl: '',
        loginUi: '',
        jsLib: '',
        enabledCookieJar: false,
        lastUpdateTime: now,
      }
    case 'cookies':
      return {
        url: 'https://example.com',
        sourceName: '',
        cookie: '',
        lastUseTime: now,
      }
    case 'dictRules':
      return {
        name: '字典规则',
        urlRule: '',
        showRule: '',
        header: '',
        jsLib: '',
        enabled: true,
        sortNumber: 0,
      }
    case 'rssArticles':
      return {
        link: `rss://article/${now}`,
        title: '',
        sourceName: '',
        group: '',
        author: '',
        pubDate: '',
        read: false,
        starred: false,
        content: '',
      }
    case 'rssReadRecords':
      return {
        record: `${now}`,
        title: '',
        link: '',
        sourceName: '',
        progress: '',
        readTime: now,
      }
    case 'rssStars':
      return {
        link: `rss://star/${now}`,
        title: '',
        sourceName: '',
        starTime: now,
        summary: '',
      }
    case 'cacheRecords':
      return {
        key: `cache-${now}`,
        type: 'source-variable',
        sourceUrl: '',
        expires: 0,
        updatedAt: now,
        value: '',
      }
    case 'downloadTasks':
      return {
        id: `${now}`,
        name: '',
        kind: 'book',
        status: 'pending',
        progress: 0,
        url: '',
        path: '',
        error: '',
        updatedAt: now,
      }
    case 'themeConfigs':
      return {
        themeName: '新主题',
        isNight: false,
        colorPrimary: '#795548',
        colorAccent: '#e53935',
        backgroundColor: '#f5f5f5',
        textColor: '#222222',
        cover: '',
        config: '',
        updatedAt: now,
      }
    case 'readStyles':
      return {
        name: '阅读样式',
        bgStr: '#EEEEEE',
        textColor: '#222222',
        textSize: 20,
        lineSpacingExtra: 12,
        paragraphSpacing: 2,
        paddingLeft: 16,
        paddingRight: 16,
        fontPath: '',
        bold: false,
      }
    default:
      return base
  }
}
