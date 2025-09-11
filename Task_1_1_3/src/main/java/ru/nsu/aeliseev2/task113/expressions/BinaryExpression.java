package ru.nsu.aeliseev2.task113.expressions;

import java.util.Objects;

/**
 * Represents an expression with two operands.
 */
public abstract class BinaryExpression extends Expression {
    protected final Expression left;
    protected final Expression right;

    /**
     * Constructs a new binary expression.
     *
     * @param left The first operand.
     * @param right The second operand.
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        var objBe = (BinaryExpression) obj;
        return left.equals(objBe.left) && right.equals(objBe.right);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getClass(), left, right);
    }
}
