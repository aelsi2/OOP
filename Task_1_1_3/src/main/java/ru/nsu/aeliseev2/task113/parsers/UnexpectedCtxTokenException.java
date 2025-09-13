package ru.nsu.aeliseev2.task113.parsers;

/**
 * An exception thrown when {@code EvalContextLexer} encounters an invalid token.
 */
public class UnexpectedCtxTokenException extends ExpressionParseException {
    public final Token<EvalContextTokenData> token;

    /**
     * Constructs a new exception.
     *
     * @param token The offending token.
     */
    public UnexpectedCtxTokenException(Token<EvalContextTokenData> token) {
        super(
            String.format(
                "Unexpected token '%s' at position %d.",
                token.data(),
                token.position()),
            token.position());
        this.token = token;
    }
}
