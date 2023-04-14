package testUtils;

import io.restassured.response.Response;
import io.restassured.specification.PreemptiveAuthSpec;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestAssuredContext {
    private Response response;
    private RequestSpecification requestSpecification;
    private PreemptiveAuthSpec preemptiveAuthSpec;
}
