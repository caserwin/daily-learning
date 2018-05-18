package cassandra.conn;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.*;


/**
 * @author yiding
 */
public class MyConnection {
    public static Session getConn() {
        //cassandra主机地址
        String[] hosts = new String[]{"192.168.1.1", "192.168.1.2", "192.168.1.3"};
        //认证配置
        AuthProvider authProvider = new PlainTextAuthProvider("ershixiong", "123456");
        LoadBalancingPolicy lbp = new TokenAwarePolicy(DCAwareRoundRobinPolicy.builder().withLocalDc("myDC").build());
        //读超时或连接超时设置
        SocketOptions so = new SocketOptions().setReadTimeoutMillis(3000).setConnectTimeoutMillis(3000);

        //连接池配置
        //PoolingOptions poolingOptions = new PoolingOptions().setConnectionsPerHost(HostDistance.LOCAL, 2, 3);
        //集群在同一个机房用HostDistance.LOCAL   不同的机房用HostDistance.REMOTE   忽略用HostDistance.IGNORED
        PoolingOptions poolingOptions = new PoolingOptions()
                                            //每个连接最多允许64个并发请求
                                            .setMaxRequestsPerConnection(HostDistance.LOCAL, 64)
                                            //和集群里的每个机器都至少有2个连接
                                            .setCoreConnectionsPerHost(HostDistance.LOCAL, 2)
                                            //和集群里的每个机器都最多有6个连接
                                            .setMaxConnectionsPerHost(HostDistance.LOCAL, 6);

        //查询配置
        //设置一致性级别ANY(0),ONE(1),TWO(2),THREE(3),QUORUM(4),ALL(5),LOCAL_QUORUM(6),EACH_QUORUM(7),SERIAL(8),LOCAL_SERIAL(9),LOCAL_ONE(10);
        //可以在每次生成查询statement的时候设置，也可以像这样全局设置
        QueryOptions queryOptions = new QueryOptions().setConsistencyLevel(ConsistencyLevel.ONE);

        //重试策略
        RetryPolicy retryPolicy = DowngradingConsistencyRetryPolicy.INSTANCE;
        //端口号
        int port = 9042;
        //要连接的库，可以不写
        String keyspace = "keyspacename";

        Cluster cluster = Cluster.builder()
                              .addContactPoints(hosts)
                              .withAuthProvider(authProvider)
                              .withLoadBalancingPolicy(lbp)
                              .withSocketOptions(so)
                              .withPoolingOptions(poolingOptions)
                              .withQueryOptions(queryOptions)
                              .withRetryPolicy(retryPolicy)
                              .withPort(port)
                              .build();

        return cluster.connect(keyspace);
    }
}
