package testUtils;

@SuppressWarnings({"WrapperTypeMayBePrimitive", "unused"})
public class ParseFactory {

    @SuppressWarnings("unchecked")
    public static <T> T customTryParse(String stringToParse) {
        if (stringToParse.equals("true") || stringToParse.equals("false")) {
            Boolean bool = Boolean.parseBoolean(stringToParse);
            return (T) bool;
        } else {
            try {
                Integer number = Integer.parseInt(stringToParse);
                return (T) number;
            } catch (NumberFormatException e) {
                //Empty catch
            }
        }
        return (T) stringToParse;
    }
}
