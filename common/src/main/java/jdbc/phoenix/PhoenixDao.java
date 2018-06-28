package jdbc.phoenix;

import jdbc.DBConnection;
import org.apache.commons.lang.StringUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by yidxue on 2018/6/28
 * https://my.vertica.com/docs/8.1.x/HTML/index.htm#Authoring/ConnectingToVertica/ClientJDBC/BatchInsertsUsingJDBCPreparedStatements.htm
 */
public class PhoenixDao {

    private String URLHIVE = "jdbc:hive2://localhost:10001/default";
    private String DBType = "phoenix";
    private Connection conn;

    public PhoenixDao() {
        this.conn = DBConnection.getConnection(DBType, URLHIVE);
    }

    public void createTable(String tablename, String[] cols) {
        try {
            Statement stmt = this.conn.createStatement();
            String sql = "";
            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * insert into table
     */
    public void insertTable(String tablename, ArrayList<String> cols, ArrayList<String[]> rows) {
        int num = cols.size();
        try {
            this.conn.setAutoCommit(false);
            String fields = cols.stream().collect(Collectors.joining(","));
            String valueNUM = StringUtils.repeat("?,", num);
            String sql = "INSERT INTO " + tablename + "(" + fields + ") VALUES(" + valueNUM.substring(0, valueNUM.length() - 1) + ")";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for (String[] row : rows) {
                for (int i = 0; i < num; i++) {
                    pstmt.setString(i, row[i]);
                }
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }
}
