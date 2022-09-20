import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import java.util.*;
public class PhoneCode {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.89",6379);
        verifyCode("199872");
        String code = jedis.get("VerifyCode199872:code");
        System.out.println("code:" + code);
        getRedisCode("199872",code);
    }

    // 生成六位数字验证码
    public static String getCode(){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(10);
            code += rand;
        }
        return code;
    }

    // 让每个手机每天只能发送3次， 验证码放到redis中去 设置存活时间
    public static void verifyCode(String phone){
        // 连接redis
        Jedis jedis = new Jedis("192.168.1.89",6379);
        // 拼接key
        // 手机发送Key
        String countKey = "VerifyCode" + phone + ":count";
        //验证码key
        String codeKey = "VerifyCode" + phone +":code";
        //每个手机只能发送三次
        String count = jedis.get(codeKey);
        if(count == null){
            //没有发送次数 第一次发送
            //设置发送次数为1
            jedis.setex(countKey,24*60*60,"1");
        }else if(Integer.parseInt(count) <= 2){
            jedis.incr(countKey);
        }else if(Integer.parseInt(count) > 2){
            //发送三次 不能再发送
            System.out.println("今天已经发送了三次了，不能再发送了");
            jedis.close();
        }
        //发送验证码到redis里面
        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
        jedis.close();
    }
    // 验证码校验
    public static void getRedisCode(String phone, String code){
        Jedis jedis = new Jedis("192.168.1.89",6379);
        //验证码Key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        //判断
        if(redisCode.equals(code)){
            System.out.println("成功");
        }else{
            System.out.println("失败");
        }
        jedis.close();
    }
}
