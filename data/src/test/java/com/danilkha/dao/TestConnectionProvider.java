package com.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnectionProvider implements ConnectionProvider {
    private static final String HOST = "jdbc:postgresql://localhost:5432/car_configurator_database";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    private Connection connection;
    public TestConnectionProvider(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(HOST, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection provide() {
        return connection;
    }
}
