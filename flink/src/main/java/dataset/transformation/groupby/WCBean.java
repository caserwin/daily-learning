package dataset.transformation.groupby;

/**
 * Created by yidxue on 2018/2/11
 */
public class WCBean {
    public String word;
    public int count;

    public WCBean(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public String toString() {
        return this.word + "\t" + this.count;
    }
}
