package ru.nsu.aeliseev2.task212;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.protocol.DiscoverMessage;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;

class DiscoverMessageTests {
    @Test
    void roundTripIpv4() throws ProtocolException {
        DiscoverMessage message = new DiscoverMessage(new InetSocketAddress("127.0.0.1", 8080));
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        message.write(buffer);
        buffer.flip();
        DiscoverMessage readMessage = DiscoverMessage.read(buffer);
        Assertions.assertEquals(message, readMessage);
    }

    @Test
    void roundTripIpv6() throws ProtocolException {
        DiscoverMessage message = new DiscoverMessage(new InetSocketAddress("::1", 8080));
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        message.write(buffer);
        buffer.flip();
        DiscoverMessage readMessage = DiscoverMessage.read(buffer);
        Assertions.assertEquals(message, readMessage);
    }

    @Test
    void invalidMagic() {
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        buffer.put(new byte[]{1, 2, 3, 4, 5, 4, 0, 0, 0, 0, 10, 10});
        buffer.flip();
        Assertions.assertThrows(ProtocolException.class, () -> DiscoverMessage.read(buffer));
    }

    @Test
    void invalidIpVersion() {
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        buffer.put(new byte[]{80, 82, 73, 77, 69, 10, 0, 0, 0, 0, 10, 10});
        buffer.flip();
        Assertions.assertThrows(ProtocolException.class, () -> DiscoverMessage.read(buffer));
    }

    @Test
    void invalidSize() {
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        buffer.put(new byte[]{80, 82, 73, 77, 69});
        buffer.flip();
        Assertions.assertThrows(ProtocolException.class, () -> DiscoverMessage.read(buffer));
    }

    @Test
    void invalidIpSize() {
        ByteBuffer buffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        buffer.put(new byte[]{80, 82, 73, 77, 69, 6, 0, 0, 0, 0, 10, 10});
        buffer.flip();
        Assertions.assertThrows(ProtocolException.class, () -> DiscoverMessage.read(buffer));
    }
}
