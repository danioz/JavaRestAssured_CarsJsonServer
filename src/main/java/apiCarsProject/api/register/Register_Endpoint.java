package apiCarsProject.api.register;

import apiCarsProject.api.register.dataModel.RegisterDataModel;
import utils.endpointHandlers.BaseEndpoint;
import utils.endpointHandlers.IEndpoint;

public class Register_Endpoint extends BaseEndpoint implements IEndpoint {

    public final String path = "/register";
    private final Class<RegisterDataModel[]> classType = RegisterDataModel[].class;

    public Register_Endpoint() {
        super.initRequestBody(classType, "apiCarsProject/api/register/RegisterRequestBody.json");
    }

    @Override
    public void convertResponseToDataModel(String response) {
        super.convertJsonToDataModelArray(response, classType);
    }

    public void setRegisterData(RegisterDataModel registerData) {
        RegisterDataModel register = super.getDataModelAsArray(classType)[0];
        register.setEmail(registerData.getEmail());
        register.setPassword(registerData.getPassword());
    }

    public void setRegisterDataFromString(String email, String password) {
        RegisterDataModel register = super.getDataModelAsArray(classType)[0];
        register.setEmail(email);
        register.setPassword(password);
    }

    public String getAccessToken() {
        return super.getDataModelAsArray(classType)[0].getAccessToken();
    }
}
