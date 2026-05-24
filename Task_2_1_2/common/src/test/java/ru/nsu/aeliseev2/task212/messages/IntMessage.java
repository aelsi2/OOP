package ru.nsu.aeliseev2.task212.messages;

import java.nio.ByteBuffer;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;

public record IntMessage(int number) implements Message {
    private static final byte MESSAGE_TYPE = 0;
    private static final int MESSAGE_SIZE = 4;

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
            int number = buffer.getInt();
            return new IntMessage(number);
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
            buffer.putInt(number);
            return true;
        }
    }

    @Override
    public Message.Serializer serialize() {
        return new Serializer();
    }
}
