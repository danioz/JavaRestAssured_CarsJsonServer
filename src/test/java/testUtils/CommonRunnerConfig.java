package testUtils;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        glue = {"utils",
                "common", "shared"},
        plugin = {"pretty", "summary", "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"})
public class CommonRunnerConfig extends AbstractTestNGCucumberTests {

    @BeforeTest
    @Parameters({"url"})
    public void init(String url) {
        System.setProperty("url", url);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
