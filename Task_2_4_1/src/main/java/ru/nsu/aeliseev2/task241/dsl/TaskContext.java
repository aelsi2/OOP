package ru.nsu.aeliseev2.task241.dsl;

/**
 * Configuration DSL {@code task} context.
 */
public class TaskContext {
    /**
     * The name of the task directory in students' GitHub repositories.
     */
    public String dirName = null;

    /**
     * The pretty name of the task.
     */
    public String name = null;

    /**
     * Whether this task is an extra task required for the best grade.
     */
    public boolean isExtra = false;

    /**
     * The date of the soft deadline for this task in the format {@code yyyy-MM-dd}.
     */
    public String softDeadline = null;

    /**
     * The date of the hard deadline for this task in the format {@code yyyy-MM-dd}.
     */
    public String hardDeadline = null;
}
