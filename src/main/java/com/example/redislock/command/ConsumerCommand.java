package com.example.redislock.command;/*
 * @program: redis-lock
 *
 * @description:
 *
 * @author: guangpeng.li
 *
 * @create: 2019-12-16 17:07
 */

import com.example.redislock.task.ConsumerTask;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@ConditionalOnExpression("${app.consumer.task-enabled}")
@Component
public class ConsumerCommand implements CommandLineRunner {
    private final ConsumerTask consumerTask;

    public ConsumerCommand(ConsumerTask consumerTask) {
        this.consumerTask = consumerTask;
    }

    @Override
    public void run(String... args) throws Exception {
        consumerTask.redissonTask();
    }
}
