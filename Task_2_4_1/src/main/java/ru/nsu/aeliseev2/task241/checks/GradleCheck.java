package ru.nsu.aeliseev2.task241.checks;

import com.google.common.collect.ObjectArrays;
import com.google.common.io.CharStreams;
import java.io.BufferedReader;
import java.nio.file.Path;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

public abstract class GradleCheck implements TaskCheck {
    /**
     * The task to build with Gradle.
     *
     * @return The name of the task.
     */
    protected abstract String[] args();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        try {
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
