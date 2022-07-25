package ua.com.alevel.hw2.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.WashingMachine;

import java.util.*;


public class TechProductRepository implements CrudRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TechProductRepository.class);

    private final List<TechProduct> products;

    public TechProductRepository() {
        products = new LinkedList<>();
    }

    @Override
    public void save(TechProduct product) {
        if (product == null) {
            final IllegalArgumentException exception = new IllegalArgumentException("Cannot save a null product");
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        } else {
            checkDuplicates(product);
            products.add(product);
        }
    }

    private void checkDuplicates(TechProduct product) {
        String s = null;
        if (product instanceof Phone) {
            s = "Phone";
        } else if (product instanceof Mouse) {
            s = "Mouse";
        } else if (product instanceof WashingMachine) {
            s = "WashingMachine";
        }

        boolean flag = false;

        for (TechProduct p : products) {
            if (s.equals("Phone") && p instanceof Phone) {
                if (((Phone) product).hashCode() == ((Phone) p).hashCode() && ((Phone) product).equals(((Phone) p))) {
                    flag = true;
                }
            }

            if (s.equals("Mouse") && p instanceof Mouse) {
                if (((Mouse) product).hashCode() == ((Mouse) p).hashCode() && ((Mouse) product).equals(((Mouse) p))) {
                    flag = true;
                }
            }

            if (s.equals("WashingMachine") && p instanceof WashingMachine) {
                if (((WashingMachine) product).hashCode() == ((WashingMachine) p).hashCode() && ((WashingMachine) product).equals(((WashingMachine) p))) {
                    flag = true;
                }
            }
        }

        if (flag == true) {
            final IllegalArgumentException exception = new IllegalArgumentException("Duplicate product: " +
                    product.getId());
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
    }

    @Override
    public void saveAll(List<TechProduct> products) {
        for (TechProduct product : products) {
            save(product);
        }
    }

    @Override
    public boolean update(TechProduct product) {
        final Optional<TechProduct> result = findById(product.getId());
        if (result.isEmpty()) {
            return false;
        }
        final TechProduct originProduct = result.get();
        ProductCopy.copy(originProduct, product);
        return true;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<TechProduct> iterator = products.iterator();
        while (iterator.hasNext()) {
            final TechProduct product = iterator.next();
            if (product.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<TechProduct> findById(String id) {
        TechProduct result = null;
        for (TechProduct product : products) {
            if (product.getId().equals(id)) {
                result = product;
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public List<TechProduct> getAll() {
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(products);
    }

    private static class ProductCopy {
        private static void copy(final TechProduct to, final TechProduct from) {
            to.setCount(from.getCount());
            to.setPrice(from.getPrice());
        }
    }

}
