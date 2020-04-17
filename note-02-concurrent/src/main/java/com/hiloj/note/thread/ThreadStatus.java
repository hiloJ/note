package com.hiloj.note.thread;

import java.util.concurrent.TimeUnit;

/**
 *  线程的六种状态：
 *      1. Thread.State.NEW：尚未启动的线程的状态
 *      2. Thread.State.RUNNABLE：在jvm中执行的线程的状态
 *      3. Thread.State.BLOCKED：阻塞等待监视器锁定的线程的状态
 *      4. Thread.State.WAITING：线程A无限期等待线程B执行一个特定的动作时，线程A的状态
 *      5. Thread.State.TIMED_WAITING：线程A在限定时间内等待线程B执行一个特定的动作时，线程A的状态
 *      6. Thread.State.TERMINATED：退出的线程的状态
 */
class ThreadStatusObj implements Runnable{
    private Object object1;
    private Object object2;

    public ThreadStatusObj(Object object1, Object object2) {
        this.object1 = object1;
        this.object2 = object2;
    }

    @Override
    public void run() {
        synchronized (object1) {
            try {TimeUnit.SECONDS.sleep(2);} catch(InterruptedException e) {e.printStackTrace();}
            synchronized (object2) {

            }
        }
    }
}
public class ThreadStatus {
    public static void main(String[] args) {
        Object object1 = new Object();
        Object object2 = new Object();

        Thread thread1 = new Thread(new ThreadStatusObj(object1, object2), "thread-status-1");
        System.out.println("线程【" + thread1.getName() + "】启动前状态：" + thread1.getState());
        thread1.start();
        System.out.println("线程【" + thread1.getName() + "】启动后状态：" + thread1.getState());

        Thread thread2 = new Thread(new ThreadStatusObj(object2, object1), "thread-status-2");
        thread2.start();
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("线程【" + thread2.getName() + "】启动1s后状态为：" + thread2.getState());
        try {TimeUnit.SECONDS.sleep(3);} catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("线程【" + thread1.getName() + "】启动4s后状态为：" + thread1.getState());

        final Object object3 = new Object();
        Thread thread3 = new Thread(() -> {
            synchronized (object3) {
                try {
                    object3.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread-status-3");
        thread3.start();

        new Thread(()->{
            synchronized (object3) {
                object3.notify();
            }
        },"thread-status-4").start();
        System.out.println("线程【" + thread3.getName() + "】调用wait()后状态为：" + thread3.getState());
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("线程【" + thread3.getName() + "】在t4调用notify()后状态为：" + thread3.getState());
    }
}