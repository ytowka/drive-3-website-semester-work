package org.danilkha.controllers.pages.auth;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.ContentTypes;
import org.danilkha.Result;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.AuthenticationService;
import org.danilkha.utils.RememberMeCookies;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE) != null){
            resp.sendRedirect("feed");
        }else{
            super.doGet(req, resp);
        }
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        return freemarkerCfg.getTemplate("auth/sign-in.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        AuthenticationService authenticationService = (AuthenticationService) getServletContext().getAttribute(ServiceLocator.AUTH_SERVICE);

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        Result<UserDto> result = authenticationService.authUser(login, password);
        if(result instanceof Result.Success<UserDto> success){
            if("on".equals(req.getParameter("rememberMe"))){
                RememberMeCookies.addCredentialsCookie(resp, success.getData());
            }
            req.getServletContext().setAttribute(AuthServletFilter.USER_ATTRIBUTE, success.getData());
            resp.setStatus(HttpServletResponse.SC_OK);
        }else{
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
