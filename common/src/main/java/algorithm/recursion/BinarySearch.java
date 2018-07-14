package algorithm.recursion;

/**
 * Created by yidxue on 2018/7/14
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] a = {1, 3, 6, 9, 10, 18, 32, 67};
        System.out.println(search(a, 9, 0, a.length - 1));
    }

    public static int search(int[] a, int value, int begin, int end) {
        if (begin > end) {
            return -1;
        } else {
            int mid = (begin + end) / 2;
            if (a[mid] == value) {
                return mid;
            }
            // 继续递归
            if (a[mid] > value) {
                return search(a, value, begin, mid);
            } else {
                return search(a, value, mid, end);
            }
        }
    }
}
