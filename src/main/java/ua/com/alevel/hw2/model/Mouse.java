package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Mouse extends TechProduct {

    private ConnectionType connectionType;
    private int dpiAmount;

    private Mouse() {}

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

    public static class Builder{
        private Mouse mouse;

        public Builder(ConnectionType connectionType, double price) {
            mouse = new Mouse();
            mouse.setConnectionType(connectionType);
            mouse.setPrice(price);
        }

        public Builder setModel(String model) {
            if (model.length() <= 20) {
                mouse.setModel(model);
                return this;
            }
            throw new IllegalArgumentException("Model length must be not greater than 20");
        }

        public Builder setManufacturer(Manufacturer manufacturer) {
            mouse.setManufacturer(manufacturer);
            return this;
        }

        public Builder setCount(int count) {
            if (count > 0) {
                mouse.setCount(count);
                return this;
            }
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        public Builder setDpiAmount(int dpiAmount) {
            mouse.setDpiAmount(dpiAmount);
            return this;
        }

        public Mouse build() {
            return mouse;
        }
    }
}
