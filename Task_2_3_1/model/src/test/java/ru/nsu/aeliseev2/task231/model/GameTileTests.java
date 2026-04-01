package ru.nsu.aeliseev2.task231.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTileTests {
    @Test
    void emptyIndex() {
        Assertions.assertEquals(0, GameTile.EMPTY.toInt());
    }

    @Test
    void foodToFromInt() {
        Assertions.assertEquals(GameTile.FOOD, GameTile.fromInt(GameTile.FOOD.toInt()));
    }

    @Test
    void headToFromInt() {
        Assertions.assertEquals(GameTile.HEAD_L, GameTile.fromInt(GameTile.HEAD_L.toInt()));
    }

    @Test
    void bodyToFromInt() {
        Assertions.assertEquals(GameTile.BODY_L, GameTile.fromInt(GameTile.BODY_L.toInt()));
    }

    @Test
    void rotateBodyLeft() {
        Assertions.assertEquals(GameTile.BODY_L, GameTile.BODY_L.rotate(Direction.LEFT));
    }

    @Test
    void rotateBodyUp() {
        Assertions.assertEquals(GameTile.BODY_U, GameTile.BODY_L.rotate(Direction.UP));
    }

    @Test
    void rotateBodyRight() {
        Assertions.assertEquals(GameTile.BODY_R, GameTile.BODY_L.rotate(Direction.RIGHT));
    }

    @Test
    void rotateBodyDown() {
        Assertions.assertEquals(GameTile.BODY_D, GameTile.BODY_L.rotate(Direction.DOWN));
    }

    @Test
    void rotateHeadLeft() {
        Assertions.assertEquals(GameTile.HEAD_L, GameTile.HEAD_L.rotate(Direction.LEFT));
    }

    @Test
    void rotateHeadUp() {
        Assertions.assertEquals(GameTile.HEAD_U, GameTile.HEAD_L.rotate(Direction.UP));
    }

    @Test
    void rotateHeadRight() {
        Assertions.assertEquals(GameTile.HEAD_R, GameTile.HEAD_L.rotate(Direction.RIGHT));
    }

    @Test
    void rotateHeadDown() {
        Assertions.assertEquals(GameTile.HEAD_D, GameTile.HEAD_L.rotate(Direction.DOWN));
    }
}
