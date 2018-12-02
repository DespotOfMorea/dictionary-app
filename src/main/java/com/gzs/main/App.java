package com.gzs.main;

import com.gzs.daos.mysql.LanguageDaoMySQLImpl;
import com.gzs.daos.mysql.TermDaoMySQLImpl;
import com.gzs.daos.mysql.TranslationDaoMySQLImpl;
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
            log.info("Starting application shutdown process.");
            LanguageDaoMySQLImpl.endStatements();
            log.info("LanguageDao statements closed.Starting TermDao statements closing...");
            TermDaoMySQLImpl.endStatements();
            log.info("TermDao statements closed.Starting TranslationDao statements closing...");
            TranslationDaoMySQLImpl.endStatements();
            log.info("TranslationDao statements closed.Starting connection closing...");

            dbConnector = DBConnector.getInstance();
            dbConnector.endConn();
            log.info("Connection is closed.");
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