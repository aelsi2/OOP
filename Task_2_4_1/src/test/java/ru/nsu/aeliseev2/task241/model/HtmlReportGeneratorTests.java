package ru.nsu.aeliseev2.task241.model;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task241.report.HtmlReportGenerator;
import ru.nsu.aeliseev2.task241.report.ReportGenerator;

class HtmlReportGeneratorTests {
    record DummyGradeStrategy(int taskCount) implements GradeStrategy {
        @Override
        public List<Task> getTasks(TaskDatabase taskDatabase) {
            return taskDatabase.getTasks().subList(0, taskCount);
        }

        @Override
        public double getTaskPoints(Task task, Student student, TaskDatabase taskDatabase) {
            return 1.0;
        }

        @Override
        public double getTotalPoints(Student student, TaskDatabase taskDatabase) {
            return 67.0;
        }

        @Override
        public double calculate(Student student, TaskDatabase taskDatabase) {
            return 4.5;
        }
    }

    private static Template getTemplate() {
        try {
            return new Handlebars().compile("report.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getExpected() throws IOException {
        try (InputStream stream = HtmlReportGenerator.class
            .getResourceAsStream("/report.expected.txt")) {
            Assertions.assertNotNull(stream);
            return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(stream.readAllBytes())).toString();
        }
    }

    @Test
    void generate() throws IOException {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "1", "task1", "task", false,
            null, Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "2", "task2", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getGroups().add(new Group("group", List.of(
            new Student("Pesi Kotik", "M1KoTiK")
        )));
        taskDatabase.getGroups().add(new Group("24214", List.of(
            new Student("Nikolay Mashkin", "LookAsLukas")
        )));
        taskDatabase.getStatus("1", "M1KoTiK").buildPassed = true;
        taskDatabase.getStatus("1", "M1KoTiK").docPassed = false;
        taskDatabase.getStatus("1", "M1KoTiK").stylePassed = true;
        taskDatabase.getStatus("1", "M1KoTiK").tests = new TestResult(2, 2, 0);
        taskDatabase.getStatus("2", "M1KoTiK").tests = new TestResult(2, 0, 0);
        taskDatabase.getStatus("2", "M1KoTiK").extraPoints = 1;
        taskDatabase.getStatus("1", "LookAsLukas").softAccepted = Instant.EPOCH;
        taskDatabase.getStatus("2", "LookAsLukas").hardAccepted = Instant.EPOCH;
        List<Grade> grades = List.of(
            new Grade("Mid", new DummyGradeStrategy(1)),
            new Grade("Final", new DummyGradeStrategy(2))
        );
        ReportGenerator generator = new HtmlReportGenerator(getTemplate());
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        generator.generate(buffer, taskDatabase, grades);
        String actual = buffer.toString(StandardCharsets.UTF_8);
        Assertions.assertEquals(getExpected(), actual);
    }
}
