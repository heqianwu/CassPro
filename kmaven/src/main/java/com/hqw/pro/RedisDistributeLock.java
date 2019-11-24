package com.hqw.pro;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.UUID;


//Redis set nx ex分布式锁为非公平锁，而且没有监听通知机制，会有大量setnx请求。
public class RedisDistributeLock {

    private static JedisCluster jedisCluster;

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * 加锁操作，为防止死锁发生，加锁的同时设定过期时间
     *
     * @param key
     * @param timeOutSec
     */
    public static void lock(String key, int timeOutSec) {
        if (key == null || key.equals("")) {
            return;
        }

        Long expireFlag = null;
        try {
            while (!tryLock(key, jedisCluster, timeOutSec)) {
                Thread.sleep(500);
            }
        } catch (Exception ex) {
        } finally {
        }
    }

    /**
     * 释放分布式锁
     *
     * @param key
     */
    public static void unLock(String key) {
        if (key == null || key.equals("")) {
            return;
        }

        try {
            jedisCluster.del(key);
        } catch (Exception ex) {
        }
    }

    /**
     * tryLock
     *
     * @param key
     * @param jedisCluster
     * @return
     */
    private static boolean tryLock(String key, JedisCluster jedisCluster, int timeOutSec) {
        String result = jedisCluster.set(key, "lockvalue", SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, timeOutSec);
        return LOCK_SUCCESS.equals(result);
    }
}





class RedisTool {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final String SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    private static final Long RELEASE_SUCCESS = 1L;

    // test
    private int count = 0;

    /**
     * 获取请求Id
     *
     * @return 返回UUID
     */
    public static String getRequestId() {
        return UUID.randomUUID().toString();
    }

    /**
     * 尝试获取分布式锁
     *
     * @param jedis      Redis客户端
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }


    /**
     * 释放分布式锁
     *
     * @param jedis     Redis客户端
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        Object result = jedis.eval(SCRIPT, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        RedisTool redisTool = new RedisTool();
        redisTool.testDistributedLock();
    }

    public void testDistributedLock() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    Jedis jedis = new Jedis("localhost");
                    String lockKey = "lock";
                    String requestId = getRequestId();
                    // 加锁
                    if (tryGetDistributedLock(jedis, lockKey, requestId, 10)) {
                        count++;
                        System.out.println(requestId + ":" + count);
                        // 解锁
                        releaseDistributedLock(jedis, lockKey, requestId);
                    } else {
                        System.out.println(requestId + ": tryGetDistributedLock fail");
                    }

                }
            });
        }
    }
}
