package ru.nsu.aeliseev2.task141;

/**
 * Represents a grade in the grade book.
 */
public class Grade {
    private final Subject subject;
    private final int semesterIndex;
    private final GradeType gradeType;
    private GradeValue gradeValue;

    /**
     * Initializes a new instance of {@code Grade} class.
     *
     * @param subject       The subject this grade is for.
     * @param semesterIndex The index of the semester this grade is for.
     * @param gradeType     The type of the grade.
     */
    public Grade(Subject subject, int semesterIndex, GradeType gradeType) {
        this.subject = subject;
        this.semesterIndex = semesterIndex;
        this.gradeType = gradeType;
        this.gradeValue = null;
    }

    /**
     * Gets the subject this grade is for.
     *
     * @return The subject.
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Gets the index of the semester this grade is for.
     *
     * @return The index of the semester.
     */
    public int getSemesterIndex() {
        return semesterIndex;
    }

    /**
     * Gets the type of this grade.
     *
     * @return The grade type.
     */
    public GradeType getGradeType() {
        return gradeType;
    }

    /**
     * Gets the value of this grade.
     *
     * @return The grade value (can be null).
     */
    public GradeValue getGradeValue() {
        return gradeValue;
    }

    /**
     * Sets the value of this grade.
     *
     * @param gradeValue The grade value (can be null).
     */
    public void setGradeValue(GradeValue gradeValue) {
        this.gradeValue = gradeValue;
    }

    /**
     * Checks if this grade has a value.
     *
     * @return {@code true} if this grade has a non-{@code null} value, {@code false} otherwise.
     */
    public boolean hasGrade() {
        return this.gradeValue != null;
    }

    /**
     * Checks if this grade should be counted towards averages.
     *
     * @return {@code true} if this grade type is differentiated and has a non-{@code null} value,
     *     {@code false} otherwise.
     * @see GradeType#isDifferentiated()
     */
    public boolean countsTowardsAverage() {
        return this.gradeType.isDifferentiated() && hasGrade();
    }

    /**
     * Gets the numeric value of this grade.
     *
     * @return The numeric equivalent of the grade's value, if it is non-{@code null}, zero
     *     otherwise.
     * @see GradeValue#toInt()
     */
    public int getNumericValue() {
        if (hasGrade()) {
            return getGradeValue().toInt();
        } else {
            return 0;
        }
    }

    /**
     * Checks if this grade is good for transferring to budget quota.
     *
     * @return {@code true} if this grade has a {@code null} value, or the value is above the
     *     threshold for the grade type, {@code false} otherwise.
     * @see GradeType#isBudgetSatAllowed()
     */
    public boolean isGoodForBudget() {
        if (!hasGrade()) {
            return true;
        }
        if (getGradeType().isBudgetSatAllowed()) {
            return getGradeValue().compareTo(GradeValue.SATISFACTORY) >= 0;
        } else {
            return getGradeValue().compareTo(GradeValue.GOOD) >= 0;
        }
    }

    /**
     * Checks if this grade is good for getting a diploma with honors.
     *
     * @return {@code true} if this grade has a {@code null} value, or the value is
     *     {@code GradeValue.GOOD} or above, {@code false} otherwise.
     */
    public boolean isGoodForDiplomaWithHonors() {
        return !hasGrade() || getGradeValue().compareTo(GradeValue.SATISFACTORY) >= 0;
    }

    /**
     * Checks if the grade is can have the value {@code GradeValue.EXCELLENT}.
     *
     * @return {@code true} if this grade has a {@code null} value, or the value is
     *     {@code GradeValue.EXCELLENT}, {@code false} otherwise.
     */
    public boolean canBeExcellent() {
        return !hasGrade() || getGradeValue().equals(GradeValue.EXCELLENT);
    }
}
