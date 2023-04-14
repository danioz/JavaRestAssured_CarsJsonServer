package utils.endpointHandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.NoSuchFileException;

import static utils.ConvertToDataModel.convertJsonAsStringToDataModel;
import static utils.ConvertToDataModel.convertJsonFileToDataModel;

@SuppressWarnings("ALL")
public class BaseEndpoint {

    //public String jsonFilePath;
    private Object[] dataModelAsArray;
    private String requestBody;

    //Get JSON as string from Data Model
    public String getRequestBody() {
        this.requestBody = convertDataModelToJson(false);
        return requestBody;
    }

    //Get JSON as string from Data Model as array
    public String getRequestBody(boolean isArray) {
        this.requestBody = convertDataModelToJson(isArray);
        return requestBody;
    }

    //Converts JSON file with array to data model as array and then to string.
    protected <T> void initRequestBody(Class<T[]> valueType, String jsonPath, boolean isArray) {
        loadFileAsInputStream(valueType, jsonPath);
        this.requestBody = convertDataModelToJson(isArray);
    }

    @SuppressWarnings("SameParameterValue")
    protected <T> void initRequestBody(Class<T[]> valueType, String jsonPath) {
        loadFileAsInputStream(valueType, jsonPath);
        this.requestBody = convertDataModelToJson(false);
    }

    @SneakyThrows
    private <T> void loadFileAsInputStream(Class<T[]> valueType, String jsonPath) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jsonPath);
        if (inputStream == null) {
            String[] pathAsArray = jsonPath.split("/");
            String fileName = pathAsArray[pathAsArray.length - 1];
            inputStream = this.getClass().getResourceAsStream(fileName);
            if (inputStream == null) {
                throw new NoSuchFileException("File not found.");
            }
        }
        this.dataModelAsArray = valueType.cast(convertJsonFileToDataModel(inputStream, valueType));
    }

    //Converts JSON to data model as array
    @SneakyThrows
    protected <T> void convertJsonToDataModelArray(String response, Class<T[]> valueType) {
        this.dataModelAsArray = convertJsonAsStringToDataModel(response, valueType);
    }

    //Gets data model as an array
    @SneakyThrows
    protected <T> T getDataModelAsArray(Class<T> type) {
        if (dataModelAsArray.length != 0) {
            return type.cast(dataModelAsArray);
        } else {
            throw new Exception("Data model is empty! " + type.getSimpleName());
        }
    }

    protected void setDataModelAsArray(Object[] objectsList) {
        this.dataModelAsArray = objectsList;
    }

    protected Object getNodeObjectByName(String nodeName, Object objectType) {
        return getObjectByKeyName(nodeName, objectType);
    }

    protected String getValueFromField(String fieldName, Object objectType) {
        Object object = getObjectByKeyName(fieldName, objectType);
        return object != null ? object.toString() : null;
    }

    private Object getObjectByKeyName(String keyName, Object objectType) {
        try {
            Field field = objectType.getClass().getDeclaredField(keyName);
            field.setAccessible(true);
            return field.get(objectType);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void setValueOfField(String fieldName, Object value, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private String convertDataModelToJson(boolean isArray) {
        if (isArray) {
            return getJson(dataModelAsArray);
        } else {
            return getJson(dataModelAsArray[0]);
        }
    }

    private String getJson(Object requestBodyDataModel) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return objectMapper.writeValueAsString(requestBodyDataModel);
    }
}
