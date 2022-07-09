package ua.com.alevel.hw2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.TechProductRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ProductService {

    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private static final TechProductRepository REPOSITORY = new TechProductRepository();

    public void createAndSaveProducts(int count, TechProductType type) {
        List<TechProduct> products = new LinkedList<TechProduct>();
        for (int i = 0; i < count; i++) {
            products.add(ProductFactory.creatProduct(type));
            LOGGER.info(type + " {} has been saved", products.get(products.size() - 1).getId());
        }
        REPOSITORY.saveAll(products);
    }

    public void saveProduct(TechProduct product) {
        REPOSITORY.save(product);
        LOGGER.info(product.getClass().getSimpleName() + " {} has been saved", product.getId());
    }

    public boolean updateProduct(TechProduct product) {
        return REPOSITORY.update(product);
    }

    public boolean deleteProduct(String id) {
        boolean flag = REPOSITORY.delete(id);
        if (flag) {
            LOGGER.info("Product {} has been removed", id);
        }
        return flag;
    }

    public void findProduct(String id) {
        REPOSITORY.findById(id);
    }

    public void printAllProducts() {
        for (TechProduct products : REPOSITORY.getAll()) {
            System.out.println(products);
        }
    }

}
