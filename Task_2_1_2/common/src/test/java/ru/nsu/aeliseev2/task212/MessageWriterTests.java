package ru.nsu.aeliseev2.task212;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.protocol.MessageWriter;

class MessageWriterTests {
    @Test
    void writeBasic() {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        final MessageWriter writer = new MessageWriter();
        writer.enqueue(new ShortMessage((short) 123));

        Assertions.assertTrue(writer.hasData());
        writer.write(buffer);
        Assertions.assertFalse(writer.hasData());

        buffer.flip();
        Assertions.assertEquals(3, buffer.limit());
        Assertions.assertEquals(1, buffer.get());
        Assertions.assertEquals(0, buffer.get());
        Assertions.assertEquals(123, buffer.get());
    }

    @Test
    void writeMany() {
        final ByteBuffer buffer = ByteBuffer.allocate(1024);
        final MessageWriter writer = new MessageWriter();
        writer.enqueue(new ShortMessage((short) 123));
        writer.enqueue(new IntMessage((short) 456));
        writer.enqueue(new ShortMessage((short) 678));

        Assertions.assertTrue(writer.hasData());
        writer.write(buffer);
        Assertions.assertFalse(writer.hasData());

        buffer.flip();
        Assertions.assertEquals(11, buffer.limit());
        Assertions.assertEquals(1, buffer.get());
        Assertions.assertEquals(123, buffer.getShort());
        Assertions.assertEquals(0, buffer.get());
        Assertions.assertEquals(456, buffer.getInt());
        Assertions.assertEquals(1, buffer.get());
        Assertions.assertEquals(678, buffer.getShort());
    }

    @Test
    void writePartial() {
        final ByteBuffer buffer = ByteBuffer.allocate(5);
        final MessageWriter writer = new MessageWriter();
        writer.enqueue(new ShortMessage((short) 123));
        writer.enqueue(new IntMessage((short) 456));
        writer.enqueue(new ShortMessage((short) 678));

        writer.write(buffer);

        buffer.flip();
        Assertions.assertEquals(1, buffer.get());
        Assertions.assertEquals(123, buffer.getShort());
        buffer.compact();

        writer.write(buffer);

        buffer.flip();
        Assertions.assertEquals(0, buffer.get());
        Assertions.assertEquals(456, buffer.getInt());
        buffer.compact();

        writer.write(buffer);

        buffer.flip();
        Assertions.assertEquals(1, buffer.get());
        Assertions.assertEquals(678, buffer.getShort());
        buffer.compact();
    }
}
