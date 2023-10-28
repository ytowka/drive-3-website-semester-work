package org.danilkha.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.danilkha.ConnectionProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private final BasicDataSource dataSource;

    private static final String HOST = "jdbc:postgresql://localhost:5432/car_configurator_database";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    public ConnectionPool(){
        dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mydatabase");
        dataSource.setUsername("username");
        dataSource.setPassword("password");

        // Set other connection pool configuration properties (e.g., max connection count, validation query)

        dataSource.setMaxTotal(10); // Maximum number of active connections in the pool
        dataSource.setMaxIdle(5); // Maximum number of idle connections in the pool
        dataSource.setMinIdle(2); // Minimum number of idle connections in the pool
    }

    public ConnectionProvider getConnectionProvider(){
        return () -> {
            try {
                return dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
