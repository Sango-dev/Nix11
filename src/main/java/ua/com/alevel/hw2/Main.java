package ua.com.alevel.hw2;

import ua.com.alevel.hw2.comparator.CustomComparator;
import ua.com.alevel.hw2.customlist.CustomLinkedList;
import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final Random RANDOM = new Random();
    public static final int LIMIT = 20;
    public static final int N = 5;

    public static void main(String[] args) throws InterruptedException {
        toDoSmthWithList();
        sortWithComparator();
    }

    public static void toDoSmthWithList() throws InterruptedException {
        System.out.println("Work with list:");
        CustomLinkedList<TechProduct> list = new CustomLinkedList<>();
        list.add(ProductFactory.createProduct(TechProductType.PHONE), 1);
        TimeUnit.SECONDS.sleep(1);
        list.add(ProductFactory.createProduct(TechProductType.WASHING_MACHINE), 29);
        list.add(ProductFactory.createProduct(TechProductType.WASHING_MACHINE), 15);
        list.add(ProductFactory.createProduct(TechProductType.PHONE), 10);
        list.add(ProductFactory.createProduct(TechProductType.PHONE), 9);
        list.add(ProductFactory.createProduct(TechProductType.MOUSE), 8);
        list.add(ProductFactory.createProduct(TechProductType.MOUSE), 8);
        list.add(ProductFactory.createProduct(TechProductType.PHONE), 21);
        list.add(ProductFactory.createProduct(TechProductType.WASHING_MACHINE), 22);
        list.add(ProductFactory.createProduct(TechProductType.MOUSE), 2);

        System.out.println("1. Show list: ");
        list.forEach(System.out::println);

        System.out.print("\n2. Find product by version (21): ");
        System.out.println(list.findByVersion(21).get());

        System.out.println("\n3. Remove product by version (21)");
        list.removeByVersion(21);
        System.out.println("Show list:");
        list.forEach(System.out::println);

        System.out.println("\n4. Update product by version (2)");
        list.updateProductByVersion(ProductFactory.createProduct(TechProductType.PHONE), 2);
        System.out.println("Show list:");
        list.forEach(System.out::println);

        System.out.print("\n5. Get amount of versions: " + list.getVersionsAmount());
        System.out.print("\n6. Get date of first version: " + list.getDateOfFirstVersion().get());
        System.out.print("\n7. Get date of last version: " + list.getDateOfLastVersion().get());
    }

    public static void sortWithComparator() {
        System.out.println("\n\nWork with comparator:");
        ArrayList<TechProduct> list = new ArrayList<>();
        Phone phone = new Phone(
                "Model-161",
                Manufacturer.SAMSUNG,
                446,
                142.1919191919,
                8,
                5000);

        Phone phone2 = new Phone(
                "Model-161",
                Manufacturer.SONY,
                401,
                142.1919191919,
                8,
                5000);

        Phone phone3 = new Phone(
                "Model-163",
                Manufacturer.APPLE,
                399,
                178.18181818,
                8,
                5000);

        Phone phone4 = new Phone(
                "Model-162",
                Manufacturer.LG,
                250,
                178.18181818,
                8,
                5000);

        list.add(phone);
        list.add(phone2);
        list.add(phone3);
        list.add(phone4);

        for (int i = 0; i < N; i++) {
            list.add(ProductFactory.createProduct(TechProductType.PHONE));
        }

        System.out.println("Model\t\t  Count\t\tPrice");
        for (TechProduct product : list) {
            System.out.println(product.getModel() + "\t\t" + product.getCount() + "\t\t" + product.getPrice());
        }

        System.out.println("~".repeat(10));
        System.out.println("Sort");
        list.sort(new CustomComparator<>());
        System.out.println("Model\t\t  Count\t\tPrice");
        for (TechProduct product : list) {
            System.out.println(product.getModel() + "\t\t" + product.getCount() + "\t\t" + product.getPrice());
        }
    }
}
