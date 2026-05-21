package ru.nsu.aeliseev2.task212.protocol.messages;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/**
 * Message sent by the client (master) to a server (slave) to schedule a new work unit.
 *
 * @param id The id of the work unit.
 * @param data The array to look for primes in.
 */
public record WorkMessage(long id, long[] data) implements Message {
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
                this.message = new WorkMessage(id, data);
                this.dataIndex = 0;
            }

            LongBuffer longBuffer = buffer.asLongBuffer();
            int dataRemaining = message.data.length - dataIndex;
            int bufferRemaining = longBuffer.remaining();

            int receiveLength = Integer.min(dataRemaining, bufferRemaining);
            longBuffer.get(message.data, dataIndex, receiveLength);
            dataIndex += receiveLength;

            if (dataIndex == message.data.length - 1) {
                Message msg = message;
                message = null;
                return msg;
            }
            return null;
        }
    }

    private class Serializer implements Message.Serializer {
        boolean writtenHeader;
        int dataIndex = 0;

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
                buffer.putInt(data.length);
                writtenHeader = true;
            }

            LongBuffer longBuffer = buffer.asLongBuffer();
            int dataRemaining = data.length - dataIndex;
            int bufferRemaining = longBuffer.remaining();

            int sendLength = Integer.min(dataRemaining, bufferRemaining);
            buffer.asLongBuffer().put(data, dataIndex, sendLength);
            dataIndex += sendLength;

            return dataIndex == data.length - 1;
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
