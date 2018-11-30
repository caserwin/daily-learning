package algorithm;

/**
 * Created by yidxue on 2018/11/29
 */
public class IterPrintDemo {
    class Node {
        public Node(int id, String value, Node parent) {
            this.id = id;
            this.value = value;
            this.parent = parent;
        }

        int id;
        String value;
        Node parent;
    }

    public static void main(String[] args) {
        Node root = new IterPrintDemo().new Node(0, "a", null);
        Node node1 = new IterPrintDemo().new Node(1, "b", root);
        Node node2 = new IterPrintDemo().new Node(2, "c", node1);
        Node node3 = new IterPrintDemo().new Node(3, "d", node2);

        System.out.println(getNodePath(node3));
        printNodePath(node3);
    }

    public static String getNodePath(Node node) {
        if (node.parent == null) {
            return node.id + ":" + node.value;
        }
        return getNodePath(node.parent) + "-->" + node.id + ":" + node.value;
    }

    public static void printNodePath(Node node) {
        while (node.parent != null) {
            System.out.print(node.id + ":" + node.value + "<--");
            node = node.parent;
        }
        System.out.println(node.id + ":" + node.value);
    }
}
