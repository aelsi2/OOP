package ru.nsu.aeliseev2.task113.parsing;

public sealed interface ExpressionTokenData {
    Symbol LEFT_PAREN = new Symbol("'('");
    Symbol RIGHT_PAREN = new Symbol("')'");
    Symbol PLUS = new Symbol("'+'");
    Symbol MINUS = new Symbol("'-'");
    Symbol MULTIPLY = new Symbol("'*'");
    Symbol DIVIDE = new Symbol("'/'");
    Symbol EOF = new Symbol("EOF");

    record Symbol(String text) implements ExpressionTokenData {
        @Override
        public String toString() {
            return text;
        }
    }

    record Name(String value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return "'" + value + "'";
        }
    }

    record Number(double value) implements ExpressionTokenData {
        @Override
        public String toString() {
            return "'" + value + "'";
        }
    }
}
