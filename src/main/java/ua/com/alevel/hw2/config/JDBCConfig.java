package ua.com.alevel.hw2.config;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public final class JDBCConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/ProductShop";
    private static final String USER = "postgres";
    private static final String PASSWORD = "rootroot";

    private JDBCConfig() {}

    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
