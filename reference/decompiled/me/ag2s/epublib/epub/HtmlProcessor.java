/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.io.OutputStream;
import me.ag2s.epublib.domain.Resource;

public interface HtmlProcessor {
    public void processHtmlResource(Resource var1, OutputStream var2);
}

