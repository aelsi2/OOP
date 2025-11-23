package ru.nsu.aeliseev2.task141;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Represents a student's virtual grade book.
 */
public class GradeBook {
    private final long gradeBookId;
    private final ArrayList<HashMap<Subject, Grade>> semesterGrades;

    /**
     * Initializes a new instance of {@code GradeBook} class.
     *
     * @param gradeBookId    The ID of this grade book.
     * @param semesterGrades The list of subject-type mappings for each semester.
     */
    public GradeBook(long gradeBookId, List<Map<Subject, GradeType>> semesterGrades) {
        this.gradeBookId = gradeBookId;
        this.semesterGrades = new ArrayList<>();
        for (int semesterIndex = 0; semesterIndex < semesterGrades.size(); semesterIndex++) {
            var map = new HashMap<Subject, Grade>();
            for (var entry : semesterGrades.get(semesterIndex).entrySet()) {
                var subject = entry.getKey();
                var type = entry.getValue();
                int finalSemester = subject.finalSemester();
                if (finalSemester < 0 || finalSemester > semesterGrades.size()) {
                    throw new IllegalArgumentException(
                        String.format("Subject has invalid final semester: %s", subject)
                    );
                }
                if (!semesterGrades.get(finalSemester).containsKey(subject)) {
                    throw new IllegalArgumentException(
                        String.format("Missing grade type for last semester of subject: %s",
                            subject)
                    );
                }
                var grade = new Grade(subject, semesterIndex, type);
                map.put(subject, grade);
            }
            this.semesterGrades.add(map);
        }
    }

    /**
     * Gets the ID of this grade book.
     *
     * @return This grade book's numerical ID.
     */
    public long getGradeBookId() {
        return gradeBookId;
    }

    /**
     * Gets how many semesters there are in this grade book.
     *
     * @return The semester count
     */
    public int semesterCount() {
        return semesterGrades.size();
    }

    /**
     * Gets a list of grades for the specified semester.
     *
     * @param index The index of the semester to get the grades for.
     * @return The list of grades.
     * @throws NoSuchElementException The grade book doesn't have information about this semester.
     */
    public List<Grade> getSemesterGrades(int index) {
        if (index < 0 || index >= semesterGrades.size()) {
            throw new NoSuchElementException("No such semester");
        }
        return semesterGrades.get(index).values().stream().toList();
    }

    /**
     * Gets a grade for a certain semester and subject.
     *
     * @param semesterIndex The index of the semester.
     * @param subject       The subject.
     * @return The grade for this semester and subject.
     * @throws NoSuchElementException The grade book doesn't have this grade.
     */
    public Grade getGrade(int semesterIndex, Subject subject) {
        if (semesterIndex < 0 || semesterIndex >= semesterGrades.size()) {
            throw new NoSuchElementException("No such semester");
        }
        var semester = semesterGrades.get(semesterIndex);
        if (!semester.containsKey(subject)) {
            throw new NoSuchElementException("No such subject in semester");
        }
        return semester.get(subject);
    }

    /**
     * Gets the average grade for the entire grade book.
     *
     * @return The average grade (numerical representation).
     */
    public double getAverageGrade() {
        return semesterGrades.stream()
            .flatMap(hm -> hm.values().stream())
            .filter(Grade::countsTowardsAverage)
            .mapToInt(Grade::getNumericValue)
            .average().orElse(0);
    }

    /**
     * Checks if the student is eligible for transfer to budget quota, based on their grades from
     * the last semester.
     *
     * @return Is the student eligible (or would they be eligible if they were not already on budget
     *     quota).
     */
    public boolean canTransferToBudget() {
        if (semesterGrades.isEmpty()) {
            return false;
        }
        int lastSemester = semesterGrades.stream()
            .flatMap(hm -> hm.values().stream())
            .filter(Grade::hasGrade)
            .mapToInt(Grade::getSemesterIndex)
            .max().orElse(0);
        if (lastSemester == 0) {
            return false;
        }
        boolean lastGood = semesterGrades.get(lastSemester).values().stream()
            .allMatch(Grade::isGoodForBudget);
        boolean prevGood = semesterGrades.get(lastSemester - 1).values().stream()
            .allMatch(Grade::isGoodForBudget);
        return lastGood && prevGood;
    }

    /**
     * Checks if the student is eligible for getting increased scholarship, based on their grades
     * from the last semester.
     *
     * @return Is the student eligible.
     */
    public boolean canGetHighScholarship() {
        if (semesterGrades.isEmpty()) {
            return false;
        }
        int lastSemester = semesterGrades.stream()
            .flatMap(hm -> hm.values().stream())
            .filter(Grade::hasGrade)
            .mapToInt(Grade::getSemesterIndex)
            .max().orElse(0);
        return semesterGrades.get(lastSemester).values().stream()
            .allMatch(Grade::canBeExcellent);
    }

    /**
     * Checks if the student is eligible for getting a diploma with honors.
     *
     * @return Is the student eligible.
     */
    public boolean canGetDiplomaWithHonors() {
        var subjects = semesterGrades.stream()
            .flatMap(hm -> hm.keySet().stream())
            .collect(Collectors.toCollection(HashSet::new));
        var finalSemesterGrades = subjects.stream()
            .map(subject -> semesterGrades.get(subject.finalSemester()).get(subject))
            .collect(Collectors.toCollection(ArrayList::new));
        boolean allGradesGood = finalSemesterGrades.stream()
            .allMatch(Grade::isGoodForDiplomaWithHonors);
        if (!allGradesGood) {
            return false;
        }

        long differentiatedCount = finalSemesterGrades.stream()
            .filter(Grade::countsTowardsAverage)
            .count();
        long excellentCount = finalSemesterGrades.stream()
            .filter(Grade::countsTowardsAverage)
            .filter(Grade::canBeExcellent)
            .count();
        if (differentiatedCount != 0 && (double) excellentCount / differentiatedCount < 0.75) {
            return false;
        }

        return finalSemesterGrades.stream()
            .filter(grade -> grade.getGradeType().equals(GradeType.THESIS))
            .allMatch(Grade::canBeExcellent);
    }
}
