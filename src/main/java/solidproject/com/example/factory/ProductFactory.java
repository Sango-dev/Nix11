package solidproject.com.example.factory;

import solidproject.com.example.model.NotifiableProduct;
import solidproject.com.example.model.Product;
import solidproject.com.example.model.ProductBundle;

import java.util.Random;

public final class ProductFactory {
    private static final Random RANDOM = new Random();

    private ProductFactory() {}

    public static Product generateRandomProduct() {
        Random random = new Random();
        if (random.nextBoolean()) {
            ProductBundle productBundle = new ProductBundle();
            productBundle.setAmount(random.nextInt(15));
            productBundle.setAvailable(random.nextBoolean());
            productBundle.setPrice(random.nextDouble());
            productBundle.setId(random.nextLong());
            productBundle.setTitle(random.nextFloat() + "" + random.nextDouble());
            return productBundle;
        } else {
            NotifiableProduct notifiableProduct = new NotifiableProduct();
            notifiableProduct.setId(random.nextLong());
            notifiableProduct.setTitle(random.nextFloat() + "" + random.nextDouble());
            notifiableProduct.setAvailable(random.nextBoolean());
            notifiableProduct.setChannel(random.nextBoolean() + "" + random.nextDouble());
            notifiableProduct.setPrice(random.nextDouble());
            return notifiableProduct;
        }
    }

}
