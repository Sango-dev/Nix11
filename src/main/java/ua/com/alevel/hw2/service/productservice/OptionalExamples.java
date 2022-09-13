package ua.com.alevel.hw2.service.productservice;

import ua.com.alevel.hw2.model.product.Manufacturer;
import ua.com.alevel.hw2.model.product.Phone;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Optional;
import java.util.Random;

public class OptionalExamples {

    private static OptionalExamples instance;

    private final PhoneRepository repository;

    private OptionalExamples(final PhoneRepository repository) {
        this.repository = repository;
    }

    public static OptionalExamples getInstance() {
        if (instance == null) {
            instance = new OptionalExamples(PhoneRepository.getInstance());
        }
        return instance;
    }

    public static OptionalExamples getInstance(final PhoneRepository repository) {
        if (instance == null) {
            instance = new OptionalExamples(repository);
        }
        return instance;
    }

    public Phone findOrReturnDefaultPhone(String id) {
        return repository.findById(id).orElse(createPhone());
    }

    public void deletePhoneIfPriceLessThan(String id, double price) {
        repository.findById(id)
                .filter(phone -> phone.getPrice() < price)
                .ifPresent(deletePhone -> repository.delete(id));
    }

    public String getStrPhoneOrDefault(String id) {
        return repository.findById(id)
                .map(Phone::toString)
                .orElse(createPhone().toString());
    }

    public Phone findOrSaveDefault(String id) {
        return repository.findById(id)
                .orElseGet(() -> {
                    Phone phone = createPhone();
                    repository.save(phone);
                    return phone;
                });
    }

    public Phone findOrThrowException(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Phone with id: " + '"' + id + '"' + " was not found"));
    }

    public void updateOrSaveIfNotExists(Phone phone) {
        repository.findById(phone.getId())
                .ifPresentOrElse(
                        updPhone -> repository.update(phone),
                        () -> repository.save(phone)
                );
    }

    public Optional<Phone> getPhoneOrEmpty(String id) {
        return repository.findById(id).or(() -> Optional.empty());
    }

    public void deleteIfPhoneOrThrowException(String id) {
        repository.findById(id)
                .filter(phone -> phone.getClass() == Phone.class)
                .ifPresentOrElse(
                        delPhone -> {
                            repository.delete(id);
                        },
                        () -> {
                            throw new IllegalArgumentException("This product is not phone");
                        }
                );
    }

    private Phone createPhone() {
        Random random = new Random();
        return new Phone(
                "Iphone " + random.nextInt(15),
                Manufacturer.APPLE, random.nextInt(250),
                5000.9, random.nextInt(20),
                random.nextInt(10000));
    }
}
