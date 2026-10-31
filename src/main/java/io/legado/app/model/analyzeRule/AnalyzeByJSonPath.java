/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import io.legado.app.model.analyzeRule.RuleAnalyzer;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0003J\u001d\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\nJ\u0015\u0010\u000b\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\fJ\u0010\u0010\r\u001a\u0004\u0018\u00010\t2\u0006\u0010\b\u001a\u00020\tJ\u001b\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\b\u001a\u00020\tH\u0000\u00a2\u0006\u0002\b\u0010R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSonPath;", "", "json", "(Ljava/lang/Object;)V", "ctx", "Lcom/jayway/jsonpath/ReadContext;", "getList", "Ljava/util/ArrayList;", "rule", "", "getList$reader_pro", "getObject", "getObject$reader_pro", "getString", "getStringList", "", "getStringList$reader_pro", "Companion", "reader-pro"})
public final class AnalyzeByJSonPath {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private ReadContext ctx;

    public AnalyzeByJSonPath(@NotNull Object json) {
        Intrinsics.checkNotNullParameter(json, "json");
        this.ctx = Companion.parse(json);
    }

    @Nullable
    public final String getString(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return null;
        }
        String result2 = null;
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        Object object = new String[]{"&&", "||"};
        ArrayList<String> rules = ruleAnalyzes.splitRule((String[])object);
        if (rules.size() == 1) {
            ruleAnalyzes.reSetPos();
            result2 = RuleAnalyzer.innerRule$default(ruleAnalyzes, "{$.", 0, 0, new Function1<String, String>(this){
                final /* synthetic */ AnalyzeByJSonPath this$0;
                {
                    this.this$0 = $receiver;
                    super(1);
                }

                @Nullable
                public final String invoke(@NotNull String it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    return this.this$0.getString(it);
                }
            }, 6, null);
            object = result2;
            boolean bl2 = false;
            if (object.length() == 0) {
                try {
                    Object ob = this.ctx.read(rule, new Predicate[0]);
                    result2 = ob instanceof List ? CollectionsKt.joinToString$default((Iterable)ob, "\n", null, null, 0, null, null, 62, null) : ob.toString();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result2;
        }
        boolean bl3 = false;
        ArrayList<String> textList = new ArrayList<String>();
        for (String rl : rules) {
            Intrinsics.checkNotNullExpressionValue(rl, "rl");
            String temp = this.getString(rl);
            CharSequence charSequence2 = temp;
            boolean bl4 = false;
            boolean bl5 = false;
            if (charSequence2 == null || charSequence2.length() == 0) continue;
            textList.add(temp);
            if (!Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
            break;
        }
        return CollectionsKt.joinToString$default(textList, "\n", null, null, 0, null, null, 62, null);
    }

    @NotNull
    public final List<String> getStringList$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        ArrayList<String> result2 = new ArrayList<String>();
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return result2;
        }
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        String[] stringArray = new String[]{"&&", "||", "%%"};
        ArrayList<String> rules = ruleAnalyzes.splitRule(stringArray);
        if (rules.size() == 1) {
            block16: {
                ruleAnalyzes.reSetPos();
                String st2 = RuleAnalyzer.innerRule$default(ruleAnalyzes, "{$.", 0, 0, new Function1<String, String>(this){
                    final /* synthetic */ AnalyzeByJSonPath this$0;
                    {
                        this.this$0 = $receiver;
                        super(1);
                    }

                    @Nullable
                    public final String invoke(@NotNull String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return this.this$0.getString(it);
                    }
                }, 6, null);
                CharSequence charSequence2 = st2;
                boolean bl2 = false;
                if (charSequence2.length() == 0) {
                    try {
                        Object obj = this.ctx.read(rule, new Predicate[0]);
                        if (obj instanceof List) {
                            for (Object o : (List)obj) {
                                result2.add(String.valueOf(o));
                            }
                            break block16;
                        }
                        result2.add(obj.toString());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    result2.add(st2);
                }
            }
            return result2;
        }
        ArrayList<List<String>> results = new ArrayList<List<String>>();
        for (String string : rules) {
            Intrinsics.checkNotNullExpressionValue(string, "rl");
            List<String> temp = this.getStringList$reader_pro(string);
            Object object = temp;
            boolean bl2 = false;
            if (!(!object.isEmpty())) continue;
            results.add(temp);
            object = temp;
            bl2 = false;
            if (!(!object.isEmpty()) || !Intrinsics.areEqual(ruleAnalyzes.getElementsType(), "||")) continue;
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

    @NotNull
    public final Object getObject$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        Object t = this.ctx.read(rule, new Predicate[0]);
        Intrinsics.checkNotNullExpressionValue(t, "ctx.read(rule)");
        return t;
    }

    @Nullable
    public final ArrayList<Object> getList$reader_pro(@NotNull String rule) {
        Intrinsics.checkNotNullParameter(rule, "rule");
        ArrayList<Object> result2 = new ArrayList<Object>();
        CharSequence charSequence = rule;
        boolean bl = false;
        if (charSequence.length() == 0) {
            return result2;
        }
        RuleAnalyzer ruleAnalyzes = new RuleAnalyzer(rule, true);
        Object object = new String[]{"&&", "||", "%%"};
        ArrayList<String> rules = ruleAnalyzes.splitRule((String[])object);
        if (rules.size() == 1) {
            object = this.ctx;
            boolean bl2 = false;
            boolean bl3 = false;
            Object it = object;
            boolean bl4 = false;
            try {
                return (ArrayList)it.read(rules.get(0), new Predicate[0]);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();
            for (String string : rules) {
                Intrinsics.checkNotNullExpressionValue(string, "rl");
                ArrayList<Object> temp = this.getList$reader_pro(string);
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
                    int n2 = ((ArrayList)results.get(0)).size();
                    if (n < n2) {
                        do {
                            int i = n++;
                            for (ArrayList arrayList : results) {
                                Object e;
                                if (i >= arrayList.size() || (e = arrayList.get(i)) == null) continue;
                                Object e2 = e;
                                boolean bl3 = false;
                                boolean bl4 = false;
                                Object it = e2;
                                boolean bl7 = false;
                                result2.add(it);
                            }
                        } while (n < n2);
                    }
                } else {
                    for (ArrayList arrayList : results) {
                        result2.addAll(arrayList);
                    }
                }
            }
        }
        return result2;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001\u00a8\u0006\u0006"}, d2={"Lio/legado/app/model/analyzeRule/AnalyzeByJSonPath$Companion;", "", "()V", "parse", "Lcom/jayway/jsonpath/ReadContext;", "json", "reader-pro"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ReadContext parse(@NotNull Object json) {
            ReadContext readContext;
            Intrinsics.checkNotNullParameter(json, "json");
            Object object = json;
            if (object instanceof ReadContext) {
                readContext = (ReadContext)json;
            } else if (object instanceof String) {
                DocumentContext documentContext = JsonPath.parse((String)json);
                Intrinsics.checkNotNullExpressionValue(documentContext, "parse(json)");
                readContext = documentContext;
            } else {
                DocumentContext documentContext = JsonPath.parse(json);
                Intrinsics.checkNotNullExpressionValue(documentContext, "parse(json)");
                readContext = documentContext;
            }
            return readContext;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

