package apiCarsProject.api.steps.register;

import apiCarsProject.api.register.Register_Endpoint;
import apiCarsProject.api.register.dataModel.RegisterDataModel;
import apiCarsProject.api.steps.Base_Steps;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import testUtils.RestAssuredContext;
import utils.TestContext;
import utils.enums.CarsProject;

import java.util.Map;

import static utils.Extensions.generateLimitedString;
import static utils.Extensions.generateUUID;


public class Register_Steps extends Base_Steps<Register_Endpoint> {
    public Register_Steps(TestContext testContext, RestAssuredContext restAssuredContext, Register_Endpoint endpoint) {
        super(testContext, restAssuredContext);
        super.setEndpoint(endpoint);
    }

    @DataTableType
    public RegisterDataModel createRegisterObject(Map<String, String> entry) {
        RegisterDataModel registerData = new RegisterDataModel();
        registerData.setEmail(entry.get("email"));
        registerData.setPassword(entry.get("password"));
        return registerData;
    }

    //region When
    @When("Register request is updated with")
    public void registerRequestIsUpdatedWith(RegisterDataModel registerData) {
        endpoint.setRegisterData(registerData);
    }

    @When("Register request is updated with {} and {}")
    public void registerRequestIsUpdatedWithEmailAndPassword(String email, String password) {
        endpoint.setRegisterDataFromString(email, password);
    }

    @When("Register request is updated with random data")
    public void registerRequestIsUpdatedWithRandomData() {
        String email = generateLimitedString(10) + "@mail.com";
        String password = generateUUID();
        endpoint.setRegisterDataFromString(email, password);
    }

    @When("User sends a valid POST request to Register")
    public void userSendsAValidPOSTRequestToRegister() {
        sendPostRequest(endpoint.path, endpoint.getRequestBody(), true);
        testContext.setCommonContextMapOfObjects(CarsProject.ACCESS_TOKEN, endpoint.getAccessToken());
    }
    //endregion

    //region Then
    @Then("Created user should have Access Token")
    public void userShouldHaveAccessToken() {
        //Act
        String actualAccessToken = (String) testContext.getCommonContextMapOfObjects(CarsProject.ACCESS_TOKEN);

        //Assert
        Assertions.assertThat(actualAccessToken).isNotEmpty();
    }
    //endregion
}
