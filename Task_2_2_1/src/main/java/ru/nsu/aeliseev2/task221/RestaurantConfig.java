package ru.nsu.aeliseev2.task221;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Random;

/**
 * Restaurant configuration class intended for serialization.
 */
public class RestaurantConfig {
    /**
     * Employee configuration class.
     */
    public static class EmployeeConfig {
        /**
         * The minimum amount of time in milliseconds spent working on each iteration.
         */
        public int minDelayMillis = 100;

        /**
         * The maximum amount of time in milliseconds spent working on each iteration.
         */
        public int maxDelayMillis = 2000;
    }

    /**
     * Delivery man configuration class.
     */
    public static class DeliveryManConfig extends EmployeeConfig {
        /**
         * The maximum number of orders the delivery man is able to carry at the same time.
         */
        public int capacity = 5;
    }

    /**
     * The order takers working in this restaurant.
     */
    public EmployeeConfig[] orderTakers = null;

    /**
     * The bakers working in this restaurant.
     */
    public EmployeeConfig[] bakers = null;

    /**
     * The delivery men working in this restaurant.
     */
    public DeliveryManConfig[] deliveryMen = null;

    /**
     * The capacity of the taker-baker order queue.
     */
    public int toBakeCapacity = 10;

    /**
     * The capacity of the baker-deliverer order queue.
     */
    public int toDeliverCapacity = 10;

    /**
     * The number of orders the restaurant will take.
     */
    public int orderCount = 100;

    /**
     * Parses a {@code RestaurantConfig} from a JSON string.
     *
     * @param json The JSON string to parse from.
     * @return The parsed {@code RestaurantConfig}.
     */
    public static RestaurantConfig fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, RestaurantConfig.class);
    }

    /**
     * Creates a new {@code Restaurant} based on this configuration.
     *
     * @return The created restaurant.
     */
    public Restaurant createRestaurant() {
        if (orderTakers == null || orderTakers.length == 0) {
            throw new IllegalStateException("At least one order taker is required");
        }
        if (bakers == null || bakers.length == 0) {
            throw new IllegalStateException("At least one baker is required");
        }
        if (deliveryMen == null || deliveryMen.length == 0) {
            throw new IllegalStateException("At least one delivery man is required");
        }

        final OrderFactory orderFactory = new SequentialOrderFactory(orderCount);
        final OrderQueue toBake = new OrderQueue(toBakeCapacity);
        final OrderQueue toDeliver = new OrderQueue(toDeliverCapacity);
        final OrderLogger logger = new PrintStreamOrderLogger(System.out);
        final ArrayList<Runnable> employees = new ArrayList<>();

        for (EmployeeConfig orderTaker : orderTakers) {
            final WorkingStrategy strategy =
                new RandomDelayWorkingStrategy(new Random(),
                    orderTaker.minDelayMillis, orderTaker.maxDelayMillis);
            employees.add(new OrderTaker(orderFactory, toBake, logger, strategy));
        }
        for (EmployeeConfig baker : bakers) {
            final WorkingStrategy strategy =
                new RandomDelayWorkingStrategy(new Random(),
                    baker.minDelayMillis, baker.maxDelayMillis);
            employees.add(new Baker(toBake, toDeliver, logger, strategy));
        }
        for (DeliveryManConfig deliveryMan : deliveryMen) {
            final WorkingStrategy strategy =
                new RandomDelayWorkingStrategy(new Random(),
                    deliveryMan.minDelayMillis, deliveryMan.maxDelayMillis);
            employees.add(new DeliveryMan(toDeliver, deliveryMan.capacity, logger, strategy));
        }

        return new Restaurant(employees);
    }
}
