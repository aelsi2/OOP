package ru.nsu.aeliseev2.task212.protocol.messages;

import java.nio.ByteBuffer;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;

/**
 * A message sent between a client (master) and a server (slave).
 */
public interface Message {
    /**
     * A stateful deserializer for messages of a specific type.
     */
    interface Deserializer {
        /**
         * Gets the type code of messages handled by this deserializer.
         *
         * @return The message type code.
         */
        byte type();

        /**
         * Reads a chunk of message data from the buffer.
         *
         * @param buffer The buffer to read the message from.
         * @return The parsed message or {@code null}, if there wasn't enough data in the buffer to
         *     read the message completely.
         * @exception ProtocolException Protocol violation.
         */
        Message read(ByteBuffer buffer) throws ProtocolException;
    }

    /**
     * A stateful serializer for a message.
     */
    interface Serializer {
        /**
         * Gets the type code of the message.
         *
         * @return The message type code.
         */
        byte type();

        /**
         * Writes a chunk of message data to the buffer.
         *
         * @param buffer The buffer to write the message to.
         * @return {@code true} if all message data has been written, {@code false} otherwise.
         */
        boolean write(ByteBuffer buffer);
    }

    /**
     * Creates a serializer for this message.
     *
     * @return The created serializer.
     */
    Serializer serialize();
}
