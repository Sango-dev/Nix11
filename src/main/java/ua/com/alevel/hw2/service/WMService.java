package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.repository.CrudRepository;

public class WMService extends TechProductService<WashingMachine> {

    public WMService(CrudRepository<WashingMachine> repository) {
        super(repository);
    }
}
