package com.hiloj.note.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 *  参赛选手
 */
public class Player implements Runnable {
    private CountDownLatch countDownLatch;
    public Player(CountDownLatch countDownLatch){this.countDownLatch = countDownLatch;}
    @Override
    public void run() {
        try {
            System.out.println("选手：" + Thread.currentThread().getName() + "准备就绪");
            countDownLatch.await();
            System.out.println("选手：" + Thread.currentThread().getName() + "起跑了");
            int i = ThreadLocalRandom.current().nextInt(10);
            TimeUnit.SECONDS.sleep(i);
            System.out.println("选手：" + Thread.currentThread().getName() + "到达终点");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
