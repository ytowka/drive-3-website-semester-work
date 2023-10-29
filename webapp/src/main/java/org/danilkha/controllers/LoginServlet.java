package org.danilkha.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.danilkha.ContentTypes;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.freemarker.FreemarkerConfiguration;
import org.danilkha.utils.ClientInfoReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "sign-up", value = "/sign-up")
public class LoginServlet extends HtmlServlet {

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        System.out.println(ClientInfoReader.getClientInfo(req));
        return freemarkerCfg.getTemplate("auth/sign-up.ftl");
    }
}
