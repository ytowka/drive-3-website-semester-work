package org.danilkha.controllers.api;

import org.danilkha.controllers.pages.PostServlet;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.PostsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "likes-api", value = "/api/like")
public class LikesApiServlet extends HttpServlet {

    PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postsService = (PostsService) config.getServletContext().getAttribute(ServiceLocator.POST_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("do like");
        UUID postId = UUID.fromString(req.getParameter("postId"));
        UUID userId = UUID.fromString(req.getParameter("userId"));
        boolean isLiked = Boolean.parseBoolean(req.getParameter("isLiked"));
        System.out.println("read");
        System.out.println(userId);
        postsService.changeLikeState(
                postId,
                userId,
                isLiked
        );
        System.out.println(postId+" "+isLiked+" "+ userId);
    }
}
