package util.bean;

/**
 * Created by yidxue on 2018/2/11
 */
public class WCBean {
    public String word;
    public int frequency;

    public WCBean() {
    }

    public WCBean(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return this.word + "\t" + this.frequency;
    }
}
