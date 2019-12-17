package com.example.redislock.service;/*
 * @program: redis-lock
 *
 * @description:
 *
 * @author: guangpeng.li
 *
 * @create: 2019-12-13 16:47
 */

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ConsumerService {
    @Value("${app.consumer.redis-key}")
    private String consumerKey;
    @Value("${app.consumer.lock-key}")
    private String lockKey;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedissonClient redissonClient;

    public ConsumerService(StringRedisTemplate stringRedisTemplate, RedissonClient redissonClient) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redissonClient = redissonClient;
    }


    public void sale() {
        String uuid = UUID.randomUUID().toString();
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 10, TimeUnit.SECONDS);
        try {
            Optional<Boolean> flagOptional = Optional.ofNullable(flag);
            if (flagOptional.isPresent() && flagOptional.get()) {
                consumer();
            } else {
                log.info("当前资源被占用，请耐心等待。。。。。。。。。");
            }
        } finally {
            String originalUuid = stringRedisTemplate.opsForValue().get(lockKey);
            if (uuid.equals(originalUuid)) {
                stringRedisTemplate.delete(lockKey);
            }
        }
    }

    public void redissonSale() {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            consumer();
        } finally {
            if (null != lock && lock.isLocked())
                lock.unlock();
        }

    }

    private void consumer() {
        String value = stringRedisTemplate.opsForValue().get(consumerKey);
        Optional<String> stringOptional = Optional.ofNullable(value);
        if (stringOptional.isPresent()) {
            int integer = Integer.parseInt(stringOptional.get()) - 1;
            stringRedisTemplate.opsForValue().set(consumerKey, String.valueOf(integer));
            log.info("资源剩余:[{}]", integer);
        } else {
            log.info("资源已消费光。。。。。。。。。。。。。。");
        }
    }
}
