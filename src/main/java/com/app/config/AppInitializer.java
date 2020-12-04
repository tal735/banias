package com.app.config;

import com.app.config.hibernate.HibernateConfig;
import com.app.config.mvc.WebMvcConfiguration;
import com.app.config.security.SecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class <?> [] getRootConfigClasses() {
        return new Class[] { HibernateConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class <?> [] getServletConfigClasses() {
        return new Class[] {WebMvcConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}