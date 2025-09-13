package ru.nsu.aeliseev2.task113.parsers;

import java.io.Reader;
import java.io.StringReader;

/**
 * A lexer for {@code Expression} strings.
 */
public class ExpressionLexer extends Lexer<ExpressionTokenData> {
    /**
     * Constructs a new expression lexer from a string.
     *
     * @param string The string to tokenize.
     */
    public ExpressionLexer(String string) {
        this(new StringReader(string));
    }

    /**
     * Constructs a new expression lexer from a standard reader.
     *
     * @param reader The {@code Reader} to use.
     */
    public ExpressionLexer(Reader reader) {
        this(new CharReader(reader));
    }

    /**
     * Constructs a new expression lexer from a character token reader.
     *
     * @param reader The {@code TokenReader<>} to use.
     */
    public ExpressionLexer(TokenReader<Integer> reader) {
        super(reader);
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
        if (value.equalsIgnoreCase("nan")) {
            return new Token<>(ExpressionTokenData.NAN, position);
        }
        if (value.equalsIgnoreCase("inf")) {
            return new Token<>(ExpressionTokenData.INF, position);
        }
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected Token<ExpressionTokenData> readToken() {
        Token<Integer> rawToken = skipWhitespace();
        if (rawToken.data() == CHAR_EOF) {
            return new Token<>(ExpressionTokenData.EOF, rawToken.position());
        }
        switch ((char) (int) rawToken.data()) {
            case '(':
                reader.consume();
                return new Token<>(ExpressionTokenData.LEFT_PAREN, rawToken.position());
            case ')':
                reader.consume();
                return new Token<>(ExpressionTokenData.RIGHT_PAREN, rawToken.position());
            case '+':
                reader.consume();
                return new Token<>(ExpressionTokenData.PLUS, rawToken.position());
            case '-':
                reader.consume();
                return new Token<>(ExpressionTokenData.MINUS, rawToken.position());
            case '*':
                reader.consume();
                return new Token<>(ExpressionTokenData.MULTIPLY, rawToken.position());
            case '/':
                reader.consume();
                return new Token<>(ExpressionTokenData.DIVIDE, rawToken.position());
            default:
                break;
        }
        if (isNameStart(rawToken.data())) {
            return readName();
        }
        if (isDigit(rawToken.data())) {
            return readNumber();
        }
        throw new UnexpectedCharException(rawToken);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isEnd(Token<ExpressionTokenData> token) {
        return token.data().equals(ExpressionTokenData.EOF);
    }

}
