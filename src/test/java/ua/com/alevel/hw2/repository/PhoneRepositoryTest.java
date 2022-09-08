package ua.com.alevel.hw2.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.model.product.TechProductType;

import java.lang.reflect.Field;
import java.util.*;

class PhoneRepositoryTest {

    private static PhoneRepository target;

    private Phone phone;

    @BeforeEach
    void setUp() {
        target = PhoneRepository.initializeAndGetInstance();
        final Random random = new Random();
        phone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
    }

    @Test
    void save() {
        target.save(phone);
        final List<Phone> phones = target.getAll();
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
        final List<Phone> actualResult = target.getAll();
        Assertions.assertNotEquals(1, actualResult.size());
    }

    @Test
    void saveAll_singleProduct() {
        target.saveAll(Collections.singletonList(phone));
        final List<Phone> phones = target.getAll();
        Assertions.assertEquals(1, phones.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
    }

    @Test
    void saveAll_hasNullProduct() {
        List<Phone> phones = new ArrayList<>();
        phones.add(phone);
        phones.add(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(phones));
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(phones.get(0).getId(), phone.getId());
    }

    @Test
    void saveAll_manyPhone() {
        final Phone otherPhone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
        target.saveAll(List.of(phone, otherPhone));
        final List<Phone> phones = target.getAll();
        Assertions.assertEquals(2, phones.size());
        Assertions.assertEquals(phone.getId(), phones.get(0).getId());
        Assertions.assertEquals(otherPhone.getId(), phones.get(1).getId());
    }

    @Test
    void saveAll_hasDuplicates() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.saveAll(List.of(phone, phone)));
    }

    @Test
    void update() throws NoSuchFieldException, IllegalAccessException {
        final double price = 999.9;
        target.save(phone);
        Phone otherPhone = new Phone(
                phone.getModel(),
                phone.getManufacturer(),
                phone.getCount(),
                price,
                phone.getCoreNumbers(),
                phone.getBatteryPower()
        );

        Field field = otherPhone.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(otherPhone, phone.getId());

        final boolean result = target.update(otherPhone);
        Assertions.assertTrue(result);
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(price, actualResult.get(0).getPrice());
    }

    @Test
    void update_WrongID() {
        final int count = 5;
        target.save(phone);
        Phone otherPhone = new Phone(
                phone.getModel(),
                phone.getManufacturer(),
                count,
                phone.getPrice(),
                phone.getCoreNumbers(),
                phone.getBatteryPower()
        );
        final boolean result = target.update(otherPhone);
        Assertions.assertFalse(result);
    }

    @Test
    void delete() {
        target.save(phone);
        final boolean result = target.delete(phone.getId());
        Assertions.assertTrue(result);
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void delete_WrongID() {
        target.save(phone);
        final boolean result = target.delete("1000-12002-3ere");
        Assertions.assertFalse(result);
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void findById() {
        target.save(phone);
        final Optional<Phone> optionalPhone = target.findById(phone.getId());
        Assertions.assertTrue(optionalPhone.isPresent());
        final Phone actualPhone = optionalPhone.get();
        Assertions.assertEquals(phone.getId(), actualPhone.getId());
    }

    @Test
    void findById_WrongID() {
        target.save(phone);
        final Optional<Phone> optionalProduct = target.findById("1000-12002-3ere");
        Assertions.assertFalse(optionalProduct.isPresent());
    }

    @Test
    void getAll() {
        target.save(phone);
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(1, actualResult.size());
    }

    @Test
    void getAll_noProducts() {
        final List<Phone> actualResult = target.getAll();
        Assertions.assertEquals(0, actualResult.size());
    }
}