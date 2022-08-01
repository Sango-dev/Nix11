package ua.com.alevel.hw2.service;

import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.repository.CrudRepository;

public class MouseService extends TechProductService<Mouse> {

    public MouseService(CrudRepository<Mouse> repository) {
        super(repository);
    }
}
