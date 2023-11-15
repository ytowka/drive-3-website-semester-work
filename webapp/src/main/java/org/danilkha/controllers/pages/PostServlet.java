package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.comments.CommentResponse;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "post", value = "/post/*")
public class PostServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        String postId = req.getPathInfo().split("/")[1];
        int likeCount = 10;
        boolean isLiked = true;
        String topicLink = "topicLink";
        String topicName = "topic";
        String date = "date";
        String authorUrl = "authorUrl";
        String author = "author";
        String avatar = "avatar";
        String image = "/cars/vw.jpg";
        String text = "text";

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

        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            commentResponseList.add(new CommentResponse(
                    "/pictures/AUIJYZsoQaomHw==1699868967299ZGFuaWxraGEt0KHQvdC40LzQvtC6INGN0LrRgNCw0L3QsCAyMDIzLTAzLTI0IDIzMDE0NC5wbmc=.png",
                    "name",
                    "14.03.2023",
                    "http://localhost:8080/drive3/profile/532d5e2a-4e1c-41df-a7d6-df412189d486",
                    "awesome "+i
            ));
        }
        root.put("comments", commentResponseList);
        return freemarkerCfg.getTemplate("post/post.ftl");
    }
}
