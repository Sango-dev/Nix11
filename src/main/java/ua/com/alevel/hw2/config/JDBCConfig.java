package ua.com.alevel.hw2.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public final class JDBCConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/ProductShop";
    private static final String USER = "kwxmuuikcdvnus";
    private static final String PASSWORD = "a7291f133f4577fbbbfceebfb070de06a62db5da0664e84b92d242ebd16853ce";

    private JDBCConfig() {}

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
