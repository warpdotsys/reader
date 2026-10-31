/*
 * Decompiled with CFR 0.152.
 */
package io.legado.app.model.analyzeRule;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class QueryTTF {
    private final ByteArrayReader fontReader;
    private final Header fileHeader = new Header();
    private final List<Directory> directorys = new LinkedList<Directory>();
    private final NameLayout name = new NameLayout();
    private final HeadLayout head = new HeadLayout();
    private final MaxpLayout maxp = new MaxpLayout();
    private final List<Integer> loca = new LinkedList<Integer>();
    private final CmapLayout Cmap = new CmapLayout();
    private final List<GlyfLayout> glyf = new LinkedList<GlyfLayout>();
    private final Pair<Integer, Integer>[] pps = new Pair[]{Pair.of(3, 10), Pair.of(0, 4), Pair.of(3, 1), Pair.of(1, 0), Pair.of(0, 3), Pair.of(0, 1)};
    public final Map<Integer, String> codeToGlyph = new HashMap<Integer, String>();
    public final Map<String, Integer> glyphToCode = new HashMap<String, Integer>();
    private int limitMix = 0;
    private int limitMax = 0;

    public QueryTTF(byte[] buffer) {
        int i;
        this.fontReader = new ByteArrayReader(buffer, 0);
        this.fileHeader.majorVersion = this.fontReader.ReadUInt16();
        this.fileHeader.minorVersion = this.fontReader.ReadUInt16();
        this.fileHeader.numOfTables = this.fontReader.ReadUInt16();
        this.fileHeader.searchRange = this.fontReader.ReadUInt16();
        this.fileHeader.entrySelector = this.fontReader.ReadUInt16();
        this.fileHeader.rangeShift = this.fontReader.ReadUInt16();
        for (int i2 = 0; i2 < this.fileHeader.numOfTables; ++i2) {
            Directory d = new Directory();
            d.tag = this.fontReader.ReadStrings(4, StandardCharsets.US_ASCII);
            d.checkSum = this.fontReader.ReadUInt32();
            d.offset = this.fontReader.ReadUInt32();
            d.length = this.fontReader.ReadUInt32();
            this.directorys.add(d);
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("name")) continue;
            this.fontReader.index = Temp.offset;
            this.name.format = this.fontReader.ReadUInt16();
            this.name.count = this.fontReader.ReadUInt16();
            this.name.stringOffset = this.fontReader.ReadUInt16();
            for (i = 0; i < this.name.count; ++i) {
                NameRecord record = new NameRecord();
                record.platformID = this.fontReader.ReadUInt16();
                record.encodingID = this.fontReader.ReadUInt16();
                record.languageID = this.fontReader.ReadUInt16();
                record.nameID = this.fontReader.ReadUInt16();
                record.length = this.fontReader.ReadUInt16();
                record.offset = this.fontReader.ReadUInt16();
                this.name.records.add(record);
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("head")) continue;
            this.fontReader.index = Temp.offset;
            this.head.majorVersion = this.fontReader.ReadUInt16();
            this.head.minorVersion = this.fontReader.ReadUInt16();
            this.head.fontRevision = this.fontReader.ReadUInt32();
            this.head.checkSumAdjustment = this.fontReader.ReadUInt32();
            this.head.magicNumber = this.fontReader.ReadUInt32();
            this.head.flags = this.fontReader.ReadUInt16();
            this.head.unitsPerEm = this.fontReader.ReadUInt16();
            this.head.created = this.fontReader.ReadUInt64();
            this.head.modified = this.fontReader.ReadUInt64();
            this.head.xMin = this.fontReader.ReadInt16();
            this.head.yMin = this.fontReader.ReadInt16();
            this.head.xMax = this.fontReader.ReadInt16();
            this.head.yMax = this.fontReader.ReadInt16();
            this.head.macStyle = this.fontReader.ReadUInt16();
            this.head.lowestRecPPEM = this.fontReader.ReadUInt16();
            this.head.fontDirectionHint = this.fontReader.ReadInt16();
            this.head.indexToLocFormat = this.fontReader.ReadInt16();
            this.head.glyphDataFormat = this.fontReader.ReadInt16();
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("maxp")) continue;
            this.fontReader.index = Temp.offset;
            this.maxp.majorVersion = this.fontReader.ReadUInt16();
            this.maxp.minorVersion = this.fontReader.ReadUInt16();
            this.maxp.numGlyphs = this.fontReader.ReadUInt16();
            this.maxp.maxPoints = this.fontReader.ReadUInt16();
            this.maxp.maxContours = this.fontReader.ReadUInt16();
            this.maxp.maxCompositePoints = this.fontReader.ReadUInt16();
            this.maxp.maxCompositeContours = this.fontReader.ReadUInt16();
            this.maxp.maxZones = this.fontReader.ReadUInt16();
            this.maxp.maxTwilightPoints = this.fontReader.ReadUInt16();
            this.maxp.maxStorage = this.fontReader.ReadUInt16();
            this.maxp.maxFunctionDefs = this.fontReader.ReadUInt16();
            this.maxp.maxInstructionDefs = this.fontReader.ReadUInt16();
            this.maxp.maxStackElements = this.fontReader.ReadUInt16();
            this.maxp.maxSizeOfInstructions = this.fontReader.ReadUInt16();
            this.maxp.maxComponentElements = this.fontReader.ReadUInt16();
            this.maxp.maxComponentDepth = this.fontReader.ReadUInt16();
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("loca")) continue;
            this.fontReader.index = Temp.offset;
            int offset = this.head.indexToLocFormat == 0 ? 2 : 4;
            for (long i3 = 0L; i3 < (long)Temp.length; i3 += (long)offset) {
                this.loca.add(offset == 2 ? this.fontReader.ReadUInt16() << 1 : this.fontReader.ReadUInt32());
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("cmap")) continue;
            this.fontReader.index = Temp.offset;
            this.Cmap.version = this.fontReader.ReadUInt16();
            this.Cmap.numTables = this.fontReader.ReadUInt16();
            for (i = 0; i < this.Cmap.numTables; ++i) {
                CmapRecord record = new CmapRecord();
                record.platformID = this.fontReader.ReadUInt16();
                record.encodingID = this.fontReader.ReadUInt16();
                record.offset = this.fontReader.ReadUInt32();
                this.Cmap.records.add(record);
            }
            for (i = 0; i < this.Cmap.numTables; ++i) {
                CmapFormat f;
                int fmtOffset = this.Cmap.records.get((int)i).offset;
                int EndIndex = this.fontReader.index = Temp.offset + fmtOffset;
                int format = this.fontReader.ReadUInt16();
                if (this.Cmap.tables.containsKey(fmtOffset)) continue;
                if (format == 0) {
                    f = new CmapFormat();
                    f.format = format;
                    f.length = this.fontReader.ReadUInt16();
                    f.language = this.fontReader.ReadUInt16();
                    f.glyphIdArray = this.fontReader.GetBytes(f.length - 6);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 4) {
                    f = new CmapFormat4();
                    ((CmapFormat4)f).format = format;
                    ((CmapFormat4)f).length = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).language = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).segCountX2 = this.fontReader.ReadUInt16();
                    int segCount = ((CmapFormat4)f).segCountX2 >> 1;
                    ((CmapFormat4)f).searchRange = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).entrySelector = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).rangeShift = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).endCode = this.fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4)f).reservedPad = this.fontReader.ReadUInt16();
                    ((CmapFormat4)f).startCode = this.fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4)f).idDelta = this.fontReader.GetInt16Array(segCount);
                    ((CmapFormat4)f).idRangeOffset = this.fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4)f).glyphIdArray = this.fontReader.GetUInt16Array(EndIndex + ((CmapFormat4)f).length - this.fontReader.index >> 1);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 6) {
                    f = new CmapFormat6();
                    ((CmapFormat6)f).format = format;
                    ((CmapFormat6)f).length = this.fontReader.ReadUInt16();
                    ((CmapFormat6)f).language = this.fontReader.ReadUInt16();
                    ((CmapFormat6)f).firstCode = this.fontReader.ReadUInt16();
                    ((CmapFormat6)f).entryCount = this.fontReader.ReadUInt16();
                    ((CmapFormat6)f).glyphIdArray = this.fontReader.GetUInt16Array(((CmapFormat6)f).entryCount);
                    this.Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format != 12) continue;
                f = new CmapFormat12();
                ((CmapFormat12)f).format = format;
                ((CmapFormat12)f).reserved = this.fontReader.ReadUInt16();
                ((CmapFormat12)f).length = this.fontReader.ReadUInt32();
                ((CmapFormat12)f).language = this.fontReader.ReadUInt32();
                ((CmapFormat12)f).numGroups = this.fontReader.ReadUInt32();
                ((CmapFormat12)f).groups = new ArrayList<Triple<Integer, Integer, Integer>>(((CmapFormat12)f).numGroups);
                for (int n = 0; n < ((CmapFormat12)f).numGroups; ++n) {
                    ((CmapFormat12)f).groups.add(Triple.of(this.fontReader.ReadUInt32(), this.fontReader.ReadUInt32(), this.fontReader.ReadUInt32()));
                }
                this.Cmap.tables.put(fmtOffset, f);
            }
        }
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("glyf")) continue;
            this.fontReader.index = Temp.offset;
            for (i = 0; i < this.maxp.numGlyphs; ++i) {
                short same;
                int n;
                this.fontReader.index = Temp.offset + this.loca.get(i);
                short numberOfContours = this.fontReader.ReadInt16();
                if (numberOfContours <= 0) continue;
                GlyfLayout g = new GlyfLayout();
                g.numberOfContours = numberOfContours;
                g.xMin = this.fontReader.ReadInt16();
                g.yMin = this.fontReader.ReadInt16();
                g.xMax = this.fontReader.ReadInt16();
                g.yMax = this.fontReader.ReadInt16();
                g.endPtsOfContours = this.fontReader.GetUInt16Array(numberOfContours);
                g.instructionLength = this.fontReader.ReadUInt16();
                g.instructions = this.fontReader.GetBytes(g.instructionLength);
                int flagLength = g.endPtsOfContours[g.endPtsOfContours.length - 1] + 1;
                g.flags = new byte[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    g.flags[n] = this.fontReader.GetByte();
                    if ((g.flags[n] & 8) == 0) continue;
                    for (int m = this.fontReader.ReadUInt8(); m > 0; --m) {
                        g.flags[++n] = g.flags[n - 1];
                    }
                }
                g.xCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short)((g.flags[n] & 0x10) != 0 ? 1 : -1);
                    g.xCoordinates[n] = (g.flags[n] & 2) != 0 ? (short)(same * this.fontReader.ReadUInt8()) : (same == 1 ? (short)0 : this.fontReader.ReadInt16());
                }
                g.yCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short)((g.flags[n] & 0x20) != 0 ? 1 : -1);
                    g.yCoordinates[n] = (g.flags[n] & 4) != 0 ? (short)(same * this.fontReader.ReadUInt8()) : (same == 1 ? (short)0 : this.fontReader.ReadInt16());
                }
                this.glyf.add(g);
            }
        }
        for (int key = 0; key < 130000; ++key) {
            int gid;
            if (key == 255) {
                key = 13312;
            }
            if ((gid = this.getGlyfIndex(key)) == 0) continue;
            StringBuilder sb = new StringBuilder();
            for (short b : this.glyf.get((int)gid).xCoordinates) {
                sb.append(b);
            }
            for (short b : this.glyf.get((int)gid).yCoordinates) {
                sb.append(b);
            }
            String val = sb.toString();
            if (this.limitMix == 0) {
                this.limitMix = key;
            }
            this.limitMax = key;
            this.codeToGlyph.put(key, val);
            if (this.glyphToCode.containsKey(val)) continue;
            this.glyphToCode.put(val, key);
        }
    }

    public String getNameById(int nameId) {
        for (Directory Temp : this.directorys) {
            if (!Temp.tag.equals("name")) continue;
            this.fontReader.index = Temp.offset;
            break;
        }
        for (NameRecord record : this.name.records) {
            if (record.nameID != nameId) continue;
            this.fontReader.index += this.name.stringOffset + record.offset;
            return this.fontReader.ReadStrings(record.length, record.platformID == 1 ? StandardCharsets.UTF_8 : StandardCharsets.UTF_16BE);
        }
        return "error";
    }

    private int getGlyfIndex(int code) {
        CmapFormat tab;
        if (code == 0) {
            return 0;
        }
        int fmtKey = 0;
        for (Pair<Integer, Integer> item : this.pps) {
            for (CmapRecord record : this.Cmap.records) {
                if (item.getLeft() != record.platformID || item.getRight() != record.encodingID) continue;
                fmtKey = record.offset;
                break;
            }
            if (fmtKey > 0) break;
        }
        if (fmtKey == 0) {
            return 0;
        }
        int glyfID = 0;
        CmapFormat table = this.Cmap.tables.get(fmtKey);
        assert (table != null);
        int fmt = table.format;
        if (fmt == 0) {
            if (code < table.glyphIdArray.length) {
                glyfID = table.glyphIdArray[code] & 0xFF;
            }
        } else if (fmt == 4) {
            tab = (CmapFormat4)table;
            if (code > tab.endCode[tab.endCode.length - 1]) {
                return 0;
            }
            int start2 = 0;
            int end = tab.endCode.length - 1;
            while (start2 + 1 < end) {
                int middle = (start2 + end) / 2;
                if (tab.endCode[middle] <= code) {
                    start2 = middle;
                    continue;
                }
                end = middle;
            }
            if (tab.endCode[start2] < code) {
                ++start2;
            }
            if (code < tab.startCode[start2]) {
                return 0;
            }
            glyfID = tab.idRangeOffset[start2] != 0 ? tab.glyphIdArray[code - tab.startCode[start2] + (tab.idRangeOffset[start2] >> 1) - (tab.idRangeOffset.length - start2)] : code + tab.idDelta[start2];
            glyfID &= 0xFFFF;
        } else if (fmt == 6) {
            tab = (CmapFormat6)table;
            int index = code - ((CmapFormat6)tab).firstCode;
            glyfID = index < 0 || index >= ((CmapFormat6)tab).glyphIdArray.length ? 0 : ((CmapFormat6)tab).glyphIdArray[index];
        } else if (fmt == 12) {
            tab = (CmapFormat12)table;
            if (code > ((CmapFormat12)tab).groups.get(((CmapFormat12)tab).numGroups - 1).getMiddle()) {
                return 0;
            }
            int start3 = 0;
            int end = ((CmapFormat12)tab).numGroups - 1;
            while (start3 + 1 < end) {
                int middle = (start3 + end) / 2;
                if (((CmapFormat12)tab).groups.get(middle).getLeft() <= code) {
                    start3 = middle;
                    continue;
                }
                end = middle;
            }
            if (((CmapFormat12)tab).groups.get(start3).getLeft() <= code && code <= ((CmapFormat12)tab).groups.get(start3).getMiddle()) {
                glyfID = ((CmapFormat12)tab).groups.get(start3).getRight() + code - ((CmapFormat12)tab).groups.get(start3).getLeft();
            }
        }
        return glyfID;
    }

    public boolean inLimit(char code) {
        return this.limitMix <= code && code < this.limitMax;
    }

    public String getGlyfByCode(int key) {
        return this.codeToGlyph.getOrDefault(key, "");
    }

    public int getCodeByGlyf(String val) {
        return this.glyphToCode.getOrDefault(val, 0);
    }

    private static class ByteArrayReader {
        public int index;
        public byte[] buffer;

        public ByteArrayReader(byte[] buffer, int index) {
            this.buffer = buffer;
            this.index = index;
        }

        public long ReadUIntX(long len) {
            long result2 = 0L;
            for (long i = 0L; i < len; ++i) {
                result2 <<= 8;
                result2 |= (long)(this.buffer[this.index++] & 0xFF);
            }
            return result2;
        }

        public long ReadUInt64() {
            return this.ReadUIntX(8L);
        }

        public int ReadUInt32() {
            return (int)this.ReadUIntX(4L);
        }

        public int ReadUInt16() {
            return (int)this.ReadUIntX(2L);
        }

        public short ReadInt16() {
            return (short)this.ReadUIntX(2L);
        }

        public short ReadUInt8() {
            return (short)this.ReadUIntX(1L);
        }

        public String ReadStrings(int len, Charset charset) {
            byte[] result2 = len > 0 ? new byte[len] : null;
            for (int i = 0; i < len; ++i) {
                result2[i] = this.buffer[this.index++];
            }
            return new String(result2, charset);
        }

        public byte GetByte() {
            return this.buffer[this.index++];
        }

        public byte[] GetBytes(int len) {
            byte[] result2 = len > 0 ? new byte[len] : null;
            for (int i = 0; i < len; ++i) {
                result2[i] = this.buffer[this.index++];
            }
            return result2;
        }

        public int[] GetUInt16Array(int len) {
            int[] result2 = len > 0 ? new int[len] : null;
            for (int i = 0; i < len; ++i) {
                result2[i] = this.ReadUInt16();
            }
            return result2;
        }

        public short[] GetInt16Array(int len) {
            short[] result2 = len > 0 ? new short[len] : null;
            for (int i = 0; i < len; ++i) {
                result2[i] = this.ReadInt16();
            }
            return result2;
        }
    }

    private static class GlyfLayout {
        public short numberOfContours;
        public short xMin;
        public short yMin;
        public short xMax;
        public short yMax;
        public int[] endPtsOfContours;
        public int instructionLength;
        public byte[] instructions;
        public byte[] flags;
        public short[] xCoordinates;
        public short[] yCoordinates;

        private GlyfLayout() {
        }
    }

    private static class CmapFormat12
    extends CmapFormat {
        public int reserved;
        public int length;
        public int language;
        public int numGroups;
        public List<Triple<Integer, Integer, Integer>> groups;

        private CmapFormat12() {
        }
    }

    private static class CmapFormat6
    extends CmapFormat {
        public int firstCode;
        public int entryCount;
        public int[] glyphIdArray;

        private CmapFormat6() {
        }
    }

    private static class CmapFormat4
    extends CmapFormat {
        public int segCountX2;
        public int searchRange;
        public int entrySelector;
        public int rangeShift;
        public int[] endCode;
        public int reservedPad;
        public int[] startCode;
        public short[] idDelta;
        public int[] idRangeOffset;
        public int[] glyphIdArray;

        private CmapFormat4() {
        }
    }

    private static class CmapFormat {
        public int format;
        public int length;
        public int language;
        public byte[] glyphIdArray;

        private CmapFormat() {
        }
    }

    private static class CmapRecord {
        public int platformID;
        public int encodingID;
        public int offset;

        private CmapRecord() {
        }
    }

    private static class CmapLayout {
        public int version;
        public int numTables;
        public List<CmapRecord> records = new LinkedList<CmapRecord>();
        public Map<Integer, CmapFormat> tables = new HashMap<Integer, CmapFormat>();

        private CmapLayout() {
        }
    }

    private static class MaxpLayout {
        public int majorVersion;
        public int minorVersion;
        public int numGlyphs;
        public int maxPoints;
        public int maxContours;
        public int maxCompositePoints;
        public int maxCompositeContours;
        public int maxZones;
        public int maxTwilightPoints;
        public int maxStorage;
        public int maxFunctionDefs;
        public int maxInstructionDefs;
        public int maxStackElements;
        public int maxSizeOfInstructions;
        public int maxComponentElements;
        public int maxComponentDepth;

        private MaxpLayout() {
        }
    }

    private static class HeadLayout {
        public int majorVersion;
        public int minorVersion;
        public int fontRevision;
        public int checkSumAdjustment;
        public int magicNumber;
        public int flags;
        public int unitsPerEm;
        public long created;
        public long modified;
        public short xMin;
        public short yMin;
        public short xMax;
        public short yMax;
        public int macStyle;
        public int lowestRecPPEM;
        public short fontDirectionHint;
        public short indexToLocFormat;
        public short glyphDataFormat;

        private HeadLayout() {
        }
    }

    private static class NameRecord {
        public int platformID;
        public int encodingID;
        public int languageID;
        public int nameID;
        public int length;
        public int offset;

        private NameRecord() {
        }
    }

    private static class NameLayout {
        public int format;
        public int count;
        public int stringOffset;
        public List<NameRecord> records = new LinkedList<NameRecord>();

        private NameLayout() {
        }
    }

    private static class Directory {
        public String tag;
        public int checkSum;
        public int offset;
        public int length;

        private Directory() {
        }
    }

    private static class Header {
        public int majorVersion;
        public int minorVersion;
        public int numOfTables;
        public int searchRange;
        public int entrySelector;
        public int rangeShift;

        private Header() {
        }
    }
}

