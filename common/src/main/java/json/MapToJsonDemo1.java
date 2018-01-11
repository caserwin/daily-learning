package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;

/**
 * @author yidxue
 */
public class MapToJsonDemo1 {
    public static void main(String[] args){
        ObjectMapper om = new ObjectMapper();
        HashMap<String, String> map = new HashMap<>();
        map.put("name","erwin");
        map.put("age","28");
        map.put("org","pda");

        try {
            System.out.println(om.writeValueAsString(map));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
