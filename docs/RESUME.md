# 续接说明（2026-11-05 08:00）

## 用户目标（明确、无妥协）

**最终要 100% 对得上的源码，无论多费劲。** 不接受"编译器版本差异"作为永久借口。
ABI 一致性已经不重要，类名和字节码三重对齐才是目标。

## 当前状态

```
制品应用类   : 936
本仓库已编译 : 933
已适配       : 930  (99.36%)   还缺 6 / 多出 3
字节码完全一致: 720 / 930 (77.4%)   有差异 210 个类
HEAD         : bde56ef (restore/core)
```

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

## 剩余 210 个有差异的类（按 delta 降序）

```
18441  com.htmake.reader.api.controller.BookController     ← 12 个方法，已在啃
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
exportBook            473   499/308    ← 本地少 191
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
