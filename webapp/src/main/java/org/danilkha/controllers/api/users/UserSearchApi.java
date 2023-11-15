package org.danilkha.controllers.api.users;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "user-search", value = "/api/users")
public class UserSearchApi extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserResponse> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(
                    new UserResponse(
                            "danilkha",
                            "danil khairulin",
                            "/pictures/AUIJYZsoQaomHw==1699868967299ZGFuaWxraGEt0KHQvdC40LzQvtC6INGN0LrRgNCw0L3QsCAyMDIzLTAzLTI0IDIzMDE0NC5wbmc=.png",
                            "http://localhost:8080%s/profile/532d5e2a-4e1c-41df-a7d6-df412189d486".formatted(getServletContext().getContextPath())
                    )
            );
        }
        System.out.println("mode "+req.getParameter("mode"));
        System.out.println("userId "+req.getParameter("mode"));
        System.out.println("query "+req.getParameter("query"));
        resp.setStatus(HttpServletResponse.SC_OK);
        UsersResponse response = new UsersResponse(users);
        resp.getWriter().write(objectMapper.writeValueAsString(response));
    }
}
