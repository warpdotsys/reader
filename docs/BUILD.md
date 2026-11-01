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

### ABI 一致性

`scripts/verify-abi.ps1` 把 `src/main/java` 里的每个类，与参考制品字节码用 `javap -p -s` 逐类比对：
类声明、成员声明、JVM 描述符、泛型签名。它需要在有制品的机器上运行，所以不进默认构建。

```powershell
./scripts/verify-abi.ps1 -Artifact C:\path\to\reader-pro-3.2.14.jar -JavaHome D:\Tools\java\jdk-11.0.8+10
```

`-Artifact` 也可以直接给已解包的 `BOOT-INF/classes` 目录。有任何差异时退出码为 1，并打印 diff。

注意：ABI 一致**不等于**行为一致。它只保证接口没跑偏。

### 恢复期编译引导

`io.legado.app` 内部依赖成环：`data.entities` 引用 `utils`/`help`/`model`，`help` 又反过来引用
`data.entities`，因此不存在"按包逐个迁入且始终可编译"的顺序。为此提供一个显式开关：把参考制品的
应用类与内嵌依赖挂到 `compileOnly` 上，尚未迁入的类从制品解析，已迁入的由本仓库源码提供。

```powershell
# 需要先解包制品：jar xf reader-pro-3.2.14.jar
.\gradlew.bat classes -PrestoreBootstrap=D:\path\to\BOOT-INF\classes
```

这样每一小批源码都能独立编译、独立核验。

**不传该参数时制品完全不参与构建。** 正式构建与发布一律不带它。

### 类级行为差分

静态判据（ABI、常量、字节码）都只是代理。制品本身是可运行的，所以最接近事实的判据是
**同一批输入，两边输出相同**。

`scripts/diffcheck/ClassDiffCheck.java` 把制品的类与本仓库的同名类装进两个互相隔离的
类加载器（各自带一份依赖 jar，父加载器为 platform），反射调用同一方法并比对结果。

```powershell
javac -encoding UTF-8 -d scripts\diffcheck\out scripts\diffcheck\ClassDiffCheck.java
java -Dfile.encoding=UTF-8 -cp scripts\diffcheck\out ClassDiffCheck `
  D:\path\to\BOOT-INF\classes `
  "build\classes\kotlin\main;build\classes\java\main" `
  D:\path\to\BOOT-INF\lib
```

覆盖范围：输入输出都是 JDK 类型的纯函数。需要外部状态、网络、文件系统的方法不在覆盖内。

**注意一个易犯的 harness 错误**：`InputStream` 这类可变输入必须每次调用都新建。
复用同一个对象时，先跑的一侧会把它读空，后跑的必然失败——那是 harness 的 bug，
不是被测代码的 bug。第一次跑就踩了这个坑，XML 两条被误报为 FAIL。

### 确定性打包

`bootJar` 关闭了文件时间戳并固定归档顺序，因此相同输入的重新构建会产生相同归档。它同时会断言入口类。

## 已知工具链限制

- Gradle 6.1.1 只能运行在 JDK 8–13 上。用 JDK 21 会在配置阶段之前就失败。
- 构建脚本里**故意没有** `jcenter()`；所有声明的依赖都能从 Maven Central 解析（引入时已逐个探测）。
- `src/lib/rhino-1.7.13-1.jar` 与 `src/lib/xmlpull-1.1.3.1.jar` 是本地内嵌的，因为参考制品内就是这两个文件。
