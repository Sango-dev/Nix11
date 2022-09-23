package ua.com.alevel.hw2.model.invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ua.com.alevel.hw2.model.product.TechProduct;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Invoice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "created")
    private double sum;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TechProduct> products;

    @Column
    private Date date;

    public Invoice(double sum, List<TechProduct> products, Date date) {
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
