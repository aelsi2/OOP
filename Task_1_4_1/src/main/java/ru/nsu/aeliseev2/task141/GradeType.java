package ru.nsu.aeliseev2.task141;

/**
 * Represents the type of {@code Grade}.
 *
 * @see Grade
 */
public enum GradeType {
    /**
     * Exam.
     */
    EXAM(true, false),

    /**
     * Differentiated credit.
     */
    DIFFERENTIATED_CREDIT(true, true),

    /**
     * Regular credit (binary).
     */
    BINARY_CREDIT(false, true),

    /**
     * Practice defense.
     */
    PRACTICE(true, false),

    /**
     * Thesis defense.
     */
    THESIS(true, false);

    private final boolean differentiated;
    private final boolean budgetSatAllowed;

    GradeType(boolean differentiated, boolean budgetSatAllowed) {
        this.differentiated = differentiated;
        this.budgetSatAllowed = budgetSatAllowed;
    }

    /**
     * Gets if grades of this type are differentiated (non-binary).
     *
     * @return Is this grade type differentiated.
     */
    public boolean isDifferentiated() {
        return differentiated;
    }

    /**
     * Gets if grades of this type can be satisfactory when considering transferring to budget
     * quota.
     *
     * @return Does this grade type allow {@code GradeValue.SATISFACTORY} when transferring.
     */
    public boolean isBudgetSatAllowed() {
        return budgetSatAllowed;
    }
}
