package apiCarsProject.api.runner;

import io.cucumber.testng.CucumberOptions;
import testUtils.CommonRunnerConfig;

@CucumberOptions(
        features = {"src/test/resources/apiCarsProject/api/features"},
        glue = {"apiCarsProject/api/steps"})
public class CarsProjectTestRunner extends CommonRunnerConfig {
}
