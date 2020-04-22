package com.hiloj.note.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  实例化几类常用的线程池
 */
public class ExecutorMethod7 {
    public static void main(String[] args) {
        Runnable r = ()->{
            System.out.println("线程：【" + Thread.currentThread().getName() + "】运行了");
        };

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.submit(r);
        System.out.println("打印singleThreadExecutor --->" + singleThreadExecutor);
        singleThreadExecutor.shutdown();
        try {
            Thread.sleep(10);
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool(r1 -> new Thread(r1,"cachedThreadPool"));
        cachedThreadPool.submit(r);
        System.out.println("打印cachedThreadPool --->" + cachedThreadPool);
        cachedThreadPool.shutdown();
        try {
            Thread.sleep(10);
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        fixedThreadPool.submit(r);
        System.out.println("打印fixedThreadPool --->" + fixedThreadPool);
        fixedThreadPool.shutdown();
        try {
            Thread.sleep(10);
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        workStealingPool.submit(r);
        System.out.println("打印workStealingPool --->" + workStealingPool);
        workStealingPool.shutdown();
        try {
            Thread.sleep(10);
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        scheduledThreadPool.schedule(r,2, TimeUnit.SECONDS);
        System.out.println("打印scheduledThreadPool --->" + scheduledThreadPool);
        scheduledThreadPool.shutdown();
    }
}