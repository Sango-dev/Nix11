package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telephone extends Product {

    private final Manufacturer model;

    public Telephone(String series, Manufacturer model, ScreenType screenType, double price) {
        super(series, screenType, price);
        this.model = model;
    }

    @Override
    public String toString() {
        return "Telephone{" +
                "series='" + series + '\'' +
                ", model=" + model +
                ", screenType=" + screenType +
                ", price=" + price +
                '}';
    }
}
