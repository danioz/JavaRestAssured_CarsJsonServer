package apiCarsProject.api.cars;

import apiCarsProject.api.cars.dataModel.CarsDataModel;
import utils.endpointHandlers.BaseEndpoint;
import utils.endpointHandlers.IEndpoint;

import java.util.Arrays;

public class Cars_Endpoint extends BaseEndpoint implements IEndpoint {

    public final String path = "/cars/{id}";
    private final Class<CarsDataModel[]> classType = CarsDataModel[].class;

    public Cars_Endpoint() {
        super.initRequestBody(classType, "apiCarsProject/api/cars/CarRequestBody.json");
    }

    @Override
    public void convertResponseToDataModel(String response) {
            super.convertJsonToDataModelArray(response, classType);
    }

    public void setCarData(CarsDataModel carData) {
        CarsDataModel car = super.getDataModelAsArray(classType)[0];
        car.setBrand(carData.getBrand());
        car.setModel(carData.getModel());
        car.setGeneration(carData.getGeneration());
        if (carData.getEngine() != null) {
            car.setEngine(carData.getEngine());
        }
        if (carData.getColorDetails() != null) {
            car.setColorDetails(carData.getColorDetails());
        }
        if (carData.getInterior() != null) {
            car.setInterior(carData.getInterior());
        }
        if (carData.getProductionHistory() != null) {
            car.setProductionHistory(carData.getProductionHistory());
        }
    }

    public CarsDataModel getCarData() {
        return super.getDataModelAsArray(classType)[0];
    }

    public CarsDataModel[] getCarsData() {
        return super.getDataModelAsArray(classType);
    }

    public Integer getCarId() {
        return super.getDataModelAsArray(classType)[0].getId();
    }

    public CarsDataModel getCarByBrandAndModel(String brand, String model) {
        return Arrays.stream(super.getDataModelAsArray(classType))
                .filter(elem -> elem.getBrand().equals(brand))
                .filter(elem -> elem.getModel().equals(model)).findFirst().orElse(null);
    }

}
