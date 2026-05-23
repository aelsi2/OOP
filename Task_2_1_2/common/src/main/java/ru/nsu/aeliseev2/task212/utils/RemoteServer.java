package ru.nsu.aeliseev2.task212.utils;

import java.net.InetSocketAddress;

/**
 * Address of a remote server.
 *
 * @param hostname Server hostname.
 * @param port     Server port number.
 */
public record RemoteServer(String hostname, int port) {
    /**
     * Parses the address from a string.
     *
     * @param string The string to parse from.
     * @return The parsed address.
     */
    public static RemoteServer parse(String string) {
        String[] parts = string.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid server address: " + string);
        }
        int port = Port.parse(parts[1]);
        return new RemoteServer(parts[0], port);
    }

    /**
     * Converts the address to an {@code InetSocketAddress}.
     *
     * @return The socket address.
     */
    public InetSocketAddress getAddress() {
        return new InetSocketAddress(hostname, port);
    }
}
