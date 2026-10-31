# 路线图

源码迁移分批进行，排序原则是：每一批都能在下一批依赖它之前，先对着制品完成核验。

## 验收标准

一批只有同时满足以下全部条件才算完成：

1. `./gradlew build` 通过。
2. 该批恢复的每个公开类型与成员，都用 `javap` 对着制品字节码核对过 —— 名称、描述符、修饰符一致。**类名相同不等于方法体同版。**
3. `./gradlew verifyDependencyParity` 没有新增漂移。
4. 不存在空壳方法。要么恢复它，要么不写它。
5. 未被 1–3 覆盖的内容，在该批笔记中记录为**未核验**。

## 还原规则

- **带 `@Metadata` 的类还原为 Kotlin**，放在 `src/main/kotlin`；**纯 Java 来源的保持 `.java`**，
  放在 `src/main/java`。制品是 Kotlin 写的，内联类（`Result`）、内联函数、协程状态机、委托属性
  这些构造在 Java 里根本无法表达——CFR 为它们生成的方法名带连字符（如 `constructor-impl`），
  不是"难改"，而是非法。
- 共同源码骨架只作参考，不直接采用：每个类都要过 `scripts/verify-abi.ps1`。
- 常量内容不在 ABI 范围内，因此涉及字面量（正则、模板字符串）的类要额外比对字节码里的字符串常量。

## 批次

| # | 范围 | 状态 |
| --- | --- | --- |
| 1 | `org.kxml2`、`me.ag2s.epublib`、`me.ag2s.umdlib`、`io.legado.app.lib.icu4j`（86 个类，纯 Java） | 已完成 |
| 2a | `io.legado.app.exception`、`io.legado.app.constant`（13 个类） | 已完成 |
| 2b | `io.legado.app.data.entities`（含 `rule`，24 个类） | 待做 |
| 2c | `io.legado.app.utils`（28 个类） | 待做 |
| 2d | `io.legado.app.help`（含 `coroutine`、`http`，22 个类） | 待做 |
| 3 | `io.legado.app.model.*` | 待做 |
| 4 | `com.htmake.reader.{db,config,entity,utils}` | 待做 |
| 5 | `com.htmake.reader.{api,verticle,init,lib.tts}` | 待做 |
| 6 | `web` / `simple-web` 的前端源码 | 待做 |

第 2 批按包拆成 2a–2d，每片都能独立编译、独立核验、独立提交，保证主干始终可构建。

## 跨批次工作

- **ABI 矩阵。** `scripts/verify-abi.ps1` 把已恢复的类与制品字节码逐类比对（声明、描述符、
  泛型签名）。这是让验收标准 2 可落地的手段，每批迁入都要跑。
- **运行时对比。** 让恢复版服务与制品并排运行（各自隔离的数据目录），逐路由比对响应。
- **核验任务接入。** `verifyDependencyParity` 已接进 `check`；ABI 比对需要在有制品的前提下运行，
  因此保留为独立脚本，不进入默认构建。

## 当前明确不做

- 重建原始 Gradle 文件。它们不在制品里；本仓库的构建是依据制品的依赖集合写的，并如实标注为这样，而不是冒充原构建。
- 制品字节码或资源所触及范围之外的任何行为。

## 当前进度

- 第 1 批（86 个类，纯 Java）已完成。
- 第 2a 批（`io.legado.app.exception`、`io.legado.app.constant`，13 个类，按 Kotlin 还原）已完成。
- 进行中：第 2b 批 `io.legado.app.data.entities`。

逐批记录见 [BATCHES.md](BATCHES.md)。
