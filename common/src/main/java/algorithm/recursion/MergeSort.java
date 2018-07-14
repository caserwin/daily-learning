package algorithm.recursion;

import java.util.Arrays;

/**
 * https://blog.csdn.net/qq_25827845/article/details/70994874
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] nums = {2, 7, 8, 3, 1, 6, 9, 0, 5, 4, -3};
        System.out.println(Arrays.toString(nums));
        sort(nums, 0, nums.length - 1);
        System.out.println(Arrays.toString(nums));
    }

    public static void sort(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 处理左边
            sort(nums, low, mid);
            // 处理右边
            sort(nums, mid + 1, high);
            // 左右归并
            merge(nums, low, mid, high);
        }
    }

    private static void merge(int[] nums, int low, int mid, int high) {
        // 定义一个辅助数组，所以该算法的空间复杂度为O（n）
        int[] temp = new int[high - low + 1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        // 找出较小值元素放入temp数组中
        while (i <= mid && j <= high) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        // 处理较长部分
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= high) {
            temp[k++] = nums[j++];
        }
        // 使用temp中的元素覆盖nums中元素
        for (int k2 = 0; k2 < temp.length; k2++) {
            nums[k2 + low] = temp[k2];
        }
    }
}
