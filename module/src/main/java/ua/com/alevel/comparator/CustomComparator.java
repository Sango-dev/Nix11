package ua.com.alevel.comparator;

import ua.com.alevel.model.Invoice;

import java.util.Comparator;

public class CustomComparator implements Comparator<Invoice> {
    @Override
    public int compare(Invoice o1, Invoice o2) {

        int ageF = o1.getCustomer().getAge();
        int ageS = o2.getCustomer().getAge();
        int szF = o1.getProducts().size();
        int szS = o2.getProducts().size();

        if (ageF == ageS) {
            if (szF == szS) {
                return Double.compare(getSum(o1), getSum(o2));
            }
            return Integer.compare(szF, szS);
        }
        return Double.compare(ageS, ageF);
    }

    private double getSum(Invoice invoice) {
        return invoice.getProducts().stream().mapToDouble(product -> product.getPrice()).sum();
    }
}