package cassandra.demo;

import cassandra.conn.CassConnSimple;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * @author erwin
 */
public class Demo1 {

    public static void main(String[] args) {
        Session sess = CassConnSimple.getConn();

        String table = "emp";
        // 删除原表中所有数据
        sess.execute("truncate " + table);
        System.out.println("===================================");
        // 插入
        sess.execute("INSERT INTO " + table + " (empid, emp_first, emp_last, emp_dept) VALUES (?,?,?,?)", 2, "fred222", "smith", "CHI");
        sess.execute("INSERT INTO " + table + " (empid, emp_first, emp_last, emp_dept) VALUES (?,?,?,?)", 1, "fred111", "smith", "CHI");
        // 查询
        ResultSet rs1 = sess.execute("select * from " + table);
        for (Row row : rs1) {
            System.out.println(row.getInt("empid") + "->" + row.getString("emp_first") + "->" + row.getString("emp_last") + "->" + row.getString("emp_dept"));
        }

        System.out.println("===================================");
        // 更新
        sess.execute("update " + table + " set emp_dept = 'erwin' where empid = 1");
        // 查询
        ResultSet rs2 = sess.execute("select * from emp");
        for (Row row : rs2) {
            System.out.println(row.getInt("empid") + "->" + row.getString("emp_first") + "->" + row.getString("emp_last") + "->" + row.getString("emp_dept"));
        }

        sess.close();
        System.out.println("===================================");
    }
}
