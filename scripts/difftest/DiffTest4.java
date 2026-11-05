import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * 差分测试 v3：全类覆盖 + 大规模样本池。
 * 针对规则引擎（AnalyzeRule/AnalyzeUrl/RuleAnalyzer）等复杂组件，
 * 样本池覆盖：XPath、JSONPath、CSS 选择器、JS 表达式、正则、URL 各形态、HTML、JSON、XML。
 */
public class DiffTest4 {

    static PrintStream out;
    static URLClassLoader artLoader, locLoader;
    static int pass = 0, fail = 0, skip = 0;
    static List<String> failures = new ArrayList<>();

    static Map<String, Object[]> VALUES = new LinkedHashMap<>();
    static {
        VALUES.put("java.lang.String", new Object[]{
                null, "", "a", "0", "1", "-1", "true", "false", "null",
                // 规则字符串：XPath / JSONPath / CSS / 默认规则
                "//div[@class='x']", "//*[@id=\"a\"]/text()", "$.data.list[*]", "$..name",
                "@css:.item > a", "@json:$.list", "@xpath://a", "class.item@href",
                "tag.a@text", "id.con@html", "//div##p", "//div##p##-1",
                // JS
                "@js:1+1", "<js>1+1</js>", "@js:result", "<js>result</js>",
                "@js:java.ajax('http://a.com')", "@js:base64Decode('YQ==')",
                // 其他前缀
                "@put:{'k':'v'}", "@get:{k}", "{{$.a}}", "%%a%%", "@css:a@href",
                // 分隔符/占位
                "-", ",", "&&", "||", "##", "\n", "\t", "  x  ", "a,b,c", "a-b-c",
                // URL 形态
                "http://a.com", "https://x.y/z?q=1", "//host/path", "a.com",
                "http://a.com/path?x=1#f", "ftp://x", "file:///c:/a",
                // JSON / HTML / XML
                "{\"a\":1}", "[1,2]", "{\"data\":{\"list\":[{\"n\":\"x\"}]}}",
                "<div><p>x</p></div>", "<?xml version=\"1.0\"?><r><a>x</a></r>",
                "YQ==", "5L2g5aW9", "\u4e2d\u6587", "\uD83D\uDE00",
                // 正则
                "^a.*z$", "(?i)abc", "\\\\d+", "[a-z]+", "(a)(b)",
                // 数字/日期
                "2024-01-01", "2024-01-01 12:00:00", "12:00", "1.5", "1e10"
        });
        VALUES.put("int", new Object[]{0, 1, -1, 2, -2, 42, 100, Integer.MAX_VALUE, Integer.MIN_VALUE});
        VALUES.put("long", new Object[]{0L, 1L, -1L, 42L, 1499999999999L, Long.MAX_VALUE});
        VALUES.put("boolean", new Object[]{true, false});
        VALUES.put("char", new Object[]{'a', '[', ']', '{', '}', '$', '@', '-', '.', '/', ' '});
        VALUES.put("double", new Object[]{0.0, 1.5, -1.5, 1e10});
        VALUES.put("float", new Object[]{0.0f, 1.5f, -1.5f});
        VALUES.put("short", new Object[]{(short) 0, (short) 1, (short) -1});
        VALUES.put("byte", new Object[]{(byte) 0, (byte) 1, (byte) -1});
        VALUES.put("java.lang.Object", new Object[]{null, "a", 1, true});
    }

    public static void main(String[] args) throws Exception {
        String artDir = args[0], locDirs = args[1], libDir = args[2], outFile = args[3];
        String filter = args.length > 4 ? args[4] : "";
        int maxCombos = args.length > 5 ? Integer.parseInt(args[5]) : 120;
        int maxStrSamples = args.length > 6 ? Integer.parseInt(args[6]) : 999;

        out = new PrintStream(new FileOutputStream(outFile), true, "UTF-8");

        List<URL> deps = Files.list(Paths.get(libDir))
                .filter(p -> p.toString().endsWith(".jar"))
                .map(p -> { try { return p.toUri().toURL(); } catch (Exception e) { return null; } })
                .filter(Objects::nonNull).collect(Collectors.toList());
        List<URL> a = new ArrayList<>(deps); a.add(Paths.get(artDir).toUri().toURL());
        artLoader = new URLClassLoader(a.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());
        List<URL> l = new ArrayList<>(deps);
        for (String d : locDirs.split(",")) if (!d.isEmpty()) l.add(Paths.get(d).toUri().toURL());
        locLoader = new URLClassLoader(l.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());

        // 限制 String 样本数（用于控制组合爆炸）
        if (maxStrSamples < 999) {
            Object[] all = VALUES.get("java.lang.String");
            VALUES.put("java.lang.String", Arrays.copyOf(all, Math.min(maxStrSamples, all.length)));
        }

        List<String> prefixes = new ArrayList<>();
        for (String f : filter.split(",")) if (!f.trim().isEmpty()) prefixes.add(f.trim());

        List<String> classes = new ArrayList<>();
        Files.walk(Paths.get(artDir))
                .filter(p -> p.toString().endsWith(".class"))
                .forEach(p -> {
                    String rel = Paths.get(artDir).relativize(p).toString().replace(File.separatorChar, '.');
                    classes.add(rel.substring(0, rel.length() - 6));
                });
        Collections.sort(classes);
        List<String> selected = new ArrayList<>();
        for (String c : classes) {
            if (prefixes.isEmpty()) { selected.add(c); continue; }
            for (String p : prefixes) if (c.startsWith(p)) { selected.add(c); break; }
        }
        out.println("# 选中 " + selected.size() + " 个类，maxCombos=" + maxCombos
                + "，String 样本 " + VALUES.get("java.lang.String").length + " 个");
        out.flush();

        for (String cls : selected) {
            try { testClass(cls, maxCombos); } catch (Throwable t) { skip++; }
            out.flush();
        }

        out.println();
        out.printf("=== 通过 %d / 失败 %d / 跳过 %d%n", pass, fail, skip);
        out.println("=== 失败明细 ===");
        for (String f : failures) out.println(f);
        out.println("END");
        out.close();
    }

    static void testClass(String clsName, int maxCombos) {
        Class<?> ca, cb;
        try { ca = Class.forName(clsName, false, artLoader); } catch (Throwable t) { skip++; return; }
        try { cb = Class.forName(clsName, false, locLoader); }
        catch (Throwable t) { fail++; failures.add(clsName + " :: 本地加载失败"); return; }

        Object ia = null, ib = null;
        boolean canInst = false;
        try {
            Constructor<?> c1 = ca.getDeclaredConstructor(), c2 = cb.getDeclaredConstructor();
            c1.setAccessible(true); c2.setAccessible(true);
            ia = c1.newInstance(); ib = c2.newInstance(); canInst = true;
        } catch (Throwable ignored) {}

        for (Method ma : ca.getDeclaredMethods()) {
            int mod = ma.getModifiers();
            if (Modifier.isAbstract(mod)) continue;
            if (ma.isSynthetic() || ma.isBridge()) continue;
            if (ma.getName().contains("$")) continue;
            boolean isStatic = Modifier.isStatic(mod);
            if (!isStatic && !canInst) continue;

            Class<?>[] ptypes = ma.getParameterTypes();
            if (ptypes.length > 3) continue;
            boolean ok = true;
            for (Class<?> pt : ptypes) if (!VALUES.containsKey(pt.getName())) { ok = false; break; }
            if (!ok) continue;

            Method mb;
            try { mb = cb.getDeclaredMethod(ma.getName(), ptypes); }
            catch (NoSuchMethodException e) {
                fail++; failures.add(clsName + " :: 方法缺失 " + ma.getName() + "/" + ptypes.length);
                continue;
            }

            // 按参数个数分配样本数：1 参给足，3 参压缩
            int[] perParam = new int[ptypes.length];
            if (ptypes.length == 0) { perParam = new int[0]; }
            else if (ptypes.length == 1) { perParam[0] = maxCombos; }
            else if (ptypes.length == 2) { perParam[0] = Math.max(2, (int) Math.sqrt(maxCombos)); perParam[1] = Math.max(2, (int) Math.sqrt(maxCombos)); }
            else { int c = Math.max(2, (int) Math.cbrt(maxCombos)); perParam[0] = c; perParam[1] = c; perParam[2] = c; }

            List<Object[]> combos = new ArrayList<>();
            combos.add(new Object[0]);
            for (int pi = 0; pi < ptypes.length; pi++) {
                Object[] vals = VALUES.get(ptypes[pi].getName());
                int take = Math.min(perParam[pi], vals.length);
                List<Object[]> next = new ArrayList<>();
                for (Object[] pre : combos)
                    for (int vi = 0; vi < take; vi++) {
                        Object[] n = Arrays.copyOf(pre, pre.length + 1);
                        n[pre.length] = vals[vi];
                        next.add(n);
                        if (next.size() >= maxCombos) break;
                    }
                combos = next;
                if (combos.size() >= maxCombos) break;
            }

            for (Object[] args : combos) {
                String label = ma.getName() + "(" + Arrays.toString(args) + ")";
                try {
                    ma.setAccessible(true); mb.setAccessible(true);
                    Object ra = ma.invoke(isStatic ? null : ia, args);
                    Object rb = mb.invoke(isStatic ? null : ib, args);
                    String sa = ser(ra), sb = ser(rb);
                    if (Objects.equals(sa, sb)) pass++;
                    else { fail++; failures.add(clsName + " :: " + label + " 制品=" + trunc(sa) + " 本地=" + trunc(sb)); }
                } catch (InvocationTargetException e) {
                    try {
                        mb.invoke(isStatic ? null : ib, args);
                        fail++; failures.add(clsName + " :: " + label + " 制品抛 " + e.getCause().getClass().getSimpleName() + " 本地未抛");
                    } catch (InvocationTargetException e2) {
                        String na = e.getCause().getClass().getName(), nb = e2.getCause().getClass().getName();
                        String ma2 = String.valueOf(e.getCause().getMessage()), mb2 = String.valueOf(e2.getCause().getMessage());
                        int nl1 = ma2.indexOf("\n\tat "); if (nl1 > 0) ma2 = ma2.substring(0, nl1);
                        int nl2 = mb2.indexOf("\n\tat "); if (nl2 > 0) mb2 = mb2.substring(0, nl2);
                        if (na.equals(nb) && ma2.equals(mb2)) pass++;
                        else { fail++; failures.add(clsName + " :: " + label + " 异常 制品=" + na + ":" + trunc(ma2) + " 本地=" + nb + ":" + trunc(mb2)); }
                    } catch (Throwable t2) { skip++; }
                } catch (Throwable t) { skip++; }
            }
        }
    }

    static String ser(Object o) {
        if (o == null) return "null";
        if (o instanceof String) {
            String t = ((String) o).replaceAll("\\b1[0-9]{12}\\b", "<ts>");
            int nl = t.indexOf("\n\tat ");
            if (nl > 0) t = t.substring(0, nl) + "<stacktrace>";
            return "\"" + t + "\"";
        }
        if (o instanceof Number) {
            double d = ((Number) o).doubleValue();
            if (d > 1.0E12 && d < 1.0E13) return "<ts>";
            return o.toString();
        }
        if (o instanceof Boolean || o instanceof Character) return o.toString();
        if (o.getClass().isArray()) {
            int n = Array.getLength(o); StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < n && i < 30; i++) { if (i > 0) sb.append(","); sb.append(ser(Array.get(o, i))); }
            if (n > 30) sb.append(",...");
            return sb.append("]").toString();
        }
        if (o instanceof Collection) {
            StringBuilder sb = new StringBuilder("{"); int i = 0;
            for (Object e : (Collection<?>) o) { if (i++ > 0) sb.append(","); if (i > 30) { sb.append("..."); break; } sb.append(ser(e)); }
            return sb.append("}").toString();
        }
        if (o instanceof Map) {
            StringBuilder sb = new StringBuilder("{"); int i = 0;
            for (Map.Entry<?, ?> e : ((Map<?, ?>) o).entrySet()) { if (i++ > 0) sb.append(","); if (i > 30) { sb.append("..."); break; } sb.append(ser(e.getKey())).append("=").append(ser(e.getValue())); }
            return sb.append("}").toString();
        }
        try {
            String s = o.toString();
            if (s != null) {
                s = s.replaceAll("\\b1[0-9]{12}\\b", "<ts>");
                int nl = s.indexOf("\n\tat ");
                if (nl > 0) s = s.substring(0, nl) + "<stacktrace>";
                if (!s.contains("@") && s.length() < 500) return s;
            }
        } catch (Throwable ignored) {}
        return "<" + o.getClass().getName() + ">";
    }

    static String trunc(String s) { return s == null ? "null" : (s.length() > 160 ? s.substring(0, 157) + "..." : s); }
}
