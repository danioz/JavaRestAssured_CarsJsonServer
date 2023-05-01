package utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class JacksonDataTableTransformer {

    private final ObjectMapper objectMapper;

    public JacksonDataTableTransformer() {
        objectMapper = new ObjectMapper();
    }

    @DefaultDataTableEntryTransformer
    public Object transform(Map<String, String> map, Type type, TableCellByTypeTransformer tableCellByTypeTransformer) throws Throwable {
        Class<?> clazz = Class.forName(type.getTypeName());

        final Map<String, Map<String, String>> nestedEntries =
                map.entrySet().stream()
                        .filter(e -> e.getKey().contains("."))
                        .collect(groupingBy(e -> e.getKey().substring(0, e.getKey().indexOf('.')),
                                HashMap::new,
                                toMap(e -> e.getKey().substring(e.getKey().indexOf('.') + 1), Map.Entry::getValue)));

        final Map<String, Object> nestedValues = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> mapEntry : nestedEntries.entrySet()) {
            String k = mapEntry.getKey();
            Map<String, String> v = mapEntry.getValue();
            try {
                nestedValues.put(k, transform(v, Class.forName(clazz.getDeclaredField(k).getType().getTypeName()), tableCellByTypeTransformer));
            } catch (NoSuchFieldException e) {
                throw e;
            }
        }

        nestedValues.putAll(
                map.entrySet().stream()
                        .filter(e -> !e.getKey().contains("."))
                        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));

        return objectMapper.convertValue(nestedValues, clazz);
    }

    @DefaultDataTableCellTransformer
    public Object transform(String s, Type type) throws Throwable {
        JavaType javaType = objectMapper.constructType(type);
        return objectMapper.readValue(s, javaType);
    }
}
