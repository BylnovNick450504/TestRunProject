package run.service.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class DateHelper {

    private DateHelper() {}

    private static final Log LOGGER = LogFactory.getLog(DateHelper.class);

    public static String dateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        return formatter.format(date);
    }

    public static String dateToString(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static Date stringToDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
        Date date = new Date();
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            LOGGER.info("Parse exception");
        }
        return date;
    }
}
