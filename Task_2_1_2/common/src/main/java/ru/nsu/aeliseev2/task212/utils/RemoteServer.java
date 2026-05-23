package ru.nsu.aeliseev2.task212.utils;

import java.net.InetSocketAddress;

public record RemoteServer(String hostname, int port) {
    public static RemoteServer parse(String string) {
        String[] parts = string.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid server address: " + string);
        }
        int port = Port.parse(parts[1]);
        return new RemoteServer(parts[0], port);
    }

    public InetSocketAddress getAddress() {
        return new InetSocketAddress(hostname, port);
    }
}
