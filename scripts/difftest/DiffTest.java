import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * 差分测试：用两个 ClassLoader 分别加载「制品」和「本地构建」的同名类，
 * 对同样输入调用方法，对比序列化后的结果。
 *
 * 只测「可自动构造 + 无副作用」的部分：
 *   L1 静态常量字段
 *   L2 无参方法（实例用无参构造或无参静态工厂）
 *   L3 data class 的 toString / hashCode
 */
public class DiffTest {

    static PrintStream out;
    static URLClassLoader artLoader, locLoader;
    static int pass = 0, fail = 0, skip = 0;
    static List<String> failures = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String artDir = args[0];
        String locDirs = args[1];   // 逗号分隔
        String libDir = args[2];
        String outFile = args[3];

        out = new PrintStream(new FileOutputStream(outFile), true, "UTF-8");

        List<URL> deps = Files.list(Paths.get(libDir))
                .filter(p -> p.toString().endsWith(".jar"))
                .map(p -> { try { return p.toUri().toURL(); } catch (Exception e) { return null; } })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<URL> artUrls = new ArrayList<>(deps);
        artUrls.add(Paths.get(artDir).toUri().toURL());
        artLoader = new URLClassLoader(artUrls.toArray(new URL[0]),
                ClassLoader.getPlatformClassLoader());

        List<URL> locUrls = new ArrayList<>(deps);
        for (String d : locDirs.split(",")) {
            if (!d.isEmpty()) locUrls.add(Paths.get(d).toUri().toURL());
        }
        locLoader = new URLClassLoader(locUrls.toArray(new URL[0]),
                ClassLoader.getPlatformClassLoader());

        // 收集制品里所有类名
        List<String> classes = new ArrayList<>();
        Files.walk(Paths.get(artDir))
                .filter(p -> p.toString().endsWith(".class"))
                .forEach(p -> {
                    String rel = Paths.get(artDir).relativize(p).toString()
                            .replace(File.separatorChar, '.');
                    classes.add(rel.substring(0, rel.length() - 6));
                });
        Collections.sort(classes);
        // 包名前缀过滤（逗号分隔），空则全测
        String filter = args.length > 4 ? args[4] : "";
        List<String> prefixes = new ArrayList<>();
        for (String f : filter.split(",")) if (!f.trim().isEmpty()) prefixes.add(f.trim());

        List<String> selected = new ArrayList<>();
        for (String c : classes) {
            if (prefixes.isEmpty()) { selected.add(c); continue; }
            for (String p : prefixes) if (c.startsWith(p)) { selected.add(c); break; }
        }
        out.println("# 共 " + classes.size() + " 个类，选中 " + selected.size());

        for (String cls : selected) {
            try {
                testClass(cls);
            } catch (Throwable t) {
                skip++;
            }
            out.flush();
        }

        out.println();
        out.println("=== 汇总 ===");
        out.printf("通过 %d / 失败 %d / 跳过 %d%n", pass, fail, skip);
        out.println();
        out.println("=== 失败明细 ===");
        for (String f : failures) out.println(f);
        out.println("END");
        out.close();
    }

    static void testClass(String clsName) {
        Class<?> a, b;
        try {
            a = Class.forName(clsName, false, artLoader);
        } catch (Throwable t) { skip++; return; }
        try {
            b = Class.forName(clsName, false, locLoader);
        } catch (Throwable t) {
            fail++; failures.add(clsName + " :: 本地加载失败 " + t); return;
        }

        // --- L1: 静态字段 ---
        for (Field fa : a.getDeclaredFields()) {
            int mod = fa.getModifiers();
            if (!Modifier.isStatic(mod)) continue;
            if (fa.isSynthetic()) continue;
            Field fb;
            try { fb = b.getDeclaredField(fa.getName()); }
            catch (NoSuchFieldException e) {
                fail++; failures.add(clsName + " :: 字段缺失 " + fa.getName()); continue;
            }
            if (!fa.getType().getName().equals(fb.getType().getName())) {
                fail++; failures.add(clsName + " :: 字段类型 " + fa.getName()
                        + " 制品=" + fa.getType().getName() + " 本地=" + fb.getType().getName());
                continue;
            }
            try {
                fa.setAccessible(true); fb.setAccessible(true);
                if (!Modifier.isFinal(mod)) continue;   // 只比 final（常量）
                Object va = fa.get(null), vb = fb.get(null);
                String sa = ser(va), sb = ser(vb);
                if (Objects.equals(sa, sb)) pass++;
                else { fail++; failures.add(clsName + " :: 常量 " + fa.getName()
                        + " 制品=" + sa + " 本地=" + sb); }
            } catch (Throwable t) { skip++; }
        }

        // --- L2/L3: 无参方法 ---
        Object ia = null, ib = null;
        boolean canInstantiate = false;
        try {
            Constructor<?> ca = a.getDeclaredConstructor();
            Constructor<?> cb = b.getDeclaredConstructor();
            ca.setAccessible(true); cb.setAccessible(true);
            ia = ca.newInstance(); ib = cb.newInstance();
            canInstantiate = true;
        } catch (Throwable t) {
            // 没有无参构造：只测静态无参方法
        }

        for (Method ma : a.getDeclaredMethods()) {
            int mod = ma.getModifiers();
            if (!Modifier.isPublic(mod) && !Modifier.isProtected(mod)) continue;
            if (ma.isSynthetic() || ma.isBridge()) continue;
            if (ma.getParameterCount() != 0) continue;
            if (ma.getReturnType() == void.class) continue;

            Method mb;
            try {
                mb = b.getDeclaredMethod(ma.getName());
            } catch (NoSuchMethodException e) {
                fail++; failures.add(clsName + " :: 方法缺失 " + ma.getName() + "()"); continue;
            }
            boolean isStatic = Modifier.isStatic(mod);
            if (isStatic && !Modifier.isStatic(mb.getModifiers())) {
                fail++; failures.add(clsName + " :: static 不一致 " + ma.getName()); continue;
            }
            if (!isStatic && !canInstantiate) continue;

            try {
                ma.setAccessible(true); mb.setAccessible(true);
                Object ra = ma.invoke(isStatic ? null : ia);
                Object rb = mb.invoke(isStatic ? null : ib);
                String sa = ser(ra), sb = ser(rb);
                if (Objects.equals(sa, sb)) pass++;
                else {
                    fail++;
                    failures.add(clsName + " :: " + ma.getName() + "() 制品="
                            + trunc(sa) + " 本地=" + trunc(sb));
                }
            } catch (InvocationTargetException e) {
                // 两边都抛异常 → 比异常类型
                try {
                    mb.invoke(isStatic ? null : ib);
                    fail++; failures.add(clsName + " :: " + ma.getName()
                            + "() 制品抛 " + e.getCause().getClass().getName() + " 本地未抛");
                } catch (InvocationTargetException e2) {
                    String na = e.getCause().getClass().getName();
                    String nb = e2.getCause().getClass().getName();
                    if (na.equals(nb)) pass++;
                    else { fail++; failures.add(clsName + " :: " + ma.getName()
                            + "() 异常类型 制品=" + na + " 本地=" + nb); }
                } catch (Throwable t2) { skip++; }
            } catch (Throwable t) { skip++; }
        }
    }

    /** 把反射拿到的对象转成可比较的字符串（处理跨 ClassLoader 的类型不等） */
    static String ser(Object o) {
        if (o == null) return "null";
        if (o instanceof String) return "\"" + o + "\"";
        if (o instanceof Number || o instanceof Boolean || o instanceof Character) return o.toString();
        if (o.getClass().isArray()) {
            int n = Array.getLength(o);
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < n && i < 20; i++) {
                if (i > 0) sb.append(",");
                sb.append(ser(Array.get(o, i)));
            }
            if (n > 20) sb.append(",...");
            return sb.append("]").toString();
        }
        if (o instanceof Collection) {
            StringBuilder sb = new StringBuilder("{");
            int i = 0;
            for (Object e : (Collection<?>) o) {
                if (i++ > 0) sb.append(",");
                if (i > 20) { sb.append("..."); break; }
                sb.append(ser(e));
            }
            return sb.append("}").toString();
        }
        if (o instanceof Map) {
            StringBuilder sb = new StringBuilder("{");
            int i = 0;
            for (Map.Entry<?, ?> e : ((Map<?, ?>) o).entrySet()) {
                if (i++ > 0) sb.append(",");
                if (i > 20) { sb.append("..."); break; }
                sb.append(ser(e.getKey())).append("=").append(ser(e.getValue()));
            }
            return sb.append("}").toString();
        }
        // 其他类型：如果 toString 看起来是自动生成的、无地址信息，就用它
        try {
            String s = o.toString();
            if (s != null && !s.contains("@") && s.length() < 500) return s;
        } catch (Throwable ignored) {}
        return "<" + o.getClass().getName() + ">";
    }

    static String trunc(String s) {
        if (s == null) return "null";
        return s.length() > 120 ? s.substring(0, 117) + "..." : s;
    }
}
