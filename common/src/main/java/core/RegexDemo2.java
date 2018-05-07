package core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yidxue
 */
public class RegexDemo2 {

    public static String getJMA(String text) {
        String pattern1 = ".+,jma:([\\d.]+),.+";
        Pattern r = Pattern.compile(pattern1);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    public static String getMcsInstancdId(String text) {
        String pattern1 = "/opt/webex/mmp/logs/([0-9a-zA-Z-]+)_metrics_";
        Pattern r = Pattern.compile(pattern1);
        Matcher m = r.matcher(text);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        String text = "/opt/webex/mmp/logs/wbxmcs-01_metrics_01022018_0.28919.log";

        System.out.println(getMcsInstancdId(text));

    }
}

