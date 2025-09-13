package ru.nsu.aeliseev2.task113.parsers;

import java.io.Reader;
import java.io.StringReader;

/**
 * A lexer for {@code EvaluationContext} strings.
 */
public class EvalContextLexer extends Lexer<EvalContextTokenData> {
    /**
     * Constructs a new evaluation context lexer from a string.
     *
     * @param string The string to tokenize.
     */
    public EvalContextLexer(String string) {
        this(new StringReader(string));
    }

    /**
     * Constructs a new evaluation context lexer from a standard reader.
     *
     * @param reader The {@code Reader} to use.
     */
    public EvalContextLexer(Reader reader) {
        this(new CharReader(reader));
    }

    /**
     * Constructs a new evaluation context lexer from a character token reader.
     *
     * @param reader The {@code TokenReader<>} to use.
     */
    public EvalContextLexer(TokenReader<Integer> reader) {
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

    private Token<EvalContextTokenData> readName() {
        var builder = new StringBuilder();
        var rawToken = reader.peek();
        int position = rawToken.position();
        while (isName((rawToken = reader.peek()).data())) {
            builder.append((char) (int) rawToken.data());
            reader.consume();
        }
        var value = builder.toString();
        if (value.equalsIgnoreCase("nan")) {
            return new Token<>(EvalContextTokenData.NAN, position);
        }
        if (value.equalsIgnoreCase("inf")) {
            return new Token<>(EvalContextTokenData.INF, position);
        }
        return new Token<>(new EvalContextTokenData.Name(value), position);
    }

    private Token<EvalContextTokenData> readNumber() {
        var builder = new StringBuilder();
        var rawToken = reader.peek();
        int position = rawToken.position();
        while (isDigit((rawToken = reader.peek()).data())) {
            builder.append((char) (int) reader.consume().data());
        }
        if ((char) (int) rawToken.data() != '.') {
            double value = Double.parseDouble(builder.toString());
            return new Token<>(new EvalContextTokenData.Number(value), position);
        }
        do {
            builder.append((char) (int) reader.consume().data());
        } while (isDigit(reader.peek().data()));
        double value = Double.parseDouble(builder.toString());
        return new Token<>(new EvalContextTokenData.Number(value), position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Token<EvalContextTokenData> readToken() {
        Token<Integer> rawToken = skipWhitespace();
        if (rawToken.data() == CHAR_EOF) {
            return new Token<>(EvalContextTokenData.EOF, rawToken.position());
        }
        switch ((char) (int) rawToken.data()) {
            case '=':
                reader.consume();
                return new Token<>(EvalContextTokenData.EQUALS, rawToken.position());
            case ';':
                reader.consume();
                return new Token<>(EvalContextTokenData.SEMICOLON, rawToken.position());
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
    protected boolean isEnd(Token<EvalContextTokenData> token) {
        return token.data().equals(EvalContextTokenData.EOF);
    }

}
