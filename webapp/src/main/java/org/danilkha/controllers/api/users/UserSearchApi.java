package org.danilkha.controllers.api.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.services.SubscriptionsService;
import org.danilkha.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "user-search", value = "/api/users")
public class UserSearchApi extends HttpServlet {

    public static final String SEARCH_MODE = "search";
    public static final String SUBSCRIBERS_MODE = "subscribers";
    public static final String SUBSCRIPTIONS_MODE = "subscriptions";

    private final ObjectMapper objectMapper = new ObjectMapper();

    UserService userService;
    SubscriptionsService subscriptionsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        userService = (UserService) getServletContext().getAttribute(ServiceLocator.USER_SERVICE);
        subscriptionsService = (SubscriptionsService) getServletContext().getAttribute(ServiceLocator.SUBSCRIPTIONS_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserResponse> users = new ArrayList<>();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String mode = req.getParameter("mode");
        String query = req.getParameter("query");
        UUID userId = ((UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE)).id();
        switch (mode){
            case SEARCH_MODE ->{
                users = mapUsers(userService.searchUser(query));
            }
            case SUBSCRIPTIONS_MODE -> {
                List<UserDto> userDtos = subscriptionsService.getSubscriptions(userId);
                users = mapUsers(userDtos);
            }
            case SUBSCRIBERS_MODE -> {
                users = mapUsers(subscriptionsService.getSubscribers(userId));
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        UsersResponse response = new UsersResponse(users);
        resp.getWriter().write(objectMapper.writeValueAsString(response));
    }

    private List<UserResponse> mapUsers(List<UserDto> userDtos){
        return userDtos.stream().map(userDto -> new UserResponse(
                userDto.username(),
                userDto.firstname()+" "+userDto.surname(),
                userDto.avatarUri(),
                "http://localhost:8080%s/profile/%s".formatted(getServletContext().getContextPath(), userDto.id().toString())
        )).toList();
    }
}
