package utils;


@SuppressWarnings("unused")
public class UrlHandler {

    public static String getUrlAlias(String urlFull) {
        return urlFull.replace(System.getProperty("url"), "");
    }
}
