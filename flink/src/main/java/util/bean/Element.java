package util.bean;

/**
 * Created by yidxue on 2018/5/11
 */
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.number;
    }
}
