package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

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
