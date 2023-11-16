package com.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.dao.TopicDao;
import org.danilkha.entities.TopicEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class TestTopicDao {

    private static final String HOST = "jdbc:postgresql://localhost:5432/car_configurator_database";
    private static final String USER = "postgres";
    private static final String PASS = "admin";

    ConnectionProvider connectionProvider;

    TopicDao topicDao;
    @BeforeEach
    void setup(){
        connectionProvider = new TestConnectionProvider();
        topicDao = new TopicDao.Impl(connectionProvider);
    }

    @Test
    void getAllWorks() throws SQLException {
        Assertions.assertDoesNotThrow(() -> {
            List<TopicEntity> list = topicDao.getAllTopics();
            System.out.println(Arrays.toString(list.toArray()));
        });
    }

    @Test
    void getByIdWorks(){
        Assertions.assertDoesNotThrow(() -> {
            topicDao.getTopicById(UUID.randomUUID());
        });
    }
}
