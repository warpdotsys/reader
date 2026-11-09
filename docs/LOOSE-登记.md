# LOOSE-登记：顽固差异登记表

> 更新：2026-11-10 凌晨 · 基线 v69 = 790/935 类全等
> 原则：差异压不下去的，按实际情况登记 + 写明证据，不硬凑、不编造通过。

## 一、编译器版本发射差异（不可源码修复）

制品编译于 Kotlin 1.5.1（@Metadata 认定），本仓库固定 1.5.21。以下差异经逐条比对确认为
两个编译器版本的发射行为不同，**源码已正确，无法通过改源码消除**：

| # | 现象 | 证据（实例） |
|---|------|--------------|
| 1 | **InlineMarker 帧**：1.5.1 在 inline 调用（withContext 等）前后发 `InlineMarker.mark(0/1)`，1.5.21 不发 | `Coroutine.executeBlock`（且已修可见性差 private/public） |
| 2 | **常量折叠差异**：`KB.toDouble()` 1.5.21 折叠为 double 常量，1.5.1 保留 `ldc2 long + l2d` | `FileUtils.toFileSizeString`（0.907，余差即此） |
| 3 | **字符串拼接折叠**：`"" + fileSize` 1.5.1 会省掉空串 append，1.5.21 保留 `ldc ""; append` | `toFileSizeString` 分支一 |
| 4 | **lazy 属性 getter 空检查**：1.5.21 对 `by lazy` getter 发 `checkNotNullExpressionValue("<get-xxx>(...)")`，1.5.1 不发 | `ReaderApplication$Companion.getVertx` |
| 5 | **方法名 mangle**：对局部函数/值类参数的 callable reference，1.5.21 发 `access$xxx$add-24` 形式，1.5.1 无 `-24` 后缀 | `BookController$ttsByTextToSpeechCn$2`（delta=1 即此） |
| 6 | **override 参数断言**：对 Java 方法 override 的 checkNotNullParameter，两版本发射策略不同 | `ByteConverter.responseBodyConverter / $lambda-0` |
| 7 | **数组循环代码生成**：`for (x in array)` 的展开帧（元素拷贝槽）两版本不同 | `FileUtils.getPath / listDirs / copy`、`StringUtils.getChnMap` |
| 8 | **switch 分支布局 / goto 合并**：同一控制流两版本的 goto 数与位置不同 | `UmdReader.ReadAdditionalSection`（1 个 goto）、`findIndexSet`（delta 5） |

**结论**：第 1–8 类共覆盖剩余差异的大头（约 100+ 类的方法级差异），这是 1.5.1 与 1.5.21
的物理边界。要完全消除需换 SDK 为 1.5.1 —— 用户已明确「接受切换版本带来的大变动」作为可选
路线，尚未执行。

## 二、源码级可修但未完成（需要整方法手术）

| 类/方法 | 状态 | 待办 |
|---------|------|------|
| BookController.getBookContent | 0.58（2197/2101） | 需重构前半段的 `if (isInBookShelf)` 大分支 + 状态机场外变量（`aload 30; putfield` 溢写） |
| BookController.getChapterList | 800/788，Δ+2 | 定位多出的 2 个局部（疑似两个中间 val 的形态） |
| BookController.getBookInfo | 496/512，Δ+2 | 同上（9/10 槽两个未内联临时） |
| BookSourceController.saveBookSource | 274/287 | 差在 30+ 处块，需逐块过 |
| UserController.logout / uploadFile / deleteUsers | Δ-1/+1 | 各 5-7 块差异（as?/as 形态、槽位） |
| WebdavController.webdavUpload/Move/Copy/Lock | Δ-1 | 各 1-4 块 |
| ExtKt.sendEmail / decryptToLicense | Δ+1/-1 | 小块 |
| 其余 42 个槽位偏移名单 | — | 见 `scan_slots.py` 输出（scratch/bc/slot_shift.json） |

## 三、已定性但写法无法复现（登记认账）

| 类/方法 | 结论 |
|---------|------|
| FileController.mkdir | 制品 `home` 的静态类型不是 String（append(Object)），kotlin 原生 `Any? +` 无法编译 |
| NotifyLite 等 `?: false` 家族 | 条件链单双跳，源码形态已达极限 |
| `"" + System.currentTimeMillis()` 类 | kotlinc 优化方向不一致 |

## 四、项目收口状态

- **类名集合**：936/936 = 100%
- **类全等**：790/935 = 84.5%
- **行为回归**：双实例 HTTP 差分 84/84 通过（唯一差异 = freeMemory 波动假阳性）
- **源码覆盖**：全部类有源码；方法逻辑通过反编译核验
- **构建**：`./gradlew classes` 全绿；`docs/RESUME.md` 含 40+ 条实证规律
- **待推**：无（全部同步到 origin/restore/core）
