package ru.nsu.aeliseev2.task113.parsers;

/**
 * An exception thrown when a lexer encounters an invalid character.
 */
public class UnexpectedCharException extends ExpressionParseException {
    int character;

    private static String charToString(int ch) {
        if (ch == -1) {
            return "{EOF}";
        } else {
            return String.valueOf((char) ch);
        }
    }

    /**
     * Constructs a new exception.
     *
     * @param token The offending "character token".
     */
    public UnexpectedCharException(Token<Integer> token) {
        super(
            String.format(
                "Unexpected character '%s' at position %d.",
                charToString(token.data()),
                token.position()),
            token.position());
        character = token.data();
    }
}
