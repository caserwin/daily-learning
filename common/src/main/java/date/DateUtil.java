package date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yidxue
 */
public class DateUtil {

    /**
     * 日期加1天
     */
    public static String getAfterDay(String str, String inFormat, String outFormat) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(inFormat);
        DateTime dateTime = formatter.parseDateTime(str);
        DateTime d = dateTime.plusDays(1);
        return DateTimeFormat.forPattern(outFormat).print(d);
    }

    /**
     * 得到日期范围
     */
    public static List<String> getDayListBetween(String start, String end, String dateFormatter) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormatter);
        DateTime dateStart = formatter.parseDateTime(start);
        DateTime dateEnd = formatter.parseDateTime(end);
        if (dateStart.isAfter(dateEnd)) {
            return new ArrayList<>();
        }
        List<String> res = new ArrayList<>();
        while (dateStart.isBefore(dateEnd)) {
            res.add(DateTimeFormat.forPattern(dateFormatter).print(dateStart));
            dateStart = dateStart.plusDays(1);
        }
        res.add(end);
        return res;
    }

    /**
     * 判断是否是有效的日期
     */
    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }



    public static void main(String[] args){
        System.out.println(isValidDate("2017-11-11"));
    }
}
