package ru.nsu.aeliseev2.task241.report;

import java.io.OutputStream;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.Grade;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;

/**
 * Abstract report file generator that uses an arbitrary format.
 */
public interface ReportGenerator {
    /**
     * Generates the report file and outputs.
     *
     * @param outStream    The stream to write the report to.
     * @param taskDatabase The task database.
     * @param grades       The list of grades to calculate.
     */
    void generate(OutputStream outStream, TaskDatabase taskDatabase, List<Grade> grades);
}
