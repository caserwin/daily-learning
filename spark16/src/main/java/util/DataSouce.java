package util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yidxue on 2018/10/22
 */
public class DataSouce {
    public static ArrayList<String> getElements(String str) {
        String[] strs = str.split(",");
        return new ArrayList<>(Arrays.asList(strs));
    }
}
