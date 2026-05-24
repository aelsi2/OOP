package ru.nsu.aeliseev2.task212.app.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;

/**
 * A server that receives requests from clients and performs calculations.
 */
public class PrimeServer implements AutoCloseable {
    private final HashMap<SelectionKey, ServerConnection> connections;
    private final ServerSocketChannel serverSocket;
    private final Selector selector;
    private final PrimeChecker algorithm;

    /**
     * Initializes a new instance of {@code PrimeServer}.
     *
     * @param port      The port to listen on.
     * @param algorithm The algorithm to use to find composite numbers.
     * @throws IOException Socket
     */
    public PrimeServer(int port, PrimeChecker algorithm) throws IOException {
        Selector selector = null;
        ServerSocketChannel serverSocket = null;
        try {
            serverSocket = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocket.configureBlocking(false);
            serverSocket.bind(new InetSocketAddress(port));
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
            throw exception;
        }
        this.serverSocket = serverSocket;
        this.selector = selector;
        this.connections = new HashMap<>();
        this.algorithm = algorithm;
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
            System.err.println("Incoming connection: " +
                clientSocket.getRemoteAddress());
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
     * Begins listening for incoming connections and servicing them. Blocks indefinitely.
     *
     * @throws IOException Client accept error.
     */
    public void listen() throws IOException {
        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    acceptClient(key);
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
        for (ServerConnection connection : connections.values()) {
            connection.close();
        }
    }
}
