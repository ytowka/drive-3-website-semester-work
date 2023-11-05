package org.danilkha.controllers.registration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.ValidationConfig;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.utils.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "sign-up", value = "/sign-up")
public class RegistrationServlet extends HtmlServlet {


    public static final String usernameRegexPattern = "[A-z0-9_-]+";

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        root.put("usernameRegexPattern", usernameRegexPattern);
        root.put("minNameLength", ValidationConfig.minNameLength);
        root.put("maxNameLength",  ValidationConfig.maxNameLength);
        root.put("minPasswordLength", ValidationConfig.mimPasswordLength);
        return freemarkerCfg.getTemplate("auth/sign-up.ftl");
    }
}
