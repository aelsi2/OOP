package ru.nsu.aeliseev2.task113.parsing;

import java.io.Reader;
import java.io.StringReader;

public class ExpressionLexer implements TokenReader<ExpressionTokenData> {
    private static final int CHAR_EOF = -1;

    private final TokenReader<Integer> reader;
    private Token<ExpressionTokenData> nextToken;

    public ExpressionLexer(String string) {
        this(new StringReader(string));
    }

    public ExpressionLexer(Reader reader) {
        this(new CharReader(reader));
    }

    public ExpressionLexer(TokenReader<Integer> reader) {
        this.reader = reader;
        this.nextToken = null;
    }

    private static boolean isNameStart(int ch) {
        return Character.isAlphabetic(ch) || ch == '_';
    }

    private static boolean isName(int ch) {
        return isNameStart(ch) || Character.isDigit(ch);
    }

    private static boolean isDigit(int ch) {
        return Character.isDigit(ch);
    }

    private Token<ExpressionTokenData> readName() {
        var builder = new StringBuilder();
        var rawToken = reader.peek();
        int position = rawToken.position();
        while (isName((rawToken = reader.peek()).data())) {
            builder.append((char) (int) rawToken.data());
            reader.consume();
        }
        var value = builder.toString();
        return new Token<>(new ExpressionTokenData.Name(value), position);
    }

    private Token<ExpressionTokenData> readNumber() {
        var builder = new StringBuilder();
        var rawToken = reader.peek();
        int position = rawToken.position();
        while (isDigit((rawToken = reader.peek()).data())) {
            builder.append((char) (int) reader.consume().data());
        }
        if ((char) (int) rawToken.data() != '.') {
            double value = Double.parseDouble(builder.toString());
            return new Token<>(new ExpressionTokenData.Number(value), position);
        }
        do {
            builder.append((char) (int) reader.consume().data());
        } while (isDigit(reader.peek().data()));
        double value = Double.parseDouble(builder.toString());
        return new Token<>(new ExpressionTokenData.Number(value), position);
    }

    private Token<Integer> skipWhitespace() {
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

    private void readNext() {
        if (nextToken != null && nextToken.data().equals(ExpressionTokenData.EOF)) {
            return;
        }
        Token<Integer> rawToken = skipWhitespace();
        if (rawToken.data() == CHAR_EOF) {
            nextToken = new Token<>(ExpressionTokenData.EOF, rawToken.position());
            return;
        }
        switch ((char) (int) rawToken.data()) {
            case '(':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.LEFT_PAREN, rawToken.position());
                return;
            case ')':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.RIGHT_PAREN, rawToken.position());
                return;
            case '+':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.PLUS, rawToken.position());
                return;
            case '-':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.MINUS, rawToken.position());
                return;
            case '*':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.MULTIPLY, rawToken.position());
                return;
            case '/':
                reader.consume();
                nextToken = new Token<>(ExpressionTokenData.DIVIDE, rawToken.position());
                return;
            default:
                break;
        }
        if (isNameStart(rawToken.data())) {
            nextToken = readName();
            return;
        }
        if (isDigit(rawToken.data())) {
            nextToken = readNumber();
            return;
        }
        throw new UnexpectedCharException(rawToken);
    }

    @Override
    public Token<ExpressionTokenData> peek() {
        if (nextToken == null) {
            readNext();
        }
        return nextToken;
    }

    @Override
    public Token<ExpressionTokenData> consume() {
        if (nextToken == null) {
            readNext();
        }
        var token = nextToken;
        nextToken = null;
        return token;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
