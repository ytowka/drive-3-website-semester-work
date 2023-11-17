package org.danilkha.controllers.pages.newpost;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.topic.TopicResponse;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.PostsService;
import org.danilkha.services.TopicService;
import org.danilkha.utils.MultipartUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@MultipartConfig(
        fileSizeThreshold = 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5
)
@WebServlet(name = "new-post", value = "/new-post")
public class NewPostServlet extends HtmlServlet {

    PostsService postsService;
    TopicService topicService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        postsService = (PostsService) config.getServletContext().getAttribute(ServiceLocator.POST_SERVICE);
        topicService = (TopicService) config.getServletContext().getAttribute(ServiceLocator.TOPIC_SERVICE);
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        List<TopicResponse> topics = topicService.getAllTopics().stream().map(dto -> new TopicResponse(
                dto.id(),
                dto.name()
        )).collect(Collectors.toList());

        root.put("topics", topics);

        return freemarkerCfg.getTemplate("new-post/new-post.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try {
            Part part = req.getPart("image");
            InputStream picture = null;
            String fileName = null;
            if(part != null){
                picture = part.getInputStream();
                fileName = MultipartUtils.getFileName(part);
            }
            System.out.println("file: "+fileName);
            String topicId = req.getParameter("topic");
            String text = req.getParameter("text");
            UserDto currentUser = (UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);
            UUID id = postsService.writeNewPost(
                    picture,
                    fileName,
                    currentUser.id(),
                    text,
                    UUID.fromString(topicId)
            );
            resp.getWriter().write(objectMapper.writeValueAsString(new NewPostResponse(id.toString())));
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("new post ");
    }
}
