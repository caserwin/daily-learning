package stream;

import java.util.function.Function;

public class JavaLambda {

    public static void main(String[] args){
        // 正常写法
        Function myParseInt = (Object s) -> { return Integer.parseInt((String) s);};

        // 简化写法，省略参数类型，一个输入参数时省略圆括号，一行输出的话，省略{}和;以及return
        Function myParseInt1 = s -> Integer.parseInt((String) s);

        // 两个参数



    }
}
