package ru.nsu.aeliseev2.task113.parsing;

public class UnexpectedCtxTokenException extends ExpressionParseException {
    public final Token<EvalContextTokenData> token;

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
