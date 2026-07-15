<template>
  <div class="server-console">
    <aside class="console-sidebar">
      <div class="brand">
        <div class="brand-mark">L</div>
        <div>
          <h1>Legado Web</h1>
          <p>Linux Server</p>
        </div>
      </div>

      <nav class="console-nav" aria-label="Legado Web">
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

      <div class="endpoint-panel">
        <div class="endpoint-title">
          <el-icon><Connection /></el-icon>
          <span>服务入口</span>
        </div>
        <button class="endpoint-line" type="button" @click="copyEndpoint(httpEndpoint)">
          <span>HTTP</span>
          <strong>{{ httpEndpoint }}</strong>
        </button>
        <button class="endpoint-line" type="button" @click="copyEndpoint(wsEndpoint)">
          <span>WS</span>
          <strong>{{ wsEndpoint }}</strong>
        </button>
      </div>
    </aside>

    <main class="console-main">
      <section class="status-band">
        <div class="status-copy">
          <h2>服务端控制台</h2>
          <p>{{ serverInfo?.service || 'legado-server' }}</p>
        </div>
        <div class="status-actions">
          <el-button :icon="Refresh" :loading="loading" @click="refreshAll">
            刷新
          </el-button>
          <el-button :icon="Connection" :loading="checkingUpdates" @click="checkForUpdates">
            检查更新
          </el-button>
          <el-button :icon="Download" type="primary" @click="downloadBackup">
            导出
          </el-button>
          <el-button :icon="Download" :loading="exportingBooks" @click="exportShelfBooks">
            导出书架
          </el-button>
          <el-button :icon="Files" :loading="creatingBackup" @click="createServerBackup">
            创建备份
          </el-button>
          <el-button :icon="Upload" @click="backupInput?.click()">
            导入
          </el-button>
          <input
            ref="backupInput"
            class="visually-hidden"
            type="file"
            accept="application/json,.json"
            @change="importBackup"
          />
        </div>
      </section>

      <section class="metric-grid" aria-label="服务数据">
        <div v-for="card in countCards" :key="card.key" class="metric-card">
          <div class="metric-icon" :class="card.tone">
            <component :is="card.icon" />
          </div>
          <div>
            <span>{{ card.label }}</span>
            <strong>{{ card.value }}</strong>
          </div>
        </div>
      </section>

      <section class="workspace-grid">
        <article class="panel local-book-panel">
          <div class="panel-heading">
            <div>
              <h3>本地书籍</h3>
              <p>TXT / EPUB 入库</p>
            </div>
            <el-button
              :icon="FolderAdd"
              :loading="uploadingLocalBook"
              type="primary"
              @click="txtInput?.click()"
            >
              上传电子书
            </el-button>
            <input
              ref="txtInput"
              class="visually-hidden"
              type="file"
              accept=".txt,.epub,text/plain,application/epub+zip"
              multiple
              @change="uploadLocalBook"
            />
          </div>
          <div class="local-book-body">
            <div class="local-book-figure">
              <el-icon><Reading /></el-icon>
            </div>
            <div class="local-book-meta">
              <strong>{{ lastLocalBook?.name || '等待文件' }}</strong>
              <span>{{ lastLocalBook?.originName || 'TXT 按目录规则拆章，EPUB 保留书内目录' }}</span>
            </div>
          </div>
        </article>

        <article class="panel data-dir-panel">
          <div class="panel-heading">
            <div>
              <h3>数据目录</h3>
              <p>{{ serverInfo?.dataDir || '-' }}</p>
            </div>
            <el-tag :type="serverInfo ? 'success' : 'warning'" effect="plain">
              {{ serverInfo ? '在线' : '等待连接' }}
            </el-tag>
          </div>
          <div class="data-actions">
            <button
              v-for="item in quickActions"
              :key="item.label"
              class="quick-action"
              type="button"
              @click="item.action"
            >
              <component :is="item.icon" />
              <span>{{ item.label }}</span>
            </button>
          </div>
        </article>

        <article class="panel backup-panel">
          <div class="panel-heading">
            <div>
              <h3>备份库</h3>
              <p>{{ backupDirHint }}</p>
            </div>
            <el-tag type="success" effect="plain">
              {{ backups.length }} 个快照
            </el-tag>
          </div>
          <div class="backup-actions">
            <el-button
              :icon="Files"
              type="primary"
              :loading="creatingBackup"
              @click="createServerBackup"
            >
              创建快照
            </el-button>
            <el-button :icon="Refresh" :loading="loading" @click="loadBackups">
              刷新
            </el-button>
          </div>
          <div v-if="backups.length" class="backup-list">
            <div v-for="backup in backups" :key="backup.fileName" class="backup-row">
              <div>
                <strong>{{ backup.fileName }}</strong>
                <span>{{ formatTime(backup.modifiedTime) }} · {{ formatBytes(backup.size) }}</span>
              </div>
              <div class="backup-row-actions">
                <el-button
                  :icon="Upload"
                  text
                  :loading="restoringBackup === backup.fileName"
                  @click="restoreServerBackup(backup)"
                />
                <el-button
                  :icon="Delete"
                  text
                  type="danger"
                  @click="removeServerBackup(backup)"
                />
              </div>
            </div>
          </div>
          <div v-else class="backup-empty">
            <el-icon><Files /></el-icon>
            <span>暂无本地备份快照</span>
          </div>
        </article>
      </section>

      <section class="management-panel">
        <el-tabs v-model="activeTab" class="console-tabs">
          <el-tab-pane label="TXT 目录规则" name="toc">
            <div class="table-toolbar">
              <el-input
                v-model="tocKeyword"
                :prefix-icon="Search"
                clearable
                placeholder="筛选规则"
              />
              <el-button :icon="EditPen" type="primary" @click="openTocEditor()">
                新建规则
              </el-button>
            </div>
            <el-table
              v-loading="loading"
              :data="filteredTocRules"
              class="console-table"
              height="360"
            >
              <el-table-column min-width="190" prop="name" label="名称" />
              <el-table-column
                min-width="340"
                prop="rule"
                label="正则"
                show-overflow-tooltip
              />
              <el-table-column width="88" label="启用">
                <template #default="{ row }">
                  <el-switch
                    :model-value="row.enable !== false"
                    @change="toggleTocRule(row)"
                  />
                </template>
              </el-table-column>
              <el-table-column width="140" align="right" label="操作">
                <template #default="{ row }">
                  <el-button :icon="EditPen" text @click="openTocEditor(row)" />
                  <el-button
                    :icon="Delete"
                    text
                    type="danger"
                    @click="removeTocRule(row)"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>

          <el-tab-pane label="替换规则" name="replace">
            <div class="table-toolbar">
              <el-input
                v-model="replaceKeyword"
                :prefix-icon="Search"
                clearable
                placeholder="筛选规则"
              />
              <el-button
                :icon="EditPen"
                type="primary"
                @click="openReplaceEditor()"
              >
                新建规则
              </el-button>
            </div>
            <el-table
              v-loading="loading"
              :data="filteredReplaceRules"
              class="console-table"
              height="360"
            >
              <el-table-column min-width="180" prop="name" label="名称" />
              <el-table-column min-width="280" label="匹配" show-overflow-tooltip>
                <template #default="{ row }">
                  <code>{{ row.pattern }}</code>
                </template>
              </el-table-column>
              <el-table-column min-width="160" prop="replacement" label="替换为" />
              <el-table-column width="88" label="启用">
                <template #default="{ row }">
                  <el-switch
                    :model-value="row.isEnabled !== false"
                    @change="toggleReplaceRule(row)"
                  />
                </template>
              </el-table-column>
              <el-table-column width="140" align="right" label="操作">
                <template #default="{ row }">
                  <el-button
                    :icon="EditPen"
                    text
                    @click="openReplaceEditor(row)"
                  />
                  <el-button
                    :icon="Delete"
                    text
                    type="danger"
                    @click="removeReplaceRule(row)"
                  />
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>

          <el-tab-pane label="源检查" name="sourceCheck">
            <div class="source-check-panel">
              <div class="source-check-toolbar">
                <el-radio-group v-model="sourceCheckScope" size="small">
                  <el-radio-button label="all">全部</el-radio-button>
                  <el-radio-button label="bookSources">书源</el-radio-button>
                  <el-radio-button label="rssSources">订阅源</el-radio-button>
                </el-radio-group>

                <el-switch
                  v-model="sourceCheckOnlyEnabled"
                  active-text="仅启用"
                  inactive-text="包含停用"
                />

                <label class="compact-field">
                  <span>超时</span>
                  <el-input-number
                    v-model="sourceCheckTimeout"
                    :min="1000"
                    :max="15000"
                    :step="1000"
                    controls-position="right"
                  />
                </label>

                <label class="compact-field">
                  <span>数量</span>
                  <el-input-number
                    v-model="sourceCheckLimit"
                    :min="1"
                    :max="300"
                    controls-position="right"
                  />
                </label>

                <div class="source-check-actions">
                  <el-button
                    :icon="Search"
                    type="primary"
                    :loading="checkingSources"
                    @click="runSourceCheck"
                  >
                    检查源
                  </el-button>
                  <el-button
                    :icon="Delete"
                    :disabled="!sourceChecks.length"
                    @click="clearSourceChecks"
                  >
                    清空报告
                  </el-button>
                </div>
              </div>

              <div class="source-check-summary">
                <div v-for="item in sourceCheckSummaryCards" :key="item.label">
                  <span>{{ item.label }}</span>
                  <strong>{{ item.value }}</strong>
                </div>
              </div>

              <el-table
                v-loading="checkingSources || loading"
                :data="sourceChecks"
                class="console-table source-check-table"
                height="380"
                empty-text="还没有源检查报告"
              >
                <el-table-column min-width="180" label="源">
                  <template #default="{ row }: { row: SourceCheckReport }">
                    <div class="source-name-cell">
                      <strong>{{ row.sourceName || '未命名源' }}</strong>
                      <span>{{ sourceKindLabel(row.kind) }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column width="92" label="状态">
                  <template #default="{ row }: { row: SourceCheckReport }">
                    <el-tag :type="row.ok ? 'success' : 'danger'" effect="plain">
                      {{ row.ok ? '可达' : '异常' }}
                    </el-tag>
                  </template>
                </el-table-column>
                <el-table-column width="96" label="HTTP">
                  <template #default="{ row }: { row: SourceCheckReport }">
                    {{ row.statusCode ?? '-' }}
                  </template>
                </el-table-column>
                <el-table-column width="100" label="延迟">
                  <template #default="{ row }: { row: SourceCheckReport }">
                    {{ row.latencyMs }} ms
                  </template>
                </el-table-column>
                <el-table-column min-width="180" prop="message" label="结果" show-overflow-tooltip />
                <el-table-column min-width="180" label="检查时间">
                  <template #default="{ row }: { row: SourceCheckReport }">
                    {{ formatTime(row.checkedAt) }}
                  </template>
                </el-table-column>
                <el-table-column min-width="260" prop="sourceUrl" label="地址" show-overflow-tooltip />
              </el-table>
            </div>
          </el-tab-pane>

          <el-tab-pane label="功能覆盖" name="coverage">
            <div class="coverage-list">
              <div
                v-for="item in coverageRows"
                :key="item.name"
                class="coverage-row"
              >
                <div class="coverage-main">
                  <component :is="item.icon" />
                  <div>
                    <strong>{{ item.name }}</strong>
                    <span>{{ item.scope }}</span>
                  </div>
                </div>
                <el-tag :type="statusTagType(item.status)" effect="plain">
                  {{ item.status }}
                </el-tag>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </section>
    </main>

    <el-drawer v-model="tocDrawerOpen" size="520px" title="TXT 目录规则">
      <el-form label-position="top" @submit.prevent>
        <el-form-item label="名称">
          <el-input v-model="tocForm.name" />
        </el-form-item>
        <el-form-item label="正则">
          <el-input v-model="tocForm.rule" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="替换">
          <el-input v-model="tocForm.replacement" />
        </el-form-item>
        <el-form-item label="示例">
          <el-input v-model="tocForm.example" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="启用">
          <el-switch v-model="tocForm.enable" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="tocDrawerOpen = false">取消</el-button>
        <el-button type="primary" :loading="savingToc" @click="saveTocRule">
          保存
        </el-button>
      </template>
    </el-drawer>

    <el-drawer v-model="replaceDrawerOpen" size="560px" title="替换规则">
      <el-form label-position="top" @submit.prevent>
        <el-form-item label="名称">
          <el-input v-model="replaceForm.name" />
        </el-form-item>
        <el-form-item label="分组">
          <el-input v-model="replaceForm.group" />
        </el-form-item>
        <el-form-item label="匹配">
          <el-input v-model="replaceForm.pattern" type="textarea" :rows="4" />
        </el-form-item>
        <el-form-item label="替换为">
          <el-input v-model="replaceForm.replacement" type="textarea" :rows="3" />
        </el-form-item>
        <div class="switch-row">
          <el-switch v-model="replaceForm.isRegex" active-text="正则" />
          <el-switch v-model="replaceForm.isEnabled" active-text="启用" />
          <el-switch v-model="replaceForm.scopeTitle" active-text="标题" />
          <el-switch v-model="replaceForm.scopeContent" active-text="正文" />
        </div>
        <div class="replace-test">
          <el-input
            v-model="replaceTestText"
            type="textarea"
            :rows="3"
            placeholder="测试文本"
          />
          <el-button :icon="VideoPlay" @click="runReplaceTest">测试</el-button>
          <pre v-if="replaceTestResult">{{ replaceTestResult }}</pre>
        </div>
      </el-form>
      <template #footer>
        <el-button @click="replaceDrawerOpen = false">取消</el-button>
        <el-button
          type="primary"
          :loading="savingReplace"
          @click="saveReplaceRule"
        >
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
  FolderAdd,
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
import API, {
  legado_http_entry_point,
  legado_webSocket_entry_point,
} from '@api'
import type {
  LeagdoApiResponse,
  ReplaceRule,
  ServerBackup,
  ServerExportData,
  ServerInfo,
  SourceCheckReport,
  TxtTocRule,
  AppSettings,
  UpdateCheckResult,
} from '@api'
import type { Book } from '@/book'

type ResponseBox<T> = {
  data: LeagdoApiResponse<T>
}

type NavItem = {
  label: string
  path: string
  icon: Component
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

const router = useRouter()
const route = useRoute()
const activeTab = ref(resolveConsoleTab(route.query.tab))
const loading = ref(false)
const uploadingLocalBook = ref(false)
const savingToc = ref(false)
const savingReplace = ref(false)
const creatingBackup = ref(false)
const exportingBooks = ref(false)
const restoringBackup = ref('')
const checkingSources = ref(false)
const checkingUpdates = ref(false)
const serverInfo = ref<ServerInfo>()
const txtInput = ref<HTMLInputElement>()
const backupInput = ref<HTMLInputElement>()
const lastLocalBook = ref<Book>()
const appSettings = ref<AppSettings>({})
const backups = ref<ServerBackup[]>([])
const sourceChecks = ref<SourceCheckReport[]>([])
const updateCheck = ref<UpdateCheckResult>()

const tocRules = ref<TxtTocRule[]>([])
const replaceRules = ref<ReplaceRule[]>([])
const tocKeyword = ref('')
const replaceKeyword = ref('')
const sourceCheckScope = ref<'all' | 'bookSources' | 'rssSources'>('all')
const sourceCheckOnlyEnabled = ref(false)
const sourceCheckTimeout = ref(6000)
const sourceCheckLimit = ref(80)
const tocDrawerOpen = ref(false)
const replaceDrawerOpen = ref(false)
const tocForm = ref<TxtTocRule>(createTocRule())
const replaceForm = ref<ReplaceRule>(createReplaceRule())
const replaceTestText = ref('')
const replaceTestResult = ref('')

const httpEndpoint = computed(() => legado_http_entry_point || location.origin)
const wsEndpoint = computed(() => legado_webSocket_entry_point || '-')
const backupDirHint = computed(
  () => backups.value[0]?.path.replace(/[\\/][^\\/]+$/, '') || '本地备份目录',
)

const countCards = computed(() => {
  const counts = serverInfo.value?.counts
  return [
    {
      key: 'books',
      label: '书架',
      value: counts?.books ?? 0,
      icon: Reading,
      tone: 'tone-green',
    },
    {
      key: 'bookSources',
      label: '书源',
      value: counts?.bookSources ?? 0,
      icon: Collection,
      tone: 'tone-blue',
    },
    {
      key: 'rssSources',
      label: '订阅源',
      value: counts?.rssSources ?? 0,
      icon: Link,
      tone: 'tone-cyan',
    },
    {
      key: 'replaceRules',
      label: '替换规则',
      value: counts?.replaceRules ?? 0,
      icon: DocumentCopy,
      tone: 'tone-amber',
    },
    {
      key: 'txtTocRules',
      label: '目录规则',
      value: counts?.txtTocRules ?? 0,
      icon: Files,
      tone: 'tone-red',
    },
    {
      key: 'settings',
      label: '设置项',
      value: counts?.settings ?? 0,
      icon: Setting,
      tone: 'tone-violet',
    },
    {
      key: 'appData',
      label: 'App 数据',
      value: counts?.appData ?? 0,
      icon: SetUp,
      tone: 'tone-slate',
    },
    {
      key: 'backups',
      label: '备份',
      value: counts?.backups ?? 0,
      icon: Files,
      tone: 'tone-green',
    },
    {
      key: 'sourceChecks',
      label: '源检查',
      value: counts?.sourceChecks ?? 0,
      icon: Search,
      tone: 'tone-blue',
    },
  ]
})

const filteredTocRules = computed(() => {
  const keyword = tocKeyword.value.trim().toLowerCase()
  if (!keyword) return tocRules.value
  return tocRules.value.filter(rule =>
    [rule.name, rule.rule, rule.replacement, rule.example]
      .filter(Boolean)
      .some(value => String(value).toLowerCase().includes(keyword)),
  )
})

const filteredReplaceRules = computed(() => {
  const keyword = replaceKeyword.value.trim().toLowerCase()
  if (!keyword) return replaceRules.value
  return replaceRules.value.filter(rule =>
    [rule.name, rule.group, rule.pattern, rule.replacement, rule.scope]
      .filter(Boolean)
      .some(value => String(value).toLowerCase().includes(keyword)),
  )
})

const sourceCheckSummaryCards = computed(() => {
  const total = sourceChecks.value.length
  const ok = sourceChecks.value.filter(report => report.ok).length
  const failed = sourceChecks.value.filter(report => !report.ok).length
  const latest = sourceChecks.value.reduce(
    (time, report) => Math.max(time, report.checkedAt || 0),
    0,
  )
  return [
    { label: '总计', value: total },
    { label: '可达', value: ok },
    { label: '异常', value: failed },
    { label: '最近检查', value: latest ? formatTime(latest) : '-' },
  ]
})

const quickActions = computed(() => [
  { label: '快照', icon: Files, action: createServerBackup },
  { label: '下载', icon: Download, action: downloadBackup },
  { label: '恢复', icon: Upload, action: () => backupInput.value?.click() },
  { label: '书架', icon: Reading, action: () => router.push('/') },
  { label: '规则', icon: SetUp, action: () => (activeTab.value = 'toc') },
])

const coverageRows = [
  {
    name: '书架与阅读',
    scope: '书籍列表、章节目录、正文、阅读进度',
    status: '可用',
    icon: Reading,
  },
  {
    name: '书源管理',
    scope: '导入、编辑、保存、删除、调试入口',
    status: '可用',
    icon: Collection,
  },
  {
    name: '订阅源管理',
    scope: 'RSS 源编辑、保存、删除、调试入口',
    status: '可用',
    icon: Link,
  },
  {
    name: '本地 TXT',
    scope: '上传、入库、目录拆分、正文读取',
    status: '可用',
    icon: FolderAdd,
  },
  {
    name: '替换规则',
    scope: '列表、编辑、启停、测试',
    status: '可用',
    icon: DocumentCopy,
  },
  {
    name: '备份恢复',
    scope: '本地快照、导入导出、书架、源、设置、App 数据',
    status: '可用',
    icon: Files,
  },
  {
    name: '源检查',
    scope: 'HTTP/HTTPS 可达性、状态码、延迟报告、历史结果清理',
    status: '可用',
    icon: Search,
  },
  {
    name: '在线搜索与抓取',
    scope: 'Android 规则引擎待抽取到 JVM core',
    status: '兼容入口',
    icon: Search,
  },
  {
    name: 'TTS / WebDAV / 通知',
    scope: 'WebDAV 账号已配置落盘，远程协议继续接入',
    status: '规划中',
    icon: SetUp,
  },
]
watch(
  () => route.query.tab,
  value => {
    const nextTab = resolveConsoleTab(value)
    if (nextTab !== activeTab.value) activeTab.value = nextTab
  },
)

watch(activeTab, tab => {
  if (route.query.tab !== tab) {
    void router.replace({ query: { ...route.query, tab } })
  }
})

function resolveConsoleTab(value: unknown) {
  const tab = typeof value === 'string' ? value : ''
  return ['toc', 'replace', 'sourceCheck', 'coverage'].includes(tab) ? tab : 'toc'
}

function createTocRule(): TxtTocRule {
  return {
    name: '',
    rule: '',
    replacement: '',
    example: '',
    serialNumber: tocRules.value?.length ?? 0,
    enable: true,
  }
}

function createReplaceRule(): ReplaceRule {
  return {
    name: '',
    group: '',
    pattern: '',
    replacement: '',
    isEnabled: true,
    isRegex: true,
    scopeTitle: false,
    scopeContent: true,
    timeoutMillisecond: 3000,
  }
}

function statusTagType(status: string) {
  if (status === '可用') return 'success'
  if (status === '兼容入口') return 'warning'
  return 'info'
}

async function refreshAll() {
  loading.value = true
  try {
    await Promise.all([
      loadServerInfo(),
      loadTxtTocRules(),
      loadReplaceRules(),
      loadBackups(),
      loadSourceChecks(),
      loadAppSettings(),
    ])
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '刷新失败')
  } finally {
    loading.value = false
  }
}

async function loadServerInfo() {
  serverInfo.value = unwrap(await API.getServerInfo())
}

async function loadTxtTocRules() {
  txtTocRulesNormalize(unwrap(await API.getTxtTocRules()))
}

async function loadReplaceRules() {
  const data = unwrap(await API.getReplaceRules())
  replaceRules.value = typeof data === 'string' ? JSON.parse(data || '[]') : data
}

async function loadBackups() {
  backups.value = unwrap(await API.getBackups())
}

async function loadSourceChecks() {
  sourceChecks.value = unwrap(await API.getSourceChecks())
}

async function loadAppSettings() {
  appSettings.value = unwrap(await API.getAppSettings())
}

async function checkForUpdates() {
  checkingUpdates.value = true
  try {
    updateCheck.value = unwrap(await API.checkForUpdates())
    const update = updateCheck.value
    if (update.newer && update.latestVersion) {
      const action = update.releaseUrl ? '打开发布页' : '确定'
      await ElMessageBox.confirm(
        `${update.releaseName || update.latestVersion}\n当前：${update.currentVersion}\n最新：${update.latestVersion}`,
        '发现服务端更新',
        { confirmButtonText: action, cancelButtonText: '关闭', type: 'info' },
      ).then(() => {
        if (update.releaseUrl) window.open(update.releaseUrl, '_blank', 'noopener,noreferrer')
      }).catch(() => undefined)
    } else {
      ElMessage.info(update.message || '当前已是最新版本')
    }
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '更新检查失败')
  } finally {
    checkingUpdates.value = false
  }
}

function txtTocRulesNormalize(rules: TxtTocRule[]) {
  txtTocRulesSort(rules)
  tocRules.value = rules
}

function txtTocRulesSort(rules: TxtTocRule[]) {
  rules.sort(
    (left, right) =>
      (left.serialNumber ?? Number.MAX_SAFE_INTEGER) -
        (right.serialNumber ?? Number.MAX_SAFE_INTEGER) ||
      String(left.name).localeCompare(String(right.name)),
  )
}

async function downloadBackup() {
  try {
    const data = unwrap(await API.exportData())
    downloadJson(data, `legado-server-backup-${new Date().toISOString()}.json`)
    ElMessage.success('备份已生成')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '导出失败')
  }
}

async function exportShelfBooks() {
  exportingBooks.value = true
  try {
    const books = unwrap(await API.getBookShelf())
    const batch = unwrap(await API.exportBooks(books.map(book => book.bookUrl)))
    batch.results.forEach(result => {
      if (result.isSuccess && result.data) downloadBookExport(result.data)
    })
    const mode = batch.parallel ? '并行' : '串行'
    const message = `${mode}导出完成：成功 ${batch.succeeded} 本${batch.failed ? `，失败 ${batch.failed} 本` : ''}`
    if (batch.failed) ElMessage.warning(message)
    else ElMessage.success(message)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '书架导出失败')
  } finally {
    exportingBooks.value = false
  }
}

async function createServerBackup() {
  creatingBackup.value = true
  try {
    const backup = unwrap(await API.createBackup())
    await Promise.all([loadBackups(), loadServerInfo()])
    ElMessage.success(`已创建快照：${backup.fileName}`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '创建备份失败')
  } finally {
    creatingBackup.value = false
  }
}

async function restoreServerBackup(backup: ServerBackup) {
  try {
    await ElMessageBox.confirm(
      `恢复 ${backup.fileName} 会覆盖同名数据集合。`,
      '恢复备份快照',
      {
        type: 'warning',
        confirmButtonText: '恢复',
        cancelButtonText: '取消',
      },
    )
    restoringBackup.value = backup.fileName
    const result = unwrap(await API.restoreBackup(backup.fileName))
    ElMessage.success(
      Object.entries(result)
        .map(([key, value]) => `${key}: ${value}`)
        .join(' / '),
    )
    await refreshAll()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '恢复备份失败')
    }
  } finally {
    restoringBackup.value = ''
  }
}

async function removeServerBackup(backup: ServerBackup) {
  try {
    await ElMessageBox.confirm(`删除 ${backup.fileName}？`, '删除备份快照', {
      type: 'warning',
      confirmButtonText: '删除',
      cancelButtonText: '取消',
    })
    backups.value = unwrap(await API.deleteBackup(backup.fileName))
    await loadServerInfo()
    ElMessage.success('备份快照已删除')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '删除备份失败')
    }
  }
}

async function runSourceCheck() {
  checkingSources.value = true
  try {
    const result = unwrap(
      await API.checkSources({
        scope: sourceCheckScope.value,
        onlyEnabled: sourceCheckOnlyEnabled.value,
        timeoutMillis: sourceCheckTimeout.value,
        limit: sourceCheckLimit.value,
      }),
    )
    sourceChecks.value = result.reports
    await loadServerInfo()
    const { total, ok, failed, skipped } = result.summary
    ElMessage.success(`检查完成：${total} 个，${ok} 个可达，${failed} 个异常，跳过 ${skipped} 个`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '源检查失败')
  } finally {
    checkingSources.value = false
  }
}

async function clearSourceChecks() {
  try {
    await ElMessageBox.confirm('清空当前源检查报告？', '清空源检查', {
      type: 'warning',
      confirmButtonText: '清空',
      cancelButtonText: '取消',
    })
    sourceChecks.value = unwrap(await API.deleteSourceChecks())
    await loadServerInfo()
    ElMessage.success('源检查报告已清空')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '清空源检查失败')
    }
  }
}

async function importBackup(event: Event) {
  const input = event.target as HTMLInputElement
  const file = input.files?.[0]
  if (!file) return
  try {
    const content = await file.text()
    const data = JSON.parse(content) as Partial<ServerExportData>
    await ElMessageBox.confirm('导入会覆盖同名数据集合。', '导入备份', {
      type: 'warning',
      confirmButtonText: '导入',
      cancelButtonText: '取消',
    })
    const result = unwrap(await API.importData(data))
    ElMessage.success(
      Object.entries(result)
        .map(([key, value]) => `${key}: ${value}`)
        .join(' / '),
    )
    await refreshAll()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '导入失败')
    }
  } finally {
    input.value = ''
  }
}

async function uploadLocalBook(event: Event) {
  const input = event.target as HTMLInputElement
  const files = Array.from(input.files || [])
  if (files.length === 0) return
  const mode = String(appSettings.value.backup?.localBookImportSort ?? '0')
  files.sort((left, right) => {
    if (mode === '1') return left.lastModified - right.lastModified
    return left.name.localeCompare(right.name, 'zh-Hans-CN', { numeric: mode === '2' })
  })
  uploadingLocalBook.value = true
  try {
    for (const file of files) lastLocalBook.value = unwrap(await API.addLocalBook(file))
    ElMessage.success(`${files.length} 本本地书籍已入库`)
    await loadServerInfo()
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '上传失败')
  } finally {
    uploadingLocalBook.value = false
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

function downloadBookExport(result: import('@/api/api').BookExportResult) {
  const binary = atob(result.base64)
  const bytes = Uint8Array.from(binary, character => character.charCodeAt(0))
  const url = URL.createObjectURL(new Blob([bytes], { type: result.mime }))
  const anchor = document.createElement('a')
  anchor.href = url
  anchor.download = result.fileName
  anchor.click()
  URL.revokeObjectURL(url)
}

function formatTime(value: number) {
  if (!value) return '-'
  return new Date(value).toLocaleString(document.documentElement.lang || undefined)
}

function formatBytes(value: number) {
  if (!Number.isFinite(value)) return '-'
  if (value < 1024) return `${value} B`
  if (value < 1024 * 1024) return `${(value / 1024).toFixed(1)} KB`
  return `${(value / 1024 / 1024).toFixed(1)} MB`
}

function sourceKindLabel(kind: string) {
  if (kind === 'bookSource') return '书源'
  if (kind === 'rssSource') return '订阅源'
  return kind || '-'
}

async function copyEndpoint(value: string) {
  if (!value || value === '-') return
  await navigator.clipboard?.writeText(value)
  ElMessage.success('已复制')
}

function openTocEditor(rule?: TxtTocRule) {
  tocForm.value = rule ? { ...rule } : createTocRule()
  tocDrawerOpen.value = true
}

async function saveTocRule() {
  if (!tocForm.value.name.trim() || !tocForm.value.rule.trim()) {
    ElMessage.warning('名称和正则不能为空')
    return
  }
  savingToc.value = true
  try {
    unwrap(await API.saveTxtTocRule(tocForm.value))
    tocDrawerOpen.value = false
    await Promise.all([loadTxtTocRules(), loadServerInfo()])
    ElMessage.success('目录规则已保存')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存失败')
  } finally {
    savingToc.value = false
  }
}

async function toggleTocRule(rule: TxtTocRule) {
  const nextRule = { ...rule, enable: rule.enable === false }
  try {
    unwrap(await API.saveTxtTocRule(nextRule))
    await loadTxtTocRules()
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '更新失败')
  }
}

async function removeTocRule(rule: TxtTocRule) {
  if (rule.id === undefined) return
  try {
    await ElMessageBox.confirm(`删除 ${rule.name}？`, '删除目录规则', {
      type: 'warning',
      confirmButtonText: '删除',
      cancelButtonText: '取消',
    })
    unwrap(await API.deleteTxtTocRule({ id: rule.id }))
    await Promise.all([loadTxtTocRules(), loadServerInfo()])
    ElMessage.success('目录规则已删除')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '删除失败')
    }
  }
}

function openReplaceEditor(rule?: ReplaceRule) {
  replaceForm.value = rule ? { ...rule } : createReplaceRule()
  replaceTestText.value = ''
  replaceTestResult.value = ''
  replaceDrawerOpen.value = true
}

async function saveReplaceRule() {
  if (!replaceForm.value.name.trim() || !replaceForm.value.pattern.trim()) {
    ElMessage.warning('名称和匹配不能为空')
    return
  }
  savingReplace.value = true
  try {
    unwrap(await API.saveReplaceRule(replaceForm.value))
    replaceDrawerOpen.value = false
    await Promise.all([loadReplaceRules(), loadServerInfo()])
    ElMessage.success('替换规则已保存')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存失败')
  } finally {
    savingReplace.value = false
  }
}

async function toggleReplaceRule(rule: ReplaceRule) {
  const nextRule = { ...rule, isEnabled: rule.isEnabled === false }
  try {
    unwrap(await API.saveReplaceRule(nextRule))
    await loadReplaceRules()
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '更新失败')
  }
}

async function removeReplaceRule(rule: ReplaceRule) {
  if (rule.id === undefined) return
  try {
    await ElMessageBox.confirm(`删除 ${rule.name}？`, '删除替换规则', {
      type: 'warning',
      confirmButtonText: '删除',
      cancelButtonText: '取消',
    })
    unwrap(await API.deleteReplaceRule({ id: rule.id }))
    await Promise.all([loadReplaceRules(), loadServerInfo()])
    ElMessage.success('替换规则已删除')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error instanceof Error ? error.message : '删除失败')
    }
  }
}

async function runReplaceTest() {
  if (!replaceForm.value.pattern.trim()) {
    ElMessage.warning('匹配不能为空')
    return
  }
  try {
    replaceTestResult.value = unwrap(
      await API.testReplaceRule(replaceForm.value, replaceTestText.value),
    )
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '测试失败')
  }
}

onMounted(() => {
  document.title = '服务端控制台'
  refreshAll()
})
</script>

<style scoped lang="scss">
.server-console {
  min-height: 100vh;
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  background: #f5f7fa;
  color: #1f2937;
}

.console-sidebar {
  position: sticky;
  top: 0;
  height: 100vh;
  padding: 28px 22px;
  box-sizing: border-box;
  background: #101820;
  color: #e7edf3;
  display: flex;
  flex-direction: column;
  gap: 24px;
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

.console-nav {
  display: grid;
  gap: 8px;
}

.nav-item {
  min-height: 42px;
  padding: 0 12px;
  border-radius: 8px;
  color: #cbd5e1;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;

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

.endpoint-panel {
  margin-top: auto;
  padding: 14px;
  border: 1px solid rgba(148, 163, 184, 0.28);
  border-radius: 8px;
  background: rgba(15, 23, 42, 0.5);
}

.endpoint-title {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
  font-size: 13px;
  color: #e2e8f0;
}

.endpoint-line {
  width: 100%;
  padding: 9px 0;
  border: 0;
  border-top: 1px solid rgba(148, 163, 184, 0.18);
  background: transparent;
  color: inherit;
  display: grid;
  gap: 3px;
  text-align: left;
  cursor: pointer;

  span {
    font-size: 11px;
    color: #94a3b8;
  }

  strong {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    font-size: 12px;
    font-weight: 600;
  }
}

.console-main {
  min-width: 0;
  padding: 30px;
}

.status-band,
.panel,
.management-panel,
.metric-card {
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #fff;
  box-shadow: 0 18px 42px rgba(15, 23, 42, 0.06);
}

.status-band {
  min-height: 96px;
  padding: 22px 24px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.status-copy {
  min-width: 0;

  h2 {
    margin: 0;
    font-size: 26px;
    line-height: 1.2;
    font-weight: 750;
    color: #111827;
  }

  p {
    margin: 8px 0 0;
    font-size: 14px;
    color: #64748b;
  }
}

.status-actions {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 10px;
}

.metric-grid {
  margin-top: 18px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(130px, 1fr));
  gap: 14px;
}

.metric-card {
  min-height: 88px;
  padding: 16px;
  display: flex;
  align-items: center;
  gap: 13px;

  span {
    display: block;
    font-size: 13px;
    color: #64748b;
  }

  strong {
    display: block;
    margin-top: 5px;
    font-size: 24px;
    line-height: 1;
    color: #111827;
  }
}

.metric-icon {
  width: 38px;
  height: 38px;
  border-radius: 8px;
  display: grid;
  place-items: center;

  svg {
    width: 20px;
    height: 20px;
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

.tone-cyan {
  background: #e6f7fb;
  color: #0e7490;
}

.tone-amber {
  background: #fff4d8;
  color: #b7791f;
}

.tone-red {
  background: #ffe8e8;
  color: #c2410c;
}

.tone-violet {
  background: #f0ebff;
  color: #6d28d9;
}

.tone-slate {
  background: #e8eef5;
  color: #334155;
}

.workspace-grid {
  margin-top: 18px;
  display: grid;
  grid-template-columns: minmax(0, 0.78fr) minmax(0, 1.05fr) minmax(0, 1.05fr);
  gap: 18px;
}

.panel {
  padding: 20px;
}

.panel-heading {
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;

  h3 {
    margin: 0;
    font-size: 18px;
    color: #111827;
  }

  p {
    margin: 6px 0 0;
    max-width: 680px;
    overflow-wrap: anywhere;
    font-size: 13px;
    color: #64748b;
  }
}

.local-book-body {
  margin-top: 22px;
  min-height: 96px;
  display: flex;
  align-items: center;
  gap: 16px;
  border-top: 1px solid #edf1f5;
}

.local-book-figure {
  width: 58px;
  height: 72px;
  border-radius: 6px;
  display: grid;
  place-items: center;
  background: #ecfdf5;
  color: #12805c;
  box-shadow: inset 0 -10px 0 rgba(18, 128, 92, 0.08);

  .el-icon {
    font-size: 28px;
  }
}

.local-book-meta {
  min-width: 0;
  display: grid;
  gap: 6px;

  strong,
  span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  strong {
    font-size: 16px;
    color: #111827;
  }

  span {
    font-size: 13px;
    color: #64748b;
  }
}

.data-actions {
  margin-top: 22px;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(86px, 1fr));
  gap: 10px;
}

.quick-action {
  min-height: 66px;
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #f8fafc;
  color: #334155;
  display: grid;
  place-items: center;
  gap: 5px;
  cursor: pointer;

  svg {
    width: 20px;
    height: 20px;
    color: #0f766e;
  }

  span {
    font-size: 13px;
  }

  &:hover {
    border-color: #99f6e4;
    background: #f0fdfa;
  }
}

.backup-actions {
  margin-top: 18px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.backup-list {
  margin-top: 16px;
  display: grid;
  gap: 10px;
}

.backup-row {
  min-height: 58px;
  padding: 10px 12px;
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  background: #fbfdff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;

  > div:first-child {
    min-width: 0;
    display: grid;
    gap: 5px;
  }

  strong,
  span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  strong {
    color: #111827;
    font-size: 13px;
  }

  span {
    color: #64748b;
    font-size: 12px;
  }
}

.backup-row-actions {
  flex: 0 0 auto;
  display: flex;
  align-items: center;
}

.backup-empty {
  margin-top: 16px;
  min-height: 92px;
  border: 1px dashed #cbd5e1;
  border-radius: 8px;
  background: #f8fafc;
  color: #64748b;
  display: grid;
  place-items: center;
  align-content: center;
  gap: 8px;

  .el-icon {
    font-size: 24px;
    color: #0f766e;
  }
}

.management-panel {
  margin-top: 18px;
  padding: 6px 18px 20px;
}

.table-toolbar {
  padding: 8px 0 16px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;

  .el-input {
    max-width: 360px;
  }
}

.console-table {
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

.source-check-panel {
  display: grid;
  gap: 14px;
  padding-top: 8px;
}

.source-check-toolbar {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 12px;
}

.compact-field {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #64748b;
  font-size: 13px;

  .el-input-number {
    width: 118px;
  }
}

.source-check-actions {
  margin-left: auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-end;
  gap: 10px;
}

.source-check-summary {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 10px;

  > div {
    min-height: 66px;
    padding: 12px 14px;
    border: 1px solid #e3e8ef;
    border-radius: 8px;
    background: #fbfdff;
  }

  span {
    display: block;
    color: #64748b;
    font-size: 12px;
  }

  strong {
    display: block;
    margin-top: 6px;
    color: #111827;
    font-size: 18px;
    line-height: 1.2;
    overflow-wrap: anywhere;
  }
}

.source-name-cell {
  min-width: 0;
  display: grid;
  gap: 4px;

  strong,
  span {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  strong {
    color: #111827;
    font-size: 13px;
  }

  span {
    color: #64748b;
    font-size: 12px;
  }
}

.coverage-list {
  display: grid;
  gap: 10px;
  padding: 8px 0 4px;
}

.coverage-row {
  min-height: 64px;
  padding: 0 14px;
  border: 1px solid #e3e8ef;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  background: #fbfdff;
}

.coverage-main {
  min-width: 0;
  display: flex;
  align-items: center;
  gap: 12px;

  svg {
    flex: 0 0 auto;
    width: 20px;
    height: 20px;
    color: #0f766e;
  }

  strong {
    display: block;
    font-size: 14px;
    color: #111827;
  }

  span {
    display: block;
    margin-top: 3px;
    overflow-wrap: anywhere;
    font-size: 12px;
    color: #64748b;
  }
}

.switch-row {
  display: flex;
  flex-wrap: wrap;
  gap: 18px;
  margin-bottom: 18px;
}

.replace-test {
  display: grid;
  gap: 10px;

  .el-button {
    justify-self: start;
  }

  pre {
    margin: 0;
    padding: 12px;
    max-height: 170px;
    overflow: auto;
    border-radius: 8px;
    background: #0f172a;
    color: #e2e8f0;
    font-size: 12px;
    line-height: 1.6;
    white-space: pre-wrap;
  }
}

.visually-hidden {
  position: fixed;
  width: 1px;
  height: 1px;
  opacity: 0;
  pointer-events: none;
}

@media (max-width: 1080px) {
  .server-console {
    grid-template-columns: 1fr;
  }

  .console-sidebar {
    position: static;
    height: auto;
  }

  .console-nav {
    grid-template-columns: repeat(4, minmax(120px, 1fr));
  }

  .endpoint-panel {
    margin-top: 0;
  }

  .metric-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .source-check-actions {
    width: 100%;
    margin-left: 0;
    justify-content: flex-start;
  }

  .workspace-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 680px) {
  .console-main,
  .console-sidebar {
    padding: 18px;
  }

  .status-band,
  .panel-heading,
  .table-toolbar,
  .coverage-row {
    align-items: stretch;
    flex-direction: column;
  }

  .status-actions {
    justify-content: flex-start;
  }

  .console-nav {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .metric-grid,
  .data-actions,
  .source-check-summary {
    grid-template-columns: 1fr;
  }

  .source-check-toolbar,
  .compact-field,
  .source-check-actions {
    align-items: stretch;
    flex-direction: column;
  }

  .compact-field .el-input-number,
  .source-check-actions .el-button {
    width: 100%;
  }

  .table-toolbar .el-input {
    max-width: none;
  }
}
</style>
