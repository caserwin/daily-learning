package jdbc.bean;

import jdbc.conn.URLConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/7/2
 */
public abstract class BaseRecord {

    private String[] primaryKey;
    private String[] cols;
    private String[] types;
    private LinkedHashMap<String, String> colAndValue;
    private static LinkedHashMap<String, String> colAndType = new LinkedHashMap<>();

    public BaseRecord(String cls) {
        cols = URLConstant.getCols(cls);
        types = URLConstant.getType(cls);
        primaryKey = URLConstant.getPrimaryKey(cls);
        colAndValue = new LinkedHashMap<>();
        for (int i = 0; i < cols.length; i++) {
            colAndType.put(cols[i], types[i]);
        }
    }

    public abstract BaseRecord buildFields(String... args);


    public void setColAndValue(String field, String value) {
        colAndValue.put(field, value);
    }

    public HashMap<String, String> getColAndValue() {
        return colAndValue;
    }

    public String[] getPrimaryKey() {
        return primaryKey;
    }

    public String[] getCols() {
        return cols;
    }

    public String[] getTypes() {
        return types;
    }

    public static HashMap<String, String> getColAndType() {
        return colAndType;
    }

    public ArrayList<String> getValues() {
        return new ArrayList<>(colAndValue.values());
    }

    @Override
    public String toString() {
        return colAndValue.values().stream().collect(Collectors.joining("\t"));
    }
}

