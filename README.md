# reader-pro 3.2.14 源码重建

`reader-pro-3.2.14.jar` 的完整源码重建：**服务端**（Kotlin + Java，Spring Boot 2.1.6）+ **Web 前端**（Vue 2 + Vuex + Vue Router + ElementUI）。

代码按原制品逐类 / 逐模块还原，构建产物是可运行的 fat jar 与前端静态资源。

## 仓库结构

```
build.gradle.kts              构建脚本（Spring Boot / Kotlin / Java，Java 8 目标）
settings.gradle.kts
gradle.properties
gradle/wrapper/               Gradle 6.1.1 wrapper
gradlew / gradlew.bat
src/main/kotlin/              145 个 Kotlin 源文件（带 @Metadata 的类）
src/main/java/                Java 源文件
src/main/resources/
  application.yml             运行配置
  web/                        前端静态资源（构建产物）
  simple-web/                 电纸书 UI（源码化，含 7 个 js）
Dockerfile                    镜像构建
.github/workflows/build.yml   CI：构建 jar 并推 GHCR

# 前端源码（按产物 webpack 模块逐模块还原）
index.html                    入口页
manifest.json                 PWA manifest
service-worker.js             离线缓存
css/                          5 个样式文件（app / chunk-vendors / index / reader / setting）
img/                          图标与静态图

# 可开发前端工程
dev/                          npm run dev 可直接开发（webpack 4 + vue-loader 15）
  src/views/                  38 个 .vue 单文件组件（从产物恢复）
  src/store/                  Vuex（29 getters / 47 mutations，源自产物 1a58）
  src/mixins/                 global（26 computed + 7 方法）+ root-methods（10 个数据加载）
  src/utils/                  storage / event-bus / async / clone / common / font / regenerator / merge / date-format
  src/config/defaults.js      themes / fonts / shelfConfig / searchConfig（源自产物 bfff）
  webpack.dev.js              dev server 8930，proxy 可配后端
```

## 前端源码（src/）

按原产物的模块 id 组织，`src/module-loader.js` 复刻了 webpack 的 `__webpack_require__` 语义
（含 `.n()` 返回带 `.a` 的 getter、`.e()` 返回已解析 Promise、`__webpack_require__.p` 为空串）。

- 样式与图片取自同一制品，未做改动；
- `src/views/` 下按原 scope id 分目录（如 `app/08713662/`）；
- 每个目录含 `.vue` + `template.html` + `render.js` + `options.json` + `style.css`，便于逐项核对。

### 静态运行

```bash
npx http-server .     # 或用任意静态服务器指向仓库根目录
```

页面以 hash 路由工作：`#/`、`#/setting`、`#/reader?bookUrl=<url>`；接口默认走同源 `/reader3/*`。

## 可开发前端（dev/）

```bash
cd dev
npm install
npm run dev           # http://localhost:8930
```

`webpack.dev.js` 的 proxy 决定连哪个后端：

| 后端 | secure | 书架 | 说明 |
|---|---|---|---|
| `127.0.0.1:4400` | false | 169 本 | 原版 reader-pro 3.2.14（无登录）|
| `127.0.0.1:4396` | true | 视账号 | Docker 容器，需登录（参数名是 `code`）|

实测（proxy → 4400）：书架 169 本全部渲染，侧栏完整，三页面零错误。

### 与原版对齐的两处行为

- `hasLogin` 按产物 `1a58` 的定义：`!isSecureMode || (isSecureMode && userInfo.username)`，**不是** `!!token`
- 启动时先调 `/getUserInfo` 判定 secure 模式，再 `init()` 拉首屏数据

### 已知限制

`VirtList`（虚拟滚动列表，产物模块 `febf`，32KB）目前是占位实现，
所以 `src/config/defaults.js` 里 `virtualOptimize` 暂设 `"no"`，走普通 v-for 列表。
真实实现已在 `docs/` 记录提取方法，接线后即可切回 `"yes"`。

## 服务端构建

```bash
./gradlew bootJar                 # 产出 build/libs/reader-pro-3.2.14.jar
```

或（本机 Gradle 不便时）：

```bash
java -cp <gradle-launcher.jar> org.gradle.launcher.GradleMain bootJar --offline
```

需要 JDK 8+（目标字节码 Java 8）。

## 运行

```bash
java -jar reader-pro-3.2.14.jar
# 默认 http://localhost:8080
```

### 环境变量

| 变量 | 说明 |
|---|---|
| `READER_APP_SECURE` | `true` 开启登录鉴权（多用户模式）|
| `READER_APP_SECUREKEY` | 管理员密码 |
| `READER_APP_INVITECODE` | 注册邀请码 |
| `READER_APP_REMOTEWEBVIEWAPI` | webview 服务地址，如 `http://readerwebview:8050` |
| `READER_APP_WORKDIR` | 工作目录 |
| `READER_APP_USERLIMIT` / `READER_APP_USERBOOKLIMIT` | 用户数 / 每用户书籍上限 |

`READER_APP_REMOTEWEBVIEWAPI` 指向的 webview 是**独立容器**：

```bash
docker run -d --name readerwebview --network reader-net \
  --restart always -e TZ=Asia/Shanghai hectorqin/remote-webview
```

缺它时 webview 类书源不可用（正文抓取走 Playwright），其它书源不受影响。

## CI 与镜像

`.github/workflows/build.yml`：

- **触发**：push tag `v*`，或 `workflow_dispatch` 手动（可选是否推镜像）
- **产物**：
  - `ghcr.io/<owner>/reader:<tag>`（如 `3.2.14`）
  - `ghcr.io/<owner>/reader:legacy`（固定别名）
  - `ghcr.io/<owner>/reader:latest`（跟随最新 tag）
- **权限**：`packages:write`（内置 `GITHUB_TOKEN` 即可）

发布新版本：

```bash
git tag v3.2.14-<后缀>
git push origin v3.2.14-<后缀>
```

## 许可

GNU General Public License v3.0（与 reader-pro 的 Legado 血统一致）。
