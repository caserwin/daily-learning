package json;

import java.util.HashMap;
import org.json.JSONObject;

/**
 * @author yidxue
 */
public class MapToJsonDemo2 {
    public static void main(String[] args){
        HashMap<String, String> map = new HashMap<>();
        map.put("name","erwin");
        map.put("age","28");
        map.put("org","pda");

        System.out.println(new JSONObject(map).toString());
    }
}
