package ru.nsu.aeliseev2.task241.checks;

import java.nio.file.Path;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * An abstract check for a task.
 */
public interface TaskCheck {
    /**
     * Runs the check for the specified project and stores the result in the provided
     * {@code TaskStatus} instance.
     *
     * @param project    The project to run this check on.
     * @param taskStatus The task status to store the result in.
     * @return Whether the check passed.
     */
    boolean run(Path project, TaskStatus taskStatus);
}
