package ru.nsu.aeliseev2.task212.app.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;
import ru.nsu.aeliseev2.task212.protocol.DiscoverMessage;
import ru.nsu.aeliseev2.task212.protocol.ProtocolException;

/**
 * A server that receives requests from clients and performs calculations.
 */
public class PrimeServer implements AutoCloseable {
    private final InetSocketAddress dataAddress;
    private final InetSocketAddress discoverAddress;
    private final HashMap<SelectionKey, ServerConnection> connections;
    private final ServerSocketChannel serverSocket;
    private final DatagramChannel discoverSocket;
    private final Selector selector;
    private final PrimeChecker algorithm;
    private final SelectionKey discoverKey;
    private final ByteBuffer discoverReceiveBuffer;
    private final ByteBuffer discoverSendBuffer;

    /**
     * Initializes a new instance of {@code PrimeServer}.
     *
     * @param dataAddress       The address to listen for connections on.
     * @param discoverAddress   The multicast address to listen for discover messages on.
     * @param discoverInterface The interface to listen for discover messages on.
     * @param algorithm         The algorithm to use to find composite numbers.
     * @throws IOException Socket
     */
    public PrimeServer(
        InetSocketAddress dataAddress, InetSocketAddress discoverAddress,
        NetworkInterface discoverInterface, PrimeChecker algorithm
    ) throws IOException {
        Selector selector = null;
        ServerSocketChannel serverSocket = null;
        DatagramChannel discoverSocket = null;
        try {
            selector = Selector.open();

            discoverSocket = DatagramChannel.open(StandardProtocolFamily.INET6);
            discoverSocket.configureBlocking(false);
            discoverSocket.join(discoverAddress.getAddress(), discoverInterface);
            discoverSocket.bind(new InetSocketAddress(discoverAddress.getPort()));
            this.discoverKey = discoverSocket.register(selector, SelectionKey.OP_READ);

            serverSocket = ServerSocketChannel.open();
            serverSocket.configureBlocking(false);
            serverSocket.bind(dataAddress);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException exception) {
            try {
                if (selector != null) {
                    selector.close();
                }
            } catch (IOException ioException) {
                System.err.println("Selector close failed: " + exception.getMessage());
            }
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException ioException) {
                System.err.println("Socket close failed: " + exception.getMessage());
            }
            try {
                if (discoverSocket != null) {
                    discoverSocket.close();
                }
            } catch (IOException ioException) {
                System.err.println("Discover socket close failed: " + exception.getMessage());
            }
            throw exception;
        }
        this.dataAddress = dataAddress;
        this.discoverAddress = discoverAddress;
        this.serverSocket = serverSocket;
        this.discoverSocket = discoverSocket;
        this.selector = selector;
        this.connections = new HashMap<>();
        this.algorithm = algorithm;
        this.discoverReceiveBuffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
        this.discoverSendBuffer = ByteBuffer.allocate(DiscoverMessage.MAX_SIZE);
    }

    /**
     * Terminates the connection.
     *
     * @param connection The connection to terminate.
     */
    private void disconnectClient(ServerConnection connection) {
        connections.remove(connection.key());
        connection.close();
    }

    /**
     * Accepts a new client.
     *
     * @param key The server socket key.
     */
    private void acceptClient(SelectionKey key) {
        ServerSocketChannel serverSocket = (ServerSocketChannel) key.channel();
        SocketChannel clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.err.println("Incoming connection: " + clientSocket.getRemoteAddress());
            ServerConnection connection =
                new ServerConnection(clientSocket, selector, algorithm);
            connections.put(connection.key(), connection);
        } catch (IOException exception) {
            System.err.println("Accept failed.");
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException ioException) {
                    System.err.println("Close failed: " + ioException.getMessage());
                }
            }
        }
    }

    /**
     * Handles read/write operations on a client.
     *
     * @param key The client key.
     */
    private void handleOps(SelectionKey key) {
        ServerConnection connection = connections.get(key);
        try {
            if (connection.handleOps()) {
                System.err.println("Client disconnected.");
                disconnectClient(connection);
            }
        } catch (Exception exception) {
            System.err.println("Connection error: " + exception.getMessage());
            disconnectClient(connection);
        }
    }

    /**
     * Handles discover messages.
     */
    private void handleDiscover() {
        try {
            discoverReceiveBuffer.clear();
            discoverSocket.receive(discoverReceiveBuffer);
            discoverReceiveBuffer.flip();
            DiscoverMessage discoverMessage = DiscoverMessage.read(discoverReceiveBuffer);
            System.err.println("Incoming discover message: " + discoverMessage);

            discoverSendBuffer.clear();
            new DiscoverMessage(dataAddress).write(discoverSendBuffer);
            discoverSendBuffer.flip();
            discoverSocket.send(discoverSendBuffer, discoverMessage.address());
        } catch (ProtocolException | IOException exception) {
            System.err.println("Discover read error: " + exception.getMessage());
        }
    }

    /**
     * Begins listening for incoming connections and servicing them. Blocks indefinitely.
     *
     * @throws IOException Selector error.
     */
    public void listen() throws IOException {
        System.err.println("Listening for discover messages on UDP " + discoverAddress);
        System.err.println("Listening for connections on TCP " + dataAddress);
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    acceptClient(key);
                } else if (key.equals(discoverKey)) {
                    handleDiscover();
                } else {
                    handleOps(key);
                }
                iterator.remove();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        try {
            selector.close();
        } catch (IOException exception) {
            System.err.println("Selector close failed: " + exception.getMessage());
        }
        try {
            serverSocket.close();
        } catch (IOException exception) {
            System.err.println("Server socket close failed: " + exception.getMessage());
        }
        try {
            discoverSocket.close();
        } catch (IOException exception) {
            System.err.println("Discover socket close failed: " + exception.getMessage());
        }
        for (ServerConnection connection : connections.values()) {
            connection.close();
        }
    }
}
