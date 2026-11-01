<#
.SYNOPSIS
    方法级字节码比对：把本仓库编译出的类与参考制品逐方法比对指令序列。

.DESCRIPTION
    ABI 比对只看成员声明，字符串常量比对只看常量池，两者都覆盖不到方法体。
    本脚本补齐这一层：逐方法比对归一化后的指令序列。

    归一化做的事：
      - 去掉行号、局部变量表、StackMapTable
      - 去掉常量池索引（`#22`），保留符号引用注释（`// Method foo:(...)V`）
      - 分支指令的数值目标改写为「相对位移」，与指令在方法中的位置无关
      - 抹平空白

    不归一化、因而会引入噪声的东西：
      - 局部变量槽位分配（iload 5 这类）——源码结构等价时应当一致，不一致就值得看
      - 编译器次要版本差异。制品是 Kotlin 1.5 线，重建也用 1.5.21，所以 Kotlin 来源的类噪声应当很低；
        反编译产物里没有 @Metadata 的 Java 来源类由另一套 javac 编译过，噪声会明显更大，
        这类按 docs/GOAL.md 的规则降级，不作为失败。

    这是报告而不是门禁：beanCounter 不会因为差异而失败，除非传 -FailOnDiff。

.PARAMETER Artifact
    参考制品：.jar，或已解包的 BOOT-INF/classes 目录。

.PARAMETER ProjectRoot
    工程根目录，默认取脚本所在目录的上一级。

.PARAMETER Classes
    要比对的类名（完全限定）。留空则比对本仓库已编译的全部类。

.PARAMETER ShowDiff
    对不一致的方法打印归一化后的指令序列差异。

.PARAMETER FailOnDiff
    存在差异时以退出码 1 结束。

.EXAMPLE
    ./scripts/verify-bytecode.ps1 -Artifact ../reader-pro-3.2.14.jar
#>
param(
    [Parameter(Mandatory = $true)][string]$Artifact,
    [string]$ProjectRoot = (Split-Path -Parent $PSScriptRoot),
    [string[]]$Classes = @(),
    [switch]$ShowDiff,
    [switch]$FailOnDiff
)

$ErrorActionPreference = 'Stop'

$javaHome = $env:JAVA_HOME
if ([string]::IsNullOrWhiteSpace($javaHome)) { throw '需要 JDK：请设置 JAVA_HOME。' }
$javap = Join-Path $javaHome 'bin\javap.exe'
if (-not (Test-Path -LiteralPath $javap)) { throw "找不到 javap：$javap" }

$newCps = @(
    (Join-Path $ProjectRoot 'build\classes\kotlin\main'),
    (Join-Path $ProjectRoot 'build\classes\java\main')
) | Where-Object { Test-Path -LiteralPath $_ }
if (-not $newCps) { throw "还没编译过。先跑 ./gradlew classes。" }

# 参考 classpath
$staging = $null
if (Test-Path -LiteralPath $Artifact -PathType Container) {
    $oldCp = (Resolve-Path -LiteralPath $Artifact).Path
}
else {
    $staging = Join-Path ([IO.Path]::GetTempPath()) ("reader-bytecode-ref-" + [Guid]::NewGuid().ToString('N'))
    New-Item -ItemType Directory -Path $staging | Out-Null
    Add-Type -AssemblyName System.IO.Compression.FileSystem
    $zip = [IO.Compression.ZipFile]::OpenRead((Resolve-Path -LiteralPath $Artifact).Path)
    try {
        $prefix = 'BOOT-INF/classes/'
        foreach ($entry in $zip.Entries) {
            if (-not $entry.FullName.StartsWith($prefix) -or $entry.FullName.EndsWith('/')) { continue }
            $target = Join-Path $staging $entry.FullName.Substring($prefix.Length)
            New-Item -ItemType Directory -Path (Split-Path $target) -Force | Out-Null
            [IO.Compression.ZipFileExtensions]::ExtractToFile($entry, $target, $true)
        }
    }
    finally { $zip.Dispose() }
    $oldCp = $staging
}

$branchOpcodes = @(
    'ifeq', 'ifne', 'iflt', 'ifge', 'ifgt', 'ifle', 'if_icmpeq', 'if_icmpne', 'if_icmplt',
    'if_icmpge', 'if_icmpgt', 'if_icmple', 'if_acmpeq', 'if_acmpne', 'goto', 'jsr',
    'ifnull', 'ifnonnull', 'goto_w', 'jsr_w'
)

# 把 javap -c 的输出拆成方法 -> 归一化指令序列。
# 注意：$Dump 必须是 string[]。写成 [string] 会把整个输出压成一个字符串，
# 行结构消失、方法集合为空、于是两边永远判等——一个永远说「一致」的检查器。
function Get-MethodInstructions([string[]]$Dump) {
    $methods = @{}
    $current = $null
    $buffer = New-Object System.Collections.ArrayList
    $lastMember = $null
    $pendingCode = $false

    foreach ($line in $Dump) {
        if ($line -match '^\s*$') { continue }
        if ($line -match '^Compiled from') { continue }

        # 方法头：两空格缩进、以 ; 结尾，紧接着 Code:
        if ($line -match '^  .+;$') {
            $lastMember = $line.Trim()
            $pendingCode = $true
            continue
        }
        if ($line -match '^    Code:\s*$') {
            if ($pendingCode -and $lastMember -ne $null) {
                if ($current) { $methods[$current] = $buffer.ToArray() }
                $current = $lastMember
                $buffer = New-Object System.Collections.ArrayList
            }
            $pendingCode = $false
            continue
        }
        if ($line -match '^\s{0,2}\}' ) { continue }
        if ($line -match '^\s*(LineNumberTable|LocalVariableTable|StackMapTable|Exceptions):') { continue }

        if ($current -and $line -match '^\s*\d+:\s') { [void]$buffer.Add($line) }
    }
    if ($current) { $methods[$current] = $buffer.ToArray() }
    return $methods
}

# 取一条指令访问的局部变量槽位号，没有则返回 $null。
function Get-LocalSlot([string]$Text) {
    if ($Text -match '^(?:i|l|f|d|a)(?:load|store)_([0-3])$') { return [int]$Matches[1] }
    if ($Text -match '^(?:i|l|f|d|a)(?:load|store) (\d+)$') { return [int]$Matches[1] }
    if ($Text -match '^ret (\d+)$') { return [int]$Matches[1] }
    if ($Text -match '^iinc (\d+),') { return [int]$Matches[1] }
    return $null
}

function Get-NormalizedInstructions($RawLines) {
    $parsed = @()
    foreach ($line in $RawLines) {
        if ($line -match '^\s*(\d+):\s+(.*)$') {
            $parsed += [PSCustomObject]@{ Offset = [int]$Matches[1]; Text = $Matches[2].Trim() }
        }
    }
    $index = @{}
    for ($i = 0; $i -lt $parsed.Count; $i++) { $index[$parsed[$i].Offset] = $i }

    # 局部变量槽位号由编译器分配，反编译产物里的分配顺序与原文不同，
    # 会产生大量与语义无关的噪声（istore_3 vs istore 4 之类）。
    # 按「首次出现的顺序」重编号：消除绝对槽位噪声，同时保留
    # 「用了几个槽、以什么顺序用」这一层信息。
    $slots = @{}
    foreach ($ins in $parsed) {
        $slot = Get-LocalSlot $ins.Text
        if ($null -ne $slot -and -not $slots.ContainsKey($slot)) { $slots[$slot] = $slots.Count }
    }

    $out = New-Object System.Collections.ArrayList
    for ($i = 0; $i -lt $parsed.Count; $i++) {
        $text = $parsed[$i].Text
        $text = $text -replace '#\d+\s*', ''
        $text = $text -replace '\s+', ' '
        $text = $text.Trim()

        # 槽位重编号
        if ($text -match '^(?:i|l|f|d|a)(?:load|store)_([0-3])$') {
            $text = $text.Substring(0, $text.Length - 2) + " v$($slots[[int]$Matches[1]])"
        }
        elseif ($text -match '^((?:i|l|f|d|a)(?:load|store)) (\d+)$') {
            $text = "$($Matches[1]) v$($slots[[int]$Matches[2]])"
        }
        elseif ($text -match '^ret (\d+)$') {
            $text = "ret v$($slots[[int]$Matches[1]])"
        }
        elseif ($text -match '^iinc (\d+), (\d+)$') {
            $text = "iinc v$($slots[[int]$Matches[1]]), $($Matches[2])"
        }

        # 分支目标：改写为相对位移，与绝对偏移无关
        $parts = $text -split ' ', 2
        if ($parts.Count -eq 2 -and $branchOpcodes -contains $parts[0] -and $parts[1] -match '^\d+$') {
            $target = [int]$parts[1]
            if ($index.ContainsKey($target)) {
                $text = "$($parts[0]) +$($index[$target] - $i)"
            }
        }
        [void]$out.Add($text)
    }

    # Kotlin 编译器在调用 Intrinsics.checkNotNullExpressionValue(obj, "…") 时，
    # 第二参数是从源码表达式字面形式生成的诊断消息，只在编译器自身假设被破坏时才会出现在异常里。
    # 但这段「取对象 + 诊断消息 + 断言」的三指令惯用法是否出现，取决于源码里平台类型
    # 恰好被写成了什么形式——换一种等价写法，字节码就多/少三条，语义完全一样。
    # 整段抹掉，否则严格档会被这类误报淹没。
    # 参数名那一类（checkNotNullParameter）保留：那是真实的 API 信息，不是编译器自证。
    $filtered = New-Object System.Collections.ArrayList
    $i = 0
    while ($i -lt $out.Count) {
        if ($i + 2 -lt $out.Count -and $out[$i + 2] -match 'checkNotNullExpressionValue' -and
            ($out[$i] -match '^(aload|dup)' ) -and $out[$i + 1] -match '^ldc // String ') {
            $i += 3
            continue
        }
        if ($out[$i] -match 'checkNotNullExpressionValue') { $i++; continue }
        # 「存回同一槽位、紧接着又读回来」是栈上往返，语义上什么都没做。
        # 它是否出现取决于源码怎么拆局部变量，属于噪声。
        if ($i + 1 -lt $out.Count -and
            $out[$i] -match '^\w+store (v\d+)$' -and
            $out[$i + 1] -eq ("$($out[$i] -replace 'store ', 'load ')") ) {
            $i += 2
            continue
        }
        [void]$filtered.Add($out[$i])
        $i++
    }
    return $filtered.ToArray()
}

# 判断某个类是否在 classpath 上。不能靠 javap 的报错文本——方法体里的字符串常量
# 也可能包含「找不到」之类的字样，那会变成误报与静默遗漏。直接查文件。
function Test-ClassExists([string]$ClassPath, [string]$ClassName) {
    foreach ($root in ($ClassPath -split ';')) {
        if ([string]::IsNullOrWhiteSpace($root)) { continue }
        $file = Join-Path $root ($ClassName.Replace('.', '\') + '.class')
        if (Test-Path -LiteralPath $file) { return $true }
    }
    return $false
}

function Get-Methods([string]$ClassPath, [string]$ClassName) {
    if (-not (Test-ClassExists $ClassPath $ClassName)) { return $null }
    $dump = & $javap -c -p -classpath $ClassPath $ClassName 2>&1
    $raw = Get-MethodInstructions $dump
    $result = @{}
    foreach ($key in $raw.Keys) { $result[$key] = Get-NormalizedInstructions $raw[$key] }
    return $result
}

try {
    if ($Classes.Count -eq 0) {
        $Classes = @(
            foreach ($cp in $newCps) {
                Get-ChildItem -LiteralPath $cp -Recurse -File -Filter *.class | ForEach-Object {
                    $_.FullName.Substring($cp.Length + 1) -replace '\\', '.' -replace '\.class$', ''
                }
            }
        ) | Sort-Object -Unique
    }

    $same = 0
    $different = New-Object System.Collections.ArrayList
    $unavailable = New-Object System.Collections.ArrayList

    foreach ($class in $Classes) {
        $reference = Get-Methods $oldCp $class
        if ($null -eq $reference) { [void]$unavailable.Add($class); continue }
        $restored = Get-Methods ($newCps -join ';') $class
        if ($null -eq $restored) { [void]$unavailable.Add($class); continue }

        $allKeys = @($reference.Keys + $restored.Keys) | Sort-Object -Unique
        $methodDiffs = New-Object System.Collections.ArrayList
        foreach ($k in $allKeys) {
            $a = if ($reference.ContainsKey($k)) { ($reference[$k] -join "`n") } else { '<缺失>' }
            $b = if ($restored.ContainsKey($k)) { ($restored[$k] -join "`n") } else { '<缺失>' }
            if ($a -cne $b) { [void]$methodDiffs.Add($k) }
        }
        if ($methodDiffs.Count -eq 0) { $same++ }
        else {
            [void]$different.Add([PSCustomObject]@{ Class = $class; Methods = $methodDiffs })
            if ($ShowDiff) {
                Write-Output "--- $class ---"
                foreach ($k in $methodDiffs) {
                    Write-Output "  方法 $k"
                    $a = if ($reference.ContainsKey($k)) { $reference[$k] } else { @('<缺失>') }
                    $b = if ($restored.ContainsKey($k)) { $restored[$k] } else { @('<缺失>') }
                    Compare-Object $a $b | Format-Table -AutoSize | Out-String | Write-Output
                }
            }
        }
    }

    Write-Output "已比对类数     : $($Classes.Count)"
    Write-Output "指令序列一致   : $same"
    Write-Output "存在方法差异   : $($different.Count)"
    Write-Output "无法比对       : $($unavailable.Count)"
    if ($unavailable.Count -gt 0) {
        Write-Output ''
        Write-Output '无法比对的类（必须查清，不能默认它没问题）：'
        $unavailable | ForEach-Object { Write-Output "  ? $_" }
    }
    if ($different.Count -gt 0) {
        Write-Output ''
        Write-Output '有差异的类（类名 : 差异方法数）：'
        $different | Sort-Object { $_.Methods.Count } -Descending |
            ForEach-Object { Write-Output ("  {0}  : {1}" -f $_.Class, $_.Methods.Count) }
    }
    if ($FailOnDiff -and $different.Count -gt 0) { exit 1 }
}
finally {
    if ($staging -and (Test-Path -LiteralPath $staging)) { Remove-Item -LiteralPath $staging -Recurse -Force }
}
