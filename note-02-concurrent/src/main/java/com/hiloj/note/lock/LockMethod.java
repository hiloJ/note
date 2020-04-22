package com.hiloj.note.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock锁方法：
 * 1.【t1】通过lock.lock()获取锁，并持有锁5秒，然后解锁
 * 2.【t2】通过lock.lock()去获取锁，获取锁后持有锁1秒，然后解锁
 * 3.【t3】通过lock.tryLock()去获取锁，获取锁之后持有锁1秒，然后解锁
 * 4.【t4】通过lock.tryLock(long,TimeUnit)尝试在2秒内去获取锁，获取锁之后持有锁1秒，然后解锁
 * 5.【t5】通过lock.lockInterruptibly()去获取锁，获取锁之后持有锁1秒，然后解锁
 * 所有线程启动5秒后，中断【t5】
 */
public class LockMethod {
    public static void main(String[] args) {
        // 定义非公平锁
        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            // 通过lock.lock()获取锁，并持有锁5秒，然后解锁
            lock.lock();
            try {
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获取了锁");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 持有锁5s后，释放了锁");
            }
        }, "Thread-t1").start();

        new Thread(() -> {
            // 通过lock.lock()去获取锁，获取锁后持有锁1秒，然后解锁
            lock.lock();
            try {
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 获取了锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 持有锁1s后，释放了锁");
            }
        }, "Thread-t2").start();

        new Thread(() -> {
            // 通过lock.tryLock()去获取锁，获取锁之后持有锁1秒，然后解锁
            if (lock.tryLock()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 尝试获取到了锁，持有锁1s，然后解锁");
                }
            } else {
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 尝试获取锁失败，不再获取锁");
            }
        }, "Thread-t3").start();

        new Thread(() -> {
            // 通过lock.tryLock(long,TimeUnit)尝试在2秒内去获取锁，获取锁之后持有锁1秒，然后解锁
            try {
                if (lock.tryLock(2, TimeUnit.SECONDS)) {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.tryLock(long,TimeUnit)尝试在2s内获取锁成功");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.tryLock(long,TimeUnit)尝试在2s内获取锁失败，不再获取锁");
                }
            } catch (InterruptedException e) {
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.tryLock(long,TimeUnit)尝试在2s内获取锁，被中断，不再获取锁");
            } finally {
                if (lock.hasQueuedThread(Thread.currentThread())) {
                    lock.unlock();
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.tryLock(long,TimeUnit)尝试在2s内获取锁成功，持有锁1s后，解锁");
                }
            }
        }, "Thread-t4").start();

        Thread t5 = new Thread(() -> {
            // 通过lock.lockInterruptibly()去获取锁，获取锁之后持有锁1秒，然后解锁
            try {
                lock.lockInterruptibly();
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.lockInterruptibly()去获取锁，被中断，不再获取锁");
            } finally {
                if (lock.hasQueuedThread(Thread.currentThread())) {
                    lock.unlock();
                    System.out.println("线程【" + Thread.currentThread().getName() + "】\t 通过lock.lockInterruptibly()去获取锁，获取锁之后持有锁1秒，然后解锁");
                }
            }
        }, "Thread-t5");

        t5.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t5.interrupt();
    }
}