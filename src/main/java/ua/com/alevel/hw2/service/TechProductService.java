package ua.com.alevel.hw2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.CrudRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class TechProductService<T extends TechProduct> {

    private final CrudRepository<T> repository;
    protected static final Random RANDOM = new Random();

    protected TechProductService(CrudRepository<T> repository) {
        this.repository = repository;
    }

    public void createAndSave(int count, TechProductType type) {
        if (count < 1) {
            throw new IllegalArgumentException("count must be greater than zero");
        }
        List<T> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            products.add((T) ProductFactory.createProduct(type));
        }
        repository.saveAll(products);
    }

    public void save(T product) {
        if (product != null) {
            repository.save(product);
        } else {
            throw new IllegalArgumentException("product cannot be null");
        }
    }

    public boolean update(TechProduct product) {
        if (product != null) {
            return repository.update((T) product);
        } else {
            throw new IllegalArgumentException("product cannot be null");
        }
    }

    public boolean delete(String id) {
        if (id != null) {
            return repository.delete(id);
        } else {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public Optional<T> findById(String id) {
        if (id != null) {
            return repository.findById(id);
        } else {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    public List<T> getAll() {
        return repository.getAll();
    }

    public void printAll() {
        for (T product : repository.getAll()) {
            System.out.println(product);
        }
    }


}
