package algorithm;

/**
 * 题目描述：一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种方法？
 */
public class SkipSteps {

    public static void main(String[] args) {
        System.out.println(processByRecursion(4));
        System.out.println(processByIteration(4));
    }


    public static int processByRecursion(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return processByRecursion(n - 1) + processByRecursion(n - 2);
    }

    public static int processByIteration(int n) {
        int[] res = new int[n];
        res[0] = 1;
        res[1] = 2;

        if (n < 3) {
            return res[n - 1];
        }

        for (int i = 2; i < n; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }

        return res[n - 1];
    }
}
