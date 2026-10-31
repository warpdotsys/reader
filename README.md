# reader (reverse-engineered)

Decompiled source recovered from `reader-pro-3.2.14.jar`.

## Source artifact

| Field | Value |
| --- | --- |
| Artifact | `reader-pro-3.2.14.jar` |
| Size | 72,913,887 bytes |
| Format | Spring Boot 2.1.6.RELEASE executable fat jar |
| Main-Class | `org.springframework.boot.loader.JarLauncher` |
| Start-Class | `com.htmake.reader.ReaderApplicationKt` |
| Language | Kotlin (compiled to JVM bytecode) |
| Entry point | `com.htmake.reader.ReaderApplicationKt#main` |

## How this tree was produced

1. The jar was unpacked with `jar xf`.
2. Application classes (`BOOT-INF/classes`, 936 `.class` files) were re-archived into a flat
   classes jar.
3. The classes jar was decompiled with **CFR 0.152**, with the bundled `BOOT-INF/lib` jars
   supplied as an extra classpath so library types resolve.
4. Non-class entries from `BOOT-INF/classes` were copied to `src/main/resources`.

No bytecode was modified and nothing was executed from the artifact beyond the decompiler.

## Layout

```
src/main/java/            Decompiled Java sources
  com/htmake/reader/       Application code (controllers, services, entities, verticles)
  io/legado/app/           Vendored/repackaged Legado runtime classes
  me/ag2s/                 Vendored EPUB and UMD reader utilities
  org/kxml2/               Vendored XML pull parser
src/main/resources/       Non-class assets from BOOT-INF/classes
  application.yml, banner.txt, logback-spring.xml, simplelogger.properties
  web/, simple-web/        Web UI bundles
  images/, icons/, epub/, dtd/, defaultData/, bookSourceDebug/
docs/dependencies.md      Inventory of the 85 bundled third-party jars (not vendored here)
docs/decompilation-report.txt  CFR per-method report (unstructured methods and failures)
```

## Known limitations

- **Kotlin coroutines.** `suspend` functions compile to state machines, so every `invokeSuspend`
  frame and many suspend bodies are reported by CFR as "Unable to fully structure code" and
  appear as `switch`-based state machines rather than idiomatic linear code. Behaviour is
  preserved, readability is not.
- **Two methods failed outright** (`io.legado.app.help.coroutine.Coroutine`,
  `io.legado.app.model.webBook.BookChapterList#analyzeChapterList`). See
  `docs/decompilation-report.txt` for the exact list.
- **No build files.** The original Gradle configuration was not part of the jar; it has not been
  reconstructed. `docs/dependencies.md` lists what the artifact linked against.
- **No comments, no original names for synthetic members.** Local variable names, parameter
  names outside `@Metadata`, and line numbers are not recoverable.
- Third-party libraries are **not** included. The 85 dependency jars stay out of this repository.

## Notice

This repository contains decompiled output of a third-party artifact and is published for
research and interoperability purposes. All rights to the original software remain with its
authors.
