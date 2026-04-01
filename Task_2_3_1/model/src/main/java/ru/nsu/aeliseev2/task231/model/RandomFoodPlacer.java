package ru.nsu.aeliseev2.task231.model;

import java.util.Random;

/**
 * An implementation of {@code FoodPlacer} that keeps the specified amount of food bits on the game
 * field placing them randomly.
 */
@NoCoverageGenerated
public class RandomFoodPlacer implements FoodPlacer {
    private final int foodCount;
    private final Random random;

    /**
     * Initializes a new instance of {@code RandomFoodPlacer}.
     *
     * @param foodCount The number of bits of food always present at the game field.
     */
    public RandomFoodPlacer(int foodCount) {
        this.foodCount = foodCount;
        this.random = new Random();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void placeFood(int[] data, int width, int height, int score) {
        int actualFoodCount = 0;
        int emptySpaceCount = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == GameTile.FOOD.toInt()) {
                actualFoodCount += 1;
            }
            if (data[i] == GameTile.EMPTY.toInt()) {
                emptySpaceCount += 1;
            }
        }
        while (actualFoodCount < foodCount && emptySpaceCount > 0) {
            int targetIndex = random.nextInt(emptySpaceCount);
            int currentIndex = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i] != GameTile.EMPTY.toInt()) {
                    continue;
                }
                if (currentIndex == targetIndex) {
                    data[i] = GameTile.FOOD.toInt();
                    actualFoodCount += 1;
                    emptySpaceCount -= 1;
                    break;
                }
                currentIndex += 1;
            }
        }
    }
}
