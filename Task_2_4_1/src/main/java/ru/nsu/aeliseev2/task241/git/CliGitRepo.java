package ru.nsu.aeliseev2.task241.git;

import com.google.common.io.MoreFiles;
import java.io.IOException;
import java.nio.file.Path;

/**
 * An implementation of {@code GitRepo} that uses the Git CLI tool directly.
 */
public class CliGitRepo implements GitRepo {
    private final Path repoDirectory;

    /**
     * Initializes a new instance of {@code CliGitRepo}.
     *
     * @param repoDirectory The directory the repository is located in.
     */
    public CliGitRepo(Path repoDirectory) {
        this.repoDirectory = repoDirectory.toAbsolutePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path directory() {
        return repoDirectory;
    }

    /**
     * Deletes the repository.
     */
    @Override
    public void close() {
        try {
            MoreFiles.deleteRecursively(repoDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
