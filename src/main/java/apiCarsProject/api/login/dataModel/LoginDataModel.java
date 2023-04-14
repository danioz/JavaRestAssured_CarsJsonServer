package apiCarsProject.api.login.dataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDataModel {
    private String email;
    private String password;
    private String accessToken;
    private User user;

    @Data
    public static class User {
        private Integer id;
        private String email;
    }
}
