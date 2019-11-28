package tools;

import org.apache.commons.lang.StringUtils;

/**
 * Created by yidxue on 2018/2/23
 */
public class StringUtil {
    public static void main(String[] args){

        System.out.println(StringUtils.equals("user", "user"));
        System.out.println(StringUtils.equals("user1", "user"));

        System.out.println(StringUtils.countMatches("abrcdebcbcb","bc"));

        System.out.println("===============================");
        System.out.println(StringUtils.isBlank(null));
        System.out.println(StringUtils.isBlank(" "));
        System.out.println(StringUtils.isBlank(""));
        System.out.println(StringUtils.isBlank("1"));

        System.out.println("===============================");
        String str = "dhdhdhdj, *.(ddd)";
        //判断字符串是否全为英文字母，是则返回true
        boolean isWord = str.matches("[a-zA-Z\\s,().]+");
        System.out.println(isWord);

        // 创建String 数组
        System.out.println(StringUtils.repeat(" ,", 5).split(",").length);
        String strnum[] = StringUtils.repeat(",", 5).split(",");
        System.out.println(strnum.length);

        // String 格式化
        String sql = String.format("select id,name,position from employee where name = '%s'", "ltq");
        System.out.println(sql);
    }

    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;
    }

    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
}
