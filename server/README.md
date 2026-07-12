# Legado Linux server

This module is a standalone JVM command-line web service for Linux.

It reuses Legado's existing static web assets and exposes the same HTTP API surface used by the Android `WebService` where the behavior is platform-neutral. Android-only features are intentionally isolated behind compatible responses so the service can run without Android framework classes.

## Build

```bash
./gradlew :server:installDist
```

For a distributable archive:

```bash
./gradlew :server:distTar
```

## Run

```bash
server/build/install/legado-server/bin/legado-server --host 0.0.0.0 --port 1122
```

Useful options:

```text
--host <host>       Bind address, default 0.0.0.0
--port <port>       HTTP port, default 1122
--ws-port <port>    WebSocket port, default port + 1
--data-dir <path>   Data directory, default $XDG_DATA_HOME/legado-server or ~/.local/share/legado-server
--web-root <path>   Override static web asset directory
--no-websocket      Disable compatibility WebSocket listener
```

Open `http://localhost:1122/` after the service starts. The Web console is at
`http://localhost:1122/vue/index.html#/server`, the App feature workbench is at
`http://localhost:1122/vue/index.html#/features`, and the settings center is at
`http://localhost:1122/vue/index.html#/settings`.

## Smoke test

```bash
./server/scripts/smoke-test.sh
```

The smoke test starts the installed service on a high local port, checks health,
source persistence, source health checks, settings persistence, App data
persistence, local backups, and local TXT chapter/content reads.

## Docker

```bash
docker build -f server/Dockerfile -t legado-server .
docker run --rm -p 1122:1122 -v legado-data:/var/lib/legado-server legado-server
```

## systemd example

After copying `server/build/install/legado-server` to `/opt/legado-server`, install
`server/packaging/legado-server.service` as `/etc/systemd/system/legado-server.service`
and adjust the `User`, `Group`, and paths for your host.

## Current scope

Implemented:

- Static Legado web UI hosting.
- Web server console for status, import/export, local TXT upload, TXT TOC rules,
  replace rules, local backup snapshots, source health checks, restore, and
  feature coverage.
- Web settings center covering Android preference groups for home/bookshelf,
  themes/covers/welcome, reading, TTS, backup/WebDAV, network/source, manga, and
  maintenance. Settings persist in `appSettings.json` and are included in
  import/export backups.
- App feature workbench for Android-side data assets that are not simple
  preferences, including book groups, bookmarks, read records, HTTP TTS engines,
  cookies, dictionary rules, RSS article records, cache/source variables,
  download tasks, theme configs, and read styles. These collections have
  structured Web table/form editors plus JSON fallback editing, persist as JSON
  files in the server data directory, and are included under `appData` in
  import/export backups.
- Book source, RSS source, source health report, replace rule, bookshelf,
  chapter list, chapter content, read-config persistence.
- Local text-book upload through `addLocalBook`.
- Image proxy for local files and HTTP/HTTPS images.
- Compatibility WebSocket endpoints for search/debug, returning empty or explanatory responses instead of failing the UI.

Still Android-bound upstream:

- Full source-rule crawling and search engine.
- Linux implementations for WebDAV protocol sync, HTTP TTS/audio playback,
  notifications, scheduled maintenance jobs, and download services.
- Android Room database migrations.
- Android UI, notifications, wake locks, content provider, and system integration.
- Glide/Bitmap based cover processing.

Those pieces need to be extracted into a shared JVM core before this server can be a complete behavioral replacement for the Android app.
