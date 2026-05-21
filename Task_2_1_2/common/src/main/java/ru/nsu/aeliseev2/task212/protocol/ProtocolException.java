package ru.nsu.aeliseev2.task212.protocol;

/**
 * Thrown when trying to read a malformed message.
 */
public class ProtocolException extends Exception {
    /**
     * Initializes an instance of {@code ProtolException}.
     *
     * @param message The exception message.
     */
    public ProtocolException(String message) {
        super(message);
    }

    /**
     * Initializes an instance of {@code ProtolException}.
     *
     * @param cause The exception that caused this exception.
     */
    public ProtocolException(Throwable cause) {
        super(cause);
    }
}
