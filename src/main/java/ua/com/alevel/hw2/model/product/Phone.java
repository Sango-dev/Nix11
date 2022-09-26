package ua.com.alevel.hw2.model.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Phone extends TechProduct {
    @Column(name = "core_numbers")
    private int coreNumbers;

    @Column(name = "battery_power")
    private int batteryPower;

    @Transient
    private List<String> details;

    public Phone(String model, Manufacturer manufacturer, int count, double price, int coreNumbers, int batteryPower) {
        super(model, manufacturer, count, price);
        this.coreNumbers = coreNumbers;
        this.batteryPower = batteryPower;
        this.details = Collections.emptyList();
    }

    public Phone(String model, Manufacturer manufacturer, int count, double price, int coreNumbers, int batteryPower, List<String> details) {
        super(model, manufacturer, count, price);
        this.coreNumbers = coreNumbers;
        this.batteryPower = batteryPower;
        this.details = details;
    }

    public Phone(String id, String model, Manufacturer manufacturer, int count, double price, int coreNumbers, int batteryPower) {
        super(id, model, manufacturer, count, price);
        this.coreNumbers = coreNumbers;
        this.batteryPower = batteryPower;
        this.details = Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return coreNumbers == phone.coreNumbers && batteryPower == phone.batteryPower
                && id.equals(phone.id) && model.equals(phone.model)
                && manufacturer == phone.manufacturer && count == phone.count
                && Double.compare(price, phone.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, count, price, coreNumbers, batteryPower);
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
