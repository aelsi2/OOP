package ru.nsu.aeliseev2.task113;

import java.util.Scanner;
import java.util.regex.Pattern;

public interface EvaluationContext {
    Pattern EQUALS_PATTERN = Pattern.compile("=");
    Pattern SEPARATOR_PATTERN = Pattern.compile(";");

    double getVariable(String name);

    static EvaluationContext scan(Scanner scanner) {
        var context = new HashMapEvaluationContext();
        while (scanner.hasNext()) {
            String name = scanner.next();
            scanner.next(EQUALS_PATTERN);
            double value = scanner.nextDouble();
            context.setVariable(name, value);
            if (!scanner.hasNext(SEPARATOR_PATTERN)) {
                break;
            }
            scanner.next(SEPARATOR_PATTERN);
        }
        return context;
    }

    static EvaluationContext fromString(String str) {
        return scan(new Scanner(str));
    }
}
