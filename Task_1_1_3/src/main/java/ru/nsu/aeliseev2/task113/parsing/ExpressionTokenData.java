package ru.nsu.aeliseev2.task113.parsing;

public sealed interface ExpressionTokenData {
    Symbol LEFT_PAREN = new Symbol("(");
    Symbol RIGHT_PAREN = new Symbol(")");
    Symbol PLUS = new Symbol("+");
    Symbol MINUS = new Symbol("-");
    Symbol MULTIPLY = new Symbol("*");
    Symbol DIVIDE = new Symbol("/");
    Symbol NAN = new Symbol("NaN");
    Symbol INF = new Symbol("Inf");
    Eof EOF = new Eof();

    record Eof() implements ExpressionTokenData {
        @Override
        public String toString() {
            return "{EOF}";
        }
    }

    record Symbol(String text) implements ExpressionTokenData {
        @Override
        public String toString() {
            return text;
        }
    }

    record Name(String value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return value;
        }
    }

    record Number(double value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return new ru.nsu.aeliseev2.task113.expressions.Number(value).toString();
        }
    }
}
