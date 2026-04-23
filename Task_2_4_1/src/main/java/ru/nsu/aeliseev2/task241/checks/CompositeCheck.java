package ru.nsu.aeliseev2.task241.checks;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * A composite {@code TaskCheck} that runs multiple checks sequentially and stops if one of them
 * fails.
 */
public class CompositeCheck implements TaskCheck {
    private final List<TaskCheck> checks;

    /**
     * Initializes a new instance of {@code CompositeCheck}.
     *
     * @param checks The array of task checks to combine.
     */
    public CompositeCheck(TaskCheck... checks) {
        this.checks = Arrays.stream(checks).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        for (TaskCheck check : checks) {
            if (!check.run(project, taskStatus)) {
                return false;
            }
        }
        return true;
    }
}
