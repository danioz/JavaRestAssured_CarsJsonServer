package testUtils;

import java.util.HashMap;

public class Users {

    public static final HashMap<String, UserModel> userRoles = new HashMap<String, UserModel>() {{
        //API
        put("Admin", new UserModel("admin", "password123"));
    }};
}
