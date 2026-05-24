package ru.nsu.aeliseev2.task212;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;
import ru.nsu.aeliseev2.task212.utils.WorkStatus;
import ru.nsu.aeliseev2.task212.utils.WorkUnit;

class WorkUnitTests {
    @Test
    void getStatusAllPrimes() {
        List<WorkUnit> workUnits = List.of(
            new WorkUnit(0, new long[0], 0, 0),
            new WorkUnit(1, new long[0], 0, 0),
            new WorkUnit(2, new long[0], 0, 0),
            new WorkUnit(3, new long[0], 0, 0),
            new WorkUnit(4, new long[0], 0, 0)
        );
        for (WorkUnit unit : workUnits) {
            unit.status = WorkStatus.ALL_PRIMES;
        }
        Assertions.assertEquals(WorkStatus.ALL_PRIMES, WorkUnit.getStatus(workUnits));
    }

    @Test
    void getStatusAllPrimesButOne() {
        List<WorkUnit> workUnits = List.of(
            new WorkUnit(0, new long[0], 0, 0),
            new WorkUnit(1, new long[0], 0, 0),
            new WorkUnit(2, new long[0], 0, 0),
            new WorkUnit(3, new long[0], 0, 0),
            new WorkUnit(4, new long[0], 0, 0)
        );
        for (WorkUnit unit : workUnits) {
            unit.status = WorkStatus.ALL_PRIMES;
        }
        workUnits.get(1).status = WorkStatus.WORKING;
        Assertions.assertEquals(WorkStatus.WORKING, WorkUnit.getStatus(workUnits));
    }

    @Test
    void getStatusAllWorking() {
        List<WorkUnit> workUnits = List.of(
            new WorkUnit(0, new long[0], 0, 0),
            new WorkUnit(1, new long[0], 0, 0),
            new WorkUnit(2, new long[0], 0, 0),
            new WorkUnit(3, new long[0], 0, 0),
            new WorkUnit(4, new long[0], 0, 0)
        );
        Assertions.assertEquals(WorkStatus.WORKING, WorkUnit.getStatus(workUnits));
    }

    @Test
    void getStatusHasComposites() {
        List<WorkUnit> workUnits = List.of(
            new WorkUnit(0, new long[0], 0, 0),
            new WorkUnit(1, new long[0], 0, 0),
            new WorkUnit(2, new long[0], 0, 0),
            new WorkUnit(3, new long[0], 0, 0),
            new WorkUnit(4, new long[0], 0, 0)
        );
        workUnits.get(2).status = WorkStatus.HAS_COMPOSITES;
        Assertions.assertEquals(WorkStatus.HAS_COMPOSITES, WorkUnit.getStatus(workUnits));
    }

    private static void checkUnits(Collection<WorkUnit> units,
                                   int startIndex, int endIndex, int numUnits) {
        var sortedUnits =
            units.stream().sorted(Comparator.comparingInt(a -> a.startIndex)).toList();
        Assertions.assertAll(
            () -> Assertions.assertEquals(numUnits, units.size()),
            () -> {
                for (int i = 0; i < sortedUnits.size() - 1; i++) {
                    Assertions.assertEquals(sortedUnits.get(i).endIndex,
                        sortedUnits.get(i + 1).startIndex);
                }
            },
            () -> Assertions.assertEquals(startIndex,
                sortedUnits.get(0).startIndex),
            () -> Assertions.assertEquals(endIndex,
                sortedUnits.get(sortedUnits.size() - 1).endIndex)
        );
    }

    @Test
    void splitEmpty() {
        var units = WorkUnit.split(new long[0], 0, 0, 10);
        checkUnits(units.values(), 0, 0, 10);
    }

    @Test
    void split10by3() {
        var units = WorkUnit.split(new long[10], 0, 10, 3);
        checkUnits(units.values(), 0, 10, 3);
    }

    @Test
    void split10by10() {
        var units = WorkUnit.split(new long[10], 0, 10, 10);
        checkUnits(units.values(), 0, 10, 10);
    }

    @Test
    void split10by20() {
        var units = WorkUnit.split(new long[10], 0, 10, 20);
        checkUnits(units.values(), 0, 10, 20);
    }

    @Test
    void split10by4withOffset() {
        var units = WorkUnit.split(new long[20], 5, 15, 4);
        checkUnits(units.values(), 5, 15, 4);
    }

    @Test
    void split15by3withOffset() {
        var units = WorkUnit.split(new long[24], 7, 22, 3);
        checkUnits(units.values(), 7, 22, 3);
    }

    @Test
    void toMessage() {
        long[] array = new long[]{1, 2, 3, 4};
        var expectedMessage = new WorkMessage(67, array, 1, 2);
        var workUnit = new WorkUnit(67, array, 1, 2);
        Assertions.assertEquals(expectedMessage, workUnit.toMessage());
    }
}
