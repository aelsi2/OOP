package ru.nsu.aeliseev2.task131;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utilities for finding strings in files.
 */
public final class StringFinder {
    private StringFinder() {
    }

    /**
     * Finds starting indices of all entries of {@code string} in the file with the specified name.
     *
     * @param fileName The name of the file to search in.
     * @param string The string to search for.
     * @return The list of entries in ascending order.
     * @throws IOException An error occurred while reading with the reader.
     */
    public static List<Long> find(String fileName, String string) throws IOException {
        try (var reader = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8)) {
            return find(reader, string);
        }
    }

    /**
     * Finds starting indices of all entries of {@code string} in {@code reader}.
     *
     * @param reader The reader to search in.
     * @param string The string to search for.
     * @return The list of entries in ascending order.
     * @throws IOException An error occurred while reading with the reader.
     */
    public static List<Long> find(Reader reader, String string) throws IOException {
        Objects.requireNonNull(reader, "reader cannot be null");
        Objects.requireNonNull(string, "string cannot be null");
        if (string.isEmpty()) {
            throw new IllegalArgumentException("string cannot be empty.");
        }

        ArrayList<Long> result = new ArrayList<>();

        long index = 0;
        int stringLength = string.length();
        var potentialMatches = new MatchList();
        var chars = string.toCharArray();

        int ch;
        while ((ch = reader.read()) != -1) {
            if (ch == chars[0]) {
                potentialMatches.add();
            }
            for (int i = potentialMatches.size() - 1; i >= 0; i--) {
                if (ch != chars[potentialMatches.get(i)]) {
                    potentialMatches.remove(i);
                    continue;
                }
                if (potentialMatches.increment(i) == stringLength) {
                    result.add(index - stringLength + 1);
                    potentialMatches.remove(i);
                }
            }
            index++;
        }
        return result;
    }
}
