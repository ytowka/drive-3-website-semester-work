package org.danilkha;

import org.danilkha.dto.UserDto;
import org.danilkha.repos.UserRepository;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserRepositoryImpl implements UserRepository {

    //language=SQL
    private static String INSERT_QUERY = "INSERT INTO account (password_hash, email, username) VALUES(?, ?, ?)";
    //language=SQL
    private static String SELECT_QUERY = "SELECT * FROM account WHERE email = ?";
    private Connection connection;


    public UserRepositoryImpl(Connection connection){
        this.connection = connection;

    }

    @Override
    public boolean RegisterUser(UserDto userDto, String password) throws SQLException {
        byte[] passwordHash = PasswordCrypto.hashPassword(password);
        String encodedPassword = PasswordCrypto.encodePassword(passwordHash);
        PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);
        statement.setString(1, encodedPassword);
        statement.setString(2, userDto.email());
        statement.setString(3, userDto.name());
        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean authUser(String email, String password) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if(resultSet.next()){
            String passwordHash = resultSet.getString("password_hash");
            byte[] passwordDecoded = PasswordCrypto.decodePassword(passwordHash);
            byte[] insertedPassword = PasswordCrypto.hashPassword(password);

            return Arrays.equals(passwordDecoded, insertedPassword);
        }else{
            return false;
        }
    }
}
