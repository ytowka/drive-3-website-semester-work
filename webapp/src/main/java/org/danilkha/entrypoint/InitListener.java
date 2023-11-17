package org.danilkha.entrypoint;

import org.danilkha.services.*;
import org.danilkha.utils.PropertyReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServiceLocator serviceLocator = new ServiceLocator(sce.getServletContext());

        AuthenticationService authenticationService = serviceLocator.provideAuthenticationService();
        UserService userService = serviceLocator.provideUserService();
        PostsService postsService = serviceLocator.providePostService();
        SubscriptionsService subscriptionsService = serviceLocator.provideSubsService();
        TopicService topicService = serviceLocator.provideTopicService();

        sce.getServletContext().setAttribute(ServiceLocator.AUTH_SERVICE, authenticationService);
        sce.getServletContext().setAttribute(ServiceLocator.USER_SERVICE, userService);
        sce.getServletContext().setAttribute(ServiceLocator.POST_SERVICE, postsService);
        sce.getServletContext().setAttribute(ServiceLocator.SUBSCRIPTIONS_SERVICE, subscriptionsService);
        sce.getServletContext().setAttribute(ServiceLocator.TOPIC_SERVICE, topicService);
    }
}
