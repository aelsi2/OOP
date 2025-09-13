package ru.nsu.aeliseev2.task113.parsers;

/**
 * Represents a token reader with a single token lookahead.
 *
 * @param <TokenType> The data stored in the tokens.
 */
public interface TokenReader<TokenType> extends AutoCloseable {
    /**
     * Peeks at the next token without consuming it.
     *
     * @return The next token.
     */
    Token<TokenType> peek();

    /**
     * Reads the next token and consumes it.
     *
     * @return The read token.
     */
    Token<TokenType> consume();
}
