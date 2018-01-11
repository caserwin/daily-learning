package phoenix;

import java.sql.*;

/**
 * @author yiding
 */

public class PhoenixService {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Statement stmt;
        ResultSet rset;

        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        Connection con = DriverManager.getConnection("jdbc:phoenix:10.29.42.42:2181");
        stmt = con.createStatement();

        stmt.executeUpdate("create table IF NOT EXISTS ptest (mykey integer not null primary key, mycolumn varchar)");
        stmt.executeUpdate("upsert into ptest values (1,'Hello')");
        stmt.executeUpdate("upsert into ptest values (2,'World!')");
        con.commit();

        PreparedStatement statement = con.prepareStatement("select * from ptest");
        rset = statement.executeQuery();
        while (rset.next()) {
            System.out.println(rset.getString("mycolumn"));
        }
        statement.close();
        con.close();
    }
}