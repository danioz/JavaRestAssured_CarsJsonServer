package apiCarsProject.api.cars.dataModel;

import lombok.Data;
@SuppressWarnings("unused")
@Data
public class Interior {
    private Seats frontSeats;
    private Seats backSeats;
    private SecurityPackage securityPackage;
    private Extras extras;
}
