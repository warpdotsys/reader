/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.Date;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.epub.PackageDocumentBase;
import me.ag2s.epublib.util.StringUtil;
import org.xmlpull.v1.XmlSerializer;

public class PackageDocumentMetadataWriter
extends PackageDocumentBase {
    public static void writeMetaData(EpubBook book, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag("http://www.idpf.org/2007/opf", "metadata");
        serializer.setPrefix("dc", "http://purl.org/dc/elements/1.1/");
        serializer.setPrefix("", "http://www.idpf.org/2007/opf");
        PackageDocumentMetadataWriter.writeIdentifiers(book.getMetadata().getIdentifiers(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("title", book.getMetadata().getTitles(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("subject", book.getMetadata().getSubjects(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("description", book.getMetadata().getDescriptions(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("publisher", book.getMetadata().getPublishers(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("type", book.getMetadata().getTypes(), serializer);
        PackageDocumentMetadataWriter.writeSimpleMetdataElements("rights", book.getMetadata().getRights(), serializer);
        for (Author author : book.getMetadata().getAuthors()) {
            serializer.startTag("http://purl.org/dc/elements/1.1/", "creator");
            serializer.attribute("http://www.idpf.org/2007/opf", "role", author.getRelator().getCode());
            serializer.attribute("http://www.idpf.org/2007/opf", "file-as", author.getLastname() + ", " + author.getFirstname());
            serializer.text(author.getFirstname() + " " + author.getLastname());
            serializer.endTag("http://purl.org/dc/elements/1.1/", "creator");
        }
        for (Author author : book.getMetadata().getContributors()) {
            serializer.startTag("http://purl.org/dc/elements/1.1/", "contributor");
            serializer.attribute("http://www.idpf.org/2007/opf", "role", author.getRelator().getCode());
            serializer.attribute("http://www.idpf.org/2007/opf", "file-as", author.getLastname() + ", " + author.getFirstname());
            serializer.text(author.getFirstname() + " " + author.getLastname());
            serializer.endTag("http://purl.org/dc/elements/1.1/", "contributor");
        }
        for (Date date : book.getMetadata().getDates()) {
            serializer.startTag("http://purl.org/dc/elements/1.1/", "date");
            if (date.getEvent() != null) {
                serializer.attribute("http://www.idpf.org/2007/opf", "event", date.getEvent().toString());
            }
            serializer.text(date.getValue());
            serializer.endTag("http://purl.org/dc/elements/1.1/", "date");
        }
        if (StringUtil.isNotBlank(book.getMetadata().getLanguage())) {
            serializer.startTag("http://purl.org/dc/elements/1.1/", "language");
            serializer.text(book.getMetadata().getLanguage());
            serializer.endTag("http://purl.org/dc/elements/1.1/", "language");
        }
        if (book.getMetadata().getOtherProperties() != null) {
            for (Map.Entry entry : book.getMetadata().getOtherProperties().entrySet()) {
                serializer.startTag(((QName)entry.getKey()).getNamespaceURI(), "meta");
                serializer.attribute("", "property", ((QName)entry.getKey()).getLocalPart());
                serializer.text((String)entry.getValue());
                serializer.endTag(((QName)entry.getKey()).getNamespaceURI(), "meta");
            }
        }
        if (book.getCoverImage() != null) {
            serializer.startTag("http://www.idpf.org/2007/opf", "meta");
            serializer.attribute("", "name", "cover");
            serializer.attribute("", "content", book.getCoverImage().getId());
            serializer.endTag("http://www.idpf.org/2007/opf", "meta");
        }
        serializer.startTag("http://www.idpf.org/2007/opf", "meta");
        serializer.attribute("", "name", "generator");
        serializer.attribute("", "content", "Ag2S EpubLib");
        serializer.endTag("http://www.idpf.org/2007/opf", "meta");
        serializer.startTag("http://www.idpf.org/2007/opf", "meta");
        serializer.attribute("", "name", "duokan-body-font");
        serializer.attribute("", "content", "DK-SONGTI");
        serializer.endTag("http://www.idpf.org/2007/opf", "meta");
        serializer.endTag("http://www.idpf.org/2007/opf", "metadata");
    }

    private static void writeSimpleMetdataElements(String tagName, List<String> values, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        for (String value : values) {
            if (StringUtil.isBlank(value)) continue;
            serializer.startTag("http://purl.org/dc/elements/1.1/", tagName);
            serializer.text(value);
            serializer.endTag("http://purl.org/dc/elements/1.1/", tagName);
        }
    }

    private static void writeIdentifiers(List<Identifier> identifiers, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        Identifier bookIdIdentifier = Identifier.getBookIdIdentifier(identifiers);
        if (bookIdIdentifier == null) {
            return;
        }
        serializer.startTag("http://purl.org/dc/elements/1.1/", "identifier");
        serializer.attribute("", "id", "duokan-book-id");
        serializer.attribute("http://www.idpf.org/2007/opf", "scheme", bookIdIdentifier.getScheme());
        serializer.text(bookIdIdentifier.getValue());
        serializer.endTag("http://purl.org/dc/elements/1.1/", "identifier");
        for (Identifier identifier : identifiers.subList(1, identifiers.size())) {
            if (identifier == bookIdIdentifier) continue;
            serializer.startTag("http://purl.org/dc/elements/1.1/", "identifier");
            serializer.attribute("http://www.idpf.org/2007/opf", "scheme", identifier.getScheme());
            serializer.text(identifier.getValue());
            serializer.endTag("http://purl.org/dc/elements/1.1/", "identifier");
        }
    }
}

