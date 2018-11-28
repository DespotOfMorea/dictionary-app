package com.gzs.main;

import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

@Slf4j
public class App {
    private static Configuration config;
    private static final String CONTEXT_ROOT = "/";
    private static DBConnector dbConnector;

    public static void main(String[] args) throws Exception {
//        createDataBase();
//        generateDBData();

        log.info("Starting application");
        config = new Configuration();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            LanguageDaoImpl.endStatements();
//            TermDaoImpl.endStatements();
//            TranslationDaoImpl.endStatements();

            dbConnector = DBConnector.getInstance();
            dbConnector.endConn();
            log.info("Application closed.");
        }));

        Server jettyServer = new Server(config.getPort());

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