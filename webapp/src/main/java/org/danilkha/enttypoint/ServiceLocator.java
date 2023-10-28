package org.danilkha.enttypoint;

import org.danilkha.ConnectionProvider;
import org.danilkha.dao.ConfirmationCodesDao;
import org.danilkha.dao.PasswordResetCodesDao;
import org.danilkha.dao.UserDao;
import org.danilkha.service.AuthenticationServiceImpl;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.CodeGenerator;
import org.danilkha.utils.ConnectionPool;
import org.danilkha.utils.EmailSender;
import org.danilkha.utils.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceLocator {

    public static final String AUTH_SERVICE = "auth_service";

    protected static AuthenticationService provideAuthenticationRepository(){
        return new AuthenticationServiceImpl(
            provideUserDao(),
                provideConfirmationCodesDao(),
                providePasswordResetCodesDao(),
                providePasswordEncoder(),
                10,
                10,
                provideCodeGenerator(),
                provideEmailSender()
        );
    }

    protected static ConfirmationCodesDao provideConfirmationCodesDao(){
        return new ConfirmationCodesDao.Impl(provideConnectionProvider());
    }

    protected static UserDao provideUserDao(){
        return new UserDao.Impl(provideConnectionProvider());
    }

    protected static PasswordResetCodesDao providePasswordResetCodesDao(){
        return new PasswordResetCodesDao.Impl(provideConnectionProvider());
    }

    protected static PasswordEncoder providePasswordEncoder(){
        return new PasswordEncoder(provideMessageDigest());
    }

    protected static MessageDigest provideMessageDigest(){
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected static EmailSender provideEmailSender(){
        return new EmailSender();
    }

    protected static CodeGenerator provideCodeGenerator(){
        return new CodeGenerator();
    }

    private static ConnectionPool _connectionPoolInstance = null;
    protected static synchronized ConnectionProvider provideConnectionProvider(){
        if(_connectionPoolInstance == null){
            _connectionPoolInstance = new ConnectionPool();
        }
        return _connectionPoolInstance.getConnectionProvider();
    }
}
