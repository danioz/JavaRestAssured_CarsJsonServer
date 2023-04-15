package apiCarsProject.api.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.PreemptiveAuthSpec;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import testUtils.RestAssuredContext;
import utils.TestContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static testUtils.ParseFactory.customTryParse;
import static testUtils.Users.userRoles;

@SuppressWarnings("rawtypes")
public class Common_Steps extends Base_Steps {

    public Common_Steps(TestContext testContext, RestAssuredContext restAssuredContext) {
        super(testContext, restAssuredContext);
    }

    // region Given
    @Given("Setup base request specification with user credentials for {}")
    public void setupPreemptiveAuthSpec(String userRole) {
        RequestSpecification requestSpecification = given()
                .filters(new AllureRestAssured())
                .relaxedHTTPSValidation()
                .baseUri(System.getProperty("url"))
                .auth()
                .preemptive()
                .basic(userRoles.get(userRole).getUserName(), userRoles.get(userRole).getUserPassword())
                .contentType(ContentType.JSON)
                .header("User-Agent", "RestAssured")
                .header("X-Requested-With", "XMLHttpRequest");
        restAssuredContext.setRequestSpecification(requestSpecification);
    }

    @Given("Setup base request specification without auth")
    public void setupPreemptiveAuthSpecWithoutAuth() {
        RequestSpecification requestSpecification = given()
                .filters(new AllureRestAssured())
                .relaxedHTTPSValidation()
                .baseUri(System.getProperty("url"))
                .contentType(ContentType.JSON)
                .header("User-Agent", "RestAssured")
                .header("X-Requested-With", "XMLHttpRequest");
        restAssuredContext.setRequestSpecification(requestSpecification);
    }

    @Given("Setup base request specification")
    public void setupPreemptiveAuthSpec() {
        PreemptiveAuthSpec preemptiveAuthSpec = given()
                .relaxedHTTPSValidation()
                .baseUri(System.getProperty("url"))
                .auth()
                .preemptive();
        restAssuredContext.setPreemptiveAuthSpec(preemptiveAuthSpec);
    }
    //endregion

    //region When
    @When("Set user credentials for {}")
    public void setUserCredentials(String userRole) {
        String userName = userRoles.get(userRole).getUserName();
        String userPassword = userRoles.get(userRole).getUserPassword();
        Allure.step(userRole + " with name: " + userName + "and password: " + userPassword);
        RequestSpecification requestSpecification = restAssuredContext.getPreemptiveAuthSpec()
                .basic(userName, userPassword)
                .contentType(ContentType.JSON)
                .header("User-Agent", "RestAssured")
                .header("X-Requested-With", "XMLHttpRequest");
        restAssuredContext.setRequestSpecification(requestSpecification);
    }

    @When("User defines request with parameter {string} and value {string}")
    public void defineParams(String key, String value) {
        restAssuredContext.getRequestSpecification().param(key, value);
    }

    @When("User defines request with query parameter {string} and value {string}")
    public void defineQueryParam(String key, String value) {
        restAssuredContext.getRequestSpecification().queryParam(key, value);
    }

    @When("User defines request with query parameter {} with a list of values")
    public void defineQueryParams(String key, List<String> value) {
        restAssuredContext.getRequestSpecification().queryParam(key, value);
    }
    //endregion

    //region Then
    @Then("Response status should be {}")
    public void assertResponseStatusCode(String code) {
        //Arrange
        int expectedStatusCode = customTryParse(code);

        //Act
        int actualStatusCode = restAssuredContext.getResponse().getStatusCode();
        String responseBody = "Response body: " + restAssuredContext.getResponse().asString();

        //Assert
        if (actualStatusCode != 200) {
            if (responseBody.length() < 1000) {
                System.out.println(responseBody);
            }
            System.out.println("Response status line: " + restAssuredContext.getResponse().statusLine());
        }
        Assertions.assertThat(actualStatusCode).isEqualTo(expectedStatusCode);
    }

    @Then("Body response should contain text {}")
    public void bodyResponseShouldContainText(String expectedResponseBody) {
        //Act
        String actualResponseBody = restAssuredContext.getResponse().asString();

        //Assert
        Assertions.assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }
}
