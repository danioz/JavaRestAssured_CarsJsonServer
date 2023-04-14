package testUtils;

import java.util.UUID;

@SuppressWarnings("unused")
public class RandomStringGenerator {

    public static String generateString(String inputString, String key){
        return inputString.equals(key) ? UUID.randomUUID().toString() : inputString;
    }

    public static String generateLimitedString(String inputString, String key, int limit){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return inputString.equals(key) ? uuid.substring(0, limit) : inputString;
    }
}

