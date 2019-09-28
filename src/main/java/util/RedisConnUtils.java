package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 获取与redis连接的工具类
 */
public class RedisConnUtils {

    private static JedisPool pool = null;

    static {
        /**设置Redis 连接池*/
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(1);

        String host = "192.168.194.128";
        int port = 6379;
        pool = new JedisPool(poolConfig,host,port);
    }

    public static Jedis getConn() {
        Jedis jedis = pool.getResource();
        jedis.auth("redis");
        return jedis;
    }

    public static void closeConn(Jedis jedis){
        jedis.close();
    }
}
