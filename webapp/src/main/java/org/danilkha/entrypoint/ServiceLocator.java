package org.danilkha.entrypoint;

import com.danilkha.service.AuthenticationServiceImpl;
import org.danilkha.ConnectionProvider;
import org.danilkha.dao.UserDao;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.*;

import javax.servlet.ServletContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceLocator {

    public static final String AUTH_SERVICE = "auth_service";
    public static final String USER_PROFILE_PICS_PATH = "/pictures";
    public static final String PROPERTY_FILE = "local.properties";

    private final ServletContext servletContext;

    protected ServiceLocator(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    protected AuthenticationService provideAuthenticationService(
            String basePath
    ){
        return new AuthenticationServiceImpl(
            provideUserDao(),
                provideFileProvider(basePath+USER_PROFILE_PICS_PATH),
                providePasswordEncoder()
        );
    }
    protected FileProvider provideFileProvider(String basePath){
        return new FileProvider(basePath, provideCodeGenerator());
    }

    protected UserDao provideUserDao(){
        return new UserDao.Impl(provideConnectionProvider());
    }

    protected PropertyReader providePropertyReader(){
      return new PropertyReader(servletContext.getRealPath(PROPERTY_FILE));
    }

    protected PasswordEncoder providePasswordEncoder(){
        return new PasswordEncoder(provideMessageDigest());
    }

    protected MessageDigest provideMessageDigest(){
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    protected CodeGenerator provideCodeGenerator(){
        return new CodeGenerator();
    }

    private ConnectionPool _connectionPoolInstance = null;
    protected synchronized ConnectionProvider provideConnectionProvider(){
        if(_connectionPoolInstance == null){
            _connectionPoolInstance = new ConnectionPool();
        }
        return _connectionPoolInstance.getConnectionProvider();
    }
}
