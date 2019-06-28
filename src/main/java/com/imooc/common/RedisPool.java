package com.imooc.common;

import com.imooc.utils.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    private  static JedisPool pool; //jedis连接池
    private static Integer  maxTotal =10; //最大连接数
    private static Integer maxIdle =10; //在jedispool中最大的idle状态的jedis实例个数
    private static Integer minIdle =10;  //在jedispool中最小的idle状态的jedis实例个数
    private static Boolean testOnBorow = true; //在borrow一个jedis实例时候是否进行验证操作
    private static Boolean testOnReturn = true; //在return一个jedis实例时候是否进行验证操作

    private static String redisIP = PropertiesUtil.getProperty("redis.ip");
    private static int  redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));
    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);//连接耗尽的时候是否阻塞，false抛出异常 true 阻塞直到超时，默认为true
        pool = new JedisPool(config,redisIP,redisPort,1000*2);
    }

    static{
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis){
        pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis){
        pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = pool.getResource();
        jedis.set("gee","gee");
        returnResource(jedis);
        pool.destroy();
        System.out.println("program is end");
    }
}


