package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultGradeStrategyTests {
    @Test
    void getTasksAll() {
        TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(
            new Task(
                "task1",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(10000),
                Instant.ofEpochMilli(20000)
            )
        );
        taskDatabase.getTasks().add(
            new Task(
                "task2",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(30000),
                Instant.ofEpochMilli(40000)
            )
        );
        taskDatabase.getTasks().add(
            new Task(
                "task3",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(50000),
                Instant.ofEpochMilli(60000)
            )
        );
        GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(70000));
        Assertions.assertEquals(taskDatabase.getTasks(), strategy.getTasks(taskDatabase));
    }

    @Test
    void getTasksThreshold() {
        TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(
            new Task(
                "task1",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(10000),
                Instant.ofEpochMilli(20000)
            )
        );
        taskDatabase.getTasks().add(
            new Task(
                "task2",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(30000),
                Instant.ofEpochMilli(40000)
            )
        );
        taskDatabase.getTasks().add(
            new Task(
                "task3",
                "task",
                "task",
                false,
                Instant.ofEpochMilli(50000),
                Instant.ofEpochMilli(60000)
            )
        );
        GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(
            List.of(
                taskDatabase.getTasks().get(0),
                taskDatabase.getTasks().get(1)
            ),
            strategy.getTasks(taskDatabase)
        );
    }

    @Test
    void getTaskPointsNone() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(10000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(0, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsSoftInTime() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(10000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setSoftAccepted(Instant.ofEpochMilli(9000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(0.5, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsHardInTime() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(10000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setSoftAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(task, student).setHardAccepted(Instant.ofEpochMilli(17000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(0.5, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsBothInTime() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(10000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(task, student).setHardAccepted(Instant.ofEpochMilli(10000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(1, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsBothLate() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(10000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setSoftAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(task, student).setHardAccepted(Instant.ofEpochMilli(30000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(0, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsNoSoft() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            null,
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setHardAccepted(Instant.ofEpochMilli(10000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(1, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTaskPointsExtra() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        final Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(15000),
            Instant.ofEpochMilli(20000)
        );
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getTasks().add(task);
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(task, student).setSoftAccepted(Instant.ofEpochMilli(12000));
        taskDatabase.getStatus(task, student).setHardAccepted(Instant.ofEpochMilli(17000));
        taskDatabase.getStatus(task, student).setExtraPoints(1);

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(2, strategy.getTaskPoints(task, student, taskDatabase));
    }

    @Test
    void getTotalPointsNone() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(15000),
            Instant.ofEpochMilli(20000)
        ));
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(0, strategy.getTotalPoints(student, taskDatabase));
    }

    @Test
    void getTotalPointsNonZero() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(15000),
            Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2",
            "task",
            "task",
            false,
            Instant.ofEpochMilli(25000),
            Instant.ofEpochMilli(30000)
        ));
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));
        taskDatabase.getStatus(taskDatabase.getTasks().get(0), student).setSoftAccepted(
            Instant.ofEpochMilli(16000)
        );
        taskDatabase.getStatus(taskDatabase.getTasks().get(0), student).setHardAccepted(
            Instant.ofEpochMilli(19000)
        );
        taskDatabase.getStatus(taskDatabase.getTasks().get(1), student).setSoftAccepted(
            Instant.ofEpochMilli(24000)
        );
        taskDatabase.getStatus(taskDatabase.getTasks().get(1), student).setHardAccepted(
            Instant.ofEpochMilli(29000)
        );
        taskDatabase.getStatus(taskDatabase.getTasks().get(1), student).setExtraPoints(0.75);

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(50000));
        Assertions.assertEquals(2.25, strategy.getTotalPoints(student, taskDatabase));
    }

    @Test
    void calculate2() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", false,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertTrue(1 < value && value < 3);
    }

    @Test
    void calculate3() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", false,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));
        taskDatabase.getStatus(tasks.get(2), student).setSoftAccepted(Instant.ofEpochMilli(45000));
        taskDatabase.getStatus(tasks.get(2), student).setHardAccepted(Instant.ofEpochMilli(55000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertTrue(2 < value && value < 4);
    }

    @Test
    void calculate4() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", false,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));
        taskDatabase.getStatus(tasks.get(2), student).setSoftAccepted(Instant.ofEpochMilli(45000));
        taskDatabase.getStatus(tasks.get(2), student).setHardAccepted(Instant.ofEpochMilli(55000));
        taskDatabase.getStatus(tasks.get(3), student).setSoftAccepted(Instant.ofEpochMilli(65000));
        taskDatabase.getStatus(tasks.get(3), student).setHardAccepted(Instant.ofEpochMilli(75000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertTrue(4 <= value && value < 5);
    }

    @Test
    void calculate5NoExtra() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", false,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));
        taskDatabase.getStatus(tasks.get(2), student).setSoftAccepted(Instant.ofEpochMilli(45000));
        taskDatabase.getStatus(tasks.get(2), student).setHardAccepted(Instant.ofEpochMilli(55000));
        taskDatabase.getStatus(tasks.get(3), student).setSoftAccepted(Instant.ofEpochMilli(65000));
        taskDatabase.getStatus(tasks.get(3), student).setHardAccepted(Instant.ofEpochMilli(75000));
        taskDatabase.getStatus(tasks.get(4), student).setSoftAccepted(Instant.ofEpochMilli(85000));
        taskDatabase.getStatus(tasks.get(4), student).setHardAccepted(Instant.ofEpochMilli(95000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertEquals(5, value);
    }

    @Test
    void calculate5ExtraNotMet() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", true,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));
        taskDatabase.getStatus(tasks.get(2), student).setSoftAccepted(Instant.ofEpochMilli(45000));
        taskDatabase.getStatus(tasks.get(2), student).setHardAccepted(Instant.ofEpochMilli(55000));
        taskDatabase.getStatus(tasks.get(3), student).setSoftAccepted(Instant.ofEpochMilli(65000));
        taskDatabase.getStatus(tasks.get(3), student).setHardAccepted(Instant.ofEpochMilli(75000));
        taskDatabase.getStatus(tasks.get(3), student).setExtraPoints(1);

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertTrue(4 <= value && value < 5);
    }

    @Test
    void calculate5ExtraMet() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", true,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", true,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));
        taskDatabase.getStatus(tasks.get(2), student).setSoftAccepted(Instant.ofEpochMilli(45000));
        taskDatabase.getStatus(tasks.get(2), student).setHardAccepted(Instant.ofEpochMilli(55000));
        taskDatabase.getStatus(tasks.get(3), student).setSoftAccepted(Instant.ofEpochMilli(65000));
        taskDatabase.getStatus(tasks.get(3), student).setHardAccepted(Instant.ofEpochMilli(75000));
        taskDatabase.getStatus(tasks.get(4), student).setSoftAccepted(Instant.ofEpochMilli(85000));
        taskDatabase.getStatus(tasks.get(4), student).setHardAccepted(Instant.ofEpochMilli(95000));

        final GradeStrategy strategy = new DefaultGradeStrategy(Instant.ofEpochMilli(105000));
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertEquals(5, value);
    }

    @Test
    void calculateNaN() {
        final TaskDatabase taskDatabase = new TaskDatabase();
        taskDatabase.getTasks().add(new Task(
            "task1", "task", "task", false,
            Instant.ofEpochMilli(10000), Instant.ofEpochMilli(20000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task2", "task", "task", false,
            Instant.ofEpochMilli(30000), Instant.ofEpochMilli(40000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task3", "task", "task", false,
            Instant.ofEpochMilli(50000), Instant.ofEpochMilli(60000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task4", "task", "task", false,
            Instant.ofEpochMilli(70000), Instant.ofEpochMilli(80000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task5", "task", "task", false,
            Instant.ofEpochMilli(90000), Instant.ofEpochMilli(100000)
        ));
        taskDatabase.getTasks().add(new Task(
            "task6", "task", "task", false,
            Instant.ofEpochMilli(110000), Instant.ofEpochMilli(120000)
        ));
        List<Task> tasks = taskDatabase.getTasks();
        final Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        taskDatabase.getGroups().add(new Group("group", List.of(student)));

        taskDatabase.getStatus(tasks.get(0), student).setSoftAccepted(Instant.ofEpochMilli(5000));
        taskDatabase.getStatus(tasks.get(0), student).setHardAccepted(Instant.ofEpochMilli(15000));
        taskDatabase.getStatus(tasks.get(1), student).setSoftAccepted(Instant.ofEpochMilli(25000));
        taskDatabase.getStatus(tasks.get(1), student).setHardAccepted(Instant.ofEpochMilli(35000));

        final GradeStrategy strategy = new DefaultGradeStrategy(
            Instant.now().plus(10, ChronoUnit.DAYS)
        );
        final double value = strategy.calculate(student, taskDatabase);
        Assertions.assertTrue(Double.isNaN(value));
    }
}
