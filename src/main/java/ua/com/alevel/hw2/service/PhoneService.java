package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.repository.CrudRepository;

public class PhoneService extends TechProductService<Phone> {

    public PhoneService(CrudRepository<Phone> repository) {
        super(repository);
    }

}
