package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.dto.PostDto;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "feed", value = "/feed")
public class FeedServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        root.put("feedApiPath", "http://localhost:8080%s/api/feed".formatted(getServletContext().getContextPath()));
        String topic = req.getParameter("topic");
        UserDto userDto = (UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);
        if(topic != null){
            root.put("topic", topic);

        }
        if(userDto != null){
            root.put("userId", userDto.id().toString());
        }
        return freemarkerCfg.getTemplate("feed/feed.ftl");
    }
}
