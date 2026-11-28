/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.umdlib.domain;

import java.io.IOException;
import me.ag2s.umdlib.tool.WrapOutputStream;

public class UmdEnd {
    public void buildEnd(WrapOutputStream wos) throws IOException {
        wos.writeBytes(35, 12, 0, 1, 9);
        wos.writeInt(wos.getWritten() + 4);
    }
}

