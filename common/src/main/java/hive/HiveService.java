package hive;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

/**
 * @author yidxue
 * https://my.vertica.com/docs/8.1.x/HTML/index.htm#Authoring/ConnectingToVertica/ClientJDBC/BatchInsertsUsingJDBCPreparedStatements.htm
 */
public class HiveService {

    private static final String URLHIVE = "jdbc:hive2://localhost:10000/default";
    private static Connection connection = null;

    public static Connection getHiveConnection() {
        if (null == connection) {
            synchronized (HiveService.class) {
                if (null == connection) {
                    try {
                        Class.forName("org.apache.hive.jdbc.HiveDriver");
                        connection = DriverManager.getConnection(URLHIVE, "", "");
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public void createHiveTable(String tablename, ArrayList<String> cols) {
        Connection conn = HiveService.getHiveConnection();
        try {
            Statement stmt = conn.createStatement();
            String fields = cols.stream().map(col -> col + " string").collect(Collectors.joining(","));
            String sql = "create table if not exists " + tablename + "(" + fields + ")"
                             + " row format delimited fields terminated by '\\t'"
                             + " collection items terminated by ',' stored as sequenceFile;";

            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertTable(String tablename, ArrayList<String> cols, ArrayList<String[]> rows) {
        Connection conn = HiveService.getHiveConnection();
        int num = cols.size();
        try {
            conn.setAutoCommit(false);

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

    public void loadToHive(String filepath, String tableName) {
        Connection conn = HiveService.getHiveConnection();
        try {
            Statement stmt = conn.createStatement();
            String sql = "LOAD DATA LOCAL INPATH '" + filepath + "' into table " + tableName;
            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> cols = new ArrayList<>();
        cols.add("col1");
        cols.add("col2");
        cols.add("col3");

        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"1", "2", "3"});
        rows.add(new String[]{"4", "5", "6"});
        rows.add(new String[]{"7", "8", "9"});

        String tablename = "test";
        int num = cols.size();
        String fields = cols.stream().collect(Collectors.joining(","));
        String valueNUM = StringUtils.repeat("?,", num);
        String sql = "INSERT INTO " + tablename + "(" + fields + ") VALUES(" + valueNUM.substring(0, valueNUM.length() - 1) + ")";

        System.out.println(sql);

//        String tablename ="test.hive_learn";
//        ArrayList<String> cols = new ArrayList<>();
//        cols.add("col1");
//        cols.add("col2");
//        cols.add("col3");
//
//        String fields = cols.stream().map(col -> col + " string").collect(Collectors.joining(","));
//        String sql = "create table if not exists " + tablename + "(" + fields + ")"
//                         + " row format delimited fields terminated by '\\t'"
//                         + " collection items terminated by ',' stored as sequenceFile;";

//        System.out.println(sql);
    }
}
