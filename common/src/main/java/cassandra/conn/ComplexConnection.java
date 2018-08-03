package cassandra.conn;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.HostDistance;
import com.datastax.driver.core.PoolingOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.*;

/**
 * Created by yidxue on 2018/5/16
 *
 * @author yidxue
 */
public class ComplexConnection {

    private Cluster cluster = null;
    private Session session = null;

    public synchronized void buildCluster() {
        if (cluster != null) {
            return;
        }

        String nodes = "localhost";
        int port = 9042;
        String localDCName = "dc";
        String userName = "cassandra";
        String password = "cassandra";

        PoolingOptions poolingOptions = new PoolingOptions();
        poolingOptions.setHeartbeatIntervalSeconds(30);
        poolingOptions.setPoolTimeoutMillis(5000);

        // connection可以支持多少并发request
        poolingOptions.setMaxRequestsPerConnection(HostDistance.LOCAL, 1024);
        poolingOptions.setMaxRequestsPerConnection(HostDistance.REMOTE, 0);

        // 将core设置低于Max，在非繁忙期间可以减少connection连接数
        poolingOptions.setCoreConnectionsPerHost(HostDistance.LOCAL, 2);
        poolingOptions.setMaxConnectionsPerHost(HostDistance.LOCAL, 4);
        poolingOptions.setCoreConnectionsPerHost(HostDistance.REMOTE, 0);
        poolingOptions.setMaxConnectionsPerHost(HostDistance.REMOTE, 0);

        poolingOptions.setIdleTimeoutSeconds(30);

        RetryPolicy retryPolicy = new LoggingRetryPolicy(FallthroughRetryPolicy.INSTANCE);

        cluster = Cluster.builder().withReconnectionPolicy(new ExponentialReconnectionPolicy(1000, 10 * 60 * 1000)).withRetryPolicy(retryPolicy).withPoolingOptions(poolingOptions)
                      .withLoadBalancingPolicy(new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().withLocalDc(localDCName).build())).withCredentials(userName, password)
                      .addContactPoints(nodes.split(",")).withPort(port).withoutJMXReporting().build().init();

        session = cluster.connect();
    }

    public synchronized Session getSession() {
        return session;
    }
}
