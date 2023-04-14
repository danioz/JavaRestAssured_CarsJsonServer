package testUtils;

@SuppressWarnings("unused")
public class EndpointsPathHandler {
    public static String getSubPathWithId(String path, String id) {
        StringBuilder sb = new StringBuilder(path);
        if (!path.endsWith("/")) {
            sb.append("/");
        }
        sb.append(id);
        return sb.toString();
    }

    public static String getPathWithNameParameter(String path, String id) {
        if (id.equals("")) {
            return path.replace("/{id}", "");
        } else {
            return path.replace("{id}", id);
        }
    }

    public static String getPathWithNameParameter(String path, String parameter, String keyToReplace) {
        if (parameter.equals("")) {
            return path.replace("/{" + keyToReplace + "}", "");
        } else {
            return path.replace("{" + keyToReplace + "}", parameter);
        }
    }
}
