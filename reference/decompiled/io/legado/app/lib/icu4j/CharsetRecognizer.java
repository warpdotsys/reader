/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.lib.icu4j;

import io.legado.app.lib.icu4j.CharsetDetector;
import io.legado.app.lib.icu4j.CharsetMatch;

abstract class CharsetRecognizer {
    CharsetRecognizer() {
    }

    abstract String getName();

    public String getLanguage() {
        return null;
    }

    abstract CharsetMatch match(CharsetDetector var1);
}

