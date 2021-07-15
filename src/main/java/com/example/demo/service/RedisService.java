package com.example.demo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 添加redis信息
     * @param key
     */
    public void setString(String key, String value) {
        set(key, value, null);
    }

    //传入键值对和过期时间
    public void setString(String key, String value, Long timeOut) {
        set(key, value, timeOut);
    }

    //传入object对象
    private void set(String key, Object value, Long timeOut) {
        if (value != null) {
            if (value instanceof String) {
                String setValue = (String) value;
                redisTemplate.opsForValue().set(key, setValue);
            }
            //设置有效期
            if (timeOut != null) {
                redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
            }
        }
    }

    /**
     * 使用key查找redis信息
     * @param key
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 使用key删除redis信息
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }
}
