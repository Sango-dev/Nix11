package ua.com.alevel.hw2.model.product;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class WashingMachine extends TechProduct {

    private final int turnsNumber;

    public WashingMachine(String model, Manufacturer manufacturer, int count, double price, int turnsNumber) {
        super(model, manufacturer, count, price);
        this.turnsNumber = turnsNumber;
    }

    public WashingMachine(String id, String model, Manufacturer manufacturer, int count, double price, int turnsNumber) {
        super(id, model, manufacturer, count, price);
        this.turnsNumber = turnsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WashingMachine)) return false;
        WashingMachine washingMachine = (WashingMachine) o;
        return turnsNumber == washingMachine.turnsNumber
                && id.equals(washingMachine.id) && model.equals(washingMachine.model)
                && manufacturer == washingMachine.manufacturer && count == washingMachine.count
                && Double.compare(price, washingMachine.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, count, price, turnsNumber);
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
