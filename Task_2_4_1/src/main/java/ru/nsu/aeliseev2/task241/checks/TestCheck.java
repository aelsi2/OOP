package ru.nsu.aeliseev2.task241.checks;

import com.google.common.io.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import ru.nsu.aeliseev2.task241.model.TaskStatus;
import ru.nsu.aeliseev2.task241.model.TestResult;

/**
 * A check that executes {@code ./gradlew test}.
 */
public class TestCheck extends GradleCheck {
    /**
     * {@inheritDoc}
     */
    @Override
    protected final String[] args() {
        return new String[]{"test"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean run(Path project, TaskStatus taskStatus) {
        System.err.println("Testing " + project.toString());
        boolean result = super.run(project, taskStatus);
        try {
            int total = 0;
            int passed = 0;
            int skipped = 0;

            Path testDir = project.resolve("build/test-results/test");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            try (Stream<Path> files = java.nio.file.Files.list(testDir)) {
                for (Path file : files.toList()) {
                    if (!Files.getFileExtension(file.toString()).equals("xml")) {
                        continue;
                    }
                    Document doc = builder.parse(file.toFile());
                    int fileTests = Integer.parseInt(
                        doc.getDocumentElement().getAttribute("tests"));
                    int fileSkipped = Integer.parseInt(
                        doc.getDocumentElement().getAttribute("skipped"));
                    int fileFailures = Integer.parseInt(
                        doc.getDocumentElement().getAttribute("failures"));
                    int fileErrors = Integer.parseInt(
                        doc.getDocumentElement().getAttribute("errors"));
                    total += fileTests;
                    passed += fileTests - fileSkipped - fileFailures - fileErrors;
                    skipped += fileSkipped;
                }
            }

            taskStatus.tests = new TestResult(total, passed, skipped);
            System.err.printf(
                "Total: %d, Passed: %d, Skipped: %d%n",
                taskStatus.tests.total(), taskStatus.tests.passed(), taskStatus.tests.skipped()
            );
            return result;
        } catch (Exception e) {
            System.err.println("Test parse error:\n" + e);
            return false;
        }
    }
}
