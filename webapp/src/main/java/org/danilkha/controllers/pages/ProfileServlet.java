package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.SubscriptionsService;
import org.danilkha.services.UserService;
import org.danilkha.utils.DateFormatter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "profile", value = "/profile/*")
public class ProfileServlet extends HtmlServlet {

    UserService userService;
    SubscriptionsService subscriptionsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        userService = (UserService) config.getServletContext().getAttribute(ServiceLocator.USER_SERVICE);
        subscriptionsService = (SubscriptionsService) config.getServletContext().getAttribute(ServiceLocator.SUBSCRIPTIONS_SERVICE);
    }

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
        String userId = req.getPathInfo().split("/")[1];
        UserDto currentUser = (UserDto)req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);
        boolean isCurrentUser = currentUser != null && currentUser.id().toString().equals(userId);


        UserDto user = userService.getUserById(UUID.fromString(userId));
        List<UserDto> subscribers = subscriptionsService.getSubscribers(user.id());

        boolean isSubscribed = false;
        if(currentUser != null){
            for (UserDto subscriber : subscribers) {
                if(subscriber.id().equals(currentUser.id())){
                    isSubscribed = true;
                    break;
                }
            }
        }

        root.put("feedApiPath", "http://localhost:8080%s/api/feed".formatted(getServletContext().getContextPath()));
        root.put("isCurrentUser", isCurrentUser);
        root.put("subscriptionsCount", subscriptionsService.getSubscriptions(user.id()).size());
        root.put("subscribersCount", subscribers.size());
        root.put("username", userId);
        root.put("user", user);
        root.put("isLoggedIn", currentUser != null);
        root.put("regDate", DateFormatter.formatDate(user.registrationDate()));
        root.put("isSubscribed", isSubscribed);
        return freemarkerCfg.getTemplate("profile/profile.ftl");
    }
}
