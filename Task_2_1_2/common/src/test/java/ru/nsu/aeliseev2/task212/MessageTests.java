package ru.nsu.aeliseev2.task212;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.protocol.messages.Message;
import ru.nsu.aeliseev2.task212.protocol.messages.PingMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.ResultMessage;
import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;

class MessageTests {
    @Test
    void pingRoundTrip() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Message message = new PingMessage(1944534243);

        Assertions.assertTrue(message.serialize().write(buffer));
        buffer.flip();
        Assertions.assertEquals(message, PingMessage.Deserializer.INSTANCE.read(buffer));
    }

    @Test
    void pingRoundTripPartial() {
        ByteBuffer buffer = ByteBuffer.allocate(3);
        Message message = new PingMessage(323432454);

        Assertions.assertFalse(message.serialize().write(buffer));
        buffer.flip();
        Assertions.assertNull(PingMessage.Deserializer.INSTANCE.read(buffer));
    }

    @Test
    void resultRoundTripAllPrimes() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Message message = new ResultMessage(345970592, false);

        Assertions.assertTrue(message.serialize().write(buffer));
        buffer.flip();
        Assertions.assertEquals(message, ResultMessage.Deserializer.INSTANCE.read(buffer));
    }

    @Test
    void resultRoundTripHasComposites() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Message message = new ResultMessage(359857423, true);

        Assertions.assertTrue(message.serialize().write(buffer));
        buffer.flip();
        Assertions.assertEquals(message, ResultMessage.Deserializer.INSTANCE.read(buffer));
    }

    @Test
    void resultRoundTripPartial() {
        ByteBuffer buffer = ByteBuffer.allocate(3);
        Message message = new ResultMessage(53454353, false);

        Assertions.assertFalse(message.serialize().write(buffer));
        buffer.flip();
        Assertions.assertNull(ResultMessage.Deserializer.INSTANCE.read(buffer));
    }

    @Test
    void workRoundTripFull() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        WorkMessage message = new WorkMessage(4234354, new long[]{1, 2, 3, 4, 5}, 0, 5);

        Assertions.assertTrue(message.serialize().write(buffer));
        buffer.flip();
        WorkMessage parsedMessage = (WorkMessage) new WorkMessage.Deserializer().read(buffer);

        Assertions.assertNotNull(parsedMessage);
        Assertions.assertEquals(message.id(), parsedMessage.id());
        Assertions.assertArrayEquals(message.data(), parsedMessage.data());
        Assertions.assertEquals(0, parsedMessage.startIndex());
        Assertions.assertEquals(message.data().length, parsedMessage.endIndex());
    }

    @Test
    void workRoundTripSlice() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        WorkMessage message = new WorkMessage(4234354, new long[]{1, 2, 3, 4, 5}, 3, 5);

        Assertions.assertTrue(message.serialize().write(buffer));
        buffer.flip();
        WorkMessage parsedMessage = (WorkMessage) new WorkMessage.Deserializer().read(buffer);

        Assertions.assertNotNull(parsedMessage);
        Assertions.assertEquals(message.id(), parsedMessage.id());
        Assertions.assertArrayEquals(new long[]{4, 5}, parsedMessage.data());
        Assertions.assertEquals(0, parsedMessage.startIndex());
        Assertions.assertEquals(parsedMessage.data().length, parsedMessage.endIndex());
    }

    @Test
    void workRoundTripFullPartialWrite() {
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        ByteBuffer writeBuffer = ByteBuffer.allocate(20);
        WorkMessage message = new WorkMessage(234324535,
            new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 0, 16);

        Message.Serializer serializer = message.serialize();
        while (!serializer.write(writeBuffer)) {
            writeBuffer.flip();
            readBuffer.put(writeBuffer);
            writeBuffer.compact();
        }
        writeBuffer.flip();
        readBuffer.put(writeBuffer);
        writeBuffer.compact();

        readBuffer.flip();
        WorkMessage parsedMessage = (WorkMessage) new WorkMessage.Deserializer().read(readBuffer);

        Assertions.assertNotNull(parsedMessage);
        Assertions.assertEquals(message.id(), parsedMessage.id());
        Assertions.assertArrayEquals(message.data(), parsedMessage.data());
        Assertions.assertEquals(0, parsedMessage.startIndex());
        Assertions.assertEquals(message.data().length, parsedMessage.endIndex());
    }
}
