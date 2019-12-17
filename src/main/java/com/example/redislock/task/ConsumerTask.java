package com.example.redislock.task;/*
 * @program: redis-lock
 *
 * @description:
 *
 * @author: guangpeng.li
 *
 * @create: 2019-12-16 17:03
 */

import com.example.redislock.service.ConsumerService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Service
public class ConsumerTask {
    private final ConsumerService consumerService;

    public ConsumerTask(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    public void task() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).parallel().forEach(value -> {
            executorService.submit(consumerService::sale);
        });
    }

    public void redissonTask() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 10).parallel().forEach(value -> {
            executorService.submit(consumerService::redissonSale);
        });
    }
}
