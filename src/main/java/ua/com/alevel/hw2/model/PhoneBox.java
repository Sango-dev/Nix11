package ua.com.alevel.hw2.model;

import lombok.Setter;

import java.util.Date;

@Setter
public class PhoneBox {

    private final String title;
    private final String model;
    private double price;
    private String currency;
    private final Manufacturer manufacturer;
    private final Date created;
    private int count;
    private OperatingSystem operatingSystem;

    public PhoneBox() {
        title = "";
        model = "";
        price = 0.0;
        currency = "";
        manufacturer = null;
        created = null;
        count = 0;
        operatingSystem = null;
    }

    public PhoneBox(String title,
                    String model,
                    double price,
                    String currency,
                    Manufacturer manufacturer,
                    Date created,
                    int count,
                    OperatingSystem operatingSystem) {

        this.title = title;
        this.model = model;
        this.price = price;
        this.currency = currency;
        this.manufacturer = manufacturer;
        this.created = created;
        this.count = count;
        this.operatingSystem = operatingSystem;
    }

    @Override
    public String toString() {
        return "PhoneBox{" +
                "title='" + title + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", manufacturer=" + manufacturer +
                ", created=" + created +
                ", count=" + count +
                ", operatingSystem=" + operatingSystem +
                '}';
    }
}
