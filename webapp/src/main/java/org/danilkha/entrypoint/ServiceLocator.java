package org.danilkha.entrypoint;

import com.danilkha.service.*;
import org.danilkha.ConnectionProvider;
import org.danilkha.dao.*;
import org.danilkha.services.*;
import org.danilkha.utils.*;

import javax.servlet.ServletContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServiceLocator {

    public static final String AUTH_SERVICE = "auth_service";
    public static final String USER_SERVICE = "user_service";
    public static final String POST_SERVICE = "post_service";
    public static final String SUBSCRIPTIONS_SERVICE = "subscription_service";
    public static final String TOPIC_SERVICE = "topic_service";


    public final String USER_PROFILE_PICS_PATH;
    public final String POST_PICTURES_PATH;
    public final String TOPICS_PICTURES_PATH;
    public final int PAGINATION_PAGE_SIZE;
    public final String RES_DIR;

    public static final String PROPERTY_FILE = "local.properties";

    protected ServiceLocator(ServletContext servletContext) {
        PropertyReader propertyReader = new PropertyReader(servletContext.getRealPath(PROPERTY_FILE));

        USER_PROFILE_PICS_PATH = propertyReader.getProp("user_pictures_path");
        POST_PICTURES_PATH = propertyReader.getProp("post_pictures_path");
        TOPICS_PICTURES_PATH = propertyReader.getProp("topic_pictures_path");
        PAGINATION_PAGE_SIZE = Integer.parseInt(propertyReader.getProp("page_size"));
        RES_DIR = propertyReader.getProp("RES_DIR");
    }

    protected AuthenticationService provideAuthenticationService(){
        return new AuthenticationServiceImpl(
                userDaoLazy.get(),
                provideFileProvider(USER_PROFILE_PICS_PATH),
                passwordEncoderLazy.get(),
                USER_PROFILE_PICS_PATH
        );
    }


    protected UserService provideUserService(){
        return new UserServiceImpl(
                USER_PROFILE_PICS_PATH,
                userDaoLazy.get()
        );
    }

    protected PostsService providePostService(){
        return new PostsServiceImpl(
                userDaoLazy.get(),
                postDaoLazy.get(),
                likesDaoLazy.get(),
                commentsDaoLazy.get(),
                topicDaoLazy.get(),
                subscriptionsDaoLazy.get(),
                PAGINATION_PAGE_SIZE,
                TOPICS_PICTURES_PATH,
                POST_PICTURES_PATH,
                USER_PROFILE_PICS_PATH,
                provideFileProvider("")
        );
    }

    protected SubscriptionsService provideSubsService(){
        return new SubscriptionsServiceImpl(
                subscriptionsDaoLazy.get(),
                userDaoLazy.get(),
                USER_PROFILE_PICS_PATH
        );
    }

    protected TopicService provideTopicService(){
        return new TopicServiceImpl(
                topicDaoLazy.get(),
                TOPICS_PICTURES_PATH
        );
    }



    protected FileProvider provideFileProvider(String basePath){
        return new FileProvider(RES_DIR+basePath, provideCodeGenerator());
    }

    protected Lazy<UserDao> userDaoLazy = new Lazy<>(() -> new UserDao.Impl(provideConnectionProvider()));
    protected Lazy<TopicDao> topicDaoLazy = new Lazy<>(() -> new TopicDao.Impl(provideConnectionProvider()));
    protected Lazy<SubscriptionsDao> subscriptionsDaoLazy = new Lazy<>(() -> new SubscriptionsDao.Impl(provideConnectionProvider()));
    protected Lazy<PostDao> postDaoLazy = new Lazy<>(() -> new PostDao.Impl(provideConnectionProvider()));
    protected Lazy<LikesDao> likesDaoLazy = new Lazy<>(() -> new LikesDao.Impl(provideConnectionProvider()));
    protected Lazy<CommentsDao> commentsDaoLazy = new Lazy<>(() -> new CommentsDao.Impl(provideConnectionProvider()));

    protected Lazy<PasswordEncoder> passwordEncoderLazy = new Lazy<>(() -> new PasswordEncoder(provideMessageDigest()));

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
