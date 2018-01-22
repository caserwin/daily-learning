package json;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

/**
 * @author ydixue
 *
 * 嵌套解析，感觉比fastjson靠谱
 */
public class JsonPathDemo {
    public static void main(String[] args) {
        // multi json
        String json = "{ \"message\": {\"label\": 174065352} }";
        DocumentContext dc = JsonPath.parse(json);
        System.out.println(dc.read("$.message.label").toString());
    }
}
