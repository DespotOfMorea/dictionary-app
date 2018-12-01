package com.gzs.main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

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
        boolean isDefaultFromFile = true;
        defaultConfig = ConfigFactory.parseResources("defaults.conf");
        if (defaultConfig.isEmpty()) {
            defaultConfig = loadDefaultConfig();
            isDefaultFromFile = false;
        }
        config = ConfigFactory.parseResources("overrides.conf");

        if (!isDefaultFromFile && config.isEmpty()) {
            log.error("Failed to load configuration files or they are empty.");
        } else if (!isDefaultFromFile) {
            log.error("Failed to load default configuration.");
        } else if (config.isEmpty()) {
            log.error("Failed to load override configuration file.");
        } else {
            log.info("Configuration loaded successfully.");
        }

        config = ConfigFactory.parseResources("overrides.conf").withFallback(defaultConfig).resolve();
    }

    private Config loadDefaultConfig() {
        Map<String, Object> defaultValuesMap = new HashMap();
        defaultValuesMap.put("connection.port", 2222);
        defaultValuesMap.put("database.path", "jdbc:mysql://localhost/");
        defaultValuesMap.put("database.name", "geodictionary");
        defaultValuesMap.put("database.username", "root");
        defaultValuesMap.put("database.password", "");

        Config config = ConfigFactory.parseMap(defaultValuesMap);
        return config;
    }

    private void loadConfigValues() {
        port = config.getInt("connection.port");
        dataType = config.getString("data.type");
        dbPath = config.getString("database.path");
        dbName = config.getString("database.name");
        dbUserName = config.getString("database.username");
        dbPassword = config.getString("database.password");
    }
}