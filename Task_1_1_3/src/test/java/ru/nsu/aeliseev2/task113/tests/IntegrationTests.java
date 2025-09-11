package ru.nsu.aeliseev2.task113.tests;

import java.text.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task113.EvaluationContext;
import ru.nsu.aeliseev2.task113.expressions.Expression;
import ru.nsu.aeliseev2.task113.expressions.Number;

class IntegrationTests {
    @Test
    void expressionParseEval() throws ParseException {
        var context = EvaluationContext.parse("x = 1;y =2; z = 3");
        var expression = Expression.parse("(x + y * z - z) * 2");
        double result = expression.evaluate(context);
        Assertions.assertEquals(8, result);
    }

    @Test
    void expressionDerivative() throws ParseException {
        var expression = Expression.parse("2 * x*x + 5 * x - 10");
        var derivative = expression.differentiate("x").optimize();
        var context = EvaluationContext.parse("x=20");
        Assertions.assertEquals(85, derivative.evaluate(context));
    }

    @Test
    void multipleDerivatives() throws ParseException {
        var expression = Expression.parse("x * y");
        var derivative = expression
            .differentiate("x")
            .optimize()
            .differentiate("y")
            .optimize();
        Assertions.assertEquals(Number.ONE, derivative);
    }

    @Test
    void fromStringAndBack() throws ParseException {
        var expression = Expression.parse("-x + (x + 2) - 10 / 3");
        var newExpression = Expression.parse(expression.toString());
        Assertions.assertEquals(expression, newExpression);
    }
}
