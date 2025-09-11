package ru.nsu.aeliseev2.task131.tests;

import com.google.common.base.Strings;
import com.google.common.primitives.Longs;
import java.io.IOException;
import java.io.StringReader;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task131.Kmp;

class FindTests {
    @Test
    void smallInput() throws IOException {
        var reader = new StringReader("абракадабра");
        var result = Longs.toArray(Kmp.find(reader, "бра"));
        var expected = new long[]{1, 8};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallInputOverlapping1() throws IOException {
        var reader = new StringReader("быбыбыб");
        var result = Longs.toArray(Kmp.find(reader, "быб"));
        var expected = new long[]{0, 2, 4};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void smallInputOverlapping2() throws IOException {
        var reader = new StringReader("ббббыбыбыхбыбыбыхбыб");
        var result = Longs.toArray(Kmp.find(reader, "быбыбых"));
        var expected = new long[]{3, 10};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void manyMatches() throws IOException {
        var reader = new StringReader(Strings.repeat("бы", 100));
        var result = Longs.toArray(Kmp.find(reader, "быб"));
        var expected = LongStream.range(0, 99).map(l -> l * 2).toArray();
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void manyPotentialMatches() throws IOException {
        var string = Strings.repeat("б", 99) + "хы"
            + Strings.repeat("б", 100);
        var reader = new StringReader(string);
        var result = Longs.toArray(
            Kmp.find(reader, Strings.repeat("б", 100)));
        var expected = new long[]{101};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void fatAssInputNoMatches() throws IOException {
        var reader = new TestReader(
            new TestReaderSection("хыъ", 16_000_000_000L)
        );
        var result = Longs.toArray(Kmp.find(reader, "хых"));
        var expected = new long[]{};
        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    void fatAssInputStartEnd() throws IOException {
        var reader = new TestReader(
            new TestReaderSection("хых"),
            new TestReaderSection("хыъ", 1_000_000_000L),
            new TestReaderSection("хыхшышхых")
        );
        var result = Longs.toArray(Kmp.find(reader, "хых"));
        var expected = new long[]{0, 1_000_000_003, 1_000_000_009};
        Assertions.assertArrayEquals(expected, result);
    }
}
