package ru.nsu.aeliseev2.task231.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ScoreWinConditionTests {
    @Test
    void zero() {
        var condition = new ScoreWinCondition(5);
        Assertions.assertFalse(condition.isWin(new int[100], 10, 10, 0));
    }

    @Test
    void belowWin() {
        var condition = new ScoreWinCondition(5);
        Assertions.assertFalse(condition.isWin(new int[100], 10, 10, 3));
    }

    @Test
    void atWin() {
        var condition = new ScoreWinCondition(5);
        Assertions.assertTrue(condition.isWin(new int[100], 10, 10, 5));
    }

    @Test
    void aboveWin() {
        var condition = new ScoreWinCondition(5);
        Assertions.assertTrue(condition.isWin(new int[100], 10, 10, 6));
    }
}
