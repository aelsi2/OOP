package ru.nsu.aeliseev2.task113.parsers;

import java.io.IOException;
import java.io.Reader;

/**
 * A {@code TokenReader<>} for individual characters.
 */
public class CharReader implements TokenReader<Integer> {
    private static final int CHAR_EOF = -1;
    private static final int CHAR_NULL = -2;

    private final Reader reader;
    private int nextChar;
    private int position;

    /**
     * Constructs a new character reader.
     *
     * @param reader The internal reader.
     */
    public CharReader(Reader reader) {
        this.reader = reader;
        this.position = -1;
        this.nextChar = CHAR_NULL;
    }

    private void readNext() {
        try {
            position++;
            nextChar = reader.read();
        } catch (IOException e) {
            throw new ExpressionParseException("IO error.", position, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token<Integer> peek() {
        if (nextChar == CHAR_NULL) {
            readNext();
        }
        return new Token<>(nextChar, position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token<Integer> consume() {
        if (nextChar == CHAR_NULL) {
            readNext();
        }
        var token = new Token<>(nextChar, position);
        if (nextChar != CHAR_EOF) {
            nextChar = CHAR_NULL;
        }
        return token;
    }

    /**
     * Closes the {@code reader}.
     *
     * @throws IOException {@code reader.close()} failed.
     */
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
