package ru.nsu.aeliseev2.task113.parsing;

public sealed interface EvalContextTokenData {
    Symbol EQUALS = new Symbol("=");
    Symbol SEMICOLON = new Symbol(";");
    Symbol MINUS = new Symbol("-");
    Symbol NAN = new Symbol("NaN");
    Symbol INF = new Symbol("Inf");
    Eof EOF = new Eof();

    record Eof() implements EvalContextTokenData {
        @Override
        public String toString() {
            return "{EOF}";
        }
    }

    record Symbol(String text) implements EvalContextTokenData {
        @Override
        public String toString() {
            return text;
        }
    }

    record Name(String value) implements EvalContextTokenData {
        @Override
        public String toString() {
            return value;
        }
    }

    record Number(double value) implements EvalContextTokenData {
        @Override
        public String toString() {
            return new ru.nsu.aeliseev2.task113.expressions.Number(value).toString();
        }
    }
}
