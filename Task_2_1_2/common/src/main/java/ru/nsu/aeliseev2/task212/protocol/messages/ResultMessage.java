package ru.nsu.aeliseev2.task212.protocol.messages;

import java.nio.ByteBuffer;

/**
 * Message sent by a server (slave) to the client (master) to report the result of a work unit.
 *
 * @param id The id of the work unit.
 * @param hasPrime Whether the array contained prime numbers.
 */
public record ResultMessage(long id, boolean hasPrime) implements Message {
    private static final byte MESSAGE_TYPE = 3;
    private static final int MESSAGE_SIZE = 9;

    /**
     * Deserializer for {@code ResultMessage}.
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
            boolean hasPrime = buffer.get() != 0;
            return new ResultMessage(id, hasPrime);
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
            buffer.put((byte)(hasPrime ? 1 : 0));
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
