# 差分测试（黑盒行为对比）

用两个独立 `URLClassLoader` 分别加载**制品**（`BOOT-INF/classes`）和**本地构建产物**，
两边共享同一批依赖 jar，对同样输入反射调用方法，对比序列化后的结果。

字节码对比只能证明"结构像"，差分测试才能证明"行为一致"。
两者互补：`scripts/bytecode-diff.py` 查结构，这里查行为。

## 为什么需要这个

字节码差异很大时（当前 159 个类），不代表行为不同；反过来，
**字节码看起来一样，行为也可能不同**。实际抓到的例子：

- `BookSourceAny.bookSourceComment` 字节码显示是 nullable（类型对），
  但默认值是 `""` 而不是 `null`——只有黑盒测试能发现
- `HttpTTS` 三个字段默认值全错（`id`/`lastUpdateTime` 该是 `currentTimeMillis()`，
  `concurrentRate` 该是 `"0"`）
- `HttpTTS.fromJson` / `RssSource.fromJson` 用错了 JsonPath 配置
  （静态 `JsonPath.parse` vs 带 `SUPPRESS_EXCEPTIONS` 的 `jsonPath.parse`），
  导致异常类型不同：制品抛 NPE，本地抛 `PathNotFoundException`

## 四个工具

| 文件 | 用途 |
| --- | --- |
| `DiffTest.java` | v1：只测无参方法 + 静态 final 常量 |
| `DiffTest3.java` | v3：支持带参数方法，68 种字符串样本（XPath/JSONPath/CSS/JS/URL/HTML/JSON/XML/正则） |
| `DiffTest4.java` | 同 v3，但**包含 private 方法**（规则解析核心逻辑都在 private 里） |
| `OneDiff.java` | 单类单方法对比，打印首个差异位置和上下文（调试用） |

## 编译

```bash
cd scripts/difftest
javac -encoding UTF-8 DiffTest.java DiffTest3.java DiffTest4.java OneDiff.java
```

## 运行

参数顺序：`<制品目录> <本地目录,逗号分隔> <依赖jar目录> <输出文件> [包名前缀,逗号分隔] [最大组合数] [String样本数]`

```bash
# 全量 data 包
java -Xmx2g DiffTest3 \
  "D:/Projects/reader/BOOT-INF/classes" \
  "D:/Projects/reader-repo/build/classes/kotlin/main,D:/Projects/reader-repo/build/classes/java/main" \
  "D:/Projects/reader/BOOT-INF/lib" \
  result.txt "io.legado.app.data." 48 999

# 规则引擎（含 private）
java -Xmx2g DiffTest4 \
  "D:/Projects/reader/BOOT-INF/classes" \
  "D:/Projects/reader-repo/build/classes/kotlin/main,D:/Projects/reader-repo/build/classes/java/main" \
  "D:/Projects/reader/BOOT-INF/lib" \
  rule.txt "io.legado.app.model.analyzeRule." 80 999

# 单类单方法调试
java OneDiff \
  "D:/Projects/reader/BOOT-INF/classes" \
  "D:/Projects/reader-repo/build/classes/kotlin/main" \
  "D:/Projects/reader/BOOT-INF/lib" \
  "io.legado.app.data.entities.Book" "toString"
```

## 样本池

`DiffTest3.VALUES` 里每种类型都有固定样本：

- **String**（68 种）：XPath `//div[@class='x']`、JSONPath `$.data.list[*]`、
  CSS `@css:.item > a`、`@js:1+1` / `<js>1+1</js>`、URL 各形态、
  HTML/XML/JSON、正则 `^a.*z$`、中文、emoji、日期时间……
- **int/long**：0、±1、2、42、MAX/MIN
- **boolean**：true/false
- **char**：`[]{}` `$@-./` 等分隔符

组合策略：1 个参数给满 `maxCombos`，2 个参数各取 √n，3 个参数各取 ∛n。

## 假阳性过滤

框架已内建：

- **13 位时间戳** → 归一化成 `<ts>`（`System.currentTimeMillis()` 必然不同）
- **stacktrace** → 截断成 `<stacktrace>`（类加载器地址不同）
- **异常对比** → 比对类型名 + 消息首行（不比 stacktrace）

**需要人工排除的**（框架挡不住）：

- `randomUUID()` / `UUID.randomUUID()` → 随机值
- `hashCode()` 里含时间戳或 UUID 的数据类
- lambda 对象地址（`SSLHelper.unsafeHostnameVerifier`）
- `UnknownHostException` 的中英文消息（JDK 按系统 Locale 生成）
- `Object.toString()` 默认实现里的 `@xxxxx` 地址

## 已知的边界差异

| 项 | 说明 |
| --- | --- |
| `FilesUtil.listFiles("", null)` | 制品抛 `StackOverflowError`，本地不抛。`File("")` 指向当前目录，边界情况，实际不用 |
| `BaseController.limitConcurrent` 的 `needContinue` | 制品是 `(ArrayList<Any>, Int) -> Boolean`，本地 `ArrayList<Any?>`。改成 `Any` 要连带 `deferredList`/`handler`/`resultList` 全链路，收益低未改 |

## 当前覆盖（约 22600 个测试点）

| 包 | 通过 | 真差异 |
| --- | ---: | ---: |
| `io.legado.app.data.` | 15303 | 0（剩 6 个假阳性） |
| `io.legado.app.utils.` | 3300 | 1（`listFiles` 边界） |
| `org.kxml2.` | 2417 | 0（36 个对象地址假阳性） |
| `io.legado.app.help.` | 866 | 0 |
| `io.legado.app.model.` | 631 | 0（7 个假阳性） |
| `io.legado.app.lib.` | 83 | 0 |
| `AnalyzeRule`+`AnalyzeUrl`（含 private） | 568 | 0 |
| `RuleAnalyzer`+`StringUtils`（含 private） | 867 | 0 |

## 注意

- **内存**：`-Xmx2g` 起步。跑之前确认系统提交内存有 2 GB 以上余量，
  否则 JVM 会报 `页面文件太小 (errno=1455)` 直接失败。
- **不要用 `-Xmx3g`**：会把 16 GB 机器的提交内存顶爆，导致 Gradle 编译也失败。
- 单次运行 4-5 分钟。包太大就拆开跑。
