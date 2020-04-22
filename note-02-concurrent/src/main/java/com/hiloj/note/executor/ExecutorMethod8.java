package com.hiloj.note.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 *  实例化不可配置的线程池
 */
public class ExecutorMethod8 {
    public static void main(String[] args) {
        ExecutorService service = Executors.unconfigurableExecutorService(Executors.newFixedThreadPool(1));
        service.submit(()->{
            System.out.println("unconfigurableExecutorService");
        });

        service.shutdown();

        // 存在延时调用的方法
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        // 包装后，只有ExecutorService接口及其父类中的方法可以使用，延时调用方法无效
        ExecutorService scheduledExecutorService = Executors.unconfigurableScheduledExecutorService(executor);
        scheduledExecutorService.submit(()->{
            System.out.println("unconfigurableScheduledExecutorService");
        });

        scheduledExecutorService.shutdown();
    }
}