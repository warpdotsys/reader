# 批次记录

每个已迁入批次的可核验记录。计划见 [ROADMAP.md](ROADMAP.md)。

## 第 1 批 —— 内嵌格式库与解析器

**状态：已完成（ABI 已核验）**

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
