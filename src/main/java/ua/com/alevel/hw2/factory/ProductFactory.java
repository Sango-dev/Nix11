package ua.com.alevel.hw2.factory;

import ua.com.alevel.hw2.model.*;

import java.util.Random;

public class ProductFactory {

    private static final Random RANDOM = new Random();

    private ProductFactory() {
    }

    public static TechProduct createProduct(TechProductType type) {
        return switch (type) {
            case WASHING_MACHINE -> new WashingMachine(
                    "Model-" + RANDOM.nextInt(200),
                    getRandomManufacturer(),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    RANDOM.nextInt(800, 1000)
            );
            case MOUSE -> new Mouse(
                    "Model-" + RANDOM.nextInt(200),
                    getRandomManufacturer(),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    getRandomConnectionType(),
                    RANDOM.nextInt(1000, 8000)
            );
            case PHONE -> new Phone(
                    "Model-" + RANDOM.nextInt(200),
                    getRandomManufacturer(),
                    RANDOM.nextInt(500),
                    RANDOM.nextDouble(1000.0),
                    RANDOM.nextInt(2, 16),
                    RANDOM.nextInt(2000, 5550)
            );
            default -> throw new IllegalArgumentException("Unknown Product type: " + type);
        };
    }

    private static Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    private static ConnectionType getRandomConnectionType() {
        final ConnectionType[] values = ConnectionType.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }


}
