package ru.nsu.aeliseev2.task231.model;

/**
 * A tile on the game field.
 */
public enum GameTile {
    /**
     * An empty tile.
     */
    EMPTY(false, 0, 0, Direction.RIGHT),

    /**
     * A body segment facing left.
     */
    BODY_L(true, 0, 0, Direction.LEFT),

    /**
     * A body segment facing up.
     */
    BODY_U(true, 0, 0, Direction.UP),

    /**
     * A body segment facing right.
     */
    BODY_R(true, 0, 0, Direction.RIGHT),

    /**
     * A body segment facing down.
     */
    BODY_D(true, 0, 0, Direction.DOWN),

    /**
     * The head facing left.
     */
    HEAD_L(true, 0, 0, Direction.LEFT),

    /**
     * The head facing up.
     */
    HEAD_U(true, 0, 0, Direction.UP),

    /**
     * The head facing right.
     */
    HEAD_R(true, 0, 0, Direction.RIGHT),

    /**
     * The head facing down.
     */
    HEAD_D(true, 0, 0, Direction.DOWN),

    /**
     * A bit of food.
     */
    FOOD(false, 1, 1, Direction.RIGHT);

    /**
     * Whether the head can collide with the tile.
     */
    public final boolean hasCollision;

    /**
     * How many segments the snake grows by after crossing this tile.
     */
    public final int growLength;

    /**
     * How many points get added after crossing this tile.
     */
    public final int scoreIncrease;

    /**
     * The direction this tile is facing (if it makes sense for the tile).
     */
    public final Direction direction;

    GameTile(boolean hasCollision, int growLength, int scoreIncrease, Direction direction) {
        this.hasCollision = hasCollision;
        this.growLength = growLength;
        this.scoreIncrease = scoreIncrease;
        this.direction = direction;
    }

    /**
     * Converts a tile's integer representation back to the corresponding tile.
     *
     * @param value The integer representation.
     * @return The original tile.
     */
    public static GameTile fromInt(int value) {
        return GameTile.values()[value];
    }

    /**
     * Converts the tile to its integer representation.
     *
     * @return The integer representation of the tile.
     */
    public int toInt() {
        return this.ordinal();
    }

    /**
     * Rotates the tile to face the specified direction.
     *
     * @param direction The direction.
     * @return The rotated tile.
     */
    public GameTile rotate(Direction direction) {
        return fromInt(toInt() + direction.ordinal());
    }
}
