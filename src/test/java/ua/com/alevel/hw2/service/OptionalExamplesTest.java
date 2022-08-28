package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Optional;

import static org.mockito.Mockito.mock;

class OptionalExamplesTest {

    private static OptionalExamples target;
    private static PhoneRepository repository;
    private Phone phone;

    @BeforeAll
    static void beforeAll() {
        repository = mock(PhoneRepository.class);
        target = OptionalExamples.getInstance(repository);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(repository);
        phone = (Phone) ProductFactory.createProduct(TechProductType.PHONE);
    }

    @Test
    void findOrReturnDefaultPhone_found() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        Phone retPhone = target.findOrReturnDefaultPhone(phone.getId());
        Assertions.assertEquals(phone.getId(), retPhone.getId());
    }

    @Test
    void findOrReturnDefaultPhone_notFound() {
        String id = "zLPu";
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of((Phone) ProductFactory.createProduct(TechProductType.PHONE)));
        Phone retPhone = target.findOrReturnDefaultPhone(id);
        Assertions.assertNotEquals(id, retPhone.getId());
    }

    @Test
    void deletePhoneIfPriceLessThan_less() {
        double price = 800.1;
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.deletePhoneIfPriceLessThan(phone.getId(), price);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository).delete(argumentCaptor.capture());
    }

    @Test
    void deletePhoneIfPriceLessThan_notLess() {
        double price = 1.1;
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.deletePhoneIfPriceLessThan(phone.getId(), price);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(repository, Mockito.times(0)).delete(argumentCaptor.capture());
    }

    @Test
    void getStrPhoneOrDefault_foundProd() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        String res = target.getStrPhoneOrDefault(phone.getId());
        Assertions.assertEquals(phone.toString(), res);
    }

    @Test
    void getStrPhoneOrDefault_getDefault() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of((Phone) ProductFactory.createProduct(TechProductType.PHONE)));
        String res = target.getStrPhoneOrDefault(phone.getId());
        Assertions.assertNotEquals(phone.toString(), res);
    }

    @Test
    void findOrSaveDefault_foundPhone() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        Phone otherPhone = target.findOrSaveDefault(phone.getId());
        Assertions.assertEquals(phone, otherPhone);
    }

    @Test
    void findOrSaveDefault_saveDefault() {
        String id = "sBbZd";
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        target.findOrSaveDefault(id);
        ArgumentCaptor<Phone> argumentCaptor = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
    }

    @Test
    void findOrThrowException_found() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        Phone otherPhone = target.findOrThrowException(phone.getId());
        Assertions.assertEquals(phone, otherPhone);
    }

    @Test
    void findOrThrowException_throwExc() {
        String id = "FpgH5s";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findOrThrowException(id));
    }

    @Test
    void updateOrSaveIfNotExists_exist() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.updateOrSaveIfNotExists(phone);
        ArgumentCaptor<Phone> argumentCaptor = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).update(argumentCaptor.capture());
    }

    @Test
    void updateOrSaveIfNotExists_notExist() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.empty());
        target.updateOrSaveIfNotExists(phone);
        ArgumentCaptor<Phone> argumentCaptor = ArgumentCaptor.forClass(Phone.class);
        Mockito.verify(repository).save(argumentCaptor.capture());
    }

    @Test
    void getPhoneOrEmpty_found() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        Optional<Phone> otherPhone = target.getPhoneOrEmpty(phone.getId());
        Assertions.assertEquals(phone, otherPhone.get());
    }

    @Test
    void getPhoneOrEmpty_notFound() {
        String id = "RO73v0R";
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Optional<Phone> otherPhone = target.getPhoneOrEmpty(id);
        Assertions.assertFalse(otherPhone.isPresent());
    }

    @Test
    void deleteIfPhoneOrThrowException_ifPhone() {
        Mockito.when(repository.findById(phone.getId())).thenReturn(Optional.of(phone));
        target.deleteIfPhoneOrThrowException(phone.getId());
        Mockito.verify(repository).delete(phone.getId());
    }

    @Test
    void deleteIfPhoneOrThrowException_throwExc() {
        String id = "u4Mb";
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.deleteIfPhoneOrThrowException(id));
    }
}