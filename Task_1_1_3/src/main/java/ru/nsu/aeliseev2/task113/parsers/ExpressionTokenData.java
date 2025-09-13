package ru.nsu.aeliseev2.task113.parsers;

/**
 * Represents a specific token type used in {@code ExpressionLexer}.
 */
public sealed interface ExpressionTokenData {
    /**
     * The '(' token.
     */
    Symbol LEFT_PAREN = new Symbol("(");

    /**
     * The ')' token.
     */
    Symbol RIGHT_PAREN = new Symbol(")");

    /**
     * The '+' token.
     */
    Symbol PLUS = new Symbol("+");

    /**
     * The '-' token.
     */
    Symbol MINUS = new Symbol("-");

    /**
     * The '*' token.
     */
    Symbol MULTIPLY = new Symbol("*");

    /**
     * The '/' token.
     */
    Symbol DIVIDE = new Symbol("/");

    /**
     * The 'NaN' token.
     */
    Symbol NAN = new Symbol("NaN");

    /**
     * The 'Inf' token.
     */
    Symbol INF = new Symbol("Inf");

    /**
     * The token representing the end of the file.
     */
    Eof EOF = new Eof();

    /**
     * A token representing the end of the file.
     */
    record Eof() implements ExpressionTokenData {
        @Override
        public String toString() {
            return "{EOF}";
        }
    }

    /**
     * A keyword or a special character.
     *
     * @param text The text representation of the token.
     */
    record Symbol(String text) implements ExpressionTokenData {
        @Override
        public String toString() {
            return text;
        }
    }

    /**
     * A variable name token.
     *
     * @param value The name of the variable.
     */
    record Name(String value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * A finite number token.
     *
     * @param value The value of the number.
     */
    record Number(double value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return new ru.nsu.aeliseev2.task113.expressions.Number(value).toString();
        }
    }
}
