package com.hiloj.note.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量案例：停车
 * 有一个停车场，剩下最后一个停车位，此时，来了四辆车需要停车
 * 汽车A进入停车场，占用了最后一个车位，10s后离开的停车场
 * 汽车B在停车厂门口等待其他车辆出来
 * 汽车C在停车厂门口等待其他车辆出来，等待了5s后，离开寻找其他停车厂了
 * 汽车D在停车厂门口等待其他车辆出来，看到路边停车位空了一个，将车停到了路边停车位
 */
public class Parking implements Runnable {
    // 车辆名称
    private String carName;

    // 车位数量
    private Semaphore semaphore;

    public Parking(String carName, Semaphore semaphore) {
        this.carName = carName;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        switch (carName) {
            case "A":
                try {
                    semaphore.acquire();
                    System.out.println("汽车" + carName + "进入停车场，占用了最后一个车位");
                    TimeUnit.SECONDS.sleep(10);
                    semaphore.release();
                    System.out.println("汽车" + carName + "离开了停车厂，空出一个车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "B":
                System.out.println("汽车" + carName + "在停车厂门口等待其他车辆出来");
                semaphore.acquireUninterruptibly();
                System.out.println("汽车" + carName + "在停车厂门口等到了空位，将车停了进去");
                break;
            case "C":
                try {
                    System.out.println("汽车" + carName + "在停车厂门口等待其他车辆出来");
                    boolean tryAcquire = semaphore.tryAcquire(5, TimeUnit.SECONDS);
                    if (tryAcquire) {
                        System.out.println("汽车" + carName + "等了没有5s，等到了车位，将车停了进去");
                    } else {
                        System.out.println("汽车" + carName + "等了5s，没有等到车位，离开寻找其他停车厂了");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "D":
                try {
                    System.out.println("汽车" + carName + "在停车厂门口等待其他车辆出来");
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    System.out.println("汽车" + carName + "看到路边停车位空了一个，将车停到了路边停车位");
                }
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // 定义停车场空车位
        Semaphore semaphore = new Semaphore(1);
        new Thread(new Parking("A", semaphore)).start();
        new Thread(new Parking("B", semaphore)).start();
        new Thread(new Parking("C", semaphore)).start();
        Thread t = new Thread(new Parking("D", semaphore));
        t.start();
        try {
            Thread.sleep(3000);
            t.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}