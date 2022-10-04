package ua.com.alevel.hw2.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ua.com.alevel.hw2.model.invoice.Invoice;

import javax.persistence.*;
import java.util.UUID;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TechProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    protected String id;

    @Column
    protected String model;

    @Column
    protected Manufacturer manufacturer;

    @Column
    protected int count;

    @Column
    protected double price;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    protected transient Invoice invoice;

    public TechProduct() {
        this.id = UUID.randomUUID().toString();
    }

    public TechProduct(String model, Manufacturer manufacturer, int count, double price) {
        this();
        this.model = model;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }

    public TechProduct(String id, String model, Manufacturer manufacturer, int count, double price) {
        this.id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }
}
