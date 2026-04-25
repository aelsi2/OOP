package ru.nsu.aeliseev2.task241.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task241.checks.CompositeCheck;
import ru.nsu.aeliseev2.task241.checks.TaskCheck;

class CompositeCheckTests {
    static class UnreachableCheck implements TaskCheck {
        @Override
        public boolean run(Path project, TaskStatus taskStatus) {
            Assertions.fail();
            return false;
        }
    }

    record DummyCheck(boolean passes, List<TaskCheck> checks) implements TaskCheck {
        @Override
        public boolean run(Path project, TaskStatus taskStatus) {
            checks.add(this);
            return passes;
        }
    }

    @Test
    void allPass() {
        List<TaskCheck> executedChecks = new ArrayList<>();
        List<TaskCheck> checks = List.of(
            new DummyCheck(true, executedChecks),
            new DummyCheck(true, executedChecks),
            new DummyCheck(true, executedChecks)
        );
        TaskCheck check = new CompositeCheck(checks.toArray(new TaskCheck[0]));
        Assertions.assertTrue(check.run(null, null));
        Assertions.assertEquals(checks, executedChecks);
    }

    @Test
    void firstFails() {
        List<TaskCheck> executedChecks = new ArrayList<>();
        List<TaskCheck> checks = List.of(
            new DummyCheck(false, executedChecks),
            new UnreachableCheck(),
            new UnreachableCheck()
        );
        TaskCheck check = new CompositeCheck(checks.toArray(new TaskCheck[0]));
        Assertions.assertFalse(check.run(null, null));
        Assertions.assertEquals(checks.subList(0, 1), executedChecks);
    }
}
