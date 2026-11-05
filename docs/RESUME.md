# 续接说明（2026-11-06 14:10）

## 用户目标（明确、无妥协）

**最终要 100% 对得上的源码，无论多费劲。** 不接受"编译器版本差异"作为永久借口。
ABI 一致性已经不重要，类名和字节码三重对齐才是目标。

## 当前状态

```
制品应用类   : 936
已适配       : 930  (99.36%)   还缺 6 / 多出 3
字节码完全一致: 757 / 930 (81.4%)   有差异 173 个类
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
23. **`const val TAG` 不发 static 字段**：`const` 是编译期常量会内联，
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
