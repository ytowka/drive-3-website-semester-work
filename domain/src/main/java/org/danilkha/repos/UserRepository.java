package org.danilkha.repos;

import org.danilkha.dto.UserDto;

import java.sql.SQLException;

public interface UserRepository {

    boolean RegisterUser(UserDto userDto, String password) throws SQLException;

    boolean authUser(String email, String password) throws SQLException;
}
