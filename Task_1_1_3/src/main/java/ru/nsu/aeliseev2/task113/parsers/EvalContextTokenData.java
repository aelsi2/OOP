package ru.nsu.aeliseev2.task113.parsers;

/**
 * Represents a specific token type used in {@code EvalContextLexer}.
 */
public sealed interface EvalContextTokenData {
    /**
     * The '=' token.
     */
    Symbol EQUALS = new Symbol("=");

    /**
     * The ';' token.
     */
    Symbol SEMICOLON = new Symbol(";");

    /**
     * The '-' token.
     */
    Symbol MINUS = new Symbol("-");

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
    record Eof() implements EvalContextTokenData {
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
    record Symbol(String text) implements EvalContextTokenData {
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
    record Name(String value) implements EvalContextTokenData {
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
    record Number(double value) implements EvalContextTokenData {
        @Override
        public String toString() {
            return ru.nsu.aeliseev2.task113.expressions.Number.formatValue(value);
        }
    }
}
