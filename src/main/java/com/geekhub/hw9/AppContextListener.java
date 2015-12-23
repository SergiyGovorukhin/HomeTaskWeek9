package com.geekhub.hw9;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("ServletContextListener started");
        ServletContext context = event.getServletContext();
        context.setAttribute("ctx", context.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {}
}
