package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.users.UserSearchApi;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "users", value = "/users")
public class UserSearchServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        root.put("apiPath", "http://localhost:8080%s/api/users?mode=%s"
                .formatted(
                        getServletContext().getContextPath(),
                        UserSearchApi.SEARCH_MODE
                )
        );
        return freemarkerCfg.getTemplate("users/user-search.ftl");
    }
}
