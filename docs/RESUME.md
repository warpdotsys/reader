# 恢复清单（断电/中断后从这里接）

> 最后更新：2026-11-04 11:45
> 本地工作笔记，不进 git。**恢复后直接续做，不等用户发话。**

## 当前策略（用户 2026-11-04 明确要求：不计代价要百分百源码）

**目标是 936 个类全部有源码，ABI 恶化可接受。** 之前四次 BookController 尝试都是因为
「ABI 不能恶化」的硬约束而回滚，**这个约束已经解除**。

- 基线分支：`backup/abi10-baseline`（ABI 10 / 811 类时的状态，随时可回）
- 另一条备份：`backup/before-kotlin-switch`

## 进行中：BookController（51 个占位方法）

```
BookController* 类数: 73 / 制品 114（缺 52、多 11，实际对上 62）
```

**核心机制（已实测验证）**：占位方法只生成孤立的主类方法，不生成续体类。
把方法体按 CFR 写成真实实现（带 suspend 调用）后，`$方法名$1` 这类内嵌类自动出现。

例：`getBookshelf` 还原成真实实现（含 `checkAuth(context)` 和 `getBookShelfBooks(...)`
两个 suspend 调用）后，`BookController$getBookshelf$1.class` 就生成了。

**待还原的方法**（搜 `TODO(restore)` 或 `"未恢复"`）：
`addBookGroupMulti`, `backupToMongodb`, `cacheBookOnServer`, `cacheBookSSE`,
`getAvailableBookSource`, `getBookCover`, `getBookShelfBooks`,
`getBookSourceStringBySourceURLOpt`, `getLastBackFileFromWebdav`, `getLocalChapterList`,
`getShelfBook`, `getShelfBookByURL`, `refreshLocalBook`, `removeBookGroupMulti`,
`restoreFromMongodb`, `saveBook`, `saveBookConfig`, `saveBookCover`, `saveBookGroupId`,
`saveLocalBookCover`, `saveShelfBookLatestChapter`, `saveShelfBookProgress`, `saveToWebdav`,
`searchBookSource`, `searchBookSourceSSE`, `searchBookWithSource`, `setBookSource`,
`setEpubContent`, `syncBookProgressFromWebdav`, `syncFromWebdav`, `textToSpeech`,
`ttsByTextToSpeechCn` 等

## 关键约束

- `FileController.kt:521` 已改成 `result.first.inShelf`（配合 BookController 源码化），**别改回去**
- `Book.kt` 的 `inShelf` **不能**改成 `isInShelf`（IR 崩溃，3a-46）
- 每次必须 `rm -rf build` 全量编译（增量编译报 conflicting dependencies）
- `git` 用 `C:/Program Files/Git/cmd/git.exe`
- 构建：`gradlew.bat classes "-PrestoreBootstrap=D:\Projects\reader\BOOT-INF\classes" --no-daemon`，
  `JAVA_HOME=D:\Tools\java\jdk-11.0.8+10`

## 已完成 / 已封存

**ABI 10 / 811 类 / 字节码 75.1%**（`backup/abi10-baseline` 那个状态）：
- `analyzeRule`、`webBook`、`localBook`、`entities`、`controller`（除 BookController）、
  `api`（YueduApi 148 类）等全部完成
- 19 个类做到字节码逐指令完全一致（BATCHES 3a-54）

**已试过、不要再试的**：
| 尝试 | 结果 | 记录 |
|---|---|---|
| Kotlin 1.5.0 / 1.5.10 | 编译崩（`@Transient` getter） | 3a-56 |
| Kotlin 1.5.20 | ABI 11（比 1.5.21 差） | 3a-56 |
| Kotlin 1.5.31 | ABI 27（大幅恶化） | 3a-56 |
| `Book.kt` 改 `isInShelf` | IR 崩溃 | 3a-46 |
| `License` 换 Java 源 | 丢 `@Metadata`，ABI 必崩 | 3a-47 |
| 去掉 `-Xjsr305=strict` | 零改善 | 3a-41 |
| BookController 直接开写 / 脚本生成骨架 | 类名 71/114、54/114 | 3a-39 / 3a-43 |

## 剩余 141 个没源码的类

- **BookController 114 个**（进行中）
- **零散 27 个**：`LicenseController` 10 + `LicenseControllerKt` 2（卡 `License.validHost` 编译崩溃）、
  `SourceAnalyzer` 11 + `BaseSource` 3 + `JsExtensions` 1（卡文件级 lambda 编号）

## 逐文件重构的进展与边界（2026-11-05 03:35）

### 已完成

| 文件 | 进度 | 关键点 |
| --- | --- | --- |
| `JsExtensions` | **1/1 全清** | `connect(urlStr, header)` 用 `GSON.fromJsonObject` 解析 header 传给 `AnalyzeUrl.headerMapF` |
| `BaseSource` | **2/3** | `loginHeaderMap`/`loginInfoMap` 是**接口方法**不是属性（属性 getter 的内联 lambda 叫 `special`） |
| `BookController` | **2/8** | `getBookSourceStringBySourceURLOpt`（Jackson 流式）+ `getLocalChapterList`（网络书源逻辑） |

### 关键规则（这轮吃透的）

**`$lambda-N` 数的是「所有 lambda 表达式」，不是「生成的类」。**

`let` / `forEach` / `runCatching` 这些 inline 函数的 lambda **不生成类，但占编号**。
实测：带 `getOrNull()?.let { }` 时生成 `$4`，改成 `if (!x.isNullOrEmpty())` 后变成 `$3`。
一个 `let` 正好差一个号。

**另一个隐蔽的坑：参数名写错，ABI 闸门查不出来。**

`getLocalChapterList(book, bookSource, refresh, userNameSpace, debugLog, mutex)` 被我写成
`(book, bookSource, useCache, userNameSpace, refresh, mutex)`——第 3 个是 `refresh` 不是
`useCache`，第 5 个是 `debugLog` 不是 `refresh`。JVM 描述符完全一样
（`(Book,String,boolean,String,boolean,Mutex)`），**ABI 闸门一个字都查不出来**。

### 剩余（都卡在编译器行为，不建议继续）

- **`BookController` 6 个**：前置 lambda 计数差 1/3、SAM 转换被 1.5.21 优化掉
- **`SourceAnalyzer` 11 个**：编号规则比「文件级/方法级」更复杂，需要 controlled experiment 才能确定
- **`BaseSource` 1 个**：`getHeaderMap$lambda-4$lambda-2` 需要两层 lambda 嵌套

**要真正解决，得先做 controlled experiment**：写几个最小样例，测出 `$lambda-N` 在
「`runCatching` 包裹 + 内联高阶函数 + SAM 转换」组合下的真实编号规则。

### 最终状态

```
制品应用类   : 936
本仓库已编译 : 935
已适配       : 913
还缺         : 21（BookController 6 + SourceAnalyzer 11 + BaseSource 1 + 其他 3）
行为占位     : 0
降级实现     : 0
```
