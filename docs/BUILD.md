# 构建

## 工具链

| 组件 | 版本 | 原因 |
| --- | --- | --- |
| JDK | 11 | Spring Boot 2.1.6 与 Gradle 6.1.1 不支持 Java 17+。制品类是 Java 8（major 52），构建目标为 1.8。 |
| Gradle | 6.1.1 | 由 `gradle/wrapper/gradle-wrapper.properties` 固定。 |
| Kotlin | 1.5.21 | 与制品内 `kotlin-stdlib` 一致。 |
| Spring Boot 插件 | 2.1.6.RELEASE | 与制品清单里的 `Spring-Boot-Version` 一致。 |

工具链不随仓库提供。请安装 JDK 11 并把 `JAVA_HOME` 指向它。

### 获取固定版本的 JDK

参考构建使用 AdoptOpenJDK 11.0.8+10。任何经验证的 11.x JDK 均可。

```bash
curl -L -o jdk11.zip \
  "https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_windows_hotspot_11.0.8_10.zip"
unzip jdk11.zip -d .tools
export JAVA_HOME="$PWD/.tools/jdk-11.0.8+10"
```

```powershell
curl.exe -L -o jdk11.zip "https://github.com/AdoptOpenJDK/openjdk11-binaries/releases/download/jdk-11.0.8%2B10/OpenJDK11U-jdk_x64_windows_hotspot_11.0.8_10.zip"
tar -xf jdk11.zip -C .tools
$env:JAVA_HOME = (Resolve-Path '.tools\jdk-11.0.8+10').Path
```

`.tools/` 已被 git 忽略。

## 构建

```bash
./gradlew build
```

```powershell
.\gradlew.bat build
```

产物：`build/libs/reader-3.2.14.jar`。

Gradle wrapper 首次使用时会下载 Gradle 6.1.1。

## 核验

### 依赖一致性

`docs/dependencies.txt` 逐文件名记录了制品 `BOOT-INF/lib` 里的 85 个库。`verifyDependencyParity` 任务解析 `runtimeClasspath`，与这份清单逐项比对，**不一致时直接让构建失败**。它已挂到 `check` 上，所以 `./gradlew build` 会强制执行。

```bash
./gradlew verifyDependencyParity
```

迁移期间确实需要临时放宽时，加 `-PallowParityDrift` 可把失败降级为警告。

制品不遵循任何已发布的 BOM，因此除依赖声明外，构建脚本里还有两类显式约束：

- `resolutionStrategy.eachDependency`：`io.netty` → `4.1.42.Final`（Vert.x 3.8.5 的 Netty 线，不是 Spring Boot 2.1.6 自带的那条）；`com.fasterxml.jackson.core` → `2.9.9` / `annotations:2.9.0`。
- `resolutionStrategy.force`：`kotlin-stdlib{,-jdk7,-jdk8}` 与 `kotlin-reflect` → `1.5.21`，`kotlin-stdlib-common` → `1.5.30`，`slf4j-api` → `1.7.26`，`commons-lang3` → `3.8.1`。这些不锁定就会被传递依赖抬到更高版本。

另有一处**已记录的命名差异**：Gradle 的 okio 模块元数据把 JVM 制品声明为 `okio-jvm-2.8.0.jar`，实际下载的文件名却是 `okio-2.8.0.jar`（字节相同）。校验任务按制品声明的名字归一，代码里有注释说明。

### 确定性打包

`bootJar` 关闭了文件时间戳并固定归档顺序，因此相同输入的重新构建会产生相同归档。它同时会断言入口类。

## 已知工具链限制

- Gradle 6.1.1 只能运行在 JDK 8–13 上。用 JDK 21 会在配置阶段之前就失败。
- 构建脚本里**故意没有** `jcenter()`；所有声明的依赖都能从 Maven Central 解析（引入时已逐个探测）。
- `src/lib/rhino-1.7.13-1.jar` 与 `src/lib/xmlpull-1.1.3.1.jar` 是本地内嵌的，因为参考制品内就是这两个文件。
