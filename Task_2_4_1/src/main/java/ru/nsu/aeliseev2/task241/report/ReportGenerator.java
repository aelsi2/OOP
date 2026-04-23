package ru.nsu.aeliseev2.task241.report;

import java.io.PrintStream;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.Grade;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;

public interface ReportGenerator {
    void generate(PrintStream outStream, TaskDatabase taskDatabase, List<Grade> grades);
}
