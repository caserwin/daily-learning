package tools;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
     * 分钟加减
     */
    public static String modifyMins(String str, String inFormat, String outFormat, int mins) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(str);
        DateTime d = dateTime.plusMinutes(mins);
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
     * TODO 改成joda time的写法
     */
    public static boolean isValidDate(String str, String inFormat) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(inFormat);
        try {
            // 设置lenient为 false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
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

    /**
     * 每个小时的时间按 minutes=range，进行分段
     */
    public static String[] getTimeMinutesRange(String date, String inFormat, String outFormat, int range) {
        String[] timeRange = new String[2];
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(date);

        int startMin = (dateTime.getMinuteOfHour() / range) * range;
        timeRange[0] = DateTimeFormat.forPattern(outFormat).print(dateTime.withMinuteOfHour(startMin).withSecondOfMinute(0));
        timeRange[1] = modifyMins(timeRange[0], outFormat, outFormat, range);

        return timeRange;
    }

    /**
     * 判断一个日期是否是周末
     * TODO 改成joda time的写法
     */
    public static boolean weekendCheck(String date) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    /**
     * 返回当前时间所在的分段
     */
    public static String[] getTimeArrays(String starttime, String endtime, String inFormat, int num) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        long startTimeStamp = formatter.parseDateTime(starttime).getMillis() / 1000;
        long endTimeStamp = formatter.parseDateTime(endtime).getMillis() / 1000;
        long range = (endTimeStamp - startTimeStamp) / num;
        String[] timeArrays = new String[num + 2];
        timeArrays[0] = starttime;
        timeArrays[num + 1] = endtime;

        for (int i = 1; i < timeArrays.length - 1; i++) {
            if (startTimeStamp + range <= endTimeStamp) {
                timeArrays[i] = DateTimeFormat.forPattern(inFormat).print(new DateTime((startTimeStamp + range) * 1000L));
            } else {
                timeArrays[i] = DateTimeFormat.forPattern(inFormat).print(new DateTime(endTimeStamp * 1000L));
            }
            startTimeStamp += range;
        }

        if (timeArrays[timeArrays.length - 1].equals(timeArrays[timeArrays.length - 2])) {
            return Arrays.copyOfRange(timeArrays, 0, timeArrays.length - 1);
        } else {
            return timeArrays;
        }
    }

    public static String timestampToDate(Long timestamp) {
        return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(new DateTime(timestamp));
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

        // 获得指定时间所在的分段
        String[] timeRange = getTimeMinutesRange("2018-02-28T23:55:36Z", "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd HH:mm:ss", 15);
        System.out.println(timeRange[0] + "\t\t" + timeRange[1]);

        // 对于给定的时间区间进行分段
        String[] timeArrays = getTimeArrays("2018-03-21 18:10:00", "2018-03-21 18:20:00", "yyyy-MM-dd HH:mm:ss", 10);
        for (String time : timeArrays) {
            System.out.println(time);
        }

        // 检查是否是周末
        System.out.println(weekendCheck("2018-06-17"));
        System.out.println(weekendCheck("2018-06-18"));
    }
}
