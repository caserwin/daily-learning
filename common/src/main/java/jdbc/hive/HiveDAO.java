package jdbc.hive;

import jdbc.DBConnection;
import jdbc.PersonRecord;
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
public class HiveDAO {

    private String URLHIVE = "jdbc:hive2://10.29.42.49:10000/default";
    private String DBType = "hive";
    private Connection conn;

    public HiveDAO() {
        this.conn = DBConnection.getConnection(DBType, URLHIVE);
    }

    public void createHiveTable(String tablename, String[] cols) {
        try {
            Statement stmt = this.conn.createStatement();
            String fields = Stream.of(cols).map(col -> col + " string").collect(Collectors.joining(","));
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
            Statement stmt = this.conn.createStatement();
            String sql = "LOAD DATA LOCAL INPATH '" + path + "' into table " + tableName;
            System.out.println(sql);
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("args error!");
            return;
        }
        String table = args[0];
        String path = args[1];

        ArrayList<PersonRecord> records = new ArrayList<>();
        records.add(new PersonRecord("1", "AA", "17", "male"));
        records.add(new PersonRecord("1", "BB", "19", "female"));
        records.add(new PersonRecord("1", "CC", "23", "male"));

        HiveDAO hiveDAO = new HiveDAO();
        // create table
        hiveDAO.createHiveTable(table, PersonRecord.getAttributes());

        // load data
        hiveDAO.loadToHive(records, path, table);

    }
}
