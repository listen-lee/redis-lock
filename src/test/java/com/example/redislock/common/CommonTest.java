package com.example.redislock.common;/*
 * @program: redis-lock
 *
 * @description:
 *
 * @author: guangpeng.li
 *
 * @create: 2019-12-16 16:06
 */

import org.junit.jupiter.api.Test;

import java.util.UUID;

public class CommonTest {
    @Test
    public void testUuid() {
        System.out.println(UUID.randomUUID().toString());
    }
}
