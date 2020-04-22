package com.hiloj.note.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreMethod1 {
    public static void main(String[] args) {
        // 初始化两个许可证，非公平
        Semaphore semaphore = new Semaphore(5);
        System.out.println("new Semaphore(5)是否公平：" + semaphore.isFair());
        // 初始化两个许可证，公平
        semaphore = new Semaphore(5,true);
        System.out.println("new Semaphore(5,true)是否公平：" + semaphore.isFair());

        // 获取当前可用的许可证数量
        System.out.println("当前可用许可证数量为：" + semaphore.availablePermits());

        // 获取一个许可证
        try {
            // 会一直阻塞，直到获取许可证或者被中断
            semaphore.acquire();
            System.out.println("semaphore.acquire()当前可用许可证数量为：" + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放一个许可证
        semaphore.release();
        System.out.println("semaphore.release()当前可用许可证数量为：" + semaphore.availablePermits());

        // 获取3个许可证
        try {
            semaphore.acquire(3);
            System.out.println("semaphore.acquire(3)当前可用许可证数量为：" + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 释放3个许可证
        semaphore.release(3);
        System.out.println("semaphore.release(3)当前可用许可证数量为：" + semaphore.availablePermits());

        // 是否有正在等待许可证的线程
        System.out.println("是否有正在等待许可证的线程:" + semaphore.hasQueuedThreads());

        // 正在等待许可证的队列长度
        System.out.println("正在等待许可证的队列长度" + semaphore.getQueueLength());

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // lambda需要声明为final类型或不会发生变化
        final Semaphore finalSemaphore = semaphore;

        new Thread(()->{
            int num = finalSemaphore.drainPermits();
            System.out.println("线程【"+Thread.currentThread().getName()+"】通过drainPermits获取剩余的所有许可证：" + num);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 释放所有许可证
            finalSemaphore.release(num);
            System.out.println("线程【"+Thread.currentThread().getName()+"】释放了"+num+"个许可证");
        },"t1").start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            try {
                System.out.println("线程【"+Thread.currentThread().getName()+"】准备获取1个许可证");
                finalSemaphore.acquire();
                System.out.println("线程【"+Thread.currentThread().getName()+"】获取了1个许可证");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finalSemaphore.release();
            System.out.println("线程【"+Thread.currentThread().getName()+"】释放了1个许可证");
        },"t2").start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程t2启动之后，当前可用许可证数量为：" + finalSemaphore.availablePermits());
        System.out.println("线程t2启动之后，是否有正在等待许可证的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("线程t2启动之后，正在等待许可证的队列长度：" + finalSemaphore.getQueueLength());

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            try {
                System.out.println("线程【"+Thread.currentThread().getName()+"】准备获取2个许可证");
                finalSemaphore.acquire(2);
                System.out.println("线程【"+Thread.currentThread().getName()+"】获取了2个许可证");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finalSemaphore.release(2);
            System.out.println("线程【"+Thread.currentThread().getName()+"】释放了2个许可证");
        },"t3").start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程t3启动之后，当前可用许可证数量为：" + finalSemaphore.availablePermits());
        System.out.println("线程t3启动之后，是否有正在等待许可证的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("线程t3启动之后，正在等待许可证的队列长度：" + finalSemaphore.getQueueLength());


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("等待5s之后，当前可用许可证数量为：" + finalSemaphore.availablePermits());
        System.out.println("等待5s之后，是否有正在等待许可证的线程：" + finalSemaphore.hasQueuedThreads());
        System.out.println("等待5s之后，正在等待许可证的队列长度：" + finalSemaphore.getQueueLength());
    }
}
