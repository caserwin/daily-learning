package util.bean;

import lombok.Data;

/**
 * Created by yidxue on 2018/5/11
 */
@Data
public class Element {
    /**
     * 必须设置为 public
     */
    public String name;
    /**
     * 必须设置为 public
     */
    public long number;

    public Element() {
    }

    public Element(String name, long number) {
        this.name = name;
        this.number = number;
    }
}
