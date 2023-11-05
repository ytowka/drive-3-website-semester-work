package org.danilkha.entrypoint;

import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.PropertyReader;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.awt.*;
import java.io.File;

@WebListener
public class InitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServiceLocator appServiceLocator = new ServiceLocator(sce.getServletContext());
        PropertyReader propertyReader = appServiceLocator.providePropertyReader();
        String basePath = propertyReader.getProp("RES_DIR");
        AuthenticationService authenticationService = appServiceLocator.provideAuthenticationService(basePath);
        sce.getServletContext().setAttribute(ServiceLocator.AUTH_SERVICE, authenticationService);
    }
}
