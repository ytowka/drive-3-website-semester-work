package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.UserEntity;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface UserDao {

    UserEntity getByEmail(String email) throws SQLException;

    UUID create(UserEntity userEntity) throws SQLException;

    boolean update(UserEntity userEntity) throws SQLException;

    boolean delete(UUID uuid) throws SQLException;

    void confirmEmail(UUID uuid) throws SQLException;

    class Impl implements UserDao{

        //language=SQL
        private static final String INSERT_QUERY = "INSERT INTO account (password_hash, email, username) VALUES(?, ?, ?) RETURNING id";

        //language=SQL
        private static final String UPDATE_QUERY = "UPDATE account SET (password_hash, email, username, is_email_confirmed) = (?, ?, ?, ?) WHERE id = ?";
        //language=SQL
        private static final String UPDATE_CONFIRM_QUERY = "UPDATE account SET (is_email_confirmed) = (TRUE)";
        //language=SQL
        private static final String SELECT_QUERY = "SELECT * FROM account WHERE email = ?";

        //language=SQL
        private static final String DELETE_QUERY = "DELETE FROM account WHERE id = ?";

        private final ConnectionProvider connectionProvider;
        Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        @Override
        public UserEntity getByEmail(String email) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_QUERY);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
               return ORM.parseResultSet(resultSet, UserEntity.class);
            }
            return null;
        }

        @Override
        public UUID create(UserEntity userEntity) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(INSERT_QUERY);
            statement.setString(1, userEntity.encodedPasswordHash());
            statement.setString(2, userEntity.email());
            statement.setString(3, userEntity.userName());
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                return UUID.fromString(resultSet.getString("id"));
            }
            return null;
        }

        @Override
        public boolean update(UserEntity userEntity) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(UPDATE_QUERY);
            statement.setString(1, userEntity.encodedPasswordHash());
            statement.setString(2, userEntity.email());
            statement.setString(3, userEntity.userName());
            statement.setBoolean(4, userEntity.isEmailConfirmed());
            statement.setString(5, userEntity.id().toString());
            int result = statement.executeUpdate();
            return result > 0;
        }

        @Override
        public boolean delete(UUID uuid) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(DELETE_QUERY);
            statement.setString(1, uuid.toString());
            int result = statement.executeUpdate();
            return result > 0;
        }

        @Override
        public void confirmEmail(UUID uuid) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(UPDATE_CONFIRM_QUERY);
            statement.executeUpdate();
        }
    }
}
