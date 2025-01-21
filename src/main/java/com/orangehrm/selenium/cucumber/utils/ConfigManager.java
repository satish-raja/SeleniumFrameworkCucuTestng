package com.orangehrm.selenium.cucumber.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ConfigManager is a singleton class responsible for loading and managing 
 * configuration properties based on the environment (dev, prod, staging, etc.).
 * It provides thread-safe methods to access and update property values.
 */
public class ConfigManager {

    private static volatile ConfigManager instance;
    private static final Object lock = new Object();
    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static String environment;

    // Using ConcurrentHashMap for thread-safe access to dynamic properties
    private static ConcurrentHashMap<String, String> properties = new ConcurrentHashMap<>();

    // Static block to load configuration based on environment
    static {
        environment = System.getProperty("env", "dev").toLowerCase();
        loadProperties();  // Load properties based on the environment
    }

    // Private constructor to prevent instantiation
    private ConfigManager() {}

    /**
     * Returns the singleton instance of ConfigManager.
     *
     * @return The singleton instance of ConfigManager.
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    /**
     * Loads the properties file based on the current environment.
     * The environment is determined by the "env" system property.
     * If the file does not exist or fails to load, a RuntimeException is thrown.
     */
    private static void loadProperties() {
        String configFilePath = "src/test/resources/config/config-" + environment + ".properties";

        if (!isFileExist(configFilePath)) {
            logger.error("Config file for environment '{}' not found at: {}", environment, configFilePath);
            throw new RuntimeException("Config file for environment '" + environment + "' not found.");
        }

        try (FileInputStream input = new FileInputStream(configFilePath)) {
            Properties fileProperties = new Properties();
            fileProperties.load(input);

            // Convert Properties to ConcurrentHashMap for thread-safe access
            for (String key : fileProperties.stringPropertyNames()) {
                properties.put(key, fileProperties.getProperty(key));
            }

            logger.info("Loaded configuration file: {}", configFilePath);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", configFilePath, e);
            throw new RuntimeException("Failed to load configuration file: " + configFilePath, e);
        }
    }

    /**
     * Checks if the specified file exists.
     *
     * @param filePath The path of the file to check.
     * @return true if the file exists, false otherwise.
     */
    private static boolean isFileExist(String filePath) {
        return Paths.get(filePath).toFile().exists();
    }

    /**
     * Retrieves the value of the specified property key in a thread-safe manner.
     * Logs a warning if the property key is not found.
     *
     * @param key The property key to retrieve.
     * @return The value of the property, or null if the key is not found.
     */
    public static String getProperty(String key) {
        String value = properties.get(key);
        if (value == null) {
            logger.warn("Property not found for key: {}", key);
        }
        return value;
    }

    /**
     * Retrieves the value of the specified property key in a thread-safe manner,
     * returning a default value if the key is not found.
     *
     * @param key The property key to retrieve.
     * @param defaultValue The default value to return if the key is not found.
     * @return The value of the property, or the default value if the key is not found.
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getOrDefault(key, defaultValue);
    }

    /**
     * Retrieves the current environment (e.g., dev, prod, staging, etc.).
     *
     * @return The current environment.
     */
    public static String getEnvironment() {
        return environment;
    }

    /**
     * Dynamically updates the value of the specified property key in a thread-safe manner.
     * Logs the updated property key and value.
     *
     * @param key The property key to update.
     * @param value The new value of the property.
     */
    public static void setProperty(String key, String value) {
        properties.put(key, value);
        logger.info("Property updated: {} = {}", key, value);
    }
}