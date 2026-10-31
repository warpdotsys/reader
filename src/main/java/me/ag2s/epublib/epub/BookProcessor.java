/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.epub;

import me.ag2s.epublib.domain.EpubBook;

public interface BookProcessor {
    public static final BookProcessor IDENTITY_BOOKPROCESSOR = book -> book;

    public EpubBook processBook(EpubBook var1);
}

