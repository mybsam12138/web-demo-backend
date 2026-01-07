package com.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisStartupChecker implements ApplicationRunner {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        try {
            redisTemplate.opsForValue().set("startup-check", "ok");
            System.out.println("✅ Redis connected successfully at startup");
        } catch (Exception e) {
            System.err.println("❌ Redis connection failed at startup");
            throw e; // 关键：抛异常 → 应用启动失败
        }
    }
}