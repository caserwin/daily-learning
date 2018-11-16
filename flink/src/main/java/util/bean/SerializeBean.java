package util.bean;

import java.io.*;
import java.util.HashSet;
import util.tool.SerializeUtil;

/**
 * Created by yidxue on 2018/11/14
 */
public class SerializeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashSet<String> set = new HashSet<>();

    public HashSet<String> getSet() {
        return set;
    }

    public static void main(String[] args) {
        SerializeBean serializeBean = new SerializeBean();
        serializeBean.getSet().add("A");
        serializeBean.getSet().add("B");
        serializeBean.getSet().add("C");

        SerializeUtil.serializeToFile(serializeBean, "data/serializeBean.txt");
    }
}
