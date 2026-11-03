# 恢复清单（断电/中断后从这里接）

> 最后更新：2026-11-04 00:20（HEAD `6562d84`，分支 `restore/core`）

## 当前基线（实测数字）

```
制品应用类   : 936
本仓库已编译 : 800
已适配       : 784
还缺         : 152
多出         : 16

ABI: 800 比对 / 772 一致 / 12 不一致 / 16 制品无此类
```

## 进行中

**BookController（114 类）——第四次尝试**

- 子代理 `deleg_42550c6b`
- 基线 WIP：`C:/Users/chong/AppData/Local/hermes/cache/scratch/bc/WIP_BookController.kt`（1841 行，54/114 类名）
- 工具（都在 `scratch/bc/`）：
  - `dc.py <类名>` —— 秒级本地 ABI 逐类 diff（不用等 4 分钟 verify-abi）
  - `all.json` —— 114 个类的 javap 全量签名分类
  - `abi_count.py` —— 批量 ABI 计数
  - `filt.py` —— 剥离协程脚手架
- 打法：类名差集爬坡，一次 5–10 个
- **必须连带改**：`FileController.kt:521` 的 `result.first.isInShelf` → `.inShelf`

## 待做

1. **`License` 换 Java 源** —— 能同时消掉全工程仅有的两处已知行为差异：
   - `UserController.getUserLimit`（走无许可分支）
   - `YueduApi` 的 `/simple-web/*`（`license.type == host` 代替 `validHost`）
   - 根因：`License.validHost` 与调用点同批编译时在 psi2ir / IR lowering 两阶段崩
   - `src/main/java` 已验证可用（`QueryTTF.java`、`Encoding.java`、`BytesEncodingDetect.java` 都在那儿）
2. `BookController` 落地后回头验 `-Xsam-conversions=indy` 有没有让那 7 条 indy descriptor 对上
3. 剩余 38 个零散类（不在 `BookController` 里的那部分）

## 已记录的负结果（**不要再试**）

| 尝试 | 结果 | 记录 |
|---|---|---|
| `Book.kt` 改成 `var isInShelf` | IR lowering 必崩（即使 BookController 不存在） | 3a-46 |
| 去掉 `-Xjsr305=strict` | ABI 与字节码**零变化** | 3a-41 |
| `BookInfo` 8 参重载改 public | `BookInfo` 从 1 个 loose 变 8 个，更差 | 3a-40 |
| `BookController` 直接开写 | 71/114，ABI 12→50 | 3a-39 |
| `BookController` 脚本生成骨架 | 54/114，ABI 12→26，比第一次还差 | 3a-43 |

## 已完成的包（类名全对、ABI 零新增）

`analyzeRule`（10 文件 / 含 AnalyzeUrl 2842 行、AnalyzeRule 2420 行）、`webBook`（6 文件 28 类）、
`localBook`（6 文件 16 类）、`entities`（全部）、`controller`（除 BookController）、
`api`（`YueduApi` 148 类）、`utils`（ExtKt 等）、`help` 部分（Java 源集）

## 关键环境事实

- `git` 必须用 `C:/Program Files/Git/cmd/git.exe`（其他路径 exit 126/128）
- 构建：`rm -rf build && gradlew.bat classes "-PrestoreBootstrap=D:\Projects\reader\BOOT-INF\classes" --no-daemon`，`JAVA_HOME=D:\Tools\java\jdk-11.0.8+10`
- **每次改完必须 `rm -rf build`**（增量编译会报 `conflicting dependencies`）
- `src/main/java` 可用（Java 源性类往那儿放）
- `build.gradle.kts` 现有 flags：`-Xjsr305=strict` + `-Xsam-conversions=indy`
- 备份：`scratch/build.gradle.kts.bak`（只有 strict）、`bak2`（strict + indy）、`Book.kt.bak`
