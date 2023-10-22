package org.danilkha.repo;

import org.danilkha.utils.PasswordEncoder;
import org.danilkha.dao.ConfirmationCodesDao;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.entities.ConfirmationCodeEntity;
import org.danilkha.entities.UserEntity;
import org.danilkha.repos.UserRepository;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository {

    private final UserDao userDao;
    private final ConfirmationCodesDao confirmationCodesDao;

    private final PasswordEncoder passwordEncoder;


    public UserRepositoryImpl(
            UserDao userDao,
            ConfirmationCodesDao confirmationCodesDao,
            PasswordEncoder passwordEncoder
    ){
        this.userDao = userDao;
        this.confirmationCodesDao = confirmationCodesDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UUID registerUser(UserDto userDto, String password) throws SQLException {
        byte[] passwordHash = passwordEncoder.hashPassword(password);
        String encodedPassword = passwordEncoder.encodePassword(passwordHash);
        return userDao.create(new UserEntity(null, encodedPassword, userDto.email(), userDto.name(), userDto.avatarUri(),false));
    }

    @Override
    @Nullable
    public UserDto authUser(String email, String password) throws SQLException {
        UserEntity userEntity = userDao.getByEmail(email);
        if(!userEntity.isEmailConfirmed()){
            boolean isExpired = isEmailConfirmationCodeExpired(userEntity.id());
            if(isExpired){
                userDao.delete(userEntity.id());
                confirmationCodesDao.delete(userEntity.id());
                return null;
            }
        }
        byte[] passwordDecoded = passwordEncoder.decodePassword(userEntity.encodedPasswordHash());
        boolean isPasswordCorrect = Arrays.equals(passwordDecoded, passwordEncoder.hashPassword(password));
        if(isPasswordCorrect){
            return userEntity.toDto();
        }
        return null;
    }

    @Override
    public boolean confirmEmail(UUID userId, int code) throws SQLException {
        ConfirmationCodeEntity confirmationCode = confirmationCodesDao.getById(userId);
        if(confirmationCode.code() == code){
            confirmationCodesDao.delete(userId);
            userDao.confirmEmail(userId);
            return true;
        }
        return false;
    }

    @Override
    public void sendEmailConfirmationCode(UUID userId) throws SQLException {

    }

    @Override
    public boolean resetPassword(UUID userId, String link, String newPassword) throws SQLException {
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email) throws SQLException {
        return false;
    }

    private boolean isEmailConfirmationCodeExpired(UUID userId){
        return false;
    }
}
