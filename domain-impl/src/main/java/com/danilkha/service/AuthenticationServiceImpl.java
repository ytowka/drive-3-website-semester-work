package com.danilkha.service;

import org.danilkha.Result;
import org.danilkha.dao.PasswordResetCodesDao;
import org.danilkha.entities.PasswordResetLinkEntity;
import org.danilkha.utils.CodeGenerator;
import org.danilkha.utils.EmailSender;
import org.danilkha.utils.PasswordEncoder;
import org.danilkha.dao.ConfirmationCodesDao;
import org.danilkha.dao.UserDao;
import org.danilkha.dto.UserDto;
import org.danilkha.entities.ConfirmationCodeEntity;
import org.danilkha.entities.UserEntity;
import org.danilkha.services.AuthenticationService;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserDao userDao;
    private final ConfirmationCodesDao confirmationCodesDao;
    private final PasswordResetCodesDao passwordResetCodesDao;

    private final PasswordEncoder passwordEncoder;
    private final CodeGenerator codeGenerator;

    private final EmailSender emailSender;

    private final int emailConfirmationCodeAgeMinutes;
    private final int passwordResetCodeAgeMinutes;

    public AuthenticationServiceImpl(
            UserDao userDao,
            ConfirmationCodesDao confirmationCodesDao,
            PasswordResetCodesDao passwordResetCodesDao, PasswordEncoder passwordEncoder,
            int emailConfirmationCodeAgeMinutes,
            int passwordResetCodeAgeMinutes,
            CodeGenerator codeGenerator,
            EmailSender emailSender
    ){
        this.userDao = userDao;
        this.confirmationCodesDao = confirmationCodesDao;
        this.passwordResetCodesDao = passwordResetCodesDao;
        this.passwordEncoder = passwordEncoder;
        this.emailConfirmationCodeAgeMinutes = emailConfirmationCodeAgeMinutes;
        this.passwordResetCodeAgeMinutes = passwordResetCodeAgeMinutes;
        this.codeGenerator = codeGenerator;
        this.emailSender = emailSender;
    }

    @Override
    public Result<UserDto> registerUser(String name, String email, String avatarUri, String password){
        byte[] passwordHash = passwordEncoder.hashPassword(password);
        String encodedPassword = passwordEncoder.encodePassword(passwordHash);

        try {
            if(userDao.getByUsername(name) != null){
                return new Result.Error<>(AuthenticationService.REGISTRATION_USERNAME_ALREADY_USED);
            }
            if(userDao.getByEmail(email) != null){
                return new Result.Error<>(AuthenticationService.REGISTRATION_EMAIL_ALREADY_USED);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        UUID id = null;
        try {
            id = userDao.create(new UserEntity(null, encodedPassword, email, name, avatarUri,false));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(id != null){
            sendEmailConfirmationCode(id);
            return new Result.Success<>(new UserDto(
                    id,
                    name,
                    email,
                    avatarUri,
                    false
            ));

        }
        return null;
    }

    @Override
    @Nullable
    public UserDto authUser(String email, String password)  {
        UserEntity userEntity = null;
        try {
            userEntity = userDao.getByEmail(email);
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

    @Override
    public boolean confirmEmail(UUID userId, int code){
        try {
            ConfirmationCodeEntity confirmationCode = confirmationCodesDao.getById(userId);
            boolean isFresh = System.currentTimeMillis() - confirmationCode.creationTime().getTime() < (long) emailConfirmationCodeAgeMinutes * 60 * 1000;
            if(confirmationCode.code() == code && isFresh){
                confirmationCodesDao.delete(userId);
                userDao.confirmEmail(userId);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public void sendEmailConfirmationCode(UUID userId) {
        int code = codeGenerator.generate6SignCode();
        try {
            confirmationCodesDao.create(
                    new ConfirmationCodeEntity(
                            userId,
                            code,
                            null
                    )
            );
            String userEmail = userDao.getById(userId).email();
            emailSender.sendEmail(userEmail, "password reset link: "+code,"password reset");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean resetPassword(String link, String newPassword) {
        try {
            PasswordResetLinkEntity passwordResetLink = passwordResetCodesDao.getByLink(link);
            boolean isFresh = System.currentTimeMillis() - passwordResetLink.creationTime().getTime() < (long) passwordResetCodeAgeMinutes * 60 * 1000;
            if(passwordResetLink != null && isFresh){
                byte[] passwordHash = passwordEncoder.hashPassword(newPassword);
                String encodedPassword = passwordEncoder.encodePassword(passwordHash);
                userDao.updatePassword(passwordResetLink.userId(), encodedPassword);
                passwordResetCodesDao.delete(passwordResetLink.userId());
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email)  {
        UserEntity userEntity = null;
        try {
            userEntity = userDao.getByEmail(email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (userEntity == null){
            return false;
        }

        String code = codeGenerator.generateStringCode(32);
        try {
            passwordResetCodesDao.create(new PasswordResetLinkEntity(
                    userEntity.id(),
                    code,
                    null
            ));
            emailSender.sendEmail(email, "password reset link: "+code,"password reset");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
