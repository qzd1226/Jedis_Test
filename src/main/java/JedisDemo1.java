import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import java.util.*;
public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.89",6379);
        String pong = jedis.ping();
        System.out.println("连接成功："+pong);
        //添加数据
        jedis.set("k1","v1");
        jedis.set("k2","v2");
        jedis.set("k3","v3");
        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());
        for(String key:keys){
            System.out.println(key);
        }
        System.out.println(jedis.exists("k1"));
        System.out.println(jedis.ttl("k1"));
        System.out.println(jedis.get("k1"));


        //williamlast@WilliamdeMBP ~ % sudo redis-server /usr/local/etc/redis.conf
        //Password:
        //williamlast@WilliamdeMBP ~ % redis-cli /usr/local/etc/redis.conf
        //(error) ERR unknown command '/usr/local/etc/redis.conf', with args beginning with:
        //williamlast@WilliamdeMBP ~ % redis-cli -p 6379
        //127.0.0.1:6379> config get protected-mode
        //1) "protected-mode"
        //2) "yes"
        //127.0.0.1:6379> config set protected-mode no
        //OK
        //127.0.0.1:6379> config get protected-mode
        //1) "protected-mode"
        //2) "no"
        //127.0.0.1:6379> exit
        //williamlast@WilliamdeMBP ~ % redis-cli -p 6379
        //127.0.0.1:6379> config get protected-mode
        //1) "protected-mode"
        //2) "no"
        //127.0.0.1:6379>
        jedis.close();
    }
    @Test
    public void demo2(){
        Jedis jedis = new Jedis("192.168.1.89",6379);
        jedis.lpush("key1","lucy","mary","jack");
        List<String> values = jedis.lrange("key1",0,-1);
        for(String key:values){
            System.out.println(key);
        }
        jedis.close();
    }
    @Test
    public void demo3(){
        Jedis jedis = new Jedis("192.168.1.89",6379);
        jedis.sadd("name", "lucy","jack");
        Set<String> smembers = jedis.smembers("name");
        for(String order: smembers){
            System.out.println(order);
        }
        jedis.close();

    }
}
