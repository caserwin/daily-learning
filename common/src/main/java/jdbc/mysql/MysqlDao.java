package jdbc.mysql;

import jdbc.DBOperate;
import jdbc.conn.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by yidxue on 2018/6/30
 */
public class MysqlDao implements DBOperate {

    private String url = "jdbc:mysql://xxx:3306/dbname";
    private String username = "";
    private String password = "";
    private String DBType = "phoenix";
    private Connection conn;


    public MysqlDao(String url, String username, String password) {
        this.conn = DBConnection.getConnection(DBType, url, username, password);
    }

    @Override
    public void create(String tablename, String cl) {

    }

    @Override
    public void insert() {
//        Connection conn = DBConnection.getConnection();
//        String sql = "INSERT INTO ebk_teen_student_cls_hour (sid, cls_type, cls_hour, create_time, expire_time," +
//                         " for_free, order_id, use_level, cls_hour_bak, cls_type_bak, expire_time_bak)" +
//                         " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
//
//        PreparedStatement pstmt;
//        try {
//            conn.setAutoCommit(false);
//            pstmt = conn.prepareStatement(sql);
//            for (StudentClsHour record : recordLS) {
//                pstmt.setInt(1, record.getSid());
//                pstmt.setInt(2, record.getCls_type());
//                pstmt.setInt(3, record.getCls_hour());
//                pstmt.setInt(4, record.getCreate_time());
//                pstmt.setInt(5, record.getExpire_time());
//                pstmt.setInt(6, record.getFor_free());
//                pstmt.setInt(7, record.getOrder_id());
//                pstmt.setInt(8, record.getUse_level());
//                pstmt.setInt(9, record.getCls_hour_bak());
//                pstmt.setInt(10, record.getCls_type_bak());
//                pstmt.setInt(11, record.getExpire_time_bak());
//
//                pstmt.addBatch();
//            }
//
//            pstmt.executeBatch();
//            conn.commit();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void select() {

    }
}
