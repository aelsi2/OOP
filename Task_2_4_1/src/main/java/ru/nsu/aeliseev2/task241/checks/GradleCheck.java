package ru.nsu.aeliseev2.task241.checks;

import com.google.common.collect.ObjectArrays;
import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

public abstract class GradleCheck implements TaskCheck {
    /**
     * The task to build with Gradle.
     *
     * @return The name of the task.
     */
    protected abstract String[] args();

    private void makeGradlewExecutable(Path project) throws IOException {
        if (!FileSystems.getDefault().supportedFileAttributeViews().contains("posix")) {
            return;
        }
        Path gradlewPath = project.resolve("gradlew");
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(gradlewPath);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        perms.add(PosixFilePermission.GROUP_EXECUTE);
        perms.add(PosixFilePermission.OTHERS_EXECUTE);
        Files.setPosixFilePermissions(gradlewPath, perms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        try {
            makeGradlewExecutable(project);
            Process process = new ProcessBuilder()
                .directory(project.toFile())
                .command(ObjectArrays.concat("./gradlew", args()))
                .start();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                String error;
                try (BufferedReader reader = process.errorReader()) {
                    error = CharStreams.toString(reader);
                }
                throw new RuntimeException("Gradle returned non-zero exit code:\n" + error);
            }
            return true;
        } catch (Exception e) {
            System.err.println("Gradle build error:\n" + e);
            return false;
        }
    }
}
