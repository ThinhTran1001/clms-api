package vn.threeluaclmsapi.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

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
}
