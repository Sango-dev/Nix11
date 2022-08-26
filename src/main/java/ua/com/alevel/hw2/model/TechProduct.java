package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class TechProduct {

    protected final String id;
    protected String model;
    protected Manufacturer manufacturer;
    protected int count;
    protected double price;

    public TechProduct() {
        id = UUID.randomUUID().toString();
    }

    public TechProduct(String model, Manufacturer manufacturer, int count, double price) {
        id = UUID.randomUUID().toString();
        this.model = model;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }
}
