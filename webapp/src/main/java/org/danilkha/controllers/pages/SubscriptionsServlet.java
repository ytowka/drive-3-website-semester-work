package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.users.UserSearchApi;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebServlet(name="subscriptions", value = "/subscriptions/*")
public class SubscriptionsServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        String userId = req.getPathInfo().split("/")[1];
        root.put("apiPath", "http://localhost:8080%s/api/users?mode=%s&userId=%s"
                .formatted(
                        getServletContext().getContextPath(),
                        UserSearchApi.SUBSCRIPTIONS_MODE,
                        userId
                ));
        return freemarkerCfg.getTemplate("users/subscriptions.ftl");
    }
}
