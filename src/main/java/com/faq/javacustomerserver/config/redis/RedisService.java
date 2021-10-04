package com.faq.javacustomerserver.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Set;

/**
 * @author xuebin3765@163.com
 * @date 2018/7/17 15:59
 */
public interface RedisService {
    public boolean set(final String key , Object value);
    public boolean setObjToJson(final String key , Object value);
    public Set<String> getAllKeysSet(String prefix);
    public void deleteKeys(final String key);
    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key , Object value , Long expireTime);

    /**
     * 批量删除对应的value
     * @param keys
     */
    public void remove(final String... keys);

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern);
    /**
     * 删除对应的value
     * @param key
     */
    public void remove(final String key);
    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key);
    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(final String key);
    public <T> T getJsonToObj(String key,Class<T> valueType) throws JsonProcessingException;
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey);

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k,Object v);
    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List lRange(String k, long l, long l1);

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key,Object value);

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key);
    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure);

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1);
}
