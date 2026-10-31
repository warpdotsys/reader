# 架构

参考应用的模块地图，读自制品的包结构。包名沿用制品自身的命名。

## 运行形态

单进程 Spring Boot，没有 servlet 容器。`ReaderApplication` 启动 Spring，随后部署一个 Vert.x verticle，由它持有 HTTP/WebSocket 服务器。业务逻辑从 Vert.x 路由处理器进入，而不是 Spring MVC 控制器 —— `@Component` 用于装配，不用于请求映射。

```
SpringApplication
  └── ReaderApplication            @SpringBootApplication, @EnableScheduling
        └── 部署 Verticle
              └── YueduApi                   路由表  (/reader3/...)
                    ├── *Controller          请求处理器
                    └── RestVerticle         服务启动、静态资源
```

## `com.htmake.reader` —— 应用层

| 包 | 职责 |
| --- | --- |
| （根） | `ReaderApplication` 入口、`ReaderApplicationKt.main`、Spring 事件 |
| `api` | `YueduApi` 路由注册、`ReturnData` 响应封装 |
| `api.controller` | 请求处理器：书籍、书源、RSS、用户、文件、WebDAV、TTS、许可证 |
| `config` | `AppConfig`、`BookConfig` —— 由 `application.yml` 绑定的应用配置 |
| `db` | `DB` 存储门面，底层是 `JSONTable` 与 `SQLTable` |
| `entity` | `User`、`License`、`ActiveLicense`、`MongoFile`、`Size`、`BasicError` |
| `init` | `ReaderAdapter` —— 把移植过来的 Legado 运行时接到本服务 |
| `lib.tts` | 语音合成客户端：常量、SSML 模型、服务、异常 |
| `utils` | 扩展函数、缓存、Mongo 访问、按用户加锁、webview 渲染 |
| `verticle` | `RestVerticle` —— Vert.x HTTP 服务、路由与静态资源 |

## `io.legado.app` —— 移植的 Legado 运行时

书源引擎，从 Legado 迁移而来。这是代码量最大的部分，也是相对原产品改动最少的部分。

| 包 | 职责 |
| --- | --- |
| `model.webBook` | 书籍信息、列表、目录与正文的解析流水线 |
| `model.analyzeRule` | 规则求值：JSONPath、CSS/JSoup、XPath、正则、JS |
| `model.localBook` | TXT、EPUB、CBZ、UMD、PDF 本地格式读取 |
| `model.rss` | RSS 按规则与按默认方式解析 |
| `data.entities` | `Book`、`BookSource`、`BookChapter`、`RssSource` 及规则 DTO |
| `help` | 缓存、默认数据、编码探测、HTTP 辅助、协程 |
| `lib.icu4j` | 字符集探测 |
| `adapters`、`utils`、`constant`、`exception` | 支撑代码 |

## `me.ag2s` —— 格式库

| 包 | 职责 |
| --- | --- |
| `epublib.*` | EPUB 读写 |
| `umdlib.*` | UMD 读取 |

## `org.kxml2` —— XML pull 解析器

内嵌是因为服务对外暴露了 XML 界面；`io`、`kdom`、`wap` 分别是解析器、文档模型与 WAP/WBXML 层。

## 资源

`src/main/resources` 原样取自制品，包含配置（`application.yml`、`logback-spring.xml`）、已构建的前端包（`web`、`simple-web`）、书源调试资源、默认数据，以及 EPUB/DTD 支撑文件。

前端包是原项目的构建产物。它们的源码不在制品中，目前也不在本仓库中。
