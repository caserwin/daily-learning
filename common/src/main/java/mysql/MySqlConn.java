package mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;

/**
 * @author yidxue
 */
public class MySqlConn {

    public static Connection getConnection(String dbName) throws Exception {
        Map<String, Object> mysqlConf = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = mysqlConf.get("url").toString();
        String username = mysqlConf.get("username").toString();
        String password = mysqlConf.get("password").toString();
        Class.forName(driver).newInstance();
        Properties info = new Properties();
        info.setProperty("user", username);
        info.setProperty("password", password);
        info.setProperty("useUnicode", "true");
        info.setProperty("characterEncoding", "UTF8");
        return DriverManager.getConnection(url, info);
    }
}