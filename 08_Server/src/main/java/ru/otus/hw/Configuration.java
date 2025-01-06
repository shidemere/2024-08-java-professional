package ru.otus.hw;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Считывание конфигураций сервера из файла.
 */
public class Configuration {
    private static final String CONFIG_PATH = "application.properties";

    private Properties properties;

    public Configuration() {
        try {
            getProperties();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getProperties() throws IOException {
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream stream = loader.getResourceAsStream(CONFIG_PATH)) {
            properties.load(stream);
        }
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }
}
