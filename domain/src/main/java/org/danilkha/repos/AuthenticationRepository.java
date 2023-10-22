package org.danilkha.repos;

import org.danilkha.dto.UserDto;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.UUID;

public interface AuthenticationRepository {


    /**
     *
     * @param name
     * @param email
     * @param avatarUri
     * @param password
     * @return UserDto object if account was successfully created
     * @throws SQLException
     */
    @Nullable
    UserDto registerUser(String name,
                         String email,
                         String avatarUri,
                         String password);


    /**
     *
     * @param email auth email
     * @param password auth password
     * @return UserDto object if authentication was successful, null if credentials was wrong or there is no user with
     * @throws SQLException
     */
    @Nullable
    UserDto authUser(String email, String password);

    /**
     *
     * @param userId user id
     * @param code email confirmation code
     * @return true if email was successfully confirmed, false if code is wrong or expired
     * @throws SQLException
     */
    boolean confirmEmail(UUID userId, int code);


    /**
     *
     * @param userId user id
     * @throws SQLException
     */
    void sendEmailConfirmationCode(UUID userId);

    /**
     * @param link confirmation link
     * @param newPassword new password
     * @return true if password was changed, false if link is invalid or expired
     * @throws SQLException
     */
    boolean resetPassword(String link, String newPassword);

    /**
     *
     * @param email account's email that request password reset
     * @return true if reset link was successfully sent, false if there is no account with such email
     * @throws SQLException
     */
    boolean requestPasswordReset(String email);
}