package tools;

/**
 * Created by yidxue on 2018/4/11
 */
public class IsChinese {

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

    public static void main(String[] args){
        String str = "dhdhdhdj, (ddd)";
        //判断字符串是否全为英文字母，是则返回true
        boolean isWord = str.matches("[a-zA-Z\\s,()]+");
        System.out.println(isWord);
    }
}
