# 计算还原顺序：按「谁引用谁」做拓扑剥离。
#
# 背景（本项目踩过的坑）：只要还有**未还原**的类引用某个类，那个类就不能先换成源码类
# ——编译器无法把二进制类的签名桥接到源码类上，会报
#   Cannot access class '...'. Check your module classpath for missing or conflicting dependencies
# 所以正确的还原顺序是：每一步只做「所有引用者都已还原」的类（即依赖图的叶子）。
#
# 实现：直接扫 .class 的常量池字节（比逐个 javap 快两个数量级），
# 抽出所有 io.legado.app.* 的类名作为引用边。
#
# 用法：./scripts/restore-order.ps1 -Artifact <BOOT-INF/classes>

param(
    [Parameter(Mandatory = $true)][string]$Artifact,
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot),
    [int]$Show = 40
)

$ErrorActionPreference = 'Stop'
$src = (Resolve-Path $Artifact).Path
$latin1 = [System.Text.Encoding]::GetEncoding('ISO-8859-1')

# 1. 制品里的顶层应用类
$classes = @{}
Get-ChildItem $src -Recurse -File -Filter *.class | ForEach-Object {
    $rel = $_.FullName.Substring($src.Length + 1).Replace('\', '.')
    $fqn = $rel.Substring(0, $rel.Length - 6)
    if ($fqn -match '\$') { return }
    $classes[$fqn] = $_.FullName
}

# 2. 引用边：只保留指向应用类的引用
$refs = @{}
foreach ($fqn in $classes.Keys) {
    $text = $latin1.GetString([IO.File]::ReadAllBytes($classes[$fqn]))
    $set = New-Object System.Collections.Generic.HashSet[string]
    foreach ($m in [regex]::Matches($text, 'io/legado/app/[A-Za-z0-9_$/]+')) {
        $target = $m.Value.Replace('/', '.')
        if ($classes.ContainsKey($target) -and $target -ne $fqn) { [void]$set.Add($target) }
    }
    $refs[$fqn] = $set
}

# 3. 已还原 = 编译产物里存在的类
$done = New-Object System.Collections.Generic.HashSet[string]
foreach ($root in @((Join-Path $ProjectRoot 'build\classes\kotlin\main'), (Join-Path $ProjectRoot 'build\classes\java\main'))) {
    if (-not (Test-Path $root)) { continue }
    Get-ChildItem $root -Recurse -File -Filter *.class | ForEach-Object {
        $rel = $_.FullName.Substring($root.Length + 1).Replace('\', '/')
        [void]$done.Add($rel.Substring(0, $rel.Length - 6).Replace('/', '.').Split('$')[0])
    }
}

# 4. 反向边：某类被谁引用
$referrers = @{}
foreach ($fqn in $refs.Keys) {
    foreach ($t in $refs[$fqn]) {
        if (-not $referrers.ContainsKey($t)) { $referrers[$t] = New-Object System.Collections.Generic.HashSet[string] }
        [void]$referrers[$t].Add($fqn)
    }
}

Write-Output ("制品顶层应用类 : {0}" -f $classes.Count)
Write-Output ("本仓库已还原   : {0}" -f $done.Count)
Write-Output ''

# 5. 现在就能做的：未还原，且所有未还原的引用者都不存在
$ready = New-Object System.Collections.ArrayList
foreach ($fqn in ($classes.Keys | Sort-Object)) {
    if ($done.Contains($fqn)) { continue }
    $blockers = @()
    if ($referrers.ContainsKey($fqn)) {
        foreach ($r in $referrers[$fqn]) { if (-not $done.Contains($r)) { $blockers += $r } }
    }
    if ($blockers.Count -eq 0) { [void]$ready.Add($fqn) }
}

Write-Output ("当前可还原（叶子）: {0} 个" -f $ready.Count)
$ready | Select-Object -First $Show | ForEach-Object { Write-Output "  $_" }
if ($ready.Count -gt $Show) { Write-Output ("  ...（还有 {0} 个）" -f ($ready.Count - $Show)) }
