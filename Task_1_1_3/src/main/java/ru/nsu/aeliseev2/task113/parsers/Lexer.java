package ru.nsu.aeliseev2.task113.parsers;

public abstract class Lexer<Data> implements TokenReader<Data> {
    protected static final int CHAR_EOF = -1;

    protected final TokenReader<Integer> reader;
    private Token<Data> nextToken;

    public Lexer(TokenReader<Integer> reader) {
        this.reader = reader;
        this.nextToken = null;
    }

    protected abstract Token<Data> readToken();
    protected abstract boolean isEnd(Token<Data> token);

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

    @Override
    public Token<Data> peek() {
        if (nextToken == null) {
            nextToken = readToken();
        }
        return nextToken;
    }

    @Override
    public Token<Data> consume() {
        if (nextToken == null) {
            nextToken = readToken();
        }
        var token = nextToken;
        if (!isEnd(token)) {
            nextToken = null;
        }
        return token;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
