package ru.nsu.aeliseev2.task241.checks;

import java.nio.file.Path;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * A check that executes {@code ./gradlew javadoc}.
 */
public class JavadocCheck extends GradleCheck {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final String[] args() {
        return new String[]{"javadoc", "-x", "test"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        System.err.println("Generating docs for " + project.toString());
        taskStatus.setDocPassed(super.run(project, taskStatus));
        return taskStatus.getDocPassed();
    }
}
