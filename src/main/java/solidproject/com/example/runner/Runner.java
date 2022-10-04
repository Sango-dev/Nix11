package solidproject.com.example.runner;

import solidproject.com.example.factory.ProductFactory;
import solidproject.com.example.model.Product;
import solidproject.com.example.service.ProductService;
import solidproject.com.example.util.Utils;

import java.util.ArrayList;
import java.util.List;

public final class Runner {
    public static final ProductService productService = ProductService.getInstance();
    private Runner() {}

    public static void run() {
        List<Product> products = new ArrayList<>();
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());
        products.add(ProductFactory.generateRandomProduct());

        products.forEach(productService::save);
        productService.getAll().forEach(System.out::println);
        System.out.println("notifications sent: " + Utils.filterNotifiableProductsAndSendNotifications());
    }
}
