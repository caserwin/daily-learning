package jdbc.bean;

import jdbc.conn.URLConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/6/30
 */
public class PersonRecord {

    private final static String cls = "person";
    private static String[] primaryKey = URLConstant.getPrimaryKey(cls);
    private static String[] cols = URLConstant.getCols(cls);
    private static String[] types = URLConstant.getType(cls);
    private static LinkedHashMap<String, String> colAndType = new LinkedHashMap<>();
    private LinkedHashMap<String, String> colAndValue = new LinkedHashMap<>();

    static {
        for (int i = 0; i < cols.length; i++) {
            colAndType.put(cols[i], types[i]);
        }
    }

    public PersonRecord(int id, String name, int age, String gender) {
        colAndValue.put("id", String.valueOf(id));
        colAndValue.put("name", String.valueOf(name));
        colAndValue.put("age", String.valueOf(age));
        colAndValue.put("gender", String.valueOf(gender));
    }

    public static String[] getPrimaryKey() {
        return primaryKey;
    }

    public static String[] getCols() {
        return cols;
    }

    public static String[] getTypes() {
        return types;
    }

    public static HashMap<String, String> getColAndType() {
        return colAndType;
    }

    public HashMap<String, String> getColAndValue() {
        return colAndValue;
    }

    public ArrayList<String> getValues() {
        return new ArrayList<>(colAndValue.values());
    }


    @Override
    public String toString() {
        return colAndValue.values().stream().collect(Collectors.joining("\t"));
    }
}
