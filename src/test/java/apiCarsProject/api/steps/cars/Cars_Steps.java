package apiCarsProject.api.steps.cars;

import apiCarsProject.api.cars.Cars_Endpoint;
import apiCarsProject.api.cars.dataModel.*;
import apiCarsProject.api.steps.Base_Steps;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import testUtils.RestAssuredContext;
import utils.TestContext;
import utils.enums.CarsProject;

import java.util.List;
import java.util.Map;

import static testUtils.EndpointsPathHandler.getPathWithNameParameter;
import static testUtils.ParseFactory.customTryParse;

public class Cars_Steps extends Base_Steps<Cars_Endpoint> {


    public Cars_Steps(TestContext testContext, RestAssuredContext restAssuredContext, Cars_Endpoint endpoint) {
        super(testContext, restAssuredContext);
        super.setEndpoint(endpoint);
    }

    @DataTableType
    public CarsDataModel createCarsObject(Map<String, String> entry) {
        CarsDataModel carData = new CarsDataModel();
        carData.setBrand(entry.get("brand"));
        carData.setModel(entry.get("model"));
        if (entry.get("generation") != null) {
            carData.setGeneration(customTryParse(entry.get("generation")));
        }
        if (entry.get("color") != null && entry.get("code") != null) {
            carData.setColorDetails(new ColorDetails() {{
                setColor(entry.get("color"));
                setCode(entry.get("code"));
            }});
        }
        if (entry.get("displacement") != null && entry.get("cylinderSystem") != null && entry.get("power") != null) {
            carData.setEngine(new Engine() {{
                setDisplacement(customTryParse(entry.get("displacement")));
                setCylinderSystem(entry.get("cylinderSystem"));
                setPower(entry.get("power"));
            }});
        }
        if (entry.get("frontSeats heated") != null && entry.get("frontSeats ventilated") != null && entry.get("frontSeats massage") != null) {
            carData.setInterior(new Interior() {{
                setFrontSeats(new Seats() {{
                    setHeated(customTryParse(entry.get("heated")));
                    setVentilated(customTryParse(entry.get("ventilated")));
                    setMassage(customTryParse(entry.get("massage")));
                }});
            }});
        }
        if (entry.get("backSeats heated") != null && entry.get("backSeats ventilated") != null && entry.get("backSeats massage") != null) {
            carData.setInterior(new Interior() {{
                setBackSeats(new Seats() {{
                    setHeated(customTryParse(entry.get("heated")));
                    setVentilated(customTryParse(entry.get("ventilated")));
                    setMassage(customTryParse(entry.get("massage")));
                }});
            }});
        }
        return carData;
    }

    //region When
    @When("Car request is updated with")
    public void updateCarRequest(CarsDataModel carData) {
        endpoint.setCarData(carData);
    }

    @When("User sends a valid POST request to Cars to create a new car")
    public void createNewCar() {
        sendPostRequest(getPathWithNameParameter(endpoint.path, ""), endpoint.getRequestBody(), true);
        testContext.setCommonContextMapOfObjects(CarsProject.CAR_ID, endpoint.getCarId());
    }

    @When("User sends a valid GET request to Cars to get all cars")
    public void userSendsAValidGETRequestToCarsLookingToFindAll() {
        sendGetRequest(getPathWithNameParameter(endpoint.path,""), true);
    }

    @When("User sends a valid GET request to Cars looking for car by Id")
    public void getCarById() {
        String carId = ((Integer) testContext.getCommonContextMapOfObjects(CarsProject.CAR_ID)).toString();
        sendGetRequest(getPathWithNameParameter(endpoint.path, carId), true);
    }

    @When("User sends a valid PUT request to Cars looking for created car by Id to update")
    public void updateCarById() {
        String carId = ((Integer) testContext.getCommonContextMapOfObjects(CarsProject.CAR_ID)).toString();
        sendPutRequest(getPathWithNameParameter(endpoint.path, carId), endpoint.getRequestBody(), true);
    }
    @When("User sends a valid DELETE request to Cars looking for created car by Id to delete")
    public void deleteCarById() {
        String carId = ((Integer) testContext.getCommonContextMapOfObjects(CarsProject.CAR_ID)).toString();
        sendDeleteRequest(getPathWithNameParameter(endpoint.path, carId));
    }
    //endregion

    //region Then
    @Then("Car should have data")
    public void assertCreatedCar(CarsDataModel expectedCarsData) {
        //Act
        CarsDataModel actualCarData = endpoint.getCarData();

        //Assert
        RecursiveComparisonConfiguration comparisonConfiguration = new RecursiveComparisonConfiguration();
        comparisonConfiguration.setIgnoreAllExpectedNullFields(true);
        Assertions.assertThat(actualCarData).usingRecursiveComparison(comparisonConfiguration).isEqualTo(expectedCarsData);
    }

    @Then("Deleted car should have no body - {}")
    public void deletedCarShouldHaveNoData(String expectedResponseBody) {
        //Act
        String actualResponseBody = restAssuredContext.getResponse().asString();

        //Assert
        Assertions.assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    @Then("Cars response should have proper cars details")
    public void carsResponseShouldHaveProperCarDetailsMany(List<CarsDataModel> expectedCarsData) {
//Assertj can handle two collection there should not be for loop inside Then step
        for (CarsDataModel expectedCarDatum : expectedCarsData) {
            //Act
            CarsDataModel actualCarData = endpoint.getCarByBrandAndModel(expectedCarDatum.getBrand(), expectedCarDatum.getModel());

            //Assert
            RecursiveComparisonConfiguration comparisonConfiguration = new RecursiveComparisonConfiguration();
            comparisonConfiguration.setIgnoreAllExpectedNullFields(true);
            Assertions.assertThat(actualCarData).usingRecursiveComparison(comparisonConfiguration).isEqualTo(expectedCarDatum);
        }
    }

    @Then("Cars response should contains entered data")
    public void carsResponseShould(List<CarsDataModel> expectedCarsData) {
        //Act
        CarsDataModel[] actualCarsData = endpoint.getCarsData();

        //Assert
        Assertions.assertThat(actualCarsData).usingRecursiveFieldByFieldElementComparatorOnFields("brand", "model")
                .containsAll(expectedCarsData);
    }
    //endregion
}
