package ru.nsu.aeliseev2.task241;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.aeliseev2.task241.checks.BuildCheck;
import ru.nsu.aeliseev2.task241.checks.CompositeCheck;
import ru.nsu.aeliseev2.task241.checks.JavadocCheck;
import ru.nsu.aeliseev2.task241.checks.StyleCheck;
import ru.nsu.aeliseev2.task241.checks.TaskCheck;
import ru.nsu.aeliseev2.task241.checks.TestCheck;
import ru.nsu.aeliseev2.task241.dsl.ConfigScript;
import ru.nsu.aeliseev2.task241.git.CliGitClient;
import ru.nsu.aeliseev2.task241.git.GitClient;
import ru.nsu.aeliseev2.task241.git.GitRepo;
import ru.nsu.aeliseev2.task241.model.Grade;
import ru.nsu.aeliseev2.task241.model.Group;
import ru.nsu.aeliseev2.task241.model.Student;
import ru.nsu.aeliseev2.task241.model.Task;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;
import ru.nsu.aeliseev2.task241.report.HtmlReportGenerator;
import ru.nsu.aeliseev2.task241.report.ReportGenerator;

public class Application {
    private static final String DEFAULT_CONFIG = "check.groovy";
    private static final String REPO_URI = "https://github.com/%s/OOP";

    public static void main(String[] args) {
        String configPath = args.length >= 1 ? args[0] : DEFAULT_CONFIG;
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        try {
            ConfigScript.execute(Path.of(configPath), taskDatabase, grades);
        } catch (Exception e) {
            System.err.println("Error while reading config file:");
            e.printStackTrace(System.err);
            return;
        }

        GitClient git = new CliGitClient();
        TaskCheck check = new CompositeCheck(
            new BuildCheck(),
            new JavadocCheck(),
            new TestCheck(),
            new StyleCheck()
        );
        ReportGenerator reportGenerator = new HtmlReportGenerator();

        for (Group group : taskDatabase.getGroups()) {
            for (Student student : group.students()) {
                String uri = String.format(REPO_URI, student.githubUsername());
                try (GitRepo repo = git.clone(uri)) {
                    for (Task task : taskDatabase.getTasks()) {
                        Path taskDirectory = repo.directory().resolve(task.dirName());
                        if (Files.isDirectory(taskDirectory)) {
                            check.run(taskDirectory, taskDatabase.getStatus(task, student));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error while processing repo:");
                    e.printStackTrace(System.err);
                }
            }
        }
        reportGenerator.generate(System.out, taskDatabase, grades);
    }
}
