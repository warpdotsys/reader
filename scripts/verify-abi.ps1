<#
.SYNOPSIS
    把 src/main/java 里已恢复的类，与参考制品（reader-pro-3.2.14.jar）的字节码做 ABI 比对。

.DESCRIPTION
    ABI = 类声明、成员声明、JVM 描述符、泛型签名。它不含方法体，所以本脚本通过不代表行为一致，
    只说明"接口没跑偏"。这是每批源码迁入时的准入检查之一，见 docs/ROADMAP.md 的验收标准。

.PARAMETER Artifact
    参考制品：可以是 .jar，也可以是已经解包出来的 BOOT-INF/classes 目录。

.PARAMETER JavaHome
    含 javap 的 JDK 目录，默认取 $env:JAVA_HOME。

.PARAMETER ProjectRoot
    工程根目录，默认取脚本所在目录的上一级。

.EXAMPLE
    ./scripts/verify-abi.ps1 -Artifact ../reader-pro-3.2.14.jar
#>
param(
    [Parameter(Mandatory = $true)][string]$Artifact,
    [string]$JavaHome = $env:JAVA_HOME,
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot)
)

$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($JavaHome)) {
    throw '需要 JDK 11，请设置 JAVA_HOME 或传 -JavaHome。'
}
$javap = Join-Path $JavaHome 'bin\javap.exe'
if (-not (Test-Path -LiteralPath $javap)) {
    throw "找不到 javap：$javap"
}

# Kotlin 与 Java 的编译产物在不同目录，两者都要在核验 classpath 上。
$newCp = @(
    (Join-Path $ProjectRoot 'build\classes\kotlin\main'),
    (Join-Path $ProjectRoot 'build\classes\java\main')
) | Where-Object { Test-Path -LiteralPath $_ }
if (-not $newCp) {
    throw "还没编译过：$ProjectRoot\build\classes。先跑 ./gradlew classes。"
}
$newCp = $newCp -join ';'

# 制品是 fat jar 时，把 BOOT-INF/classes 解到临时目录作为参考 classpath。
$staging = $null
if (Test-Path -LiteralPath $Artifact -PathType Container) {
    $oldCp = (Resolve-Path -LiteralPath $Artifact).Path
}
else {
    $staging = Join-Path ([IO.Path]::GetTempPath()) ("reader-abi-ref-" + [Guid]::NewGuid().ToString('N'))
    New-Item -ItemType Directory -Path $staging | Out-Null
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [IO.Compression.ZipFile]::OpenRead((Resolve-Path -LiteralPath $Artifact).Path)
    try {
        $prefix = 'BOOT-INF/classes/'
        foreach ($entry in $zip.Entries) {
            if (-not $entry.FullName.StartsWith($prefix) -or $entry.FullName.EndsWith('/')) { continue }
            $relative = $entry.FullName.Substring($prefix.Length)
            $target = Join-Path $staging $relative
            New-Item -ItemType Directory -Path (Split-Path $target) -Force | Out-Null
            [IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $target, $true)
        }
    }
    finally { $zip.Dispose() }
    $oldCp = $staging
}

function Get-AbiDump([string]$ClassPath, [string]$ClassName) {
    $raw = & $javap -p -s -classpath $ClassPath $ClassName 2>&1
    $raw |
        Where-Object { $_ -notmatch '^Compiled from' -and $_ -notmatch '^Warning' -and $_ -notmatch '^Error' } |
        ForEach-Object { ($_ -replace '\s+', ' ').Trim() } |
        Where-Object { $_ -ne '' } |
        Sort-Object
}

try {
    # 枚举「已编译出的类」，而不是从源文件名推类名。
    # 源文件名推不出 Kotlin 文件门面（LogUtils.kt -> LogUtilsKt），也推不出同文件里的
    # 多个顶层类（AnkoHelps.kt -> AnkoHelpsKt + AttemptResult）。
    # 用文件名推导时，javap 两边都找不到该类，两边的输出都会是同一行报错，于是被判「一致」——
    # 那正是静默遗漏。
    $classDirs = @(
        (Join-Path $ProjectRoot 'build\classes\kotlin\main'),
        (Join-Path $ProjectRoot 'build\classes\java\main')
    ) | Where-Object { Test-Path -LiteralPath $_ }

    $classes = @(
        foreach ($dir in $classDirs) {
            Get-ChildItem -LiteralPath $dir -Recurse -File -Filter *.class | ForEach-Object {
                $_.FullName.Substring($dir.Length + 1) -replace '\\', '.' -replace '\.class$', ''
            }
        }
    ) | Sort-Object -Unique

    $identical = 0
    $differences = New-Object System.Collections.ArrayList
    $unavailable = New-Object System.Collections.ArrayList
    foreach ($class in $classes) {
        # 制品里没有的类不能算「一致」——那只是两边都找不到。
        $referenceClassFile = Join-Path $oldCp ($class.Replace('.', '\') + '.class')
        if (-not (Test-Path -LiteralPath $referenceClassFile)) {
            [void]$unavailable.Add($class)
            continue
        }
        $reference = Get-AbiDump $oldCp $class
        $restored = Get-AbiDump $newCp $class
        if (($reference -join "`n") -ceq ($restored -join "`n")) { $identical++ }
        else {
            [void]$differences.Add($class)
            Write-Output "--- $class ---"
            Compare-Object $reference $restored | Format-Table -AutoSize | Out-String | Write-Output
        }
    }

    Write-Output "已比对类数 : $($classes.Count)"
    Write-Output "ABI 一致   : $identical"
    Write-Output "ABI 不一致 : $($differences.Count)"
    Write-Output "制品中无此类 : $($unavailable.Count)"
    if ($unavailable.Count -gt 0) {
        $unavailable | ForEach-Object { Write-Output "  ? $_" }
    }
    if ($differences.Count -gt 0) { exit 1 }
}
finally {
    if ($staging -and (Test-Path -LiteralPath $staging)) { Remove-Item -LiteralPath $staging -Recurse -Force }
}
