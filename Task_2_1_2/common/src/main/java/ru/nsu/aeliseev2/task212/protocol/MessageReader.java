package ru.nsu.aeliseev2.task212.protocol;

import java.nio.ByteBuffer;
import java.util.HashMap;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;

/**
 * A stateful message reader.
 *
 * @see Message
 */
public class MessageReader {
    private final HashMap<Byte, Message.Deserializer> deserializers;
    private Message.Deserializer currentDeserializer;

    /**
     * Initializes a new instance of {@code MessageReader}.
     *
     * @param deserializers The list of message deserializers to use.
     */
    public MessageReader(Iterable<Message.Deserializer> deserializers) {
        this.deserializers = new HashMap<>();
        this.currentDeserializer = null;
        for (var deserializer : deserializers) {
            this.deserializers.put(deserializer.type(), deserializer);
        }
    }

    /**
     * Reads a chunk of a message from the buffer.
     *
     * @param buffer The buffer to read from.
     * @return The message if all chunks of a message have been read, or {@code null}.
     * @throws ProtocolException The buffer has malformed data.
     */
    public Message read(ByteBuffer buffer) throws ProtocolException {
        if (currentDeserializer == null) {
            if (!buffer.hasRemaining()) {
                return null;
            }
            byte type = buffer.get();
            if (!deserializers.containsKey(type)) {
                throw new ProtocolException("Unknown message type");
            }
            currentDeserializer = deserializers.get(type);
        }
        try {
            Message message = currentDeserializer.read(buffer);
            if (message != null) {
                currentDeserializer = null;
                System.err.println("Received message: " + message);
            }
            return message;
        } catch (Exception e) {
            throw new ProtocolException(e);
        }
    }
}
