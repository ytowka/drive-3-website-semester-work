package org.danilkha.dao;

import org.danilkha.ConnectionProvider;
import org.danilkha.entities.ConfirmationCodeEntity;
import org.example.orm.ORM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public interface ConfirmationCodesDao {

    ConfirmationCodeEntity getById(UUID userId) throws SQLException;

    boolean create(ConfirmationCodeEntity userEntity) throws SQLException;

    boolean delete(UUID uuid) throws SQLException;

    class Impl implements ConfirmationCodesDao{
        //language=SQL
        private static final String INSERT_QUERY = "" +
                "INSERT INTO email_confirmation_codes (account_id, code) VALUES(?, ?)" +
                " ON CONFLICT (account_id) DO UPDATE SET code = ?, creation_time = now()";

        //language=SQL
        private static final String SELECT_QUERY = "SELECT * FROM email_confirmation_codes WHERE account_id = ?";

        //language=SQL
        private static final String DELETE_QUERY = "DELETE FROM email_confirmation_codes WHERE account_id = ?";


        private final ConnectionProvider connectionProvider;

        public Impl(ConnectionProvider connectionProvider){
            this.connectionProvider = connectionProvider;
        }

        @Override
        public ConfirmationCodeEntity getById(UUID userId) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(SELECT_QUERY);
            statement.setObject(1, userId);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if(resultSet.next()){
                return ORM.parseResultSet(resultSet, ConfirmationCodeEntity.class);
            }
            return null;
        }

        @Override
        public boolean create(ConfirmationCodeEntity confirmationCodeEntity) throws SQLException {
            PreparedStatement statement = connectionProvider.provide().prepareStatement(INSERT_QUERY);
            statement.setObject(1, confirmationCodeEntity.userId());
            statement.setInt(2, confirmationCodeEntity.code());
            statement.setInt(3, confirmationCodeEntity.code());
            System.out.println(statement.toString());
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
