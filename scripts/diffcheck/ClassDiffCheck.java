import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类级行为差分：把参考制品里的类与本仓库编译出的同名类装进两个互相隔离的类加载器，
 * 喂同一批输入，比较输出。
 *
 * 这是 ABI 与字节码比对之外唯一能证明「行为一致」的手段：
 * 制品本身是可运行的，它比任何静态代理都更接近事实。
 *
 * 用法：
 *   java -cp <deps> ClassDiffCheck <参考classes目录> <本仓库classes目录> <依赖jar目录>
 *
 * 只覆盖纯函数（输入输出都是 JDK 类型），不覆盖需要外部状态的方法。
 */
public final class ClassDiffCheck {

    private static URLClassLoader newLoader(String classesDirs, File[] jars) throws Exception {
        List<URL> urls = new ArrayList<>();
        // classesDirs 允许用 ';' 分隔多个目录（Kotlin 与 Java 产物分处不同目录）
        for (String dir : classesDirs.split(";")) {
            if (!dir.isEmpty()) {
                urls.add(new File(dir).toURI().toURL());
            }
        }
        for (File jar : jars) {
            urls.add(jar.toURI().toURL());
        }
        return new URLClassLoader(urls.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());
    }

    /** 取 Kotlin object 的 INSTANCE，或返回 null（普通类）。 */
    private static Object instanceOf(Class<?> type) throws Exception {
        try {
            Field f = type.getField("INSTANCE");
            if (Modifier.isStatic(f.getModifiers())) {
                return f.get(null);
            }
        } catch (NoSuchFieldException ignored) {
            // 不是 object
        }
        return null;
    }

    /**
     * 按名称与参数个数找方法，并在重载之间按实参类型挑匹配的那个
     * （例如 removeUTF8BOM 同时有 String 与 byte[] 两个重载）。
     * 只看元素个数会挑错重载，然后反射调用抛 IllegalArgument。
     */
    private static Method findMethod(Class<?> type, String name, Object[] args) {
        Method fallback = null;
        for (Method m : type.getMethods()) {
            if (!m.getName().equals(name) || m.getParameterCount() != args.length) {
                continue;
            }
            if (fallback == null) {
                fallback = m;
            }
            if (assignable(m.getParameterTypes(), args)) {
                return m;
            }
        }
        if (fallback == null) {
            throw new IllegalStateException("找不到方法 " + name + "/" + args.length + " on " + type.getName());
        }
        return fallback;
    }

    private static boolean assignable(Class<?>[] params, Object[] args) {
        for (int i = 0; i < params.length; i++) {
            if (args[i] == null) {
                if (params[i].isPrimitive()) {
                    return false;
                }
                continue;
            }
            if (!wrap(params[i]).isInstance(args[i])) {
                return false;
            }
        }
        return true;
    }

    private static Class<?> wrap(Class<?> c) {
        if (!c.isPrimitive()) {
            return c;
        }
        if (c == int.class) return Integer.class;
        if (c == long.class) return Long.class;
        if (c == boolean.class) return Boolean.class;
        if (c == byte.class) return Byte.class;
        if (c == char.class) return Character.class;
        if (c == short.class) return Short.class;
        if (c == float.class) return Float.class;
        if (c == double.class) return Double.class;
        return c;
    }

    private static Object invoke(ClassLoader loader, String className, String methodName, Object... args)
            throws Exception {
        Class<?> type = Class.forName(className, true, loader);
        Object target = instanceOf(type);
        Method m = findMethod(type, methodName, args);
        return m.invoke(target, args);
    }

    private static String render(Object value) {
        if (value == null) {
            return "null";
        }
        String text;
        if (value instanceof byte[]) {
            text = hex((byte[]) value);
        } else if (value instanceof Object[]) {
            text = Arrays.deepToString((Object[]) value);
        } else {
            text = String.valueOf(value);
        }
        if (text.length() > 400) {
            text = text.substring(0, 400) + "...(truncated)";
        }
        return value.getClass().getName() + ":" + text;
    }

    private static String hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private static int passed = 0;
    private static int failed = 0;

    /**
     * 可变输入（如 InputStream）必须每次调用都造一个新的：
     * 复用同一个对象时，先跑的那一侧会把它读空，后跑的必然失败——
     * 那是 harness 的错，不是被测代码的错。
     */
    private static void checkStream(URLClassLoader refLoader, URLClassLoader ourLoader,
                                    String className, String methodName, byte[] data) {
        String label = className + "." + methodName + "(stream " + data.length + " bytes)";
        Object expected;
        Object actual;
        try {
            expected = invoke(refLoader, className, methodName,
                    new java.io.ByteArrayInputStream(data));
        } catch (Throwable t) {
            expected = "EXCEPTION:" + t.getCause();
        }
        try {
            actual = invoke(ourLoader, className, methodName,
                    new java.io.ByteArrayInputStream(data));
        } catch (Throwable t) {
            actual = "EXCEPTION:" + t.getCause();
        }
        String a = render(expected);
        String b = render(actual);
        if (a.equals(b)) {
            passed++;
            System.out.println("PASS " + label + " -> " + a);
        } else {
            failed++;
            System.out.println("FAIL " + label);
            System.out.println("     制品 : " + a);
            System.out.println("     本仓库: " + b);
        }
    }

    private static void check(URLClassLoader refLoader, URLClassLoader ourLoader,
                              String className, String methodName, Object... args) {
        String label = className + "." + methodName + "(" + describe(args) + ")";
        Object expected;
        Object actual;
        try {
            expected = invoke(refLoader, className, methodName, args);
        } catch (Throwable t) {
            expected = "EXCEPTION:" + t.getCause();
        }
        try {
            actual = invoke(ourLoader, className, methodName, args);
        } catch (Throwable t) {
            actual = "EXCEPTION:" + t.getCause();
        }
        String a = render(expected);
        String b = render(actual);
        if (a.equals(b)) {
            passed++;
            System.out.println("PASS " + label + " -> " + a);
        } else {
            failed++;
            System.out.println("FAIL " + label);
            System.out.println("     制品 : " + a);
            System.out.println("     本仓库: " + b);
        }
    }

    private static String describe(Object[] args) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            Object arg = args[i];
            if (arg == null) {
                sb.append("null");
            } else {
                String text = String.valueOf(arg);
                if (text.length() > 40) {
                    text = text.substring(0, 40) + "...";
                }
                sb.append(text);
            }
        }
        return sb.toString();
    }

    public static void main(String[] argv) throws Exception {
        if (argv.length < 3) {
            System.err.println("用法: ClassDiffCheck <参考classes> <本仓库classes> <依赖jar目录>");
            System.exit(2);
        }
        File libDir = new File(argv[2]);
        File[] jars = libDir.listFiles((d, n) -> n.endsWith(".jar"));
        if (jars == null) {
            jars = new File[0];
        }
        URLClassLoader refLoader = newLoader(argv[0], jars);
        URLClassLoader ourLoader = newLoader(argv[1], jars);

        String md5 = "io.legado.app.utils.MD5Utils";
        for (String s : new String[]{null, "", "abc", "中文测试", "The quick brown fox", repeat("a", 1000)}) {
            check(refLoader, ourLoader, md5, "md5Encode", s);
        }
        for (String s : new String[]{"", "abc", "中文测试"}) {
            check(refLoader, ourLoader, md5, "md5Encode16", s);
        }

        String text = "io.legado.app.utils.TextUtils";
        for (String s : new String[]{null, "", "x", " "}) {
            check(refLoader, ourLoader, text, "isEmpty", (Object) s);
        }
        check(refLoader, ourLoader, text, "join", ",", (Object) new Object[]{"a", "b", "c"});
        check(refLoader, ourLoader, text, "join", ",", (Object) new Object[]{});
        check(refLoader, ourLoader, text, "join", "-", (Object) Arrays.asList("x", "y"));

        String xml = "io.legado.app.utils.XmlUtils";
        String xmlText = "<root><a>1</a><b><c>2</c></b></root>";
        checkStream(refLoader, ourLoader, xml, "xml2map", xmlText.getBytes("UTF-8"));
        String xmlText2 = "<root><item id=\"1\">x</item><item id=\"2\">y</item></root>";
        checkStream(refLoader, ourLoader, xml, "xml2map", xmlText2.getBytes("UTF-8"));

        // 带 BOM 与不带 BOM 的字节序列
        byte[] withBom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF, 'h', 'i'};
        byte[] withoutBom = new byte[]{'h', 'i', 'j', 'k'};
        for (String cls : new String[]{"io.legado.app.utils.Utf8BomUtils", "io.legado.app.utils.UTF8BOMFighter"}) {
            check(refLoader, ourLoader, cls, "removeUTF8BOM", withBom.clone());
            check(refLoader, ourLoader, cls, "removeUTF8BOM", withoutBom.clone());
            check(refLoader, ourLoader, cls, "removeUTF8BOM", (Object) "\uFEFFhi");
            check(refLoader, ourLoader, cls, "removeUTF8BOM", (Object) "hi");
        }
        check(refLoader, ourLoader, "io.legado.app.utils.Utf8BomUtils", "hasBom", withBom.clone());
        check(refLoader, ourLoader, "io.legado.app.utils.Utf8BomUtils", "hasBom", withoutBom.clone());

        String net = "io.legado.app.utils.NetworkUtils";
        for (String s : new String[]{"192.168.1.1", "256.1.1.1", "1.2.3", "abc", "0.0.0.0"}) {
            check(refLoader, ourLoader, net, "isIPv4Address", s);
        }
        for (String s : new String[]{"http://a.b.com/x/y", "https://a.com", "ftp://x", "http//bad"}) {
            check(refLoader, ourLoader, net, "getBaseUrl", s);
        }
        check(refLoader, ourLoader, net, "getBaseUrl", (Object) null);
        for (String s : new String[]{"http://a.b.com/x", "http://a.com/x", "http://a.com"}) {
            check(refLoader, ourLoader, net, "getSubDomain", s);
        }
        check(refLoader, ourLoader, net, "getSubDomain", (Object) null);
        for (String s : new String[]{"abc", "a b", "a%20b", "a%2Gb", "a+b", "中文", ""}) {
            check(refLoader, ourLoader, net, "hasUrlEncoded", s);
        }
        check(refLoader, ourLoader, net, "getAbsoluteURL", "http://a.com/x/", "y.png");
        check(refLoader, ourLoader, net, "getAbsoluteURL", (Object) null, "y.png");
        check(refLoader, ourLoader, net, "getAbsoluteURL", "http://a.com/x/", "");
        check(refLoader, ourLoader, net, "getAbsoluteURL", "http://a.com/x?a=1,2", "../y.png");

        System.out.println();
        System.out.println("通过 " + passed + " / 失败 " + failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static String repeat(String s, int n) {
        StringBuilder sb = new StringBuilder(s.length() * n);
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }
}
