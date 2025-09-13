package ru.nsu.aeliseev2.task113.parsers;

/**
 * An exception thrown when an error occurs while parsing an expression or an evaluation context.
 */
public class ExpressionParseException extends RuntimeException {
    public final int position;

    /**
     * Constructs a new exception.
     *
     * @param message  The exception message.
     * @param position The position in the input string where the error occurred.
     */
    public ExpressionParseException(String message, int position) {
        super(message);
        this.position = position;
    }

    /**
     * Constructs a new exception.
     *
     * @param message  The exception message.
     * @param position The position in the input string where the error occurred.
     * @param cause    The exception that caused this exception.
     */
    public ExpressionParseException(String message, int position, Throwable cause) {
        super(message, cause);
        this.position = position;
    }
}
