package com.lzb.cloudlike.provider.third;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.protocol.ScoredEntry;
import org.redisson.config.Config;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * projectName: cloudlike
 *
 * @author: 罗智博
 * time: 2020/11/2 21:12
 * description: 给予Redisson实现Redis数据库的操作
 */

public class RedissonUtils {
    public static RedissonClient client;
    static {
        Config config = new Config();
        config.setThreads(1000);
        config.useSingleServer().setAddress("redis://39.105.189.141:6380").setPassword("qfjava");
//        config.useSingleServer();单机
//        config.useSentinelServers();哨兵
//        config.useClusterServers();集群
        client = Redisson.create(config);
    }
    //新增
    public static void setStr(String key,String val){
        client.getBucket(key).set(val);
    }
    public static void setStr(String key,Object val){
        client.getBucket(key).set(val);
    }
    public static void setStr(String key,String val,long seconds){
        client.getBucket(key).set(val,seconds, TimeUnit.SECONDS);
    }
    public static void setSet(String key,String val){
        client.getSet(key).add(val);
    }
    public static void setSet(String key, Set<Object> vals){
        client.getSet(key).addAll(vals);
    }
    public static void setZset(String key,double score,String val){
        client.getScoredSortedSet(key).add(score,val);
    }
    public static void setZset(String key, Map<Object,Double> vals){
        client.getScoredSortedSet(key).addAll(vals);
    }
    //查询
    public static String getStr(String key){
        return client.getBucket(key).get().toString();
    }
    public static Set<Object> getSet(String key){
        return client.getSet(key).readAll();
    }
    public static Collection<Object> getZSet(String key){
        return client.getScoredSortedSet(key).readAll();
    }
    public static Collection<ScoredEntry<Object>> getZSet(String key, double score){
        return client.getScoredSortedSet(key).entryRange(score,true,System.currentTimeMillis(),true);
        //return client.getScoredSortedSet(key).readSort(SortOrder.ASC,score,);
    }
    //校验
    public static boolean checkSet(String key,Object val){
        return client.getSet(key).contains(val);
    }
    public static boolean checkZSet(String key,Object val){
        return client.getScoredSortedSet(key).contains(val);
    }
    public static boolean checkKey(String key){
        return client.getKeys().countExists(key)>0;
    }
    //设置有效期
    public static void setTime(String key,long time,TimeUnit timeUnit){
        client.getKeys().expire(key, time, timeUnit);
    }
    //删除key
    public static void delKeys(String... keys){
        client.getKeys().delete(keys);
    }
    //移除元素
    public static boolean delZSet(String key,String val){
        return client.getScoredSortedSet(key).remove(val);
    }
    //分布式锁 =Redis(setnx)+Java(Lock)
    //加锁
    public static void lock(String key){
        client.getLock(key).lock();
    }
    //加锁 设置默认的超时时间
    public static void lock(String key,long seconds){
        client.getLock(key).lock(seconds,TimeUnit.SECONDS);
    }
    //释放锁
    public static void unlock(String key){
        client.getLock(key).unlock();
    }
    public static Iterable<String> getKeys(String pat){
        return client.getKeys().getKeysByPattern(pat);
    }
}