package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.comments.CommentResponse;
import org.danilkha.dto.CommentDto;
import org.danilkha.dto.PostDto;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.PostsService;
import org.danilkha.utils.DateFormatter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "post", value = "/post/*")
public class PostServlet extends HtmlServlet {

    PostsService postsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postsService = (PostsService) config.getServletContext().getAttribute(ServiceLocator.POST_SERVICE);
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        String postId = req.getPathInfo().split("/")[1];
        PostDto postDto = postsService.getPostById(UUID.fromString(postId));

        UserDto userDto = (UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);

        List<UUID> likes = postsService.getPostLikes(UUID.fromString(postId));
        int likeCount = likes.size();

        boolean isLiked = false;

        if(userDto != null){
            isLiked = likes.contains(userDto.id());
        }

        String basePage = "http://localhost:8080%s".formatted(getServletContext().getContextPath());

        String topicLink = basePage+"/feed?topic="+postDto.topic().name();
        String topicName = postDto.topic().name();
        String date = DateFormatter.formatDateTime(postDto.datetime());
        String authorUrl = basePage+"/profile/"+postDto.author().id();
        String author = postDto.author().username();
        String avatar = postDto.author().avatarUri();
        String image = postDto.picture();
        String text = postDto.content();

        root.put("postId", postId);
        root.put("likeCount", likeCount);
        root.put("isLiked", isLiked);
        root.put("topicLink", topicLink);
        root.put("topicName", topicName);
        root.put("date", date);
        root.put("authorUrl", authorUrl);
        root.put("author", author);
        root.put("avatar", avatar);
        root.put("image",image);
        root.put("text", text);
        if(userDto != null){
            root.put("userId", userDto.id());
        }


        List<CommentResponse> commentDtoList = postsService.getPostComments(UUID.fromString(postId)).stream().map(commentDto ->{
            return new CommentResponse(
                    commentDto.userDto().avatarUri(),
                    commentDto.userDto().username(),
                    DateFormatter.formatDateTime(commentDto.date()),
                    basePage+"/profile/"+commentDto.userDto().id(),
                    commentDto.text()
            );
        }).toList();



        root.put("comments", commentDtoList);
        return freemarkerCfg.getTemplate("post/post.ftl");
    }
}
