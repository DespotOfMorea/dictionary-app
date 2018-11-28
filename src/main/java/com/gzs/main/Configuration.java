package com.gzs.main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Configuration {

    private static Config defaultConfig;
    private static Config fallbackConfig;

    public Configuration() {
        loadConfig();
    }

    private void loadConfig() {
        boolean isDefaultFromFile = true;
        defaultConfig = ConfigFactory.parseResources("defaults.conf");
        if (defaultConfig.isEmpty()) {
            defaultConfig = loadDefaultConfig();
            isDefaultFromFile = false;
        }
        fallbackConfig = ConfigFactory.parseResources("overrides.conf");

        if (!isDefaultFromFile && fallbackConfig.isEmpty()) {
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

    public int getPort() {
        return fallbackConfig.getInt("connection.port");
    }

    public String getDataType() {
        return fallbackConfig.getString("data.type");
    }

    public String getDBPath() {
        return fallbackConfig.getString("database.path");
    }

    public String getDBName() {
        return fallbackConfig.getString("database.name");
    }

    public String getDBUserName() {
        return fallbackConfig.getString("database.username");
    }

    public String getDBPassword() {
        return fallbackConfig.getString("database.password");
    }
}