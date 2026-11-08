/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.epub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import me.ag2s.epublib.domain.Author;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Identifier;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.TOCReference;
import me.ag2s.epublib.domain.TableOfContents;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

public class NCXDocumentV3 {
    public static final String NAMESPACE_XHTML = "http://www.w3.org/1999/xhtml";
    public static final String NAMESPACE_EPUB = "http://www.idpf.org/2007/ops";
    public static final String LANGUAGE = "en";
    public static final String PREFIX_XHTML = "html";
    public static final String NCX_ITEM_ID = "htmltoc";
    public static final String DEFAULT_NCX_HREF = "toc.xhtml";
    public static final String V3_NCX_PROPERTIES = "nav";
    public static final MediaType V3_NCX_MEDIATYPE = MediaTypes.XHTML;
    private static final String TAG = NCXDocumentV3.class.getName();

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
            if (ncxResource.getHref().endsWith(".ncx")) {
                System.err.println(TAG + " \u8be5epub\u6587\u4ef6\u4e0d\u6807\u51c6\uff0c\u4f7f\u7528\u4e86epub2\u7684\u76ee\u5f55\u6587\u4ef6");
                return NCXDocumentV2.read(book, epubReader);
            }
            System.out.println(TAG + " " + ncxResource.getHref());
            Document ncxDocument = ResourceUtil.getAsDocument(ncxResource);
            System.out.println(TAG + " " + ncxDocument.getNodeName());
            Element navMapElement = (Element)ncxDocument.getElementsByTagName(V3_NCX_PROPERTIES).item(0);
            if (navMapElement == null) {
                System.out.println(TAG + " epub3\u76ee\u5f55\u6587\u4ef6\u672a\u53d1\u73b0nav\u8282\u70b9\uff0c\u5c1d\u8bd5\u4f7f\u7528epub2\u7684\u89c4\u5219\u89e3\u6790");
                return NCXDocumentV2.read(book, epubReader);
            }
            navMapElement = (Element)navMapElement.getElementsByTagName("ol").item(0);
            System.out.println(TAG + " " + navMapElement.getTagName());
            TableOfContents tableOfContents = new TableOfContents(NCXDocumentV3.readTOCReferences(navMapElement.getChildNodes(), book));
            System.out.println(TAG + " " + tableOfContents.toString());
            book.setTableOfContents(tableOfContents);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ncxResource;
    }

    private static List<TOCReference> doToc(Node n, EpubBook book) {
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>();
        if (n == null || n.getNodeType() != 1) {
            return result2;
        }
        Element el = (Element)n;
        NodeList nodeList = el.getElementsByTagName("li");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            result2.add(NCXDocumentV3.readTOCReference((Element)nodeList.item(i), book));
        }
        return result2;
    }

    static List<TOCReference> readTOCReferences(NodeList navpoints, EpubBook book) {
        if (navpoints == null) {
            return new ArrayList<TOCReference>();
        }
        ArrayList<TOCReference> result2 = new ArrayList<TOCReference>(navpoints.getLength());
        for (int i = 0; i < navpoints.getLength(); ++i) {
            Node node = navpoints.item(i);
            if (node == null || node.getNodeType() != 1) continue;
            Element el = (Element)node;
            if (el.getTagName().equals("li")) {
                result2.add(NCXDocumentV3.readTOCReference(el, book));
            }
        }
        return result2;
    }

    static TOCReference readTOCReference(Element navpointElement, EpubBook book) {
        String label = NCXDocumentV3.readNavLabel(navpointElement);
        String tocResourceRoot = StringUtil.substringBeforeLast(book.getSpine().getTocResource().getHref(), '/');
        tocResourceRoot = tocResourceRoot.length() == book.getSpine().getTocResource().getHref().length() ? "" : tocResourceRoot + "/";
        String reference = StringUtil.collapsePathDots(tocResourceRoot + NCXDocumentV3.readNavReference(navpointElement));
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
        List<TOCReference> childTOCReferences = NCXDocumentV3.doToc(navpointElement, book);
        result2.setChildren(childTOCReferences);
        return result2;
    }

    private static String readNavReference(Element navpointElement) {
        Element contentElement = DOMUtil.getFirstElementByTagNameNS(navpointElement, "", "a");
        if (contentElement == null) {
            return null;
        }
        String result2 = DOMUtil.getAttribute(contentElement, "", "href");
        try {
            result2 = URLDecoder.decode(result2, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result2;
    }

    private static String readNavLabel(Element navpointElement) {
        String label;
        Element labelElement = DOMUtil.getFirstElementByTagNameNS(navpointElement, "", "a");
        assert (labelElement != null);
        label = labelElement.getTextContent();
        if (StringUtil.isNotBlank(label)) {
            return label;
        }
        labelElement = DOMUtil.getFirstElementByTagNameNS(navpointElement, "", "span");
        assert (labelElement != null);
        label = labelElement.getTextContent();
        return label;
    }

    public static Resource createNCXResource(EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        return NCXDocumentV3.createNCXResource(book.getMetadata().getIdentifiers(), book.getTitle(), book.getMetadata().getAuthors(), book.getTableOfContents());
    }

    public static Resource createNCXResource(List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        XmlSerializer out = EpubProcessorSupport.createXmlSerializer(data);
        NCXDocumentV3.write(out, identifiers, title, authors, tableOfContents);
        Resource resource = new Resource(NCX_ITEM_ID, data.toByteArray(), DEFAULT_NCX_HREF, V3_NCX_MEDIATYPE);
        resource.setProperties(V3_NCX_PROPERTIES);
        return resource;
    }

    public static void write(XmlSerializer xmlSerializer, EpubBook book) throws IllegalArgumentException, IllegalStateException, IOException {
        NCXDocumentV3.write(xmlSerializer, book.getMetadata().getIdentifiers(), book.getTitle(), book.getMetadata().getAuthors(), book.getTableOfContents());
    }

    public static void write(XmlSerializer serializer, List<Identifier> identifiers, String title, List<Author> authors, TableOfContents tableOfContents) throws IllegalArgumentException, IllegalStateException, IOException {
        serializer.startDocument("UTF-8", false);
        serializer.setPrefix("", NAMESPACE_XHTML);
        serializer.startTag(NAMESPACE_XHTML, PREFIX_XHTML);
        serializer.attribute("", "xmlns:epub", NAMESPACE_EPUB);
        serializer.attribute("", "xml:lang", LANGUAGE);
        serializer.attribute("", "lang", LANGUAGE);
        NCXDocumentV3.writeHead(title, serializer);
        serializer.startTag(NAMESPACE_XHTML, "body");
        serializer.startTag(NAMESPACE_XHTML, "h1");
        serializer.text(title);
        serializer.endTag(NAMESPACE_XHTML, "h1");
        serializer.startTag(NAMESPACE_XHTML, V3_NCX_PROPERTIES);
        serializer.attribute("", "epub:type", "toc");
        serializer.attribute("", "id", "toc");
        serializer.attribute("", "role", "doc-toc");
        serializer.startTag(NAMESPACE_XHTML, "h2");
        serializer.text("\u76ee\u5f55");
        serializer.endTag(NAMESPACE_XHTML, "h2");
        NCXDocumentV3.writeNavPoints(tableOfContents.getTocReferences(), 1, serializer);
        serializer.endTag(NAMESPACE_XHTML, V3_NCX_PROPERTIES);
        serializer.endTag(NAMESPACE_XHTML, "body");
        serializer.endTag(NAMESPACE_XHTML, PREFIX_XHTML);
        serializer.endDocument();
    }

    private static int writeNavPoints(List<TOCReference> tocReferences, int playOrder, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeOlStart(serializer);
        for (TOCReference tocReference : tocReferences) {
            if (tocReference.getResource() == null) {
                playOrder = NCXDocumentV3.writeNavPoints(tocReference.getChildren(), playOrder, serializer);
                continue;
            }
            NCXDocumentV3.writeNavPointStart(tocReference, serializer);
            ++playOrder;
            if (!tocReference.getChildren().isEmpty()) {
                playOrder = NCXDocumentV3.writeNavPoints(tocReference.getChildren(), playOrder, serializer);
            }
            NCXDocumentV3.writeNavPointEnd(tocReference, serializer);
        }
        NCXDocumentV3.writeOlSEnd(serializer);
        return playOrder;
    }

    private static void writeNavPointStart(TOCReference tocReference, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeLiStart(serializer);
        String title = tocReference.getTitle();
        String href = tocReference.getCompleteHref();
        if (StringUtil.isNotBlank(href)) {
            NCXDocumentV3.writeLabel(title, href, serializer);
        } else {
            NCXDocumentV3.writeLabel(title, serializer);
        }
    }

    private static void writeNavPointEnd(TOCReference tocReference, XmlSerializer serializer) throws IOException {
        NCXDocumentV3.writeLiEnd(serializer);
    }

    protected static void writeLabel(String title, String href, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE_XHTML, "a");
        serializer.attribute("", "href", href);
        serializer.text(title);
        serializer.endTag(NAMESPACE_XHTML, "a");
    }

    protected static void writeLabel(String title, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE_XHTML, "span");
        serializer.text(title);
        serializer.endTag(NAMESPACE_XHTML, "span");
    }

    private static void writeLiStart(XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE_XHTML, "li");
        System.out.println(TAG + " writeLiStart");
    }

    private static void writeLiEnd(XmlSerializer serializer) throws IOException {
        serializer.endTag(NAMESPACE_XHTML, "li");
        System.out.println(TAG + " writeLiEND");
    }

    private static void writeOlStart(XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE_XHTML, "ol");
        System.out.println(TAG + " writeOlStart");
    }

    private static void writeOlSEnd(XmlSerializer serializer) throws IOException {
        serializer.endTag(NAMESPACE_XHTML, "ol");
        System.out.println(TAG + " writeOlEnd");
    }

    private static void writeHead(String title, XmlSerializer serializer) throws IOException {
        serializer.startTag(NAMESPACE_XHTML, "head");
        serializer.startTag(NAMESPACE_XHTML, "title");
        serializer.text(StringUtil.defaultIfNull(title));
        serializer.endTag(NAMESPACE_XHTML, "title");
        serializer.startTag(NAMESPACE_XHTML, "link");
        serializer.attribute("", "rel", "stylesheet");
        serializer.attribute("", "type", "text/css");
        serializer.attribute("", "href", "css/style.css");
        serializer.endTag(NAMESPACE_XHTML, "link");
        serializer.startTag(NAMESPACE_XHTML, "meta");
        serializer.attribute("", "http-equiv", "Content-Type");
        serializer.attribute("", "content", "text/html; charset=utf-8");
        serializer.endTag(NAMESPACE_XHTML, "meta");
        serializer.endTag(NAMESPACE_XHTML, "head");
    }

    private static interface XHTMLAttributeValues {
        public static final String Content_Type = "Content-Type";
        public static final String HTML_UTF8 = "text/html; charset=utf-8";
        public static final String lang = "en";
        public static final String epub_type = "toc";
        public static final String role_toc = "doc-toc";
    }

    private static interface XHTMLAttributes {
        public static final String xmlns = "xmlns";
        public static final String xmlns_epub = "xmlns:epub";
        public static final String lang = "lang";
        public static final String xml_lang = "xml:lang";
        public static final String rel = "rel";
        public static final String type = "type";
        public static final String epub_type = "epub:type";
        public static final String id = "id";
        public static final String role = "role";
        public static final String href = "href";
        public static final String http_equiv = "http-equiv";
        public static final String content = "content";
    }

    private static interface XHTMLTgs {
        public static final String html = "html";
        public static final String head = "head";
        public static final String title = "title";
        public static final String meta = "meta";
        public static final String link = "link";
        public static final String body = "body";
        public static final String h1 = "h1";
        public static final String h2 = "h2";
        public static final String nav = "nav";
        public static final String ol = "ol";
        public static final String li = "li";
        public static final String a = "a";
        public static final String span = "span";
    }
}

