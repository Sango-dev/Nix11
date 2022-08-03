package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.repository.TechProductRepository;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void findOrReturnDefaultPhone_found() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        TechProduct techProduct = target.findOrReturnDefaultPhone(washingMachine.getId());
        Assertions.assertEquals(washingMachine.getId(), techProduct.getId());
    }

    @Test
    void findOrReturnDefaultPhone_notFound() {
        String id = "zLPu";
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(ProductFactory.creatProduct(TechProductType.PHONE)));
        TechProduct techProduct = target.findOrReturnDefaultPhone(id);
        Assertions.assertNotEquals(id, techProduct.getId());
    }

    @Test
    void deleteProductIfPriceLessThan_less() {
        double price = 800.1;
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        target.deleteProductIfPriceLessThan(washingMachine.getId(), price);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository).delete(argumentCaptor.capture());
    }

    @Test
    void deleteProductIfPriceLessThan_notLess() {
        double price = 1.1;
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        target.deleteProductIfPriceLessThan(washingMachine.getId(), price);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository, Mockito.times(0)).delete(argumentCaptor.capture());
    }

    @Test
    void getStrProdOrDefault_foundProd() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        String res = target.getStrProdOrDefault(washingMachine.getId());
        Assertions.assertEquals(washingMachine.toString(), res);
    }

    @Test
    void getStrProdOrDefault_getDefault() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(ProductFactory.creatProduct(TechProductType.PHONE)));
        String res = target.getStrProdOrDefault(washingMachine.getId());
        Assertions.assertNotEquals(washingMachine.toString(), res);
    }

    @Test
    void findOrSaveDefault_foundProd() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        TechProduct techProduct = target.findOrSaveDefault(washingMachine.getId());
        Assertions.assertEquals(washingMachine, techProduct);
    }

    @Test
    void findOrSaveDefault_saveDefault() {
        String id = "sBbZd";
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        target.findOrSaveDefault(id);
        ArgumentCaptor<TechProduct> argumentCaptor = ArgumentCaptor.forClass(TechProduct.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
    }

    @Test
    void findOrThrowException_found() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        TechProduct techProduct = target.findOrThrowException(washingMachine.getId());
        Assertions.assertEquals(washingMachine, techProduct);
    }

    @Test
    void findOrThrowException_throwExc() {
        String id = "FpgH5s";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findOrThrowException(id));
    }

    @Test
    void updateOrSaveIfNotExists_exist() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        target.updateOrSaveIfNotExists(washingMachine);
        ArgumentCaptor<TechProduct> argumentCaptor = ArgumentCaptor.forClass(TechProduct.class);
        Mockito.verify(repository).update(argumentCaptor.capture());
    }

    @Test
    void updateOrSaveIfNotExists_notExist() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.empty());
        target.updateOrSaveIfNotExists(washingMachine);
        ArgumentCaptor<TechProduct> argumentCaptor = ArgumentCaptor.forClass(TechProduct.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
    }

    @Test
    void getProductOrEmpty_found() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        Optional<TechProduct> techProduct = target.getProductOrEmpty(washingMachine.getId());
        Assertions.assertEquals(washingMachine, techProduct.get());
    }

    @Test
    void getProductOrEmpty_notFound() {
        String id = "RO73v0R";
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Optional<TechProduct> techProduct = target.getProductOrEmpty(id);
        Assertions.assertFalse(techProduct.isPresent());
    }

    @Test
    void deleteIfWashingMachineOrThrowException_ifWashingMachine() {
        Mockito.when(repository.findById(washingMachine.getId())).thenReturn(Optional.of(washingMachine));
        target.deleteIfWashingMachineOrThrowException(washingMachine.getId());
        Mockito.verify(repository).delete(washingMachine.getId());
    }

    @Test
    void deleteIfWashingMachineOrThrowException_throwExc() {
        String id = "u4Mb";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.deleteIfWashingMachineOrThrowException(id));
    }

}