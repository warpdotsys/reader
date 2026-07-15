param(
  [string]$HostName = '192.168.1.148',
  [string]$User = 'transwarp',
  [int]$Port = 1132,
  [int]$WebSocketPort = 1133,
  [string]$InstallRoot = '/opt/legado-server',
  [string]$DataDir = '/var/lib/legado-server',
  [switch]$SkipBuild,
  [switch]$InteractiveSudo
)

$ErrorActionPreference = 'Stop'

function Invoke-Native {
  param([string]$File, [string[]]$Arguments)

  & $File @Arguments
  if ($LASTEXITCODE -ne 0) {
    throw "$File exited with code $LASTEXITCODE"
  }
}

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$remote = "$User@$HostName"

Push-Location $repoRoot
try {
  if (-not $SkipBuild) {
    Push-Location (Join-Path $repoRoot 'modules/web')
    try {
      Invoke-Native 'pnpm' @('build')
    } finally {
      Pop-Location
    }
    Invoke-Native (Join-Path $repoRoot 'gradlew.bat') @(':server:installDist', '--no-daemon')
  }

  $release = (& git rev-parse --short=12 HEAD).Trim()
  if (-not $release) { throw 'Unable to determine the current Git revision.' }

  $stagingPath = "/home/$User/.legado-server-upload-$release"
  Invoke-Native 'ssh' @($remote, "rm -rf $stagingPath")
  Invoke-Native 'scp' @('-rq', (Join-Path $repoRoot 'server/build/install/legado-server'), "${remote}:$stagingPath")

  $remoteScript = @"
set -euo pipefail
release='$release'
install_root='$InstallRoot'
data_dir='$DataDir'
staging='$stagingPath'

install -d -m 0755 "`$install_root/releases"
rm -rf "`$install_root/releases/`$release"
mv "`$staging" "`$install_root/releases/`$release"
find "`$install_root/releases/`$release/bin" -type f -exec chmod 0755 {} +
ln -sfn "`$install_root/releases/`$release" "`$install_root/current"
install -d -m 0750 "`$data_dir"

pm2 delete legado-server >/dev/null 2>&1 || true
pm2 start /bin/sh --name legado-server --interpreter none -- -c 'exec "$InstallRoot/current/bin/legado-server" --host 0.0.0.0 --port $Port --ws-port $WebSocketPort --data-dir "$DataDir"'
pm2 save
env PATH="`$PATH:/usr/bin" pm2 startup systemd -u root --hp /root
curl -fsS "http://127.0.0.1:$Port/health" >/dev/null
"@
  $encodedScript = [Convert]::ToBase64String([Text.Encoding]::UTF8.GetBytes($remoteScript))
  $remoteCommand = "sudo {0}bash -c 'echo $encodedScript | base64 -d | bash'"

  if ($InteractiveSudo) {
    Invoke-Native 'ssh' @('-tt', $remote, ($remoteCommand -f ''))
  } else {
    Invoke-Native 'ssh' @($remote, ($remoteCommand -f '-n '))
  }

  $health = & curl.exe -fsS "http://${HostName}:$Port/health"
  if ($LASTEXITCODE -ne 0) { throw "Remote health check failed for ${HostName}:$Port" }
  Write-Host "Deployed Legado Server $release to http://${HostName}:$Port/"
  Write-Host $health
} finally {
  Pop-Location
}
