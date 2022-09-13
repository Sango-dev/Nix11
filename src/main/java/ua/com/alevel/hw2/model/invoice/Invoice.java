package ua.com.alevel.hw2.model.invoice;

import lombok.Getter;
import lombok.Setter;
import ua.com.alevel.hw2.model.product.TechProduct;


import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Invoice {
    private String id;
    private double sum;
    private List<TechProduct> products;
    private Date date;

    public Invoice(String id, double sum, List<TechProduct> products, Date date) {
        this.id = id;
        this.sum = sum;
        this.products = products;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return sum == invoice.sum && id.equals(invoice.id) && products.equals(invoice.products) && date.equals(invoice.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum, products, date);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", sum=" + sum +
                ", products=" + products +
                ", date=" + date +
                '}';
    }
}
