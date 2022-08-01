package ua.com.alevel.hw2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.repository.TechProductRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TechProductService {

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
        }
        repository.saveAll(products);
    }

    public void save(TechProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("product cannot be null");
        }
        repository.save(product);
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
        return repository.delete(id);
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

    public void deleteProductIfPriceLessThan(String id, double price) {
        repository.findById(id)
                .filter(techProduct -> techProduct.getPrice() < price)
                .ifPresent(deleteTechProduct -> repository.delete(id));
    }

    public TechProduct findOrReturnDefaultPhone(String id) {
        return repository.findById(id).orElse(createPhone());
    }

    public String getStrProdOrDefault(String id) {
        return repository.findById(id)
                .map(techProduct -> techProduct.toString())
                .orElse(createPhone().toString());
    }

    public TechProduct findOrSaveDefault(String id) {
        return repository.findById(id)
                .orElseGet(() -> {
                    TechProduct techProduct = createPhone();
                    repository.save(techProduct);
                    return techProduct;
                });
    }

    public TechProduct findOrThrowException(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + '"' + id + '"' + " was not found"));
    }

    public void updateOrSaveIfNotExists(TechProduct techProduct) {
        repository.findById(techProduct.getId())
                .ifPresentOrElse(
                        updProd -> update(techProduct),
                        () -> repository.save(techProduct)
                );
    }

    public Optional<TechProduct> getProductOrEmpty(String id) {
        return repository.findById(id).or(() -> Optional.empty());
    }

    public void deleteIfWashingMachineOrThrowException(String id) {
        repository.findById(id)
                .filter(product -> product.getClass() == WashingMachine.class)
                .ifPresentOrElse(
                        washMachine -> {
                            repository.delete(id);
                        },
                        () -> {
                            throw new IllegalArgumentException("This product is not washing machine");
                        }
                );
    }

    private Phone createPhone() {
        Random random = new Random();
        return new Phone(
                "Iphone " + random.nextInt(15),
                Manufacturer.APPLE, random.nextInt(250),
                5000.9, random.nextInt(20),
                random.nextInt(10000));
    }


}
