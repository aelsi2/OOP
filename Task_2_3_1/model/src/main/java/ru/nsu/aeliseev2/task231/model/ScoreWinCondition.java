package ru.nsu.aeliseev2.task231.model;

/**
 * An implementation of {@code WinCondition} that makes the player win the game when the specified
 * score is reached.
 */
public class ScoreWinCondition implements WinCondition {
    private final int scoreToWin;

    /**
     * Initializes a new instance of {@code ScoreWinCondition}.
     *
     * @param scoreToWin The score needed to win the game.
     */
    public ScoreWinCondition(int scoreToWin) {
        this.scoreToWin = scoreToWin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWin(int[] data, int width, int height, int score) {
        return score >= scoreToWin;
    }
}
