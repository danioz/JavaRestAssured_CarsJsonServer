package apiCarsProject.api.steps;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import testUtils.RestAssuredContext;
import utils.TestContext;
import utils.endpointHandlers.IEndpoint;

import java.util.ArrayList;

@SuppressWarnings({"SameParameterValue", "unused"})
public class Base_Steps<T> {

    protected final TestContext testContext;
    protected final RestAssuredContext restAssuredContext;
    protected T endpoint;
    private IEndpoint iEndpoint;

    public void setEndpoint(T endpoint) {
        this.endpoint = endpoint;
        iEndpoint = (IEndpoint) endpoint;
    }

    public Base_Steps(TestContext testContext, RestAssuredContext restAssuredContext) {
        this.testContext = testContext;
        this.restAssuredContext = restAssuredContext;
    }

    //region GET
    protected void sendGetRequest(String path, boolean isResponseConvertible) {
        getRequest(path, isResponseConvertible);
    }

    protected void sendGetRequest(String path) {
        getRequest(path, false);
    }

    private void getRequest(String path, boolean isResponseConvertible) {
        Response response = restAssuredContext.getRequestSpecification()
                .when()
                .body("")
                .get(path)
                .then().extract().response();
        restAssuredContext.setResponse(response);
        if (response.getStatusCode() == 200 && isResponseConvertible) {
            iEndpoint.convertResponseToDataModel(response.asString());
        }
    }
    //endregion

    //region POST
    protected void sendPostRequest(String path, String requestBody, boolean isResponseConvertible) {
        postRequest(path, requestBody, isResponseConvertible);
    }

    protected void sendPostRequest(String path, String requestBody) {
        postRequest(path, requestBody, false);
    }

    private void postRequest(String path, String requestBody, boolean isResponseConvertible) {
        Response response = restAssuredContext.getRequestSpecification()
                .when()
                .body(requestBody).log().body()
                .post(path)
                .then().extract().response();
        restAssuredContext.setResponse(response);
        if ((response.getStatusCode() == 200 || response.getStatusCode() == 201) && isResponseConvertible) {
            iEndpoint.convertResponseToDataModel(response.asString());
        }
    }
    //endregion

    //region PUT
    protected void sendPutRequest(String path, String requestBody, boolean isResponseConvertible) {
        putRequest(path, requestBody, isResponseConvertible);
    }

    protected void sendPutRequest(String path, String requestBody) {
        putRequest(path, requestBody, false);
    }

    private void putRequest(String path, String requestBody, boolean isResponseConvertible) {
        Response response = restAssuredContext.getRequestSpecification()
                .when()
                .body(requestBody).log().body()
                .put(path)
                .then().extract().response();
        restAssuredContext.setResponse(response);
        if (response.getStatusCode() == 200 && isResponseConvertible) {
            iEndpoint.convertResponseToDataModel(response.asString());
        }
    }
    //endregion

    //region DELETE
    protected void sendDeleteRequest(String path,  boolean isResponseConvertible) {
        deleteRequest(path, isResponseConvertible);
    }

    protected void sendDeleteRequest(String path) {
        deleteRequest(path, false);
    }

    public void deleteRequest(String path, boolean isResponseConvertible) {
        Response response = restAssuredContext.getRequestSpecification()
                .when()
                .body("")
                .delete(path)
                .then().extract().response();
        restAssuredContext.setResponse(response);
        if (response.getStatusCode() == 200 && isResponseConvertible) {
            iEndpoint.convertResponseToDataModel(response.asString());
        }
    }
    //endregion

    @SuppressWarnings("SameParameterValue")
    //region GET with wait
    protected boolean sendGetRequestAndWaitForContent(String path, int maxWait, boolean isResponseConvertible) {
        return getRequestWithWait(path, maxWait, isResponseConvertible);
    }

    protected boolean sendGetRequestAndWaitForContent(String path, int maxWait)  {
        return getRequestWithWait(path, maxWait, false);
    }

    @SneakyThrows
    private boolean getRequestWithWait(String path, int maxWait, boolean isResponseConvertible) {
        for (int i = 1; i <= maxWait; i++) {
            Thread.sleep(500);
            Response response = restAssuredContext.getRequestSpecification()
                    .when()
                    .get(path)
                    .then().extract().response();
            if (!response.asString().equals("[]")) {
                restAssuredContext.setResponse(response);
                if (response.getStatusCode() == 200 && isResponseConvertible) {
                    iEndpoint.convertResponseToDataModel(response.asString());
                }
                return true;
            }
        }
        return false;
    }
    //endregion

    protected void attachHeader(String key, String value) {
        RequestSpecification requestSpecification = restAssuredContext.getRequestSpecification();
        restAssuredContext.setRequestSpecification(requestSpecification.header(key, value));
    }

    protected void detachHeader(String key) {
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) restAssuredContext.getRequestSpecification();
        requestSpecification.removeHeader(key);
    }

    protected void removeCookie(String cookie) {
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) restAssuredContext.getRequestSpecification();
        requestSpecification.removeCookie(cookie);
    }

    protected void clearReqQueryParams() {
        FilterableRequestSpecification requestSpecification = (FilterableRequestSpecification) restAssuredContext.getRequestSpecification();
        new ArrayList<>(requestSpecification.getQueryParams().keySet()).forEach(requestSpecification::removeQueryParam);
    }
}
