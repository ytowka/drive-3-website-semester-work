package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.UserEntity;
import org.example.orm.Insertion;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface UserDao {

    UserEntity getByEmail(String email) throws SQLException;
    UserEntity getByUsername(String username) throws SQLException;

    UserEntity getById(UUID uuid) throws SQLException;

    UUID create(UserEntity userEntity) throws SQLException;

    boolean update(UserEntity userEntity) throws SQLException;

    boolean delete(UUID uuid) throws SQLException;

    void updatePassword(UUID uuid, String newPasswordHash) throws SQLException;

    class Impl implements UserDao{

        //language=SQL
        private static final String UPDATE_QUERY = "UPDATE account SET (password_hash, email, username, registration_date) = (?, ?, ?, ?) WHERE id = ?";
        //language=SQL
        private static final String UPDATE_PASSWORD_QUERY = "UPDATE account SET password_hash = ? where id = ?";
        //language=SQL
        private static final String SELECT_BY_EMAIL_QUERY = "SELECT * FROM account WHERE email = ?";
        //language=SQL
        private static final String SELECT_BY_USERNAME_QUERY = "SELECT * FROM account WHERE username = ?";
        //language=SQL
        private static final String SELECT_QUERY = "SELECT * FROM account WHERE id = ?";

        //language=SQL
        private static final String DELETE_QUERY = "DELETE FROM account WHERE id = ?";

        private final ConnectionProvider connectionProvider;
        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        @Override
        public UserEntity getByEmail(String email) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_BY_EMAIL_QUERY);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
               return ORM.parseResultSet(resultSet, UserEntity.class);
            }
            return null;
        }

        @Override
        public UserEntity getByUsername(String username) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_BY_USERNAME_QUERY);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return ORM.parseResultSet(resultSet, UserEntity.class);
            }
            return null;
        }

        @Override
        public UserEntity getById(UUID uuid) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_QUERY);
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return ORM.parseResultSet(resultSet, UserEntity.class);
            }
            return null;
        }

        @Override
        public UUID create(UserEntity userEntity) throws SQLException {
            PreparedStatement statement = Insertion.prepareInsertStatement(connectionProvider.provide(), userEntity);
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
            statement.setString(3, userEntity.username());
            statement.setObject(4, userEntity.registrationDate());
            statement.setObject(5, userEntity.id());
            int result = statement.executeUpdate();
            return result > 0;
        }

        @Override
        public boolean delete(UUID uuid) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(DELETE_QUERY);
            statement.setObject(1, uuid);
            int result = statement.executeUpdate();
            return result > 0;
        }

        @Override
        public void updatePassword(UUID uuid, String newPasswordHash) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(UPDATE_PASSWORD_QUERY);
            statement.setString(1, newPasswordHash);
            statement.setObject(2, uuid);
            statement.execute();
        }
    }
}
