package ru.nsu.aeliseev2.task241.git;

import java.nio.file.Path;
import java.util.List;

/**
 * An abstract interface for a cloned Git repository.
 */
public interface GitRepo extends AutoCloseable {
    /**
     * Gets the list of branches in this git repository.
     *
     * @return The list of branches.
     */
    List<String> branches();

    /**
     * Switches the repository to the specified branch.
     *
     * @param branch The branch to switch to.
     * @throws IllegalStateException The repository doesn't contain the specified branch.
     */
    void checkout(String branch);

    /**
     * Gets the local directory that contains the repository files.
     *
     * @return The path to the directory.
     */
    Path directory();
}
