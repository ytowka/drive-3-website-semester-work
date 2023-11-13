package org.danilkha.entrypoint;

import org.danilkha.services.AuthenticationService;
import org.danilkha.services.UserService;
import org.danilkha.utils.PropertyReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServiceLocator appServiceLocator = new ServiceLocator(sce.getServletContext());
        AuthenticationService authenticationService = appServiceLocator.provideAuthenticationService();
        UserService userService = appServiceLocator.provideUserService();

        sce.getServletContext().setAttribute(ServiceLocator.AUTH_SERVICE, authenticationService);
        sce.getServletContext().setAttribute(ServiceLocator.USER_SERVICE, userService);
    }
}
