# 批次记录

每个已迁入批次的可核验记录。计划与还原规则见 [ROADMAP.md](ROADMAP.md)。

> 第 2b 批起在 `restore/core` 分支上进行。原因是 `io.legado.app` 内部依赖成环
> （`data.entities` ↔ `help`，两者都引用 `model`/`utils`），无法按包切出"始终可编译"的增量。
> 该分支上的每片源码通过 `-PrestoreBootstrap` 引导编译后立即做 ABI 核验，见 [BUILD.md](BUILD.md)。
> `main` 保持绿色。

## 第 2d 批 —— help（进行中）

**状态：9/22 文件已还原；ABI 231/231 一致**

已完成：

| 文件 | 语言 | 备注 |
| --- | --- | --- |
| `coroutine/CoroutineContainer` | Kotlin | 接口，5 个方法 |
| `http/RequestMethod` | Kotlin | 枚举 GET/POST |
| `http/api/CookieManager` | Kotlin | 接口，6 个方法 |
| `http/ByteConverter` | Kotlin | Retrofit Converter.Factory |
| `DefaultData` | Kotlin | 资源读取 + GSON |
| `Encoding` | Java | 编码名表，沿用 CFR 产物 |
| `EncodingDetectHelp` | Java | 同上 |
| `http/EncodeConverter` | Kotlin | 按 charset/Content-Type 选编码 |
| `http/Res` | Kotlin | data class |

**踩到的坑**：`ByteConverter` 覆写 `Retrofit Converter.Factory.responseBodyConverter` 时，
CFR 显示的 `@Nullable` 参数是误导——必须写成非空 `Type` / `Array<Annotation>` / `Retrofit`，
写成可空会报 `overrides nothing`。

剩余 13 个文件（按行数）：

| 文件 | 行数 |
| --- | --- |
| `http/StrResponse` | 115 |
| `http/HttpHelperKt` | 147 |
| `coroutine/CompositeCoroutine` | 171 |
| `CacheManager` | 190 |
| `http/CoroutinesCallAdapterFactory` | 217 |
| `http/SSLHelper` | 244 |
| `http/CookieStore` | 317 |
| `BookHelp` | 554 |
| `http/OkHttpUtilsKt` | 647 |
| `coroutine/Coroutine` | 799 |
| `SourceAnalyzer` | 1730 |
| `JsExtensions` | 1912 |
| `BytesEncodingDetect` | 3982 |

## 第 2c 批 —— utils（已完成）

**状态：utils 包已完成 —— 28/28 顶层类、50/50 类，类集合与制品逐项一致。
ABI 全部一致（221/221）。行为差分 11 个类、304 个用例全通过。**

### 严格档里程碑

严格档在 utils 里的 6 个类全部还原：`TextUtils`、`MD5Utils`、`EncodingDetect`、
`HtmlFormatter`、`StringExtensionsKt`、`EncoderUtils`。

其中 `TextUtils` 做到字节码完全一致，`MD5Utils` 仅局部装配不同，
`StringExtensionsKt` / `EncoderUtils` 有 ABI + 行为证据，`EncodingDetect` / `HtmlFormatter`
仍是「ABI 正确、行为未覆盖」（`HtmlFormatter.format` 需要 `Regex` 参数，跨类加载器不好传）。

### 行为差分结果（本轮新增，比静态判据更强）

`scripts/diffcheck/ClassDiffCheck.java` 用制品当 oracle，两个隔离类加载器比对同一批输入：

| 类 | 用例数 | 结果 |
| --- | --- | --- |
| `MD5Utils` | 9 | 全通过 |
| `TextUtils` | 7 | 全通过 |
| `XmlUtils` | 2 | 全通过（`InputStream` 输入） |
| `Utf8BomUtils` | 5 | 全通过 |
| `UTF8BOMFighter` | 4 | 全通过 |
| `NetworkUtils` | 25 | 全通过 |
| `StringExtensionsKt` | 104 | 全通过 |
| `Base64` | 0 | 未覆盖（API 待接） |
| **合计** | **157** | **157 通过 / 0 失败** |

`StringExtensionsKt` 是严格档且被解析引擎广泛依赖，104 个用例覆盖了
`safeTrim` / `isAbsUrl` / `isDataUrl` / `isJson*` / `isXml` / `isTrue` / `htmlFormat` /
`splitNotBlank` / `startWithIgnoreCase` / `toStringArray` / `cnCompare`，
包括 null、空串、全角空格、emoji、空分隔符等边界。

这 8 个类现在有行为证据，不再只是 ABI 正确。

### 三级分类结果（200 个已编译类）

| 结果 | 类数 | 含义 |
| --- | --- | --- |
| 指令序列完全一致 | 137 | 方法体逐指令相同 |
| 仅局部装配不同 | 1 | 去掉局部变量读写后完全一致（调用/常量/分支都对） |
| 结构与调用不同 | 63 | **连调用目标、常量或分支都不同** |

**63 这个数字是坏消息，必须正视。** 它说明差异不是"局部变量怎么摆"这种噪声，
而是方法体在调用层面就不同。原先设想"逐条审查后大概率判为编译噪声"，实测不成立——
至少大部分不是。

目前能确定的是：这 63 个类**ABI 正确、语义未证明**。静态分析在这条路上已经到极限，
下一步要靠行为差分（用制品当 oracle）来判定。

### 已知工具缺陷（已修）

`verify-bytecode.ps1` 的槽位重编号曾用未归一化空白的原始 javap 文本（`astore        5`
有多个空格），正则匹配失败 → 槽位没登记 → 标签塌成裸 `v`，把槽位信息整个抹掉，比对因此偏松。
已改为先 `\s+` 归一化。修好后同时加了两级比对（完整序列 / 去局部变量后的语义骨架）。

### 关于严格档成本的一个重要发现

Kotlin 字节码对**源码书写形式**极其敏感，而不只是语义：

- `Intrinsics.checkNotNullExpressionValue(obj, "…")` 的第二个参数是从源码表达式**字面文本**
  生成的（如 `"md5.digest(str.toByteArray())"`）。换一种等价写法，常量就变了。
- 平台类型是否被插入空值断言，取决于那个表达式恰好怎么写。
- 局部分配（`astore/aload` 往返、临时槽数量）随源码拆分方式变化。

这三点都不影响语义，但都会让字节码不同。因此"零差异"实际上要求**复原原始源码的写法**，
而不只是复原语义。`MD5Utils.md5Encode` 已经收敛到只差 4 条 `astore v / aload v`，
但要再往下走，只能靠猜原文怎么写。

这改变了严格档的成本模型，需要重新决定判据（语义等价 + 人工审查差异，还是继续追零）。

### 清单基准

utils 包在制品里是 **50 个类 = 28 个顶层 + 22 个合成**。两种形态容易漏：

- **文件门面成对出现**：`ZipUtils.kt` 里既有 `object ZipUtils`，又有顶层 `private val logger`，
  于是同时生成 `ZipUtils` 和 `ZipUtilsKt`（后者只放 logger）。同理 `ACache`/`ACacheKt`。
- **一个文件多个顶层类**：`AnkoHelps.kt` 同时生成 `AnkoHelpsKt`（`attempt`）和 `AttemptResult`。
  归属只能看字节码的 `Compiled from`，不能看文件名。

### 已完成

这 5 个类都落在**宽松档**（严格档只包含 `EncodingDetect`、`StringExtensions`、`EncoderUtils`、
`HtmlFormatter`、`TextUtils`、`MD5Utils`）。宽松档判据是 ABI + 冒烟，所以它们已达到所在档位的标准。

| 类 | 档位 | ABI | 字节码 |
| --- | --- | --- | --- |
| `LogUtilsKt` | loose | 一致 | 一致 |
| `FileExtensionsKt` | loose | 一致 | 一致 |
| `AnkoHelpsKt` | loose | 一致 | 一致 |
| `AttemptResult` | loose | 一致 | 3 个方法有差异（记录，不算失败） |
| `ThrowableExtensionsKt` | loose | 一致 | 1 个方法有差异（记录，不算失败） |
| `ParameterizedTypeImpl` | loose | 一致 | 一致 |
| `JsonExtensionsKt` | loose | 一致 | 3 个方法有差异 |
| `GsonExtensionsKt` | loose | 一致 | 5 个方法有差异 |
| `IntJsonDeserializer` | loose | 一致 | 1 个方法有差异 |
| `MapDeserializerDoubleAsIntFix` | loose | 一致 | 3 个方法有差异 |
| `JsoupExtensionsKt` | loose | 一致 | 3 个方法有差异 |
| `XmlUtils` | loose | 一致 | 2 个方法有差异 |
| `UTF8BOMFighter` | loose | 一致 | 2 个方法有差异 |
| `Utf8BomUtils` | loose | 一致 | 2 个方法有差异 |
| `Base64` | loose | 一致 | 一致 |
| `TextUtils` | **strict** | 一致 | **一致（严格档通过）** |
| `MD5Utils` | **strict** | 一致 | 仅局部装配不同（调用/常量/分支一致） |
| `EncodingDetect` | **strict** | 一致 | 结构与调用不同（未判定语义） |
| `HtmlFormatter` | **strict** | 一致 | 结构与调用不同（未判定语义） |
| `NetworkUtils` | loose | 一致 | 见行为差分（25 例全通过） |
| `StringExtensionsKt` | **strict** | 一致 | 见行为差分（104 例全通过） |
| `EncoderUtils` | **strict** | 一致 | 见行为差分（28 例全通过，含 AES/DES/DESede/RSA 分段） |
| `StringUtils` | loose | 一致 | 见行为差分（80 例全通过） |
| `ACache` + `ACacheKt` | loose | 一致 | 结构差异 5 / 6 / 2（未判定语义）；行为未覆盖 |
| `ZipUtils` + `ZipUtilsKt` | loose | 一致 | ZipUtils 结构差异 5、`$zipFiles$3` 差异 1；行为未覆盖（文件系统） |
| `FileUtils`（源文件 `FilesUtil.kt`） | loose | 一致 | 见行为差分（39 例全通过） |

**注意**：`FileUtils` 的源文件名是 `FilesUtil.kt`（字节码 `Compiled from "FilesUtil.kt"`），
与类名不同。只有看字节码才发现，按类名命名会错。

其中 `MapDeserializerDoubleAsIntFix` 曾被 ABI 抓出真问题：`deserialize` 缺少
`@Throws(JsonParseException::class)`，参考侧的签名带 `throws`，补上后全一致。
这是到目前为止 ABI 闸门唯一一次发现实质问题。

### 一个反直觉的结果

`Base64` 与 `TextUtils` 是 **Java 来源**，直接沿用 CFR 的 Java 后**字节码完全一致**；
而我按 Kotlin 重写的 `XmlUtils` / `UTF8BOMFighter` / `Utf8BomUtils` 各有 2 个方法形态不同。

也就是说"Java 来源压不到零差异"这条不是绝对的——**简单的 Java 类转写后可以完全一致**，
前提是原文结构简单、没有 CFR 会拆解的写法（如 `x = y = expr`）。

严格档的 `TextUtils` 因此通过了严格判据。

### utils 包完成情况

| 指标 | 结果 |
| --- | --- |
| 顶层类 | 28 / 28 |
| 全部类（含合成） | 50 / 50，类名逐项一致 |
| ABI | 221 / 221 一致 |
| 行为差分 | 11 个类 / 304 个用例 / 0 失败 |

**行为差分的一处已知缺口**：`ACache` / `ZipUtils` 依赖文件系统，两个类加载器会写同一个目录
而互相干扰，需要给 harness 加「每侧各自构造参数」的能力才能覆盖。已登记，未做。
`FileUtils` 只覆盖了不写盘的纯函数。

### 两处字节码差异（宽松档，已记录不阻塞）

- `ThrowableExtensionsKt.getMsg`：参考侧在 `CharSequence` 上取 `length()` 并带内联标记，
  写成 `stackTrace.length`（`String.length()`）时会多一条 `String.length()` 少一条 `CharSequence.length()`。
  试过 `ifEmpty`，展开后指令更多、离原文更远，已回退为三元形式。
- `AttemptResult`：参考侧 `then` 里对 `error` 的判断带一个临时槽和布尔物化，
  `isError()` / `getHasValue()` 开头各有一条内联标记（`iconst_0; istore`）。
  说明原文这两个判断里有内联调用，不是裸的 `error != null`。语义等价，形态未复原。

**这两处若将来升到严格档，必须先解决。** 现在的结论是"宽松档达标、字节码形态未复原"。


### 顺带修掉的一个核验缺陷

`verify-abi.ps1` 原来从**源文件名**推类名。`LogUtils.kt` 生成的类是 `LogUtilsKt`，
两边都找不到 → 两边输出同一行报错 → 被判"一致"。于是 ABI 报 111/111 全对，实际漏了 5 个类。
已改为枚举**编译产物**里的类，并对"制品中无此类"单独计数。修复后是 176 个类全部实际比对过。


## 第 2b 批 —— 数据实体（进行中）

**状态：8/24 已完成并核验**

已完成：`rule/` 全部 6 个（`BookListRule`、`BookInfoRule`、`ContentRule`、`ExploreRule`、
`SearchRule`、`TocRule`）+ `Cookie`、`SearchKeyword`。

这些都是 Kotlin `data class`，声明即可决定 ABI（`componentN`/`copy`/`copy$default`/`toString`/
`hashCode`/`equals` 全部由编译器生成），所以按 `javap` 的字段顺序与类型、`@Metadata` 的属性名
逐项还原后 ABI 即一致。默认值从合成构造函数的位掩码分支读出：`rule` 包全部为 `null`。

```
已比对类数 : 107
ABI 一致   : 107
ABI 不一致 : 0
```

待做 16 个，其中 `Book`(1804 行)、`BookSource`(1565)、`RssSource`(1480)、`BaseSource`(1008)、
`HttpTTS`(1014) 含大量业务方法，不是纯数据类，需要逐方法从字节码重建。

## 第 2a 批 —— 常量与异常

**状态：已完成（ABI 与字符串常量均已核验）**

### 范围

| 包 | 文件数 | 语言 |
| --- | --- | --- |
| `io.legado.app.exception` | 5 | Kotlin |
| `io.legado.app.constant` | 8 | Kotlin |
| **合计** | **13** | |

### 为什么第 2 批要拆

原批次是 `io.legado.app.{constant,exception,data.entities,utils,help}` 共 87 个文件。用 CFR 产物直接编译时，
34 个文件报的不是"语义错误"而是**语法错误**，根因是 Java 表达不了源语言构造：

| 根因 | 出现次数 | 说明 |
| --- | --- | --- |
| Kotlin 内联类 `Result` | 94 | 调用点的方法名是 `Result.constructor-impl(...)`，带连字符，Java 标识符不允许 |
| 内联函数接收者 | 若干 | CFR 把它写成 `void $this$fromJsonObject$iv;` 这种非法局部变量 |
| 缺失的 lambda 内部类 | 18 | CFR 没有输出 `AppConst$userAgent$2` 之类，只留下非法的 `userAgent.2.INSTANCE` |
| 协程状态机 | 若干 | `orphaned case`、`'catch' without 'try'` 等结构性错位 |

结论：这些类**只能按 Kotlin 还原**。因此第 2 批按包拆成 2a–2d，每片独立完成、独立核验、独立提交。

### 做法

1. 从 `reference/decompiled/` 取对应类的 CFR 产物作为结构参考。
2. 从制品字节码取事实：`javap -p -c` 读方法体与字符串常量，`@Metadata` 的 `d1`/`d2` 读属性名与签名。
3. 按 Kotlin 写出 `src/main/kotlin/...`。
4. `./gradlew build` + `./scripts/verify-abi.ps1` 核验。

### 核验

```
./scripts/verify-abi.ps1 -Artifact <reader-pro-3.2.14.jar>

已比对类数 : 99
ABI 一致   : 99
ABI 不一致 : 0
```

ABI 之外，字符串常量另做逐字比对（ABI 不覆盖常量池）：

| 类 | 字面量数 | 结果 |
| --- | --- | --- |
| `AppConst$userAgent$2` / `$TIME_FORMAT$2` / `$timeFormat$2` / `$dateFormat$2` / `$fileNameFormat$2` | 各 1 | 一致 |
| `AppConst$keyboardToolChars$2` | 26 | 一致 |
| `AppPattern` | 16 | 一致 |
| `DeepinkBookSource` | 15 | 一致 |

### 仍未覆盖

- **行为未核验。** 没有跑过任何实际调用对比。
- **`AppConst` 的委托体是从字节码读出来重写的**（`lazy { }` 的 lambda），
  语义一致但书写形式未必与原始源码相同。
- 第 2 批其余 74 个文件（含 `Book`、`BookSource` 等核心实体）尚未迁入。

## 第 1 批 —— 内嵌格式库与解析器

**状态：ABI 已完成；方法体等价只做到 102/150**（2026-11 复核后下调）

### 复核结论（后加，推翻原来的"已完成"）

原来这一批只过了 ABI 与字符串常量两道闸门，就记为"已完成"。加上方法级字节码比对后
（`scripts/verify-bytecode.ps1`），实测：

| 结果 | 类数 |
| --- | --- |
| 指令序列完全一致 | 102 |
| 存在方法体差异 | 48 |

差异全部落在 Java 来源的类上，按包分布：`me.ag2s.epublib` 24、`io.legado.app.lib.icu4j` 15、
`org.kxml2` 6、`me.ag2s.umdlib` 3。

原因是转写 CFR 产物时结构会变：CFR 把 `x = y = expr` 拆成两条语句（参考侧 `dup_x1`，
我方 `aload_1`），循环与三元表达式的形态也不同。**这些差异是否影响行为，尚未判定。**

因此这一批的正确状态是"ABI 完成、等价未完成"。待办：

- 逐类判定 48 个差异是否影响行为（可选：行为差分）
- `io.legado.app.lib.icu4j` 已划入严格档（编码判定错了不会报错），需要更细的复核


### 范围

| 包 | 文件数 | 说明 |
| --- | --- | --- |
| `me.ag2s.epublib` | 58 | EPUB 读写 |
| `me.ag2s.umdlib` | 9 | UMD 读取 |
| `org.kxml2` | 11 | XML pull 解析器 |
| `io.legado.app.lib.icu4j` | 8 | 字符集探测 |
| **合计** | **86** | |

选它作第一批的理由：纯 Java，没有协程、没有 Kotlin 元数据，是对"反编译产物能否变成可编译源码"最直接的检验。

### 做法

1. 从 `reference/decompiled/` 取这四个包的 CFR 输出。
2. 去掉反编译器的噪音：文件头的 `Decompiled with CFR 0.152.` 横幅，以及 CFR 的
   `WARNING - Removed try catching itself` 之类说明性注释块。这些是注释，不是代码。
3. 删掉同一包内的自引用 import（CFR 会为同包类型也生成 import）。
4. 统一换行符为 LF，每个文件加一行溯源标记。
5. `./gradlew compileJava` 迭代到通过。

### 为何是"还原"而不是"重写"

这四个包是原制品内嵌的第三方库拷贝（epublib、umdlib、kxml2、ICU 字符集探测的改版）。
它们的上游包名是 `nl.siegmann.epublib`、`org.kxml2` 等，与制品内的 `me.ag2s.*` 不一致，
替换成上游依赖会改变包名和行为，因此按字节码还原才是正确做法。

### 编译修正（共 2 处，均为 CFR 的推断错误，不是行为改写）

| 文件 | 现象 | 处理 |
| --- | --- | --- |
| `me/ag2s/epublib/domain/TableOfContents.java` | CFR 把局部变量 `result2` 推成父类型 `TitledResourceReference`，导致 `return result2;` 无法编译 | 改回实际类型 `TOCReference`，并去掉 CFR 为迁就错误类型插入的 `(TOCReference)` 强转 |
| `org/kxml2/wap/WbxmlParser.java` | CFR 把 `int count = readInt();` 的初始化挪进了 `for` 初始化段，导致 `new byte[count]` 读到未初始化变量 | 还原为 `int count = this.readInt();` 加 `while` 循环 |

两处都只影响局部结构与局部变量类型，不涉及方法签名。

### 核验

```
./scripts/verify-abi.ps1 -Artifact <reader-pro-3.2.14.jar>

已比对类数 : 86
ABI 一致   : 86
ABI 不一致 : 0
```

比对口径：`javap -p -s` 的完整输出，即类声明、成员声明、JVM 描述符、泛型签名。
86 个类逐项一致。

### 仍未覆盖

- **行为未核验。** ABI 一致只说明接口没跑偏。没有跑过任何 EPUB/UMD/XML 解析的输入输出对比。
- **方法体未逐行核对。** 编译修正之外的代码来自 CFR，未与字节码逐指令比对。
- **`me.ag2s.epublib.util.EpubArchivePolicy` 一类文件不存在于本制品。** 制品里的 epublib 与
  常见的 epublib 上游并不完全同源，未做上游比对。
