package ru.nsu.aeliseev2.task113.parsers;

public interface TokenReader<TokenType> extends AutoCloseable {
    Token<TokenType> peek();

    Token<TokenType> consume();
}
