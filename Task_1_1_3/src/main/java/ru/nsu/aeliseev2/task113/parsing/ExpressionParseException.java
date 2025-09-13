package ru.nsu.aeliseev2.task113.parsing;

public class ExpressionParseException extends RuntimeException {
    public final int position;

    public ExpressionParseException(String message, int position) {
        super(message);
        this.position = position;
    }

    public ExpressionParseException(String message, int position, Exception cause) {
        super(message, cause);
        this.position = position;
    }
}
