package org.danilkha.framework;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.danilkha.ContentTypes;
import org.danilkha.freemarker.FreemarkerConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HtmlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(ContentTypes.HTML);
        resp.setCharacterEncoding("UTF-8");
        Configuration freemarkerCfg = FreemarkerConfiguration.getConfig(getServletContext());
        Map<String, Object> root = new HashMap<>();
        root.put("contextPath", getServletContext().getContextPath());
        Template template = buildPage(req, freemarkerCfg, root);
        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException;
}
