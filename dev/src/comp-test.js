// 验证：24 个 .vue 能否被工程正确编译和加载
import Vue from 'vue'
import * as COMPONENTS from './components-index'

const names = Object.keys(COMPONENTS).filter(n => n !== 'ImageViewer')
console.log('[comp-test] 组件总数:', names.length)

// 逐个检查是否是可用的组件对象（有 render 或 template 编译结果）
const bad = []
for (const n of names) {
  const c = COMPONENTS[n]
  const ok = c && (typeof c === 'object' || typeof c === 'function')
  const hasRender = c && (typeof c.render === 'function' || typeof c.template === 'string' || c.__file)
  if (!ok) bad.push(n + ': 非对象')
  else if (!hasRender) bad.push(n + ': 无 render/template')
}

const status = {
  total: names.length,
  loaded: names.length - bad.length,
  failed: bad,
}
console.log('[comp-test] 加载结果:', JSON.stringify(status))

// 挂到 window 方便 Playwright 读取
window.__compTest = status
window.__compNames = names
