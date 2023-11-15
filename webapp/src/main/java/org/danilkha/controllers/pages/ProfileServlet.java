package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "profile", value = "/profile/*")
public class ProfileServlet extends HtmlServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if(req.getPathInfo() == null){
            String userId = ((UserDto)req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE)).id().toString();
            resp.sendRedirect("http://localhost:8080%s/profile/%s".formatted(getServletContext().getContextPath(), userId));
        }else{
            super.doGet(req, resp);
        }
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        System.out.println(req.getPathInfo());
        String userId = req.getPathInfo().split("/")[1];
        UserDto userDto = (UserDto)req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);

        root.put("feedApiPath", "http://localhost:8080%s/api/feed?user=%S".formatted(getServletContext().getContextPath(),userId));
        root.put("isCurrentUser", userDto.id().toString().equals(userId));
        root.put("subscriptionsCount",10);
        root.put("subscribersCount",20);
        root.put("userId", userId);
        root.put("username", userId);
        root.put("realName", userDto.firstname()+" "+userDto.surname());
        root.put("registrationDate", userDto.registrationDate().toString());
        root.put("avatar", userDto.avatarUri());
        return freemarkerCfg.getTemplate("profile/profile.ftl");
    }
}
