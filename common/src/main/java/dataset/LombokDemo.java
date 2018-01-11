package dataset;

import lombok.Data;

/**
 * @author erwin
 */
@Data
public class LombokDemo {
    private String id;
    private String name;
    private String identity;

    public static void main(String[] args){
        LombokDemo lombokDemo =new LombokDemo();
        lombokDemo.setId("id1");
        lombokDemo.setName("erwin");
        lombokDemo.setIdentity("111");

        System.out.println(lombokDemo.getId()+"\t"+lombokDemo.getName()+"\t"+lombokDemo.getIdentity());
    }
}
