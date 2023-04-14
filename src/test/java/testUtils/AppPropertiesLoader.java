package testUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppPropertiesLoader {

    private final Properties appProperties;

    public AppPropertiesLoader() throws IOException {
        String appConfigPath = System.getProperty("user.dir") + "/app.properties";
        Properties appProperties = new Properties();
        appProperties.load(new FileInputStream(appConfigPath));
        this.appProperties = appProperties;
    }

    public int getAppProperty(String propertyName) {
        return Integer.parseInt(appProperties.getProperty(propertyName));
    }
}
