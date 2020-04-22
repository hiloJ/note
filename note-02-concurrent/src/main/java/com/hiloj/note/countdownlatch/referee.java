package com.hiloj.note.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 *  裁判
 */
public class referee implements Runnable {
    private CountDownLatch countDownLatch;
    public referee(CountDownLatch countDownLatch){this.countDownLatch = countDownLatch;}
    @Override
    public void run() {
        System.out.println("比赛倒计时，3...2...1 比赛开始!!!");
        countDownLatch.countDown();
    }
}
