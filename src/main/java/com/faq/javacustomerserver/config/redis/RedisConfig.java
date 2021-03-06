package com.faq.javacustomerserver.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.time.Duration;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400*30)
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return null;
        };
    }
    @Bean(name = {"redisTemplate"})
    @ConditionalOnMissingBean(name = {"redisTemplate"})
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object> template  = new RedisTemplate<String,Object>();
        template.setConnectionFactory(factory);
        // ??????Jackson2JsonRedisSerializer???????????????
        setRedisTemplate(template);
        template.afterPropertiesSet();
        return template;
    }
    private void setRedisTemplate(RedisTemplate<String,Object> template){
        Jackson2JsonRedisSerializer jackson2JsonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // ????????????????????????????????????????????????final????????????final?????????????????????String,Integer???????????????
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance ,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        om.registerModule(new Hibernate5Module());
        jackson2JsonSerializer.setObjectMapper(om);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        // redis value ?????????????????????jackson
        template.setValueSerializer(jackson2JsonSerializer);
        // redis hash key ?????????????????????stringSerial
        template.setHashKeySerializer(stringRedisSerializer);
        // redis hash value ?????????????????????jackson
        template.setHashValueSerializer(jackson2JsonSerializer);
    }
    /**
     * ?????????????????????
     * @param factory Redis ????????????????????????
     * @return ???????????????
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // ????????????????????????????????? Config ??????????????????????????????????????????
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                // ?????????????????? 10 ??????
                .entryTtl(Duration.ofMinutes(10))
                .prefixCacheNameWith("cache:user:")
                // ???????????? null ???
                .disableCachingNullValues()
                // ?????? key ?????????
                .serializeKeysWith(keyPair())
                // ?????? value ?????????
                .serializeValuesWith(valuePair());
        // ?????? Redis ???????????????
        return RedisCacheManager.builder(factory)
                .withCacheConfiguration("user", cacheConfig).build();
    }

    /**
     * ??????????????????
     * @return StringRedisSerializer
     */
    private RedisSerializationContext.SerializationPair<String> keyPair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
    }

    /**
     * ??????????????????????????? GenericJackson2JsonRedisSerializer ?????????????????????
     * @return GenericJackson2JsonRedisSerializer
     */
    private RedisSerializationContext.SerializationPair<Object> valuePair() {
        return RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());
    }
}