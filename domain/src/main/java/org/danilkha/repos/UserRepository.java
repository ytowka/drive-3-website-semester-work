package org.danilkha.repos;

import org.danilkha.dto.UserDto;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.UUID;

public interface UserRepository {


    /**
     *
     * @param userDto y
     * @param password
     * @return
     * @throws SQLException
     */
    @Nullable
    UUID registerUser(UserDto userDto, String password) throws SQLException;


    /**
     *
     * @param email auth email
     * @param password auth password
     * @return UserDto object if authentication was successful, null if credentials was wrong or there is no user with
     * @throws SQLException
     */
    @Nullable
    UserDto authUser(String email, String password) throws SQLException;

    /**
     *
     * @param userId user id
     * @param code email confirmation code
     * @return true if email was successfully confirmed
     * @throws SQLException
     */
    boolean confirmEmail(UUID userId, int code) throws SQLException;


    /**
     *
     * @param userId user id
     * @throws SQLException
     */
    void sendEmailConfirmationCode(UUID userId) throws SQLException;

    /**
     * @param userId user id
     * @param link confirmation link
     * @param newPassword new password
     * @return true if password was changed, false if link is invalid
     * @throws SQLException
     */
    boolean resetPassword(UUID userId, String link, String newPassword) throws SQLException;

    /**
     *
     * @param email account's email that request password reset
     * @return true if reset link was successfully sent, false if there is no account with such email
     * @throws SQLException
     */
    boolean requestPasswordReset(String email) throws SQLException;
}
