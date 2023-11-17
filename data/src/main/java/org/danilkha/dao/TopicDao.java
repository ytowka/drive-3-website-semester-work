package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.TopicEntity;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TopicDao {

    List<TopicEntity> getAllTopics() throws SQLException;
    Optional<TopicEntity> getTopicById(UUID id) throws SQLException;

    Optional<TopicEntity> getTopicByName(String name) throws SQLException;

    class Impl implements TopicDao{

        private final ConnectionProvider connectionProvider;
        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        //language=SQL
        private static final String getAll = "SELECT * from topic";

        //language=SQL
        private static final String getByID = "SELECT * from topic where id = ?";

        //language=SQL
        private static final String getByName = "SELECT * from topic where name = ?";

        @Override
        public List<TopicEntity> getAllTopics() throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(getAll);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, TopicEntity.class);
        }

        @Override
        public Optional<TopicEntity> getTopicById(UUID id) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(getByID);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(ORM.parseResultSet(resultSet, TopicEntity.class));
            }
            return Optional.empty();
        }

        @Override
        public Optional<TopicEntity> getTopicByName(String name) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(getByName);
            statement.setObject(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return Optional.of(ORM.parseResultSet(resultSet, TopicEntity.class));
            }
            return Optional.empty();
        }
    }
}
