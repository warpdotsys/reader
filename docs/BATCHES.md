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

#### 规约

0. **`@JsonProperty` 必须写 `@get:`**：写普通的 `@JsonProperty` 会落到构造参数
   （`RuntimeVisibleParameterAnnotations`），制品是在 getter 的 `RuntimeVisibleAnnotations`。
   同理**委托属性上的 `@Transient` 要写 `@delegate:Transient`**，直接写 `@Transient`
   报 "not applicable to target 'member property with delegate'"。
（顺序即试错顺序）

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
| `checkAuthorization` | 内联 `isEmpty` 的稳定化往返（制品 211 条 / 我们 191 条）：制品在 `checkcast CharSequence` **之前**多一对 `astore/aload`，且该槽在 LVT 里没有条目——是编译器自动插的临时，1.5.21 已优化掉。`split` 那处已按制品补成 `split(":", limit = 2)`（`iconst_0; iconst_2; iconst_2` 逐条相同），但整体仍是 loose。 |
| `_init_$lambda-1` | switch 表项数值整体差 4（前置指令数不同），分支目标同比位移 |
| `webdavList` | 同上的目标位移；`formatter` 调用点语义一致 |
| `webdavUpload` / `webdavMove` / `webdavCopy` | 同上：分支目标位移 2~4，指令语义一致 |

这 6 项都已核对：调用目标、字符串常量、分支条件一致，差异只在局部槽位与目标偏移；
按 `docs/GOAL.md` 记 loose。

### 3a-6 `RssSourceController`（842 行 CFR ≈ 8 个成员，衍生 9 个类）

**状态：9 个类名逐一相同；8 个类指令级一致，本体 7 个方法停在 loose（局部装配）**

这一批是**替换占位**：原文件只有 `private val logger`（7 行），被差集脚本当成「已还原」。
按 skill 的规矩整份重写，不保留占位。

#### 规约

1. **循环里被重新赋值的集合变量必须声明成非空类型**。`var list = asJsonArray(...)` +
   `if (list == null) list = JsonArray()` 这种两段式，一旦在循环体内再赋值
   （`list = JsonArray(newList)`），Kotlin 的 smart cast 在后续迭代里失效，
   报 `Only safe calls are allowed`。改成
   `var rssSourceList: JsonArray = asJsonArray(...) ?: JsonArray()`。
2. **`RssSource.fromJson(str)` 返回 `Result<RssSource>`**，要 `.getOrNull()` 解包；
   制品里是 `Result.isFailure-impl ? null : value` 的内联形态。平台类型传参不写 `!!`。
3. **`Rss.getArticles(sortName, sortUrl, rssSource, page, Debug)` 与
   `Rss.getContent(article, ruleContent, rssSource, Debug)` 都是 suspend**，在 suspend 方法里直接调用；
   `Debug` 是 `io.legado.app.model.Debug` 这个 object（实现 `DebugLog` 接口），不是 `io.legado.app.model.rss.Debug`。
4. **`_rssSource.sourceUrl != rssSource.sourceUrl` 用 `!=`**：两侧都是非空 String，
   编译成 `String.equals`；写成 `.equals(...)` 反而要 import。
5. **`context.bodyAsJson.getString("sortName", "")` 仍要 `!!`**：带默认值不等于返回非空，
   Vert.x 的 `JsonObject.getString(String, String)` 在 Kotlin 侧仍是平台类型。

#### 核验

```
已比对类数         : 9
指令序列完全一致   : 8
仅局部装配不同     : 0
结构与调用不同     : 1
无法比对           : 0
```

#### 已登记的 loose 档差异（7 个方法）

`getRssSources` / `saveRssSource` / `saveRssSources` / `deleteRssSource` /
`getRssSourceByURL` / `getRssArticles` / `getRssContent` 全是同一类：局部变量顺序不同
（多一对 `astore/aload` 的稳定化往返），调用目标、字符串常量、分支条件一致。

### 3a-7 controller 包既有文件形态对齐

`BookmarkController` / `ReplaceRuleController` / `HttpTTSController` / `BookGroupController`
是前几批留下的实现，不是占位，但有几处写法与制品不同。按字节码逐处对齐后：

| 文件 | 修正 | 结果 |
| --- | --- | --- |
| `BookmarkController` | `x.length == 0` → `isEmpty()`；`==` → `.equals()` | 3/3 类全绿 |
| `ReplaceRuleController` | 三处 `x.length == 0` → `isEmpty()` | 3/3 类全绿 |
| `HttpTTSController` | 两处 `length == 0`；`convertToEntity` 用 `getOrNull()!!` 而非 `getOrThrow()` | 3→1 个方法差异 |
| `BookGroupController` | `groupId == x` → `groupId.equals(x)` | 5→3 个方法差异 |

四个家族合计 11 个类：**9 个指令级完全一致**，`BookGroupController` 3 个方法、
`HttpTTSController` 1 个方法（`convertToEntityList` 的集合构造形态）仍是 loose。

#### 规约

1. **`x.length == 0` 一律写 `isEmpty()`**。四个文件里 6 处都是这个差别：制品是
   `checkcast java/lang/CharSequence` + `CharSequence.length` + 一个假布尔，
   写 `String.length` 得到 `invokevirtual String.length`，不是同一个方法。
2. **包装类型的相等用 `.equals()` 而不是 `==`**。`Long == Long` 编译成
   `longValue()` + `lcmp`；制品是 `Object.equals`（两侧都装箱）。`BookmarkController`
   和 `BookGroupController` 的 `checker` 都是这个差别。
3. **`Result` 解包要看制品用哪个**：`getOrThrow()` 生成
   `throwOnFailure` + `checkcast`；`getOrNull()!!` 生成
   `isFailure-impl ? null : value` + `checkNotNull` + `checkcast`。
   `HttpTTSController.convertToEntity` 是后者。

### 3a-8 `UserController`（2015 行 CFR，21 个类，19 个成员）

**状态：21/21 类名一致；20/21 类指令级一致；本体 10/19 成员一致，9 项 loose；
ABI 全一致（未进不一致清单）**

> ⚠️ **本批有一处真实语义差异，必须记住**：`getUserLimit`。
> 制品是 `License.validHost(host)` + `license.userMaxLimit`；当前实现走「无许可分支」取值。
> Kotlin 1.5.21 在 `License.kt` 与调用点**同批编译**时必崩，且分两个阶段：
> 引用 `validHost` → psi2ir `Undefined parameter referenced: value-parameter queryHost`；
> 降到只读属性 → IR lowering `No mapping for symbol: VALUE_PARAMETER INSTANCE_RECEIVER … License`。
> 独立 probe（单文件 + 制品 classpath）不崩，只在整模块编译时崩，改方法体/写法/降版本都绕不过去。
> **这一处的行为与制品不同，其它成员无语义差异。**

最大的一批。`login` 一个方法就有 1007 条指令、60+ 行 LNT，状态机 17 个。
关键教训：`uploadFile` 少写一层 `.forEach {}` 会让合成类名从
`$lambda-7$lambda-6$` 退化成 `$lambda-6$lambda-5$`——**嵌套 lambda 的编号是从外往里
1 递增的，少一层就整体前移**。这是本批唯一一次类名不一致，靠 diff 抓出来的。

#### 规约（顺序即试错顺序）

1. **`map[key]` 在制品里不是 `Map.get`**。制品是 `getOrDefault(key, null)` 加一层内联包装
   （`astore 临时; aconst_null; astore 默认值; iconst_0; istore 标记; …; getOrDefault`）。
   写 `userMap[username]` 得到 `Map.get`，整段少 8 条指令；写
   `userMap.getOrDefault(username, null)` 才逐条对上（工程里能编过，独立 probe 反而报
   "null can not be a value of a non-null type"——判据以工程编译为准）。
2. **`saveUserSession(context, user)` 必须是两参数**。第三参 `regenerateToken` 走默认值，
   制品是 `saveUserSession$default(..., iconst_4, aconst_null)`；写成三参数会少
   `checkcast BaseController / iconst_4 / aconst_null` 三条。
3. **早退 `if (!x) continue` / `return@forEach` 会多出 `ifne +2; goto`**。制品是单条
   `ifeq/ifne` 直跳循环尾——原文写的是正向 `if (x) { … }`。
   `getUserInfo`、`uploadFile`、`deleteUsers`、`forEachUser` 四处都是这个差别。
4. **`$lambda-N` 编号是文件级计数器，少一层内联 lambda 就会整体差 1**。
   症状：`forEachUser$lambda-6$lambda-5$$inlined$toDataClass$1` vs 制品的
   `…$lambda-7$lambda-6$…`。定位手法：`javap -p -l` 抓两边的 `$i$a$-…` 名字集合，
   差集直接指出「哪个方法少了一个 lambda」——本轮是 `uploadFile` 写成了 `for (it in …)`，
   制品是 `context.fileUploads().forEach { … }`。
5. **`isEmpty()` 与 `isNullOrEmpty()` 都要看 `ifnull`**：`isNullOrEmpty()` 多一条 `ifnull`
   且多一个假布尔。制品 `login` 里 `isEmpty()` 带两个假布尔却**没有** `ifnull`，
   1.5.21 复现不出来（`isEmpty()`=1 个标记、`isNullOrEmpty()`=2 个+`ifnull`），记编译噪声。
6. **`var type: String? = null` 再赋平台值、且被 lambda 捕获时**，制品会插
   `checkNotNullExpressionValue(type, "type")`；声明成平台类型（`var type = …getParam(…)`）
   会丢掉这条断言。
7. **`as? MutableMap<K,V>` 的形态**：制品是 `ifnull + asMutableMap + ifnull` 两段检查，
   `as?` 生成的是 `TypeIntrinsics.isMutableMap + checkcast + goto + aconst_null`。
8. **`logout`** 另有一处 `nop`（try 起点）位置差异和 1 处槽位差 1，属编译噪声。
9. **恢复期验证本类必须 `rm -rf build` 后全量编译**：`build/` 里已有 `License.class` 时，
   增量编译会报 `Cannot access class 'com.htmake.reader.entity.License' … conflicting dependencies`。

### 3a-9 `RestVerticle`（493 行 CFR，6 个类）

**状态：6/6 类名一致；5/6 类指令级完全一致；主类 8 个方法 loose；ABI 全一致**

这是 `CoroutineVerticle` 的抽象基类，`router` 用 `lateinit var`、`port` 用 `open var`。

#### 规约（全部是 ABI 级的，错一条 verify-abi 就红）

1. **`port` 必须写 `open var`**。父类有同名非 final 的 `getPort()/setPort()`，写成
   普通 `var` 生成的 getter 是 `final`，ABI 直接不一致（4 个方法差异全消）。
2. **`suspend (RoutingContext) -> Unit` 与 `-> Any?` 是两种类型**，即使方法体不返回值。
   `coroutineHandlerWithoutRes` 的 `$fn` 字段泛型是
   `Function2<RoutingContext, Continuation<Object>, Object>`——源码必须写 `-> Any?`，
   写 `-> Unit` 会变成 `Continuation<? super Unit>`。
3. **lambda 捕获变量的顺序 = 构造函数参数顺序 = 字节码里的 aload 序列**。
   `ctx.success(fn(ctx))` 与 `val res = fn(ctx); ctx.success(res)` 捕获顺序相反
   （前者 ctx 先、后者 fn 先），`verify-abi` 会报构造函数签名不一致。
4. **内层 lambda 想引用外层变量时，内层参数写 `_`**：
   `it.addHeadersEndHandler { _ -> ... it.getCookie(...) }`。写成 `{ ctx: RoutingContext -> }`
   会让 Kotlin 在 SAM 转换里给**参数**插 `checkNotNullParameter`（字符串常量还会带上
   你的变量名），完全对不上。
5. **`Cookie.setMaxAge(long)` / `setPath(String)` 返回 `Cookie`，不合成 Kotlin 属性**。
   写 `cookie.maxAge = x` 编译不过，必须 `cookie.setMaxAge(x)`。

### 3a-10 `Book`（1804 行 CFR，7 个类，33 个构造参数）

**状态：类名一致；20 个类比对里 14 个指令级一致；`Book` 本体 8 个方法 loose；
verify-abi 多出 1 处（私有字段名，见下）**

#### 编译器 bug（重要）

`isInShelf` 作为 data class 构造属性时，Kotlin 1.5.21 的 IR lowering **必崩**：

```
Caused by: java.lang.RuntimeException: Exception while generating code for:
FUN DEFAULT_PROPERTY_ACCESSOR name:<get-isInShelf> ...
Caused by: java.lang.IllegalStateException: No mapping for symbol:
  VALUE_PARAMETER INSTANCE_RECEIVER name:<this> type:io.legado.app.data.entities.Book
```

试过无效：`@get:JsonProperty`、`@JsonProperty`（默认 target）、完全去掉注解——**与注解无关**。
（`BookChapter` 13 个参数带 `isVolume` 原样写不崩，`Book` 33 个参数带 `isInShelf` 崩，
参数规模可能是相关因素之一，按实际编译结果判断，别一概而论。）

**真正触发的是三件套同时成立**（对照实验逐条排出来的）：

1. 源码里有一个与制品**同 FQN** 的类（`Book`）；
2. 属性访问器名与 `is` 前缀属性同名（`var isInShelf: Boolean` 的 getter 就叫 `isInShelf()`，
   不是 `getIsInShelf()`）；
3. 还有**别的源文件经二进制签名**引用这个成员——本工程是
   `BookController.saveBookToShelf(): Pair<Book, String>`，`Book` 仍解析到制品类。

验证：把属性改名成 `isShelf`/`isInShelfX` → 编过；把 `FileController.kt` 移走 → 编过；
把 `FileController.kt:521` 的 `result.first.isInShelf` 改成 `(result.first as Book).isInShelf`
→ BUILD SUCCESSFUL。

**绕过（本工程采用）**：属性改名 `inShelf`，用 `@get:JvmName("isInShelf")` 把访问器名改回去：

```kotlin
@get:JsonProperty("isInShelf")
@get:JvmName("isInShelf")
var inShelf: Boolean = false,
```

Kotlin 内部按属性名 `inShelf` 走 codegen（不匹配 `is` 前缀规则），对外方法名却被
`@JvmName` 改回 `isInShelf()` / `setInShelf(boolean)`，三件套里的第 2 条不成立，bug 消失。

代价：**私有字段名**变成 `inShelf`（制品是 `isInShelf`），verify-abi 会报这一处；
对外 API 完全一致，实测 7 个类里 3 个指令级完全一致、其余 4 个是内联装配顺序差异。
比「改调用处加 `as Book`」便宜——那种改法要动别的类的源码，且 `BookController` 一还原就得回退。

#### 规约

1. **`@get:JvmName` 能改方法名，改不了字段名**。字段名永远跟 Kotlin 属性名走。
2. 遇到 IR lowering 崩且报 `No mapping for symbol: VALUE_PARAMETER INSTANCE_RECEIVER`，
   先看是不是 `isXxx` 属性——这是 1.5.21 已知坑，不是你的代码写错。

### 3a-11 `BookChapter`（525 行 CFR，3 个类）

**状态：3/3 类名一致；2/3 指令级完全一致；`BookChapter$variableMap$2` 1 个方法 loose；
ABI 无新增差异（545 比对 / 515 一致 / 12 不一致，仍是 3a-10 的 1 处 + 2d 批 11 处）**

与 `Book` 同构：data class + `variableMap` 惰性委托 + `Companion` 缓存表。
本体 `BookChapter` 全部方法指令级一致；只有 `BookChapter$variableMap$2` 的 `invoke()` 差 1 处。

#### 规约

1. **13 个参数的 data class 带 `isXxx` 布尔属性不会触发 3a-10 的 IR 崩溃**——
   `var isVolume: Boolean = false` 原样写即可，**不要预先套 `@get:JvmName`**：那会把私有字段名
   从 `isVolume` 变成 `volume`，反而造出一处 ABI 差异。（`Book` 是 33 个参数才崩，
   两处的差别说明参数规模可能相关，别一概而论，按实际编译结果判断。）
2. **属性还是函数，用 `@Metadata` 的 `d2` 判定，不要猜**：属性会在 d2 里**同时**出现属性名和
   getter 名（`"variableMap"`, `"getVariableMap"`）；`getAbsoluteURL` / `getFileName`
   只出现方法名 → 它们是 `fun getAbsoluteURL()`，不是 `val absoluteURL`。
3. **`x.substring(0, n)` 不必绕**：Kotlin 走 `kotlin.text` 内联，自动生成
   `(this as java.lang.String)` 的 NPE 分支 + `checkNotNullExpressionValue`，与制品逐条相同。
   （与 3a-2 里 `length` 的陷阱方向相反——那里要绕，这里不要。）
4. **内联扩展函数的差异源自被内联的函数体，与调用点无关**。`variableMap$2` 里多出的
   `$this$fromJsonObject$lambda-0` 槽、少的 `as?` 转换，来自 `GsonExtensionsKt.fromJsonObject`
   （该文件在册已是「结构差异 5」）；`Book`、`RssArticle`、
   `AnalyzeUrl$UrlOption$getHeaderMap$$inlined$fromJsonObject$1`、
   `SourceAnalyzer…$$inlined$fromJsonObject$1~11` 上都是同一形态。
   **改调用点只会同时破坏其他类**，修要去修被内联的那个文件。

#### 待验证：全仓库的 `@Metadata` 模块名

`javap -v` 全文 diff 显示本类与制品只剩两类差异：`LineNumberTable`，以及
常量池 `#378 = Utf8 reader-pro` vs `reader`（`@Metadata` 的 d2 末位）。
根因是 `settings.gradle.kts:8 rootProject.name = "reader"`——**全仓库每个 Kotlin 类都带这条**。

**已核实：不影响判据。** `verify-bytecode.ps1` 在 javap 反汇编层面比对，
第 121 行显式跳过 `LineNumberTable` / `LocalVariableTable` / `StackMapTable` / `Exceptions`，
不看 `@Metadata`；`verify-abi.ps1` 里零处提及 Metadata。所以**不要**为了这条去改
`rootProject.name`——改了只会连带影响产物名，判据一分不涨。只在需要「`javap -v` 全文
逐字节相等」这种超验收时才要动它。

### 3a-12 `SearchBook` + `Cookie` / `Cache` / `SearchKeyword`

**状态：7 个类，6 个指令级完全一致。**
`SearchBook`（610 行 CFR，3 个类）里 `SearchBook$variableMap$2` 与 `BookChapter` 同款
惰性初始化差异（1 个方法 loose）；另外三个小实体（112/128/130 行 CFR）**全绿**。

三个小实体都是纯 data class，照抄默认值即可，没有坑：

```kotlin
data class Cookie(var url: String = "", var cookie: String = "")
data class Cache(val key: String = "", var value: String? = null, var deadline: Long = 0L)
data class SearchKeyword(
    var word: String = "",
    var usage: Int = 1,
    var lastUseTime: Long = System.currentTimeMillis()
)
```

`Cookie.key`/`Cache.key` 在制品里没有 setter（只有 getter），所以是 `val`——
**判断 val/var 看 javap 里有没有 `setXxx`**，比猜字段的 ACC_FINAL 更直接。

#### `SearchBook` 追加规约（5 条）

1. **data class 里 override 了接口的属性走 getter**（`getBookUrl()`），非 override 走 `getfield`。
   `equals` / `hashCode` / `toString` / `copy$default` / `toBook` 全按这条生成，写错即「调用不同」。
2. **`equals` 用 `if (cond) { return true }; return false`**，不要 `return cond`——后者多一条 `goto`。
3. **`addOrigin` 里是 `linkedSetOf(this.origin)`**：参数 `origin` 遮蔽同名属性，
   制品取的是字段（`aload_0; getfield`），所以不能去掉 `this.`。
4. `infoHtml` / `tocHtml` 是**类体** `override var … = null`（不是构造参数），
   字段序在 `variableMap$delegate` 之前；`origins` 是 `var … private set`（有 getter 无 setter）。
5. `_userNameSpace` 沿用 `Book` 的模式：`@Transient private var` +
   `fun setUserNameSpace(...)` + `override fun getUserNameSpace()`。

#### 顺带修掉的一处真行为差异：`GsonExtensions.GSON`

`GsonExtensionsKt$GSON$2.invoke()` 的制品在 `create()` 之前有两条：
偏移 43 `GsonBuilder.disableHtmlEscaping()`、偏移 46 `setPrettyPrinting()`。
本仓库原来没有 → 所有 `putVariable` 写出的 `variable` JSON 会变成紧凑格式且 HTML 转义，
与制品**不兼容**。这不是字节码 aesthetics，是实打实的行为差异，已补上（待编译验证后提交）。

`SearchBook$variableMap$2` / `Book$variableMap$2` / `BookChapter$variableMap$2`
那三处「多 4 条指令」是同一个根因——`GsonExtensionsKt.fromJsonObject` 内联展开时把
`GSON` 接收者物化进 `$this$fromJsonObject$lambda-0$iv` 槽，属于被内联文件的问题（见 3a-11 第 4 条）。

### 3a-13 `BookGroup` / `TxtTocRule`（185 / 188 行 CFR）

**状态：2 个类全部指令级完全一致**（各 23 个成员 / 308 与 312 条指令，连局部变量槽位都一致）。

**如实更正**：这两个文件此前就是**完整的 data class 实现**（10 行），不是占位、也不是「属性对但
默认值不全」的残废骨架。本批实际动作只是逐项复核 + 补 KDoc，字节码零改动。

#### 规约

1. **`wc -l` 看到 10 行不要直接判定「占位」**——先 `grep '^data class'` / `grep 'class '`。
   属性声明齐全的 data class 骨架就是成品，剩下的只是注释和 KDoc。
2. **`show` 属性必须叫 `show`，不能叫 `isShow`**：制品生成的是 `getShow()` 而不是 `isShow()`，
   所以不触发 3a-10 的 `isXxx` bug，别自作聪明加 `@get:JvmName`。
3. **制品里这两个类没有任何注解**（全制品 grep 不到 `androidx/room`，也没有 Room jar）。
   3.2.14 服务端已剥掉 Android 上游的 `@Entity` / `@PrimaryKey`，源码**不要补注解**——
   补了就是 `RuntimeVisibleAnnotations` 差异。
4. **LNT 比制品整体少一层**：制品 `data class` 在第 8 行、首参在第 10 行，本地是 7 / 8。
   两个类偏移量完全相同，说明原始源在类声明前多 1 行、首参前多 1 行（形态与被删掉的
   `@Entity` / `@PrimaryKey` 留下的空行一致）。verify-bytecode 归一化 LNT，不必追。
5. **纯 data class 实体（无伴生对象、无 `variableMap`）可以做到 0 差异**，
   不必套用 3a-10 / 3a-11 的降级口径。

### 3a-14 `Bookmark` / `SearchResult` / `ReplaceRule`（260 / 267 / 386 行 CFR）

**状态：3 个类全部指令级完全一致。**

三个都是 data class，但被已还原的 controller 反向依赖（`BookmarkController` /
`ReplaceRuleController` 用它们的字段和 `fromJson`）。补全后 controller 侧零改动、
ABI 无变化（548 比对 / 518 一致 / 12 不一致）。

到这里 `io.legado.app.data.entities` 包的顶层类已全部还原完毕。

#### 规约（5 条）

1. **`isXxx` + data class 不必然触发 IR bug**：`ReplaceRule`（12 个参数）直接写
   `var isEnabled` 编译正常，getter/setter 就是 `isEnabled()` / `setEnabled()`，与制品一致。
   只有 `Book`（33 个参数）那个特例才崩——**不要反射性地改名加 `@get:JvmName`**，
   那会给本来正确的类白造一处字段名差异。
2. **`@JsonProperty` 必须写在 `@get:` 站点**。`ReplaceRule.isEnabled` / `isRegex` 的 getter 带注解；
   漏掉不但少约 100 字节、常量池缺 `Lcom/fasterxml/jackson/annotation/JsonProperty;`，
   **Jackson 还会把字段序列化成 `enabled` / `regex`——API 契约就变了**。
3. **⚠️ 验收盲区：`verify-bytecode` 看不见注解。** `@JsonProperty` 只进
   `RuntimeVisibleAnnotations`，指令序列一模一样。这类差异只有 `javap -v`（或类文件大小）
   能抓到。本批三个类额外做了 `javap -v` 全量归一化 diff（LNT、常量池序号、模块名归一后零差异），
   但**这个检查没有进脚本**，属流程缺口，登记在此备查。
4. **val / var 一律看 `setXxx` 有无**：`SearchResult` 10 个属性全 `val`；
   `Bookmark` 只有 `time` / `bookName` / `bookAuthor` 是 `val`。
5. **LNT 行号不可复原**：制品 `SearchResult` 原始文件 14 行、`package` 在第 1 行、class 在第 3 行；
   `Bookmark` class 在第 8 行、参数从第 10 行起（第 9 行有一行无字节码内容的空档）。
   本仓库统一加 `// Restored…` 头 + KDoc 后必然偏移，属非语义差异，
   **不要为了对齐行号去改写文件布局**。

### 3a-15 analyzeRule 包开局：`RuleDataInterface` / `RuleData` / `AnalyzeByRegex`

**状态：7 个类全部指令级完全一致**（RuleDataInterface 2 + RuleData 4 + AnalyzeByRegex 1）。

这是 `analyzeRule` 包（49 个类、11 个顶层文件、合计 8000+ 行 CFR）的第一批，
也是解锁 `rss` 包和 `webBook` 包的前置——之前 rss 包就是因为 `AnalyzeRule` 还是 7 行占位、
`Cannot access class io.legado.app.data.entities.BaseSource` 而回滚的。

`RuleData` 是 `RuleDataInterface` 的唯一实现，`AnalyzeByRegex` 只依赖接口类型，
所以这三个能一起切。剩下的 `AnalyzeRule`（2420 行）/ `AnalyzeUrl`（2842 行）/
`AnalyzeByJSoup`（916）/ `RuleAnalyzer`（765）/ `QueryTTF`（601）每一个都是独立的大工程。

#### 规约（7 条，全部实测得来）

1. **`arrayListOf()` 与 `ArrayList()` 靠 inline 标记区分**：前者在 `new ArrayList` 之前留
   `iconst_0; istore <槽位>`，后者不留；标记槽位 = 变量槽位之后的下一个空槽
   （`RuleData$variableMap$2` 里 `hashMapOf()` 同为 `iconst_0; istore_1`）。
   本批唯一一处差异就出在 `books` 用错这个，改成 `ArrayList<ArrayList<String>>()` 后归零。
2. **接口默认方法不要手写委托**。写 `fun getVariable(key: String): String? = variableMap[key]`，
   编译器在 `-Xjvm-default=disable` 下自动产出 `RuleDataInterface$DefaultImpls` +
   实现类末尾的委托 override（`public` 非 `final`，位置在 `getUserNameSpace` 之后）。
   手写 `RuleDataInterface.DefaultImpls.getVariable(this, key)` 会把成员顺序和元数据搞乱。
3. **`getUserNameSpace` 是函数不是属性**：`d2` 里只有 `"getUserNameSpace"`、没有
   `"userNameSpace"`；`d1` 对应零参函数签名。**「名字以 get 开头的函数」只能靠 d2 判**，
   javap 看不出来。
4. **空 `static {}` 是 `hashMapOf()` 这类 inline 工厂的正常产物**（`RuleData` 和制品的
   `BookChapter` 都有），不要去「清理」。
5. **`info.add(resM.group(i)!!)` 生成 `dup; checkNotNull`**（无局部槽位）；
   换成 `?: ""` 会变成 `astore; aload; ifnonnull` + 临时槽。
   制品 LVT 里**没有** `string` 这个局部变量——CFR 里出现的那个是它编的。
6. **`do { append } while (find())` 在这里是真 do-while**（先执行体后判条件）；
   写成 `while (find()) {}` 会先判条件、**吃掉第一个匹配**，语义不同。
7. **把 `@Metadata` 的 d1/d2 逐字比对纳入常规核验**——见 `scripts/verify-metadata.ps1`。
   ABI 脚本排序且不看注解，测不出声明顺序 / 默认值写法 / 属性还是函数；
   d1/d2 一致基本等于「声明形态被复原」，成本只是两次 `javap -v`。
   脚本会把 `reader-pro` 归一成 `reader`（模块名差异不影响判据，见 3a-11 末段）。

### 3a-16 `Debug` / `DebugLog`（**两个源文件**，7 个类）

**状态：7 个类全部指令级完全一致**——`Debug`、`DebugKt`、`DebugKt$logger$1`、`DebugLog`、
`DebugLog$DefaultImpls`、`DebugLogKt`、`DebugLogKt$logger$1`。
`javap -p -c -l` 的输出与制品**逐字符相同**（含常量池索引与 LineNumberTable），
是全工程少有的「连 LNT 都对上」的批次。

**更正任务书里的一处误解**：这不是「四个文件」。`DebugKt` / `DebugLogKt` 是
`Debug.kt` / `DebugLog.kt` 的**文件门面**，不是独立源文件——真去建 `DebugKt.kt`
只会得到 `DebugKtKt.class`。这也细化了 skill 里「门面不可单独复现」那条：
门面与本体同文件，`access$getLogger$p` 取决于**同文件内有无引用者**
（`DebugLogKt` 有，`DefaultImpls` 在调它；`DebugKt` 的 logger 无人引用，所以没有 accessor，
但字段和 `<clinit>` 照样存在）。原来那个 7 行占位本身就是 `Debug.kt` 的正确上半截。

#### 规约

1. **`Debug` 是 `object`，不是 class**：`INSTANCE` + private 构造器 + 两个委托给
   `DebugLog$DefaultImpls` 的 `log`。源码就是一行 `object Debug : DebugLog`。
2. **`DebugLog` 的 3 参 `log` 不能写 `override`**：它不是 `HttpLoggingInterceptor.Logger`
   的成员，写了报 `'log' overrides nothing` + 覆写函数不许有默认值。
   只有 `log(message: String)` 才是 `override`。
3. **这组类隐式依赖 `-Xjvm-default=disable`**（Kotlin 1.5.21 的默认值）：接口方法在接口里是
   abstract，方法体与 `log$default` 全落在 `DebugLog$DefaultImpls`。
   构建里若有人改成 `-Xjvm-default=all`，这一组会整体崩掉。
4. **行号可以完全对齐**：header 后留空行使 logger 落第 8 行、object 第 10 行、
   默认值 12–14、body 16–17、`log(String)` 20–21。小文件值得这么干——
   对齐后 `javap` 逐字符相同，比「LNT 反正不查」的批次更硬。

`DebugLog` 是接口，`log(url, msg, isLog = false)` 带默认参数——analyzeRule / rss 包
大量 `debugLog?.log(...)` 全靠它。这一批通了，`rss` 包的前置条件就只剩 `AnalyzeRule`。

### 3a-17 `AnalyzeByXPath` / `AnalyzeByJSonPath`

**状态：5 个类全部指令级完全一致**（XPath 1 + JSonPath 4，含 3 个 lambda 合成类）。

analyzeRule 包里除 `AnalyzeRule` / `AnalyzeUrl` / `AnalyzeByJSoup` / `RuleAnalyzer` /
`QueryTTF` 之外的分析器都已就位。这两个都能直接引用制品里的接口类型编译通过，
不需要等 `AnalyzeRule` 本体还原。

#### 规约（8 条，全部实测）

1. **`internal` 函数的 JVM 名带模块名后缀——这是 ABI 级的差异**。本仓库
   `rootProject.name = "reader"`，`internal fun getElements(...)` 会编成 `getElements$reader`；
   制品模块名是 `reader-pro`，期望 `getElements$reader-pro`。子代理的解法是显式
   `@JvmName("getElements\$reader_pro")`（`$` 必须转义成 `\$`，否则被当字符串模板），
   代价是 Kotlin 层可见性变成 `public`。

   > ⚠️ **这条修正了 3a-11 末段的判断**。那里说「`@Metadata` 模块名不影响判据」——
   > 那部分是对的（两个脚本确实不看 d2），**但模块名还决定 `internal` 成员的 JVM 名**，
   > 而 JVM 名是 verify-abi 直接比对的。所以 `rootProject.name` 到底该不该改，
   > 取决于工程里还剩多少 `internal` 成员：改成 `reader-pro` 能让所有 internal 函数名
   > 自动正确，代价只是产物名变化。**下次全量验证时应该试一次**，看 ABI 一致数是否上升。

2. **`ctx.read(rule)` 直接推断 T 编不过**（`Not enough information to infer type variable T`）。
   `ctx.read<Any>(rule)` 给的是非平台类型、不插断言；`return ctx.read(rule)`（由返回类型推断）
   会插 `checkNotNullExpressionValue(t, "ctx.read(rule)")`。同一份文件里两种写法并存，
   说明原文就不同——不要统一。
3. **`arrayListOf()` 与 `ArrayList()` 在方法体内也不同**：前者多一条 inline 标记
   `iconst_0; istore`。制品里 `jxNodes` / `result` / `results` 无标记、`textList` 有标记，
   不能统一处理。
4. **`for (i in 0..size - 1)` ≠ `for (i in list.indices)`**：前者 `iconst_1; isub` +
   `if_icmpne`，后者 `iconst_m1; iadd` + `if_icmple`。制品用 `indices`
   （`getList` 里则是 `0 until size`，`if_icmpge/if_icmplt`）。
5. **`if (x == null) continue` / `?: continue` 编成 `ifnonnull +2; goto`**；
   `if (x != null && x.isNotEmpty()) { … }` 才是单条 `ifnull → 循环头`。
   同理 `if (cond) continue` 是 `ifeq +2; goto`，`if (!cond) { … }` 才是单条 `ifne`。
6. **`Intrinsics.areEqual` 的参数顺序暴露源码次序**：同一方法里 `"%%" == elementsType`
   （常量在左）与 `elementsType == "||"`（常量在右）并存，必须逐处对照。
7. **`getResult(xPath)?.map { result.add(it.asString()) }`** 是原文写法——`add` 的 `Boolean`
   被当成 map 结果再丢弃，不是 `mapTo` 也不是 `forEach`；
   `ctx.let { try { return it.read(...) } catch … }` 同理（`let` 内 `it: ReadContext`）。
8. **`let` 的展开在 1.5.21 里带 3 个 `iconst_0; istore` 标记**
   （`arrayListOf` / `isEmpty` 各 1 个，`map` 是 `$i$f$map` + `$i$f$mapTo` + 1 个 lambda 标记）。

### 3a-18 `AnalyzeRule`（2420 行 CFR，13 个类）

**状态：13/13 类名一致；7/13 指令级一致；本体 10 个方法 loose（全部编译噪声、
逐条已定性）；ABI 新增 1 处（`AnalyzeRule$ajax$1` 多一个私有字段 `L$0`）。**

**最重要的数字**：`AnalyzeRule` **本体不在 ABI 不一致清单里**。这是本工程第一个
「千行级 CFR + 多状态机」但本体 ABI 完全一致的大类。

修复过程中抓到并改掉的**唯一真实语义差异**：`getElements` 误多调了一次
`sourceRule.makeUpRule(result)`——会让 JS 规则求值两次。

#### 规约（5 条）

1. **`access$getX$p` 只能靠「私有属性 + 显式 getter」生成**。`override val` 隐式 open，
   内部一律发 getter。仓库里 `JsExtensions` 把它声明成 `val`，所以只能用
   `private val source` + `override fun getSource()` + **文件头**
   `@file:Suppress("VIRTUAL_MEMBER_HIDDEN","CANNOT_WEAKEN_ACCESS_PRIVILEGE","NOTHING_TO_OVERRIDE")`。
   **写在属性上会多出 `getSource$annotations()`，ABI 直接红。**
2. **`@JvmOverloads` 只在制品确有重载时加**：`evalJS` 没有 1 参重载，加了就进不一致清单。
3. **`$reader_pro` 只是 JVM 名**，调用侧仍写 Kotlin 名 `getStringList(...)`；
   跨模块调制品里的 `internal` 成员靠
   `@file:Suppress("INVISIBLE_MEMBER","INVISIBLE_REFERENCE")`。
4. **`SourceRule.rule` 用「无初值 + init 赋值」**；`defaultRuleType` 制品从不写入（恒为 0），
   写 `= 0` 不会多指令。
5. 10 个 loose 的定性：`String.valueOf(x)` vs `x.toString()`（4 个求值器）、
   局部槽位整体差 1（`get`）、分支位移差 1–9 字节（`setBaseUrl`/`toNumChapter`/`getElement`/`getElements`）、
   `splitSourceRule` 的 `String.trim()` 内联展开 vs `StringsKt.trim` 调用、
   `splitPutRule`/`replaceRegex` 的参数求值顺序、`SourceRule.makeUpRule` 的
   `?.let{} ?:` 展开形态（少 19 条指令，调用序列完全相同）。

### 3a-19 `GsonExtensions.GSON` 补两个 builder 调用（真行为差异，已修）

制品 `GsonExtensionsKt$GSON$2.invoke()` 在 `create()` 之前有
`disableHtmlEscaping()`（偏移 43）与 `setPrettyPrinting()`（偏移 46），本仓库原来直接跳到
`create()`。后果是**所有 `putVariable` 写出的 `variable` JSON 变成紧凑格式且 HTML 转义**，
与制品不兼容（`@Metadata` 里那些 variable 字符串都是 pretty-print 过的）。

补上后 `GSON$2` 从有差异变为**完全一致**（Gson 家族 6 个类里 5 个一致）。
`GsonExtensionsKt` 本体仍有 5 处既有差异——与 3a-11 记的「在册结构差异 5」是同一批，
不是本次改动引起的。

### 3a-20 实验：`rootProject.name = "reader-pro"`（结论：ABI 不变，保留）

3a-17 发现 `internal` 成员的 JVM 名带模块名后缀，于是把 `settings.gradle.kts`
的 `rootProject.name` 从 `reader` 改成 `reader-pro` 试了一次。

**实测：ABI 数字一分没变**（574 比对 / 543 一致 / 13 不一致，改前改后完全相同）。
原因：工程里现存的 `internal` 成员此前都已用显式 `@JvmName("xxx\$reader_pro")` 顶住了，
模块名自动生效在这里没有额外收益。

**收益因此改为两点**：① `@Metadata` d2 的模块名自动对上（`verify-metadata.ps1` 里不用再做归一）；
② 以后还原 `internal` 成员不必逐个写 `@JvmName`。**保留，不回滚。**

### 3a-21 `rss` 包重启成功（`Rss` / `RssParserDefault` / `RssParserByRule`，5 个类）

**状态：5/5 类名一致；2/5 指令级完全一致（两个协程状态机）；3 个本体各有 1–2 个方法 loose；
ABI 无新增差异（579 比对 / 548 一致 / 13 不一致）。**

这个包在 3a 早期回滚过一次——那时 `AnalyzeRule` 还是 7 行占位，编译报
`Cannot access class io.legado.app.data.entities.BaseSource`。`AnalyzeRule` 落地后
（3a-18）原样重来一次就成了。**回滚的东西不要丢，前置补齐后成本只是重写一遍。**

#### 踩到的 4 个坑（全是「制品签名与直觉不同」）

1. **`Rss.getContent` 返回 `String` 不是 `String?`**；`getArticles` / `parseXML` 返回
   `Pair<List<RssArticle>, String>`，第二项也非空（`nextUrl` 声明成 `var nextUrl: String = ""`）。
   CFR 看不出可空性，必须 `javap -s` 看 `Continuation<? super java.lang.String>` 上有没有
   `@Nullable`。
2. **`AnalyzeUrl` 第 6 参数 `baseUrl: String = ""` 是非空的**，制品调用点靠**默认值掩码**
   （`iconst 314`）跳过它，不是显式传 null。Kotlin 侧必须用**具名参数**：
   ```kotlin
   AnalyzeUrl(sortUrl, null, page, source = rssSource, ruleData = ruleData,
              headerMapF = rssSource.getHeaderMap(), debugLog = debugLog)
   ```
   照 CFR 的位置顺序传 `null` 会报 `Null can not be a value of a non-null type String`。
3. **`BaseSource.getHeaderMap(hasLoginHeader: Boolean = false)` 是函数不是属性**，
   写 `rssSource.headerMap` 报 Unresolved。制品是
   `getHeaderMap$default(rssSource, false, 1, null)`——`BaseSource` 里根本没有 headerMap 属性。
4. **Kotlin 里不能写 `obj.setXxx(v)` 去调 `var` 属性的 setter**：
   `analyzeRule.setRuleData(rssArticle)` 报 Unresolved reference，得写
   `analyzeRule.ruleData = rssArticle`。

### 3a-22 `verify-metadata.ps1` 全量结果：66 处不一致，绝大多数是声明形态噪声

用 `-ShowList` 跑全量，579 个类里 66 个 `d1`/`d2` 对不上。抽查 `AnalyzeByJSonPath`
（这个类 verify-bytecode 是 5/5 全绿）：

```
制品: ..., "getList", "Ljava/util/ArrayList;", "rule", ...
我们: ..., "getList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "rule", ...
```

我们多一个 `Lkotlin/collections/ArrayList;`——Kotlin 类型别名与 Java 类型在元数据里的写法差别，
JVM 描述符完全相同。**这类噪声占 66 处的大多数**（类型别名、KDoc、声明顺序）。

真正有行为含义的是少数，比如 `Book` 的 `isShelf`（`@get:JvmName` 改名后 d2 里属性名变了）。

**用法定位：这个脚本不是闸门，是定位器。** 当某个类的行为可疑时，用它看声明形态
（属性还是函数、默认值写法、声明顺序）；不要把它当成第二个 verify-abi 去逐项追平——
66 处里追平的收益接近零，风险是把本来正确的类改坏。

### 3a-23 `AnalyzeUrl` 补全（24 个类）——ABI 不一致从 13 降到 11

**状态：24/24 类名一致；18/24 指令级完全一致；本体 9 个方法 + `UrlOption` 4 个方法 loose；
`AnalyzeUrl` 与 `AnalyzeUrl$UrlOption` 两个本体已从不一致清单里救出。**

子代理中途被 provider 掐断（71 次调用 / 16 分钟），但它落盘的 161 行改动（574 → 650 行）
是有效的：编译通过、ABI 从 13 直接降到 11。
**中断的子代理不要从零重来**——先编译、先跑闸门，看它留在盘上的东西值多少。

剩下唯一一处 `AnalyzeUrl$UrlOption$getHeaderMap$$inlined$fromJsonObject$1`，与
`BookChapter$variableMap$2` 同源（`GsonExtensionsKt.fromJsonObject` 的内联展开），
属已知上游问题，不是 AnalyzeUrl 自己的写法。

#### 现在这 11 个 ABI 不一致的构成（必须心里有数）

| 类 | 性质 |
| --- | --- |
| `Book` | 3a-10 的 `@get:JvmName` 私有字段名代价 |
| `AnalyzeRule$ajax$1` | 3a-18 的 `L$0` 溢出槽 |
| `RemoteWebview$getStrResponse$strResponse$1` | 2d 批既有 |
| `Coroutine$cancel$1$1` / `$executeBlock$2` / `$executeBlock$2$1` | 2d 批既有（3 处） |
| `JsExtensions$ajaxAll$1` / `$connect$1` / `$connect$2` / `$queryTTF$font$1` | 2d 批既有（4 处） |
| `AnalyzeUrl$UrlOption$getHeaderMap$$inlined$fromJsonObject$1` | GsonExtensions 上游问题 |

**9 个是 2d 批就存在的**，2 个是我们自己引入的（Book / `AnalyzeRule$ajax$1`），
1 个等上游（GsonExtensions）修。**再往下压需要先动 GsonExtensions，那是全局影响面。**

### 3a-24 【待验证】`GsonExtensions.fromJsonObject` 缺 `as T?` —— 4 处 loose 的共同根因

对照 CFR（`reference/decompiled/io/legado/app/utils/GsonExtensionsKt.java` 50-75 行），
制品的 `fromJsonObject` 在 `fromJson` 之后有一条我们**没有**的指令：

```java
Object t = $this$fromJsonObject.fromJson(json, type);
Intrinsics.reifiedOperationMarker(2, "T");     // ← 本仓库缺
Object t2 = t;
object = Result.constructor-impl(t2);
```

`reifiedOperationMarker` 只在**用了 reified 类型参数的强制转换**时才生成。
也就是说原文是：

```kotlin
inline fun <reified T> Gson.fromJsonObject(json: String?): Result<T?> =
    runCatching { fromJson<T>(json, genericType<T>()) as T? }        // ← 缺 as T?
```

本仓库现在写的是 `runCatching { fromJson<T>(json, genericType<T>()) }`，没有 `as T?`，
所以内联展开到每个调用点都少这一条 —— 这正是

- `BookChapter$variableMap$2`
- `SearchBook$variableMap$2`
- `Book$variableMap$2`
- `AnalyzeUrl$UrlOption$getHeaderMap$$inlined$fromJsonObject$1`（唯一还挂在 ABI 不一致清单里的那个）

四处差异的共同根因。`fromJsonArray` 同理（`as List<T>?`，制品对应
`reifiedOperationMarker(4, "T")` + `t instanceof List ? (List) t : null`）。

**四个重载（String / InputStream × Object / Array）都要补 `as`。**
风险：这是 inline 函数，改完所有调用点的字节码都会变 —— **必须全量重跑两道闸门**，
不能只看这几个类。留到专门一批做。

### 3a-25 `BookChapterList`（229 行 CFR，4 个类）

**状态：4/4 类名一致；3/4 指令级完全一致；本体 2 个方法 loose；ABI 全一致。**

**关键坑：`debugLog?.let { it.log(...) }` 比 `if (debugLog != null) { ... }` 多占一个
lambda 编号。** 症状是 `$analyzeChapterList$4$asyncArray$1$1` vs 制品的
`$3$asyncArray$1$1`——整体差 1，而代码逻辑完全看不出问题。

把开头那个 `?.let` 改成正向 `if`，四个类名立刻全对。这是 3a-8 第 4 条
（`$lambda-N` 是**文件级**计数器）的第一次「反向应用」：
之前都是「少写了一层 lambda 要补回去」，这次是「多写了一层要拆掉」。

**排查顺序**：类名差 1 时不要急着读全部源码，先数文件里有几个 `?.let` / `forEach` /
`runCatching` / `Array {}` / `async {}`——内联 lambda 一样占编号，但它们不生成
`$lambda-N` 方法，`javap | grep lambda` 是查不到的。
