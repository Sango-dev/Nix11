package ua.com.alevel.service;

import ua.com.alevel.fileservice.ReadCSV;
import ua.com.alevel.model.Invoice;
import ua.com.alevel.model.Product;
import ua.com.alevel.model.Type;
import ua.com.alevel.repositoriy.OrderRepository;

import java.util.*;

public class ShopService {

    private static final Random RANDOM = new Random();
    private static final int N = 15;
    private static final int MAX_LIMIT = 15;
    private static OrderRepository orderRepository;

    public static void createOrders(double limit) {
        orderRepository = OrderRepository.newInstance();
        List<Product> listProd = ReadCSV.read();
        if (listProd == null) {
            System.out.println("Please check file. Repository is empty!!!");
            return;
        }

        for (int i = 0; i < N; i++) {

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            orderRepository.save(createNeededInvoice(listProd, limit));
        }
    }

    private static Invoice createNeededInvoice(List<Product> listProd, double limit) {
        int randomAmountOfProdInOrder = RANDOM.nextInt(Math.min(MAX_LIMIT, listProd.size())) + 1;
        Set<Product> products = new HashSet<>();
        while (randomAmountOfProdInOrder > 0) {
            Product product = listProd.get(RANDOM.nextInt(listProd.size()));
            if (!products.contains(product)) {
                products.add(product);
                randomAmountOfProdInOrder--;
            }
        }
        double sumPrice = products.stream().mapToDouble(product -> product.getPrice()).sum();
        Type type = sumPrice > limit ? Type.WHOLESALE : Type.RETAIL;
        return new Invoice(products.stream().toList(), PersonService.createPerson(), type, new Date());
    }
}
