# simple-web（电纸书 UI）

本目录由 `D:/Projects/reader-simple-web/` 构建产出，**不要直接改这里**。

## 来历

原制品里本目录只有压缩后的产物（`readerPage-8f8136bb4d.js` 等，共 24 个文件、约 152 KB 压缩 JS）。
2026-11-28 已把全部压缩产物恢复成可读源码：

| 文件 | 恢复后行数 | 职责 |
|---|---|---|
| `src/common.js` | 975 | Zepto 封装、请求、通用工具 |
| `src/readerPage.js` | 1,834 | 阅读器核心：分页、章节、目录、字体、缓存 |
| `src/rssPage.js` | 743 | RSS 阅读 |
| `src/searchPage.js` | 371 | 搜索 |
| `src/template.js` | 83 | 模板渲染 |
| `src/indexPage.js` | 90 | 书架首页 |
| `src/polyfill.js` | 22 | 垫片 |

合计 **4,118 行**。

## 构建

```bash
cd D:/Projects/reader-simple-web
node build.mjs            # 展开态产物（默认）
node build.mjs --minify   # 压缩产物
```

产物输出到 `dist/`，再复制到本目录。

## 与主 SPA 的区别

- 主 SPA：`web/`（Vue 2，源码在 `D:/Projects/reader-web-recovered/src/`）
- 电纸书 UI：本目录（Zepto + 命令式 DOM，面向墨水屏，带 3 套中文字体）

## 访问

`/simple-web/` 需要许可证的 `simpleWebExpiredAt` 未过期，否则返回 403「未激活该功能」。
