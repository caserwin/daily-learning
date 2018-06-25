package stream.lambda;

import java.util.ArrayList;

/**
 * @author yidxue
 */
public class ListLambdaDemo {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.forEach(x -> System.out.println("a" + x));
    }
}
