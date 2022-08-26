package ua.com.alevel.hw2;

import ua.com.alevel.hw2.model.ConnectionType;
import ua.com.alevel.hw2.model.Manufacturer;
import ua.com.alevel.hw2.model.Mouse;

public class Main {

    public static void main(String[] args) {
        Mouse mouse = new Mouse.Builder(ConnectionType.WIRED, 98.1)
                .setDpiAmount(1000)
                .setManufacturer(Manufacturer.SAMSUNG)
                .setCount(2)
                .setModel("Globe X")
                .build();

        System.out.println(mouse);
    }
}
