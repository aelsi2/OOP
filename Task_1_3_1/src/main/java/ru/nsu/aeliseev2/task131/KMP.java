package ru.nsu.aeliseev2.task131;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the KMP (Knuth–Morris–Pratt) algorithm.
 */
public final class KMP {
    private KMP() {
    }

    private static int[] makePrefixFunction(char[] pattern) {
        int[] prefixFunction = new int[pattern.length];
        prefixFunction[0] = 0;
        for (int i = 1; i < pattern.length; i++) {
            int k = prefixFunction[i - 1];
            while (k > 0 && pattern[i] != pattern[k]) {
                k = prefixFunction[k - 1];
            }
            if (pattern[k] == pattern[i]) {
                ++k;
            }
            prefixFunction[i] = k;
        }
        return prefixFunction;
    }

    /**
     * Finds the indices of all entries of {@code pattern} in the file with the specified name
     * using the KMP algorithm.
     *
     * @param fileName The name of the file to search in.
     * @param pattern The string to search for.
     * @return The list of indices in ascending order.
     * @throws IOException An error occurred while reading with the reader.
     */
    public static List<Long> find(String fileName, String pattern) throws IOException {
        try (var reader = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8)) {
            return find(reader, pattern);
        }
    }

    /**
     * Finds the indices of all entries of {@code pattern} in {@code reader}
     * using the KMP algorithm.
     *
     * @param reader The reader to search in.
     * @param pattern The string to search for.
     * @return The list of indices in ascending order.
     * @throws IOException An error occurred while reading with the reader.
     */
    public static List<Long> find(Reader reader, String pattern) throws IOException {
        ArrayList<Long> result = new ArrayList<>();

        char[] patternChars = pattern.toCharArray();
        var prefixFunction = makePrefixFunction(patternChars);
        long streamIndex = 0;
        int patternIndex = 0;

        int ch;
        while ((ch = reader.read()) != -1) {
            while (patternIndex > 0 && patternChars[patternIndex] != ch){
                patternIndex = prefixFunction[patternIndex - 1];
            }
            if (ch == patternChars[patternIndex]){
                patternIndex++;
            }
            if (patternIndex == patternChars.length){
                result.add(streamIndex - patternChars.length + 1);
                patternIndex = prefixFunction[patternIndex - 1];
            }
            streamIndex++;
        }
        return result;
    }

}
