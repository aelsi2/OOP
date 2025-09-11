package ru.nsu.aeliseev2.task113.expressions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import ru.nsu.aeliseev2.task113.EvaluationContext;

/**
 * A constant number expression.
 */
public class Number extends Expression {
    public final static Number ZERO = new Number(0);
    public final static Number ONE = new Number(1);
    private final static DecimalFormat FORMAT = new DecimalFormat("0.##########",
        DecimalFormatSymbols.getInstance(Locale.ENGLISH));

    private final double value;

    /**
     * Constructs a new number expression.
     *
     * @param value The value of the number.
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Gets the constant value of expression.
     *
     * @return The value of the expression.
     */
    public double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double evaluate(EvaluationContext context) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression differentiate(String variableName) {
        return ZERO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Expression optimize() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Number number)) {
            return false;
        }
        if (Double.isNaN(value) && Double.isNaN(number.value)) {
            return true;
        }
        return value == number.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (Double.isNaN(value)) {
            return "NaN";
        }
        if (Double.isInfinite(value)) {
            if (value > 0) {
                return "Inf";
            } else {
                return "-Inf";
            }
        }
        return FORMAT.format(value);
    }
}
