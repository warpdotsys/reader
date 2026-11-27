/*
 * Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
 */

package me.ag2s.epublib.domain;


public enum ManifestItemProperties implements ManifestProperties
{
    COVER_IMAGE("cover-image"),
    MATHML("mathml"),
    NAV("nav"),
    REMOTE_RESOURCES("remote-resources"),
    SCRIPTED("scripted"),
    SVG("svg"),
    SWITCH("switch");

    private final String name;

    private ManifestItemProperties(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}

