package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Mouse extends TechProduct {

    private final ConnectionType connectionType;
    private final int dpiAmount;

    public Mouse(String model, Manufacturer manufacturer, int count, double price, ConnectionType connectionType, int dpiAmount) {
        super(model, manufacturer, count, price);
        this.connectionType = connectionType;
        this.dpiAmount = dpiAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mouse)) return false;
        Mouse mouse = (Mouse) o;
        return connectionType == mouse.connectionType && dpiAmount == mouse.dpiAmount
                && id.equals(mouse.id) && model.equals(mouse.model)
                && manufacturer == mouse.manufacturer && count == mouse.count
                && Double.compare(price, mouse.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, manufacturer, count, price, connectionType, dpiAmount);
    }

    @Override
    public String toString() {
        return "Mouse{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer=" + manufacturer +
                ", connectionType=" + connectionType +
                ", dpiAmount=" + dpiAmount +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
