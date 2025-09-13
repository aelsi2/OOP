package ru.nsu.aeliseev2.task113.parsers;

/**
 * An exception thrown when {@code ExpressionParser} encounters an invalid token.
 */
public class UnexpectedExprTokenException extends ExpressionParseException {
    public final Token<ExpressionTokenData> token;

    /**
     * Constructs a new exception.
     *
     * @param token The offending token.
     */
    public UnexpectedExprTokenException(Token<ExpressionTokenData> token) {
        super(
            String.format(
                "Unexpected token '%s' at position %d.",
                token.data(),
                token.position()),
            token.position());
        this.token = token;
    }
}
