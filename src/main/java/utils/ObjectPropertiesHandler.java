package utils;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class ObjectPropertiesHandler {

    public static void setValueOfField(String fieldName, Object value, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
