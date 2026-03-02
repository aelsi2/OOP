package ru.aeliseev2.task221;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.aeliseev2.task221.RestaurantConfig;

class RestaurantConfigTests {
    @Test
    void deserialize() {
        String json = """
            {
                "orderTakers": [
                    {
                        "minDelayMillis": 500,
                        "maxDelayMillis": 5000
                    }
                ],
                "bakers": [
                    {
                        "minDelayMillis": 300,
                        "maxDelayMillis": 3000
                    },
                    {
                        "minDelayMillis": 350,
                        "maxDelayMillis": 3050
                    }
                ],
                "deliveryMen": [
                    {
                        "minDelayMillis": 1000,
                        "maxDelayMillis": 6000,
                        "capacity": 10
                    },
                    {
                        "minDelayMillis": 1000,
                        "maxDelayMillis": 6000,
                        "capacity": 7
                    },
                    {
                        "minDelayMillis": 1000,
                        "maxDelayMillis": 6000,
                        "capacity": 12
                    }
                ],
                "toBakeCapacity": 20,
                "toDeliverCapacity": 5,
                "orderCount": 50
            }
            """;
        RestaurantConfig config = RestaurantConfig.fromJson(json);
        Assertions.assertAll(
            () -> Assertions.assertEquals(1, config.orderTakers.length),
            () -> Assertions.assertEquals(500, config.orderTakers[0].minDelayMillis),
            () -> Assertions.assertEquals(5000, config.orderTakers[0].maxDelayMillis)
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(2, config.bakers.length),
            () -> Assertions.assertEquals(300, config.bakers[0].minDelayMillis),
            () -> Assertions.assertEquals(3000, config.bakers[0].maxDelayMillis),
            () -> Assertions.assertEquals(350, config.bakers[1].minDelayMillis),
            () -> Assertions.assertEquals(3050, config.bakers[1].maxDelayMillis)
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(3, config.deliveryMen.length),
            () -> Assertions.assertEquals(1000, config.deliveryMen[0].minDelayMillis),
            () -> Assertions.assertEquals(6000, config.deliveryMen[0].maxDelayMillis),
            () -> Assertions.assertEquals(10, config.deliveryMen[0].capacity),
            () -> Assertions.assertEquals(1000, config.deliveryMen[1].minDelayMillis),
            () -> Assertions.assertEquals(6000, config.deliveryMen[1].maxDelayMillis),
            () -> Assertions.assertEquals(7, config.deliveryMen[1].capacity),
            () -> Assertions.assertEquals(1000, config.deliveryMen[2].minDelayMillis),
            () -> Assertions.assertEquals(6000, config.deliveryMen[2].maxDelayMillis),
            () -> Assertions.assertEquals(12, config.deliveryMen[2].capacity)
        );
        Assertions.assertEquals(20, config.toBakeCapacity);
        Assertions.assertEquals(5, config.toDeliverCapacity);
        Assertions.assertEquals(50, config.orderCount);
    }

    @Test
    void deserializeDefaultFields() {
        String json = """
            {
                "orderTakers": [{}],
                "bakers": [{}],
                "deliveryMen": [{}]
            }
            """;
        RestaurantConfig config = RestaurantConfig.fromJson(json);
        Assertions.assertAll(
            () -> Assertions.assertEquals(1, config.orderTakers.length),
            () -> Assertions.assertEquals(100, config.orderTakers[0].minDelayMillis),
            () -> Assertions.assertEquals(2000, config.orderTakers[0].maxDelayMillis)
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(1, config.bakers.length),
            () -> Assertions.assertEquals(100, config.bakers[0].minDelayMillis),
            () -> Assertions.assertEquals(2000, config.bakers[0].maxDelayMillis)
        );
        Assertions.assertAll(
            () -> Assertions.assertEquals(1, config.deliveryMen.length),
            () -> Assertions.assertEquals(100, config.deliveryMen[0].minDelayMillis),
            () -> Assertions.assertEquals(2000, config.deliveryMen[0].maxDelayMillis),
            () -> Assertions.assertEquals(5, config.deliveryMen[0].capacity)
        );
        Assertions.assertEquals(10, config.toBakeCapacity);
        Assertions.assertEquals(10, config.toDeliverCapacity);
        Assertions.assertEquals(100, config.orderCount);
    }

    @Test
    void create() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.bakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{
            new RestaurantConfig.DeliveryManConfig()
        };
        Assertions.assertNotNull(config.createRestaurant());
    }

    @Test
    void createNullOrderTakers() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = null;
        config.bakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{
            new RestaurantConfig.DeliveryManConfig()
        };
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }

    @Test
    void createEmptyOrderTakers() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{};
        config.bakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{
            new RestaurantConfig.DeliveryManConfig()
        };
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }

    @Test
    void createNullBakers() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.bakers = null;
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{
            new RestaurantConfig.DeliveryManConfig()
        };
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }

    @Test
    void createEmptyBakers() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.bakers = new RestaurantConfig.EmployeeConfig[]{};
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{
            new RestaurantConfig.DeliveryManConfig()
        };
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }

    @Test
    void createNullDeliveryMen() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.bakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.deliveryMen = null;
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }

    @Test
    void createEmptyDeliveryMen() {
        RestaurantConfig config = new RestaurantConfig();
        config.orderTakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.bakers = new RestaurantConfig.EmployeeConfig[]{
            new RestaurantConfig.EmployeeConfig()
        };
        config.deliveryMen = new RestaurantConfig.DeliveryManConfig[]{};
        Assertions.assertThrows(IllegalStateException.class, config::createRestaurant);
    }
}
