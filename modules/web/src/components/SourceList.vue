<template>
  <el-input
    v-model="searchKey"
    class="search"
    :prefix-icon="Search"
    placeholder="筛选源"
  />
  <div v-if="defaultScope || defaultGroup" class="default-filters" aria-label="默认源过滤">
    <el-tag v-if="defaultScope" effect="plain">范围：{{ defaultScope }}</el-tag>
    <el-tag v-if="defaultGroup" effect="plain">分组：{{ defaultGroup }}</el-tag>
  </div>
  <div class="tool">
    <el-button @click="importSourceFile" :icon="Folder">打开</el-button>
    <el-button
      :disabled="sourcesFiltered.length === 0"
      @click="outExport"
      :icon="Download"
    >
      导出</el-button
    >
    <el-button
      type="danger"
      :icon="Delete"
      @click="deleteSelectSources"
      :disabled="sourceSelect.length === 0"
      >删除</el-button
    >
    <el-button
      type="danger"
      :icon="Delete"
      @click="clearAllSources"
      :disabled="sources.length === 0"
      >清空</el-button
    >
  </div>
  <el-checkbox-group id="source-list" v-model="sourceUrlSelect">
    <virtual-list
      style="height: 100%; overflow-y: auto; overflow-x: hidden"
      :data-key="(source: Source) => getSourceName(source)"
      :data-sources="sourcesFiltered"
      :data-component="SourceItem"
      :estimate-size="45"
    />
  </el-checkbox-group>
</template>

<script setup lang="ts">
import API from '@api'
import { Folder, Delete, Download, Search } from '@element-plus/icons-vue'
import {
  isSourceMatches,
  getSourceUniqueKey,
  getSourceName,
  convertSourcesToMap,
} from '@utils/souce'
import VirtualList from 'vue3-virtual-scroll-list'
import SourceItem from './SourceItem.vue'
import type { Source } from '@/source'
import type { AppSettings } from '@/api/api'

const store = useSourceStore()
const appSettings = ref<AppSettings>({})
const sourceUrlSelect = ref<string[]>([])
const searchKey = ref('')
const sources = computed(() => store.sources)
const defaultScope = computed(() => String(appSettings.value.main?.searchScope ?? '').trim())
const defaultGroup = computed(() => String(appSettings.value.main?.searchGroup ?? '').trim())

const splitFilter = (value: string) => value
  .split(/[,;|\n]+/)
  .map(item => item.trim().toLocaleLowerCase())
  .filter(Boolean)

const sourceGroup = (source: Source) => {
  const record = source as Source & Record<string, unknown>
  return String(record.bookSourceGroup ?? record.sourceGroup ?? '').toLocaleLowerCase()
}

const matchesDefaultFilter = (source: Source) => {
  const groups = splitFilter(defaultGroup.value)
  if (groups.length && !groups.some(group => sourceGroup(source).includes(group))) return false
  const scopes = splitFilter(defaultScope.value)
  if (!scopes.length) return true
  const searchable = [getSourceName(source), getSourceUniqueKey(source), sourceGroup(source)]
    .join('\n')
    .toLocaleLowerCase()
  return scopes.some(scope => searchable.includes(scope))
}

onMounted(async () => {
  try {
    const response = await API.getAppSettings()
    if (response.data.isSuccess) appSettings.value = response.data.data
  } catch {
    // Source import remains available with default merge behavior.
  }
})

/* 筛选源 */
const sourcesFiltered = computed<Source[]>(() => {
  const key = searchKey.value
  return sources.value.filter(source =>
    matchesDefaultFilter(source) && (key === '' || isSourceMatches(source, key)),
  )
})
// 计算当前筛选关键词下的选中源
const sourceSelect = computed<Source[]>(() => {
  const urls = sourceUrlSelect.value
  if (urls.length == 0) return []
  const sourcesFilteredMap =
    searchKey.value == ''
      ? store.sourcesMap
      : convertSourcesToMap(sourcesFiltered.value)
  return urls.reduce((sources, sourceUrl) => {
    const source = sourcesFilteredMap.get(sourceUrl)
    if (source) sources.push(source)
    return sources
  }, [] as Source[])
})

const deleteSelectSources = () => {
  const sourceSelectValue = sourceSelect.value
  API.deleteSource(sourceSelectValue).then(({ data }) => {
    if (!data.isSuccess) return ElMessage.error(data.errorMsg)
    store.deleteSources(sourceSelectValue)
    const sourceUrlSelectRawValue = toRaw(sourceUrlSelect.value)
    sourceSelectValue.forEach(source => {
      const index = sourceUrlSelectRawValue.indexOf(getSourceUniqueKey(source))
      if (index > -1) sourceUrlSelectRawValue.splice(index, 1)
    })
    sourceUrlSelect.value = sourceUrlSelectRawValue
  })
}
const clearAllSources = () => {
  store.clearAllSource()
  sourceUrlSelect.value = []
}

//导入本地文件
const importSourceFile = () => {
  const input = document.createElement('input')
  input.type = 'file'
  input.accept = '.json,.txt'
  input.addEventListener('change', () => {
    const files = input.files
    if (files === null) {
      return ElMessage.info('未选择文件')
    }
    const reader = new FileReader()
    reader.readAsText(files[0])
    reader.onload = () => {
      try {
        const jsonData = JSON.parse(reader.result as string)
        if (!Array.isArray(jsonData)) throw new Error('导入内容必须是源数组')
        const imported = jsonData as Source[]
        const existing = convertSourcesToMap(sources.value)
        const preferences = appSettings.value.network || {}
        const merged = new Map(existing)
        const comments: string[] = []
        imported.forEach(source => {
          const key = getSourceUniqueKey(source)
          const local = existing.get(key)
          const next = { ...source } as Source & Record<string, unknown>
          if (local) {
            const localRecord = local as Source & Record<string, unknown>
            const isBook = 'bookSourceName' in source
            const nameKey = isBook ? 'bookSourceName' : 'sourceName'
            const groupKey = isBook ? 'bookSourceGroup' : 'sourceGroup'
            if (preferences.importKeepName === true) next[nameKey] = localRecord[nameKey]
            if (preferences.importKeepGroup === true) next[groupKey] = localRecord[groupKey]
            if (preferences.importKeepEnable === true) next.enabled = local.enabled
          }
          if (preferences.importShowComment === true) {
            const comment = 'bookSourceComment' in next ? next.bookSourceComment : next.sourceComment
            if (typeof comment === 'string' && comment.trim()) {
              comments.push(`${getSourceName(next)}: ${comment.trim()}`)
            }
          }
          merged.set(key, next)
        })
        store.saveSources(Array.from(merged.values()))
        if (comments.length) {
          void ElMessageBox.alert(comments.slice(0, 20).join('\n'), `已导入 ${imported.length} 个源`, {
            confirmButtonText: '确定',
          })
        } else {
          ElMessage.success(`已导入 ${imported.length} 个源`)
        }
      } catch (e: unknown) {
        ElMessage.error('上传的源格式错误: ' + (e as Error).message)
      }
    }
  })
  input.click()
}

const isBookSource = /bookSource/i.test(window.location.href)
const outExport = () => {
  const exportFile = document.createElement('a')
  const sources =
      sourceUrlSelect.value.length === 0
        ? sourcesFiltered.value
        : sourceSelect.value,
    sourceType = isBookSource ? 'BookSource' : 'RssSource'

  exportFile.download = `${sourceType}_${Date()
    .replace(/.*?\s(\d+)\s(\d+)\s(\d+:\d+:\d+).*/, '$2$1$3')
    .replace(/:/g, '')}.json`

  const myBlob = new Blob([JSON.stringify(sources, null, 4)], {
    type: 'application/json',
  })
  exportFile.href = window.URL.createObjectURL(myBlob)
  exportFile.click()
  window.URL.revokeObjectURL(exportFile.href) //avoid memory leak
}
</script>

<style lang="scss" scoped>
.tool {
  display: flex;
  margin: 4px 0;
  justify-content: center;
}

.default-filters {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin: 6px 0 2px;
}

#source-list {
  margin-top: 6px;
  height: calc(100vh - 112px - 7px);
  :deep(.el-checkbox) {
    margin-bottom: 4px;
    width: 100%;
  }
}
</style>
