package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {
    public static void main(String[] args) {
        String text = "jme#Download[atmgr.exe][WebEx_V]16242694/7768";
        String pattern = "(jme#Download\\[[A-Za-z0-9_.]+]\\[\\w+])(\\d+)/(\\d+)";

        boolean isMatch = Pattern.matches(pattern, text);
        System.out.println(isMatch);

        boolean isMatch1 = Pattern.matches("\\[[A-Za-z0-9_.]+]", "[atmgr.exe]");
        System.out.println(isMatch1);

        boolean isMatch2 = Pattern.matches("\\w+", "ttt");
        System.out.println(isMatch2);

//         创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
//         现在创建 matcher 对象
        Matcher m = r.matcher(text);

        if (m.find()) {
            System.out.println("字符串本身: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3));
        }


    }
}
