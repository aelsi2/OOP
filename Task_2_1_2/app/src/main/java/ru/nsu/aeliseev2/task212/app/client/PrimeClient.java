package ru.nsu.aeliseev2.task212.app.client;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import ru.nsu.aeliseev2.task212.utils.RemoteServer;
import ru.nsu.aeliseev2.task212.utils.WorkStatus;
import ru.nsu.aeliseev2.task212.utils.WorkUnit;

/**
 * A client that schedules calculations on multiple servers.
 */
public class PrimeClient implements Closeable {
    private static final long HEARTBEAT_TIMEOUT = 1000;

    private final Collection<RemoteServer> servers;
    private final Selector selector;
    private final HashMap<SelectionKey, ClientConnection> connections;

    /**
     * Initializes a new instance of {@code PrimeClient}.
     *
     * @param servers The list of the servers to use.
     * @throws IOException {@code Selector} open error.
     */
    public PrimeClient(Collection<RemoteServer> servers) throws IOException {
        this.servers = servers;
        this.selector = Selector.open();
        this.connections = new HashMap<>();
    }

    /**
     * Terminates the connection.
     *
     * @param connection The connection to terminate.
     */
    private void disconnectServer(ClientConnection connection) {
        connections.remove(connection.key());
        connection.close();
    }

    /**
     * Handles timeouts on all connections.
     */
    private void handleTimeouts() {
        Iterator<ClientConnection> connectionIterator = connections.values().iterator();
        while (connectionIterator.hasNext()) {
            ClientConnection connection = connectionIterator.next();
            if (connection.handleTimeouts()) {
                System.err.println("Connection timed out");
                connectionIterator.remove();
                connection.close();
                scheduleWorkUnits(connection.scheduledWorkUnits());
            }
        }
    }

    /**
     * Performs a handshake with the servers.
     *
     * @throws IOException {@code Selector} error.
     */
    public void connect() throws IOException {
        for (RemoteServer server : servers) {
            try {
                ClientConnection connection = new ClientConnection(server, selector);
                connections.put(connection.key(), connection);
            } catch (IOException exception) {
                System.err.println("Connection failed: " + exception.getMessage());
            }
        }
        HashSet<SelectionKey> readyKeys = new HashSet<>();
        while (readyKeys.size() < connections.size()) {
            selector.select(HEARTBEAT_TIMEOUT);

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                ClientConnection connection = connections.get(key);
                try {
                    connection.handleOps();
                    if (connection.isReady()) {
                        readyKeys.add(key);
                    }
                } catch (Exception exception) {
                    System.err.println("Handshake failed: " + exception.getMessage());
                    readyKeys.remove(key);
                    disconnectServer(connection);
                }
                iterator.remove();
            }
            handleTimeouts();
        }
    }

    /**
     * Schedules a collection of work units on established connections.
     *
     * @param workUnits The work units to schedule.
     */
    private void scheduleWorkUnits(Collection<WorkUnit> workUnits) {
        Iterator<ClientConnection> connectionsIterator = connections.values().iterator();
        for (WorkUnit unit : workUnits) {
            if (connectionsIterator.hasNext()) {
                ClientConnection connection = connectionsIterator.next();
                connection.schedule(unit);
            } else {
                connectionsIterator = connections.values().iterator();
                if (!connectionsIterator.hasNext()) {
                    throw new RuntimeException("All servers are dead");
                }
                ClientConnection connection = connectionsIterator.next();
                connection.schedule(unit);
            }
        }
    }

    /**
     * Checks if the specified array contains any non-prime (composite numbers).
     *
     * @param numbers    The array to search for composite numbers.
     * @param startIndex The start index in the number array.
     * @param endIndex   The start index in the number array.
     * @return {@code true} if {@code numbers} contains at least one composite number, {@code false}
     *     otherwise.
     */
    public boolean hasComposites(long[] numbers, int startIndex, int endIndex) throws IOException {
        if (connections.size() == 0) {
            throw new IllegalStateException("No active connections");
        }

        Map<Long, WorkUnit> workUnits = WorkUnit.split(
            numbers, startIndex, endIndex, connections.size());
        scheduleWorkUnits(workUnits.values());

        while (true) {
            selector.select(HEARTBEAT_TIMEOUT);
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                ClientConnection connection = connections.get(key);
                try {
                    if (connection.handleOps()) {
                        WorkStatus status = WorkUnit.getStatus(workUnits.values());
                        if (status == WorkStatus.HAS_COMPOSITES) {
                            return true;
                        }
                        if (status == WorkStatus.ALL_PRIMES) {
                            return false;
                        }
                    }
                } catch (Exception exception) {
                    System.err.println("Connection error: " + exception.getMessage());
                    disconnectServer(connection);
                    scheduleWorkUnits(connection.scheduledWorkUnits());
                }
                keyIterator.remove();
            }
            handleTimeouts();
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
        for (ClientConnection connection : connections.values()) {
            connection.close();
        }
    }
}
