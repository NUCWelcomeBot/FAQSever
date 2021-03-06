package com.faq.javacustomerserver.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis service 类
 * @author xuebin3765@163.com
 * @date 2018/7/17 15:59
 */
@Service
public class RedisServiceImpl implements RedisService {
    public final static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 写入缓存
     * @param key
     * @param value
     * @return Boolean
     */
    public boolean set(final String key , Object value){
        boolean result = false;
        try {
            ValueOperations<Serializable , Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key , value);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean setObjToJson(String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable , Object> valueOperations = redisTemplate.opsForValue();
            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(value);
            valueOperations.set(key , json);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Set getAllKeysSet(String prefix) {
        return redisTemplate.keys(prefix.concat("*")); // 表示以prefix开头的所有key集合
    }

    @Override
    public void deleteKeys(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key , Object value , Long expireTime){
        boolean result = false;
        try {
            ValueOperations<Serializable , Object> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(key , value);
            redisTemplate.expire(key , expireTime , TimeUnit.MILLISECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    @Override
    public <T> T getJsonToObj(String key,Class<T> valueType) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json =(String) get(key);
        try {
            return mapper.readValue(json,valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey){
        HashOperations<String, Object, Object>  hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v){
        ListOperations list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List lRange(String k, long l, long l1){
        return redisTemplate.opsForList().range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key,double scoure,double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }
}