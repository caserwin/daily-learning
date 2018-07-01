package jdbc.hive;

import jdbc.DBOperate;
import jdbc.Service;
import jdbc.bean.PersonRecord;
import jdbc.conn.DBConnection;
import tools.FileUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yidxue
 * https://my.vertica.com/docs/8.1.x/HTML/index.htm#Authoring/ConnectingToVertica/ClientJDBC/BatchInsertsUsingJDBCPreparedStatements.htm
 */
public class HiveDAO implements DBOperate {

    private String URLHIVE = "jdbc:hive2://localhost:10000/default";
    private String DBType = "hive";
    private Connection conn;

    public HiveDAO() {
        this.conn = DBConnection.getConnection(DBType, URLHIVE);
    }

    @Override
    public void create(String tablename, String cls) {
        try {
            String[] cols = Service.getCols(tablename);
            Statement stmt = this.conn.createStatement();
            String fields = Stream.of(cols).collect(Collectors.joining(","));
            String sql = "create table if not exists " + tablename + "(" + fields + ")"
                             + " row format delimited fields terminated by '\\t'"
                             + " collection items terminated by ','";

            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadToHive(ArrayList<PersonRecord> records, String path, String tableName) {
        try {
            FileUtil.writeByStream(records.stream().map(PersonRecord::toString).collect(Collectors.toList()), path);
            // 每一层目录都设置 777 权限
            FileUtil.changeFolderPermission(path, true);
            Statement stmt = this.conn.createStatement();
            String sql = "LOAD DATA LOCAL INPATH '" + path + "' into table " + tableName;
            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insert() {

    }

    @Override
    public void select() {

    }
}
