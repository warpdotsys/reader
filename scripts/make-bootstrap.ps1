# 生成「只含尚未迁入的类」的编译引导副本。
#
# 背景：-PrestoreBootstrap 会把整份 BOOT-INF/classes 挂到 compileOnly 上。
# 一旦某个类本仓库已经有源码，同一个 FQN 就会在 classpath 上出现两份；
# 若该类的父类/字段类型也被还原，Kotlin 会报
#   Cannot access class '...'. Check your module classpath for missing or conflicting dependencies
# 因此需要一份把「已迁入的类」删掉的副本。
#
# 注意：classpath 条目只能是目录或 jar。不要把这批 .class 文件逐个挂上去——
# 裸 .class 文件在 classpath 上等于不存在。
#
# 用法：./scripts/make-bootstrap.ps1 -Artifact <解包后的 BOOT-INF/classes 目录>

param(
    [Parameter(Mandatory = $true)][string]$Artifact,
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot)
)

$ErrorActionPreference = 'Stop'

$src = (Resolve-Path $Artifact).Path
if (-not (Test-Path $src -PathType Container)) { throw "不是目录：$src" }
$dst = Join-Path (Split-Path -Parent $src) 'classes-filtered'

if (Test-Path $dst) { Remove-Item $dst -Recurse -Force }
robocopy $src $dst /E /NFL /NDL /NJH /NJS | Out-Null
if (-not (Test-Path $dst -PathType Container)) { throw "复制失败：$dst" }

# 从本仓库源码推导「已迁入」的类前缀：X.kt 可能产出 X 与 XKt（文件门面）
$prefixes = New-Object System.Collections.Generic.HashSet[string]
foreach ($root in @((Join-Path $ProjectRoot 'src\main\kotlin'), (Join-Path $ProjectRoot 'src\main\java'))) {
    if (-not (Test-Path $root)) { continue }
    Get-ChildItem $root -Recurse -File -Include *.kt, *.java | ForEach-Object {
        $rel = $_.FullName.Substring($root.Length + 1).Replace('\', '/')
        $dir = if ($rel.Contains('/')) { $rel.Substring(0, $rel.LastIndexOf('/') + 1) } else { '' }
        $base = [IO.Path]::GetFileNameWithoutExtension($rel)
        [void]$prefixes.Add("$dir$base")
        [void]$prefixes.Add("$dir${base}Kt")
    }
}

$removed = 0
Get-ChildItem $dst -Recurse -File -Filter *.class | ForEach-Object {
    $rel = $_.FullName.Substring($dst.Length + 1).Replace('\', '/')
    $outer = $rel.Substring(0, $rel.Length - 6).Split('$')[0]
    if ($prefixes.Contains($outer)) {
        Remove-Item $_.FullName -Force
        $removed++
    }
}

$kept = (Get-ChildItem $dst -Recurse -File).Count
Write-Output "已迁入类前缀 : $($prefixes.Count)"
Write-Output "删除 class 数 : $removed"
Write-Output "剩余文件数    : $kept"
Write-Output "输出目录      : $dst"
Write-Output ''
Write-Output "用法：gradlew classes -PrestoreBootstrap=$dst"
