package com.nongshanie.async.service;

import com.nongshanie.async.service.task.AsyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author: zhouxinhang
 * @date: 2019/6/12
 * @description: 异步方法
 */
@Slf4j
@Service
public class AsyncService {

    private static Random random = new Random();

    private static final int NUM = 128;


    private final AsyncTask asyncTask;

    public AsyncService(AsyncTask asyncTask) {
        this.asyncTask = asyncTask;
    }

    public void doBatch()  throws InterruptedException{
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(NUM );
        final CountDownLatch endGate = new CountDownLatch(NUM );
        final CountDownLatch startGate = new CountDownLatch(1);
        try {
            createOrder();
            for (int i = 0; i < NUM; i++) {
                asyncTask.doMore(startGate,endGate,map,i);
            }
        } catch (InterruptedException e) {
            log.error("异常", e);
        }
        startGate.countDown();
        endGate.await();
        //otherJob();
        log.info("size=====================" + map.size());
    }


    private void createOrder() {
        log.info("开始做任务1：下单成功");
    }

    /**
     * 错误使用，不会异步执行：调用方与被调方不能在同一个类。主要是使用了动态代理，同一个类的时候直接调用，不是通过生成的动态代理类调用
     */
    @Async("asyncCommon")
    public void otherJob() {
        log.info("开始做任务4：物流");
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(random.nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成任务4，耗时：" + (end - start) + "毫秒");
    }

}
