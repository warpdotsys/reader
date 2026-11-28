# reader-pro 3.2.14 —— 完全由本仓库源码构建的镜像
#
# 基础镜像按现有 reader-local:3.2.14 反推：
#   发行版 Ubuntu 26.04 / Temurin JDK 11.0.33+10 / JAVA_HOME=/opt/java/openjdk
#   容器结构：WorkDir=/app，jar=/app/bin/reader.jar，Expose=8080
#
# 构建：
#   docker build -t reader-local:3.2.14 .
#
# 注意：敏感配置（secureKey / inviteCode / 账号）不写进镜像，
#      运行时用 -e 或 compose 的 env_file 传入。

# ---------- 构建阶段：Gradle + JDK 11 打 bootJar ----------
FROM eclipse-temurin:11-jdk AS build
WORKDIR /src

# 先只拷构建脚本，利用层缓存
COPY gradle/            ./gradle/
COPY gradlew            ./gradlew
COPY gradlew.bat        ./gradlew.bat
COPY build.gradle.kts   ./
COPY settings.gradle.kts ./
COPY src/lib/           ./src/lib/

# 依赖（离线构建时可用 --offline；CI 中先 warm 一次）
RUN chmod +x ./gradlew || true

# 再拷源码
COPY src/               ./src/

# bootJar：注意 Spring Boot 项目里 `jar` 任务会被 skip，必须用 bootJar
# CI 上无 gradlew 执行权限问题时可直接用 gradle 镜像内的 gradle 命令
RUN ./gradlew bootJar --no-daemon --console=plain \
        || gradle bootJar --no-daemon --console=plain

# ---------- 运行阶段：只要 JRE ----------
FROM eclipse-temurin:11-jdk

ENV TZ=Asia/Shanghai \
    LANG=en_US.UTF-8 \
    LANGUAGE=en_US:en \
    LC_ALL=en_US.UTF-8 \
    READER_APP_WORKDIR=/ \
    READER_APP_CACHECHAPTERCONTENT=true

WORKDIR /app

# jar 放到与原镜像一致的位置
COPY --from=build /src/build/libs/reader-pro-3.2.14.jar /app/bin/reader.jar

# 与原镜像一致：数据目录外挂
VOLUME ["/storage"]

EXPOSE 8080

# Temurin 镜像自带该 entrypoint，保持与原镜像一致
ENTRYPOINT ["/__cacert_entrypoint.sh"]
CMD ["java", "-Dreader.app.workDir=/", "-jar", "/app/bin/reader.jar"]
