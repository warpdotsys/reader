/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.domain;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class ResourceInputStream
extends FilterInputStream {
    private final ZipFile zipFile;

    public ResourceInputStream(InputStream in, ZipFile zipFile) {
        super(in);
        this.zipFile = zipFile;
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.zipFile.close();
    }
}

