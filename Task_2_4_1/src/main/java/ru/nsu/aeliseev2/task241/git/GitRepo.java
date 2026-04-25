package ru.nsu.aeliseev2.task241.git;

import java.nio.file.Path;

/**
 * An abstract interface for a cloned Git repository.
 */
public interface GitRepo extends AutoCloseable {
    /**
     * Gets the local directory that contains the repository files.
     *
     * @return The path to the directory.
     */
    Path directory();
}
