package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Phone extends TechProduct {

    private final int coreNumbers;
    private final int batteryPower;

    public Phone(String model, Manufacturer manufacturer, int count, double price, int coreNumbers, int batteryPower) {
        super(model, manufacturer, count, price);
        this.coreNumbers = coreNumbers;
        this.batteryPower = batteryPower;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", coreNumbers=" + coreNumbers +
                ", batteryPower=" + batteryPower +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
