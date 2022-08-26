package solidproject.com.example.util;

import solidproject.com.example.model.NotifiableProduct;
import solidproject.com.example.model.Product;
import solidproject.com.example.repository.IRepository;
import solidproject.com.example.repository.Repository;

import java.util.List;

public final class Utils {
    private static Repository repository;

    static {
        repository = Repository.getInstance();
    }

    private Utils() {
    }

    public static int filterNotifiableProductsAndSendNotifications() {
        List<NotifiableProduct> products = repository.getAll()
                .stream()
                .filter(NotifiableProduct.class::isInstance)
                .map(NotifiableProduct.class::cast)
                .toList();

        return products.size();
    }
}

