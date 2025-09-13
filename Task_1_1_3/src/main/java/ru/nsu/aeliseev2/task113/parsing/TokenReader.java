package ru.nsu.aeliseev2.task113.parsing;

public interface TokenReader<TokenType> extends AutoCloseable {
    Token<TokenType> peek();

    Token<TokenType> consume();

    Token<TokenType> consume(TokenType expected);
}
