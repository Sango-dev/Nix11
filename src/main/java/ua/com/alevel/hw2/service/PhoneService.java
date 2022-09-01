package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.annotation.Autowired;
import ua.com.alevel.hw2.annotation.Singleton;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Map;

@Singleton
public class PhoneService extends TechProductService<Phone> {

    private static PhoneService instance;

    private final PhoneRepository repository;

    @Autowired
    private PhoneService(final PhoneRepository repository) {
        super(repository);
        this.repository = repository;
    }

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(PhoneRepository.getInstance());
        }
        return instance;
    }

    public static PhoneService getInstance(final PhoneRepository repository) {
        if (instance == null) {
            instance = new PhoneService(repository);
        }
        return instance;
    }

    public boolean isConcreteDetailExist(String needsDetail) {
        return getAll()
                .stream()
                .flatMap(phone -> phone.getDetails().stream())
                .anyMatch(detail -> detail.equals(needsDetail));
    }

    @Override
    public Phone createProductFromMapImpl(Map<String, Object> map) {
        return new Phone(map.get("model").toString(),
                Manufacturer.valueOf(map.get("manufacturer").toString()),
                Integer.parseInt(map.get("count").toString()),
                Double.parseDouble(map.get("price").toString()),
                Integer.parseInt(map.get("coreNumbers").toString()),
                Integer.parseInt(map.get("batteryPower").toString())
        );
    }
}
