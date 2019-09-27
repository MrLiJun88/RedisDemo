package pool;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import util.RedisConnUtils;

import java.util.Map;

public class RedisPoolDemo {

    /**
     * 需求：用hash存储一个对象
     * 判断Redis中是否存在该key,如果存在，直接返回值
     * 如果不存在，则查询数据库，存入redis并返回
     */
    @Test
    public void testSaveObjectByHash() {
        Jedis jedis = RedisConnUtils.getConn();
        String key = "person";
        if(jedis.exists(key)){
           Map<String,String> map = jedis.hgetAll(key);
           map.forEach((k,v) -> System.out.println(k + " " + v));
            System.out.println("from redis");
        }
        else {
            jedis.hset(key,"name","lijun");
            jedis.hset(key,"age","25");
            jedis.hset(key,"address","beijing");
            System.out.println("from mysql");
        }
        RedisConnUtils.closeConn(jedis);
    }
}
