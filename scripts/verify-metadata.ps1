<#
.SYNOPSIS
    比对 Kotlin 类的 @Metadata d1/d2（声明形态判据）。

.DESCRIPTION
    verify-abi 比成员签名（排序后），verify-bytecode 比指令序列，两者都看不见：
      - 声明顺序
      - 属性的默认值写法
      - "函数 vs 属性"（getUserNameSpace 这种 get 开头但其实是 fun 的情况）
      - 注解（@JsonProperty 只进 RuntimeVisibleAnnotations）

    @Metadata 的 d1/d2 恰好编码了这些。d1/d2 逐字相同 ≈ 声明形态被复原。

    注意：d2 末位是模块名（rootProject.name），制品是 reader-pro、本地是 reader，
    所以比对前统一把 reader-pro 归一成 reader，否则每个类都会报差异。

.PARAMETER Artifact
    制品 classes 根目录（含 .class）

.PARAMETER Build
    本地构建产物根目录，默认 build\classes\kotlin\main

.PARAMETER JavaHome
    JDK 目录，默认 D:\Tools\java\jdk-11.0.8+10

.PARAMETER Only
    只查这些类（逗号分隔的内部类名，如 a.b.C,a.b.D）；省略则全量

.PARAMETER ShowList
    打印全部不一致的类名（默认只打印前 20 条）

.EXAMPLE
    .\scripts\verify-metadata.ps1
    .\scripts\verify-metadata.ps1 -Only "io.legado.app.model.rss.Rss"
#>
param(
    [string]$Artifact = "D:\Projects\reader\BOOT-INF\classes",
    [string]$Build = "build\classes\kotlin\main",
    [string]$JavaHome = "D:\Tools\java\jdk-11.0.8+10",
    [string[]]$Only = @(),
    [switch]$ShowList
)

$ErrorActionPreference = "Stop"
$javap = Join-Path $JavaHome "bin\javap.exe"
if (-not (Test-Path $javap)) { throw "javap not found: $javap" }
if (-not (Test-Path $Build)) { throw "build dir not found: $Build (先跑 gradlew classes)" }
if (-not (Test-Path $Artifact)) { throw "artifact dir not found: $Artifact" }

$buildRoot = (Resolve-Path $Build).Path
$artifactPath = (Resolve-Path $Artifact).Path

# 取 d1/d2，做模块名归一
function Get-MetadataLines([string]$clsPath) {
    $out = & $javap -v -p $clsPath 2>$null
    $lines = $out | Where-Object { $_ -match '^\s+d1=' -or $_ -match '^\s+d2=' }
    if (-not $lines) { return $null }
    return ($lines -join "`n") -replace 'reader-pro', 'reader'
}

$classes = Get-ChildItem -Path $buildRoot -Recurse -Filter *.class
# -File 调用时 -Only "a,b" 会作为单个字符串进来，这里拆开
if ($Only.Count -eq 1 -and $Only[0] -like "*,*") { $Only = $Only[0] -split '\s*,\s*' }
if ($Only.Count -gt 0) {
    $set = @{}
    foreach ($n in $Only) { $set[$n] = $true }
    $classes = $classes | Where-Object {
        $rel = $_.FullName.Substring($buildRoot.Length + 1)
        $n = $rel -replace '\\', '.' -replace '\.class$', ''
        $set.ContainsKey($n)
    }
}

$same = 0
$diff = 0
$missingArtifact = 0
$noMeta = 0
$diffList = New-Object System.Collections.Generic.List[string]

foreach ($f in $classes) {
    $rel = $f.FullName.Substring($buildRoot.Length + 1)
    $artFile = Join-Path $artifactPath $rel
    if (-not (Test-Path $artFile)) { $missingArtifact++; continue }

    $ours = Get-MetadataLines $f.FullName
    if ($null -eq $ours) { $noMeta++; continue }
    $theirs = Get-MetadataLines $artFile
    if ($null -eq $theirs) { $noMeta++; continue }

    if ($ours -eq $theirs) { $same++ } else { $diff++; $diffList.Add(($rel -replace '\\','.' -replace '\.class$','')) }
}

Write-Host ""
Write-Host ("已比对类数       : {0}" -f ($same + $diff))
Write-Host ("@Metadata 一致    : {0}" -f $same) -ForegroundColor Green
Write-Host ("@Metadata 不一致  : {0}" -f $diff) -ForegroundColor $(if ($diff -gt 0) { "Yellow" } else { "Green" })
Write-Host ("制品中无此类      : {0}" -f $missingArtifact)
Write-Host ("无 @Metadata       : {0}" -f $noMeta) -ForegroundColor DarkGray

if ($diff -gt 0) {
    Write-Host ""
    Write-Host "不一致的类：" -ForegroundColor Yellow
    $show = if ($ShowList) { $diffList } else { $diffList | Select-Object -First 20 }
    foreach ($c in $show) { Write-Host ("  ! {0}" -f $c) }
    if (-not $ShowList -and $diffList.Count -gt 20) {
        Write-Host ("  ... 还有 {0} 个，加 -ShowList 看全" -f ($diffList.Count - 20))
    }
}
Write-Host ""
