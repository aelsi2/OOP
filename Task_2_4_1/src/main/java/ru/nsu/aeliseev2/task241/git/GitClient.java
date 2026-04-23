package ru.nsu.aeliseev2.task241.git;

/**
 * An abstract interface for a git client.
 */
public interface GitClient {
    /**
     * Clones a repository from a remote URI.
     *
     * @param uri The remote URI.
     * @return The cloned repository.
     */
    GitRepo clone(String uri);
}
