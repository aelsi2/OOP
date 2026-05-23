package ru.nsu.aeliseev2.task212.utils;

public class Port {
    public static int parse(String string) {
        int port;
        try {
            port = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number: " + string, e);
        }
        if (port > Short.toUnsignedInt((short) -1)) {
            throw new IllegalArgumentException("Port number out of range: " + string);
        }
        return port;
    }
}
