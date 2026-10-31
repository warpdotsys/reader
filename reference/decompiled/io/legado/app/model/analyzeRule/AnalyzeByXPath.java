/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import io.legado.app.model.analyzeRule.RuleAnalyzer;
import io.legado.app.utils.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\tH\u0002J\u0010\u0010\f\u001a\u0004\u0018\u00010\t2\u0006\u0010\r\u001a\u00020\tJ\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u00062\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u000fJ\u0010\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tH\u0002R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByXPath;", "", "doc", "(Ljava/lang/Object;)V", "jxNode", "getElements", "", "Lorg/seimicrawler/xpath/JXNode;", "xPath", "", "getElements$reader_pro", "getResult", "getString", "rule", "getStringList", "getStringList$reader_pro", "parse", "strToJXDocument", "Lorg/seimicrawler/xpath/JXDocument;", "html", "reader-pro"})
public final class AnalyzeByXPath {
    @NotNull
    private Object jxNode;

    public AnalyzeByXPath(@NotNull Object doc) {
        Intrinsics.checkNotNullParameter(doc, "doc");
        this.jxNode = this.parse(doc);
    }

    private final Object parse(Object doc) {
        Object object;
        Object object2 = doc;
        if (object2 instanceof JXNode) {
            object = ((JXNode)doc).isElement() ? doc : this.strToJXDocument(doc.toString());
        } else if (object2 instanceof Document) {
            JXDocument jXDocument = JXDocument.create((Document)doc);
            Intrinsics.checkNotNullExpressionValue(jXDocument, "create(doc)");
            object = jXDocument;
        } else if (object2 instanceof Element) {
            Element[] elementArray = new Element[]{(Element)doc};
            JXDocument jXDocument = JXDocument.create(new Elements(elementArray));
            Intrinsics.checkNotNullExpressionValue(jXDocument, "create(Elements(doc))");
            object = jXDocument;
        } else if (object2 instanceof Elements) {
            JXDocument jXDocument = JXDocument.create((Elements)doc);
            Intrinsics.checkNotNullExpressionValue(jXDocument, "create(doc)");
            object = jXDocument;
        } else {
            object = this.strToJXDocument(doc.toString());
        }
        return object;
    }

    private final JXDocument strToJXDocument(String html) {
        String html1 = html;
        if (StringsKt.endsWith$default(html1, "</td>", false, 2, null)) {
            html1 = "<tr>" + html1 + "</tr>";
        }
        if (StringsKt.endsWith$default(html1, "</tr>", false, 2, null) || StringsKt.endsWith$default(html1, "</tbody>", false, 2, null)) {
            html1 = "<table>" + html1 + "</table>";
        }
        JXDocument jXDocument = JXDocument.create(html1);
        Intrinsics.checkNotNullExpressionValue(jXDocument, "create(html1)");
        return jXDocument;
    }

    private final List<JXNode> getResult(String xPath) {
        Object node = this.jxNode;
        return node instanceof JXNode ? ((JXNode)node).sel(xPath) : ((JXDocument)node).selN(xPath);
    }

    @Nullable
    public final List<JXNode> getElements$reader_pro(@NotNull String xPath) {
        Intrinsics.checkNotNullParameter(xPath, "xPath");
        CharSequence charSequence = xPath;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        ArrayList jxNodes = new ArrayList();
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(xPath, false, 2, null);
        Object object = new String[]{"&&", "||", "%%"};
        ArrayList<String> rules = ruleAnalyzes.splitRule((String)object);
        if (rules.size() == 1) {
            object = rules.get(0);
            Intrinsics.checkNotNullExpressionValue(object, "rules[0]");
            return this.getResult((String)object);
        }
        ArrayList<List<JXNode>> results = new ArrayList<List<JXNode>>();
        for (String string : rules) {
            Intrinsics.checkNotNullExpressionValue(string, "rl");
            List<JXNode> temp = this.getElements$reader_pro(string);
            if (temp == null) continue;
            Object object2 = temp;
            boolean bl2 = false;
            if (!(!object2.isEmpty())) continue;
            results.add(temp);
            object2 = temp;
            bl2 = false;
            if (!(!object2.isEmpty()) || !Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
        }
        if (results.size() > 0) {
            if (Intrinsics.areEqual("%%", ruleAnalyzes.getElementsType())) {
                int n = 0;
                int n2 = ((List)results.get(0)).size() + -1;
                if (n <= n2) {
                    do {
                        int i = n++;
                        for (List list2 : results) {
                            if (i >= list2.size()) continue;
                            jxNodes.add(list2.get(i));
                        }
                    } while (n <= n2);
                }
            } else {
                for (List list3 : results) {
                    jxNodes.addAll(list3);
                }
            }
        }
        return jxNodes;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String xPath) {
        Intrinsics.checkNotNullParameter(xPath, "xPath");
        ArrayList<String> result2 = new ArrayList<String>();
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(xPath, false, 2, null);
        Object object = new String[]{"&&", "||", "%%"};
        ArrayList<String> rules = ruleAnalyzes.splitRule((String)object);
        if (rules.size() == 1) {
            object = this.getResult(xPath);
            if (object != null) {
                void $this$mapTo$iv$iv;
                Iterable $this$map$iv = (Iterable)object;
                boolean bl = false;
                Iterable iterable = $this$map$iv;
                Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
                boolean bl2 = false;
                for (Object item$iv$iv : $this$mapTo$iv$iv) {
                    void it;
                    JXNode jXNode = (JXNode)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl3 = false;
                    Boolean bl4 = result2.add(it.asString());
                    collection.add(bl4);
                }
                List cfr_ignored_0 = (List)destination$iv$iv;
            }
            return result2;
        }
        ArrayList<List<String>> results = new ArrayList<List<String>>();
        for (String string : rules) {
            Intrinsics.checkNotNullExpressionValue(string, "rl");
            List<String> temp = this.getStringList$reader_pro(string);
            Object object2 = temp;
            boolean bl = false;
            if (!(!object2.isEmpty())) continue;
            results.add(temp);
            object2 = temp;
            bl = false;
            if (!(!object2.isEmpty()) || !Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
        }
        if (results.size() > 0) {
            if (Intrinsics.areEqual("%%", ruleAnalyzes.getElementsType())) {
                int n = 0;
                int n2 = ((List)results.get(0)).size() + -1;
                if (n <= n2) {
                    do {
                        int i = n++;
                        for (List list2 : results) {
                            if (i >= list2.size()) continue;
                            result2.add((String)list2.get(i));
                        }
                    } while (n <= n2);
                }
            } else {
                for (List list3 : results) {
                    result2.addAll(list3);
                }
            }
        }
        return result2;
    }

    @Nullable
    public final String getString(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, false, 2, null);
        Object object = new String[]{"&&", "||"};
        ArrayList<String> rules = ruleAnalyzes.splitRule((String)object);
        if (rules.size() == 1) {
            object = this.getResult(rule);
            if (object != null) {
                Object object2 = object;
                boolean bl = false;
                boolean bl2 = false;
                Object it = object2;
                boolean bl3 = false;
                return TextUtils.join((CharSequence)"\n", (Iterable)it);
            }
            return null;
        }
        boolean bl = false;
        ArrayList<String> textList = new ArrayList<String>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue(rl, "rl");
            String temp = this.getString(rl);
            CharSequence charSequence = temp;
            boolean bl4 = false;
            boolean bl5 = false;
            if (charSequence == null || charSequence.length() == 0) continue;
            textList.add(temp);
            if (!Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
            break;
        }
        return CollectionsKt.joinToString$default(textList, "\n", null, null, 0, null, null, 62, null);
    }
}

