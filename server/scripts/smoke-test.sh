#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
BIN="$ROOT_DIR/server/build/install/legado-server/bin/legado-server"
PORT="${LEGADO_SMOKE_PORT:-19122}"
WS_PORT="${LEGADO_SMOKE_WS_PORT:-19123}"
DATA_DIR="$(mktemp -d)"
SERVER_PID=""

cleanup() {
  if [[ -n "$SERVER_PID" ]] && kill -0 "$SERVER_PID" 2>/dev/null; then
    kill "$SERVER_PID"
    wait "$SERVER_PID" 2>/dev/null || true
  fi
  rm -rf "$DATA_DIR"
}
trap cleanup EXIT

if [[ ! -x "$BIN" ]]; then
  echo "Missing installed server. Run ./gradlew :server:installDist first." >&2
  exit 1
fi

"$BIN" --host 127.0.0.1 --port "$PORT" --ws-port "$WS_PORT" --data-dir "$DATA_DIR" >/tmp/legado-server-smoke.log 2>&1 &
SERVER_PID="$!"

for _ in {1..30}; do
  if curl -fsS "http://127.0.0.1:$PORT/health" >/dev/null; then
    break
  fi
  sleep 0.5
done

curl -fsS "http://127.0.0.1:$PORT/health" | grep -q '"isSuccess": true'
curl -fsS "http://127.0.0.1:$PORT/getServerInfo" | grep -q '"bookSources"'
curl -fsS "http://127.0.0.1:$PORT/getAppSettings" | grep -q '"themeMode"'

SETTINGS_JSON="$DATA_DIR/settings.json"
cat >"$SETTINGS_JSON" <<'JSON'
{"main":{"language":"zh","bookshelfLayout":"1"},"network":{"threadCount":12}}
JSON
curl -fsS -H "Content-Type: application/json" --data-binary "@$SETTINGS_JSON" "http://127.0.0.1:$PORT/saveAppSettings" | grep -q '"language": "zh"'

APP_DATA_JSON="$DATA_DIR/book-group.json"
cat >"$APP_DATA_JSON" <<'JSON'
{"groupId":1001,"groupName":"Smoke","order":1,"enableRefresh":true,"show":true,"bookSort":-1,"onlyUpdateRead":false}
JSON
curl -fsS "http://127.0.0.1:$PORT/getAppDataKinds" | grep -q '"kind": "bookGroups"'
curl -fsS -H "Content-Type: application/json" --data-binary "@$APP_DATA_JSON" "http://127.0.0.1:$PORT/saveAppData?kind=bookGroups" | grep -q '"groupName": "Smoke"'
curl -fsS "http://127.0.0.1:$PORT/getAppData?kind=bookGroups" | grep -q '"groupName": "Smoke"'
curl -fsS "http://127.0.0.1:$PORT/exportData" | grep -q '"appData"'
BACKUP_RESPONSE="$(curl -fsS -H "Content-Type: application/json" --data '{}' "http://127.0.0.1:$PORT/createBackup")"
BACKUP_NAME="$(printf '%s' "$BACKUP_RESPONSE" | python3 -c 'import json,sys; print(json.load(sys.stdin)["data"]["fileName"])')"
curl -fsS "http://127.0.0.1:$PORT/getBackups" | grep -q "$BACKUP_NAME"
printf '{"fileName":"%s"}' "$BACKUP_NAME" | curl -fsS -H "Content-Type: application/json" --data-binary @- "http://127.0.0.1:$PORT/restoreBackup" | grep -q '"appSettings"'

SOURCE_JSON="$DATA_DIR/source.json"
cat >"$SOURCE_JSON" <<'JSON'
{"bookSourceUrl":"https://example.com","bookSourceName":"Example","enabled":true}
JSON
curl -fsS -H "Content-Type: application/json" --data-binary "@$SOURCE_JSON" "http://127.0.0.1:$PORT/saveBookSource" | grep -q '"isSuccess": true'
curl -fsS "http://127.0.0.1:$PORT/getBookSource?url=https%3A%2F%2Fexample.com" | grep -q '"bookSourceName": "Example"'
curl -fsS -H "Content-Type: application/json" --data '{"scope":"bookSources","timeoutMillis":3000,"limit":5}' "http://127.0.0.1:$PORT/checkSources" | grep -q '"reports"'
curl -fsS "http://127.0.0.1:$PORT/getSourceChecks" | grep -q '"sourceName": "Example"'
curl -fsS -H "Content-Type: application/json" --data '{}' "http://127.0.0.1:$PORT/deleteSourceChecks" | grep -q '"data": \[\]'

BOOK_FILE="$DATA_DIR/sample.txt"
cat >"$BOOK_FILE" <<'TXT'
第一章 开始
hello world
第二章 继续
next
TXT

UPLOAD_RESPONSE="$(curl -fsS -F "fileData=@$BOOK_FILE" "http://127.0.0.1:$PORT/addLocalBook?fileName=sample.txt")"
BOOK_URL="$(printf '%s' "$UPLOAD_RESPONSE" | python3 -c 'import json,sys,urllib.parse; print(urllib.parse.quote(json.load(sys.stdin)["data"]["bookUrl"], safe=""))')"
curl -fsS "http://127.0.0.1:$PORT/getChapterList?url=$BOOK_URL" | grep -q '"title": "第一章 开始"'
curl -fsS "http://127.0.0.1:$PORT/getBookContent?url=$BOOK_URL&index=1" | grep -q '"data": "next"'

echo "legado-server smoke test passed"
