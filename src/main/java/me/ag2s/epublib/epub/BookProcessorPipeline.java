/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.epub.BookProcessor;

public class BookProcessorPipeline
implements BookProcessor {
    private static final String TAG = BookProcessorPipeline.class.getName();
    private List<BookProcessor> bookProcessors;

    public BookProcessorPipeline() {
        this(null);
    }

    public BookProcessorPipeline(List<BookProcessor> bookProcessingPipeline) {
        this.bookProcessors = bookProcessingPipeline;
    }

    @Override
    public EpubBook processBook(EpubBook book) {
        if (this.bookProcessors == null) {
            return book;
        }
        for (BookProcessor bookProcessor : this.bookProcessors) {
            try {
                book = bookProcessor.processBook(book);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return book;
    }

    public void addBookProcessor(BookProcessor bookProcessor) {
        if (this.bookProcessors == null) {
            this.bookProcessors = new ArrayList<BookProcessor>();
        }
        this.bookProcessors.add(bookProcessor);
    }

    public void addBookProcessors(Collection<BookProcessor> bookProcessors) {
        if (this.bookProcessors == null) {
            this.bookProcessors = new ArrayList<BookProcessor>();
        }
        this.bookProcessors.addAll(bookProcessors);
    }

    public List<BookProcessor> getBookProcessors() {
        return this.bookProcessors;
    }

    public void setBookProcessingPipeline(List<BookProcessor> bookProcessingPipeline) {
        this.bookProcessors = bookProcessingPipeline;
    }
}

