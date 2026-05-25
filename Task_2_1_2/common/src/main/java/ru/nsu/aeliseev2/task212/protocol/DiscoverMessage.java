package ru.nsu.aeliseev2.task212.protocol;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * A multicast message sent over UDP to discover servers.
 *
 * @param address The address of the server to connect to over TCP or the address of the client to
 *                send a response to.
 */
public record DiscoverMessage(InetSocketAddress address) {
    private static final byte[] MAGIC = "PRIME".getBytes(StandardCharsets.UTF_8);
    private static final int INET4_SIZE = 5 + 1 + 4 + 2;
    private static final int INET6_SIZE = 5 + 1 + 16 + 2;

    /**
     * The maximum size of the message in bytes.
     */
    public static final int MAX_SIZE = Integer.max(INET4_SIZE, INET6_SIZE);

    /**
     * Writes the discover message to a byte buffer.
     *
     * @param buffer The buffer to write to.
     */
    public void write(ByteBuffer buffer) {
        var inetAddress = address.getAddress();
        if (inetAddress instanceof Inet4Address) {
            byte[] inet4Address = inetAddress.getAddress();
            buffer.put(MAGIC);
            buffer.put((byte) 4);
            buffer.put(inet4Address);
            buffer.putShort((short) address.getPort());
        } else if (inetAddress instanceof Inet6Address) {
            byte[] inet6Address = inetAddress.getAddress();
            buffer.put(MAGIC);
            buffer.put((byte) 6);
            buffer.put(inet6Address);
            buffer.putShort((short) address.getPort());
        }
    }

    /**
     * Reads a discover message from the byte buffer.
     *
     * @param buffer The buffer to read the message from.
     * @return The parsed discover message.
     * @throws ProtocolException Message is invalid.
     */
    public static DiscoverMessage read(ByteBuffer buffer) throws ProtocolException {
        if (buffer.remaining() != INET4_SIZE && buffer.remaining() != INET6_SIZE) {
            throw new ProtocolException("Unexpected detect message size.");
        }
        byte[] magic = new byte[MAGIC.length];
        buffer.get(magic);
        if (!Arrays.equals(MAGIC, magic)) {
            throw new ProtocolException("Magic number doesn't match.");
        }
        byte[] byteAddress;
        switch (buffer.get()) {
            case 4:
                byteAddress = new byte[4];
                break;
            case 6:
                byteAddress = new byte[16];
                break;
            default:
                throw new ProtocolException("Unknown IP version.");
        }
        buffer.get(byteAddress);
        InetAddress address;
        try {
            address = InetAddress.getByAddress(byteAddress);
        } catch (UnknownHostException exception) {
            throw new ProtocolException(exception);
        }
        int port = Short.toUnsignedInt(buffer.getShort());
        return new DiscoverMessage(new InetSocketAddress(address, port));
    }
}
