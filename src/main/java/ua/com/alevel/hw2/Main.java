package ua.com.alevel.hw2;

import ua.com.alevel.hw2.model.Mouse;
import ua.com.alevel.hw2.model.Phone;
import ua.com.alevel.hw2.model.TechProduct;
import ua.com.alevel.hw2.model.TechProductType;
import ua.com.alevel.hw2.repository.TechProductRepository;
import ua.com.alevel.hw2.service.ProductFactory;
import ua.com.alevel.hw2.service.TechProductService;

import java.lang.reflect.Field;
import java.util.Random;

public class Main {

    private static final TechProductService PRODUCT_SERVICE = new TechProductService(new TechProductRepository());

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Random Random = new Random();
        double price = 980.0;
        //create
        TechProduct mouse = ProductFactory.creatProduct(TechProductType.MOUSE);
        TechProduct phone = ProductFactory.creatProduct(TechProductType.PHONE);
        TechProduct washingMachine = ProductFactory.creatProduct(TechProductType.WASHING_MACHINE);

        //save
        PRODUCT_SERVICE.save(mouse);
        PRODUCT_SERVICE.save(phone);
        PRODUCT_SERVICE.save(washingMachine);

        //ifPresent
        System.out.println("\ndeleteProductIfPriceLessThan (ifPresent) method: ");
        PRODUCT_SERVICE.deleteProductIfPriceLessThan(mouse.getId(), price);
        PRODUCT_SERVICE.getAll();

        //orElse
        System.out.println("\nfindOrReturnDefaultPhone (orElse) method: ");
        System.out.println("Was found: " + PRODUCT_SERVICE.findOrReturnDefaultPhone(phone.getId()));
        System.out.println("Default: " + PRODUCT_SERVICE.findOrReturnDefaultPhone("4Au1Eu"));

        //orElseGet
        System.out.println("\nfindOrSaveDefault (orElseGet) method: ");
        System.out.println("Was found: " + PRODUCT_SERVICE.findOrSaveDefault(washingMachine.getId()));
        PRODUCT_SERVICE.findOrSaveDefault("KMBLMLL");
        System.out.println("Was saved default: ");
        PRODUCT_SERVICE.getAll();

        //map
        System.out.println("\ngetStrProdOrDefault (map) method: ");
        System.out.println("Get string product: " + PRODUCT_SERVICE.getStrProdOrDefault(washingMachine.getId()));
        System.out.println("Get string default product: " + PRODUCT_SERVICE.getStrProdOrDefault("8HUxpI8c"));

        //ifPresentOrElse
        System.out.println("\nupdateOrSaveIfNotExists (ifPresentOrElse) method: ");
        System.out.println("Before: ");
        PRODUCT_SERVICE.getAll();
        TechProduct phone2 = ProductFactory.creatProduct(TechProductType.PHONE);
        Field field = phone2.getClass().getSuperclass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(phone2, phone.getId());
        PRODUCT_SERVICE.updateOrSaveIfNotExists(phone2);
        System.out.println("After update (change count, price): ");
        PRODUCT_SERVICE.getAll();
        System.out.println("After save: ");
        PRODUCT_SERVICE.updateOrSaveIfNotExists(ProductFactory.creatProduct(TechProductType.PHONE));
        PRODUCT_SERVICE.getAll();

        //filter
        System.out.println("\ndeleteIfWashingMachineOrThrowException (filter) method: ");
        System.out.println("delete washing machine by id:");
        PRODUCT_SERVICE.deleteIfWashingMachineOrThrowException(washingMachine.getId());
        PRODUCT_SERVICE.getAll();
        System.out.println("throw exception:");
        try {
            PRODUCT_SERVICE.deleteIfWashingMachineOrThrowException(phone.getId());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //orElseThrow
        System.out.println("\nfindOrThrowException (orElseThrow) method: ");
        System.out.println("Was found: " + PRODUCT_SERVICE.findOrThrowException(phone.getId()));
        System.out.print("Throw exception: ");
        try {
            PRODUCT_SERVICE.findOrThrowException("aFsWHm");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //or
        System.out.println("\ngetProductOrEmpty (or) method: ");
        System.out.println("Was found: " + PRODUCT_SERVICE.getProductOrEmpty(phone.getId()));
        System.out.println("Empty: " + PRODUCT_SERVICE.getProductOrEmpty("h4FTph"));

    }
}
