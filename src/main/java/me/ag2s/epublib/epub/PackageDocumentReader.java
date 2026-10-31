/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import me.ag2s.epublib.domain.EpubBook;
import me.ag2s.epublib.domain.Guide;
import me.ag2s.epublib.domain.GuideReference;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.domain.Spine;
import me.ag2s.epublib.domain.SpineReference;
import me.ag2s.epublib.epub.DOMUtil;
import me.ag2s.epublib.epub.EpubReader;
import me.ag2s.epublib.epub.PackageDocumentBase;
import me.ag2s.epublib.epub.PackageDocumentMetadataReader;
import me.ag2s.epublib.util.ResourceUtil;
import me.ag2s.epublib.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PackageDocumentReader
extends PackageDocumentBase {
    private static final String TAG = PackageDocumentReader.class.getName();
    private static final String[] POSSIBLE_NCX_ITEM_IDS = new String[]{"toc", "ncx", "ncxtoc", "htmltoc"};

    public static void read(Resource packageResource, EpubReader epubReader, EpubBook book, Resources resources) throws SAXException, IOException {
        Document packageDocument = ResourceUtil.getAsDocument(packageResource);
        String packageHref = packageResource.getHref();
        resources = PackageDocumentReader.fixHrefs(packageHref, resources);
        PackageDocumentReader.readGuide(packageDocument, epubReader, book, resources);
        HashMap<String, String> idMapping = new HashMap<String, String>();
        String version = DOMUtil.getAttribute(packageDocument.getDocumentElement(), "", "version");
        resources = PackageDocumentReader.readManifest(packageDocument, packageHref, epubReader, resources, idMapping);
        book.setResources(resources);
        book.setVersion(version);
        PackageDocumentReader.readCover(packageDocument, book);
        book.setMetadata(PackageDocumentMetadataReader.readMetadata(packageDocument));
        book.setSpine(PackageDocumentReader.readSpine(packageDocument, book.getResources(), idMapping));
        if (book.getCoverPage() == null && book.getSpine().size() > 0) {
            book.setCoverPage(book.getSpine().getResource(0));
        }
    }

    private static Resources readManifest(Document packageDocument, String packageHref, EpubReader epubReader, Resources resources, Map<String, String> idMapping) {
        Element manifestElement = DOMUtil.getFirstElementByTagNameNS(packageDocument.getDocumentElement(), "http://www.idpf.org/2007/opf", "manifest");
        Resources result2 = new Resources();
        if (manifestElement == null) {
            System.err.println(TAG + " Package does not contain element " + "manifest");
            return result2;
        }
        NodeList itemElements = manifestElement.getElementsByTagNameNS("http://www.idpf.org/2007/opf", "item");
        for (int i = 0; i < itemElements.getLength(); ++i) {
            Element itemElement = (Element)itemElements.item(i);
            String id = DOMUtil.getAttribute(itemElement, "http://www.idpf.org/2007/opf", "id");
            String href = DOMUtil.getAttribute(itemElement, "http://www.idpf.org/2007/opf", "href");
            try {
                href = URLDecoder.decode(href, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String mediaTypeName = DOMUtil.getAttribute(itemElement, "http://www.idpf.org/2007/opf", "media-type");
            Resource resource = resources.remove(href);
            if (resource == null) {
                System.err.println(TAG + " resource with href '" + href + "' not found");
                continue;
            }
            resource.setId(id);
            String properties = DOMUtil.getAttribute(itemElement, "http://www.idpf.org/2007/opf", "properties");
            resource.setProperties(properties);
            MediaType mediaType = MediaTypes.getMediaTypeByName(mediaTypeName);
            if (mediaType != null) {
                resource.setMediaType(mediaType);
            }
            result2.add(resource);
            idMapping.put(id, resource.getId());
        }
        return result2;
    }

    private static void readGuide(Document packageDocument, EpubReader epubReader, EpubBook book, Resources resources) {
        Element guideElement = DOMUtil.getFirstElementByTagNameNS(packageDocument.getDocumentElement(), "http://www.idpf.org/2007/opf", "guide");
        if (guideElement == null) {
            return;
        }
        Guide guide = book.getGuide();
        NodeList guideReferences = guideElement.getElementsByTagNameNS("http://www.idpf.org/2007/opf", "reference");
        for (int i = 0; i < guideReferences.getLength(); ++i) {
            Element referenceElement = (Element)guideReferences.item(i);
            String resourceHref = DOMUtil.getAttribute(referenceElement, "http://www.idpf.org/2007/opf", "href");
            if (StringUtil.isBlank(resourceHref)) continue;
            Resource resource = resources.getByHref(StringUtil.substringBefore(resourceHref, '#'));
            if (resource == null) {
                System.err.println(TAG + " Guide is referencing resource with href " + resourceHref + " which could not be found");
                continue;
            }
            String type = DOMUtil.getAttribute(referenceElement, "http://www.idpf.org/2007/opf", "type");
            if (StringUtil.isBlank(type)) {
                System.err.println(TAG + " Guide is referencing resource with href " + resourceHref + " which is missing the 'type' attribute");
                continue;
            }
            String title = DOMUtil.getAttribute(referenceElement, "http://www.idpf.org/2007/opf", "title");
            if ("cover".equalsIgnoreCase(type)) continue;
            GuideReference reference = new GuideReference(resource, type, title, StringUtil.substringAfter(resourceHref, '#'));
            guide.addReference(reference);
        }
    }

    static Resources fixHrefs(String packageHref, Resources resourcesByHref) {
        int lastSlashPos = packageHref.lastIndexOf(47);
        if (lastSlashPos < 0) {
            return resourcesByHref;
        }
        Resources result2 = new Resources();
        for (Resource resource : resourcesByHref.getAll()) {
            if (StringUtil.isNotBlank(resource.getHref()) && resource.getHref().length() > lastSlashPos) {
                resource.setHref(resource.getHref().substring(lastSlashPos + 1));
            }
            result2.add(resource);
        }
        return result2;
    }

    private static Spine readSpine(Document packageDocument, Resources resources, Map<String, String> idMapping) {
        Element spineElement = DOMUtil.getFirstElementByTagNameNS(packageDocument.getDocumentElement(), "http://www.idpf.org/2007/opf", "spine");
        if (spineElement == null) {
            System.err.println(TAG + " Element " + "spine" + " not found in package document, generating one automatically");
            return PackageDocumentReader.generateSpineFromResources(resources);
        }
        Spine result2 = new Spine();
        String tocResourceId = DOMUtil.getAttribute(spineElement, "http://www.idpf.org/2007/opf", "toc");
        System.out.println(TAG + " " + tocResourceId);
        result2.setTocResource(PackageDocumentReader.findTableOfContentsResource(tocResourceId, resources));
        NodeList spineNodes = DOMUtil.getElementsByTagNameNS(packageDocument, "http://www.idpf.org/2007/opf", "itemref");
        if (spineNodes == null) {
            System.err.println(TAG + " spineNodes is null");
            return result2;
        }
        ArrayList<SpineReference> spineReferences = new ArrayList<SpineReference>(spineNodes.getLength());
        for (int i = 0; i < spineNodes.getLength(); ++i) {
            Resource resource;
            Element spineItem = (Element)spineNodes.item(i);
            String itemref = DOMUtil.getAttribute(spineItem, "http://www.idpf.org/2007/opf", "idref");
            if (StringUtil.isBlank(itemref)) {
                System.err.println(TAG + " itemref with missing or empty idref");
                continue;
            }
            String id = idMapping.get(itemref);
            if (id == null) {
                id = itemref;
            }
            if ((resource = resources.getByIdOrHref(id)) == null) {
                System.err.println(TAG + " resource with id '" + id + "' not found");
                continue;
            }
            SpineReference spineReference = new SpineReference(resource);
            if ("no".equalsIgnoreCase(DOMUtil.getAttribute(spineItem, "http://www.idpf.org/2007/opf", "linear"))) {
                spineReference.setLinear(false);
            }
            spineReferences.add(spineReference);
        }
        result2.setSpineReferences(spineReferences);
        return result2;
    }

    private static Spine generateSpineFromResources(Resources resources) {
        Spine result2 = new Spine();
        ArrayList<String> resourceHrefs = new ArrayList<String>(resources.getAllHrefs());
        Collections.sort(resourceHrefs, String.CASE_INSENSITIVE_ORDER);
        for (String resourceHref : resourceHrefs) {
            Resource resource = resources.getByHref(resourceHref);
            if (resource.getMediaType() == MediaTypes.NCX) {
                result2.setTocResource(resource);
                continue;
            }
            if (resource.getMediaType() != MediaTypes.XHTML) continue;
            result2.addSpineReference(new SpineReference(resource));
        }
        return result2;
    }

    static Resource findTableOfContentsResource(String tocResourceId, Resources resources) {
        Resource tocResource = resources.getByProperties("nav");
        if (tocResource != null) {
            return tocResource;
        }
        if (StringUtil.isNotBlank(tocResourceId)) {
            tocResource = resources.getByIdOrHref(tocResourceId);
        }
        if (tocResource != null) {
            return tocResource;
        }
        tocResource = resources.findFirstResourceByMediaType(MediaTypes.NCX);
        if (tocResource == null) {
            String possibleNcxItemId;
            String[] stringArray = POSSIBLE_NCX_ITEM_IDS;
            int n = stringArray.length;
            for (int i = 0; i < n && (tocResource = resources.getByIdOrHref(possibleNcxItemId = stringArray[i])) == null && (tocResource = resources.getByIdOrHref(possibleNcxItemId.toUpperCase())) == null; ++i) {
            }
        }
        if (tocResource == null) {
            System.err.println(TAG + " Could not find table of contents resource. Tried resource with id '" + tocResourceId + "', " + "toc" + ", " + "toc".toUpperCase() + " and any NCX resource.");
        }
        return tocResource;
    }

    static Set<String> findCoverHrefs(Document packageDocument) {
        String coverHref;
        HashSet<String> result2 = new HashSet<String>();
        String coverResourceId = DOMUtil.getFindAttributeValue(packageDocument, "http://www.idpf.org/2007/opf", "meta", "name", "cover", "content");
        if (StringUtil.isNotBlank(coverResourceId)) {
            coverHref = DOMUtil.getFindAttributeValue(packageDocument, "http://www.idpf.org/2007/opf", "item", "id", coverResourceId, "href");
            if (StringUtil.isNotBlank(coverHref)) {
                result2.add(coverHref);
            } else {
                result2.add(coverResourceId);
            }
        }
        if (StringUtil.isNotBlank(coverHref = DOMUtil.getFindAttributeValue(packageDocument, "http://www.idpf.org/2007/opf", "reference", "type", "cover", "href"))) {
            result2.add(coverHref);
        }
        return result2;
    }

    private static void readCover(Document packageDocument, EpubBook book) {
        Set<String> coverHrefs = PackageDocumentReader.findCoverHrefs(packageDocument);
        for (String coverHref : coverHrefs) {
            Resource resource = book.getResources().getByHref(coverHref);
            if (resource == null) {
                System.err.println(TAG + " Cover resource " + coverHref + " not found");
                continue;
            }
            if (resource.getMediaType() == MediaTypes.XHTML) {
                book.setCoverPage(resource);
                continue;
            }
            if (!MediaTypes.isBitmapImage(resource.getMediaType())) continue;
            book.setCoverImage(resource);
        }
    }
}

