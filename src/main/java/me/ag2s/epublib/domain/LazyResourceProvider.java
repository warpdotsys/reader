/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.domain;

import java.io.IOException;
import java.io.InputStream;

public interface LazyResourceProvider {
    public InputStream getResourceStream(String var1) throws IOException;
}

