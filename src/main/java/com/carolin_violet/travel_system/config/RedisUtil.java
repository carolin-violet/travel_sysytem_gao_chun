package com.carolin_violet.travel_system.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @ClassName RedisUtil
 * @Description 由于验证码过滤模块Redistemplate还没注入spring就先跑了，所以自定义一个
 * @Author zj
 * @Date 2022/5/24 23:37
 * @Version 1.0
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, String> redis;

    //赋值一个静态的redisTemplate
    public static RedisTemplate<String, String> redisTemplate;

    @PostConstruct //此注解表示构造时赋值
    public void redisTemplate() {
        redisTemplate = this.redis;
    }
}
