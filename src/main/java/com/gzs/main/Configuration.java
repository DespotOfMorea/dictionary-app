package com.gzs.main;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Configuration {
    private static volatile Configuration instance;
    private static Config defaultConfig;
    private static Config fallbackConfig;

    private Configuration() {
        loadConfig();
    }

    public static synchronized Configuration getInstance() {
        if (instance == null) {
            synchronized (Configuration.class) {
                if (instance == null) {
                    instance = new Configuration();
                }
            }
        }
        return instance;
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

    public Config getConfiguration() {
        return fallbackConfig;
    }
}