package apiCarsProject.api.cars.dataModel;

import lombok.Data;

import java.util.List;


@Data
public class CarsDataModel {
    private Integer id;
    private String brand;
    private String model;
    private Integer generation;
    private ColorDetails colorDetails;
    private Engine engine;
    private Interior interior;
    private List<ProductionHistory> productionHistory;
}
