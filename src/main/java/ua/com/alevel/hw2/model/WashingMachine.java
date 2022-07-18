package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WashingMachine extends TechProduct {

    private final int turnsNumber;

    public WashingMachine(String model, Manufacturer manufacturer, int count, double price, int turnsNumber) {
        super(model, manufacturer, count, price);
        this.turnsNumber = turnsNumber;
    }

    @Override
    public String toString() {
        return "WashingMachine{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", turnsNumber=" + turnsNumber +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
