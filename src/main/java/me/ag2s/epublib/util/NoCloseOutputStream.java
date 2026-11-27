/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.util;

import java.io.IOException;
import java.io.OutputStream;

public class NoCloseOutputStream
extends OutputStream {
    private final OutputStream outputStream;

    public NoCloseOutputStream(OutputStream outputStream2) {
        this.outputStream = outputStream2;
    }

    @Override
    public void write(int b) throws IOException {
        this.outputStream.write(b);
    }

    @Override
    public void close() {
    }
}

