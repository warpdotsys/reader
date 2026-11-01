<#
.SYNOPSIS
    类集合适配：把参考制品的类集合与本仓库已编译的类集合做差集。

.DESCRIPTION
    "完成到哪"必须由脚本给出，不能靠口报。本脚本产出：

      - 制品有多少个应用类，其中多少个已在本仓库编译出来
      - 还缺哪些（按包与档位归并）
      - 多出了哪些（本仓库有、制品没有——通常意味着多生成了东西，值得查）
      - 应用资源的适配情况

    档位来自 scripts/tier-rules.txt，判定规则见 docs/GOAL.md。

    这是报告而不是门禁：它不会因为缺类而失败。缺类是迁移过程的正常状态。

.PARAMETER Artifact
    参考制品：.jar，或已经解包出来的 BOOT-INF/classes 目录。

.PARAMETER ProjectRoot
    工程根目录，默认取脚本所在目录的上一级。

.PARAMETER RosterPath
    可选。给出路径时，把逐类索引写到该文件（制表符分隔：类名、档位、状态）。

.EXAMPLE
    ./scripts/class-coverage.ps1 -Artifact ../reader-pro-3.2.14.jar
#>
param(
    [Parameter(Mandatory = $true)][string]$Artifact,
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot),
    [string]$RosterPath = ''
)

$ErrorActionPreference = 'Stop'

# 编译产物分两处：Kotlin 和 Java 的目录不同。
$classDirs = @(
    (Join-Path $ProjectRoot 'build\classes\kotlin\main'),
    (Join-Path $ProjectRoot 'build\classes\java\main')
) | Where-Object { Test-Path -LiteralPath $_ }

if (-not $classDirs) {
    throw "还没编译过：$ProjectRoot\build\classes。先跑 ./gradlew classes。"
}

# 制品是 fat jar 时先解包，拿到 BOOT-INF/classes。
$staging = $null
if (Test-Path -LiteralPath $Artifact -PathType Container) {
    $referenceRoot = (Resolve-Path -LiteralPath $Artifact).Path
}
else {
    $staging = Join-Path ([IO.Path]::GetTempPath()) ("reader-ref-" + [Guid]::NewGuid().ToString('N'))
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
    $referenceRoot = $staging
}

# 档位规则：先匹配者胜，未匹配为 loose。
$rules = New-Object System.Collections.ArrayList
foreach ($line in (Get-Content -LiteralPath (Join-Path $PSScriptRoot 'tier-rules.txt'))) {
    $trimmed = $line.Trim()
    if ($trimmed -eq '' -or $trimmed.StartsWith('#')) { continue }
    $parts = $trimmed -split '\s+', 2
    if ($parts.Count -ne 2) { continue }
    [void]$rules.Add(@{ Tier = $parts[0]; Pattern = [regex]::new($parts[1]) })
}

function Get-Tier([string]$ClassName) {
    foreach ($rule in $rules) {
        if ($rule.Pattern.IsMatch($ClassName)) { return $rule.Tier }
    }
    return 'loose'
}

# 验证以类为单位（ABI、字节码都是类级），但工作量以源文件为单位。
# 一个顶层类会衍生出很多合成类（协程状态机、$default、lambda），所以两个口径都要看。
function Get-TopLevelName([string]$ClassName) {
    return ($ClassName -split '\$')[0]
}

function Get-TopLevelCount($Rows, [string]$Tier) {
    return @($Rows | Where-Object { $_.Tier -eq $Tier } |
        ForEach-Object { Get-TopLevelName $_.Class } | Sort-Object -Unique).Count
}

function Get-Classes([string]$Root) {
    Get-ChildItem -LiteralPath $Root -Recurse -File -Filter *.class | ForEach-Object {
        $_.FullName.Substring($Root.Length + 1) -replace '\\', '.' -replace '\.class$', ''
    }
}

try {
    $referenceClasses = @(Get-Classes $referenceRoot | Sort-Object -Unique)

    $restoredClasses = @(
        foreach ($dir in $classDirs) { Get-Classes $dir }
    ) | Sort-Object -Unique

    $referenceSet = [System.Collections.Generic.HashSet[string]]::new([string[]]$referenceClasses)
    $restoredSet = [System.Collections.Generic.HashSet[string]]::new([string[]]$restoredClasses)

    $missing = @($referenceClasses | Where-Object { -not $restoredSet.Contains($_) })
    $extra = @($restoredClasses | Where-Object { -not $referenceSet.Contains($_) })

    # 按档位归并缺失类。
    $byTier = @{ strict = 0; loose = 0; excluded = 0 }
    $restoredByTier = @{ strict = 0; loose = 0; excluded = 0 }
    $missingRows = foreach ($c in $missing) {
        $tier = Get-Tier $c
        $byTier[$tier]++
        [PSCustomObject]@{ Class = $c; Tier = $tier; Package = ($c -replace '\.[^.]+$', '') }
    }
    foreach ($c in $restoredClasses) { $restoredByTier[(Get-Tier $c)]++ }

    Write-Output "制品应用类   : $($referenceClasses.Count)"
    Write-Output "本仓库已编译 : $($restoredClasses.Count)"
    Write-Output "已适配       : $($referenceClasses.Count - $missing.Count)"
    Write-Output "还缺         : $($missing.Count)"
    Write-Output "多出         : $($extra.Count)"
    Write-Output ''
    Write-Output '还缺的类按档位（类 / 顶层类）：'
    Write-Output ("  strict   : {0} 类 / {1} 顶层类" -f $byTier.strict, (Get-TopLevelCount $missingRows 'strict'))
    Write-Output ("  loose    : {0} 类 / {1} 顶层类" -f $byTier.loose, (Get-TopLevelCount $missingRows 'loose'))
    Write-Output ("  excluded : {0} 类 / {1} 顶层类" -f $byTier.excluded, (Get-TopLevelCount $missingRows 'excluded'))
    Write-Output ''
    Write-Output '已完成的类按档位（类 / 顶层类）：'
    Write-Output ("  strict   : {0}" -f $restoredByTier.strict)
    Write-Output ("  loose    : {0}" -f $restoredByTier.loose)
    Write-Output ("  excluded : {0}" -f $restoredByTier.excluded)

    if ($missingRows.Count -gt 0) {
        Write-Output ''
        Write-Output '还缺的类按包（前 20）：'
        $missingRows | Group-Object Package | Sort-Object Count -Descending |
            Select-Object -First 20 | ForEach-Object {
                Write-Output ("  {0,4}  {1}" -f $_.Count, $_.Name)
            }
    }

    if ($extra.Count -gt 0) {
        Write-Output ''
        Write-Output "多出的类（本仓库有、制品没有，共 $($extra.Count)）："
        $extra | Select-Object -First 25 | ForEach-Object { Write-Output "  + $_" }
        if ($extra.Count -gt 25) { Write-Output "  ...（还有 $($extra.Count - 25) 个）" }
    }

    if ($RosterPath -ne '') {
        $lines = New-Object System.Collections.ArrayList
        [void]$lines.Add("# 由 scripts/class-coverage.ps1 生成，不要手工改。档位规则见 scripts/tier-rules.txt。")
        [void]$lines.Add("# 类名`t档位`t状态")
        foreach ($c in $referenceClasses) {
            $state = if ($restoredSet.Contains($c)) { '已还原' } else { '待还原' }
            [void]$lines.Add("$c`t$(Get-Tier $c)`t$state")
        }
        $resolved = if ([IO.Path]::IsPathRooted($RosterPath)) { $RosterPath } else { Join-Path $ProjectRoot $RosterPath }
        New-Item -ItemType Directory -Path (Split-Path $resolved) -Force | Out-Null
        [IO.File]::WriteAllLines($resolved, $lines, (New-Object System.Text.UTF8Encoding($false)))
        Write-Output ''
        Write-Output "索引已写入：$resolved"
    }
}
finally {
    if ($staging -and (Test-Path -LiteralPath $staging)) { Remove-Item -LiteralPath $staging -Recurse -Force }
}
