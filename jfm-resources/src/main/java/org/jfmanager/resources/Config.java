package org.jfmanager.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * User: kvych
 * Date: 1/7/14
 * Time: 2:07 PM
 */
public class Config {

    private static final Logger log = LoggerFactory.getLogger(Config.class);

    private static Config INSTANCE = new Config();

    private Properties defaults = new Properties();

    private Properties properties = new Properties(defaults);

    private Config() {
    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public void init() throws IOException {
        log.info("Loading configuration...");
        defaults.load(Config.class.getResourceAsStream("/config/default.properties"));
        properties.load(new FileInputStream(getConfigFile()));
    }

    public void save() {
        try {
            log.info("Storing configuration...");
            properties.store(new FileOutputStream(getConfigFile()), "Configuration for JFManager");
        } catch (IOException e) {
            log.error("Exception while storing configuration!", e);
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public double getDouble(String key) {
        return Double.parseDouble(properties.getProperty(key));
    }

    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public void set(String key, int value) {
        properties.setProperty(key, Integer.toString(value));
    }

    public void set(String key, double value) {
        properties.setProperty(key, Double.toString(value));
    }

    private File getConfigFile() throws IOException {
        File file = new File("config.properties");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
