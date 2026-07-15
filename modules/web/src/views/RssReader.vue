<template>
  <main class="rss-reader">
    <header class="rss-header">
      <div>
        <h1>订阅阅读</h1>
        <p>{{ articleSummary }}</p>
      </div>
      <div class="rss-actions">
        <el-button :icon="Setting" @click="router.push('/rssSource')">管理订阅源</el-button>
        <el-button :icon="Download" :disabled="filteredArticles.length === 0" @click="exportFilteredArticles">导出当前筛选</el-button>
        <el-button :icon="CircleCheck" :disabled="filteredArticles.length === 0" :loading="batching" @click="markFilteredRead">当前筛选已读</el-button>
        <el-button :icon="Collection" :disabled="filteredArticles.length === 0" :loading="batching" @click="setFilteredGroup">设置分组</el-button>
        <el-button type="primary" :icon="Refresh" :loading="refreshing" @click="refreshFeeds">刷新订阅</el-button>
      </div>
    </header>

    <section class="rss-workspace">
      <aside class="rss-list-panel">
        <div class="rss-filters">
          <el-select v-model="sourceFilter" clearable placeholder="全部订阅源">
            <el-option v-for="source in sourceOptions" :key="source.value" :label="source.label" :value="source.value" />
          </el-select>
          <el-select v-model="groupFilter" clearable placeholder="全部分组">
            <el-option v-for="group in groupOptions" :key="group" :label="group" :value="group" />
          </el-select>
          <el-input v-model="searchText" :prefix-icon="Search" clearable placeholder="搜索文章" />
          <el-checkbox v-model="unreadOnly">仅未读</el-checkbox>
          <el-checkbox v-model="starredOnly">仅星标</el-checkbox>
        </div>
        <div class="rss-list" role="list">
          <button
            v-for="article in filteredArticles"
            :key="article.link"
            class="rss-row"
            :class="{ active: selectedArticle?.link === article.link, read: article.isRead }"
            type="button"
            @click="selectArticle(article)"
          >
            <span class="rss-row-source">{{ article.sourceName || article.sourceUrl }}</span>
            <strong>{{ article.title }}</strong>
            <small>{{ article.pubDate || formatTime(article.refreshedAt) }}</small>
          </button>
          <el-empty v-if="!loading && filteredArticles.length === 0" description="暂无订阅文章" />
        </div>
      </aside>

      <article v-if="selectedArticle" class="rss-detail-panel">
        <div class="rss-detail-meta">
          <span>{{ selectedArticle.sourceName || selectedArticle.sourceUrl }}</span>
          <span>{{ selectedArticle.pubDate || formatTime(selectedArticle.refreshedAt) }}</span>
        </div>
        <h2>{{ selectedArticle.title }}</h2>
        <img v-if="selectedArticle.image" :src="selectedArticle.image" :alt="selectedArticle.title" @error="hideBrokenImage" />
        <div v-loading="contentLoading" class="rss-content-area">
          <p v-if="contentError" class="rss-content-error">{{ contentError }}</p>
          <p class="rss-description">{{ articleText || '此文章没有可用正文或摘要。' }}</p>
        </div>
        <div class="rss-detail-actions">
          <el-button :icon="Reading" :loading="contentLoading" @click="loadArticleContent(selectedArticle)">读取全文</el-button>
          <el-button :icon="selectedArticle.isRead ? CircleCheck : Reading" @click="toggleRead(selectedArticle)">
            {{ selectedArticle.isRead ? '标记未读' : '标记已读' }}
          </el-button>
          <el-button :class="{ starred: selectedArticle.starred }" :icon="Star" @click="toggleStar(selectedArticle)">
            {{ selectedArticle.starred ? '取消星标' : '加入星标' }}
          </el-button>
          <el-button type="primary" :icon="Link" @click="openArticle(selectedArticle.link)">打开原文</el-button>
        </div>
      </article>
      <section v-else class="rss-detail-empty">
        <el-empty description="选择一篇文章开始阅读" />
      </section>
    </section>
  </main>
</template>

<script setup lang="ts">
import API, { type AppDataItem } from '@/api/api'
import { CircleCheck, Collection, Download, Link, Reading, Refresh, Search, Setting, Star } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'

type RssArticle = AppDataItem & {
  link: string
  title: string
  sourceUrl?: string
  sourceName?: string
  pubDate?: string
  description?: string
  content?: string
  articleContent?: string
  image?: string
  isRead?: boolean
  starred?: boolean
  group?: string
  starTime?: number
  refreshedAt?: number
}

const router = useRouter()
const loading = ref(false)
const refreshing = ref(false)
const articles = ref<RssArticle[]>([])
const selectedLink = ref('')
const sourceFilter = ref('')
const groupFilter = ref('')
const searchText = ref('')
const unreadOnly = ref(false)
const starredOnly = ref(false)
const batching = ref(false)
const contentLoading = ref(false)
const contentError = ref('')
const articleText = computed(() => selectedArticle.value?.articleContent || selectedArticle.value?.content || selectedArticle.value?.description || '')

const sourceOptions = computed(() => {
  const sources = new Map<string, string>()
  articles.value.forEach(article => {
    if (article.sourceUrl) sources.set(article.sourceUrl, article.sourceName || article.sourceUrl)
  })
  return [...sources.entries()].map(([value, label]) => ({ value, label }))
})
const groupOptions = computed(() => [...new Set(
  articles.value.map(article => String(article.group || '').trim()).filter(Boolean),
)].sort((left, right) => left.localeCompare(right, 'zh-Hans-CN')))
const filteredArticles = computed(() => {
  const query = searchText.value.trim().toLocaleLowerCase()
  return articles.value.filter(article => {
    if (sourceFilter.value && article.sourceUrl !== sourceFilter.value) return false
    if (groupFilter.value && article.group !== groupFilter.value) return false
    if (unreadOnly.value && article.isRead) return false
    if (starredOnly.value && !article.starred) return false
    return !query || [article.title, article.description, article.sourceName].some(value =>
      String(value || '').toLocaleLowerCase().includes(query),
    )
  })
})
const selectedArticle = computed(() =>
  filteredArticles.value.find(article => article.link === selectedLink.value),
)
const articleSummary = computed(() => {
  const unread = articles.value.filter(article => !article.isRead).length
  return `${articles.value.length} 篇文章${unread ? `，${unread} 篇未读` : ''}`
})

watch(filteredArticles, items => {
  if (!items.some(article => article.link === selectedLink.value)) selectedLink.value = items[0]?.link || ''
}, { immediate: true })

function formatTime(value: unknown) {
  const time = Number(value || 0)
  return time > 0 ? new Date(time).toLocaleString() : ''
}
function hideBrokenImage(event: Event) {
  ;(event.target as HTMLImageElement).style.display = 'none'
}
function selectArticle(article: RssArticle) {
  selectedLink.value = article.link
  if (!article.isRead) void updateArticle(article, { isRead: true, readAt: Date.now() })
  void loadArticleContent(article)
}
function openArticle(link: string) {
  if (link) window.open(link, '_blank', 'noopener,noreferrer')
}
async function updateArticle(article: RssArticle, patch: Partial<RssArticle>) {
  const next = { ...article, ...patch }
  const response = await API.saveAppData('rssArticles', next)
  if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '保存文章状态失败')
  const index = articles.value.findIndex(item => item.link === article.link)
  if (index >= 0) articles.value[index] = next
}
async function toggleRead(article: RssArticle) {
  try {
    await updateArticle(article, { isRead: !article.isRead, readAt: !article.isRead ? Date.now() : undefined })
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存文章状态失败')
  }
}
async function toggleStar(article: RssArticle) {
  try {
    await updateArticle(article, {
      starred: !article.starred,
      starTime: !article.starred ? Date.now() : undefined,
    })
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存文章状态失败')
  }
}
async function updateFiltered(patch: Record<string, unknown>, successMessage: string) {
  const links = filteredArticles.value.map(article => article.link)
  if (links.length === 0) return
  batching.value = true
  try {
    const response = await API.updateRssArticles(links, patch)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '批量更新失败')
    await loadArticles()
    ElMessage.success(`${successMessage} ${response.data.data.changed} 篇文章`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '批量更新失败')
  } finally {
    batching.value = false
  }
}
async function markFilteredRead() {
  try {
    await ElMessageBox.confirm(`将当前筛选的 ${filteredArticles.value.length} 篇文章标记为已读？`, '批量标记', {
      type: 'warning',
      confirmButtonText: '标记已读',
      cancelButtonText: '取消',
    })
    await updateFiltered({ isRead: true }, '已标记')
  } catch (error) {
    if (error !== 'cancel') ElMessage.error(error instanceof Error ? error.message : '批量更新失败')
  }
}
async function setFilteredGroup() {
  try {
    const result = await ElMessageBox.prompt('留空可清除当前筛选文章的分组。', '设置文章分组', {
      inputValue: groupFilter.value,
      confirmButtonText: '保存',
      cancelButtonText: '取消',
    })
    await updateFiltered({ group: result.value.trim() }, '已更新')
  } catch (error) {
    if (error !== 'cancel') ElMessage.error(error instanceof Error ? error.message : '批量更新失败')
  }
}
function exportFilteredArticles() {
  const payload = JSON.stringify(filteredArticles.value, null, 2)
  const blob = new Blob([payload], { type: 'application/json;charset=utf-8' })
  const link = document.createElement('a')
  link.href = URL.createObjectURL(blob)
  link.download = `legado-rss-${new Date().toISOString().replace(/[:.]/g, '-')}.json`
  document.body.appendChild(link)
  link.click()
  link.remove()
  URL.revokeObjectURL(link.href)
  ElMessage.success(`已导出 ${filteredArticles.value.length} 篇文章`)
}
async function loadArticleContent(article: RssArticle) {
  if (article.articleContent) return
  contentLoading.value = true
  contentError.value = ''
  try {
    const response = await API.getRssArticleContent(article.link)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '读取文章正文失败')
    const content = response.data.data.content
    const index = articles.value.findIndex(item => item.link === article.link)
    if (index >= 0) articles.value[index] = { ...articles.value[index], articleContent: content }
  } catch (error) {
    contentError.value = error instanceof Error ? error.message : '读取文章正文失败，当前显示订阅摘要。'
  } finally {
    contentLoading.value = false
  }
}
async function loadArticles() {
  loading.value = true
  try {
    const response = await API.getAppData('rssArticles')
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '加载订阅文章失败')
    articles.value = [...response.data.data as RssArticle[]]
      .filter(article => Boolean(article.link && article.title))
      .sort((left, right) => Number(right.refreshedAt || 0) - Number(left.refreshedAt || 0))
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '加载订阅文章失败')
  } finally {
    loading.value = false
  }
}
async function refreshFeeds() {
  refreshing.value = true
  try {
    const response = await API.refreshRssSources()
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '刷新订阅失败')
    await loadArticles()
    const result = response.data.data
    ElMessage.success(`已刷新 ${result.succeeded}/${result.attempted} 个订阅源，获得 ${result.articleCount} 篇文章`)
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '刷新订阅失败')
  } finally {
    refreshing.value = false
  }
}

onMounted(loadArticles)
</script>

<style scoped lang="scss">
.rss-reader {
  min-height: 100vh;
  box-sizing: border-box;
  padding: 76px 28px 28px;
  background: var(--legado-page-bg);
  color: #1e293b;
}

.rss-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  max-width: 1440px;
  margin: 0 auto 18px;

  h1,
  p { margin: 0; }
  h1 { font-size: 24px; }
  p { margin-top: 6px; color: #64748b; font-size: 13px; }
}

.rss-actions,
.rss-detail-actions { display: flex; flex-wrap: wrap; gap: 8px; }

.rss-workspace {
  display: grid;
  grid-template-columns: minmax(300px, 0.85fr) minmax(0, 1.6fr);
  max-width: 1440px;
  min-height: calc(100vh - 142px);
  margin: 0 auto;
  border: 1px solid #d9e1ea;
  background: var(--legado-surface-bg);
}

.rss-list-panel { min-width: 0; border-right: 1px solid #d9e1ea; }
.rss-filters { display: grid; gap: 9px; padding: 14px; border-bottom: 1px solid #d9e1ea; }
.rss-list { max-height: calc(100vh - 250px); overflow: auto; }
.rss-row {
  display: grid;
  gap: 5px;
  width: 100%;
  padding: 13px 14px;
  border: 0;
  border-bottom: 1px solid #edf1f5;
  background: transparent;
  color: inherit;
  cursor: pointer;
  text-align: left;

  &.active { background: #ecfdf5; }
  &.read strong { font-weight: 500; color: #64748b; }
  &:hover { background: #f8fafc; }
  .rss-row-source, small { color: #64748b; font-size: 12px; }
  strong { overflow: hidden; text-overflow: ellipsis; white-space: nowrap; font-size: 14px; }
}

.rss-detail-panel,
.rss-detail-empty { min-width: 0; padding: 32px; }
.rss-detail-meta { display: flex; flex-wrap: wrap; gap: 12px; color: #64748b; font-size: 13px; }
.rss-detail-panel h2 { margin: 12px 0 18px; font-size: 25px; line-height: 1.35; }
.rss-detail-panel img { display: block; max-width: min(100%, 720px); max-height: 360px; margin: 0 0 20px; object-fit: contain; }
.rss-description { max-width: 780px; margin: 0 0 28px; color: #334155; font-size: 16px; line-height: 1.85; white-space: pre-wrap; overflow-wrap: anywhere; }
.rss-content-area { max-width: 780px; min-height: 64px; }
.rss-content-error { margin: 0 0 12px; color: #b45309; font-size: 13px; line-height: 1.6; }
.starred :deep(svg) { color: #b45309; fill: currentColor; }

@media (max-width: 760px) {
  .rss-reader { padding: 62px 0 0; }
  .rss-header { align-items: flex-start; padding: 14px; }
  .rss-header h1 { font-size: 20px; }
  .rss-workspace { display: block; min-height: auto; border-right: 0; border-left: 0; }
  .rss-list-panel { border-right: 0; border-bottom: 1px solid #d9e1ea; }
  .rss-list { max-height: 44vh; }
  .rss-detail-panel, .rss-detail-empty { padding: 20px 14px 32px; }
  .rss-detail-panel h2 { font-size: 21px; }
}
</style>
