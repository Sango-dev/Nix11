package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.PhoneRepository;

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

}
