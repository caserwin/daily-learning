package ignite;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by yidxue on 2018/9/19
 */
public class IgniteJavaService implements Serializable {

    private Statement stmt = null;
    private Connection conn = null;

    public IgniteJavaService(String url) {
        try {
            conn = DriverManager.getConnection("jdbc:ignite:thin://" + url);
            stmt = conn.createStatement();
            System.out.println("this is a new instance IgniteJavaService !!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(String table, String cols, String prikeys) {
        try {
            String fields = Stream.of(cols.split(",")).map(x -> x + " VARCHAR").collect(Collectors.joining(","));
            String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s, PRIMARY KEY(%s)) WITH \"template=partitioned,backups=2\"", table, fields, prikeys);
            System.out.println(sql);
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createIndex(String tableName, String indexName) {
        try {
            String sql = "CREATE INDEX IF NOT EXISTS " + indexName + " ON " + tableName + " (SITEID, CONFID)";
            System.out.println(sql);
            this.stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
