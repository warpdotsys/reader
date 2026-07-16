<template>
  <el-checkbox
    size="large"
    border
    :value="sourceUrl"
    :class="{
      error: isSaveError,
      edit: sourceUrl == currentSourceUrl,
    }"
  >
    <span class="source-name">{{ getSourceName(source) }}</span>
    <span class="source-toggles" @click.stop>
      <el-switch
        :model-value="source.enabled !== false"
        size="small"
        inline-prompt
        active-text="启"
        inactive-text="停"
        :aria-label="`${getSourceName(source)}启用状态`"
        @click.stop
        @change="(value: boolean | string | number) => updateSourceState({ enabled: value === true })"
      />
      <el-switch
        v-if="isBookSource"
        :model-value="(source as BookSoure).enabledExplore !== false"
        size="small"
        inline-prompt
        active-text="发"
        inactive-text="隐"
        :aria-label="`${getSourceName(source)}发现状态`"
        @click.stop
        @change="(value: boolean | string | number) => updateSourceState({ enabledExplore: value === true })"
      />
    </span>
    <el-button text :icon="Edit" @click="handleSourceClick(source)" />
  </el-checkbox>
</template>

<script setup lang="ts">
import { Edit } from '@element-plus/icons-vue'
import API from '@api'
import { getSourceUniqueKey, getSourceName } from '@/utils/souce'
import type { BookSoure, Source } from '@/source'

const props = defineProps<{
  source: Source
}>()

const store = useSourceStore()

const currentSourceUrl = computed(() => store.currentSourceUrl)
const sourceUrl = computed(() => getSourceUniqueKey(props.source))
const isBookSource = computed(() => 'bookSourceName' in props.source)

const handleSourceClick = (source: Source) => {
  store.changeCurrentSource(source)
}

async function updateSourceState(patch: Partial<Source>) {
  const next = { ...props.source, ...patch } as Source
  try {
    const response = await API.saveSource(next)
    if (!response.data.isSuccess) throw new Error(response.data.errorMsg || '保存源状态失败')
    store.saveSources(store.sources.map(source =>
      getSourceUniqueKey(source) === sourceUrl.value ? next : source,
    ))
    ElMessage.success('源状态已保存')
  } catch (error) {
    ElMessage.error(error instanceof Error ? error.message : '保存源状态失败')
  }
}
const isSaveError = computed(() => {
  const map = store.savedSourcesMap
  if (map.size == 0) return false
  return !map.has(sourceUrl.value)
})
</script>
<style lang="scss" scoped>
:deep(.el-checkbox__label) {
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.source-name {
  min-width: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.source-toggles {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  margin-left: auto;
  padding: 0 8px;
}
.source-toggles :deep(.el-switch) {
  --el-switch-on-color: #0f766e;
  --el-switch-off-color: #94a3b8;
}
.error {
  border-color: var(--el-color-error) !important;
  color: var(--el-color-error) !important;
  --el-checkbox-checked-text-color: var(--el-color-error);
  --el-checkbox-checked-bg-color: var(--el-color-error);
  --el-checkbox-checked-input-border-color: var(--el-color-error);
}
.edit {
  border-color: var(--el-color-dark) !important;
}
</style>
