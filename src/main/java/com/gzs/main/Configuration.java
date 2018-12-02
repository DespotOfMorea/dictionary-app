package com.gzs.main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Configuration {
    
    private static Config defaultConfig;
    private static Config config;

    private int port;
    private String dataType;
    private String dbPath;
    private String dbName;
    private String dbUserName;
    private String dbPassword;

    public Configuration() {
        loadConfigFiles();
        loadConfigValues();
    }

    private void loadConfigFiles() {
        defaultConfig = ConfigFactory.parseResources("defaults.conf");
        config = ConfigFactory.parseResources("configuration.conf");

        if (defaultConfig.isEmpty() && config.isEmpty()) {
            log.error("Failed to load configuration files or they are empty.");
        } else if (defaultConfig.isEmpty()) {
            log.error("Failed to load default configuration.");
        } else if (config.isEmpty()) {
            log.error("Failed to load override configuration file.");
        } else {
            log.info("Configuration loaded successfully.");
        }
        config = ConfigFactory.parseResources("configuration.conf").withFallback(defaultConfig).resolve();
    }

    private void loadConfigValues() {
        port = config.hasPath("connection.port") ? config.getInt("connection.port") : 2222;
        dataType = config.hasPath("data.type") ? config.getString("data.type") : "MySQL";
        dbPath = config.hasPath("database.path") ? config.getString("database.path") : "jdbc:mysql://localhost/";
        dbName = config.hasPath("database.name") ? config.getString("database.name") : "geodictionary";
        dbUserName = config.hasPath("database.username") ? config.getString("database.username") : "root";
        dbPassword = config.hasPath("database.password") ? config.getString("database.password") : "";
    }
}