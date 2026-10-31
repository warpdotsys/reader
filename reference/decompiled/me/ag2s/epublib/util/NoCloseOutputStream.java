/*
 * Decompiled with CFR 0.152.
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

