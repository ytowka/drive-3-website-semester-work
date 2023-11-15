package org.danilkha.controllers.pages.cars;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.danilkha.framework.HtmlServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name="cars", value = "/cars")
public class CarsServlet extends HtmlServlet {

    @Override
    public Template buildPage(HttpServletRequest req, Configuration freemarkerCfg, Map<String, Object> root) throws IOException {
        List<CarResponse> cars = new ArrayList<>();
        for (int i = 0; i < 17; i++) {
            cars.add(new CarResponse(
                    "/cars/vw.jpg",
                    "vw "+i,
                    "http://localhost:8080%s/feed?car=<carId>".formatted(getServletContext().getContextPath())
            ));
        }
        root.put("cars", cars);
        return freemarkerCfg.getTemplate("cars/cars.ftl");
    }
}
