package kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author yidxue
 */
public class Service {
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(currentTime);
    }

    public static String getRandomStr(){
        String str = "qwertyuiopasdfghjklzxcvbnm";
        int a =new Random().nextInt(8);
        StringBuilder res= new StringBuilder();
        for (int i = 0; i <= a; i++) {
            int b=(int)(Math.random()*25);
            res.append(str.charAt(b));
        }
        return res.toString();
    }

    public static Integer getRandomInt(){
        return new Random().nextInt(8);
    }
}
