package utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@SuppressWarnings("unused")
public class TestContext {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<Object, Object> commonContextMapOfObjects = new HashMap<>();

    public void setCommonContextMapOfObjects(Object key, Object value) {
        commonContextMapOfObjects.put(key, value);
    }

    public Object getCommonContextMapOfObjects(Object key) {
        return commonContextMapOfObjects.get(key);
    }
}
