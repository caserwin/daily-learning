package algorithm.pathplanning;

/**
 * Created by yidxue on 2018/11/30
 */
public class Node {
    public Node(int id, int value, Node parent) {
        this.id = id;
        this.value = value;
        this.parent = parent;
    }

    public int id;
    public int value;
    public Node parent;
}