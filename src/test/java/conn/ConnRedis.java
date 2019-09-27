package conn;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import util.RedisConnUtils;

public class ConnRedis {

    /**
     * 判断某key是否在redis中存在，若存在，则从redis中查询出来
     * 若不存在，就查询数据库，并将结果存放在redis中
     */
    @Test
    public void testString(){
        Jedis jedis = RedisConnUtils.getConn();
        String key = "hello";
        if(jedis.exists(key)){
            String value = jedis.get(key);
            System.out.println("从Redis中获取：" + value);
        }
        else {
            String value = "from mysql";
            /**将从mysql中获取的数据存储到redis中*/
            jedis.set(key,value);
            System.out.println("从mysql中获取：" + value);
        }
        RedisConnUtils.closeConn(jedis);
    }
}
