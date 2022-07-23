package ua.com.alevel.hw3.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.hw3.model.Manufacturer;
import ua.com.alevel.hw3.model.Phone;
import ua.com.alevel.hw3.model.TechProduct;

import java.lang.reflect.Field;
import java.util.*;


class TechProductRepositoryTest {

    private TechProductRepository target;

    private TechProduct product;

    @BeforeEach
    void setUp() {
        target = new TechProductRepository();
        final Random random = new Random();
        product = new Phone(
                "Model-" + random.nextInt(200),
                Manufacturer.APPLE,
                random.nextInt(500),
                random.nextDouble(1000.0),
                random.nextInt(2, 16),
                random.nextInt(2000, 5550)
        );
    }

    @Test
    void save() {
        target.save(product);
        final List<TechProduct> products = target.getAll();
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(products.get(0).getId(), product.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertNotEquals(1, actualResult.size());
    }

    @Test
    void saveAll_singleProduct() {
        target.saveAll(Collections.singletonList(product));
        final List<TechProduct> products = target.getAll();
        Assertions.assertEquals(1, products.size());
        Assertions.assertEquals(products.get(0).getId(), product.getId());
    }

    @Test
    void saveAll_hasNullProduct() {
        List<TechProduct> products = new ArrayList<>();
        products.add(product);
        products.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(products));
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(products.get(0).getId(), product.getId());
    }

    @Test
    void saveAll_manyPhone() {
        final TechProduct otherProduct = new Phone(
                "Model-200",
                Manufacturer.APPLE,
                500,
                1000.0,
                8,
                3100
        );

        target.saveAll(List.of(product, otherProduct));
        final List<TechProduct> products = target.getAll();
        Assertions.assertEquals(2, products.size());
        Assertions.assertEquals(product.getId(), products.get(0).getId());
        Assertions.assertEquals(otherProduct.getId(), products.get(1).getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(List.of(product, product)));
    }

    @Test
    void update() throws NoSuchFieldException, IllegalAccessException {
        final double price = 999.9;
        target.save(product);
        TechProduct otherProduct = new Phone(
                product.getModel(),
                product.getManufacturer(),
                product.getCount(),
                price,
                ((Phone) product).getCoreNumbers(),
                ((Phone) product).getBatteryPower()
        );

        Field field = otherProduct.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(otherProduct, product.getId());

        final boolean result = target.update(otherProduct);
        Assertions.assertTrue(result);
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(price, actualResult.get(0).getPrice());
    }

    @Test
    void update_WrongID() {
        final int count = 5;
        target.save(product);
        TechProduct otherProduct = new Phone(
                product.getModel(),
                product.getManufacturer(),
                count,
                product.getPrice(),
                ((Phone) product).getCoreNumbers(),
                ((Phone) product).getBatteryPower()
        );
        final boolean result = target.update(otherProduct);
        Assertions.assertFalse(result);
    }

    @Test
    void delete() {
        target.save(product);
        final boolean result = target.delete(product.getId());
        Assertions.assertTrue(result);
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_WrongID() {
        target.save(product);
        final boolean result = target.delete("1000-12002-3ere");
        Assertions.assertFalse(result);
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void findById() {
        target.save(product);
        final Optional<TechProduct> optionalProduct = target.findById(product.getId());
        Assertions.assertTrue(optionalProduct.isPresent());
        final TechProduct actualProduct = optionalProduct.get();
        Assertions.assertEquals(product.getId(), actualProduct.getId());
    }

    @Test
    void findById_WrongID() {
        target.save(product);
        final Optional<TechProduct> optionalProduct = target.findById("1000-12002-3ere");
        Assertions.assertFalse(optionalProduct.isPresent());
    }

    @Test
    void getAll() {
        target.save(product);
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noProducts() {
        final List<TechProduct> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }
}