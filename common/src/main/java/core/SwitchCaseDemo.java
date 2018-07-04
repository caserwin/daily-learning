package core;

/**
 * Created by yidxue on 2018/7/4
 */
public class SwitchCaseDemo {
    public static void main(String[] args) {
        switch ("2") {
            case "1":
                System.out.println("===1===");
                break;
            case "2":
                System.out.println("===2===");
                break;
            default:
                System.out.println("===3===");
                break;
        }
    }
}
