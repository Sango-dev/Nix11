package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Product {

    protected final String series;
    protected final ScreenType screenType;
    protected double price;

    protected Product(String series, ScreenType screenType, double price) {
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }
}
