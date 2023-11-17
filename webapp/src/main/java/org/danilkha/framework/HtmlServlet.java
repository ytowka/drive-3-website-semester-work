package org.danilkha.framework;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.danilkha.ContentTypes;
import org.danilkha.dto.UserDto;
import org.danilkha.entrypoint.AuthServletFilter;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.freemarker.FreemarkerConfiguration;
import org.danilkha.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HtmlServlet extends HttpServlet {


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(ContentTypes.HTML);
        resp.setCharacterEncoding("UTF-8");
        Configuration freemarkerCfg = FreemarkerConfiguration.getConfig(getServletContext());
        Map<String, Object> root = new HashMap<>();
        root.put("contextPath", getServletContext().getContextPath());
        UserDto userDto = (UserDto) req.getSession().getAttribute(AuthServletFilter.USER_ATTRIBUTE);
        root.put("isUserLoggedIn", userDto != null);
        if(userDto != null){
            root.put("userAvatar", userDto.avatarUri());
        }
        Template template = buildPage(req, freemarkerCfg, root);
        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException;
}
