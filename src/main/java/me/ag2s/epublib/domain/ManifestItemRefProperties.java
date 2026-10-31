/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.domain;


public enum ManifestItemRefProperties implements ManifestProperties
{
    PAGE_SPREAD_LEFT("page-spread-left"),
    PAGE_SPREAD_RIGHT("page-spread-right");

    private final String name;

    private ManifestItemRefProperties(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

