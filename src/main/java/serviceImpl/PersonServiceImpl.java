package serviceImpl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import service.PersonService;

import java.util.concurrent.TimeUnit;

public class PersonServiceImpl implements PersonService {

    private RedisTemplate<String,String> redisTemplate = new RedisTemplate();

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**通过key从redis中得到值*/
    @Override
    public String getString(String key) {
        ValueOperations<String,String> string = redisTemplate.opsForValue();
        /**为key设置过期时间*/
        /**redisTemplate.opsForValue().set("welcome","welcomeToJava",2, TimeUnit.HOURS);*/
        if(redisTemplate.hasKey(key)){
            System.out.println("从redis中获取");
            String value = string.get(key);
            return value;
        }
        else {
            System.out.println("从数据库中获取");
            String result = "hello world";
            /**存入redis中*/
            string.set(key,result);
            return result;
        }
    }
}
