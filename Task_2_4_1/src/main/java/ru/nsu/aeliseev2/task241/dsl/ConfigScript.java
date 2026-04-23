package ru.nsu.aeliseev2.task241.dsl;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.aeliseev2.task241.model.Grade;
import ru.nsu.aeliseev2.task241.model.Group;
import ru.nsu.aeliseev2.task241.model.Task;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;

/**
 * A configuration script.
 */
public abstract class ConfigScript extends Script {
    private TaskDatabase taskDatabase() {
        return (TaskDatabase) getBinding().getVariable("taskDatabase");
    }

    private Path scriptPath() {
        return (Path) getBinding().getVariable("scriptPath");
    }

    private List<Grade> gradeList() {
        //noinspection unchecked
        return (List<Grade>) getBinding().getVariable("gradeList");
    }

    /**
     * Loads and runs the script from the specified file.
     *
     * @param path         The path to the script.
     * @param taskDatabase The task database to write to.
     * @param grades       The grade list to write to.
     * @throws IOException An error occurred while reading the script file.
     */
    public static void execute(
        Path path,
        TaskDatabase taskDatabase,
        List<Grade> grades
    ) throws IOException {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(ConfigScript.class.getCanonicalName());
        Binding binding = new Binding();
        binding.setVariable("taskDatabase", taskDatabase);
        binding.setVariable("scriptPath", path.toAbsolutePath().getParent());
        binding.setVariable("gradeList", grades);
        GroovyShell shell = new GroovyShell(binding, config);
        shell.parse(path.toFile()).run();
    }

    /**
     * Includes a file in the script.
     *
     * @param path The path to the file to include.
     */
    public void include(String path) {
        try {
            execute(scriptPath().resolve(path), taskDatabase(), gradeList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Begins constructing a new group.
     *
     * @param name        The pretty name of the group.
     * @param groupConfig The closure to configure the group with.
     */
    public void group(String name, Closure<Object> groupConfig) {
        GroupsContext context = new GroupsContext();
        groupConfig.setDelegate(context);
        groupConfig.setResolveStrategy(Closure.DELEGATE_ONLY);
        groupConfig.run();
        taskDatabase().getGroups().add(new Group(name, context.students));
    }

    /**
     * Begins constructing a new task.
     *
     * @param name       The pretty name of the task.
     * @param taskConfig The closure to configure the task with.
     */
    public void task(String name, Closure<Object> taskConfig) {
        TaskContext context = new TaskContext();
        context.name = name;
        taskConfig.setDelegate(context);
        taskConfig.setResolveStrategy(Closure.DELEGATE_ONLY);
        taskConfig.run();
        if (context.name == null) {
            throw new IllegalArgumentException("Task must have a name");
        }
        if (context.dirName == null) {
            throw new IllegalArgumentException("Task must have a directory name");
        }
        Instant softDeadline = null;
        Instant hardDeadline = null;
        if (context.softDeadline != null) {
            try {
                softDeadline = LocalDate.parse(
                    context.softDeadline,
                    DateTimeFormatter.ISO_LOCAL_DATE
                ).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "Soft deadline must be a string in the yyyy-MM-dd format");
            }
        }
        if (context.hardDeadline != null) {
            try {
                hardDeadline = LocalDate.parse(
                    context.hardDeadline,
                    DateTimeFormatter.ISO_LOCAL_DATE
                ).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            } catch (Exception e) {
                throw new IllegalArgumentException(
                    "Hard deadline must be a string in the yyyy-MM-dd format");
            }
        }
        taskDatabase().getTasks().add(new Task(
            context.dirName,
            context.name,
            context.isExtra,
            softDeadline,
            hardDeadline
        ));
    }

    /**
     * Begins a new review.
     *
     * @param date         The date of the review in the format {@code yyyy-MM-dd}.
     * @param reviewConfig The closure to configure the review with.
     */
    void review(String date, Closure<Object> reviewConfig) {
        Instant reviewDate;
        try {
            reviewDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                "Review date must be a string in the yyyy-MM-dd format");
        }
        ReviewContext context = new ReviewContext(reviewDate, taskDatabase());
        reviewConfig.setDelegate(context);
        reviewConfig.setResolveStrategy(Closure.DELEGATE_ONLY);
        reviewConfig.run();
    }

    /**
     * Begins the definition of one or multiple grades.
     *
     * @param gradesConfig The closure to configure grades with.
     */
    void grades(Closure<Object> gradesConfig) {
        GradesContext context = new GradesContext(gradeList());
        gradesConfig.setDelegate(context);
        gradesConfig.setResolveStrategy(Closure.DELEGATE_ONLY);
        gradesConfig.run();
    }
}
