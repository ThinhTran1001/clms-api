package vn.threeluaclmsapi.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String convertDateFormatFromExcel(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = inputFormat.parse(dateStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            return outputFormat.format(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr, e);
        }
    }

    public static String formatSchedule(String startTimeStr, String endTimeStr, String dateStr) {
        LocalTime startTime = LocalTime.parse(startTimeStr);
        LocalTime endTime = LocalTime.parse(endTimeStr);

        LocalDate date = LocalDate.parse(dateStr);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dayOfWeek = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH); // Lấy thứ trong tuần

        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("H:mm"));
        String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("H:mm"));

        return formattedStartTime + "-" + formattedEndTime + " " + dayOfWeek + " " + date.format(dateFormatter);
    }

    public static LocalDate convertStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, FORMATTER);
    }
}
