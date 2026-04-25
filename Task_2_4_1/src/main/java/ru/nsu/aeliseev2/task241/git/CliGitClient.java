package ru.nsu.aeliseev2.task241.git;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * An implementation of {@code GitClient} that uses the Git CLI tool directly.
 */
public class CliGitClient implements GitClient {
    private final String gitCommand;

    /**
     * Initializes a new instance of {@code CliGitClient} using the default command {@code git}.
     */
    public CliGitClient() {
        this("git");
    }

    /**
     * Initializes a new instance of {@code CliGitClient}.
     *
     * @param gitCommand The Git command to use.
     */
    public CliGitClient(String gitCommand) {
        this.gitCommand = gitCommand;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GitRepo clone(String uri) {
        try {
            Path tempDir = Files.createTempDirectory("aelsi2_task241_");
            System.err.println("Cloning " + uri + " into " + tempDir.toString());

            ProcessBuilder pb = new ProcessBuilder();
            pb.environment().put("GIT_TERMINAL_PROMPT", "0");
            pb.command(gitCommand, "clone", uri, tempDir.toString());
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("Git returned non-zero exit code");
            }
            return new CliGitRepo(tempDir);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
