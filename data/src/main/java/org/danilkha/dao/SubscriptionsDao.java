package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.SubscriptionEntity;
import org.danilkha.entities.UserEntity;
import org.example.orm.Insertion;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface SubscriptionsDao {
    void subscribeUser(SubscriptionEntity subscriptionEntity) throws SQLException;
    void unsubscribeUser(SubscriptionEntity subscriptionEntity) throws SQLException;
    List<UserEntity> getSubscribers(UUID userId) throws SQLException;
    List<UserEntity> getSubscriptions(UUID userId) throws SQLException;

    List<UUID> getSubscriptionsIds(UUID userId) throws SQLException;

    class Impl implements SubscriptionsDao{

        private final ConnectionProvider connectionProvider;

        //language=SQL
        private static final String DELETE = "DELETE FROM subscriptions WHERE from_user_id = ? AND to_user_id = ?";

        //language=SQL
        private static final String GET_SUBSCRIBERS = "SELECT * FROM account WHERE id IN (SELECT from_user_id FROM subscriptions WHERE to_user_id = ?)";
        //language=SQL
        private static final String GET_SUBSCRIPTIONS = "SELECT * FROM account WHERE id IN (SELECT to_user_id FROM subscriptions WHERE from_user_id = ?)";

        //language=SQL
        private static final String GET_SUBSCRIPTIONS_IDS = "SELECT to_user_id FROM subscriptions WHERE from_user_id = ?";

        public Impl(ConnectionProvider connectionProvider) {
            this.connectionProvider = connectionProvider;
        }


        @Override
        public void subscribeUser(SubscriptionEntity subscriptionEntity) throws SQLException {
            PreparedStatement statement = Insertion.prepareInsertStatement(connectionProvider.provide(), subscriptionEntity);
            statement.executeUpdate();
        }

        @Override
        public void unsubscribeUser(SubscriptionEntity subscriptionEntity) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(DELETE);
            statement.setObject(1, subscriptionEntity.from());
            statement.setObject(2, subscriptionEntity.to());
            statement.executeUpdate();
        }

        @Override
        public List<UserEntity> getSubscribers(UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_SUBSCRIBERS);
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, UserEntity.class);
        }

        @Override
        public List<UserEntity> getSubscriptions(UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_SUBSCRIPTIONS);
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();
            return ORM.parseResultSetList(resultSet, UserEntity.class);
        }

        @Override
        public List<UUID> getSubscriptionsIds(UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(GET_SUBSCRIPTIONS);
            statement.setObject(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<UUID> ids = new ArrayList<>();
            while (resultSet.next()){
                ids.add((UUID) resultSet.getObject("to_user_id"));
            }
            return ids;
        }
    }
}
