# reader-pro 3.2.14 服务端源码重建

`reader-pro-3.2.14.jar` 的服务端源码重建工程：Kotlin + Java，Spring Boot 2.1.6 / Vert.x 3.8.5，Gradle 6.1.1。

代码按原制品的字节码逐类还原，构建产物是可运行的 fat jar（javas 启动后监听 `/reader3/*`）。

## 目录结构

```
build.gradle.kts      构建脚本（Spring Boot / Kotlin / Java，Java 8 目标）
settings.gradle.kts
gradle.properties
gradle/wrapper/       Gradle 6.1.1 wrapper
gradlew / gradlew.bat
src/main/kotlin/      145 个 Kotlin 源文件（带 @Metadata 的类）
src/main/java/        104 个 Java 源文件
src/main/resources/   159 个应用资源（含 simple-web、epub、书源默认数据等）
src/lib/              2 个制品自带、无法从仓库解析的 jar（rhino、xmlpull）
src/test/java/        1 个测试
LICENSE               GNU GPL v3.0
```

## 构建

需要 **JDK 11**（原制品的工具链早于 Gradle/Spring Boot 支持 Java 17+ 的版本）。

```bash
export JAVA_HOME=/path/to/jdk-11
./gradlew build
```

```powershell
$env:JAVA_HOME = "D:\Tools\java\jdk-11.0.8+10"
.\gradlew.bat build
```

产物：`build/libs/reader-pro-3.2.14.jar`。

依赖版本对齐原制品 `BOOT-INF/lib`（86 个依赖），由构建脚本的 dependency parity 校验保证：

```bash
./gradlew verifyDependencyParity
./gradlew build -PallowParityDrift   # 临时放宽为警告
```

## 运行

```bash
java -Dfile.encoding=UTF-8 -jar build/libs/reader-pro-3.2.14.jar \
  --reader.server.port=4396 \
  --server.port=4396 \
  --reader.app.workDir=/path/to/data
```

- 数据目录下会生成 `storage/`（`data/<userNS>/` 存书架、书源、替换规则等）与 `logs/`；
- **不要**传 `--reader.server.contextPath=/reader3`：Vert.x 已自带 `/reader3/*` 路由，再加会让全部 API 404；
- 接口鉴权用 `accessToken=<userNS>:<token>`，token 由 `POST /reader3/login`
  （body `{"username","password","isLogin":true}`）签发。

## 说明

- 源码以原制品为准：行为、资源字节、依赖版本都取自 `reader-pro-3.2.14.jar`；
- 恢复的代码库源自 `reader-pro` 所基于的 GPL-3.0 Legado 血统；
- 未经验证的能力不在本仓库中声称。

## 许可

GNU General Public License v3.0 —— 见 [LICENSE](LICENSE)。
