package datastream.timetype.eventtime.research;

import java.text.SimpleDateFormat;

/**
 * Created by yidxue on 2018/9/4
 */
public class BoundaryForTimeWindowTest {
    public static void main(String[] args) {

        // 注意是毫秒为单位
        long windowsize = 14000L;
        // 注意是毫秒为单位
        long offset = 0L;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long a1 = 1000000050000L;
        long a2 = 1000000054000L;
        long a3 = 1000000079900L;
        long a4 = 1000000115000L;
        long b5 = 1000000100000L;
        long b6 = 1000000109000L;

        System.out.println(a1 + " -> " + format.format(a1) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a1, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a1, offset, windowsize)));
        System.out.println(a2 + " -> " + format.format(a2) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a2, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a2, offset, windowsize)));
        System.out.println(a3 + " -> " + format.format(a3) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a3, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a3, offset, windowsize)));
        System.out.println(a4 + " -> " + format.format(a4) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(a4, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(a4, offset, windowsize)));
        System.out.println(b5 + " -> " + format.format(b5) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(b5, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(b5, offset, windowsize)));
        System.out.println(b6 + " -> " + format.format(b6) + "\t所属窗口的起始时间是: " + getWindowStartWithOffset(b6, offset, windowsize) + " -> " + format.format(getWindowStartWithOffset(b6, offset, windowsize)));

    }

    private static long getWindowStartWithOffset(long timestamp, long offset, long windowSize) {
        return timestamp - (timestamp - offset + windowSize) % windowSize;
    }
}
