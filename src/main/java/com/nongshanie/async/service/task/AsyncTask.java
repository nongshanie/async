package com.nongshanie.async.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author: zhouxinhang
 * @date: 2019/6/12
 * @Description: 异步调用方法
 */
@Component
@Slf4j
public class AsyncTask {
    public static Random random = new Random();

    @Async("asyncCommon")
    public void doMore(CountDownLatch startGate,CountDownLatch endGate, ConcurrentHashMap<String,Object> map, int i) throws InterruptedException {
        startGate.await();
        log.info("开始批量任务"+i);
        long start = System.currentTimeMillis();
        //Thread.sleep(random.nextInt(10000));
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        log.info("结束批量任务"+i+"，耗时：" + (end - start) + "毫秒");
        map.put("批量任务"+i,"批量任务"+i);
        endGate.countDown();
    }
}