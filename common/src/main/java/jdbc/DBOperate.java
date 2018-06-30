package jdbc;

/**
 * Created by yidxue on 2018/6/30
 */
public interface DBOperate {
    /**
     * 建表
     */
    public void create(String tablename, String[] cols);

    /**
     * 插入数据
     */
    public void insert();

    /**
     * 查询数据
     */
    public void select();
}
