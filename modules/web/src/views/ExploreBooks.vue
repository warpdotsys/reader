<template>
  <main class="explore-page">
    <header class="explore-header">
      <div>
        <h1>发现</h1>
        <p>{{ resultLabel }}</p>
      </div>
      <div class="explore-actions">
        <el-select v-model="selectedSourceUrl" :loading="loadingSources" placeholder="选择书源" @change="selectSource">
          <el-option v-for="source in sources" :key="source.sourceUrl" :label="source.sourceName" :value="source.sourceUrl" />
        </el-select>
        <el-select v-model="selectedEntryUrl" :disabled="!selectedSource" placeholder="选择分类" @change="reload">
          <el-option v-for="entry in selectedSource?.entries || []" :key="entry.url" :label="entry.title" :value="entry.url" />
        </el-select>
        <el-input v-model="searchText" clearable placeholder="筛选书名或作者" />
        <el-button :icon="Refresh" :loading="loadingBooks" @click="reload">刷新</el-button>
      </div>
    </header>

    <section v-loading="loadingBooks" class="book-grid" aria-live="polite">
      <article v-for="book in visibleBooks" :key="`${book.origin}:${book.bookUrl}`" class="book-card">
        <img :src="coverUrl(book.coverUrl)" :alt="book.name" @error="hideBrokenCover" />
        <div class="book-copy">
          <span>{{ book.originName }}</span>
          <h2>{{ book.name }}</h2>
          <p>{{ book.author || '未知作者' }}</p>
          <small>{{ book.kind || book.latestChapterTitle || ' ' }}</small>
          <div class="book-actions">
            <el-button :icon="Plus" @click="addBook(book)">加入书架</el-button>
            <el-button :icon="Reading" type="primary" @click="readBook(book)">阅读</el-button>
          </div>
        </div>
      </article>
      <el-empty v-if="!loadingBooks && visibleBooks.length === 0" :description="emptyLabel" />
    </section>

    <footer class="pagination-bar">
      <el-button :icon="ArrowLeft" :disabled="page <= 1 || loadingBooks" @click="changePage(-1)">上一页</el-button>
      <span>第 {{ page }} 页</span>
      <el-button :icon="ArrowRight" :disabled="books.length === 0 || loadingBooks" @click="changePage(1)">下一页</el-button>
    </footer>
  </main>
</template>

<script setup lang="ts">
import { ArrowLeft, ArrowRight, Plus, Reading, Refresh } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import API, { type ExploreSource } from '@api'
import type { SeachBook } from '@/book'

const router = useRouter()
const sources = ref<ExploreSource[]>([])
const books = ref<SeachBook[]>([])
const selectedSourceUrl = ref('')
const selectedEntryUrl = ref('')
const searchText = ref('')
const page = ref(1)
const loadingSources = ref(false)
const loadingBooks = ref(false)

const selectedSource = computed(() => sources.value.find(source => source.sourceUrl === selectedSourceUrl.value))
const visibleBooks = computed(() => {
  const query = searchText.value.trim().toLocaleLowerCase()
  if (!query) return books.value
  return books.value.filter(book => [book.name, book.author, book.kind].some(value =>
    String(value || '').toLocaleLowerCase().includes(query),
  ))
})
const resultLabel = computed(() => books.value.length ? `${visibleBooks.value.length} / ${books.value.length} 本` : '发现书籍')
const emptyLabel = computed(() => selectedSource.value ? '当前分类没有匹配书籍' : '没有可用的发现书源')

function coverUrl(url?: string) {
  return API.getProxyCoverUrl(url || '')
}

function hideBrokenCover(event: Event) {
  ;(event.target as HTMLImageElement).style.visibility = 'hidden'
}

function selectSource() {
  selectedEntryUrl.value = selectedSource.value?.entries[0]?.url || ''
  page.value = 1
  void reload()
}

async function loadSources() {
  loadingSources.value = true
  try {
    const response = await API.getExploreSources()
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '无法读取发现书源')
    sources.value = response.data.data || []
    if (!selectedSourceUrl.value || !selectedSource.value) {
      selectedSourceUrl.value = sources.value[0]?.sourceUrl || ''
      selectedEntryUrl.value = sources.value[0]?.entries[0]?.url || ''
    }
    if (selectedSourceUrl.value && selectedEntryUrl.value) await reload()
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '无法读取发现书源')
  } finally {
    loadingSources.value = false
  }
}

async function reload() {
  if (!selectedSourceUrl.value || !selectedEntryUrl.value) {
    books.value = []
    return
  }
  loadingBooks.value = true
  try {
    const response = await API.exploreBooks(selectedSourceUrl.value, selectedEntryUrl.value, page.value)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '发现请求失败')
    books.value = response.data.data || []
  } catch (error) {
    books.value = []
    ElMessage.error(error instanceof Error ? error.message : '发现请求失败')
  } finally {
    loadingBooks.value = false
  }
}

async function addBook(book: SeachBook): Promise<boolean> {
  try {
    const response = await API.saveBook(book)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '无法加入书架')
    ElMessage.success(`已加入《${book.name}》`)
    return true
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '无法加入书架')
    return false
  }
}

async function readBook(book: SeachBook) {
  if (!await addBook(book)) return
  sessionStorage.setItem('bookUrl', book.bookUrl)
  sessionStorage.setItem('bookName', book.name)
  sessionStorage.setItem('bookAuthor', book.author || '')
  sessionStorage.setItem('chapterIndex', '0')
  sessionStorage.setItem('chapterPos', '0')
  sessionStorage.setItem('isSeachBook', 'true')
  await router.push('/chapter')
}

function changePage(delta: number) {
  page.value = Math.max(1, page.value + delta)
  void reload()
}

onMounted(loadSources)
</script>

<style scoped lang="scss">
.explore-page { min-height: 100vh; padding: 72px 32px 34px; background: #f5f7fa; color: #1f2937; }
.explore-header, .pagination-bar { max-width: 1320px; margin: 0 auto; }
.explore-header { display: flex; align-items: end; justify-content: space-between; gap: 20px; padding-bottom: 22px; border-bottom: 1px solid #d9e1e8; }
.explore-header h1 { margin: 0; font-size: 28px; line-height: 1.2; }
.explore-header p { margin: 8px 0 0; color: #64748b; }
.explore-actions { display: flex; align-items: center; gap: 10px; }
.explore-actions .el-select { width: 180px; }
.book-grid { max-width: 1320px; min-height: 420px; margin: 24px auto; display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 16px; }
.book-card { min-height: 184px; border: 1px solid #dce4ea; border-radius: 8px; background: #fff; display: grid; grid-template-columns: 88px minmax(0, 1fr); overflow: hidden; }
.book-card img { width: 88px; height: 100%; min-height: 184px; background: #e7edf2; object-fit: cover; }
.book-copy { min-width: 0; padding: 14px; display: flex; flex-direction: column; }
.book-copy > span, .book-copy small { overflow: hidden; color: #64748b; font-size: 12px; text-overflow: ellipsis; white-space: nowrap; }
.book-copy h2 { margin: 8px 0 4px; overflow: hidden; font-size: 17px; line-height: 1.35; text-overflow: ellipsis; white-space: nowrap; }
.book-copy p { margin: 0 0 8px; overflow: hidden; color: #475569; text-overflow: ellipsis; white-space: nowrap; }
.book-actions { margin-top: auto; display: flex; gap: 8px; }
.pagination-bar { display: flex; align-items: center; justify-content: center; gap: 14px; color: #475569; }
@media (max-width: 760px) {
  .explore-page { padding: max(60px, calc(env(safe-area-inset-top) + 52px)) 14px 24px; }
  .explore-header { align-items: stretch; flex-direction: column; }
  .explore-actions { display: grid; grid-template-columns: minmax(0, 1fr) minmax(0, 1fr); }
  .explore-actions .el-select { width: auto; }
  .book-grid { grid-template-columns: 1fr; gap: 10px; }
  .book-card, .book-card img { min-height: 152px; }
}
</style>
