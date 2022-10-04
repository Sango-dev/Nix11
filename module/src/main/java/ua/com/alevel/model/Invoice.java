package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Invoice {

    private List<Product> products;
    private final Customer customer;
    private Type type;
    private final Date dateOfCreation;

    public Invoice(List<Product> products, Customer customer, Type type, Date dateOfCreation) {
        this.products = products;
        this.customer = customer;
        this.type = type;
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "products=" + products +
                ", customer=" + customer +
                ", type=" + type +
                ", dateOfCreation=" + dateOfCreation +
                '}';
    }
}
