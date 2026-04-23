package ru.nsu.aeliseev2.task241.checks;

import java.nio.file.Path;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * A check that executes {@code ./gradlew build}.
 */
public class BuildCheck extends GradleCheck {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final String[] args() {
        return new String[]{"build", "-x", "test"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        System.err.println("Building " + project.toString());
        return taskStatus.buildPassed = super.run(project, taskStatus);
    }
}
