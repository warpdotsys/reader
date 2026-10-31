/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.TOCReference;
import me.ag2s.epublib.domain.TableOfContents;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.EpubProcessorSupport;
import me.ag2s.epublib.epub.EpubReader;
import me.ag2s.epublib.epub.EpubWriter;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class NCXDocumentV2 {
    public static final String NAMESPACE_NCX = "http://www.daisy.org/z3986/2005/ncx/";
    public static final String PREFIX_NCX = "ncx";
    public static final String NCX_ITEM_ID = "ncx";
    public static final String DEFAULT_NCX_HREF = "toc.ncx";
    public static final String PREFIX_DTB = "dtb";
    private static final String TAG = NCXDocumentV2.class.getName();

    public static Resource read(EpubBook book, EpubReader epubReader) {
        Resource ncxResource = null;
        if (book.getSpine().getTocResource() == null) {
            System.err.println(TAG + " Book does not contain a table of contents file");
            return null;
        }
        try {
            ncxResource = book.getSpine().getTocResource();
            if (ncxResource == null) {
                return null;
            }
            System.out.println(TAG + " ncxResource.getHref()" + ncxResource.getHref());
            Document ncxDocument = ResourceUtil.getAsDocument(ncxResource);
            Element navMapElement = DOMUtil.getFirstElementByTagNameNS(ncxDocument.getDocumentElement(), NAMESPACE_NCX, "navMap");
            if (navMapElement == null) {
                return null;
            }
            TableOfContents tableOfContents = new TableOfContents(NCXDocumentV2.readTOCReferences(navMapElement.getChildNodes(), book));
            book.setTableOfContents(tableOfContents);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ncxResource;
    }

    static List<TOCReference> readTOCReferences(NodeList navpoints, EpubBook book) {
        if (navpoints == null) {
            return new ArrayList<TOCReference>();
        }
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>(navpoints.getLength());
        for (int i = 0; i < navpoints.getLength(); ++i) {
            Node node = navpoints.item(i);
            if (node.getNodeType() != 1 || !node.getLocalName().equals("navPoint")) continue;
            TOCReference tocReference = NCXDocumentV2.readTOCReference((Element)node, book);
            result2.add(tocReference);
        }
        return result2;
    }

    static TOCReference readTOCReference(Element navpointElement, EpubBook book) {
        String label = NCXDocumentV2.readNavLabel(navpointElement);
        String tocResourceRoot = StringUtil.substringBeforeLast(book.getSpine().getTocResource().getHref(), '/');
        tocResourceRoot = tocResourceRoot.length() == book.getSpine().getTocResource().getHref().length() ? "" : tocResourceRoot + "/";
        String reference = StringUtil.collapsePathDots(tocResourceRoot + NCXDocumentV2.readNavReference(navpointElement));
        String href = StringUtil.substringBefore(reference, '#');
        String fragmentId = StringUtil.substringAfter(reference, '#');
        Resource resource = book.getResources().getByHref(href);
        if (resource == null) {
            System.err.println(TAG + " Resource with href " + href + " in NCX document not found");
        }
        System.out.println(TAG + " label:" + label);
        System.out.println(TAG + " href:" + href);
        System.out.println(TAG + " fragmentId:" + fragmentId);
        TOCReference result2 = new TOCReference(label, resource, fragmentId);
        List<TOCReference> childTOCReferences = NCXDocumentV2.readTOCReferences(navpointElement.getChildNodes(), book);
        result2.setChildren(childTOCReferences);
        return result2;
    }

    private static String readNavReference(Element navpointElement) {
        Element contentElement = DOMUtil.getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX, "content");
        if (contentElement == null) {
            return null;
        }
        String result2 = DOMUtil.getAttribute(contentElement, NAMESPACE_NCX, "src");
        try {
            result2 = URLDecoder.decode(result2, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result2;
    }

    private static String readNavLabel(Element navpointElement) {
        Element navLabel = DOMUtil.getFirstElementByTagNameNS(navpointElement, NAMESPACE_NCX, "navLabel");
        assert (navLabel != null);
        return DOMUtil.getTextChildrenContent(DOMUtil.getFirstElementByTagNameNS(navLabel, NAMESPACE_NCX, "text"));
    }

    public static void write(EpubWriter epubWriter, EpubBook book, ZipOutputStream resultStream) throws IOException {
        resultStream.putNextEntry(new ZipEntry(book.getSpine().getTocResource().getHref()));
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer(resultStream);
        NCXDocumentV2.write(out, book);
        out.flush();
    }

    public static void write(XmlSerializer xmlSerializer, EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        NCXDocumentV2.write(xmlSerializer, book.getMetadata().getIdentifiers(), book.getTitle(), book.getMetadata().getAuthors(), book.getTableOfContents());
    }

    public static Resource createNCXResource(EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        return NCXDocumentV2.createNCXResource(book.getMetadata().getIdentifiers(), book.getTitle(), book.getMetadata().getAuthors(), book.getTableOfContents());
    }

    public static Resource createNCXResource(List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer(data);
        NCXDocumentV2.write(out, identifiers, title, authors, tableOfContents);
        return new Resource("ncx", data.toByteArray(), DEFAULT_NCX_HREF, MediaTypes.NCX);
    }

    public static void write(XmlSerializer serializer, List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startDocument("UTF-8", false);
        serializer.setPrefix("", NAMESPACE_NCX);
        serializer.startTag(NAMESPACE_NCX, "ncx");
        serializer.attribute("", "version", "2005-1");
        serializer.startTag(NAMESPACE_NCX, "head");
        for (Identifier identifier : identifiers) {
            NCXDocumentV2.writeMetaElement(identifier.getScheme(), identifier.getValue(), serializer);
        }
        NCXDocumentV2.writeMetaElement("generator", "Ag2S EpubLib", serializer);
        NCXDocumentV2.writeMetaElement("depth", String.valueOf(tableOfContents.calculateDepth()), serializer);
        NCXDocumentV2.writeMetaElement("totalPageCount", "0", serializer);
        NCXDocumentV2.writeMetaElement("maxPageNumber", "0", serializer);
        serializer.endTag(NAMESPACE_NCX, "head");
        serializer.startTag(NAMESPACE_NCX, "docTitle");
        serializer.startTag(NAMESPACE_NCX, "text");
        serializer.text(StringUtil.defaultIfNull(title));
        serializer.endTag(NAMESPACE_NCX, "text");
        serializer.endTag(NAMESPACE_NCX, "docTitle");
        for (Author author : authors) {
            serializer.startTag(NAMESPACE_NCX, "docAuthor");
            serializer.startTag(NAMESPACE_NCX, "text");
            serializer.text(author.getLastname() + ", " + author.getFirstname());
            serializer.endTag(NAMESPACE_NCX, "text");
            serializer.endTag(NAMESPACE_NCX, "docAuthor");
        }
        serializer.startTag(NAMESPACE_NCX, "navMap");
        NCXDocumentV2.writeNavPoints(tableOfContents.getTocReferences(), 1, serializer);
        serializer.endTag(NAMESPACE_NCX, "navMap");
        serializer.endTag(NAMESPACE_NCX, "ncx");
        serializer.endDocument();
    }

    private static void writeMetaElement(String dtbName, String content, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag(NAMESPACE_NCX, "meta");
        serializer.attribute("", "name", "dtb:" + dtbName);
        serializer.attribute("", "content", content);
        serializer.endTag(NAMESPACE_NCX, "meta");
    }

    private static int writeNavPoints(List<TOCReference> tocReferences, int playOrder, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        for (TOCReference tocReference : tocReferences) {
            if (tocReference.getResource() == null) {
                playOrder = NCXDocumentV2.writeNavPoints(tocReference.getChildren(), playOrder, serializer);
                continue;
            }
            NCXDocumentV2.writeNavPointStart(tocReference, playOrder, serializer);
            ++playOrder;
            if (!tocReference.getChildren().isEmpty()) {
                playOrder = NCXDocumentV2.writeNavPoints(tocReference.getChildren(), playOrder, serializer);
            }
            NCXDocumentV2.writeNavPointEnd(tocReference, serializer);
        }
        return playOrder;
    }

    private static void writeNavPointStart(TOCReference tocReference, int playOrder, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startTag(NAMESPACE_NCX, "navPoint");
        serializer.attribute("", "id", "navPoint-" + playOrder);
        serializer.attribute("", "playOrder", String.valueOf(playOrder));
        serializer.attribute("", "class", "chapter");
        serializer.startTag(NAMESPACE_NCX, "navLabel");
        serializer.startTag(NAMESPACE_NCX, "text");
        serializer.text(tocReference.getTitle());
        serializer.endTag(NAMESPACE_NCX, "text");
        serializer.endTag(NAMESPACE_NCX, "navLabel");
        serializer.startTag(NAMESPACE_NCX, "content");
        serializer.attribute("", "src", tocReference.getCompleteHref());
        serializer.endTag(NAMESPACE_NCX, "content");
    }

    private static void writeNavPointEnd(TOCReference tocReference, XmlSerializer serializer) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.endTag(NAMESPACE_NCX, "navPoint");
    }

    private static interface NCXAttributeValues {
        public static final String chapter = "chapter";
        public static final String version = "2005-1";
    }

    private static interface NCXAttributes {
        public static final String src = "src";
        public static final String name = "name";
        public static final String content = "content";
        public static final String id = "id";
        public static final String playOrder = "playOrder";
        public static final String clazz = "class";
        public static final String version = "version";
    }

    private static interface NCXTags {
        public static final String ncx = "ncx";
        public static final String meta = "meta";
        public static final String navPoint = "navPoint";
        public static final String navMap = "navMap";
        public static final String navLabel = "navLabel";
        public static final String content = "content";
        public static final String text = "text";
        public static final String docTitle = "docTitle";
        public static final String docAuthor = "docAuthor";
        public static final String head = "head";
    }
}

