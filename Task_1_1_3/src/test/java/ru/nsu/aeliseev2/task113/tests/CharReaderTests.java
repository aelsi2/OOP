package ru.nsu.aeliseev2.task113.tests;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.parsers.CharReader;
import ru.nsu.aeliseev2.task113.parsers.ExpressionParseException;
import ru.nsu.aeliseev2.task113.parsers.Token;

class CharReaderTests {
    @Test
    void consume() {
        var reader = new CharReader(new StringReader("abcdef"));
        Assertions.assertEquals(new Token<>((int)'a', 0), reader.consume());
        Assertions.assertEquals(new Token<>((int)'b', 1), reader.consume());
        Assertions.assertEquals(new Token<>((int)'c', 2), reader.consume());
        Assertions.assertEquals(new Token<>((int)'d', 3), reader.consume());
        Assertions.assertEquals(new Token<>((int)'e', 4), reader.consume());
        Assertions.assertEquals(new Token<>((int)'f', 5), reader.consume());
    }

    @Test
    void peek() {
        var reader = new CharReader(new StringReader("abcdef"));
        Assertions.assertEquals(new Token<>((int)'a', 0), reader.peek());
        Assertions.assertEquals(new Token<>((int)'a', 0), reader.peek());
        Assertions.assertEquals(new Token<>((int)'a', 0), reader.consume());
        Assertions.assertEquals(new Token<>((int)'b', 1), reader.consume());
        Assertions.assertEquals(new Token<>((int)'c', 2), reader.peek());
        Assertions.assertEquals(new Token<>((int)'c', 2), reader.peek());
        Assertions.assertEquals(new Token<>((int)'c', 2), reader.consume());
        Assertions.assertEquals(new Token<>((int)'d', 3), reader.consume());
    }

    @Test
    void multipleEof() {
        var reader = new CharReader(new StringReader(""));
        var eof1 = reader.consume();
        var eof2 = reader.consume();
        Assertions.assertEquals(eof1, eof2);
    }

    @Test
    void ioException() {
        var reader = new Reader() {

            @Override
            public int read(char[] chars, int i, int i1) throws IOException {
                throw new IOException();
            }

            @Override
            public void close() {
            }
        };
        Assertions.assertThrows(ExpressionParseException.class, () -> {
            new CharReader(reader).consume();
        });
    }
}
