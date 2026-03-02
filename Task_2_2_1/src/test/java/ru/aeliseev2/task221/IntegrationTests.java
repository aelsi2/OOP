package ru.aeliseev2.task221;

import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.RestaurantConfig;

class IntegrationTests {
    @Test
    void test() {
        String json = """
            {
                "orderTakers": [
                    {
                        "minDelayMillis": 50,
                        "maxDelayMillis": 250
                    }
                ],
                "bakers": [
                    {
                        "minDelayMillis": 30,
                        "maxDelayMillis": 300
                    },
                    {
                        "minDelayMillis": 35,
                        "maxDelayMillis": 200
                    }
                ],
                "deliveryMen": [
                    {
                        "minDelayMillis": 100,
                        "maxDelayMillis": 600,
                        "capacity": 2
                    },
                    {
                        "minDelayMillis": 100,
                        "maxDelayMillis": 600,
                        "capacity": 4
                    },
                    {
                        "minDelayMillis": 100,
                        "maxDelayMillis": 600,
                        "capacity": 3
                    }
                ],
                "toBakeCapacity": 20,
                "toDeliverCapacity": 5,
                "orderCount": 10
            }
            """;
        RestaurantConfig config = RestaurantConfig.fromJson(json);
        var restaurant = config.createRestaurant();
        restaurant.run();
    }
}
