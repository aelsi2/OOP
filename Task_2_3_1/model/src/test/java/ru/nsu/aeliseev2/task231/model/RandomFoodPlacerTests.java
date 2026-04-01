package ru.nsu.aeliseev2.task231.model;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomFoodPlacerTests {
    @Test
    void empty() {
        int width = 10;
        int height = 10;
        int[] data = new int[width * height];
        var placer = new RandomFoodPlacer(5);
        placer.placeFood(data, width, height, 0);

        long count = Arrays.stream(data).filter(t -> t == GameTile.FOOD.toInt()).count();
        Assertions.assertEquals(5, count);
    }

    @Test
    void tooMany() {
        int width = 10;
        int height = 10;
        int[] data = new int[width * height];
        data[0] = GameTile.FOOD.toInt();
        data[1] = GameTile.FOOD.toInt();
        data[2] = GameTile.FOOD.toInt();
        data[3] = GameTile.FOOD.toInt();
        var placer = new RandomFoodPlacer(3);
        placer.placeFood(data, width, height, 0);

        long count = Arrays.stream(data).filter(t -> t == GameTile.FOOD.toInt()).count();
        Assertions.assertEquals(4, count);
    }

    @Test
    void notEnoughSpaceForAll() {
        int width = 10;
        int height = 10;
        int[] data = new int[width * height];
        Arrays.fill(data, GameTile.BODY_L.toInt());
        data[0] = GameTile.EMPTY.toInt();
        var placer = new RandomFoodPlacer(3);
        placer.placeFood(data, width, height, 0);

        long count = Arrays.stream(data).filter(t -> t == GameTile.FOOD.toInt()).count();
        Assertions.assertEquals(1, count);
    }

    @Test
    void noSpace() {
        int width = 10;
        int height = 10;
        int[] data = new int[width * height];
        Arrays.fill(data, GameTile.BODY_L.toInt());
        var placer = new RandomFoodPlacer(10);
        placer.placeFood(data, width, height, 0);

        long count = Arrays.stream(data).filter(t -> t == GameTile.FOOD.toInt()).count();
        Assertions.assertEquals(0, count);
    }
}
