package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class ConvertToDataModel {

    public static <T> T convertJsonFileToDataModel(InputStream filePath, Class<T> valueType) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return objectMapper.readValue(filePath, valueType);
    }

    public static <T> T convertJsonAsStringToDataModel(String jsonAsString, Class<T> valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        if (isJson(jsonAsString)) {
            return objectMapper.readValue(jsonAsString, valueType);
        }
        return null;
    }

    private static boolean isJson(final String json) {
        boolean valid = true;
        try {
            new ObjectMapper().readTree(json);
        } catch (JsonProcessingException e) {
            valid = false;
        }
        return valid;
    }
}
