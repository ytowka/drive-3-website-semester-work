package org.danilkha.controllers.api.subscription;

import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.services.SubscriptionsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "subscription-api", value = "/api/subscribe")
public class SubscriptionServlet extends HttpServlet {

    SubscriptionsService subscriptionsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("init SubscriptionServlet");
        subscriptionsService = (SubscriptionsService) config.getServletContext().getAttribute(ServiceLocator.SUBSCRIPTIONS_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("subscribe");
        UUID userId = UUID.fromString(req.getParameter("userId"));
        boolean subscribe = Boolean.parseBoolean(req.getParameter("subscribe"));
        UUID currentUser = ((UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE)).id();
        System.out.println("subscribe "+userId+" "+subscribe+" "+currentUser );
        resp.setStatus(HttpServletResponse.SC_OK);
        if(subscribe){
            subscriptionsService.subscribeUser(currentUser, userId);
        }else{
            subscriptionsService.unsubscribeUser(currentUser, userId);
        }
    }
}
