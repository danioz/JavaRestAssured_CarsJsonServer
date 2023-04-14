package testUtils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import testUtils.AppPropertiesLoader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.ParamConfig.paramConfig;

public class Config {

    public final String fileName = "requestLogger.txt";

    @Before
    public void rerouteRestAssuredLoggingToFile() throws IOException {
        AppPropertiesLoader appPropertiesLoader = new AppPropertiesLoader();

        PrintStream printStream;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            printStream = new PrintStream(fileOutputStream);
            RestAssured.config = RestAssuredConfig.config()
                    .logConfig(logConfig().defaultStream(printStream))
                    .and()
                    .paramConfig(paramConfig().replaceAllParameters())
                    .httpClient(HttpClientConfig.httpClientConfig()
                            .setParam("http.connection.timeout", appPropertiesLoader.getAppProperty("http.connection.timeout"))
                            .setParam("http.socket.timeout", appPropertiesLoader.getAppProperty("http.socket.timeout"))
                            .setParam("http.connection-manager.timeout", appPropertiesLoader.getAppProperty("http.connection-manager.timeout")))
                    .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        } catch (IOException e) {
            System.err.println("Unable to write to a file.");
            throw e;
        }
    }

    @After
    public void logger() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        if (fileInputStream.available() != 0) {
            Allure.addAttachment("Request Log", "text/html", fileInputStream, ".txt");
        } else {
            Allure.step("Nothing to show.");
        }
    }
}
