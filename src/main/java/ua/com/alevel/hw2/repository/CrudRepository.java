package ua.com.alevel.hw2.repository;

import ua.com.alevel.hw2.model.TechProduct;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T extends TechProduct> {

    void save(T product);

    void saveAll(List<T> products);

    boolean update(T product);

    boolean delete(String id);

    List<T> getAll();

    Optional<T> findById(String id);
}
