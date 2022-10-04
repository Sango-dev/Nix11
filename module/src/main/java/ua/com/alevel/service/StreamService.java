package ua.com.alevel.service;

import ua.com.alevel.comparator.CustomComparator;
import ua.com.alevel.model.Customer;
import ua.com.alevel.model.Invoice;
import ua.com.alevel.model.Type;
import ua.com.alevel.repositoriy.OrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class StreamService {

    public static void printNumbersOfSoldTelephoneProducts() {
        long count = OrderRepository.getInstance().
                getOrders()
                .stream()
                .flatMap(invoice -> invoice.getProducts().stream().filter(product -> product.getClass().getSimpleName().toString().equals("Telephone")))
                .count();

        System.out.println("1. Amount of sold telephone products: " + count);
    }

    public static void printNumbersOfSoldTelevisionProducts() {
        long count = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .flatMap(invoice -> invoice.getProducts().stream().filter(product -> product.getClass().getSimpleName().toString().equals("Television")))
                .count();

        System.out.println("2. Amount of sold television products: " + count);
    }

    public static void printMinSumAndInfoAboutCustomer() {
        OptionalDouble minSum = OrderRepository.getInstance().
                getOrders()
                .stream()
                .flatMapToDouble(invoice -> DoubleStream.of(invoice.getProducts().stream().mapToDouble(product -> product.getPrice()).sum()))
                .min();

        List<Customer> customer = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .filter(invoice -> invoice.getProducts().stream().mapToDouble(product -> product.getPrice()).sum() == minSum.getAsDouble())
                .map(invoice -> invoice.getCustomer())
                .collect(Collectors.toList());

        System.out.println("3. Min sum: " + minSum.getAsDouble() + ", Customer: " + customer.get(0));
    }

    public static void printWholeSum() {
        double sum = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .flatMapToDouble(invoice -> DoubleStream.of(invoice.getProducts().stream().mapToDouble(product -> product.getPrice()).sum()))
                .sum();

        System.out.println("4. Whole sum: " + sum);
    }

    public static void printAmountOfRetailCheck() {
        long count = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .filter(invoice -> invoice.getType() == Type.RETAIL)
                .count();

        System.out.println("5. Number of checks (RETAIL): " + count);
    }

    public static void printCheckConsistOfOneTypeOfProduct() {
        List<Invoice> listInvoiceTelephone = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .filter(invoice -> invoice.getProducts().stream().filter(product -> product.getClass().getSimpleName().toString().equals("Television")).count() == 0)
                .collect(Collectors.toList());

        List<Invoice> listInvoiceTelevision = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .filter(invoice -> invoice.getProducts().stream().filter(product -> product.getClass().getSimpleName().toString().equals("Telephone")).count() == 0)
                .collect(Collectors.toList());

        System.out.println("6. Checks containing only one type of item: ");
        listInvoiceTelevision.forEach(System.out::println);
        listInvoiceTelephone.forEach(System.out::println);
    }

    public static void printThreeFistChecks() {
        System.out.println("\n7. First three checks made by buyers: ");
        OrderRepository.getInstance()
                .getOrders()
                .stream()
                .sorted(Comparator.comparing(Invoice::getDateOfCreation))
                .limit(3)
                .forEach(System.out::println);
    }

    public static void printInfoAboutChecksWhereCustomersNotAdult() {
        System.out.println("\n8. Information on checks purchased by a user under the age of 18: ");
        OrderRepository.getInstance()
                .getOrders()
                .stream()
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .peek(invoice -> invoice.setType(Type.LOW_AGE))
                .forEach(System.out::println);
    }

    public static void sortStream() {
        System.out.println("\n9. Sort method: ");
        List<Invoice> list = OrderRepository.getInstance()
                .getOrders()
                .stream()
                .sorted(new CustomComparator())
                .collect(Collectors.toList());

        System.out.println("Age\t\tAmount of products\t\ttotal price");
        for (Invoice invoice : list) {
            double sum = invoice.getProducts().stream().mapToDouble(product -> product.getPrice()).sum();
            System.out.println(invoice.getCustomer().getAge() + "\t\t\t" + invoice.getProducts().size() + "\t\t\t\t\t\t" + sum);
        }
    }
}
