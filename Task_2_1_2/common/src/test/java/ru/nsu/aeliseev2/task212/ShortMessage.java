package ru.nsu.aeliseev2.task212;

import java.nio.ByteBuffer;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;

record ShortMessage(short number) implements Message {
    private static final byte MESSAGE_TYPE = 1;
    private static final int MESSAGE_SIZE = 2;

    public static class Deserializer implements Message.Deserializer {
        private Deserializer() {
        }

        public static final Deserializer INSTANCE = new Deserializer();

        @Override
        public byte type() {
            return MESSAGE_TYPE;
        }

        @Override
        public Message read(ByteBuffer buffer) {
            if (buffer.remaining() < MESSAGE_SIZE) {
                return null;
            }
            short number = buffer.getShort();
            return new ShortMessage(number);
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
            buffer.putShort(number);
            return true;
        }
    }

    @Override
    public Message.Serializer serialize() {
        return new Serializer();
    }
}
