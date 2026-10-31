/*
 * Decompiled with CFR 0.152.
 */
package me.ag2s.epublib.epub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import me.ag2s.epublib.domain.EpubResourceProvider;
import me.ag2s.epublib.domain.LazyResource;
import me.ag2s.epublib.domain.LazyResourceProvider;
import me.ag2s.epublib.domain.MediaType;
import me.ag2s.epublib.domain.MediaTypes;
import me.ag2s.epublib.domain.Resource;
import me.ag2s.epublib.domain.Resources;
import me.ag2s.epublib.util.CollectionUtil;
import me.ag2s.epublib.util.ResourceUtil;

public class ResourcesLoader {
    private static final String TAG = ResourcesLoader.class.getName();

    public static Resources loadResources(ZipFile zipFile, String defaultHtmlEncoding, List<MediaType> lazyLoadedTypes) throws IOException {
        EpubResourceProvider resourceProvider = new EpubResourceProvider(zipFile.getName());
        Resources result2 = new Resources();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            Resource resource;
            ZipEntry zipEntry = entries.nextElement();
            if (zipEntry == null || zipEntry.isDirectory()) continue;
            String href = zipEntry.getName();
            if (ResourcesLoader.shouldLoadLazy(href, lazyLoadedTypes)) {
                resource = new LazyResource((LazyResourceProvider)resourceProvider, zipEntry.getSize(), href);
            } else {
                resource = ResourceUtil.createResource(zipEntry, zipFile.getInputStream(zipEntry));
                if (href.endsWith("opf")) {
                    String string = new String(resource.getData()).replace("smlns=\"", "xmlns=\"");
                    resource.setData(string.getBytes());
                }
            }
            if (resource.getMediaType() == MediaTypes.XHTML) {
                resource.setInputEncoding(defaultHtmlEncoding);
            }
            result2.add(resource);
        }
        return result2;
    }

    private static boolean shouldLoadLazy(String href, Collection<MediaType> lazilyLoadedMediaTypes) {
        if (CollectionUtil.isEmpty(lazilyLoadedMediaTypes)) {
            return false;
        }
        MediaType mediaType = MediaTypes.determineMediaType(href);
        return lazilyLoadedMediaTypes.contains(mediaType);
    }

    public static Resources loadResources(ZipInputStream zipInputStream, String defaultHtmlEncoding) throws IOException {
        ZipEntry zipEntry;
        Resources result2 = new Resources();
        do {
            if ((zipEntry = ResourcesLoader.getNextZipEntry(zipInputStream)) == null || zipEntry.isDirectory()) continue;
            String href = zipEntry.getName();
            Resource resource = ResourceUtil.createResource(zipEntry, zipInputStream);
            if (href.endsWith("opf")) {
                String string = new String(resource.getData()).replace("smlns=\"", "xmlns=\"");
                resource.setData(string.getBytes());
            }
            if (resource.getMediaType() == MediaTypes.XHTML) {
                resource.setInputEncoding(defaultHtmlEncoding);
            }
            result2.add(resource);
        } while (zipEntry != null);
        return result2;
    }

    private static ZipEntry getNextZipEntry(ZipInputStream zipInputStream) throws IOException {
        try {
            return zipInputStream.getNextEntry();
        }
        catch (ZipException e) {
            e.printStackTrace();
            try {
                zipInputStream.closeEntry();
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw e;
        }
    }

    public static Resources loadResources(ZipFile zipFile, String defaultHtmlEncoding) throws IOException {
        ArrayList<MediaType> ls = new ArrayList<MediaType>();
        return ResourcesLoader.loadResources(zipFile, defaultHtmlEncoding, ls);
    }
}

