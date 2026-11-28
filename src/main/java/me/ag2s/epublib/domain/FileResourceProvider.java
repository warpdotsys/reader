/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileResourceProvider
implements LazyResourceProvider {
    String dir;

    public FileResourceProvider(String parentDir) {
        this.dir = parentDir;
    }

    public FileResourceProvider(File parentFile) {
        this.dir = parentFile.getPath();
    }

    @Override
    public InputStream getResourceStream(String href) throws IOException {
        return new FileInputStream(new File(this.dir, href));
    }
}

