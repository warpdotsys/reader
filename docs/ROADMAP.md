# 路线图

目标、验收分档、判据与书写纪律见 [GOAL.md](GOAL.md)。本文件只放**会变的东西**：批次与进度。

## 批次

| # | 范围 | 类数 | 档位 | 状态 |
| --- | --- | --- | --- | --- |
| 1 | `org.kxml2`、`me.ag2s.epublib`、`me.ag2s.umdlib`、`io.legado.app.lib.icu4j` | 86 | 宽松 | 已完成 |
| 2a | `io.legado.app.exception`、`io.legado.app.constant` | 13 | 宽松 | 已完成 |
| 2b | `io.legado.app.data.entities`（含 `rule`） | 24 | 宽松（data class） | 8/24 |
| 2c | `io.legado.app.utils` | 28 | 混合：编码相关进严格档 | 待做 |
| 2d | `io.legado.app.help`（含 `coroutine`、`http`） | 22 | 严格 | 待做 |
| 3 | `io.legado.app.model`（含 `analyzeRule`、`webBook`、`localBook`、`rss`） | 34 | 严格 | 待做 |
| 3b | `io.legado.app.adapters` | 3 | 宽松 | 待做 |
| 4 | `com.htmake.reader.{db,config,entity,utils,lib.tts}` | ~30 | 宽松 | 待做 |
| 5 | `com.htmake.reader.{api,verticle,init}` | ~36 | 宽松（含降级候选） | 待做 |
| 6 | `web` / `simple-web` 的前端源码 | — | — | 待做，来源未定（制品里没有） |

严格档合计约 60–80 个类；宽松档约 90 个。第 4、5 批的精确切分待索引建立后确定。

批次只表示**推进顺序**，不表示"每批独立可编译"——`io.legado.app` 内部依赖成环，
编译靠 `-PrestoreBootstrap`，见 [BUILD.md](BUILD.md)。

## 跨批次工作

- **类集合适配**（待做）。制品类集合 − 本仓库已编译类集合 = 待还原清单。它是索引的数据来源，
  也是"完成度"唯一客观的口径。
- **方法级字节码比对**（待做）。`scripts/verify-abi.ps1` 目前只做 ABI；要在它之上加一层归一化
  指令序列比对，严格档靠它。
- **行为差分**（待做，阶段二的闸门）。恢复版与制品各自隔离数据目录并排运行，逐路由比对。
- **索引生成**（待做）。见 [GOAL.md](GOAL.md#索引)。

## 当前进度

- 第 1 批（86 个类）、第 2a 批（13 个类）已完成并核验，在 `main`。
- 第 2b 批进行中（8/24），在分支 `restore/core`。
- 已核验：107 个类 ABI 一致；依赖 85/85 一致。
- **未核验：行为。一条证据都没有**，这是当前最大的缺口。

逐批记录见 [BATCHES.md](BATCHES.md)。
