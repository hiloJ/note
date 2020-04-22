package com.hiloj.note.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 5个线程：
 *  1.【t1】通过await()进行等待
 *  2.【t2】通过awaitNanos(long)进行等待，long=1000000000，即1秒钟
 *  3.【t3】通过await(long,TimeUnit)进行等待，long=2，TimeUnit单位为秒
 *  4.【t4】通过awaitUntil(deadline)进行等待，deadline为五秒钟后的时刻
 *  5.【t5】通过awaitUninterruptibly()进行等待
 *  3种场景：
 *  1. 场景一：等待所有线程自己结束
 *  2.场景二：5个线程启动后，等待100毫秒，通过interrupt()中断所有线程
 *  3.场景三：5个线程启动后，等待100毫秒，通过condition.signalAll()唤醒所有线程
 */
public class ConditionMethod {
    public static void main(String[] args) {
        // 创建非公平lock锁
        Lock lock = new ReentrantLock();
        // 获取condition对象
        Condition condition = lock.newCondition();
        Thread t1 = new Thread(()->{
            System.out.println("线程【await()-" + Thread.currentThread().getName() + "】尝试获取锁...");
            lock.lock();
            System.out.println("线程【await()-" + Thread.currentThread().getName() + "】获取了锁");
            try {
                // 通过await()进入等待状态，直到被中断或被唤醒
                condition.await();
                System.out.println("线程【await()-" + Thread.currentThread().getName() + "】被唤醒");
            } catch (InterruptedException e) {
                System.out.println("线程【await()-" + Thread.currentThread().getName() + "】被中断");
            } finally {
                lock.unlock();
                System.out.println("线程【await()-" + Thread.currentThread().getName() + "】释放了锁");
            }
        },"t1");

        Thread t2 = new Thread(()->{
            System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】尝试获取锁...");
            lock.lock();
            System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】获取了锁");
            try {
                // awaitNanos(nanosTimeout)进入等待状态，直到被中断、被唤醒或等待时间用完
                Long remainTime = condition.awaitNanos(1000000000);
                if (remainTime > 0) {
                    // 剩余时间大于0，线程被唤醒
                    System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】被唤醒");
                } else {
                    // 等待时间耗尽而停止等待
                    System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】等待时间耗尽，停止等待");
                }
            } catch (InterruptedException e) {
                System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】被中断");
            } finally {
                lock.unlock();
                System.out.println("线程【awaitNanos(nanosTimeout)-" + Thread.currentThread().getName() + "】释放了锁");
            }
        },"t2");

        Thread t3 = new Thread(()->{
            System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】尝试获取锁...");
            lock.lock();
            System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】获取了锁");
            try {
                // awaitNanos()进入等待状态，直到被中断、被唤醒或等待时间用完
                boolean flag = condition.await(2, TimeUnit.SECONDS);
                if (flag) {
                    // true表示线程被唤醒
                    System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】被唤醒");
                } else {
                    // false表示等待时间耗尽
                    System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】等待时间耗尽，停止等待");
                }
            } catch (InterruptedException e) {
                System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】被中断");
            } finally {
                lock.unlock();
                System.out.println("线程【await(long,TimeUnit)-" + Thread.currentThread().getName() + "】释放了锁");
            }
        },"t3");

        Thread t4 = new Thread(()->{
            System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】尝试获取锁...");
            lock.lock();
            System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】获取了锁");
            try {
                // awaitUntil(deadline)进入等待状态，直到被中断、被唤醒或到达截止时间
                Date deadline = new Date(System.currentTimeMillis() + 5000);
                boolean flag = condition.awaitUntil(deadline);
                if (flag) {
                    // true表示线程被唤醒
                    System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】被唤醒");
                } else {
                    // false表示到达截止时间
                    System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】到达截止时间，停止等待");
                }
            } catch (InterruptedException e) {
                System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】被中断");
            } finally {
                lock.unlock();
                System.out.println("线程【awaitUntil(deadline)-" + Thread.currentThread().getName() + "】释放了锁");
            }
        },"t4");

        Thread t5 = new Thread(()->{
            System.out.println("线程【awaitUninterruptibly()-" + Thread.currentThread().getName() + "】尝试获取锁...");
            lock.lock();
            System.out.println("线程【awaitUninterruptibly()-" + Thread.currentThread().getName() + "】获取了锁");
            try {
                // awaitUninterruptibly()进入等待状态，直到被唤醒
                condition.awaitUninterruptibly();
                System.out.println("线程【awaitUninterruptibly()-" + Thread.currentThread().getName() + "】被唤醒");
            } finally {
                lock.unlock();
                System.out.println("线程【awaitUninterruptibly()-" + Thread.currentThread().getName() + "】释放了锁");
            }
        },"t5");

        // 线程启动
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        /**
         * 3种场景：
         * 1. 场景一：等待所有线程自己结束
         * 2.场景二：5个线程启动后，等待100毫秒，通过interrupt()中断所有线程
         * 3.场景三：5个线程启动后，等待100毫秒，通过condition.signalAll()唤醒所有线程
         */

        int type = 3;

        switch (type){
            case 1:
                try {
                    Thread.sleep(100);
                    System.out.println("================ 等待线程自己结束 ================");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    Thread.sleep(100);
                    System.out.println("================ 尝试中断线程 ================");
                    t1.interrupt();
                    t2.interrupt();
                    t3.interrupt();
                    t4.interrupt();
                    t5.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    Thread.sleep(100);
                    System.out.println("================ 开始唤醒所有还在等待的线程 ================");
                    //在main线程中，通过condition.signalAll()唤醒所有
                    System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]尝试获取锁...");
                    lock.lock();
                    System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]获取了锁,并唤醒所有等待的线程...");
                    try {
                        // 唤醒所有等待的线程
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                        System.out.println("线程[condition.signalAll()-" + Thread.currentThread().getName() + "]释放了锁.");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
