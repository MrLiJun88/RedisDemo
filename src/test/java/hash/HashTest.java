package hash;

import entity.Person;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import util.RedisConnUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通过Redis对hash类型的操作
 */
public class HashTest {

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

    @Test
    public void testSaveObjectForEntity() {
        Jedis jedis = RedisConnUtils.getConn();
        String id = "1";
        String key = Person.getKeyName() + id;
        /**如果key存在，则从redis中取出*/
        if(jedis.exists(key)){
            System.out.println("from redis");
            Map<String, String> hash = jedis.hgetAll(key);
            Person person = new Person(hash.get("name"),Integer.parseInt(hash.get("age")),hash.get("address"));
            System.out.println(person);
        }
        /**如果redis中不存在，则从数据库中取出，再存入redis中*/
        else {
            System.out.println("from mysql");
            Person person = new Person("wangwu",36,"xian");
            System.out.println(person);

            /**将数据库中查询出的对象存入hash类型中*/
            Map<String,String> hash = new HashMap<>();
            hash.put("name",person.getName());
            hash.put("age",person.getAge() + "");
            hash.put("address",person.getAddress());
            jedis.hmset(key,hash);
        }
        RedisConnUtils.closeConn(jedis);
    }
}
