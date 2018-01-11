package datastream.activeuser.service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yiding
 */
public class DateService {

    public String getSpecificDay(String strDate, int offsetDay) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar specificDay = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(strDate, pos);

        specificDay.setTime(date);
        specificDay.add(Calendar.DAY_OF_WEEK, offsetDay);

        return sdf.format(specificDay.getTime());
    }


    public String getSpecificWeek(String strDate, int offsetWeek) {
        String[] dates = strDate.split(" ");

        String startDay = getSpecificDay(dates[0], 7*offsetWeek);
        String endDay = getSpecificDay(dates[1],7*offsetWeek);

        return startDay+" "+endDay;
    }


    public String getSpecificMonth(String strDate, int offsetMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        Calendar specificMonth = Calendar.getInstance();
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(strDate, pos);

        specificMonth.setTime(date);
        specificMonth.add(Calendar.MONTH, offsetMonth);

        return sdf.format(specificMonth.getTime());
    }


    public String[] getSpecificWeekRange(String strDate) {
        SimpleDateFormat rawParseFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00'.000Z'");

        ParsePosition pos = new ParsePosition(0);
        Date date = rawParseFormat.parse(strDate, pos);

        Calendar specificWeek = Calendar.getInstance();
        specificWeek.setTime(date);

        specificWeek.set(Calendar.DAY_OF_WEEK, 1);
        String startTime = sdf.format(specificWeek.getTime());

        specificWeek.set(Calendar.DAY_OF_WEEK, 7);
        String endTime = sdf.format(specificWeek.getTime());

        return new String[] {startTime, endTime};
    }

}
