package ru.nsu.aeliseev2.task241.report;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import ru.nsu.aeliseev2.task241.model.Grade;
import ru.nsu.aeliseev2.task241.model.Group;
import ru.nsu.aeliseev2.task241.model.Student;
import ru.nsu.aeliseev2.task241.model.Task;
import ru.nsu.aeliseev2.task241.model.TaskDatabase;
import ru.nsu.aeliseev2.task241.model.TaskStatus;

/**
 * HTML report generator.
 */
public class HtmlReportGenerator implements ReportGenerator {
    record Report(List<ReportGroup> groups) {
    }

    record ReportGroup(String name, List<ReportTask> tasks, List<ReportGrade> grades) {
    }

    record ReportTask(String name, List<ReportTaskItem> students, boolean hasSoft) {
    }

    record ReportTaskItem(String studentName, TaskStatus status,
                          boolean softAccepted, boolean hardAccepted) {
    }

    record ReportGrade(String name, List<String> taskNames, List<ReportGradeItem> students) {
    }

    record ReportGradeItem(String studentName, List<Double> taskPoints, double totalPoints,
                           double gradeValue) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate(OutputStream outStream, TaskDatabase taskDatabase, List<Grade> grades) {
        List<ReportGroup> groups = new ArrayList<>();
        for (Group group : taskDatabase.getGroups()) {
            List<ReportTask> reportTasks = new ArrayList<>();
            for (Task task : taskDatabase.getTasks()) {
                List<ReportTaskItem> items = new ArrayList<>();
                for (Student student : group.students()) {
                    TaskStatus status = taskDatabase.getStatus(task, student);

                    Instant softActual = status.softAccepted;
                    Instant hardActual = status.hardAccepted;
                    boolean soft = softActual != null && (task.softDeadline() == null
                        || softActual.toEpochMilli() <= task.softDeadline().toEpochMilli());
                    boolean hard = hardActual != null && (task.hardDeadline() == null
                        || hardActual.toEpochMilli() <= task.hardDeadline().toEpochMilli());

                    items.add(
                        new ReportTaskItem(student.name(), status, soft, hard)
                    );
                }
                reportTasks.add(
                    new ReportTask(task.name(), items, task.softDeadline() != null)
                );
            }
            List<ReportGrade> reportGrades = new ArrayList<>();
            for (Grade grade : grades) {
                List<String> taskNames = new ArrayList<>();
                List<ReportGradeItem> students = new ArrayList<>();

                List<Task> tasks = grade.strategy().getTasks(taskDatabase);
                for (Task task : tasks) {
                    taskNames.add(task.name());
                }
                for (Student student : group.students()) {
                    double totalPoints = grade.strategy().getTotalPoints(student, taskDatabase);
                    List<Double> taskPoints = new ArrayList<>();
                    for (Task task : tasks) {
                        taskPoints.add(grade.strategy().getTaskPoints(task, student, taskDatabase));
                    }
                    double gradeValue = grade.strategy().calculate(student, taskDatabase);
                    students.add(new ReportGradeItem(
                        student.name(),
                        taskPoints,
                        totalPoints,
                        gradeValue
                    ));
                }
                reportGrades.add(new ReportGrade(grade.name(), taskNames, students));
            }
            groups.add(new ReportGroup(group.name(), reportTasks, reportGrades));
        }
        Report report = new Report(groups);

        try {
            Handlebars handlebars = new Handlebars();
            Template template = handlebars.compile("template.html");
            Writer writer = new OutputStreamWriter(outStream);
            template.apply(report, writer);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
