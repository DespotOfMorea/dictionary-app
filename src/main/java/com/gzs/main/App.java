package com.gzs.main;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class App {

    private static final int PORT = 2222;
    private static final String CONTEXT_ROOT = "/";

    public static void main(String[] args) throws Exception {
//        createDataBase();
//        generateDBData();
      
        log.info("Starting application");
      
        Server jettyServer = new Server(PORT);

        final ServletContextHandler context = new ServletContextHandler(jettyServer, CONTEXT_ROOT);
        final ServletHolder restEasyServlet = new ServletHolder(new HttpServletDispatcher());
        restEasyServlet.setInitParameter("javax.ws.rs.Application","com.gzs.main.RestApplication");
      
        context.addServlet(restEasyServlet,"/*");

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
}