package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;

import java.util.Map;

public class PhoneService extends TechProductService<Phone> {

    public PhoneService(CrudRepository<Phone> repository) {
        super(repository);
    }

    private static PhoneService instance;

    public static PhoneService getInstance() {
        if (instance == null) {
            instance = new PhoneService(new PhoneRepository());
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
