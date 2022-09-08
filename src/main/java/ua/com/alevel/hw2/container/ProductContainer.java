package ua.com.alevel.hw2.container;

import ua.com.alevel.hw2.model.product.TechProduct;

import java.util.Random;

public class ProductContainer<T extends TechProduct> {

    private T product;
    private static final int UPPER_BOUND = 21;
    private static final int LOWER_BOUND = 10;
    private static final Random RANDOM = new Random();

    public ProductContainer(T product) {
        this.product = product;
    }

    public T getProduct() {
        return product;
    }

    public void setProduct(T product) {
        this.product = product;
    }

    public int changePriceWithDiscount() {
        int valueOfDiscount = RANDOM.nextInt(LOWER_BOUND) + UPPER_BOUND;
        double price = product.getPrice();
        product.setPrice(price - (price * valueOfDiscount * 0.01));
        return valueOfDiscount;
    }

    public <X extends Number> void addCount(X cnt) {
        product.setCount(product.getCount() + cnt.intValue());
    }
}
