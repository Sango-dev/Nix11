package solidproject.com.example.repository;

import solidproject.com.example.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository<Product>{
    private Map<Long, Product> storage;
    private static Repository instance;

    private Repository() {
        storage = new HashMap<>();
    }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    @Override
    public Product save(Product product) {
        return storage.put(product.getId(), product);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(storage.values());
    }

}
