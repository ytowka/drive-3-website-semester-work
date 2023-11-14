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
        return freemarkerCfg.getTemplate("feed/feed.ftl");
    }
}
