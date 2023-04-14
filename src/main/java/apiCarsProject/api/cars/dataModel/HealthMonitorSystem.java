package apiCarsProject.api.cars.dataModel;

import lombok.Data;
@SuppressWarnings("unused")
@Data
public class HealthMonitorSystem {
    private BackupAirSystem backupAirSystem;
    private FirstAid firstAid;

    @Data
    public static class BackupAirSystem {
        private Integer airCapacity;
        private String airPowerAuxiliary;
    }

    @Data
    public static class FirstAid {
        private boolean severedLimbsAid;
        private GunShotWoundsAid gunShotWoundsAid;

    }
    @Data
    public static class GunShotWoundsAid {
        private boolean cal5_6mmAid;
        private boolean cal7_62mmAid;
        private boolean cal9mmAid;
        private boolean cal9_5mmAid;
        private boolean cal13mmAid;
    }
}
