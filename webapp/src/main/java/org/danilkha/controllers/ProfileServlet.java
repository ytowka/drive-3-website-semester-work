package org.danilkha.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "profile", value = "/profile/*")
public class ProfileServlet extends HtmlServlet {
    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        System.out.println(req.getServletPath());
        System.out.println(req.getServletContext().getServerInfo());
        return freemarkerCfg.getTemplate("profile/profile.ftl");
    }
}
