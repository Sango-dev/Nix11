package ua.com.alevel.hw2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.CrudRepository;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class TechProductService<T extends TechProduct> {

    private final CrudRepository<T> repository;
    protected static final Random RANDOM = new Random();

    private final Predicate<Collection<T>> predicate = products -> products.stream().allMatch(product -> product.getPrice() !=0);
    private final Function<Map<String, Object>, T> function = this::createProductFromMapImpl;

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
        repository.getAll()
                .stream()
                .forEach(System.out::println);
    }

    public void findProductsMoreExpensive(double price) {
        repository.getAll()
                .stream()
                .filter(product -> product.getPrice() > price)
                .forEach(System.out::println);
    }

    public double calculatePrice() {
        return repository.getAll()
                .stream()
                .map(TechProduct::getPrice)
                .reduce(0.0, Double::sum);
    }

    public Map<String, String> sortedOfModelDistinctsProductsToMap() {
        return repository.getAll()
                .stream()
                .sorted(Comparator.comparing(TechProduct::getModel))
                .distinct()
                .collect(Collectors.toMap(TechProduct::getId, TechProduct::toString, (p1, p2) -> p2));
    }

    public DoubleSummaryStatistics getSummaryPriceStatistics() {
        return repository.getAll()
                .stream()
                .mapToDouble(TechProduct::getPrice)
                .summaryStatistics();
    }

    public boolean isAllProductsHavePrice() {
        return predicate.test(repository.getAll());
    }

    public T createProductFromMap(Map<String, Object> map) {
        return function.apply(map);
    }

    public abstract T createProductFromMapImpl(Map<String, Object> map);

}
