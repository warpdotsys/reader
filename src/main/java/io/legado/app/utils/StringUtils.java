/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.utils;

import io.legado.app.utils.TextUtils;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\f\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0010\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011J\u000e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\nJ\u0016\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\nJ\u0016\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nJ\u000e\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nJ\u000e\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\nJ\u000e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\nJ\u000e\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\nJ\u000e\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020\nJ\u0012\u0010%\u001a\u0004\u0018\u00010\n2\b\u0010&\u001a\u0004\u0018\u00010\nJ\u0016\u0010'\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\u0006\u0010(\u001a\u00020\u0006J\u0010\u0010)\u001a\u00020\u00062\b\u0010$\u001a\u0004\u0018\u00010\nJ\u000e\u0010*\u001a\u00020\n2\u0006\u0010$\u001a\u00020\nJ\u000e\u0010+\u001a\u00020\n2\u0006\u0010,\u001a\u00020\u0016J\u000e\u0010-\u001a\u00020\n2\u0006\u0010.\u001a\u00020\nJ\u0010\u0010/\u001a\u00020\n2\b\u00100\u001a\u0004\u0018\u00010\nR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000e\u00a8\u00061"}, d2={"Lio/legado/app/utils/StringUtils;", "", "()V", "ChnMap", "Ljava/util/HashMap;", "", "", "DAY_OF_YESTERDAY", "HOUR_OF_DAY", "TAG", "", "TIME_UNIT", "chnMap", "getChnMap", "()Ljava/util/HashMap;", "byteToHexString", "bytes", "", "chineseNumToInt", "chNum", "dateConvert", "time", "", "pattern", "source", "formatHtml", "html", "fullToHalf", "input", "halfToFull", "hexStringToByte", "hexString", "isContainNumber", "", "company", "isNumeric", "str", "removeUTFCharacters", "data", "repeat", "n", "stringToInt", "toFirstCapital", "toSize", "length", "trim", "s", "wordCountFormat", "wc", "reader-pro"})
public final class StringUtils {
    @NotNull
    public static final StringUtils INSTANCE = new StringUtils();
    @NotNull
    private static final String TAG = "StringUtils";
    private static final int HOUR_OF_DAY = 24;
    private static final int DAY_OF_YESTERDAY = 2;
    private static final int TIME_UNIT = 60;
    @NotNull
    private static final HashMap<Character, Integer> ChnMap = INSTANCE.getChnMap();

    private StringUtils() {
    }

    private final HashMap<Character, Integer> getChnMap() {
        boolean bl;
        Integer n;
        Character c;
        Object object;
        int i;
        String cnStr;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        String string = cnStr = "\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341";
        boolean bl2 = false;
        char[] cArray = string.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "(this as java.lang.String).toCharArray()");
        char[] c2 = cArray;
        int n2 = 0;
        do {
            i = n2++;
            object = map;
            c = Character.valueOf(c2[i]);
            n = i;
            bl = false;
            object.put(c, n);
        } while (n2 <= 10);
        String string2 = cnStr = "\u3007\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396\u62fe";
        i = 0;
        char[] cArray2 = string2.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray2, "(this as java.lang.String).toCharArray()");
        c2 = cArray2;
        int n3 = 0;
        do {
            i = n3++;
            object = map;
            c = Character.valueOf(c2[i]);
            n = i;
            bl = false;
            object.put(c, n);
        } while (n3 <= 10);
        Map map2 = map;
        Character c3 = Character.valueOf('\u4e24');
        object = 2;
        boolean bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u767e');
        object = 100;
        bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u4f70');
        object = 100;
        bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u5343');
        object = 1000;
        bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u4edf');
        object = 1000;
        bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u4e07');
        object = 10000;
        bl3 = false;
        map2.put(c3, object);
        map2 = map;
        c3 = Character.valueOf('\u4ebf');
        object = 100000000;
        bl3 = false;
        map2.put(c3, object);
        return map;
    }

    @NotNull
    public final String dateConvert(long time, @NotNull String pattern) {
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String string = format.format(date);
        Intrinsics.checkNotNullExpressionValue(string, "format.format(date)");
        return string;
    }

    @NotNull
    public final String dateConvert(@NotNull String source, @NotNull String pattern) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        try {
            String string;
            Date date = format.parse(source);
            long curTime = calendar.getTimeInMillis();
            calendar.setTime(date);
            long difSec = Math.abs((curTime - date.getTime()) / (long)1000);
            long difMin = difSec / (long)60;
            long difHour = difMin / (long)60;
            long difDate = difHour / (long)60;
            int oldHour = calendar.get(10);
            if (oldHour == 0) {
                if (difDate == 0L) {
                    return "\u4eca\u5929";
                }
                if (difDate < 2L) {
                    return "\u6628\u5929";
                }
                SimpleDateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd");
                String string2 = convertFormat.format(date);
                Intrinsics.checkNotNullExpressionValue(string2, "convertFormat.format(date)");
                return string2;
            }
            if (difSec < 60L) {
                string = difSec + "\u79d2\u524d";
            } else if (difMin < 60L) {
                string = difMin + "\u5206\u949f\u524d";
            } else if (difHour < 24L) {
                string = difHour + "\u5c0f\u65f6\u524d";
            } else if (difDate < 2L) {
                string = "\u6628\u5929";
            } else {
                SimpleDateFormat convertFormat = new SimpleDateFormat("yyyy-MM-dd");
                String string3 = convertFormat.format(date);
                Intrinsics.checkNotNullExpressionValue(string3, "{\n                    val convertFormat = SimpleDateFormat(\"yyyy-MM-dd\")\n                    convertFormat.format(date)\n                }");
                string = string3;
            }
            return string;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    @NotNull
    public final String toSize(long length) {
        if (length <= 0L) {
            return "0";
        }
        String[] stringArray = new String[]{"b", "kb", "M", "G", "T"};
        String[] units = stringArray;
        double d = length;
        boolean bl = false;
        double d2 = Math.log10(d);
        d = 1024.0;
        bl = false;
        int digitGroups = (int)(d2 / Math.log10(d));
        d = 1024.0;
        double d3 = digitGroups;
        boolean bl2 = false;
        return new DecimalFormat("#,##0.##").format((double)length / Math.pow(d, d3)) + ' ' + units[digitGroups];
    }

    @NotNull
    public final String toFirstCapital(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        String string = str;
        int n = 0;
        int n2 = 1;
        boolean bl = false;
        String string2 = string.substring(n, n2);
        Intrinsics.checkNotNullExpressionValue(string2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        string = string2;
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault()");
        n2 = 0;
        String string3 = string;
        if (string3 == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
        }
        String string4 = string3.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(string4, "(this as java.lang.String).toUpperCase(locale)");
        string = str;
        int n3 = 1;
        n2 = 0;
        String string5 = string.substring(n3);
        Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).substring(startIndex)");
        return Intrinsics.stringPlus(string4, string5);
    }

    @NotNull
    public final String halfToFull(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        String string = input;
        int n = 0;
        char[] cArray = string.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "(this as java.lang.String).toCharArray()");
        char[] c = cArray;
        int n2 = 0;
        n = c.length + -1;
        if (n2 <= n) {
            do {
                int i = n2++;
                char c2 = c[i];
                char c3 = '\u0000';
                if (c2 == ' ') {
                    c[i] = 12288;
                    continue;
                }
                c3 = c[i];
                boolean bl = false;
                c2 = c3;
                boolean bl2 = '!' <= c2 ? c2 <= '~' : false;
                if (!bl2) continue;
                c2 = c[i];
                c3 = '\u0000';
                c[i] = (char)(c2 + 65248);
            } while (n2 <= n);
        }
        n2 = 0;
        return new String(c);
    }

    @NotNull
    public final String fullToHalf(@NotNull String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        String string = input;
        int n = 0;
        char[] cArray = string.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "(this as java.lang.String).toCharArray()");
        char[] c = cArray;
        int n2 = 0;
        n = c.length + -1;
        if (n2 <= n) {
            do {
                int i = n2++;
                char c2 = c[i];
                char c3 = '\u0000';
                if (c2 == '\u3000') {
                    c[i] = 32;
                    continue;
                }
                c3 = c[i];
                boolean bl = false;
                c2 = c3;
                boolean bl2 = '\uff01' <= c2 ? c2 <= '\uff5e' : false;
                if (!bl2) continue;
                c2 = c[i];
                c3 = '\u0000';
                c[i] = (char)(c2 - 65248);
            } while (n2 <= n);
        }
        n2 = 0;
        return new String(c);
    }

    public final int chineseNumToInt(@NotNull String chNum) {
        Object object;
        Intrinsics.checkNotNullParameter(chNum, "chNum");
        int result2 = 0;
        int tmp = 0;
        int billion = 0;
        CharSequence charSequence = chNum;
        boolean bl = false;
        char[] cArray = charSequence.toCharArray();
        Intrinsics.checkNotNullExpressionValue(cArray, "(this as java.lang.String).toCharArray()");
        char[] cn = cArray;
        if (cn.length > 1) {
            charSequence = chNum;
            Object object2 = "^[\u3007\u96f6\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u58f9\u8d30\u53c1\u8086\u4f0d\u9646\u67d2\u634c\u7396]$";
            boolean bl2 = false;
            object2 = new Regex((String)object2);
            bl2 = false;
            if (((Regex)object2).matches(charSequence)) {
                int n = 0;
                int n2 = cn.length + -1;
                if (n <= n2) {
                    do {
                        Integer n3;
                        int i = n++;
                        Intrinsics.checkNotNull(ChnMap.get(Character.valueOf(cn[i])));
                        Intrinsics.checkNotNullExpressionValue(n3, "ChnMap[cn[i]]!!");
                        cn[i] = (char)(48 + ((Number)n3).intValue());
                    } while (n <= n2);
                }
                n = 0;
                return Integer.parseInt(new String(cn));
            }
        }
        boolean bl3 = false;
        try {
            object = Result.Companion;
            boolean bl4 = false;
            int n = 0;
            int n4 = cn.length + -1;
            if (n <= n4) {
                do {
                    int n5;
                    Integer n6;
                    int i = n++;
                    Intrinsics.checkNotNull(ChnMap.get(Character.valueOf(cn[i])));
                    Intrinsics.checkNotNullExpressionValue(n6, "ChnMap[cn[i]]!!");
                    int tmpNum = ((Number)n6).intValue();
                    if (tmpNum == 100000000) {
                        result2 += tmp;
                        billion = billion * 100000000 + (result2 *= tmpNum);
                        result2 = 0;
                        tmp = 0;
                        continue;
                    }
                    if (tmpNum == 10000) {
                        result2 += tmp;
                        result2 *= tmpNum;
                        tmp = 0;
                        continue;
                    }
                    if (tmpNum >= 10) {
                        if (tmp == 0) {
                            tmp = 1;
                        }
                        result2 += tmpNum * tmp;
                        tmp = 0;
                        continue;
                    }
                    if (i >= 2 && i == cn.length - 1) {
                        Integer n7 = ChnMap.get(Character.valueOf(cn[i - 1]));
                        Intrinsics.checkNotNull(n7);
                        n6 = n7;
                        Intrinsics.checkNotNullExpressionValue(n6, "ChnMap[cn[i - 1]]!!");
                        if (((Number)n6).intValue() > 10) {
                            Integer n8 = ChnMap.get(Character.valueOf(cn[i - 1]));
                            Intrinsics.checkNotNull(n8);
                            n6 = n8;
                            Intrinsics.checkNotNullExpressionValue(n6, "ChnMap[cn[i - 1]]!!");
                            n5 = tmpNum * ((Number)n6).intValue() / 10;
                            continue;
                        }
                    }
                    n5 = tmp = tmp * 10 + tmpNum;
                } while (n <= n4);
            }
            Integer n9 = result2 += tmp + billion;
            n = 0;
            object = Result.constructor-impl(n9);
        }
        catch (Throwable throwable) {
            Result.Companion companion = Result.Companion;
            boolean bl5 = false;
            object = Result.constructor-impl(ResultKt.createFailure(throwable));
        }
        Object object3 = object;
        object = -1;
        boolean bl6 = false;
        return ((Number)(Result.isFailure-impl(object3) ? object : object3)).intValue();
    }

    public final int stringToInt(@Nullable String str) {
        if (str != null) {
            Object object;
            CharSequence charSequence = this.fullToHalf(str);
            Object object2 = "\\s+";
            boolean bl = false;
            object2 = new Regex((String)object2);
            String string = "";
            boolean bl2 = false;
            String num = ((Regex)object2).replace(charSequence, string);
            boolean bl3 = false;
            try {
                object2 = Result.Companion;
                boolean bl4 = false;
                Integer n = Integer.parseInt(num);
                bl2 = false;
                object2 = Result.constructor-impl(n);
            }
            catch (Throwable throwable) {
                Result.Companion companion = Result.Companion;
                boolean bl5 = false;
                object2 = Result.constructor-impl(ResultKt.createFailure(throwable));
            }
            Object object3 = object2;
            boolean bl6 = false;
            boolean bl7 = false;
            Throwable throwable = Result.exceptionOrNull-impl(object3);
            if (throwable == null) {
                object = object3;
            } else {
                Throwable it = throwable;
                boolean bl8 = false;
                object = INSTANCE.chineseNumToInt(num);
            }
            return ((Number)object).intValue();
        }
        return -1;
    }

    public final boolean isContainNumber(@NotNull String company) {
        Intrinsics.checkNotNullParameter(company, "company");
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(company);
        return m.find();
    }

    public final boolean isNumeric(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        Pattern pattern = Pattern.compile("-?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    @NotNull
    public final String wordCountFormat(@Nullable String wc) {
        if (wc == null) {
            return "";
        }
        String wordsS = "";
        if (this.isNumeric(wc)) {
            String string = wc;
            boolean bl = false;
            int words = Integer.parseInt(string);
            if (words > 0) {
                wordsS = "" + words + '\u5b57';
                if (words > 10000) {
                    DecimalFormat df = new DecimalFormat("#.#");
                    wordsS = Intrinsics.stringPlus(df.format((double)((float)words * 1.0f) / 10000.0), "\u4e07\u5b57");
                }
            }
        } else {
            wordsS = wc;
        }
        return wordsS;
    }

    @NotNull
    public final String trim(@NotNull String s) {
        String string;
        boolean bl;
        char c;
        int start2;
        Intrinsics.checkNotNullParameter(s, "s");
        CharSequence charSequence = s;
        boolean bl2 = false;
        if (charSequence.length() == 0) {
            return "";
        }
        int len = s.length();
        int end = len - 1;
        for (start2 = 0; start2 < end; ++start2) {
            c = s.charAt(start2);
            bl = false;
            if (c > ' ' && s.charAt(start2) != '\u3000') break;
        }
        while (start2 < end) {
            c = s.charAt(end);
            bl = false;
            if (c > ' ' && s.charAt(end) != '\u3000') break;
            --end;
        }
        if (end < len) {
            ++end;
        }
        if (start2 > 0 || end < len) {
            String string2 = s;
            bl = false;
            String string3 = string2.substring(start2, end);
            string = string3;
            Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        } else {
            string = s;
        }
        return string;
    }

    @NotNull
    public final String repeat(@NotNull String str, int n) {
        Intrinsics.checkNotNullParameter(str, "str");
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        if (n2 < n) {
            do {
                int i = n2++;
                stringBuilder.append(str);
            } while (n2 < n);
        }
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "stringBuilder.toString()");
        return string;
    }

    @Nullable
    public final String removeUTFCharacters(@Nullable String data) {
        if (data == null) {
            return null;
        }
        Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
        Matcher m = p.matcher(data);
        StringBuffer buf = new StringBuffer(data.length());
        while (m.find()) {
            String string = m.group(1);
            Intrinsics.checkNotNull(string);
            String ch = String.valueOf((char)Integer.parseInt(string, 16));
            m.appendReplacement(buf, Matcher.quoteReplacement(ch));
        }
        m.appendTail(buf);
        return buf.toString();
    }

    @NotNull
    public final String formatHtml(@NotNull String html) {
        String string;
        Intrinsics.checkNotNullParameter(html, "html");
        if (TextUtils.isEmpty(html)) {
            string = "";
        } else {
            CharSequence charSequence = html;
            Object object = "(?i)<(br[\\s/]*|/*p.*?|/*div.*?)>";
            boolean bl = false;
            object = new Regex((String)object);
            String string2 = "\n";
            boolean bl2 = false;
            charSequence = ((Regex)object).replace(charSequence, string2);
            object = "<[script>]*.*?>|&nbsp;";
            boolean bl3 = false;
            object = new Regex((String)object);
            String string3 = "";
            bl2 = false;
            charSequence = ((Regex)object).replace(charSequence, string3);
            object = "\\s*\\n+\\s*";
            boolean bl4 = false;
            object = new Regex((String)object);
            String string4 = "\n\u3000\u3000";
            bl2 = false;
            charSequence = ((Regex)object).replace(charSequence, string4);
            object = "^[\\n\\s]+";
            boolean bl5 = false;
            object = new Regex((String)object);
            String string5 = "\u3000\u3000";
            bl2 = false;
            charSequence = ((Regex)object).replace(charSequence, string5);
            object = "[\\n\\s]+$";
            boolean bl6 = false;
            object = new Regex((String)object);
            String string6 = "";
            bl2 = false;
            string = ((Regex)object).replace(charSequence, string6);
        }
        return string;
    }

    @NotNull
    public final String byteToHexString(@Nullable byte[] bytes2) {
        if (bytes2 == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes2.length * 2);
        Object object = bytes2;
        int n = 0;
        int n2 = ((byte[])object).length;
        while (n < n2) {
            byte b = object[n];
            ++n;
            int hex = 0xFF & b;
            if (hex < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(hex));
        }
        object = sb.toString();
        Intrinsics.checkNotNullExpressionValue(object, "sb.toString()");
        return object;
    }

    @NotNull
    public final byte[] hexStringToByte(@NotNull String hexString) {
        Intrinsics.checkNotNullParameter(hexString, "hexString");
        String hexStr = StringsKt.replace$default(hexString, " ", "", false, 4, null);
        int len = hexStr.length();
        byte[] bytes2 = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            bytes2[i / 2] = (byte)((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return bytes2;
    }
}

