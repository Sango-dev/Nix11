package ua.com.alevel.hw2;

import ua.com.alevel.hw2.factory.ProductFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.service.PhoneService;

import java.util.*;

public class Main {

    public static final int N = 8;
    public static final int ZERO = 0;
    private static PhoneService service = PhoneService.getInstance();

    public static void main(String[] args) {
        doCheck();
    }

    private static void doCheck() {

        System.out.println("Create phones:");
        for (int i = ZERO; i < N; i++) {
            service.save((Phone) ProductFactory.createProduct(TechProductType.PHONE));
        }
        service.getAll().forEach(System.out::println);

        double price = 500.5;
        System.out.println("\nPrice of products > " + price);
        service.findProductsMoreExpensive(price);

        System.out.println("\nSum prices: " + service.calculatePrice());

        System.out.println("\nMap of products: ");
        Map<String, String> map = service.sortedOfModelDistinctsProductsToMap();
        map.forEach((key, value) -> System.out.println("Key: " + key + " Value: " + value));

        service.getAll().get(0).setDetails(List.of("battery", "display", "buttons", "microphone"));
        System.out.println("\nIs exist detail (display):" + service.isConcreteDetailExist("display"));
        System.out.println(service.getSummaryPriceStatistics());
        System.out.println("Is price present for all products: " + service.isAllProductsHavePrice());

        Map<String, Object> createMap = new HashMap<>();
        createMap.put("model", "Iphone 10");
        createMap.put("manufacturer", "APPLE");
        createMap.put("price", 500.7);
        createMap.put("count", 3);
        createMap.put("coreNumbers", 8);
        createMap.put("batteryPower", 1800);
        Phone phone = service.createProductFromMap(createMap);
        System.out.println("Created product from map: " + phone);
    }

}
