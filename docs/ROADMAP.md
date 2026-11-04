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
制品应用类   : 936        （811 个已编译 + 141 个还缺，另有 16 个多出）
本仓库已编译 : 935
已适配       : 913
多出         : 18         （已知：合成 lambda 命名与制品不同，逐个登记在案）

还缺的类按档位（类 / 顶层类）：
  strict   : 113 类 / 33 顶层类
  loose    : 284 类 / 10 顶层类
  excluded :  12 类 /  2 顶层类

已完成的类按档位：
  strict   : 145
  loose    : 398
  excluded :   2

ABI 闸门（811 个已编译类，其中 16 个制品无此类）：一致 784 / 不一致 11，
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

## 字节码层最终状态（2026-11-04）

判据：去掉常量池号 `#NN` 和指令偏移后逐行比。

```
可比较类        : 795（811 已编译 - 16 制品无此类）
指令序列完全一致 : 597（75.1%）
有差异          : 198
```

198 个差异里，**真正能靠源码修的都修完了**（19 个，见 BATCHES 3a-54）。
剩下的全属三类编译器版本差异（1.5.1 vs 1.5.21），源码层面无解：

1. IR 优化策略（1.5.21 会做 CSE / `when` subject 提取 / `$$forInline`）
2. indy lambda 捕获范围（1.5.1 塞全部局部变量，1.5.21 只塞用到的）
3. suspend 参数溢出（1.5.1 存局部槽位，1.5.21 不存）

## 最终状态（2026-11-04 18:25）

```
制品应用类   : 936
本仓库已编译 : 935
已适配       : 913
还缺         : 23   （全是编译器合成类，对应源文件全部存在）
多出         : 22

行为占位     : 0
降级实现     : 0

ABI    : 935 比对 / 870 一致 / 43 不一致 / 22 制品无此类
字节码 : 597 / 795 逐指令一致（75.1%）
```

### 已达成

- **936 个制品类对应的源文件全部存在**，全部有真实逻辑（最深的是 `BookController.kt` 2865 行、
  `YueduApi.kt` 803 行、`LicenseController.kt` 430 行）
- **无行为占位**：最后一个 `saveBookToShelf` 已按 CFR 完整还原
- **无降级实现**：`License.validHost` 的编译崩溃随仓库结构变化自动消失，两处降级已回填
- **19 个类做到字节码逐指令完全一致**（BATCHES 3a-54）

### 剩下的 23 个合成类为什么停手

它们的源文件都在、逻辑都对，缺的只是编译器合成类的**编号/形态**：

- `BookController` 8 个：文件级 lambda 计数器偏移（我们 `lambda-19`，制品 `lambda-16`）
- `SourceAnalyzer` 11 + `BaseSource` 3 + `JsExtensions` 1：`$lambda-N$$inlined$fromJsonObject$N`，
  同一个根因

修它们要**逐文件重构 lambda 序列**，成本远高于收益。这些是编译器产物，不是源码的一部分。

### 43 个 ABI 不一致的构成

- **33 个**：`BookController` 的续体类，私有捕获槽位（`L$0`/`I$3`）对不上
- **9 个**：1.5.21 vs 1.5.1 的 IR 差异（`$$forInline` / `Ref$ObjectRef` 捕获 `val` / `L$0`）
- **1 个**：`Book` 的私有字段名（IR 崩溃，3a-46）

### 如果以后要继续

唯一有意义的动作是**换 Kotlin 编译器**，但 1.5 家族已全部试过（3a-56）：
1.5.0 / 1.5.10 编译崩，1.5.20 差 1 个，1.5.31 差 17 个，**1.5.21 是最优**。
