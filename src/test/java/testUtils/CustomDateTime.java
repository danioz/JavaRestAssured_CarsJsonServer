package testUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;

@SuppressWarnings("unused")
public class CustomDateTime {

    public static String getOffsetFormattedTime(String date) {
        String currentFormat = "yyyy-MM-dd'T'HH:mm:ss";
        DateFormat simpleDateFormat = new SimpleDateFormat(currentFormat);
        try {
            return simpleDateFormat.parse(OffsetDateTime.parse(date).toInstant().toString()).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
