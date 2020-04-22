package com.hiloj.note.thread;

import java.util.concurrent.TimeUnit;

/**
 * 线程方法：
 *  线程休眠：Thread.sleep(time)或TimeUnit.SECONDS.sleep(time)
 *  线程启动：thread.start()
 *  存活线程数：Thread.activeCount()
 *  当前线程：Thread.currentThread()
 *  线程组：thread.getThreadGroup()
 *  线程优先级：thread.getPriority()
 *  线程是否存活：thread.isAlive()
 *  线程是否守护线程：thread.isDaemon()
 *  中断线程：thread.interrupt()
 *  线程是否中断，清除中断标志位：Thread.interrupted()
 *  线程是否中断，不清除中断标志位：thread.isInterrupted()
 *  线程插队：thread.join()
 */
public class ThreadMethod {
    public static void main(String[] args) {
        // 基本方法
        // BaseMethod();
        // 线程插队
        // threadJoin();
        // 守护线程
        // threadDeamon();
        threadInterrupt();
    }

    // 线程中断
    private static void threadInterrupt() {
        new Thread(()->{
            for (int i = 0; i < 4; i++) {
                if (i == 1) {
                    // 当前线程中断
                    Thread.currentThread().interrupt();
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 调用了interrupt()");
                }
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 当前值为：" + i);
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t Thread.interrupted()为：" + Thread.interrupted());
            }
        },"Thread-A").start();

        new Thread(()->{
            for (int i = 0; i < 4; i++) {
                if (i == 1) {
                    // 当前线程中断
                    Thread.currentThread().interrupt();
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 调用了interrupt()");
                }
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 当前值为：" + i);
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t Thread.currentThread().isInterrupted()为：" + Thread.currentThread().isInterrupted());
            }
        },"Thread-B").start();
    }

    // 设置守护线程
    private static void threadDeamon() {
        // 添加退出钩子
        Runtime.getRuntime().addShutdownHook(new Thread(()-> System.out.println("虚拟机成功退出")));
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);} catch(InterruptedException e) {e.printStackTrace();}
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 守护线程在运行");
            }
        }, "Thread-deamon-1");


        // 设置守护线程
        thread.setDaemon(true);
        thread.start();
        try {TimeUnit.SECONDS.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("主线程即将推出");
    }

    private static void threadJoin() {
        Thread thread = new Thread(() -> {
            System.out.println("线程【" + Thread.currentThread().getName() + "】\t 开始执行");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程【" + Thread.currentThread().getName() + "】\t 结束执行");
        }, "Thread-join-1");

        new Thread(()->{
            System.out.println("线程【" + Thread.currentThread().getName() + "】\t 开始执行");
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程【" + Thread.currentThread().getName() + "】\t 结束执行");
        },"Thread-join-2").start();

        thread.start();
    }

    private static void BaseMethod() {
        Thread thread1 = new Thread(new ThreadGroup("Thread-1-group"),() -> {
            try {
                // 线程休眠
                TimeUnit.SECONDS.sleep(1);
                // 存活线程数
                System.out.println("当前存活线程数为：" + Thread.activeCount());
                // 当前线程
                Thread thread = Thread.currentThread();
                // 当前线程名称
                System.out.println("线程名称为：" +thread.getName());
                // 当前线程组
                System.out.println("线程组为：" +thread.getThreadGroup());
                // 当前线程优先级
                System.out.println("线程优先级为：" + thread.getPriority());
                // 线程是否存活
                System.out.println("线程是否存活：" + thread.isAlive());
                // 是否是守护线程
                System.out.println("是否是守护线程：" + thread.isDaemon());
                // 是否中断
                System.out.println("线程是否中断" + thread.isInterrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-1");

        // 线程启动
        thread1.start();
    }
}