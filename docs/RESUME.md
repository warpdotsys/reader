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


## 2026-11-05 深夜 ~ 11-06 凌晨：字符串常量层大扫荡

用户指令「今晚自行推进」。这一轮把"字符串常量（ldc）集合差"这个扫描层面用到了极致，
挖出了不少**只有这一层能发现**的真 bug。

### 关键工具经验：UTF-8 强制输出

Windows 下 javap 默认用 GBK 输出，中文全变成 U+FFFD——
**两个不同的字（如 〉vs ︽）会显示成同一个乱码，看不出差异**。
必须用：
```
javap -J-Dfile.encoding=UTF-8 -v -p -classpath <cp> <class>
```
Python 侧 `r.stdout.decode('utf-8', errors='replace')`。

### 这一轮修掉的真差异

1. **StringUtils 正则多了一个"拾"**
   制品：`^[〇零一二三四五六七八九壹贰叁肆伍陆柒捌玖]$`
   本地多了末位的"拾"。多这一个字会让"拾"被判定为合法单个中文数字。

2. **ACache 日志用了插值不是格式化占位符**
   `logger.info("ACache can't make dirs in %s", path)` vs `${path}` 插值——
   占位符是延迟格式化，插值是调用前拼接，字节码和输出行为都不同。

3. **JsExtensions.aesEncodeToByteArray / aesEncodeToBase64ByteArray 缺 catch**
   制品是 try/catch + e.printOnDebug() + log(...ERROR) + 返回 null；
   本地直接 return，加密失败会直接抛异常而不是返回 null。
   （注意 printOnDebug 是 Throwable 的扩展函数，写法是 e.printOnDebug()）

4. **BookHelp 的 "%d.txt".format(index) → String.format("%d.txt", index)**
   前者走 Kotlin 的 String.format 扩展（receiver 是 format 字符串），
   后者是 Java 静态方法。3 处。

5. **Debugger 日志文本逐字符对齐**（这一项是 UTF-8 输出的最大受益者）
   - `⇒开始访问目录页:` → `⇒开始访目录页:`（缺"问"，原作者笔误，照抄）
   - `⇒开始访问正文页:` → `⇒开始访正文页:`
   - `《开始解析XX页` → `︾开始解析XX页`（U+300A → U+FE3E）
   - `》XX页解析完成` → `︽XX页解析完成`（U+300B → U+FE3D）
   - `》未获取到书籍` → `︽未获取到书籍`、`》目录列表为空` → `︽目录列表为空`

6. **Book.fileCharset 加 charsetName 中间变量**

7. **MapDeserializerDoubleAsIntFix.read 解构 → 显式 entry.key/value + map.put**

8. **HttpTTS/RssSource.fromJsonArray 的 jsonItem 中间变量**（还漏了一处静态 JsonPath.parse）

9. **LicenseController 的 JsonObject.mapFrom 加非空类型声明**

10. **DefaultAdpater.getWorkDir：if 表达式 → if 语句 + path 中间变量**

11. **YueduApi 未激活页面 HTML 补全**
    `<body></body></html>` → `<body><div style='text-align: center;padding: 30px 0;'>未激活该功能，请加<a href='https://t.me/+pQ8HDlANPZ84ZWNl'>TG群</a>激活</div></body></html>`
    （含 TG 群链接，之前只到 body 就关了）

12. **exportToEpub 全套重写**（最大的一块，见下节）

### exportToEpub 重写详情

之前是半成品，现在按制品字节码逐条重写：

- `setAssets`：从 classpath 读 `/epub/fonts.css`、`/epub/main.css`、`/epub/logo.png`
  加进 resources；用 `ResourceUtil.createPublicResource(name, author, intro, kind, wordCount, html, href)`
  加"封面"、"简介"两个 section；返回 `/epub/chapter.html` 文本
- `setEpubContent`：章节名受 `appConfig.exportNoChapterName` 控制；
  用 `ResourceUtil.createChapterResource(title.replace("🔒",""), content1, contentModel, "Text/chapter_$index.html")`
- `fixPic`：**之前是空实现**。完整逻辑：
  `AppPattern.imgPattern` 匹配 → `NetworkUtils.getAbsoluteURL(chapter.url, src)` →
  `MD5Utils.md5Encode16(src) + '.' + BookHelp.getImageSuffix(src)` →
  `LazyResource(FileResourceProvider(imageFile.parent), "Images/$originalHref", originalHref)` →
  行内替换成 `"../Images/$originalHref"`
- `setEpubMetadata`：完整 Metadata（titles/authors(getRealAuthor)/language/dates/publishers("Legado")/descriptions）
  **注意 `Date` 是 `me.ag2s.epublib.domain.Date`，不是 java.util.Date**（编译报错过）

### 编译器行为差异（源码层无解，跳过）

- `setAssets` 里 `Charsets.UTF_8`：制品每次 `getstatic`，本地编译器提取为局部变量（CSE），
  与已知的 Utf8BomUtils/UTF8BOMFighter 同类问题。
- `CURD$DefaultImpls` 的 `context.bodyAsJson`：制品生成 `checkNotNullExpressionValue`（非空声明），
  本地是 `!!` 生成 `checkNotNull`。低频差异，暂不动。

### 最终状态

- 类名 **936/936** 对齐
- 字节码 **775/934 = 83.0%**（v39）
- HTTP 差分（灌入书源+书架数据）：**84 通过 / 1 假阳性**
- 工作区干净，HEAD 到 `c412ab9`


### 深夜续战（02:00 ~ 03:20）追加

又修了 5 项：

13. **EncoderUtils**：`Base64.NO_WRAP` 常量替代字面量 2（13 处）、`base64Decode` 加 `val bytes` 中间变量
14. **ExtKt.unzip**：`entries.nextElement()!!` → `as ZipEntry`（与 ZipUtils 同源）
15. **ttsByEdge 行为重写**（重要）：
    - voice 参数类型从 String 改为 `VoiceEnum`，默认 `zh_CN_XiaoxiaoNeural`，`fromSortName` 解析失败回落
    - rate 默认 `"0"`、pitch 默认 `"0%"`，**options 有 pitch 时拼 `$value%`**（之前原样取）
    - `rate` 用 `containsKey` 取（试过 `in` 反而更差，回滚）
    - SSML 加 `.style(TtsStyleEnum.chat)`
    - **新增 `options["base64"] == "1"` 分支**：返回 JSON（Base64 编码的 mp3），content-type `application/json; charset=utf-8`；
      否则照旧返回 `audio/mpeg` 二进制
    - 去掉 `usingAzureApi(false)`
16. **MapDeserializerDoubleAsIntFix**：试了 `val arr` + `MutableList/MutableMap` 声明（对齐 invokeinterface），
    剩余 11 个差异块（编译器 for 循环展开模式），相似度 0.872，**已回滚**。
17. **DeepSeek 报告**：全量 775/934 稳定；HTTP 84/1 稳定。

### 今夜的统计

- 提交数：`bcaf729` → `c3ea111` → `6245a24` → `57af494` → `092ee35` → `c598942` → `26005f9` → `c412ab9` → `546b0fd` → `b770116` → `142f748` → `d7ccc4c`
- 修复类数：16 个类（StringUtils、ACache、JsExtensions、BookHelp、Debugger、Book、MapDeserializer、
  HttpTTS、RssSource、LicenseController、DefaultAdpater、YueduApi、BookController(exportToEpub 全套 + ttsByEdge)、
  EncoderUtils、ExtKt）
- 工具经验：`javap -J-Dfile.encoding=UTF-8` 强制 UTF-8（GBK 会把不同中文字都变成 U+FFFD）


### 凌晨续战（05:00 ~ 07:30）再追加

18. **SourceAnalyzer.toNewUrls 正则**（花了很久，教训值得记）：
    javap 的显示规则有歧义：显示 `\\` = 一个实际反斜杠；显示 `\n` = 一个真实的换行符（U+000A 的转义显示）。
    制品常量 `\n\\s*`（显示）的真实值 = [换行符] + \ + s + *，正则语义"换行 + \s*"。
    之前我们写 `"\\n\\s*"` 得到 "\ + n + \ + \ + s + *"，字符串层面不同。
    **正确源码：`Regex("\n\\s*")`**（Kotlin 里 \n -> 真换行符，\\s -> 反斜杠+s）。
    教训：直接用字符级构造目标字符串 + 写个"数连续反斜杠"的分析器验证，不要心算转义层数。

19. **BookController.saveBookSources 按制品重写**：
    - 检查 `book.name.isEmpty()`（不是 sourceList.isEmpty）
    - `replace=false` 时先读已有列表（book.name_author 路径）
    - 构建 urlMap 合并去重：已存在 -> set；不存在 -> add + 更新 urlMap
    - 之前我们直接 add 所有，忽略 replace 语义和去重

20. **editShelfBook 已重写完成**（2026-11-06 早晨）：
    锁 + name/author 匹配 + 返回 Book? 全部按制品实现，8 个调用方已核对无回归（HTTP 84/1）。
    剩余 20 条槽位编号差异（330 vs 310），不影响行为。
    之前登记的内容保留在下方供参考：

    ~~editShelfBook 的差异已确认但暂缓（风险高）~~：
    制品有 `UserMutex.getLocker("$ns@bookshelf")` 锁 + `logger.info("wait for lock {}/lock success")`，
    匹配策略是 bookUrl 或 name+author，**找不到时直接返回 null（不 add）**，返回类型是 `Book?`。
    我们目前的实现没有锁、只按 bookUrl 匹配、找不到时 add。
    8 个调用方大多忽略返回值（只有 saveBookConfig 用了 `?: book`），但改返回类型 + 去掉 add 分支涉及行为变化，需要全链路验证后再动。
    另外制品 `setData` 的非空参数与 Book? 返回值的组合方式还没研究清楚（字节码有 checkNotNullParameter 但调用方直接传可能为 null 的值）。

### 最终状态（07:30）

- 类名 936/936
- 字节码 775/934 = 83.0%（v44）
- HTTP 84 通过 / 1 假阳性（http14.txt）
- 今夜共 15 个提交：bcaf729 → 0c45516
- 工作区干净

---

## 2026-11-06 下午续战（14:00~15:40）——BookController 方法级清零

提交链（全部已推送）：92230f5 → ef33db3 → 0eea422 → 本批（searchBook/exploreBook 等）

### 新达成"逐条指令完全一致"（ratio 1.0000）的方法（BookController 内 40→49 个）

cacheBookOnServer(JsonArray,String)、saveBookInfoCache、saveBookProgressToWebdav、
bookSourceDebugSSE、searchBook、exploreBook、getChapterCacheDir、getResultAndQueryIndex、
getBookSourceStringBySourceURLOpt（getHttpTTSByName 仅剩 1 块 areEqual/equals）

### 本轮核心规律（全部经独立编译器实验 1.5.21 验证）

21. **Kotlin `==` 永远编译成 Intrinsics.areEqual**（非空 String 也一样）。
    制品里出现 `String.equals` 的地方 = 源码显式写了 `.equals(...)`。
    接收者顺序以字节码为准（如 `"1".equals(options["base64"])`、`book.bookUrl.equals(url)`）。
    已修：getShelfBookByURL、setBookSource、ttsByEdge、getHttpTTSByName。

22. **空值判定三兄弟字节码各不相同**（看 inline 标记数）：
    - `x.isEmpty()` → 1 个 iconst_0/istore 标记
    - `x.isNullOrEmpty()`（x 非空）→ 2 个标记、无 ifnull
    - `x.isNullOrEmpty()`（x 可空）→ 2 个标记 + ifnull
    - `x.isNotEmpty()` → ifle + iconst_1（反向比较）
    制品全文用 isNullOrEmpty 居多；per-method 按标记数选定。

23. **非空 var 无初始化**（`var key: String` + 双分支赋值）：
    - 编译出 `aconst_null; astore`（槽位重置）+ 平台值赋值的
      `temp=call; checkNNE(temp, "调用表达式文本"); aload temp; astore var` 舞蹈
    - 调用点直接 aload、无检查（definite assignment 保证）
    - **我们误改成 `: String? = null` 反而编译不过**（平台赋值破坏 smart-cast）。
      searchBook/exploreBook 的 key/page/ruleFindUrl 都是此形态。
    - var 声明顺序影响槽位（exploreBook：page=5 在前、ruleFindUrl=6 在后）。

24. **checkNNE 消息 = 源码写的表达式文本**，含变量名（`bookshelf.getJsonObject(i).map`、
    `list.getJsonObject(i).toString()`、`parser.readValueAsTree()`）。
    readValueAsTree 带泛型 `<JsonNode>` 会写出带泛型的消息——制品无泛型，
    源码为 `val jsonNode: JsonNode = parser.readValueAsTree()`。

25. **原作者风格**：正逻辑嵌套 if（不用 continue）、表达式内联（不做中间变量）、
    数组直接 `.forEach {}`（不经过变量）。`continue` 会编译成 ifeq+goto，
    包裹式 if 是 ifne 直落——语义相同但字节码不同。

26. **for 循环两生种**：`list.indices` = size-1/if_icmpgt；`0 until list.size` = 双向比较。
    索引循环若在 try/catch 挂起点内使用，需要**第二变量**（`val index = chapterIndex`）
    才会产生制品的双拷贝布局（cacheBookOnServer 教训）。

27. **制品里 searchBook/exploreBook 丢弃 checkAuth 结果**（bytecode: `aload v0; pop`，
    方法内无 NEED_LOGIN 常量）。已按制品改为裸语句 `checkAuth(context)`。——
    **这是制品行为**（未登录可搜书），跟制品对齐。

28. **同名方法的两处脑裂教训**：getHttpTTSByName 的 checkNNE 消息变量名必须是 `list`；
    数组 forEach 直接调用形态（`localCacheDir.listFiles().forEach`）才产生
    `checkcast Object[]` 同槽布局。

### 待续（BookController 内剩余大缺口）

getAllContents 0.217 / getLocalChapterList 0.351 / ttsByApi 0.395（制品 228 vs 本地 106，缺分支）/
getChapterList 0.459 / editShelfBook 0.468 / backupToMongodb 0.488（制品 282 vs 本地 120）/
saveBookToShelf 0.509 / mergeBookCacheInfo 0.525（本地多 4 个 CS）/ getLastBackFileFromWebdav 0.539
以及 0.90~0.99 一批（textToSpeech 0.900、deleteBooks 0.912、importBookPreview 0.922、
exportToTxt 0.972 等）

---

## 2026-11-06 傍晚续战（15:50~16:15）——方法级一致 59/116

提交：db3ee6b → 9638827 → 本批

### 新达成逐指令一致

- **getAllContents（0.220 → 1.0000）**：制品是「导出 TXT」实现（读章节缓存文件），不是读正文！
  - 头部 = 字符串模板 `"${book.name}\n作者：${book.getRealAuthor()}\n简介：${HtmlFormatter.format(book.getDisplayIntro())}"`
    （显式 StringBuilder 链会多 checkNNE(整段多行表达式) 5 条指令；模板没有）
  - `append: (String, ArrayList<Triple<String, Int, String>>?) -> Unit`（第二参可空，call 传 null）
  - chapterList.forEachIndexed；content 拼接 `+= title + '\n'` / `+= readText() + '\n'` / `+= "暂无缓存内容。\n"`；
    最后 `append("\n\n" + content, null)`
- **exportToTxt（0.981 → 1.0000）**：lambda 内联 `bookFile.appendText(text, Charset.forName(appConfig.exportCharset))`
  （无中间变量时 lambda 类字段顺序/检查消息全对齐）
- **ttsByApi（0.394 → 1.0000）**：签名实为 `(response, text, userNameSpace, options)`（LocalVariableTable 确认），
  逻辑：voice=options["voice"] isNullOrEmpty→404；getHttpTTSByName(voice, userNameSpace)；
  speechRate=options["rate"]?.toDouble() ?: 1.0，再 `5 + (speechRate-0.5)*30`；
  getSpeakStream(httpTTS, text, speechRate.toInt())；stream null→404；
  options["base64"]=="1" → Base64+jsonEncode response；否则 Content-Type = httpTTS.contentType ?: "audio/mpeg"
- **backupToMongodb（0.487 → 0.996）**：补 checkManagerAuth(NEED_SECURE_KEY)、saveUserStorage 替换 saveMongoFile、
  appConfig.secure 下 userMap.forEach handler(ns)、尾块 `getStorage("users")?.let { saveStorage("users", value = it) }`
  （`?.let` 内联后无 lambda 类）；剩 1 条 iconst_0/istore7 死存储

### 新规律

29. **`saveStorage(vararg name: String, value: Any, ...)` 的调用必须写 `value = x`**（vararg 后有必填参数）。
30. **`x?.let { saveStorage("users", value = it) }`**：无捕获 lambda 被完全内联，不产生 $N 类；
    字节码 = `ifnonnull; [aload x astore]; iconst_0×3 marker; saveStorage$default; nop; nop`。
31. **死存储伪影家族**（`iconst_0; istoreN` 无用途）：出现在「create Collection / listFiles 后」且该槽
    随后被循环内部槽复用时——getCachedChapterContentSet / backupToMongodb / getInvalidBookSources 三处，
    编译器内部槽预分配，源码形态未解，暂接受（各 2 指令）。

### 剩余大件（BookController 内）

getLocalChapterList 0.354（制品 767 vs 本地 692：多一个挂起点 tableswitch 0..5、getBookChaptersCache 早取、
preUpdateJs 分支、isInShelf 分支）/ getChapterList 0.461 / editShelfBook 0.463 / saveBookToShelf 0.509 /
mergeBookCacheInfo 0.525 / getLastBackFileFromWebdav 0.539 / getSpeakStream 0.573 / getBookContent 0.628（2197 行）


## 2026-11-06 晚 · 重大发现批（方法级 61→68/116）

### 发现 1：Ref 装箱 = 编译器行为，源码回归普通变量
制品字节码中出现的 `kotlin.jvm.internal.Ref$ObjectRef/LongRef/FloatRef/IntRef/BooleanRef`
绝大多数是 **Kotlin 编译器对「被闭包捕获的普通局部 var」的自动装箱**，不是源码显式 `Ref.Xxx()`。
- 显式 `Ref.ObjectRef<T>()` 写法会引入额外 `checkNNE`（反馈字段读取的灵活性）与 `!!` 舞蹈，
  且 @Metadata 可空性不一致；普通 `var x = ...` + 捕获则：自动装箱、元素读取无检查、语义类型受信。
- 已按此修正并清零：searchBookWithSource、getAvailableBookSource、editShelfBook、
  syncBookProgressFromWebdav、getBookShelfBooks（0.986，剩捕获顺序微差）、setBookSource（0.998）。
- 对照实验：T33~T39（scrub/），核心证据 = 显式 Ref 的 `.element` 读取产生
  `astore; ldc "x.element"; checkNNE; aload`，编译器装箱则直接 `getfield; checkcast; invoke`。

### 发现 2：javap -l（LocalVariableTable）= 强力工具
制品带完整 LVT：可直接读取**原始变量名**、lambda 标签（$files$1 vs $2）、作用域与槽位、
捕获列表字段名（$bookshelf/$bookList 等）、甚至判断 withLock 是否被使用过（$i$f$withLock 痕迹）。
- 死存储变量名、`mutex` vs `locker` 等命名差异都靠它确认。
- lambda 捕获顺序差异（getBookShelfBooks $2 构造）也从 desc 参数顺序读出。

### 发现 3：上游源码 = 公开仓库，作为风格参照系（不直接抄，只对形状）
- github.com/warpdotsys/reader-dev（用户自己的 fork）`legacy` 分支 = 上游 hectorqin/reader 旧线完整代码。
  提取：`git show origin/legacy:src/main/java/com/htmake/reader/api/controller/BookController.kt`
  （本次存为 scrub/BC_legacy.kt，3642 行）。
- reader-pro 3.2.14 = 该线的改版（Ref 装箱版 = 编译产物一致，上游为普通变量版，正好同构）。
- 用法：每个待还原方法先对照 legacy 的结构（条件正负、return 位置、运算符风格），
  再按制品字节码修正 pro 特异处（如 getBookInfo 调用去掉了 `, false` 恢复默认参数形式）。

### 发现 4：kotlin-reflect 探针可读 @Metadata 可空性
scratch/probe/ProbeBC.java：kotlin-reflect 1.5.21 + 制品类路径，读方法参数/返回的可空标注。
用于验证「可空返回 hack」与制品 metadata 的一致性（如 searchBookWithSource 返回实为不可空）。

### 本批方法级战绩（68/116 逐指令一致）
新增：mergeBookCacheInfo、getLastBackFileFromWebdav（0.979）、searchBookWithSource、
getAvailableBookSource、editShelfBook、syncBookProgressFromWebdav、saveBookGroupId（复核）、
saveBookConfig（复核）、cacheBookOnServer（复核 1.0000）、setBookSource（0.998）。


## 2026-11-07 凌晨 · 深夜批（方法级 72→79/116 @ f8363bf）

### 规律 24：内联工厂函数标记（"凭空 iconst_0; istoreN"之谜）
`arrayListOf()` / `mutableListOf()` / `linkedSetOf()` / `linkedMapOf()` 的 **no-arg 重载是 inline**，
每次调用会在字节码留下 `iconst_0; istoreN` 标记；直接写 `ArrayList()` / `LinkedHashSet()` /
`LinkedHashMap()` 构造器则没有。制品里莫名多出的 `iconst_0; istoreN` 几乎都是这个。
已修：getCachedChapterContentSet(linkedSetOf)、getInvalidBookSources(arrayListOf)、
backupToMongodb(linkedMapOf)、getTxtTocRules/searchBookMulti/SearchBookMultiSSE/saveBookSources/
searchBookContent/deleteBooks/importBookPreview 的同类点。
注意：带 vararg 的 arrayListOf(x) 不是 inline，无标记。

### 规律 25：`mutex?.lock()` ≠ `if (mutex != null) mutex.lock()`
safe-call 版本编译出 `aload; astore T; ifnonnull` 的临时槽（+1 槽），if 版本没有。
saveShelfBookLatestChapter 的 LVT 槽差 1 即此因；改为 ?.lock()/?.unlock() 后 1.0000。

### 规律 26：`else null as File?` 产生 `aconst_null; checkcast File`
Ternary 值参与 finally 缓冲时，裸 null 无 checkcast；显式 `as File?` 才产生。
createUserBackup 依赖此形态。

### 规律 27：正条件包裹式 if ≠ 提前 return
`if (x != null && !x.startsWith("/")) { 全体body }` 编译为 `ifnull → END; startsWith; ifne → END`；
`if (x == null || x.startsWith("/")) return` 则是 ifeq + 内联 Unit;areturn。两者不同！
saveBookCover 需前者。

### 规律 28：`com.script.ScriptException`（rhino-1.7.13-1.jar 的包名）
不是 javax.script.ScriptException！getSpeakStream 的 catch 分支必须用 com.script 版本。

### 本批清零（1.0000）
setBookSource、createUserBackup、getCachedChapterContentSet、getInvalidBookSources、
backupToMongodb、deleteBookCache、saveShelfBookLatestChapter、getLocalChapterList$default
### 接近（>0.95）
saveBookCover 0.991、getLastBackFileFromWebdav 0.979、getBookShelfBooks 0.986、
getLocalChapterList 0.993、importBookPreview 0.969、getSpeakStream 0.890
### 挂起的大件（下轮）
getBookContent 0.628（中段 171 行错位，需整段重排）、getChapterList 0.777（状态机 +2 槽）、
getBookInfo 0.626（+2 槽）、saveBook 0.676（spill 含 book + checkNNE("book") 悬疑）、
searchBookContent 0.659、cacheBookSSE 0.720、saveToWebdav/syncFromWebdav/等小方法槽差
