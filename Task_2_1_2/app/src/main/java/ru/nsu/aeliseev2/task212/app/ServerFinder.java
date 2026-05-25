package ru.nsu.aeliseev2.task212.app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import ru.nsu.aeliseev2.task212.protocol.DiscoverMessage;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;

/**
 * Utility class for discovering servers in a local network.
 */
final class ServerFinder {
    private static final long DISCOVER_TIMEOUT = 3000;

    private ServerFinder() {
    }

    private static InetAddress getAddress(NetworkInterface networkInterface) {
        Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
        if (addresses.hasMoreElements()) {
            return addresses.nextElement();
        }
        throw new IllegalArgumentException("The specified interface does not have an address");
    }

    /**
     * Finds servers by sending a multicast UDP datagram.
     *
     * @param discoverAddress   The address to send discover requests to.
     * @param discoverInterface The interface to send discover requests from.
     * @return The list of discovered servers.
     * @throws IOException I/O error.
     */
    public static List<InetSocketAddress> discover(
        InetSocketAddress discoverAddress, NetworkInterface discoverInterface
    ) throws IOException {
        final ArrayList<InetSocketAddress> results = new ArrayList<>();
        try (Selector selector = Selector.open()) {
            try (DatagramChannel channel = DatagramChannel.open(StandardProtocolFamily.INET6)) {
                channel.setOption(StandardSocketOptions.IP_MULTICAST_IF, discoverInterface)
                    .bind(null)
                    .configureBlocking(false);

                int responsePort = ((InetSocketAddress) channel.getLocalAddress()).getPort();
                InetSocketAddress responseAddress = new InetSocketAddress(
                    getAddress(discoverInterface), responsePort);

                ByteBuffer sendBuffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
                DiscoverMessage discoverMessage = new DiscoverMessage(responseAddress);
                discoverMessage.write(sendBuffer);
                sendBuffer.flip();

                SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
                channel.send(sendBuffer, discoverAddress);
                long discoverTime = System.currentTimeMillis();

                System.err.println("Sent discover request.");
                ByteBuffer receiveBuffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
                while (true) {
                    selector.select(DISCOVER_TIMEOUT);
                    selector.selectedKeys().remove(key);
                    if (System.currentTimeMillis() - discoverTime > DISCOVER_TIMEOUT) {
                        break;
                    }
                    if (!key.isReadable()) {
                        continue;
                    }

                    try {
                        receiveBuffer.clear();
                        channel.receive(receiveBuffer);
                        receiveBuffer.flip();
                        DiscoverMessage message = DiscoverMessage.read(receiveBuffer);
                        System.err.println("Discovered server: " + message.address());
                        results.add(message.address());
                    } catch (ProtocolException exception) {
                        System.err.println("Invalid detect response: " + exception.getMessage());
                    }
                }
                return results;
            }
        }
    }
}
