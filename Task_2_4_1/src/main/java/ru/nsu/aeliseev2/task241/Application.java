package ru.nsu.aeliseev2.task241;

import java.nio.file.Path;
import java.time.Instant;
import ru.nsu.aeliseev2.task241.checks.BuildCheck;
import ru.nsu.aeliseev2.task241.checks.JavadocCheck;
import ru.nsu.aeliseev2.task241.checks.StyleCheck;
import ru.nsu.aeliseev2.task241.checks.TestCheck;
import ru.nsu.aeliseev2.task241.git.CliGitClient;
import ru.nsu.aeliseev2.task241.model.Task;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

public class Application {
    public static void main(String[] args) throws Exception {
        var git = new CliGitClient();
        var task = new Task("Task_1_1_1", "primes", Instant.now(), Instant.now());
        try (var repo = git.clone("https://github.com/aelsi2/OOP")) {
            Path directory = repo.directory().resolve(task.dirName());
            var status = new TaskStatus();
            System.err.println(new BuildCheck().run(directory, status));
            System.err.println(new JavadocCheck().run(directory, status));
            System.err.println(new TestCheck().run(directory, status));
            System.err.println(new StyleCheck().run(directory, status));
        }
    }
}
