# reader

`reader-pro-3.2.14.jar` 的源码恢复工程 —— 一个用 Kotlin 写的 Spring Boot 2.1.6 / Vert.x 3.8.5 阅读服务。

本仓库当前是**构建地基**。它的职责是把参考制品的精确工具链和依赖集合变成可复现的，这样应用源码才能按批次、可核验地迁入，而不是把无法验证的反编译产物直接倒进来。

## 现状

| 部分 | 状态 |
| --- | --- |
| 工具链（JDK 11、Gradle 6.1.1、Kotlin 1.5.21、Java 8 目标） | 已可复现 |
| 依赖集合对齐制品 `BOOT-INF/lib` | 85/85 完全一致，由 `build` 强制校验 |
| 应用资源（从制品提取） | 已在 `src/main/resources` |
| 应用源码 | **尚未迁入** —— 见 [docs/ROADMAP.md](docs/ROADMAP.md) |
| fat jar 打包 | `bootJar` 已配置入口 `com.htmake.reader.ReaderApplicationKt` |

目前 `gradlew build` 可以通过。它打包的是一个空应用，这在源码迁移期间是预期状态 —— **它还不是可运行的服务**。

## 构建

需要 **JDK 11**（参考制品早于 Gradle 与 Spring Boot 支持 Java 17+ 的版本）。完整说明（含固定版本的 AdoptOpenJDK 11.0.8 下载方式）见 [docs/BUILD.md](docs/BUILD.md)。

```bash
export JAVA_HOME=/path/to/jdk-11
./gradlew build
```

```powershell
$env:JAVA_HOME = "D:\Tools\java\jdk-11.0.8+10"
.\gradlew.bat build
```

检查依赖相对参考制品的漂移（`build` 已包含此校验，不一致即失败）：

```bash
./gradlew verifyDependencyParity
./gradlew build -PallowParityDrift   # 迁移期间临时放宽为警告
```

## 目录结构

```
build.gradle.kts           Spring Boot / Kotlin / Java 构建脚本
settings.gradle.kts
gradle/wrapper/            Gradle 6.1.1 wrapper
src/main/java/             应用源码（Kotlin 与 Java 共用此根目录）
src/main/resources/        从制品提取的应用资源
src/lib/                   制品自带、无法从仓库解析的 jar（rhino、xmlpull）
src/test/java/             测试
docs/                      溯源、构建、架构、路线图、依赖记录
reference/decompiled/      CFR 反编译产物，仅作迁移证据，不参与构建
```

## 原则

- **以制品为准。** 行为、资源字节和依赖版本都取自 `reader-pro-3.2.14.jar`，不靠记忆或猜测。
- **反编译产物是证据，不是源码。** `reference/decompiled/` 永远不会进入构建路径，见 [reference/README.md](reference/README.md)。
- **不做空实现。** 一个方法要么来自可辩护的来源被恢复，要么就不存在并登记在路线图里。
- **任何声称的能力都必须可核验。** 未验证的内容在文档中一律标注为未验证。

## 文档

- [docs/PROVENANCE.md](docs/PROVENANCE.md) —— 制品是什么，每条结论如何得出
- [docs/BUILD.md](docs/BUILD.md) —— 工具链、构建与核验
- [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) —— 参考应用的模块地图
- [docs/ROADMAP.md](docs/ROADMAP.md) —— 源码迁移批次与验收标准
- [docs/dependencies.txt](docs/dependencies.txt) —— 85 个内嵌依赖，逐项原样记录

## 许可

GNU General Public License v3.0 —— 见 [LICENSE](LICENSE)。恢复的代码库源自 `reader-pro` 所基于的 GPL-3.0 Legado 血统。
