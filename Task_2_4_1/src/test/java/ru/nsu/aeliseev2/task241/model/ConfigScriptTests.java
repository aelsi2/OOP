package ru.nsu.aeliseev2.task241.model;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task241.dsl.ConfigScript;

class ConfigScriptTests {
    static void execute(String resourcePath, TaskDatabase taskDatabase, List<Grade> grades) {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setScriptBaseClass(ConfigScript.class.getCanonicalName());
        Binding binding = new Binding();
        binding.setVariable("taskDatabase", taskDatabase);
        binding.setVariable("scriptPath", Path.of("."));
        binding.setVariable("gradeList", grades);
        GroovyShell shell = new GroovyShell(binding, config);
        try (InputStream stream = ConfigScriptTests.class.getResourceAsStream(resourcePath)) {
            Assertions.assertNotNull(stream);
            ByteBuffer bytes = ByteBuffer.wrap(stream.readAllBytes());
            String scriptText = StandardCharsets.UTF_8.decode(bytes).toString();
            shell.parse(scriptText).run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void groups() {
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        execute("/scripts/groups.groovy", taskDatabase, grades);
        List<Group> expected = List.of(
            new Group(
                "24213",
                List.of(
                    new Student("Andrey Eliseev", "aelsi2"),
                    new Student("Klim Sadov", "7AD0VNIK")
                )
            ),
            new Group(
                "24214",
                List.of(
                    new Student("Matvey Zenin", "Proletcultist")
                )
            )
        );
        Assertions.assertEquals(expected, taskDatabase.getGroups());
    }

    @Test
    void tasks() {
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        execute("/scripts/tasks.groovy", taskDatabase, grades);
        List<Task> expected = List.of(
            new Task(
                "Task_1_1_1",
                "1.1.1",
                "Heap sort",
                false,
                null,
                LocalDate.of(2025, 9, 13)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            ),
            new Task(
                "Task_1_1_2",
                "1.1.2",
                "Console blackjack",
                false,
                LocalDate.of(2025, 9, 20)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                LocalDate.of(2025, 9, 27)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            ),
            new Task(
                "Task_1_1_3",
                "1.1.3",
                "Equations",
                false,
                LocalDate.of(2025, 10, 4)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                LocalDate.of(2025, 10, 11)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            ),
            new Task(
                "Task_1_2_1",
                "1.2.1",
                "Graph",
                false,
                LocalDate.of(2025, 10, 18)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                LocalDate.of(2025, 11, 1)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            ),
            new Task(
                "Task_1_2_2",
                "1.2.2",
                "Hash table",
                true,
                LocalDate.of(2025, 11, 8)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                LocalDate.of(2025, 11, 15)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            )
        );
        Assertions.assertEquals(expected, taskDatabase.getTasks());
    }

    @Test
    void grades() {
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        execute("/scripts/grades.groovy", taskDatabase, grades);
        Assertions.assertAll(
            () -> Assertions.assertEquals(2, grades.size()),
            () -> Assertions.assertEquals("Mid-semester", grades.get(0).name()),
            () -> Assertions.assertEquals("Final", grades.get(1).name()),
            () -> Assertions.assertEquals(1,
                grades.get(0).strategy().getTasks(taskDatabase).size()),
            () -> Assertions.assertEquals(2,
                grades.get(1).strategy().getTasks(taskDatabase).size())
        );
    }

    @Test
    void review() {
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        execute("/scripts/review.groovy", taskDatabase, grades);
        Assertions.assertAll(
            () -> Assertions.assertEquals(
                LocalDate.of(2025, 9, 12)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                taskDatabase.getStatus("1", "aelsi2").hardAccepted
            ),
            () -> Assertions.assertEquals(2,
                taskDatabase.getStatus("1", "aelsi2").extraPoints
            ),
            () -> Assertions.assertEquals(
                LocalDate.of(2025, 9, 12)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                taskDatabase.getStatus("1", "7AD0VNIK").softAccepted
            ),
            () -> Assertions.assertEquals(
                LocalDate.of(2025, 9, 12)
                    .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(),
                taskDatabase.getStatus("2", "7AD0VNIK").hardAccepted
            )
        );
    }

    @Test
    void badDate() {
        TaskDatabase taskDatabase = new TaskDatabase();
        List<Grade> grades = new ArrayList<>();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            execute("/scripts/bad_date.groovy", taskDatabase, grades);
        });
    }
}
