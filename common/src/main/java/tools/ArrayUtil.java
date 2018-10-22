package tools;

/**
 * Created by yidxue on 2018/10/12
 */
public class ArrayUtil {
    public static void main(String[] args) {
        String[] strs = {"1", "2", "3", "4", "5"};
        String[] dest = new String[3];

        System.arraycopy(strs, 0, dest, 0, strs.length - 2);

        for (String str : dest) {
            System.out.println(str);
        }
    }
}
