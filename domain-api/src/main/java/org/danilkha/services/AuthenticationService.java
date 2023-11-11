package org.danilkha.services;

import org.danilkha.Result;
import org.danilkha.dto.UserDto;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.UUID;

public interface AuthenticationService {

    public static final String REGISTRATION_EMAIL_ALREADY_USED = "email-already-used";
    public static final String REGISTRATION_USERNAME_ALREADY_USED = "username-already-used";

    /**
     * @param avatarPicture input stream of file
     * @param username
     * @param email
     * @param password
     * @return Result
     * @throws SQLException
     */
    Result<UserDto> registerUser(
            @Nullable InputStream avatarPicture,
            @Nullable String fileName,
            String username,
            String firstname,
            String surname,
            String email,
            String password
    );


    /**
     *
     * @param login username or email
     * @param password auth password
     * @return UserDto object if authentication was successful, null if credentials was wrong or there is no user with
     * @throws SQLException
     */
    @Nullable
    UserDto authUser(String login, String password);
}
