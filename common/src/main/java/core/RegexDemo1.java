package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yidxue
 */
public class RegexDemo1 {
    public static void main(String[] args) {
        String text = "jme#Download[atmgr.exe][WebEx_V]";
        String pattern = "jme#Download\\[[A-Za-z0-9_.]+]\\[\\w+]";
        //
        System.out.println(Pattern.matches(pattern, text));
        System.out.println(Pattern.matches("\\[[A-Za-z0-9_.]+]", "[atmgr.exe]"));
        System.out.println(Pattern.matches("\\w+", "ttt"));

        //  创建 Pattern 对象
//        Pattern r = Pattern.compile(pattern);
        //  现在创建 matcher 对象
//        Matcher m = r.matcher(text);
//        if (m.find()) {
//            System.out.println("Found value: " + m.group(1));
//            System.out.println("Found value: " + m.group(2));
//            System.out.println("Found value: " + m.group(3));
//        }


        String text1 = "qoe2={v:2.0,f:82938211572130241,n:83890177,t:a,u:2434884316,nma:4.13,nmm:4.13,jma:4.13,jmm:4.13,jba:164,jbm:164,pa:1.00,pm:1.00,ela:0,elm:0,eja:15,ejm:18,sla:0,slm:0,sja:15,sjm:18,lp:0,ls:100745216,ln:,jp:0,js:3519880704,jn:Ken Hedges}";
        String pattern1 = ".+,jma:([\\d.]+),.+";

        // 查看是否匹配
        System.out.println(Pattern.matches(pattern1, text1));

        // 正则捕获
        Pattern r1 = Pattern.compile(pattern1);
        Matcher m1 = r1.matcher(text1);
        if (m1.find()) {
            System.out.println("Found value: " + m1.group(1));
        }
    }
}
