# 续接说明（2026-11-07 04:10）

## 用户目标（明确、无妥协）

**最终要 100% 对得上的源码，无论多费劲。** 不接受"编译器版本差异"作为永久借口。
ABI 一致性已经不重要，类名和字节码三重对齐才是目标。

## 当前状态

```
制品应用类   : 936
已适配       : 930  (99.36%)   还缺 6 / 多出 3
字节码完全一致: 772 / 930 (83.0%)   有差异 158 个类
HEAD         : 见 git log（本轮最后提交在 3a-97 之后）
D 盘可用     : 92G（清掉 handwriting-yolo-cache/uv 的 90G uv 缓存后）
```

**注意：D 盘曾两次写满导致编译失败。** 真凶是 `D:/Projects/handwriting-yolo-cache/uv`
（uv 包缓存，实际 90G，PowerShell 的 Measure-Object 会算成 3.27G）。
它可随时删，不是项目数据。**不要碰** `xwechat_files`（微信数据，用户明确要求保留）。

## 三层验收脚本

```bash
# 类名覆盖
powershell -NoProfile -Command "& { .\scripts\class-coverage.ps1 -Artifact 'D:\Projects\reader\BOOT-INF\classes' }"

# 字节码（我自己写的 Python 版，比 PowerShell 快）
python scripts/bytecode-diff.py --artifact 'D:/Projects/reader/BOOT-INF/classes' \
  --local-dir 'D:/Projects/reader-repo/build/classes/kotlin/main' \
  --local-dir 'D:/Projects/reader-repo/build/classes/java/main' \
  --jobs 10 --out 'C:/Users/chong/AppData/Local/hermes/cache/scratch/bc/latest.json'

# ABI
powershell -NoProfile -Command "& { $env:JAVA_HOME='D:\Tools\java\jdk-11.0.8+10'; .\scripts\verify-abi.ps1 -Artifact 'D:\Projects\reader\BOOT-INF\classes' -JavaHome $env:JAVA_HOME }"
```

**每次改代码必须 `rm -rf build` 全量编译**，增量编译会报 conflicting dependencies。

## 已确认的「编译噪声」判据（命中就跳过，不要浪费时间）

1. **`trim()` 内联**：制品字节码里 `CharSequence.charAt` + `Intrinsics.compare(c, 32)`
   → 1.5.1 把 `trim()` 当 `@InlineOnly` 展开 60+ 条，1.5.21 只发 `StringsKt.trim`。
   **自动检测**：`'CharSequence.charAt' in body and 'Intrinsics.compare' in body`
2. **`nop`**：编译器的行号占位，两侧插入点不同
3. **常量池编号** `#123`：分配顺序不同，同一字符串可能 #9/#10
4. **局部变量槽位偏移**：`astore 23` vs `astore 21`，改一处会整体位移
5. **`checkNotNullExpressionValue`**：jsoup 等 Java 库的 `@NotNull` 在 `-Xjsr305=strict` 下的处理差异

## 已确认的「真差异」判据

1. **`invokeinterface Map.xxx` vs `invokevirtual HashMap.xxx`** → 局部变量声明类型不同
2. **`String.valueOf(x)` vs `x.toString()`** → 前者是字符串模板 `"$x"`
3. **`ldc "$this$xxx"` vs `ldc "it"`** → 带接收者的 lambda `T.() -> Unit` vs 普通单参
4. **制品多 20+ 条 + `astore`/`goto`/`LogUtils`** → 缺 `try/catch`
5. **`StringsKt.isBlank`** → 源码写 `isNullOrBlank()` 而不是 `== null`
6. **`new cn/hutool/crypto/symmetric/AES`** → 直接用了 hutool，不是包一层 EncoderUtils
7. **`astore` + `aload` vs `dup`** → 制品先把中间结果命名存变量，我们写链式
8. **`data class` 自动生成 vs 手写**：比对方法条目数，182 条 = 自动生成全字段，
   4/29 条 = 手写只比部分字段。**两个方向都可能错**（BookSource 该删手写，
   RssArticle 该加手写）
9. **`aload_n` → `astore m` → `aload m` → 比较** = `when` 多分支（subject 只求值一次），
   不是 `if`。`DB$Companion.table` 就是这条，改成 `when` 后归零。
10. **`aconst_null` 后有无 `checkcast`**：有 = if-else 表达式分支返回 null；
    无 = 语句级 `return null`。
11. **跳转目标偏移不是差异**：`ifle 49` vs `ifle 48` 只是字节偏移不同。
    `norm()` 已归一化，别再手工判。
12. **`delta` 大不等于差异多**：一条指令插入/删除会让后面全部平移，
    delta 立刻虚高到「方法总量级」。`Relator.byCode` 2669 条指令 delta 2652，
    真实差异只有 1 处 2 条。**必须用 `difflib.SequenceMatcher` 对齐后看差异块规模。**
    扫描脚本 `…/scratch/bc/scan_blocks2.py`（每类只跑 2 次 javap）。
13. **本地槽位号整体比制品大 N → 先找「多出来的局部变量」**：
    最常见是链式调用存了中间对象。`val res = f(); res.body` 改成
    `val body = f().body`（只存最终结果），槽位就少一个，后面全部对齐。
14. **三元表达式 vs if/else 语句**：三元结果压栈（无 `astore`），
    if/else 语句每个分支都要 `astore`。制品在 `ldc ""` 后多一条 `astore_3`
    → 源码是 if/else 不是三元。
15. **`ifnull` 出现在 `?.` 链之前 = `x == null || x?.y` 的短路形式**：
    `!!` 不产生 `ifnull`（走 `dup` + `checkNotNull` + NPE）。
16. **`dup_x1` 紧跟 `putfield` = 赋值表达式**（`a.field = value` 的结果被继续使用）。
    如果 `putfield` 后又 `aload/getfield` 读同一字段，说明该合并成赋值表达式。
17. **CFR 生成的「未使用局部变量」是 `dup` 的元凶**：
    `boolean bl = ignoreSpace = mb == x;`（`bl` 后面从不用）会多一条 `dup`。
    制品没有 `dup` → 原始源码就是单赋值 `ignoreSpace = mb == x;`。
    **看到未使用变量 + `dup`，优先怀疑 CFR 产物。**
18. **`ldc_w` vs `ldc` 是假阳性**：`ldc_w` 只是常量池索引宽度（编号 > 255 用宽指令），
    `norm()` 已归一化。**这是统计器第四次假阳性**（前三次：常量池编号、`nop`、跳转目标偏移）。
19. **方法末尾多出 `iconst_1 / ireturn`**：本地写成了「末尾 `return true`」，
    制品是单表达式（`return a == b || (...)`)只有一 return 点。
20. **多余的 `override` 会改变字节码**：接口已有默认实现时，子类再写一遍
    `override val x get() = this` 会从 `invokestatic DefaultImpls.getX` 变成 `checkcast`。
21. **局部变量的声明顺序决定槽位号**：`String origin; String url2;` 和
    `String url2; String origin;` 生成的 `astore_1`/`astore_2` 完全对调
    （`TTSService.getOrCreateWs`）。
22. **`continue` 在 for-each 里批量改要逐个看**：`Resources` 8 个方法一次改完
    6 处 `continue` + 2 处 `ArrayList`，全清。
23. **捕获字段 `Ljava/lang/Long;` vs `J`**：变量声明缺显式类型，Kotlin 把
    Java 平台类型推断成了可空。加 `: Long` / `: Int` 能拉回基本类型
    （`addBookGroupMulti` / `removeBookGroupMulti` 各一处，合计 2 个类归零）。
24. **捕获字段 `Ref$ObjectRef` = 源码里是 `var`**；直接是具体类型 = `val`。
    `RemoteWebview$getStrResponse` 的两个变量改 `var` 后指令 23/23 对齐
    （只剩捕获字段声明顺序差一位）。
25. **`tableswitch` 的跳转表是纯数字行**（`"  109"` / `"  109: 179"`），
    全是偏移不是指令。统计器第五次修正，`norm()` 已过滤。
26. **`checkcast` 的位置反映「哪一层是表达式、哪一层是块」**：
    出现在内层 else → 内层是块、外层是表达式；出现在外层 else → 外层是块。
    三个 TypeAdapter 就是靠这条精确到「差一个括号」搞定的。
27. **lambda 参数名进字节码**（`ldc "it"` vs `ldc "node"`）；
    **局部变量声明顺序决定槽位**；**`!!` 生成 `dup`+`checkNotNull`，
    `as X` 只生成 `checkcast`**。
28. **`const val TAG` 不发 static 字段**：`const` 是编译期常量会内联，
    制品有 `<clinit>` 里的 `putstatic TAG` 就说明是普通 `val`。
    **`javap -p` 只比方法名集合，查不出缺字段。**
14. **`while (i < n)` vs `for (i in 0 until n)`**：前者 `goto` 回循环头，
    后者 `if_icmpge` 前置 + `iinc` + `if_icmplt` 回跳。
15. **`checkNotNullParameter` 数量对不上 → 查类型参数上界**：
    `<P>`（可空）不检查，`<P : Any>`（非空）检查。这是 API 契约差异。
16. **`Object.equals` vs `Intrinsics.areEqual`**：显式 `.equals()` 前者，
    Kotlin 的 `==` 后者。**且看求值顺序判断谁是左操作数**。
17. **`Element.tagName()` vs `Element.tag().name`**：语义相同，字节码不同。
18. **参数名和类型是硬信息**：`exportBook` 的 `isEpub: Int` 被我们写成
    `type: String`，不是实现差异而是**接口不兼容**，delta 473 全在参数解析段。
    **比方法体之前先核对参数名/类型/返回方式。**

## 剩余 210 个有差异的类（按 delta 降序）

```
17918  com.htmake.reader.api.controller.BookController     ← exportBook 已归零，10 个方法待修
 2691  com.htmake.reader.api.YueduApi
 2653  me.ag2s.epublib.domain.Relator
 2275  io.legado.app.utils.Base64$Decoder
 1808  com.htmake.reader.utils.ExtKt
 1746  io.legado.app.help.BytesEncodingDetect
 1647  com.htmake.reader.api.controller.UserController
 1445  io.legado.app.model.webBook.BookChapterList
 1263  io.legado.app.model.localBook.TextFile
 1140  io.legado.app.model.analyzeRule.RuleAnalyzer
 1087  com.htmake.reader.api.controller.LicenseController
 1087  io.legado.app.lib.icu4j.CharsetRecog_sbcs$NGramParser_IBM420
 1048  com.htmake.reader.api.controller.BookSourceController
 1043  org.kxml2.io.KXmlParser
 1040  io.legado.app.utils.Base64$Encoder
 1023  io.legado.app.utils.StringUtils
  920  com.htmake.reader.api.controller.BaseController
  903  io.legado.app.model.localBook.EpubFile
  869  io.legado.app.model.webBook.BookInfo
  850  io.legado.app.help.coroutine.Coroutine$executeInternal$1
```

## BookController 的 12 个待修方法（改前数据）

```
getBookContent       1830  2208/2092   ← 差 116
getLocalChapterList   735   773/697    ← 差 76
searchBookSourceSSE   694   825/862    ← 本地多 37
cacheBookOnServer     640   736/537    ← 本地少 199（只补了 launch 外壳）
searchBookContent     615   684/501    ← 本地少 183
getChapterList        609   809/781
cacheBookSSE          568   808/705
searchBookMultiSSE    564   796/794
searchBookMulti       533   661/651
searchBookSource      514   616/645
exportToEpub          482   → 已归零（3a-81）
exportBook            473   → 已归零（3a-87，参数应是 isEpub: Int 不是 type: String）
```

## 工作方法（已验证有效）

1. **不要猜**：拿 `javap -c` 两侧对比，找第一条不同指令
2. **先分类再动手**：用 `bytecode-diff.py` 看方法级 delta，判断是"缺逻辑"还是"编译噪声"
3. **改完必须全量编译**（`rm -rf build`），1.5~3.5 分钟
4. **一批改完跑一次校验**，记录到 `docs/BATCHES.md`（当前已到 3a-81）
5. **每次提交都写清楚改了什么、为什么、delta 变化**

## 不要做的事

- 不要为了字节码一样而写反直觉的代码（比如手写一遍 trim）
- 不要忽略"本地多"的情况（RssArticle 就是这么发现的）
- 不要相信 `$i$f$xxx` 局部变量作为 inline 的证据（3a-70 的教训，类名才是证据）
- 不要用增量编译

## 提交规范

```bash
G="C:/Program Files/Git/cmd/git.exe"
"$G" add -A
"$G" -c user.name="Hermes" -c user.email="hermes@local" commit -m "中文说明 + delta 变化"
"$G" push origin restore/core
```

push 偶尔会 `Internal Server Error`，等 30-60 秒重试即可。

## 已确认「试过但做不到」的清单（不要再重复投入）

| 类 / 方法 | 原因 |
| --- | --- |
| `ReaderApplication$Companion.getVertx` | 1.5.21 给 `by lazy` getter 多加了 `checkNotNullExpressionValue` |
| `ByteConverter.responseBodyConverter` | Java 平台类型参数：制品无 `checkNotNullParameter`，改可空又 overrides nothing |
| `Utf8BomUtils` / `UTF8BOMFighter` | 1.5.21 的 CSE 把 `Charsets.UTF_8` 提取成局部变量 |
| `VertExtKt.success` | 1.5.21 提取 `when` subject（`astore_3`/`aload_3`） |
| `BookController$setBookSource$2` | 6 个 `checkNotNullExpressionValue`（1.5.21 平台类型检查） |
| `Coroutine.executeBlock` | 加 `inline` 后 delta 28→29（`InlineMarker.mark` 形态不同） |
| `BookController$textToSpeech$2` | `text.element!!` 存变量后 97→115 |
| `CURD$deleteMulti$2` | lambda 参数名改 `it` 后 45→44（索引循环 vs for-each 没解决） |
| `LicenseController$importLicense$2` | `getJsonObject("data")` 存变量后 45→171 |
| `BookController$getBookShelfBooks$2$1` | `book.element!!` 存变量后 38→40 |
| `BookController$syncBookProgressFromWebdav$2` | 4 个 `!!` 改局部变量后 42/34（原 42/50） |
| `BaseBook$DefaultImpls.getKindList` | `wordCount?.let{!it.isBlank()}` 改 `!isNullOrBlank()` 后 delta 不变（76） |
| `TextFile$Companion.getContent` | `Regex("...")` 的字符串存变量后 21→37 |
| `RssArticle.equals` | `if (other is ...)` 结构后 27→23 |
| `NCXDocumentV2.readTOCReferences` | 试了 `&&`、嵌套 if，编译产物一样，都差 `goto` |
| `CharsetRecog_euc.nextChar` | 试了 `else{if}`、调换 if 顺序，都没改善 |
| `Base64$Encoder/Decoder` | 差异在 `switch(state)` 的 case 布局 |
| `UmdReader.ReadAdditionalSection` | `switch` 空 case 布局 |
| `ResourcesLoader.loadResources` | 22 块槽位 + `LazyResource` 构造位置 |
| `CharsetRecog_UTF8.match` | 槽位链（本地少一个变量），关系难解 |
| `EncodingDetectHelp` | 槽位 + `dup` vs `astore` |
| `OkHttpUtilsKt$await$2$2` / `Book$Converters` | `runCatching` 在 lambda 里的展开形态（1.5.21 多一层局部变量） |
| `AttemptResult` | inline 类 + `var` 占位 + `getError()` 调用 |

## 方法：字段集合扫描能发现真 bug

**`javap -p` 的方法名集合几乎全部对齐（只差 1 个 SAM 桥），但字段集合还有 11 个类不一致。**

扫法：对每个差异类 `javap -p`，取「类体里、方法签名之前」的字段行，
对比制品的 `字段名:类型` 集合和本地的。

发现的问题分两类：

| 类 | 差异 | 性质 |
| --- | --- | --- |
| `Book` | 制品 `isInShelf` / 本地 `inShelf` | **ABI 硬差异**，已修 |
| `BookSourceController$updateRemoteSourceSub$2` | 制品捕获 `$i`/`$remoteBookSource`，本地没有 | **逻辑缺失 bug**，已修 |
| `BookController$cacheBookSSE$3` | 制品 `$localCacheDir`/`$isEnd`，本地 `$response`/`$returnData` | 实现不同，未修 |
| `BookController$exportToTxt$2` | 制品 `$bookFile`+`this$0`，本地 `$writer` | 实现不同，未修 |
| `BookController$getAvailableBookSource$2` | 本地 `$bookRef` vs 制品 `$book` | 变量名，已修 |
| `BookController$getBookShelfBooks$2` | 本地多 `L$1`/`L$2` | 续体槽位 |
| `AnalyzeRule$ajax$1` / `JsExtensions$connect$1` / `$ajaxAll$1` | `L$0`/`I$3` 槽位 | 续体槽位 |
| `JsExtensions$queryTTF$font$1` | 本地多 `$cacheInstance` | 捕获多余变量 |
| `BookController$restoreFromMongodb$handler$1` | 本地多 `this$0` | 多用了成员方法 |

**`updateRemoteSourceSub` 的 bug 值得单独说**：

```kotlin
// 复原错的：
saveUserStorage(userNameSpace, "remoteBookSourceSub",
    "remoteBookSourceList.set(i, remoteBookSource.put(\"lastSyncTime\", System.currentTimeMillis()))")
// 第三参数是个字符串字面量，set/put 根本没执行
```

**远程书源的 `lastSyncTime` 永远不会更新** —— 这是功能性 bug，不是字节码差异。
发现它的唯一线索就是「制品 lambda 捕获了 `$i`，本地没有」。

**教训：字节码差异有时指向的是逻辑缺失，不只是编译形态。**

## Kotlin 编译器版本对照实验（1.5.0 / 1.5.10 / 1.5.20 / 1.5.21 / 1.5.31）

用户授权切换编译器版本后做的完整矩阵（每行都是全量编译 + 全量对比的实测数字）：

| Kotlin 版本 | exact/compared | 百分比 | 结论 |
| --- | --- | --- | --- |
| 1.5.0 | 748/930 | 80.4% | 差 |
| 1.5.10 | 748/930 | 80.4% | 差 |
| **1.5.20** | **772/930** | **83.0%** | 与 1.5.21 完全等价 |
| **1.5.21** | **772/930** | **83.0%** | 当前采用 |
| 1.5.31 | 747/930 | 80.3% | 差 |

**不能装 1.5.1**：`org.jetbrains.kotlin.jvm:1.5.1` 这个插件版本在 Gradle Plugin Portal 和 Maven Central 都不存在
（Kotlin 插件只发布过 1.5.0 / 1.5.10 / 1.5.20 / 1.5.21 / 1.5.30 / 1.5.31 这几个版本）。
报错：`Plugin [id: 'org.jetbrains.kotlin.jvm', version: '1.5.1'] was not found`。

**关键认知纠正**：`@Metadata(mv={1,5,1})` 里的 `mv` 是 **metadata 格式版本**，不是编译器版本。
Kotlin 1.5.0 到 1.5.31 **全都输出 `mv={1,5,1}`**，所以「制品 mv=1.5.1」不能推出「制品用 1.5.1 编译」。

真正的结论：制品的字节码特征与 **1.5.20 / 1.5.21** 最吻合（772/930），
1.5.0/1.5.10/1.5.31 都明显更差（747-748/930），说明制品是 1.5.2x 线编译的。
**当前 1.5.21 是最优选择，不需要再切换版本。**


## 2026-11-05 夜间：HTTP 层差分测试（黑盒行为验证）

用户要求"对所有都做差分测试，样本全一点，复杂引擎样本少了看不出差异"，由此建立了完整的四层验证体系。

### 四层验证现状

| 层 | 覆盖 | 结果 |
| --- | --- | --- |
| 类名集合 | 936 个类 | **936/936 完全对齐** |
| 字节码指令 | 934 个可比类 | 775/934 = 83.0% |
| 反射差分（无参+带参+private） | 约 22600 个测试点 | 真差异 1 个边界（`listFiles("")`） |
| **HTTP 端到端** | 133 条路由 / 223 项 | **常规 84 通过 / 1 假阳性；特殊 15 通过 / 3 假阳性** |

### 双实例 HTTP 差分的搭建方法

```bash
# 制品实例（工作目录不同，避免互相干扰）
java -Xmx512m -cp "D:/Projects/reader/BOOT-INF/classes;D:/Projects/reader/BOOT-INF/lib/*" \
     com.htmake.reader.ReaderApplicationKt \
     --reader.server.port=8830 --reader.app.workDir="D:/bench/art"

# 本地实例
java -Xmx512m -cp "build/classes/kotlin/main;build/classes/java/main;build/resources/main;D:/Projects/reader/BOOT-INF/lib/*" \
     com.htmake.reader.ReaderApplicationKt \
     --reader.server.port=8831 --reader.app.workDir="D:/bench/loc"
```

工具在 `scripts/difftest/`：
- `http-diff.py` —— 常规 JSON 路由，内置 MUTATING 正则跳过会改状态的接口
- `http-diff-special.py` —— 二进制/文件类比状态码+类型+长度+sha256，SSE 类读前 5 个事件
- `routes-full.txt` —— 从 YueduApi.kt 提取的 120 条可测路由

**关键坑**：顺序执行时 `saveBookSource` / `deleteBookSource` 这类 POST 会改服务端状态，导致后续 GET 读到污染数据，且两实例状态不同步，产生假差异。必须用 MUTATING 正则跳过。

### 这轮抓到的 14 个真 bug

**最严重的：`AppConfig` 缺注解**

```kotlin
// 制品有，我们没有
@Component
@ConfigurationProperties(prefix = "reader.app")
open class AppConfig {
```

后果：`YueduApi` 注入 `AppConfig` 失败，**Spring 上下文直接退出，本地构建根本起不来**。
前面 22600 个类级测试点全过都测不出来——它们不需要 Spring 上下文就能跑。

**逻辑完全相反的：`restoreFromMongodb`**

```kotlin
// 制品：清理本地缓存的备份文件
val handler = { userNameSpace: String ->
    syncDataFileList.forEach { fileName ->
        val file = File(getWorkDir("storage", "data", userNameSpace, "$fileName.json"))
        if (!file.exists()) return@forEach
        file.delete()
    }
}
handler("default")
if (appConfig.secure) { 遍历 users，对每个用户名调 handler }

// 我们原来：从 mongo 读内容再写本地 —— 方向相反
```

另外缺了 `checkManagerAuth(context)` 管理密码校验。

**参数名不兼容的：`searchBookContent`**

| 制品 | 本地 |
| --- | --- |
| `url` / `bookUrl` fallback | `url` |
| `keyword` | `query` |
| `lastIndex`（默认 0） | `pageIndex` |
| `size`（默认 20） | `pageSize` |

客户端按制品文档传 `keyword`，本地当空处理直接返回错误。
深层逻辑也不同：制品用 `lastIndex` 游标逐章搜索 + `closeHandler` 提前停止 + 返回 `mapOf("list", "lastIndex")`，我们是搜完整个书架再 `subList` 分页。

**其余 11 个**

| 接口/类 | 问题 |
| --- | --- |
| `getShelfBookWithCacheInfo` | 制品遍历书架返回统计列表（带 `cachedChapterCount`），我们做单本查询 |
| `getBookSource` | 制品非空 `String` 声明抛 NPE，我们返回"书源链接不能为空" |
| `deleteBookCache` | 制品支持 `url`/`bookUrl` 回退 + 本地书检查 + 三个不同消息 |
| `getRssArticles` / `getRssContent` | `!!` 生成 `checkNotNull`（消息 "null"），非空声明生成 `checkNotNullExpressionValue`（带完整表达式） |
| `searchBookSource` | `getString("url") ?: ""` 吞掉 null，制品直接非空使用 |
| `bookSourceDebugSSE` | 消息文案 + 输出格式（`jsonEncode(ReturnData)` vs 纯文本） |
| `exportToTxt` | 文件名格式 `《书名》作者：xxx.txt`、`appendText(text, charset)`、`appConfig.exportCharset` |
| `BookSourceAny.bookSourceComment` | 默认值 `""` 不是 `null` |
| `HttpTTS` 三个默认值 | `id`/`lastUpdateTime` = `currentTimeMillis()`，`concurrentRate` = `"0"` |
| `JsExtensions.importScript` | 异常类型 `NoStackTraceException` 不是 `Exception` |
| `HttpTTS` / `RssSource` 的 `fromJson` | 静态 `JsonPath.parse` 应换成带 `SUPPRESS_EXCEPTIONS` 的 `jsonPath.parse` |

### 新增的扫描方法（按发现能力排序）

1. **捕获字段语义扫描** —— 比对 lambda 的 `$xxx` 捕获集合（排除 `L$`/`I$` 续体槽位）。158 个差异类里只 3 个不同，挖出 2 个真逻辑缺失。
2. **HTTP 端到端差分** —— 唯一能发现"应用起不来"和"参数名不兼容"的手段。
3. **字符串常量扫描** —— 抓 `ldc` 的字符串集合差。挖出 `restoreFromMongodb` 方向相反的 bug。注意大量假阳性：`checkNotNullExpressionValue` 的表达式文本、`Base64.NO_WRAP` 常量折叠、控制台编码乱码。
4. **`d2` 元数据扫描** —— `javap -v` 的 `@Metadata d2` 记录参数名、`@ParameterName`、属性名、返回类型签名。挖出 `forEachUser` 的 lambda 参数名缺失。34 个类有差异，但只有 1 个是真源码差异，其余是元数据生成策略。
5. **请求参数名扫描** —— 专抓 `getString`/`queryParam` 的字面量参数。842 个项目类里只有 1 处不同（且是文件路径不是 HTTP 参数），说明参数名已全对齐。
6. **`AdaptedFunctionReference` 判据** —— 类 `extends AdaptedFunctionReference` 且构造带方法名+签名 → 源码用了方法引用 `x::y`。全库只有 1 处（`ttsByTextToSpeechCn$2`）。

### 环境注意

- **编译内存**：内存紧张时用 `-Xmx768m -XX:MaxMetaspaceSize=320m -XX:+UseSerialGC --offline`，实测 1分34秒编译成功。正常时 `-Xmx2048m` 约 15 秒。
- **不要改页面文件**（用户明确禁止）。
- **后台实例会超时退出**，每次验证前先探测 8830/8831 的 `/health`。
- **报时间必须跑 `date`**，绝不推算。
