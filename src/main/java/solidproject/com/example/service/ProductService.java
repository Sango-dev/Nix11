package solidproject.com.example.service;

import solidproject.com.example.model.Product;
import solidproject.com.example.repository.Repository;

import java.util.List;

public class ProductService {
    private final Repository repository;
    private static ProductService instance;

    private ProductService(Repository repository) {
        this.repository = repository;
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService(Repository.getInstance());
        }
        return instance;
    }

    public void save(Product product) {
        if (product != null) {
            repository.save(product);
        } else {
            throw new IllegalArgumentException("product cannot be null");
        }
    }

    public List<Product> getAll() {
        return repository.getAll();
    }



}
