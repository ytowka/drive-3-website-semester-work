package org.danilkha.controllers.pages.cars;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.controllers.api.topic.TopicResponse;
import org.danilkha.entrypoint.ServiceLocator;
import org.danilkha.framework.HtmlServlet;
import org.danilkha.services.TopicService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name="cars", value = "/cars")
public class CarsServlet extends HtmlServlet {

    TopicService topicService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        topicService = (TopicService) config.getServletContext().getAttribute(ServiceLocator.TOPIC_SERVICE);
    }

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {

        List<CarResponse>  cars = topicService.getAllTopics().stream().map(topicDto -> new CarResponse(
                 topicDto.picture(),
                 topicDto.name(),
                "http://localhost:8080%s/feed?topic=%s".formatted(getServletContext().getContextPath(), topicDto.name())
         )).toList();

        root.put("cars", cars);
        return freemarkerCfg.getTemplate("cars/cars.ftl");
    }
}
