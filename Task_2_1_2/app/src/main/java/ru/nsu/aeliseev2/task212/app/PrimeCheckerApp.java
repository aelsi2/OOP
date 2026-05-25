package ru.nsu.aeliseev2.task212.app;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ru.nsu.aeliseev2.task212.algorithms.ParallelStreamPrimeChecker;
import ru.nsu.aeliseev2.task212.algorithms.PrimeChecker;
import ru.nsu.aeliseev2.task212.app.client.PrimeClient;
import ru.nsu.aeliseev2.task212.app.server.PrimeServer;
import ru.nsu.aeliseev2.task212.utils.AddressParser;

/**
 * A distributed client+server application for finding composite numbers.
 */
public class PrimeCheckerApp {
    private static void printHelp() {
        System.err.print("""
            Usage:
            prime-check listen <DATA IP>:<DATA PORT> <DISCOVER IFACE> <DISCOVER IP>:<DISCOVER PORT>
            prime-check check <HOST>:<PORT>, <HOST>:<PORT>, <HOST>:<PORT>, ...
            prime-check check-net <DISCOVER IFACE> <DISCOVER IP>:<DISCOVER PORT>
            """);
    }

    /**
     * Runs the application in server mode.
     *
     * @param args Application command line arguments.
     * @return Return code of the application.
     */
    private static int runListen(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Unexpected number of arguments");
        }
        InetSocketAddress dataAddress = AddressParser.parse(args[1]);
        NetworkInterface discoverInterface;
        try {
            discoverInterface = NetworkInterface.getByName(args[2]);
        } catch (SocketException exception) {
            throw new IllegalArgumentException("Invalid interface name.");
        }
        InetSocketAddress discoverAddress = AddressParser.parse(args[3]);
        PrimeChecker algorithm = new ParallelStreamPrimeChecker();
        try (PrimeServer server = new PrimeServer(
            dataAddress, discoverAddress, discoverInterface, algorithm)) {
            server.listen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * Runs the application in client mode.
     *
     * @param args Application command line arguments.
     * @return Return code of the application.
     * @throws IOException I/O error.
     */
    private static int runCheck(String[] args) throws IOException {
        if (args.length < 2) {
            throw new IllegalArgumentException("At least one host needs to be provided.");
        }
        ArrayList<InetSocketAddress> servers = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            servers.add(AddressParser.parse(args[i]));
        }
        try (PrimeClient client = new PrimeClient(servers)) {
            int numCount = 0;
            long[] array = new long[4];

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLong()) {
                array[numCount++] = scanner.nextLong();
                if (numCount == array.length) {
                    long[] newArray = new long[array.length * 2];
                    System.arraycopy(array, 0, newArray, 0, array.length);
                    array = newArray;
                }
            }
            client.connect();
            boolean result = client.hasComposites(array, 0, numCount);
            System.out.println(result);
            return 0;
        }
    }

    /**
     * Runs the application in client mode.
     *
     * @param args Application command line arguments.
     * @return Return code of the application.
     * @throws IOException I/O error.
     */
    private static int runCheckNet(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Unexpected number of arguments.");
        }
        NetworkInterface discoverInterface;
        try {
            discoverInterface = NetworkInterface.getByName(args[1]);
        } catch (SocketException exception) {
            throw new IllegalArgumentException("Invalid interface name.");
        }
        InetSocketAddress discoverAddress = AddressParser.parse(args[2]);
        List<InetSocketAddress> servers = ServerFinder.discover(discoverAddress, discoverInterface);

        try (PrimeClient client = new PrimeClient(servers)) {
            int numCount = 0;
            long[] array = new long[4];

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLong()) {
                array[numCount++] = scanner.nextLong();
                if (numCount == array.length) {
                    long[] newArray = new long[array.length * 2];
                    System.arraycopy(array, 0, newArray, 0, array.length);
                    array = newArray;
                }
            }
            client.connect();
            boolean result = client.hasComposites(array, 0, numCount);
            System.out.println(result);
            return 0;
        }
    }

    /**
     * Runs the application.
     *
     * @param args Command line arguments.
     * @return Return code of the application.
     * @throws IOException I/O error.
     */
    private static int run(String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("An action needs to be specified.");
        }
        if (args[0].equalsIgnoreCase("listen")) {
            return runListen(args);
        } else if (args[0].equalsIgnoreCase("check")) {
            return runCheck(args);
        } else if (args[0].equalsIgnoreCase("check-net")) {
            return runCheckNet(args);
        } else {
            throw new IllegalArgumentException("Unknown action: " + args[0]);
        }
    }

    /**
     * The main entry point of the application.
     *
     * @param args Application command line arguments.
     */
    public static void main(String[] args) {
        try {
            System.exit(run(args));
        } catch (IllegalArgumentException exception) {
            System.err.println(exception.getMessage());
            printHelp();
            System.exit(1);
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
            System.exit(1);
        }
    }
}
