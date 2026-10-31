# 溯源

本仓库的一切都源自同一个制品。本文记录该制品是什么，以及关于它的每类结论是如何得出的。

## 制品

| 字段 | 值 |
| --- | --- |
| 名称 | `reader-pro-3.2.14.jar` |
| 大小 | 72,913,887 字节 |
| SHA-256 | `b26fb4769d689d98ff26408ce79a275d719f360906c84acf52ff404e98030c8c` |
| 格式 | Spring Boot 可执行 fat jar |
| `Main-Class` | `org.springframework.boot.loader.JarLauncher` |
| `Start-Class` | `com.htmake.reader.ReaderApplicationKt` |
| `Spring-Boot-Version` | `2.1.6.RELEASE` |
| 类 | `BOOT-INF/classes/` |
| 依赖库 | `BOOT-INF/lib/` —— 85 个 jar |

制品本身不纳入版本库。把本地副本作为基线前，请按上面的 SHA-256 校验。

## 证据等级

关于恢复工程的每一条陈述都带有下列标签之一。

| 标签 | 含义 |
| --- | --- |
| **已核验** | 直接读自制品的字节、归档条目或字节码。 |
| **已推断** | 与多项彼此独立的制品事实一致，但未证明完全相同。 |
| **未核验** | 尚未确认。一律当作待办，绝不当作已完成。 |

## 溯源过程

### 本源码是如何得到的

本树是用 CFR 0.152 对制品的应用类反编译、再重新组织得到的。反编译产物逐字保存在 `reference/decompiled/` 下，且**不**在构建路径上。

反编译产物是关于字节码的证据，不是原始源码：Kotlin 的 `suspend` 函数会变成状态机，默认参数与内联泛型会丢失原有形态，还有两个方法完全反编译失败。`src/` 下的可读源是按包逐步重建的，与这份转储存在差异是预期内的。

### 逐项依据

| 事实 | 等级 | 依据 |
| --- | --- | --- |
| 入口类、Spring Boot 版本 | 已核验 | 制品中的 `META-INF/MANIFEST.MF` |
| 应用类数量与 Java 级别 | 已核验 | `BOOT-INF/classes` 下 936 个类全部为 major 52（Java 8） |
| 依赖库集合与版本 | 已核验 | `BOOT-INF/lib` 列表，记录在 `docs/dependencies.txt` |
| 资源集合 | 已核验 | `BOOT-INF/classes` 下的非 class 条目，原样拷入 `src/main/resources` |
| 应用行为 | 未核验 | 仅随源码恢复并与制品对比后逐步确认 |

### 已做的交叉校验

- 上面这个制品 SHA-256 在本地重算过，与另一处独立记录的值一致。
- `build.gradle.kts` 中声明的每个依赖，在声明前都确认过 Maven Central 上存在对应固定版本。
- `verifyDependencyParity` 会把解析出的 `runtimeClasspath` 与 `docs/dependencies.txt` 对照，因此版本相对制品的漂移是可见的。

## 边界

- 资源字节直接取自制品，因此 `src/main/resources/web` 与 `src/main/resources/simple-web` 下的前端包是原项目的构建产物，不是它的前端源码。
- 生产本树的过程中没有修改任何字节码，也没有执行制品中的任何代码。
