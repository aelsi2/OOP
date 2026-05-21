package ru.nsu.aeliseev2.task212.protocol.messages;

import java.nio.ByteBuffer;

/**
 * Message sent by the client (master) to a server (slave) to confirm it's still up. The server must
 * respond with the same message.
 *
 * @param id A unique id of the message.
 */
public record PingMessage(long id) implements Message {
    private static final byte MESSAGE_TYPE = 0;
    private static final int MESSAGE_SIZE = 8;

    /**
     * Deserializer for {@code PingMessage}.
     */
    public static class Deserializer implements Message.Deserializer {
        private Deserializer() {
        }

        /**
         * The shared instance.
         */
        public static final Deserializer INSTANCE = new Deserializer();

        /**
         * {@inheritDoc}
         */
        @Override
        public byte type() {
            return MESSAGE_TYPE;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Message read(ByteBuffer buffer) {
            if (buffer.remaining() < MESSAGE_SIZE) {
                return null;
            }
            long id = buffer.getLong();
            return new PingMessage(id);
        }
    }

    private class Serializer implements Message.Serializer {
        @Override
        public byte type() {
            return MESSAGE_TYPE;
        }

        @Override
        public boolean write(ByteBuffer buffer) {
            if (buffer.remaining() < MESSAGE_SIZE) {
                return false;
            }
            buffer.putLong(id);
            return true;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message.Serializer serialize() {
        return new Serializer();
    }
}
