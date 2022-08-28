package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PhoneServiceTest {

    private static PhoneService target;
    private static PhoneRepository repository;
    private Phone phone;

    @BeforeAll
    static void beforeAll() {
        repository = mock(PhoneRepository.class);
        target = PhoneService.getInstance(repository);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(repository);
        phone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
    }

    @Test
    void createAndSave_negativeCounts() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSave(-1, TechProductType.PHONE));
    }

    @Test
    void createAndSave() {
        target.createAndSave(2, TechProductType.PHONE);
        Mockito.verify(repository).saveAll(Mockito.anyList());
    }

    @Test
    void save() {
        target.save(phone);
        ArgumentCaptor<Phone> argument = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argument.capture());
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
        Mockito.verify(repository).update(argument.capture());
    }

    @Test
    void update_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }

    @Test
    void delete() {
        target.delete(phone.getId());
        Mockito.verify(repository).delete(Mockito.anyString());
    }

    @Test
    void delete_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.delete(null));
    }

    @Test
    void findById() {
        Mockito.when(repository.findById(
                ArgumentMatchers.argThat(arg ->
                        arg.equals("uAlU5")))).thenReturn(Optional.ofNullable(phone));

        Optional<Phone> dupMachine = target.findById("uAlU5");
        Mockito.verify(repository).findById(Mockito.anyString());
        Assertions.assertEquals(phone, dupMachine.get());
    }

    @Test
    void findById_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById(null));
    }

    @Test
    void findById_nothing() {
        Mockito.when(repository.findById(Mockito.anyString())).thenCallRealMethod();
        Assertions.assertThrows(NullPointerException.class, () -> target.findById("uAlU5"));
    }

    @Test
    void getAll() {
        target.getAll();
        Mockito.verify(repository).getAll();
    }
}