package com.danilkha.service;

import org.danilkha.Result;
import org.danilkha.utils.CodeGenerator;
import org.danilkha.utils.FileProvider;
import org.danilkha.utils.PasswordEncoder;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.entities.UserEntity;
import org.danilkha.services.AuthenticationService;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;

    private final FileProvider userAvatarFileProvider;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(
            UserDao userDao,
            FileProvider userAvatarFileProvider,
            PasswordEncoder passwordEncoder
    ){
        this.userDao = userDao;
        this.userAvatarFileProvider = userAvatarFileProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Result<UserDto> registerUser(InputStream avatarPicture, @Nullable String fileName, String username, String firstname, String surname,String email, String password){
        byte[] passwordHash = passwordEncoder.hashPassword(password);
        String encodedPassword = passwordEncoder.encodePassword(passwordHash);

        try {
            if(userDao.getByUsername(username) != null){
                return new Result.Error<>(AuthenticationService.REGISTRATION_USERNAME_ALREADY_USED);
            }
            if(userDao.getByEmail(email) != null){
                return new Result.Error<>(AuthenticationService.REGISTRATION_EMAIL_ALREADY_USED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UUID id = null;
        String avatarFileName = null;
        try {
            if(avatarPicture != null){
                try {
                    avatarFileName = userAvatarFileProvider.saveFile(avatarPicture, username+"-"+fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            id = userDao.create(new UserEntity(null, encodedPassword, email, username,firstname, surname, avatarFileName,false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(id != null){
            return new Result.Success<>(new UserDto(
                    id,
                    username,
                    firstname,
                    surname,
                    email,
                    avatarFileName,
                    false
            ));

        }
        return new Result.Error<>("error");
    }

    @Override
    @Nullable
    public UserDto authUser(String login, String password)  {
        UserEntity userEntity;
        try {
            if(login.contains("@")){
                userEntity = userDao.getByEmail(login);
            }else{
                userEntity = userDao.getByUsername(login);
            }

            if(userEntity != null){
                byte[] passwordDecoded = passwordEncoder.decodePassword(userEntity.encodedPasswordHash());
                boolean isPasswordCorrect = Arrays.equals(passwordDecoded, passwordEncoder.hashPassword(password));
                if(isPasswordCorrect){
                    return userEntity.toDto();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
