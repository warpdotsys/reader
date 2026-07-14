<template>
  <div class="feature-workbench">
    <aside class="workbench-sidebar">
      <div class="brand">
        <div class="brand-mark">L</div>
        <div>
          <h1>Legado Web</h1>
          <p>App Workbench</p>
        </div>
      </div>

      <nav class="main-nav" aria-label="Legado Web">
        <router-link
          v-for="item in navItems"
          :key="item.path"
          :to="item.path"
          class="nav-item"
        >
          <component :is="item.icon" />
          <span>{{ item.label }}</span>
        </router-link>
      </nav>

      <div class="kind-list" aria-label="App 数据集合">
        <button
          v-for="kind in kinds"
          :key="kind.kind"
          :class="{ active: selectedKindName === kind.kind }"
          type="button"
          @click="selectedKindName = kind.kind"
        >
          <span>{{ kind.label }}</span>
          <strong>{{ kind.count }}</strong>
        </button>
      </div>
    </aside>

    <main class="workbench-main">
      <section class="workbench-header">
        <div>
          <h2>App 功能工作台</h2>
          <p>把 Android App 的功能入口、设置项和 Room 数据资产集中到 Web 端管理。</p>
        </div>
        <div class="header-actions">
          <el-button :icon="Refresh" :loading="loading" @click="refreshAll">
            刷新
          </el-button>
          <el-button :icon="Download" @click="downloadCollection">
            导出集合
          </el-button>
          <el-button :icon="Upload" @click="dataInput?.click()">
            导入集合
          </el-button>
          <input
            ref="dataInput"
            class="visually-hidden"
            type="file"
            accept="application/json,.json"
            @change="importCollection"
          />
        </div>
      </section>

      <section class="workbench-stats">
        <div class="stat-item">
          <span>App 数据集合</span>
          <strong>{{ kinds.length }}</strong>
        </div>
        <div class="stat-item">
          <span>已落盘资产</span>
          <strong>{{ serverInfo?.counts.appData ?? 0 }}</strong>
        </div>
        <div class="stat-item">
          <span>Web 可用面</span>
          <strong>{{ usableFeatureCount }}</strong>
        </div>
        <div class="stat-item">
          <span>待接 Linux 能力</span>
          <strong>{{ linuxFeatureCount }}</strong>
        </div>
      </section>

      <section class="feature-grid">
        <article
          v-for="group in featureGroups"
          :key="group.name"
          class="feature-card"
        >
          <div class="feature-heading">
            <div class="feature-icon" :class="group.tone">
              <component :is="group.icon" />
            </div>
            <div>
              <h3>{{ group.name }}</h3>
              <p>{{ group.description }}</p>
            </div>
          </div>
          <div class="feature-status">
            <el-tag :type="statusTagType(group.status)" effect="plain">
              {{ group.status }}
            </el-tag>
            <span>{{ group.coverage }}</span>
          </div>
          <div class="feature-actions">
            <button
              v-for="action in group.actions"
              :key="action.label"
              type="button"
              @click="runFeatureAction(action)"
            >
              <component :is="action.icon" />
              <span>{{ action.label }}</span>
            </button>
          </div>
        </article>
      </section>

      <section class="data-vault">
        <div class="vault-heading">
          <div>
            <h3>{{ activeKind?.label || 'App 数据资产' }}</h3>
            <p>
              {{ activeKind?.description || '选择左侧集合后查看、编辑、导入或导出。' }}
            </p>
          </div>
          <div class="vault-actions">
            <el-tag v-if="activeKind" :type="statusTagType(activeKind.status)" effect="plain">
              {{ activeKind.status }}
            </el-tag>
            <el-button :icon="EditPen" type="primary" @click="openCreateEditor">
              新增
            </el-button>
            <el-button :icon="Files" @click="openReplaceEditor">
              批量替换
            </el-button>
          </div>
        </div>

        <div class="vault-toolbar">
          <el-input
            v-model="searchText"
            :prefix-icon="Search"
            clearable
            class="vault-search"
            placeholder="搜索当前集合"
          />
          <div class="vault-pills">
            <span>{{ filteredAppData.length }} / {{ appData.length }}</span>
            <span>{{ activeProfile ? '结构化编辑' : 'JSON 编辑' }}</span>
            <span>{{ activeKind?.primaryKey || '-' }}</span>
          </div>
        </div>

        <div class="profile-summary">
          <div v-for="metric in collectionMetrics" :key="metric.label">
            <span>{{ metric.label }}</span>
            <strong>{{ metric.value }}</strong>
          </div>
        </div>

        <el-table
          v-loading="loading"
          :data="filteredAppData"
          class="vault-table"
          height="430"
          :row-key="rowIdentity"
          empty-text="暂无数据，可新增或导入 JSON"
        >
          <el-table-column
            v-for="column in tableColumns"
            :key="column.key"
            :label="column.label"
            :min-width="column.minWidth || 170"
            show-overflow-tooltip
          >
            <template #default="{ row }">
              <el-tag
                v-if="column.type === 'boolean'"
                :type="truthy(row[column.key]) ? 'success' : 'info'"
                effect="plain"
              >
                {{ truthy(row[column.key]) ? '开启' : '关闭' }}
              </el-tag>
              <span v-else-if="column.type === 'color'" class="color-cell">
                <i :style="{ background: colorValue(row[column.key]) }" />
                <code>{{ previewValue(row[column.key]) || '-' }}</code>
              </span>
              <el-tag
                v-else-if="column.type === 'status'"
                :type="statusCellType(row[column.key])"
                effect="plain"
              >
                {{ previewValue(row[column.key]) || '未设置' }}
              </el-tag>
              <span v-else-if="column.type === 'time'" class="muted-cell">
                {{ formatTime(row[column.key]) }}
              </span>
              <code v-else>{{ previewValue(row[column.key]) }}</code>
            </template>
          </el-table-column>
          <el-table-column width="178" align="right" label="操作" fixed="right">
            <template #default="{ row }">
              <el-button :icon="EditPen" text @click="openEditEditor(row)" />
              <el-button :icon="Files" text @click="openJsonEditor(row)" />
              <el-button
                v-if="activeKind?.kind === 'downloadTasks' && row.status === 'done'"
                :icon="Download"
                text
                @click="downloadTaskFile(row)"
              />
              <el-button
                :icon="Delete"
                text
                type="danger"
                @click="removeItem(row)"
              />
            </template>
          </el-table-column>
        </el-table>
      </section>
    </main>

    <el-drawer v-model="editorOpen" :title="editorTitle" size="680px">
      <el-form
        v-if="useStructuredEditor"
        :model="formDraft"
        label-position="top"
        class="structured-form"
      >
        <div class="form-grid">
          <el-form-item
            v-for="field in activeProfile?.fields || []"
            :key="field.key"
            :label="field.label"
            :class="{ 'form-field-wide': field.wide || field.type === 'textarea' }"
          >
            <el-switch
              v-if="field.type === 'switch'"
              v-model="formDraft[field.key]"
              active-text="开"
              inactive-text="关"
            />
            <el-input-number
              v-else-if="field.type === 'number'"
              v-model="formDraft[field.key]"
              :min="field.min"
              :max="field.max"
              :step="field.step || 1"
              controls-position="right"
            />
            <el-select
              v-else-if="field.type === 'select'"
              v-model="formDraft[field.key]"
              clearable
              filterable
            >
              <el-option
                v-for="option in field.options || []"
                :key="String(option.value)"
                :label="option.label"
                :value="option.value"
              />
            </el-select>
            <el-color-picker
              v-else-if="field.type === 'color'"
              v-model="formDraft[field.key]"
              show-alpha
            />
            <el-input
              v-else-if="field.type === 'textarea'"
              v-model="formDraft[field.key]"
              type="textarea"
              :autosize="{ minRows: field.rows || 3, maxRows: 10 }"
              :placeholder="field.placeholder"
            />
            <el-input
              v-else
              v-model="formDraft[field.key]"
              clearable
              :placeholder="field.placeholder"
            />
          </el-form-item>
        </div>
      </el-form>
      <el-input
        v-else
        v-model="editorText"
        type="textarea"
        :rows="22"
        spellcheck="false"
        class="json-editor"
      />
      <template #footer>
        <el-button @click="editorOpen = false">取消</el-button>
        <el-button type="primary" :loading="saving" @click="saveEditor">
          保存
        </el-button>
      </template>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import type { Component } from 'vue'
import {
  Collection,
  Connection,
  DataAnalysis,
  Delete,
  DocumentCopy,
  Download,
  EditPen,
  Files,
  Link,
  Reading,
  Refresh,
  Search,
  SetUp,
  Setting,
  Upload,
  VideoPlay,
} from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import API from '@api'
import type {
  AppDataItem,
  AppDataKind,
  LeagdoApiResponse,
  ServerInfo,
} from '@api'
import {
  appDataProfiles,
  createAppDataTemplate,
} from './featureWorkbenchProfiles'
import type {
  AppDataColumn as WorkbenchColumn,
  AppDataProfile,
} from './featureWorkbenchProfiles'

type ResponseBox<T> = {
  data: LeagdoApiResponse<T>
}

type NavItem = {
  label: string
  path: string
  icon: Component
}

type FeatureAction = {
  label: string
  icon: Component
  route?: string
  query?: Record<string, string>
  kind?: string
}

type FeatureGroup = {
  name: string
  description: string
  coverage: string
  status: string
  tone: string
  icon: Component
  actions: FeatureAction[]
}

const unwrap = <T,>(response: ResponseBox<T>): T => {
  if (!response.data.isSuccess) {
    throw new Error(response.data.errorMsg || '请求失败')
  }
  return response.data.data
}

const navItems: NavItem[] = [
  { label: '书架', path: '/', icon: Reading },
  { label: '书源', path: '/bookSource', icon: Collection },
  { label: '订阅源', path: '/rssSource', icon: Link },
  { label: '功能', path: '/features', icon: SetUp },
  { label: '设置', path: '/settings', icon: Setting },
  { label: '控制台', path: '/server', icon: DataAnalysis },
]

const featureGroups: FeatureGroup[] = [
  {
    name: '书架与阅读',
    description: '书架、分组、书签、阅读记录、阅读样式和本地 TXT。',
    coverage: '核心链路已在 Web 端可用',
    status: 'Web 可用',
    tone: 'tone-green',
    icon: Reading,
    actions: [
      { label: '打开书架', icon: Reading, route: '/' },
      { label: '书籍分组', icon: Collection, kind: 'bookGroups' },
      { label: '书签摘录', icon: DocumentCopy, kind: 'bookmarks' },
      { label: '阅读样式', icon: Setting, kind: 'readStyles' },
    ],
  },
  {
    name: '书源与订阅源',
    description: '书源/RSS 源编辑、导入导出、调试入口、源变量和 Cookie。',
    coverage: '管理 UI 已可用，规则执行待抽 JVM core',
    status: '兼容入口',
    tone: 'tone-blue',
    icon: Collection,
    actions: [
      { label: '书源', icon: Collection, route: '/bookSource' },
      { label: '订阅源', icon: Link, route: '/rssSource' },
      { label: 'Cookie', icon: Connection, kind: 'cookies' },
      { label: '缓存变量', icon: Files, kind: 'cacheRecords' },
    ],
  },
  {
    name: '规则工具',
    description: '替换规则、TXT 目录规则、字典规则和检查源配置。',
    coverage: '替换/TXT/划词字典均可执行，JavaScript 规则降级为可读正文。',
    status: 'Web 可用',
    tone: 'tone-amber',
    icon: Search,
    actions: [
      { label: '控制台规则', icon: SetUp, route: '/server', query: { tab: 'replace' } },
      { label: '字典规则', icon: Search, kind: 'dictRules' },
      { label: '源检查配置', icon: Files, route: '/settings', query: { section: 'network' } },
    ],
  },
  {
    name: '备份与同步',
    description: '整包备份、WebDAV 偏好、同步进度和恢复忽略项。',
    coverage: '本地导入导出可用，WebDAV 协议待接入',
    status: '部分可用',
    tone: 'tone-cyan',
    icon: Connection,
    actions: [
      { label: '备份设置', icon: Setting, route: '/settings', query: { section: 'backup' } },
      { label: '服务导出', icon: Download, route: '/server', query: { tab: 'coverage' } },
      { label: 'RSS 收藏', icon: Link, kind: 'rssStars' },
    ],
  },
  {
    name: '朗读与媒体',
    description: 'HTTP TTS、朗读按键、音频焦点、漫画和视频配置。',
    coverage: '浏览器系统朗读、HTTP TTS 音频代理和服务端离线书籍缓存可用，漫画继续实现。',
    status: '部分可用',
    tone: 'tone-violet',
    icon: VideoPlay,
    actions: [
      { label: 'HTTP TTS', icon: VideoPlay, kind: 'httpTTS' },
      { label: '漫画设置', icon: Setting, route: '/settings', query: { section: 'media' } },
      { label: '下载任务', icon: Download, kind: 'downloadTasks' },
    ],
  },
  {
    name: '维护与系统',
    description: '缓存、日志、主题方案、Web 端口和升级渠道。',
    coverage: '服务端可持久化，系统集成按 Linux 化推进',
    status: '配置保留',
    tone: 'tone-red',
    icon: Files,
    actions: [
      { label: '主题方案', icon: Setting, kind: 'themeConfigs' },
      { label: '缓存记录', icon: Files, kind: 'cacheRecords' },
      { label: '高级设置', icon: SetUp, route: '/settings', query: { section: 'maintenance' } },
    ],
  },
]

const router = useRouter()
const route = useRoute()
const kinds = ref<AppDataKind[]>([])
const appData = ref<AppDataItem[]>([])
const selectedKindName = ref('')
const serverInfo = ref<ServerInfo>()
const loading = ref(false)
const saving = ref(false)
const editorOpen = ref(false)
const editorText = ref('')
const editorTitle = ref('')
const editorMode = ref<'single' | 'replace'>('single')
const rawEditor = ref(false)
const searchText = ref('')
const formDraft = ref<Record<string, any>>({})
const dataInput = ref<HTMLInputElement>()

const activeKind = computed(() =>
  kinds.value.find(kind => kind.kind === selectedKindName.value),
)

const activeProfile = computed<AppDataProfile | undefined>(() => {
  const kind = activeKind.value?.kind
  return kind ? appDataProfiles[kind] : undefined
})

const tableColumns = computed<WorkbenchColumn[]>(() => {
  if (activeProfile.value) return activeProfile.value.columns
  const key = activeKind.value?.primaryKey
  const columns = new Set<string>()
  if (key) columns.add(key)
  appData.value.slice(0, 20).forEach(item => {
    Object.keys(item).forEach(column => columns.add(column))
  })
  return Array.from(columns)
    .slice(0, 6)
    .map(column => ({ key: column, label: column, minWidth: 170 }))
})

const filteredAppData = computed(() => {
  const query = searchText.value.trim().toLowerCase()
  if (!query) return appData.value
  return appData.value.filter(item =>
    JSON.stringify(item).toLowerCase().includes(query),
  )
})

const useStructuredEditor = computed(
  () => editorMode.value === 'single' && !rawEditor.value && !!activeProfile.value,
)

const collectionMetrics = computed(() => {
  const enabled = appData.value.filter(item =>
    ['enabled', 'enable', 'show', 'isEnabled'].some(key => truthy(item[key])),
  ).length
  const disabled = appData.value.filter(item =>
    ['enabled', 'enable', 'show', 'isEnabled'].some(
      key => item[key] !== undefined && !truthy(item[key]),
    ),
  ).length
  return [
    { label: '当前集合', value: String(appData.value.length) },
    { label: '可见/启用', value: String(enabled) },
    { label: '关闭/隐藏', value: String(disabled) },
    { label: '状态', value: activeKind.value?.status || '-' },
  ]
})

const usableFeatureCount = computed(
  () => featureGroups.filter(group => group.status === 'Web 可用').length,
)
const linuxFeatureCount = computed(
  () => featureGroups.filter(group => group.status.includes('Linux')).length,
)

watch(
  () => route.query.kind,
  value => {
    const nextKind = requestedKind(value)
    if (
      nextKind &&
      kinds.value.some(kind => kind.kind === nextKind) &&
      nextKind !== selectedKindName.value
    ) {
      selectedKindName.value = nextKind
    }
  },
)

watch(selectedKindName, async value => {
  if (value) {
    searchText.value = typeof route.query.search === 'string' ? route.query.search : ''
    await loadAppData()
    if (route.query.kind !== value) {
      await router.replace({ query: { ...route.query, kind: value } })
    }
  }
})

async function refreshAll() {
  loading.value = true
  try {
    await Promise.all([loadServerInfo(), loadKinds()])
const queryKind = requestedKind()
    if (queryKind && kinds.value.some(kind => kind.kind === queryKind)) {
      selectedKindName.value = queryKind
    } else if (!selectedKindName.value && kinds.value.length > 0) {
      selectedKindName.value = kinds.value[0].kind
    } else {
      await loadAppData()
    }
    openTextDraftFromRoute()
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '刷新失败')
  } finally {
    loading.value = false
  }
}

async function loadServerInfo() {
  serverInfo.value = unwrap(await API.getServerInfo())
}

async function loadKinds() {
  kinds.value = unwrap(await API.getAppDataKinds())
}

async function loadAppData() {
  if (!selectedKindName.value) return
  appData.value = unwrap(await API.getAppData(selectedKindName.value))
}

function statusTagType(status: string) {
  if (status === 'Web 可用') return 'success'
  if (status === 'Linux 需实现') return 'warning'
  if (status === '部分可用' || status === '兼容入口') return 'warning'
  return 'info'
}

function runFeatureAction(action: FeatureAction) {
  if (action.route) {
    void router.push({ path: action.route, query: action.query })
    return
  }
  if (action.kind) {
    selectedKindName.value = action.kind
    document.querySelector('.data-vault')?.scrollIntoView({ behavior: 'smooth' })
  }
}

function requestedKind(value: unknown = route.query.kind) {
  return typeof value === 'string' ? value : ''
}

function openTextDraftFromRoute() {
  const text = typeof route.query.draftText === 'string' ? route.query.draftText.trim() : ''
  const kind = activeKind.value
  if (!text || kind?.kind !== 'bookmarks') return
  editorMode.value = 'single'
  rawEditor.value = false
  editorTitle.value = '新增 书签摘录'
  formDraft.value = {
    ...createAppDataTemplate(kind),
    content: text,
    bookText: text,
    bookName: typeof route.query.bookName === 'string' ? route.query.bookName : '',
    bookAuthor: typeof route.query.bookAuthor === 'string' ? route.query.bookAuthor : '',
    chapterName: typeof route.query.chapterName === 'string' ? route.query.chapterName : '',
    chapterIndex: Number(route.query.chapterIndex || 0),
  }
  editorText.value = JSON.stringify(formDraft.value, null, 2)
  editorOpen.value = true
  void router.replace({ query: { kind: 'bookmarks' } })
}

function previewValue(value: unknown) {
  if (value == null) return ''
  const text = typeof value === 'object' ? JSON.stringify(value) : String(value)
  return text.length > 88 ? `${text.slice(0, 88)}...` : text
}

function truthy(value: unknown) {
  return value === true || value === 'true' || value === 1 || value === '1'
}

function colorValue(value: unknown) {
  const text = typeof value === 'string' ? value : ''
  return /^#|rgb|hsl/i.test(text) ? text : '#e2e8f0'
}

function statusCellType(value: unknown) {
  const status = String(value ?? '').toLowerCase()
  if (['done', 'success', 'finished', 'complete', '已完成'].some(item => status.includes(item))) {
    return 'success'
  }
  if (['fail', 'error', '失败', '错误'].some(item => status.includes(item))) {
    return 'danger'
  }
  if (['running', 'pending', 'wait', '等待', '运行'].some(item => status.includes(item))) {
    return 'warning'
  }
  return 'info'
}

function formatTime(value: unknown) {
  if (value == null || value === '') return '-'
  const numeric =
    typeof value === 'number'
      ? value
      : typeof value === 'string' && /^\d+$/.test(value)
        ? Number(value)
        : undefined
  if (numeric && numeric > 10_000_000_000) {
    return new Date(numeric).toLocaleString(document.documentElement.lang || undefined)
  }
  if (numeric && numeric > 1_000_000_000) {
    return new Date(numeric * 1000).toLocaleString(document.documentElement.lang || undefined)
  }
  return String(value)
}

function rowIdentity(row: AppDataItem) {
  const key = activeKind.value?.primaryKey
  return String((key && row[key]) || row.id || row.name || JSON.stringify(row))
}

function deepCopy<T>(value: T): T {
  return JSON.parse(JSON.stringify(value)) as T
}

function stripEmptyFields(item: Record<string, any>): AppDataItem {
  const cleaned: AppDataItem = {}
  Object.entries(item).forEach(([key, value]) => {
    if (value !== undefined) cleaned[key] = value
  })
  return cleaned
}

function openCreateEditor() {
  const kind = activeKind.value
  if (!kind) return
  editorMode.value = 'single'
  rawEditor.value = false
  editorTitle.value = `新增 ${kind.label}`
  formDraft.value = { ...createAppDataTemplate(kind) }
  editorText.value = JSON.stringify(formDraft.value, null, 2)
  editorOpen.value = true
}

function openEditEditor(row: AppDataItem) {
  const kind = activeKind.value
  if (!kind) return
  editorMode.value = 'single'
  rawEditor.value = false
  editorTitle.value = `编辑 ${kind.label}`
  formDraft.value = { ...createAppDataTemplate(kind), ...deepCopy(row) }
  editorText.value = JSON.stringify(formDraft.value, null, 2)
  editorOpen.value = true
}

function openJsonEditor(row: AppDataItem) {
  const kind = activeKind.value
  if (!kind) return
  editorMode.value = 'single'
  rawEditor.value = true
  editorTitle.value = `JSON 编辑 ${kind.label}`
  editorText.value = JSON.stringify(row, null, 2)
  editorOpen.value = true
}

function openReplaceEditor() {
  const kind = activeKind.value
  if (!kind) return
  editorMode.value = 'replace'
  rawEditor.value = true
  editorTitle.value = `批量替换 ${kind.label}`
  editorText.value = JSON.stringify(appData.value, null, 2)
  editorOpen.value = true
}

async function saveEditor() {
  const kind = activeKind.value
  if (!kind) return
  saving.value = true
  try {
    const data = useStructuredEditor.value
      ? stripEmptyFields(formDraft.value)
      : (JSON.parse(editorText.value) as AppDataItem | AppDataItem[])
    if (editorMode.value === 'replace' && !Array.isArray(data)) {
      throw new Error('批量替换需要 JSON 数组')
    }
    appData.value = unwrap(
      await API.saveAppData(
        kind.kind,
        data,
        editorMode.value === 'replace' ? 'replace' : 'upsert',
      ),
    )
    await Promise.all([loadKinds(), loadServerInfo()])
    editorOpen.value = false
    ElMessage.success('已保存')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存失败')
  } finally {
    saving.value = false
  }
}

async function removeItem(row: AppDataItem) {
  const kind = activeKind.value
  if (!kind) return
  try {
    await ElMessageBox.confirm(
      `删除 ${kind.label}：${previewValue(row[kind.primaryKey])}？`,
      '删除数据',
      {
        type: 'warning',
        confirmButtonText: '删除',
        cancelButtonText: '取消',
      },
    )
    appData.value = unwrap(await API.deleteAppData(kind.kind, row))
    await Promise.all([loadKinds(), loadServerInfo()])
    ElMessage.success('已删除')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '删除失败')
    }
  }
}

function downloadCollection() {
  const kind = activeKind.value
  if (!kind) return
  downloadJson(appData.value, `legado-${kind.kind}-${new Date().toISOString()}.json`)
}

async function importCollection(event: Event) {
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  const kind = activeKind.value
  if (!file || !kind) return
  try {
    const data = JSON.parse(await file.text()) as AppDataItem[]
    if (!Array.isArray(data)) throw new Error('导入集合需要 JSON 数组')
    await ElMessageBox.confirm(`导入会替换 ${kind.label} 当前集合。`, '导入集合', {
      type: 'warning',
      confirmButtonText: '导入',
      cancelButtonText: '取消',
    })
    appData.value = unwrap(await API.saveAppData(kind.kind, data, 'replace'))
    await Promise.all([loadKinds(), loadServerInfo()])
    ElMessage.success('导入完成')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '导入失败')
    }
  } finally {
    input.value = ''
  }
}

function downloadJson(data: unknown, fileName: string) {
  const blob = new Blob([JSON.stringify(data, null, 2)], {
    type: 'application/json;charset=utf-8',
  })
  const url = URL.createObjectURL(blob)
  const anchor = document.createElement('a')
  anchor.href = url
  anchor.download = fileName.replace(/[:.]/g, '-')
  anchor.click()
  URL.revokeObjectURL(url)
}

async function downloadTaskFile(task: AppDataItem) {
  const id = String(task.id || '')
  if (!id) return
  try {
    const response = await API.getDownloadTaskFile(id)
    const name = `${String(task.name || 'book').replace(/[\\/:*?"<>|]/g, '_')}.txt`
    const url = URL.createObjectURL(response.data)
    const anchor = document.createElement('a')
    anchor.href = url
    anchor.download = name
    anchor.click()
    URL.revokeObjectURL(url)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '下载离线文件失败')
  }
}

onMounted(() => {
  document.title = 'App 功能工作台'
  refreshAll()
})
</script>

<style scoped lang="scss">
.feature-workbench {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 300px minmax(0, 1fr);
  background: #f5f7fa;
  color: #1f2937;
}

.workbench-sidebar {
  position: sticky;
  top: 0;
  height: 100vh;
  padding: 28px 22px;
  box-sizing: border-box;
  background: #101820;
  color: #e7edf3;
  display: flex;
  flex-direction: column;
  gap: 22px;
  overflow: auto;
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;

  h1 {
    margin: 0;
    font-size: 20px;
    line-height: 1.2;
    font-weight: 700;
  }

  p {
    margin: 4px 0 0;
    font-size: 12px;
    color: #94a3b8;
  }
}

.brand-mark {
  width: 40px;
  height: 40px;
  display: grid;
  place-items: center;
  border-radius: 8px;
  background: #1aa37a;
  color: #fff;
  font-weight: 800;
}

.main-nav,
.kind-list {
  display: grid;
  gap: 8px;
}

.nav-item,
.kind-list button {
  min-height: 42px;
  padding: 0 12px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
}

.nav-item {
  color: #cbd5e1;
  text-decoration: none;

  svg {
    width: 18px;
    height: 18px;
  }

  &:hover,
  &.router-link-active {
    color: #fff;
    background: rgba(255, 255, 255, 0.1);
  }
}

.kind-list {
  padding-top: 12px;
  border-top: 1px solid rgba(148, 163, 184, 0.18);

  button {
    justify-content: space-between;
    border: 0;
    background: transparent;
    color: #94a3b8;
    text-align: left;
    cursor: pointer;

    strong {
      color: #cbd5e1;
      font-size: 12px;
    }

    &.active,
    &:hover {
      color: #fff;
      background: rgba(255, 255, 255, 0.1);
    }
  }
}

.workbench-main {
  min-width: 0;
  padding: 30px;
}

.workbench-header,
.workbench-stats,
.data-vault,
.feature-card {
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 18px 42px rgba(15, 23, 42, 0.06);
}

.workbench-header {
  min-height: 96px;
  padding: 22px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;

  h2 {
    margin: 0;
    font-size: 26px;
    line-height: 1.2;
    color: #111827;
  }

  p {
    margin: 8px 0 0;
    color: #64748b;
  }
}

.header-actions,
.vault-actions {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  align-items: center;
  gap: 10px;
}

.workbench-stats {
  margin-top: 18px;
  padding: 14px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;
}

.stat-item {
  min-height: 76px;
  padding: 14px 16px;
  border-radius: 8px;
  background: #f8fafc;
  display: grid;
  align-content: center;
  gap: 6px;

  span {
    font-size: 13px;
    color: #64748b;
  }

  strong {
    font-size: 24px;
    line-height: 1;
    color: #111827;
  }
}

.feature-grid {
  margin-top: 18px;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.feature-card {
  padding: 18px;
  display: grid;
  gap: 16px;
}

.feature-heading {
  display: flex;
  gap: 12px;
  align-items: flex-start;

  h3 {
    margin: 0;
    font-size: 17px;
    color: #111827;
  }

  p {
    margin: 5px 0 0;
    color: #64748b;
    font-size: 13px;
    line-height: 1.5;
  }
}

.feature-icon {
  flex: 0 0 auto;
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: grid;
  place-items: center;

  svg {
    width: 21px;
    height: 21px;
  }
}

.tone-green {
  background: #e7f7ef;
  color: #12805c;
}

.tone-blue {
  background: #e8f1ff;
  color: #2563eb;
}

.tone-amber {
  background: #fff4d8;
  color: #b7791f;
}

.tone-cyan {
  background: #e6f7fb;
  color: #0e7490;
}

.tone-violet {
  background: #f0ebff;
  color: #6d28d9;
}

.tone-red {
  background: #ffe8e8;
  color: #c2410c;
}

.feature-status {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  color: #64748b;
  font-size: 12px;
}

.feature-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;

  button {
    min-height: 34px;
    padding: 0 10px;
    border: 1px solid #e3e8ef;
    border-radius: 8px;
    background: #f8fafc;
    color: #334155;
    display: flex;
    align-items: center;
    gap: 7px;
    cursor: pointer;

    svg {
      width: 15px;
      height: 15px;
      color: #0f766e;
    }

    span {
      font-size: 12px;
    }

    &:hover {
      border-color: #99f6e4;
      background: #f0fdfa;
    }
  }
}

.data-vault {
  margin-top: 18px;
  padding: 20px;
}

.vault-heading {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;
  margin-bottom: 16px;

  h3 {
    margin: 0;
    font-size: 20px;
    color: #111827;
  }

  p {
    margin: 6px 0 0;
    font-size: 13px;
    color: #64748b;
  }
}

.vault-toolbar {
  margin-bottom: 14px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 14px;
}

.vault-search {
  max-width: 360px;
}

.vault-pills {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 8px;

  span {
    min-height: 30px;
    padding: 0 10px;
    border: 1px solid #dbe4ee;
    border-radius: 8px;
    background: #f8fafc;
    color: #475569;
    display: inline-flex;
    align-items: center;
    font-size: 12px;
    font-weight: 600;
  }
}

.profile-summary {
  margin-bottom: 14px;
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;

  div {
    min-height: 58px;
    padding: 10px 12px;
    border: 1px solid #e3e8ef;
    border-radius: 8px;
    background: #fbfdff;
    display: grid;
    gap: 5px;
  }

  span {
    color: #64748b;
    font-size: 12px;
  }

  strong {
    color: #111827;
    font-size: 15px;
    line-height: 1.3;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.vault-table {
  width: 100%;

  code {
    padding: 2px 5px;
    border-radius: 4px;
    background: #f1f5f9;
    color: #334155;
    font-family: Consolas, Monaco, monospace;
    font-size: 12px;
  }
}

.color-cell {
  display: inline-flex;
  align-items: center;
  gap: 7px;

  i {
    width: 16px;
    height: 16px;
    border: 1px solid #cbd5e1;
    border-radius: 50%;
    box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.5);
  }
}

.muted-cell {
  color: #64748b;
  font-size: 13px;
}

.structured-form {
  padding-right: 4px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 2px 16px;

  :deep(.el-input-number),
  :deep(.el-select) {
    width: 100%;
  }
}

.form-field-wide {
  grid-column: 1 / -1;
}

.json-editor {
  :deep(textarea) {
    font-family: Consolas, Monaco, monospace;
    font-size: 13px;
    line-height: 1.6;
  }
}

.visually-hidden {
  position: fixed;
  width: 1px;
  height: 1px;
  opacity: 0;
  pointer-events: none;
}

@media (max-width: 1180px) {
  .feature-workbench {
    grid-template-columns: 1fr;
  }

  .workbench-sidebar {
    position: static;
    height: auto;
  }

  .main-nav,
  .kind-list {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }

  .feature-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .profile-summary {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 760px) {
  .workbench-main,
  .workbench-sidebar {
    padding: 18px;
  }

  .workbench-header,
  .vault-heading,
  .vault-toolbar {
    align-items: stretch;
    flex-direction: column;
  }

  .header-actions,
  .vault-actions,
  .vault-pills {
    justify-content: flex-start;
  }

  .vault-search {
    max-width: none;
  }

  .main-nav,
  .kind-list {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .workbench-stats,
  .feature-grid,
  .profile-summary,
  .form-grid {
    grid-template-columns: 1fr;
  }

  .kind-list {
    max-height: 236px;
    overflow: auto;
  }
}
</style>
