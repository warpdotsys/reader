/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.utils;

import io.legado.app.utils.StringExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

@Metadata(mv={1, 5, 1}, k=2, xi=48, d1={"\u00006\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u00012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u001a\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0012\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0002\u001a\u0015\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r*\u00020\u000f\u00a2\u0006\u0002\u0010\u0010\u00a8\u0006\u0011"}, d2={"appendNormalisedText", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "textNode", "Lorg/jsoup/nodes/TextNode;", "lastCharIsWhitespace", "", "preserveWhitespace", "node", "Lorg/jsoup/nodes/Node;", "textArray", "", "", "Lorg/jsoup/nodes/Element;", "(Lorg/jsoup/nodes/Element;)[Ljava/lang/String;", "reader-pro"})
public final class JsoupExtensionsKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final String[] textArray(@NotNull Element $this$textArray) {
        void $this$trim$iv;
        Intrinsics.checkNotNullParameter($this$textArray, "<this>");
        StringBuilder sb = StringUtil.borrowBuilder();
        NodeTraversor.traverse(new NodeVisitor(sb){
            final /* synthetic */ StringBuilder $sb;
            {
                this.$sb = $sb;
            }

            public void head(@NotNull Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof TextNode) {
                    StringBuilder stringBuilder = this.$sb;
                    Intrinsics.checkNotNullExpressionValue(stringBuilder, "sb");
                    JsoupExtensionsKt.access$appendNormalisedText(stringBuilder, (TextNode)node);
                } else if (node instanceof Element) {
                    CharSequence charSequence = this.$sb;
                    Intrinsics.checkNotNullExpressionValue(charSequence, "sb");
                    charSequence = charSequence;
                    boolean bl = false;
                    if (charSequence.length() > 0 && (((Element)node).isBlock() || Intrinsics.areEqual(((Element)node).tag().getName(), "br"))) {
                        charSequence = this.$sb;
                        Intrinsics.checkNotNullExpressionValue(charSequence, "sb");
                        if (!JsoupExtensionsKt.access$lastCharIsWhitespace((StringBuilder)charSequence)) {
                            this.$sb.append("\n");
                        }
                    }
                }
            }

            public void tail(@NotNull Node node, int depth) {
                Intrinsics.checkNotNullParameter(node, "node");
                if (node instanceof Element && ((Element)node).isBlock() && node.nextSibling() instanceof TextNode) {
                    StringBuilder stringBuilder = this.$sb;
                    Intrinsics.checkNotNullExpressionValue(stringBuilder, "sb");
                    if (!JsoupExtensionsKt.access$lastCharIsWhitespace(stringBuilder)) {
                        this.$sb.append("\n");
                    }
                }
            }
        }, $this$textArray);
        String[] stringArray = StringUtil.releaseBuilder(sb);
        Intrinsics.checkNotNullExpressionValue(stringArray, "releaseBuilder(sb)");
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
            boolean bl = false;
            boolean bl2 = match$iv$iv = Intrinsics.compare(it, 32) <= 0;
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
        stringArray = new String[]{"\n"};
        return StringExtensionsKt.splitNotBlank(text, stringArray);
    }

    private static final void appendNormalisedText(StringBuilder sb, TextNode textNode) {
        String text = textNode.getWholeText();
        if (JsoupExtensionsKt.preserveWhitespace(textNode.parentNode()) || textNode instanceof CDataNode) {
            sb.append(text);
        } else {
            StringUtil.appendNormalisedWhitespace(sb, text, JsoupExtensionsKt.lastCharIsWhitespace(sb));
        }
    }

    private static final boolean preserveWhitespace(Node node) {
        if (node instanceof Element) {
            int n;
            Element el = (Element)node;
            int i = 0;
            do {
                if (el.tag().preserveWhitespace()) {
                    return true;
                }
                el = el.parent();
            } while ((i = (n = i) + 1) < 6 && el != null);
        }
        return false;
    }

    private static final boolean lastCharIsWhitespace(StringBuilder sb) {
        CharSequence charSequence = sb;
        boolean bl = false;
        return charSequence.length() > 0 && sb.charAt(sb.length() - 1) == ' ';
    }

    public static final /* synthetic */ void access$appendNormalisedText(StringBuilder sb, TextNode textNode) {
        JsoupExtensionsKt.appendNormalisedText(sb, textNode);
    }

    public static final /* synthetic */ boolean access$lastCharIsWhitespace(StringBuilder sb) {
        return JsoupExtensionsKt.lastCharIsWhitespace(sb);
    }
}

