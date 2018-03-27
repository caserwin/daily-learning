package core;

import java.util.Arrays;

/**
 * Created by yidxue on 2018/3/22
 */
public class CopyArrayDemo {
    public static void main(String[] args){
        int[] arr = {1,2,3,4,5};
        int[] copied = Arrays.copyOfRange(arr, 1,3);
        System.out.println(Arrays.toString(copied));
    }
}
