package com.hiloj.note.bizscene;

import java.util.concurrent.TimeUnit;

/**
 *  死锁案例
 *  jps -l
 *  jstack pid
 */
public class DeathLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        new Thread(()->{
            synchronized (o1){
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获得锁o1，等待获取锁o2");
                try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
                synchronized (o2){
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获得锁o2");
                }
            }
        },"Thread-1").start();

        new Thread(()->{
            synchronized (o2){
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获得锁o2，等待获取锁o1");
                synchronized (o1){
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获得锁o1");
                }
            }
        },"Thread-2").start();
    }
}