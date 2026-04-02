package ru.nsu.aeliseev2.task231.model;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GameTests {
    @Test
    void mapSize() {
        var game = new Game(228, 69,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
            });
        Assertions.assertAll(
            () -> Assertions.assertEquals(228, game.getMapWidth()),
            () -> Assertions.assertEquals(69, game.getMapHeight()),
            () -> Assertions.assertEquals(228 * 69, game.getData().length)
        );
    }

    @Test
    void snakeGrow() {
        var game = new Game(10, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        for (int i = 0; i < 5; i++) {
            game.tick(Direction.RIGHT);
        }
        Assertions.assertAll(
            () -> Assertions.assertEquals(5, game.getScore()),
            () -> Assertions.assertEquals(6, snakeLength(game.getData()))
        );
    }

    @Test
    void snakeNoGrow() {
        var game = new Game(10, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
            });
        for (int i = 0; i < 5; i++) {
            game.tick(Direction.RIGHT);
        }
        Assertions.assertAll(
            () -> Assertions.assertEquals(0, game.getScore()),
            () -> Assertions.assertEquals(1, snakeLength(game.getData()))
        );
    }

    @Test
    void snakeZigZag() {
        var game = new Game(10, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                if (score < 5) {
                    for (int i = 0; i < data.length; i++) {
                        if (data[i] == GameTile.EMPTY.toInt()) {
                            data[i] = GameTile.FOOD.toInt();
                        }
                    }
                } else {
                    for (int i = 0; i < data.length; i++) {
                        if (data[i] == GameTile.FOOD.toInt()) {
                            data[i] = GameTile.EMPTY.toInt();
                        }
                    }
                }
            });
        for (int i = 0; i < 5; i++) {
            game.tick(Direction.RIGHT);
        }
        for (int i = 0; i < 2; i++) {
            game.tick(Direction.UP);
            game.tick(Direction.LEFT);
            game.tick(Direction.UP);
            game.tick(Direction.RIGHT);
        }
        Assertions.assertAll(
            () -> Assertions.assertEquals(5, game.getScore()),
            () -> Assertions.assertEquals(6, snakeLength(game.getData()))
        );
    }

    @Test
    void winCondition() {
        var game = new Game(10, 10,
            (data, width, height, score) -> score == 5,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
        };
        Assertions.assertAll(
            () -> Assertions.assertArrayEquals(new GameState[]{
                GameState.RUNNING,
                GameState.RUNNING,
                GameState.RUNNING,
                GameState.RUNNING,
                GameState.WIN,
            }, states),
            () -> Assertions.assertEquals(5, game.getScore()),
            () -> Assertions.assertEquals(6, snakeLength(game.getData()))
        );
    }

    @Test
    void selfCollide() {
        var game = new Game(10, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.UP),
            game.tick(Direction.UP),
            game.tick(Direction.LEFT),
            game.tick(Direction.LEFT),
            game.tick(Direction.DOWN),
            game.tick(Direction.DOWN)
        };
        Assertions.assertArrayEquals(new GameState[]{
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.LOSE,
        }, states);
    }

    @Test
    void leftWallCollide() {
        var game = new Game(10, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.UP),
            game.tick(Direction.UP),
            game.tick(Direction.LEFT),
            game.tick(Direction.LEFT),
            game.tick(Direction.LEFT),
        };
        Assertions.assertArrayEquals(new GameState[]{
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.LOSE,
        }, states);
    }

    @Test
    void rightWallCollide() {
        var game = new Game(5, 10,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
            game.tick(Direction.RIGHT),
        };
        Assertions.assertArrayEquals(new GameState[]{
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.LOSE,
        }, states);
    }

    @Test
    void topWallCollide() {
        var game = new Game(5, 5,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.UP),
            game.tick(Direction.UP),
            game.tick(Direction.UP),
        };
        Assertions.assertArrayEquals(new GameState[]{
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.LOSE,
        }, states);
    }

    @Test
    void bottomWallCollide() {
        var game = new Game(5, 5,
            (data, width, height, score) -> false,
            (data, width, height, score) -> {
                for (int i = 0; i < data.length; i++) {
                    if (data[i] == GameTile.EMPTY.toInt()) {
                        data[i] = GameTile.FOOD.toInt();
                    }
                }
            });
        GameState[] states = {
            game.tick(Direction.DOWN),
            game.tick(Direction.DOWN),
            game.tick(Direction.DOWN),
        };
        Assertions.assertArrayEquals(new GameState[]{
            GameState.RUNNING,
            GameState.RUNNING,
            GameState.LOSE,
        }, states);
    }

    private static int snakeLength(int[] data) {
        return (int) Arrays.stream(data)
            .filter(i -> i != GameTile.FOOD.toInt() && i != GameTile.EMPTY.toInt())
            .count();
    }
}
