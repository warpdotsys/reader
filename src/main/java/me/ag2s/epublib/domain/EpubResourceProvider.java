/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import me.ag2s.epublib.domain.LazyResourceProvider;
import me.ag2s.epublib.domain.ResourceInputStream;

public class EpubResourceProvider
implements LazyResourceProvider {
    private final String epubFilename;

    public EpubResourceProvider(String epubFilename) {
        this.epubFilename = epubFilename;
    }

    @Override
    public InputStream getResourceStream(String href) throws IOException {
        ZipFile zipFile = new ZipFile(this.epubFilename);
        ZipEntry zipEntry = zipFile.getEntry(href);
        if (zipEntry == null) {
            zipFile.close();
            throw new IllegalStateException("Cannot find entry " + href + " in epub file " + this.epubFilename);
        }
        return new ResourceInputStream(zipFile.getInputStream(zipEntry), zipFile);
    }
}

