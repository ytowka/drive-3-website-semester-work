package org.danilkha.freemarker;

import freemarker.template.Configuration;

import javax.servlet.ServletContext;

public class FreemarkerConfiguration {

    private static Configuration cfg = null;
    public static synchronized Configuration getConfig(ServletContext context){
        if(cfg == null){
            cfg = new Configuration(Configuration.VERSION_2_3_32);
            cfg.setServletContextForTemplateLoading(
                    context, "/WEB-INF/templates"
            );
            cfg.setDefaultEncoding("UTF-8");
            cfg.setLogTemplateExceptions(true);
        }
        return cfg;
    }
}
