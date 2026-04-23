package ru.nsu.aeliseev2.task241.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A database that stores the task status for each task+student pair.
 */
public class TaskDatabase {
    private record Key(String taskName, String githubUsername) {
    }

    private final List<Task> tasks;
    private final List<Group> groups;
    private final HashMap<Key, TaskStatus> statusMap;

    /**
     * Initializes a new instance of {@code TaskDatabase}.
     */
    public TaskDatabase() {
        this(new ArrayList<>(), new ArrayList<>());
    }

    /**
     * Initializes a new instance of {@code TaskDatabase}.
     *
     * @param tasks  The list of all tasks.
     * @param groups The list of all students.
     */
    public TaskDatabase(List<Task> tasks, List<Group> groups) {
        this.tasks = tasks;
        this.groups = groups;
        this.statusMap = new HashMap<>();
    }

    /**
     * Gets the status for the specified task+student pair.
     *
     * @param task    The task.
     * @param student The student.
     * @return The task status for the specified task+student.
     */
    public TaskStatus getStatus(Task task, Student student) {
        return getStatus(task.dirName(), student.githubUsername());
    }

    /**
     * Gets the status for the specified task+student pair.
     *
     * @param task    The task name.
     * @param student The student username.
     * @return The task status for the specified task+student.
     */
    public TaskStatus getStatus(String task, String student) {
        final Key key = new Key(task, student);
        TaskStatus status = statusMap.getOrDefault(key, null);
        if (status == null) {
            status = new TaskStatus();
            statusMap.put(key, status);
        }
        return status;
    }

    /**
     * Gets the list of all known groups.
     *
     * @return The list of groups.
     */
    public List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets the list of all known tasks.
     *
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }
}
