# reader-pro 3.2.14 前端源码重建

`reader-pro 3.2.14` Web 前端的源码重建：Vue 2.6 + Vuex + Vue Router（hash 模式）+ ElementUI，
按原产物的 webpack 模块逐模块还原。

## 目录结构

```
index.html                  入口页
manifest.json               PWA manifest
service-worker.js           离线缓存
package.json
css/                        5 个样式文件（app / chunk-vendors / index / reader / setting）
img/                        图标与静态图（18 个）
src/
  main.js                   应用主模块（views / store / router / api 引用入口）
  module-loader.js          模块加载器（webpack require 语义：n / d / r / o / p / e）
  registry.js               组件注册表
  components/               组件定义
  views/                    路由视图（index / reader / setting 等，按原 scope id 分目录）
  utils/                    工具与业务模块（app / index / reader / vendor）
  api/                      接口封装
  store/                    Vuex（state / getters / mutations / actions）
  modules-map.json          模块 id → 路径映射
  component-ids.json        组件 scope id 清单
  graph.json                模块依赖图
```

## 说明

- 源码按原产物的模块 id 组织，`src/module-loader.js` 复刻了 webpack 的 `__webpack_require__` 语义
  （含 `.n()` 返回带 `.a` 的 getter、`.e()` 返回已解析 Promise、`__webpack_require__.p` 为空串）；
- 样式与图片取自同一制品，未做改动；
- 本仓库只含源码与静态资源，不含构建产物、验证脚本或过程文档。

## 运行

需要 Node 16+：

```bash
npm install
npx http-server .    # 或用任意静态服务器指向仓库根目录
```

页面以 hash 路由工作：`#/`、`#/setting`、`#/reader?bookUrl=<url>`；接口默认走同源 `/reader3/*`。

## 许可

GNU General Public License v3.0（与 reader-pro 的 Legado 血统一致）。
