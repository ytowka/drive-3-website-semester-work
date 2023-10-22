package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.PasswordResetLinkEntity;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface PasswordResetCodesDao {

    PasswordResetLinkEntity getByLink(String link) throws SQLException;

    boolean create(PasswordResetLinkEntity userEntity) throws SQLException;

    boolean delete(UUID uuid) throws SQLException;

    class Impl implements PasswordResetCodesDao {
        //language=SQL
        private static final String INSERT_QUERY = "INSERT INTO password_reset_links (account_id, link) VALUES(?, ?) ON CONFLICT (account_id) DO UPDATE SET link = ?, creation_time = now()";

        //language=SQL
        private static final String SELECT_QUERY = "SELECT * FROM password_reset_links WHERE link = ?";

        //language=SQL
        private static final String DELETE_QUERY = "DELETE FROM password_reset_links WHERE account_id = ?";


        private final ConnectionProvider connectionProvider;

        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        @Override
        public PasswordResetLinkEntity getByLink(String link) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_QUERY);
            statement.setString(1, link);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                return ORM.parseResultSet(resultSet, PasswordResetLinkEntity.class);
            }
            return null;
        }

        @Override
        public boolean create(PasswordResetLinkEntity confirmationCodeEntity) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(INSERT_QUERY);
            statement.setObject(1, confirmationCodeEntity.userId());
            statement.setString(2, confirmationCodeEntity.link());
            statement.setString(3, confirmationCodeEntity.link());
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
    }
}
