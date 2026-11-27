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
    private final List<Directory> directorys = new LinkedList<>();
    private final NameLayout name = new NameLayout();
    private final HeadLayout head = new HeadLayout();
    private final MaxpLayout maxp = new MaxpLayout();
    private final List<Integer> loca = new LinkedList<>();
    private final CmapLayout Cmap = new CmapLayout();
    private final List<GlyfLayout> glyf = new LinkedList<>();
    @SuppressWarnings("unchecked")
    private final Pair<Integer, Integer>[] pps = new Pair[]{
            Pair.of(3, 10), Pair.of(0, 4), Pair.of(3, 1), Pair.of(1, 0), Pair.of(0, 3), Pair.of(0, 1)
    };
    public final Map<Integer, String> codeToGlyph = new HashMap<>();
    public final Map<String, Integer> glyphToCode = new HashMap<>();
    private int limitMix = 0;
    private int limitMax = 0;

    public QueryTTF(byte[] buffer) {
        fontReader = new ByteArrayReader(buffer, 0);
        fileHeader.majorVersion = fontReader.ReadUInt16();
        fileHeader.minorVersion = fontReader.ReadUInt16();
        fileHeader.numOfTables = fontReader.ReadUInt16();
        fileHeader.searchRange = fontReader.ReadUInt16();
        fileHeader.entrySelector = fontReader.ReadUInt16();
        fileHeader.rangeShift = fontReader.ReadUInt16();
        for (int i2 = 0; i2 < fileHeader.numOfTables; ++i2) {
            Directory d = new Directory();
            d.tag = fontReader.ReadStrings(4, StandardCharsets.US_ASCII);
            d.checkSum = fontReader.ReadUInt32();
            d.offset = fontReader.ReadUInt32();
            d.length = fontReader.ReadUInt32();
            directorys.add(d);
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("name")) continue;
            fontReader.index = Temp.offset;
            name.format = fontReader.ReadUInt16();
            name.count = fontReader.ReadUInt16();
            name.stringOffset = fontReader.ReadUInt16();
            for (int i = 0; i < name.count; ++i) {
                NameRecord record = new NameRecord();
                record.platformID = fontReader.ReadUInt16();
                record.encodingID = fontReader.ReadUInt16();
                record.languageID = fontReader.ReadUInt16();
                record.nameID = fontReader.ReadUInt16();
                record.length = fontReader.ReadUInt16();
                record.offset = fontReader.ReadUInt16();
                name.records.add(record);
            }
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("head")) continue;
            fontReader.index = Temp.offset;
            head.majorVersion = fontReader.ReadUInt16();
            head.minorVersion = fontReader.ReadUInt16();
            head.fontRevision = fontReader.ReadUInt32();
            head.checkSumAdjustment = fontReader.ReadUInt32();
            head.magicNumber = fontReader.ReadUInt32();
            head.flags = fontReader.ReadUInt16();
            head.unitsPerEm = fontReader.ReadUInt16();
            head.created = fontReader.ReadUInt64();
            head.modified = fontReader.ReadUInt64();
            head.xMin = fontReader.ReadInt16();
            head.yMin = fontReader.ReadInt16();
            head.xMax = fontReader.ReadInt16();
            head.yMax = fontReader.ReadInt16();
            head.macStyle = fontReader.ReadUInt16();
            head.lowestRecPPEM = fontReader.ReadUInt16();
            head.fontDirectionHint = fontReader.ReadInt16();
            head.indexToLocFormat = fontReader.ReadInt16();
            head.glyphDataFormat = fontReader.ReadInt16();
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("maxp")) continue;
            fontReader.index = Temp.offset;
            maxp.majorVersion = fontReader.ReadUInt16();
            maxp.minorVersion = fontReader.ReadUInt16();
            maxp.numGlyphs = fontReader.ReadUInt16();
            maxp.maxPoints = fontReader.ReadUInt16();
            maxp.maxContours = fontReader.ReadUInt16();
            maxp.maxCompositePoints = fontReader.ReadUInt16();
            maxp.maxCompositeContours = fontReader.ReadUInt16();
            maxp.maxZones = fontReader.ReadUInt16();
            maxp.maxTwilightPoints = fontReader.ReadUInt16();
            maxp.maxStorage = fontReader.ReadUInt16();
            maxp.maxFunctionDefs = fontReader.ReadUInt16();
            maxp.maxInstructionDefs = fontReader.ReadUInt16();
            maxp.maxStackElements = fontReader.ReadUInt16();
            maxp.maxSizeOfInstructions = fontReader.ReadUInt16();
            maxp.maxComponentElements = fontReader.ReadUInt16();
            maxp.maxComponentDepth = fontReader.ReadUInt16();
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("loca")) continue;
            fontReader.index = Temp.offset;
            int offset = head.indexToLocFormat == 0 ? 2 : 4;
            for (long i3 = 0L; i3 < (long) Temp.length; i3 += (long) offset) {
                loca.add(offset == 2 ? fontReader.ReadUInt16() << 1 : fontReader.ReadUInt32());
            }
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("cmap")) continue;
            fontReader.index = Temp.offset;
            Cmap.version = fontReader.ReadUInt16();
            Cmap.numTables = fontReader.ReadUInt16();
            for (int i = 0; i < Cmap.numTables; ++i) {
                CmapRecord record = new CmapRecord();
                record.platformID = fontReader.ReadUInt16();
                record.encodingID = fontReader.ReadUInt16();
                record.offset = fontReader.ReadUInt32();
                Cmap.records.add(record);
            }
            for (int i = 0; i < Cmap.numTables; ++i) {
                CmapFormat f;
                int fmtOffset = Cmap.records.get(i).offset;
                int EndIndex = fontReader.index = Temp.offset + fmtOffset;
                int format = fontReader.ReadUInt16();
                if (Cmap.tables.containsKey(fmtOffset)) continue;
                if (format == 0) {
                    f = new CmapFormat();
                    f.format = format;
                    f.length = fontReader.ReadUInt16();
                    f.language = fontReader.ReadUInt16();
                    f.glyphIdArray = fontReader.GetBytes(f.length - 6);
                    Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 4) {
                    f = new CmapFormat4();
                    ((CmapFormat4) f).format = format;
                    ((CmapFormat4) f).length = fontReader.ReadUInt16();
                    ((CmapFormat4) f).language = fontReader.ReadUInt16();
                    ((CmapFormat4) f).segCountX2 = fontReader.ReadUInt16();
                    int segCount = ((CmapFormat4) f).segCountX2 >> 1;
                    ((CmapFormat4) f).searchRange = fontReader.ReadUInt16();
                    ((CmapFormat4) f).entrySelector = fontReader.ReadUInt16();
                    ((CmapFormat4) f).rangeShift = fontReader.ReadUInt16();
                    ((CmapFormat4) f).endCode = fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4) f).reservedPad = fontReader.ReadUInt16();
                    ((CmapFormat4) f).startCode = fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4) f).idDelta = fontReader.GetInt16Array(segCount);
                    ((CmapFormat4) f).idRangeOffset = fontReader.GetUInt16Array(segCount);
                    ((CmapFormat4) f).glyphIdArray = fontReader.GetUInt16Array(
                            EndIndex + ((CmapFormat4) f).length - fontReader.index >> 1);
                    Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format == 6) {
                    f = new CmapFormat6();
                    ((CmapFormat6) f).format = format;
                    ((CmapFormat6) f).length = fontReader.ReadUInt16();
                    ((CmapFormat6) f).language = fontReader.ReadUInt16();
                    ((CmapFormat6) f).firstCode = fontReader.ReadUInt16();
                    ((CmapFormat6) f).entryCount = fontReader.ReadUInt16();
                    ((CmapFormat6) f).glyphIdArray = fontReader.GetUInt16Array(((CmapFormat6) f).entryCount);
                    Cmap.tables.put(fmtOffset, f);
                    continue;
                }
                if (format != 12) continue;
                f = new CmapFormat12();
                ((CmapFormat12) f).format = format;
                ((CmapFormat12) f).reserved = fontReader.ReadUInt16();
                ((CmapFormat12) f).length = fontReader.ReadUInt32();
                ((CmapFormat12) f).language = fontReader.ReadUInt32();
                ((CmapFormat12) f).numGroups = fontReader.ReadUInt32();
                ((CmapFormat12) f).groups = new ArrayList<Triple<Integer, Integer, Integer>>(((CmapFormat12) f).numGroups);
                for (int n = 0; n < ((CmapFormat12) f).numGroups; ++n) {
                    ((CmapFormat12) f).groups.add(Triple.of(
                            fontReader.ReadUInt32(), fontReader.ReadUInt32(), fontReader.ReadUInt32()));
                }
                Cmap.tables.put(fmtOffset, f);
            }
        }
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("glyf")) continue;
            fontReader.index = Temp.offset;
            for (int i = 0; i < maxp.numGlyphs; ++i) {
                short same;
                int n;
                fontReader.index = Temp.offset + loca.get(i);
                short numberOfContours = fontReader.ReadInt16();
                if (numberOfContours <= 0) continue;
                GlyfLayout g = new GlyfLayout();
                g.numberOfContours = numberOfContours;
                g.xMin = fontReader.ReadInt16();
                g.yMin = fontReader.ReadInt16();
                g.xMax = fontReader.ReadInt16();
                g.yMax = fontReader.ReadInt16();
                g.endPtsOfContours = fontReader.GetUInt16Array(numberOfContours);
                g.instructionLength = fontReader.ReadUInt16();
                g.instructions = fontReader.GetBytes(g.instructionLength);
                int flagLength = g.endPtsOfContours[g.endPtsOfContours.length - 1] + 1;
                g.flags = new byte[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    g.flags[n] = fontReader.GetByte();
                    if ((g.flags[n] & 8) == 0) continue;
                    for (int m = fontReader.ReadUInt8(); m > 0; --m) {
                        g.flags[++n] = g.flags[n - 1];
                    }
                }
                g.xCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short) ((g.flags[n] & 0x10) != 0 ? 1 : -1);
                    g.xCoordinates[n] = (g.flags[n] & 2) != 0
                            ? (short) (same * fontReader.ReadUInt8())
                            : (same == 1 ? (short) 0 : fontReader.ReadInt16());
                }
                g.yCoordinates = new short[flagLength];
                for (n = 0; n < flagLength; ++n) {
                    same = (short) ((g.flags[n] & 0x20) != 0 ? 1 : -1);
                    g.yCoordinates[n] = (g.flags[n] & 4) != 0
                            ? (short) (same * fontReader.ReadUInt8())
                            : (same == 1 ? (short) 0 : fontReader.ReadInt16());
                }
                glyf.add(g);
            }
        }
        for (int key = 0; key < 130000; ++key) {
            int gid;
            if (key == 255) {
                key = 13312;
            }
            if ((gid = getGlyfIndex(key)) == 0) continue;
            StringBuilder sb = new StringBuilder();
            for (short b : glyf.get(gid).xCoordinates) {
                sb.append(b);
            }
            for (short b : glyf.get(gid).yCoordinates) {
                sb.append(b);
            }
            String val = sb.toString();
            if (limitMix == 0) {
                limitMix = key;
            }
            limitMax = key;
            codeToGlyph.put(key, val);
            if (glyphToCode.containsKey(val)) continue;
            glyphToCode.put(val, key);
        }
    }

    public String getNameById(int nameId) {
        for (Directory Temp : directorys) {
            if (!Temp.tag.equals("name")) continue;
            fontReader.index = Temp.offset;
            break;
        }
        for (NameRecord record : name.records) {
            if (record.nameID != nameId) continue;
            fontReader.index += name.stringOffset + record.offset;
            return fontReader.ReadStrings(record.length,
                    record.platformID == 1 ? StandardCharsets.UTF_8 : StandardCharsets.UTF_16BE);
        }
        return "error";
    }

    private int getGlyfIndex(int code) {
        if (code == 0) {
            return 0;
        }
        int fmtKey = 0;
        for (Pair<Integer, Integer> item : pps) {
            for (CmapRecord record : Cmap.records) {
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
        CmapFormat table = Cmap.tables.get(fmtKey);
        assert (table != null);
        int fmt = table.format;
        if (fmt == 0) {
            if (code < table.glyphIdArray.length) {
                glyfID = table.glyphIdArray[code] & 0xFF;
            }
        } else if (fmt == 4) {
            CmapFormat4 tab = (CmapFormat4) table;
            if (code > tab.endCode[tab.endCode.length - 1]) {
                return 0;
            }
            int start = 0;
            int end = tab.endCode.length - 1;
            while (start + 1 < end) {
                int middle = (start + end) / 2;
                if (tab.endCode[middle] <= code) {
                    start = middle;
                    continue;
                }
                end = middle;
            }
            if (tab.endCode[start] < code) {
                ++start;
            }
            if (code < tab.startCode[start]) {
                return 0;
            }
            glyfID = tab.idRangeOffset[start] != 0
                    ? tab.glyphIdArray[code - tab.startCode[start] + (tab.idRangeOffset[start] >> 1) - (tab.idRangeOffset.length - start)]
                    : code + tab.idDelta[start];
            glyfID &= 0xFFFF;
        } else if (fmt == 6) {
            CmapFormat6 tab = (CmapFormat6) table;
            int index = code - tab.firstCode;
            glyfID = index < 0 || index >= tab.glyphIdArray.length
                    ? 0 : tab.glyphIdArray[index];
        } else if (fmt == 12) {
            CmapFormat12 tab = (CmapFormat12) table;
            if (code > tab.groups.get(tab.numGroups - 1).getMiddle()) {
                return 0;
            }
            int start = 0;
            int end = tab.numGroups - 1;
            while (start + 1 < end) {
                int middle = (start + end) / 2;
                if (tab.groups.get(middle).getLeft() <= code) {
                    start = middle;
                    continue;
                }
                end = middle;
            }
            if (tab.groups.get(start).getLeft() <= code
                    && code <= tab.groups.get(start).getMiddle()) {
                glyfID = tab.groups.get(start).getRight()
                        + code - tab.groups.get(start).getLeft();
            }
        }
        return glyfID;
    }

    public boolean inLimit(char code) {
        return limitMix <= code && code < limitMax;
    }

    public String getGlyfByCode(int key) {
        return codeToGlyph.getOrDefault(key, "");
    }

    public int getCodeByGlyf(String val) {
        return glyphToCode.getOrDefault(val, 0);
    }

    private static class ByteArrayReader {
        public int index;
        public byte[] buffer;

        public ByteArrayReader(byte[] buffer, int index) {
            this.buffer = buffer;
            this.index = index;
        }

        public long ReadUIntX(long len) {
            long result = 0L;
            for (long i = 0L; i < len; ++i) {
                result <<= 8;
                result |= (long) (buffer[index++] & 0xFF);
            }
            return result;
        }

        public long ReadUInt64() {
            return ReadUIntX(8L);
        }

        public int ReadUInt32() {
            return (int) ReadUIntX(4L);
        }

        public int ReadUInt16() {
            return (int) ReadUIntX(2L);
        }

        public short ReadInt16() {
            return (short) ReadUIntX(2L);
        }

        public short ReadUInt8() {
            return (short) ReadUIntX(1L);
        }

        public String ReadStrings(int len, Charset charset) {
            byte[] result = len > 0 ? new byte[len] : null;
            for (int i = 0; i < len; ++i) {
                result[i] = buffer[index++];
            }
            return new String(result, charset);
        }

        public byte GetByte() {
            return buffer[index++];
        }

        public byte[] GetBytes(int len) {
            byte[] result = len > 0 ? new byte[len] : null;
            for (int i = 0; i < len; ++i) {
                result[i] = buffer[index++];
            }
            return result;
        }

        public int[] GetUInt16Array(int len) {
            int[] result = len > 0 ? new int[len] : null;
            for (int i = 0; i < len; ++i) {
                result[i] = ReadUInt16();
            }
            return result;
        }

        public short[] GetInt16Array(int len) {
            short[] result = len > 0 ? new short[len] : null;
            for (int i = 0; i < len; ++i) {
                result[i] = ReadInt16();
            }
            return result;
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

    private static class CmapFormat12 extends CmapFormat {
        public int reserved;
        public int length;
        public int language;
        public int numGroups;
        public List<Triple<Integer, Integer, Integer>> groups;

        private CmapFormat12() {
        }
    }

    private static class CmapFormat6 extends CmapFormat {
        public int firstCode;
        public int entryCount;
        public int[] glyphIdArray;

        private CmapFormat6() {
        }
    }

    private static class CmapFormat4 extends CmapFormat {
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
        public List<CmapRecord> records = new LinkedList<>();
        public Map<Integer, CmapFormat> tables = new HashMap<>();

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
        public List<NameRecord> records = new LinkedList<>();

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
