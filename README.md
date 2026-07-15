# Legado Server

`Legado Server` is a Linux command-line web service and browser UI inspired by the data model and user workflows of [warpdotsys/legado](https://github.com/warpdotsys/legado). It is not an Android application and does not contain Android code.

It runs a JVM HTTP/WebSocket service, stores data as portable JSON files, and serves a Vue reader, bookshelf, source editor, feature workbench, server console, and settings center from the same process.

## Status

Implemented today:

- Bookshelf, reading view, local TXT/EPUB/CBZ import and chapter splitting.
- Book-source and RSS-source CRUD, import/export, replace rules, TXT TOC rules, and source availability checks.
- Browser reading preferences, theme/cover preferences, bookmarks, reading records, reader export, local backups, and WebDAV snapshot management.
- Server console for backup management, source checks, local book import, maintenance, update checks, and App-data JSON collections.
- JSON data directory suitable for bind mounts, backups, and migration between Linux hosts.

Not implemented yet:

- Android-only integrations such as phone-call state, notifications, content providers, Room migrations, and Android media services.
- Android Java/Rhino bridge APIs (`java.*`, `source.*`, `book.*`, WebView DOM) and Android-only login flows. Portable HTTP, JSONPath, regex, CSS, XPath, and restricted `@js:` transformation rules are supported.

Remote search, remote TOC/content crawling, RSS refresh/full-text reading, automatic source switching, HTTP TTS, dictionary lookup, download tasks, TXT/EPUB/CBZ local import, WebDAV snapshot management, and LAN password/session authentication are implemented. The optional WebSocket compatibility listener supports search and source debugging through the same JVM engine as the HTTP API. Do not expose the service to an untrusted network without a firewall, reverse proxy, or VPN.

The upstream project remains the reference for Legado data formats and user-facing semantics. This repository documents only behavior that exists in this Linux server.

## Requirements

- JDK 17 or newer to build and run the server.
- Node.js 20+ and pnpm only when changing the Vue frontend.
- Docker is optional.

## Build

Build the frontend first when `modules/web` changes. The build copies static files into `server/src/main/resources/web`.

```bash
cd modules/web
pnpm install --frozen-lockfile
pnpm build

cd ../..
./gradlew :server:installDist
```

The runnable distribution is created under `server/build/install/legado-server`.

## Run

```bash
server/build/install/legado-server/bin/legado-server \
  --host 0.0.0.0 \
  --port 1122 \
  --ws-port 1123 \
  --data-dir /var/lib/legado-server
```

Open `http://SERVER_IP:1122/`. The root URL redirects to the Web UI.

Options:

```text
--host <host>       Bind address. Default: 0.0.0.0
--port <port>       HTTP port. Default: 1122
--ws-port <port>    WebSocket port. Default: HTTP port + 1
--data-dir <path>   Data directory. Default: $XDG_DATA_HOME/legado-server or ~/.local/share/legado-server
--web-root <path>   Serve Web assets from a directory instead of the bundled assets
--no-websocket      Disable the compatibility WebSocket listener
```

`--host 0.0.0.0` exposes the service on the LAN. Put it behind a reverse proxy, firewall, or VPN before exposing it beyond a trusted network.

## Data and Backup

The data directory contains JSON collections such as `books.json`, `bookSources.json`, `rssSources.json`, `appSettings.json`, and `bookmarks.json`, plus chapter files and `backups/`.

Use the server console or `GET /exportData` for a portable snapshot. `POST /importData` restores compatible data. The backup UI can also create local snapshots and, when configured, upload them to WebDAV.

## Operations

### PM2 deployment

The repository includes a deployment helper for the maintained Linux test host. It builds the Vue bundle and JVM distribution, uploads a versioned release, preserves the server data directory, starts the service through root PM2, enables the `pm2-root` systemd unit, and checks `/health`.

```powershell
.\scripts\deploy-remote.ps1
```

The default target is `transwarp@192.168.1.148:1132`. It never stores credentials. On hosts without passwordless sudo, run it from an interactive terminal with `-InteractiveSudo` so `sudo` can prompt locally:

```powershell
.\scripts\deploy-remote.ps1 -InteractiveSudo
```

The deployed release is under `/opt/legado-server/current`; persistent books, settings, and backups remain under `/var/lib/legado-server`.

Smoke test an installed distribution:

```bash
./server/scripts/smoke-test.sh
```

Build and run with Docker:

```bash
docker build -f server/Dockerfile -t legado-server .
docker run --rm -p 1122:1122 -v legado-data:/var/lib/legado-server legado-server
```

For systemd, adapt and install [server/packaging/legado-server.service](server/packaging/legado-server.service). The unit expects the distribution under `/opt/legado-server` and data under `/var/lib/legado-server`.

## Development Layout

```text
server/                         JVM service, packaging, and bundled resources
server/src/main/resources/web/  Built Vue application served by the service
modules/web/                    Vue source code and frontend build tooling
```

No Android module, Android Gradle plugin, mobile resource tree, or Android CI workflow is part of this repository.
