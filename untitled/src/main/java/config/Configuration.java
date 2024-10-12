package config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import logger.Log;
import org.yaml.snakeyaml.Yaml;


import java.io.*;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class Configuration {

    /**
     * Method to initialize configuration.
     */

    public Properties initializeConfig() {
        Properties conf = new Properties();
        Properties configuration = new Properties();
        Properties translationConf;

        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/configuration.properties")) {

            conf.load(fis);

            Map<String, String> environmentConfig;

            String environmentProperty = System.getProperty("environment");

            String environment = (environmentProperty != null) ? environmentProperty : conf.getProperty("ENV_NAME");

            environmentConfig = parseYaml("src/main/resources/Environments.yml", environment);

            conf = initializeSessions(conf, environment);

            conf = loadDeviceProperty(environmentConfig, validateDeviceProperties(conf));

            configuration.putAll(conf);

        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        }
        return configuration;
    }




    /**
     * Method to load properties specific for the emulator/device
     * param: properties
     * param: config
     * the map of emulator properties read from the sessions-sit.yml
     * file
     */

    public Properties loadDeviceProperty(Map<String, String> properties, Properties config) {
        for (Entry<String, String> iterator : properties.entrySet()) {
            config.setProperty(iterator.getKey(), iterator.getValue());
        }
        return config;
    }


    private static Properties validateDeviceProperties(Properties config) {
        if (System.getProperty("platformName") != null) {
            config.replace("OS", System.getProperty("platformName"));
        }
        if (System.getProperty("environment") != null) {
            config.replace("ENV_NAME", System.getProperty("environment"));
        }
        return config;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, String> parseYaml(String filename, String parameter) throws IOException {
        FileInputStream fis = null;
        Map<String, Object> platforms;
        Map<String, String> configs = null;
        try {
            fis = new FileInputStream(System.getProperty("user.dir") + "/" + filename);
            platforms = new Yaml().load(fis);
            for (Entry<String, Object> key : platforms.entrySet()) {
                if (key.getKey().equalsIgnoreCase(parameter)) {
                    configs = (Map<String, String>) key.getValue();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return configs;
    }

    public Properties initializeConstants(Properties config) {
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/constants.properties")) {
            config.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        }
        return config;
    }

    public Properties initializeSessions(Properties config, String environment) {
        try {
            Map<String, String> additionalConfig;

            String platformNameProperty = System.getProperty("platformName");
            String platformName = (platformNameProperty != null) ? platformNameProperty
                    : config.getProperty("PLATFORM_NAME");

            additionalConfig = parseYaml("src/main/resources/sessions/sessions-" + environment.toLowerCase() + ".yml", platformName);
            config.setProperty("PLATFORM_NAME", platformName);

            loadDeviceProperty(additionalConfig, config);
        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        }
        return config;
    }

    public Properties initializeCustomerSessions(Properties config, String environment) {
        try {
            Map<String, String> additionalConfig;

            String platformNameProperty = System.getProperty("platformName");
            String platformName = (platformNameProperty != null) ? platformNameProperty
                    : config.getProperty("PLATFORM_NAME");

            additionalConfig = parseYaml("src/main/resources/sessions/sessions-" + environment.toLowerCase() + ".yml", platformName);
            config.setProperty("PLATFORM_NAME", platformName);

            loadDeviceProperty(additionalConfig, config);
        } catch (Exception e) {
            e.printStackTrace();
            Log.warn(e.getMessage());
        }
        return config;
    }

    @SuppressWarnings("unchecked")
    public void updateYaml(String fileName, Properties config, Map<String, String> testData) {
        try {
            ObjectMapper objectMapper = new YAMLMapper();
            Map<String, Object> yaml;
            Map<String, String> latestYamlData;

            String platformProperty = System.getProperty("platformName");
            String platform = (platformProperty != null) ? platformProperty : config.getProperty("PLATFORM_NAME");

            String environmentProperty = System.getProperty("environment");
            String environment = (environmentProperty != null) ? environmentProperty : config.getProperty("ENV_NAME");

            File file = new File("" + fileName + "-sessions-" + environment.toLowerCase() + ".yml");
            yaml = objectMapper
                    .readValue(
                            file,
                            new TypeReference<Map<String, Object>>() {
                            });

            latestYamlData = (Map<String, String>) yaml.get(platform);
            for (Entry<String, String> iterator : testData.entrySet()) {
                latestYamlData.computeIfPresent(iterator.getKey(), (k, v) -> iterator.getValue());
            }
            yaml.replace(platform, latestYamlData);
            objectMapper.writeValue(file, yaml);
            Log.info("Yaml Updated Successfully");
        } catch (Exception e) {
            Log.info("Updating yaml failed due to " + e.getMessage());
        }
    }

}
