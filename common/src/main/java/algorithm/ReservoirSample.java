package algorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by yidxue on 2018/7/14
 */
public class ReservoirSample {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            int[] a = {1, 2, 3, 4};
            Arrays.stream(getSample(a, 3)).forEach(x -> System.out.print(x + "\t"));
            System.out.println();
        }
    }

    private static int[] getSample(int[] a, int k) {
        int[] reservoir = new int[k];
        Random rand = new Random();
        int j = 0;
        for (int i = 0; i < a.length; i++) {
            if (i < k) {
                reservoir[j++] = a[i];
            } else {
                // 取[1, i]
                int randnum = rand.nextInt(i + 1) + 1;
                if (randnum <= k) {
                    int randIndex = rand.nextInt(k);
                    reservoir[randIndex] = a[i];
                }
            }
        }
        return reservoir;
    }
}
