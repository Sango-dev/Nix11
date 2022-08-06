package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.WashingMachine;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.WMRepository;

public class WMService extends TechProductService<WashingMachine> {

    public WMService(CrudRepository<WashingMachine> repository) {
        super(repository);
    }

    private static WMService instance;

    public static WMService getInstance() {
        if (instance == null) {
            instance = new WMService(new WMRepository());
        }
        return instance;
    }
}
