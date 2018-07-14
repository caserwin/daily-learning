package algorithm.recursion;

/**
 * Created by yidxue on 2018/7/14
 */
public class HanoiTower {

    public static void main(String[] args) {
        move(4, "A", "B", "C");
    }

    public static void move(int level, String from, String mid, String to) {
        if (level == 1) {
            System.out.println(level + "号盘子" + "从" + from + " 移动到" + to);
        } else {
            move(level - 1, from, to, mid);
            System.out.println(level + "号盘子" + "从" + from + " 移动到" + to);
            move(level - 1, mid, from, to);
        }
    }
}
