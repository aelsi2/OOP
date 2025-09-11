package ru.nsu.aeliseev2.task131.tests;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task131.KMP;

class FindTests {
    @Test
    void smallInput() throws IOException {
        var reader = new StringReader("абракадабра");
        var result = Longs.toArray(KMP.find(reader, "бра"));
        var expected = new long[]{1, 8};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallInputOverlapping1() throws IOException {
        var reader = new StringReader("быбыбыб");
        var result = Longs.toArray(KMP.find(reader, "быб"));
        var expected = new long[]{0, 2, 4};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallInputOverlapping2() throws IOException {
        var reader = new StringReader("ббббыбыбыхбыбыбыхбыб");
        var result = Longs.toArray(KMP.find(reader, "быбыбых"));
        var expected = new long[]{3, 10};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void manyMatches() throws IOException {
        var reader = new StringReader(Strings.repeat("бы", 100));
        var result = Longs.toArray(KMP.find(reader, "быб"));
        var expected = LongStream.range(0, 99).map(l -> l * 2).toArray();
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void manyPotentialMatches() throws IOException {
        var string = Strings.repeat("б", 99) + "хы"
            + Strings.repeat("б", 100);
        var reader = new StringReader(string);
        var result = Longs.toArray(
            KMP.find(reader, Strings.repeat("б", 100)));
        var expected = new long[]{101};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void fatAssInputNoMatches() throws IOException {
        var reader = new Reader() {
            long charsLeft = 16_000_000_000L;
            final char[] singleCharBuffer = new char[1];

            @Override
            public int read(char[] chars, int startIndex, int count) throws IOException {
                int readCount = 0;
                for (int i = startIndex; i < startIndex + count; i++) {
                    if (charsLeft <= 0) {
                        break;
                    }
                    chars[i] = 'ы';
                    readCount++;
                    charsLeft--;
                }
                if (readCount == 0) {
                    return -1;
                }
                return readCount;
            }

            @Override
            public int read() throws IOException {
                if (read(singleCharBuffer, 0, 1) == -1) {
                    return -1;
                }
                return singleCharBuffer[0];
            }

            @Override
            public void close() throws IOException {
            }
        };
        var result = Longs.toArray(KMP.find(reader, "хых"));
        var expected = new long[]{};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void fatAssInputStartEnd() throws IOException {
        var startReader = new StringReader("хых");
        var endReader = new StringReader("хых");
        var reader = new Reader() {
            long charsLeft = 1_000_000_000L;
            final char[] singleCharBuffer = new char[1];

            @Override
            public int read(char[] chars, int startIndex, int count) throws IOException {
                int startResult = startReader.read(chars, startIndex, count);
                if (startResult != -1) {
                    return startResult;
                }
                int readCount = 0;
                for (int i = startIndex; i < startIndex + count; i++) {
                    if (charsLeft == 0) {
                        break;
                    }
                    chars[i] = 'х';
                    readCount++;
                    charsLeft--;
                }
                if (readCount != 0) {
                    return readCount;
                }
                return endReader.read(chars, startIndex, count);
            }

            @Override
            public int read() throws IOException {
                if (read(singleCharBuffer, 0, 1) == -1) {
                    return -1;
                }
                return singleCharBuffer[0];
            }

            @Override
            public void close() throws IOException {
            }
        };
        var result = Longs.toArray(KMP.find(reader, "хых"));
        var expected = new long[]{0, 1_000_000_003};
        Assertions.assertArrayEquals(expected, result);
    }
}
