/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.umdlib.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.zip.InflaterInputStream;

public class UmdUtils {
    private static final int EOF = -1;
    private static final int BUFFER_SIZE = 8192;
    private static Random random = new Random();

    public static byte[] stringToUnicodeBytes(String s) {
        if (s == null) {
            throw new NullPointerException();
        }
        int len = s.length();
        byte[] ret = new byte[len * 2];
        int a;
        int b;
        int c;
        for (int i = 0; i < len; ++i) {
            c = s.charAt(i);
            a = c >> 8;
            b = c & 0xFF;
            if (a < 0) {
                a += 255;
            }
            if (b < 0) {
                b += 255;
            }
            ret[i * 2] = (byte)b;
            ret[i * 2 + 1] = (byte)a;
        }
        return ret;
    }

    public static String unicodeBytesToString(byte[] bytes) {
        char[] s = new char[bytes.length / 2];
        StringBuilder sb = new StringBuilder();
        int a;
        int b;
        int c;
        for (int i = 0; i < s.length; ++i) {
            a = bytes[i * 2 + 1];
            b = bytes[i * 2];
            c = (a & 0xFF) << 8 | b & 0xFF;
            if (c < 0) {
                c += 65535;
            }
            char[] c1 = Character.toChars(c);
            sb.append(c1);
        }
        return sb.toString();
    }

    public static String toHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length);
        String sTmp;
        for (int i = 0; i < bArr.length; ++i) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2) {
                sb.append(0);
            }
            sb.append(sTmp.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] decompress(byte[] compress) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(compress);
        InflaterInputStream iis = new InflaterInputStream(bais);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = 0;
        byte[] buf = new byte[8192];
        while (true) {
            c = iis.read(buf);
            if (c == -1) {
                break;
            }
            baos.write(buf, 0, c);
        }
        baos.flush();
        return baos.toByteArray();
    }
    public static void saveFile(File f, byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(f);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(content);
            bos.flush();
        } finally {
            fos.close();
        }
    }
    public static byte[] readFile(File f) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bis = new BufferedInputStream(fis);
            int ch;
            while ((ch = bis.read()) >= 0) {
                baos.write(ch);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            fis.close();
        }
    }

    public static byte[] genRandomBytes(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("Length must > 0: " + len);
        }
        byte[] ret = new byte[len];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (byte)random.nextInt(256);
        }
        return ret;
    }
}

