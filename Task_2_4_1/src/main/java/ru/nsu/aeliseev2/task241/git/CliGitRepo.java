package ru.nsu.aeliseev2.task241.git;

import com.google.common.io.CharStreams;
import com.google.common.io.MoreFiles;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of {@code GitRepo} that uses the Git CLI tool directly.
 */
public class CliGitRepo implements GitRepo {
    private final Path repoDirectory;
    private final String gitCommand;

    /**
     * Initializes a new instance of {@code CliGitRepo}.
     *
     * @param gitCommand    The Git command name.
     * @param repoDirectory The directory the repository is located in.
     */
    public CliGitRepo(String gitCommand, Path repoDirectory) {
        this.gitCommand = gitCommand;
        this.repoDirectory = repoDirectory.toAbsolutePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> branches() {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.environment().put("GIT_TERMINAL_PROMPT", "0");
            pb.command(gitCommand, "-C", repoDirectory.toString(),
                "branch", "--format", "%(refname:short)");
            Process process = pb.start();

            List<String> result = new ArrayList<>();
            try (BufferedReader reader = process.inputReader()) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                String error;
                try (BufferedReader reader = process.errorReader()) {
                    error = CharStreams.toString(reader);
                }
                throw new RuntimeException("Git returned non-zero exit code:\n" + error);
            }
            return result;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkout(String branch) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.environment().put("GIT_TERMINAL_PROMPT", "0");
            pb.command(gitCommand, "-C", repoDirectory.toString(),
                "checkout", branch);
            Process process = pb.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                String error;
                try (BufferedReader reader = process.errorReader()) {
                    error = CharStreams.toString(reader);
                }
                throw new RuntimeException("Git returned non-zero exit code:\n" + error);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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
