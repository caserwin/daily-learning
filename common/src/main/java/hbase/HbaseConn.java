package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class HbaseConn {
    public static Configuration configuration;
    private static Connection connection;

    /**
     * 初始化链接
     */
    public static void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "xxx.xxx");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("zookeeper.znode.parent", "xxx");
        configuration.setInt("hbase.client.retries.number", 1);
        configuration.setInt("zookeeper.recovery.retry", 0);
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        init();
        return connection;
    }

    /**
     * 关闭连接
     */
    public static void close() {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
