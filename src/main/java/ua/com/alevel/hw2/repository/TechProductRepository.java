package ua.com.alevel.hw2.repository;

import ua.com.alevel.hw2.model.TechProduct;

import java.util.*;


public class TechProductRepository implements CrudRepository {

    private final List<TechProduct> products;

    public TechProductRepository() {
        products = new LinkedList<>();
    }

    @Override
    public void save(TechProduct product) {
        products.add(product);
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
