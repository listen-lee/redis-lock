package com.example.redislock.redis.config;/*
 * @program: redis-lock
 *
 * @description:
 *
 * @author: guangpeng.li
 *
 * @create: 2019-12-16 17:14
 */

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {
    @Bean(name = "redissonConfig")
    public Config redissonConfig() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://172.16.0.241:6379");
//        config.useClusterServers()
//                // use "rediss://" for SSL connection
//                .addNodeAddress("redis://172.16.0.241:6379");
        return config;
    }

    @Bean
    public RedissonClient redissonClient() {
        return Redisson.create(redissonConfig());
    }
}
