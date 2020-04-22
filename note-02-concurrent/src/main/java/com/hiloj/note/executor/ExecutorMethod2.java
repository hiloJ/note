package com.hiloj.note.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorMethod2 {
    public static void main(String[] args) {
        /**
         * ExecutorService两种关闭的效果：
         *  1. shutdown 当正在运行的线程运行结束后，关闭线程池
         *  2. shutdownNow 立即关闭线程池，正在运行的线程将被停止
         */
        int type = 1;
        ExecutorService executorService = Executors.newCachedThreadPool();

        switch (type) {
            case 1:
                executorService.submit(() -> {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdown 开始 ");
                    try {
                        Thread.sleep(1000);
                        System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdown 结束 ");
                    } catch (InterruptedException e) {
                        System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdown 中断 ");
                    }
                });
                    System.out.println("线程执行 shutdown");
                    executorService.shutdown();
                break;
            case 2:
                executorService.submit(() -> {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdownNow 开始 ");
                    try {
                        Thread.sleep(1000);
                        System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdownNow 结束 ");
                    } catch (InterruptedException e) {
                        System.out.println("线程【" + Thread.currentThread().getName() + "】 执行 shutdownNow 中断 ");
                    }
                });

                    System.out.println("线程执行 shutdownNow");
                    executorService.shutdownNow();
                break;
            default:
                break;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("isShutdown():" + executorService.isShutdown());

        System.out.println("isTerminated():" + executorService.isTerminated());

        try {
            System.out.println("awaitTermination():" + executorService.awaitTermination(1000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}