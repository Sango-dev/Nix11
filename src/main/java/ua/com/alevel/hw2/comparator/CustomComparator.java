package ua.com.alevel.hw2.comparator;

import ua.com.alevel.hw2.model.product.TechProduct;

import java.util.Comparator;

public class CustomComparator <T extends TechProduct> implements Comparator<T> {
    @Override
    public int compare(T o1, T o2) {
        if (o1.getPrice() == o2.getPrice()) {
            if (o1.getModel().equals(o2.getModel())) {
                return Integer.compare(o1.getCount(), o2.getCount());
            }
            return o1.getModel().compareTo(o2.getModel());
        }
        return Double.compare(o2.getPrice(), o1.getPrice());
    }
}