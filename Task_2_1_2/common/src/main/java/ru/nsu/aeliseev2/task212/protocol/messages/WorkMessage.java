package ru.nsu.aeliseev2.task212.protocol.messages;

import java.nio.ByteBuffer;

/**
 * Message sent by the client (master) to a server (slave) to schedule a new work unit.
 *
 * @param id         The id of the work unit.
 * @param data       The array to look for primes in.
 * @param startIndex The start index in the array.
 * @param endIndex   The end index in the array.
 */
public record WorkMessage(long id, long[] data, int startIndex, int endIndex) implements Message {
    private static final byte MESSAGE_TYPE = 1;
    private static final int HEADER_SIZE = 12;

    /**
     * Deserializer for {@code WorkMessage}.
     */
    public static class Deserializer implements Message.Deserializer {
        WorkMessage message = null;
        private int dataIndex = 0;

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
            if (message == null) {
                if (buffer.remaining() < HEADER_SIZE) {
                    return null;
                }
                long id = buffer.getLong();
                long[] data = new long[buffer.getInt()];
                this.message = new WorkMessage(id, data, 0, data.length);
                this.dataIndex = 0;
            }

            int dataRemaining = message.data.length - dataIndex;
            int bufferRemaining = buffer.remaining() / 8;

            int receiveLength = Integer.min(dataRemaining, bufferRemaining);
            for (int i = 0; i < receiveLength; i++) {
                message.data[dataIndex + i] = buffer.getLong();
            }
            dataIndex += receiveLength;

            if (dataIndex == message.data.length) {
                Message msg = message;
                message = null;
                return msg;
            }
            return null;
        }
    }

    private class Serializer implements Message.Serializer {
        boolean writtenHeader;
        int dataIndex = startIndex;

        @Override
        public byte type() {
            return MESSAGE_TYPE;
        }

        @Override
        public boolean write(ByteBuffer buffer) {
            if (!writtenHeader) {
                if (buffer.remaining() < HEADER_SIZE) {
                    return false;
                }
                buffer.putLong(id);
                buffer.putInt(endIndex - startIndex);
                writtenHeader = true;
            }

            int dataRemaining = endIndex - dataIndex;
            int bufferRemaining = buffer.remaining() / 8;

            int sendLength = Integer.min(dataRemaining, bufferRemaining);
            for (int i = 0; i < sendLength; i++) {
                buffer.putLong(data[dataIndex + i]);
            }
            dataIndex += sendLength;
            return dataIndex == endIndex;
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
