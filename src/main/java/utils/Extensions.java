package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public class Extensions {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateUnderscoredUUID() {
        return generateUUID().replace("-", "_");
    }

    public static String generateRandomNumber() {
        String formatUUID = generateUUID()
                .replaceAll("[^0-9.]", "")
                .replaceFirst("^0", "");
        return formatUUID
                .substring(0, Math.min(formatUUID.length(), 9));
    }

    public static LocalTime getRandomTimeFromTo(LocalTime startTime, LocalTime endTime) {
        return LocalTime.ofSecondOfDay(ThreadLocalRandom.current().nextInt(startTime.toSecondOfDay(), endTime.toSecondOfDay()));
    }

    public static Integer getRandomNumberFromRange(Integer min, Integer max) {
        return new Random().nextInt(max - min) + min;
    }

    public static String getRandomDateBetween(LocalDate startDate, LocalDate endDate) {
        OffsetDateTime newDate = LocalDate.ofEpochDay(ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay()))
                .atTime(getRandomTimeFromTo(LocalTime.MIN, LocalTime.MAX))
                .atOffset(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return OffsetDateTime.parse(newDate.format(formatter), formatter).toString();
    }

    public static String getRandomElementFromList(List<String> givenList) {
        return givenList.get(new Random().nextInt(givenList.size()));
    }

    public static String generateLimitedString(int limit) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, limit);
    }
}
