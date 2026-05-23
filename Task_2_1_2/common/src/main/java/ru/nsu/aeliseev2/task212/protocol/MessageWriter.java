package ru.nsu.aeliseev2.task212.protocol;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;

/**
 * A stateful message writer.
 *
 * @see Message
 */
public class MessageWriter {
    private final ArrayDeque<Message> messages = new ArrayDeque<>();
    private Message.Serializer currentSerializer = null;

    /**
     * Adds a message to the writer's queue.
     *
     * @param message The message to enqueue.
     */
    public void enqueue(Message message) {
        System.err.println("Enqueued message: " + message);
        messages.add(message);
    }

    /**
     * Checks if the writer has data left to write.
     *
     * @return Whether the buffer has any data.
     */
    public boolean hasData() {
        return currentSerializer != null || !messages.isEmpty();
    }

    /**
     * Writes a chunk of message data (if any) to the buffer.
     *
     * @param buffer The buffer to write to.
     */
    public void write(ByteBuffer buffer) {
        while (buffer.hasRemaining() && hasData()) {
            if (currentSerializer != null) {
                if (currentSerializer.write(buffer)) {
                    currentSerializer = null;
                }
            } else if (!messages.isEmpty()) {
                currentSerializer = messages.remove().serialize();
                buffer.put(currentSerializer.type());
            }
        }
    }
}
