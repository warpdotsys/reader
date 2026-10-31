/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package io.legado.app.lib.icu4j;


abstract class CharsetRecognizer {
    CharsetRecognizer() {
    }

    abstract String getName();

    public String getLanguage() {
        return null;
    }

    abstract CharsetMatch match(CharsetDetector var1);
}

