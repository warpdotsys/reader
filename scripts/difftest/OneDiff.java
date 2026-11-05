import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/** 单独对比一个类的某个无参方法，完整打印差异 */
public class OneDiff {
    public static void main(String[] args) throws Exception {
        String artDir = args[0], locDirs = args[1], libDir = args[2];
        String cls = args[3], method = args[4];

        List<URL> deps = Files.list(Paths.get(libDir))
                .filter(p -> p.toString().endsWith(".jar"))
                .map(p -> { try { return p.toUri().toURL(); } catch (Exception e) { return null; } })
                .filter(Objects::nonNull).collect(Collectors.toList());

        List<URL> a = new ArrayList<>(deps); a.add(Paths.get(artDir).toUri().toURL());
        URLClassLoader la = new URLClassLoader(a.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());
        List<URL> l = new ArrayList<>(deps);
        for (String d : locDirs.split(",")) if (!d.isEmpty()) l.add(Paths.get(d).toUri().toURL());
        URLClassLoader lb = new URLClassLoader(l.toArray(new URL[0]), ClassLoader.getPlatformClassLoader());

        Class<?> ca = Class.forName(cls, true, la), cb = Class.forName(cls, true, lb);
        Constructor<?> k1 = ca.getDeclaredConstructor(), k2 = cb.getDeclaredConstructor();
        k1.setAccessible(true); k2.setAccessible(true);
        Object ia = k1.newInstance(), ib = k2.newInstance();
        Method ma = ca.getDeclaredMethod(method), mb = cb.getDeclaredMethod(method);
        ma.setAccessible(true); mb.setAccessible(true);
        String sa = String.valueOf(ma.invoke(ia)), sb = String.valueOf(mb.invoke(ib));

        System.out.println("制品 len=" + sa.length());
        System.out.println("本地 len=" + sb.length());
        System.out.println("相等: " + sa.equals(sb));
        if (!sa.equals(sb)) {
            int n = Math.min(sa.length(), sb.length());
            int i = 0;
            while (i < n && sa.charAt(i) == sb.charAt(i)) i++;
            System.out.println("首个差异位置: " + i);
            System.out.println("制品: ..." + sa.substring(Math.max(0, i - 60), Math.min(sa.length(), i + 200)));
            System.out.println("本地: ..." + sb.substring(Math.max(0, i - 60), Math.min(sb.length(), i + 200)));
        }
    }
}
