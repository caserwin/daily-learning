package reflection;

/**
 * @author yidxue
 */
public class Service3 {
    private int i;
    public Service3(int i){
        this.i = i;
    }

    public void doService3(){
        System.out.println("业务方法 "+i);
    }

    public static void main(String[] args){
        new Service3(11).doService3();
    }
}
