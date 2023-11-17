package org.danilkha.controllers.api.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.danilkha.dto.PostDto;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.services.PostsService;
import org.danilkha.utils.DateFormatter;

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
import java.util.stream.Collectors;

@WebServlet(name = "feed-api", value = "/api/feed")
public class FeedApiServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postsService = (PostsService) config.getServletContext().getAttribute(ServiceLocator.POST_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {;
        resp.setStatus(HttpServletResponse.SC_OK);
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String basePage = "http://localhost:8080%s/".formatted(getServletContext().getContextPath());

        int page = 0;

        UserDto userDto = (UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);

        String topic = req.getParameter("topic");
        String userId = req.getParameter("userId");

        List<PostDto> postsDtos;
        if(topic != null){
            System.out.println("get topic posts "+topic);
            postsDtos = postsService.getPostsByTopic(topic);
        } else if (userId != null) {
            System.out.println("get user posts "+userId);
            postsDtos = postsService.getUserPosts(UUID.fromString(userId));
        } else{
            page = Integer.parseInt(req.getParameter("page"));
            if(userDto != null){
                System.out.println("get feed posts "+userDto.id());
                postsDtos = postsService.getUserFeed(userDto.id(), page);
                System.out.println("got user feed "+postsDtos.size());
            }else{
                System.out.println("get all posts");
                postsDtos = postsService.getAllFeed(page);
            }

        }

        System.out.println("map to response");
        List<PostResponse> postsResponse = mapToResponse(postsDtos, userDto, basePage);
        System.out.println("mapped to response");
        PostListResponse response = new PostListResponse(
                page,
                postsResponse
        );
        resp.getWriter().write(objectMapper.writeValueAsString(response));
    }

    private List<PostResponse> mapToResponse(List<PostDto> postDtos, UserDto userDto, String basePage){
        return postDtos.stream().map(postDto -> {
            List<UUID> postLikes =  postsService.getPostLikes(postDto.id());
            System.out.println("get post likes "+postLikes.size());
            boolean isLiked = false;
            if(userDto != null){
                isLiked = postLikes.contains(userDto.id());
            }
            return new PostResponse(
                    postDto.id(),
                    postDto.author().username(),
                    basePage+"profile/"+postDto.author().id(),
                    postDto.author().avatarUri(),
                    DateFormatter.formatDateTime(postDto.datetime()),
                    postDto.picture(),
                    postDto.content(),
                    postLikes.size(),
                    isLiked,
                    basePage+"feed?topic="+postDto.topic().name(),
                    postDto.topic().name()

            );
        }).collect(Collectors.toList());
    }
}
