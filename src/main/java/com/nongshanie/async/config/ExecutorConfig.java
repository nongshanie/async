package com.nongshanie.async.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: zhouxinhang
 * @date: 2019/6/13
 * @description:    线程池配置
 */
@Slf4j
@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean
    public Executor asyncCommon() {
        log.info("start asyncRecommendArea.......");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(16);
        //配置最大线程数
        executor.setMaxPoolSize(160);
        //配置队列大小
        executor.setQueueCapacity(5000);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async_common-");
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //等待任务在关机时完成--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 30分钟后强制停止
        executor.setAwaitTerminationSeconds(60 * 30);
        //执行初始化
        executor.initialize();
        return executor;
    }
}
