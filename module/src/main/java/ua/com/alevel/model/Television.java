package ua.com.alevel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Television extends Product {

    private final Country country;
    private final double diagonal;

    public Television(String series, double diagonal, ScreenType screenType, Country country, double price) {
        super(series, screenType, price);
        this.country = country;
        this.diagonal = diagonal;
    }

    @Override
    public String toString() {
        return "Television{" +
                "series='" + series + '\'' +
                ", diagonal=" + diagonal +
                ", screenType=" + screenType +
                ", country=" + country +
                ", price=" + price +
                '}';
    }
}
