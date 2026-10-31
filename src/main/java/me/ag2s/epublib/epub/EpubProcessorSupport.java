/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.epub;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class EpubProcessorSupport {
    private static final String TAG = EpubProcessorSupport.class.getName();
    protected static DocumentBuilderFactory documentBuilderFactory;

    private static void init() {
        documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setValidating(false);
    }

    public static XmlSerializer createXmlSerializer(OutputStream out) throws UnsupportedEncodingException {
        return EpubProcessorSupport.createXmlSerializer(new OutputStreamWriter(out, "UTF-8"));
    }

    public static XmlSerializer createXmlSerializer(Writer out) {
        XmlSerializer result2 = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setValidating(true);
            result2 = factory.newSerializer();
            result2.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            result2.setOutput(out);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result2;
    }

    public static EntityResolver getEntityResolver() {
        return new EntityResolverImpl();
    }

    public DocumentBuilderFactory getDocumentBuilderFactory() {
        return documentBuilderFactory;
    }

    public static DocumentBuilder createDocumentBuilder() {
        DocumentBuilder result2 = null;
        try {
            result2 = documentBuilderFactory.newDocumentBuilder();
            result2.setEntityResolver(EpubProcessorSupport.getEntityResolver());
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return result2;
    }

    static {
        EpubProcessorSupport.init();
    }

    static class EntityResolverImpl
    implements EntityResolver {
        private String previousLocation;

        EntityResolverImpl() {
        }

        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws IOException {
            String resourcePath;
            if (systemId.startsWith("http:")) {
                URL url2 = new URL(systemId);
                resourcePath = "dtd/" + url2.getHost() + url2.getPath();
                this.previousLocation = resourcePath.substring(0, resourcePath.lastIndexOf(47));
            } else {
                resourcePath = this.previousLocation + systemId.substring(systemId.lastIndexOf(47));
            }
            if (Objects.requireNonNull(this.getClass().getClassLoader()).getResource(resourcePath) == null) {
                throw new RuntimeException("remote resource is not cached : [" + systemId + "] cannot continue");
            }
            InputStream in = Objects.requireNonNull(EpubProcessorSupport.class.getClassLoader()).getResourceAsStream(resourcePath);
            return new InputSource(in);
        }
    }
}

