package datastream.activeuser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.table.functions.ScalarFunction;

/**
 * @author yiding
 */
public class ExtractJsonField extends ScalarFunction {

    public String eval(ObjectNode node, String fields) {
        JsonNode jsonNode = node;
        String regex = "\\.";
        for(String s: fields.split(regex)){
            jsonNode = jsonNode.get(s);
            if(jsonNode == null) {
                return null;
            }
        }
        return jsonNode.asText();
    }
}
