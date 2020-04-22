package com.hiloj.note.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *  家务
 */
public class HouseWork implements Runnable{
    // 家务名称
    private String name;
    private CountDownLatch countDownLatch;

    public HouseWork(String name, CountDownLatch countDownLatch){
        this.name = name;
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "做的家务是：" + name);
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            System.out.println(Thread.currentThread().getName() + "家务做完了");
            countDownLatch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
