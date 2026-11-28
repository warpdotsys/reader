<template>
  <div class="placeholder">
    <h2>chunk: {{ chunk }}</h2>
    <p>这里将接入 <code>src/views/{{ chunk }}/</code> 下的真实组件（C3 阶段）。</p>

    <el-alert
      title="工程骨架 + 真实 store 已就位"
      type="success"
      :closable="false"
      description="webpack + vue-loader + element-ui + vue-router + vuex(29 getters / 45 mutations) 全部可用，热更新生效。"
    />

    <el-divider content-position="left">store 实测</el-divider>
    <el-descriptions :column="2" border size="small">
      <el-descriptions-item label="version">{{ $store.state.version }}</el-descriptions-item>
      <el-descriptions-item label="api">{{ $store.getters.api }}</el-descriptions-item>
      <el-descriptions-item label="isNight">{{ $store.getters.isNight }}</el-descriptions-item>
      <el-descriptions-item label="isNormalPage">{{ $store.getters.isNormalPage }}</el-descriptions-item>
      <el-descriptions-item label="hasLogin">{{ $store.getters.hasLogin }}</el-descriptions-item>
      <el-descriptions-item label="字体数">{{ fontCount }}</el-descriptions-item>
      <el-descriptions-item label="主题数">{{ themeCount }}</el-descriptions-item>
      <el-descriptions-item label="书架数">{{ $store.state.shelfBooks.length }}</el-descriptions-item>
    </el-descriptions>

    <div style="margin-top: 16px">
      <el-button type="primary" @click="$message.success('element-ui 正常')">测试 message</el-button>
      <el-button @click="toggleNight">切换夜间（当前 {{ $store.getters.isNight ? '夜' : '日' }}）</el-button>
      <el-button @click="$router.push('/reader')">去 /reader</el-button>
    </div>
  </div>
</template>

<script>
import defaults from '@/config/defaults'

export default {
  name: 'Placeholder',
  props: { chunk: { type: String, default: 'index' } },
  computed: {
    fontCount() { return (defaults.fonts || []).length },
    themeCount() { return (defaults.themes || []).length },
  },
  methods: {
    toggleNight() {
      const cur = this.$store.state.config.theme
      const night = defaults.defaultNightTheme
      this.$store.commit('setConfig', {
        ...this.$store.state.config,
        theme: cur === night ? 0 : night,
      })
    },
  },
}
</script>

<style scoped>
.placeholder { padding: 24px; max-width: 980px; }
code { background: #f4f4f4; padding: 2px 6px; border-radius: 3px; }
</style>
