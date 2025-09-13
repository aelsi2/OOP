package ru.nsu.aeliseev2.task113.parsing;

public class UnexpectedCharException extends ExpressionParseException {
    int character;

    private static String charToString(int ch) {
        if (ch == -1) {
            return "EOF";
        } else {
            return "'" + ch + "'";
        }
    }

    public UnexpectedCharException(Token<Integer> token) {
        super(
            String.format(
                "Unexpected character %s at position %d.",
                charToString(token.data()),
                token.position()),
            token.position());
        character = token.data();
    }
}
