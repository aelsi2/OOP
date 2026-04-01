package ru.nsu.aeliseev2.task231.model;

/**
 * A 2D axis-aligned direction.
 */
public enum Direction {
    /**
     * Left.
     */
    LEFT(-1, 0),

    /**
     * Up.
     */
    UP(0, -1),

    /**
     * Right.
     */
    RIGHT(1, 0),

    /**
     * Down.
     */
    DOWN(0, 1);

    /**
     * The X component of the direction.
     */
    public final int deltaX;

    /**
     * The Y component of the direction.
     */
    public final int deltaY;

    Direction(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }
}
