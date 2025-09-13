package ru.nsu.aeliseev2.task113.parsers;

/**
 * Represents a token reader with a single token lookahead.
 *
 * @param <TokenDataT> The data stored in the tokens.
 */
public interface TokenReader<TokenDataT> extends AutoCloseable {
    /**
     * Peeks at the next token without consuming it.
     *
     * @return The next token.
     */
    Token<TokenDataT> peek();

    /**
     * Reads the next token and consumes it.
     *
     * @return The read token.
     */
    Token<TokenDataT> consume();
}
