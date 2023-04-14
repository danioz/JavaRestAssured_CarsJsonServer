package apiCarsProject.api.login;

import apiCarsProject.api.login.dataModel.LoginDataModel;
import utils.endpointHandlers.BaseEndpoint;
import utils.endpointHandlers.IEndpoint;

public class Login_Endpoint extends BaseEndpoint implements IEndpoint {

    public final String path = "/login";
    private final Class<LoginDataModel[]> classType = LoginDataModel[].class;

    public Login_Endpoint() {
        super.initRequestBody(classType, "apiCarsProject/api/login/LoginRequestBody.json");
    }

    @Override
    public void convertResponseToDataModel(String response) {
        super.convertJsonToDataModelArray(response, classType);
    }

    public void setLoginData(LoginDataModel loginData) {
        LoginDataModel login = super.getDataModelAsArray(classType)[0];
        login.setEmail(loginData.getEmail());
        login.setPassword(loginData.getPassword());
    }

    public String getAccessToken() {
        return super.getDataModelAsArray(classType)[0].getAccessToken();
    }
}
