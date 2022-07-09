package ua.com.alevel.hw2.model;

import java.util.UUID;

public abstract class TechProduct {

    protected final String id;
    protected final String model;
    protected final Manufacturer manufacturer;
    protected int count;
    protected double price;

    public TechProduct(String model, Manufacturer manufacturer, int count, double price) {
        id = UUID.randomUUID().toString();
        this.model = model;
        this.manufacturer = manufacturer;
        this.count = count;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
