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

- **类集合适配**（已完成）。`scripts/class-coverage.ps1` —— 制品类集合 − 本仓库已编译类集合。
- **方法级字节码比对**（已完成）。`scripts/verify-bytecode.ps1` —— 归一化指令序列比对，
  已做过一次故意注入改动的验证（确认它会报错），也在全部已编译类上校准过。
- **行为差分**（已起步）。`scripts/diffcheck/ClassDiffCheck.java` —— 两个隔离类加载器 + 制品当 oracle。
  当前覆盖 6 个纯工具类、28 个用例，全通过。后续每还原一个纯函数类就补进去。
- **索引生成**（部分完成）。`class-coverage.ps1 -RosterPath` 已能产出逐类清单；
  "判据结果"一列还要把 ABI 与字节码两个结果并进去。

## 当前进度

由 `scripts/class-coverage.ps1` 产出，不靠口报：

```
制品应用类   : 936        （652 个已编译 + 300 个还缺，另有 16 个多出）
本仓库已编译 : 652
已适配       : 636
多出         : 18         （已知：合成 lambda 命名与制品不同，逐个登记在案）

还缺的类按档位（类 / 顶层类）：
  strict   : 113 类 / 33 顶层类
  loose    : 284 类 / 10 顶层类
  excluded :  12 类 /  2 顶层类

已完成的类按档位：
  strict   : 145
  loose    : 398
  excluded :   2

ABI 闸门（652 个已编译类，其中 16 个制品无此类）：一致 624 / 不一致 12，
11 处 = 2d 批 9 处 + `Book` 的私有字段名（3a-10）+ `AnalyzeRule$ajax$1`
多一个 `L$0` 溢出槽（3a-18）+ `AnalyzeUrl$UrlOption$getHeaderMap$$inlined$fromJsonObject$1`
（GsonExtensions 上游问题，3a-23）。
```
**一个要记住的口径问题**：验证以**类**为单位（ABI 与字节码都是类级），但工作量以
**顶层类（源文件）**为单位。936 个类来自 169 个顶层类——一个顶层类会衍生出协程状态机、
`$default`、lambda 等合成类。所以 936 这个数字会让人高估工作量约 5 倍。

早先我按"严格档约 60–80 个类"估过，那个数字按**类**算是错的（实际 196），
按**顶层类**算才接近（实际 58）。

## 缺口

- 严格档已落地 145 个类（utils 的 6 个严格文件、epublib/umdlib/kxml2、db、config 等）。
- 还有 32 个方法只做到"行为等价"而非"指令一致"（`BaseController` 6、
  `BookSourceController` 10、`FileController` 3、`WebdavController` 6、
  `RssSourceController` 7），逐条列在 `docs/BATCHES.md` 第 3a 批。
- 行为证据仍然为零——这是整个项目最大的缺口，也是阶段二存在的理由。

