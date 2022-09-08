package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw2.dao.productdao.PhoneDao;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.model.product.TechProductType;
import ua.com.alevel.hw2.service.productservice.PhoneService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PhoneServiceTest {

    private static PhoneService target;
    private static PhoneDao phoneDao;
    private Phone phone;

    @BeforeAll
    static void beforeAll() {
        phoneDao = mock(PhoneDao.class);
        target = PhoneService.getInstance(phoneDao);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(phoneDao);
        phone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
    }

    @Test
    void createAndSave_negativeCounts() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSave(-1, TechProductType.PHONE));
    }

    @Test
    void createAndSave() {
        target.createAndSave(2, TechProductType.PHONE);
        Mockito.verify(phoneDao).saveAll(Mockito.anyList());
    }

    @Test
    void save() {
        target.save(phone);
        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(phoneDao).save(argument.capture());
        assertEquals(phone.getModel(), argument.getValue().getModel());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void update() {
        target.update(phone);
        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(phoneDao).update(argument.capture());
    }

    @Test
    void update_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }

    @Test
    void delete() {
        target.delete(phone.getId());
        Mockito.verify(phoneDao).delete(Mockito.anyString());
    }

    @Test
    void delete_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.delete(null));
    }

    @Test
    void findById() {
        Mockito.when(phoneDao.findById(
                ArgumentMatchers.argThat(arg ->
                        arg.equals("uAlU5")))).thenReturn(Optional.ofNullable(phone));

        Optional<Phone> dupMachine = target.findById("uAlU5");
        Mockito.verify(phoneDao).findById(Mockito.anyString());
        Assertions.assertEquals(phone, dupMachine.get());
    }

    @Test
    void findById_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById(null));
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(phoneDao).getAll();
    }
}