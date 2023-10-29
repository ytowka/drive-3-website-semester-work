package org.danilkha.controllers.registration;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.framework.HtmlServlet;

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
        return freemarkerCfg.getTemplate("auth/sign-up.ftl");
    }
}
