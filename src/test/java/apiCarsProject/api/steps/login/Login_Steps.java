package apiCarsProject.api.steps.login;

import apiCarsProject.api.login.Login_Endpoint;
import apiCarsProject.api.login.dataModel.LoginDataModel;
import apiCarsProject.api.steps.Base_Steps;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import testUtils.RestAssuredContext;
import utils.TestContext;
import utils.enums.CarsProject;

import java.util.Map;

public class Login_Steps extends Base_Steps<Login_Endpoint> {

    public Login_Steps(TestContext testContext, RestAssuredContext restAssuredContext, Login_Endpoint endpoint) {
        super(testContext, restAssuredContext);
        super.setEndpoint(endpoint);
    }

    @DataTableType
    public LoginDataModel createLoginObject(Map<String, String> entry) {
        LoginDataModel loginData = new LoginDataModel();
        loginData.setEmail(entry.get("email"));
        loginData.setPassword(entry.get("password"));
        return loginData;
    }

    //region When
    @When("Login request is updated with")
    public void loginRequestIsUpdatedWith(LoginDataModel loginData) {
        endpoint.setLoginData(loginData);
    }

    @When("User sends a valid POST request to login")
    public void userSendAValidPOSTRequestToGetAccessToken() {
        sendPostRequest(endpoint.path, endpoint.getRequestBody(), true);
        restAssuredContext.getRequestSpecification().auth().preemptive().oauth2(endpoint.getAccessToken());
    }

    @When("User sends a valid POST request to Login as existing user")
    @When("User sends a not valid POST request to Login")
    public void userSendsAValidPOSTRequestToLoginExistingUser() {
        sendPostRequest(endpoint.path, endpoint.getRequestBody(), true);
        testContext.setCommonContextMapOfObjects(CarsProject.ACCESS_TOKEN, endpoint.getAccessToken());
    }
    //endregion

    //region Then
    @Then("Logged user should have Access Token")
    public void userShouldHaveAccessToken() {
        //Act
        String actualAccessToken = (String) testContext.getCommonContextMapOfObjects(CarsProject.ACCESS_TOKEN);

        //Assert
        Assertions.assertThat(actualAccessToken).isNotEmpty();
    }
    //endregion
}
