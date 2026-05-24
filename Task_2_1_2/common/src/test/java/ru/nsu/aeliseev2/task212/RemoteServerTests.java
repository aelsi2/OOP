package ru.nsu.aeliseev2.task212;

import java.net.InetSocketAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.utils.RemoteServer;

class RemoteServerTests {
    @Test
    void parseNoColon() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RemoteServer.parse("localhost");
        });
    }

    @Test
    void parseTwoColons() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RemoteServer.parse("localhost:1:2");
        });
    }

    @Test
    void parseInvalidPort() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RemoteServer.parse("localhost:localhost");
        });
    }

    @Test
    void parseNormal() {
        Assertions.assertEquals(new RemoteServer("localhost", 8080),
            RemoteServer.parse("localhost:8080"));
    }

    @Test
    void getAddress() {
        RemoteServer server = new RemoteServer("example.com", 8080);
        Assertions.assertEquals(new InetSocketAddress("example.com", 8080),
            server.getAddress());
    }
}
