package org.mass.framework.redis.util;

import com.alibaba.fastjson.JSONObject;
import org.mass.framework.core.utils.SpringContextUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Allen on 2016/1/9.
 */
public class RedisUtils {

    private static JedisPool jedisPool;

    public static JedisPool getJedisPool() {
        if (jedisPool == null) {
            jedisPool = (JedisPool) SpringContextUtil.getBean("jedisPool");
        }
        return jedisPool;
    }

    public static void set(String key,Object o,Integer seconds){
        Jedis jedis = getJedisPool().getResource();
        jedis.set(key, JSONObject.toJSONString(o));
        if (seconds != null && seconds > 0) {
            jedis.expire(key,seconds);
        }
        jedis.close();
    }

    public static String get(String key) {
        Jedis jedis = getJedisPool().getResource();
        String text = jedis.get(key);
        jedis.close();
        return text;
    }


    public static <T> T get(String key,Class<T> clazz){
        String text = get(key);
        T result = JSONObject.parseObject(text, clazz);
        return result;
    }

    public static void del(String key) {
        Jedis jedis = getJedisPool().getResource();
        jedis.del(key);
        jedis.close();
    }

    /**
     * 把对象放入Hash中
     */
    public static void hset(String key,String field,Object o){
        Jedis jedis = getJedisPool().getResource();
        jedis.hset(key, field, JSONObject.toJSONString(o));
        jedis.close();
    }


    /**
     * 从Hash中获取对象
     */
    public static String hget(String key,String field) {
        Jedis jedis = getJedisPool().getResource();
        String text = jedis.hget(key, field);
        jedis.close();
        return text;
    }


    /**
     * 从Hash中获取对象,转换成制定类型
     */
    public static <T> T hget(String key,String field,Class<T> clazz){
        String text = hget(key, field);
        T result = JSONObject.parseObject(text, clazz);
        return result;
    }

    /**
     * 从Hash中删除对象
     */
    public static void del(String key,String ... field) {
        Jedis jedis = getJedisPool().getResource();
        Object result=jedis.hdel(key,field);
        jedis.close();
    }

    /**
     * 清空某个DB的数据
     */
    public static void flushDB() {
        Jedis jedis = getJedisPool().getResource();
        jedis.flushDB();
        jedis.close();
    }

}
