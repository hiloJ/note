package com.hiloj.note.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  可重入读写锁互斥规则：
 *      1- 写写互斥
 *      2- 写读互斥
 *      3- 读写互斥
 *      4- 读读共享
 */
public class ReentrantReadWriteLockMethod {
    public static void main(String[] args) {
        // 非公平读写锁
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        int type = 1;
        switch (type){
            case 1:
                Lock writeLock1 = readWriteLock.writeLock();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取写锁...");
                    writeLock1.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了写锁");

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock1.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了写锁");
                    }
                },"1-t1").start();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取写锁...");
                    writeLock1.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了写锁");

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock1.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了写锁");
                    }
                },"1-t2").start();
                break;
            case 2:
                Lock writeLock2 = readWriteLock.writeLock();
                Lock readLock2 = readWriteLock.readLock();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取读锁...");
                    readLock2.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了读锁");

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock2.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了读锁");
                    }
                },"2-t1").start();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取写锁...");
                    writeLock2.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了写锁");

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock2.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了写锁");
                    }
                },"2-t2").start();
                break;
            case 3:
                Lock writeLock3 = readWriteLock.writeLock();
                Lock readLock3 = readWriteLock.readLock();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取写锁...");
                    writeLock3.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了写锁");

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        writeLock3.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了写锁");
                    }
                },"3-t1").start();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取读锁...");
                    readLock3.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了读锁");

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock3.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了读锁");
                    }
                },"3-t2").start();
                break;
            case 4:
                Lock readLock4 = readWriteLock.readLock();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取读锁...");
                    readLock4.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了读锁");

                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock4.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了读锁");
                    }
                },"4-t1").start();
                new Thread(()->{
                    System.out.println("线程【"+Thread.currentThread().getName()+"】尝试获取读锁...");
                    readLock4.lock();
                    System.out.println("线程【"+Thread.currentThread().getName()+"】获取了读锁");

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock4.unlock();
                        System.out.println("线程【"+Thread.currentThread().getName()+"】释放了读锁");
                    }
                },"4-t2").start();
                break;
            default:
                break;
        }
    }
}