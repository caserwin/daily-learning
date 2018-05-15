package bean;

/**
 * Created by yidxue on 2018/5/11
 *
 * @author yidxue
 */
public class CustomType {
    /**
     * 必须设置为 public
     */
    public String aName;
    /**
     * 必须设置为 public
     */
    public int aNumber;

    public CustomType() {
    }

    public CustomType(String aName, int aNumber) {
        this.aName = aName;
        this.aNumber = aNumber;
    }

    @Override
    public String toString() {
        return this.aName + ":" + this.aNumber;
    }
}
