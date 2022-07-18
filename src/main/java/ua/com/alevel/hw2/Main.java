package ua.com.alevel.hw2;

import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.service.ProductFactory;
import ua.com.alevel.hw2.service.ProductService;

import java.lang.reflect.Field;

public class Main {

    private static final ProductService PRODUCT_SERVICE = new ProductService();

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("Create phone, mouse and washing machine objects: ");
        PRODUCT_SERVICE.createAndSaveProducts(1, TechProductType.PHONE);
        TechProduct product = ProductFactory.creatProduct(TechProductType.MOUSE);
        PRODUCT_SERVICE.saveProduct(product);
        PRODUCT_SERVICE.createAndSaveProducts(1, TechProductType.WASHING_MACHINE);
        PRODUCT_SERVICE.printAllProducts();

        System.out.println("\nUpdate mouse object (count, price): ");

        String id = product.getId();
        TechProduct product1 = new Mouse(
                product.getModel(),
                product.getManufacturer(),
                product.getCount(),
                product.getPrice(),
                ((Mouse) (product)).getConnectionType(),
                ((Mouse) (product)).getDpiAmount());

        product1.setPrice(111.1);
        product1.setCount(3);

        Field field = product1.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(product1, id);


        PRODUCT_SERVICE.updateProduct(product1);
        PRODUCT_SERVICE.printAllProducts();


        System.out.println("\nDelete mouse object: ");
        PRODUCT_SERVICE.deleteProduct(id);
        PRODUCT_SERVICE.printAllProducts();
    }
}
