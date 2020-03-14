package redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author yidxue
 */
public class JedisConn {
    private int timout = 2000;
    private int coreSize = 5;
    private int maxSize = 10;
    private int maxRedirections = 3;
    JedisCluster cluster = null;
    private GenericObjectPoolConfig jedisPoolConfig;


    public JedisCluster getConn(String redisCluster) {
        if (cluster == null) {
            // 集群写法
            HashSet<HostAndPort> hosts = Arrays.stream(redisCluster.split(",")).map(
                    x -> new HostAndPort(x.split(":")[0], Integer.parseInt(x.split(":")[1])))
                    .collect(Collectors.toCollection(HashSet::new));

            jedisPoolConfig = new GenericObjectPoolConfig();
            jedisPoolConfig.setMaxIdle(coreSize);
            jedisPoolConfig.setMinIdle(coreSize);
            jedisPoolConfig.setMaxWaitMillis(2000);
            jedisPoolConfig.setMaxTotal(maxSize);

            cluster = new JedisCluster(hosts, timout, maxRedirections, jedisPoolConfig);
        }
        return cluster;
    }
}