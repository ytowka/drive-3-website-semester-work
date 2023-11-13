package org.danilkha.controllers.feed;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.dto.PostDto;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "feed", value = "/feed")
public class FeedServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        List<PostDto> postDtoList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            postDtoList.add(new PostDto(
                    UUID.randomUUID(),
                    new Date(),
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    "content "+i
            ));
        }
        root.put("posts", postDtoList);
        return freemarkerCfg.getTemplate("feed/feed.ftl");
    }
}
