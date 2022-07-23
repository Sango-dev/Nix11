package ua.com.alevel.hw3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw3.model.TechProduct;
import ua.com.alevel.hw3.model.TechProductType;
import ua.com.alevel.hw3.repository.TechProductRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TechProductService {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(TechProductService.class);
    private final TechProductRepository repository;

    public TechProductService(TechProductRepository repository) {
        this.repository = repository;
    }

    public void createAndSaveProducts(int count, TechProductType type) {
        if (count < 1) {
            throw new IllegalArgumentException("count must be greater than zero");
        }
        List<TechProduct> products = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            products.add(ProductFactory.creatProduct(type));
            LOGGER.info(type + " {} has been saved", products.get(products.size() - 1).getId());
        }
        repository.saveAll(products);
    }

    public void save(TechProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        repository.save(product);
        LOGGER.info(product.getClass().getSimpleName() + " {} has been saved", product.getId());
    }

    public boolean update(TechProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        return repository.update(product);
    }

    public boolean delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        boolean flag = repository.delete(id);
        if (flag) {
            LOGGER.info("Product {} has been removed", id);
        }
        return flag;
    }

    public Optional<TechProduct> findById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        return repository.findById(id);
    }

    public void getAll() {
        for (TechProduct products : repository.getAll()) {
            System.out.println(products);
        }
    }
}
