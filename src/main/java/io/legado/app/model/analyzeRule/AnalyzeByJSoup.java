/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.RuleAnalyzer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.seimicrawler.xpath.JXNode;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000e\u0018\u0000 \u00182\u00020\u0001:\u0003\u0018\u0019\u001aB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u0015\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u001a\u0010\u0006\u001a\u00020\u00072\b\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u0002J\u001e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\tH\u0002J\u0018\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\r2\u0006\u0010\u0011\u001a\u00020\tH\u0002J\u0017\u0010\u0012\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0013J\u0015\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0015J\u001b\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\r2\u0006\u0010\u0011\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSoup;", "", "doc", "(Ljava/lang/Object;)V", "element", "Lorg/jsoup/nodes/Element;", "getElements", "Lorg/jsoup/select/Elements;", "rule", "", "getElements$reader_pro", "temp", "getResultLast", "", "elements", "lastRule", "getResultList", "ruleStr", "getString", "getString$reader_pro", "getString0", "getString0$reader_pro", "getStringList", "getStringList$reader_pro", "Companion", "ElementsSingle", "SourceRule", "reader-pro"})
public final class AnalyzeByJSoup {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Element element;
    @NotNull
    private static final String[] validKeys;

    public AnalyzeByJSoup(@NotNull Object doc) {
        Intrinsics.checkNotNullParameter(doc, "doc");
        this.element = Companion.parse(doc);
    }

    @NotNull
    public final Elements getElements$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        return this.getElements(this.element, rule);
    }

    @Nullable
    public final String getString$reader_pro(@NotNull String ruleStr) {
        String string;
        Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
        Object object = ruleStr;
        boolean bl = false;
        if (object.length() == 0) {
            string = null;
        } else {
            List<String> list2 = this.getStringList$reader_pro(ruleStr);
            boolean bl2 = false;
            boolean bl3 = false;
            List<String> it = list2;
            boolean bl4 = false;
            Collection collection = it;
            boolean bl5 = false;
            object = !collection.isEmpty() ? list2 : null;
            string = object == null ? null : CollectionsKt.joinToString$default((Iterable)object, "\n", null, null, 0, null, null, 62, null);
        }
        return string;
    }

    @NotNull
    public final String getString0$reader_pro(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
        List<String> list2 = this.getStringList$reader_pro(ruleStr);
        boolean bl = false;
        boolean bl2 = false;
        List<String> it = list2;
        boolean bl3 = false;
        return it.isEmpty() ? "" : it.get(0);
    }

    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String ruleStr) {
        Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
        ArrayList<String> textS = new ArrayList<String>();
        CharSequence charSequence = ruleStr;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return textS;
        }
        SourceRule sourceRule = new SourceRule(ruleStr);
        CharSequence charSequence2 = sourceRule.getElementsRule();
        boolean bl2 = false;
        if (charSequence2.length() == 0) {
            charSequence2 = this.element.data();
            textS.add((String)(charSequence2 == null ? "" : charSequence2));
        } else {
            RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(sourceRule.getElementsRule(), false, 2, null);
            String[] stringArray = new String[]{"&&", "||", "%%"};
            ArrayList<String> ruleStrS = ruleAnalyzes.splitRule(stringArray);
            ArrayList<List<String>> results = new ArrayList<List<String>>();
            for (String string : ruleStrS) {
                List<String> list2;
                int n;
                if (sourceRule.isCss()) {
                    Intrinsics.checkNotNullExpressionValue(string, "ruleStrX");
                    int lastIndex = StringsKt.lastIndexOf$default((CharSequence)string, '@', 0, false, 6, null);
                    String string2 = string;
                    int n2 = 0;
                    boolean bl3 = false;
                    String string3 = string2.substring(n2, lastIndex);
                    Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    Elements elements = this.element.select(string3);
                    Intrinsics.checkNotNullExpressionValue(elements, "element.select(ruleStrX.substring(0, lastIndex))");
                    String string4 = string;
                    n = lastIndex + 1;
                    n2 = 0;
                    String string5 = string4.substring(n);
                    Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.String).substring(startIndex)");
                    list2 = this.getResultLast(elements, string5);
                } else {
                    Intrinsics.checkNotNullExpressionValue(string, "ruleStrX");
                    list2 = this.getResultList(string);
                }
                List<String> temp = list2;
                Collection collection = temp;
                boolean bl4 = false;
                n = 0;
                if (collection == null || collection.isEmpty()) continue;
                results.add(temp);
                if (!Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
            }
            if (results.size() > 0) {
                if (Intrinsics.areEqual("%%", ruleAnalyzes.getElementsType())) {
                    int n = 0;
                    int n3 = ((List)results.get(0)).size() + -1;
                    if (n <= n3) {
                        do {
                            int i = n++;
                            for (List list3 : results) {
                                if (i >= list3.size()) continue;
                                textS.add((String)list3.get(i));
                            }
                        } while (n <= n3);
                    }
                } else {
                    for (List list4 : results) {
                        textS.addAll(list4);
                    }
                }
            }
        }
        return textS;
    }

    private final Elements getElements(Element temp, String rule) {
        block22: {
            block21: {
                if (temp == null) break block21;
                CharSequence charSequence = rule;
                boolean bl = false;
                if (!(charSequence.length() == 0)) break block22;
            }
            return new Elements();
        }
        Elements elements = new Elements();
        SourceRule sourceRule = new SourceRule(rule);
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(sourceRule.getElementsRule(), false, 2, null);
        String[] stringArray = new String[]{"&&", "||", "%%"};
        ArrayList<String> ruleStrS = ruleAnalyzes.splitRule(stringArray);
        ArrayList<Elements> elementsList = new ArrayList<Elements>();
        if (sourceRule.isCss()) {
            for (String ruleStr : ruleStrS) {
                Elements tempS = temp.select(ruleStr);
                elementsList.add(tempS);
                if (tempS.size() <= 0 || !Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
                break;
            }
        } else {
            for (String ruleStr : ruleStrS) {
                Elements elements2;
                Intrinsics.checkNotNullExpressionValue(ruleStr, "ruleStr");
                RuleAnalyzer rsRule = new RuleAnalyzer(ruleStr, false, 2, null);
                rsRule.trim();
                String[] stringArray2 = new String[]{"@"};
                ArrayList<String> rs = rsRule.splitRule(stringArray2);
                if (rs.size() > 1) {
                    Elements el = new Elements();
                    el.add(temp);
                    for (String rl : rs) {
                        Elements es = new Elements();
                        for (Element et : el) {
                            Intrinsics.checkNotNullExpressionValue(rl, "rl");
                            es.addAll(this.getElements(et, rl));
                        }
                        el.clear();
                        el.addAll(es);
                    }
                    elements2 = el;
                } else {
                    elements2 = new ElementsSingle('\u0000', null, null, null, 15, null).getElementsSingle(temp, ruleStr);
                }
                Elements el = elements2;
                elementsList.add(el);
                if (el.size() <= 0 || !Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
            }
        }
        if (elementsList.size() > 0) {
            if (Intrinsics.areEqual("%%", ruleAnalyzes.getElementsType())) {
                int n = 0;
                int ruleStr = ((Elements)elementsList.get(0)).size();
                if (n < ruleStr) {
                    do {
                        int i = n++;
                        for (Elements es : elementsList) {
                            if (i >= es.size()) continue;
                            elements.add(es.get(i));
                        }
                    } while (n < ruleStr);
                }
            } else {
                for (Elements es : elementsList) {
                    elements.addAll(es);
                }
            }
        }
        return elements;
    }

    private final List<String> getResultList(String ruleStr) {
        List<String> list2;
        CharSequence charSequence = ruleStr;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        Elements elements = new Elements();
        elements.add(this.element);
        RuleAnalyzer rule = new RuleAnalyzer(ruleStr, false, 2, null);
        rule.trim();
        String[] stringArray = new String[]{"@"};
        ArrayList<String> rules = rule.splitRule(stringArray);
        int last = rules.size() - 1;
        int n = 0;
        if (n < last) {
            do {
                int i = n++;
                Elements es = new Elements();
                for (Element elt : elements) {
                    ElementsSingle elementsSingle = new ElementsSingle('\u0000', null, null, null, 15, null);
                    Intrinsics.checkNotNullExpressionValue(elt, "elt");
                    String string = rules.get(i);
                    Intrinsics.checkNotNullExpressionValue(string, "rules[i]");
                    es.addAll(elementsSingle.getElementsSingle(elt, string));
                }
                elements.clear();
                elements = es;
            } while (n < last);
        }
        if (elements.isEmpty()) {
            list2 = null;
        } else {
            String string = rules.get(last);
            Intrinsics.checkNotNullExpressionValue(string, "rules[last]");
            list2 = this.getResultLast(elements, string);
        }
        return list2;
    }

    private final List<String> getResultLast(Elements elements, String lastRule) {
        ArrayList<String> textS = new ArrayList<String>();
        switch (lastRule) {
            case "text": {
                for (Element element : elements) {
                    String text = element.text();
                    Intrinsics.checkNotNullExpressionValue(text, "text");
                    CharSequence charSequence = text;
                    boolean bl = false;
                    if (!(charSequence.length() > 0)) continue;
                    textS.add(text);
                }
                break;
            }
            case "textNodes": {
                for (Element element : elements) {
                    boolean bl = false;
                    ArrayList<String> tn = new ArrayList<String>();
                    List<TextNode> contentEs = element.textNodes();
                    for (TextNode item : contentEs) {
                        CharSequence charSequence = item.text();
                        Intrinsics.checkNotNullExpressionValue(charSequence, "item.text()");
                        String $this$trim$iv = charSequence;
                        boolean $i$f$trim = false;
                        CharSequence $this$trim$iv$iv = $this$trim$iv;
                        boolean $i$f$trim2 = false;
                        int startIndex$iv$iv = 0;
                        int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                        boolean startFound$iv$iv = false;
                        while (startIndex$iv$iv <= endIndex$iv$iv) {
                            boolean match$iv$iv;
                            int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                            char it = $this$trim$iv$iv.charAt(index$iv$iv);
                            boolean bl2 = false;
                            boolean bl3 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                            if (!startFound$iv$iv) {
                                if (!match$iv$iv) {
                                    startFound$iv$iv = true;
                                    continue;
                                }
                                ++startIndex$iv$iv;
                                continue;
                            }
                            if (!match$iv$iv) break;
                            --endIndex$iv$iv;
                        }
                        String text = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                        charSequence = text;
                        boolean bl4 = false;
                        if (!(charSequence.length() > 0)) continue;
                        tn.add(text);
                    }
                    Collection collection = tn;
                    boolean bl5 = false;
                    if (!(!collection.isEmpty())) continue;
                    textS.add(CollectionsKt.joinToString$default(tn, "\n", null, null, 0, null, null, 62, null));
                }
                break;
            }
            case "ownText": {
                for (Element element : elements) {
                    String text = element.ownText();
                    Intrinsics.checkNotNullExpressionValue(text, "text");
                    CharSequence charSequence = text;
                    boolean bl = false;
                    if (!(charSequence.length() > 0)) continue;
                    textS.add(text);
                }
                break;
            }
            case "html": {
                elements.select("script").remove();
                elements.select("style").remove();
                String html = elements.outerHtml();
                Intrinsics.checkNotNullExpressionValue(html, "html");
                CharSequence element = html;
                boolean text = false;
                if (!(element.length() > 0)) break;
                textS.add(html);
                break;
            }
            case "all": {
                textS.add(elements.outerHtml());
                break;
            }
            default: {
                for (Element element : elements) {
                    String url2 = element.attr(lastRule);
                    Intrinsics.checkNotNullExpressionValue(url2, "url");
                    if (StringsKt.isBlank(url2) || textS.contains(url2)) continue;
                    textS.add(url2);
                }
            }
        }
        return textS;
    }

    static {
        String[] stringArray = new String[]{"class", "id", "tag", "text", "children"};
        validKeys = stringArray;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0001R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\f"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSoup$Companion;", "", "()V", "validKeys", "", "", "getValidKeys", "()[Ljava/lang/String;", "[Ljava/lang/String;", "parse", "Lorg/jsoup/nodes/Element;", "doc", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final String[] getValidKeys() {
            return validKeys;
        }

        @NotNull
        public final Element parse(@NotNull Object doc) {
            Element element;
            Intrinsics.checkNotNullParameter(doc, "doc");
            Object object = doc;
            if (object instanceof Element) {
                element = (Element)doc;
            } else if (object instanceof JXNode) {
                Element element2 = ((JXNode)doc).isElement() ? ((JXNode)doc).asElement() : (Element)Jsoup.parse(doc.toString());
                Intrinsics.checkNotNullExpressionValue(element2, "if (doc.isElement) doc.asElement() else Jsoup.parse(doc.toString())");
                element = element2;
            } else {
                Document document = Jsoup.parse(doc.toString());
                Intrinsics.checkNotNullExpressionValue(document, "parse(doc.toString())");
                element = document;
            }
            return element;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0010\b\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B9\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\u0002\u0010\nJ\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u00c6\u0003J=\u0010\u001a\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0005H\u0002J\u0016\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010 \u001a\u00020\u0005J\t\u0010%\u001a\u00020\bH\u00d6\u0001J\t\u0010&\u001a\u00020\u0005H\u00d6\u0001R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015\u00a8\u0006'"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSoup$ElementsSingle;", "", "split", "", "beforeRule", "", "indexDefault", "", "", "indexes", "(CLjava/lang/String;Ljava/util/List;Ljava/util/List;)V", "getBeforeRule", "()Ljava/lang/String;", "setBeforeRule", "(Ljava/lang/String;)V", "getIndexDefault", "()Ljava/util/List;", "getIndexes", "getSplit", "()C", "setSplit", "(C)V", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "findIndexSet", "", "rule", "getElementsSingle", "Lorg/jsoup/select/Elements;", "temp", "Lorg/jsoup/nodes/Element;", "hashCode", "toString", "reader-pro"})
    public static final class ElementsSingle {
        private char split;
        @NotNull
        private String beforeRule;
        @NotNull
        private final List<Integer> indexDefault;
        @NotNull
        private final List<Object> indexes;

        public ElementsSingle(char split, @NotNull String beforeRule, @NotNull List<Integer> indexDefault, @NotNull List<Object> indexes) {
            Intrinsics.checkNotNullParameter(beforeRule, "beforeRule");
            Intrinsics.checkNotNullParameter(indexDefault, "indexDefault");
            Intrinsics.checkNotNullParameter(indexes, "indexes");
            this.split = split;
            this.beforeRule = beforeRule;
            this.indexDefault = indexDefault;
            this.indexes = indexes;
        }

        public /* synthetic */ ElementsSingle(char c, String string, List list2, List list3, int n, DefaultConstructorMarker defaultConstructorMarker) {
            boolean bl;
            if ((n & 1) != 0) {
                c = (char)46;
            }
            if ((n & 2) != 0) {
                string = "";
            }
            if ((n & 4) != 0) {
                bl = false;
                list2 = new ArrayList();
            }
            if ((n & 8) != 0) {
                bl = false;
                list3 = new ArrayList();
            }
            this(c, string, list2, list3);
        }

        public final char getSplit() {
            return this.split;
        }

        public final void setSplit(char c) {
            this.split = c;
        }

        @NotNull
        public final String getBeforeRule() {
            return this.beforeRule;
        }

        public final void setBeforeRule(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.beforeRule = string;
        }

        @NotNull
        public final List<Integer> getIndexDefault() {
            return this.indexDefault;
        }

        @NotNull
        public final List<Object> getIndexes() {
            return this.indexes;
        }

        @NotNull
        public final Elements getElementsSingle(@NotNull Element temp, @NotNull String rule) {
            int ix;
            int it3;
            Elements elements;
            block35: {
                block34: {
                    Intrinsics.checkNotNullParameter(temp, "temp");
                    Intrinsics.checkNotNullParameter(rule, "rule");
                    this.findIndexSet(rule);
                    CharSequence charSequence = this.beforeRule;
                    boolean bl = false;
                    if (!(charSequence.length() == 0)) break block34;
                    elements = temp.children();
                    break block35;
                }
                Object object = new String[]{"."};
                List rules = StringsKt.split$default((CharSequence)this.beforeRule, (String[])object, false, 0, 6, null);
                switch ((String)rules.get(0)) {
                    case "children": {
                        elements = temp.children();
                        break;
                    }
                    case "class": {
                        elements = temp.getElementsByClass((String)rules.get(1));
                        break;
                    }
                    case "tag": {
                        elements = temp.getElementsByTag((String)rules.get(1));
                        break;
                    }
                    case "id": {
                        elements = Collector.collect(new Evaluator.Id((String)rules.get(1)), temp);
                        break;
                    }
                    case "text": {
                        elements = temp.getElementsContainingOwnText((String)rules.get(1));
                        break;
                    }
                    default: {
                        elements = temp.select(this.beforeRule);
                    }
                }
            }
            Elements elements2 = elements;
            int len = elements2.size();
            Integer n = this.indexDefault.size() - 1;
            boolean bl = false;
            boolean bl2 = false;
            int it2 = ((Number)n).intValue();
            boolean bl3 = false;
            Integer n2 = it2 != -1 ? n : null;
            int lastIndexes = n2 == null ? this.indexes.size() - 1 : n2;
            int n3 = 0;
            Set indexSet = new LinkedHashSet();
            if (this.indexes.isEmpty()) {
                n3 = lastIndexes;
                if (0 <= n3) {
                    do {
                        boolean bl4 = 0 <= (it3 = ((Number)this.indexDefault.get(ix = n3--)).intValue()) ? it3 < len : false;
                        if (bl4) {
                            indexSet.add(it3);
                            continue;
                        }
                        if (it3 >= 0 || len < -it3) continue;
                        indexSet.add(it3 + len);
                    } while (0 <= n3);
                }
            } else {
                n3 = lastIndexes;
                if (0 <= n3) {
                    do {
                        if (this.indexes.get(ix = n3--) instanceof Triple) {
                            int end;
                            int start2;
                            Triple it3 = (Triple)this.indexes.get(ix);
                            Integer startX = (Integer)it3.component1();
                            Integer endX = (Integer)it3.component2();
                            int stepX = ((Number)it3.component3()).intValue();
                            int n4 = startX == null ? 0 : (startX >= 0 ? (startX < len ? startX : len - 1) : (start2 = -startX.intValue() <= len ? len + startX : 0));
                            int n5 = endX == null ? len - 1 : (endX >= 0 ? (endX < len ? endX : len - 1) : (end = -endX.intValue() <= len ? len + endX : 0));
                            if (start2 == end || stepX >= len) {
                                indexSet.add(start2);
                                continue;
                            }
                            int step = stepX > 0 ? stepX : (-stepX < len ? stepX + len : 1);
                            CollectionsKt.addAll((Collection)indexSet, end > start2 ? RangesKt.step(new IntRange(start2, end), step) : RangesKt.step(RangesKt.downTo(start2, end), step));
                            continue;
                        }
                        it3 = (Integer)this.indexes.get(ix);
                        boolean bl5 = 0 <= it3 ? it3 < len : false;
                        if (bl5) {
                            indexSet.add(it3);
                            continue;
                        }
                        if (it3 >= 0 || len < -it3) continue;
                        indexSet.add(it3 + len);
                    } while (0 <= n3);
                }
            }
            if (this.split == '!') {
                Iterator iterator2 = indexSet.iterator();
                while (iterator2.hasNext()) {
                    int pcInt = ((Number)iterator2.next()).intValue();
                    elements2.set(pcInt, null);
                }
                elements2.removeAll((Collection)CollectionsKt.listOf(null));
            } else if (this.split == '.') {
                Elements es = new Elements();
                Iterator iterator3 = indexSet.iterator();
                while (iterator3.hasNext()) {
                    int pcInt = ((Number)iterator3.next()).intValue();
                    es.add(elements2.get(pcInt));
                }
                elements2 = es;
            }
            Elements elements3 = elements2;
            Intrinsics.checkNotNullExpressionValue(elements3, "elements");
            return elements3;
        }

        private final void findIndexSet(String rule) {
            String rus;
            block27: {
                int n;
                int n2;
                int n3;
                int rl;
                boolean head;
                String $this$trim$iv = rule;
                boolean $i$f$trim = false;
                CharSequence $this$trim$iv$iv = $this$trim$iv;
                boolean $i$f$trim2 = false;
                int startIndex$iv$iv = 0;
                int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                int startFound$iv$iv = 0;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    boolean match$iv$iv;
                    int index$iv$iv = startFound$iv$iv == 0 ? startIndex$iv$iv : endIndex$iv$iv;
                    char it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl = false;
                    boolean bl2 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                    if (startFound$iv$iv == 0) {
                        if (!match$iv$iv) {
                            startFound$iv$iv = 1;
                            continue;
                        }
                        ++startIndex$iv$iv;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv;
                }
                rus = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                int len = rus.length();
                Integer curInt = null;
                boolean curMinus = false;
                startIndex$iv$iv = 0;
                List curList = new ArrayList();
                String l = "";
                boolean bl = head = StringsKt.last(rus) == ']';
                if (head) {
                    startFound$iv$iv = len;
                    len = startFound$iv$iv + -1;
                    while (true) {
                        Integer n4;
                        startFound$iv$iv = len;
                        len = startFound$iv$iv + -1;
                        if (startFound$iv$iv < 0) break block27;
                        rl = rus.charAt(len);
                        if (rl == 32) continue;
                        boolean bl3 = 48 <= rl ? rl <= 57 : false;
                        if (bl3) {
                            n3 = rl;
                            n2 = 0;
                            l = String.valueOf((char)n3) + l;
                            continue;
                        }
                        if (rl == 45) {
                            curMinus = true;
                            continue;
                        }
                        CharSequence charSequence = l;
                        n2 = 0;
                        if (charSequence.length() == 0) {
                            n4 = null;
                        } else if (curMinus) {
                            charSequence = l;
                            n2 = 0;
                            n4 = -Integer.parseInt((String)charSequence);
                        } else {
                            charSequence = l;
                            n2 = 0;
                            n4 = Integer.parseInt((String)charSequence);
                        }
                        curInt = n4;
                        n3 = rl;
                        if (n3 == 58) {
                            curList.add(curInt);
                        } else {
                            if (curList.isEmpty()) {
                                if (curInt == null) break block27;
                                this.indexes.add(curInt);
                            } else {
                                this.indexes.add(new Triple(curInt, CollectionsKt.last(curList), curList.size() == 2 ? (Integer)CollectionsKt.first(curList) : Integer.valueOf(1)));
                                curList.clear();
                            }
                            if (rl == 33) {
                                this.split = (char)33;
                                do {
                                    rl = rus.charAt(--len);
                                } while (len > 0 && rl == 32);
                            }
                            if (rl == 91) {
                                String string = rus;
                                n = 0;
                                boolean bl4 = false;
                                String string2 = string;
                                if (string2 == null) {
                                    throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                                }
                                String string3 = string2.substring(n, len);
                                Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                                this.beforeRule = string3;
                                return;
                            }
                            if (rl != 44) break block27;
                        }
                        l = "";
                        curMinus = false;
                    }
                }
                while (true) {
                    int n5;
                    String string;
                    rl = len;
                    len = rl + -1;
                    if (rl < 0) break;
                    rl = rus.charAt(len);
                    if (rl == 32) continue;
                    boolean bl5 = 48 <= rl ? rl <= 57 : false;
                    if (bl5) {
                        n3 = rl;
                        n2 = 0;
                        l = String.valueOf((char)n3) + l;
                        continue;
                    }
                    if (rl == 45) {
                        curMinus = true;
                        continue;
                    }
                    if (rl != 33 && rl != 46 && rl != 58) break;
                    if (curMinus) {
                        string = l;
                        n2 = 0;
                        n5 = -Integer.parseInt(string);
                    } else {
                        string = l;
                        n2 = 0;
                        n5 = Integer.parseInt(string);
                    }
                    this.indexDefault.add(n5);
                    if (rl != 58) {
                        this.split = rl;
                        string = rus;
                        n2 = 0;
                        n = 0;
                        String string4 = string;
                        if (string4 == null) {
                            throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
                        }
                        String string5 = string4.substring(n2, len);
                        Intrinsics.checkNotNullExpressionValue(string5, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                        this.beforeRule = string5;
                        return;
                    }
                    l = "";
                    curMinus = false;
                }
            }
            this.split = (char)32;
            this.beforeRule = rus;
        }

        public final char component1() {
            return this.split;
        }

        @NotNull
        public final String component2() {
            return this.beforeRule;
        }

        @NotNull
        public final List<Integer> component3() {
            return this.indexDefault;
        }

        @NotNull
        public final List<Object> component4() {
            return this.indexes;
        }

        @NotNull
        public final ElementsSingle copy(char split, @NotNull String beforeRule, @NotNull List<Integer> indexDefault, @NotNull List<Object> indexes) {
            Intrinsics.checkNotNullParameter(beforeRule, "beforeRule");
            Intrinsics.checkNotNullParameter(indexDefault, "indexDefault");
            Intrinsics.checkNotNullParameter(indexes, "indexes");
            return new ElementsSingle(split, beforeRule, indexDefault, indexes);
        }

        public static /* synthetic */ ElementsSingle copy$default(ElementsSingle elementsSingle, char c, String string, List list2, List list3, int n, Object object) {
            if ((n & 1) != 0) {
                c = elementsSingle.split;
            }
            if ((n & 2) != 0) {
                string = elementsSingle.beforeRule;
            }
            if ((n & 4) != 0) {
                list2 = elementsSingle.indexDefault;
            }
            if ((n & 8) != 0) {
                list3 = elementsSingle.indexes;
            }
            return elementsSingle.copy(c, string, list2, list3);
        }

        @NotNull
        public String toString() {
            return "ElementsSingle(split=" + this.split + ", beforeRule=" + this.beforeRule + ", indexDefault=" + this.indexDefault + ", indexes=" + this.indexes + ')';
        }

        public int hashCode() {
            int result2 = Character.hashCode(this.split);
            result2 = result2 * 31 + this.beforeRule.hashCode();
            result2 = result2 * 31 + ((Object)this.indexDefault).hashCode();
            result2 = result2 * 31 + ((Object)this.indexes).hashCode();
            return result2;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ElementsSingle)) {
                return false;
            }
            ElementsSingle elementsSingle = (ElementsSingle)other;
            if (this.split != elementsSingle.split) {
                return false;
            }
            if (!Intrinsics.areEqual(this.beforeRule, elementsSingle.beforeRule)) {
                return false;
            }
            if (!Intrinsics.areEqual(this.indexDefault, elementsSingle.indexDefault)) {
                return false;
            }
            return Intrinsics.areEqual(this.indexes, elementsSingle.indexes);
        }

        public ElementsSingle() {
            this('\u0000', null, null, null, 15, null);
        }
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0080\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSoup$SourceRule;", "", "ruleStr", "", "(Lio/legado/app/model/analyzeRule/AnalyzeByJSoup;Ljava/lang/String;)V", "elementsRule", "getElementsRule", "()Ljava/lang/String;", "setElementsRule", "(Ljava/lang/String;)V", "isCss", "", "()Z", "setCss", "(Z)V", "reader-pro"})
    public final class SourceRule {
        private boolean isCss;
        @NotNull
        private String elementsRule;

        /*
         * WARNING - void declaration
         */
        public SourceRule(String ruleStr) {
            String string;
            Intrinsics.checkNotNullParameter(AnalyzeByJSoup.this, "this$0");
            Intrinsics.checkNotNullParameter(ruleStr, "ruleStr");
            SourceRule sourceRule = this;
            if (StringsKt.startsWith(ruleStr, "@CSS:", true)) {
                void $this$trim$iv;
                this.isCss = true;
                String string2 = ruleStr;
                int n = 5;
                boolean bl = false;
                String string3 = string2.substring(n);
                Intrinsics.checkNotNullExpressionValue(string3, "(this as java.lang.String).substring(startIndex)");
                string2 = string3;
                SourceRule sourceRule2 = sourceRule;
                boolean $i$f$trim = false;
                CharSequence $this$trim$iv$iv = (CharSequence)$this$trim$iv;
                boolean $i$f$trim2 = false;
                int startIndex$iv$iv = 0;
                int endIndex$iv$iv = $this$trim$iv$iv.length() - 1;
                boolean startFound$iv$iv = false;
                while (startIndex$iv$iv <= endIndex$iv$iv) {
                    boolean match$iv$iv;
                    int index$iv$iv = !startFound$iv$iv ? startIndex$iv$iv : endIndex$iv$iv;
                    char it = $this$trim$iv$iv.charAt(index$iv$iv);
                    boolean bl2 = false;
                    boolean bl3 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
                    if (!startFound$iv$iv) {
                        if (!match$iv$iv) {
                            startFound$iv$iv = true;
                            continue;
                        }
                        ++startIndex$iv$iv;
                        continue;
                    }
                    if (!match$iv$iv) break;
                    --endIndex$iv$iv;
                }
                String string4 = ((Object)$this$trim$iv$iv.subSequence(startIndex$iv$iv, endIndex$iv$iv + 1)).toString();
                sourceRule = sourceRule2;
                string = string4;
            } else {
                string = ruleStr;
            }
            sourceRule.elementsRule = string;
        }

        public final boolean isCss() {
            return this.isCss;
        }

        public final void setCss(boolean bl) {
            this.isCss = bl;
        }

        @NotNull
        public final String getElementsRule() {
            return this.elementsRule;
        }

        public final void setElementsRule(@NotNull String string) {
            Intrinsics.checkNotNullParameter(string, "<set-?>");
            this.elementsRule = string;
        }
    }
}

