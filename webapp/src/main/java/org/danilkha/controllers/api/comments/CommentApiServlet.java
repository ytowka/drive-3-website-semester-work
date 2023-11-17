package org.danilkha.controllers.api.comments;

import org.danilkha.controllers.pages.PostServlet;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.services.PostsService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


@WebServlet(name="comment-api",value = "/api/comment")
public class CommentApiServlet extends HttpServlet {

    PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postsService = (PostsService) config.getServletContext().getAttribute(ServiceLocator.POST_SERVICE);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postsService.addComment(
                UUID.fromString(req.getParameter("postId")),
                ((UserDto)req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE)).id(),
                req.getParameter("text"),
                null
        );
    }
}
