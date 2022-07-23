package ua.com.alevel.hw3.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw3.model.Manufacturer;
import ua.com.alevel.hw3.model.TechProduct;
import ua.com.alevel.hw3.model.TechProductType;
import ua.com.alevel.hw3.model.WashingMachine;
import ua.com.alevel.hw3.repository.TechProductRepository;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TechProductServiceTest {

    private TechProductService target;
    private TechProductRepository repository;
    private WashingMachine washingMachine;

    @BeforeEach
    void setUp() {
        Random random = new Random();
        repository = mock(TechProductRepository.class);
        target = new TechProductService(repository);
        washingMachine = new WashingMachine(
                "Model-" + random.nextInt(200),
                Manufacturer.APPLE,
                random.nextInt(500),
                random.nextDouble(1000.0),
                random.nextInt(400, 1000)
        );
    }

    @Test
    void createAndSaveProducts_negativeCounts() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.createAndSaveProducts(-1, TechProductType.WASHING_MACHINE));
    }

    @Test
    void createAndSaveProducts() {
        target.createAndSaveProducts(2, TechProductType.WASHING_MACHINE);
        Mockito.verify(repository).saveAll(Mockito.anyList());
    }

    @Test
    void save() {
        target.save(washingMachine);
        ArgumentCaptor<TechProduct> argument = ArgumentCaptor.forClass(WashingMachine.class);
        Mockito.verify(repository).save(argument.capture());
        assertEquals(washingMachine.getModel(), argument.getValue().getModel());
    }

    @Test
    void save_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void update() {
        target.update(washingMachine);
        ArgumentCaptor<TechProduct> argument = ArgumentCaptor.forClass(WashingMachine.class);
        Mockito.verify(repository).update(argument.capture());
    }

    @Test
    void update_putNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }

    @Test
    void delete() {
        target.delete(washingMachine.getId());
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
                        arg.equals("uAlU5")))).thenReturn(Optional.ofNullable(washingMachine));

        Optional<TechProduct> dupMachine = target.findById("uAlU5");
        Mockito.verify(repository).findById(Mockito.anyString());
        Assertions.assertEquals(washingMachine, dupMachine.get());
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