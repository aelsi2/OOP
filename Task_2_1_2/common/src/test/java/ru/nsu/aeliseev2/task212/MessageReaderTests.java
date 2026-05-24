package ru.nsu.aeliseev2.task212;

import java.nio.ByteBuffer;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.protocol.MessageReader;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;

class MessageReaderTests {
    @Test
    void readBasic() throws ProtocolException {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{0, 0, 0, 0, (byte) 255});
        buffer.flip();

        var reader = new MessageReader(List.of(IntMessage.Deserializer.INSTANCE));
        var message = reader.read(buffer);
        Assertions.assertEquals(5, buffer.position());
        Assertions.assertEquals(new IntMessage(255), message);
    }

    @Test
    void readMany() throws ProtocolException {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{0, 0, 0, 0, 1});
        buffer.put(new byte[]{0, 0, 0, 0, 2});
        buffer.put(new byte[]{0, 0, 0, 0, 3});
        buffer.flip();

        var reader = new MessageReader(List.of(IntMessage.Deserializer.INSTANCE));
        Assertions.assertEquals(new IntMessage(1), reader.read(buffer));
        Assertions.assertEquals(5, buffer.position());
        Assertions.assertEquals(new IntMessage(2), reader.read(buffer));
        Assertions.assertEquals(10, buffer.position());
        Assertions.assertEquals(new IntMessage(3), reader.read(buffer));
        Assertions.assertEquals(15, buffer.position());
        Assertions.assertNull(reader.read(buffer));
        Assertions.assertEquals(15, buffer.position());
    }

    @Test
    void readUnknown() {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{50, 0, 0, 0, (byte) 255});
        buffer.flip();

        var reader = new MessageReader(List.of(IntMessage.Deserializer.INSTANCE));
        Assertions.assertThrows(ProtocolException.class, () -> reader.read(buffer));
    }

    @Test
    void readPartial() throws ProtocolException {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{0, 0});
        buffer.flip();

        var reader = new MessageReader(List.of(IntMessage.Deserializer.INSTANCE));
        Assertions.assertNull(reader.read(buffer));
        buffer.compact();
        buffer.put(new byte[]{0, 0, 69});
        buffer.flip();

        Assertions.assertEquals(new IntMessage(69), reader.read(buffer));
    }

    @Test
    void readDifferent() throws ProtocolException {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(new byte[]{0, 0, 0, 0, 25, 1, (byte) 0xDE, (byte) 0xAD, 0, 0, 0, 0, 23});
        buffer.flip();

        var reader = new MessageReader(List.of(
            IntMessage.Deserializer.INSTANCE,
            ShortMessage.Deserializer.INSTANCE
        ));
        Assertions.assertEquals(new IntMessage(25), reader.read(buffer));
        Assertions.assertEquals(new ShortMessage((short) 0xDEAD), reader.read(buffer));
        Assertions.assertEquals(new IntMessage(23), reader.read(buffer));
    }
}
