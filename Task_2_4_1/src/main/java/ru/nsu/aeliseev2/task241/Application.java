package ru.nsu.aeliseev2.task241;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import ru.nsu.aeliseev2.task241.checks.BuildCheck;
import ru.nsu.aeliseev2.task241.checks.CompositeCheck;
import ru.nsu.aeliseev2.task241.checks.JavadocCheck;
import ru.nsu.aeliseev2.task241.checks.StyleCheck;
import ru.nsu.aeliseev2.task241.checks.TaskCheck;
import ru.nsu.aeliseev2.task241.checks.TestCheck;
import ru.nsu.aeliseev2.task241.git.CliGitClient;
import ru.nsu.aeliseev2.task241.git.GitClient;
import ru.nsu.aeliseev2.task241.git.GitRepo;
import ru.nsu.aeliseev2.task241.model.Group;
import ru.nsu.aeliseev2.task241.model.Student;
import ru.nsu.aeliseev2.task241.model.Task;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;

public class Application {

    public static void main(String[] args) {
        GitClient git = new CliGitClient();
        TaskCheck check = new CompositeCheck(
            new BuildCheck(),
            new JavadocCheck(),
            new TestCheck(),
            new StyleCheck()
        );

        TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "Task_1_1_1",
            "primes",
            false,
            Instant.now(),
            Instant.now()
        ));
        taskDatabase.getTasks().add(new Task(
            "Task_1_1_2",
            "blackjack",
            false,
            Instant.now(),
            Instant.now()
        ));
        taskDatabase.getGroups().add(
            new Group("24213",
                List.of(
                    new Student("Andrey Eliseev", "aelsi2"),
                    new Student("Klim Sadov", "7AD0VNIK")
                ))
        );


        for (Group group : taskDatabase.getGroups()) {
            for (Student student : group.students()) {
                String uri = String.format("https://github.com/%s/OOP", student.githubUsername());
                try (GitRepo repo = git.clone(uri)) {
                    for (Task task : taskDatabase.getTasks()) {
                        Path taskDirectory = repo.directory().resolve(task.dirName());
                        if (Files.isDirectory(taskDirectory)) {
                            check.run(taskDirectory, taskDatabase.getStatus(task, student));
                        }
                    }
                } catch (Exception e) {
                    System.err.printf("Error while processing repo: %s\n", e);
                }
            }
        }
    }
}
