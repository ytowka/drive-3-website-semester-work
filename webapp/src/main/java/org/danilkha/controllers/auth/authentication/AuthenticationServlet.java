package org.danilkha.controllers.auth.authentication;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.ContentTypes;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@MultipartConfig()
@WebServlet(name = "sign-in", value = "/sign-in")
public class AuthenticationServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        return freemarkerCfg.getTemplate("auth/sign-in.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType(ContentTypes.JSON);
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);

        System.out.println("param map "+req.getParameterMap());
        req.getParameterMap().forEach((key, value) -> {
            System.out.println(key+": "+Arrays.toString(value));
        });

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        System.out.println(password);

        UserDto user = authenticationService.authUser(login, password);
        if(user != null){
            req.getServletContext().setAttribute("user", user);
            resp.setStatus(HttpServletResponse.SC_OK);
        }else{
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
