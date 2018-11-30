package algorithm.pathplanning;

/**
 * Created by yidxue on 2018/11/30
 */
public class Tool {
    public static String getNodePath(Node node) {
        if (node.parent == null) {
            return node.id + ":" + node.value;
        }
        return getNodePath(node.parent) + "-->" + node.id + ":" + node.value;
    }
}
