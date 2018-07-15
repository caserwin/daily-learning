package reflection;

/**
 * @author yidxue
 */
public class Service2 {
    private int i;
    public Service2(int i){
        this.i = i;
    }

    public void doService2(){
        System.out.println("无参方法 "+i);
    }

    public static void main(String[] args){
        new Service2(11).doService2();
    }
}