package ru.nsu.aeliseev2.task113.parsers;

/**
 * A base class for lexers.
 *
 * @param <TokenDataT> The data stored in the tokens.
 */
public abstract class Lexer<TokenDataT> implements TokenReader<TokenDataT> {
    /**
     * The {@code reader} value corresponding to the end of file.
     */
    protected static final int CHAR_EOF = -1;

    /**
     * The character reader.
     */
    protected final TokenReader<Integer> reader;
    private Token<TokenDataT> nextToken;

    /**
     * Constructs a new lexer.
     *
     * @param reader The character reader.
     */
    public Lexer(TokenReader<Integer> reader) {
        this.reader = reader;
        this.nextToken = null;
    }

    /**
     * Reads the next token from {@code reader}.
     *
     * @return The read token.
     */
    protected abstract Token<TokenDataT> readToken();

    /**
     * Checks if the token is an EOF token.
     *
     * @param token The token.
     * @return Whether the token is an EOF token.
     */
    protected abstract boolean isEnd(Token<TokenDataT> token);

    /**
     * Skips until the first non-whitespace character in the reader.
     *
     * @return The first non-whitespace character.
     */
    protected Token<Integer> skipWhitespace() {
        while (true) {
            var rawToken = reader.peek();
            if (rawToken.data() == CHAR_EOF) {
                return rawToken;
            }
            if (Character.isWhitespace(rawToken.data())) {
                reader.consume();
                continue;
            }
            return rawToken;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token<TokenDataT> peek() {
        if (nextToken == null) {
            nextToken = readToken();
        }
        return nextToken;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Token<TokenDataT> consume() {
        if (nextToken == null) {
            nextToken = readToken();
        }
        var token = nextToken;
        if (!isEnd(token)) {
            nextToken = null;
        }
        return token;
    }

    /**
     * Closes the {@code reader}.
     */
    @Override
    public void close() throws Exception {
        reader.close();
    }
}
