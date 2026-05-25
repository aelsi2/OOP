package ru.nsu.aeliseev2.task212;

import java.net.InetSocketAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.utils.AddressParser;

class AddressParserTests {
    @Test
    void parseNoColon() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AddressParser.parse("localhost");
        });
    }

    @Test
    void parseTwoColons() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AddressParser.parse("localhost:1:2");
        });
    }

    @Test
    void parseInvalidPort() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            AddressParser.parse("localhost:localhost");
        });
    }

    @Test
    void parseHost() {
        Assertions.assertEquals(new InetSocketAddress("localhost", 8080),
            AddressParser.parse("localhost:8080"));
    }

    @Test
    void parseIpv4() {
        Assertions.assertEquals(new InetSocketAddress("127.0.0.1", 8080),
            AddressParser.parse("127.0.0.1:8080"));
    }

    @Test
    void parseIpv6() {
        Assertions.assertEquals(new InetSocketAddress("::1", 8080),
            AddressParser.parse("[::1]:8080"));
    }
}
