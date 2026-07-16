<template>
  <el-input
    v-if="isBookSource"
    id="debug-key"
    v-model="searchKey"
    placeholder="搜索书名、作者"
    :prefix-icon="Search"
    style="padding-bottom: 4px"
    @keydown.enter="startDebug"
  />
  <el-input
    id="debug-text"
    v-model="printDebug"
    type="textarea"
    readonly
    :rows="29"
    :loading="debugging"
    placeholder="这里用于输出调试信息"
  />
  <div class="debug-actions">
    <el-button :disabled="!printDebug" @click="printDebug = ''">清空输出</el-button>
  </div>
</template>

<script setup lang="ts">
import API from '@api'
import { Search } from '@element-plus/icons-vue'

const store = useSourceStore()

const printDebug = ref('')
const searchKey = ref('')
const debugging = ref(false)

watch(
  () => store.isDebuging,
  () => {
    if (store.isDebuging) startDebug()
  },
)

const appendDebugMsg = (msg: string) => {
  const debugDom = document.querySelector('#debug-text')
  debugDom!.scrollTop = debugDom!.scrollHeight
  printDebug.value += msg + '\n'
}
const startDebug = async () => {
  if (debugging.value) return
  printDebug.value = ''
  debugging.value = true
  try {
    const saveResponse = await API.saveSource(store.currentSource)
    if (!saveResponse.data.isSuccess) {
      throw new Error(saveResponse.data.errorMsg || '保存书源失败，已停止调试')
    }
  } catch (error) {
    appendDebugMsg(error instanceof Error ? error.message : '保存书源失败，已停止调试')
    debugging.value = false
    store.debugFinish()
    return
  }
  API.debug(
    store.currentSourceUrl,
    searchKey.value || store.searchKey,
    appendDebugMsg,
    () => {
      debugging.value = false
      store.debugFinish()
    },
  )
}

const isBookSource = computed(() => {
  return /bookSource/i.test(window.location.href)
})
</script>

<style lang="scss" scoped>
:deep(#debug-text) {
  height: calc(100vh - 45px - 36px - 5px);
}

.debug-actions {
  display: flex;
  justify-content: flex-end;
  padding-top: 6px;
}
</style>
