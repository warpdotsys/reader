/**
 * 路由表：AST 切片自 js/app.54619a3e.js 的 new VueRouter({ base: "", routes })
 * 证据：REA javascript-routes 视图 routes=/、/reader、/setting；无 mode 字段 => 默认 hash 模式
 */
const routes = [
  {
    'path': '/',
    'name': 'index'
  },
  {
    'path': '/setting',
    'name': 'Setting'
  },
  {
    'path': '/reader',
    'name': 'Reader'
  }
]

// component: 产物里的 chunk 懒加载表达式（逐字，作为注释保留）
// / (index) -> function() { return Promise.all([o.e("index~reader"), o.e("index")]).then(o.bind(null, "d504")) }
// /setting (Setting) -> function() { return o.e("setting").then(o.bind(null, "4ef5")) }
// /reader (Reader) -> function() { return Promise.all([o.e("index~reader"), o.e("reader")]).then(o.bind(null, "968f")) }

// 组件映射：从产物 chunk 懒加载还原（index/index~reader/setting/reader）
export const componentChunks = {
  "/": "function() { return Promise.all([o.e(\"index~reader\"), o.e(\"index\")]).then(o.bind(null, \"d504\")) }",
  "/setting": "function() { return o.e(\"setting\").then(o.bind(null, \"4ef5\")) }",
  "/reader": "function() { return Promise.all([o.e(\"index~reader\"), o.e(\"reader\")]).then(o.bind(null, \"968f\")) }"
}

export default routes
