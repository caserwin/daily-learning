package cassandra.conn;

import com.datastax.driver.core.*;

/**
 * @author erwin
 */
public class CassConnSimple {

    public static Session getConn() {
        //要连接的库，可以不写
        String keyspace = "dev";
        //cassandra主机地址
        String[] hosts = new String[]{"localhost"};
        //认证配置，没有用户名、密码
        AuthProvider authProvider = new PlainTextAuthProvider("", "");
        // 创建连接到Cassandra的客户端
        Cluster cluster = Cluster.builder().addContactPoints(hosts).withAuthProvider(authProvider).build();
        // 创建用户会话
        return cluster.connect(keyspace);
    }
}
