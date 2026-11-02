# 批次记录

每个已迁入批次的可核验记录。计划与还原规则见 [ROADMAP.md](ROADMAP.md)。

> 第 2b 批起在 `restore/core` 分支上进行。原因是 `io.legado.app` 内部依赖成环
> （`data.entities` ↔ `help`，两者都引用 `model`/`utils`），无法按包切出"始终可编译"的增量。
> 该分支上的每片源码通过 `-PrestoreBootstrap` 引导编译后立即做 ABI 核验，见 [BUILD.md](BUILD.md)。
> `main` 保持绿色。

### 第 2e 批补充（RssSource）

`RssSource`(1014 行 CFR) 已还原：28 参 data class + BaseSource 实现 + Companion。
要点：
- `equals`/`hashCode` 是**手写**的（只比 sourceUrl），不是 data class 生成的那份；
  但 componentN/copy 仍是 data class 生成的 → 写在类体里覆盖即可。
- `BaseSource` 的 `tag`/`key`/`userNameSpace`/`logger` 是属性，不是 getXxx 函数，
  必须写 `override val ... get() = ...`。
- `logger` 的 getter 在制品里直接 `return null`（`debugLog` 只写不读）。
- `sortUrl` 支持 `<js>`/`@js:` 前缀，用 `evalJS` 求值后按 `(&&|\n)+` 拆分，
  再按 `::` 取前两段；为空时回退 `Pair("", sourceUrl)`。
- Companion 的 `fromJsonDoc` 按 26 个 `$.xxx` 逐字段读（不读 variableComment/loginUi）。

新增 1 处已知差异：`RssSource.evalJS` 多 `throws java.lang.Exception`
（继承 `BaseSource` 的 `@Throws`，与 `BookSource` 同因）。
## 第 2d 批 —— help（进行中）

**状态：闭包合并；ABI 356/374；已编译 374 / 936 类**

### 闭包（已完成合并）
`help` 与 `data.entities` 的核心依赖链必须整体切换，否则二进制类无法引用源码类。
成员共 9 个文件，已全部落地：

| 文件 | 行数 | 说明 |
| --- | --- | --- |
| `help/JsExtensions` | 1912 | 接口 + 80 个默认方法（ajax/connect/cacheFile/queryTTF/加解密…） |
| `data/entities/BaseSource` | 1008 | 接口 + 默认实现（loginJs/headerMap/loginInfo/variable/evalJS） |
| `model/analyzeRule/AnalyzeUrl` | 2842 | 2842 行 CFR → 约 700 行 Kotlin |
| `data/entities/BookSource` | 1565 | 26 参 data class + Companion/Converters/ExploreKind |
| `adapters/ReaderAdapterInterface` | 84 | 12 参 webview 方法 + 两个 getWorkDir |
| `adapters/ReaderAdapterHelper` | 42 | object |
| `adapters/DefaultAdpater` | 112 | workDir 分支逻辑 |
| `help/SourceAnalyzer` | 1730 | 待做（依赖已就绪） |

### 已知差异（闭包引入，11 处）

- `BookSource.evalJS` 多一个编译器生成的 `throws java.lang.Exception`（继承 `BaseSource` 的 `@Throws`）
- `AnalyzeUrl$UrlOption` 的 `headers`/`body`/`retry` 存取器类型与制品不一致（`d2` 说字段是 `Any?`/`Int?`，但制品访问器是 `String`）
- 其余 9 处是合成 lambda 命名：`$cancel$1$1`、`$executeBlock$2`、`$ajaxAll$1`、`$connect$1/2`、`$queryTTF$font$1`、`$getHeaderMap$$inlined$fromJsonObject$1`

### 已完成文件

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
| `http/StrResponse` | Kotlin | **指令完全一致** |
| `http/HttpHelper` | Kotlin | okHttpClient / getProxyClient |
| `CacheManager` | Kotlin | 缓存门面 + QueryTTF 内存缓存 |
| `coroutine/CompositeCoroutine` | Kotlin | 协程集合，`synchronized` 块 |
| `http/CoroutinesCallAdapterFactory` | Kotlin | Deferred 适配器，**4 个类全部指令一致** |
| `http/SSLHelper` | Kotlin | TLS/证书工具，**全部指令一致** |
| `http/CookieStore` | Kotlin | Cookie 存取，`init` 块里建缓存目录 |
| `BookHelp` | Kotlin | 正文/图片缓存，含两个 suspend 函数 |
| `http/OkHttpUtils` | Kotlin | 顶层扩展：重试请求、Call.await、编码探测、表单 |
| `coroutine/Coroutine` | Kotlin | 任务封装：超时/回调/取消，含 3 个 inline 辅助函数 |
| `BytesEncodingDetect` | Java | 字符集探测（3982 行），沿用 CFR 产物 |

### 本轮方法学收获（重要）

**1. `javap -l` 的局部变量名直接暴露原文构造。**

不信 CFR 的 `bl`/`result2`，看 LVT。实例：

- `StrResponse.url()` 的 LVT 里有 `$i$a$-let-StrResponse$url$1` 和 `it`（类型 `Lokhttp3/Response;`）
  → 原文是 `raw.networkResponse?.let { return it.request.url.toString() }; return raw.request.url.toString()`，
  而不是 `?.let{} ?: fallback`。按前者写，该类**指令序列一字不差**。
- `HttpHelperKt$okHttpClient$2` 的 LVT 给出 `specs: ArrayList`、`builder: OkHttpClient$Builder`，
  且 `builder` 的槽位只覆盖最后 5 字节 → 原文是 `val builder = ...` 承接整条链，再单独一句 `builder.build()`。
- `DefaultData$txtTocRules$2` 的 LVT 给出 `json: String` 和 `$i$a$-runCatching-GsonExtensionsKt$fromJsonArray$1$iv`。

**2. 常量字符串也会暴露源码写法。**

`Intrinsics.checkNotNullExpressionValue` 的消息是**源码文本**。制品里是
`DefaultData::class.java.getResource("/defaultData/${txtTocRuleFileName}")`——带花括号，
说明原文写的是 `${txtTocRuleFileName}` 而不是 `$txtTocRuleFileName`。改用带花括号后该常量即一致。
同一条也说明原文**没有用 `!!`**（用 `!!` 会生成 `checkNotNull` 而不是带消息的 `checkNotNullExpressionValue`）。

**3. 内联函数会改变 lambda 的落地形态。**

okhttp 4.9.1 的 `OkHttpClient.Builder.addInterceptor` 是 `inline`。
写 `.addInterceptor { chain -> ... }`（尾随 lambda）时，Kotlin 无法把 lambda 内联进 inline 函数，
于是生成实体类 `HttpHelperKt$okHttpClient$2$invoke$$inlined$-addInterceptor$1`（`Compiled from "OkHttpClient.kt"`）；
制品里没有这个类，只有 `okHttpClient$2.invoke$lambda-0` + `invokedynamic`。
写成 `.addInterceptor(Interceptor { chain -> ... })`（显式 SAM 构造）即与制品完全一致。

**4. inline 工厂函数与 `synchronized` 都会留下标记。**

- `HashMap()` 写成 `new HashMap` 没有标记；`hashMapOf()` 是 inline，会在字段初始化器里多出
  `iconst_0; istore` 标记。`HashSet()` / `hashSetOf()` 同理。制品的构造器里带标记，
  所以原文用的是 `hashMapOf()` / `hashSetOf()`。改用后构造器即对齐。
- `synchronized(this) { }` 会生成 `$i$a$-synchronized-<类>$<方法>$1` 这个 LVT 项，
  可用来确认用的是语言级 `synchronized` 块而不是 `@Synchronized` 注解。

**5. 被判空/短路写法，LVT 依然能定位。**

`CompositeCoroutine.add` 里制品是 `aload_0; getfield resources; ifnonnull` —— 判空打在**字段**上而不是
局部变量上，说明原文是 `var set = resources; if (resources == null) { set = hashSetOf(); resources = set }`，
随后 `return set!!.add(coroutine)`（那一处 `dup; checkNotNull`）。
`addAll` 的 LVT 直接给出 `add: Z`，说明原文是 `val add = set!!.add(coroutine); if (add) continue`。
`delete` 的 LVT 给出 `set: HashSet`，对应 `val set = resources; if (set == null || !set.remove(...)) return false`。

**6. `verify-bytecode.ps1` 的 `-ShowDiff` 之前只声明、从未实现。**

`.PARAMETER ShowDiff` 写着「对不一致的方法打印归一化后的指令序列差异」，但脚本里除了参数声明
没有任何使用点。于是每次排查只能看到一张「结构差异 N / 方法差异 M」的表，
既不知道是哪个方法，也不知道差在哪——排查全靠另写临时脚本，而那些临时脚本本身又有 bug。

已补上实现：在比对循环里逐行 diff 归一化后的指令序列，汇总到末尾统一打印。
修完之后立刻有了收益——直接看出

- `SSLHelper$unsafeSSLSocketFactory$2` 的差异是 `anewarray X509TrustManager` + 事后 `checkcast`，
  也就是原文写的是 `arrayOf(unsafeTrustManager)` 而不是 `arrayOf<TrustManager>(unsafeTrustManager)`；
- `SSLHelper.getSslSocketFactoryBase` 的差异是一个临时槽位，原文用的是 `?:` 而不是 `if/else`。

两处改完，`SSLHelper` 三个类全部指令一致。

**7. 之前对「Java 来源类字节相同」的判断是错的。**

`Base64` / `Base64$Decoder` 并非逐字节相同：CFR 反编译出的 Java 源码重编译后，
方法体指令数会差几个（`process` 的分支相对位移 `+253` vs `+259`）。
这正是 [BATCHES 第 1 批] 记录的那批既存债（Java 来源类的方法体差异），不是新问题。

**8. 构造器里多出来的临时槽位，说明原文用的是 `init {}` + 局部变量，不是属性初始化器。**

`CacheManager` / `CookieStore` 的构造函数里，制品是
`new File(...) → astore <槽位> → aload <槽位> → ACache.Companion.get(...)`，
而按属性初始化器写会变成「先取 `ACache.Companion` 再建 `File`」。

`javap -l` 给出该槽位的名字是 `cacheDir`，且它是**局部变量**（不是字段），
行号也显示 `val cacheDir =` 与 `ACache.get(...)` 是两条独立语句。所以原文是

```kotlin
val cacheInstance: ACache

init {
    val cacheDir =
        File(ReaderAdapterHelper.getAdapter().getWorkDir("storage", "cache", "runtimeCache", userNameSpace))
    cacheInstance = ACache.get(cacheDir, 50000000L, 1000000)
}
```

照此改写后，两个类的构造函数都完全一致。

**9. `if (x.length == 0)` 与 `if (x.isEmpty())` 编出来不一样。**

`isEmpty()` / `isNotEmpty()` 是 `CharSequence` 上的 **inline 扩展**，展开后带
`checkcast java/lang/CharSequence` + `invokeinterface CharSequence.length()` + 标记；
`length == 0` 则是 `invokevirtual String.length()`。制品里
`getCookie` / `removeCookie` 用 `isEmpty()`，`setCookie` 用 `isNotEmpty()`，
三者逐个对上后这 3 个方法的差异全部消失。同类的还有
`value.isNullOrBlank()` 与 `value == null || value.isBlank()`。

**10. 返回类型写成 `Map` 还是 `MutableMap`，ABI 检查看不出来。**

`CookieStore.replaceCookie` 里调了 `cookieMap.putAll(...)`，按 `Map` 声明根本编译不过。
说明 `CookieManager.cookieToMap` 返回的是 `MutableMap`——两者擦除后描述符都是 `java/util/Map`，
泛型签名也一样，所以 ABI 比对无法区分。只能靠「这行代码能不能编译」反推。

**11. 扩展函数与被调用方方法，编出来的顺序不一样。**

`regex.replace(input, "")` 是「先建 Regex 再传参」；而 `input.replace(regex, "")`
（`CharSequence.replace` 扩展）是「先物化接收者，再建 Regex」。
`AppPattern.nameRegex.replace(name, "")` 与 `name.replace(AppPattern.nameRegex, "")` 同理。
同样，`fileSuffixRegex.matches(suffix)` 与 `suffix.matches(fileSuffixRegex)` 差一个
`checkcast java/lang/CharSequence`。`BookHelp.getImageSuffix` 按扩展写法调整后即完全一致。

**12. 调用 Java 平台方法时的临时槽位。**

`"%d.txt".format(index)` 编译后会多出 `StringCompanionObject.INSTANCE` 的取用与
`checkNotNullExpressionValue(..., "java.lang.String.format(format, *args)")`；
`StringsKt.readText$default` / `writeText$default` 对应 `file.readText()` / `file.writeText(text)`。
这些调用在制品与重建里一致，差的只是临时槽位摆放。

**13. suspend 扩展只能点调用，普通调用形式会解析失败。**

```kotlin
suspend fun Call.await(): Response
```

在 Kotlin 1.5.21 里，`await(call)`（把接收者当普通参数传）会报
`Unresolved reference. None of the following candidates is applicable because of receiver type mismatch`，
候选里明明列着这个函数。改成 `call.await()` 即可编译。

排查时先用改名验证（`awaitCall`）排除了重名遮蔽，再用完全限定类型排除了 import 冲突，
最后发现是调用形式的问题。**这类失败会连锁到整个方法体**——`response` 推断成 `Response?`，
后面所有 `response.isSuccessful` 都跟着报错，容易误判成空安全写错。

**14. okhttp 4.x 的 `body()` / `message()` 是 error 级弃用。**

`it.body()` / `response.message()` 直接编译失败（提示 `moved to val`），
必须写 `it.body` / `response.message`。

**15. 私有辅助函数是不是 inline，从「有没有 `access$` 桥接」能反推。**

`Coroutine` 里 `dispatchVoidCallback` / `dispatchCallback` / `executeBlock` 被
`executeInternal` 的 lambda（另一个类）调用。若它们是普通 `private suspend fun`，
编译器必须生成 `access$dispatchCallback` 这样的合成桥接；制品里**没有**这些桥接，
且它们的 lambda 类是包私有 `final class`（而不是 `public final class`）
——两条都指向「它们是 `inline`」。改成 `inline` 后这两处差异消失。

`executeBlock` 的 `block` 参数还需要 `crossinline`：它在体内被传进 `withContext{}` /
`withTimeout{}` 这两个非 inline 的挂起函数，不加 `crossinline` 编译不过。

**16. CFR 反编译失败的方法，可以直接读字节码还原。**

`Coroutine$executeInternal$1.invokeSuspend` 在 CFR 输出里只有一段异常栈
（`ConfusedCFRException: Tried to end blocks`），没有源码。用 `javap -c` 读它的
`tableswitch`（16 个状态）+ 调用序列 + 异常表，照样能还原出 try/catch/finally 的形状：

```kotlin
try {
    start?.let { dispatchVoidCallback(this, it) }
    val result = executeBlock(this, context, timeMillis ?: 0L, block)
    if (isActive) success?.let { dispatchCallback(this, result, it) }
} catch (e: Throwable) {
    e.printStackTrace()
    errorReturn?.value?.let { if (isActive) success?.let { ... } }
    if (isActive) error?.let { dispatchCallback(this, e, it) }
} finally {
    if (isActive) finally?.let { dispatchVoidCallback(this, it) }
}
```

**17. inline 函数会把 lambda 内联进调用方，类名约定随编译器版本变。**

制品把 `executeBlock` / `dispatchVoidCallback` 内联进了 `executeInternal` 与 `cancel`，
内联出来的 lambda 类名是 `Coroutine$cancel$1$1$1`（按位置编号）；
本仓库 Kotlin 1.5.21 生成的是 `Coroutine$executeInternal$1$invokeSuspend$$inlined$executeBlock$1`。
语义相同、名字不同，**这类差异无法用源码写法抹平**。

### 已知差异（已分类，不阻塞）

**A. Retrofit/OkHttp 覆写里的可空参数。**
`ByteConverter`、`EncodeConverter` 覆写 `Converter.Factory.responseBodyConverter` 时，
制品参数带 `@Nullable`×3 且**没有** `checkNotNullParameter`；本仓库写成可空参数会报
`'responseBodyConverter' overrides nothing`（Kotlin 1.5.21 不允许把 Java 平台类型参数在覆写中改成可空），
只能写非空，于是多出 3 条 `checkNotNullParameter`。

- 常量、调用、分支序列均一致，仅方法开头多 3 条参数判空；
- 语义影响：仅在调用方传 null 时提前抛异常，正常路径无差异；
- 结论：**编译器版本差异**，按 `docs/GOAL.md` 记录在案，不改写。

**B. `DefaultData$txtTocRules$2` 的局部槽位装配。**
常量已一致，仅差 inline 标记（`iconst_0; istore`）与 `new String` 的先后 —— 「仅局部装配不同」。

**C. 覆写 `Authenticator.authenticate` 需要显式 `@Throws(IOException::class)`。**
制品该匿名类带 `throws java.io.IOException`，本仓库不写 `@Throws` 就没有，ABI 会不一致。

**D. `CompositeCoroutine.addAll` 的分支极性。**
制品把 `if (add) continue` 编成 `ifne <循环头>`（真分支是回边，假分支落到 `return false`），
本仓库编成 `ifeq <return false>; goto <循环头>`。两条路径去向完全相同，仅编译器安排顺序不同。

**E. `CookieStore` 的 `mapToCookie` / `cookieToMap` 两处局部装配。**
调用的方法、常量、分支去向都一致，差异在于制品把布尔结果落到临时槽位（`&&` 的正向形式）
并多一层 inline 标记。`mapToCookie` 已按 `value != null && !value.isBlank()` 改写，
仍差一个布尔临时；`cookieToMap` 差一个方法开头的标记槽位。两者均无语义影响。

**F. `BookHelp` 的 9 个方法：临时槽位与求值顺序。**
`formatFolderName` / `formatBookName` / `formatBookAuthor` / `formatAuthor`、
`getBookCacheDir` / `getContent` / `delContent` / `saveText` 的调用目标、常量、
分支结构都一致，差异在于 `String.format` 与非捕获量物化的先后、以及局部槽位编号。
两个 suspend 函数（`saveImages` / `saveImage`）差在状态机的槽位分配 ——
CFR 对这两处也只给出「Unable to fully structure code」。
这些按 `docs/GOAL.md` 的降级规则处理：ABI + 调用/常量一致即为通过，逐条记录在此。

**G. `OkHttpUtilsKt` 的 3 个类：协程状态机。**
`$newCall$1` / `$newCallStrResponse$1` 的 `invokeSuspend` 差在续体字段
（`L$0`/`I$0..I$2`/`label`）的存取次序；`$newCallResponse$2` 与 `$await$2$2`
差在状态机槽位分配。调用目标、常量、分支去向一致。
`text` / `addHeaders` / `get` / `postForm` / `postMultipart` / `postJson` 六个非挂起函数
与制品一致（`getImageSuffix` 之外的全部扩展写法都已按第 11 条核对过）。

**H. `Coroutine` 的 3 处 ABI 差异 + 2 个多出的合成类。**

全部落在 inline 展开出来的 lambda 上：

| 项 | 制品 | 本仓库 |
| --- | --- | --- |
| `$cancel$1$1` 构造器 | `(VoidCallback, Coroutine, Continuation)` | `(Coroutine, VoidCallback, Continuation)` |
| `$executeBlock$2` | 无 `invokeSuspend$$forInline` | 有（inline 生成的副本） |
| `$executeBlock$2$1` | 同上 | 同上 |
| 内联 lambda 类名 | `Coroutine$cancel$1$1$1` | `Coroutine$executeInternal$1$invokeSuspend$$inlined$executeBlock$1` |

捕获顺序与类名约定都由编译器决定，源码层面无法指定。已确认制品的 `Coroutine`
本身（含全部 11 个内部类）与重建的**别名字段命名/结构一致**，只有上述合成产物的命名不同。

另：`class-coverage` 会把多出的 2 个类**逐个点名**（不是只报数字），见脚本输出末尾的
「多出的类」段落。

剩余 2 个文件（按行数）：

| 文件 | 行数 |
| --- | --- |
| `SourceAnalyzer` | 1730 |
| `JsExtensions` | 1912 |

### 阻塞点：bootstrap 与已还原包重名

`SourceAnalyzer` 需要构造 `data.entities.rule.SearchRule` / `ExploreRule` / `BookInfoRule` /
`TocRule` / `ContentRule`——而**这几个类本仓库已经还原**（在 `src/main/kotlin/io/legado/app/data/entities/rule/`）。
`-PrestoreBootstrap` 又把整份 `BOOT-INF/classes` 挂在 compileOnly 上，于是同一 FQN 出现两份；
这些类还都继承已还原的 `BookListRule`，编译器解析父类时给出：

```
e: ... Cannot access class 'io.legado.app.data.entities.rule.SearchRule'.
   Check your module classpath for missing or conflicting dependencies
```

（`ACache` 这类**没有已还原父类**的重名类不受影响，所以此前没暴露。）

尝试过把 bootstrap 复制一份、用 `robocopy /XD` 排除已还原的包，但 `-PrestoreBootstrap`
在 build 脚本里被 `require(isDirectory)` 拦下（副本路径未通过校验），未继续。

**修复方向**：让 bootstrap 只包含「尚未迁入」的类——在 `build.gradle.kts` 里按
`src/main` 下已存在的包路径生成过滤副本，或把 bootstrap 打成 jar 时排除同名条目。
修好后 `SourceAnalyzer` 即可继续；本轮已把该文件回退，`restore/core` 保持可构建。

#### 2026-11 复查：真正的约束是什么

做过两轮实验，结论比「重名」更根本：

1. **classpath 条目只能是目录或 jar。** 第一次尝试把过滤后的类做成 `files(List<File>)`
   （一堆裸 `.class` 文件）挂 compileOnly——Gradle 接受了，但类路径上等于什么都没挂，
   于是 `Book`/`BookChapter` 全部 unresolved。这是个我自己制造的假象，不是真问题。
2. 改成「全量复制一份 `BOOT-INF/classes-filtered`，再把已迁入的类删掉」后，
   过滤是**准确**的（实测：`Book.class` 在、`JsExtensions.class` 在、`SearchRule.class` 不在），
   项目也能正常构建。
3. 但 `SourceAnalyzer` 仍然编译不过，错误位置是 `BookSource()` 那一行，
   抱怨的却是 `rule.SearchRule` 等 5 个类：

   ```
   e: SourceAnalyzer.kt: (62, 22): Cannot access class 'io.legado.app.data.entities.rule.SearchRule'.
   ```

   **根因**：`BookSource` 仍是**二进制类**（在 bootstrap 里），它的字段签名引用 `rule.*`；
   而我们已经把 `rule.*` 换成了**源码类**。编译器无法把一个二进制类的签名桥接到源码类上。

**真正的约束**：恢复顺序必须沿「引用方向」自底向上——只要还有二进制类引用某个类，
就不能把这个类提前改成源码类。`rule.*` 的引用者 `BookSource` / `BaseSource` 等还在
`data.entities` 里没恢复，所以 `SourceAnalyzer` 必须排在它们之后。

**修正后的计划**：先恢复 `data.entities`（`BookSource` 1565 行、`BaseSource` 1008 行等），
再回来做 `SourceAnalyzer`。`SourceAnalyzer` 的源码已成形（含 500 行的迁移函数与
25 字段的 `BookSourceAny`），届时可直接落盘。

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

## 第 3a 批 —— com.htmake.reader.api.controller（进行中）

**状态：`CURD` 已落地，ABI 与其 3 个合成 lambda 全部一致**

### 本次落地

| 文件 | 说明 |
| --- | --- |
| `api/controller/CURD.kt` | Controller 通用契约：17 个成员，其中 12 个带默认实现 |

### 要点（都是编译/ABI 对照试出来的）

- 制品里 `getTableName()` / `getEntityClass()` 是**函数**，不是属性。先写成 `val tableName`
  会让已还原的 `BookmarkController`、`BookGroupController` 等全部报 "overrides nothing"——
  它们的 `override fun` 就是判据。
- 三个合成 lambda 的形态必须逐个按 `javap -p` 对照制品，同一句调用里可以混用：
  - `save` / `saveMulti` 的 `onCheckEnd` 传的是**函数引用** `this::onCheckEnd`（生成
    `FunctionReferenceImpl`）
  - 同一调用的 `checker` 传的是**lambda** `{ jsonObject, value -> checker(...) }`
    （生成 `Lambda` + `this$0` 字段）
- `deleteMulti` 的 `itemList` 必须是 `var`。写成 `val` 时捕获的是 `T[]` 本身，制品捕获的是
  `Ref$ObjectRef<T[]>`；这一个关键字不同就会多一个 ABI 差异类。
- `convertToEntityList` 里 `Array.newInstance` 要写全限定名。`import java.lang.reflect.Array`
  会遮蔽 `kotlin.Array`，`Array<T>` 直接报 "No type arguments expected for class Array"。

### 核验

```
已比对类数 : 440
ABI 一致   : 411
ABI 不一致 : 11   （全部为 2d 批已登记的既有差异）
```

### 3a-2 `BaseController`（889 行 CFR ≈ 24 个成员，衍生 12 个类）

**状态：ABI 全绿；12 个类中 11 个指令序列完全一致，本体有 6 个方法停留在 loose 档**

制品里的形态：`BaseController` 是 `open class ... : CoroutineScope`，持有
`coroutineContext` / `loginExpireDays` / `appConfig` / `env` / `userMutex` 五个字段，
外加 3 个 suspend 方法（各自带协程状态机）。

#### 规约（顺序即试错顺序）

1. **必须 `open class`**。写成 `class` 会让 5 个已还原的 Controller 子类全部报
   `This type is final, so it cannot be inherited from`，连带几十条 "receiver type mismatch"
   的级联错误——级联信息会掩盖真正的第一因，先看**第一条**错误。
2. **不能写 `userMutex.withLock { }`**。这样写会把 `removeAll` 的谓词编译成
   `BaseController$saveUserSession$2$1`（多一层嵌套）；制品是 `saveUserSession$2`。
   改成显式 `lock()` + `try { ... } finally { unlock() }` 后，12 个合成类名与制品**逐一相同**。
   顺带：`Mutex()` 在字节码里是 `Mutex$default(false, 1, null)`，也就是走默认参数，不是 `Mutex(false)`。
3. **`x.length == 0` 要写成 `x.isEmpty()`**。制品里是
   `checkcast CharSequence` + `invokeinterface CharSequence.length` + 一个假布尔
   （内联扩展的 `$i$f$` 标记）；写 `x.length` 得到的是 `invokevirtual String.length`，
   不是同一个方法。同理 `length > 0` → `isNotEmpty()`。
   改完差异方法从 11 个降到 10 个。
4. **`when (context)` 而不是 `if / else if`**。`getUserStorage`、`saveUserStorage`、
   `getUserWebdavHome` 开头那段 `is RoutingContext / is String` 分派，制品里有 subject
   临时变量，导致后面局部槽位整体后移一格。换成 `when` 后三个方法转正。
5. **`getUserInfoClass` 是单表达式**：`return getUserInfoMap(username)?.toDataClass<User>()`。
   写成 `?: return null` 是早退（`aconst_null; areturn`），制品是 `aconst_null; goto <merge>`。
6. **`limitConcurrent` 的 `coroutineCount` 必须提到 `while` 外**：制品 continuation 有
   `I$5` 字段，说明它跨 `delay(10)` 存活；而且日志第 2 个占位符传的是 `coroutineCount`，
   不是 `resultCount`（占位符文案叫 `concurrentCount` 是对的）。提出去之后
   `BaseController$limitConcurrent$3` 的 ABI 转正。
7. `appConfig` / `env` / `userMutex` 是**公开只读属性**（制品有 `getAppConfig` 等 getter），
   不是 `private val`；`loginExpireDays` 才是 `private var`（无 getter）。

#### 核验

```
已比对类数 : 452
ABI 一致   : 423
ABI 不一致 : 11        （全部为第 2d 批已登记的既有差异）

BaseController 家族（12 个类）：
  指令序列完全一致 : 11
  结构与调用不同   : 1  （BaseController 本体，6 个方法）
```

#### 已登记的 loose 档差异（6 个方法，全在本体）

| 方法 | 差异性质 |
| --- | --- |
| `getUserInfoClass` | 局部槽位整体差 1 格（制品 v3 起、我们 v2 起），指令语义等价 |
| `getUserInfoMap` | 制品多一处内联扩展的假布尔 `iconst_0; istore`，其余等价 |
| `formatUser` | 同上：`user == null` 分支前多一处假布尔 |
| `checkAuth` | 状态机指令数 796 vs 703，分支布局与寄存器分配不同，语义等价 |
| `saveUserSession` | 同上 |
| `limitConcurrent` | 同上：外层 `block25 / block26` 的两级 `break` 结构未完全还原 |

这 6 条都能对上同样的调用、同样的常量、同样的分支条件，但**没有**做到指令级一致，
按 `docs/GOAL.md` 记 loose，不宣称完成。

### 3a-3 `BookSourceController`（1574 行 CFR ≈ 24 个成员，衍生 18 个类）

**状态：18 个类名与制品逐一相同；17 个类指令序列完全一致，本体 10 个方法记 loose**

制品形态：`class ... : BaseController(coroutineContext)`，无 `open`，持有
`private var webClient`（**不是** `val`：制品字段没有 `ACC_FINAL`），
15 个 `suspend` 成员方法各带一个协程状态机，外加 `saveFromRemoteSource` 的
挂起点内嵌套 `launch` 与 `awaitResult` 两层 lambda。

#### 规约（顺序即试错顺序）

1. **`webClient` 必须是 `var`**。`private val` 生成 `private final` 字段，制品是
   `private io.vertx.ext.web.client.WebClient webClient;`——ABI 比对里这一条直接把类标成不一致。
2. **`bodyAsJsonArray` 是属性**，写 `context.bodyAsJsonArray`，不是 `context.bodyAsJsonArray()`。
   制品里 `getBodyAsJsonArray()` 由 Kotlin 属性访问生成，加括号会得到
   `Expression ... cannot be invoked as a function`。
3. **`File.deleteRecursively()` 是扩展**，不是顶层函数。写成 `deleteRecursively(f)`
   报 `receiver type mismatch`，而候选列表里只列 `public fun File.deleteRecursively()`——
   看着像类型不匹配，其实是调用形态不对。
4. **平台类型不要写 `!!`**。`context.bodyAsString`、`getString("x")` 都是 `String!`，
   直接传给非空参数/赋给非空变量时，编译器插入
   `checkNotNullExpressionValue(value, "调用表达式")`；手写 `!!` 得到的是
   `checkNotNull(value)`——两条不同指令，判据会报差异。本轮 6 处 `!!` 全去掉后转正。
5. **`?:` 与 `if` 语句产生不同 CFG**。制品用显式 `if (x == null) { x = ... }`
   （`astore; aload; ifnonnull`），写 `?:` 会多一条 `goto` 汇合。
   `getUserBookSourceJson` 的分支、`saveUserBookSources` 的列表兜底、
   `deleteBookSource` 的三处列表兜底都是这个差别。
6. **`isNullOrEmpty()` 与 `isEmpty()` 不能混**。制品在 `getBookSource` 里是
   `length()` 直取（无 `ifnull`），在 `deleteBookSources` 里带 `ifnull`——
   前者 `isEmpty()`，后者 `isNullOrEmpty()`，判据靠这条区分。
7. **`checkAuth(context)` 在 `getBookSource` / `getBookSources` 里返回值被丢弃**。
   制品字节码是 `pop`，源码没有 `if (!checkAuth(...)) return ...NEED_LOGIN`。
   按「统一模式」补鉴权分支会多出 12 条指令——这是制品行为，照抄，不要"修"。
8. **`readSourceFile` 用 `forEach` 而非 `for`**。制品有 `$i$f$forEach` 标记，
   循环体里是 `if (file.exists()) { ... }`，不是 `if (!file.exists()) return@forEach`
   （后者生成 `ifne + goto` 而不是 `ifeq`）。
9. **`String.equals(x)` 而非 `==`**。`deleteBookSources` 的内层查找制品是
   `invokevirtual String.equals`；写 `==` 得到 `Intrinsics.areEqual`。

#### 核验

```
BookSourceController 家族（18 个类）：
  类名集合与制品一致 : 18/18
  指令序列完全一致   : 17
  结构与调用不同     : 1  （BookSourceController 本体，10 个方法）
```

#### 已登记的 loose 档差异（本体 10 项）

| 方法 / 位置 | 差异性质 |
| --- | --- |
| 构造函数 | 制品多 2 个 `nop`（类体起始与结束的断点占位），指令语义等价 |
| `saveBookSource` | 状态机局部槽位整体后移 3 格（17 vs 13），分支与调用一致 |
| `saveUserBookSources` | 局部变量装配顺序不同（`urlMap` 与循环计数器的槽位互换），指令数 234 vs 216 |
| `getBookSource` | POST 分支的平台类型检查形态不同（`astore+checkNotNullExpressionValue` vs `dup+checkNotNull`） |
| `getBookSources` | 末尾少 1 个内联标记假布尔（`iconst_0; istore`） |
| `deleteBookSources` | 分支极性相反（`iflt` vs `ifge+goto`），语义等价 |
| `setAsDefaultBookSources` | 恢复分支里 `$continuation.result` 的读取顺序不同，槽位整体差 1 |
| `saveFromRemoteSource` | 同上：`result` 读取顺序，槽位差 1 |
| `deleteUserBookSource` | 同上：`result` 读取顺序，槽位差 1 |
| `generateBookSourceMap` | 首个 elvis 的 CFG 形态不同（`ifnull` vs `astore+ifnonnull+goto`） |

这 10 项都已核对：同样的调用、同样的常量、同样的分支条件，没有语义差异；
但**没有**做到指令级一致，按 `docs/GOAL.md` 记 loose，不宣称完成。

### 3a-4 `FileController`（1443 行 CFR ≈ 12 个 suspend 成员，衍生 15 个类）

**状态：15 个类名逐一相同；14 个类指令级一致，本体 3 个方法只剩分支目标位移**

制品形态：`class ... : BaseController(coroutineContext)`，无字段、无 `open`，
12 个 `suspend` 成员各带一个状态机，`checkAccess` 另有一个默认参数桥
（`isSave`/`isDelete` 掩码 2/4）。

#### 规约（顺序即试错顺序）

1. **`context.fileUploads()` 的空判断写成 `== null || isEmpty()`，不是 `isNullOrEmpty()`**。
   `isNullOrEmpty()` 生成 `checkcast java/util/Collection` + `Collection.isEmpty()`
   + 两个内联标记；制品是 `ifnull` + `Set.isEmpty()`（无 checkcast）。这一条差 11 条指令。
2. **平台类型"就地承接"还是"声明成可空"，决定后面有没有 `checkNotNullExpressionValue`**。
   `var path = context.request().getParam("path")`（`String!`）后面 `path.toDir(true)`
   会插断言；写成 `var path: String? = null; path = getParam(...)` 就没有。
   制品 `upload` 是前者，`list/get/save/mkdir/delete/restore/download` 是后者——
   两种都合法，只能按断言有无来选。
3. **`var x = json.getString("k", "") ?: ""` 直接声明**：没有 `aconst_null` 初始化，
   且变量槽位排在 elvis 临时槽**之前**（path=5、temp=6）。写成
   `var x: String? = null` 再赋值会多一对 `aconst_null; astore`。
4. **`home: Any?` 参与字符串拼接要写 `(home as String?)`**。`as String` 在 1.5.21 上会多一对
   `astore/aload`；`as String?` 直接是 `aload; checkcast String; stringPlus`。
   **参数位置没法用 `as String?`**，所以那 3 个方法留下了这 1 对往返。
5. **循环体用 `if (cond) { body }`，不要 `if (!cond) return@forEach`**。后者在内联展开里是
   `ifeq + goto` 两条，制品是一条 `ifne`/`ifeq` 直接跳到循环尾。
6. **`importPreview` 的 `paths` 必须是 `var`**，才会出现 `aload; astore; aload; checkcast Iterable`
   的稳定化往返；`deleteMulti` 同一个表达式用的是 `val`——两处不能统一。
7. **`when (ext) { "txt", "pdf", … -> }` 与 `if (ext != "txt" && …)` 不能互换**：
   前者是一次 `hashCode` lookupswitch + 一次 `switch(tmp)`（`parse`），
   后者是 5 次 `Intrinsics.areEqual` 短路链（`importPreview`）。
8. **`Book.userNameSpace` 是函数不是属性**：字段是 `private transient _userNameSpace`，
   只有 `setUserNameSpace(x)` / `getUserNameSpace()`。
9. **`context.success(x)` 是 `RoutingContext` 的扩展函数**，不是 `success(context, x)`。
10. **`logger.info(msg, context.get(...))` 会选中 `info(String, Throwable)` 重载**（平台类型同时适配）。
    要 `context.get<Any>("__FILE_HOME__")` 才会落到 `info(String, Object)`，制品是后者。
11. **引用制品里的 `BookController` 会让增量编译炸**：`gradlew classes` 报
    `unresolved supertypes: BaseController`，`clean classes` 正常。验证本类必须 clean 后编译。

#### 核验

```
已比对类数         : 15
指令序列完全一致   : 14
仅局部装配不同     : 0
结构与调用不同     : 1
无法比对           : 0
```

#### 已登记的 loose 档差异（3 个方法，全在本体）

| 方法 | 差异性质 |
| --- | --- |
| `list` | `replace(it.toString(), home as String, "")` 处多一对 `astore/aload`（1.5.21 的 smart-cast 稳定化），3 处分支目标位移 2 |
| `upload` | 同上，4 处分支目标位移 2 |
| `parse` | 同上，13 处分支 + 5 个 lookupswitch 表项位移 4，及其后槽位重编号 +1 |

这 3 项已核对：过滤掉 `astore/aload` 往返后指令序列逐条相同，只差分支目标数值，
无任何调用、常量、分支条件差异。把 `var home` 降级成 `val` 能让这 3 处对上，
但会丢掉制品里的 `aconst_null; astore`，差异只是换位置，所以保留更接近原文的写法。

### 3a-5 `WebdavController`（1319 行 CFR ≈ 12 个成员，衍生 16 个类）

**状态：16 个类名逐一相同；15 个类指令级一致，本体 6 个方法停在 loose（分支位移 + 内联往返）**

制品形态：`class ... : BaseController(coroutineContext)` 但构造函数是**三参数**——
`(coroutineContext, router: Router, handler: (RoutingContext, Exception) -> Unit)`，
类体内有 `init` 块把 webdav 路由挂到 router 上（两个静态 lambda：`lambda-1$lambda-0`、
`_init_$lambda-1`）。另有 10 个 `suspend` 成员（list/mkdir/upload/download/delete/move/
copy/lock/unLock）都返回 `Unit` 且以 `context.response()...end()` 收尾，加
`checkAuthorization`（非 suspend）与 `backupToWebdav`（返回 `ReturnData`）。

#### 规约（顺序即试错顺序）

1. **合成类的名字由「lambda 被赋给谁」决定，这是本轮唯一卡住的地方**。
   制品是 `WebdavController$webdavList$formatter$1`——中间那段 `formatter` 就是变量名。
   只有写成**一行、`val`、显式函数类型**才会拿到这个名字：
   ```kotlin
   val formatter: (File, String, Boolean) -> String = { f, url, showName -> … }
   ```
   写成 `var formatter: ((…)->String)? = null; formatter = { … }`（两步式）会退化成
   `webdavList$1`；写成局部函数 `fun formatter(…)` 会被内联成私有静态方法
   `webdavList$formatter`，**根本不生成类**。三种都编译通过，只有第一种对上类名。
2. **局部函数只有在被当作值传递时才会变成类**；被 `forEach{}` 直接调用时编译器把它内联成
   `outer$name` 静态方法（含捕获参数）。所以「capture 了两个 `Ref$ObjectRef` 的 Lambda 子类」
   一定来自 lambda 值，不是局部函数。
3. **`fileUrl` / `fileResponse` / `dirResponse` 必须是 `var`**：制品给它们生成
   `Ref$ObjectRef`（被 formatter lambda 捕获且可变）。写成 `val` 就直接捕获 String，
   formatter 的构造签名会少两个 `Ref$ObjectRef` 参数。
4. **`context.response().setStatusCode(207).end(...)` 这种链式调用不要拆成局部变量**，
   拆了会多出槽位并让后续分支目标整体位移。
5. **`checkAuthorization` 里的空判断是 `x == null || x.isEmpty()`**，
   制品带一对 `astore/aload` 的稳定化往返（`checkcast CharSequence` 前后各一次），
   比 `isNullOrEmpty()` 多约 20 条指令。
6. **`init` 块里的两个 lambda 必须保持静态**（不捕获 `this` 以外的对象）：
   `lambda-1$lambda-0` 与 `_init_$lambda-1` 是 `private static final`，
   写进类方法体会变成实例方法并多一个 `this$0` 字段。

#### 核验

```
已比对类数         : 16
指令序列完全一致   : 15
仅局部装配不同     : 0
结构与调用不同     : 1
无法比对           : 0
```

#### 已登记的 loose 档差异（6 个方法，全在本体）

| 方法 | 差异性质 |
| --- | --- |
| `checkAuthorization` | 内联 `isEmpty` 的稳定化往返（制品 211 条 / 我们 191 条），分支条件与调用一致 |
| `_init_$lambda-1` | switch 表项数值整体差 4（前置指令数不同），分支目标同比位移 |
| `webdavList` | 同上的目标位移；`formatter` 调用点语义一致 |
| `webdavUpload` / `webdavMove` / `webdavCopy` | 同上：分支目标位移 2~4，指令语义一致 |

这 6 项都已核对：调用目标、字符串常量、分支条件一致，差异只在局部槽位与目标偏移；
按 `docs/GOAL.md` 记 loose。
