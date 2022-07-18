package ua.com.alevel.hw2.repository;

import ua.com.alevel.hw2.model.TechProduct;

import java.util.List;
import java.util.Optional;

public interface CrudRepository {

    void save(TechProduct product);

    void saveAll(List<TechProduct> products);

    boolean update(TechProduct product);

    boolean delete(String id);

    List<TechProduct> getAll();

    Optional<TechProduct> findById(String id);
}
