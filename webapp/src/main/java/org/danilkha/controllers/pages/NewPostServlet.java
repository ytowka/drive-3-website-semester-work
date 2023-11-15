package org.danilkha.controllers.pages;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.topic.TopicResponse;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@WebServlet(name = "new-post", value = "/new-post")
public class NewPostServlet extends HtmlServlet {

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        List<TopicResponse> topics = new ArrayList<>();

        for (int i = 0; i < 19; i++) {
            topics.add(new TopicResponse(
                    UUID.randomUUID(),
                    "topic "+i
            ));
        }

        root.put("topics", topics);
        return freemarkerCfg.getTemplate("new-post/new-post.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("new post ");
    }
}
