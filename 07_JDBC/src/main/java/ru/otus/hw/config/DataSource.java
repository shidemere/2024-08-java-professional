package ru.otus.hw.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
    private String url;
    private Connection connection;

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(url, "username", "password");
        System.out.println("Установлено соединение с БД: " + url);
        return connection;
    }

    public DataSource(String url) {
        this.url = url;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("От БД отключились");
    }
}
