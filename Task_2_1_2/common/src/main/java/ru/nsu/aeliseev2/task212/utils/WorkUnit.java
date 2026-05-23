package ru.nsu.aeliseev2.task212.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import ru.nsu.aeliseev2.task212.protocol.messages.WorkMessage;

/**
 * A unit of work scheduled on a server.
 */
public class WorkUnit {
    /**
     * The unique id of the work unit.
     */
    public final long id;

    /**
     * The array to find composite numbers in.
     */
    public final long[] data;

    /**
     * Search start index in the array.
     */
    public final int startIndex;

    /**
     * Search end index in the array.
     */
    public final int endIndex;

    /**
     * Status of this work unit.
     */
    public WorkStatus status;

    /**
     * Initializes a new instance of {@code WorkUnit}.
     *
     * @param id         The unique id of the work unit.
     * @param data       The array to find composite numbers in.
     * @param startIndex Search start index in the array.
     * @param endIndex   Search end index in the array.
     */
    public WorkUnit(long id, long[] data, int startIndex, int endIndex) {
        this.id = id;
        this.data = data;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.status = WorkStatus.WORKING;
    }

    /**
     * Converts this work unit into a message.
     *
     * @return The work message.
     */
    public WorkMessage toMessage() {
        return new WorkMessage(id, data, startIndex, endIndex);
    }

    /**
     * Splits a data array into a number of units.
     *
     * @param numbers    The array to find composite numbers in.
     * @param startIndex Search start index in the array.
     * @param endIndex   Search end index in the array.
     * @param numUnits   Number of units.
     * @return A map that contains the work units with their ids as keys.
     */
    public static Map<Long, WorkUnit> split(
        long[] numbers, int startIndex, int endIndex, int numUnits
    ) {
        long jobId = 0;

        final int numNumbers = endIndex - startIndex;
        final int numPerUnit = numNumbers / numUnits;
        final int numExtra = numNumbers % numUnits;

        HashMap<Long, WorkUnit> workUnits = new HashMap<>();
        for (int connIndex = 0; connIndex < numUnits; connIndex++) {
            int numStart = startIndex + numPerUnit * connIndex;
            int numEnd = numStart + numPerUnit + (connIndex == numUnits - 1 ? numExtra : 0);
            WorkUnit unit = new WorkUnit(jobId++, numbers, numStart, numEnd);
            workUnits.put(unit.id, unit);
        }
        return workUnits;
    }

    /**
     * Gets the status of a group of work units.
     *
     * @param units The group of work units.
     * @return The status of the group.
     */
    public static WorkStatus getStatus(Collection<WorkUnit> units) {
        for (WorkUnit unit : units) {
            if (unit.status == WorkStatus.HAS_COMPOSITES) {
                return WorkStatus.HAS_COMPOSITES;
            }
            if (unit.status == WorkStatus.WORKING) {
                return WorkStatus.WORKING;
            }
        }
        return WorkStatus.ALL_PRIMES;
    }
}
