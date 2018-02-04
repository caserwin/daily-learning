package date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author yidxue
 */
public class DateUtil {

    private static final String[] DATE_PARTS = new String[]{"year", "month", "day", "hour", "minute", "second"};

    /**
     * 日期加1天
     * inFormat demo:
     */
    public static String getAfterDay(String str, String inFormat, String outFormat) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(str);
        DateTime d = dateTime.plusDays(1);
        return DateTimeFormat.forPattern(outFormat).print(d);
    }

    /**
     * 得到日期范围
     * inFormat demo:
     */
    public static List<String> getDayListBetween(String start, String end, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateStart = formatter.parseDateTime(start);
        DateTime dateEnd = formatter.parseDateTime(end);
        if (dateStart.isAfter(dateEnd)) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        while (dateStart.isBefore(dateEnd)) {
            res.add(DateTimeFormat.forPattern(format).print(dateStart));
            dateStart = dateStart.plusDays(1);
        }
        res.add(end);
        return res;
    }

    /**
     * 判断是否是指定格式的日期
     */
    public static boolean isValidDate(String str, String inFormat) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 对日期进行格式转换
     */
    public static String transFormat(String str, String inFormat, String outFormat) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(str);
        return DateTimeFormat.forPattern(outFormat).print(dateTime);
    }

    /**
     * 指定格式日期的具体时间部分
     */
    public static String getDatePart(String date, String inFormat, String part) {
        if (Arrays.stream(DATE_PARTS).noneMatch(part.toLowerCase()::equals)) {
            return "part should be one of value \"year, month, day, hour, minute, second\"";
        }

        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(date);
        HashMap<String, Integer> hashMap = new HashMap<>();

        hashMap.put("year", dateTime.getYear());
        hashMap.put("month", dateTime.getMonthOfYear());
        hashMap.put("day", dateTime.getDayOfMonth());
        hashMap.put("hour", dateTime.getHourOfDay());
        hashMap.put("minute", dateTime.getMinuteOfHour());
        hashMap.put("second", dateTime.getSecondOfMinute());

        return String.valueOf(hashMap.get(part.toLowerCase()));
    }


    public static void main(String[] args) {
        // 是否是指定格式的日期
        System.out.println(isValidDate("2017-11-11", "yyyy-MM-dd"));
        System.out.println(isValidDate("2018-01-02T00:02:36Z", "yyyy-MM-dd'T'HH:mm:ss'Z'"));

        // 日期格式转换
        System.out.println(transFormat("2017-11-11", "yyyy-MM-dd", "yyyy-MM-dd HH:mm"));

        // 取指定格式下日期的年、月、日、时、分、秒
        System.out.println(getDatePart("2017-11-11", "yyyy-MM-dd", "day"));
        System.out.println(getDatePart("2018-01-02T00:02:36Z", "yyyy-MM-dd'T'HH:mm:ss'Z'", "minute"));

    }
}
