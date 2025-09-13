package ru.nsu.aeliseev2.task113.parsing;

import java.io.IOException;
import java.io.Reader;

public class CharReader implements TokenReader<Integer> {
    private static final int CHAR_EOF = -1;
    private static final int CHAR_NULL = -2;

    private final Reader reader;
    private int nextChar;
    private int position;

    public CharReader(Reader reader) {
        this.reader = reader;
        this.position = 0;
        this.nextChar = CHAR_NULL;
    }

    private void readNext() {
        if (nextChar == CHAR_EOF) {
            return;
        }
        try {
            position++;
            nextChar = reader.read();
        } catch (IOException e) {
            throw new ExpressionParseException("IO error.", position, e);
        }
    }

    @Override
    public Token<Integer> peek() {
        if (nextChar == CHAR_NULL) {
            readNext();
        }
        return new Token<>(nextChar, position);
    }

    @Override
    public Token<Integer> consume() {
        if (nextChar == CHAR_NULL) {
            readNext();
        }
        var token = new Token<>(nextChar, position);
        nextChar = CHAR_NULL;
        return token;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
