package ru.nsu.aeliseev2.task113.parsing;

public class UnexpectedExprTokenException extends ExpressionParseException {
    public final Token<ExpressionTokenData> token;

    public UnexpectedExprTokenException(Token<ExpressionTokenData> token) {
        super(
            String.format(
                "Unexpected token %s at position %d.",
                token.data(),
                token.position()),
            token.position());
        this.token = token;
    }
}
