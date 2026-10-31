/*
 * Decompiled with CFR 0.152.
 */
package com.htmake.reader.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003:\u0001\u001cB\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\r\u001a\u00020\u000eJ\u0015\u0010\u000f\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0010\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0011J \u0010\u0012\u001a\u00020\u000e2\u0016\u0010\u0013\u001a\u00120\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000H\u0002J\u001b\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00028\u00002\u0006\u0010\u0015\u001a\u00028\u0001\u00a2\u0006\u0002\u0010\u0016J%\u0010\u0017\u001a\u0014\u0018\u00010\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u0010\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0018J\b\u0010\u0019\u001a\u00020\u000eH\u0002J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R*\u0010\b\u001a\u001e\u0012\u0004\u0012\u00028\u0000\u0012\u0014\u0012\u00120\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00000\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\u000b\u001a\u0014\u0018\u00010\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010\f\u001a\u0014\u0018\u00010\nR\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0000X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lcom/htmake/reader/utils/LRUCache;", "K", "V", "", "size", "", "(I)V", "cacheCapacity", "caches", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/htmake/reader/utils/LRUCache$CacheNode;", "first", "last", "clear", "", "get", "k", "(Ljava/lang/Object;)Ljava/lang/Object;", "moveToFirst", "node", "put", "v", "(Ljava/lang/Object;Ljava/lang/Object;)V", "remove", "(Ljava/lang/Object;)Lcom/htmake/reader/utils/LRUCache$CacheNode;", "removeLast", "toString", "", "CacheNode", "reader-pro"})
public final class LRUCache<K, V> {
    private int cacheCapacity;
    @NotNull
    private ConcurrentHashMap<K, CacheNode> caches;
    @Nullable
    private CacheNode first;
    @Nullable
    private CacheNode last;

    public LRUCache(int size) {
        this.cacheCapacity = size;
        this.caches = new ConcurrentHashMap(size);
    }

    public final void put(K k, V v) {
        Map map;
        CacheNode node = this.caches.get(k);
        if (node == null) {
            if (this.caches.size() >= this.cacheCapacity) {
                map = this.caches;
                CacheNode cacheNode = this.last;
                cacheNode = cacheNode == null ? null : cacheNode.getKey();
                boolean bl = false;
                Map map2 = map;
                if (map2 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
                }
                TypeIntrinsics.asMutableMap(map2).remove(cacheNode);
                this.removeLast();
            }
            node = new CacheNode();
            node.setKey(k);
        }
        node.setValue(v);
        this.moveToFirst(node);
        map = this.caches;
        boolean bl = false;
        map.put(k, node);
    }

    @Nullable
    public final V get(K k) {
        V v;
        CacheNode node = this.caches.get(k);
        CacheNode cacheNode = node;
        if (cacheNode == null) {
            v = null;
        } else {
            this.moveToFirst(node);
            v = node.getValue();
        }
        return v;
    }

    @Nullable
    public final CacheNode remove(K k) {
        CacheNode node = this.caches.get(k);
        if (node != null) {
            CacheNode cacheNode;
            if (node.getPre() != null && (cacheNode = node.getPre()) != null) {
                cacheNode.setNext(node.getNext());
            }
            if (node.getNext() != null && (cacheNode = node.getNext()) != null) {
                cacheNode.setPre(node.getPre());
            }
            if (Intrinsics.areEqual(node, this.first)) {
                this.first = node.getNext();
            }
            if (Intrinsics.areEqual(node, this.last)) {
                this.last = node.getPre();
            }
        }
        return this.caches.remove(k);
    }

    private final void moveToFirst(CacheNode node) {
        CacheNode cacheNode;
        if (Intrinsics.areEqual(this.first, node)) {
            return;
        }
        if (node.getNext() != null && (cacheNode = node.getNext()) != null) {
            cacheNode.setPre(node.getPre());
        }
        if (node.getPre() != null && (cacheNode = node.getPre()) != null) {
            cacheNode.setNext(node.getNext());
        }
        if (Intrinsics.areEqual(node, this.last)) {
            cacheNode = this.last;
            CacheNode cacheNode2 = this.last = cacheNode == null ? null : cacheNode.getPre();
        }
        if (this.first == null || this.last == null) {
            this.first = node;
            this.last = node;
            return;
        }
        node.setNext(this.first);
        cacheNode = this.first;
        if (cacheNode != null) {
            cacheNode.setPre(node);
        }
        cacheNode = this.first = node;
        if (cacheNode != null) {
            cacheNode.setPre(null);
        }
    }

    private final void removeLast() {
        if (this.last != null) {
            CacheNode cacheNode = this.last;
            CacheNode cacheNode2 = this.last = cacheNode == null ? null : cacheNode.getPre();
            if (this.last == null) {
                this.first = null;
            } else {
                cacheNode = this.last;
                if (cacheNode != null) {
                    cacheNode.setNext(null);
                }
            }
        }
    }

    public final void clear() {
        this.first = null;
        this.last = null;
        this.caches.clear();
    }

    @NotNull
    public String toString() {
        Object object;
        StringBuilder sb = new StringBuilder();
        for (CacheNode node = this.first; node != null; node = node.getNext()) {
            object = StringCompanionObject.INSTANCE;
            String string = "%s:%s ";
            Object[] objectArray = new Object[]{node.getKey(), node.getValue()};
            boolean bl = false;
            String string2 = String.format(string, Arrays.copyOf(objectArray, objectArray.length));
            Intrinsics.checkNotNullExpressionValue(string2, "java.lang.String.format(format, *args)");
            sb.append(string2);
        }
        object = sb.toString();
        Intrinsics.checkNotNullExpressionValue(object, "sb.toString()");
        return object;
    }

    @Metadata(mv={1, 5, 1}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u0004\u0018\u00018\u0000X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\b\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R,\u0010\t\u001a\u0014\u0018\u00010\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR,\u0010\u000f\u001a\u0014\u0018\u00010\u0000R\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001e\u0010\u0012\u001a\u0004\u0018\u00018\u0001X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\b\u001a\u0004\b\u0013\u0010\u0005\"\u0004\b\u0014\u0010\u0007\u00a8\u0006\u0015"}, d2={"Lcom/htmake/reader/utils/LRUCache$CacheNode;", "", "(Lcom/htmake/reader/utils/LRUCache;)V", "key", "getKey", "()Ljava/lang/Object;", "setKey", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "next", "Lcom/htmake/reader/utils/LRUCache;", "getNext", "()Lcom/htmake/reader/utils/LRUCache$CacheNode;", "setNext", "(Lcom/htmake/reader/utils/LRUCache$CacheNode;)V", "pre", "getPre", "setPre", "value", "getValue", "setValue", "reader-pro"})
    public final class CacheNode {
        @Nullable
        private CacheNode pre;
        @Nullable
        private CacheNode next;
        @Nullable
        private K key;
        @Nullable
        private V value;

        public CacheNode() {
            Intrinsics.checkNotNullParameter(LRUCache.this, "this$0");
        }

        @Nullable
        public final CacheNode getPre() {
            return this.pre;
        }

        public final void setPre(@Nullable CacheNode cacheNode) {
            this.pre = cacheNode;
        }

        @Nullable
        public final CacheNode getNext() {
            return this.next;
        }

        public final void setNext(@Nullable CacheNode cacheNode) {
            this.next = cacheNode;
        }

        @Nullable
        public final K getKey() {
            return this.key;
        }

        public final void setKey(@Nullable K k) {
            this.key = k;
        }

        @Nullable
        public final V getValue() {
            return this.value;
        }

        public final void setValue(@Nullable V v) {
            this.value = v;
        }
    }
}

