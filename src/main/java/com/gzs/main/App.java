package com.gzs.main;

import com.gzs.daos.*;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class App {

    private static Config defaultConfig;
    private static Config fallbackConfig;
    private static int PORT;
    private static final String CONTEXT_ROOT = "/";
    private static DBConnector dbConnector;

    public static void main(String[] args) throws Exception {
//        createDataBase();
//        generateDBData();

        log.info("Starting application");
        loadConfig();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LanguageDaoImpl.endStatements();
            TermDaoImpl.endStatements();
            TranslationDaoImpl.endStatements();

            dbConnector = DBConnector.getInstance();
            dbConnector.endConn();
            log.info("Application closed.");
        }));

        PORT = fallbackConfig.getInt("connection.port");
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

    private static void loadConfig() {
        boolean isDefaultFromFile = true;
        defaultConfig = ConfigFactory.parseResources("defaults.conf");
        if (defaultConfig.isEmpty()) {
            defaultConfig=loadDefaultConfig();
            isDefaultFromFile=false;
        }
        fallbackConfig = ConfigFactory.parseResources("overrides.conf");

        if (!isDefaultFromFile&&fallbackConfig.isEmpty()) {
            log.error("Failed to load configuration files or they are empty.");
        } else if (!isDefaultFromFile) {
            log.error("Failed to load default configuration.");
        } else if (fallbackConfig.isEmpty()) {
            log.error("Failed to load override configuration file.");
        } else {
            log.info("Configuration loaded successfully.");
        }

        fallbackConfig = ConfigFactory.parseResources("overrides.conf").withFallback(defaultConfig).resolve();
    }

    private static Config loadDefaultConfig() {
        Map<String, Object> defaultValuesMap = new HashMap();
        defaultValuesMap.put("connection.port", 2222);
        defaultValuesMap.put("database.path", "jdbc:mysql://localhost/");
        defaultValuesMap.put("database.name", "geodictionary");
        defaultValuesMap.put("database.username", "root");
        defaultValuesMap.put("database.password", "");

        Config config = ConfigFactory.parseMap(defaultValuesMap);
        return config;
    }

    public static Config getFallbackConfig() {
        return fallbackConfig;
    }
}