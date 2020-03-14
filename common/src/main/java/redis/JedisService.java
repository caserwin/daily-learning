package redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yidxue
 */
public class JedisService {

    private JedisCluster cluster;

    public JedisService(String redisCluster) {
        cluster = new JedisConn().getConn(redisCluster);
    }

    /**
     * get keys from redis by scan
     */
    public HashSet<String> getKeysByScan(String pattern) {
        ScanParams scanParams = new ScanParams().count(100).match(pattern);
        HashSet<String> allKeys = new HashSet<>();
        cluster.getClusterNodes().values().forEach((pool) -> {
            String cur = ScanParams.SCAN_POINTER_START;
            do {
                try (Jedis jedis = pool.getResource()) {
                    ScanResult<String> scanResult = jedis.scan(cur, scanParams);
                    allKeys.addAll(scanResult.getResult());
                    cur = scanResult.getStringCursor();
                }
            } while (!cur.equals(ScanParams.SCAN_POINTER_START));
        });
        return allKeys;
    }

    /**
     * get keys from redis by scan
     */
    public HashSet<String> getKeysByScanLimit(String pattern, int limit) {
        ScanParams scanParams = new ScanParams().count(200).match(pattern);
        HashSet<String> allKeys = new HashSet<>();
        cluster.getClusterNodes().values().forEach((pool) -> {
            String cur = ScanParams.SCAN_POINTER_START;
            do {
                try (Jedis jedis = pool.getResource()) {
                    ScanResult<String> scanResult = jedis.scan(cur, scanParams);
                    allKeys.addAll(scanResult.getResult());
                    cur = scanResult.getStringCursor();
                    if (allKeys.size() > limit) {
                        break;
                    }
                }
            } while (!cur.equals(ScanParams.SCAN_POINTER_START));
        });
        return allKeys;
    }


    /**
     * get values by key
     */
    public HashMap<String, String> getValueByKeys(Set<String> keys) {
        HashMap<String, String> kv = new HashMap<>();
        for (String key : keys) {
            if (cluster.get(key) == null) {
                continue;
            }
            kv.put(key, cluster.get(key));
        }
        return kv;
    }

    /**
     * get values by key
     */
    public HashMap<String, String> getValueByKeysUpadte(HashSet<String> keys) {
        HashMap<String, String> kv = new HashMap<>();
        for (String key : keys) {
            if (cluster.get(key) == null) {
                continue;
            }

            HashMap<String, String> map = JSON.parseObject(cluster.get(key), new TypeReference<HashMap<String, String>>() {
            });

            kv.put(key, map.get("b"));
        }
        return kv;
    }

    public JedisCluster getCluster() {
        return this.cluster;
    }
}

