package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.repository.CrudRepository;
import ua.com.alevel.hw2.repository.MouseRepository;

public class MouseService extends TechProductService<Mouse> {

    public MouseService(CrudRepository<Mouse> repository) {
        super(repository);
    }

    private static MouseService instance;

    public static MouseService getInstance() {
        if (instance == null) {
            instance = new MouseService(new MouseRepository());
        }
        return instance;
    }
}
