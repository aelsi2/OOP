package ru.nsu.aeliseev2.task241.model;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskDatabaseTests {
    @Test
    void getMissing() {
        TaskDatabase taskDatabase = new TaskDatabase();
        Task task = new Task(
            "task",
            "task",
            "task",
            false,
            Instant.EPOCH,
            Instant.EPOCH
        );
        Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        TaskStatus status = taskDatabase.getStatus(task, student);
        Assertions.assertAll(
            () -> Assertions.assertNotNull(status),
            () -> Assertions.assertFalse(status.buildPassed),
            () -> Assertions.assertFalse(status.docPassed),
            () -> Assertions.assertFalse(status.stylePassed),
            () -> Assertions.assertEquals(new TestResult(0, 0, 0), status.tests),
            () -> Assertions.assertNull(status.softAccepted),
            () -> Assertions.assertNull(status.hardAccepted),
            () -> Assertions.assertEquals(0, status.extraPoints)
        );
    }

    @Test
    void getExisting() {
        TaskDatabase taskDatabase = new TaskDatabase();
        Task task = new Task(
            "task",
            "task",
            "task",
            false,
            Instant.EPOCH,
            Instant.EPOCH
        );
        Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        TaskStatus status = taskDatabase.getStatus(task, student);
        TaskStatus actualStatus = taskDatabase.getStatus(task, student);
        Assertions.assertSame(actualStatus, status);
    }

    @Test
    void getDifferentSameStudent() {
        TaskDatabase taskDatabase = new TaskDatabase();
        Task task1 = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.EPOCH,
            Instant.EPOCH
        );
        Task task2 = new Task(
            "task2",
            "task",
            "task",
            false,
            Instant.EPOCH,
            Instant.EPOCH
        );
        Student student = new Student("Vonavi Navi Chivonavi", "vonyaet");
        TaskStatus status1 = taskDatabase.getStatus(task1, student);
        TaskStatus status2 = taskDatabase.getStatus(task2, student);
        TaskStatus actualStatus1 = taskDatabase.getStatus(task1, student);
        TaskStatus actualStatus2 = taskDatabase.getStatus(task2, student);
        Assertions.assertAll(
            () -> Assertions.assertSame(status1, actualStatus1),
            () -> Assertions.assertSame(status2, actualStatus2)
        );
    }

    @Test
    void getDifferent() {
        TaskDatabase taskDatabase = new TaskDatabase();
        Task task = new Task(
            "task1",
            "task",
            "task",
            false,
            Instant.EPOCH,
            Instant.EPOCH
        );
        Student student1 = new Student("Vonavi Navi Chivonavi", "vonyaet");
        Student student2 = new Student("Pesi Kotik", "M1KoTiK");
        TaskStatus status1 = taskDatabase.getStatus(task, student1);
        TaskStatus status2 = taskDatabase.getStatus(task, student2);
        TaskStatus actualStatus1 = taskDatabase.getStatus(task, student1);
        TaskStatus actualStatus2 = taskDatabase.getStatus(task, student2);
        Assertions.assertAll(
            () -> Assertions.assertSame(status1, actualStatus1),
            () -> Assertions.assertSame(status2, actualStatus2)
        );
    }

    @Test
    void groupsTasksPassthrough() {
        List<Task> tasks = List.of();
        List<Group> groups = List.of();
        TaskDatabase taskDatabase = new TaskDatabase(tasks, groups);
        List<Task> actualTasks = taskDatabase.getTasks();
        List<Group> actualGroups = taskDatabase.getGroups();
        Assertions.assertAll(
            () -> Assertions.assertSame(tasks, actualTasks),
            () -> Assertions.assertSame(groups, actualGroups)
        );
    }
}
